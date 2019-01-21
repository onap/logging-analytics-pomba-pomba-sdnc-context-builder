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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.onap.aai.restclient.client.OperationResult;
import org.onap.aai.restclient.client.RestClient;
import org.onap.pomba.common.datatypes.Attribute;
import org.onap.pomba.common.datatypes.Attribute.Name;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.common.datatypes.Network;
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
    private static final String JSON_RESOURCE_TYPE = "resource-type";
    private static final String JSON_RESOURCE_LINK = "resource-link";
    private static final String JSON_GLOBAL_CUSTOMER_ID = "global-customer-id";
    private static final String JSON_SUBSCRIBER_TYPE = "subscriber-type";
    private static final String JSON_SUBSCRIBER_NAME = "subscriber-name";
    private static final String RESULT_DATA = "result-data";
    private static final String CATALOG_SERVICE_INSTANCE = "service-instance";
    private static final String CUSTOMER_ID_STRING = "/customers/customer/";
    private static final String SERVICE_TYPE_STRING = "/service-subscriptions/service-subscription/";

    private static final String FORWARD_SLASH = "/";
    // SDNC vnf Json Path
    private static final String SPEC_PATH = "config/vnflist.spec";

    // Parameters for Query SDNC Model Data REST API  URL
    private static final String SERVICE_INSTANCE_ID = "serviceInstanceId";

    private RestUtil() {
    }

    /**
     * Validates the URL parameter.
     * @throws AuditException if there is missing parameter
     */
    public static void validateURL(String serviceInstanceId) throws AuditException {

        if (serviceInstanceId == null || serviceInstanceId.isEmpty())
            throw new AuditException("Invalid request URL, missing parameter: "+ SERVICE_INSTANCE_ID, Status.BAD_REQUEST);
    }


    public static String validateHeader(HttpHeaders headers, String sdncCtxBuilderBasicAuthorization) throws AuditException {

        /*
         * Validate that the headers are there and return the FROM_APP_ID
         */

        String fromAppId = headers.getRequestHeaders().getFirst(FROM_APP_ID);
        if((fromAppId == null) || fromAppId.trim().isEmpty()) {
            throw new AuditException("Missing header parameter: "+ FROM_APP_ID, Status.BAD_REQUEST);
        }

        String headerAuthorization = headers.getRequestHeaders().getFirst(AUTHORIZATION);
        if((headerAuthorization == null) || headerAuthorization.trim().isEmpty()) {
            throw new AuditException("Missing header parameter: "+ AUTHORIZATION, Status.BAD_REQUEST);
        }
        if((!headerAuthorization.contentEquals(sdncCtxBuilderBasicAuthorization))) {
            throw new AuditException("Failed Basic "+ AUTHORIZATION, Status.UNAUTHORIZED);
        }

        return fromAppId;
    }


    /*
     * The purpose is to keep same transaction Id from north bound interface to south bound interface
     */
    public static String extractTranactionIdHeader(HttpHeaders headers)  {
        String transactionId = null;
        transactionId = headers.getRequestHeaders().getFirst(TRANSACTION_ID);
        if((transactionId == null) || transactionId.trim().isEmpty()) {
            transactionId = UUID.randomUUID().toString();
            log.info("Header ", TRANSACTION_ID, " not present in request, generating new value: ", transactionId);
        }
        return transactionId;
    }


    /**
     * For each AAI VnfInstance, use the AAI VfModuleId to make a rest call to SDNC VNF-API to create a list of all SDNC Vnfs.
     * The URL for VNF-API is https://<SDN-C_HOST_NAME>:8543/restconf/config/VNF-API:vnfs/vnf-list/<vnf-id>
     * @param sdncClient
     * @param sdncBaseUrl
     * @param authorization
     * @param sdncGenericResourcePath
     * @param serviceInstaceId
     * @return
     * @throws AuditException
     */
    public static String getSdncGenericResource(Client sdncClient, String sdncBaseUrl, String authorization, String sdncGenericResourcePath,
                                                        String serviceInstaceId) throws AuditException {
        String genericResourceSdncURL = sdncBaseUrl+generateSdncInstanceURL(sdncGenericResourcePath, serviceInstaceId);
        // send rest request to SDNC GENERIC-RESOURCE-API
        return getSdncResource(sdncClient, genericResourceSdncURL, authorization);
    }

    /**
     * For each AAI VnfInstance, use the AAI Vf_module_id to make a rest call to SDNC VNF-API to create a list of all SDNC Vnfs.
     * The URL for VNF-API is https://<SDN-C_HOST_NAME>:8543/restconf/config/VNF-API:vnfs/vnf-list/<vnf-id>
     * @param sdncClient
     * @param sdncBaseUrl
     * @param sdncVnfInstancePath
     * @param transactionId
     * @param genericVNFPayload
     * @param vnfInstance
     * @return
     * @throws AuditException
     */
    public static Map<String,List<Vnf>> getSdncVnfList(Client sdncClient, String sdncBaseUrl, String sdncVnfInstancePath,
                                                        String authorization, List<VnfInstance> vnfList) throws AuditException {

        // define map [key: vnf-id, value: list of SDNC vnfs, which in fact are vf_modules]
        Map<String,List<Vnf>> sdncVnfMap = new HashMap<>();
        for (VnfInstance vnfInstance: vnfList) {
            if (vnfInstance.getVfModules() != null) {
                List<Vnf> sdncVnfList = new ArrayList<>();
                List<VfModule> vfModuleList = vnfInstance.getVfModules().getVfModule();
                if (vfModuleList != null && !vfModuleList.isEmpty()) {
                    for (VfModule vfModuleInstance : vfModuleList) {
                        // create SDNC VNF-API url using AAI VnfInstance VfModule Vf_module_id
                        String vnfSdncURL = sdncBaseUrl+generateSdncInstanceURL(sdncVnfInstancePath, vfModuleInstance.getVfModuleId());
                        // send rest request to SDNC VNF-API
                        String sndcVNFPayload = getSdncResource(sdncClient, vnfSdncURL, authorization);
                        if (isEmptyJson(sndcVNFPayload)) {
                            log.info("VNF with vf-module-id is not found from SDNC");
                        } else {
                            List<Vnf> vnfsList = extractVnfList(sndcVNFPayload);
                            sdncVnfList.addAll(vnfsList);
                        }
                    }
                }
                sdncVnfMap.put(vnfInstance.getVnfId(),sdncVnfList);
            }
        }
        return sdncVnfMap;
    }


    public static ModelContext transformGenericResource(String sdncResponse, String SPEC_PATH) {
        List<Object> jsonSpec = JsonUtils.filepathToList(SPEC_PATH);
        Object jsonInput = JsonUtils.jsonToObject(sdncResponse);
        Chainr chainr = Chainr.fromSpec(jsonSpec);
        Object transObject = chainr.transform(jsonInput);
        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.toPrettyJsonString(transObject), ModelContext.class);

    }

    /**
     * Transform the AAI and SDNC models to the audit common model
     * @param aaiVnfLst
     * @param sdncVnfMap
     * @return
     * @throws AuditException
     */
    public static ModelContext transformVnfList(List<VnfInstance> aaiVnfLst, Map<String,List<Vnf>> sdncVnfMap) {
        ModelContext context = new ModelContext();
        Service service = new Service();
        List<VNF> vnfList = new ArrayList<>();

        // Initialize common model members to null
        service.setModelInvariantUUID("null");
        service.setUuid("null");
        service.setName("null");

        for(VnfInstance aaiVnfInstance : aaiVnfLst)  {
            VNF  vnf = new VNF();
            // Initialize common model members to null
            vnf.setName("null");
            vnf.setType("null");
            vnf.setModelInvariantUUID("null");
            vnf.setUuid("null");
            List<Vnf> sdncVnfList = sdncVnfMap.get(aaiVnfInstance.getVnfId());
            try {
                // Set the common model VNF name and type from the SDNC topology info
                if (sdncVnfList != null && !sdncVnfList.isEmpty()) {
                    for(Vnf sdncVnf : sdncVnfList) {
                        if (null == sdncVnf.getServiceData()) {
                            break;
                        }
                        if (null == sdncVnf.getServiceData().getVnfTopologyInformation()) {
                            break;
                        }
                        VnfTopologyIdentifier vnfTopologyId = sdncVnf.getServiceData().getVnfTopologyInformation().getVnfTopologyIdentifier();
                        if (null != vnfTopologyId) {
                            if (vnf.getName().contentEquals("null")) {
                                vnf.setName(vnfTopologyId.getGenericVnfName());
                            }
                            if (vnf.getType().contentEquals("null")) {
                                vnf.setType(vnfTopologyId.getGenericVnfType());
                            }
                            if (vnf.getAttributes().isEmpty()) {
                                if ((null != vnfTopologyId.getInMaint()) &&  !(vnfTopologyId.getInMaint().isEmpty())) {
                                    Attribute  lockedBoolean = new Attribute();
                                    lockedBoolean.setName(Name.lockedBoolean);
                                    if (vnfTopologyId.getInMaint().equalsIgnoreCase("yes")) {
                                        lockedBoolean.setValue("true");
                                    }
                                    if (vnfTopologyId.getInMaint().equalsIgnoreCase("no")) {
                                        lockedBoolean.setValue("false");
                                    }
                                    vnf.addAttribute(lockedBoolean);
                                }
                                if ((null != vnfTopologyId.getProvStatus()) &&  !(vnfTopologyId.getProvStatus().isEmpty())) {
                                    Attribute provStatus = new Attribute();
                                    provStatus.setName(Name.provStatus);
                                    provStatus.setValue(vnfTopologyId.getProvStatus());
                                    vnf.addAttribute(provStatus);
                                }
                                if (null != vnfTopologyId.getPserver()) {
                                    if ((null != vnfTopologyId.getPserver().getHostname()) && !(vnfTopologyId.getPserver().getHostname().isEmpty())) {
                                        Attribute  hostname = new Attribute();
                                        hostname.setName(Name.hostName);
                                        hostname.setValue(vnfTopologyId.getPserver().getHostname());
                                        vnf.addAttribute(hostname);

                                    }
                                }
                                if (null != vnfTopologyId.getImage()) {
                                    if ((null != vnfTopologyId.getImage().getImageName()) && !(vnfTopologyId.getImage().getImageName().isEmpty())) {
                                        Attribute  imageName = new Attribute();
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

        context.setService(service);
        context.setVnfs(vnfList);
        return context;
    }

    public static String getSdncResource(Client sdncClient, String url, String authorization) throws AuditException  {

        log.info("SDNC GET request at url = " + url);
        Response response = sdncClient.target(url).request()
                .header("Accept", "application/json")
                .header(AUTHORIZATION, authorization).get();

        if (response.getStatus()  == 200) {
            return response.readEntity(String.class);
        } else if (response.getStatus() == 404) {
            //Resource not found, generate empty JSON format
            log.info("Return empty Json. Resource not found: ", url);
            return new JSONObject().toString();

        } else {
            throw new AuditException(AuditError.INTERNAL_SERVER_ERROR + " with " + response.getStatus());
        }
    }


    private static String generateSdncInstanceURL(String siPath, String vfModuleLink) {
        return MessageFormat.format(siPath, vfModuleLink);
    }

    /**
     * Get customer info from multiple AAI api
     * @param aaiClient
     * @param aaiBaseUrl
     * @param aaiBasicAuthorization
     * @param aaiPathToSearchNodeQuery
     * @param aaiPathToCustomerQuery
     * @param serviceInstaceId
     * @param transactionId
     * @return
     * @throws AuditException
     */
    public static ServiceEntity getServiceEntity(RestClient aaiClient,
                                                String aaiBaseUrl,
                                                String aaiBasicAuthorization,
                                                String aaiPathToSearchNodeQuery,
                                                String aaiPathToCustomerQuery,
                                                String serviceInstanceId,
                                                String transactionId) throws AuditException {

        String getResourceLinkUrl = generateAaiUrl(aaiBaseUrl, aaiPathToSearchNodeQuery, serviceInstanceId);
        String aaiResourceData  = getAaiResource(aaiClient, getResourceLinkUrl, aaiBasicAuthorization, transactionId);

        // Handle the case if the service instance is not found in AAI
        if (isEmptyJson(aaiResourceData)) {
            log.info(" Service Instance {} is not found from AAI", serviceInstanceId);
            // Only return the empty Json on the root level. i.e service instance
            return null;
        }

        String resourceLink = extractResourceLink(aaiResourceData, CATALOG_SERVICE_INSTANCE);

        ServiceEntity serviceEntityObj =  createServiceEntityObj (resourceLink); //customerId and serviceType are updated here.
        if (serviceEntityObj != null) {
            serviceEntityObj.setTransactionId(transactionId);
            serviceEntityObj.setServiceInstanceId(serviceInstanceId);
            String customerId = serviceEntityObj.getCustomerId();
            // Obtain customerType and customerName
            String getCustomerUrl = generateAaiUrl (aaiBaseUrl, aaiPathToCustomerQuery, customerId);
            String aaiCustomerData  = getAaiResource(aaiClient, getCustomerUrl, aaiBasicAuthorization, transactionId);
            if (isEmptyJson(aaiCustomerData)) {
                log.info(" Customer name {} is not found from AAI", customerId);
                // Only return the empty Json on the root level. i.e service instance
                throw new AuditException(AuditError.INTERNAL_SERVER_ERROR + ": Customer ID cannot be found from AAI :" + customerId);
            }
            // Update customerType and customerName to the existing serviceEntityObj
            updateServiceEntityObj ( aaiCustomerData , serviceEntityObj);
        }

        return serviceEntityObj;
    }

    private static boolean isEmptyJson(String serviceInstancePayload) {
        return (serviceInstancePayload.equals(EMPTY_JSON_STRING));
    }

    public static String generateAaiUrl (String aaiBaseURL, String aaiPathToCustomerQuery, String parameter) {
        return aaiBaseURL + aaiPathToCustomerQuery + (parameter != null ? parameter : "");
    }

    private static Map<String, List<String>> buildHeaders(String aaiBasicAuthorization, String transactionId) {
        MultivaluedMap<String, String> headers = new MultivaluedMapImpl();
        headers.put(TRANSACTION_ID, Collections.singletonList(transactionId));
        headers.put(FROM_APP_ID, Collections.singletonList(APP_NAME));
        headers.put(AUTHORIZATION, Collections.singletonList(aaiBasicAuthorization));
        return headers;
    }

    public static String getAaiResource(RestClient client, String url, String aaiBasicAuthorization, String transactionId)
            throws AuditException {
        OperationResult result = client.get(url, buildHeaders(aaiBasicAuthorization, transactionId), MediaType.valueOf(MediaType.APPLICATION_JSON));

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


    /*
    * Extract the resource-Link from Json payload. For example
    * {
    *     "result-data": [
    *         {
    *             "resource-type": "service-instance",
    *             "resource-link": "/aai/v11/business/customers/customer/DemoCust_651800ed-2a3c-45f5-b920-85c1ed155fc2/service-subscriptions/service-subscription/vFW/service-instances/service-instance/adc3cc2a-c73e-414f-8ddb-367de81300cb"
    *         }
    *     ]
    * }
    */
    private static String extractResourceLink(String payload, String catalog) throws AuditException {
        String resourceLink = null;
        log.info("Fetching the resource-link based on resource-type=" + catalog);

        try {
            JSONArray resultDataList = new JSONObject(payload).getJSONArray(RESULT_DATA);
            if (resultDataList != null) {
                for (int i = 0; i < resultDataList.length(); i++) {
                    JSONObject obj = resultDataList.optJSONObject(i);
                    if (obj.has(JSON_RESOURCE_TYPE) && (obj.getString(JSON_RESOURCE_TYPE).equals(catalog) ))  {
                        resourceLink = obj.getString(JSON_RESOURCE_LINK);
                        log.info(resourceLink);
                        return resourceLink;
                    }
                }
            }
        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new AuditException(AuditError.JSON_PARSE_ERROR + e.getMessage());
        }

        log.warn("resource-link CANNOT be found: ", payload );

        return resourceLink;
    }

    /*
     * Extract the "subscriber-name" and "subscriber-type" from Json payload. For example
     *         {
     *             "global-customer-id": "OttoonMorph36",
     *             "subscriber-name": "OttoonMorph36",
     *             "subscriber-type": "CUST",
     *             "resource-version": "1526324315029"
     *         }
     */
    private static void updateServiceEntityObj(String payload,  ServiceEntity serviceEntityObj) throws AuditException {
        if (serviceEntityObj == null ) {
            throw new AuditException("null pointer  serviceEntityObj");
        }
        String customerId = serviceEntityObj.getCustomerId();
        log.info("Fetching the subscriber type based on customer-id = " + customerId);

        try {
            JSONObject obj = new JSONObject (payload);
            if (obj.has(JSON_GLOBAL_CUSTOMER_ID) && (obj.getString(JSON_GLOBAL_CUSTOMER_ID).equals(customerId) ))  {
                serviceEntityObj.setCustomerType(obj.getString(JSON_SUBSCRIBER_TYPE));
                serviceEntityObj.setCustomerName(obj.getString(JSON_SUBSCRIBER_NAME));
                return;
            }
        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new AuditException("Json Reader Parse Error " + e.getMessage());
        }

        log.warn("subscriberType (" + customerId + ") CANNOT be found: ", payload );

        return;
    }

    /* Look for "/customers/customer/" and "/service-subscriptions/service-subscription/" in resourceLink
     * and find the customer id and service type.
     * For example,
     *    "/aai/v11/business/customers/customer/DemoCust_651800ed-2a3c-45f5-b920-85c1ed155fc2/service-subscriptions/service-subscription/vFW/service-instances/service-instance/adc3cc2a-c73e-414f-8ddb-367de81300cb"
     *    customerId = DemoCust_651800ed-2a3c-45f5-b920-85c1ed155fc2
     *    serviceType = vFW
     * */
    private static ServiceEntity createServiceEntityObj (String resourceLink) {
        if ((null == resourceLink) || resourceLink.isEmpty()) {
            return null;
        }
        int customerIdIdx = resourceLink.indexOf(CUSTOMER_ID_STRING);
        int serviceTypeIdx = resourceLink.indexOf(SERVICE_TYPE_STRING);

        if (( customerIdIdx < 0 ) || ( serviceTypeIdx < 0 )) {
           return null;
        }

        ServiceEntity serviceEntity = new ServiceEntity ();
        serviceEntity.setCustomerId(abstractStrInfo(resourceLink, CUSTOMER_ID_STRING));
        serviceEntity.setServiceType(abstractStrInfo(resourceLink, SERVICE_TYPE_STRING));
        return serviceEntity;
    }


    /*
     * Extract the vnf-list from the Json payload.
     */
    private static List<Vnf> extractVnfList(String payload) throws AuditException  {
        List<Object> jsonSpec = JsonUtils.filepathToList(SPEC_PATH);
        Object jsonInput = JsonUtils.jsonToObject(payload);
        Chainr chainr = Chainr.fromSpec(jsonSpec);
        Object transObject = chainr.transform(jsonInput);
        String vnfListString = JsonUtils.toPrettyJsonString(transObject);
        VnfList vnfList = VnfList.fromJson(vnfListString);
        return vnfList.getVnfList();
    }

    private static String abstractStrInfo (String origStr, String matchStr) {
        String after = origStr.substring( origStr.indexOf(matchStr) + matchStr.length());

        return after.substring(0, after.indexOf(FORWARD_SLASH));
    }

    /**
     * Get the common model list of AAI exist in SDNC per each  (AAI GenericVNF > vf-modules > vf-module > model-version-id )
     * @param aaiVnf
     * @param sdncVnfList
     * @return
     * @throws AuditException
     */
    private static List<VFModule> getVfModuleList(VnfInstance aaiVnf, List<Vnf> sdncVnfList) {
        List<VFModule> vfmoduleLst = new ArrayList<>();
        if (aaiVnf.getVfModules() != null && aaiVnf.getVfModules().getVfModule() != null ) {
            ConcurrentMap<String, AtomicInteger> vnfModulemap = buildMaxInstanceMap(aaiVnf.getVfModules().getVfModule()) ;
            for (Map.Entry<String, AtomicInteger> entry : vnfModulemap.entrySet()) {
                String modelVersionId = entry.getKey();
                for (Vnf sdncVnf : sdncVnfList) {
                    if ( sdncVnf.getVnfId().equals(modelVersionId)) {
                        VFModule vfModule = new VFModule();
                        vfModule.setUuid(modelVersionId);
                        vfModule.setMaxInstances(entry.getValue().intValue());
                        VnfAssignments vnfAssignments = sdncVnf.getServiceData().getVnfTopologyInformation().getVnfAssignments();
                        if (null != vnfAssignments) {
                            List<Network> networks = new ArrayList<>();
                            for (VnfNetwork vnfNetwork : vnfAssignments.getVnfNetworks()) {
                                Network network = new Network();
                                network.setName(vnfNetwork.getNetworkName());
                                network.setUuid(vnfNetwork.getNetworkId());
                                if (null != vnfNetwork.getNetworkRole()) {
                                    Attribute  networkRole = new Attribute();
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
        log.debug("The size of vfmoduleLst:"+ vfmoduleLst.size());
        return vfmoduleLst;
    }

    /*
     * Build the map with key (model_version_id) and with the max occurrences of the value in the map
     * @param vf_module_List
     * @return
     */
    private static ConcurrentMap<String, AtomicInteger> buildMaxInstanceMap(List<VfModule> vfModuleList) {

        ConcurrentMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

        for (VfModule  vfModule: vfModuleList) {
            String vfModuleId = vfModule.getVfModuleId();
            map.putIfAbsent(vfModuleId, new AtomicInteger(0));
            map.get(vfModuleId).incrementAndGet();
        }
        return map;
    }

    /*
     * Get the common model list of VNFC from the SDNC Vnfs
     * @param sdncVnfMap
     * @return
     */
    private static List<VNFC> getVnfcList(List<Vnf> sdncVnfList) {
        List<VNFC> vnfcList = new ArrayList<>();
        if (sdncVnfList != null && !sdncVnfList.isEmpty()) {
            for (Vnf sdncVnf : sdncVnfList) {
                try {
                    List<VnfVm> sdncVnfVmLst = sdncVnf.getServiceData().getVnfTopologyInformation().getVnfAssignments().getVnfVms();
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
        log.debug("The size of vnfcList:"+ vnfcList.size());
        return vnfcList;
    }



}
