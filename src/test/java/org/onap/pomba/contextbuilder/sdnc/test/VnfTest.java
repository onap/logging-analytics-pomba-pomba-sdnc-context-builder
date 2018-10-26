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
import org.onap.pomba.contextbuilder.sdnc.model.ServiceData;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceStatus;
import org.onap.pomba.contextbuilder.sdnc.model.Vnf;

public class VnfTest {
    @Test
    public void testVnfWithParameters() {
        Vnf vnf = new Vnf("vnfId", new ServiceStatus(), new ServiceData());
        assertEquals("vnfId", vnf.getVnfId());
    }

    @Test
    public void testVnf() {
        Vnf vnf = new Vnf();
        vnf.setVnfId("vnfId");
        vnf.setServiceStatus(new ServiceStatus());
        vnf.setServiceData(new ServiceData());
        assertEquals("vnfId", vnf.getVnfId());
        assertEquals(new ServiceStatus(), vnf.getServiceStatus());
        assertEquals(new ServiceData(), vnf.getServiceData());
        String vnfString = vnf.toString();
        assertTrue(vnfString.contains("vnfId"));
    }

    @Test
    public void testVnfIsEqual() {
        Vnf vnf1 = new Vnf("vnfId", new ServiceStatus(), new ServiceData());
        assertTrue(vnf1.equals(vnf1));
        assertTrue(vnf1.hashCode() == -1891585304);
    }

}
