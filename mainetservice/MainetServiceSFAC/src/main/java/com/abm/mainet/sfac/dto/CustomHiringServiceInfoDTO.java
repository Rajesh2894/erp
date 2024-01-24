package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomHiringServiceInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8832546327506582305L;
	
	private Long centerId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private String rentedItemName;
	
	private Long itemQuantity;
	
	private BigDecimal rentedAmount;
	
	private Date rentedFromDate;

	private Date rentedToDate;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getCenterId() {
		return centerId;
	}

	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public String getRentedItemName() {
		return rentedItemName;
	}

	public void setRentedItemName(String rentedItemName) {
		this.rentedItemName = rentedItemName;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Date getRentedFromDate() {
		return rentedFromDate;
	}

	public void setRentedFromDate(Date rentedFromDate) {
		this.rentedFromDate = rentedFromDate;
	}

	public Date getRentedToDate() {
		return rentedToDate;
	}

	public void setRentedToDate(Date rentedToDate) {
		this.rentedToDate = rentedToDate;
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

	public BigDecimal getRentedAmount() {
		return rentedAmount;
	}

	public void setRentedAmount(BigDecimal rentedAmount) {
		this.rentedAmount = rentedAmount;
	}
	
	

}
