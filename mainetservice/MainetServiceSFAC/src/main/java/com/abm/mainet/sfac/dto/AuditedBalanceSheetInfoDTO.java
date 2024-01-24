package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuditedBalanceSheetInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6436247361207153056L;

	private Long auditedBalanceSheetId;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;

	private List<AttachDocs> attachDocsListABS = new ArrayList<>();
	private List<DocumentDetailsVO> attachmentsABS = new ArrayList<>();

	private Long financialYear;

	private String documentName;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getAuditedBalanceSheetId() {
		return auditedBalanceSheetId;
	}

	public void setAuditedBalanceSheetId(Long auditedBalanceSheetId) {
		this.auditedBalanceSheetId = auditedBalanceSheetId;
	}

	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
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

	public List<AttachDocs> getAttachDocsListABS() {
		return attachDocsListABS;
	}

	public void setAttachDocsListABS(List<AttachDocs> attachDocsListABS) {
		this.attachDocsListABS = attachDocsListABS;
	}

	public List<DocumentDetailsVO> getAttachmentsABS() {
		return attachmentsABS;
	}

	public void setAttachmentsABS(List<DocumentDetailsVO> attachmentsABS) {
		this.attachmentsABS = attachmentsABS;
	}

	



}
