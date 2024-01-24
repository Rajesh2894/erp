package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreditLinkageInformationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7131414181994401600L;
	
	
	private Long crdId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;

	private Long financialYear;
	
	private String creditDisbursingAuthority;

	private Long installmentAmount;

	private String installmentUnit;
	
	private String tenure;

	private Long loanApplied;

	private BigDecimal AmtOfLoanApplied;

	private String PurposeOfLoanApplied;

	private String institutionAvailed;

	private BigDecimal LoanSanctioned;

	private Long LOANDISBURSED;

	private BigDecimal loanAmountDisbursed;

	private String utilizationOfLoan;

	
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	
	
	public String getCreditDisbursingAuthority() {
		return creditDisbursingAuthority;
	}

	public void setCreditDisbursingAuthority(String creditDisbursingAuthority) {
		this.creditDisbursingAuthority = creditDisbursingAuthority;
	}

	public Long getInstallmentAmount() {
		return installmentAmount;
	}

	public void setInstallmentAmount(Long installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	public String getInstallmentUnit() {
		return installmentUnit;
	}

	public void setInstallmentUnit(String installmentUnit) {
		this.installmentUnit = installmentUnit;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public Long getCrdId() {
		return crdId;
	}

	public void setCrdId(Long crdId) {
		this.crdId = crdId;
	}

	

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	public Long getLoanApplied() {
		return loanApplied;
	}

	public void setLoanApplied(Long loanApplied) {
		this.loanApplied = loanApplied;
	}

	public BigDecimal getAmtOfLoanApplied() {
		return AmtOfLoanApplied;
	}

	public void setAmtOfLoanApplied(BigDecimal amtOfLoanApplied) {
		AmtOfLoanApplied = amtOfLoanApplied;
	}

	public String getPurposeOfLoanApplied() {
		return PurposeOfLoanApplied;
	}

	public void setPurposeOfLoanApplied(String purposeOfLoanApplied) {
		PurposeOfLoanApplied = purposeOfLoanApplied;
	}

	public String getInstitutionAvailed() {
		return institutionAvailed;
	}

	public void setInstitutionAvailed(String institutionAvailed) {
		this.institutionAvailed = institutionAvailed;
	}

	public BigDecimal getLoanSanctioned() {
		return LoanSanctioned;
	}

	public void setLoanSanctioned(BigDecimal loanSanctioned) {
		LoanSanctioned = loanSanctioned;
	}

	public Long getLOANDISBURSED() {
		return LOANDISBURSED;
	}

	public void setLOANDISBURSED(Long lOANDISBURSED) {
		LOANDISBURSED = lOANDISBURSED;
	}

	public BigDecimal getLoanAmountDisbursed() {
		return loanAmountDisbursed;
	}

	public void setLoanAmountDisbursed(BigDecimal loanAmountDisbursed) {
		this.loanAmountDisbursed = loanAmountDisbursed;
	}

	public String getUtilizationOfLoan() {
		return utilizationOfLoan;
	}

	public void setUtilizationOfLoan(String utilizationOfLoan) {
		this.utilizationOfLoan = utilizationOfLoan;
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



}
