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

import org.apache.camel.ProducerTemplate;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;
import org.onap.pomba.contextbuilder.sdnc.service.rs.RestService;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.onap.pomba.contextbuilder.sdnc.util.RestUtil;
import org.onap.aai.restclient.client.RestClient;

@Service
public class SpringServiceImpl implements SpringService {
    private static Logger log = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private String sdncCtxBuilderBasicAuthorization;
    private KieContainer kieContainer;
    @Autowired
    private ProducerTemplate producerTemplate;

    //AAI related
    @Autowired
    private String aaiBasicAuthorization;
    @Autowired
    private RestClient aaiClient;
    @Autowired
    private String aaiBaseUrl;
    @Autowired
    private String aaiPathToSearchNodeQuery;
    @Autowired
    private String aaiPathToCustomerQuery;

    public SpringServiceImpl() {
        // needed for instantiation
    }

    @Autowired
    public SpringServiceImpl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;

    }

    @Override
    public ModelContext getContext(String serviceInstanceId, String transactionId) throws AuditException {
        ModelContext context = null;

        // Call AAI system to populate ServiceData
        ServiceEntity serviceEntity = RestUtil.getServiceEntity(aaiClient, aaiBaseUrl, aaiBasicAuthorization, aaiPathToSearchNodeQuery, aaiPathToCustomerQuery, serviceInstanceId, transactionId);

        processApiMappingRules(serviceEntity);
        log.info("SDN-C determined API: " + serviceEntity.getApiName());

        context = producerTemplate.requestBody("direct:startRoutingProcess", serviceEntity, ModelContext.class);

        return context;
    }

    private void processApiMappingRules(ServiceEntity serviceData){

        KieSession kieSession = kieContainer.newKieSession();
        log.info ("KIE Session is created");
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
