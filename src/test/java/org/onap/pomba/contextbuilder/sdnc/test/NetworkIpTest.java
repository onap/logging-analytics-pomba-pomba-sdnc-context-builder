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
import org.onap.pomba.contextbuilder.sdnc.model.NetworkIp;

public class NetworkIpTest {
    @Test
    public void testNetworkIpWithParameters() {
        NetworkIp networkIp = new NetworkIp("networkIp");
        assertEquals("networkIp", networkIp.getIpAddress());
    }

    @Test
    public void testNetworkIp() {
        NetworkIp networkIp = new NetworkIp();
        networkIp.setIpAddress("networkIp");
        assertEquals("networkIp", networkIp.getIpAddress());
        String networkIpString = networkIp.toString();
        assertTrue(networkIpString.contains("[ipAddress=networkIp]"));
    }

    @Test
    public void testNetworkIpIsEqual() {
        NetworkIp networkIp1 = new NetworkIp("networkIp1");
        assertTrue(networkIp1.equals(networkIp1));
        assertTrue(networkIp1.hashCode() == -478253317);
    }

}
