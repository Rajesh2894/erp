package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
public class VehicleScheduleDetDTO implements Serializable {

    private static final long serialVersionUID = 4414452214849222685L;

    private Long vesdId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private int vesMonth;

    private Date vesEndtime;

    private Date vesStartime;

    private String endtime;

    private String startime;

    private String vesWeekday;

    private Long routeId;

    private Long vesCollType;

    private Date veScheduledate;

    private String sheduleDate;

    @JsonIgnore
    private VehicleScheduleDTO tbSwVehicleScheduling;

    public VehicleScheduleDetDTO() {
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

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getVesCollType() {
        return vesCollType;
    }

    public void setVesCollType(Long vesCollType) {
        this.vesCollType = vesCollType;
    }

    public VehicleScheduleDTO getTbSwVehicleScheduling() {
        return this.tbSwVehicleScheduling;
    }

    public void setTbSwVehicleScheduling(VehicleScheduleDTO tbSwVehicleScheduling) {
        this.tbSwVehicleScheduling = tbSwVehicleScheduling;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStartime() {
        return startime;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public Date getVeScheduledate() {
        return veScheduledate;
    }

    public void setVeScheduledate(Date veScheduledate) {
        this.veScheduledate = veScheduledate;
    }

    public String getSheduleDate() {
        return sheduleDate;
    }

    public void setSheduleDate(String sheduleDate) {
        this.sheduleDate = sheduleDate;
    }

}