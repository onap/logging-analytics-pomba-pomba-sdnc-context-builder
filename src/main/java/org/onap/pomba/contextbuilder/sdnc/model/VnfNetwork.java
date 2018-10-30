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

public class VnfNetwork {

    @SerializedName("network-role")
    @Expose
    private String networkRole;
    @SerializedName("contrail-network-fqdn")
    @Expose
    private String contrailNetworkFqdn;
    @SerializedName("network-name")
    @Expose
    private String networkName;
    @SerializedName("network-id")
    @Expose
    private String networkId;
    @SerializedName("neutron-id")
    @Expose
    private String neutronId;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public VnfNetwork() {
    }

    /**
     *
     * @param networkRole
     * @param networkId
     * @param networkName
     * @param contrailNetworkFqdn
     * @param neutronId
     */
    public VnfNetwork(String networkRole, String contrailNetworkFqdn, String networkName, String networkId, String neutronId) {
        super();
        this.networkRole = networkRole;
        this.contrailNetworkFqdn = contrailNetworkFqdn;
        this.networkName = networkName;
        this.networkId = networkId;
        this.neutronId = neutronId;
    }

    public String getNetworkRole() {
        return networkRole;
    }

    public void setNetworkRole(String networkRole) {
        this.networkRole = networkRole;
    }

    public String getContrailNetworkFqdn() {
        return contrailNetworkFqdn;
    }

    public void setContrailNetworkFqdn(String contrailNetworkFqdn) {
        this.contrailNetworkFqdn = contrailNetworkFqdn;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getNeutronId() {
        return neutronId;
    }

    public void setNeutronId(String neutronId) {
        this.neutronId = neutronId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VnfNetwork.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("networkRole");
        sb.append('=');
        sb.append(((this.networkRole == null)?NULL_STR:this.networkRole));
        sb.append(',');
        sb.append("contrailNetworkFqdn");
        sb.append('=');
        sb.append(((this.contrailNetworkFqdn == null)?NULL_STR:this.contrailNetworkFqdn));
        sb.append(',');
        sb.append("networkName");
        sb.append('=');
        sb.append(((this.networkName == null)?NULL_STR:this.networkName));
        sb.append(',');
        sb.append("networkId");
        sb.append('=');
        sb.append(((this.networkId == null)?NULL_STR:this.networkId));
        sb.append(',');
        sb.append("neutronId");
        sb.append('=');
        sb.append(((this.neutronId == null)?NULL_STR:this.neutronId));
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
        result = ((result* 31)+((this.networkName == null)? 0 :this.networkName.hashCode()));
        result = ((result* 31)+((this.networkRole == null)? 0 :this.networkRole.hashCode()));
        result = ((result* 31)+((this.networkId == null)? 0 :this.networkId.hashCode()));
        result = ((result* 31)+((this.contrailNetworkFqdn == null)? 0 :this.contrailNetworkFqdn.hashCode()));
        result = ((result* 31)+((this.neutronId == null)? 0 :this.neutronId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VnfNetwork)) {
            return false;
        }
        VnfNetwork rhs = ((VnfNetwork) other);
        return ((((((this.networkName == rhs.networkName)||((this.networkName!= null)&&this.networkName.equals(rhs.networkName)))&&((this.networkRole == rhs.networkRole)||((this.networkRole!= null)&&this.networkRole.equals(rhs.networkRole))))&&((this.networkId == rhs.networkId)||((this.networkId!= null)&&this.networkId.equals(rhs.networkId))))&&((this.contrailNetworkFqdn == rhs.contrailNetworkFqdn)||((this.contrailNetworkFqdn!= null)&&this.contrailNetworkFqdn.equals(rhs.contrailNetworkFqdn))))&&((this.neutronId == rhs.neutronId)||((this.neutronId!= null)&&this.neutronId.equals(rhs.neutronId))));
    }

}
