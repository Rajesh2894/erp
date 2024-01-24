package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the TB_VM_VEHICLEFUEL_INREC_DET database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 16-Jun-2018
 */
@Entity
@Table(name = "TB_VM_VEHICLEFUEL_INREC_DET")
public class VehicleFuelReconciationDetails implements Serializable {

    private static final long serialVersionUID = -6628543559387957194L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INRECD_ID", unique = true, nullable = false)
    private Long inrecdId;

    @Column(name = "INRECD_ACTIVE", length = 1)
    private String inrecdActive;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    // bi-directional many-to-one association to TbSwVehiclefuelInrec
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "INREC_ID", nullable = false)
    private VmVehicleFuelReconciation tbSwVehiclefuelInrec;

    @Column(name = "VEF_ID", nullable = false)
    private Long vefId;

    public VehicleFuelReconciationDetails() {
    }

    public Long getInrecdId() {
        return this.inrecdId;
    }

    public void setInrecdId(Long inrecdId) {
        this.inrecdId = inrecdId;
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

    public String getInrecdActive() {
        return inrecdActive;
    }

    public void setInrecdActive(String inrecdActive) {
        this.inrecdActive = inrecdActive;
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

    public VmVehicleFuelReconciation getTbSwVehiclefuelInrec() {
        return this.tbSwVehiclefuelInrec;
    }

    public void setTbSwVehiclefuelInrec(VmVehicleFuelReconciation tbSwVehiclefuelInrec) {
        this.tbSwVehiclefuelInrec = tbSwVehiclefuelInrec;
    }

    public Long getVefId() {
        return vefId;
    }

    public void setVefId(Long vefId) {
        this.vefId = vefId;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_VM_VEHICLEFUEL_INREC_DET", "INRECD_ID" };
    }

}