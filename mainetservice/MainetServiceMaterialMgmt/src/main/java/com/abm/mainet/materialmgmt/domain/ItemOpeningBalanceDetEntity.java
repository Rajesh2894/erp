package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MM_ITEMOPENINGBALANCE_DET")
public class ItemOpeningBalanceDetEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "OPENBALDETID", nullable = false)
	private Long openBalDetId;
	
	@ManyToOne
	@JoinColumn(name = "BINLOCATION", nullable = false)
	private BinLocMasEntity binLocMasEntity;
	
	@ManyToOne
	@JoinColumn(name = "OPENBALID", nullable = false)
	private ItemOpeningBalanceEntity openingBalanceEntity;
	
	@Column(name = "ITEMID",nullable = false)
	private Long itemId;
	
	@Column(name = "ITEMNO",nullable = false)
	private String itemNo;
	
	@Column(name = "QUANTITY",nullable = false)
	private BigDecimal quantity;
	
	@Column(name = "STATUS",nullable = false)
	private String status;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "USER_ID" , nullable = false)
	private Long userId;

	@Column(name = "LANGID", nullable = false)
	private Long langId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LMODDATE", nullable = false)
	private Date lmoddate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@JsonIgnore
	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@JsonIgnore
	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "MFGDATE",nullable = false)
	private Date mfgDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRYDATE",nullable = false)
	private Date expiryDate;
	
	@Column(name = "ACTIVE")
	private boolean active;
	
	public BinLocMasEntity getBinLocMasEntity() {
		return binLocMasEntity;
	}

	public Long getOpenBalDetId() {
		return openBalDetId;
	}

	public void setOpenBalDetId(Long openBalDetId) {
		this.openBalDetId = openBalDetId;
	}

	public void setBinLocMasEntity(BinLocMasEntity binLocMasEntity) {
		this.binLocMasEntity = binLocMasEntity;
	}

	public ItemOpeningBalanceEntity getOpeningBalanceEntity() {
		return openingBalanceEntity;
	}

	public void setOpeningBalanceEntity(ItemOpeningBalanceEntity openingBalanceEntity) {
		this.openingBalanceEntity = openingBalanceEntity;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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

	public static String[] getPkValues() {
		return new String[] { "MM", "MM_ITEMOPENINGBALANCE_DET", "OPENBALDETID" };
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
