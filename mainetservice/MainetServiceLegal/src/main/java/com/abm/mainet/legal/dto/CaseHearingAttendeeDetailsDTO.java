package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class CaseHearingAttendeeDetailsDTO implements Serializable {

    private static final long serialVersionUID = 6685950501936381938L;

    private Long hraId;

    private Long cseId;

    private String hraName;

    private String hraDesignation;

    private String hraPhoneNo;

    private String hraEmailId;
    
    private Long hrId;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long updatedBy;

    private Date updatedDate;

    public Long getHraId() {
        return hraId;
    }

    public void setHraId(Long hraId) {
        this.hraId = hraId;
    }

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String getHraName() {
        return hraName;
    }

    public void setHraName(String hraName) {
        this.hraName = hraName;
    }

    public String getHraDesignation() {
        return hraDesignation;
    }

    public void setHraDesignation(String hraDesignation) {
        this.hraDesignation = hraDesignation;
    }

    public String getHraPhoneNo() {
        return hraPhoneNo;
    }

    public void setHraPhoneNo(String hraPhoneNo) {
        this.hraPhoneNo = hraPhoneNo;
    }

    public String getHraEmailId() {
        return hraEmailId;
    }

    public void setHraEmailId(String hraEmailId) {
        this.hraEmailId = hraEmailId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

	public Long getHrId() {
		return hrId;
	}

	public void setHrId(Long hrId) {
		this.hrId = hrId;
	}

}
