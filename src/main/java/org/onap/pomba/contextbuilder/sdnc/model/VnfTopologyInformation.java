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

public class VnfTopologyInformation {

    @SerializedName("vnf-topology-identifier")
    @Expose
    private VnfTopologyIdentifier vnfTopologyIdentifier;
    @SerializedName("vnf-assignments")
    @Expose
    private VnfAssignments vnfAssignments;
    @SerializedName("vnf-parameters")
    @Expose
    private List<VnfParameter> vnfParameters = new ArrayList<>();

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public VnfTopologyInformation() {
    }

    /**
     *
     * @param vnfAssignments
     * @param vnfTopologyIdentifier
     * @param vnfParameters
     */
    public VnfTopologyInformation(VnfTopologyIdentifier vnfTopologyIdentifier, VnfAssignments vnfAssignments, List<VnfParameter> vnfParameters) {
        super();
        this.vnfTopologyIdentifier = vnfTopologyIdentifier;
        this.vnfAssignments = vnfAssignments;
        this.vnfParameters = vnfParameters;
    }

    public VnfTopologyIdentifier getVnfTopologyIdentifier() {
        return vnfTopologyIdentifier;
    }

    public void setVnfTopologyIdentifier(VnfTopologyIdentifier vnfTopologyIdentifier) {
        this.vnfTopologyIdentifier = vnfTopologyIdentifier;
    }

    public VnfAssignments getVnfAssignments() {
        return vnfAssignments;
    }

    public void setVnfAssignments(VnfAssignments vnfAssignments) {
        this.vnfAssignments = vnfAssignments;
    }

    public List<VnfParameter> getVnfParameters() {
        return vnfParameters;
    }

    public void setVnfParameters(List<VnfParameter> vnfParameters) {
        this.vnfParameters = vnfParameters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VnfTopologyInformation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vnfTopologyIdentifier");
        sb.append('=');
        sb.append(((this.vnfTopologyIdentifier == null)?NULL_STR:this.vnfTopologyIdentifier));
        sb.append(',');
        sb.append("vnfAssignments");
        sb.append('=');
        sb.append(((this.vnfAssignments == null)?NULL_STR:this.vnfAssignments));
        sb.append(',');
        sb.append("vnfParameters");
        sb.append('=');
        sb.append(((this.vnfParameters == null)?NULL_STR:this.vnfParameters));
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
        result = ((result* 31)+((this.vnfAssignments == null)? 0 :this.vnfAssignments.hashCode()));
        result = ((result* 31)+((this.vnfTopologyIdentifier == null)? 0 :this.vnfTopologyIdentifier.hashCode()));
        result = ((result* 31)+((this.vnfParameters == null)? 0 :this.vnfParameters.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VnfTopologyInformation)) {
            return false;
        }
        VnfTopologyInformation rhs = ((VnfTopologyInformation) other);
        return ((((this.vnfAssignments == rhs.vnfAssignments)||((this.vnfAssignments!= null)&&this.vnfAssignments.equals(rhs.vnfAssignments)))&&((this.vnfTopologyIdentifier == rhs.vnfTopologyIdentifier)||((this.vnfTopologyIdentifier!= null)&&this.vnfTopologyIdentifier.equals(rhs.vnfTopologyIdentifier))))&&((this.vnfParameters == rhs.vnfParameters)||((this.vnfParameters!= null)&&this.vnfParameters.equals(rhs.vnfParameters))));
    }

}
