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
import org.onap.pomba.contextbuilder.sdnc.model.VnfAssignments;
import org.onap.pomba.contextbuilder.sdnc.model.VnfParameter;
import org.onap.pomba.contextbuilder.sdnc.model.VnfTopologyIdentifier;
import org.onap.pomba.contextbuilder.sdnc.model.VnfTopologyInformation;

public class VnfTopologyInformationTest {
    @Test
    public void testVnfTopologyInformationWithParameters() {
        VnfTopologyInformation vnfTopologyInformation = new VnfTopologyInformation(new VnfTopologyIdentifier(),
                                                                                   new VnfAssignments(),
                                                                                   new ArrayList<VnfParameter>());
        assertEquals(new VnfTopologyIdentifier(), vnfTopologyInformation.getVnfTopologyIdentifier());
        assertEquals(new VnfAssignments(), vnfTopologyInformation.getVnfAssignments());
        assertEquals(new ArrayList<VnfParameter>(), vnfTopologyInformation.getVnfParameters());
    }

    @Test
    public void testVnfTopologyInformation() {
        VnfTopologyInformation vnfTopologyInformation = new VnfTopologyInformation();
        vnfTopologyInformation.setVnfTopologyIdentifier(new VnfTopologyIdentifier());
        vnfTopologyInformation.setVnfAssignments(new VnfAssignments());
        vnfTopologyInformation.setVnfParameters(new ArrayList<VnfParameter>());
        assertEquals(new VnfTopologyIdentifier(), vnfTopologyInformation.getVnfTopologyIdentifier());
        assertEquals(new VnfAssignments(), vnfTopologyInformation.getVnfAssignments());
        assertEquals(new ArrayList<VnfParameter>(), vnfTopologyInformation.getVnfParameters());
    }

    @Test
    public void testVnfTopologyInformationIsEqual() {
        VnfTopologyInformation vnfTopologyInformation1 = new VnfTopologyInformation(new VnfTopologyIdentifier(),
                                                                                    new VnfAssignments(),
                                                                                    new ArrayList<VnfParameter>());
        assertTrue(vnfTopologyInformation1.equals(vnfTopologyInformation1));
        assertTrue(vnfTopologyInformation1.hashCode() == 917116897);
    }

}
