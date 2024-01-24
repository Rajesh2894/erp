package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_LGL_HEARINGATTENDEE_DETAILS_HIST")
public class CaseHearingAttendeeDetailsHistory implements Serializable {

    private static final long serialVersionUID = -3357117866630998107L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HRA_ID_HIS")
    private Long hraIdHIS;

    @Column(name = "HRA_ID", nullable = false)
    private Long hraId;

    @Column(name = "CSE_ID", nullable = false)
    private Long cseId;

    @Column(name = "HRA_NAME", length = 50)
    private String hraName;

    @Column(name = "HRA_DESIGNATION", length = 50)
    private String hraDesignation;

    @Column(name = "HRA_PHONENO", length = 50)
    private String hraPhoneNo;

    @Column(name = "HRA_EMAILID", length = 50)
    private String hraEmailId;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;
    
    @Column(name = "HR_ID")
    private Long hrId;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public Long getHraIdHIS() {
        return hraIdHIS;
    }

    public void setHraIdHIS(Long hraIdHIS) {
        this.hraIdHIS = hraIdHIS;
    }

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

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public Long getHrId() {
		return hrId;
	}

	public void setHrId(Long hrId) {
		this.hrId = hrId;
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

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_HEARINGATTENDEE_DETAILS_HIST", "HRA_ID_HIS" };
    }
}
