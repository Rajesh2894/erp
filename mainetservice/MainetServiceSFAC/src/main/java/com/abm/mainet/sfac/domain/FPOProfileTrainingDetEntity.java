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
@Table(name = "Tb_Sfac_FPOProfile_Training_Info_Detail")
public class FPOProfileTrainingDetEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5492346089552400852L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "TI_ID", nullable = false)
	private Long tiID;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_TRAINING")
	private Date dateOfTraining;
	
	@Column(name = "TRAINING_NAME")
	private String trainingName;
	
	@Column(name = "NO_OF_CBO")
	private Long noOFCBO;
	
	@Column(name = "NO_OF_TRAININF_BODS_INV")
	private Long noOFBODSInvolved;
	
	@Column(name = "NO_OF_TRAINING_BODS_CON")
	private Long noOFBODSConduct;
	
	@Column(name = "NO_OF_TRAINING_ACC")
	private Long noOFAccountantTraningConduct;
	
	@Column(name = "NO_OF_TRAINING_COMP_FPO")
	private Long noOFTrainingCompleteFPO;
	
	@Column(name = "NO_OF_SHF_TRAINING")
	private Long noOFSHFTraining;
	
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
	
	
	public Long getTiID() {
		return tiID;
	}

	public void setTiID(Long tiID) {
		this.tiID = tiID;
	}

	public FPOProfileManagementMaster getFPOProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}

	public void setFPOProfileMgmtMaster(FPOProfileManagementMaster fPOProfileMgmtMaster) {
		fpoProfileMgmtMaster = fPOProfileMgmtMaster;
	}

	public Date getDateOfTraining() {
		return dateOfTraining;
	}

	public void setDateOfTraining(Date dateOfTraining) {
		this.dateOfTraining = dateOfTraining;
	}

	public Long getNoOFCBO() {
		return noOFCBO;
	}

	public void setNoOFCBO(Long noOFCBO) {
		this.noOFCBO = noOFCBO;
	}

	public Long getNoOFBODSInvolved() {
		return noOFBODSInvolved;
	}

	public void setNoOFBODSInvolved(Long noOFBODSInvolved) {
		this.noOFBODSInvolved = noOFBODSInvolved;
	}

	public Long getNoOFBODSConduct() {
		return noOFBODSConduct;
	}

	public void setNoOFBODSConduct(Long noOFBODSConduct) {
		this.noOFBODSConduct = noOFBODSConduct;
	}

	public Long getNoOFAccountantTraningConduct() {
		return noOFAccountantTraningConduct;
	}

	public void setNoOFAccountantTraningConduct(Long noOFAccountantTraningConduct) {
		this.noOFAccountantTraningConduct = noOFAccountantTraningConduct;
	}

	public Long getNoOFTrainingCompleteFPO() {
		return noOFTrainingCompleteFPO;
	}

	public void setNoOFTrainingCompleteFPO(Long noOFTrainingCompleteFPO) {
		this.noOFTrainingCompleteFPO = noOFTrainingCompleteFPO;
	}

	public Long getNoOFSHFTraining() {
		return noOFSHFTraining;
	}

	public void setNoOFSHFTraining(Long noOFSHFTraining) {
		this.noOFSHFTraining = noOFSHFTraining;
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
	
	

	
	public FPOProfileManagementMaster getFpoProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}

	public void setFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileMgmtMaster) {
		this.fpoProfileMgmtMaster = fpoProfileMgmtMaster;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_FPOProfile_Training_Info_Detail", "TI_ID" };
	}
}
