package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.abm.mainet.additionalservices.domain.CFCCounterMasterEntity;

public class CFCCollectionMasterDto implements Serializable {

	private static final long serialVersionUID = -4790563600056021124L;

	private Long cmCollnid;

	private List<CFCCounterMasterDto> cfcCounterMasterDtos;

	private String cmCollncentreno;

	private String cmDescription;

	private Long dwzId;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;
	private Long cfcWard1;
	private Long cfcWard2;
	private Long cfcWard3;
	private Long cfcWard4;
	private Long cfcWard5;
	private String deviceId;

	public Long getCfcWard1() {
		return cfcWard1;
	}

	public void setCfcWard1(Long cfcWard1) {
		this.cfcWard1 = cfcWard1;
	}

	public Long getCfcWard2() {
		return cfcWard2;
	}

	public void setCfcWard2(Long cfcWard2) {
		this.cfcWard2 = cfcWard2;
	}

	public Long getCfcWard3() {
		return cfcWard3;
	}

	public void setCfcWard3(Long cfcWard3) {
		this.cfcWard3 = cfcWard3;
	}

	public Long getCfcWard4() {
		return cfcWard4;
	}

	public void setCfcWard4(Long cfcWard4) {
		this.cfcWard4 = cfcWard4;
	}

	public Long getCfcWard5() {
		return cfcWard5;
	}

	public void setCfcWard5(Long cfcWard5) {
		this.cfcWard5 = cfcWard5;
	}

	public Long getCmCollnid() {
		return cmCollnid;
	}

	public void setCmCollnid(Long cmCollnid) {
		this.cmCollnid = cmCollnid;
	}

	public List<CFCCounterMasterDto> getCfcCounterMasterDtos() {
		return cfcCounterMasterDtos;
	}

	public void setCfcCounterMasterDtos(List<CFCCounterMasterDto> cfcCounterMasterDtos) {
		this.cfcCounterMasterDtos = cfcCounterMasterDtos;
	}

	public String getCmCollncentreno() {
		return cmCollncentreno;
	}

	public void setCmCollncentreno(String cmCollncentreno) {
		this.cmCollncentreno = cmCollncentreno;
	}

	public String getCmDescription() {
		return cmDescription;
	}

	public void setCmDescription(String cmDescription) {
		this.cmDescription = cmDescription;
	}

	public Long getDwzId() {
		return dwzId;
	}

	public void setDwzId(Long dwzId) {
		this.dwzId = dwzId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
