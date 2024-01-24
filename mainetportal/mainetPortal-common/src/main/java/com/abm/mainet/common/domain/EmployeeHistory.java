/*
 * Created on 19 Aug 2015 ( Time 17:09:55 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite)

package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "EMPLOYEE"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "EMPLOYEE_HIST")

public class EmployeeHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "EMPID_H", nullable = false)
    private Long empHistId;

    @Column(name = "EMPID", nullable = false)
    private Long empId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "CPD_TTL_ID", precision = 15, scale = 0, nullable = true)
    // comments : title
    private Long title;

    @Column(name = "EMPNAME", length = 500, nullable = false)
    // comments : Employee Name
    private String empname;

    @Column(name = "EMPMNAME", length = 100, nullable = true)
    // comments : Middle Name
    private String empMName;

    @Column(name = "EMPLNAME", length = 100, nullable = true)
    // comments : Last Name
    private String empLName;

    @Column(name = "EMPPINCODE", precision = 6, scale = 0, nullable = true)
    // comments : Employee PIN Code
    private String pincode;

    @Column(name = "EMPOSLOGINNAME", length = 50, nullable = true)
    // comments : Employee Login name
    private String userAlias;

    @Column(name = "EMPLOGINNAME", length = 50, nullable = true)
    // comments : Employee Login Name
    private String emploginname;

    @Column(name = "EMPPASSWORD", length = 50, nullable = true)
    // comments : Employee password
    private String emppassword;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DSGID", referencedColumnName = "DSGID")
    private Designation designation;

    @Column(name = "EMPPAYROLLNUMBER", length = 10, nullable = true)
    // comments : Stores the payroll number of the employee (Not in use)
    private String emppayrollnumber;

    @Column(name = "EMPISECURITYKEY", length = 20, nullable = true)
    // comments : Stores the Ikey Security number for a particular key (Not in use)
    private String empisecuritykey;

    @Column(name = "EMPPISERVERNAME", length = 20, nullable = true)
    // comments : Stores servername of the employee. (Not in use)
    private String emppiservername;

    @Column(name = "ISDELETED", length = 1, nullable = true)
    // comments : Flag to identify whether the record is deleted or not. 1 for deleted (Inactive) and 0 for not deleted (Active)
    // record.
    private String isdeleted;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ORGID", referencedColumnName = "ORGID")
    private Organisation organisation;

    @Column(name = "SYNOYNMX", precision = 0, scale = 0, nullable = true)
    // comments : Synoynmx (Not in use)
    private Long synoynmx;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Creation date
    private Date ondate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Updated date
    private Date updatedDate;

    @Column(name = "EMPEMAIL", length = 50, nullable = true)
    // comments : Employee Email Id
    private String empemail;

    @Column(name = "EMP_ADDRESS", length = 100, nullable = true)
    // comments : EMPLOYEE ADDRESS
    private String empAddress;

    @Column(name = "EMP_ADDRESS1", length = 2000, nullable = true)
    // comments : Employee Address 1
    private String empAddress1;

    @Column(name = "EMPEXPIREDT", nullable = true)
    // comments : Employee login expiry date
    private Date empexpiredt;

    @Column(name = "LOCK_UNLOCK", length = 1, nullable = true)
    // comments : Flag to identify whether employee is lock or Unlock. 'L' for lock and 'U' for unlock
    private String lockUnlock;

    @Column(name = "LOGGED_IN_ATTEMPT", length = 2)
    private Integer loggedInAttempt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOCK_DATE")
    private Date lockDate;

    @Column(name = "LOGGED_IN", length = 1, nullable = true)
    // comments : Flag to identify logged in status of employee. 'Y' for logged in and 'N' for not.
    private String loggedIn;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department tbDepartment;

    @Column(name = "EMPMOBNO", length = 30, nullable = true)
    // comments : Employee Mobile No
    private String empmobno;

    @Column(name = "EMPPHONENO", length = 40, nullable = true)
    // comments : Employee Phone No
    private String empphoneno;

    @Column(name = "GM_ID")
    private Long gmid;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine’s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine’s Login Name | IP Address | Physical Address
    private String lgIpMacUpd;

    @Column(name = "AUT_MOB", length = 1, nullable = true)
    // comments : Validate Mobile number
    private String autMob;

    @Column(name = "EMPL_TYPE", precision = 12, scale = 0, nullable = true)
    // comments : New Employee Category prefix NEC
    private Long emplType;

    @Column(name = "REPORTING_EMPID", precision = 15, scale = 0, nullable = true)
    // comments : additional nvarchar2 aut_n2 to be used in future
    private Long reportingManager;

    @Column(name = "EMPNEW", precision = 1, scale = 0, nullable = true)
    // comments : Flag to identify whether login employee is new or old.
    private Long empnew;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "EMPDOB", nullable = true)
    @Temporal(value = TemporalType.DATE)
    // comments : Employee Date of Birth
    private Date empdob;

    @Column(name = "EMPUWMSOWNER", length = 1, nullable = true)
    // comments : Flag to identify whether employee is UWMS Owner or not.
    private String empuwmsowner;

    @Column(name = "EMPREGISTRY", length = 1, nullable = true)
    private String empregistry;

    @Column(name = "EMPRECORD", length = 1, nullable = true)
    private String emprecord;

    @Column(name = "EMPNETWORK", length = 1, nullable = true)
    private String empnetwork;

    @Column(name = "EMPOUTWARD", length = 1, nullable = true)
    private String empoutward;

    @Column(name = "AUT_BY", precision = 12, scale = 0, nullable = true)
    // comments : employee authorizationflag (approve- a/hold-h/reject-r)
    private Long autBy;

    @Column(name = "AUT_DATE", nullable = true)
    // comments : Authorisation Date
    private Date autDate;

    @Column(name = "CENTRALENO", length = 50, nullable = true)
    // comments : server name,owner name
    private String centraleno;

    @Column(name = "SCANSIGNATURE", length = 2000, nullable = true)
    // comments : Scan Signature Path
    private String scansignature;

    @Column(name = "EMPUID", length = 14, nullable = true)
    // comments : employee UID number
    private String empuid;

    @Column(name = "EMPUIDDOCPATH", length = 2000, nullable = true)
    // comments : employee UID path
    private String empuiddocpath;

    @Column(name = "EMPPHOTOPATH", length = 2000, nullable = true)
    // comments : employee photo path
    private String empphotopath;

    @Column(name = "EMPUIDDOCNAME", length = 100, nullable = true)
    // comments : employee UID file Name
    private String empuiddocname;

    @Column(name = "ADD_FLAG", length = 1, nullable = true)
    // comments : Flag to identify correspondance address same to Address Y/N
    private String addFlag;

    @Column(name = "AUTH_STATUS", length = 1, nullable = true)
    // comments : Employee AuthorizationFlag (Approve- A/Hold-H/Reject-R)
    private String authStatus;

    @Column(name = "EMP_GENDER", length = 1, nullable = true)
    // comments : GENDER M/F/T
    private String empGender;

    @Column(name = "ISUPLOADED", length = 1, nullable = true)
    // comments : Flag to identify whether Agency has uploaded the documents (default to N)
    private String isUploaded;

    @Column(name = "EMP_COR_ADD1", length = 2000, nullable = true)
    // comments : Correspondence Address line1
    private String empCorAddress1;

    @Column(name = "EMP_COR_ADD2", length = 2000, nullable = true)
    // comments : Correspondence Address line2
    private String empCorAddress2;

    @Column(name = "EMP_COR_PINCODE", precision = 6, scale = 0, nullable = true)
    // comments : Correspondence address pincode
    private String corPincode;

    @Column(name = "PAN_NO", length = 10)
    private String panCardNo;

    @Column(name = "LAST_LOGGEDIN")
    private Date lastLoggedIn;

    @Column(name = "SIGNATURE", length = 1)
    private String signature;

    @Column(name = "PUBLICKEY", length = 1)
    private String publicKey;

    @Column(name = "MOB_OTP", length = 50)
    private String mobNoOtp;

    @Column(name = "AUT_EMAIL", length = 1)
    private String autEmail;

    @Column(name = "EMPLOYEE_NO", length = 15)
    private String employeeNo;

    @Column(name = "AGENCY_NAME", length = 250)
    private String agencyName;

    @Column(name = "AGENCY_REG_NO", length = 15)
    private Integer agencyRegNo;

    @Column(name = "AGENCY_LOCATION", length = 250)
    private String agencyLocation;

    @Column(name = "AGENCY_NOOF_EXP", length = 3)
    private Integer agencyYearsOfExp;

    @Column(name = "AGENCY_QUALIFICATION", length = 50)
    private String agencyQualification;

    @Column(name = "H_STATUS", length = 1)
    private String status;
    
    @Column(name = "MOBILE_EXTENSION", length = 6)
    private String mobileExtension;

    public Long getEmpHistId() {
        return empHistId;
    }

    public void setEmpHistId(Long empHistId) {
        this.empHistId = empHistId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getTitle() {
        return title;
    }

    public void setTitle(Long title) {
        this.title = title;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmpMName() {
        return empMName;
    }

    public void setEmpMName(String empMName) {
        this.empMName = empMName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getEmploginname() {
        return emploginname;
    }

    public void setEmploginname(String emploginname) {
        this.emploginname = emploginname;
    }

    public String getEmppassword() {
        return emppassword;
    }

    public void setEmppassword(String emppassword) {
        this.emppassword = emppassword;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public String getEmppayrollnumber() {
        return emppayrollnumber;
    }

    public void setEmppayrollnumber(String emppayrollnumber) {
        this.emppayrollnumber = emppayrollnumber;
    }

    public String getEmpisecuritykey() {
        return empisecuritykey;
    }

    public void setEmpisecuritykey(String empisecuritykey) {
        this.empisecuritykey = empisecuritykey;
    }

    public String getEmppiservername() {
        return emppiservername;
    }

    public void setEmppiservername(String emppiservername) {
        this.emppiservername = emppiservername;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Long getSynoynmx() {
        return synoynmx;
    }

    public void setSynoynmx(Long synoynmx) {
        this.synoynmx = synoynmx;
    }

    public Employee getUserId() {
        return userId;
    }

    public void setUserId(Employee userId) {
        this.userId = userId;
    }

    public Date getOndate() {
        return ondate;
    }

    public void setOndate(Date ondate) {
        this.ondate = ondate;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getEmpemail() {
        return empemail;
    }

    public void setEmpemail(String empemail) {
        this.empemail = empemail;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpAddress1() {
        return empAddress1;
    }

    public void setEmpAddress1(String empAddress1) {
        this.empAddress1 = empAddress1;
    }

    public Date getEmpexpiredt() {
        return empexpiredt;
    }

    public void setEmpexpiredt(Date empexpiredt) {
        this.empexpiredt = empexpiredt;
    }

    public String getLockUnlock() {
        return lockUnlock;
    }

    public void setLockUnlock(String lockUnlock) {
        this.lockUnlock = lockUnlock;
    }

    public Integer getLoggedInAttempt() {
        return loggedInAttempt;
    }

    public void setLoggedInAttempt(Integer loggedInAttempt) {
        this.loggedInAttempt = loggedInAttempt;
    }

    public Date getLockDate() {
        return lockDate;
    }

    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }

    public String getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Department getTbDepartment() {
        return tbDepartment;
    }

    public void setTbDepartment(Department tbDepartment) {
        this.tbDepartment = tbDepartment;
    }

    public String getEmpmobno() {
        return empmobno;
    }

    public void setEmpmobno(String empmobno) {
        this.empmobno = empmobno;
    }

    public String getEmpphoneno() {
        return empphoneno;
    }

    public void setEmpphoneno(String empphoneno) {
        this.empphoneno = empphoneno;
    }

    public Long getGmid() {
        return gmid;
    }

    public void setGmid(Long gmid) {
        this.gmid = gmid;
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

    public String getAutMob() {
        return autMob;
    }

    public void setAutMob(String autMob) {
        this.autMob = autMob;
    }

    public Long getEmplType() {
        return emplType;
    }

    public void setEmplType(Long emplType) {
        this.emplType = emplType;
    }

    public Long getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(Long reportingManager) {
        this.reportingManager = reportingManager;
    }

    public Long getEmpnew() {
        return empnew;
    }

    public void setEmpnew(Long empnew) {
        this.empnew = empnew;
    }

    public Date getEmpdob() {
        return empdob;
    }

    public void setEmpdob(Date empdob) {
        this.empdob = empdob;
    }

    public String getEmpuwmsowner() {
        return empuwmsowner;
    }

    public void setEmpuwmsowner(String empuwmsowner) {
        this.empuwmsowner = empuwmsowner;
    }

    public String getEmpregistry() {
        return empregistry;
    }

    public void setEmpregistry(String empregistry) {
        this.empregistry = empregistry;
    }

    public String getEmprecord() {
        return emprecord;
    }

    public void setEmprecord(String emprecord) {
        this.emprecord = emprecord;
    }

    public String getEmpnetwork() {
        return empnetwork;
    }

    public void setEmpnetwork(String empnetwork) {
        this.empnetwork = empnetwork;
    }

    public String getEmpoutward() {
        return empoutward;
    }

    public void setEmpoutward(String empoutward) {
        this.empoutward = empoutward;
    }

    public Long getAutBy() {
        return autBy;
    }

    public void setAutBy(Long autBy) {
        this.autBy = autBy;
    }

    public Date getAutDate() {
        return autDate;
    }

    public void setAutDate(Date autDate) {
        this.autDate = autDate;
    }

    public String getCentraleno() {
        return centraleno;
    }

    public void setCentraleno(String centraleno) {
        this.centraleno = centraleno;
    }

    public String getScansignature() {
        return scansignature;
    }

    public void setScansignature(String scansignature) {
        this.scansignature = scansignature;
    }

    public String getEmpuid() {
        return empuid;
    }

    public void setEmpuid(String empuid) {
        this.empuid = empuid;
    }

    public String getEmpuiddocpath() {
        return empuiddocpath;
    }

    public void setEmpuiddocpath(String empuiddocpath) {
        this.empuiddocpath = empuiddocpath;
    }

    public String getEmpphotopath() {
        return empphotopath;
    }

    public void setEmpphotopath(String empphotopath) {
        this.empphotopath = empphotopath;
    }

    public String getEmpuiddocname() {
        return empuiddocname;
    }

    public void setEmpuiddocname(String empuiddocname) {
        this.empuiddocname = empuiddocname;
    }

    public String getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(String addFlag) {
        this.addFlag = addFlag;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(String empGender) {
        this.empGender = empGender;
    }

    public String getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(String isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getEmpCorAddress1() {
        return empCorAddress1;
    }

    public void setEmpCorAddress1(String empCorAddress1) {
        this.empCorAddress1 = empCorAddress1;
    }

    public String getEmpCorAddress2() {
        return empCorAddress2;
    }

    public void setEmpCorAddress2(String empCorAddress2) {
        this.empCorAddress2 = empCorAddress2;
    }

    public String getCorPincode() {
        return corPincode;
    }

    public void setCorPincode(String corPincode) {
        this.corPincode = corPincode;
    }

    public String getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getMobNoOtp() {
        return mobNoOtp;
    }

    public void setMobNoOtp(String mobNoOtp) {
        this.mobNoOtp = mobNoOtp;
    }

    public String getAutEmail() {
        return autEmail;
    }

    public void setAutEmail(String autEmail) {
        this.autEmail = autEmail;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getAgencyRegNo() {
        return agencyRegNo;
    }

    public void setAgencyRegNo(Integer agencyRegNo) {
        this.agencyRegNo = agencyRegNo;
    }

    public String getAgencyLocation() {
        return agencyLocation;
    }

    public void setAgencyLocation(String agencyLocation) {
        this.agencyLocation = agencyLocation;
    }

    public Integer getAgencyYearsOfExp() {
        return agencyYearsOfExp;
    }

    public void setAgencyYearsOfExp(Integer agencyYearsOfExp) {
        this.agencyYearsOfExp = agencyYearsOfExp;
    }

    public String getAgencyQualification() {
        return agencyQualification;
    }

    public void setAgencyQualification(String agencyQualification) {
        this.agencyQualification = agencyQualification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "EMPLOYEE_HIST", "H_EMPID" };
    }

	public String getMobileExtension() {
		return mobileExtension;
	}

	public void setMobileExtension(String mobileExtension) {
		this.mobileExtension = mobileExtension;
	}

    
}
