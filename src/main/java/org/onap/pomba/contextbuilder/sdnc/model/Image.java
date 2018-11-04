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

public class Image {

    @SerializedName("image-name")
    @Expose
    private String imageName;

    private final static String NULL_STR = "<null>";

    /**
     * No args constructor for use in serialization
     *
     */
    public Image() {
    }

    /**
     *
     * @param imageName
     */
    public Image(String imageName) {
        super();
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Image.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("imageName");
        sb.append('=');
        sb.append(((this.imageName == null)?NULL_STR:this.imageName));
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
        result = ((result* 31)+((this.imageName == null)? 0 :this.imageName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Image)) {
            return false;
        }
        Image rhs = ((Image) other);
        return ((this.imageName == rhs.imageName)||((this.imageName!= null)&&this.imageName.equals(rhs.imageName)));
    }

}
