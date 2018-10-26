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
import org.onap.pomba.contextbuilder.sdnc.model.VmName;
import org.onap.pomba.contextbuilder.sdnc.model.VmNetwork;
import org.onap.pomba.contextbuilder.sdnc.model.VnfVm;

public class VnfVmTest {
    @Test
    public void testVnfVmWithParameters() {
        VnfVm vnfVm = new VnfVm("vmType", new ArrayList<VmNetwork>(), 1, new ArrayList<VmName>());
        assertEquals("vmType", vnfVm.getVmType());
    }

    @Test
    public void testVnfVm() {
        VnfVm vnfVm = new VnfVm();
        vnfVm.setVmType("vmType");
        vnfVm.setVmNetworks(new ArrayList<VmNetwork>());
        vnfVm.setVmCount(1);
        vnfVm.setVmNames(new ArrayList<VmName>());
        assertEquals("vmType", vnfVm.getVmType());
        assertEquals(new ArrayList<VmNetwork>(), vnfVm.getVmNetworks());
        assertTrue(vnfVm.getVmCount() == 1);
        assertEquals(new ArrayList<VmName>(), vnfVm.getVmNames());
        String vnfVmString = vnfVm.toString();
        assertTrue(vnfVmString.contains("vmType"));
    }

    @Test
    public void testVnfVmIsEqual() {
        VnfVm vnfVm1 = new VnfVm("vmType", new ArrayList<VmNetwork>(), 1, new ArrayList<VmName>());
        assertTrue(vnfVm1.equals(vnfVm1));
        assertTrue(vnfVm1.hashCode() == -1026070735);
    }

}
