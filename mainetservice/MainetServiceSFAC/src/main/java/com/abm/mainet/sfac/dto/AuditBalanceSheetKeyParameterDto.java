package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuditBalanceSheetKeyParameterDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8527355614612788859L;
	
	private Long absKId;

	@JsonIgnore
	private AuditBalanceSheetMasterDto auditBalanceSheetMasterDto = new AuditBalanceSheetMasterDto();

	private Long keyParameter;

	private String keyParameterDesc;

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
	private List<AuditBalanceSheetSubParameterDto> auditBalanceSheetSubParameterDtos = new ArrayList<>();

	public Long getAbsKId() {
		return absKId;
	}

	public void setAbsKId(Long absKId) {
		this.absKId = absKId;
	}

	

	public AuditBalanceSheetMasterDto getAuditBalanceSheetMasterDto() {
		return auditBalanceSheetMasterDto;
	}

	public void setAuditBalanceSheetMasterDto(AuditBalanceSheetMasterDto auditBalanceSheetMasterDto) {
		this.auditBalanceSheetMasterDto = auditBalanceSheetMasterDto;
	}

	public Long getKeyParameter() {
		return keyParameter;
	}

	public void setKeyParameter(Long keyParameter) {
		this.keyParameter = keyParameter;
	}

	public String getKeyParameterDesc() {
		return keyParameterDesc;
	}

	public void setKeyParameterDesc(String keyParameterDesc) {
		this.keyParameterDesc = keyParameterDesc;
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

	public List<AuditBalanceSheetSubParameterDto> getAuditBalanceSheetSubParameterDtos() {
		return auditBalanceSheetSubParameterDtos;
	}

	public void setAuditBalanceSheetSubParameterDtos(
			List<AuditBalanceSheetSubParameterDto> auditBalanceSheetSubParameterDtos) {
		this.auditBalanceSheetSubParameterDtos = auditBalanceSheetSubParameterDtos;
	}
	
	

}
