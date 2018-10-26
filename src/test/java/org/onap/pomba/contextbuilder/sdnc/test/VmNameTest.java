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
import org.onap.pomba.contextbuilder.sdnc.model.VmName;

public class VmNameTest {
    @Test
    public void testVmNameWithParameters() {
        VmName vmName = new VmName("vmName");
        assertEquals("vmName", vmName.getVmName());
    }

    @Test
    public void testVmName() {
        VmName vmName = new VmName();
        vmName.setVmName("vmName");
        assertEquals("vmName", vmName.getVmName());
        String vmNameString = vmName.toString();
        assertTrue(vmNameString.contains("[vmName=vmName]"));
    }

    @Test
    public void testVmNameIsEqual() {
        VmName vmName1 = new VmName("vmName1");
        assertTrue(vmName1.equals(vmName1));
        assertTrue(vmName1.hashCode() == 546861742);
    }

}
