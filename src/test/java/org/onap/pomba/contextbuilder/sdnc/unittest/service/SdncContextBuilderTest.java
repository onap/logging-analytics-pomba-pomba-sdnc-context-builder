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
import java.util.Collections;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.onap.aai.restclient.client.RestClient;
import org.onap.pomba.contextbuilder.sdnc.Application;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import org.onap.pomba.contextbuilder.sdnc.model.ServiceEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@WebAppConfiguration
@SpringBootTest (classes = Application.class)
@TestPropertySource(properties = {"sdnc.host=localhost", "sdnc.port=30202",
        "aai.httpProtocol=http", "aai.serviceName=localhost", "aai.servicePort=9808"})
public class SdncContextBuilderTest {

    private String serviceInstanceId = "7d518257-49bd-40ac-8d17-017a726ec12a"; //match to the test data in junit/queryNodeData-1.json
    private String testRestHeaders = "testRestHeaders";
    private String customerId = "DemoCust_651800ed-2a3c-45f5-b920-85c1ed155fc2"; // match to queryNodeData-1.json and customerData-1.json
   @Autowired
    RestService service;
    @Autowired
    private String sdncCtxBuilderBasicAuthorization;

    //AAI related
    @Autowired
    private String aaiBasicAuthorization;
    @Autowired
    private RestClient aaiClient;
    @Autowired
    private String aaiBaseUrl;
    @Autowired
    private String aaiPathToSearchNodeQuery;
    @Autowired
    private String aaiPathToCustomerQuery;
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
        HttpHeaders mockHttpHeaders = mock( HttpHeaders.class);

