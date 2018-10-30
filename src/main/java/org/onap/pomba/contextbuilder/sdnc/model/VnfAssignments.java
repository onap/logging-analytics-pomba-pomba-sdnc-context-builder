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

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VnfAssignments {

    @SerializedName("vnf-networks")
    @Expose
    private List<VnfNetwork> vnfNetworks = new ArrayList<>();
    @SerializedName("availability-zones")
    @Expose
    private List<AvailabilityZone> availabilityZones = new ArrayList<>();
    @SerializedName("vnf-vms")
    @Expose
    private List<VnfVm> vnfVms = new ArrayList<>();

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public VnfAssignments() {
    }

    /**
     *
     * @param vnfVms
     * @param availabilityZones
     * @param vnfNetworks
     */
    public VnfAssignments(List<VnfNetwork> vnfNetworks, List<AvailabilityZone> availabilityZones, List<VnfVm> vnfVms) {
        super();
        this.vnfNetworks = vnfNetworks;
        this.availabilityZones = availabilityZones;
        this.vnfVms = vnfVms;
    }

    public List<VnfNetwork> getVnfNetworks() {
        return vnfNetworks;
    }

    public void setVnfNetworks(List<VnfNetwork> vnfNetworks) {
        this.vnfNetworks = vnfNetworks;
    }

    public List<AvailabilityZone> getAvailabilityZones() {
        return availabilityZones;
    }

    public void setAvailabilityZones(List<AvailabilityZone> availabilityZones) {
        this.availabilityZones = availabilityZones;
    }

    public List<VnfVm> getVnfVms() {
        return vnfVms;
    }

    public void setVnfVms(List<VnfVm> vnfVms) {
        this.vnfVms = vnfVms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VnfAssignments.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vnfNetworks");
        sb.append('=');
        sb.append(((this.vnfNetworks == null)?NULL_STR:this.vnfNetworks));
        sb.append(',');
        sb.append("availabilityZones");
        sb.append('=');
        sb.append(((this.availabilityZones == null)?NULL_STR:this.availabilityZones));
        sb.append(',');
        sb.append("vnfVms");
        sb.append('=');
        sb.append(((this.vnfVms == null)?NULL_STR:this.vnfVms));
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
        result = ((result* 31)+((this.availabilityZones == null)? 0 :this.availabilityZones.hashCode()));
        result = ((result* 31)+((this.vnfVms == null)? 0 :this.vnfVms.hashCode()));
        result = ((result* 31)+((this.vnfNetworks == null)? 0 :this.vnfNetworks.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VnfAssignments)) {
            return false;
        }
        VnfAssignments rhs = ((VnfAssignments) other);
        return ((((this.availabilityZones == rhs.availabilityZones)||((this.availabilityZones!= null)&&this.availabilityZones.equals(rhs.availabilityZones)))&&((this.vnfVms == rhs.vnfVms)||((this.vnfVms!= null)&&this.vnfVms.equals(rhs.vnfVms))))&&((this.vnfNetworks == rhs.vnfNetworks)||((this.vnfNetworks!= null)&&this.vnfNetworks.equals(rhs.vnfNetworks))));
    }

}
