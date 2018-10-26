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
import java.util.ArrayList;
import java.util.List;

public class VmNetwork {

    @SerializedName("network-role")
    @Expose
    private String networkRole;
    @SerializedName("network-ips-v6")
    @Expose
    private List<NetworkIpV6> networkIpsV6 = new ArrayList<NetworkIpV6>();
    @SerializedName("floating-ip")
    @Expose
    private String floatingIp;
    @SerializedName("use-dhcp")
    @Expose
    private String useDhcp;
    @SerializedName("network-ips")
    @Expose
    private List<NetworkIp> networkIps = new ArrayList<NetworkIp>();
    @SerializedName("floating-ip-v6")
    @Expose
    private String floatingIpV6;

    /**
     * No args constructor for use in serialization
     *
     */
    public VmNetwork() {
    }

    /**
     *
     * @param useDhcp
     * @param networkRole
     * @param networkIps
     * @param floatingIp
     * @param floatingIpV6
     * @param networkIpsV6
     */
    public VmNetwork(String networkRole, List<NetworkIpV6> networkIpsV6, String floatingIp, String useDhcp, List<NetworkIp> networkIps, String floatingIpV6) {
        super();
        this.networkRole = networkRole;
        this.networkIpsV6 = networkIpsV6;
        this.floatingIp = floatingIp;
        this.useDhcp = useDhcp;
        this.networkIps = networkIps;
        this.floatingIpV6 = floatingIpV6;
    }

    public String getNetworkRole() {
        return networkRole;
    }

    public void setNetworkRole(String networkRole) {
        this.networkRole = networkRole;
    }

    public List<NetworkIpV6> getNetworkIpsV6() {
        return networkIpsV6;
    }

    public void setNetworkIpsV6(List<NetworkIpV6> networkIpsV6) {
        this.networkIpsV6 = networkIpsV6;
    }

    public String getFloatingIp() {
        return floatingIp;
    }

    public void setFloatingIp(String floatingIp) {
        this.floatingIp = floatingIp;
    }

    public String getUseDhcp() {
        return useDhcp;
    }

    public void setUseDhcp(String useDhcp) {
        this.useDhcp = useDhcp;
    }

    public List<NetworkIp> getNetworkIps() {
        return networkIps;
    }

    public void setNetworkIps(List<NetworkIp> networkIps) {
        this.networkIps = networkIps;
    }

    public String getFloatingIpV6() {
        return floatingIpV6;
    }

    public void setFloatingIpV6(String floatingIpV6) {
        this.floatingIpV6 = floatingIpV6;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VmNetwork.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("networkRole");
        sb.append('=');
        sb.append(((this.networkRole == null)?"<null>":this.networkRole));
        sb.append(',');
        sb.append("networkIpsV6");
        sb.append('=');
        sb.append(((this.networkIpsV6 == null)?"<null>":this.networkIpsV6));
        sb.append(',');
        sb.append("floatingIp");
        sb.append('=');
        sb.append(((this.floatingIp == null)?"<null>":this.floatingIp));
        sb.append(',');
        sb.append("useDhcp");
        sb.append('=');
        sb.append(((this.useDhcp == null)?"<null>":this.useDhcp));
        sb.append(',');
        sb.append("networkIps");
        sb.append('=');
        sb.append(((this.networkIps == null)?"<null>":this.networkIps));
        sb.append(',');
        sb.append("floatingIpV6");
        sb.append('=');
        sb.append(((this.floatingIpV6 == null)?"<null>":this.floatingIpV6));
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
        result = ((result* 31)+((this.useDhcp == null)? 0 :this.useDhcp.hashCode()));
        result = ((result* 31)+((this.networkRole == null)? 0 :this.networkRole.hashCode()));
        result = ((result* 31)+((this.networkIps == null)? 0 :this.networkIps.hashCode()));
        result = ((result* 31)+((this.floatingIp == null)? 0 :this.floatingIp.hashCode()));
        result = ((result* 31)+((this.floatingIpV6 == null)? 0 :this.floatingIpV6 .hashCode()));
        result = ((result* 31)+((this.networkIpsV6 == null)? 0 :this.networkIpsV6 .hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VmNetwork) == false) {
            return false;
        }
        VmNetwork rhs = ((VmNetwork) other);
        return (((((((this.useDhcp == rhs.useDhcp)||((this.useDhcp!= null)&&this.useDhcp.equals(rhs.useDhcp)))&&((this.networkRole == rhs.networkRole)||((this.networkRole!= null)&&this.networkRole.equals(rhs.networkRole))))&&((this.networkIps == rhs.networkIps)||((this.networkIps!= null)&&this.networkIps.equals(rhs.networkIps))))&&((this.floatingIp == rhs.floatingIp)||((this.floatingIp!= null)&&this.floatingIp.equals(rhs.floatingIp))))&&((this.floatingIpV6 == rhs.floatingIpV6)||((this.floatingIpV6 != null)&&this.floatingIpV6 .equals(rhs.floatingIpV6))))&&((this.networkIpsV6 == rhs.networkIpsV6)||((this.networkIpsV6 != null)&&this.networkIpsV6 .equals(rhs.networkIpsV6))));
    }

}
