package com.abm.mainet.socialsecurity.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_SWD_FAMILY_DET")
public class SchemeApplicantFamilyDetEntity {
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FAM_MEMID", unique = true, nullable = false)
	private Long famMemId;
	@Column(name = "MEM_NAME", nullable = false)
	private String famMemName;
	@Column(name = "MEM_RELATION", nullable = false)
	private String relation;
	@Column(name = "MEM_GEN", nullable = false)
	private String gender;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DOB", nullable = false)
	private Date dob;
	@Column(name = "AGE", nullable = false)
	private Long age;
	@Column(name = "EDUCATION", nullable = false)
	private String education;
	@Column(name = "OCCUPATION", nullable = false)
	private String occupation;
	@Column(name = "CONTACT_NO", nullable = false)
	private String contactNo;
	@ManyToOne
	@JoinColumn(name = "SAPI_ID", referencedColumnName = "SAPI_ID")
	private SocialSecurityApplicationForm applicationId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;
	
	public static String[] getPkValues() {
		return new String[] { "SWD", "TB_SWD_FAMILY_DET", "FAM_MEMID" };
	}

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

	public String getOccupation() {
		return occupation;
	}

	public String getContactNo() {
		return contactNo;
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

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

	public SocialSecurityApplicationForm getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(SocialSecurityApplicationForm applicationId) {
		this.applicationId = applicationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
