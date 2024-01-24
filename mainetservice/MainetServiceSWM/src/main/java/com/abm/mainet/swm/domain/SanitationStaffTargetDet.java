package com.abm.mainet.swm.domain;

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
 * The persistent class for the tb_sw_sanistaff_tgdet database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 15-Jun-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLE_TGDET")
public class SanitationStaffTargetDet implements Serializable {

    private static final long serialVersionUID = -7339481200643045383L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SAND_ID", unique = true, nullable = false)
    private Long sandId;

    @Column(name = "COD_WAST1")
    private Long codWast1;

    @Column(name = "COD_WAST2")
    private Long codWast2;

    @Column(name = "COD_WAST3")
    private Long codWast3;

    @Column(name = "COD_WAST4")
    private Long codWast4;

    @Column(name = "COD_WAST5")
    private Long codWast5;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MRF_ID")
    private Long deId;

    @Column(name = "VEHICLE_ID", nullable = false)
    private Long vehicleId;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "BEAT_ID")
    private Long roId;

    @Column(name = "SAND_VOLUME", nullable = false)
    private Long sandVolume;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "SAN_ID", nullable = false)
    private SanitationStaffTarget sanitationStaffTarget;

    public SanitationStaffTargetDet() {
    }

    public Long getSandId() {
        return this.sandId;
    }

    public void setSandId(Long sandId) {
        this.sandId = sandId;
    }

    public Long getCodWast1() {
        return this.codWast1;
    }

    public void setCodWast1(Long codWast1) {
        this.codWast1 = codWast1;
    }

    public Long getCodWast2() {
        return this.codWast2;
    }

    public void setCodWast2(Long codWast2) {
        this.codWast2 = codWast2;
    }

    public Long getCodWast3() {
        return this.codWast3;
    }

    public void setCodWast3(Long codWast3) {
        this.codWast3 = codWast3;
    }

    public Long getCodWast4() {
        return this.codWast4;
    }

    public void setCodWast4(Long codWast4) {
        this.codWast4 = codWast4;
    }

    public Long getCodWast5() {
        return this.codWast5;
    }

    public void setCodWast5(Long codWast5) {
        this.codWast5 = codWast5;
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

    public Long getDeId() {
        return this.deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
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
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getRoId() {
        return this.roId;
    }

    public void setRoId(Long roId) {
        this.roId = roId;
    }

    public Long getSandVolume() {
        return this.sandVolume;
    }

    public void setSandVolume(Long sandVolume) {
        this.sandVolume = sandVolume;
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

    public SanitationStaffTarget getSanitationStaffTarget() {
        return sanitationStaffTarget;
    }

    public void setSanitationStaffTarget(SanitationStaffTarget sanitationStaffTarget) {
        this.sanitationStaffTarget = sanitationStaffTarget;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_VEHICLE_TGDET", "SAND_ID" };
    }
}