package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_CFC_SONOGRAPHY_MASTER")
public class CFCSonographyMasterEntity implements Serializable{

	private static final long serialVersionUID = -4597972286766815871L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "REG_ID")
	private Long regId;
	

	@OneToMany(mappedBy = "cfcSonographyMasEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CFCSonographyDetailEntity> cfcSonographyDetEntity = new ArrayList<>();
	
	@Column(name = "APM_APPLICATION_ID", nullable = false)
	private Long apmApplicationId;
	
	
	@Column(name = "CENTER_TYPE", nullable = false)
	private Long centerType;
	
	@Column(name = "APPLICANT_NAME", length = 100, nullable = false)
	private String applicantName;
	
	@Column(name = "APPLY_CAPACITY",  nullable = false)
	private Long applyCapacity;
	
	@Column(name = "CENTER_NAME", length = 100, nullable = false)
	private String centerName;
	
	@Column(name = "CENTER_ADDRESS", length = 250, nullable = false)
	private String centerAddress;
	
	@Column(name = "CONTACT_NO", nullable = false)
	private String contactNo;
	
	@Column(name = "EMAIL_ID", nullable = true)
	private String emailId;
	
	@Column(name = "INSTITUTION_TYPE",  nullable = false)	
	private Long institutionType;
	
	@Column(name = "Work_Area",  length = 150, nullable = false)	
	private String workArea;
	
	@Column(name = "DIAG_PROCEDURE",  length = 250, nullable = false)
	private String diagProcedure;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;
	
	

	
	public Long getRegId() {
		return regId;
	}




	public void setRegId(Long regId) {
		this.regId = regId;
	}




	public List<CFCSonographyDetailEntity> getCfcSonographyDetEntity() {
		return cfcSonographyDetEntity;
	}




	public void setCfcSonographyDetEntity(List<CFCSonographyDetailEntity> cfcSonographyDetEntity) {
		this.cfcSonographyDetEntity = cfcSonographyDetEntity;
	}




	public Long getApmApplicationId() {
		return apmApplicationId;
	}




	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}




	public Long getCenterType() {
		return centerType;
	}




	public void setCenterType(Long centerType) {
		this.centerType = centerType;
	}




	public String getApplicantName() {
		return applicantName;
	}




	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}




	public Long getApplyCapacity() {
		return applyCapacity;
	}




	public void setApplyCapacity(Long applyCapacity) {
		this.applyCapacity = applyCapacity;
	}




	public String getCenterName() {
		return centerName;
	}




	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}




	public String getCenterAddress() {
		return centerAddress;
	}




	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}




	public String getContactNo() {
		return contactNo;
	}




	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}




	public String getEmailId() {
		return emailId;
	}




	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}




	public Long getInstitutionType() {
		return institutionType;
	}




	public void setInstitutionType(Long institutionType) {
		this.institutionType = institutionType;
	}




	public String getWorkArea() {
		return workArea;
	}




	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}




	public String getDiagProcedure() {
		return diagProcedure;
	}




	public void setDiagProcedure(String diagProcedure) {
		this.diagProcedure = diagProcedure;
	}




	public Long getOrgId() {
		return orgId;
	}




	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}




	public Date getCreationDate() {
		return creationDate;
	}




	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}




	public Long getCreatedBy() {
		return createdBy;
	}




	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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




	public static String[] getPkValues() {
		return new String[] { "CFC", "TB_CFC_SONOGRAPHY_MASTER", "REG_ID" };
	}
}
