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

public class VmName {

    @SerializedName("vm-name")
    @Expose
    private String vmName;

    /**
     * No args constructor for use in serialization
     *
     */
    public VmName() {
    }

    /**
     *
     * @param vmName
     */
    public VmName(String vmName) {
        super();
        this.vmName = vmName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VmName.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vmName");
        sb.append('=');
        sb.append(((this.vmName == null)?"<null>":this.vmName));
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
        result = ((result* 31)+((this.vmName == null)? 0 :this.vmName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VmName)) {
            return false;
        }
        VmName rhs = ((VmName) other);
        return ((this.vmName == rhs.vmName)||((this.vmName!= null)&&this.vmName.equals(rhs.vmName)));
    }

}
