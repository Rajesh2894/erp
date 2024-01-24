package com.abm.mainet.council.domain;

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
 * @author aarti.paan
 * @since 4th July 2019
 *
 */
@Entity
@Table(name = "tb_cmt_council_meeting_mast_hist")
public class CouncilMeetingMasterHistoryEntity implements Serializable {

    private static final long serialVersionUID = -7956468676386042495L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MEETING_ID_H")
    private Long meetingHisId;

    @Column(name = "MEETING_ID")
    private Long meetingId;

    @Column(name = "MEETING_NUMBER")
    private String meetingNo;

    @Column(name = "MEETING_TYPE_ID")
    private Long meetingTypeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MEETING_DATE_TIME")
    private Date meetingDate;

    @Column(name = "MEETING_PLACE")
    private String meetingPlace;

    @Column(name = "MEETING_INVITATION_MSG")
    private String meetingInvitationMsg;

    @Column(name = "MEETING_STATUS")
    private String meetingStatus;

    @Column(name = "H_STATUS", length = 1)
    private String historyStatus;

    @Column(name = "AGENDA_ID")
    private Long agendaId;
    
    @Column(name = "reason")
    private String reason;

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

    public Long getMeetingHisId() {
        return meetingHisId;
    }

    public void setMeetingHisId(Long meetingHisId) {
        this.meetingHisId = meetingHisId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingNo() {
        return meetingNo;
    }

    public void setMeetingNo(String meetingNo) {
        this.meetingNo = meetingNo;
    }

    public Long getMeetingTypeId() {
        return meetingTypeId;
    }

    public void setMeetingTypeId(Long meetingTypeId) {
        this.meetingTypeId = meetingTypeId;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public String getMeetingInvitationMsg() {
        return meetingInvitationMsg;
    }

    public void setMeetingInvitationMsg(String meetingInvitationMsg) {
        this.meetingInvitationMsg = meetingInvitationMsg;
    }

    public String getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public String getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(String historyStatus) {
        this.historyStatus = historyStatus;
    }

    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long agendaId) {
        this.agendaId = agendaId;
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

    public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public static String[] getPkValues() {
        return new String[] { "CMT", "tb_cmt_council_meeting_mast_hist", "MEETING_ID_H" };
    }
}
