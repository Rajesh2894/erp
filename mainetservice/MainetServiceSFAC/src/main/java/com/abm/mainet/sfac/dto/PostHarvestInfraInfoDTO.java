package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PostHarvestInfraInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -250782443757231270L;
	
	private Long postharvestInfraId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private String storageType;
	
	private Long storageCapicity;
	
	private String phpDescription;
	
	private String processing;
	
	private String qualityAnalysis;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getPostharvestInfraId() {
		return postharvestInfraId;
	}

	public void setPostharvestInfraId(Long postharvestInfraId) {
		this.postharvestInfraId = postharvestInfraId;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public Long getStorageCapicity() {
		return storageCapicity;
	}

	public void setStorageCapicity(Long storageCapicity) {
		this.storageCapicity = storageCapicity;
	}

	public String getPhpDescription() {
		return phpDescription;
	}

	public void setPhpDescription(String phpDescription) {
		this.phpDescription = phpDescription;
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

	public String getProcessing() {
		return processing;
	}

	public void setProcessing(String processing) {
		this.processing = processing;
	}

	public String getQualityAnalysis() {
		return qualityAnalysis;
	}

	public void setQualityAnalysis(String qualityAnalysis) {
		this.qualityAnalysis = qualityAnalysis;
	}
	
	

}
