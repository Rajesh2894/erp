package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FundReleaseRequestMasterDto implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6005706622577264903L;

	private Long frrId;
	
	private Long iaId;
	
	private String iaName;
	
	private String fileReferenceNumber;
	
	@JsonIgnore
	List<FundReleaseRequestDetailDto> fundReleaseRequestDetailDtos = new ArrayList<>();
	
	private Long financialYear;
	
	private String status;
	
	private Long applicationNumber;
	
	private BigDecimal newDemandTotal;
	
	private String authRemark;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	public Long getFrrId() {
		return frrId;
	}

	public void setFrrId(Long frrId) {
		this.frrId = frrId;
	}

	public Long getIaId() {
		return iaId;
	}

	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	public String getFileReferenceNumber() {
		return fileReferenceNumber;
	}

	public void setFileReferenceNumber(String fileReferenceNumber) {
		this.fileReferenceNumber = fileReferenceNumber;
	}

	public List<FundReleaseRequestDetailDto> getFundReleaseRequestDetailDtos() {
		return fundReleaseRequestDetailDtos;
	}

	public void setFundReleaseRequestDetailDtos(List<FundReleaseRequestDetailDto> fundReleaseRequestDetailDtos) {
		this.fundReleaseRequestDetailDtos = fundReleaseRequestDetailDtos;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public BigDecimal getNewDemandTotal() {
		return newDemandTotal;
	}

	public void setNewDemandTotal(BigDecimal newDemandTotal) {
		this.newDemandTotal = newDemandTotal;
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

	public String getIaName() {
		return iaName;
	}

	public void setIaName(String iaName) {
		this.iaName = iaName;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}
	
	

}
