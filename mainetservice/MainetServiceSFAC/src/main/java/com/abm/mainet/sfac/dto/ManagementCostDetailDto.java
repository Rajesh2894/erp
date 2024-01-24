package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ManagementCostDetailDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 266679091955172515L;


	private Long fsID;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private BigDecimal managementCostDisbursed;

	private Date dateOfRelease;
	
	private BigDecimal cbboCostDisbursed;
	
	private Date cbboDateOfRelease;
	
	private BigDecimal amountRelease;
	
	private BigDecimal cbbodAmountRelease;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	
	
	public BigDecimal getCbbodAmountRelease() {
		return cbbodAmountRelease;
	}

	public void setCbbodAmountRelease(BigDecimal cbbodAmountRelease) {
		this.cbbodAmountRelease = cbbodAmountRelease;
	}

	public Long getFsID() {
		return fsID;
	}

	public void setFsID(Long fsID) {
		this.fsID = fsID;
	}

	
	public FPOProfileMasterDto getFpoProfileMasterDto() {
		return fpoProfileMasterDto;
	}

	public void setFpoProfileMasterDto(FPOProfileMasterDto fpoProfileMasterDto) {
		this.fpoProfileMasterDto = fpoProfileMasterDto;
	}

	public BigDecimal getManagementCostDisbursed() {
		return managementCostDisbursed;
	}

	public void setManagementCostDisbursed(BigDecimal managementCostDisbursed) {
		this.managementCostDisbursed = managementCostDisbursed;
	}

	public Date getDateOfRelease() {
		return dateOfRelease;
	}

	public void setDateOfRelease(Date dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}

	public BigDecimal getCbboCostDisbursed() {
		return cbboCostDisbursed;
	}

	public void setCbboCostDisbursed(BigDecimal cbboCostDisbursed) {
		this.cbboCostDisbursed = cbboCostDisbursed;
	}

	public Date getCbboDateOfRelease() {
		return cbboDateOfRelease;
	}

	public void setCbboDateOfRelease(Date cbboDateOfRelease) {
		this.cbboDateOfRelease = cbboDateOfRelease;
	}

	public BigDecimal getAmountRelease() {
		return amountRelease;
	}

	public void setAmountRelease(BigDecimal amountRelease) {
		this.amountRelease = amountRelease;
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
