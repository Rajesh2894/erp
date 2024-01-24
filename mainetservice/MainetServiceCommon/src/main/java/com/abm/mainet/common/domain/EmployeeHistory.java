/*
 * Created on 19 Aug 2015 ( Time 17:09:55 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite)

package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
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
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "EMPID_H", nullable = false)
	private Long empHistId;

	@Column(name = "EMPID", nullable = false)
	private Long empId;

	// ----------------------------------------------------------------------
	// ENTITY DATA FIELDS
	// ----------------------------------------------------------------------
	@Column(name = "EMPNAME", length = 500, nullable = false)
	// comments : Employee Name
	private String empname;

	@Column(name = "EMPOSLOGINNAME", length = 50, nullable = true)
	// comments : Employee Login name
	private String emposloginname;

	@Column(name = "EMPLOGINNAME", length = 50, nullable = true)
	// comments : Employee Login Name
	private String emploginname;

	@Column(name = "EMPPASSWORD", length = 50, nullable = true)
	// comments : Employee password
	private String emppassword;

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
	// comments : Flag to identify whether the record is deleted or not. 1 for
	// deleted (Inactive) and 0 for not deleted (Active)
	// record.
	private String isDeleted;

	@Column(name = "SYNOYNMX", precision = 0, scale = 0, nullable = true)
	// comments : Synoynmx (Not in use)
	private Long synoynmx;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	// comments : User Id
	private Long userId;

	@Column(name = "CREATED_DATE", nullable = false)
	// comments : Creation date
	private Date lmodDate;

	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	// comments : Updated by
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	// comments : Updated date
	private Date updatedDate;

	@Column(name = "EMPEMAIL", length = 50, nullable = true)
	// comments : Employee Email Id
	private String empemail;

	@Column(name = "EMPEXPIREDT", nullable = true)
	// comments : Employee login expiry date
	private Date empexpiredt;

	/*
	 * @Column(name = "EMPPHOTO", nullable = true) //comments : Employee Photo
	 * private String empphoto;
	 */

	@Column(name = "LOCK_UNLOCK", length = 1, nullable = true)
	// comments : Flag to identify whether employee is lock or Unlock. 'L' for lock
	// and 'U' for unlock
	private String lockUnlock;

	@Column(name = "LOGGED_IN_ATTEMPT", length = 2)
	private Integer loggedInAttempt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOCK_DATE")
	private Date lockDate;

	@Column(name = "LOGGED_IN", length = 1, nullable = true)
	// comments : Flag to identify logged in status of employee. 'Y' for logged in
	// and 'N' for not.
	private String loggedIn;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	// comments : Client Machine’s Login Name | IP Address | Physical Address
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	// comments : Updated Client Machine’s Login Name | IP Address | Physical
	// Address
	private String lgIpMacUpd;

	@Column(name = "EMPNEW", precision = 1, scale = 0, nullable = true)
	// comments : Flag to identify whether login employee is new or old.
	private Long empnew;

	@Column(name = "EMPDOB", nullable = true)
	// comments : Employee Date of Birth
	private Date empdob;

	@Column(name = "EMPMOBNO", length = 30, nullable = true)
	// comments : Employee Mobile No
	private String empmobno;

	@Column(name = "EMPPHONENO", length = 40, nullable = true)
	// comments : Employee Phone No
	private String empphoneno;

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
	private String empUidDocName;

	@Column(name = "ADD_FLAG", length = 1, nullable = true)
	// comments : Flag to identify correspondance address same to Address Y/N
	private String addFlag;

	@Column(name = "EMP_ADDRESS", length = 100, nullable = true)
	// comments : EMPLOYEE ADDRESS
	private String empAddress;

	@Column(name = "EMP_ADDRESS1", length = 2000, nullable = true)
	// comments : Employee Address 1
	private String empAddress1;

	@Column(name = "EMPPINCODE", precision = 6, scale = 0, nullable = true)
	// comments : Employee PIN Code
	private Long emppincode;

	@Column(name = "AUTH_STATUS", length = 1, nullable = true)
	// comments : Employee AuthorizationFlag (Approve- A/Hold-H/Reject-R)
	private String authStatus;

	@Column(name = "AUT_MOB", length = 1, nullable = true)
	// comments : Validate Mobile number
	private String autMob;

	@Column(name = "CPD_TTL_ID", precision = 15, scale = 0, nullable = true)
	// comments : title
	private Long cpdTtlId;

	@Column(name = "EMPLNAME", length = 100, nullable = true)
	// comments : Last Name
	private String emplname;

	@Column(name = "EMPMNAME", length = 100, nullable = true)
	// comments : Middle Name
	private String empmname;
	@Column(name = "EMPL_TYPE", precision = 12, scale = 0, nullable = true)
	// comments : New Employee Category prefix NEC
	private Long emplType;

	@Column(name = "EMP_GENDER", length = 1, nullable = true)
	// comments : GENDER M/F/T
	private String empGender;

	@Column(name = "ISUPLOADED", length = 1, nullable = true)
	// comments : Flag to identify whether Agency has uploaded the documents
	// (default to N)
	private String isuploaded;

	@Column(name = "EMP_COR_ADD1", length = 2000, nullable = true)
	// comments : Correspondence Address line1
	private String empCorAdd1;

	@Column(name = "EMP_COR_ADD2", length = 2000, nullable = true)
	// comments : Correspondence Address line2
	private String empCorAdd2;

	@Column(name = "EMP_COR_PINCODE", precision = 6, scale = 0, nullable = true)
	// comments : Correspondence address pincode
	private Long empCorPincode;

	@Column(name = "GM_ID")
	private Long gmid;

	@Column(name = "AUT_EMAIL", precision = 1, scale = 0, nullable = true)
	private String autEmail;

	@Column(name = "PAN_NO", length = 10)
	private String panNo;

	@Column(name = "PREV_EMPNAME", length = 500, nullable = false) // comments :Employee Name
	private String prevEmpname;

	@Column(name = "PREV_EMPLNAME", length = 100, nullable = true) // comments :Last Name
	private String prevEmplname;

	@Column(name = "PREV_EMPMNAME", length = 100, nullable = true) // comments :Middle Name
	private String prevEmpmname;
	
	@Column(name = "PREV_TTL_ID", precision = 15, scale = 0, nullable = true)
	// comments : title
	private Long prevTtlId;

	@Column(name = "MAS_ID")
	private Long masId;

	// ----------------------------------------------------------------------
	// ENTITY LINKS ( RELATIONSHIP )
	// ----------------------------------------------------------------------
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ORGID", referencedColumnName = "ORGID")
	private Organisation organisation;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "DSGID", referencedColumnName = "DSGID")
	private Designation designation;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "LOCID", referencedColumnName = "LOC_ID")
	private LocationMasEntity tbLocationMas;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
	private Department tbDepartment;

	@Column(name = "REPORTING_EMPID", precision = 15, scale = 0, nullable = true)
	// comments : additional nvarchar2 aut_n2 to be used in future
	private Long reportingManager;

	@Transient
	private String statusIS;

	@Transient
	private List<CFCAttachment> cfcAttachments = new ArrayList<>(0);

	@Transient
	private String agencyName;

	@Column(name = "H_STATUS", length = 1)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	// ----------------------------------------------------------------------
	// CONSTRUCTOR(S)
	// ----------------------------------------------------------------------
	public EmployeeHistory() {
		super();
	}

	/**
	 * @return the empId
	 */
	public Long getEmpHistId() {
		return empHistId;
	}

	public void setEmpHistId(Long empHistId) {
		this.empHistId = empHistId;
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
	 * @return the emposloginname
	 */
	public String getEmposloginname() {
		return emposloginname;
	}

	/**
	 * @param emposloginname the emposloginname to set
	 */
	public void setEmposloginname(final String emposloginname) {
		this.emposloginname = emposloginname;
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

	public String getEmpUidDocName() {
		return empUidDocName;
	}

	public void setEmpUidDocName(final String empUidDocName) {
		this.empUidDocName = empUidDocName;
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
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(final String isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the synoynmx
	 */
	public Long getSynoynmx() {
		return synoynmx;
	}

	/**
	 * @param synoynmx the synoynmx to set
	 */
	public void setSynoynmx(final Long synoynmx) {
		this.synoynmx = synoynmx;
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
	 * @return the lmodDate
	 */
	public Date getLmodDate() {
		return lmodDate;
	}

	/**
	 * @param lmodDate the lmodDate to set
	 */
	public void setLmodDate(final Date lmodDate) {
		this.lmodDate = lmodDate;
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
	 * @return the empnew
	 */
	public Long getEmpnew() {
		return empnew;
	}

	/**
	 * @param empnew the empnew to set
	 */
	public void setEmpnew(final Long empnew) {
		this.empnew = empnew;
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
	 * @return the autBy
	 */
	public Long getAutBy() {
		return autBy;
	}

	/**
	 * @param autBy the autBy to set
	 */
	public void setAutBy(final Long autBy) {
		this.autBy = autBy;
	}

	/**
	 * @return the autDate
	 */
	public Date getAutDate() {
		return autDate;
	}

	/**
	 * @param autDate the autDate to set
	 */
	public void setAutDate(final Date autDate) {
		this.autDate = autDate;
	}

	/**
	 * @return the centraleno
	 */
	public String getCentraleno() {
		return centraleno;
	}

	/**
	 * @param centraleno the centraleno to set
	 */
	public void setCentraleno(final String centraleno) {
		this.centraleno = centraleno;
	}

	/**
	 * @return the scansignature
	 */
	public String getScansignature() {
		return scansignature;
	}

	/**
	 * @param scansignature the scansignature to set
	 */
	public void setScansignature(final String scansignature) {
		this.scansignature = scansignature;
	}

	/**
	 * @return the empuid
	 */
	public String getEmpuid() {
		return empuid;
	}

	/**
	 * @param empuid the empuid to set
	 */
	public void setEmpuid(final String empuid) {
		this.empuid = empuid;
	}

	/**
	 * @return the empuiddocpath
	 */
	public String getEmpuiddocpath() {
		return empuiddocpath;
	}

	/**
	 * @param empuiddocpath the empuiddocpath to set
	 */
	public void setEmpuiddocpath(final String empuiddocpath) {
		this.empuiddocpath = empuiddocpath;
	}

	/**
	 * @return the empphotopath
	 */
	public String getEmpphotopath() {
		return empphotopath;
	}

	/**
	 * @param empphotopath the empphotopath to set
	 */
	public void setEmpphotopath(final String empphotopath) {
		this.empphotopath = empphotopath;
	}

	/**
	 * @return the addFlag
	 */
	public String getAddFlag() {
		return addFlag;
	}

	/**
	 * @param addFlag the addFlag to set
	 */
	public void setAddFlag(final String addFlag) {
		this.addFlag = addFlag;
	}

	/**
	 * @return the empAddress
	 */
	public String getEmpAddress() {
		return empAddress;
	}

	/**
	 * @param empAddress the empAddress to set
	 */
	public void setEmpAddress(final String empAddress) {
		this.empAddress = empAddress;
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
	 * @return the emppincode
	 */
	public Long getEmppincode() {
		return emppincode;
	}

	/**
	 * @param emppincode the emppincode to set
	 */
	public void setEmppincode(final Long emppincode) {
		this.emppincode = emppincode;
	}

	/**
	 * @return the authStatus
	 */
	public String getAuthStatus() {
		return authStatus;
	}

	/**
	 * @param authStatus the authStatus to set
	 */
	public void setAuthStatus(final String authStatus) {
		this.authStatus = authStatus;
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
	 * @return the cpdTtlId
	 */
	public Long getCpdTtlId() {
		return cpdTtlId;
	}

	/**
	 * @param cpdTtlId the cpdTtlId to set
	 */
	public void setCpdTtlId(final Long cpdTtlId) {
		this.cpdTtlId = cpdTtlId;
	}

	/**
	 * @return the emplname
	 */
	public String getEmplname() {
		return emplname;
	}

	/**
	 * @param emplname the emplname to set
	 */
	public void setEmplname(final String emplname) {
		this.emplname = emplname;
	}

	/**
	 * @return the empmname
	 */
	public String getEmpmname() {
		return empmname;
	}

	/**
	 * @param empmname the empmname to set
	 */
	public void setEmpmname(final String empmname) {
		this.empmname = empmname;
	}

	/**
	 * @return the emplType
	 */
	public Long getEmplType() {
		return emplType;
	}

	/**
	 * @param emplType the emplType to set
	 */
	public void setEmplType(final Long emplType) {
		this.emplType = emplType;
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

	/**
	 * @return the isuploaded
	 */
	public String getIsuploaded() {
		return isuploaded;
	}

	/**
	 * @param isuploaded the isuploaded to set
	 */
	public void setIsuploaded(final String isuploaded) {
		this.isuploaded = isuploaded;
	}

	/**
	 * @return the empCorAdd1
	 */
	public String getEmpCorAdd1() {
		return empCorAdd1;
	}

	/**
	 * @param empCorAdd1 the empCorAdd1 to set
	 */
	public void setEmpCorAdd1(final String empCorAdd1) {
		this.empCorAdd1 = empCorAdd1;
	}

	/**
	 * @return the empCorAdd2
	 */
	public String getEmpCorAdd2() {
		return empCorAdd2;
	}

	/**
	 * @param empCorAdd2 the empCorAdd2 to set
	 */
	public void setEmpCorAdd2(final String empCorAdd2) {
		this.empCorAdd2 = empCorAdd2;
	}

	/**
	 * @return the empCorPincode
	 */
	public Long getEmpCorPincode() {
		return empCorPincode;
	}

	/**
	 * @param empCorPincode the empCorPincode to set
	 */
	public void setEmpCorPincode(final Long empCorPincode) {
		this.empCorPincode = empCorPincode;
	}

	/**
	 * @return the designation
	 */
	public Designation getDesignation() {
		return designation;
	}

	/**
	 * @return the organisation
	 */
	public Organisation getOrganisation() {
		return organisation;
	}

	/**
	 * @param organisation the organisation to set
	 */
	public void setOrganisation(final Organisation organisation) {
		this.organisation = organisation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(final Designation designation) {
		this.designation = designation;
	}

	/**
	 * @return the tbLocationMas
	 */
	public LocationMasEntity getTbLocationMas() {
		return tbLocationMas;
	}

	/**
	 * @param tbLocationMas the tbLocationMas to set
	 */
	public void setTbLocationMas(final LocationMasEntity tbLocationMas) {
		this.tbLocationMas = tbLocationMas;
	}

	/**
	 * @return the tbDepartment
	 */
	public Department getTbDepartment() {
		return tbDepartment;
	}

	/**
	 * @param tbDepartment the tbDepartment to set
	 */
	public void setTbDepartment(final Department tbDepartment) {
		this.tbDepartment = tbDepartment;
	}

	/**
	 * @return the gmid
	 */
	public Long getGmid() {
		return gmid;
	}

	/**
	 * @param gmid the gmid to set
	 */
	public void setGmid(final Long gmid) {
		this.gmid = gmid;
	}

	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo the panNo to set
	 */
	public void setPanNo(final String panNo) {
		this.panNo = panNo;
	}

	public String getFullName() {

		final String fullName = replaceNull(getEmpname()) + MainetConstants.WHITE_SPACE + replaceNull(getEmpmname())
				+ MainetConstants.WHITE_SPACE + replaceNull(getEmplname());

		return fullName.trim();

	}

	private String replaceNull(String name) {
		if (name == null) {

			name = MainetConstants.BLANK;
		}
		return name;
	}

	/**
	 * @return the statusIS
	 */
	public String getStatusIS() {
		return statusIS;
	}

	/**
	 * @param statusIS the statusIS to set
	 */
	public void setStatusIS(final String statusIS) {
		this.statusIS = statusIS;
	}

	/**
	 * @return the cfcAttachments
	 */
	public List<CFCAttachment> getCfcAttachments() {
		return cfcAttachments;
	}

	/**
	 * @param cfcAttachments the cfcAttachments to set
	 */
	public void setCfcAttachments(final List<CFCAttachment> cfcAttachments) {
		this.cfcAttachments = cfcAttachments;
	}

	/**
	 * @return the agencyName
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * @param agencyName the agencyName to set
	 */
	public void setAgencyName(final String agencyName) {
		this.agencyName = agencyName;
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

	public Long getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(Long reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String[] getPkValues() {
		return new String[] { "AUT", "EMPLOYEE_HIST", "EMPID_H" };
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

	public String getPrevEmpname() {
		return prevEmpname;
	}

	public String getPrevEmplname() {
		return prevEmplname;
	}

	public String getPrevEmpmname() {
		return prevEmpmname;
	}

	public void setPrevEmpname(String prevEmpname) {
		this.prevEmpname = prevEmpname;
	}

	public void setPrevEmplname(String prevEmplname) {
		this.prevEmplname = prevEmplname;
	}

	public void setPrevEmpmname(String prevEmpmname) {
		this.prevEmpmname = prevEmpmname;
	}

	public Long getPrevTtlId() {
		return prevTtlId;
	}

	public void setPrevTtlId(Long prevTtlId) {
		this.prevTtlId = prevTtlId;
	}

	/**
	 * @return the masId
	 */
	public Long getMasId() {
		return masId;
	}

	/**
	 * @param masId the masId to set
	 */
	public void setMasId(Long masId) {
		this.masId = masId;
	}

	
}
