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

package org.onap.pomba.contextbuilder.sdnc.unittest.service;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.jknack.handlebars.internal.Files;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.pomba.contextbuilder.sdnc.Application;
import org.onap.pomba.contextbuilder.sdnc.service.rs.RestService;
import org.onap.pomba.contextbuilder.sdnc.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@WebAppConfiguration
@SpringBootTest (classes = Application.class)
@TestPropertySource(properties = {"sdnc.serviceName=localhost",
                                  "sdnc.servicePort=30202",
                                  "aai.httpProtocol=http",
                                  "aai.serviceName=localhost",
                                  "aai.servicePort=9808"})

public class SdncContextBuilderTest {

    private String testRestHeaders = "testRestHeaders";
    private String aaiGenericVnfPath = "/aai/v11/network/generic-vnfs/generic-vnf/";
    private String genericResourcePath = "/restconf/config/GENERIC-RESOURCE-API:services/service/";
    private String portMirrorConfigurationsResourcePath = "/restconf/config/GENERIC-RESOURCE-API:port-mirror-configurations/port-mirror-configuration/3c368d8d-efda-49d4-bbb5-a6465330a230/configuration-data/configuration-operation-information/port-mirror-configuration-request-input";
    private String vnfPath = "/restconf/config/VNF-API:vnfs/vnf-list/";
    private String serviceInstanceIdVfw = "7d518257-49bd-40ac-8d17-017a726ec12a"; // customerData.json
    private String serviceInstanceIdVcpe = "68352304-7bba-4609-8551-0d0b819376c3"; // queryNodeDataVcpe.json
    private String genericVnfId = "d94daff6-7d5b-4d2e-bc99-c9af0754b59d";
    private String moduleId = "2c3f8902-f278-4ee3-93cf-9d2364cbafca";

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

    @Autowired
    RestService service;
    @Autowired
    private String sdncCtxBuilderBasicAuthorization;
    //AAI related
    @Autowired
    private String aaiPathToServiceInstanceQuery;
    @Rule
    public WireMockRule aaiEnricherRule = new WireMockRule(wireMockConfig().port(9808));

    @Rule
    public WireMockRule sdncRule = new WireMockRule(wireMockConfig().port(30202));

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRestHeaders() throws Exception {
        HttpHeaders mockHttpHeaders = mock(HttpHeaders.class);

        // Test with No Partner Name
        final MultivaluedMap<String, String> multivaluedMapImpl = buildHeaders(
                null, testRestHeaders, sdncCtxBuilderBasicAuthorization);

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl);
        Response response = service.getContext(httpServletRequest, mockHttpHeaders, serviceInstanceIdVfw);
        assertTrue(response.getEntity().toString().contains("Missing header parameter: " + RestUtil.FROM_APP_ID));

