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

package org.onap.pomba.contextbuilder.sdnc.handlers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import org.apache.camel.Exchange;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.onap.aai.restclient.client.RestClient;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.common.datatypes.Service;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditError;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceEntity;
import org.onap.pomba.contextbuilder.sdnc.model.Vnf;
import org.onap.pomba.contextbuilder.sdnc.model.VnfInstance;
import org.onap.pomba.contextbuilder.sdnc.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VnfApiHandler {

    private static Logger log = LoggerFactory.getLogger(VnfApiHandler.class);
    @Autowired
    private String aaiBasicAuthorization;
    @Autowired
    private RestClient aaiClient;
    @Autowired
    private String aaiBaseUrl;
    @Autowired
    private String aaiServiceInstancePath;
    @Autowired
    private Client jerseyClient;
    @Autowired
    private String sdncBaseUrl;
    @Autowired
    private String sdncBasicAuthorization;
    @Autowired
    private String sdncVnfPath;

    private static final String JSON_RELATIONSHIP_LIST = "relationship-list";
    private static final String JSON_RELATIONSHIP = "relationship";
    private static final String JSON_RELATED_TO = "related-to";
    private static final String JSON_RELATED_LINK = "related-link";
    private static final String JSON_GENERIC_VNF = "generic-vnf";
    private static final String EMPTY_JSON_STRING = "{}";

    public ModelContext process(Exchange exchange) throws Exception {

        log.info("in VNF-API HANDLER: ");
        ModelContext context = new ModelContext();
        Service service = new Service();
        ServiceEntity serviceEntity = (ServiceEntity)exchange.getIn().getBody();
        service.setUuid(serviceEntity.getServiceInstanceId());
        service.setName( serviceEntity.getServiceType() + " service instance");

        // GET the list of VNF related-to links from AAI
        String url= aaiBaseUrl+generateServiceInstanceURL(aaiServiceInstancePath, serviceEntity.getCustomerId(), serviceEntity.getServiceType(), serviceEntity.getServiceInstanceId());
        String serviceInstancePayload = RestUtil.getAaiResource(aaiClient, url, aaiBasicAuthorization, serviceEntity.getTransactionId(), MediaType.valueOf(MediaType.APPLICATION_JSON));
        List<String> genericVnfLinks = extractGenericVnfRelatedLink(serviceInstancePayload);

        // GET the VNF list (module-id) from AAI
        List <VnfInstance> vnfList = retrieveAaiVnfList(aaiClient, aaiBaseUrl, aaiBasicAuthorization, serviceEntity.getTransactionId(), genericVnfLinks);


        // GET the module-id from SDNC using VNF-API
        Map<String,List<Vnf>> sdncVnfMap = RestUtil.getSdncVnfList(jerseyClient, sdncBaseUrl, sdncVnfPath,
                sdncBasicAuthorization, vnfList);

        // Transform the AAI and SDNC models to the audit common model
        context = RestUtil.transformVnfList(vnfList, sdncVnfMap);

        context.setService(service);

        return context;
    }

    private static String generateServiceInstanceURL(String siPath, String customerId, String serviceType, String serviceInstanceId) {
        return MessageFormat.format(siPath, customerId, serviceType, serviceInstanceId);
    }

    /*
     * Extract the generic-vnf related-linkfrom Json payload. For example
     * {
     *    "related-to": "generic-vnf",
     *    "related-link": "/aai/v11/network/generic-vnfs/generic-vnf/d94daff6-7d5b-4d2e-bc99-c9af0754b59d",
     * }
     */
    private static List<String> extractGenericVnfRelatedLink(String serviceInstancePayload) throws AuditException {
        List<String> genericVnfLinks = new ArrayList<String>();;

        try {
            JSONObject relationshipList = new JSONObject(serviceInstancePayload).getJSONObject(JSON_RELATIONSHIP_LIST);
            JSONArray relationship = relationshipList.getJSONArray(JSON_RELATIONSHIP);
            if (relationship != null) {
                for (int i = 0; i < relationship.length(); i++) {
                    JSONObject relationshipInstance = relationship.optJSONObject(i);
                    if (relationshipInstance.has(JSON_RELATED_TO) && (relationshipInstance.getString(JSON_RELATED_TO).equals(JSON_GENERIC_VNF)))  {
                        genericVnfLinks.add(relationshipInstance.getString(JSON_RELATED_LINK));
                    }
                }
            }
        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new AuditException(AuditError.JSON_PARSE_ERROR + e.getMessage());
        }
        return genericVnfLinks;
    }

    private static List<VnfInstance> retrieveAaiVnfList(RestClient aaiClient, String aaiBaseUrl, String aaiBasicAuthorization, String transactionId, List <String>genericVnfLinks) throws AuditException {
        List<VnfInstance> vnfList = new ArrayList<VnfInstance>();
        for (String genericVnfLink : genericVnfLinks) {
            String genericVnfUrl = RestUtil.generateAaiUrl(aaiBaseUrl, genericVnfLink, null);
            String genericVnfPayload = RestUtil.getAaiResource(aaiClient, genericVnfUrl, aaiBasicAuthorization, transactionId, MediaType.valueOf(MediaType.APPLICATION_JSON));
            if (genericVnfPayload.equals(EMPTY_JSON_STRING)) {
                log.info("retrieveAaiVnfList "+ genericVnfPayload +" is not found, " + "return empty Json ");
            } else {
                // Create the AAI VnfInstance from the AAI generic-vnf json
                VnfInstance vnfInstance = VnfInstance.fromJson(genericVnfPayload);
                vnfList.add(vnfInstance);
            }
        }
        log.debug("The size of vnfList:"+ vnfList.size());
        return vnfList;
    }
}
