package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommonTransactionDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long transactionId;

	private Date transactionDate;

	private Long storeId;

	private String transactionType;

	private String referenceNo;

	private Long itemId;

	private Long binLocation;

	private Long openingBal;

	private String itemNo;

	private Long uom;

	private BigDecimal debitQuantity;

	private BigDecimal creditQuantity;

	private BigDecimal closingBal;

	private BigDecimal unitPrice;

	private BigDecimal transctionAmt;

	private Date mfgDate;

	private Date expiryDate;

	private String remarks;

	private String status;

	private String disposalStatus;

	private Date disposalDate;

	private Long orgId;

	private Long userId;

	private Long langId;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String wfFlag;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getBinLocation() {
		return binLocation;
	}

	public void setBinLocation(Long binLocation) {
		this.binLocation = binLocation;
	}

	public Long getOpeningBal() {
		return openingBal;
	}

	public void setOpeningBal(Long openingBal) {
		this.openingBal = openingBal;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Long getUom() {
		return uom;
	}

	public void setUom(Long uom) {
		this.uom = uom;
	}

	public BigDecimal getDebitQuantity() {
		return debitQuantity;
	}

	public void setDebitQuantity(BigDecimal debitQuantity) {
		this.debitQuantity = debitQuantity;
	}

	public BigDecimal getCreditQuantity() {
		return creditQuantity;
	}

	public void setCreditQuantity(BigDecimal creditQuantity) {
		this.creditQuantity = creditQuantity;
	}

	public BigDecimal getClosingBal() {
		return closingBal;
	}

	public void setClosingBal(BigDecimal closingBal) {
		this.closingBal = closingBal;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTransctionAmt() {
		return transctionAmt;
	}

	public void setTransctionAmt(BigDecimal transctionAmt) {
		this.transctionAmt = transctionAmt;
	}

	public Date getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(Date mfgDate) {
		this.mfgDate = mfgDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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

	public String getDisposalStatus() {
		return disposalStatus;
	}

	public void setDisposalStatus(String disposalStatus) {
		this.disposalStatus = disposalStatus;
	}

	public Date getDisposalDate() {
		return disposalDate;
	}

	public void setDisposalDate(Date disposalDate) {
		this.disposalDate = disposalDate;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public String getWfFlag() {
		return wfFlag;
	}

	public void setWfFlag(String wfFlag) {
		this.wfFlag = wfFlag;
	}

}
