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
import org.onap.pomba.contextbuilder.sdnc.model.OperStatus;
import org.onap.pomba.contextbuilder.sdnc.model.RequestInformation;
import org.onap.pomba.contextbuilder.sdnc.model.SdncRequestHeader;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceData;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceInformation;
import org.onap.pomba.contextbuilder.sdnc.model.VnfRequestInformation;
import org.onap.pomba.contextbuilder.sdnc.model.VnfTopologyInformation;

public class ServiceDataTest {
    @Test
    public void testServiceDataWithParameters() {
        ServiceData serviceData = new ServiceData(new RequestInformation(),
                                                  new ServiceInformation(),
                                                  "vnfId",
                                                  new SdncRequestHeader(),
                                                  new VnfRequestInformation(),
                                                  new OperStatus(),
                                                  new VnfTopologyInformation());
        assertEquals(new RequestInformation(), serviceData.getRequestInformation());
        assertEquals(new ServiceInformation(), serviceData.getServiceInformation());
        assertEquals("vnfId", serviceData.getVnfId());
        assertEquals(new SdncRequestHeader(), serviceData.getSdncRequestHeader());
        assertEquals(new VnfRequestInformation(), serviceData.getVnfRequestInformation());
        assertEquals(new OperStatus(), serviceData.getOperStatus());
        assertEquals(new VnfTopologyInformation(), serviceData.getVnfTopologyInformation());
    }

    @Test
    public void testServiceData() {
        ServiceData serviceData = new ServiceData();
        serviceData.setRequestInformation(new RequestInformation());
        serviceData.setServiceInformation(new ServiceInformation());
        serviceData.setVnfId("vnfId");
        serviceData.setSdncRequestHeader(new SdncRequestHeader());
        serviceData.setVnfRequestInformation(new VnfRequestInformation());
        serviceData.setOperStatus(new OperStatus());
        serviceData.setVnfTopologyInformation(new VnfTopologyInformation());
        assertEquals(new RequestInformation(), serviceData.getRequestInformation());
        assertEquals(new ServiceInformation(), serviceData.getServiceInformation());
        assertEquals("vnfId", serviceData.getVnfId());
        assertEquals(new SdncRequestHeader(), serviceData.getSdncRequestHeader());
        assertEquals(new VnfRequestInformation(), serviceData.getVnfRequestInformation());
        assertEquals(new OperStatus(), serviceData.getOperStatus());
        assertEquals(new VnfTopologyInformation(), serviceData.getVnfTopologyInformation());
        String serviceDataString = serviceData.toString();
        assertTrue(serviceDataString.contains("vnfId=vnfId"));
    }

    @Test
    public void testServiceDataIsEqual() {
        ServiceData serviceData1 = new ServiceData(new RequestInformation(),
                                                   new ServiceInformation(),
                                                   "vnfId1",
                                                   new SdncRequestHeader(),
                                                   new VnfRequestInformation(),
                                                   new OperStatus(),
                                                   new VnfTopologyInformation());
        assertTrue(serviceData1.equals(serviceData1));
        assertTrue(serviceData1.hashCode() == -140583576);
    }

}
