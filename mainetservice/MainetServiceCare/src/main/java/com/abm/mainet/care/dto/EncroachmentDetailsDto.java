package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class EncroachmentDetailsDto implements Serializable {

    private static final long serialVersionUID = 4671389252339127884L;

    private Long encroachmentId;
    @JsonIgnore
    private LandInspectionDto landInspectionDto;

    private String name;

    private String address;

    private String contactNo;

    private String road;

    private String landmark;

    // Direction Side
    private String direction;

    private String encrType;// (S)SINGLE or (M)MULTIPLE

    private String flagStatus;// A or I

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    public Long getEncroachmentId() {
        return encroachmentId;
    }

    public void setEncroachmentId(Long encroachmentId) {
        this.encroachmentId = encroachmentId;
    }

    public LandInspectionDto getLandInspectionDto() {
        return landInspectionDto;
    }

    public void setLandInspectionDto(LandInspectionDto landInspectionDto) {
        this.landInspectionDto = landInspectionDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEncrType() {
        return encrType;
    }

    public void setEncrType(String encrType) {
        this.encrType = encrType;
    }

    public String getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
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

}
