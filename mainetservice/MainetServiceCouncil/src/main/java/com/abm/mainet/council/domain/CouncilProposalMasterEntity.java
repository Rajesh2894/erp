package com.abm.mainet.council.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author aarti.paan
 * @since 23 April 2019
 */
@Entity
@Table(name = "TB_CMT_COUNCIL_PROPOSAL_MAST")
public class CouncilProposalMasterEntity implements Serializable {

    private static final long serialVersionUID = 1665458029782211466L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PROPOSAL_ID")
    private Long proposalId;

    @Column(name = "PROPOSAL_NUMBER", length = 15, nullable = false)
    private String proposalNo;

    @Column(name = "PROPOSAL_DEP_ID", nullable = false)
    private Long proposalDepId;

    @Temporal(TemporalType.DATE)
    @Column(name = "PROPOSAL_DATE", nullable = false)
    private Date proposalDate;

    @Column(name = "PROPOSAL_DETAILS", length = 10000, nullable = false)
    private String proposalDetails;

    @Column(name = "purpose_remark", length = 3000, nullable = true)
    private String purposeremark;

    @Column(name = "PROPOSAL_AMOUNT")
    private BigDecimal proposalAmt;

    @Column(name = "AGENDA_ID", nullable = true)
    private Long agendaId;

    @Column(name = "YEAR_ID", nullable = false)
    private Long yearId;

    @Column(name = "SAC_HEAD_ID", nullable = false)
    private Long sacHeadId;

    @Column(name = "BUDGET_HEAD_DESC", nullable = true)
    private String budgetHeadDesc;

    @Column(name = "PROPOSAL_STATUS", nullable = false, length = 1)
    private String proposalStatus;

    @Column(name = "ISMOMPROPOSAL", length = 1)
    private String isMOMProposal;

    @Column(name = "PROPOSAL_TYPE", length = 1)
    private String proposalType;

    @Column(name = "PROPOSAL_SOURCE", nullable = false)
    private Long proposalSource;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true, updatable = true)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;
    
    @Column(name = "PROPOSAL_SUBTYPE", nullable = false)
    private Long proposalSubType;
    
    @Column(name = "COMMITTEE_TYPE", nullable = false)
    private Long committeeType;
    
    @Column(name = "FILED_ID", nullable = false)
    private Long filedId;
    
    @Column(name = "FUND_ID", nullable = false)
    private Long fundId;
    
    @Column(name = "SUBJECT_NO", length = 500)
    private String subNo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "proposalId", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE }, orphanRemoval = true)
    private Set<CouncilProposalWardEntity> wards = new HashSet<>();
    
    @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "councilMasEntity", cascade = CascadeType.ALL)
	@Where(clause = "YE_ACTIVE='Y'")
	private List<CouncilBudgetDetEntity> councilBugDetEntity = new ArrayList<>();

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

    public Set<CouncilProposalWardEntity> getWards() {
        return wards;
    }

    public void setWards(Set<CouncilProposalWardEntity> wards) {
        this.wards = wards;
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

    public List<CouncilBudgetDetEntity> getCouncilBugDetEntity() {
		return councilBugDetEntity;
	}

	public void setCouncilBugDetEntity(List<CouncilBudgetDetEntity> councilBugDetEntity) {
		this.councilBugDetEntity = councilBugDetEntity;
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

	public static String[] getPkValues() {
        return new String[] { "CMT", "TB_CMT_COUNCIL_PROPOSAL_MAST", "PROPOSAL_ID" };
    }
}
