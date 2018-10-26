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

import java.util.ArrayList;
import org.junit.Test;
import org.onap.pomba.contextbuilder.sdnc.model.NetworkIp;
import org.onap.pomba.contextbuilder.sdnc.model.NetworkIpV6;
import org.onap.pomba.contextbuilder.sdnc.model.VmNetwork;

public class VmNetworkTest {
    @Test
    public void testVmNetworkWithParameters() {
        VmNetwork vmNetwork = new VmNetwork("networkRole",
                                            new ArrayList<NetworkIpV6>(),
                                            "floatingIp",
                                            "useDhcp",
                                            new ArrayList<NetworkIp>(),
                                            "floatingIpV6");
        assertEquals("networkRole", vmNetwork.getNetworkRole());
        assertEquals(new ArrayList<NetworkIpV6>(), vmNetwork.getNetworkIpsV6());
        assertEquals("floatingIp", vmNetwork.getFloatingIp());
        assertEquals("useDhcp", vmNetwork.getUseDhcp());
        assertEquals(new ArrayList<NetworkIp>(), vmNetwork.getNetworkIps());
        assertEquals("floatingIpV6", vmNetwork.getFloatingIpV6());
    }

    @Test
    public void testVmNetwork() {
        VmNetwork vmNetwork = new VmNetwork();
        vmNetwork.setNetworkRole("networkRole");
        vmNetwork.setNetworkIpsV6(new ArrayList<NetworkIpV6>());
        vmNetwork.setFloatingIp("floatingIp");
        vmNetwork.setUseDhcp("useDhcp");
        vmNetwork.setNetworkIps(new ArrayList<NetworkIp>());
        vmNetwork.setFloatingIpV6("floatingIpV6");
        assertEquals("networkRole", vmNetwork.getNetworkRole());
        assertEquals(new ArrayList<NetworkIpV6>(), vmNetwork.getNetworkIpsV6());
        assertEquals("floatingIp", vmNetwork.getFloatingIp());
        assertEquals(new ArrayList<NetworkIp>(), vmNetwork.getNetworkIps());
        assertEquals("useDhcp", vmNetwork.getUseDhcp());
        assertEquals("floatingIpV6", vmNetwork.getFloatingIpV6());
        String vmNetworkString = vmNetwork.toString();
        assertTrue(vmNetworkString.contains("networkRole=networkRole"));
        assertTrue(vmNetworkString.contains("floatingIp=floatingIp,useDhcp=useDhcp"));
        assertTrue(vmNetworkString.contains("floatingIpV6=floatingIpV6"));
    }


    @Test
    public void testVmNetworkIsEqual() {
        VmNetwork vmNetwork1 = new VmNetwork("networkRole1",
                                             new ArrayList<NetworkIpV6>(),
                                             "floatingIp1",
                                             "useDhcp1",
                                             new ArrayList<NetworkIp>(),
                                             "floatingIpV61");
        assertTrue(vmNetwork1.equals(vmNetwork1));
        assertTrue(vmNetwork1.hashCode() == -103133323);
    }

}
