package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LicenseDataEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "LICENSE_TYPE")
	private String licenseType;

	@Column(name = "APPLICATION_NO")
	private String applicationNo;

	@Column(name = "APPLICANT_NAME")
	private String applicantName;

	@Column(name = "APPLICATION_DATE")
	private String applicationDate;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "Department")
	private String deptName;

	@Column(name = "SERVICE_NAME")
	private String serviceName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public String toString() {
		return "LicenseDataEntity [id=" + id + ", licenseType=" + licenseType + ", applicationNo=" + applicationNo
				+ ", applicantName=" + applicantName + ", applicationDate=" + applicationDate + ", status=" + status
				+ ", deptName=" + deptName + ", serviceName=" + serviceName + "]";
	}

}
