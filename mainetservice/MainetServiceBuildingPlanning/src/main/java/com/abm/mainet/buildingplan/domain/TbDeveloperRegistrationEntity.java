package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_DEV_REG")
public class TbDeveloperRegistrationEntity implements Serializable {

	private static final long serialVersionUID = 2636333015873037996L;

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ID", nullable = false)
    private Long devId;

    @Column(name = "TCP_DEV_APPNO", length = 20, nullable = true)
    private Long tcp_dev_appno;

    @Column(name = "TCP_DEV_TYP_ID", length = 20, nullable = true)
    private Long devType;

    @Column(name = "TCP_DEV_CIN_NO", length = 22, nullable = true)
    private String cinNo;

    @Column(name = "TCP_DEV_COMP", length = 100, nullable = true)
    private String companyName;
    
    @Column(name = "TCP_DEV_DATE_OF_INCORP ", nullable = true)
    private Date dateOfIncorporation;

    @Column(name = "TCP_DEV_REG_ADD", nullable = true)
    private String registeredAddress;

    @Column(name = "TCP_DEV_EMAIL", length = 45, nullable = true)
    private String email;

    @Column(name = "TCP_DEV_MOB", length = 10, nullable = true)
    private Long mobileNo;

    @Column(name = "TCP_DEV_GST_NO", length = 15, nullable = true)
    private String gstNo;
    
    @Column(name = "NAME", length = 45, nullable = true)
    private String name;

    @Column(name = "GENDER", length = 20, nullable = true)
    private Long gender;
       
    @Column(name = "DATE_OF_BIRTH", nullable = true)
    private Date dateOfBirth;
    
    @Column(name = "PAN", length = 10, nullable = true)
    private String panNo;
    
    @Column(name = "LLP_NUMBER", length = 10, nullable = true)
    private String llpNo;    
    
    @Column(name = "TCP_DEV_GRANTED_LIC", length = 1, nullable = true)
    private String licenseInfoFlag;

    @Column(name = "TCP_DEV_LICENCE", length = 45, nullable = true)
    private String licenseNo;

    @Column(name = "TCP_DEV_DEC_NAME", length = 45, nullable = true)
    private String devName;
    
    @Column(name = "director_Info_Flag", length = 1, nullable = true)
    private String directorInfoFlag;

    @Column(name = "TCP_DEV_GRANT_DATE", nullable = true)
    private Date devLicenseDate;

    @Column(name = "TCP_DEV_HAD_PERMISSIN", length = 1, nullable = true)
    private String licenseHDRUFlag;
    
    @Column(name = "DEV_PROJ_OUTSIDE", length = 1, nullable = true)
    private String projectsFlag;
    
    @Column(name = "DPO_PROJ_NAME", length = 45, nullable = true)
    private String projectName;
    
    @Column(name = "DPO_Auth_NAME", length = 45, nullable = true)
    private String athorityName;
    
    @Column(name = "DPO_DEV_STATUS", length = 45, nullable = true)
    private String devStatus;

    @Column(name = "DPO_PROJ_AREA", length = 45, nullable = true)
    private String areaOfProject;
    
    @Column(name = "DPO_LOC", length = 45, nullable = true)
    private String location;
    
