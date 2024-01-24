package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_PROF_REG")
public class ProfessionalRegistrationEntity implements Serializable {
	private static final long serialVersionUID = -4172501212383484097L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "USER_TYPE_ID")
	private Long userType;

	@Column(name = "F_NAME")
	private String firstName;

	@Column(name = "M_NAME")
	private String middleName;

	@Column(name = "L_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String emailId;

	@Column(name = "MOBILE")
	private String mobileNo;

	@Column(name = "STATE")
	private Long state;

	@Column(name = "DISTRICT")
	private Long district;

	@Column(name = "PINNCODE")
	private String pincode;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "COA_NO")
	private String coaNo;

	@Column(name = "COA_VAL_DATE")
	private Date coaValDate;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "OFF_CIRCLE")
	private Long officeCircle;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "LANGID")
	private long langId;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFIED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCoaNo() {
		return coaNo;
	}

	public void setCoaNo(String coaNo) {
		this.coaNo = coaNo;
	}

	public Date getCoaValDate() {
		return coaValDate;
	}

	public void setCoaValDate(Date coaValDate) {
		this.coaValDate = coaValDate;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public long getLangId() {
		return langId;
	}

	public void setLangId(long langId) {
		this.langId = langId;
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

	public String getLgIpMacUp() {
		return lgIpMacUp;
	}

	public void setLgIpMacUp(String lgIpMacUp) {
		this.lgIpMacUp = lgIpMacUp;
	}

	public Long getOfficeCircle() {
		return officeCircle;
	}

	public void setOfficeCircle(Long officeCircle) {
		this.officeCircle = officeCircle;
	}

	@Override
	public String toString() {
		return "ProfessionalRegistrationEntity [id=" + id + ", userType=" + userType + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", emailId=" + emailId + ", mobileNo="
				+ mobileNo + ", state=" + state + ", district=" + district + ", pincode=" + pincode + ", address="
				+ address + ", coaNo=" + coaNo + ", coaValDate=" + coaValDate + ", applicationId=" + applicationId
				+ ", officeCircle=" + officeCircle + ", orgId=" + orgId + ", langId=" + langId + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUp=" + lgIpMacUp + "]";
	}

	public String[] getPkValues() {
		return new String[] { "TCP", "TB_BPMS_PROF_REG", "ID" };
	}
}
