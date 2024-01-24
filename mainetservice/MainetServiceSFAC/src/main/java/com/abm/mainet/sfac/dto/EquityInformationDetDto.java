package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class EquityInformationDetDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2179710179177656735L;
	
	private Long equityId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private BigDecimal netProfit;
	
	private BigDecimal authSharedCapital;
	
	private BigDecimal totalEquityAmount;
	
	private Long isEquityGrantApplied;
	
	private Date dateOfApplication;
	
	private BigDecimal amountOfEquityGrantApplied;
	
	private Long isEquityGrantRelease;
	
	private BigDecimal amountOfEquityGrantRelease;
	
	private Date dateOfEquityRelease;
	
	private BigDecimal addShareIssueByFPO;
	
	private Date dateOfAddItionalShareByFPO;
	
	private Long utilisationEquityGrant;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getEquityId() {
		return equityId;
	}

	public void setEquityId(Long equityId) {
		this.equityId = equityId;
	}

	

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
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
