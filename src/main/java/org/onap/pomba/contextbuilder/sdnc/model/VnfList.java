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

package org.onap.pomba.contextbuilder.sdnc.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditError;
import org.onap.pomba.contextbuilder.sdnc.exception.AuditException;

public class VnfList {


    @SerializedName("vnf-list")
    @Expose
    private List<Vnf> vnfList = new ArrayList<>();

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();


    /**
     * No args constructor for use in serialization
     *
     */
    public VnfList() {
    }

    /**
     *
     * @param vnfList
     */
    public VnfList(List<Vnf> vnfList) {
        super();
        this.vnfList = vnfList;
    }

    public static VnfList fromJson(String payload) throws AuditException {
        try {
            if (payload == null || payload.isEmpty()) {
                throw new AuditException(AuditError.JSON_EMPTY_RESPONSE);
            }
            return gson.fromJson(payload, VnfList.class);
        } catch (Exception ex) {
            throw new AuditException(AuditError.JSON_PARSE_ERROR, ex);
        }
    }

    public List<Vnf> getVnfList() {
        return vnfList;
    }

    public void setVnfList(List<Vnf> vnfList) {
        this.vnfList = vnfList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VnfList.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vnfList");
        sb.append('=');
        sb.append(((this.vnfList == null)?"<null>":this.vnfList));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.vnfList == null)? 0 :this.vnfList.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VnfList)) {
            return false;
        }
        VnfList rhs = ((VnfList) other);
        return ((this.vnfList == rhs.vnfList)||((this.vnfList!= null)&&this.vnfList.equals(rhs.vnfList)));
    }

}
