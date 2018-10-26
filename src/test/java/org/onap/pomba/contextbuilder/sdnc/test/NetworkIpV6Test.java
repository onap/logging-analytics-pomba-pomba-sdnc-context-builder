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
import org.onap.pomba.contextbuilder.sdnc.model.NetworkIpV6;

public class NetworkIpV6Test {
    @Test
    public void testNetworkIpV6WithParameters() {
        NetworkIpV6 networkIpV6 = new NetworkIpV6("networkIpV6");
        assertEquals("networkIpV6", networkIpV6.getIpAddressIpV6());
    }

    @Test
    public void testNetworkIpV6() {
        NetworkIpV6 networkIpV6 = new NetworkIpV6();
        networkIpV6.setIpAddressIpV6("networkIpV6");
        assertEquals("networkIpV6", networkIpV6.getIpAddressIpV6());
        String networkIpV6String = networkIpV6.toString();
        assertTrue(networkIpV6String.contains("[ipAddressIpV6=networkIpV6]"));
    }

    @Test
    public void testNetworkIpV6IsEqual() {
        NetworkIpV6 networkIpV61 = new NetworkIpV6("networkIpV61");
        assertTrue(networkIpV61.equals(networkIpV61));
        assertTrue(networkIpV61.hashCode() == -39929445);
    }

}
