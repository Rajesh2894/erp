package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EquipmentInfoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7149899617574833697L;
	
	
	private Long eqpId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private String equipmentName;
	
	private Long noOfEquipment;
	
	private BigDecimal priceOfEquipment;
	
	private String equipmentDesc;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getEqpId() {
		return eqpId;
	}

	public void setEqpId(Long eqpId) {
		this.eqpId = eqpId;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public Long getNoOfEquipment() {
		return noOfEquipment;
	}

	public void setNoOfEquipment(Long noOfEquipment) {
		this.noOfEquipment = noOfEquipment;
	}

	public BigDecimal getPriceOfEquipment() {
		return priceOfEquipment;
	}

	public void setPriceOfEquipment(BigDecimal priceOfEquipment) {
		this.priceOfEquipment = priceOfEquipment;
	}

	public String getEquipmentDesc() {
		return equipmentDesc;
	}

	public void setEquipmentDesc(String equipmentDesc) {
		this.equipmentDesc = equipmentDesc;
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
