package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MilestoneCompletionDocDetailsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6391599020726914792L;
	
	private Long mscdId;
	
	@JsonIgnore
	private MilestoneCompletionMasterDto milestoneCompletionMasterDto;
	
	private String docDescription;
	
	private String status;
	
	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	public Long getMscdId() {
		return mscdId;
	}

	public void setMscdId(Long mscdId) {
		this.mscdId = mscdId;
	}

	public MilestoneCompletionMasterDto getMilestoneCompletionMasterDto() {
		return milestoneCompletionMasterDto;
	}

	public void setMilestoneCompletionMasterDto(MilestoneCompletionMasterDto milestoneCompletionMasterDto) {
		this.milestoneCompletionMasterDto = milestoneCompletionMasterDto;
	}

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
	
	
	

}