    @Column(name = "CREATED_BY", length = 45, nullable = true)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "MODIFIED_BY", length = 45, nullable = true)
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String Is_deleted;

    @Column(name = "LG_IP_MAC", length = 20, nullable = true)
    private String lgIpMac;

    @Column(name = "DRAFT_FLAG", length = 3, nullable = true)
    private String draftFlag;
    
    @Column(name = "Firm_Name", length = 100, nullable = true)
    private String firmName;
    
    @Column(name = "company_details_API_Flag", length = 1, nullable = true)
    private String companyDetailsAPIFlag;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developerRegMas", cascade = CascadeType.ALL)
	private List<TbStkhldrMasEntity> developerStakeholderDTOList;
	
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developerRegMas", cascade = CascadeType.ALL)
	private List<TbDirectorMasEntity> developerDirectorInfoDTOList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "developerRegMas", cascade = CascadeType.ALL)
	private List<TbAuthUserMasEntity> developerAuthorizedUserDTOList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "developerRegMas", cascade = CascadeType.ALL)
	private List<TbDevPerGrntMasEntity> devLicenseHDRUDTOList;

	public Long getTcp_dev_appno() {
		return tcp_dev_appno;
	}

	public void setTcp_dev_appno(Long tcp_dev_appno) {
		this.tcp_dev_appno = tcp_dev_appno;
	}

	public String getIs_deleted() {
		return Is_deleted;
	}

	public void setIs_deleted(String is_deleted) {
		Is_deleted = is_deleted;
	}

	public String[] getPkValues() {
        return new String[] { "DRN", "TB_BPMS_DEV_REG", "ID" };
    }

	public Long getDevType() {
		return devType;
	}

	public void setDevType(Long devType) {
		this.devType = devType;
	}

	public String getCinNo() {
		return cinNo;
	}

	public void setCinNo(String cinNo) {
		this.cinNo = cinNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(Date dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getLicenseInfoFlag() {
		return licenseInfoFlag;
	}

	public void setLicenseInfoFlag(String licenseInfoFlag) {
		this.licenseInfoFlag = licenseInfoFlag;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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

	public Date getDevLicenseDate() {
		return devLicenseDate;
	}

	public void setDevLicenseDate(Date devLicenseDate) {
		this.devLicenseDate = devLicenseDate;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}


	public List<TbStkhldrMasEntity> getDeveloperStakeholderDTOList() {
		return developerStakeholderDTOList;
	}

	public void setDeveloperStakeholderDTOList(List<TbStkhldrMasEntity> developerStakeholderDTOList) {
		this.developerStakeholderDTOList = developerStakeholderDTOList;
	}

	public List<TbDirectorMasEntity> getDeveloperDirectorInfoDTOList() {
		return developerDirectorInfoDTOList;
	}

	public void setDeveloperDirectorInfoDTOList(List<TbDirectorMasEntity> developerDirectorInfoDTOList) {
		this.developerDirectorInfoDTOList = developerDirectorInfoDTOList;
	}

	public List<TbAuthUserMasEntity> getDeveloperAuthorizedUserDTOList() {
		return developerAuthorizedUserDTOList;
	}

	public void setDeveloperAuthorizedUserDTOList(List<TbAuthUserMasEntity> developerAuthorizedUserDTOList) {
		this.developerAuthorizedUserDTOList = developerAuthorizedUserDTOList;
	}

	public List<TbDevPerGrntMasEntity> getDevLicenseHDRUDTOList() {
		return devLicenseHDRUDTOList;
	}

	public void setDevLicenseHDRUDTOList(List<TbDevPerGrntMasEntity> devLicenseHDRUDTOList) {
		this.devLicenseHDRUDTOList = devLicenseHDRUDTOList;
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

	public String getDraftFlag() {
		return draftFlag;
	}

	public void setDraftFlag(String draftFlag) {
		this.draftFlag = draftFlag;
	}

	public String getLlpNo() {
		return llpNo;
	}

	public void setLlpNo(String llpNo) {
		this.llpNo = llpNo;
	}

	public Long getDevId() {
		return devId;
	}

	public void setDevId(Long devId) {
		this.devId = devId;
	}

	public String getDirectorInfoFlag() {
		return directorInfoFlag;
	}

	public void setDirectorInfoFlag(String directorInfoFlag) {
		this.directorInfoFlag = directorInfoFlag;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getCompanyDetailsAPIFlag() {
		return companyDetailsAPIFlag;
	}

	public void setCompanyDetailsAPIFlag(String companyDetailsAPIFlag) {
		this.companyDetailsAPIFlag = companyDetailsAPIFlag;
	}
	
}