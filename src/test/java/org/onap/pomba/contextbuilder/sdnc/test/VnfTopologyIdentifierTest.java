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
import org.onap.pomba.contextbuilder.sdnc.model.VnfTopologyIdentifier;

public class VnfTopologyIdentifierTest {
    @Test
    public void testVnfTopologyIdentifierWithParameters() {
        VnfTopologyIdentifier vnfTopologyIdentifier = new VnfTopologyIdentifier("genericVnfType",
                                                                                "serviceType",
                                                                                "vnfName",
                                                                                "genericVnfName",
                                                                                "vnfType");
        assertEquals("genericVnfType", vnfTopologyIdentifier.getGenericVnfType());
        assertEquals("serviceType", vnfTopologyIdentifier.getServiceType());
        assertEquals("vnfName", vnfTopologyIdentifier.getVnfName());
        assertEquals("genericVnfName", vnfTopologyIdentifier.getGenericVnfName());
        assertEquals("vnfType", vnfTopologyIdentifier.getVnfType());
    }

    @Test
    public void testVnfTopologyIdentifier() {
        VnfTopologyIdentifier vnfTopologyIdentifier = new VnfTopologyIdentifier();
        vnfTopologyIdentifier.setGenericVnfType("genericVnfType");
        vnfTopologyIdentifier.setServiceType("serviceType");
        vnfTopologyIdentifier.setVnfName("vnfName");
        vnfTopologyIdentifier.setGenericVnfName("genericVnfName");
        vnfTopologyIdentifier.setVnfType("vnfType");
        assertEquals("genericVnfType", vnfTopologyIdentifier.getGenericVnfType());
        assertEquals("serviceType", vnfTopologyIdentifier.getServiceType());
        assertEquals("vnfName", vnfTopologyIdentifier.getVnfName());
        assertEquals("genericVnfName", vnfTopologyIdentifier.getGenericVnfName());
        assertEquals("vnfType", vnfTopologyIdentifier.getVnfType());
        String vnfTopologyIdentifierString = vnfTopologyIdentifier.toString();
        assertTrue(vnfTopologyIdentifierString
                   .contains("[genericVnfType=genericVnfType,serviceType=serviceType,vnfName=vnfName,"
                             + "genericVnfName=genericVnfName,vnfType=vnfType]"));
    }

    @Test
    public void testVnfTopologyIdentifierIsEqual() {
        VnfTopologyIdentifier vnfTopologyIdentifier1 = new VnfTopologyIdentifier("genericVnfType1",
                                                                                 "serviceType1",
                                                                                 "vnfName1",
                                                                                 "genericVnfName1",
                                                                                 "vnfType1");
        assertTrue(vnfTopologyIdentifier1.equals(vnfTopologyIdentifier1));
        assertTrue(vnfTopologyIdentifier1.hashCode() == 1705030683);
    }

}
