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

public class VnfTopologyIdentifier {

    @SerializedName("generic-vnf-type")
    @Expose
    private String genericVnfType;
    @SerializedName("service-type")
    @Expose
    private String serviceType;
    @SerializedName("vnf-name")
    @Expose
    private String vnfName;
    @SerializedName("generic-vnf-name")
    @Expose
    private String genericVnfName;
    @SerializedName("vnf-type")
    @Expose
    private String vnfType;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public VnfTopologyIdentifier() {
    }

    /**
     *
     * @param serviceType
     * @param vnfName
     * @param genericVnfName
     * @param vnfType
     * @param genericVnfType
     */
    public VnfTopologyIdentifier(String genericVnfType, String serviceType, String vnfName, String genericVnfName, String vnfType) {
        super();
        this.genericVnfType = genericVnfType;
        this.serviceType = serviceType;
        this.vnfName = vnfName;
        this.genericVnfName = genericVnfName;
        this.vnfType = vnfType;
    }

    public String getGenericVnfType() {
        return genericVnfType;
    }

    public void setGenericVnfType(String genericVnfType) {
        this.genericVnfType = genericVnfType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getVnfName() {
        return vnfName;
    }

    public void setVnfName(String vnfName) {
        this.vnfName = vnfName;
    }

    public String getGenericVnfName() {
        return genericVnfName;
    }

    public void setGenericVnfName(String genericVnfName) {
        this.genericVnfName = genericVnfName;
    }

    public String getVnfType() {
        return vnfType;
    }

    public void setVnfType(String vnfType) {
        this.vnfType = vnfType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VnfTopologyIdentifier.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("genericVnfType");
        sb.append('=');
        sb.append(((this.genericVnfType == null)?NULL_STR:this.genericVnfType));
        sb.append(',');
        sb.append("serviceType");
        sb.append('=');
        sb.append(((this.serviceType == null)?NULL_STR:this.serviceType));
        sb.append(',');
        sb.append("vnfName");
        sb.append('=');
        sb.append(((this.vnfName == null)?NULL_STR:this.vnfName));
        sb.append(',');
        sb.append("genericVnfName");
        sb.append('=');
        sb.append(((this.genericVnfName == null)?NULL_STR:this.genericVnfName));
        sb.append(',');
        sb.append("vnfType");
        sb.append('=');
        sb.append(((this.vnfType == null)?NULL_STR:this.vnfType));
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
        result = ((result* 31)+((this.vnfName == null)? 0 :this.vnfName.hashCode()));
        result = ((result* 31)+((this.serviceType == null)? 0 :this.serviceType.hashCode()));
        result = ((result* 31)+((this.genericVnfName == null)? 0 :this.genericVnfName.hashCode()));
        result = ((result* 31)+((this.vnfType == null)? 0 :this.vnfType.hashCode()));
        result = ((result* 31)+((this.genericVnfType == null)? 0 :this.genericVnfType.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VnfTopologyIdentifier)) {
            return false;
        }
        VnfTopologyIdentifier rhs = ((VnfTopologyIdentifier) other);
        return ((((((this.vnfName == rhs.vnfName)||((this.vnfName!= null)&&this.vnfName.equals(rhs.vnfName)))&&((this.serviceType == rhs.serviceType)||((this.serviceType!= null)&&this.serviceType.equals(rhs.serviceType))))&&((this.genericVnfName == rhs.genericVnfName)||((this.genericVnfName!= null)&&this.genericVnfName.equals(rhs.genericVnfName))))&&((this.vnfType == rhs.vnfType)||((this.vnfType!= null)&&this.vnfType.equals(rhs.vnfType))))&&((this.genericVnfType == rhs.genericVnfType)||((this.genericVnfType!= null)&&this.genericVnfType.equals(rhs.genericVnfType))));
    }

}
