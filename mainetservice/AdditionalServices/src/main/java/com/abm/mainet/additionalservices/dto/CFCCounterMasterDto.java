package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.additionalservices.domain.CFCCollectionMasterEntity;
import com.abm.mainet.additionalservices.domain.CFCCounterScheduleEntity;

public class CFCCounterMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7278512517439911234L;

	private Long cuCounterid;

	private List<CFCCounterScheduleDto> cfcCounterScheduleDtos;

	private String cuCountcentreno;

	private String cuDescription;

	private CFCCollectionMasterDto cfcCollectionMasterDto;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;
	private String cmCollncentreno;
	

	public Long getCuCounterid() {
		return cuCounterid;
	}

	public void setCuCounterid(Long cuCounterid) {
		this.cuCounterid = cuCounterid;
	}

	public List<CFCCounterScheduleDto> getCfcCounterScheduleDtos() {
		return cfcCounterScheduleDtos;
	}

	public void setCfcCounterScheduleDtos(List<CFCCounterScheduleDto> cfcCounterScheduleDtos) {
		this.cfcCounterScheduleDtos = cfcCounterScheduleDtos;
	}

	public String getCuCountcentreno() {
		return cuCountcentreno;
	}

	public void setCuCountcentreno(String cuCountcentreno) {
		this.cuCountcentreno = cuCountcentreno;
	}

	public String getCuDescription() {
		return cuDescription;
	}

	public void setCuDescription(String cuDescription) {
		this.cuDescription = cuDescription;
	}

	public CFCCollectionMasterDto getCfcCollectionMasterDto() {
		return cfcCollectionMasterDto;
	}

	public void setCfcCollectionMasterDto(CFCCollectionMasterDto cfcCollectionMasterDto) {
		this.cfcCollectionMasterDto = cfcCollectionMasterDto;
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

	public String getCmCollncentreno() {
		return cmCollncentreno;
	}

	public void setCmCollncentreno(String cmCollncentreno) {
		this.cmCollncentreno = cmCollncentreno;
	}



}
