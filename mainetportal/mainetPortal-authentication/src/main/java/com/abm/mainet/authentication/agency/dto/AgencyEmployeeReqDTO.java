package com.abm.mainet.authentication.agency.dto;

import java.util.Date;

import com.abm.mainet.common.dto.RequestDTO;

public class AgencyEmployeeReqDTO extends RequestDTO {

    private static final long serialVersionUID = -3989420322394867L;

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

    private Long organisation;

    private Long userId;

    private Date ondate;

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

    private String lgIpMac;

    private String lgIpMacUpd;

    private String autMob;													// ValidateMobileNumber

    private String autEmail;												// ValidateEMailAddress

    private Long emplType;

    private String empGender;

    private String authStatus;

    private String empuwmsowner;

    private String empregistry;

    private String emprecord;

    private String empoutward;

    private String empnetwork;

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

    private String addFlag;

    private Long designationId;

    private Long deptLocId;

    private int langaugeId;

    public int getLangaugeId() {
        return langaugeId;
    }

    public void setLangaugeId(final int langaugeId) {
        this.langaugeId = langaugeId;
    }

    public Long getTitle() {
        return title;
    }

    public void setTitle(final Long title) {
        this.title = title;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(final String empname) {
        this.empname = empname;
    }

    public String getEmpMName() {
        return empMName;
    }

    public void setEmpMName(final String empMName) {
        this.empMName = empMName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(final String empLName) {
        this.empLName = empLName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(final String pincode) {
        this.pincode = pincode;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(final String userAlias) {
        this.userAlias = userAlias;
    }

    public String getEmploginname() {
        return emploginname;
    }

    public void setEmploginname(final String emploginname) {
        this.emploginname = emploginname;
    }

    public String getEmppassword() {
        return emppassword;
    }

    public void setEmppassword(final String emppassword) {
        this.emppassword = emppassword;
    }

    public String getEmppayrollnumber() {
        return emppayrollnumber;
    }

    public void setEmppayrollnumber(final String emppayrollnumber) {
        this.emppayrollnumber = emppayrollnumber;
    }

    public String getEmpisecuritykey() {
        return empisecuritykey;
    }

    public void setEmpisecuritykey(final String empisecuritykey) {
        this.empisecuritykey = empisecuritykey;
    }

    public String getEmppiservername() {
        return emppiservername;
    }

    public void setEmppiservername(final String emppiservername) {
        this.emppiservername = emppiservername;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(final String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Long getOrganisation() {
        return organisation;
    }

    public void setOrganisation(final Long organisation) {
        this.organisation = organisation;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Date getOndate() {
        return ondate;
    }

    public void setOndate(final Date ondate) {
        this.ondate = ondate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getEmpemail() {
        return empemail;
    }

    public void setEmpemail(final String empemail) {
        this.empemail = empemail;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(final String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpAddress1() {
        return empAddress1;
    }

    public void setEmpAddress1(final String empAddress1) {
        this.empAddress1 = empAddress1;
    }

    public Date getEmpexpiredt() {
        return empexpiredt;
    }

    public void setEmpexpiredt(final Date empexpiredt) {
        this.empexpiredt = empexpiredt;
    }

    public String getLockUnlock() {
        return lockUnlock;
    }

    public void setLockUnlock(final String lockUnlock) {
        this.lockUnlock = lockUnlock;
    }

    public String getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(final String loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Boolean getEmpnew() {
        return empnew;
    }

    public void setEmpnew(final Boolean empnew) {
        this.empnew = empnew;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public Date getEmpdob() {
        return empdob;
    }

    public void setEmpdob(final Date empdob) {
        this.empdob = empdob;
    }

    public String getEmpmobno() {
        return empmobno;
    }

    public void setEmpmobno(final String empmobno) {
        this.empmobno = empmobno;
    }

    public String getEmpphoneno() {
        return empphoneno;
    }

    public void setEmpphoneno(final String empphoneno) {
        this.empphoneno = empphoneno;
    }

    public Long getGmid() {
        return gmid;
    }

    public void setGmid(final Long gmid) {
        this.gmid = gmid;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getAutMob() {
        return autMob;
    }

    public void setAutMob(final String autMob) {
        this.autMob = autMob;
    }

    public String getAutEmail() {
        return autEmail;
    }

    public void setAutEmail(final String autEmail) {
        this.autEmail = autEmail;
    }

    public Long getEmplType() {
        return emplType;
    }

    public void setEmplType(final Long emplType) {
        this.emplType = emplType;
    }

    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(final String empGender) {
        this.empGender = empGender;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(final String authStatus) {
        this.authStatus = authStatus;
    }

    public String getEmpuwmsowner() {
        return empuwmsowner;
    }

    public void setEmpuwmsowner(final String empuwmsowner) {
        this.empuwmsowner = empuwmsowner;
    }

    public String getEmpregistry() {
        return empregistry;
    }

    public void setEmpregistry(final String empregistry) {
        this.empregistry = empregistry;
    }

    public String getEmprecord() {
        return emprecord;
    }

    public void setEmprecord(final String emprecord) {
        this.emprecord = emprecord;
    }

    public String getEmpoutward() {
        return empoutward;
    }

    public void setEmpoutward(final String empoutward) {
        this.empoutward = empoutward;
    }

    public String getEmpnetwork() {
        return empnetwork;
    }

    public void setEmpnetwork(final String empnetwork) {
        this.empnetwork = empnetwork;
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

    public String getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(final String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(final String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(final String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getAgencyRegNo() {
        return agencyRegNo;
    }

    public void setAgencyRegNo(final Integer agencyRegNo) {
        this.agencyRegNo = agencyRegNo;
    }

    public String getAgencyLocation() {
        return agencyLocation;
    }

    public void setAgencyLocation(final String agencyLocation) {
        this.agencyLocation = agencyLocation;
    }

    public Integer getAgencyYearsOfExp() {
        return agencyYearsOfExp;
    }

    public void setAgencyYearsOfExp(final Integer agencyYearsOfExp) {
        this.agencyYearsOfExp = agencyYearsOfExp;
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

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(final Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public String getSignature() {
        return signature;
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

    public String getMobNoOtp() {
        return mobNoOtp;
    }

    public void setMobNoOtp(final String mobNoOtp) {
        this.mobNoOtp = mobNoOtp;
    }

    public String getHasAapleUSer() {
        return hasAapleUSer;
    }

    public void setHasAapleUSer(final String hasAapleUSer) {
        this.hasAapleUSer = hasAapleUSer;
    }

    public String getAapleUserId() {
        return aapleUserId;
    }

    public void setAapleUserId(final String aapleUserId) {
        this.aapleUserId = aapleUserId;
    }

    public String getStatusIS() {
        return statusIS;
    }

    public void setStatusIS(final String statusIS) {
        this.statusIS = statusIS;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(final String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public long getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(final long hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getHospitalNameInHindi() {
        return hospitalNameInHindi;
    }

    public void setHospitalNameInHindi(final String hospitalNameInHindi) {
        this.hospitalNameInHindi = hospitalNameInHindi;
    }

    public String getHospitalAddressInHindi() {
        return hospitalAddressInHindi;
    }

    public void setHospitalAddressInHindi(final String hospitalAddressInHindi) {
        this.hospitalAddressInHindi = hospitalAddressInHindi;
    }

    public String getHospitalTypeName() {
        return hospitalTypeName;
    }

    public void setHospitalTypeName(final String hospitalTypeName) {
        this.hospitalTypeName = hospitalTypeName;
    }

    public String getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(final String addFlag) {
        this.addFlag = addFlag;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(final Long designationId) {
        this.designationId = designationId;
    }

    public Long getDeptLocId() {
        return deptLocId;
    }

    public void setDeptLocId(final Long deptLocId) {
        this.deptLocId = deptLocId;
    }

}
