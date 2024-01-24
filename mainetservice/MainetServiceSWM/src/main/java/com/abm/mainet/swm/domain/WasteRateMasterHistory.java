package com.abm.mainet.swm.domain;

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
@Table(name = "TB_SW_WASTRATE_MAST_HIST")
public class WasteRateMasterHistory implements Serializable {
    private static final long serialVersionUID = -1145457413810150879L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WRAT_ID_H", unique = true, nullable = false)
    private Long wRateIdH;

    @Column(name = "WRAT_ID", nullable = false)
    private Long wRateId;

    @Column(name = "COD_WAST1", nullable = false)
    private Long codWast1;

    @Column(name = "COD_WAST", nullable = false)
    private Long codWast;

    @Column(name = "WRAT_RATE")
    private Long wasteRate;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Temporal(TemporalType.DATE)
    @Column(name = "WRAT_FROM_DATE", nullable = false)
    private Date wFromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "WRAT_TO_DATE", nullable = false)
    private Date wToDate;

    @Column(name = "H_STATUS", length = 1, nullable = false)
    private String hStatus;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public WasteRateMasterHistory() {

    }

    public Long getwRateIdH() {
        return wRateIdH;
    }

    public Long getwRateId() {
        return wRateId;
    }

    public void setwRateId(Long wRateId) {
        this.wRateId = wRateId;
    }

    public void setwRateIdH(Long wRateIdH) {
        this.wRateIdH = wRateIdH;
    }

    public Long getCodWast1() {
        return codWast1;
    }

    public void setCodWast1(Long codWast1) {
        this.codWast1 = codWast1;
    }

    public Long getCodWast() {
        return codWast;
    }

    public void setCodWast(Long codWast) {
        this.codWast = codWast;
    }

    public Long getWasteRate() {
        return wasteRate;
    }

    public void setWasteRate(Long wasteRate) {
        this.wasteRate = wasteRate;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Date getwFromDate() {
        return wFromDate;
    }

    public void setwFromDate(Date wFromDate) {
        this.wFromDate = wFromDate;
    }

    public Date getwToDate() {
        return wToDate;
    }

    public void setwToDate(Date wToDate) {
        this.wToDate = wToDate;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_WASTRATE_MAST_HIST", "WRAT_ID_H" };
    }

}
