package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author aarti.paan
 * @since 25 April 2019
 */
public class CouncilProposalMasterDto implements Serializable {

    private static final long serialVersionUID = -2512212586731021248L;

    private Long proposalId;

    private String proposalNo;

    private Long proposalDepId;

    private String proposalDeptName;

    private Date proposalDate;

    private String proposalDetails;

    private String purposeremark;

    private BigDecimal proposalAmt;

    private Long agendaId;

    private String proposalStatus;// used for Action BT

    // D#119161
    private String proposalStatusDesc;// based on language (HINDI/English)

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Date fromDate;

    private Date toDate;

    private String agendaNo;

    private Long wardId;

    private String wardDescJoin;

    private Long yearId;

    private Long sacHeadId;

    private String acHeadCode;    

    /*
     * used when we take budget head code from user in case of account setting mode
     */
    private String budgetHeadDesc;

    /* wfstatus used for workflow final status */
    private String wfStatus;

    private List<String> wardDesc = new ArrayList<>();

    private List<Long> wards = new ArrayList<>();

    private String meetingProposalStatus;

    private String meetingDate;

    private String isMOMProposal;

    private String proposalType;

    private Long proposalSource;

    private String approvalFlag;
    
    private Long proposalSubType;
    
    private Long committeeType;
    
    private Long filedId;
    
   private String proposalSubTypeDesc;
    
    private String committeeTypeDesc;
    
    private Long fundId;
    
    private String subNo;
    
    
    private List<CouncilYearDetDto> yearDtos = new ArrayList<>();

    public List<Long> getWards() {
        return wards;
    }

    public void setWards(List<Long> wards) {
        this.wards = wards;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    public Long getProposalDepId() {
        return proposalDepId;
    }

    public void setProposalDepId(Long proposalDepId) {
        this.proposalDepId = proposalDepId;
    }

    public String getProposalDeptName() {
        return proposalDeptName;
    }

    public void setProposalDeptName(String proposalDeptName) {
        this.proposalDeptName = proposalDeptName;
    }

    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public String getProposalDetails() {
        return proposalDetails;
    }

    public void setProposalDetails(String proposalDetails) {
        this.proposalDetails = proposalDetails;
    }

    public BigDecimal getProposalAmt() {
        return proposalAmt;
    }

    public void setProposalAmt(BigDecimal proposalAmt) {
        this.proposalAmt = proposalAmt;
    }

    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long agendaId) {
        this.agendaId = agendaId;
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public String getProposalStatusDesc() {
        return proposalStatusDesc;
    }

    public void setProposalStatusDesc(String proposalStatusDesc) {
        this.proposalStatusDesc = proposalStatusDesc;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getAgendaNo() {
        return agendaNo;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

    public List<String> getWardDesc() {
        return wardDesc;
    }

    public void setWardDesc(List<String> wardDesc) {
        this.wardDesc = wardDesc;
    }

    public String getWardDescJoin() {
        return wardDescJoin;
    }

    public void setWardDescJoin(String wardDescJoin) {
        this.wardDescJoin = wardDescJoin;
    }

    public void setAgendaNo(String agendaNo) {
        this.agendaNo = agendaNo;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public String getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(String acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    public String getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(String wfStatus) {
        this.wfStatus = wfStatus;
    }

    public String getBudgetHeadDesc() {
        return budgetHeadDesc;
    }

    public void setBudgetHeadDesc(String budgetHeadDesc) {
        this.budgetHeadDesc = budgetHeadDesc;
    }

    public String getMeetingProposalStatus() {
        return meetingProposalStatus;
    }

    public void setMeetingProposalStatus(String meetingProposalStatus) {
        this.meetingProposalStatus = meetingProposalStatus;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getIsMOMProposal() {
        return isMOMProposal;
    }

    public void setIsMOMProposal(String isMOMProposal) {
        this.isMOMProposal = isMOMProposal;
    }

    public String getProposalType() {
        return proposalType;
    }

    public void setProposalType(String proposalType) {
        this.proposalType = proposalType;
    }

    public Long getProposalSource() {
        return proposalSource;
    }

    public void setProposalSource(Long proposalSource) {
        this.proposalSource = proposalSource;
    }

    public String getPurposeremark() {
        return purposeremark;
    }

    public void setPurposeremark(String purposeremark) {
        this.purposeremark = purposeremark;
    }

    public String getApprovalFlag() {
        return approvalFlag;
    }

    public void setApprovalFlag(String approvalFlag) {
        this.approvalFlag = approvalFlag;
    }

	public List<CouncilYearDetDto> getYearDtos() {
		return yearDtos;
	}

	public void setYearDtos(List<CouncilYearDetDto> yearDtos) {
		this.yearDtos = yearDtos;
	}
	
	public Long getProposalSubType() {
        return proposalSubType;
    }
	
    public void setProposalSubType(Long proposalSubType) {
        this.proposalSubType = proposalSubType;
    }
    
    public Long getCommitteeType() {
        return committeeType;
    }

    public void setCommitteeType(Long committeeType) {
        this.committeeType = committeeType;
    }
    
    public Long getFiledId() {
        return filedId;
    }

    public void setFiledId(Long filedId) {
        this.filedId = filedId;
    }

	public String getProposalSubTypeDesc() {
		return proposalSubTypeDesc;
	}

	public void setProposalSubTypeDesc(String proposalSubTypeDesc) {
		this.proposalSubTypeDesc = proposalSubTypeDesc;
	}

	public String getCommitteeTypeDesc() {
		return committeeTypeDesc;
	}

	public void setCommitteeTypeDesc(String committeeTypeDesc) {
		this.committeeTypeDesc = committeeTypeDesc;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public String getSubNo() {
		return subNo;
	}

	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}

}
