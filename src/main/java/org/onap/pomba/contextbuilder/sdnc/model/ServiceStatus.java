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

public class ServiceStatus {

    @SerializedName("response-timestamp")
    @Expose
    private String responseTimestamp;
    @SerializedName("vnfsdn-action")
    @Expose
    private String vnfsdnAction;
    @SerializedName("rpc-action")
    @Expose
    private String rpcAction;
    @SerializedName("rpc-name")
    @Expose
    private String rpcName;
    @SerializedName("response-code")
    @Expose
    private String responseCode;
    @SerializedName("final-indicator")
    @Expose
    private String finalIndicator;
    @SerializedName("request-status")
    @Expose
    private String requestStatus;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public ServiceStatus() {
    }

    /**
     *
     * @param requestStatus
     * @param responseTimestamp
     * @param responseCode
     * @param vnfsdnAction
     * @param rpcAction
     * @param rpcName
     * @param finalIndicator
     */
    public ServiceStatus(String responseTimestamp, String vnfsdnAction, String rpcAction, String rpcName, String responseCode, String finalIndicator, String requestStatus) {
        super();
        this.responseTimestamp = responseTimestamp;
        this.vnfsdnAction = vnfsdnAction;
        this.rpcAction = rpcAction;
        this.rpcName = rpcName;
        this.responseCode = responseCode;
        this.finalIndicator = finalIndicator;
        this.requestStatus = requestStatus;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getVnfsdnAction() {
        return vnfsdnAction;
    }

    public void setVnfsdnAction(String vnfsdnAction) {
        this.vnfsdnAction = vnfsdnAction;
    }

    public String getRpcAction() {
        return rpcAction;
    }

    public void setRpcAction(String rpcAction) {
        this.rpcAction = rpcAction;
    }

    public String getRpcName() {
        return rpcName;
    }

    public void setRpcName(String rpcName) {
        this.rpcName = rpcName;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getFinalIndicator() {
        return finalIndicator;
    }

    public void setFinalIndicator(String finalIndicator) {
        this.finalIndicator = finalIndicator;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServiceStatus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("responseTimestamp");
        sb.append('=');
        sb.append(((this.responseTimestamp == null)?NULL_STR:this.responseTimestamp));
        sb.append(',');
        sb.append("vnfsdnAction");
        sb.append('=');
        sb.append(((this.vnfsdnAction == null)?NULL_STR:this.vnfsdnAction));
        sb.append(',');
        sb.append("rpcAction");
        sb.append('=');
        sb.append(((this.rpcAction == null)?NULL_STR:this.rpcAction));
        sb.append(',');
        sb.append("rpcName");
        sb.append('=');
        sb.append(((this.rpcName == null)?NULL_STR:this.rpcName));
        sb.append(',');
        sb.append("responseCode");
        sb.append('=');
        sb.append(((this.responseCode == null)?NULL_STR:this.responseCode));
        sb.append(',');
        sb.append("finalIndicator");
        sb.append('=');
        sb.append(((this.finalIndicator == null)?NULL_STR:this.finalIndicator));
        sb.append(',');
        sb.append("requestStatus");
        sb.append('=');
        sb.append(((this.requestStatus == null)?NULL_STR:this.requestStatus));
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
        result = ((result* 31)+((this.requestStatus == null)? 0 :this.requestStatus.hashCode()));
        result = ((result* 31)+((this.responseTimestamp == null)? 0 :this.responseTimestamp.hashCode()));
        result = ((result* 31)+((this.responseCode == null)? 0 :this.responseCode.hashCode()));
        result = ((result* 31)+((this.vnfsdnAction == null)? 0 :this.vnfsdnAction.hashCode()));
        result = ((result* 31)+((this.rpcAction == null)? 0 :this.rpcAction.hashCode()));
        result = ((result* 31)+((this.rpcName == null)? 0 :this.rpcName.hashCode()));
        result = ((result* 31)+((this.finalIndicator == null)? 0 :this.finalIndicator.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ServiceStatus)) {
            return false;
        }
        ServiceStatus rhs = ((ServiceStatus) other);
        return ((((((((this.requestStatus == rhs.requestStatus)||((this.requestStatus!= null)&&this.requestStatus.equals(rhs.requestStatus)))&&((this.responseTimestamp == rhs.responseTimestamp)||((this.responseTimestamp!= null)&&this.responseTimestamp.equals(rhs.responseTimestamp))))&&((this.responseCode == rhs.responseCode)||((this.responseCode!= null)&&this.responseCode.equals(rhs.responseCode))))&&((this.vnfsdnAction == rhs.vnfsdnAction)||((this.vnfsdnAction!= null)&&this.vnfsdnAction.equals(rhs.vnfsdnAction))))&&((this.rpcAction == rhs.rpcAction)||((this.rpcAction!= null)&&this.rpcAction.equals(rhs.rpcAction))))&&((this.rpcName == rhs.rpcName)||((this.rpcName!= null)&&this.rpcName.equals(rhs.rpcName))))&&((this.finalIndicator == rhs.finalIndicator)||((this.finalIndicator!= null)&&this.finalIndicator.equals(rhs.finalIndicator))));
    }

}
