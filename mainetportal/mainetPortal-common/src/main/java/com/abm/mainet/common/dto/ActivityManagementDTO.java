package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

public class ActivityManagementDTO implements Serializable {

    private static final long serialVersionUID = -2045244628757268450L;

    private Long actId;

    private Long actEmpid;

    private Date actEnddt;

    private String actEsttime;

    private String actName;

    private String actNote;

    private Long actPid;

    private Long actPriority;

    private Date actStartdt;

    private Long actStatus;

    private Long actType;

    private String actWatcher;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private String mode;

    private String editActNote;

    public String getEditActNote() {
        return editActNote;
    }

    public void setEditActNote(String editActNote) {
        this.editActNote = editActNote;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

}
