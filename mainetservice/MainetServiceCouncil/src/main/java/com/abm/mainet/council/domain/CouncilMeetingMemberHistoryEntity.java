package com.abm.mainet.council.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_CMT_COUNCIL_MEETING_MEMBER_HIST")
public class CouncilMeetingMemberHistoryEntity implements Serializable {
    private static final long serialVersionUID = 5994903915067834425L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MEETING_MEMBER_ID_H")
    private Long meetingMemberHisId;

    @Column(name = "MEETING_MEMBER_ID")
    private Long meetingMemberId; // primary key of TB_CMT_COUNCIL_MEETING_MEMBER

    @Column(name = "MEETING_ID")
    private Long meetingId; // primary key of TB_CMT_COUNCIL_MEETING_MAST

    @Column(name = "COU_ID", nullable = false)
    private Long memberId; // primary key of TB_CMT_COUNCIL_MEM_MAST

    @Column(name = "ATTENDANCE_STATUS", length = 1, nullable = false)
    private int attendanceStatus;

    @Column(name = "H_STATUS", length = 1)
    private String historyStatus;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public Long getMeetingMemberHisId() {
        return meetingMemberHisId;
    }

    public void setMeetingMemberHisId(Long meetingMemberHisId) {
        this.meetingMemberHisId = meetingMemberHisId;
    }

    public Long getMeetingMemberId() {
        return meetingMemberId;
    }

    public void setMeetingMemberId(Long meetingMemberId) {
        this.meetingMemberId = meetingMemberId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public int getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(int attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(String historyStatus) {
        this.historyStatus = historyStatus;
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

    public static String[] getPkValues() {
        return new String[] { "CMT", "TB_CMT_COUNCIL_MEETING_MEMBER_HIST", "MEETING_MEMBER_ID_H" };
    }

}
