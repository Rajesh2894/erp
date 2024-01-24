package com.abm.mainet.rts.domain;

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
@Table(name = "TB_RTS_EXTERNAL_SERVICES")
public class RtsExternalServicesEntity {

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "R_ID", nullable = false)
	private Long RId;

	@Column(nullable = false)
	private Long orgId;

	@Column(name = "APPLICANT_NAME", length = 100)
	private String applicantName;

	@Column(name = "APPLICANT_MOBILNO", length = 20)
	private String applicantMobilno;

	@Column(name = "APPLICANT_EMAIL", length = 100)
	private String applicantEmail;

	@Column(name = "APPLICANT_ADDRESS", length = 100)
	private String applicantAddress;

	@Column(name = "PURPOSE", length = 100)
	private String purpose;

	@Column(name = "PROPERTY_NO", length = 100)
	private String propertyNo;

	@Column(name = "LANG_ID", nullable = false)
	private int langId;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lmoddate;

	@Column(name = "SERVICE_ID")
	private Long serviceId;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "APPLICATION_ID", precision = 16, scale = 0, nullable = false)
	private Long applicationId;

	@Column(name = "NO_COPIES")
	private Long noOfCopies;
	
	@Column(name = "STATUS", nullable = false)
	private String status;

	public Long getRId() {
		return RId;
	}

	public void setRId(Long rId) {
		RId = rId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantMobilno() {
		return applicantMobilno;
	}

	public void setApplicantMobilno(String applicantMobilno) {
		this.applicantMobilno = applicantMobilno;
	}

	public String getApplicantEmail() {
		return applicantEmail;
	}

	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getPkValues() {
		return new String[] { "HD", "TB_RTS_EXTERNAL_SERVICES", "R_ID" };
	}
}
