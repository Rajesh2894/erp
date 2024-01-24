package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FPOProfileTrainingDetailDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8518747741835805699L;
	
	private Long tiID;
	
	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private Date dateOfTraining;
	
	private String trainingName;
	
	private Long noOFCBO;
	
	private Long noOFBODSInvolved;
	
	private Long noOFBODSConduct;
	
	private Long noOFAccountantTraningConduct;
	
	private Long noOFTrainingCompleteFPO;
	
	private Long noOFSHFTraining;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getTiID() {
		return tiID;
	}

	public void setTiID(Long tiID) {
		this.tiID = tiID;
	}

	
	
	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
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

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	
	

}
