package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the tb_lgl_casejudgement_detail database table.
 * 
 */
public class JudgementDetailDTO implements Serializable {

    private static final long serialVersionUID = 1541953087660195L;

    private Long cjdId;

    private String cjdActiontaken;

    private String cjdAttendee;

    private Date cjdDate;

    private String cjdDetails;

    private Long cjdType;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private Long cseId;

    private String implementerName;

    private String desigOfImplementer;

    private String impleStatus;

    private Long implementerPhoneNo;

    private String implementerEmail;

    private Date implementationStartDate;

    private Date implementationEndDate;

    private List<AttendeeDetailDto> attendeeDtoList;
    
    

	public JudgementDetailDTO() {
    }

    public Long getCjdId() {
        return this.cjdId;
    }

    public void setCjdId(Long cjdId) {
        this.cjdId = cjdId;
    }

    public String getCjdActiontaken() {
        return this.cjdActiontaken;
    }

    public void setCjdActiontaken(String cjdActiontaken) {
        this.cjdActiontaken = cjdActiontaken;
    }

    public String getCjdAttendee() {
        return this.cjdAttendee;
    }

    public void setCjdAttendee(String cjdAttendee) {
        this.cjdAttendee = cjdAttendee;
    }

    public Date getCjdDate() {
        return this.cjdDate;
    }

    public void setCjdDate(Date cjdDate) {
        this.cjdDate = cjdDate;
    }

    public String getCjdDetails() {
        return this.cjdDetails;
    }

    public void setCjdDetails(String cjdDetails) {
        this.cjdDetails = cjdDetails;
    }

    public Long getCjdType() {
        return this.cjdType;
    }

    public void setCjdType(Long cjdType) {
        this.cjdType = cjdType;
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

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String getImplementerName() {
        return implementerName;
    }

    public void setImplementerName(String implementerName) {
        this.implementerName = implementerName;
    }

    public Long getImplementerPhoneNo() {
        return implementerPhoneNo;
    }

    public void setImplementerPhoneNo(Long implementerPhoneNo) {
        this.implementerPhoneNo = implementerPhoneNo;
    }

    public String getImplementerEmail() {
        return implementerEmail;
    }

    public void setImplementerEmail(String implementerEmail) {
        this.implementerEmail = implementerEmail;
    }

    public Date getImplementationStartDate() {
        return implementationStartDate;
    }

    public void setImplementationStartDate(Date implementationStartDate) {
        this.implementationStartDate = implementationStartDate;
    }

    public Date getImplementationEndDate() {
        return implementationEndDate;
    }

    public void setImplementationEndDate(Date implementationEndDate) {
        this.implementationEndDate = implementationEndDate;
    }

    public List<AttendeeDetailDto> getAttendeeDtoList() {
        return attendeeDtoList;
    }

    public void setAttendeeDtoList(List<AttendeeDetailDto> attendeeDtoList) {
        this.attendeeDtoList = attendeeDtoList;
    }

    public String getImpleStatus() {
        return impleStatus;
    }

    public void setImpleStatus(String impleStatus) {
        this.impleStatus = impleStatus;
    }

    public String getDesigOfImplementer() {
        return desigOfImplementer;
    }

    public void setDesigOfImplementer(String desigOfImplementer) {
        this.desigOfImplementer = desigOfImplementer;
    }


}