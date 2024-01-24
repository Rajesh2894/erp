package com.abm.mainet.council.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author aarti.paan
 * @since 23 April 2019
 */
@Entity
@Table(name = "TB_CMT_COUNCIL_MEETING_MAST")
public class CouncilMeetingMasterEntity implements Serializable {

    private static final long serialVersionUID = 1665458029782211466L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MEETING_ID")
    private Long meetingId;

    @Column(name = "MEETING_NUMBER", length = 15, nullable = false)
    private String meetingNo;

    @Column(name = "MEETING_TYPE_ID", nullable = false)
    private Long meetingTypeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MEETING_DATE_TIME", nullable = false)
    private Date meetingDate;

    @Column(name = "MEETING_PLACE", nullable = false)
    private String meetingPlace;

    @Column(name = "MEETING_INVITATION_MSG", length = 250, nullable = false)
    private String meetingInvitationMsg;

    @Column(name = "MEETING_STATUS", length = 250, nullable = false)
    private String meetingStatus;

    @OneToOne
    @JoinColumn(name = "AGENDA_ID", referencedColumnName = "AGENDA_ID")
    private CouncilAgendaMasterEntity agendaId;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true, updatable = true)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

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

    public CouncilAgendaMasterEntity getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(CouncilAgendaMasterEntity agendaId) {
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

    public static String[] getPkValues() {
        return new String[] { "CMT", "TB_CMT_COUNCIL_MEETING_MAST", "MEETING_ID" };
    }
}
