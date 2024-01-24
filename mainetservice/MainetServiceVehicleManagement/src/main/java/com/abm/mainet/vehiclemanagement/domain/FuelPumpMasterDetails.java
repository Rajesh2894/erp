package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_pump_fuldet database table.
 * 
 */
@Entity
@Table(name = "TB_VM_PUMP_FULDET")
public class FuelPumpMasterDetails implements Serializable {

    private static final long serialVersionUID = -5892965119537297113L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VFU_ID")
    private Long pfuId;

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

    @Column(name = "pu_fuid")
    private Long puFuid;

    @Column(name = "pu_fuunit")
    private Long puFuunit;

    @Column(name = "PU_ACTIVE", nullable = false, length = 1)
    private String puActive;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    // bi-directional many-to-one association to TbSwPumpMast
    @ManyToOne
    @JoinColumn(name = "fpm_id")
    private FuelPumpMaster tbSwPumpMast;

    public FuelPumpMasterDetails() {
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

    public FuelPumpMaster getTbSwPumpMast() {
        return this.tbSwPumpMast;
    }

    public void setTbSwPumpMast(FuelPumpMaster tbSwPumpMast) {
        this.tbSwPumpMast = tbSwPumpMast;
    }

    public String getPuActive() {
        return puActive;
    }

    public void setPuActive(String puActive) {
        this.puActive = puActive;
    }

    public String[] getPkValues() {

        return new String[] { "VM", "TB_VM_PUMP_FULDET", "VFU_ID" };
    }
}