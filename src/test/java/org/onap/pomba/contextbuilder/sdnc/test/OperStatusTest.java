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
import org.onap.pomba.contextbuilder.sdnc.model.OperStatus;

public class OperStatusTest {
    @Test
    public void testOperStatusWithParameters() {
        OperStatus operStatus = new OperStatus("lastAction", "orderStatus", "lastOrderStatus");
        assertEquals("lastAction", operStatus.getLastAction());
        assertEquals("orderStatus", operStatus.getOrderStatus());
        assertEquals("lastOrderStatus", operStatus.getLastOrderStatus());
    }

    @Test
    public void testOperStatus() {
        OperStatus operStatus = new OperStatus();
        operStatus.setLastAction("lastAction");
        operStatus.setOrderStatus("orderStatus");
        operStatus.setLastOrderStatus("lastOrderStatus");
        assertEquals("lastAction", operStatus.getLastAction());
        assertEquals("orderStatus", operStatus.getOrderStatus());
        assertEquals("lastOrderStatus", operStatus.getLastOrderStatus());
        String operStatusString = operStatus.toString();
        assertTrue(operStatusString
                   .contains("[lastAction=lastAction,orderStatus=orderStatus,lastOrderStatus=lastOrderStatus]"));
    }

    @Test
    public void testOperStatusIsEqual() {
        OperStatus operStatus1 = new OperStatus("lastAction1", "orderStatus1", "lastOrderStatus1");
        assertTrue(operStatus1.equals(operStatus1));
        assertTrue(operStatus1.hashCode() == 1846706130);
    }

}
