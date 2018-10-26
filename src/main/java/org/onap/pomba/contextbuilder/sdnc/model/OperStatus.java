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

public class OperStatus {

    @SerializedName("last-action")
    @Expose
    private String lastAction;
    @SerializedName("order-status")
    @Expose
    private String orderStatus;
    @SerializedName("last-order-status")
    @Expose
    private String lastOrderStatus;

    /**
     * No args constructor for use in serialization
     *
     */
    public OperStatus() {
    }

    /**
     *
     * @param orderStatus
     * @param lastAction
     * @param lastOrderStatus
     */
    public OperStatus(String lastAction, String orderStatus, String lastOrderStatus) {
        super();
        this.lastAction = lastAction;
        this.orderStatus = orderStatus;
        this.lastOrderStatus = lastOrderStatus;
    }

    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLastOrderStatus() {
        return lastOrderStatus;
    }

    public void setLastOrderStatus(String lastOrderStatus) {
        this.lastOrderStatus = lastOrderStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(OperStatus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("lastAction");
        sb.append('=');
        sb.append(((this.lastAction == null)?"<null>":this.lastAction));
        sb.append(',');
        sb.append("orderStatus");
        sb.append('=');
        sb.append(((this.orderStatus == null)?"<null>":this.orderStatus));
        sb.append(',');
        sb.append("lastOrderStatus");
        sb.append('=');
        sb.append(((this.lastOrderStatus == null)?"<null>":this.lastOrderStatus));
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
        result = ((result* 31)+((this.orderStatus == null)? 0 :this.orderStatus.hashCode()));
        result = ((result* 31)+((this.lastAction == null)? 0 :this.lastAction.hashCode()));
        result = ((result* 31)+((this.lastOrderStatus == null)? 0 :this.lastOrderStatus.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OperStatus) == false) {
            return false;
        }
        OperStatus rhs = ((OperStatus) other);
        return ((((this.orderStatus == rhs.orderStatus)||((this.orderStatus!= null)&&this.orderStatus.equals(rhs.orderStatus)))&&((this.lastAction == rhs.lastAction)||((this.lastAction!= null)&&this.lastAction.equals(rhs.lastAction))))&&((this.lastOrderStatus == rhs.lastOrderStatus)||((this.lastOrderStatus!= null)&&this.lastOrderStatus.equals(rhs.lastOrderStatus))));
    }

}
