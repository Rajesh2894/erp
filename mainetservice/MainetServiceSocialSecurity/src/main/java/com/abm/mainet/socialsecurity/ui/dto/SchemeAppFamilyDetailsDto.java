package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.util.Date;


public class SchemeAppFamilyDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5842462035878731077L;
	private Long famMemId;
	private String famMemName;
	private String relation;
	private String gender;
	private Date dob;
	private Long age;
	private String education;
	private String occupation;
	private String contactNo ;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String dateOfBirth;

	public Long getFamMemId() {
		return famMemId;
	}
	public String getFamMemName() {
		return famMemName;
	}
	public String getRelation() {
		return relation;
	}
	public String getGender() {
		return gender;
	}
	public Date getDob() {
		return dob;
	}
	public Long getAge() {
		return age;
	}
	public String getEducation() {
		return education;
	}
	public String getContactNo() {
		return contactNo;
	}
	public Long getOrgId() {
		return orgId;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public String getLgIpMac() {
		return lgIpMac;
	}
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}
	public void setFamMemId(Long famMemId) {
		this.famMemId = famMemId;
	}
	public void setFamMemName(String famMemName) {
		this.famMemName = famMemName;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "SchemeAppFamilyDetailsDto [famMemId=" + famMemId + ", famMemName=" + famMemName + ", relation="
				+ relation + ", gender=" + gender + ", dob=" + dob + ", age=" + age + ", education=" + education
				+ ", contactNo=" + contactNo + ", orgId=" + orgId + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
				+ ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}
}
