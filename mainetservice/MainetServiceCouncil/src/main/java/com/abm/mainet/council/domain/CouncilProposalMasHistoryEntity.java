package com.abm.mainet.council.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author aarti.paan
 * @since 25 April 2019
 */
@Entity
@Table(name = "TB_CMT_COUNCIL_PROPOSAL_MAST_HIST")
public class CouncilProposalMasHistoryEntity implements Serializable {

    private static final long serialVersionUID = 5141135247091298622L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PROPOSAL_H_ID")
    private Long proposalHisId;

    @Column(name = "PROPOSAL_ID")
    private Long proposalId;

    @Column(name = "PROPOSAL_NUMBER", length = 15)
    private String proposalNo;

    @Column(name = "PROPOSAL_DEP_ID")
    private Long proposalDepId;

    @Temporal(TemporalType.DATE)
    @Column(name = "PROPOSAL_DATE")
    private Date proposalDate;

    @Column(name = "PROPOSAL_DETAILS", length = 10000)
    private String proposalDetails;

    @Column(name = "purpose_remark", length = 3000, nullable = true)
    private String purposeremark;

    @Column(name = "PROPOSAL_AMOUNT")
    private BigDecimal proposalAmt;

    @Column(name = "AGENDA_ID")
    private Long agendaId;

    @Column(name = "PROPOSAL_STATUS", length = 1)
    private String proposalStatus;

    @Column(name = "H_STATUS", length = 1)
    private String historyStatus;

    @Column(name = "PROPOSAL_TYPE", length = 1)
    private String proposalType;

    @Column(name = "PROPOSAL_SOURCE")
    private Long proposalSource;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "YEAR_ID")
    private Long yearId;

    @Column(name = "SAC_HEAD_ID")
    private Long sacHeadId;

    @Column(name = "BUDGET_HEAD_DESC")
    private String budgetHeadDesc;
    
    @Column(name = "FILED_ID", nullable = false)
    private Long filedId;

	public Long getProposalHisId() {
        return proposalHisId;
    }

    public void setProposalHisId(Long proposalHisId) {
        this.proposalHisId = proposalHisId;
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

    public String getPurposeremark() {
        return purposeremark;
    }

    public void setPurposeremark(String purposeremark) {
        this.purposeremark = purposeremark;
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

    public String getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(String historyStatus) {
        this.historyStatus = historyStatus;
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

    public String getBudgetHeadDesc() {
        return budgetHeadDesc;
    }

    public void setBudgetHeadDesc(String budgetHeadDesc) {
        this.budgetHeadDesc = budgetHeadDesc;
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

    public static String[] getPkValues() {
        return new String[] { "CMT", "TB_CMT_COUNCIL_PROPOSAL_MAST_HIST", "PROPOSAL_H_ID" };
    }
    
    public Long getFiledId() {
		return filedId;
	}

	public void setFiledId(Long filedId) {
		this.filedId = filedId;
	}
}
