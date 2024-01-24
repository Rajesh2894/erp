package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BusinessPlanInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5921369854470389477L;
	
	
	private Long bpID;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private List<AttachDocs> attachDocsListBP = new ArrayList<>();
	private List<DocumentDetailsVO> attachmentsBP = new ArrayList<>();
	
	private String documentDescription;
	
	private String documentName;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	

	public Long getBpID() {
		return bpID;
	}

	public void setBpID(Long bpID) {
		this.bpID = bpID;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
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

	public List<AttachDocs> getAttachDocsListBP() {
		return attachDocsListBP;
	}

	public void setAttachDocsListBP(List<AttachDocs> attachDocsListBP) {
		this.attachDocsListBP = attachDocsListBP;
	}

	public List<DocumentDetailsVO> getAttachmentsBP() {
		return attachmentsBP;
	}

	public void setAttachmentsBP(List<DocumentDetailsVO> attachmentsBP) {
		this.attachmentsBP = attachmentsBP;
	}

	
	
	

}
