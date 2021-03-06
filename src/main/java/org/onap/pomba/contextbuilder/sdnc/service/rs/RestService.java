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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.onap.pomba.common.datatypes.ModelContext;


@Api
@Path("/")
@Produces({MediaType.APPLICATION_JSON})
public interface RestService {

    @GET
    @Path("service/context")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(
            value = "Respond SDNC Context Model Data",
            notes = "Returns a JSON object which represents the SDNC Context model data",
            response = ModelContext.class
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Service not available"),
                    @ApiResponse(code = 500, message = "Unexpected Runtime error")
                    })
    public Response getContext(@Context HttpServletRequest request,
                               @Context HttpHeaders headers,
                               @QueryParam("serviceInstanceId") String serviceInstanceId
            );

    @GET
    @Path("{version:[vV][1-5]}/service/context")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(
            value = "Respond SDNC Context v1 - v5 Model Data",
            notes = "Returns a JSON object which represents the SDNC Context V1 model data",
            response = Response.class
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Service not available"),
                    @ApiResponse(code = 500, message = "Unexpected Runtime error")
                    })
    public Response getV1Context(@Context HttpServletRequest request,
                               @Context HttpHeaders headers,
                               @QueryParam("serviceInstanceId") String serviceInstanceId
            );

}
