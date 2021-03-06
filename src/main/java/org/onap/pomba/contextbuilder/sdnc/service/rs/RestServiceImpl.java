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

package org.onap.pomba.contextbuilder.sdnc.service.rs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;
import org.onap.pomba.contextbuilder.sdnc.service.SpringService;
import org.onap.pomba.contextbuilder.sdnc.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RestServiceImpl implements RestService {
    private static final String EMPTY_JSON_STRING = "{}";

    @Autowired
    private SpringService service;

    @Override
    public Response getContext(HttpServletRequest request, HttpHeaders headers, String serviceInstanceId) {

        Response response = null;
        ModelContext sdncContext = null;
        Gson gson = new GsonBuilder().create();
        try {
            // Validate URL parameters
            RestUtil.validateUrl(serviceInstanceId);

            // Validate Headers and extract Partner Name
            String partnerName = RestUtil.validateHeader(headers, service.getSdncAuthoriztion());

            // Keep the same transaction id for logging purposeString transactionId
            String transactionId = RestUtil.extractTranactionIdHeader(headers);

            sdncContext = service.getContext(request, serviceInstanceId, transactionId, partnerName);

            if (sdncContext == null) {
                // Return empty JSON
                response = Response.ok().entity(EMPTY_JSON_STRING).build();
            } else {
                response = Response.ok().entity(gson.toJson(sdncContext)).build();
            }
        } catch (AuditException ce) {
            if (ce.getHttpStatus() != null) {
                response = Response.status(ce.getHttpStatus()).entity(ce.getMessage()).build();
            } else {
                // No response received, could be the cases of network issue: i.e. unreachable
                // host
                response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(ce.getMessage()).build();
            }
        } catch (Exception e) {
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @Override
    public Response getV1Context(HttpServletRequest request, HttpHeaders headers, String serviceInstanceId) {
        return getContext(request, headers, serviceInstanceId);
    }


}
