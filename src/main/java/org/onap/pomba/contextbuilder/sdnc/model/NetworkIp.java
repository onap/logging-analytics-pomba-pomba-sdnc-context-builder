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

public class NetworkIp {

    @SerializedName("ip-address")
    @Expose
    private String ipAddress;

    /**
     * No args constructor for use in serialization
     *
     */
    public NetworkIp() {
    }

    /**
     *
     * @param ipAddress
     */
    public NetworkIp(String ipAddress) {
        super();
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NetworkIp.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("ipAddress");
        sb.append('=');
        sb.append(((this.ipAddress == null)?"<null>":this.ipAddress));
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
        result = ((result* 31)+((this.ipAddress == null)? 0 :this.ipAddress.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NetworkIp) == false) {
            return false;
        }
        NetworkIp rhs = ((NetworkIp) other);
        return ((this.ipAddress == rhs.ipAddress)||((this.ipAddress!= null)&&this.ipAddress.equals(rhs.ipAddress)));
    }

}
