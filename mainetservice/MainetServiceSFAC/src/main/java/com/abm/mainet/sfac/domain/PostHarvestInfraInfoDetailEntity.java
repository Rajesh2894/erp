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

@Entity
@Table(name = "Tb_Sfac_PostHarvest_Infra_Info_Detail")
public class PostHarvestInfraInfoDetailEntity implements Serializable {
	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8596114686173881660L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "POHI_ID", nullable = false)
	private Long postharvestInfraId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Column(name="Storage_Type")
	private String storageType;
	
	@Column(name="PROCESSING")
	private String processing;
	
	@Column(name="QUALITY_ANALYSIS")
	private String qualityAnalysis;
	
	@Column(name="Storage_Capicity")
	private Long storageCapicity;
	
	@Column(name="PHP_DESC")
	private String phpDescription;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

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




	public Long getPostharvestInfraId() {
		return postharvestInfraId;
	}




	public void setPostharvestInfraId(Long postharvestInfraId) {
		this.postharvestInfraId = postharvestInfraId;
	}



	public FPOProfileManagementMaster getFpoProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}




	public void setFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileMgmtMaster) {
		this.fpoProfileMgmtMaster = fpoProfileMgmtMaster;
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




	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_PostHarvest_Infra_Info_Detail", "POHI_ID" };
	}

}
