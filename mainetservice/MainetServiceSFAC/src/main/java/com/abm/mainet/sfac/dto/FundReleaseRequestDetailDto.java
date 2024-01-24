package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FundReleaseRequestDetailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9033934667727409400L;
	
	
	private Long frrdId;
	
	@JsonIgnore
	private FundReleaseRequestMasterDto fundReleaseRequestMasterDto;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	private Long purposeFor;
	
	private Long allocatedNoOfFPO;
	
	private BigDecimal allocatedBudget;
	
	private BigDecimal totalFundRelTillDate;
	
	private BigDecimal utilizedAmount;
	
	private BigDecimal newDemand;
	
	private String status;
	
	private String remark;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getFrrdId() {
		return frrdId;
	}

	public void setFrrdId(Long frrdId) {
		this.frrdId = frrdId;
	}

	public FundReleaseRequestMasterDto getFundReleaseRequestMasterDto() {
		return fundReleaseRequestMasterDto;
	}

	public void setFundReleaseRequestMasterDto(FundReleaseRequestMasterDto fundReleaseRequestMasterDto) {
		this.fundReleaseRequestMasterDto = fundReleaseRequestMasterDto;
	}

	public Long getPurposeFor() {
		return purposeFor;
	}

	public void setPurposeFor(Long purposeFor) {
		this.purposeFor = purposeFor;
	}

	public Long getAllocatedNoOfFPO() {
		return allocatedNoOfFPO;
	}

	public void setAllocatedNoOfFPO(Long allocatedNoOfFPO) {
		this.allocatedNoOfFPO = allocatedNoOfFPO;
	}

	public BigDecimal getAllocatedBudget() {
		return allocatedBudget;
	}

	public void setAllocatedBudget(BigDecimal allocatedBudget) {
		this.allocatedBudget = allocatedBudget;
	}

	public BigDecimal getTotalFundRelTillDate() {
		return totalFundRelTillDate;
	}

	public void setTotalFundRelTillDate(BigDecimal totalFundRelTillDate) {
		this.totalFundRelTillDate = totalFundRelTillDate;
	}

	public BigDecimal getUtilizedAmount() {
		return utilizedAmount;
	}

	public void setUtilizedAmount(BigDecimal utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}

	public BigDecimal getNewDemand() {
		return newDemand;
	}

	public void setNewDemand(BigDecimal newDemand) {
		this.newDemand = newDemand;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	

}
