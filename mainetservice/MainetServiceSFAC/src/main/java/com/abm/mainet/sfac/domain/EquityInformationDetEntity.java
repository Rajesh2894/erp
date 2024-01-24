package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Tb_Sfac_Equity_Info_Detail")
public class EquityInformationDetEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5850253059174573032L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "EQT_ID", nullable = false)
	private Long equityId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Column(name = "NET_PROFIT")
	private BigDecimal netProfit;
	
	@Column(name = "AUTH_SHARED_CAPITAL")
	private BigDecimal authSharedCapital;
	
	@Column(name = "TOTAL_EQUITY_AMT")
	private BigDecimal totalEquityAmount;
	
	@Column(name = "IS_EQUITY_GRANT_APPLIED")
	private Long isEquityGrantApplied;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_APP")
	private Date dateOfApplication;
	
	@Column(name = "AMT_OF_EQUITY_GRANT_APPLIED")
	private BigDecimal amountOfEquityGrantApplied;
	
	@Column(name = "IS_EQUITY_GRANT_RELEASE")
	private Long isEquityGrantRelease;
	
	
	
	@Column(name = "AMT_OF_EQUITY_GRANT_RELEASE")
	private BigDecimal amountOfEquityGrantRelease;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_EQT_REL")
	private Date dateOfEquityRelease;
	
	@Column(name = "ADD_SHARE_ISSUE_FPO")
	private BigDecimal addShareIssueByFPO;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_ADD_SHARE_FPO")
	private Date dateOfAddItionalShareByFPO;
	
	@Column(name = "UTILISATION_EQT_GRANT")
	private Long utilisationEquityGrant;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;



	public Long getEquityId() {
		return equityId;
	}

	public void setEquityId(Long equityId) {
		this.equityId = equityId;
	}

	public FPOProfileManagementMaster getFPOProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}

	public void setFPOProfileMgmtMaster(FPOProfileManagementMaster fPOProfileMgmtMaster) {
		fpoProfileMgmtMaster = fPOProfileMgmtMaster;
	}

	public BigDecimal getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(BigDecimal netProfit) {
		this.netProfit = netProfit;
	}

	public BigDecimal getAuthSharedCapital() {
		return authSharedCapital;
	}

	public void setAuthSharedCapital(BigDecimal authSharedCapital) {
		this.authSharedCapital = authSharedCapital;
	}

	public BigDecimal getTotalEquityAmount() {
		return totalEquityAmount;
	}

	public void setTotalEquityAmount(BigDecimal totalEquityAmount) {
		this.totalEquityAmount = totalEquityAmount;
	}

	public Long getIsEquityGrantApplied() {
		return isEquityGrantApplied;
	}

	public void setIsEquityGrantApplied(Long isEquityGrantApplied) {
		this.isEquityGrantApplied = isEquityGrantApplied;
	}

	public Date getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public BigDecimal getAmountOfEquityGrantApplied() {
		return amountOfEquityGrantApplied;
	}

	public void setAmountOfEquityGrantApplied(BigDecimal amountOfEquityGrantApplied) {
		this.amountOfEquityGrantApplied = amountOfEquityGrantApplied;
	}

	public Long getIsEquityGrantRelease() {
		return isEquityGrantRelease;
	}

	public void setIsEquityGrantRelease(Long isEquityGrantRelease) {
		this.isEquityGrantRelease = isEquityGrantRelease;
	}

	public BigDecimal getAmountOfEquityGrantRelease() {
		return amountOfEquityGrantRelease;
	}

	public void setAmountOfEquityGrantRelease(BigDecimal amountOfEquityGrantRelease) {
		this.amountOfEquityGrantRelease = amountOfEquityGrantRelease;
	}

	public Date getDateOfEquityRelease() {
		return dateOfEquityRelease;
	}

	public void setDateOfEquityRelease(Date dateOfEquityRelease) {
		this.dateOfEquityRelease = dateOfEquityRelease;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
	
	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_Equity_Info_Detail", "EQT_ID" };
	}

	public BigDecimal getAddShareIssueByFPO() {
		return addShareIssueByFPO;
	}

	public void setAddShareIssueByFPO(BigDecimal addShareIssueByFPO) {
		this.addShareIssueByFPO = addShareIssueByFPO;
	}

	public Date getDateOfAddItionalShareByFPO() {
		return dateOfAddItionalShareByFPO;
	}

	public void setDateOfAddItionalShareByFPO(Date dateOfAddItionalShareByFPO) {
		this.dateOfAddItionalShareByFPO = dateOfAddItionalShareByFPO;
	}

	public Long getUtilisationEquityGrant() {
		return utilisationEquityGrant;
	}

	public void setUtilisationEquityGrant(Long utilisationEquityGrant) {
		this.utilisationEquityGrant = utilisationEquityGrant;
	}
	
	

}
