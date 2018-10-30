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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VnfParameter {

    @SerializedName("vnf-parameter-name")
    @Expose
    private String vnfParameterName;
    @SerializedName("vnf-parameter-value")
    @Expose
    private String vnfParameterValue;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public VnfParameter() {
    }

    /**
     *
     * @param vnfParameterValue
     * @param vnfParameterName
     */
    public VnfParameter(String vnfParameterName, String vnfParameterValue) {
        super();
        this.vnfParameterName = vnfParameterName;
        this.vnfParameterValue = vnfParameterValue;
    }

    public String getVnfParameterName() {
        return vnfParameterName;
    }

    public void setVnfParameterName(String vnfParameterName) {
        this.vnfParameterName = vnfParameterName;
    }

    public String getVnfParameterValue() {
        return vnfParameterValue;
    }

    public void setVnfParameterValue(String vnfParameterValue) {
        this.vnfParameterValue = vnfParameterValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VnfParameter.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vnfParameterName");
        sb.append('=');
        sb.append(((this.vnfParameterName == null)?NULL_STR:this.vnfParameterName));
        sb.append(',');
        sb.append("vnfParameterValue");
        sb.append('=');
        sb.append(((this.vnfParameterValue == null)?NULL_STR:this.vnfParameterValue));
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
        result = ((result* 31)+((this.vnfParameterValue == null)? 0 :this.vnfParameterValue.hashCode()));
        result = ((result* 31)+((this.vnfParameterName == null)? 0 :this.vnfParameterName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VnfParameter)) {
            return false;
        }
        VnfParameter rhs = ((VnfParameter) other);
        return (((this.vnfParameterValue == rhs.vnfParameterValue)||((this.vnfParameterValue!= null)&&this.vnfParameterValue.equals(rhs.vnfParameterValue)))&&((this.vnfParameterName == rhs.vnfParameterName)||((this.vnfParameterName!= null)&&this.vnfParameterName.equals(rhs.vnfParameterName))));
    }

}