        // Test with No Partner Name
        final MultivaluedMap<String, String> multivaluedMapImpl = buildHeaders(
                null, testRestHeaders, sdncCtxBuilderBasicAuthorization);

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl);
        Response response = service.getContext(mockHttpHeaders, serviceInstanceId);
        assertTrue(response.getEntity().toString().contains("Missing header parameter: " + RestUtil.FROM_APP_ID));

        // Test with no Authorization
        final MultivaluedMap<String, String> multivaluedMapImpl1 = buildHeaders(
                "test2", testRestHeaders, null);

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl1);
        response = service.getContext(mockHttpHeaders, serviceInstanceId);
        assertTrue(response.getEntity().toString().contains("Missing header parameter: " + RestUtil.AUTHORIZATION));

        // Test with garbage Authorization
        final MultivaluedMap<String, String> multivaluedMapImpl2 = buildHeaders(
                "test2", testRestHeaders, "garbage");

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl2);
        response = service.getContext(mockHttpHeaders, serviceInstanceId);
        assertTrue(response.getEntity().toString().contains("Failed Basic " + RestUtil.AUTHORIZATION));
    }

    @Test
    public void testRestParameterServiceInstanceId() throws Exception {
        HttpHeaders mockHttpHeaders = mock( HttpHeaders.class);
        final MultivaluedMap<String, String> multivaluedMapImpl = buildHeaders(
                "testRestParameterServiceInstanceId", "test1", sdncCtxBuilderBasicAuthorization);

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl);
        Response response = service.getContext(mockHttpHeaders, null);
        assertTrue(response.getEntity().toString().contains("Invalid request URL, missing parameter: serviceInstanceId"));
    }

    @Test
    public void testVerifySdncContextBuilder() throws Exception {

        String urlStr = "/restconf/config/GENERIC-RESOURCE-API:services/service/" + serviceInstanceId;
        File file = new File(ClassLoader.getSystemResource("sdncResponse.json").getFile());
        String sdResonse = Files.read(file);
        this.sdncRule.stubFor(get(urlStr).willReturn(okJson(sdResonse)));

        HttpHeaders mockHttpHeaders = mock( HttpHeaders.class);
        final MultivaluedMap<String, String> multivaluedMapImpl = buildHeaders(
                "testVerifyServiceDecomposition", "test1", sdncCtxBuilderBasicAuthorization);

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl);

        String queryNodeUrl = aaiPathToSearchNodeQuery + serviceInstanceId;
        addResponse(queryNodeUrl, "junit/queryNodeData-1.json", aaiEnricherRule);
        String customerUrl = aaiPathToCustomerQuery + customerId;
        addResponse(customerUrl, "junit/customerData-1.json", aaiEnricherRule);

        Response response = this.service.getContext(mockHttpHeaders, serviceInstanceId);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        // Try again with no transcactionId
        final MultivaluedMap<String, String> multivaluedMapImpl1 = buildHeaders(
                "testVerifyServiceDecomposition", null, sdncCtxBuilderBasicAuthorization);

        when(mockHttpHeaders.getRequestHeaders()).thenReturn(multivaluedMapImpl1);
        response = this.service.getContext(mockHttpHeaders, serviceInstanceId);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
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

    //AAI related

    @Test
    public void testObtainResouceLinkBasedOnServiceInstanceFromAAI() throws Exception {
        String transactionId = UUID.randomUUID().toString();
        String queryNodeUrl = aaiPathToSearchNodeQuery + serviceInstanceId;
        addResponse(queryNodeUrl, "junit/queryNodeData-1.json", aaiEnricherRule);
        String customerUrl = aaiPathToCustomerQuery + customerId;
        addResponse(customerUrl, "junit/customerData-1.json", aaiEnricherRule);

        ServiceEntity serviceEntity = RestUtil.getServiceEntity(aaiClient,aaiBaseUrl,aaiBasicAuthorization,  aaiPathToSearchNodeQuery, aaiPathToCustomerQuery, serviceInstanceId, transactionId);

        assertEquals(serviceInstanceId, serviceEntity.getServiceInstanceId());
        assertEquals("vFW", serviceEntity.getServiceType());   // serviceType is hard-coded in queryNodeData-1.json
        assertEquals(customerId, serviceEntity.getCustomerId());   // customerId is hard-coded in queryNodeData-1.json
        assertEquals("DemoCust_651800ed-2a3c-45f5-b920-85c1ed155fc2", serviceEntity.getCustomerName());   // customerName is hard-coded in queryNodeData-1.json
        assertEquals("CUST", serviceEntity.getCustomerType()); //customerType is hard-coded in customerData-1.json
    }

    @Test
    public void testObtainResouceLinkBasedOnServiceInstanceFromAAI_nullResourceLink() throws Exception {
        String transactionId = UUID.randomUUID().toString();
        String queryNodeUrl = aaiPathToSearchNodeQuery + serviceInstanceId;
        addResponse(queryNodeUrl, "junit/queryNodeData-nullResourceLink.json", aaiEnricherRule);

        try {
            RestUtil.getServiceEntity(aaiClient,aaiBaseUrl,aaiBasicAuthorization,  aaiPathToSearchNodeQuery, aaiPathToCustomerQuery, serviceInstanceId, transactionId);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("JSONObject[\"resource-link\"] not found"));
        }
    }

    @Test
    public void testObtainResouceLinkBasedOnServiceInstanceFromAAI_nullCustomerType() throws Exception {
        String transactionId = UUID.randomUUID().toString();
        String queryNodeUrl = aaiPathToSearchNodeQuery + serviceInstanceId;
        addResponse(queryNodeUrl, "junit/queryNodeData-1.json", aaiEnricherRule);
        String customerUrl = aaiPathToCustomerQuery + customerId;
        addResponse(customerUrl, "junit/customerData-CustomerIdNotFound.json", aaiEnricherRule);

        try {
            RestUtil.getServiceEntity(aaiClient,aaiBaseUrl,aaiBasicAuthorization,  aaiPathToSearchNodeQuery, aaiPathToCustomerQuery, serviceInstanceId, transactionId);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Customer ID cannot be found from AAI"));
        }
    }

    private void addResponse(String path, String classpathResource, WireMockRule thisMock) throws IOException {
        String payload = readFully(ClassLoader.getSystemResourceAsStream(classpathResource));
        thisMock.stubFor(get(path).willReturn(okJson(payload)));
    }

    private String readFully(InputStream in) throws IOException {
        char[] cbuf = new char[1024];
        StringBuilder content = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(in, "UTF-8")) {
            int count;
            while ((count = reader.read(cbuf)) >= 0) {
                content.append(cbuf, 0, count);
            }
        }
        return content.toString();
    }
}
