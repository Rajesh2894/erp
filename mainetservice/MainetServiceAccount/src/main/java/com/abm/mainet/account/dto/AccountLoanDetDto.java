package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class AccountLoanDetDto  implements Serializable{
	
	private static final long serialVersionUID = 2310856257401344850L;

	private Long lnDetId;
    
	private Long loanId;
	
	private Long instSeqno;

	private BigDecimal prnpalAmount;

	private BigDecimal intAmount;

	private Date instDueDate;

	private BigDecimal balPrnpalamt;

	private BigDecimal balIntAmt;

	private String instStatus;

	private Long langId;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private AccountLoanMasterDto lnmas;
	
	private AccountBillEntryMasterBean accountBillEntryMasterBean ;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
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

	public AccountLoanMasterDto getLnmas() {
		return lnmas;
	}

	public void setLnmas(AccountLoanMasterDto lnmas) {
		this.lnmas = lnmas;
	}

	public AccountBillEntryMasterBean getAccountBillEntryMasterBean() {
		return accountBillEntryMasterBean;
	}

	public void setAccountBillEntryMasterBean(AccountBillEntryMasterBean accountBillEntryMasterBean) {
		this.accountBillEntryMasterBean = accountBillEntryMasterBean;
	}
	

}
