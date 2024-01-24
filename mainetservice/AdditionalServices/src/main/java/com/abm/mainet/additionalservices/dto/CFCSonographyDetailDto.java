package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.Date;

public class CFCSonographyDetailDto implements Serializable{

	private static final long serialVersionUID = 3354335789128973145L;
	
	private Long regDetId;
	
	private Long regId;
	
	private Long facilityCenter;
	
	private Long facilityTest;
	
	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;

	public Long getRegDetId() {
		return regDetId;
	}

	public void setRegDetId(Long regDetId) {
		this.regDetId = regDetId;
	}

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public Long getFacilityCenter() {
		return facilityCenter;
	}

	public void setFacilityCenter(Long facilityCenter) {
		this.facilityCenter = facilityCenter;
	}

	public Long getFacilityTest() {
		return facilityTest;
	}

	public void setFacilityTest(Long facilityTest) {
		this.facilityTest = facilityTest;
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
	
	

}
