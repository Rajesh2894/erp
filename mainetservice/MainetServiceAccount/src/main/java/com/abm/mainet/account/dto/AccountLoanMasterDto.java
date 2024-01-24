package com.abm.mainet.account.dto;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AccountLoanMasterDto implements Serializable {

	private static final long serialVersionUID = -7952300843725290308L;

	private Long loanId;
	
	private String lnNo;

	private String lnDeptname;

	private String lnPurpose;

	private Long resNo;

	private Date resDate;

	private Date sanctionDate;

	private BigDecimal santionAmount;
	
	

	private BigDecimal amountOfEachInstallment;

	//private  lnInrate;
	private BigDecimal lnInrate;

	private Long instCount;

	private Long instFreq;

	private Long instAmt;

	private Long authBy;

	private String lmRemark;

	private char lnStatus;

	private Long langId;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long categoryTypeId;
	
	private List<AccountLoanDetDto> accountLoanDetList;
	
	private List<AccountLoanMasterDto> ListAccountLoanMasterDto;
	
	// adding new fields from entity
	private String loanName;

	private String SanctionNo;

	private Long vendorId;

	private Long noOfInstallments;

	private Long loanPeriod;
	
	private String loanPeriodUnit;
	
	// adding newer fields

	private BigDecimal lndetPrincipalAmount;
	
	private BigDecimal lndetIntAmount;
	
	private BigDecimal lndetBalPrincipalAmount;
	
	private Date sliDate;
	
	private BigDecimal receiptAmount;
	
	
	public String getLnNo() {
		return lnNo;
	}

	public void setLnNo(String lnNo) {
		this.lnNo = lnNo;
	}

	public BigDecimal getLndetPrincipalAmount() {
		return lndetPrincipalAmount;
	}

	public void setLndetPrincipalAmount(BigDecimal lndetPrincipalAmount) {
		this.lndetPrincipalAmount = lndetPrincipalAmount;
	}

	public BigDecimal getLndetIntAmount() {
		return lndetIntAmount;
	}

	public void setLndetIntAmount(BigDecimal lndetIntAmount) {
		this.lndetIntAmount = lndetIntAmount;
	}

	public BigDecimal getLndetBalPrincipalAmount() {
		return lndetBalPrincipalAmount;
	}

	public void setLndetBalPrincipalAmount(BigDecimal lndetBalPrincipalAmount) {
		this.lndetBalPrincipalAmount = lndetBalPrincipalAmount;
	}	

	public List<AccountLoanMasterDto> getListAccountLoanMasterDto() {
		return ListAccountLoanMasterDto;
	}

	public void setListAccountLoanMasterDto(List<AccountLoanMasterDto> listAccountLoanMasterDto) {
		ListAccountLoanMasterDto = listAccountLoanMasterDto;
	}

	public Date getSliDate() {
		return sliDate;
	}

	public void setSliDate(Date sliDate) {
		this.sliDate = sliDate;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getSanctionNo() {
		return SanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		SanctionNo = sanctionNo;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getNoOfInstallments() {
		return noOfInstallments;
	}

	public void setNoOfInstallments(Long noOfInstallments) {
		this.noOfInstallments = noOfInstallments;
	}

	public Long getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Long loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getLnDeptname() {
		return lnDeptname;
	}

	public void setLnDeptname(String lnDeptname) {
		this.lnDeptname = lnDeptname;
	}

	public String getLnPurpose() {
		return lnPurpose;
	}

	public void setLnPurpose(String lnPurpose) {
		this.lnPurpose = lnPurpose;
	}

	public Long getResNo() {
		return resNo;
	}

	public void setResNo(Long resNo) {
		this.resNo = resNo;
	}

	public Date getResDate() {
		return resDate;
	}

	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public BigDecimal getSantionAmount() {
		return santionAmount;
	}

	public void setSantionAmount(BigDecimal santionAmount) {
		this.santionAmount = santionAmount;
	}

/*	public int getLnInrate() {
		return lnInrate;
	}

	public void setLnInrate(int lnInrate) {
		this.lnInrate = lnInrate;
	}*/
	



	public Long getInstCount() {
		return instCount;
	}

	public BigDecimal getLnInrate() {
		return lnInrate;
	}

	public void setLnInrate(BigDecimal lnInrate) {
		this.lnInrate = lnInrate;
	}

	public void setInstCount(Long instCount) {
		this.instCount = instCount;
	}

	public Long getInstFreq() {
		return instFreq;
	}

	public void setInstFreq(Long instFreq) {
		this.instFreq = instFreq;
	}

	public Long getInstAmt() {
		return instAmt;
	}

	public void setInstAmt(Long instAmt) {
		this.instAmt = instAmt;
	}

	public Long getAuthBy() {
		return authBy;
	}

	public void setAuthBy(Long authBy) {
		this.authBy = authBy;
	}

	public String getLmRemark() {
		return lmRemark;
	}

	public void setLmRemark(String lmRemark) {
		this.lmRemark = lmRemark;
	}

	public char getLnStatus() {
		return lnStatus;
	}

	public void setLnStatus(char lnStatus) {
		this.lnStatus = lnStatus;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public List<AccountLoanDetDto> getAccountLoanDetList() {
		return accountLoanDetList;
	}

	public void setAccountLoanDetList(List<AccountLoanDetDto> accountLoanDetList) {
		this.accountLoanDetList = accountLoanDetList;
	}

	public Long getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public String getLoanPeriodUnit() {
		return loanPeriodUnit;
	}

	public void setLoanPeriodUnit(String loanPeriodUnit) {
		this.loanPeriodUnit = loanPeriodUnit;
	}
	
	public BigDecimal getAmountOfEachInstallment() {
		return amountOfEachInstallment;
	}

	public void setAmountOfEachInstallment(BigDecimal amountOfEachInstallment) {
		this.amountOfEachInstallment = amountOfEachInstallment;
	}

	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	

	
}
