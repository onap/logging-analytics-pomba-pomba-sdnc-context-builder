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

public class Vnf {

    @SerializedName("vnf-id")
    @Expose
    private String vnfId;
    @SerializedName("service-status")
    @Expose
    private ServiceStatus serviceStatus;
    @SerializedName("service-data")
    @Expose
    private ServiceData serviceData;


    /**
     * No args constructor for use in serialization
     *
     */
    public Vnf() {
    }

    /**
     *
     * @param vnfId
     * @param serviceStatus
     * @param serviceData
     */
    public Vnf(String vnfId, ServiceStatus serviceStatus, ServiceData serviceData) {
        super();
        this.vnfId = vnfId;
        this.serviceStatus = serviceStatus;
        this.serviceData = serviceData;
    }

    public String getVnfId() {
        return vnfId;
    }

    public void setVnfId(String vnfId) {
        this.vnfId = vnfId;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public ServiceData getServiceData() {
        return serviceData;
    }

    public void setServiceData(ServiceData serviceData) {
        this.serviceData = serviceData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Vnf.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vnfId");
        sb.append('=');
        sb.append(((this.vnfId == null)?"<null>":this.vnfId));
        sb.append(',');
        sb.append("serviceStatus");
        sb.append('=');
        sb.append(((this.serviceStatus == null)?"<null>":this.serviceStatus));
        sb.append(',');
        sb.append("serviceData");
        sb.append('=');
        sb.append(((this.serviceData == null)?"<null>":this.serviceData));
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
        result = ((result* 31)+((this.serviceStatus == null)? 0 :this.serviceStatus.hashCode()));
        result = ((result* 31)+((this.serviceData == null)? 0 :this.serviceData.hashCode()));
        result = ((result* 31)+((this.vnfId == null)? 0 :this.vnfId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Vnf) == false) {
            return false;
        }
        Vnf rhs = ((Vnf) other);
        return ((((this.serviceStatus == rhs.serviceStatus)||((this.serviceStatus!= null)&&this.serviceStatus.equals(rhs.serviceStatus)))&&((this.serviceData == rhs.serviceData)||((this.serviceData!= null)&&this.serviceData.equals(rhs.serviceData))))&&((this.vnfId == rhs.vnfId)||((this.vnfId!= null)&&this.vnfId.equals(rhs.vnfId))));
    }

}
