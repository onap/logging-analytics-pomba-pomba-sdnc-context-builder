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

public class ServiceInformation {

    @SerializedName("service-id")
    @Expose
    private String serviceId;
    @SerializedName("service-type")
    @Expose
    private String serviceType;
    @SerializedName("subscriber-name")
    @Expose
    private String subscriberName;
    @SerializedName("serviceInstanceId")
    @Expose
    private String serviceInstanceId;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public ServiceInformation() {
    }

    /**
     *
     * @param serviceType
     * @param serviceId
     * @param subscriberName
     * @param serviceInstanceId
     */
    public ServiceInformation(String serviceId, String serviceType, String subscriberName, String serviceInstanceId) {
        super();
        this.serviceId = serviceId;
        this.serviceType = serviceType;
        this.subscriberName = subscriberName;
        this.serviceInstanceId = serviceInstanceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServiceInformation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("serviceId");
        sb.append('=');
        sb.append(((this.serviceId == null)?NULL_STR:this.serviceId));
        sb.append(',');
        sb.append("serviceType");
        sb.append('=');
        sb.append(((this.serviceType == null)?NULL_STR:this.serviceType));
        sb.append(',');
        sb.append("subscriberName");
        sb.append('=');
        sb.append(((this.subscriberName == null)?NULL_STR:this.subscriberName));
        sb.append(',');
        sb.append("serviceInstanceId");
        sb.append('=');
        sb.append(((this.serviceInstanceId == null)?NULL_STR:this.serviceInstanceId));
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
        result = ((result* 31)+((this.serviceType == null)? 0 :this.serviceType.hashCode()));
        result = ((result* 31)+((this.subscriberName == null)? 0 :this.subscriberName.hashCode()));
        result = ((result* 31)+((this.serviceInstanceId == null)? 0 :this.serviceInstanceId.hashCode()));
        result = ((result* 31)+((this.serviceId == null)? 0 :this.serviceId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ServiceInformation)) {
            return false;
        }
        ServiceInformation rhs = ((ServiceInformation) other);
        return (((((this.serviceType == rhs.serviceType)||((this.serviceType!= null)&&this.serviceType.equals(rhs.serviceType)))&&((this.subscriberName == rhs.subscriberName)||((this.subscriberName!= null)&&this.subscriberName.equals(rhs.subscriberName))))&&((this.serviceInstanceId == rhs.serviceInstanceId)||((this.serviceInstanceId!= null)&&this.serviceInstanceId.equals(rhs.serviceInstanceId))))&&((this.serviceId == rhs.serviceId)||((this.serviceId!= null)&&this.serviceId.equals(rhs.serviceId))));
    }

}
