package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.sfac.domain.EquityGrantMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class EquityGrantDetailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3042975897083701765L;
	
	private Long egdId;
	
	
	@JsonIgnore
	private EquityGrantMasterDto equityGrantMasterDto;

	private String name;

	private String role;

	private Long dinNo;

	private String qualification;
	
	private Long aadhaarNo;

	private Long tenure;

	private String contactNoAddress;

	private Long landHolding;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String isBOM;

	public Long getEgdId() {
		return egdId;
	}

	public void setEgdId(Long egdId) {
		this.egdId = egdId;
	}

	

	public EquityGrantMasterDto getEquityGrantMasterDto() {
		return equityGrantMasterDto;
	}

	public void setEquityGrantMasterDto(EquityGrantMasterDto equityGrantMasterDto) {
		this.equityGrantMasterDto = equityGrantMasterDto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getDinNo() {
		return dinNo;
	}

	public void setDinNo(Long dinNo) {
		this.dinNo = dinNo;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Long getTenure() {
		return tenure;
	}

	public void setTenure(Long tenure) {
		this.tenure = tenure;
	}

	public String getContactNoAddress() {
		return contactNoAddress;
	}

	public void setContactNoAddress(String contactNoAddress) {
		this.contactNoAddress = contactNoAddress;
	}

	public Long getLandHolding() {
		return landHolding;
	}

	public void setLandHolding(Long landHolding) {
		this.landHolding = landHolding;
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

	public Long getAadhaarNo() {
		return aadhaarNo;
	}

	public void setAadhaarNo(Long aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}

	public String isBOM() {
		return isBOM;
	}

	public void setBOM(String isBOM) {
		this.isBOM = isBOM;
	}


	
	

}
