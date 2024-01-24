package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

public class HelpDeskDTO implements Serializable {

    private static final long serialVersionUID = -5079165189485956780L;

    private Long helpId;

    private Long helpEmpid;

    private Date helpEnddt;

    private String helpEsttime;

    private String helpName;

    private String helpNote;

    private Long helpPid;

    private Long helpPriority;

    private Date helpStartdt;

    private Long helpStatus;

    private Long helpType;

    private String helpWatcher;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private String editHelpNote;

    private String mode;

    public Long getHelpId() {
        return helpId;
    }

    public void setHelpId(Long helpId) {
        this.helpId = helpId;
    }

    public Long getHelpEmpid() {
        return helpEmpid;
    }

    public void setHelpEmpid(Long helpEmpid) {
        this.helpEmpid = helpEmpid;
    }

    public Date getHelpEnddt() {
        return helpEnddt;
    }

    public void setHelpEnddt(Date helpEnddt) {
        this.helpEnddt = helpEnddt;
    }

    public String getHelpEsttime() {
        return helpEsttime;
    }

    public void setHelpEsttime(String helpEsttime) {
        this.helpEsttime = helpEsttime;
    }

    public String getHelpName() {
        return helpName;
    }

    public void setHelpName(String helpName) {
        this.helpName = helpName;
    }

    public String getHelpNote() {
        return helpNote;
    }

    public void setHelpNote(String helpNote) {
        this.helpNote = helpNote;
    }

    public Long getHelpPid() {
        return helpPid;
    }

    public void setHelpPid(Long helpPid) {
        this.helpPid = helpPid;
    }

    public Long getHelpPriority() {
        return helpPriority;
    }

    public void setHelpPriority(Long helpPriority) {
        this.helpPriority = helpPriority;
    }

    public Date getHelpStartdt() {
        return helpStartdt;
    }

    public void setHelpStartdt(Date helpStartdt) {
        this.helpStartdt = helpStartdt;
    }

    public Long getHelpStatus() {
        return helpStatus;
    }

    public void setHelpStatus(Long helpStatus) {
        this.helpStatus = helpStatus;
    }

    public Long getHelpType() {
        return helpType;
    }

    public void setHelpType(Long helpType) {
        this.helpType = helpType;
    }

    public String getHelpWatcher() {
        return helpWatcher;
    }

    public void setHelpWatcher(String helpWatcher) {
        this.helpWatcher = helpWatcher;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getEditHelpNote() {
        return editHelpNote;
    }

    public void setEditHelpNote(String editHelpNote) {
        this.editHelpNote = editHelpNote;
    }

}
