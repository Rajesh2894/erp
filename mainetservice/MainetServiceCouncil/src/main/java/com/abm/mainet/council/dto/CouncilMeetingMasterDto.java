package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author aarti.paan
 * @since 25 April 2019
 */
public class CouncilMeetingMasterDto implements Serializable {

    private static final long serialVersionUID = -2512212586731021248L;

    private Long meetingId;

    private String meetingNo;

    private Long meetingTypeId;

    private String meetingTypeName;

    private Date meetingDate;

    private String meetingDateDesc;

    private String meetingTime;

    private String meetingStatus;

    private String meetingInvitationMsg;

    private String meetingPlace;

    private Long agendaId;

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Date fromDate;

    private Date toDate;

    private String memberIdByCommitteeType;

    private CouncilAgendaMasterDto agendaMasterDto;

    private Long totalMember;

    private Long memberPresent;

    private Long memberAbsent;

    private Long momId;// this is use when mom summary display

    private Boolean actionBT; // this is used for meeting summary screen where runtime action button show and hide based on
                              // compare date
    
    private String reason;
    
    private String prevMeetingPlace;
    private String prevMessage;
    private String prevMeetingDateDesc;
    private Long memberLeave;

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

    public String getMeetingDateDesc() {
        return meetingDateDesc;
    }

    public void setMeetingDateDesc(String meetingDateDesc) {
        this.meetingDateDesc = meetingDateDesc;
    }

    public String getMeetingTypeName() {
        return meetingTypeName;
    }

    public void setMeetingTypeName(String meetingTypeName) {
        this.meetingTypeName = meetingTypeName;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public String getMeetingInvitationMsg() {
        return meetingInvitationMsg;
    }

    public void setMeetingInvitationMsg(String meetingInvitationMsg) {
        this.meetingInvitationMsg = meetingInvitationMsg;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public String getMemberIdByCommitteeType() {
        return memberIdByCommitteeType;
    }

    public void setMemberIdByCommitteeType(String memberIdByCommitteeType) {
        this.memberIdByCommitteeType = memberIdByCommitteeType;
    }

    public CouncilAgendaMasterDto getAgendaMasterDto() {
        return agendaMasterDto;
    }

    public void setAgendaMasterDto(CouncilAgendaMasterDto agendaMasterDto) {
        this.agendaMasterDto = agendaMasterDto;
    }

    public Long getMemberPresent() {
        return memberPresent;
    }

    public void setMemberPresent(Long memberPresent) {
        this.memberPresent = memberPresent;
    }

    public Long getMemberAbsent() {
        return memberAbsent;
    }

    public void setMemberAbsent(Long memberAbsent) {
        this.memberAbsent = memberAbsent;
    }

    public Long getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Long totalMember) {
        this.totalMember = totalMember;
    }

    public Long getMomId() {
        return momId;
    }

    public void setMomId(Long momId) {
        this.momId = momId;
    }

    public Boolean getActionBT() {
        return actionBT;
    }

    public void setActionBT(Boolean actionBT) {
        this.actionBT = actionBT;
    }

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPrevMeetingPlace() {
		return prevMeetingPlace;
	}

	public void setPrevMeetingPlace(String prevMeetingPlace) {
		this.prevMeetingPlace = prevMeetingPlace;
	}

	public String getPrevMessage() {
		return prevMessage;
	}

	public void setPrevMessage(String prevMessage) {
		this.prevMessage = prevMessage;
	}

	public String getPrevMeetingDateDesc() {
		return prevMeetingDateDesc;
	}

	public void setPrevMeetingDateDesc(String prevMeetingDateDesc) {
		this.prevMeetingDateDesc = prevMeetingDateDesc;
	}

	public Long getMemberLeave() {
		return memberLeave;
	}

	public void setMemberLeave(Long memberLeave) {
		this.memberLeave = memberLeave;
	}

}
