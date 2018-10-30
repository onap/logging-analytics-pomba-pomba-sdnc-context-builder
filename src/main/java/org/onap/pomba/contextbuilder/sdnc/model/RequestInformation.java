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

public class RequestInformation {

    @SerializedName("request-id")
    @Expose
    private String requestId;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("request-action")
    @Expose
    private String requestAction;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestInformation() {
    }

    /**
     *
     * @param requestAction
     * @param source
     * @param requestId
     */
    public RequestInformation(String requestId, String source, String requestAction) {
        super();
        this.requestId = requestId;
        this.source = source;
        this.requestAction = requestAction;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRequestAction() {
        return requestAction;
    }

    public void setRequestAction(String requestAction) {
        this.requestAction = requestAction;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(RequestInformation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("requestId");
        sb.append('=');
        sb.append(((this.requestId == null)?NULL_STR:this.requestId));
        sb.append(',');
        sb.append("source");
        sb.append('=');
        sb.append(((this.source == null)?NULL_STR:this.source));
        sb.append(',');
        sb.append("requestAction");
        sb.append('=');
        sb.append(((this.requestAction == null)?NULL_STR:this.requestAction));
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
        result = ((result* 31)+((this.requestAction == null)? 0 :this.requestAction.hashCode()));
        result = ((result* 31)+((this.source == null)? 0 :this.source.hashCode()));
        result = ((result* 31)+((this.requestId == null)? 0 :this.requestId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RequestInformation)) {
            return false;
        }
        RequestInformation rhs = ((RequestInformation) other);
        return ((((this.requestAction == rhs.requestAction)||((this.requestAction!= null)&&this.requestAction.equals(rhs.requestAction)))&&((this.source == rhs.source)||((this.source!= null)&&this.source.equals(rhs.source))))&&((this.requestId == rhs.requestId)||((this.requestId!= null)&&this.requestId.equals(rhs.requestId))));
    }

}
