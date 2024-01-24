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
@Table(name = "Tb_Sfac_FPOProfile_Farmer_Info_Detail")
public class FPOProfileFarmerSummaryDetEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7528208492468114352L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FS_ID", nullable = false)
	private Long fsID;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_ENTRY")
	private Date dateOfEntry;
	
	@Column(name = "NO_OF_SMALL_FARMER_SH")
	private Long noOFSmallFarmerSH;
	
	@Column(name = "NO_OF_MAR_FARMER_SH")
	private Long noOFMarginalFarmerSH;
	
	@Column(name = "NO_OF_LANDLESS_SH")
	private Long noOFLandlessSH;
	
	@Column(name = "NO_OF_TENANT_FARMER")
	private Long noOFTenantFarmer;
	
	@Column(name = "TOTAL_SH")
	private Long totalSharehold;
	
	@Column(name = "NO_OF_WOMEN_SH")
	private Long noOFWomenSH;
	
	
	@Column(name = "NO_OF_SC_SH")
	private Long noOFSCSH;
	
	@Column(name = "NO_OF_ST_SH")
	private Long noOFSTSH;
	
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

	public Long getFsID() {
		return fsID;
	}

	public void setFsID(Long fsID) {
		this.fsID = fsID;
	}

	public FPOProfileManagementMaster getFPOProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}

	public void setFPOProfileMgmtMaster(FPOProfileManagementMaster fPOProfileMgmtMaster) {
		fpoProfileMgmtMaster = fPOProfileMgmtMaster;
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
	
	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_FPOProfile_Farmer_Info_Detail", "FS_ID" };
	}

}
