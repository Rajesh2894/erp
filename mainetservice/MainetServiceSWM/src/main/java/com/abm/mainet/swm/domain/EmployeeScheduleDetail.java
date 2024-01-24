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
 * The persistent class for the tb_sw_employee_scheddet database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Entity
@Table(name = "TB_SW_EMPLOYEE_SCHEDDET")
public class EmployeeScheduleDetail implements Serializable {

    private static final long serialVersionUID = 8912434764034609084L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "EMSD_ID", unique = true, nullable = false)
    private Long emsdId;

    @Column(name = "COD_WARD1")
    private Long codWard1;

    @Column(name = "COD_WARD2")
    private Long codWard2;

    @Column(name = "COD_WARD3")
    private Long codWard3;

    @Column(name = "COD_WARD4")
    private Long codWard4;

    @Column(name = "COD_WARD5")
    private Long codWard5;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(nullable = false)
    private Long empid;

    @Column(name = "EMSD_DAY")
    private String emsdDay;

    @Column(name = "EMSD_COLL_TYPE")
    private Long emsdCollType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMSD_ENDTIME")
    private Date emsdEndtime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMSD_STARTTIME")
    private Date emsdStarttime;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Temporal(TemporalType.DATE)
    @Column(name = "ESD_SCHEDULEDATE", nullable = false)
    private Date esdScheduledate;

    @Column(name = "LOC_ID")
    private Long locId;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "MRF_ID")
    private Long mrfId;

    // bi-directional many-to-one association to TbSwEmployeeScheduling
    @ManyToOne
    @JoinColumn(name = "EMS_ID", nullable = false)
    private EmployeeSchedule tbSwEmployeeScheduling;

    @Column(name = "BEAT_ID")
    private Long beatId;

    @Column(name = "VE_ID")
    private Long veId;

    @Column(name = "ESD_SHIFTID")
    private Long shiftId;

    public EmployeeScheduleDetail() {
    }

    public Long getEmsdId() {
        return this.emsdId;
    }

    public void setEmsdId(Long emsdId) {
        this.emsdId = emsdId;
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

    public String getEmsdDay() {
        return emsdDay;
    }

    public void setEmsdDay(String emsdDay) {
        this.emsdDay = emsdDay;
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

    public EmployeeSchedule getTbSwEmployeeScheduling() {
        return this.tbSwEmployeeScheduling;
    }

    public void setTbSwEmployeeScheduling(EmployeeSchedule tbSwEmployeeScheduling) {
        this.tbSwEmployeeScheduling = tbSwEmployeeScheduling;
    }

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
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

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_EMPLOYEE_SCHEDDET", "EMSD_ID" };
    }

    public Date getEsdScheduledate() {
        return esdScheduledate;
    }

    public void setEsdScheduledate(Date esdScheduledate) {
        this.esdScheduledate = esdScheduledate;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

}
