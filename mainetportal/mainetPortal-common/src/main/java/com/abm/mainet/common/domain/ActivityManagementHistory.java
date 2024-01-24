package com.abm.mainet.common.domain;

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
@Table(name = "TB_ACTIVITY_MAS_HIST")
public class ActivityManagementHistory {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ACT_ID_H")
    private Long actIdH;

    @Column(name = "ACT_ID")
    private Long actId;

    @Column(name = "ACT_EMPID")
    private Long actEmpid;

    @Temporal(TemporalType.DATE)
    @Column(name = "ACT_ENDDT")
    private Date actEnddt;

    @Column(name = "ACT_ESTTIME")
    private String actEsttime;

    @Column(name = "ACT_NAME")
    private String actName;

    @Column(name = "ACT_NOTE")
    private String actNote;

    @Column(name = "ACT_PID")
    private Long actPid;

    @Column(name = "ACT_PRIORITY")
    private Long actPriority;

    @Temporal(TemporalType.DATE)
    @Column(name = "ACT_STARTDT")
    private Date actStartdt;

    @Column(name = "ACT_STATUS")
    private Long actStatus;

    @Column(name = "ACT_TYPE")
    private Long actType;

    @Column(name = "ACT_WATCHER")
    private String actWatcher;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "H_STATUS")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getActEmpid() {
        return actEmpid;
    }

    public void setActEmpid(Long actEmpid) {
        this.actEmpid = actEmpid;
    }

    public Date getActEnddt() {
        return actEnddt;
    }

    public void setActEnddt(Date actEnddt) {
        this.actEnddt = actEnddt;
    }

    public String getActEsttime() {
        return actEsttime;
    }

    public void setActEsttime(String actEsttime) {
        this.actEsttime = actEsttime;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActNote() {
        return actNote;
    }

    public void setActNote(String actNote) {
        this.actNote = actNote;
    }

    public Long getActPid() {
        return actPid;
    }

    public void setActPid(Long actPid) {
        this.actPid = actPid;
    }

    public Long getActPriority() {
        return actPriority;
    }

    public void setActPriority(Long actPriority) {
        this.actPriority = actPriority;
    }

    public Date getActStartdt() {
        return actStartdt;
    }

    public void setActStartdt(Date actStartdt) {
        this.actStartdt = actStartdt;
    }

    public Long getActStatus() {
        return actStatus;
    }

    public void setActStatus(Long actStatus) {
        this.actStatus = actStatus;
    }

    public Long getActType() {
        return actType;
    }

    public void setActType(Long actType) {
        this.actType = actType;
    }

    public String getActWatcher() {
        return actWatcher;
    }

    public void setActWatcher(String actWatcher) {
        this.actWatcher = actWatcher;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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
        return new String[] { "AUT", "TB_ACTIVITY_MAS_HIST", "ACT_ID_H" };
    }

}
