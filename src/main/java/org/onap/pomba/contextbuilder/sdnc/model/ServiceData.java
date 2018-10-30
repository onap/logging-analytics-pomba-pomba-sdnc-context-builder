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

public class ServiceData {

    @SerializedName("request-information")
    @Expose
    private RequestInformation requestInformation;
    @SerializedName("service-information")
    @Expose
    private ServiceInformation serviceInformation;
    @SerializedName("vnf-id")
    @Expose
    private String vnfId;
    @SerializedName("sdnc-request-header")
    @Expose
    private SdncRequestHeader sdncRequestHeader;
    @SerializedName("vnf-request-information")
    @Expose
    private VnfRequestInformation vnfRequestInformation;
    @SerializedName("oper-status")
    @Expose
    private OperStatus operStatus;
    @SerializedName("vnf-topology-information")
    @Expose
    private VnfTopologyInformation vnfTopologyInformation;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public ServiceData() {
    }

    /**
     *
     * @param vnfRequestInformation
     * @param vnfTopologyInformation
     * @param erviceInformation
     * @param sdncRequestHeader
     * @param vnfId
     * @param requestInformation
     * @param operStatus
     */
    public ServiceData(RequestInformation requestInformation, ServiceInformation serviceInformation, String vnfId, SdncRequestHeader sdncRequestHeader, VnfRequestInformation vnfRequestInformation, OperStatus operStatus, VnfTopologyInformation vnfTopologyInformation) {
        super();
        this.requestInformation = requestInformation;
        this.serviceInformation = serviceInformation;
        this.vnfId = vnfId;
        this.sdncRequestHeader = sdncRequestHeader;
        this.vnfRequestInformation = vnfRequestInformation;
        this.operStatus = operStatus;
        this.vnfTopologyInformation = vnfTopologyInformation;
    }

    public RequestInformation getRequestInformation() {
        return requestInformation;
    }

    public void setRequestInformation(RequestInformation requestInformation) {
        this.requestInformation = requestInformation;
    }

    public ServiceInformation getServiceInformation() {
        return serviceInformation;
    }

    public void setServiceInformation(ServiceInformation serviceInformation) {
        this.serviceInformation = serviceInformation;
    }

    public String getVnfId() {
        return vnfId;
    }

    public void setVnfId(String vnfId) {
        this.vnfId = vnfId;
    }

    public SdncRequestHeader getSdncRequestHeader() {
        return sdncRequestHeader;
    }

    public void setSdncRequestHeader(SdncRequestHeader sdncRequestHeader) {
        this.sdncRequestHeader = sdncRequestHeader;
    }

    public VnfRequestInformation getVnfRequestInformation() {
        return vnfRequestInformation;
    }

    public void setVnfRequestInformation(VnfRequestInformation vnfRequestInformation) {
        this.vnfRequestInformation = vnfRequestInformation;
    }

    public OperStatus getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(OperStatus operStatus) {
        this.operStatus = operStatus;
    }

    public VnfTopologyInformation getVnfTopologyInformation() {
        return vnfTopologyInformation;
    }

    public void setVnfTopologyInformation(VnfTopologyInformation vnfTopologyInformation) {
        this.vnfTopologyInformation = vnfTopologyInformation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServiceData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("requestInformation");
        sb.append('=');
        sb.append(((this.requestInformation == null)?NULL_STR:this.requestInformation));
        sb.append(',');
        sb.append("erviceInformation");
        sb.append('=');
        sb.append(((this.serviceInformation == null)?NULL_STR:this.serviceInformation));
        sb.append(',');
        sb.append("vnfId");
        sb.append('=');
        sb.append(((this.vnfId == null)?NULL_STR:this.vnfId));
        sb.append(',');
        sb.append("sdncRequestHeader");
        sb.append('=');
        sb.append(((this.sdncRequestHeader == null)?NULL_STR:this.sdncRequestHeader));
        sb.append(',');
        sb.append("vnfRequestInformation");
        sb.append('=');
        sb.append(((this.vnfRequestInformation == null)?NULL_STR:this.vnfRequestInformation));
        sb.append(',');
        sb.append("operStatus");
        sb.append('=');
        sb.append(((this.operStatus == null)?NULL_STR:this.operStatus));
        sb.append(',');
        sb.append("vnfTopologyInformation");
        sb.append('=');
        sb.append(((this.vnfTopologyInformation == null)?NULL_STR:this.vnfTopologyInformation));
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
        result = ((result* 31)+((this.vnfRequestInformation == null)? 0 :this.vnfRequestInformation.hashCode()));
        result = ((result* 31)+((this.vnfTopologyInformation == null)? 0 :this.vnfTopologyInformation.hashCode()));
        result = ((result* 31)+((this.serviceInformation == null)? 0 :this.serviceInformation.hashCode()));
        result = ((result* 31)+((this.sdncRequestHeader == null)? 0 :this.sdncRequestHeader.hashCode()));
        result = ((result* 31)+((this.vnfId == null)? 0 :this.vnfId.hashCode()));
        result = ((result* 31)+((this.requestInformation == null)? 0 :this.requestInformation.hashCode()));
        result = ((result* 31)+((this.operStatus == null)? 0 :this.operStatus.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ServiceData)) {
            return false;
        }
        ServiceData rhs = ((ServiceData) other);
        return ((((((((this.vnfRequestInformation == rhs.vnfRequestInformation)||((this.vnfRequestInformation!= null)&&this.vnfRequestInformation.equals(rhs.vnfRequestInformation)))&&((this.vnfTopologyInformation == rhs.vnfTopologyInformation)||((this.vnfTopologyInformation!= null)&&this.vnfTopologyInformation.equals(rhs.vnfTopologyInformation))))&&((this.serviceInformation == rhs.serviceInformation)||((this.serviceInformation!= null)&&this.serviceInformation.equals(rhs.serviceInformation))))&&((this.sdncRequestHeader == rhs.sdncRequestHeader)||((this.sdncRequestHeader!= null)&&this.sdncRequestHeader.equals(rhs.sdncRequestHeader))))&&((this.vnfId == rhs.vnfId)||((this.vnfId!= null)&&this.vnfId.equals(rhs.vnfId))))&&((this.requestInformation == rhs.requestInformation)||((this.requestInformation!= null)&&this.requestInformation.equals(rhs.requestInformation))))&&((this.operStatus == rhs.operStatus)||((this.operStatus!= null)&&this.operStatus.equals(rhs.operStatus))));
    }

}
