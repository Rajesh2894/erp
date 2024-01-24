package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.Date;

public class CFCNursingHomeInfoDetailDTO implements Serializable {

	private static final long serialVersionUID = -4864051669172865955L;

	private Long hospDetId;

	private Long hosp_id;

	private Long programId;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;
	
	private CFCNursingHomeInfoDTO cfcHospitalInfoDTO = new CFCNursingHomeInfoDTO();

	public CFCNursingHomeInfoDTO getCfcHospitalInfoDTO() {
		return cfcHospitalInfoDTO;
	}

	public void setCfcHospitalInfoDTO(CFCNursingHomeInfoDTO cfcHospitalInfoDTO) {
		this.cfcHospitalInfoDTO = cfcHospitalInfoDTO;
	}

	public Long getHospDetId() {
		return hospDetId;
	}

	public void setHospDetId(Long hospDetId) {
		this.hospDetId = hospDetId;
	}

	public Long getHosp_id() {
		return hosp_id;
	}

	public void setHosp_id(Long hosp_id) {
		this.hosp_id = hosp_id;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

}
