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
import org.onap.pomba.contextbuilder.sdnc.model.Pserver;

public class PserverTest {
    @Test
    public void testPserverWithParameters() {
        Pserver pserver = new Pserver("hostname");
        assertEquals("hostname", pserver.getHostname());
    }

    @Test
    public void testPserver() {
        Pserver pserver = new Pserver();
        pserver.setHostname("hostname");
        assertEquals("hostname", pserver.getHostname());
        String pserverString = pserver.toString();
        assertTrue(pserverString.contains("hostname"));
    }

    @Test
    public void testPserverIsEqual() {
        Pserver pserver1 = new Pserver("hostname");
        assertTrue(pserver1.equals(pserver1));
        assertTrue(pserver1.hashCode() == -299803566);
    }

}
