package com.abm.mainet.mobile.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * @author rajesh.das
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class EipUserContactUsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 890887777786L;

	private String phoneNo;

	private String descQuery;

	private String firstName;

	private String lastName;

	private String emailId;

	private String isDeleted;

	private Long orgId;

	private Long empId;

	private int langId;

	private String contactUs;

	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDescQuery() {
		return descQuery;
	}

	public void setDescQuery(String descQuery) {
		this.descQuery = descQuery;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public String getContactUs() {
		return contactUs;
	}

	public void setContactUs(String contactUs) {
		this.contactUs = contactUs;
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

	@Override
	public String toString() {
		return "EipUserContactUsDTO [phoneNo=" + phoneNo + ", descQuery=" + descQuery + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", emailId=" + emailId + ", isDeleted=" + isDeleted + ", orgId=" + orgId
				+ ", empId=" + empId + ", langId=" + langId + ", contactUs=" + contactUs + ", lmodDate=" + lmodDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + "]";
	}

}