        // Test with no Authorization
        final MultivaluedMap<String, String> multivaluedMapImpl1 = buildHeaders(
                "test2", testRestHeaders, null);

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl1);
        response = service.getContext(httpServletRequest, mockHttpHeaders, serviceInstanceIdVfw);
        assertTrue(response.getEntity().toString().contains("Missing header parameter: " + RestUtil.AUTHORIZATION));

        // Test with garbage Authorization
        final MultivaluedMap<String, String> multivaluedMapImpl2 = buildHeaders(
                "test3", testRestHeaders, "garbage");

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl2);
        response = service.getContext(httpServletRequest, mockHttpHeaders, serviceInstanceIdVfw);
        assertTrue(response.getEntity().toString().contains("Failed Basic " + RestUtil.AUTHORIZATION));
    }

    @Test
    public void testRestParameterServiceInstanceId() throws Exception {
        HttpHeaders mockHttpHeaders = mock(HttpHeaders.class);
        final MultivaluedMap<String, String> multivaluedMapImpl = buildHeaders(
                "testRestParameterServiceInstanceId", "test1", sdncCtxBuilderBasicAuthorization);

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl);
        Response response = service.getContext(httpServletRequest, mockHttpHeaders, null);
        assertTrue(response
                   .getEntity()
                   .toString()
                   .contains("Invalid request URL, missing parameter: serviceInstanceId"));
    }

    @Test
    public void testVerifySdncContextBuilderVnfApi() throws Exception {

        HttpHeaders mockHttpHeaders = mock(HttpHeaders.class);
        final MultivaluedMap<String, String> multivaluedMapImpl = buildHeaders(
                "testVerifyServiceDecomposition", "test1", sdncCtxBuilderBasicAuthorization);
        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl);

        String serviceInstanceUrl = MessageFormat.format(aaiPathToServiceInstanceQuery, serviceInstanceIdVfw);
        addResponse(serviceInstanceUrl, "junit/serviceInstancevfw.json", aaiEnricherRule);

        String vnfApiUrl = vnfPath + moduleId;
        addResponse(vnfApiUrl, "junit/vnfApiResponse.json", sdncRule);

        String genericVnfUrl = aaiGenericVnfPath + genericVnfId + "?depth=all";
        addResponse(genericVnfUrl, "junit/genericVnf.json", aaiEnricherRule);

        Response response = this.service.getContext(httpServletRequest, mockHttpHeaders, serviceInstanceIdVfw);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testVerifySdncContextBuilderGenericVnf() throws Exception {

        HttpHeaders mockHttpHeaders = mock(HttpHeaders.class);
        final MultivaluedMap<String, String> multivaluedMapImpl = buildHeaders(
                "testVerifyServiceDecomposition", "test1", sdncCtxBuilderBasicAuthorization);
        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl);
       
        String serviceInstanceUrl = MessageFormat.format(aaiPathToServiceInstanceQuery, serviceInstanceIdVcpe);
        addResponse(serviceInstanceUrl, "junit/serviceInstance.json", aaiEnricherRule);

        String genericVnfUrl = aaiGenericVnfPath + genericVnfId + "?depth=all";
        addResponse(genericVnfUrl, "junit/genericVnf.json", aaiEnricherRule);
        
        String urlStr = genericResourcePath + serviceInstanceIdVcpe;
        addResponse(urlStr, "junit/sdncGenericResponse.json", sdncRule);

        addResponse(portMirrorConfigurationsResourcePath, "junit/portMirrorConfigurationsResponse.json", sdncRule);

        Response response = this.service.getContext(httpServletRequest, mockHttpHeaders, serviceInstanceIdVcpe);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testVerifySdncContextBuilderNoTransactionId() throws Exception {

        HttpHeaders mockHttpHeaders = mock(HttpHeaders.class);
        final MultivaluedMap<String, String> multivaluedMapImpl = buildHeaders(
                "testVerifyServiceDecomposition", null, sdncCtxBuilderBasicAuthorization);
        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl);
       
        String serviceInstanceUrl = MessageFormat.format(aaiPathToServiceInstanceQuery, serviceInstanceIdVcpe);
        addResponse(serviceInstanceUrl, "junit/serviceInstance.json", aaiEnricherRule);

        String genericVnfUrl = aaiGenericVnfPath + genericVnfId + "?depth=all";
        addResponse(genericVnfUrl, "junit/genericVnf.json", aaiEnricherRule);
        
        String urlStr = genericResourcePath + serviceInstanceIdVcpe;
        addResponse(urlStr, "junit/sdncGenericResponse.json", sdncRule);

        addResponse(portMirrorConfigurationsResourcePath, "junit/portMirrorConfigurationsResponse.json", sdncRule);

        Response response = this.service.getContext(httpServletRequest, mockHttpHeaders, serviceInstanceIdVcpe);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    private void addResponse(String url, String responseFile, WireMockRule thisMock) throws IOException {
        File file = new File(ClassLoader.getSystemResource(responseFile).getFile());
        String payload = Files.read(file, Charset.defaultCharset());
        thisMock.stubFor(get(url).willReturn(okJson(payload)));
    }

    private static MultivaluedMap<String, String> buildHeaders(
            String partnerName, String transactionId, String authorization) {

        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.put(RestUtil.FROM_APP_ID, Collections.singletonList(partnerName));
        headers.put(RestUtil.TRANSACTION_ID, Collections.singletonList(transactionId));
        if (null != authorization) {
            headers.put(RestUtil.AUTHORIZATION, Collections.singletonList(authorization));
        }
        return headers;
    }

}
