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
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VfModules {

    @SerializedName("vf-module")
    @Expose
    @Valid
    private List<VfModule> vfModule = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public VfModules() {
    }

    /**
     *
     * @param vfModule
     */
    public VfModules(List<VfModule> vfModule) {
        super();
        this.vfModule = vfModule;
    }

    public List<VfModule> getVfModule() {
        return vfModule;
    }

    public void setVfModule(List<VfModule> vfModule) {
        this.vfModule = vfModule;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("vfModule", vfModule).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(vfModule).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof VfModules)) {
            return false;
        }
        VfModules rhs = ((VfModules) other);
        return new EqualsBuilder().append(vfModule, rhs.vfModule).isEquals();
    }

}
