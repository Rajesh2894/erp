package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FPOManagementCostMasterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8914623099757585177L;

	private Long fmcId;

	private Long applicationNumber;

	@JsonIgnore
	private FPOMasterDto foFpoMasterDto;

	@JsonIgnore
	private CBBOMasterDto cbboMasterDto;

	@JsonIgnore
	private IAMasterDto iaMasterDto;

	private Long financialYear;
	
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	

	private String status;
	
	private BigDecimal totalCostApproved;

	@JsonIgnore
	private List<FPOManagementCostDetailDTO> fpoManagementCostDetailDTOs = new ArrayList<>();

	@JsonIgnore
	private List<FPOManagementCostDocDetailDTO> fpoManagementCostDocDetailDTOs = new ArrayList<>();

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String authRemark;
	
	

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public Long getFmcId() {
		return fmcId;
	}

	public void setFmcId(Long fmcId) {
		this.fmcId = fmcId;
	}

	public FPOMasterDto getFoFpoMasterDto() {
		return foFpoMasterDto;
	}

	public void setFoFpoMasterDto(FPOMasterDto foFpoMasterDto) {
		this.foFpoMasterDto = foFpoMasterDto;
	}

	public CBBOMasterDto getCbboMasterDto() {
		return cbboMasterDto;
	}

	public void setCbboMasterDto(CBBOMasterDto cbboMasterDto) {
		this.cbboMasterDto = cbboMasterDto;
	}

	public IAMasterDto getIaMasterDto() {
		return iaMasterDto;
	}

	public void setIaMasterDto(IAMasterDto iaMasterDto) {
		this.iaMasterDto = iaMasterDto;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	public List<FPOManagementCostDetailDTO> getFpoManagementCostDetailDTOs() {
		return fpoManagementCostDetailDTOs;
	}

	public void setFpoManagementCostDetailDTOs(List<FPOManagementCostDetailDTO> fpoManagementCostDetailDTOs) {
		this.fpoManagementCostDetailDTOs = fpoManagementCostDetailDTOs;
	}

	public List<FPOManagementCostDocDetailDTO> getFpoManagementCostDocDetailDTOs() {
		return fpoManagementCostDocDetailDTOs;
	}

	public void setFpoManagementCostDocDetailDTOs(List<FPOManagementCostDocDetailDTO> fpoManagementCostDocDetailDTOs) {
		this.fpoManagementCostDocDetailDTOs = fpoManagementCostDocDetailDTOs;
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

	public BigDecimal getTotalCostApproved() {
		return totalCostApproved;
	}

	public void setTotalCostApproved(BigDecimal totalCostApproved) {
		this.totalCostApproved = totalCostApproved;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	
	

}
