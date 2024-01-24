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
 * The persistent class for the tb_sw_vehicle_scheddet_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLE_SCHEDDET_HIST")
public class VehicleScheduleDetHistory implements Serializable {

    private static final long serialVersionUID = 7200904878242462895L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VESD_ID_H", unique = true, nullable = false)
    private Long vesdIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    public Long getBeatId() {
        return beatId;
    }

    @Column(name = "VES_EMPID", nullable = true)
    private Long empId;

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
    }

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "BEAT_ID")
    private Long beatId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VES_ENDTIME")
    private Date vesEndtime;

    @Column(name = "VES_ID")
    private Long vesId;

    @Column(name = "VES_MONTH")
    private int vesMonth;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VES_STARTIME")
    private Date vesStartime;

    @Column(name = "VES_WEEKDAY")
    private int vesWeekday;

    @Column(name = "VESD_ID")
    private Long vesdId;

    @Column(name = "VES_COLL_TYPE")
    private Long vesCollType;

    @Temporal(TemporalType.DATE)
    @Column(name = "VES_SCHEDULEDT", nullable = false)
    private Date veScheduledate;

    public VehicleScheduleDetHistory() {
    }

    public Long getVesdIdH() {
        return this.vesdIdH;
    }

    public void setVesdIdH(Long vesdIdH) {
        this.vesdIdH = vesdIdH;
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

    public Date getVesEndtime() {
        return this.vesEndtime;
    }

    public void setVesEndtime(Date vesEndtime) {
        this.vesEndtime = vesEndtime;
    }

    public Long getVesId() {
        return this.vesId;
    }

    public void setVesId(Long vesId) {
        this.vesId = vesId;
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

    public int getVesWeekday() {
        return this.vesWeekday;
    }

    public void setVesWeekday(int vesWeekday) {
        this.vesWeekday = vesWeekday;
    }

    public Long getVesdId() {
        return this.vesdId;
    }

    public void setVesdId(Long vesdId) {
        this.vesdId = vesdId;
    }

    public Long getVesCollType() {
        return vesCollType;
    }

    public void setVesCollType(Long vesCollType) {
        this.vesCollType = vesCollType;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_VEHICLE_SCHEDDET_HIST", "VESD_ID_H" };
    }

    public Date getVeScheduledate() {
        return veScheduledate;
    }

    public void setVeScheduledate(Date veScheduledate) {
        this.veScheduledate = veScheduledate;
    }

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

}