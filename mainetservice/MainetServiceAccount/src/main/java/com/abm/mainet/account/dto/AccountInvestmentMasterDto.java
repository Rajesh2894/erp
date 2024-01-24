package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountInvestmentMasterDto implements Serializable {
	private static final long serialVersionUID = 8938258523463889570L;

	private Long invstId;

	private String invstNo;

	private Long invstType;

	private Long bankId;
	
	private String bankName;

	private String inFdrNo;

	private Date invstDate;

	private BigDecimal invstAmount;

	private Date invstDueDate;

	private BigDecimal instRate;

	private BigDecimal instAmt;

	private BigDecimal maturityAmt;

	private BigDecimal resNo;

	private Date resDate;

	private Long fundId;

	private String remarks;

	private String status;

	private Date closDate;

	private Long langId;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long categoryTypeId;
	
	private Date sliDate;
	
	private String resdate; 
	
	private String invdate; //fromdate
	
	private String invDueDate; //toDate
	
	private String fundName;
	
	private BigDecimal originalPrincipalAmt;
	
	
	private BigDecimal depositeAmt;
	
	private Long noOfTimesRenewal;
	//added for register
	private Date rmDate;
	
	private BigDecimal rmAmount;
	
	private Long accountNumber;
	
	private BigDecimal totPurchasePrice;
	private BigDecimal totAmtInteresDue;
	private BigDecimal totAmtInteresRecovered ;
	private BigDecimal totAmtReleased;
	
	
	public Date getSliDate() {
		return sliDate;
	}

	public void setSliDate(Date sliDate) {
		this.sliDate = sliDate;
	}

	public Long getInvstId() {
		return invstId;
	}

	public void setInvstId(Long invstId) {
		this.invstId = invstId;
	}

	public String getInvstNo() {
		return invstNo;
	}

	public void setInvstNo(String invstNo) {
		this.invstNo = invstNo;
	}

	public Long getInvstType() {
		return invstType;
	}

	public void setInvstType(Long invstType) {
		this.invstType = invstType;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getInFdrNo() {
		return inFdrNo;
	}

	public void setInFdrNo(String inFdrNo) {
		this.inFdrNo = inFdrNo;
	}

	public Date getInvstDate() {
		return invstDate;
	}

	public void setInvstDate(Date invstDate) {
		this.invstDate = invstDate;
	}

	public BigDecimal getInvstAmount() {
		return invstAmount;
	}

	public void setInvstAmount(BigDecimal invstAmount) {
		this.invstAmount = invstAmount;
	}

	public Date getInvstDueDate() {
		return invstDueDate;
	}

	public void setInvstDueDate(Date invstDueDate) {
		this.invstDueDate = invstDueDate;
	}

	public BigDecimal getInstRate() {
		return instRate;
	}

	public void setInstRate(BigDecimal instRate) {
		this.instRate = instRate;
	}

	public BigDecimal getInstAmt() {
		return instAmt;
	}

	public void setInstAmt(BigDecimal instAmt) {
		this.instAmt = instAmt;
	}

	public BigDecimal getMaturityAmt() {
		return maturityAmt;
	}

	public void setMaturityAmt(BigDecimal maturityAmt) {
		this.maturityAmt = maturityAmt;
	}

	public BigDecimal getResNo() {
		return resNo;
	}

	public void setResNo(BigDecimal resNo) {
		this.resNo = resNo;
	}

	public Date getResDate() {
		return resDate;
	}

	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getClosDate() {
		return closDate;
	}

	public void setClosDate(Date closDate) {
		this.closDate = closDate;
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
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getResdate() {
		return resdate;
	}

	public void setResdate(String resdate) {
		this.resdate = resdate;
	}

	public String getInvdate() {
		return invdate;
	}

	public void setInvdate(String invdate) {
		this.invdate = invdate;
	}

	public String getInvDueDate() {
		return invDueDate;
	}

	public void setInvDueDate(String invDueDate) {
		this.invDueDate = invDueDate;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
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

	public BigDecimal getOriginalPrincipalAmt() {
		return originalPrincipalAmt;
	}

	public void setOriginalPrincipalAmt(BigDecimal originalPrincipalAmt) {
		this.originalPrincipalAmt = originalPrincipalAmt;
	}

	public Long getNoOfTimesRenewal() {
		return noOfTimesRenewal;
	}

	public void setNoOfTimesRenewal(Long noOfTimesRenewal) {
		this.noOfTimesRenewal = noOfTimesRenewal;
	}

	public BigDecimal getDepositeAmt() {
		return depositeAmt;
	}

	public void setDepositeAmt(BigDecimal depositeAmt) {
		this.depositeAmt = depositeAmt;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getTotPurchasePrice() {
		return totPurchasePrice;
	}

	public void setTotPurchasePrice(BigDecimal totPurchasePrice) {
		this.totPurchasePrice = totPurchasePrice;
	}

	public BigDecimal getTotAmtInteresDue() {
		return totAmtInteresDue;
	}

	public void setTotAmtInteresDue(BigDecimal totAmtInteresDue) {
		this.totAmtInteresDue = totAmtInteresDue;
	}

	public BigDecimal getTotAmtInteresRecovered() {
		return totAmtInteresRecovered;
	}

	public void setTotAmtInteresRecovered(BigDecimal totAmtInteresRecovered) {
		this.totAmtInteresRecovered = totAmtInteresRecovered;
	}

	public BigDecimal getTotAmtReleased() {
		return totAmtReleased;
	}

	public void setTotAmtReleased(BigDecimal totAmtReleased) {
		this.totAmtReleased = totAmtReleased;
	}

}
