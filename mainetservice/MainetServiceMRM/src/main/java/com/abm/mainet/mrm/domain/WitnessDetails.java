package com.abm.mainet.mrm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_MRM_WITNESS_DET")
public class WitnessDetails implements Serializable {

    private static final long serialVersionUID = 4651003241035931589L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WITNESS_ID")
    private Long witnessId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAR_ID", nullable = false)
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

    @Column(name = "UID_NO", length = 15)
    private String uidNo;

    @Column(name = "FULL_ADDR_E", length = 500, nullable = false)
    private String fullAddrEng;

    @Column(name = "FULL_ADDR_R", length = 500, nullable = false)
    private String fullAddrReg;

    @Column(name = "OCCUPATION", nullable = false)
    private Long occupation; // Prefix OCU

    @Column(name = "REL_WITH_COUPLE", length = 12, nullable = false)
    private Long relation;

    @Column(name = "OFFICE_ADDR", length = 500)
    private String offcAddr;

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

    @Column(name = "CAPTURE_PHOTO_NAME", nullable = true)
    private String capturePhotoName;

    @Column(name = "CAPTURE_PHOTO_PATH", nullable = true)
    private String capturePhotoPath;

    @Column(name = "CAPTURE_FINGERPRINT_NAME", nullable = true)
    private String captureFingerprintName;

    @Column(name = "CAPTURE_FINGERPRINT_PATH", nullable = true)
    private String captureFingerprintPath;

    @Column(name = "OTHER_REL")
    private String otherRel;

    public Long getWitnessId() {
        return witnessId;
    }

    public void setWitnessId(Long witnessId) {
        this.witnessId = witnessId;
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

    public String getUidNo() {
        return uidNo;
    }

    public void setUidNo(String uidNo) {
        this.uidNo = uidNo;
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

    public Long getOccupation() {
        return occupation;
    }

    public void setOccupation(Long occupation) {
        this.occupation = occupation;
    }

    public Long getRelation() {
        return relation;
    }

    public void setRelation(Long relation) {
        this.relation = relation;
    }

    public String getOffcAddr() {
        return offcAddr;
    }

    public void setOffcAddr(String offcAddr) {
        this.offcAddr = offcAddr;
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

    public String getOtherRel() {
        return otherRel;
    }

    public void setOtherRel(String otherRel) {
        this.otherRel = otherRel;
    }

    public static String[] getPkValues() {
        return new String[] { "MRM", "TB_MRM_WITNESS_DET", "WITNESS_ID" };
    }
}
