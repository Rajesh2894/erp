package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MM_EXPIRED_DET")
public class ExpiryItemsDetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "expitemid")
	private Long expiryItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expiryid")
	private ExpiryItemsEntity expiryItemsEntity;

	@Column(name = "storeid")
	private Long storeId;

	@Column(name = "itemid")
	private Long itemId;

	@Column(name = "transactionid")
	private Long transactionId;

	@Column(name = "itemno")
	private String itemNo;

	@Column(name = "quantity")
	private Double quantity;

	@Column(name = "Flag")
	private String flag;

	@Column(name = "disposedflag")
	private String disposedFlag;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private int langId;

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
	
	@Column(name = "binlocation", nullable = true)
	private Long binLocation;
	
	public ExpiryItemsDetEntity() {}

	public Long getExpiryItemId() {
		return expiryItemId;
	}

	public void setExpiryItemId(Long expiryItemId) {
		this.expiryItemId = expiryItemId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDisposedFlag() {
		return disposedFlag;
	}

	public void setDisposedFlag(String disposedFlag) {
		this.disposedFlag = disposedFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
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

	public ExpiryItemsEntity getExpiryItemsEntity() {
		return expiryItemsEntity;
	}

	public void setExpiryItemsEntity(ExpiryItemsEntity expiryItemsEntity) {
		this.expiryItemsEntity = expiryItemsEntity;
	}

	public Long getBinLocation() {
		return binLocation;
	}

	public void setBinLocation(Long binLocation) {
		this.binLocation = binLocation;
	}

	public String[] getPkValues() {

		return new String[] { "MMM", "MM_EXPIRED_DET", "expitemid" };
	}
}
