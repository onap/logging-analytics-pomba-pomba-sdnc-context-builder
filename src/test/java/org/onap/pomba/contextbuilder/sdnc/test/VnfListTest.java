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
import org.onap.pomba.contextbuilder.sdnc.model.Vnf;
import org.onap.pomba.contextbuilder.sdnc.model.VnfList;

public class VnfListTest {
    @Test
    public void testVnfListWithParameters() {
        VnfList vnfList = new VnfList(new ArrayList<Vnf>());
        assertEquals(new ArrayList<Vnf>(), vnfList.getVnfList());
    }

    @Test
    public void testVnfList() {
        VnfList vnfList = new VnfList();
        vnfList.setVnfList(new ArrayList<Vnf>());
        assertEquals(new ArrayList<Vnf>(), vnfList.getVnfList());
        String vnfListString = vnfList.toString();
        assertTrue(vnfListString.contains("vnfList"));
    }

    @Test
    public void testVnfListIsEqual() {
        VnfList vnfList1 = new VnfList(new ArrayList<Vnf>());
        assertTrue(vnfList1.equals(vnfList1));
        assertTrue(vnfList1.hashCode() == 32);
    }

}
