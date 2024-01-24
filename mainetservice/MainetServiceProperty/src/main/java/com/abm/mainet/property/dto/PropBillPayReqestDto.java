package com.abm.mainet.property.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class PropBillPayReqestDto implements Serializable {

    /**
     * 
     */

    private static final long serialVersionUID = -9218845311229796121L;
    private Long orgId;
    private Long deptId;
    private Long userId;
    private String propNo;
    private String oldPropNo;
    private double paidAmonut;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public String getOldPropNo() {
        return oldPropNo;
    }

    public void setOldPropNo(String oldPropNo) {
        this.oldPropNo = oldPropNo;
    }

    public double getPaidAmonut() {
        return paidAmonut;
    }

    public void setPaidAmonut(double paidAmonut) {
        this.paidAmonut = paidAmonut;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
