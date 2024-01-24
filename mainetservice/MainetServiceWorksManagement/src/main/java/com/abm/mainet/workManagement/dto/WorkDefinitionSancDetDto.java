package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 * @since 30 April 2018
 */
public class WorkDefinitionSancDetDto implements Serializable {

    private static final long serialVersionUID = 609452834699325828L;

    private Long workSancId;

    private Long workId;

    private Long deptId;

    private String workSancNo;

    private Date workSancDate;

    private String workSancBy;

    private String workDesignBy;

    private Long orgid;

    private Long createdBy;

    private Date CreatedDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String sancNature;

    public Long getWorkSancId() {
        return workSancId;
    }

    public void setWorkSancId(Long workSancId) {
        this.workSancId = workSancId;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getWorkSancNo() {
        return workSancNo;
    }

    public void setWorkSancNo(String workSancNo) {
        this.workSancNo = workSancNo;
    }

    public Date getWorkSancDate() {
        return workSancDate;
    }

    public void setWorkSancDate(Date workSancDate) {
        this.workSancDate = workSancDate;
    }

    public String getWorkSancBy() {
        return workSancBy;
    }

    public void setWorkSancBy(String workSancBy) {
        this.workSancBy = workSancBy;
    }

    public String getWorkDesignBy() {
        return workDesignBy;
    }

    public void setWorkDesignBy(String workDesignBy) {
        this.workDesignBy = workDesignBy;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getSancNature() {
        return sancNature;
    }

    public void setSancNature(String sancNature) {
        this.sancNature = sancNature;
    }

    @Override
    public String toString() {
        return "WorkDefinitionSancDetDto [workSancId=" + workSancId + ", workId=" + workId + ", deptId=" + deptId
                + ", workSancNo=" + workSancNo + ", workSancDate=" + workSancDate + ", workSancBy=" + workSancBy
                + ", workDesignBy=" + workDesignBy + ", orgid=" + orgid + ", createdBy=" + createdBy + ", CreatedDate="
                + CreatedDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", sancNature=" + sancNature + "]";
    }

}
