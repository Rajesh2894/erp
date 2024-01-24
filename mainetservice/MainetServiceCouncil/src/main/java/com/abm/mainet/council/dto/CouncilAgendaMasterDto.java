package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.Date;

public class CouncilAgendaMasterDto implements Serializable {

    private static final long serialVersionUID = -3315560716128444811L;
    private Long agendaId;

    
    private Date agendaDate;

    private String agendaNo;

    private Long committeeTypeId;

    private Long proposalId;

    private String agendaStatus;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String proposalNumber;

    private String committeeType;

    private Date agendaFromDate;

    private Date agendaToDate;
    
    //this is doing only when passing date in JSON format it goes in MS
    private String agenDate;

    private String proposalIds;

    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long agendaId) {
        this.agendaId = agendaId;
    }

    public Date getAgendaDate() {
        return agendaDate;
    }

    public void setAgendaDate(Date agendaDate) {
        this.agendaDate = agendaDate;
    }

    public String getAgendaNo() {
        return agendaNo;
    }

    public void setAgendaNo(String agendaNo) {
        this.agendaNo = agendaNo;
    }

    public Long getCommitteeTypeId() {
        return committeeTypeId;
    }

    public void setCommitteeTypeId(Long committeeTypeId) {
        this.committeeTypeId = committeeTypeId;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getAgendaStatus() {
        return agendaStatus;
    }

    public void setAgendaStatus(String agendaStatus) {
        this.agendaStatus = agendaStatus;
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

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public String getCommitteeType() {
        return committeeType;
    }

    public void setCommitteeType(String committeeType) {
        this.committeeType = committeeType;
    }

    public Date getAgendaFromDate() {
        return agendaFromDate;
    }

    public void setAgendaFromDate(Date agendaFromDate) {
        this.agendaFromDate = agendaFromDate;
    }

    public Date getAgendaToDate() {
        return agendaToDate;
    }

    public void setAgendaToDate(Date agendaToDate) {
        this.agendaToDate = agendaToDate;
    }

    public String getProposalIds() {
        return proposalIds;
    }

    public void setProposalIds(String proposalIds) {
        this.proposalIds = proposalIds;
    }

	public String getAgenDate() {
		return agenDate;
	}

	public void setAgenDate(String agenDate) {
		this.agenDate = agenDate;
	}
    
    

}
