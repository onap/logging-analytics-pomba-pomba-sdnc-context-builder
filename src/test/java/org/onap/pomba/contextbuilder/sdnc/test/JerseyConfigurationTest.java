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

import javax.ws.rs.client.Client;
import org.junit.Test;
import org.onap.pomba.contextbuilder.sdnc.JerseyConfiguration;

public class JerseyConfigurationTest {
    @Test
    public void testJerseyConfiguration() {
        JerseyConfiguration jerseyConfiguration = new JerseyConfiguration();
        Client client = jerseyConfiguration.jerseyClient();
        String protocol = client.getSslContext().getProtocol();
        assertEquals("TLS", protocol);
    }
}
