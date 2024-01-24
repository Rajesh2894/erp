package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.util.Date;

public class LicenseApplicationPurposeTypeDetDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Long purposeId;

    private LicenseApplicationMasterDTO licenseApplicationMaster;

    private Long applicationPurpose1;

    private Long applicationPurpose2;

    private Long applicationPurpose3;

    private Long far;

    private Double area;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String gIpMacUpd;

	public Long getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(Long purposeId) {
		this.purposeId = purposeId;
	}

	public LicenseApplicationMasterDTO getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}

	public void setLicenseApplicationMaster(LicenseApplicationMasterDTO licenseApplicationMaster) {
		this.licenseApplicationMaster = licenseApplicationMaster;
	}

	public Long getApplicationPurpose1() {
		return applicationPurpose1;
	}

	public void setApplicationPurpose1(Long applicationPurpose1) {
		this.applicationPurpose1 = applicationPurpose1;
	}

	public Long getApplicationPurpose2() {
		return applicationPurpose2;
	}

	public void setApplicationPurpose2(Long applicationPurpose2) {
		this.applicationPurpose2 = applicationPurpose2;
	}

	public Long getApplicationPurpose3() {
		return applicationPurpose3;
	}

	public void setApplicationPurpose3(Long applicationPurpose3) {
		this.applicationPurpose3 = applicationPurpose3;
	}

	public Long getFar() {
		return far;
	}

	public void setFar(Long far) {
		this.far = far;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getgIpMacUpd() {
		return gIpMacUpd;
	}

	public void setgIpMacUpd(String gIpMacUpd) {
		this.gIpMacUpd = gIpMacUpd;
	}


}
