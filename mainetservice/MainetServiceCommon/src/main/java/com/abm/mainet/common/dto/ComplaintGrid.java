package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author ritesh.patil
 *
 */
public class ComplaintGrid implements Serializable {
    private static final long serialVersionUID = -6496033804289710871L;
    private Long deptCompId;
    private String deptName;
    private String deptNameReg;
    private Long orgId;
    private Long deptId;

    private String deptStatus;

    public String getDeptNameReg() {
        return deptNameReg;
    }

    public void setDeptNameReg(final String deptNameReg) {
        this.deptNameReg = deptNameReg;
    }

    private String orgName;

    public Long getDeptCompId() {
        return deptCompId;
    }

    public void setDeptCompId(final Long deptCompId) {
        this.deptCompId = deptCompId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(final String deptName) {
        this.deptName = deptName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

}
