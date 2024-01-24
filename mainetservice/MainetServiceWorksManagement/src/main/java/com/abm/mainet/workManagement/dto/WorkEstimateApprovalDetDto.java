package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 * @since 14 March 2018
 */
public class WorkEstimateApprovalDetDto implements Serializable {

   
	private static final long serialVersionUID = 4794896433057341306L;

	private Long approveId;

    private Long approveRefId;

    private Long approveEmpId;

    private String approveComment;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    public Long getApproveId() {
        return approveId;
    }

    public void setApproveId(Long approveId) {
        this.approveId = approveId;
    }

    public Long getApproveRefId() {
        return approveRefId;
    }

    public void setApproveRefId(Long approveRefId) {
        this.approveRefId = approveRefId;
    }

    public Long getApproveEmpId() {
        return approveEmpId;
    }

    public void setApproveEmpId(Long approveEmpId) {
        this.approveEmpId = approveEmpId;
    }

    public String getApproveComment() {
        return approveComment;
    }

    public void setApproveComment(String approveComment) {
        this.approveComment = approveComment;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

}
