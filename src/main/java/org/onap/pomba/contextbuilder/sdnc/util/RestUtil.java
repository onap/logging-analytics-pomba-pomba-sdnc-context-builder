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

public class RestUtil {

    private static Logger log = LoggerFactory.getLogger(RestService.class);

    public static final String INTERNAL_SERVER_ERROR   = "Internal Server Error";

    // HTTP headers
    public static final String TRANSACTION_ID = "X-TransactionId";
    public static final String FROM_APP_ID = "X-FromAppId";
    public static final String AUTHORIZATION = "Authorization";

    // Parameters for Query SDNC Model Data REST API  URL
    private static final String SERVICE_INSTANCE_ID = "serviceInstanceId";

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
            log.info("Header " + TRANSACTION_ID + " not present in request, generating new value: " + transactionId);
        }
        return transactionId;
    }


    /**
     * For each AAI VnfInstance, use the AAI VfModuleId to make a rest call to SDNC VNF-API to create a list of all SDNC Vnfs.
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
    public static String getSdncGenericResource(Client sdncClient, String sdncBaseUrl, String authorization, String sdncGenericResourcePath,
                                                        String serviceInstaceId, String transactionId) throws AuditException {
        String vnfSdncURL = sdncBaseUrl+generateSdncInstanceURL(sdncGenericResourcePath, serviceInstaceId);
        // send rest request to SDNC VNF-API
        String sndcVNFPayload = getSdncResource(sdncClient, vnfSdncURL, authorization, transactionId);
        return sndcVNFPayload;
    }


    public static ModelContext transform(String sdncResponse) {
        List<Object> jsonSpec = JsonUtils.filepathToList("config/sdnccontextbuilder.spec");
        Object jsonInput = JsonUtils.jsonToObject(sdncResponse);
        Chainr chainr = Chainr.fromSpec(jsonSpec);
        Object transObject = chainr.transform(jsonInput);
        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.toPrettyJsonString(transObject), ModelContext.class);

    }


    private static String getSdncResource(Client sdncClient, String url, String authorization, String transId) throws AuditException  {

        Response response = sdncClient.target(url).request()
                .header("Accept", "application/json")
                .header("Authorization", authorization).get();

        if (response.getStatus()  == 200) {
            return response.readEntity(String.class);
        } else if (response.getStatus() == 404) {
            //Resource not found, generate empty JSON format
            log.info("Return empty Json. Resource not found: "+url);
            return new JSONObject().toString();

        } else {
            throw new AuditException(INTERNAL_SERVER_ERROR + " with " + response.getStatus());
        }
    }


    private static String generateSdncInstanceURL(String siPath, String vfModuleLink) {
        return MessageFormat.format(siPath, vfModuleLink);
    }

}
