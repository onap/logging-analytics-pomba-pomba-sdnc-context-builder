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

public class SdncRequestHeader {

    @SerializedName("svc-notification-url")
    @Expose
    private String svcNotificationUrl;
    @SerializedName("svc-request-id")
    @Expose
    private String svcRequestId;
    @SerializedName("svc-action")
    @Expose
    private String svcAction;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public SdncRequestHeader() {
    }

    /**
     *
     * @param svcRequestId
     * @param svcNotificationUrl
     * @param svcAction
     */
    public SdncRequestHeader(String svcNotificationUrl, String svcRequestId, String svcAction) {
        super();
        this.svcNotificationUrl = svcNotificationUrl;
        this.svcRequestId = svcRequestId;
        this.svcAction = svcAction;
    }

    public String getSvcNotificationUrl() {
        return svcNotificationUrl;
    }

    public void setSvcNotificationUrl(String svcNotificationUrl) {
        this.svcNotificationUrl = svcNotificationUrl;
    }

    public String getSvcRequestId() {
        return svcRequestId;
    }

    public void setSvcRequestId(String svcRequestId) {
        this.svcRequestId = svcRequestId;
    }

    public String getSvcAction() {
        return svcAction;
    }

    public void setSvcAction(String svcAction) {
        this.svcAction = svcAction;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SdncRequestHeader.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("svcNotificationUrl");
        sb.append('=');
        sb.append(((this.svcNotificationUrl == null)?NULL_STR:this.svcNotificationUrl));
        sb.append(',');
        sb.append("svcRequestId");
        sb.append('=');
        sb.append(((this.svcRequestId == null)?NULL_STR:this.svcRequestId));
        sb.append(',');
        sb.append("svcAction");
        sb.append('=');
        sb.append(((this.svcAction == null)?NULL_STR:this.svcAction));
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
        result = ((result* 31)+((this.svcNotificationUrl == null)? 0 :this.svcNotificationUrl.hashCode()));
        result = ((result* 31)+((this.svcAction == null)? 0 :this.svcAction.hashCode()));
        result = ((result* 31)+((this.svcRequestId == null)? 0 :this.svcRequestId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SdncRequestHeader)) {
            return false;
        }
        SdncRequestHeader rhs = ((SdncRequestHeader) other);
        return ((((this.svcNotificationUrl == rhs.svcNotificationUrl)||((this.svcNotificationUrl!= null)&&this.svcNotificationUrl.equals(rhs.svcNotificationUrl)))&&((this.svcAction == rhs.svcAction)||((this.svcAction!= null)&&this.svcAction.equals(rhs.svcAction))))&&((this.svcRequestId == rhs.svcRequestId)||((this.svcRequestId!= null)&&this.svcRequestId.equals(rhs.svcRequestId))));
    }

}
