package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MeetingMOMSumotoDto implements Serializable {

    private static final long serialVersionUID = 990902111322028710L;

    private Long sumotoResoId;

    private Long momId;

    private Long sumotoDepId;

    private Long proposalId;

    private String resolutionNo;

    private String resolutionComment;

    private BigDecimal amount;

    private String status;

    private String department;

    private String detailsOfReso;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    public Long getSumotoResoId() {
        return sumotoResoId;
    }

    public void setSumotoResoId(Long sumotoResoId) {
        this.sumotoResoId = sumotoResoId;
    }

    public Long getMomId() {
        return momId;
    }

    public void setMomId(Long momId) {
        this.momId = momId;
    }

    public Long getSumotoDepId() {
        return sumotoDepId;
    }

    public void setSumotoDepId(Long sumotoDepId) {
        this.sumotoDepId = sumotoDepId;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getResolutionNo() {
        return resolutionNo;
    }

    public void setResolutionNo(String resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public String getResolutionComment() {
        return resolutionComment;
    }

    public void setResolutionComment(String resolutionComment) {
        this.resolutionComment = resolutionComment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDetailsOfReso() {
        return detailsOfReso;
    }

    public void setDetailsOfReso(String detailsOfReso) {
        this.detailsOfReso = detailsOfReso;
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
