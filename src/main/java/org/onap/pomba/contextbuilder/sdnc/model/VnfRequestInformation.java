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

public class VnfRequestInformation {

    @SerializedName("vnf-name")
    @Expose
    private String vnfName;
    @SerializedName("tenant")
    @Expose
    private String tenant;
    @SerializedName("aic-cloud-region")
    @Expose
    private String aicCloudRegion;
    @SerializedName("use-preload")
    @Expose
    private String usePreload;
    @SerializedName("vnf-type")
    @Expose
    private String vnfType;
    @SerializedName("vnf-id")
    @Expose
    private String vnfId;
    @SerializedName("generic-vnf-type")
    @Expose
    private String genericVnfType;
    @SerializedName("generic-vnf-name")
    @Expose
    private String genericVnfName;
    @SerializedName("generic-vnf-id")
    @Expose
    private String genericVnfId;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public VnfRequestInformation() {
    }

    /**
     *
     * @param aicCloudRegion
     * @param vnfId
     * @param genericVnfId
     * @param usePreload
     * @param vnfName
     * @param vnfType
     * @param genericVnfName
     * @param tenant
     * @param genericVnfType
     */
    public VnfRequestInformation(String vnfName, String tenant, String aicCloudRegion, String usePreload, String vnfType, String vnfId, String genericVnfType, String genericVnfName, String genericVnfId) {
        super();
        this.vnfName = vnfName;
        this.tenant = tenant;
        this.aicCloudRegion = aicCloudRegion;
        this.usePreload = usePreload;
        this.vnfType = vnfType;
        this.vnfId = vnfId;
        this.genericVnfType = genericVnfType;
        this.genericVnfName = genericVnfName;
        this.genericVnfId = genericVnfId;
    }

    public String getVnfName() {
        return vnfName;
    }

    public void setVnfName(String vnfName) {
        this.vnfName = vnfName;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getAicCloudRegion() {
        return aicCloudRegion;
    }

    public void setAicCloudRegion(String aicCloudRegion) {
        this.aicCloudRegion = aicCloudRegion;
    }

    public String getUsePreload() {
        return usePreload;
    }

    public void setUsePreload(String usePreload) {
        this.usePreload = usePreload;
    }

    public String getVnfType() {
        return vnfType;
    }

    public void setVnfType(String vnfType) {
        this.vnfType = vnfType;
    }

    public String getVnfId() {
        return vnfId;
    }

    public void setVnfId(String vnfId) {
        this.vnfId = vnfId;
    }

    public String getGenericVnfType() {
        return genericVnfType;
    }

    public void setGenericVnfType(String genericVnfType) {
        this.genericVnfType = genericVnfType;
    }

    public String getGenericVnfName() {
        return genericVnfName;
    }

    public void setGenericVnfName(String genericVnfName) {
        this.genericVnfName = genericVnfName;
    }

    public String getGenericVnfId() {
        return genericVnfId;
    }

    public void setGenericVnfId(String genericVnfId) {
        this.genericVnfId = genericVnfId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VnfRequestInformation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vnfName");
        sb.append('=');
        sb.append(((this.vnfName == null)?NULL_STR:this.vnfName));
        sb.append(',');
        sb.append("tenant");
        sb.append('=');
        sb.append(((this.tenant == null)?NULL_STR:this.tenant));
        sb.append(',');
        sb.append("aicCloudRegion");
        sb.append('=');
        sb.append(((this.aicCloudRegion == null)?NULL_STR:this.aicCloudRegion));
        sb.append(',');
        sb.append("usePreload");
        sb.append('=');
        sb.append(((this.usePreload == null)?NULL_STR:this.usePreload));
        sb.append(',');
        sb.append("vnfType");
        sb.append('=');
        sb.append(((this.vnfType == null)?NULL_STR:this.vnfType));
        sb.append(',');
        sb.append("vnfId");
        sb.append('=');
        sb.append(((this.vnfId == null)?NULL_STR:this.vnfId));
        sb.append(',');
        sb.append("genericVnfType");
        sb.append('=');
        sb.append(((this.genericVnfType == null)?NULL_STR:this.genericVnfType));
        sb.append(',');
        sb.append("genericVnfName");
        sb.append('=');
        sb.append(((this.genericVnfName == null)?NULL_STR:this.genericVnfName));
        sb.append(',');
        sb.append("genericVnfId");
        sb.append('=');
        sb.append(((this.genericVnfId == null)?NULL_STR:this.genericVnfId));
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
        result = ((result* 31)+((this.aicCloudRegion == null)? 0 :this.aicCloudRegion.hashCode()));
        result = ((result* 31)+((this.vnfId == null)? 0 :this.vnfId.hashCode()));
        result = ((result* 31)+((this.genericVnfId == null)? 0 :this.genericVnfId.hashCode()));
        result = ((result* 31)+((this.usePreload == null)? 0 :this.usePreload.hashCode()));
        result = ((result* 31)+((this.vnfName == null)? 0 :this.vnfName.hashCode()));
        result = ((result* 31)+((this.vnfType == null)? 0 :this.vnfType.hashCode()));
        result = ((result* 31)+((this.genericVnfName == null)? 0 :this.genericVnfName.hashCode()));
        result = ((result* 31)+((this.tenant == null)? 0 :this.tenant.hashCode()));
        result = ((result* 31)+((this.genericVnfType == null)? 0 :this.genericVnfType.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VnfRequestInformation)) {
            return false;
        }
        VnfRequestInformation rhs = ((VnfRequestInformation) other);
        return ((((((((((this.aicCloudRegion == rhs.aicCloudRegion)||((this.aicCloudRegion!= null)&&this.aicCloudRegion.equals(rhs.aicCloudRegion)))&&((this.vnfId == rhs.vnfId)||((this.vnfId!= null)&&this.vnfId.equals(rhs.vnfId))))&&((this.genericVnfId == rhs.genericVnfId)||((this.genericVnfId!= null)&&this.genericVnfId.equals(rhs.genericVnfId))))&&((this.usePreload == rhs.usePreload)||((this.usePreload!= null)&&this.usePreload.equals(rhs.usePreload))))&&((this.vnfName == rhs.vnfName)||((this.vnfName!= null)&&this.vnfName.equals(rhs.vnfName))))&&((this.vnfType == rhs.vnfType)||((this.vnfType!= null)&&this.vnfType.equals(rhs.vnfType))))&&((this.genericVnfName == rhs.genericVnfName)||((this.genericVnfName!= null)&&this.genericVnfName.equals(rhs.genericVnfName))))&&((this.tenant == rhs.tenant)||((this.tenant!= null)&&this.tenant.equals(rhs.tenant))))&&((this.genericVnfType == rhs.genericVnfType)||((this.genericVnfType!= null)&&this.genericVnfType.equals(rhs.genericVnfType))));
    }

}
