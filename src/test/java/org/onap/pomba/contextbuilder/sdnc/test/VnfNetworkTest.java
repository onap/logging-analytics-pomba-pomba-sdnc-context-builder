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
import org.onap.pomba.contextbuilder.sdnc.model.VnfNetwork;

public class VnfNetworkTest {
    @Test
    public void testVnfNetworkWithParameters() {
        VnfNetwork vnfNetwork = new VnfNetwork("networkRole",
                                               "contrailNetworkFqdn",
                                               "networkName",
                                               "networkId",
                                               "neutronId");
        assertEquals("networkRole", vnfNetwork.getNetworkRole());
        assertEquals("contrailNetworkFqdn", vnfNetwork.getContrailNetworkFqdn());
        assertEquals("networkName", vnfNetwork.getNetworkName());
        assertEquals("networkId", vnfNetwork.getNetworkId());
        assertEquals("neutronId", vnfNetwork.getNeutronId());
    }

    @Test
    public void testVnfNetwork() {
        VnfNetwork vnfNetwork = new VnfNetwork();
        vnfNetwork.setNetworkRole("networkRole");
        vnfNetwork.setContrailNetworkFqdn("contrailNetworkFqdn");
        vnfNetwork.setNetworkName("networkName");
        vnfNetwork.setNetworkId("networkId");
        vnfNetwork.setNeutronId("neutronId");
        assertEquals("networkRole", vnfNetwork.getNetworkRole());
        assertEquals("contrailNetworkFqdn", vnfNetwork.getContrailNetworkFqdn());
        assertEquals("networkName", vnfNetwork.getNetworkName());
        assertEquals("networkId", vnfNetwork.getNetworkId());
        assertEquals("neutronId", vnfNetwork.getNeutronId());
        String vnfNetworkString = vnfNetwork.toString();
        assertTrue(vnfNetworkString
                   .contains("[networkRole=networkRole,contrailNetworkFqdn=contrailNetworkFqdn,"
                             + "networkName=networkName,networkId=networkId,neutronId=neutronId]"));
    }

    @Test
    public void testVnfNetworkIsEqual() {
        VnfNetwork vnfNetwork1 = new VnfNetwork("networkRole1",
                                                "contrailNetworkFqdn1",
                                                "networkName1",
                                                "networkId1",
                                                "neutronId1");
        assertTrue(vnfNetwork1.equals(vnfNetwork1));
        assertTrue(vnfNetwork1.hashCode() == -1838617061);
    }

}
