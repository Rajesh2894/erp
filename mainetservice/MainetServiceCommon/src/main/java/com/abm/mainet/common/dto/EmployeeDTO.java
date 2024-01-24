package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Vivek.Kumar
 *
 */
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 6087901100506067446L;

    private long empId;

    private Long title;

    private String empname;

    private String empMName;

    private String empLName;

    private String pincode;

    private String userAlias;

    private String emploginname;

    private String emppassword;

    private String emppayrollnumber;

    private String empisecuritykey;

    private String emppiservername;

    private String isdeleted;

    private String empreason;

    private OrganisationDTO organisation;

    private Long userId;

    private Date ondate;

    private Long updatedBy;

    private Date updatedDate;

    private String empemail;

    private String empAddress;

    private String empAddress1;

    private Date empexpiredt;

    private String lockUnlock;

    private String loggedIn;

    private Boolean empnew;

    private Long dpDeptid;

    private Date empdob;

    private String empmobno;

    private String empphoneno;

    private Long gmid;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private String autMob;													// ValidateMobileNumber

    private String autEmail;												// ValidateEMailAddress

    private Long emplType;

    private String empGender;

    private String authStatus;

    private String empCorAddress1;

    private String empCorAddress2;

    private String corPincode;

    private String panCardNo;

    private String employeeNo;

    private String agencyName;

    private Integer agencyRegNo;

    private String agencyLocation;

    private Integer agencyYearsOfExp;

    private String agencyQualification;

    private String isUploaded;							// TO check document uploaded or not

    private Date lastLoggedIn;

    private String signature;

    private String publicKey;

    private String mobNoOtp;

    private String hasAapleUSer;

    private String aapleUserId;

    private String statusIS;

    private String hospitalCode;

    private long hospitalType;

    private String hospitalNameInHindi;

    private String hospitalAddressInHindi;

    private String hospitalTypeName;

    private List<CFCAttachmentsDTO> cfcAttachments = new ArrayList<>(0);

    private Long designationId;

    private Long locaDeptId;

    public Long getGmid() {
        return gmid;
    }

    public void setGmid(final Long gmid) {
        this.gmid = gmid;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(final String empAddress) {
        this.empAddress = empAddress;
    }

    public String getStatusIS() {
        return statusIS;
    }

    public void setStatusIS(final String statusIS) {
        this.statusIS = statusIS;
    }

    private String agencyFlag;

    public String getAgencyFlag() {
        return agencyFlag;
    }

    public void setAgencyFlag(final String agencyFlag) {
        this.agencyFlag = agencyFlag;
    }

    public String getAgencyQualification() {
        return agencyQualification;
    }

    public void setAgencyQualification(final String agencyQualification) {
        this.agencyQualification = agencyQualification;
    }

    public String getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(final String isUploaded) {
        this.isUploaded = isUploaded;
    }

    public Integer getAgencyYearsOfExp() {
        return agencyYearsOfExp;
    }

    public void setAgencyYearsOfExp(final Integer agencyYearsOfExp) {
        this.agencyYearsOfExp = agencyYearsOfExp;
    }

    public String getAgencyLocation() {
        return agencyLocation;
    }

    public void setAgencyLocation(final String agencyLocation) {
        this.agencyLocation = agencyLocation;
    }

    public Integer getAgencyRegNo() {
        return agencyRegNo;
    }

    public void setAgencyRegNo(final Integer agencyRegNo) {
        this.agencyRegNo = agencyRegNo;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(final String agencyName) {
        this.agencyName = agencyName;
    }

    public String getEmpCorAddress1() {
        return empCorAddress1;
    }

    public void setEmpCorAddress1(final String empCorAddress1) {
        this.empCorAddress1 = empCorAddress1;
    }

    public String getEmpCorAddress2() {
        return empCorAddress2;
    }

    public void setEmpCorAddress2(final String empCorAddress2) {
        this.empCorAddress2 = empCorAddress2;
    }

    public String getCorPincode() {
        return corPincode;
    }

    public void setCorPincode(final String corPincode) {
        this.corPincode = corPincode;
    }

    /**
     * @return the emplType
     */
    public Long getEmplType() {
        return emplType;
    }

    /**
     * @return the title
     */
    public Long getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(final Long title) {
        this.title = title;
    }

    /**
     * @param emplType the emplType to set
     */
    public void setEmplType(final Long emplType) {
        this.emplType = emplType;
    }

    /**
     * @return the autMob
     */
    public String getAutMob() {
        return autMob;
    }

    /**
     * @param autMob the autMob to set
     */
    public void setAutMob(final String autMob) {
        this.autMob = autMob;
    }

    /**
     * @return the autEmail
     */
    public String getAutEmail() {
        return autEmail;
    }

    /**
     * @param autEmail the autEmail to set
     */
    public void setAutEmail(final String autEmail) {
        this.autEmail = autEmail;
    }

    /**
     * @return the empId
     */
    public long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(final long empId) {
        this.empId = empId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final int langId) {
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the empname
     */
    public String getEmpname() {
        return empname;
    }

    /**
     * @param empname the empname to set
     */
    public void setEmpname(final String empname) {
        this.empname = empname;
    }

    /**
     * @param empname the empAlias to set
     */
    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(final String userAlias) {
        this.userAlias = userAlias;
    }

    /**
     * @return the emploginname
     */
    public String getEmploginname() {
        return emploginname;
    }

    /**
     * @param emploginname the emploginname to set
     */
    public void setEmploginname(final String emploginname) {
        this.emploginname = emploginname;
    }

    /**
     * @return the emppassword
     */
    public String getEmppassword() {
        return emppassword;
    }

    /**
     * @param emppassword the emppassword to set
     */
    public void setEmppassword(final String emppassword) {
        this.emppassword = emppassword;
    }

    /**
     * @return the emppayrollnumber
     */
    public String getEmppayrollnumber() {
        return emppayrollnumber;
    }

    /**
     * @param emppayrollnumber the emppayrollnumber to set
     */
    public void setEmppayrollnumber(final String emppayrollnumber) {
        this.emppayrollnumber = emppayrollnumber;
    }

    /**
     * @return the empisecuritykey
     */
    public String getEmpisecuritykey() {
        return empisecuritykey;
    }

    /**
     * @param empisecuritykey the empisecuritykey to set
     */
    public void setEmpisecuritykey(final String empisecuritykey) {
        this.empisecuritykey = empisecuritykey;
    }

    /**
     * @return the emppiservername
     */
    public String getEmppiservername() {
        return emppiservername;
    }

    /**
     * @param emppiservername the emppiservername to set
     */
    public void setEmppiservername(final String emppiservername) {
        this.emppiservername = emppiservername;
    }

    /**
     * @return the emponame
     */

    /**
     * @return the isdeleted
     */
    public String getIsdeleted() {
        return isdeleted;
    }

    /**
     * @param isdeleted the isdeleted to set
     */
    public void setIsdeleted(final String isdeleted) {
        this.isdeleted = isdeleted;
    }

    /**
     * @return the ondate
     */
    public Date getOndate() {
        return ondate;
    }

    /**
     * @param ondate the ondate to set
     */
    public void setOndate(final Date ondate) {
        this.ondate = ondate;
    }

    /**
     * @return the empemail
     */
    public String getEmpemail() {
        return empemail;
    }

    /**
     * @param empemail the empemail to set
     */
    public void setEmpemail(final String empemail) {
        this.empemail = empemail;
    }

    /**
     * @return the empexpiredt
     */
    public Date getEmpexpiredt() {
        return empexpiredt;
    }

    /**
     * @param empexpiredt the empexpiredt to set
     */
    public void setEmpexpiredt(final Date empexpiredt) {
        this.empexpiredt = empexpiredt;
    }

    /**
     * @return the lockUnlock
     */
    public String getLockUnlock() {
        return lockUnlock;
    }

    /**
     * @param lockUnlock the lockUnlock to set
     */
    public void setLockUnlock(final String lockUnlock) {
        this.lockUnlock = lockUnlock;
    }

    /**
     * @return the loggedIn
     */
    public String getLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(final String loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * @return the empnew
     */
    public Boolean getEmpnew() {
        return empnew;
    }

    /**
     * @param empnew the empnew to set
     */
    public void setEmpnew(final Boolean empnew) {
        this.empnew = empnew;
    }

    /**
     * @return the dpDeptid
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * @param dpDeptid the dpDeptid to set
     */
    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    /**
     * @return the empdob
     */
    public Date getEmpdob() {
        return empdob;
    }

    /**
     * @param empdob the empdob to set
     */
    public void setEmpdob(final Date empdob) {
        this.empdob = empdob;
    }

    /**
     * @return the empmobno
     */
    public String getEmpmobno() {
        return empmobno;
    }

    /**
     * @param empmobno the empmobno to set
     */
    public void setEmpmobno(final String empmobno) {
        this.empmobno = empmobno;
    }

    /**
     * @return the empphoneno
     */
    public String getEmpphoneno() {
        return empphoneno;
    }

    /**
     * @param empphoneno the empphoneno to set
     */
    public void setEmpphoneno(final String empphoneno) {
        this.empphoneno = empphoneno;
    }

    /**
     * @return the empAddress1
     */
    public String getEmpAddress1() {
        return empAddress1;
    }

    /**
     * @param empAddress1 the empAddress1 to set
     */
    public void setEmpAddress1(final String empAddress1) {
        this.empAddress1 = empAddress1;
    }

    /**
     * @return the empMName
     */
    public String getEmpMName() {
        return empMName;
    }

    /**
     * @param empMName the empMName to set
     */
    public void setEmpMName(final String empMName) {
        this.empMName = empMName;
    }

    /**
     * @return the empLName
     */
    public String getEmpLName() {
        return empLName;
    }

    /**
     * @param empLName the empLName to set
     */
    public void setEmpLName(final String empLName) {
        this.empLName = empLName;
    }

    /**
     * @return the pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     * @param pincode the pincode to set
     */
    public void setPincode(final String pincode) {
        this.pincode = pincode;
    }

    /**
     * @return the empGender
     */
    public String getEmpGender() {
        return empGender;
    }

    /**
     * @param empGender the empGender to set
     */
    public void setEmpGender(final String empGender) {
        this.empGender = empGender;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    /**
     * @return the panCardNo
     */
    public String getPanCardNo() {
        return panCardNo;
    }

    /**
     * @param panCardNo the panCardNo to set
     */
    public void setPanCardNo(final String panCardNo) {
        this.panCardNo = panCardNo;
    }

    /*
     * @return the employeeNo
     */
    public String getEmployeeNo() {
        return employeeNo;
    }

    /**
     * @param employeeNo the employeeNo to set
     */
    public void setEmployeeNo(final String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(final String authStatus) {
        this.authStatus = authStatus;
    }

    /**
     * @return the hospitalCode
     */
    public String getHospitalCode() {
        return hospitalCode;
    }

    /**
     * @param hospitalCode the hospitalCode to set
     */
    public void setHospitalCode(final String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    /**
     * @return the hospitalType
     */
    public long getHospitalType() {
        return hospitalType;
    }

    /**
     * @param hospitalType the hospitalType to set
     */
    public void setHospitalType(final long hospitalType) {
        this.hospitalType = hospitalType;
    }

    /**
     * @return the hospitalNameInHindi
     */
    public String getHospitalNameInHindi() {
        return hospitalNameInHindi;
    }

    /**
     * @param hospitalNameInHindi the hospitalNameInHindi to set
     */
    public void setHospitalNameInHindi(final String hospitalNameInHindi) {
        this.hospitalNameInHindi = hospitalNameInHindi;
    }

    /**
     * @return the hospitalAddressInHindi
     */
    public String getHospitalAddressInHindi() {
        return hospitalAddressInHindi;
    }

    /**
     * @param hospitalAddressInHindi the hospitalAddressInHindi to set
     */
    public void setHospitalAddressInHindi(final String hospitalAddressInHindi) {
        this.hospitalAddressInHindi = hospitalAddressInHindi;
    }

    /**
     * @return the lastLoggedIn
     */
    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    /**
     * @param lastLoggedIn the lastLoggedIn to set
     */
    public void setLastLoggedIn(final Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    /**
     * @return the hospitalTypeName
     */
    public String getHospitalTypeName() {
        return hospitalTypeName;
    }

    /**
     * @param hospitalTypeName the hospitalTypeName to set
     */
    public void setHospitalTypeName(final String hospitalTypeName) {
        this.hospitalTypeName = hospitalTypeName;
    }

    public String getMobNoOtp() {
        return mobNoOtp;
    }

    public void setMobNoOtp(final String mobNoOtp) {
        this.mobNoOtp = mobNoOtp;
    }

    public String getSignature() {
        return signature;
    }

    public String getHasAapleUSer() {
        return hasAapleUSer;
    }

    public void setHasAapleUSer(final String hasAapleUSer) {
        this.hasAapleUSer = hasAapleUSer;
    }

    public void setSignature(final String signature) {
        this.signature = signature;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(final String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAapleUserId() {
        return aapleUserId;
    }

    public void setAapleUserId(final String aapleUserId) {
        this.aapleUserId = aapleUserId;
    }

    /**
     * @return the cfcAttachments
     */
    public List<CFCAttachmentsDTO> getCfcAttachments() {
        return cfcAttachments;
    }

    /**
     * @param cfcAttachments the cfcAttachments to set
     */
    public void setCfcAttachments(final List<CFCAttachmentsDTO> cfcAttachments) {
        this.cfcAttachments = cfcAttachments;
    }

    public String getSelectTemplet() {

        final StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("<a href='javascript:void(0);' onclick=\"getEmployeeDetails('" + empId + "')\">");
        sBuilder.append(
                "<img src='css/images/view.png' width='20px' alt='Deletion Of Hoarding Data' title='Print Inspection' />");
        sBuilder.append("</a>");

        return sBuilder.toString();
    }

    /**
     * @return the designationId
     */
    public Long getDesignationId() {
        return designationId;
    }

    /**
     * @param designationId the designationId to set
     */
    public void setDesignationId(final Long designationId) {
        this.designationId = designationId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the organisation
     */
    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    /**
     * @param organisation the organisation to set
     */
    public void setOrganisation(final OrganisationDTO organisation) {
        this.organisation = organisation;
    }

    /**
     * @return the locaDeptId
     */
    public Long getLocaDeptId() {
        return locaDeptId;
    }

    /**
     * @param locaDeptId the locaDeptId to set
     */
    public void setLocaDeptId(final Long locaDeptId) {
        this.locaDeptId = locaDeptId;
    }

    public String getEmpreason() {
        return empreason;
    }

    public void setEmpreason(String empreason) {
        this.empreason = empreason;
    }

}
