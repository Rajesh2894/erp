/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
public class FPOMasterDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6271269352714722605L;

	private Long fpocId;

	@JsonIgnore
	private FPOMasterDto masterDto;

	private Long cropSeason;

	private Long cropType;

	private String cropName;

	private String iaOdop;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long priSecCrop;

	private Long approvedByDmc;

	private Long applicationId;

	private String appStatus;

	private String cropSeasonDesc;

	private String cropTypeDesc;
	private String priSecCropDesc;
	private String approvedByDmcDesc;

	/**
	 * @return the fpocId
	 */
	public Long getFpocId() {
		return fpocId;
	}

	/**
	 * @param fpocId the fpocId to set
	 */
	public void setFpocId(Long fpocId) {
		this.fpocId = fpocId;
	}

	/**
	 * @return the masterDto
	 */
	public FPOMasterDto getMasterDto() {
		return masterDto;
	}

	/**
	 * @param masterDto the masterDto to set
	 */
	public void setMasterDto(FPOMasterDto masterDto) {
		this.masterDto = masterDto;
	}

	/**
	 * @return the cropSeason
	 */
	public Long getCropSeason() {
		return cropSeason;
	}

	/**
	 * @param cropSeason the cropSeason to set
	 */
	public void setCropSeason(Long cropSeason) {
		this.cropSeason = cropSeason;
	}

	/**
	 * @return the cropType
	 */
	public Long getCropType() {
		return cropType;
	}

	/**
	 * @param cropType the cropType to set
	 */
	public void setCropType(Long cropType) {
		this.cropType = cropType;
	}

	/**
	 * @return the cropName
	 */
	public String getCropName() {
		return cropName;
	}

	/**
	 * @param cropName the cropName to set
	 */
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	/**
	 * @return the iaOdop
	 */
	public String getIaOdop() {
		return iaOdop;
	}

	/**
	 * @param iaOdop the iaOdop to set
	 */
	public void setIaOdop(String iaOdop) {
		this.iaOdop = iaOdop;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	/**
	 * @return the priSecCrop
	 */
	public Long getPriSecCrop() {
		return priSecCrop;
	}

	/**
	 * @param priSecCrop the priSecCrop to set
	 */
	public void setPriSecCrop(Long priSecCrop) {
		this.priSecCrop = priSecCrop;
	}

	/**
	 * @return the approvedByDmc
	 */
	public Long getApprovedByDmc() {
		return approvedByDmc;
	}

	/**
	 * @param approvedByDmc the approvedByDmc to set
	 */
	public void setApprovedByDmc(Long approvedByDmc) {
		this.approvedByDmc = approvedByDmc;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	/**
	 * @return the cropSeasonDesc
	 */
	public String getCropSeasonDesc() {
		return cropSeasonDesc;
	}

	/**
	 * @param cropSeasonDesc the cropSeasonDesc to set
	 */
	public void setCropSeasonDesc(String cropSeasonDesc) {
		this.cropSeasonDesc = cropSeasonDesc;
	}

	/**
	 * @return the cropTypeDesc
	 */
	public String getCropTypeDesc() {
		return cropTypeDesc;
	}

	/**
	 * @param cropTypeDesc the cropTypeDesc to set
	 */
	public void setCropTypeDesc(String cropTypeDesc) {
		this.cropTypeDesc = cropTypeDesc;
	}

	/**
	 * @return the priSecCropDesc
	 */
	public String getPriSecCropDesc() {
		return priSecCropDesc;
	}

	/**
	 * @param priSecCropDesc the priSecCropDesc to set
	 */
	public void setPriSecCropDesc(String priSecCropDesc) {
		this.priSecCropDesc = priSecCropDesc;
	}

	/**
	 * @return the approvedByDmcDesc
	 */
	public String getApprovedByDmcDesc() {
		return approvedByDmcDesc;
	}

	/**
	 * @param approvedByDmcDesc the approvedByDmcDesc to set
	 */
	public void setApprovedByDmcDesc(String approvedByDmcDesc) {
		this.approvedByDmcDesc = approvedByDmcDesc;
	}

}
