package com.abm.mainet.buildingplan.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class DeveloperAuthorizedUserDTO {
	
	private Long authUserId;
	
	private DeveloperRegistrationDTO developerRegMas;
	
	private String authUserName;
	
	private Long authMobileNo;
	
	private String authEmail;
	
	private Long authGender;
	
	private Date authDOB;
	
	private String authPanNumber;
	
	private List<DocumentDetailsVO> authDocument = new ArrayList<>();

	private List<AttachDocs> authDocumentList = new ArrayList<>();
	
	private List<DocumentDetailsVO> authDigitalPDF = new ArrayList<>();

	private List<AttachDocs> authDigitalPDFList = new ArrayList<>();
	
	private String panVerifiedFlag;
	
	private String authGenderDesc;
	
	private String isVerified;

	private String isActive;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String isDeleted;
	
	private String panDetailsFlag;
	
	public String getAuthUserName() {
		return authUserName;
	}

	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}

	public String getAuthEmail() {
		return authEmail;
	}

	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
	}

	public String getAuthPanNumber() {
		return authPanNumber;
	}

	public void setAuthPanNumber(String authPanNumber) {
		this.authPanNumber = authPanNumber;
	}


	public Date getAuthDOB() {
		return authDOB;
	}

	public void setAuthDOB(Date authDOB) {
		this.authDOB = authDOB;
	}

	public List<DocumentDetailsVO> getAuthDocument() {
		return authDocument;
	}

	public void setAuthDocument(List<DocumentDetailsVO> authDocument) {
		this.authDocument = authDocument;
	}

	public List<AttachDocs> getAuthDocumentList() {
		return authDocumentList;
	}

	public void setAuthDocumentList(List<AttachDocs> authDocumentList) {
		this.authDocumentList = authDocumentList;
	}

	public List<DocumentDetailsVO> getAuthDigitalPDF() {
		return authDigitalPDF;
	}

	public void setAuthDigitalPDF(List<DocumentDetailsVO> authDigitalPDF) {
		this.authDigitalPDF = authDigitalPDF;
	}

	public List<AttachDocs> getAuthDigitalPDFList() {
		return authDigitalPDFList;
	}

	public void setAuthDigitalPDFList(List<AttachDocs> authDigitalPDFList) {
		this.authDigitalPDFList = authDigitalPDFList;
	}

	public Long getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(Long authUserId) {
		this.authUserId = authUserId;
	}

	public Long getAuthMobileNo() {
		return authMobileNo;
	}

	public void setAuthMobileNo(Long authMobileNo) {
		this.authMobileNo = authMobileNo;
	}

	public Long getAuthGender() {
		return authGender;
	}

	public void setAuthGender(Long authGender) {
		this.authGender = authGender;
	}

	public String getPanVerifiedFlag() {
		return panVerifiedFlag;
	}

	public void setPanVerifiedFlag(String panVerifiedFlag) {
		this.panVerifiedFlag = panVerifiedFlag;
	}

	public String getAuthGenderDesc() {
		return authGenderDesc;
	}

	public void setAuthGenderDesc(String authGenderDesc) {
		this.authGenderDesc = authGenderDesc;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public DeveloperRegistrationDTO getDeveloperRegMas() {
		return developerRegMas;
	}

	public void setDeveloperRegMas(DeveloperRegistrationDTO developerRegMas) {
		this.developerRegMas = developerRegMas;
	}

	public String getPanDetailsFlag() {
		return panDetailsFlag;
	}

	public void setPanDetailsFlag(String panDetailsFlag) {
		this.panDetailsFlag = panDetailsFlag;
	}
}
