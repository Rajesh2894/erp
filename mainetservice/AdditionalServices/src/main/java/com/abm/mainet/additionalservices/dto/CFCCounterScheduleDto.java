package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.exolab.castor.types.DateTime;

import com.abm.mainet.additionalservices.domain.CFCCounterMasterEntity;

public class CFCCounterScheduleDto implements Serializable {

	private static final long serialVersionUID = 2163330539449734949L;

	private Long csScheduleid;

	private String csFromTime;

	private String csToTime;

	private Long csUserId;

	private CFCCounterMasterDto cfcCounterMasterDto;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private String csStatus;

	private String cmCollncentreno;

	private String cmDescription;

	private Long cfcWard1;
	private Long cfcWard2;
	private Long cfcWard3;
	private Long cfcWard4;
	private Long cfcWard5;

	private String cuCountcentreno;

	private String cuDescription;
	//119534   New field added to get/set status of Frequency of schedule
	private String frequencySts;
	
	private String deviceId;
	
	public String getFrequencySts() {
		return frequencySts;
	}

	public void setFrequencySts(String frequencySts) {
		this.frequencySts = frequencySts;
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

	public String getCsStatus() {
		return csStatus;
	}

	public void setCsStatus(String csStatus) {
		this.csStatus = csStatus;
	}

	private Date updatedDate;

	public Long getCsScheduleid() {
		return csScheduleid;
	}

	public void setCsScheduleid(Long csScheduleid) {
		this.csScheduleid = csScheduleid;
	}

	public String getCsFromTime() {
		return csFromTime;
	}

	public void setCsFromTime(String csFromTime) {
		this.csFromTime = csFromTime;
	}

	public String getCsToTime() {
		return csToTime;
	}

	public void setCsToTime(String csToTime) {
		this.csToTime = csToTime;
	}

	public Long getCsUserId() {
		return csUserId;
	}

	public void setCsUserId(Long csUserId) {
		this.csUserId = csUserId;
	}

	public CFCCounterMasterDto getCfcCounterMasterDto() {
		return cfcCounterMasterDto;
	}

	public void setCfcCounterMasterDto(CFCCounterMasterDto cfcCounterMasterDto) {
		this.cfcCounterMasterDto = cfcCounterMasterDto;
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
