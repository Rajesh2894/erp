package com.abm.mainet.smsemail.domain;

import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SMSAndEmailInterfaceDTO {

    private long seId;
    private Long dpDeptid;
    private Long serviceId;
    private String alertType;
    private String isDeleted;
    private Long orgId;
    private Long userId;
    private int langId;
    private Date lmodDate;
    private Long updatedBy;
    private Date updatedDate;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;
    private String tranOrNonTran;
    private String selectedMode;
    private Long eventId;
    private Long templateId;

    private String deptDesc;
    private String serviceDesc;
    private String eventDesc;
    private String alertTypeDesc;
    private String templateTypeDesc;
    private String transDesc;

    public long getSeId() {
        return seId;
    }

    public void setSeId(final long seId) {
        this.seId = seId;
    }

    public String getTranOrNonTran() {
        return tranOrNonTran;
    }

    public void setTranOrNonTran(final String tranOrNonTran) {
        this.tranOrNonTran = tranOrNonTran;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(final String alertType) {
        this.alertType = alertType;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(final Long eventId) {
        this.eventId = eventId;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(final String selectedMode) {
        this.selectedMode = selectedMode;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final Long templateId) {
        this.templateId = templateId;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(final String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(final String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(final String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getAlertTypeDesc() {
        return alertTypeDesc;
    }

    public void setAlertTypeDesc(final String alertTypeDesc) {
        this.alertTypeDesc = alertTypeDesc;
    }

    public String getTemplateTypeDesc() {
        return templateTypeDesc;
    }

    public void setTemplateTypeDesc(final String templateTypeDesc) {
        this.templateTypeDesc = templateTypeDesc;
    }

    public String getTransDesc() {
        return transDesc;
    }

    public void setTransDesc(final String transDesc) {
        this.transDesc = transDesc;
    }

}