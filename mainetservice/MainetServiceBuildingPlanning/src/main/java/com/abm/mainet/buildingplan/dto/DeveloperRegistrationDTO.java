package com.abm.mainet.buildingplan.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class DeveloperRegistrationDTO {

	private Long devId;

    private Long tcp_dev_appno;
	
	private String directorInfoFlag;
	
	private String licenseInfoFlag;
	
	private String licenseHDRUFlag;
	
	private String projectsFlag;
			
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
		
	private List<DeveloperStakeholderDTO> developerStakeholderDTOList = new ArrayList<>();
	
	private List<DeveloperAuthorizedUserDTO> developerAuthorizedUserDTOList = new ArrayList<>();
	
	private List<DeveloperDirectorInfoDTO> developerDirectorDetailsDTOList = new ArrayList<>();
	
	private List<DeveloperDirectorInfoDTO> developerDirectorInfoDTOList = new ArrayList<>();
	
	private List<DevLicenseHDRUDTO> devLicenseHDRUDTOList = new ArrayList<>();
	
	private Long devType;
	
	private String devTypeDesc;
	
	private String devTypeCode;
	
	private String cinNo;
	
	private String llpNo;
	
	private String companyName;
		
	private Date dateOfIncorporation;
	
	private String registeredAddress;
	
	private String email;
	
	private Long mobileNo;
	
	private String gstNo;
	
	private String name;
			
	private Long gender;
	
	private String genderDesc;
	
	private Date dateOfBirth;
	
	private String panNo;
			
	private String licenseNo;
	
	private String devName;
		
	private String projectName;
		
	private Date devLicenseDate;
	
	private String athorityName;
	
	private String devStatus;
	
	private String areaOfProject;
	
	private String location;
	
	private List<DocumentDetailsVO> allDocumentList = new ArrayList<>();
		
	private String cinDetailsFlag;
	
	private  Map<String, Set<File>> fileList = new LinkedHashMap<>();
		
	private Long orgId;
	
	private long langId;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long updatedBy;
	
	private Date updatedDate;
	
	private String lgIpMac;
	
	private String lgIpMacUp;
	
	private String draftFlag;
	
	private String indPanVerifiedFlag;
	
	private List<CFCAttachment> checkListDocumentSet;
	
	private String companyDetailsAPIFlag;
	
	private String firmName;
		
	public String getDirectorInfoFlag() {
		return directorInfoFlag;
	}

	public void setDirectorInfoFlag(String directorInfoFlag) {
		this.directorInfoFlag = directorInfoFlag;
	}

	public String getLicenseInfoFlag() {
		return licenseInfoFlag;
	}

	public void setLicenseInfoFlag(String licenseInfoFlag) {
		this.licenseInfoFlag = licenseInfoFlag;
	}

	public String getLicenseHDRUFlag() {
		return licenseHDRUFlag;
	}

	public void setLicenseHDRUFlag(String licenseHDRUFlag) {
		this.licenseHDRUFlag = licenseHDRUFlag;
	}

	public String getProjectsFlag() {
		return projectsFlag;
	}

	public void setProjectsFlag(String projectsFlag) {
		this.projectsFlag = projectsFlag;
	}


	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<DeveloperStakeholderDTO> getDeveloperStakeholderDTOList() {
		return developerStakeholderDTOList;
	}

	public void setDeveloperStakeholderDTOList(List<DeveloperStakeholderDTO> developerStakeholderDTOList) {
		this.developerStakeholderDTOList = developerStakeholderDTOList;
	}

	public List<DeveloperAuthorizedUserDTO> getDeveloperAuthorizedUserDTOList() {
		return developerAuthorizedUserDTOList;
	}

	public void setDeveloperAuthorizedUserDTOList(List<DeveloperAuthorizedUserDTO> developerAuthorizedUserDTOList) {
		this.developerAuthorizedUserDTOList = developerAuthorizedUserDTOList;
	}

	public List<DeveloperDirectorInfoDTO> getDeveloperDirectorInfoDTOList() {
		return developerDirectorInfoDTOList;
	}

	public void setDeveloperDirectorInfoDTOList(List<DeveloperDirectorInfoDTO> developerDirectorInfoDTOList) {
		this.developerDirectorInfoDTOList = developerDirectorInfoDTOList;
	}

	public List<DevLicenseHDRUDTO> getDevLicenseHDRUDTOList() {
		return devLicenseHDRUDTOList;
	}

	public void setDevLicenseHDRUDTOList(List<DevLicenseHDRUDTO> devLicenseHDRUDTOList) {
		this.devLicenseHDRUDTOList = devLicenseHDRUDTOList;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getDevLicenseDate() {
		return devLicenseDate;
	}

	public void setDevLicenseDate(Date devLicenseDate) {
		this.devLicenseDate = devLicenseDate;
	}

	public String getAthorityName() {
		return athorityName;
	}

	public void setAthorityName(String athorityName) {
		this.athorityName = athorityName;
	}

	public String getDevStatus() {
		return devStatus;
	}

	public void setDevStatus(String devStatus) {
		this.devStatus = devStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public Long getDevType() {
		return devType;
	}

	public void setDevType(Long devType) {
		this.devType = devType;
	}

	public String getDevTypeDesc() {
		return devTypeDesc;
	}

	public void setDevTypeDesc(String devTypeDesc) {
		this.devTypeDesc = devTypeDesc;
	}

	public Date getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(Date dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}

	public String getGenderDesc() {
		return genderDesc;
	}

	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}


	public String getAreaOfProject() {
		return areaOfProject;
	}

	public void setAreaOfProject(String areaOfProject) {
		this.areaOfProject = areaOfProject;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDevTypeCode() {
		return devTypeCode;
	}

	public void setDevTypeCode(String devTypeCode) {
		this.devTypeCode = devTypeCode;
	}

	public String getCinNo() {
		return cinNo;
	}

	public void setCinNo(String cinNo) {
		this.cinNo = cinNo;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public List<DocumentDetailsVO> getAllDocumentList() {
		return allDocumentList;
	}

	public void setAllDocumentList(List<DocumentDetailsVO> allDocumentList) {
		this.allDocumentList = allDocumentList;
	}

	public Map<String, Set<File>> getFileList() {
		return fileList;
	}

	public void setFileList(Map<String, Set<File>> fileList) {
		this.fileList = fileList;
	}

	public List<DeveloperDirectorInfoDTO> getDeveloperDirectorDetailsDTOList() {
		return developerDirectorDetailsDTOList;
	}

	public void setDeveloperDirectorDetailsDTOList(List<DeveloperDirectorInfoDTO> developerDirectorDetailsDTOList) {
		this.developerDirectorDetailsDTOList = developerDirectorDetailsDTOList;
	}

	public String getCinDetailsFlag() {
		return cinDetailsFlag;
	}

	public void setCinDetailsFlag(String cinDetailsFlag) {
		this.cinDetailsFlag = cinDetailsFlag;
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

	public String getDraftFlag() {
		return draftFlag;
	}

	public void setDraftFlag(String draftFlag) {
		this.draftFlag = draftFlag;
	}

	public String getIndPanVerifiedFlag() {
		return indPanVerifiedFlag;
	}

	public void setIndPanVerifiedFlag(String indPanVerifiedFlag) {
		this.indPanVerifiedFlag = indPanVerifiedFlag;
	}

	public String getLlpNo() {
		return llpNo;
	}

	public void setLlpNo(String llpNo) {
		this.llpNo = llpNo;
	}

	public Long getTcp_dev_appno() {
		return tcp_dev_appno;
	}

	public void setTcp_dev_appno(Long tcp_dev_appno) {
		this.tcp_dev_appno = tcp_dev_appno;
	}


	public Long getDevId() {
		return devId;
	}

	public void setDevId(Long devId) {
		this.devId = devId;
	}

	public List<CFCAttachment> getCheckListDocumentSet() {
		return checkListDocumentSet;
	}

	public void setCheckListDocumentSet(List<CFCAttachment> checkListDocumentSet) {
		this.checkListDocumentSet = checkListDocumentSet;
	}

	public String getCompanyDetailsAPIFlag() {
		return companyDetailsAPIFlag;
	}

	public void setCompanyDetailsAPIFlag(String companyDetailsAPIFlag) {
		this.companyDetailsAPIFlag = companyDetailsAPIFlag;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

}
