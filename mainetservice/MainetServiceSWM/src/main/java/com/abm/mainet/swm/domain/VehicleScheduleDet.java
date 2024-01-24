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
 * The persistent class for the tb_sw_vehicle_scheddet database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLE_SCHEDDET")
public class VehicleScheduleDet implements Serializable {

    private static final long serialVersionUID = 6350813471941136890L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VESD_ID", unique = true, nullable = false)
    private Long vesdId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VES_ENDTIME", nullable = false)
    private Date vesEndtime;

    @Column(name = "VES_MONTH")
    private int vesMonth;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VES_STARTIME", nullable = false)
    private Date vesStartime;

    @Column(name = "VES_WEEKDAY")
    private String vesWeekday;

    @Column(name = "BEAT_ID", nullable = false)
    private Long beatId;

    @Column(name = "VES_EMPID", nullable = false)
    private String empId;

    @Column(name = "VES_COLL_TYPE")
    private Long vesCollType;

    @Temporal(TemporalType.DATE)
    @Column(name = "VES_SCHEDULEDT", nullable = false)
    private Date veScheduledate;

    // bi-directional many-to-one association to TbSwVehicleScheduling
    @ManyToOne
    @JoinColumn(name = "VES_ID", nullable = false)
    private VehicleSchedule tbSwVehicleScheduling;

    public VehicleScheduleDet() {
    }

    public Long getVesdId() {
        return this.vesdId;
    }

    public void setVesdId(Long vesdId) {
        this.vesdId = vesdId;
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

    public Date getVesEndtime() {
        return this.vesEndtime;
    }

    public void setVesEndtime(Date vesEndtime) {
        this.vesEndtime = vesEndtime;
    }

    public int getVesMonth() {
        return this.vesMonth;
    }

    public void setVesMonth(int vesMonth) {
        this.vesMonth = vesMonth;
    }

    public Date getVesStartime() {
        return this.vesStartime;
    }

    public void setVesStartime(Date vesStartime) {
        this.vesStartime = vesStartime;
    }

    public String getVesWeekday() {
        return vesWeekday;
    }

    public void setVesWeekday(String vesWeekday) {
        this.vesWeekday = vesWeekday;
    }

    public Long getBeatId() {
        return beatId;
    }

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
    }

    public Long getVesCollType() {
        return vesCollType;
    }

    public void setVesCollType(Long vesCollType) {
        this.vesCollType = vesCollType;
    }

    public VehicleSchedule getTbSwVehicleScheduling() {
        return this.tbSwVehicleScheduling;
    }

    public void setTbSwVehicleScheduling(VehicleSchedule tbSwVehicleScheduling) {
        this.tbSwVehicleScheduling = tbSwVehicleScheduling;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_VEHICLE_SCHEDDET", "VESD_ID" };
    }

    public Date getVeScheduledate() {
        return veScheduledate;
    }

    public void setVeScheduledate(Date veScheduledate) {
        this.veScheduledate = veScheduledate;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

}