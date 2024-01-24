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

/**
 * The persistent class for the tb_lgl_casejudgement_detail_hist database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_CASEJUDGEMENT_DETAIL_HIST")
public class JudgementDetailHistory implements Serializable {

    private static final long serialVersionUID = 1853521588506180269L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CJD_ID_H")
    private Long cjdIdH;

    @Column(name = "CJD_ACTIONTAKEN")
    private String cjdActiontaken;

    @Column(name = "CJD_ATTENDEE")
    private String cjdAttendee;

    @Temporal(TemporalType.DATE)
    @Column(name = "CJD_DATE")
    private Date cjdDate;

    @Column(name = "CJD_DETAILS")
    private String cjdDetails;

    @Column(name = "CJD_ID")
    private Long cjdId;

    @Column(name = "CJD_TYPE")
    private Long cjdType;

    @Column(name = "CSE_ID")
    private Long cseId;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public JudgementDetailHistory() {
    }

    public Long getCjdIdH() {
        return this.cjdIdH;
    }

    public void setCjdIdH(Long cjdIdH) {
        this.cjdIdH = cjdIdH;
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

    public Long getCjdId() {
        return this.cjdId;
    }

    public void setCjdId(Long cjdId) {
        this.cjdId = cjdId;
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

    public Long getCseId() {
        return this.cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String getHStatus() {
        return this.hStatus;
    }

    public void setHStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_CASEJUDGEMENT_DETAIL_HIST", "CJD_ID_H" };
    }
}