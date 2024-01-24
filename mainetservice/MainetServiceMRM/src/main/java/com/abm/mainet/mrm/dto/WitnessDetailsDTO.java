package com.abm.mainet.mrm.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class WitnessDetailsDTO implements Serializable {

    private static final long serialVersionUID = 2092839555985366304L;

    private int srNo;
    private Long witnessId;

    @JsonBackReference
    private MarriageDTO marId;

    private String firstNameEng;

    private String firstNameReg;

    private String middleNameEng;

    private String middleNameReg;

    private String lastNameEng;

    private String lastNameReg;

    private String uidNo;

    private String fullAddrEng;

    private String fullAddrReg;

    private Long occupation; // Prefix OCU

    private String occupationDesc;

    private Long relation;

    private String relationDesc;

    private String offcAddr;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String capturePhotoName;

    private String capturePhotoPath;

    private String captureFingerprintName;

    private String captureFingerprintPath;

    private String otherRel;

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public Long getWitnessId() {
        return witnessId;
    }

    public void setWitnessId(Long witnessId) {
        this.witnessId = witnessId;
    }

    public MarriageDTO getMarId() {
        return marId;
    }

    public void setMarId(MarriageDTO marId) {
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

    public String getOccupationDesc() {
        return occupationDesc;
    }

    public void setOccupationDesc(String occupationDesc) {
        this.occupationDesc = occupationDesc;
    }

    public Long getRelation() {
        return relation;
    }

    public void setRelation(Long relation) {
        this.relation = relation;
    }

    public String getRelationDesc() {
        return relationDesc;
    }

    public void setRelationDesc(String relationDesc) {
        this.relationDesc = relationDesc;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    @Override
    public String toString() {
        return "WitnessDetailsDTO [srNo=" + srNo + ", witnessId=" + witnessId + ", marId=" + marId + ", firstNameEng="
                + firstNameEng + ", firstNameReg=" + firstNameReg + ", middleNameEng=" + middleNameEng + ", middleNameReg="
                + middleNameReg + ", lastNameEng=" + lastNameEng + ", lastNameReg=" + lastNameReg + ", uidNo=" + uidNo
                + ", fullAddrEng=" + fullAddrEng + ", fullAddrReg=" + fullAddrReg + ", occupation=" + occupation
                + ", occupationDesc=" + occupationDesc + ", relation=" + relation + ", offcAddr=" + offcAddr + ", orgId=" + orgId
                + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
    }

}
