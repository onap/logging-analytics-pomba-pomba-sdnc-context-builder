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
import org.onap.pomba.contextbuilder.sdnc.model.AvailabilityZone;
import org.onap.pomba.contextbuilder.sdnc.model.VnfAssignments;
import org.onap.pomba.contextbuilder.sdnc.model.VnfNetwork;
import org.onap.pomba.contextbuilder.sdnc.model.VnfVm;

public class VnfAssignmentsTest {
    @Test
    public void testVnfAssignmentsWithParameters() {
        VnfAssignments vnfAssignments = new VnfAssignments(new ArrayList<VnfNetwork>(),
                                                           new ArrayList<AvailabilityZone>(),
                                                           new ArrayList<VnfVm>());
        assertEquals(new ArrayList<VnfNetwork>(), vnfAssignments.getVnfNetworks());
        assertEquals(new ArrayList<AvailabilityZone>(), vnfAssignments.getAvailabilityZones());
        assertEquals(new ArrayList<VnfVm>(), vnfAssignments.getVnfVms());
    }

    @Test
    public void testVnfAssignments() {
        VnfAssignments vnfAssignments = new VnfAssignments();
        vnfAssignments.setVnfNetworks(new ArrayList<VnfNetwork>());
        vnfAssignments.setAvailabilityZones(new ArrayList<AvailabilityZone>());
        vnfAssignments.setVnfVms(new ArrayList<VnfVm>());

        assertEquals(new ArrayList<VnfNetwork>(), vnfAssignments.getVnfNetworks());
        assertEquals(new ArrayList<AvailabilityZone>(), vnfAssignments.getAvailabilityZones());
        assertEquals(new ArrayList<VnfVm>(), vnfAssignments.getVnfVms());

        String vnfAssignmentsString = vnfAssignments.toString();
        assertTrue(vnfAssignmentsString.contains("vnfNetworks"));
    }

    @Test
    public void testVnfAssignmentsIsEqual() {
        VnfAssignments vnfAssignments1 = new VnfAssignments(new ArrayList<VnfNetwork>(),
                                                            new ArrayList<AvailabilityZone>(),
                                                            new ArrayList<VnfVm>());
        assertTrue(vnfAssignments1.equals(vnfAssignments1));
        assertTrue(vnfAssignments1.hashCode() == 30784);
    }

}
