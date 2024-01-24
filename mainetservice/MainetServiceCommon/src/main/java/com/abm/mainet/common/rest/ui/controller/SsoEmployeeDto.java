/**
 * 
 */
package com.abm.mainet.common.rest.ui.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author cherupelli.srikanth
 *
 */
@JsonInclude(Include.NON_NULL)
public class SsoEmployeeDto {
	
	private String applicantName;
	private String mobileNumber;
	private String uid;
	private String userName;
	private String email;
	private String rtnUrl;
	private String ssoDashboardURL;
	private String tokenId;
	private String designationID;
	private String officeID;
	private String officeName;
	private String designation;
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRtnUrl() {
		return rtnUrl;
	}
	public void setRtnUrl(String rtnUrl) {
		this.rtnUrl = rtnUrl;
	}
	public String getSsoDashboardURL() {
		return ssoDashboardURL;
	}
	public void setSsoDashboardURL(String ssoDashboardURL) {
		this.ssoDashboardURL = ssoDashboardURL;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getDesignationID() {
		return designationID;
	}
	public void setDesignationID(String designationID) {
		this.designationID = designationID;
	}
	public String getOfficeID() {
		return officeID;
	}
	public void setOfficeID(String officeID) {
		this.officeID = officeID;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}

    

}
