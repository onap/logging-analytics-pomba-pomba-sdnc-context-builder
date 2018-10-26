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
import org.onap.pomba.contextbuilder.sdnc.model.ServiceStatus;

public class ServiceStatusTest {
    @Test
    public void testServiceStatusWithParameters() {
        ServiceStatus serviceStatus = new ServiceStatus("responseTimestamp",
                                                        "vnfsdnAction",
                                                        "rpcAction",
                                                        "rpcName",
                                                        "responseCode",
                                                        "finalIndicator",
                                                        "requestStatus");
        assertEquals("responseTimestamp", serviceStatus.getResponseTimestamp());
        assertEquals("vnfsdnAction", serviceStatus.getVnfsdnAction());
        assertEquals("rpcAction", serviceStatus.getRpcAction());
        assertEquals("rpcName", serviceStatus.getRpcName());
        assertEquals("responseCode", serviceStatus.getResponseCode());
        assertEquals("finalIndicator", serviceStatus.getFinalIndicator());
        assertEquals("requestStatus", serviceStatus.getRequestStatus());
    }

    @Test
    public void testServiceStatus() {
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setResponseTimestamp("responseTimestamp");
        serviceStatus.setVnfsdnAction("vnfsdnAction");
        serviceStatus.setRpcAction("rpcAction");
        serviceStatus.setRpcName("rpcName");
        serviceStatus.setResponseCode("responseCode");
        serviceStatus.setFinalIndicator("finalIndicator");
        serviceStatus.setRequestStatus("requestStatus");
        assertEquals("responseTimestamp", serviceStatus.getResponseTimestamp());
        assertEquals("vnfsdnAction", serviceStatus.getVnfsdnAction());
        assertEquals("rpcAction", serviceStatus.getRpcAction());
        assertEquals("responseCode", serviceStatus.getResponseCode());
        assertEquals("rpcName", serviceStatus.getRpcName());
        assertEquals("finalIndicator", serviceStatus.getFinalIndicator());
        assertEquals("requestStatus", serviceStatus.getRequestStatus());
        String serviceStatusString = serviceStatus.toString();
        assertTrue(serviceStatusString
                   .contains("[responseTimestamp=responseTimestamp,vnfsdnAction=vnfsdnAction,rpcAction=rpcAction,"
                             + "rpcName=rpcName,responseCode=responseCode,finalIndicator=finalIndicator,"
                             + "requestStatus=requestStatus]"));
    }

    @Test
    public void testServiceStatusIsEqual() {
        ServiceStatus serviceStatus1 = new ServiceStatus("responseTimestamp1",
                                                         "vnfsdnAction1",
                                                         "rpcAction1",
                                                         "rpcName1",
                                                         "responseCode1",
                                                         "finalIndicator1",
                                                         "requestStatus1");
        assertTrue(serviceStatus1.equals(serviceStatus1));
        assertTrue(serviceStatus1.hashCode() == -1947783113);
    }

}
