package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.acccount.dto.AccountReceiptDTO;

public class AccountLoanReportDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1035713776686522261L;
private Long loanId;
	
	private String lnNo;

	private String lnDeptname;

	private String lnPurpose;

	private Long resNo;

	private Date resDate;

	private Date sanctionDate;

	private BigDecimal santionAmount;
	
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
	
	private Long lnDetId;
    
	
	private Long instSeqno;

	private BigDecimal prnpalAmount;

	private BigDecimal intAmount;

	private Date instDueDate;

	private BigDecimal balPrnpalamt;

	private BigDecimal balIntAmt;

	private String instStatus;
	
	private String employeeName;
	
	
	
	//Receipt
	private Date rmDate;
	
	private BigDecimal rmAmount;
	
	//Bill
	private BigDecimal billTotalAmount;

	private BigDecimal balanceAmount;
	
	private Date billEntryDate;

	
	private AccountLoanMasterDto lnmas;
	
	List<AccountLoanReportDTO> accountLoanReportDTOList = new ArrayList <AccountLoanReportDTO> ();

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getLnNo() {
		return lnNo;
	}

	public void setLnNo(String lnNo) {
		this.lnNo = lnNo;
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

	public BigDecimal getAmountOfEachInstallment() {
		return amountOfEachInstallment;
	}

	public void setAmountOfEachInstallment(BigDecimal amountOfEachInstallment) {
		this.amountOfEachInstallment = amountOfEachInstallment;
	}

	public BigDecimal getLnInrate() {
		return lnInrate;
	}

	public void setLnInrate(BigDecimal lnInrate) {
		this.lnInrate = lnInrate;
	}

	public Long getInstCount() {
		return instCount;
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

	public Long getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(Long categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public Long getLnDetId() {
		return lnDetId;
	}

	public void setLnDetId(Long lnDetId) {
		this.lnDetId = lnDetId;
	}

	public Long getInstSeqno() {
		return instSeqno;
	}

	public void setInstSeqno(Long instSeqno) {
		this.instSeqno = instSeqno;
	}

	public BigDecimal getPrnpalAmount() {
		return prnpalAmount;
	}

	public void setPrnpalAmount(BigDecimal prnpalAmount) {
		this.prnpalAmount = prnpalAmount;
	}

	public BigDecimal getIntAmount() {
		return intAmount;
	}

	public void setIntAmount(BigDecimal intAmount) {
		this.intAmount = intAmount;
	}

	public Date getInstDueDate() {
		return instDueDate;
	}

	public void setInstDueDate(Date instDueDate) {
		this.instDueDate = instDueDate;
	}

	public BigDecimal getBalPrnpalamt() {
		return balPrnpalamt;
	}

	public void setBalPrnpalamt(BigDecimal balPrnpalamt) {
		this.balPrnpalamt = balPrnpalamt;
	}

	public BigDecimal getBalIntAmt() {
		return balIntAmt;
	}

	public void setBalIntAmt(BigDecimal balIntAmt) {
		this.balIntAmt = balIntAmt;
	}

	public String getInstStatus() {
		return instStatus;
	}

	public void setInstStatus(String instStatus) {
		this.instStatus = instStatus;
	}

	public AccountLoanMasterDto getLnmas() {
		return lnmas;
	}

	public void setLnmas(AccountLoanMasterDto lnmas) {
		this.lnmas = lnmas;
	}

	public List<AccountLoanReportDTO> getAccountLoanReportDTOList() {
		return accountLoanReportDTOList;
	}

	public void setAccountLoanReportDTOList(List<AccountLoanReportDTO> accountLoanReportDTOList) {
		this.accountLoanReportDTOList = accountLoanReportDTOList;
	}

	public Date getRmDate() {
		return rmDate;
	}

	public void setRmDate(Date rmDate) {
		this.rmDate = rmDate;
	}

	public BigDecimal getRmAmount() {
		return rmAmount;
	}

	public void setRmAmount(BigDecimal rmAmount) {
		this.rmAmount = rmAmount;
	}

	public BigDecimal getBillTotalAmount() {
		return billTotalAmount;
	}

	public void setBillTotalAmount(BigDecimal billTotalAmount) {
		this.billTotalAmount = billTotalAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Date getBillEntryDate() {
		return billEntryDate;
	}

	public void setBillEntryDate(Date billEntryDate) {
		this.billEntryDate = billEntryDate;
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

	public String getLoanPeriodUnit() {
		return loanPeriodUnit;
	}

	public void setLoanPeriodUnit(String loanPeriodUnit) {
		this.loanPeriodUnit = loanPeriodUnit;
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

	public Date getSliDate() {
		return sliDate;
	}

	public void setSliDate(Date sliDate) {
		this.sliDate = sliDate;
	}

	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	

}