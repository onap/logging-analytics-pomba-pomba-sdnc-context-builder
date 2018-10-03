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
import javax.ws.rs.ApplicationPath;
import org.eclipse.jetty.util.security.Password;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@ApplicationPath("/")
public class SdncConfiguration {

    @Value("${sdnc.serviceName}")
    private String serviceName;

    @Value("${sdnc.servicePort}")
    private String servicePort;

    @Value("${sdnc.httpProtocol}")
    private String httpProtocol;

    @Value("${sdnc.user}")
    private String user;

    @Value("${sdnc.password}")
    private String password;

    @Value("${sdnc.connectionTimeout}")
    private Integer connectionTimeout;

    @Value("${sdnc.readTimeout}")
    private Integer readTimeout;

    @Value("${sdnc.genericResourcePath}")
    private String genericResourcePath;

    @Bean(name="sdncBaseUrl")
    public String getURL() {
        String url= httpProtocol + "://" + serviceName + ":" + servicePort;
        return url;
    }

    @Bean(name="sdncGenericResourcePath")
    public String getgenericResourcePath() {
        return genericResourcePath;
    }

    @Bean(name = "sdncBasicAuthorization")
    public String getSdncBasicAuth() {
        String auth = this.user + ":"+ Password.deobfuscate(this.password);
        return ("Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
    }


}
