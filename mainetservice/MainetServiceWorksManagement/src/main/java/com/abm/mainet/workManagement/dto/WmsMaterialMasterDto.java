package com.abm.mainet.workManagement.dto;

import java.math.BigDecimal;
import java.util.Date;

public class WmsMaterialMasterDto {

	private Long maId;

	private Long sorId;

	private String maItemNo;

	private String maDescription;

	private Long maItemUnit;
	
	private String unitName;

	private BigDecimal maRate;

	private String maActive;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Long maTypeId;
	
	private String rateType;

	public Long getMaId() {
		return maId;
	}

	public void setMaId(Long maId) {
		this.maId = maId;
	}

	public Long getSorId() {
		return sorId;
	}

	public void setSorId(Long sorId) {
		this.sorId = sorId;
	}

	public String getMaItemNo() {
		return maItemNo;
	}

	public void setMaItemNo(String maItemNo) {
		this.maItemNo = maItemNo;
	}

	public String getMaDescription() {
		return maDescription;
	}

	public void setMaDescription(String maDescription) {
		this.maDescription = maDescription;
	}

	public Long getMaItemUnit() {
		return maItemUnit;
	}

	public void setMaItemUnit(Long maItemUnit) {
		this.maItemUnit = maItemUnit;
	}

	public BigDecimal getMaRate() {
		return maRate;
	}

	public void setMaRate(BigDecimal maRate) {
		this.maRate = maRate;
	}

	public String getMaActive() {
		return maActive;
	}

	public void setMaActive(String maActive) {
		this.maActive = maActive;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public Long getMaTypeId() {
		return maTypeId;
	}

	public void setMaTypeId(Long maTypeId) {
		this.maTypeId = maTypeId;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
