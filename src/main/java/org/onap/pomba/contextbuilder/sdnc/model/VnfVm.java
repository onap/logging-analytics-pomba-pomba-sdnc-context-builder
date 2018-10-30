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

public class VnfVm {

    @SerializedName("vm-type")
    @Expose
    private String vmType;
    @SerializedName("vm-networks")
    @Expose
    private List<VmNetwork> vmNetworks = new ArrayList<>();
    @SerializedName("vm-count")
    @Expose
    private Integer vmCount;
    @SerializedName("vm-names")
    @Expose
    private List<VmName> vmNames = new ArrayList<>();

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public VnfVm() {
    }

    /**
     *
     * @param vmType
     * @param vmCount
     * @param vmNetworks
     * @param vmNames
     */
    public VnfVm(String vmType, List<VmNetwork> vmNetworks, Integer vmCount, List<VmName> vmNames) {
        super();
        this.vmType = vmType;
        this.vmNetworks = vmNetworks;
        this.vmCount = vmCount;
        this.vmNames = vmNames;
    }

    public String getVmType() {
        return vmType;
    }

    public void setVmType(String vmType) {
        this.vmType = vmType;
    }

    public List<VmNetwork> getVmNetworks() {
        return vmNetworks;
    }

    public void setVmNetworks(List<VmNetwork> vmNetworks) {
        this.vmNetworks = vmNetworks;
    }

    public Integer getVmCount() {
        return vmCount;
    }

    public void setVmCount(Integer vmCount) {
        this.vmCount = vmCount;
    }

    public List<VmName> getVmNames() {
        return vmNames;
    }

    public void setVmNames(List<VmName> vmNames) {
        this.vmNames = vmNames;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VnfVm.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vmType");
        sb.append('=');
        sb.append(((this.vmType == null)?NULL_STR:this.vmType));
        sb.append(',');
        sb.append("vmNetworks");
        sb.append('=');
        sb.append(((this.vmNetworks == null)?NULL_STR:this.vmNetworks));
        sb.append(',');
        sb.append("vmCount");
        sb.append('=');
        sb.append(((this.vmCount == null)?NULL_STR:this.vmCount));
        sb.append(',');
        sb.append("vmNames");
        sb.append('=');
        sb.append(((this.vmNames == null)?NULL_STR:this.vmNames));
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
        result = ((result* 31)+((this.vmType == null)? 0 :this.vmType.hashCode()));
        result = ((result* 31)+((this.vmCount == null)? 0 :this.vmCount.hashCode()));
        result = ((result* 31)+((this.vmNames == null)? 0 :this.vmNames.hashCode()));
        result = ((result* 31)+((this.vmNetworks == null)? 0 :this.vmNetworks.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VnfVm)) {
            return false;
        }
        VnfVm rhs = ((VnfVm) other);
        return (((((this.vmType == rhs.vmType)||((this.vmType!= null)&&this.vmType.equals(rhs.vmType)))&&((this.vmCount == rhs.vmCount)||((this.vmCount!= null)&&this.vmCount.equals(rhs.vmCount))))&&((this.vmNames == rhs.vmNames)||((this.vmNames!= null)&&this.vmNames.equals(rhs.vmNames))))&&((this.vmNetworks == rhs.vmNetworks)||((this.vmNetworks!= null)&&this.vmNetworks.equals(rhs.vmNetworks))));
    }

}
