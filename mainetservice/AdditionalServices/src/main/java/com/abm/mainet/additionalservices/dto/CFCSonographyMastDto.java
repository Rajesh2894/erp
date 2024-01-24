package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;


public class CFCSonographyMastDto implements Serializable{
	

	private static final long serialVersionUID = 7076659242421054799L;
	
	private Long regId;

	private Long centerType;
	
	private Long apmApplicationId;
	
	private String applicantName;
	
	private Long applyCapacity;
	
	private String centerName;
	
	private String centerAddress;
	
	private String contactNo;
	
	private String emailId;
	
	private Long institutionType;
	
	private String workArea;
	
	private String diagProcedure;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;
	
	private Long orgId;
	
	private List<CFCSonographyDetailDto> cfcSonoDetDtoList = new ArrayList<CFCSonographyDetailDto>();
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<DocumentDetailsVO> documentList;
	
	private String birthRegstatus;
	private String birthRegremark;
	private Long smServiceId;
	private Long cfcWard1;
	private Long cfcWard2;

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public Long getCenterType() {
		return centerType;
	}

	public void setCenterType(Long centerType) {
		this.centerType = centerType;
	}
	
	
	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
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

	public List<CFCSonographyDetailDto> getCfcSonoDetDtoList() {
		return cfcSonoDetDtoList;
	}

	public void setCfcSonoDetDtoList(List<CFCSonographyDetailDto> cfcSonoDetDtoList) {
		this.cfcSonoDetDtoList = cfcSonoDetDtoList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getBirthRegstatus() {
		return birthRegstatus;
	}

	public void setBirthRegstatus(String birthRegstatus) {
		this.birthRegstatus = birthRegstatus;
	}

	public String getBirthRegremark() {
		return birthRegremark;
	}

	public void setBirthRegremark(String birthRegremark) {
		this.birthRegremark = birthRegremark;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Long getCfcWard1() {
		return cfcWard1;
	}

	public void setCfcWard1(Long cfcWard1) {
		this.cfcWard1 = cfcWard1;
	}

	public Long getCfcWard2() {
		return cfcWard2;
	}

	public void setCfcWard2(Long cfcWard2) {
		this.cfcWard2 = cfcWard2;
	}
	

}
