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
 * The persistent class for the TB_VM_VEHICLEFUEL_INREC_DET_HIST database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 16-Jun-2018
 */
@Entity
@Table(name = "TB_VM_VEHICLEFUEL_INREC_DET_HIST")
public class VmVehicleFuelReconciationDetHistory implements Serializable {

    private static final long serialVersionUID = 4339945332414052813L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INRECD_ID_H", unique = true, nullable = false)
    private Long inrecdIdH;

    @Column(name = "INRECD_ACTIVE", length = 1)
    private String inrecdActive;

    @Column(name = "INREC_ID")
    private Long inrecId;

    @Column(name = "INRECD_ID")
    private Long inrecdId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VEF_ID")
    private Long vefId;

    public VmVehicleFuelReconciationDetHistory() {
    }

    public Long getInrecdIdH() {
        return this.inrecdIdH;
    }

    public void setInrecdIdH(Long inrecdIdH) {
        this.inrecdIdH = inrecdIdH;
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

    public Long getInrecId() {
        return this.inrecId;
    }

    public void setInrecId(Long inrecId) {
        this.inrecId = inrecId;
    }

    public Long getInrecdId() {
        return this.inrecdId;
    }

    public void setInrecdId(Long inrecdId) {
        this.inrecdId = inrecdId;
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

    public Long getVefId() {
        return this.vefId;
    }

    public void setVefId(Long vefId) {
        this.vefId = vefId;
    }

    public String getInrecdActive() {
        return inrecdActive;
    }

    public void setInrecdActive(String inrecdActive) {
        this.inrecdActive = inrecdActive;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_VM_VEHICLEFUEL_INREC_DET_HIST", "INRECD_ID_H" };
    }

}