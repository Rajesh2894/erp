package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CircularNotiicationDetDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6634350006450907256L;

	private Long cndId;

	@JsonIgnore
	private CircularNotificationMasterDto circularNotificationMasterDto;

	private Long orgType;

	private String orgName;

	private Long sdb1;

	private Long sdb2;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getCndId() {
		return cndId;
	}

	public void setCndId(Long cndId) {
		this.cndId = cndId;
	}

	public CircularNotificationMasterDto getCircularNotificationMasterDto() {
		return circularNotificationMasterDto;
	}

	public void setCircularNotificationMasterDto(CircularNotificationMasterDto circularNotificationMasterDto) {
		this.circularNotificationMasterDto = circularNotificationMasterDto;
	}

	public Long getOrgType() {
		return orgType;
	}

	public void setOrgType(Long orgType) {
		this.orgType = orgType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getSdb1() {
		return sdb1;
	}

	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	public Long getSdb2() {
		return sdb2;
	}

	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
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
