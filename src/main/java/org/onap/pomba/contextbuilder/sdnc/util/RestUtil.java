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
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONObject;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;
import org.onap.pomba.contextbuilder.sdnc.service.rs.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.onap.aai.restclient.client.OperationResult;
import org.onap.aai.restclient.client.RestClient;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.Map;
import java.util.Collections;
import javax.ws.rs.core.MultivaluedMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceEntity;

public class RestUtil {

    private static Logger log = LoggerFactory.getLogger(RestService.class);

    public static final String INTERNAL_SERVER_ERROR   = "Internal Server Error";

    // HTTP headers
    public static final String TRANSACTION_ID = "X-TransactionId";
    public static final String FROM_APP_ID = "X-FromAppId";
    public static final String AUTHORIZATION = "Authorization";

    // AAI related
    private static final String APP_NAME = "sdncCtxBuilder";
    private static final String EMPTY_JSON_STRING = "{}";
    private static final String JSON_ATT_RESOURCE_TYPE = "resource-type";
    private static final String JSON_ATT_RESOURCE_LINK = "resource-link";
    private static final String RESULT_DATA = "result-data";
    private static final String CATALOG_SERVICE_INSTANCE = "service-instance";
    private static final String CUSTOMER_ID_STRING = "/customers/customer/";
    private static final String SERVICE_TYPE_STRING = "/service-subscriptions/service-subscription/";
    private static final String CUSTOMER = "customer";
    private static final String JSON_ATT_GLOBAL_CUSTOMER_ID = "global-customer-id";
    private static final String JSON_ATT_SUBSCRIBER_TYPE = "subscriber-type";
    private static final String JSON_ATT_SUBSCRIBER_NAME = "subscriber-name";

    private static final String FORWARD_SLASH = "/";

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


    public static void validateHeader(HttpHeaders headers, String sdncCtxBuilderBasicAuthorization) throws AuditException {

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
    }


    /*
     * The purpose is to keep same transaction Id from north bound interface to south bound interface
     */
    public static String extractTranIdHeader(HttpHeaders headers)  {
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
        String vnfSdncURL = sdncBaseUrl+generateSdncInstanceURL(sdncGenericResourcePath, serviceInstaceId);
        // send rest request to SDNC VNF-API
        return getSdncResource(sdncClient, vnfSdncURL, authorization);
    }


