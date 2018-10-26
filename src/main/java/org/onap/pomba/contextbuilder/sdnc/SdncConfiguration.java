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
import org.onap.aai.restclient.client.RestClient;
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

    @Value("${sdnc.vnfPath}")
    private String vnfPath;

    @Bean(name="sdncBaseUrl")
    public String getURL() {
        return httpProtocol + "://" + serviceName + ":" + servicePort;
    }

    @Bean(name="sdncGenericResourcePath")
    public String getGenericResourcePath() {
        return genericResourcePath;
    }

    @Bean(name="sdncVnfPath")
    public String getVnfPath() {
        return vnfPath;
    }

    @Bean(name = "sdncBasicAuthorization")
    public String getSdncBasicAuth() {
        String auth = this.user + ":"+ Password.deobfuscate(this.password);
        return ("Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
    }

    /* AAI related interfaces */
    @Value("${aai.serviceName}")
    private String aaiHost;
    @Value("${aai.servicePort}")
    private String aaiPort;
    @Value("${aai.username}")
    private String aaiUsername;
    @Value("${aai.password}")
    private String aaiPassword;
    @Value("${aai.httpProtocol}")
    private String aaiHttpProtocol;
    @Value("${aai.connectionTimeout}")
    private Integer aaiConnectionTimeout;
    @Value("${aai.readTimeout}")
    private Integer aaiReadTimeout;

    @Value("${aai.http.userId}")
    private String aaiHttpUserId;

    @Value("${aai.http.password}")
    private String aaiHttpPassword;

    @Value("${aai.searchNodeQuery}")
    private String aaiSearchNodeQuery;

    @Value("${aai.customerQuery}")
    private String aaiCustomerQuery;

    @Value("${aai.serviceInstancePath}")
    private String serviceInstancePath;


    @Bean(name="aaiHttpBasicAuthorization")
    public String getHttpBasicAuth() {
        String auth = new String(this.aaiHttpUserId + ":" + Password.deobfuscate(this.aaiHttpPassword));

        String encodedAuth =  Base64.getEncoder().encodeToString(auth.getBytes());
        return ("Basic " + encodedAuth);
    }

    @Bean(name="aaiBasicAuthorization")
    public String getAAIBasicAuth() {
        String auth = new String(this.aaiUsername + ":" + Password.deobfuscate(this.aaiPassword));
        String encodedAuth =  Base64.getEncoder().encodeToString(auth.getBytes());
        return ("Basic " + encodedAuth);
    }

    @Bean(name="aaiClient")
    public RestClient restClient() {
        RestClient restClient = new RestClient();
        restClient.validateServerHostname(false).validateServerCertChain(false).connectTimeoutMs(aaiConnectionTimeout).readTimeoutMs(aaiReadTimeout);
        restClient.basicAuthUsername(aaiUsername);
        restClient.basicAuthPassword(Password.deobfuscate(aaiPassword));
        return restClient;
    }

    @Bean(name="aaiBaseUrl")
    public String getAaiURL() {
        return httpProtocol + "://" + aaiHost + ":" + aaiPort;

    }

    @Bean(name="aaiPathToSearchNodeQuery")
    public String getAaiPathToSearchNodeQuery() {
        return aaiSearchNodeQuery.trim();
    }

    @Bean(name="aaiPathToCustomerQuery")
    public String getAaiPathToCustomerQuery() {
        return aaiCustomerQuery.trim();
    }

    @Bean(name="aaiServiceInstancePath")
    public String getserviceInstancePathL() {
        return serviceInstancePath;
    }

}
