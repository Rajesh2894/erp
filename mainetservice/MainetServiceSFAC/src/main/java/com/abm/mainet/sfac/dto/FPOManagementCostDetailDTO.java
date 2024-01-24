package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FPOManagementCostDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 360723115573813907L;


	private Long fmccId;

	@JsonIgnore
	private FPOManagementCostMasterDTO fpoManagementCostMasterDTO;

	private Long particulars;
	
	private BigDecimal managementCostExpected;
	
	private BigDecimal managementCostIncurred;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getFmccId() {
		return fmccId;
	}

	public void setFmccId(Long fmccId) {
		this.fmccId = fmccId;
	}

	public FPOManagementCostMasterDTO getFpoManagementCostMasterDTO() {
		return fpoManagementCostMasterDTO;
	}

	public void setFpoManagementCostMasterDTO(FPOManagementCostMasterDTO fpoManagementCostMasterDTO) {
		this.fpoManagementCostMasterDTO = fpoManagementCostMasterDTO;
	}



	public BigDecimal getManagementCostExpected() {
		return managementCostExpected;
	}

	public void setManagementCostExpected(BigDecimal managementCostExpected) {
		this.managementCostExpected = managementCostExpected;
	}

	
	

	public Long getParticulars() {
		return particulars;
	}

	public void setParticulars(Long particulars) {
		this.particulars = particulars;
	}

	public BigDecimal getManagementCostIncurred() {
		return managementCostIncurred;
	}

	public void setManagementCostIncurred(BigDecimal managementCostIncurred) {
		this.managementCostIncurred = managementCostIncurred;
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
