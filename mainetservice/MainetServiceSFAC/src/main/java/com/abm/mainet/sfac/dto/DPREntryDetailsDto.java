package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.icu.math.BigDecimal;

public class DPREntryDetailsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4420416345300121892L;
	
	private Long dprdId;
	
	@JsonIgnore
	private DPREntryMasterDto dprEntryMasterDto ;
	
	private Long dprSection;
	
	private BigDecimal dprScore;
	
	private String remark;
	
	private String docStatus;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private List<AttachDocs> attachDocsListDet = new ArrayList<>();
	private List<DocumentDetailsVO> attachmentsDet = new ArrayList<>();

	public Long getDprdId() {
		return dprdId;
	}

	public void setDprdId(Long dprdId) {
		this.dprdId = dprdId;
	}

	public DPREntryMasterDto getDprEntryMasterDto() {
		return dprEntryMasterDto;
	}

	public void setDprEntryMasterDto(DPREntryMasterDto dprEntryMasterDto) {
		this.dprEntryMasterDto = dprEntryMasterDto;
	}

	public Long getDprSection() {
		return dprSection;
	}

	public void setDprSection(Long dprSection) {
		this.dprSection = dprSection;
	}

	public BigDecimal getDprScore() {
		return dprScore;
	}

	public void setDprScore(BigDecimal dprScore) {
		this.dprScore = dprScore;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
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

	public List<AttachDocs> getAttachDocsListDet() {
		return attachDocsListDet;
	}

	public void setAttachDocsListDet(List<AttachDocs> attachDocsListDet) {
		this.attachDocsListDet = attachDocsListDet;
	}

	public List<DocumentDetailsVO> getAttachmentsDet() {
		return attachmentsDet;
	}

	public void setAttachmentsDet(List<DocumentDetailsVO> attachmentsDet) {
		this.attachmentsDet = attachmentsDet;
	}

	
	
	

}
