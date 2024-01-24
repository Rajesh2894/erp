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
 * The persistent class for the tb_sw_employee_scheddet_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Entity
@Table(name = "TB_SW_EMPLOYEE_SCHEDDET_HIST")
public class EmployeeScheduleDetailHistory implements Serializable {

    private static final long serialVersionUID = -3989545376464136159L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "EMSD_ID_H", unique = true, nullable = false)
    private Long emsdIdH;

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

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MRF_ID")
    private Long mrfId;

    private Long empid;

    @Column(name = "EMS_ID")
    private Long emsId;

    @Column(name = "EMSD_COLL_TYPE")
    private Long emsdCollType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMSD_ENDTIME")
    private Date emsdEndtime;

    @Column(name = "EMSD_ID")
    private Long emsdId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMSD_STARTTIME")
    private Date emsdStarttime;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Temporal(TemporalType.DATE)
    @Column(name = "ESD_SCHEDULEDATE", nullable = false)
    private Date esdScheduledate;

    @Column(name = "LOC_ID")
    private Long locId;

    private Long orgid;

    @Column(name = "BEAT_ID")
    private Long beatId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VE_ID")
    private Long veId;

    public EmployeeScheduleDetailHistory() {
    }

    public Long getEmsdIdH() {
        return this.emsdIdH;
    }

    public void setEmsdIdH(Long emsdIdH) {
        this.emsdIdH = emsdIdH;
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

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    public Long getEmpid() {
        return this.empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public Long getEmsId() {
        return this.emsId;
    }

    public void setEmsId(Long emsId) {
        this.emsId = emsId;
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

    public Long getEmsdId() {
        return this.emsdId;
    }

    public void setEmsdId(Long emsdId) {
        this.emsdId = emsdId;
    }

    public Date getEmsdStarttime() {
        return this.emsdStarttime;
    }

    public void setEmsdStarttime(Date emsdStarttime) {
        this.emsdStarttime = emsdStarttime;
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

    public Long getBeatId() {
        return beatId;
    }

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
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

    public Date getEsdScheduledate() {
        return esdScheduledate;
    }

    public void setEsdScheduledate(Date esdScheduledate) {
        this.esdScheduledate = esdScheduledate;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_EMPLOYEE_SCHEDDET_HIST", "EMSD_ID_H" };
    }
}