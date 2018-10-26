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

import org.apache.camel.Exchange;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.common.datatypes.Service;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VnfApiHandler {

    private static Logger log = LoggerFactory.getLogger(VnfApiHandler.class);

    public ModelContext process(Exchange exchange) throws Exception {

        log.info("in VNF-API HANDLER: ");

//  dummy population of the model context
// The following lines should be replaced with the logic of calling to SDN-C VNF-API
// and transforming the response into common model
        ModelContext context = new ModelContext();
        Service service = new Service();
        ServiceEntity serviceEntity = (ServiceEntity)exchange.getIn().getBody();
        service.setName( serviceEntity.getServiceType() + " service instance");
        context.setService(service);
// end dummy code

        return context;
    }


}
