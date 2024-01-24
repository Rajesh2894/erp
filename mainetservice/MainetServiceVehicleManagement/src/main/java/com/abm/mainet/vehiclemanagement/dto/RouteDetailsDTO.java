package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class RouteDetailsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long rodId;

    private Long codWard1;

    private Long codWard2;

    private Long codWard3;

    private Long codWard4;

    private Long codWard5;

    private String codWard3Str;

    private String codWard4Str;

    private String codWard5Str;

    private String referenceNo;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private String roCollLatitude;

    private String roCollLongitude;

    private String roCollPointadd;

    private String roCollPointname;

    private Long roCollType;

    private Long roSeqNo;

    private Long updatedBy;

    private Date updatedDate;

    private Long roAssumQuantity;

    private String roCollActive;

    private String fileName;

    private String ward;

    private String zone;

    private String assumeQty;

    private String collectionType;

    private String seqNo;

    private Long empId;

    private Long beatId;


    public Long getRodId() {
        return this.rodId;
    }

    public void setRodId(Long rodId) {
        this.rodId = rodId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getRoCollLatitude() {
        return this.roCollLatitude;
    }

    public void setRoCollLatitude(String roCollLatitude) {
        this.roCollLatitude = roCollLatitude;
    }

    public String getRoCollLongitude() {
        return this.roCollLongitude;
    }

    public void setRoCollLongitude(String roCollLongitude) {
        this.roCollLongitude = roCollLongitude;
    }

    public String getRoCollPointadd() {
        return this.roCollPointadd;
    }

    public void setRoCollPointadd(String roCollPointadd) {
        this.roCollPointadd = roCollPointadd;
    }

    public String getRoCollPointname() {
        return this.roCollPointname;
    }

    public void setRoCollPointname(String roCollPointname) {
        this.roCollPointname = roCollPointname;
    }

    public Long getRoCollType() {
        return this.roCollType;
    }

    public void setRoCollType(Long roCollType) {
        this.roCollType = roCollType;
    }

    public Long getRoSeqNo() {
        return this.roSeqNo;
    }

    public void setRoSeqNo(Long roSeqNo) {
        this.roSeqNo = roSeqNo;
    }

    public String getCodWard3Str() {
        return codWard3Str;
    }

    public void setCodWard3Str(String codWard3Str) {
        this.codWard3Str = codWard3Str;
    }

    public String getCodWard4Str() {
        return codWard4Str;
    }

    public void setCodWard4Str(String codWard4Str) {
        this.codWard4Str = codWard4Str;
    }

    public String getCodWard5Str() {
        return codWard5Str;
    }

    public void setCodWard5Str(String codWard5Str) {
        this.codWard5Str = codWard5Str;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getBeatId() {
        return beatId;
    }

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
    }

    public Long getCodWard1() {
        return this.codWard1;
    }

    public void setCodWard1(Long codWard1) {
        this.codWard1 = codWard1;
    }

    public Long getCodWard2() {
        return this.codWard2;
    }

    public void setCodWard2(Long codWard2) {
        this.codWard2 = codWard2;
    }

    public Long getCodWard3() {
        return this.codWard3;
    }

    public void setCodWard3(Long codWard3) {
        this.codWard3 = codWard3;
    }

    public Long getCodWard4() {
        return this.codWard4;
    }

    public void setCodWard4(Long codWard4) {
        this.codWard4 = codWard4;
    }

    public Long getCodWard5() {
        return this.codWard5;
    }

    public void setCodWard5(Long codWard5) {
        this.codWard5 = codWard5;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Long getRoAssumQuantity() {
        return roAssumQuantity;
    }

    public void setRoAssumQuantity(Long roAssumQuantity) {
        this.roAssumQuantity = roAssumQuantity;
    }

    public String getRoCollActive() {
        return roCollActive;
    }

    public void setRoCollActive(String roCollActive) {
        this.roCollActive = roCollActive;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getAssumeQty() {
        return assumeQty;
    }

    public void setAssumeQty(String assumeQty) {
        this.assumeQty = assumeQty;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

}