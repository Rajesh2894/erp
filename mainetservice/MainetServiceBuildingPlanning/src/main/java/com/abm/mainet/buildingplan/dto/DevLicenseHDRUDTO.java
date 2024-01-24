package com.abm.mainet.buildingplan.dto;

import java.util.Date;
import com.abm.mainet.buildingplan.domain.TbDeveloperRegistrationEntity;

public class DevLicenseHDRUDTO {
	
	private Long hDRULicenseId;
	
	private DeveloperRegistrationDTO developerRegMas;
	
	private String licenseNo;
	
	private Date dateOfGrantLicense;
	
	private Long purposeOfColony;
	
	private String purposeOfColonyDesc;
	
	private Date dateOfValidityLicense;

	public Long gethDRULicenseId() {
		return hDRULicenseId;
	}

	public void sethDRULicenseId(Long hDRULicenseId) {
		this.hDRULicenseId = hDRULicenseId;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}


	public Date getDateOfGrantLicense() {
		return dateOfGrantLicense;
	}

	public void setDateOfGrantLicense(Date dateOfGrantLicense) {
		this.dateOfGrantLicense = dateOfGrantLicense;
	}

	public Date getDateOfValidityLicense() {
		return dateOfValidityLicense;
	}

	public void setDateOfValidityLicense(Date dateOfValidityLicense) {
		this.dateOfValidityLicense = dateOfValidityLicense;
	}

	public Long getPurposeOfColony() {
		return purposeOfColony;
	}

	public void setPurposeOfColony(Long purposeOfColony) {
		this.purposeOfColony = purposeOfColony;
	}

	public String getPurposeOfColonyDesc() {
		return purposeOfColonyDesc;
	}

	public void setPurposeOfColonyDesc(String purposeOfColonyDesc) {
		this.purposeOfColonyDesc = purposeOfColonyDesc;
	}

	public DeveloperRegistrationDTO getDeveloperRegMas() {
		return developerRegMas;
	}

	public void setDeveloperRegMas(DeveloperRegistrationDTO developerRegMas) {
		this.developerRegMas = developerRegMas;
	}
}
