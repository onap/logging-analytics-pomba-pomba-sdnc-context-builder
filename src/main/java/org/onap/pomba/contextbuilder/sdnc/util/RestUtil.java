/*
 * ============LICENSE_START===================================================
 * Copyright (c) 2018 Amdocs
 * ============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=====================================================
 */

package org.onap.pomba.contextbuilder.sdnc.util;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONException;
import org.json.JSONObject;
import org.onap.aai.restclient.client.OperationResult;
import org.onap.aai.restclient.client.RestClient;
import org.onap.pomba.common.datatypes.Attribute;
import org.onap.pomba.common.datatypes.Attribute.Name;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.common.datatypes.Network;
import org.onap.pomba.common.datatypes.PNF;
import org.onap.pomba.common.datatypes.Service;
import org.onap.pomba.common.datatypes.VFModule;
import org.onap.pomba.common.datatypes.VNF;
import org.onap.pomba.common.datatypes.VNFC;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditError;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceEntity;
import org.onap.pomba.contextbuilder.sdnc.model.VfModule;
import org.onap.pomba.contextbuilder.sdnc.model.VmName;
import org.onap.pomba.contextbuilder.sdnc.model.Vnf;
import org.onap.pomba.contextbuilder.sdnc.model.VnfAssignments;
import org.onap.pomba.contextbuilder.sdnc.model.VnfInstance;
import org.onap.pomba.contextbuilder.sdnc.model.VnfList;
import org.onap.pomba.contextbuilder.sdnc.model.VnfNetwork;
import org.onap.pomba.contextbuilder.sdnc.model.VnfTopologyIdentifier;
import org.onap.pomba.contextbuilder.sdnc.model.VnfVm;
import org.onap.pomba.contextbuilder.sdnc.service.rs.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestUtil {

    private static Logger log = LoggerFactory.getLogger(RestService.class);

    // HTTP headers
    public static final String TRANSACTION_ID = "X-TransactionId";
    public static final String FROM_APP_ID = "X-FromAppId";
    public static final String AUTHORIZATION = "Authorization";

    // AAI related
    private static final String APP_NAME = "sdncCtxBuilder";
    private static final String EMPTY_JSON_STRING = "{}";
    private static final String JSON_SERVICE_TYPE = "service-type";

    // SDNC vnf Json Path
    private static final String GENERIC_API_SPEC_PATH = "config/sdncgenericresource.spec";
    private static final String PROVIDED_CONFIGURATIONS_SPEC_PATH = "config/providedConfigurations.spec";
    private static final String PORT_MIRROR_CONFIGURATIONS_SPEC_PATH = "config/portMirrorConfigurations.spec";

    // Parameters for Query SDNC Model Data REST API URL
    private static final String SERVICE_INSTANCE_ID = "serviceInstanceId";

    private RestUtil() {
    }

    /**
     * Validates the URL parameter.
     * 
     * @throws AuditException
     *                            if there is missing parameter
     */
    public static void validateUrl(String serviceInstanceId) throws AuditException {

        if (serviceInstanceId == null || serviceInstanceId.isEmpty()) {
            throw new AuditException("Invalid request URL, missing parameter: " + SERVICE_INSTANCE_ID,
                    Status.BAD_REQUEST);
        }
    }

    public static String validateHeader(HttpHeaders headers, String sdncCtxBuilderBasicAuthorization)
            throws AuditException {

        /*
         * Validate that the headers are there and return the FROM_APP_ID
         */

        String fromAppId = headers.getRequestHeaders().getFirst(FROM_APP_ID);
        if ((fromAppId == null) || fromAppId.trim().isEmpty()) {
            throw new AuditException("Missing header parameter: " + FROM_APP_ID, Status.BAD_REQUEST);
        }

        String headerAuthorization = headers.getRequestHeaders().getFirst(AUTHORIZATION);
        if ((headerAuthorization == null) || headerAuthorization.trim().isEmpty()) {
            throw new AuditException("Missing header parameter: " + AUTHORIZATION, Status.BAD_REQUEST);
        }
        if ((!headerAuthorization.contentEquals(sdncCtxBuilderBasicAuthorization))) {
            throw new AuditException("Failed Basic " + AUTHORIZATION, Status.UNAUTHORIZED);
        }

        return fromAppId;
    }

    /*
     * The purpose is to keep same transaction Id from north bound interface to
     * south bound interface
     */
    public static String extractTranactionIdHeader(HttpHeaders headers) {
        String transactionId = null;
        transactionId = headers.getRequestHeaders().getFirst(TRANSACTION_ID);
        if ((transactionId == null) || transactionId.trim().isEmpty()) {
            transactionId = UUID.randomUUID().toString();
            log.info("Header {} not present in request, generating new value: {}", TRANSACTION_ID, transactionId);
        }
        return transactionId;
    }

    /**
     * For each AAI VnfInstance, use the AAI VfModuleId to make a rest call to SDNC
     * VNF-API to create a list of all SDNC Vnfs. The URL for VNF-API is
     * https://<SDN-C_HOST_NAME>:8543/restconf/config/VNF-API:vnfs/vnf-list/<vnf-id>
     * 
     * @param sdncClient
     * @param sdncBaseUrl
     * @param authorization
     * @param sdncGenericResourcePath
     * @param serviceInstaceId
     * @return
     * @throws AuditException
     */
    public static String getSdncGenericResource(Client sdncClient, String sdncBaseUrl, String authorization,
            String sdncGenericResourcePath, String serviceInstaceId) throws AuditException {
        String genericResourceSdncUrl = sdncBaseUrl
                + generateSdncInstanceUrl(sdncGenericResourcePath, serviceInstaceId);
        // send rest request to SDNC GENERIC-RESOURCE-API
        return getSdncResource(sdncClient, genericResourceSdncUrl, authorization);
    }

    /**
     * For each AAI VnfInstance, use the AAI Vf_module_id to make a rest call to
     * SDNC VNF-API to create a list of all SDNC Vnfs. The URL for VNF-API is
     * https://<SDN-C_HOST_NAME>:8543/restconf/config/VNF-API:vnfs/vnf-list/<vnf-id>
     * 
     * @param sdncClient
     * @param sdncBaseUrl
     * @param sdncVnfInstancePath
     * @param transactionId
     * @param genericVNFPayload
     * @param vnfInstance
     * @return
     * @throws AuditException
     */
    public static Map<String, List<Vnf>> getSdncVnfList(Client sdncClient, String sdncBaseUrl,
            String sdncVnfInstancePath, String authorization, List<VnfInstance> vnfList) throws AuditException {

        // define map [key: vnf-id, value: list of SDNC vnfs, which in fact are
        // vf_modules]
        Map<String, List<Vnf>> sdncVnfMap = new HashMap<>();
        for (VnfInstance vnfInstance : vnfList) {
            if (vnfInstance.getVfModules() != null) {
                List<Vnf> sdncVnfList = new ArrayList<>();
                List<VfModule> vfModuleList = vnfInstance.getVfModules().getVfModule();
                if (vfModuleList != null && !vfModuleList.isEmpty()) {
                    for (VfModule vfModuleInstance : vfModuleList) {
                        // create SDNC VNF-API url using AAI VnfInstance VfModule Vf_module_id
                        String vnfSdncUrl = sdncBaseUrl
                                + generateSdncInstanceUrl(sdncVnfInstancePath, vfModuleInstance.getVfModuleId());
                        // send rest request to SDNC VNF-API
                        String sndcVnfPayload = getSdncResource(sdncClient, vnfSdncUrl, authorization);
                        if (isEmptyJson(sndcVnfPayload)) {
                            log.info("VNF with vf-module-id is not found from SDNC");
                        } else {
                            List<Vnf> vnfsList = extractVnfList(sndcVnfPayload);
                            sdncVnfList.addAll(vnfsList);
                        }
                    }
                }
                sdncVnfMap.put(vnfInstance.getVnfId(), sdncVnfList);
            }
        }
        return sdncVnfMap;
    }

    public static ModelContext transformGenericResource(String sdncResponse) {
        List<Object> jsonSpec = JsonUtils.filepathToList(GENERIC_API_SPEC_PATH);
        Object jsonInput = JsonUtils.jsonToObject(sdncResponse);
        Chainr chainr = Chainr.fromSpec(jsonSpec);
        Object transObject = chainr.transform(jsonInput);
        if (null == transObject) {
            return new ModelContext();
        }
        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.toPrettyJsonString(transObject), ModelContext.class);

    }

    public static List<PNF> getPnfFromSdncResonse(Client sdncClient, String sdncBaseUrl, String authorization,
            String sdncPortMirrorResourcePath, String sdncResponse) throws AuditException {
        List<PNF> pnfList = new ArrayList<>();
        List<Object> providedConfigurationsSpec = JsonUtils.filepathToList(PROVIDED_CONFIGURATIONS_SPEC_PATH);
        Object providedConfigurationsInput = JsonUtils.jsonToObject(sdncResponse);
        Chainr providedConfigurations = Chainr.fromSpec(providedConfigurationsSpec);
        Object providedConfigurationsObject = providedConfigurations.transform(providedConfigurationsInput);
        if (null == providedConfigurationsObject) {
            return pnfList;
        }
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(providedConfigurationsObject);
        JsonObject jsonObject = (JsonObject) jsonElement;
        JsonArray jsonArray = jsonObject.getAsJsonArray("configuration-id");
        for (JsonElement configurationId : jsonArray) {
            String pnfId = configurationId.getAsString();
            String portMirrorResponse = getSdncGenericResource(sdncClient, sdncBaseUrl, authorization,
                    sdncPortMirrorResourcePath, pnfId);
            List<Object> portMirrorSpec = JsonUtils.filepathToList(PORT_MIRROR_CONFIGURATIONS_SPEC_PATH);
            Object portMirrorInput = JsonUtils.jsonToObject(portMirrorResponse);
            Chainr portMirror = Chainr.fromSpec(portMirrorSpec);
            Object portMirrorObject = portMirror.transform(portMirrorInput);
            for (PNF pnf : gson.fromJson(JsonUtils.toPrettyJsonString(portMirrorObject), ModelContext.class)
                    .getPnfs()) {
                if (null != pnf) {
                    pnfList.add(pnf);
                }
            }
        }
        return pnfList;
    }

    /**
     * Transform the AAI and SDNC models to the audit common model
     * 
     * @param aaiVnfLst
     * @param sdncVnfMap
     * @return
     * @throws AuditException
     */
    public static ModelContext transformVnfList(List<VnfInstance> aaiVnfLst, Map<String, List<Vnf>> sdncVnfMap) {
        Service service = new Service();
        List<VNF> vnfList = new ArrayList<>();

        // Initialize common model members to null
        service.setModelInvariantUUID("null");
        service.setUuid("null");
        service.setName("null");

        for (VnfInstance aaiVnfInstance : aaiVnfLst) {
            VNF vnf = new VNF();
            // Initialize common model members to null
            vnf.setName("null");
            vnf.setType("null");
            vnf.setModelInvariantUUID("null");
            vnf.setUuid("null");
            List<Vnf> sdncVnfList = sdncVnfMap.get(aaiVnfInstance.getVnfId());
            try {
                // Set the common model VNF name and type from the SDNC topology info
                if (sdncVnfList != null && !sdncVnfList.isEmpty()) {
                    for (Vnf sdncVnf : sdncVnfList) {
                        if (null == sdncVnf.getServiceData()) {
                            break;
                        }
                        if (null == sdncVnf.getServiceData().getVnfTopologyInformation()) {
                            break;
                        }
                        VnfTopologyIdentifier vnfTopologyId = sdncVnf.getServiceData().getVnfTopologyInformation()
                                .getVnfTopologyIdentifier();
                        if (null != vnfTopologyId) {
                            if (vnf.getName().contentEquals("null")) {
                                vnf.setName(vnfTopologyId.getGenericVnfName());
                            }
                            if (vnf.getType().contentEquals("null")) {
                                vnf.setType(vnfTopologyId.getGenericVnfType());
                            }
                            if (vnf.getAttributes().isEmpty()) {
                                if ((null != vnfTopologyId.getInMaint()) && !(vnfTopologyId.getInMaint().isEmpty())) {
                                    Attribute lockedBoolean = new Attribute();
                                    lockedBoolean.setName(Name.lockedBoolean);
                                    if (vnfTopologyId.getInMaint().equalsIgnoreCase("yes")) {
                                        lockedBoolean.setValue("true");
                                    }
                                    if (vnfTopologyId.getInMaint().equalsIgnoreCase("no")) {
                                        lockedBoolean.setValue("false");
                                    }
                                    vnf.addAttribute(lockedBoolean);
                                }
                                if ((null != vnfTopologyId.getProvStatus())
                                        && !(vnfTopologyId.getProvStatus().isEmpty())) {
                                    Attribute provStatus = new Attribute();
                                    provStatus.setName(Name.provStatus);
                                    provStatus.setValue(vnfTopologyId.getProvStatus());
                                    vnf.addAttribute(provStatus);
                                }
                                if (null != vnfTopologyId.getPserver()) {
                                    if ((null != vnfTopologyId.getPserver().getHostname())
                                            && !(vnfTopologyId.getPserver().getHostname().isEmpty())) {
                                        Attribute hostname = new Attribute();
                                        hostname.setName(Name.hostName);
                                        hostname.setValue(vnfTopologyId.getPserver().getHostname());
                                        vnf.addAttribute(hostname);

                                    }
                                }
                                if (null != vnfTopologyId.getImage()) {
                                    if ((null != vnfTopologyId.getImage().getImageName())
                                            && !(vnfTopologyId.getImage().getImageName().isEmpty())) {
                                        Attribute imageName = new Attribute();
                                        imageName.setName(Name.imageId);
                                        imageName.setValue(vnfTopologyId.getImage().getImageName());
                                        vnf.addAttribute(imageName);

                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            // Get the common model list of VFModule from the SDNC VNF List
            List<VFModule> vfmoduleLst = getVfModuleList(aaiVnfInstance, sdncVnfList);
            vnf.setVfModules(vfmoduleLst);

            // Get the common model list of VNFC from the SDNC Vnf
            List<VNFC> vnfcList = getVnfcList(sdncVnfList);
            vnf.setVnfcs(vnfcList);
            vnfList.add(vnf);
        }

        ModelContext context = new ModelContext();
        context.setService(service);
        context.setVnfs(vnfList);
        return context;
    }

    public static String getSdncResource(Client sdncClient, String url, String authorization) throws AuditException {

        log.info("SDNC GET request at url = {}", url);
        Response response = sdncClient.target(url).request().header("Accept", "application/json")
                .header(AUTHORIZATION, authorization).get();

        if (response.getStatus() == 200) {
            return response.readEntity(String.class);
        } else if (response.getStatus() == 404) {
            // Resource not found, generate empty JSON format
            log.info("Return empty Json. Resource not found: {}", url);
            return new JSONObject().toString();

        } else {
            throw new AuditException(AuditError.INTERNAL_SERVER_ERROR + " with " + response.getStatus());
        }
    }

    private static String generateSdncInstanceUrl(String siPath, String vfModuleLink) {
        return MessageFormat.format(siPath, vfModuleLink);
    }

    /**
     * Get Service Entity from AAI api
     * 
     * @param aaiClient
     * @param aaiBaseUrl
     * @param aaiBasicAuthorization
     * @param aaiPathToServiceInstanceQuery
     * @param serviceInstanceId
     * @param transactionId
     * @return ServiceEntity
     * @throws AuditException
     */
    public static ServiceEntity getServiceEntity(RestClient aaiClient, String aaiBaseUrl, String aaiBasicAuthorization,
            String aaiPathToServiceInstanceQuery, String serviceInstanceId, String transactionId)
            throws AuditException {

        String getResourceLinkUrl = generateAaiUrl(aaiBaseUrl, aaiPathToServiceInstanceQuery, serviceInstanceId);
        String aaiResourceData = getAaiResource(aaiClient, getResourceLinkUrl, aaiBasicAuthorization, transactionId);

        // Handle the case if the service instance is not found in AAI
        if (isEmptyJson(aaiResourceData)) {
            log.info("Service Instance {} is not found from AAI", serviceInstanceId);
            // Only return the empty Json on the root level. i.e service instance
            return null;
        }

        ServiceEntity serviceEntityObj = createServiceEntityObj(aaiResourceData);
        if (serviceEntityObj != null) {
            serviceEntityObj.setTransactionId(transactionId);
            serviceEntityObj.setServiceInstanceId(serviceInstanceId);
        }

        return serviceEntityObj;
    }

    private static boolean isEmptyJson(String serviceInstancePayload) {
        return (serviceInstancePayload.equals(EMPTY_JSON_STRING));
    }

    public static String generateAaiUrl(String aaiBaseUrl, String aaiPathToServiceInstanceQuery, String parameter) {
        return aaiBaseUrl + MessageFormat.format(aaiPathToServiceInstanceQuery, parameter != null ? parameter : "");
    }

    private static Map<String, List<String>> buildHeaders(String aaiBasicAuthorization, String transactionId) {
        MultivaluedMap<String, String> headers = new MultivaluedMapImpl();
        headers.put(TRANSACTION_ID, Collections.singletonList(transactionId));
        headers.put(FROM_APP_ID, Collections.singletonList(APP_NAME));
        headers.put(AUTHORIZATION, Collections.singletonList(aaiBasicAuthorization));
        return headers;
    }

    public static String getAaiResource(RestClient client, String url, String aaiBasicAuthorization,
            String transactionId) throws AuditException {
        OperationResult result = client.get(url, buildHeaders(aaiBasicAuthorization, transactionId),
                MediaType.valueOf(MediaType.APPLICATION_JSON));

        if (result.getResultCode() == 200) {
            return result.getResult();
        } else if (result.getResultCode() == 404) {
            // Resource not found, generate empty JSON format
            log.info("Resource for {} is not found: {}", url, "return empty Json format");
            return new JSONObject().toString();

        } else {
            throw new AuditException(AuditError.INTERNAL_SERVER_ERROR + " with " + result.getFailureCause());
        }
    }

    private static ServiceEntity createServiceEntityObj(String aaiServiceInstance) throws AuditException {
        if ((aaiServiceInstance == null) || aaiServiceInstance.isEmpty()) {
            return null;
        }

        ServiceEntity serviceEntity = new ServiceEntity();

        try {
            JSONObject obj = new JSONObject(aaiServiceInstance);
            if (obj.has(JSON_SERVICE_TYPE)) {
                serviceEntity.setServiceType(obj.getString(JSON_SERVICE_TYPE));
            }
        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new AuditException("Json Reader Parse Error " + e.getMessage());
        }
        return serviceEntity;
    }

    /*
     * Extract the vnf-list from the Json payload.
     */
    private static List<Vnf> extractVnfList(String payload) throws AuditException {
        VnfList vnfList = VnfList.fromJson(payload);
        if (null != vnfList) {
            return vnfList.getVnfList();
        }
        return new ArrayList<>();

    }

    /**
     * Get the common model list of AAI exist in SDNC per each (AAI GenericVNF >
     * vf-modules > vf-module > model-version-id )
     * 
     * @param aaiVnf
     * @param sdncVnfList
     * @return
     * @throws AuditException
     */
    private static List<VFModule> getVfModuleList(VnfInstance aaiVnf, List<Vnf> sdncVnfList) {
        List<VFModule> vfmoduleLst = new ArrayList<>();
        if (aaiVnf.getVfModules() != null && aaiVnf.getVfModules().getVfModule() != null) {
            ConcurrentMap<String, AtomicInteger> vnfModulemap = buildMaxInstanceMap(
                    aaiVnf.getVfModules().getVfModule());
            for (Map.Entry<String, AtomicInteger> entry : vnfModulemap.entrySet()) {
                String modelVersionId = entry.getKey();
                for (Vnf sdncVnf : sdncVnfList) {
                    if (sdncVnf.getVnfId().equals(modelVersionId)) {
                        VFModule vfModule = new VFModule();
                        vfModule.setUuid(modelVersionId);
                        vfModule.setMaxInstances(entry.getValue().intValue());
                        VnfAssignments vnfAssignments = sdncVnf.getServiceData().getVnfTopologyInformation()
                                .getVnfAssignments();
                        if (null != vnfAssignments) {
                            List<Network> networks = new ArrayList<>();
                            for (VnfNetwork vnfNetwork : vnfAssignments.getVnfNetworks()) {
                                Network network = new Network();
                                network.setName(vnfNetwork.getNetworkName());
                                network.setUuid(vnfNetwork.getNetworkId());
                                if (null != vnfNetwork.getNetworkRole()) {
                                    Attribute networkRole = new Attribute();
                                    networkRole.setName(Name.networkRole);
                                    networkRole.setValue(vnfNetwork.getNetworkRole());
                                }
                                networks.add(network);
                            }
                            vfModule.setNetworks(networks);
                        }
                        vfmoduleLst.add(vfModule);
                    }
                }
            }
        }
        log.debug("The size of vfmoduleLst: {}", vfmoduleLst.size());
        return vfmoduleLst;
    }

    /*
     * Build the map with key (model_version_id) and with the max occurrences of the
     * value in the map
     * 
     * @param vf_module_List
     * 
     * @return
     */
    private static ConcurrentMap<String, AtomicInteger> buildMaxInstanceMap(List<VfModule> vfModuleList) {

        ConcurrentMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

        for (VfModule vfModule : vfModuleList) {
            String vfModuleId = vfModule.getVfModuleId();
            map.putIfAbsent(vfModuleId, new AtomicInteger(0));
            map.get(vfModuleId).incrementAndGet();
        }
        return map;
    }

    /*
     * Get the common model list of VNFC from the SDNC Vnfs
     * 
     * @param sdncVnfMap
     * 
     * @return
     */
    private static List<VNFC> getVnfcList(List<Vnf> sdncVnfList) {
        List<VNFC> vnfcList = new ArrayList<>();
        if (sdncVnfList != null && !sdncVnfList.isEmpty()) {
            for (Vnf sdncVnf : sdncVnfList) {
                try {
                    List<VnfVm> sdncVnfVmLst = sdncVnf.getServiceData().getVnfTopologyInformation().getVnfAssignments()
                            .getVnfVms();
                    if (sdncVnfVmLst != null && !sdncVnfVmLst.isEmpty()) {
                        for (VnfVm sdncVnfVm : sdncVnfVmLst) {
                            List<VmName> sdncVmNameLst = sdncVnfVm.getVmNames();
                            if (sdncVmNameLst != null && !sdncVmNameLst.isEmpty()) {
                                for (VmName sdncVmName : sdncVmNameLst) {
                                    VNFC vnfc = new VNFC();
                                    // Initialize common model members to null
                                    vnfc.setModelInvariantUUID("null");
                                    vnfc.setUuid("null");
                                    vnfc.setName(sdncVmName.getVmName() == null ? "null" : sdncVmName.getVmName());
                                    vnfcList.add(vnfc);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        }
        log.debug("The size of vnfcList: {}", vnfcList.size());
        return vnfcList;
    }

}
