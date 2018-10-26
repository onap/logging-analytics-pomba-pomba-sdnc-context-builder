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

package org.onap.pomba.contextbuilder.sdnc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceInformation;

public class ServiceInformationTest {
    @Test
    public void testServiceInformationWithParameters() {
        ServiceInformation serviceInformation = new ServiceInformation("serviceId",
                                                                       "serviceType",
                                                                       "subscriberName",
                                                                       "serviceInstanceId");
        assertEquals("serviceId", serviceInformation.getServiceId());
        assertEquals("serviceType", serviceInformation.getServiceType());
        assertEquals("subscriberName", serviceInformation.getSubscriberName());
        assertEquals("serviceInstanceId", serviceInformation.getServiceInstanceId());
    }

    @Test
    public void testServiceInformation() {
        ServiceInformation serviceInformation = new ServiceInformation();
        serviceInformation.setServiceId("serviceId");
        serviceInformation.setServiceType("serviceType");
        serviceInformation.setSubscriberName("subscriberName");
        serviceInformation.setServiceInstanceId("serviceInstanceId");
        assertEquals("serviceId", serviceInformation.getServiceId());
        assertEquals("serviceType", serviceInformation.getServiceType());
        assertEquals("subscriberName", serviceInformation.getSubscriberName());
        assertEquals("serviceInstanceId", serviceInformation.getServiceInstanceId());
        String serviceInformationString = serviceInformation.toString();
        assertTrue(serviceInformationString
                   .contains("[serviceId=serviceId,serviceType=serviceType,subscriberName=subscriberName,"
                             + "serviceInstanceId=serviceInstanceId]"));
    }

    @Test
    public void testServiceInformationIsEqual() {
        ServiceInformation serviceInformation1 = new ServiceInformation("serviceId1",
                                                                        "serviceType1",
                                                                        "subscriberName1",
                                                                        "serviceInstanceId1");
        assertTrue(serviceInformation1.equals(serviceInformation1));
        assertTrue(serviceInformation1.hashCode() == -1226728686);
    }

}
