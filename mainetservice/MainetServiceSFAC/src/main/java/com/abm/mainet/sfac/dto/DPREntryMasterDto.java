package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DPREntryMasterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1507220828243359467L;
	
	private Long dprId;
	
	private long cbboId;
	
	private String cbboName;
	
	private long fpoId;
	
	private String fpoName;
	
	private Long iaId;
	
	private String iaName;
	
	private Date dateOfSubmission;
	
	private Date dateOfResubmission;
	
	private Long applicationNumber;
	
	private String status;
	
	private String authRemark;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	
	@JsonIgnore
	private List<DPREntryDetailsDto> dprEntryDetailsDtos = new ArrayList<>();

	public Long getDprId() {
		return dprId;
	}

	public void setDprId(Long dprId) {
		this.dprId = dprId;
	}

	public long getCbboId() {
		return cbboId;
	}

	public void setCbboId(long cbboId) {
		this.cbboId = cbboId;
	}

	public String getCbboName() {
		return cbboName;
	}

	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	public long getFpoId() {
		return fpoId;
	}

	public void setFpoId(long fpoId) {
		this.fpoId = fpoId;
	}

	public String getFpoName() {
		return fpoName;
	}

	public void setFpoName(String fpoName) {
		this.fpoName = fpoName;
	}

	public Long getIaId() {
		return iaId;
	}

	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	public String getIaName() {
		return iaName;
	}

	public void setIaName(String iaName) {
		this.iaName = iaName;
	}

	public Date getDateOfSubmission() {
		return dateOfSubmission;
	}

	public void setDateOfSubmission(Date dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}

	public Date getDateOfResubmission() {
		return dateOfResubmission;
	}

	public void setDateOfResubmission(Date dateOfResubmission) {
		this.dateOfResubmission = dateOfResubmission;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
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

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<DPREntryDetailsDto> getDprEntryDetailsDtos() {
		return dprEntryDetailsDtos;
	}

	public void setDprEntryDetailsDtos(List<DPREntryDetailsDto> dprEntryDetailsDtos) {
		this.dprEntryDetailsDtos = dprEntryDetailsDtos;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}
	
	

}
