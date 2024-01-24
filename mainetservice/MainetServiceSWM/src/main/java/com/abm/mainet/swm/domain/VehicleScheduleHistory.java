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
 * The persistent class for the tb_sw_vehicle_scheduling_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLE_SCHEDULING_HIST")
public class VehicleScheduleHistory implements Serializable {

    private static final long serialVersionUID = 660135565068556278L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VES_ID_H", unique = true, nullable = false)
    private Long vesIdH;

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

    @Column(name = "VE_ID")
    private Long veId;

    @Column(name = "VE_VETYPE")
    private Long veVetype;

    @Temporal(TemporalType.DATE)
    @Column(name = "VES_FROMDT")
    private Date vesFromdt;

    @Column(name = "VES_ID")
    private Long vesId;

    @Column(name = "VES_REOCC", length = 1)
    private String vesReocc;

    @Temporal(TemporalType.DATE)
    @Column(name = "VES_TODT")
    private Date vesTodt;

    public VehicleScheduleHistory() {
    }

    public Long getVesIdH() {
        return this.vesIdH;
    }

    public void setVesIdH(Long vesIdH) {
        this.vesIdH = vesIdH;
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

    public Long getVeId() {
        return this.veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public Long getVeVetype() {
        return this.veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public Date getVesFromdt() {
        return this.vesFromdt;
    }

    public void setVesFromdt(Date vesFromdt) {
        this.vesFromdt = vesFromdt;
    }

    public Long getVesId() {
        return this.vesId;
    }

    public void setVesId(Long vesId) {
        this.vesId = vesId;
    }

    public String getVesReocc() {
        return this.vesReocc;
    }

    public void setVesReocc(String vesReocc) {
        this.vesReocc = vesReocc;
    }

    public Date getVesTodt() {
        return this.vesTodt;
    }

    public void setVesTodt(Date vesTodt) {
        this.vesTodt = vesTodt;
    }


    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_VEHICLE_SCHEDULING_HIST", "VES_ID_H" };
    }
}