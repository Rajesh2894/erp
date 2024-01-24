package com.abm.mainet.vehiclemanagement.domain;

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
 * The persistent class for the tb_sw_pump_mast_hist database table.
 * 
 */
@Entity
@Table(name = "TB_VM_PUMP_MAST_HIST")
public class FuelPumpMasterHistory implements Serializable {

    private static final long serialVersionUID = -6362131326405767800L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PU_ID_H")
    private Long puIdH;

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

    @Column(name = "PU_ADDRESS")
    private String puAddress;

    @Column(name = "PU_ID")
    private Long puId;

    @Column(name = "PU_PUMPNAME")
    private String puPumpname;

    @Column(name = "PU_PUTYPE")
    private Long puPutype;

    @Column(name = "PU_ACTIVE")
    private String puActive;

    @Column(name = "VM_VENDORID")
    private Long vendorId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public FuelPumpMasterHistory() {
    }

    public Long getPuIdH() {
        return this.puIdH;
    }

    public void setPuIdH(Long puIdH) {
        this.puIdH = puIdH;
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

    public String getPuAddress() {
        return this.puAddress;
    }

    public void setPuAddress(String puAddress) {
        this.puAddress = puAddress;
    }

    public Long getPuId() {
        return this.puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }

    public String getPuPumpname() {
        return this.puPumpname;
    }

    public void setPuPumpname(String puPumpname) {
        this.puPumpname = puPumpname;
    }

    public Long getPuPutype() {
        return this.puPutype;
    }

    public void setPuPutype(Long puPutype) {
        this.puPutype = puPutype;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getPuActive() {
        return puActive;
    }

    public void setPuActive(String puActive) {
        this.puActive = puActive;
    }

    public String[] getPkValues() {

        return new String[] { "VM", "TB_VM_PUMP_MAST_HIST", "PU_ID_H" };
    }
}