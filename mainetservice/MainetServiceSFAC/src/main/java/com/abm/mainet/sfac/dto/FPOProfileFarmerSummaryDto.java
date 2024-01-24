package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FPOProfileFarmerSummaryDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4670153664332731905L;
	
	
	private Long fsID;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	

	private Date dateOfEntry;
	
	private Long noOFSmallFarmerSH;
	
	private Long noOFMarginalFarmerSH;
	
	private Long noOFLandlessSH;
	
	private Long noOFTenantFarmer;
	
	private Long totalSharehold;
	
	private Long noOFWomenSH;
	
	
	private Long noOFSCSH;
	
	private Long noOFSTSH;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getFsID() {
		return fsID;
	}

	public void setFsID(Long fsID) {
		this.fsID = fsID;
	}

	

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public Date getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public Long getNoOFSmallFarmerSH() {
		return noOFSmallFarmerSH;
	}

	public void setNoOFSmallFarmerSH(Long noOFSmallFarmerSH) {
		this.noOFSmallFarmerSH = noOFSmallFarmerSH;
	}

	public Long getNoOFMarginalFarmerSH() {
		return noOFMarginalFarmerSH;
	}

	public void setNoOFMarginalFarmerSH(Long noOFMarginalFarmerSH) {
		this.noOFMarginalFarmerSH = noOFMarginalFarmerSH;
	}

	public Long getNoOFLandlessSH() {
		return noOFLandlessSH;
	}

	public void setNoOFLandlessSH(Long noOFLandlessSH) {
		this.noOFLandlessSH = noOFLandlessSH;
	}

	public Long getNoOFTenantFarmer() {
		return noOFTenantFarmer;
	}

	public void setNoOFTenantFarmer(Long noOFTenantFarmer) {
		this.noOFTenantFarmer = noOFTenantFarmer;
	}

	public Long getTotalSharehold() {
		return totalSharehold;
	}

	public void setTotalSharehold(Long totalSharehold) {
		this.totalSharehold = totalSharehold;
	}

	public Long getNoOFWomenSH() {
		return noOFWomenSH;
	}

	public void setNoOFWomenSH(Long noOFWomenSH) {
		this.noOFWomenSH = noOFWomenSH;
	}

	public Long getNoOFSCSH() {
		return noOFSCSH;
	}

	public void setNoOFSCSH(Long noOFSCSH) {
		this.noOFSCSH = noOFSCSH;
	}

	public Long getNoOFSTSH() {
		return noOFSTSH;
	}

	public void setNoOFSTSH(Long noOFSTSH) {
		this.noOFSTSH = noOFSTSH;
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
