package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class PumpFuelDetailsDTO implements Serializable {

    private static final long serialVersionUID = -3434437176581699527L;

    private Long pfuId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long puFuid;

    private String puFuName;

    private String puFuUnitName;

    private Long puFuunit;

    private String puActive;

    private Long updatedBy;

    private Date updatedDate;

    private PumpMasterDTO tbSwPumpMast;

    public PumpFuelDetailsDTO() {
    }

    public String getPuFuName() {
        return puFuName;
    }

    public void setPuFuName(String puFuName) {
        this.puFuName = puFuName;
    }

    public Long getPfuId() {
        return this.pfuId;
    }

    public void setPfuId(Long pfuId) {
        this.pfuId = pfuId;
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

    public Long getPuFuid() {
        return this.puFuid;
    }

    public void setPuFuid(Long puFuid) {
        this.puFuid = puFuid;
    }

    public Long getPuFuunit() {
        return this.puFuunit;
    }

    public void setPuFuunit(Long puFuunit) {
        this.puFuunit = puFuunit;
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

    public PumpMasterDTO getTbSwPumpMast() {
        return this.tbSwPumpMast;
    }

    public void setTbSwPumpMast(PumpMasterDTO tbSwPumpMast) {
        this.tbSwPumpMast = tbSwPumpMast;
    }

    public String getPuActive() {
        return puActive;
    }

    public void setPuActive(String puActive) {
        this.puActive = puActive;
    }

    public String getPuFuUnitName() {
        return puFuUnitName;
    }

    public void setPuFuUnitName(String puFuUnitName) {
        this.puFuUnitName = puFuUnitName;
    }

}