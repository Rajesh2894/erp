package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mm_transactions")
public class CommonTransactionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "transactionid")
	private Long transactionId;

	@Column(name = "transactiondate")
	private Date transactionDate;

	@Column(name = "storeid")
	private Long storeId;

	@Column(name = "transactiontype", length = 1)
	private String transactionType;

	@Column(name = "refno", length = 1)
	private String referenceNo;

	@Column(name = "itemid")
	private Long itemId;

	@Column(name = "binlocation")
	private Long binLocation;

	@Column(name = "openingbal")
	private Long openingBal;

	@Column(name = "itemno")
	private String itemNo;

	@Column(name = "uom")
	private Long uom;

	@Column(name = "debitqty")
	private BigDecimal debitQuantity;

	@Column(name = "creditqty")
	private BigDecimal creditQuantity;

	@Column(name = "closingbal")
	private BigDecimal closingBal;

	@Column(name = "unitprice")
	private BigDecimal unitPrice;

	@Column(name = "transctionamt")
	private BigDecimal transctionAmt;

	@Column(name = "mfgdate")
	private Date mfgDate;

	@Column(name = "expirydate")
	private Date expiryDate;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "Status", length = 1)
	private String status;

	@Column(name = "disposalstatus", length = 1)
	private String disposalStatus;

	@Column(name = "disposaldate")
	private Date disposalDate;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Column(name = "LMODDATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

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

	public String[] getPkValues() {
		return new String[] { "MMM", "mm_transactions", "transactionid" };
	}

}
