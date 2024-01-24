package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuditBalanceSheetMasterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4471954232828505436L;
	
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	
	private Long absId;

	private Long applicationId;
	
	private String absStatus;

	private String remark;

	private Long cbboId;

	private String cbboName;

	private Long fpoId;

	private String fpoName;
	
	private String fpoAddress;
	
	private Date crpDateFrom;
	
	private Date crpDateTo;
	
	private Date prpDateFrom;
	
	private Date prpDateTo;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	@JsonIgnore
	private List<AuditBalanceSheetKeyParameterDto> auditBalanceSheetKeyParameterDtos = new ArrayList<>();

	public Long getAbsId() {
		return absId;
	}

	public void setAbsId(Long absId) {
		this.absId = absId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getAbsStatus() {
		return absStatus;
	}

	public void setAbsStatus(String absStatus) {
		this.absStatus = absStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCbboId() {
		return cbboId;
	}

	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	public String getCbboName() {
		return cbboName;
	}

	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	public Long getFpoId() {
		return fpoId;
	}

	public void setFpoId(Long fpoId) {
		this.fpoId = fpoId;
	}

	public String getFpoName() {
		return fpoName;
	}

	public void setFpoName(String fpoName) {
		this.fpoName = fpoName;
	}

	public String getFpoAddress() {
		return fpoAddress;
	}

	public void setFpoAddress(String fpoAddress) {
		this.fpoAddress = fpoAddress;
	}

	public Date getCrpDateFrom() {
		return crpDateFrom;
	}

	public void setCrpDateFrom(Date crpDateFrom) {
		this.crpDateFrom = crpDateFrom;
	}

	public Date getCrpDateTo() {
		return crpDateTo;
	}

	public void setCrpDateTo(Date crpDateTo) {
		this.crpDateTo = crpDateTo;
	}

	public Date getPrpDateFrom() {
		return prpDateFrom;
	}

	public void setPrpDateFrom(Date prpDateFrom) {
		this.prpDateFrom = prpDateFrom;
	}

	public Date getPrpDateTo() {
		return prpDateTo;
	}

	public void setPrpDateTo(Date prpDateTo) {
		this.prpDateTo = prpDateTo;
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

	public List<AuditBalanceSheetKeyParameterDto> getAuditBalanceSheetKeyParameterDtos() {
		return auditBalanceSheetKeyParameterDtos;
	}

	public void setAuditBalanceSheetKeyParameterDtos(
			List<AuditBalanceSheetKeyParameterDto> auditBalanceSheetKeyParameterDtos) {
		this.auditBalanceSheetKeyParameterDtos = auditBalanceSheetKeyParameterDtos;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
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
	
	

}
