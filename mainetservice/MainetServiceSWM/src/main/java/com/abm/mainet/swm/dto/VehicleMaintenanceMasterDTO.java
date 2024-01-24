package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

public class VehicleMaintenanceMasterDTO implements Serializable {

    private static final long serialVersionUID = 2013128551832682207L;

    private Long veMeId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private String veMeActive;

    private Long veMainday;

    private Long veMainUnit;

    private Long veDowntime;

    private Long veDowntimeUnit;

    private Long veVetype;

    public Long getVeMeId() {
        return veMeId;
    }

    public void setVeMeId(Long veMeId) {
        this.veMeId = veMeId;
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

    public String getVeMeActive() {
        return veMeActive;
    }

    public void setVeMeActive(String veMeActive) {
        this.veMeActive = veMeActive;
    }

    public Long getVeMainday() {
        return veMainday;
    }

    public void setVeMainday(Long veMainday) {
        this.veMainday = veMainday;
    }

    public Long getVeMainUnit() {
        return veMainUnit;
    }

    public void setVeMainUnit(Long veMainUnit) {
        this.veMainUnit = veMainUnit;
    }

    public Long getVeDowntime() {
        return veDowntime;
    }

    public void setVeDowntime(Long veDowntime) {
        this.veDowntime = veDowntime;
    }

    public Long getVeDowntimeUnit() {
        return veDowntimeUnit;
    }

    public void setVeDowntimeUnit(Long veDowntimeUnit) {
        this.veDowntimeUnit = veDowntimeUnit;
    }

    public Long getVeVetype() {
        return veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

}
