package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long empId;
    private String fname;
    private String mname;
    private String lname;
    private String empLoginName;
    private String empPassword;
    private String designation;
    private String locNameReg;
    private String emppayrollnumber;
    private String empisecuritykey;
    private String emppiservername;
    private String ONlsOrgname;
    private String empemail;
    private String empAddress;
    private String empAddress1;
    private String lockUnlock;
    private String loggedIn;
    private String empmobno;
    private String empphoneno;
    private String lgIpMac;
    private String lgIpMacUpd;
    private int langId;
    private Long emplType;
    private Long empnew;
    private Date empdob;
    private String empuwmsowner;
    private String empregistry;
    private String emprecord;
    private String empnetwork;
    private String empoutward;
    private String empuid;
    private String empGender;
    private String isUploaded;
    private String panCardNo;
    private String autEmail;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(final Long empId) {
        this.empId = empId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(final String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(final String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(final String lname) {
        this.lname = lname;
    }

    public String getEmpLoginName() {
        return empLoginName;
    }

    public void setEmpLoginName(final String empLoginName) {
        this.empLoginName = empLoginName;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(final String empPassword) {
        this.empPassword = empPassword;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(final String designation) {
        this.designation = designation;
    }

    public String getLocNameReg() {
        return locNameReg;
    }

    public void setLocNameReg(final String locNameReg) {
        this.locNameReg = locNameReg;
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

    public String getONlsOrgname() {
        return ONlsOrgname;
    }

    public void setONlsOrgname(final String oNlsOrgname) {
        ONlsOrgname = oNlsOrgname;
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

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Long getEmplType() {
        return emplType;
    }

    public void setEmplType(final Long emplType) {
        this.emplType = emplType;
    }

    public Long getEmpnew() {
        return empnew;
    }

    public void setEmpnew(final Long empnew) {
        this.empnew = empnew;
    }

    public Date getEmpdob() {
        return empdob;
    }

    public void setEmpdob(final Date empdob) {
        this.empdob = empdob;
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

    public String getEmpnetwork() {
        return empnetwork;
    }

    public void setEmpnetwork(final String empnetwork) {
        this.empnetwork = empnetwork;
    }

    public String getEmpoutward() {
        return empoutward;
    }

    public void setEmpoutward(final String empoutward) {
        this.empoutward = empoutward;
    }

    public String getEmpuid() {
        return empuid;
    }

    public void setEmpuid(final String empuid) {
        this.empuid = empuid;
    }

    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(final String empGender) {
        this.empGender = empGender;
    }

    public String getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(final String isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(final String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public String getAutEmail() {
        return autEmail;
    }

    public void setAutEmail(final String autEmail) {
        this.autEmail = autEmail;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ActorDTO [id=" + id + ", empId=" + empId + ", fname=" + fname + ", mname=" + mname + ", lname=" + lname
                + ", empLoginName=" + empLoginName + ", empPassword=" + empPassword + ", designation=" + designation
                + ", locNameReg=" + locNameReg + ", emppayrollnumber=" + emppayrollnumber + ", empisecuritykey="
                + empisecuritykey + ", emppiservername=" + emppiservername + ", ONlsOrgname=" + ONlsOrgname
                + ", empemail=" + empemail + ", empAddress=" + empAddress + ", empAddress1=" + empAddress1
                + ", lockUnlock=" + lockUnlock + ", loggedIn=" + loggedIn + ", empmobno=" + empmobno + ", empphoneno="
                + empphoneno + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", langId=" + langId
                + ", emplType=" + emplType + ", empnew=" + empnew + ", empdob=" + empdob + ", empuwmsowner="
                + empuwmsowner + ", empregistry=" + empregistry + ", emprecord=" + emprecord + ", empnetwork="
                + empnetwork + ", empoutward=" + empoutward + ", empuid=" + empuid + ", empGender=" + empGender
                + ", isUploaded=" + isUploaded + ", panCardNo=" + panCardNo + ", autEmail=" + autEmail + ", type="
                + type + "]";
    }

}
