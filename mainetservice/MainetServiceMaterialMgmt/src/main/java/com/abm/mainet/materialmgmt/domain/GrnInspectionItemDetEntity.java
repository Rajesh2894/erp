package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;

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
@Table(name = "mm_grn_items_entry")
public class GrnInspectionItemDetEntity implements Serializable {

	private static final long serialVersionUID = -7220184218438919206L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "grnitementryid")
	private Long grnitementryid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grnid")
	private GoodsReceivedNotesEntity goodsReceivedNote;

	/* @OneToOne(fetch=FetchType.LAZY) */
	@Column(name ="storeid", nullable = false)
	private Long storeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "itemId", nullable = false)
	private ItemMasterEntity itemMasterEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grnitemid")
	private GoodsreceivedNotesitemEntity itemEntity;

	@Column(name = "Itemno")
	private String itemNo;

	@Column(name = "quantity")
	private Double quantity;

	@Column(name = "decision", length = 1)
	private Character decision;

	@Column(name = "mfgdate", nullable = true)
	private Date mfgDate;

	@Column(name = "expirydate", nullable = true)
	private Date expiryDate;

	@Column(name = "rejectionreason", nullable = true)
	private Long rejectionReason;

	@Column(name = "binlocation", nullable = true)
	private Long binLocation;

	@Column(name = "returnid", nullable = true)
	private Long returnId;

	@Column(name = "Status", length = 1)
	private Character status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE", nullable = true)
	private Date lmoDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	public Long getGrnitementryid() {
		return grnitementryid;
	}

	public void setGrnitementryid(Long grnitementryid) {
		this.grnitementryid = grnitementryid;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Character getDecision() {
		return decision;
	}

	public void setDecision(Character decision) {
		this.decision = decision;
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

	public Long getBinLocation() {
		return binLocation;
	}

	public void setBinLocation(Long binLocation) {
		this.binLocation = binLocation;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

	public Date getLmoDate() {
		return lmoDate;
	}

	public void setLmoDate(Date lmoDate) {
		this.lmoDate = lmoDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public GoodsReceivedNotesEntity getGoodsReceivedNote() {
		return goodsReceivedNote;
	}

	public void setGoodsReceivedNote(GoodsReceivedNotesEntity goodsReceivedNote) {
		this.goodsReceivedNote = goodsReceivedNote;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
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

	public ItemMasterEntity getItemMasterEntity() {
		return itemMasterEntity;
	}

	public void setItemMasterEntity(ItemMasterEntity itemMasterEntity) {
		this.itemMasterEntity = itemMasterEntity;
	}

	public GoodsreceivedNotesitemEntity getItemEntity() {
		return itemEntity;
	}

	public void setItemEntity(GoodsreceivedNotesitemEntity itemEntity) {
		this.itemEntity = itemEntity;
	}

	public String[] getPkValues() {
		return new String[] { "ITM", "mm_grn_items_entry", "grnitementryid" };
	}

	

}
