package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuditBalanceSheetSubParameterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4239560453318267802L;
	
	private Long abssId;

	@JsonIgnore
	private AuditBalanceSheetKeyParameterDto auditBalanceSheetKeyParameterDto = new AuditBalanceSheetKeyParameterDto();

	private Long subParameter;

	private String subParameterDesc;
	
	private Long applicationId;

	private String absStatus;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	@JsonIgnore
	List<AuditBalanceSheetSubParameterDetailDto> auditBalanceSheetSubParameterDetailDtos = new ArrayList<>();

	public Long getAbssId() {
		return abssId;
	}

	public void setAbssId(Long abssId) {
		this.abssId = abssId;
	}

	public AuditBalanceSheetKeyParameterDto getAuditBalanceSheetKeyParameterDto() {
		return auditBalanceSheetKeyParameterDto;
	}

	public void setAuditBalanceSheetKeyParameterDto(AuditBalanceSheetKeyParameterDto auditBalanceSheetKeyParameterDto) {
		this.auditBalanceSheetKeyParameterDto = auditBalanceSheetKeyParameterDto;
	}

	public Long getSubParameter() {
		return subParameter;
	}

	public void setSubParameter(Long subParameter) {
		this.subParameter = subParameter;
	}

	public String getSubParameterDesc() {
		return subParameterDesc;
	}

	public void setSubParameterDesc(String subParameterDesc) {
		this.subParameterDesc = subParameterDesc;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getAbsStatus() {
		return absStatus;
	}

	public void setAbsStatus(String absStatus) {
		this.absStatus = absStatus;
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

	public List<AuditBalanceSheetSubParameterDetailDto> getAuditBalanceSheetSubParameterDetailDtos() {
		return auditBalanceSheetSubParameterDetailDtos;
	}

	public void setAuditBalanceSheetSubParameterDetailDtos(
			List<AuditBalanceSheetSubParameterDetailDto> auditBalanceSheetSubParameterDetailDtos) {
		this.auditBalanceSheetSubParameterDetailDtos = auditBalanceSheetSubParameterDetailDtos;
	}
	
	

}
