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

package org.onap.pomba.contextbuilder.sdnc.service;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.camel.ProducerTemplate;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.onap.aai.restclient.client.RestClient;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceEntity;
import org.onap.pomba.contextbuilder.sdnc.service.rs.RestService;
import org.onap.pomba.contextbuilder.sdnc.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringServiceImpl implements SpringService {

    private static Logger log = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private String sdncCtxBuilderBasicAuthorization;
    private KieContainer kieContainer;
    @Autowired
    private ProducerTemplate producerTemplate;
    @Autowired
    private String aaiBasicAuthorization;
    @Autowired
    private RestClient aaiClient;
    @Autowired
    private String aaiBaseUrl;
    @Autowired
    private String aaiPathToServiceInstanceQuery;

    public static final String APP_NAME = "SdncContextBuilder";
    public static final String MDC_REQUEST_ID = "RequestId";
    public static final String MDC_SERVER_FQDN = "ServerFQDN";
    public static final String MDC_SERVICE_NAME = "ServiceName";
    public static final String MDC_PARTNER_NAME = "PartnerName";
    public static final String MDC_START_TIME = "StartTime";
    public static final String MDC_SERVICE_INSTANCE_ID = "ServiceInstanceId";
    public static final String MDC_INVOCATION_ID = "InvocationID";
    public static final String MDC_CLIENT_ADDRESS = "ClientAddress";
    public static final String MDC_STATUS_CODE = "StatusCode";
    public static final String MDC_RESPONSE_CODE = "ResponseCode";
    public static final String MDC_INSTANCE_UUID = "InstanceUUID";

    private static UUID instanceUUID = UUID.randomUUID();

    public SpringServiceImpl() {
        // needed for instantiation
    }

    @Autowired
    public SpringServiceImpl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    @Override
    public ModelContext getContext(HttpServletRequest request, String serviceInstanceId, String transactionId, String partnerName) throws AuditException {

        String remoteAddress = request.getRemoteAddr() != null ? request.getRemoteAddr() : null;
        initMDC(transactionId, partnerName, serviceInstanceId, remoteAddress);

        ModelContext context = null;

        // Call AAI system to populate ServiceData
        ServiceEntity serviceEntity = RestUtil.getServiceEntity(aaiClient, aaiBaseUrl, aaiBasicAuthorization, aaiPathToServiceInstanceQuery, serviceInstanceId, transactionId);

        if (null == serviceEntity) {
            return context;
        }

        processApiMappingRules(serviceEntity);
        log.info("SDN-C determined API: {}", serviceEntity.getApiName());

        context = producerTemplate.requestBody("direct:startRoutingProcess", serviceEntity, ModelContext.class);

        return context;
    }

    private void initMDC(String requestId, String partnerName, String serviceInstanceId, String remoteAddress) {
        MDC.clear();
        MDC.put(MDC_REQUEST_ID, requestId);
        MDC.put(MDC_SERVICE_NAME, APP_NAME);
        MDC.put(MDC_SERVICE_INSTANCE_ID, serviceInstanceId);
        MDC.put(MDC_PARTNER_NAME, partnerName);
        MDC.put(MDC_CLIENT_ADDRESS, remoteAddress);
        MDC.put(MDC_START_TIME, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date()));
        MDC.put(MDC_INVOCATION_ID, UUID.randomUUID().toString());
        MDC.put(MDC_INSTANCE_UUID, instanceUUID.toString());

        try {
            MDC.put(MDC_SERVER_FQDN, InetAddress.getLocalHost().getCanonicalHostName());
        } catch (Exception e) {
            // If, for some reason we are unable to get the canonical host name,
            // we just want to leave the field null.
            log.info("Could not get canonical host name for " + MDC_SERVER_FQDN + ", leaving field null", e);
        }
    }

    private void processApiMappingRules(ServiceEntity serviceData) {

        KieSession kieSession = kieContainer.newKieSession();
        log.info("KIE Session is created");
        kieSession.insert(serviceData);
        kieSession.fireAllRules();
        log.info("Rules are fired");
        kieSession.getFactHandles().forEach(fh -> kieSession.delete(fh));
        kieSession.dispose();
    }

    public String getSdncAuthoriztion() {
        return sdncCtxBuilderBasicAuthorization;
    }



}
