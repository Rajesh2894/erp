package com.abm.mainet.rti.dto;

import java.io.Serializable;

public class FormEReportDTO implements Serializable {
	private static final long serialVersionUID = -3204049018898575377L;
	private String applicationId;

	private String applicantName;
	private String letterNo;
	private String applicationAdd;
	private String applicationDate;
	private String signPath;
	private String mobileno;
	private String email;
	private String orgName;
	private String orgAddress;
	private String authorityName;
	private String authorityDepartment;
	private String authorityAddress;
	private String rtino;
	private String applicationFee;
	private String applicationFeeInWord;
	private String pioName;
	private String pioActionDate;
	private String pioEmail;
	private String pioMobNo;

	
	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicationAdd() {
		return applicationAdd;
	}

	public void setApplicationAdd(String applicationAdd) {
		this.applicationAdd = applicationAdd;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getSignPath() {
		return signPath;
	}

	public void setSignPath(String signPath) {
		this.signPath = signPath;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAuthorityDepartment() {
		return authorityDepartment;
	}

	public void setAuthorityDepartment(String authorityDepartment) {
		this.authorityDepartment = authorityDepartment;
	}

	public String getAuthorityAddress() {
		return authorityAddress;
	}

	public void setAuthorityAddress(String authorityAddress) {
		this.authorityAddress = authorityAddress;
	}

	public String getRtino() {
		return rtino;
	}

	public void setRtino(String rtino) {
		this.rtino = rtino;
	}

	public String getApplicationFee() {
		return applicationFee;
	}

	public void setApplicationFee(String applicationFee) {
		this.applicationFee = applicationFee;
	}

	public String getApplicationFeeInWord() {
		return applicationFeeInWord;
	}

	public void setApplicationFeeInWord(String applicationFeeInWord) {
		this.applicationFeeInWord = applicationFeeInWord;
	}

	public String getPioName() {
		return pioName;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setPioName(String pioName) {
		this.pioName = pioName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getPioActionDate() {
		return pioActionDate;
	}

	public void setPioActionDate(String pioActionDate) {
		this.pioActionDate = pioActionDate;
	}

	public String getLetterNo() {
		return letterNo;
	}

	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}

	public String getPioMobNo() {
		return pioMobNo;
	}

	public void setPioMobNo(String pioMobNo) {
		this.pioMobNo = pioMobNo;
	}

	public String getPioEmail() {
		return pioEmail;
	}

	public void setPioEmail(String pioEmail) {
		this.pioEmail = pioEmail;
	}

}
