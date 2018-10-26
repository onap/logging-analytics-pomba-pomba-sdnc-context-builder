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
import org.onap.pomba.contextbuilder.sdnc.model.VnfRequestInformation;

public class VnfRequestInformationTest {
    @Test
    public void testVnfRequestInformationWithParameters() {
        VnfRequestInformation vnfRequestInformation = new VnfRequestInformation("vnfName",
                                                                                "tenant",
                                                                                "aicCloudRegion",
                                                                                "usePreload",
                                                                                "vnfType",
                                                                                "vnfId",
                                                                                "genericVnfType",
                                                                                "genericVnfName",
                                                                                "genericVnfId");
        assertEquals("vnfName", vnfRequestInformation.getVnfName());
        assertEquals("tenant", vnfRequestInformation.getTenant());
        assertEquals("aicCloudRegion", vnfRequestInformation.getAicCloudRegion());
        assertEquals("usePreload", vnfRequestInformation.getUsePreload());
        assertEquals("vnfType", vnfRequestInformation.getVnfType());
        assertEquals("vnfId", vnfRequestInformation.getVnfId());
        assertEquals("genericVnfType", vnfRequestInformation.getGenericVnfType());
        assertEquals("genericVnfName", vnfRequestInformation.getGenericVnfName());
        assertEquals("genericVnfId", vnfRequestInformation.getGenericVnfId());
    }

    @Test
    public void testVnfRequestInformation() {
        VnfRequestInformation vnfRequestInformation = new VnfRequestInformation();
        vnfRequestInformation.setVnfName("vnfName");
        vnfRequestInformation.setTenant("tenant");
        vnfRequestInformation.setAicCloudRegion("aicCloudRegion");
        vnfRequestInformation.setUsePreload("usePreload");
        vnfRequestInformation.setVnfType("vnfType");
        vnfRequestInformation.setVnfId("vnfId");
        vnfRequestInformation.setGenericVnfType("genericVnfType");
        vnfRequestInformation.setGenericVnfName("genericVnfName");
        vnfRequestInformation.setGenericVnfId("genericVnfId");
        assertEquals("vnfName", vnfRequestInformation.getVnfName());
        assertEquals("tenant", vnfRequestInformation.getTenant());
        assertEquals("aicCloudRegion", vnfRequestInformation.getAicCloudRegion());
        assertEquals("usePreload", vnfRequestInformation.getUsePreload());
        assertEquals("vnfType", vnfRequestInformation.getVnfType());
        assertEquals("vnfId", vnfRequestInformation.getVnfId());
        assertEquals("genericVnfType", vnfRequestInformation.getGenericVnfType());
        assertEquals("genericVnfName", vnfRequestInformation.getGenericVnfName());
        assertEquals("genericVnfId", vnfRequestInformation.getGenericVnfId());
        String vnfRequestInformationString = vnfRequestInformation.toString();
        assertTrue(vnfRequestInformationString
                   .contains("[vnfName=vnfName,tenant=tenant,aicCloudRegion=aicCloudRegion,usePreload=usePreload,"
                             + "vnfType=vnfType,vnfId=vnfId,genericVnfType=genericVnfType,"
                             + "genericVnfName=genericVnfName,genericVnfId=genericVnfId]"));
    }

    @Test
    public void testVnfRequestInformationIsEqual() {
        VnfRequestInformation vnfRequestInformation1 = new VnfRequestInformation("vnfName1",
                                                                                 "tenant1",
                                                                                 "aicCloudRegion1",
                                                                                 "usePreload1",
                                                                                 "vnfType1",
                                                                                 "vnfId1",
                                                                                 "genericVnfType1",
                                                                                 "genericVnfName1",
                                                                                 "genericVnfId1");
        assertTrue(vnfRequestInformation1.equals(vnfRequestInformation1));
        assertTrue(vnfRequestInformation1.hashCode() == 869221953);
    }

}
