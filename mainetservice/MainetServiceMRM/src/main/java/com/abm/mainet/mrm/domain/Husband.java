package com.abm.mainet.mrm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_MRM_HUSBAND")
public class Husband implements Serializable {

    private static final long serialVersionUID = -8824129050937234044L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HUSBAND_ID")
    private Long husbandId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAR_ID", referencedColumnName = "MAR_ID", nullable = false)
    private Marriage marId;

    @Column(name = "FIRST_NAME_E", length = 100, nullable = false)
    private String firstNameEng;

    @Column(name = "FIRST_NAME_R", length = 100, nullable = false)
    private String firstNameReg;

    @Column(name = "MIDDLE_NAME_E", length = 100)
    private String middleNameEng;

    @Column(name = "MIDDLE_NAME_R", length = 100)
    private String middleNameReg;

    @Column(name = "LAST_NAME_E", length = 100, nullable = false)
    private String lastNameEng;

    @Column(name = "LAST_NAME_R", length = 100, nullable = false)
    private String lastNameReg;

    @Column(name = "OTHER_NAME", length = 100)
    private String otherName;

    @Column(name = "UID_NO", length = 15)
    private String uidNo;

    @Column(name = "DOB", nullable = false)
    private Date dob;

    @Column(name = "YEAR", length = 3, nullable = false)
    private int year;

    @Column(name = "MONTH", length = 3, nullable = false)
    private int month;

    @Column(name = "RELIGION_BIRTH", nullable = false)
    private Long religionBirth;  // Prefix RLG

    @Column(name = "RELIGION_ADOPTION")
    private Long religionAdopt;  // Prefix RLG

    @Column(name = "OCCUPATION", nullable = false)
    private Long occupation; // Prefix OCU

    @Column(name = "STATUS_MAR_TIME", nullable = false)
    private Long statusMarTime; // Prefix STA

    @Column(name = "FULL_ADDR_E", length = 500, nullable = false)
    private String fullAddrEng;

    @Column(name = "FULL_ADDR_R", length = 500, nullable = false)
    private String fullAddrReg;

    @Column(name = "CAPTURE_PHOTO_NAME", nullable = true)
    private String capturePhotoName;

    @Column(name = "CAPTURE_PHOTO_PATH", nullable = true)
    private String capturePhotoPath;

    @Column(name = "CAPTURE_FINGERPRINT_NAME", nullable = true)
    private String captureFingerprintName;

    @Column(name = "CAPTURE_FINGERPRINT_PATH", nullable = true)
    private String captureFingerprintPath;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "CASTE1")
    private Long caste1;

    @Column(name = "CASTE2")
    private Long caste2;

    @Column(name = "CASTE3")
    private Long caste3;

    @Column(name = "CASTE4")
    private Long caste4;

    @Column(name = "CASTE5")
    private Long caste5;

    @Column(name = "NRI", length = 5)
    private String nri;

    public Long getHusbandId() {
        return husbandId;
    }

    public void setHusbandId(Long husbandId) {
        this.husbandId = husbandId;
    }

    public Marriage getMarId() {
        return marId;
    }

    public void setMarId(Marriage marId) {
        this.marId = marId;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getFirstNameReg() {
        return firstNameReg;
    }

    public void setFirstNameReg(String firstNameReg) {
        this.firstNameReg = firstNameReg;
    }

    public String getMiddleNameEng() {
        return middleNameEng;
    }

    public void setMiddleNameEng(String middleNameEng) {
        this.middleNameEng = middleNameEng;
    }

    public String getMiddleNameReg() {
        return middleNameReg;
    }

    public void setMiddleNameReg(String middleNameReg) {
        this.middleNameReg = middleNameReg;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getLastNameReg() {
        return lastNameReg;
    }

    public void setLastNameReg(String lastNameReg) {
        this.lastNameReg = lastNameReg;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getUidNo() {
        return uidNo;
    }

    public void setUidNo(String uidNo) {
        this.uidNo = uidNo;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Long getReligionBirth() {
        return religionBirth;
    }

    public void setReligionBirth(Long religionBirth) {
        this.religionBirth = religionBirth;
    }

    public Long getReligionAdopt() {
        return religionAdopt;
    }

    public void setReligionAdopt(Long religionAdopt) {
        this.religionAdopt = religionAdopt;
    }

    public Long getOccupation() {
        return occupation;
    }

    public void setOccupation(Long occupation) {
        this.occupation = occupation;
    }

    public Long getStatusMarTime() {
        return statusMarTime;
    }

    public void setStatusMarTime(Long statusMarTime) {
        this.statusMarTime = statusMarTime;
    }

    public String getFullAddrEng() {
        return fullAddrEng;
    }

    public void setFullAddrEng(String fullAddrEng) {
        this.fullAddrEng = fullAddrEng;
    }

    public String getFullAddrReg() {
        return fullAddrReg;
    }

    public void setFullAddrReg(String fullAddrReg) {
        this.fullAddrReg = fullAddrReg;
    }

    public String getCapturePhotoName() {
        return capturePhotoName;
    }

    public void setCapturePhotoName(String capturePhotoName) {
        this.capturePhotoName = capturePhotoName;
    }

    public String getCapturePhotoPath() {
        return capturePhotoPath;
    }

    public void setCapturePhotoPath(String capturePhotoPath) {
        this.capturePhotoPath = capturePhotoPath;
    }

    public String getCaptureFingerprintName() {
        return captureFingerprintName;
    }

    public void setCaptureFingerprintName(String captureFingerprintName) {
        this.captureFingerprintName = captureFingerprintName;
    }

    public String getCaptureFingerprintPath() {
        return captureFingerprintPath;
    }

    public void setCaptureFingerprintPath(String captureFingerprintPath) {
        this.captureFingerprintPath = captureFingerprintPath;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getCaste1() {
        return caste1;
    }

    public void setCaste1(Long caste1) {
        this.caste1 = caste1;
    }

    public Long getCaste2() {
        return caste2;
    }

    public void setCaste2(Long caste2) {
        this.caste2 = caste2;
    }

    public Long getCaste3() {
        return caste3;
    }

    public void setCaste3(Long caste3) {
        this.caste3 = caste3;
    }

    public Long getCaste4() {
        return caste4;
    }

    public void setCaste4(Long caste4) {
        this.caste4 = caste4;
    }

    public Long getCaste5() {
        return caste5;
    }

    public void setCaste5(Long caste5) {
        this.caste5 = caste5;
    }

    public String getNri() {
        return nri;
    }

    public void setNri(String nri) {
        this.nri = nri;
    }

    public static String[] getPkValues() {
        return new String[] { "MRM", "TB_MRM_HUSBAND", "HUSBAND_ID" };
    }
}
