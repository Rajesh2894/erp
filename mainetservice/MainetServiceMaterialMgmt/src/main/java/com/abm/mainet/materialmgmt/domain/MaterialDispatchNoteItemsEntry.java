package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mm_mdn_items_entry")
public class MaterialDispatchNoteItemsEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "mdnitementryid")
	private Long mdnItemEntryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mdnid")
	private MaterialDispatchNote materialDispatchNote;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mdnitemid")
	private MaterialDispatchNoteItems materialDispatchNoteItems;

	@Column(name = "issuestoreid")
	private Long issueStoreId;

	@Column(name = "itemid")
	private Long itemId;

	@Column(name = "issuebinlocation")
	private Long issueBinLocation;

	@Column(name = "Itemno")
	private String itemNo;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "acceptqty")
	private BigDecimal acceptQty;

	@Column(name = "rejectqty")
	private BigDecimal rejectQty;
	
	@Column(name = "mfgdate")
	private Date mfgDate;

	@Column(name = "expirydate")
	private Date expiryDate;

	@Column(name = "rejectionreason")
	private Long rejectionReason;

	@Column(name = "receivedbinlocation")
	private Long receivedBinLocation;

	@Column(name = "returnid")
	private Long returnId;

	@Column(name = "Status", length = 1)
	private String status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE")
	private Date lmodDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	public Long getMdnItemEntryId() {
		return mdnItemEntryId;
	}

	public void setMdnItemEntryId(Long mdnItemEntryId) {
		this.mdnItemEntryId = mdnItemEntryId;
	}

	public MaterialDispatchNote getMaterialDispatchNote() {
		return materialDispatchNote;
	}

	public void setMaterialDispatchNote(MaterialDispatchNote materialDispatchNote) {
		this.materialDispatchNote = materialDispatchNote;
	}

	public MaterialDispatchNoteItems getMaterialDispatchNoteItems() {
		return materialDispatchNoteItems;
	}

	public void setMaterialDispatchNoteItems(MaterialDispatchNoteItems materialDispatchNoteItems) {
		this.materialDispatchNoteItems = materialDispatchNoteItems;
	}

	public Long getIssueStoreId() {
		return issueStoreId;
	}

	public void setIssueStoreId(Long issueStoreId) {
		this.issueStoreId = issueStoreId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getIssueBinLocation() {
		return issueBinLocation;
	}

	public void setIssueBinLocation(Long issueBinLocation) {
		this.issueBinLocation = issueBinLocation;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAcceptQty() {
		return acceptQty;
	}

	public void setAcceptQty(BigDecimal acceptQty) {
		this.acceptQty = acceptQty;
	}

	public BigDecimal getRejectQty() {
		return rejectQty;
	}

	public void setRejectQty(BigDecimal rejectQty) {
		this.rejectQty = rejectQty;
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

	public Long getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(Long rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Long getReceivedBinLocation() {
		return receivedBinLocation;
	}

	public void setReceivedBinLocation(Long receivedBinLocation) {
		this.receivedBinLocation = receivedBinLocation;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
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
		return new String[] { "MMM", "mm_mdn_items_entry", "mdnitementryid" };
	}

}
