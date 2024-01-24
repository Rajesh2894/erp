package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductionInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6075998160985925332L;
	
	private Long productionId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private String commodityName;
	
	private Long itemQuantity;
	
	private Long unit;
	
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getProductionId() {
		return productionId;
	}

	public void setProductionId(Long productionId) {
		this.productionId = productionId;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Long getUnit() {
		return unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	
}
