package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SubsidiesInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7026454976541647810L;
	
	private Long subsidiesId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private String schemeName;
	
	private BigDecimal subsidiesAmount;
	
	private String schemeAgency;
	
	private BigDecimal totalAmount;
	
	private BigDecimal amountPaidByFarmer;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getSubsidiesId() {
		return subsidiesId;
	}

	public void setSubsidiesId(Long subsidiesId) {
		this.subsidiesId = subsidiesId;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public BigDecimal getSubsidiesAmount() {
		return subsidiesAmount;
	}

	public void setSubsidiesAmount(BigDecimal subsidiesAmount) {
		this.subsidiesAmount = subsidiesAmount;
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

	public String getSchemeAgency() {
		return schemeAgency;
	}

	public void setSchemeAgency(String schemeAgency) {
		this.schemeAgency = schemeAgency;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAmountPaidByFarmer() {
		return amountPaidByFarmer;
	}

	public void setAmountPaidByFarmer(BigDecimal amountPaidByFarmer) {
		this.amountPaidByFarmer = amountPaidByFarmer;
	}


	

}
