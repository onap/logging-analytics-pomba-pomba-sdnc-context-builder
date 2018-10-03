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

import javax.ws.rs.client.Client;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;
import org.onap.pomba.contextbuilder.sdnc.service.rs.RestService;
import org.onap.pomba.contextbuilder.sdnc.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringServiceImpl implements SpringService {
    private static Logger log = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private Client jerseyClient;
    @Autowired
    private String sdncBaseUrl;
    @Autowired
    private String sdncBasicAuthorization;
    @Autowired
    private String sdncGenericResourcePath;
    @Autowired
    private String sdncCtxBuilderBasicAuthorization;

    public SpringServiceImpl() {
        // needed for instantiation
    }


    @Override
    public ModelContext getContext(String serviceInstanceId, String transactionId) throws AuditException {
        ModelContext context = null;
        String url = "serviceInstanceId=" + serviceInstanceId + " transactionId=" + transactionId;
        log.info("URL Query the SDN-C model data with URL: " , url);

        // Retrieve the service instance information from SDNC and AAI
        try {
            String sdncResponse = RestUtil.getSdncGenericResource(jerseyClient, sdncBaseUrl, sdncBasicAuthorization, sdncGenericResourcePath, serviceInstanceId);
            log.info("sdncResponse: ", sdncResponse);
            context = RestUtil.transform(sdncResponse);
        } catch (AuditException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AuditException(e.getLocalizedMessage());
        }
        return context;
    }

    public String getSdncAuthoriztion() {
        return sdncCtxBuilderBasicAuthorization;
    }

}
