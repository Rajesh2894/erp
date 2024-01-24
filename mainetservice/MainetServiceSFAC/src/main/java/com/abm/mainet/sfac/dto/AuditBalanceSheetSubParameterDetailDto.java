package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuditBalanceSheetSubParameterDetailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4493289350487354760L;
	
	private Long absSpdId;

	@JsonIgnore
	AuditBalanceSheetSubParameterDto auditBalanceSheetSubParameterDto = new AuditBalanceSheetSubParameterDto();

	@Column(name = "CONDITIONS")
	private Long condition;

	@Column(name = "CONDITION_DESC")
	private String conditionDesc;

	@Column(name = "CRP_AMOUNT")
	private BigDecimal crpAmount;

	@Column(name = "PRP_AMOUNT")
	private BigDecimal prpAmount;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getAbsSpdId() {
		return absSpdId;
	}

	public void setAbsSpdId(Long absSpdId) {
		this.absSpdId = absSpdId;
	}

	public AuditBalanceSheetSubParameterDto getAuditBalanceSheetSubParameterDto() {
		return auditBalanceSheetSubParameterDto;
	}

	public void setAuditBalanceSheetSubParameterDto(AuditBalanceSheetSubParameterDto auditBalanceSheetSubParameterDto) {
		this.auditBalanceSheetSubParameterDto = auditBalanceSheetSubParameterDto;
	}

	public Long getCondition() {
		return condition;
	}

	public void setCondition(Long condition) {
		this.condition = condition;
	}

	public String getConditionDesc() {
		return conditionDesc;
	}

	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	

	public BigDecimal getCrpAmount() {
		return crpAmount;
	}

	public void setCrpAmount(BigDecimal crpAmount) {
		this.crpAmount = crpAmount;
	}

	public BigDecimal getPrpAmount() {
		return prpAmount;
	}

	public void setPrpAmount(BigDecimal prpAmount) {
		this.prpAmount = prpAmount;
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
	
	
}
