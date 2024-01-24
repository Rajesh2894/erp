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

/**
 * The persistent class for the tb_sw_pump_fuldet_hist database table.
 * 
 */
@Entity
@Table(name = "TB_SW_PUMP_FULDET_HIST")
public class PumpFuelDetailsHistory implements Serializable {

    private static final long serialVersionUID = 2636717925455990255L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PFU_ID_H")
    private Long pfuIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "PFU_ID")
    private Long pfuId;

    @Column(name = "PU_FUID")
    private Long puFuid;

    @Column(name = "PU_FUUNIT")
    private Long puFuunit;

    @Column(name = "PU_ID")
    private Long puId;

    @Column(name = "PU_ACTIVE", nullable = false, length = 1)
    private String puActive;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public PumpFuelDetailsHistory() {
    }

    public Long getPfuIdH() {
        return this.pfuIdH;
    }

    public void setPfuIdH(Long pfuIdH) {
        this.pfuIdH = pfuIdH;
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

    public Long getPfuId() {
        return this.pfuId;
    }

    public void setPfuId(Long pfuId) {
        this.pfuId = pfuId;
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

    public Long getPuId() {
        return this.puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
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

    public String getPuActive() {
        return puActive;
    }

    public void setPuActive(String puActive) {
        this.puActive = puActive;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_PUMP_FULDET_HIST", "PFU_ID_H" };
    }
}