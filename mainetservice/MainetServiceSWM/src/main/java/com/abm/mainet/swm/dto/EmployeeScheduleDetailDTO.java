package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
public class EmployeeScheduleDetailDTO implements Serializable {

    private static final long serialVersionUID = 7888552007614890934L;

    private Long emsdId;

    private Long mrfId;

    private Long codWard1;

    private Long codWard2;

    private Long codWard3;

    private Long codWard4;

    private Long codWard5;

    private Long createdBy;

    private Date createdDate;

    private Long empid;

    private Long emsdCollType;

    private String emsdDay;

    private Date emsdEndtime;

    private Date emsdStarttime;

    private String endTime;

    private String startTime;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long locId;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private Date esdScheduledate;

    private Long deId;

    private EmployeeScheduleDTO tbSwEmployeeScheduling;

    private Long beatId;

    private Long veId;

    private Long veVetype;

    private Long shiftId;

    private String scheduleDate;

    public EmployeeScheduleDetailDTO() {
    }

    public Long getEmsdId() {
        return this.emsdId;
    }

    public void setEmsdId(Long emsdId) {
        this.emsdId = emsdId;
    }

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    public Long getCodWard1() {
        return this.codWard1;
    }

    public void setCodWard1(Long codWard1) {
        this.codWard1 = codWard1;
    }

    public Long getCodWard2() {
        return this.codWard2;
    }

    public void setCodWard2(Long codWard2) {
        this.codWard2 = codWard2;
    }

    public Long getCodWard3() {
        return this.codWard3;
    }

    public void setCodWard3(Long codWard3) {
        this.codWard3 = codWard3;
    }

    public Long getCodWard4() {
        return this.codWard4;
    }

    public void setCodWard4(Long codWard4) {
        this.codWard4 = codWard4;
    }

    public Long getCodWard5() {
        return this.codWard5;
    }

    public void setCodWard5(Long codWard5) {
        this.codWard5 = codWard5;
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

    public Long getEmpid() {
        return this.empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public Long getEmsdCollType() {
        return this.emsdCollType;
    }

    public void setEmsdCollType(Long emsdCollType) {
        this.emsdCollType = emsdCollType;
    }

    public Date getEmsdEndtime() {
        return this.emsdEndtime;
    }

    public void setEmsdEndtime(Date emsdEndtime) {
        this.emsdEndtime = emsdEndtime;
    }

    public Date getEmsdStarttime() {
        return this.emsdStarttime;
    }

    public void setEmsdStarttime(Date emsdStarttime) {
        this.emsdStarttime = emsdStarttime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public Long getLocId() {
        return this.locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
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

    public EmployeeScheduleDTO getTbSwEmployeeScheduling() {
        return this.tbSwEmployeeScheduling;
    }

    public void setTbSwEmployeeScheduling(EmployeeScheduleDTO tbSwEmployeeScheduling) {
        this.tbSwEmployeeScheduling = tbSwEmployeeScheduling;
    }

    public Long getDeId() {
        return deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public Long getBeatId() {
        return beatId;
    }

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
    }

    public Long getVeId() {
        return veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public String getEmsdDay() {
        return emsdDay;
    }

    public void setEmsdDay(String emsdDay) {
        this.emsdDay = emsdDay;
    }

    public Date getEsdScheduledate() {
        return esdScheduledate;
    }

    public void setEsdScheduledate(Date esdScheduledate) {
        this.esdScheduledate = esdScheduledate;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Long getVeVetype() {
        return veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

}