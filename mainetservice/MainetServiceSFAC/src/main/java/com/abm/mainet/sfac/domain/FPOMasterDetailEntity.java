/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "Tb_SFAC_Fpo_Master_Det")
public class FPOMasterDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6900432773774446399L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FPOC_ID", unique = true, nullable = false)
	private Long fpocId;

	@ManyToOne
	@JoinColumn(name = "FPO_ID", referencedColumnName = "FPO_ID")
	private FPOMasterEntity masterEntity;

	@Column(name = "CROP_TYPE")
	private Long cropType;

	@Column(name = "CROP_SEASON")
	private Long cropSeason;

	@Column(name = "CROP_NAME")
	private String cropName;

	@Column(name = "PRIMARY_SECONDARY_CROP", nullable = true)
	private Long priSecCrop;

	@Column(name = "APPROVED_BY_DMC", nullable = true)
	private Long approvedByDmc;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "APP_STATUS")
	private String appStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

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
	 * @return the masterEntity
	 */
	public FPOMasterEntity getMasterEntity() {
		return masterEntity;
	}

	/**
	 * @param masterEntity the masterEntity to set
	 */
	public void setMasterEntity(FPOMasterEntity masterEntity) {
		this.masterEntity = masterEntity;
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

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Fpo_Master_Det", "FPOC_ID" };
	}
}
