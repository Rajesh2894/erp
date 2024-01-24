package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class LicenseInformationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6160900515703665835L;
	
	private Long licId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	 private List<AttachDocs> attachDocsListLi = new ArrayList<>();
	 private List<DocumentDetailsVO> attachmentsLi = new ArrayList<>();
	 
	 private Long length;

	private Long LicenseType;
	
	private String licenseName;

	private String licenseDesc;

	private Date licIssueDate;

	private Date licExpDate;

	private String licIssueAuth;
	
	private String documentName;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	 private String removedIds;
	 
	 

	public String getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(String removedIds) {
		this.removedIds = removedIds;
	}

	public Long getLicId() {
		return licId;
	}

	public void setLicId(Long licId) {
		this.licId = licId;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public Long getLicenseType() {
		return LicenseType;
	}

	public void setLicenseType(Long licenseType) {
		LicenseType = licenseType;
	}

	public String getLicenseDesc() {
		return licenseDesc;
	}

	public void setLicenseDesc(String licenseDesc) {
		this.licenseDesc = licenseDesc;
	}

	public Date getLicIssueDate() {
		return licIssueDate;
	}

	public void setLicIssueDate(Date licIssueDate) {
		this.licIssueDate = licIssueDate;
	}

	public Date getLicExpDate() {
		return licExpDate;
	}

	public void setLicExpDate(Date licExpDate) {
		this.licExpDate = licExpDate;
	}

	public String getLicIssueAuth() {
		return licIssueAuth;
	}

	public void setLicIssueAuth(String licIssueAuth) {
		this.licIssueAuth = licIssueAuth;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
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

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	

	public List<AttachDocs> getAttachDocsListLi() {
		return attachDocsListLi;
	}

	public void setAttachDocsListLi(List<AttachDocs> attachDocsListLi) {
		this.attachDocsListLi = attachDocsListLi;
	}

	public List<DocumentDetailsVO> getAttachmentsLi() {
		return attachmentsLi;
	}

	public void setAttachmentsLi(List<DocumentDetailsVO> attachmentsLi) {
		this.attachmentsLi = attachmentsLi;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	
	

	

}
