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

package org.onap.pomba.contextbuilder.sdnc;

import java.util.Base64;
import org.eclipse.jetty.util.security.Password;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SdncCtxbConfiguration {
    @Value("${sdncCtxBuilder.userId:admin}")
    private String sdncCtxBuilderUserId;

    @Value("${sdncCtxBuilder.password:OBF:1u2a1toa1w8v1tok1u30}")
    private String sdncCtxBuilderPassword;

    @Bean(name = "sdncCtxBuilderBasicAuthorization")
    public String getSdncBasicAuth() {
        String auth = this.sdncCtxBuilderUserId + ":"
                + Password.deobfuscate(this.sdncCtxBuilderPassword);
        return ("Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
    }
}
