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

public class NetworkIpV6 {

    @SerializedName("ip-address-ipv6")
    @Expose
    private String ipAddressIpV6;

    /**
     * No args constructor for use in serialization
     *
     */
    public NetworkIpV6() {
    }

    /**
     *
     * @param ipAddressIpV6
     */
    public NetworkIpV6(String ipAddressIpV6) {
        super();
        this.ipAddressIpV6 = ipAddressIpV6;
    }

    public String getIpAddressIpV6() {
        return ipAddressIpV6;
    }

    public void setIpAddressIpV6(String ipAddressIpV6) {
        this.ipAddressIpV6 = ipAddressIpV6;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NetworkIpV6 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("ipAddressIpV6");
        sb.append('=');
        sb.append(((this.ipAddressIpV6 == null)?"<null>":this.ipAddressIpV6));
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
        result = ((result* 31)+((this.ipAddressIpV6 == null)? 0 :this.ipAddressIpV6 .hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NetworkIpV6)) {
            return false;
        }
        NetworkIpV6 rhs = ((NetworkIpV6) other);
        return ((this.ipAddressIpV6 == rhs.ipAddressIpV6)||((this.ipAddressIpV6 != null)&&this.ipAddressIpV6 .equals(rhs.ipAddressIpV6)));
    }

}
