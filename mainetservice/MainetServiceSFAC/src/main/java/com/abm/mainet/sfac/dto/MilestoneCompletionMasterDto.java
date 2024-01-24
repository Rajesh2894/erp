package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MilestoneCompletionMasterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4871481169073921288L;
	

	private Long mscId;
	
	private Long iaId;
	
	private String iaName;
	
	private Long cbboId;
	
	private String milestoneName;
	
	private Long msId;
	
	private Date dateOfWorkOrder;
	
	private Date targetDate;
	
	private Date actualCompletionDate;
	
	private BigDecimal allocationBudget;
	
	private BigDecimal invoiceAmount;
	
	private String invoiceDesc;
	
	private Long applicationNumber;
	
	private String status;
	
	private String authRemark;
	
	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	
	@JsonIgnore
	private List<MilestoneCompletionDocDetailsDto> milestoneCompletionDocDetailsDtos = new ArrayList<>();

	public Long getMscId() {
		return mscId;
	}

	public void setMscId(Long mscId) {
		this.mscId = mscId;
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

	public String getMilestoneName() {
		return milestoneName;
	}

	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}

	public Long getMsId() {
		return msId;
	}

	public void setMsId(Long msId) {
		this.msId = msId;
	}

	public Date getDateOfWorkOrder() {
		return dateOfWorkOrder;
	}

	public void setDateOfWorkOrder(Date dateOfWorkOrder) {
		this.dateOfWorkOrder = dateOfWorkOrder;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public Date getActualCompletionDate() {
		return actualCompletionDate;
	}

	public void setActualCompletionDate(Date actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
	}

	public BigDecimal getAllocationBudget() {
		return allocationBudget;
	}

	public void setAllocationBudget(BigDecimal allocationBudget) {
		this.allocationBudget = allocationBudget;
	}

	public String getInvoiceDesc() {
		return invoiceDesc;
	}

	public void setInvoiceDesc(String invoiceDesc) {
		this.invoiceDesc = invoiceDesc;
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

	public List<MilestoneCompletionDocDetailsDto> getMilestoneCompletionDocDetailsDtos() {
		return milestoneCompletionDocDetailsDtos;
	}

	public void setMilestoneCompletionDocDetailsDtos(
			List<MilestoneCompletionDocDetailsDto> milestoneCompletionDocDetailsDtos) {
		this.milestoneCompletionDocDetailsDtos = milestoneCompletionDocDetailsDtos;
	}

	public Long getCbboId() {
		return cbboId;
	}

	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
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

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}
	

	

}
