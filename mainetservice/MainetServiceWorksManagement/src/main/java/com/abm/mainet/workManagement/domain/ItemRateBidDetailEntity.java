package com.abm.mainet.workManagement.domain;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_wms_bid_itemrate_det")
public class ItemRateBidDetailEntity implements Serializable {

	private static final long serialVersionUID = 8143573924204714103L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ITEM_RATE_BID_ID", nullable = false)
	private Long itemRateBidId;

	@ManyToOne
	@JoinColumn(name = "BID_ID")
	private BIDMasterEntity bidMasterEntity;

	@Column(name = "ITEM_ID", nullable = true)
	private Long itemId;

	@Column(name = "QUANTITY", nullable = true)
	private Double quantity;

	@Column(name = "RATE", nullable = true)
	private BigDecimal perUnitRate;

	@Column(name = "AMOUNT", nullable = true)
	private BigDecimal amount;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	public Long getItemRateBidId() {
		return itemRateBidId;
	}

	public void setItemRateBidId(Long itemRateBidId) {
		this.itemRateBidId = itemRateBidId;
	}

	public BIDMasterEntity getBidMasterEntity() {
		return bidMasterEntity;
	}

	public void setBidMasterEntity(BIDMasterEntity bidMasterEntity) {
		this.bidMasterEntity = bidMasterEntity;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPerUnitRate() {
		return perUnitRate;
	}

	public void setPerUnitRate(BigDecimal perUnitRate) {
		this.perUnitRate = perUnitRate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public static String[] getPkValues() {
		return new String[] { "COM", "tb_wms_bid_itemrate_det", "ITEM_RATE_BID_ID" };
	}
}
