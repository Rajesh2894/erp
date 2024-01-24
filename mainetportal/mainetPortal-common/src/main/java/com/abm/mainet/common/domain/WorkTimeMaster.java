package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_WORKTIME_MAS")
public class WorkTimeMaster implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -874253081365786709L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WR_ID", precision = 12, scale = 0, nullable = false)
    private Long wrId;

    @Column(name = "WR_ENTRY_DATE", nullable = false)
    private Date wrEntryDate;

    @Column(name = "WR_START_TIME", columnDefinition = "DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date wrStartTime;

    @Column(name = "WR_END_TIME", columnDefinition = "DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date wrEndTime;

    @Column(name = "WR_WEEK_TYPE", precision = 12, scale = 0, nullable = true)
    private Long wrWeekType;

    @Column(name = "WR_WORK_WEEK", length = 50, nullable = true)
    private String wrWorkWeek;

    @Column(name = "WR_ODD_WORK_WEEK", length = 50, nullable = true)
    private String wrOddWorkWeek;

    @Column(name = "WR_EVEN_WORK_WEEK", length = 50, nullable = true)
    private String wrEvenWorkWeek;

    @Column(name = "WR_VALID_END_DATE", nullable = true)
    private Date wrValidEndDate;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = false)
    private String lgIpMacUpd;

    @Transient
    private String wrStartTimeString;

    @Transient
    private String wrEndTimeString;

    @Transient
    private String worrkWeekFlag;

    public Long getWrId() {
        return wrId;
    }

    public void setWrId(Long wrId) {
        this.wrId = wrId;
    }

    public Date getWrEntryDate() {
        return wrEntryDate;
    }

    public void setWrEntryDate(Date wrEntryDate) {
        this.wrEntryDate = wrEntryDate;
    }

    public Date getWrStartTime() {
        return wrStartTime;
    }

    public void setWrStartTime(Date wrStartTime) {
        this.wrStartTime = wrStartTime;
    }

    public Date getWrEndTime() {
        return wrEndTime;
    }

    public void setWrEndTime(Date wrEndTime) {
        this.wrEndTime = wrEndTime;
    }

    public Long getWrWeekType() {
        return wrWeekType;
    }

    public void setWrWeekType(Long wrWeekType) {
        this.wrWeekType = wrWeekType;
    }

    public String getWrWorkWeek() {
        return wrWorkWeek;
    }

    public void setWrWorkWeek(String wrWorkWeek) {
        this.wrWorkWeek = wrWorkWeek;
    }

    public String getWrOddWorkWeek() {
        return wrOddWorkWeek;
    }

    public void setWrOddWorkWeek(String wrOddWorkWeek) {
        this.wrOddWorkWeek = wrOddWorkWeek;
    }

    public String getWrEvenWorkWeek() {
        return wrEvenWorkWeek;
    }

    public void setWrEvenWorkWeek(String wrEvenWorkWeek) {
        this.wrEvenWorkWeek = wrEvenWorkWeek;
    }

    public Date getWrValidEndDate() {
        return wrValidEndDate;
    }

    public void setWrValidEndDate(Date wrValidEndDate) {
        this.wrValidEndDate = wrValidEndDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getWrStartTimeString() {
        return wrStartTimeString;
    }

    public void setWrStartTimeString(String wrStartTimeString) {
        this.wrStartTimeString = wrStartTimeString;
    }

    public String getWrEndTimeString() {
        return wrEndTimeString;
    }

    public void setWrEndTimeString(String wrEndTimeString) {
        this.wrEndTimeString = wrEndTimeString;
    }

    public String getWorrkWeekFlag() {
        return worrkWeekFlag;
    }

    public void setWorrkWeekFlag(String worrkWeekFlag) {
        this.worrkWeekFlag = worrkWeekFlag;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_WORKTIME_MAS", "WR_ID" };
    }

}
