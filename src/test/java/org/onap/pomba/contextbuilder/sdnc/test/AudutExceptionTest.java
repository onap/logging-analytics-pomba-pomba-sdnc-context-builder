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

import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response.Status;
import org.junit.Test;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditError;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;

public class AudutExceptionTest {
    @Test
    public void testAudutException() {
        AuditException auditException1 = new AuditException(AuditError.JSON_PARSE_ERROR);
        assertTrue(auditException1.getMessage().equals(AuditError.JSON_PARSE_ERROR));

        AuditException auditException2 = new AuditException(AuditError.INTERNAL_SERVER_ERROR,
                                                            Status.INTERNAL_SERVER_ERROR);
        assertTrue(auditException2.getMessage().equals(AuditError.INTERNAL_SERVER_ERROR));
        assertTrue(auditException2.getHttpStatus().equals(Status.INTERNAL_SERVER_ERROR));

        Exception ex = new Exception(AuditError.JSON_EMPTY_RESPONSE);
        AuditException auditException3 = new AuditException(AuditError.JSON_EMPTY_RESPONSE, ex);
        assertTrue(auditException3.getMessage().equals(AuditError.JSON_EMPTY_RESPONSE));
        assertTrue(auditException3.getCause().getMessage().equals(AuditError.JSON_EMPTY_RESPONSE));


    }

}
