package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreditGrantDetailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1157805262867172700L;
	
	private Long fsID;

	@JsonIgnore
	private FPOProfileMasterDto fpoProfileMasterDto;
	
	private Date dateOfCGF;
	
	private Long cgfAvailed;
	
	private BigDecimal amountOfCGF;
	
	private String actCGF;
	
	private BigDecimal totalCovrageCGF;
	
	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

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

	public Date getDateOfCGF() {
		return dateOfCGF;
	}

	public void setDateOfCGF(Date dateOfCGF) {
		this.dateOfCGF = dateOfCGF;
	}

	public Long getCgfAvailed() {
		return cgfAvailed;
	}

	public void setCgfAvailed(Long cgfAvailed) {
		this.cgfAvailed = cgfAvailed;
	}

	public BigDecimal getAmountOfCGF() {
		return amountOfCGF;
	}

	public void setAmountOfCGF(BigDecimal amountOfCGF) {
		this.amountOfCGF = amountOfCGF;
	}

	public String getActCGF() {
		return actCGF;
	}

	public void setActCGF(String actCGF) {
		this.actCGF = actCGF;
	}

	public BigDecimal getTotalCovrageCGF() {
		return totalCovrageCGF;
	}

	public void setTotalCovrageCGF(BigDecimal totalCovrageCGF) {
		this.totalCovrageCGF = totalCovrageCGF;
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
