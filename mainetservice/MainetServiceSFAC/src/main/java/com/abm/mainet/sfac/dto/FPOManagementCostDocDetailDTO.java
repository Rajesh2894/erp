package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.icu.math.BigDecimal;

public class FPOManagementCostDocDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2024352100732607130L;

	private Long docId;

	@JsonIgnore
	private FPOManagementCostMasterDTO fpoManagementCostMasterDTO;

	private String documentDescription;

	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private String status;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public FPOManagementCostMasterDTO getFpoManagementCostMasterDTO() {
		return fpoManagementCostMasterDTO;
	}

	public void setFpoManagementCostMasterDTO(FPOManagementCostMasterDTO fpoManagementCostMasterDTO) {
		this.fpoManagementCostMasterDTO = fpoManagementCostMasterDTO;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

}