    public static ModelContext transform(String sdncResponse) {
        List<Object> jsonSpec = JsonUtils.filepathToList("config/sdnccontextbuilder.spec");
        Object jsonInput = JsonUtils.jsonToObject(sdncResponse);
        Chainr chainr = Chainr.fromSpec(jsonSpec);
        Object transObject = chainr.transform(jsonInput);
        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.toPrettyJsonString(transObject), ModelContext.class);

    }


    private static String getSdncResource(Client sdncClient, String url, String authorization) throws AuditException  {

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
            throw new AuditException(INTERNAL_SERVER_ERROR + " with " + response.getStatus());
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
                                                String transactionId)throws AuditException {

        String obtainResourceLink_url = generateUrl_ForResourceLink(aaiBaseUrl, aaiPathToSearchNodeQuery, serviceInstanceId);
        String aaiResourceData  = getAaiResource(aaiClient, obtainResourceLink_url, aaiBasicAuthorization, transactionId, MediaType.valueOf(MediaType.APPLICATION_JSON));

        // Handle the case if the service instance is not found in AAI
        if (isEmptyJson(aaiResourceData)) {
            log.info(" Service Instance {} is not found from AAI", serviceInstanceId);
            // Only return the empty Json on the root level. i.e service instance
            return null;
        }

        String resourceLink = extractResourceLinkBasedOnServiceInstance(aaiResourceData, CATALOG_SERVICE_INSTANCE);

        ServiceEntity serviceEntityObj =  createServiceEntityObj (resourceLink); //customerId and serviceType are updated here.
        if (serviceEntityObj != null) {
           serviceEntityObj.setServiceInstanceId(serviceInstanceId);
           String customerId = serviceEntityObj.getCustomerId();
           // Obtain customerType and customerName
           String obtainCustomer_url = generateUrl_ForCustomer (aaiBaseUrl, aaiPathToCustomerQuery, customerId);
           String aaiCustomerData  = getAaiResource(aaiClient, obtainCustomer_url, aaiBasicAuthorization, transactionId, MediaType.valueOf(MediaType.APPLICATION_JSON));
           if (isEmptyJson(aaiCustomerData)) {
               log.info(" Customer name {} is not found from AAI", customerId);
               // Only return the empty Json on the root level. i.e service instance
               throw new AuditException(INTERNAL_SERVER_ERROR + ": Customer ID cannot be found from AAI :" + customerId);
           }
           // Update customerType and customerName to the existing serviceEntityObj
           updateServiceEntityObj ( aaiCustomerData , serviceEntityObj);
        }

        return serviceEntityObj;
    }

    private static boolean isEmptyJson(String serviceInstancePayload) {
        return (serviceInstancePayload.equals(EMPTY_JSON_STRING));
    }

    private static String generateUrl_ForResourceLink (String aaiBaseURL, String aaiPathToSearchNodeQuery ,String serviceInstanceId) {
        return aaiBaseURL + aaiPathToSearchNodeQuery + serviceInstanceId;
    }

    private static String generateUrl_ForCustomer (String aaiBaseURL, String aaiPathToCustomerQuery ,String customerId) {
        return aaiBaseURL + aaiPathToCustomerQuery + customerId;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, List<String>> buildHeaders(String aaiBasicAuthorization, String transactionId) {
        MultivaluedMap<String, String> headers = new MultivaluedMapImpl();
        headers.put(TRANSACTION_ID, Collections.singletonList(transactionId));
        headers.put(FROM_APP_ID, Collections.singletonList(APP_NAME));
        headers.put(AUTHORIZATION, Collections.singletonList(aaiBasicAuthorization));
        return headers;
    }

    private static String getAaiResource(RestClient client, String url, String aaiBasicAuthorization, String transId, MediaType mediaType)
            throws AuditException {
        OperationResult result = client.get(url, buildHeaders(aaiBasicAuthorization, transId), MediaType.valueOf(MediaType.APPLICATION_JSON));

        if (result.getResultCode() == 200) {
            return result.getResult();
        } else if (result.getResultCode() == 404) {
            // Resource not found, generate empty JSON format
            log.info("Resource for {} is not found: {}", url, "return empty Json format");
            return new JSONObject().toString();

        } else {
            throw new AuditException(INTERNAL_SERVER_ERROR + " with " + result.getFailureCause());
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
    private static String extractResourceLinkBasedOnServiceInstance(String payload, String catalog) throws AuditException {
        String resourceLink = null;
        log.info("Fetching the resource-link based on resource-type=" + catalog);

        try {
            JSONArray result_data_list = new JSONObject(payload).getJSONArray(RESULT_DATA);
            if (result_data_list != null) {
                for (int i = 0; i < result_data_list.length(); i++) {
                    JSONObject obj = result_data_list.optJSONObject(i);
                    if (obj.has(JSON_ATT_RESOURCE_TYPE) && (obj.getString(JSON_ATT_RESOURCE_TYPE).equals(catalog) ))  {
                        resourceLink = obj.getString(JSON_ATT_RESOURCE_LINK);
                        log.info(resourceLink);
                        return resourceLink;
                    }
                }
            }
        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new AuditException("Json Reader Parse Error " + e.getMessage());
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
            if (obj.has(JSON_ATT_GLOBAL_CUSTOMER_ID) && (obj.getString(JSON_ATT_GLOBAL_CUSTOMER_ID).equals(customerId) ))  {
                serviceEntityObj.setCustomerType(obj.getString(JSON_ATT_SUBSCRIBER_TYPE));
                serviceEntityObj.setCustomerName(obj.getString(JSON_ATT_SUBSCRIBER_NAME));
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
        int customer_id_idx = resourceLink.indexOf(CUSTOMER_ID_STRING);
        int service_type_idx = resourceLink.indexOf(SERVICE_TYPE_STRING);

        if (( customer_id_idx < 0 ) || ( service_type_idx < 0 )) {
           return null;
        }

        ServiceEntity serviceEntity = new ServiceEntity ();
        serviceEntity.setCustomerId(abstractStrInfo(resourceLink, CUSTOMER_ID_STRING));
        serviceEntity.setServiceType(abstractStrInfo(resourceLink, SERVICE_TYPE_STRING));
        return serviceEntity;
    }

    private static String abstractStrInfo (String origStr, String matchStr) {
        String after = origStr.substring( origStr.indexOf(matchStr) + matchStr.length());

        return after.substring(0, after.indexOf(FORWARD_SLASH));
    }
}
