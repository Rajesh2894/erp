package com.abm.mainet.dashboard.citizen.dto;

import java.io.Serializable;

public class CitizenDashBoardReqDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String mobileNo;
    private Long empId;
    private Long orgId;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "CitizenDashBoardReqDTO [mobileNo=" + mobileNo + ", empId=" + empId + ", orgId=" + orgId + "]";
    }

}
