package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemRateBidDetailDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long itemRateBidId;

	private Long bidId;

	private Long itemId;

	private String itemName;

	private Double quantity;

	private BigDecimal perUnitRate;

	private BigDecimal amount;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;
	
	public ItemRateBidDetailDto() {	}

	public ItemRateBidDetailDto(Long itemId, String itemName, Double quantity) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
	}

	public Long getItemRateBidId() {
		return itemRateBidId;
	}

	public void setItemRateBidId(Long itemRateBidId) {
		this.itemRateBidId = itemRateBidId;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

}
