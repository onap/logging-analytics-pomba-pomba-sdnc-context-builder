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
import org.onap.pomba.contextbuilder.sdnc.model.VnfParameter;

public class VnfParameterTest {
    @Test
    public void testVnfParameterWithParameters() {
        VnfParameter vnfParamter = new VnfParameter("vnfParameterName", "vnfParameterValue");
        assertEquals("vnfParameterName", vnfParamter.getVnfParameterName());
        assertEquals("vnfParameterValue", vnfParamter.getVnfParameterValue());
    }

    @Test
    public void testVnfParameter() {
        VnfParameter vnfParamter = new VnfParameter();
        vnfParamter.setVnfParameterName("vnfParameterName");
        vnfParamter.setVnfParameterValue("vnfParameterValue");
        assertEquals("vnfParameterName", vnfParamter.getVnfParameterName());
        assertEquals("vnfParameterValue", vnfParamter.getVnfParameterValue());
        String vnfParamterString = vnfParamter.toString();
        assertTrue(vnfParamterString
                   .contains("[vnfParameterName=vnfParameterName,vnfParameterValue=vnfParameterValue]"));
    }

    @Test
    public void testVnfParameterIsEqual() {
        VnfParameter vnfParamter1 = new VnfParameter("vnfParameterName1", "vnfParameterValue1");
        assertTrue(vnfParamter1.equals(vnfParamter1));
        assertTrue(vnfParamter1.hashCode() == -1128531727);
    }

}
