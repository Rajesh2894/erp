package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrintingDto implements Serializable {

    private static final long serialVersionUID = 2373776087695048431L;

    private String committeeName;

    private String meetingName;

    private String meetingNo;

    private String agendaNo;

    private String meetingDateDesc;

    private String momDate;

    private String meetingTime;

    private String memberName;

    private String designation;

    private String electionWard;

    private String memberAddress;

    private String ulbName;

    private String muncipal;

    private String location;

    private String departmentName;
    
    private String committeeCode;

    // D#76375
    private String meetingInvitationMsg;

    private List<CouncilProposalMasterDto> subjects = new ArrayList<>();

    private List<CouncilMemberCommitteeMasterDto> attendees = new ArrayList<>();

    private MOMResolutionDto momResolutionDto;

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingNo() {
        return meetingNo;
    }

    public void setMeetingNo(String meetingNo) {
        this.meetingNo = meetingNo;
    }

    public String getMeetingDateDesc() {
        return meetingDateDesc;
    }

    public void setMeetingDateDesc(String meetingDateDesc) {
        this.meetingDateDesc = meetingDateDesc;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMomDate() {
        return momDate;
    }

    public void setMomDate(String momDate) {
        this.momDate = momDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUlbName() {
        return ulbName;
    }

    public void setUlbName(String ulbName) {
        this.ulbName = ulbName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMeetingInvitationMsg() {
        return meetingInvitationMsg;
    }

    public void setMeetingInvitationMsg(String meetingInvitationMsg) {
        this.meetingInvitationMsg = meetingInvitationMsg;
    }

    public List<CouncilProposalMasterDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<CouncilProposalMasterDto> subjects) {
        this.subjects = subjects;
    }

    public String getElectionWard() {
        return electionWard;
    }

    public void setElectionWard(String electionWard) {
        this.electionWard = electionWard;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMuncipal() {
        return muncipal;
    }

    public void setMuncipal(String muncipal) {
        this.muncipal = muncipal;
    }

    public String getAgendaNo() {
        return agendaNo;
    }

    public void setAgendaNo(String agendaNo) {
        this.agendaNo = agendaNo;
    }

    public List<CouncilMemberCommitteeMasterDto> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<CouncilMemberCommitteeMasterDto> attendees) {
        this.attendees = attendees;
    }

    public MOMResolutionDto getMomResolutionDto() {
        return momResolutionDto;
    }

    public void setMomResolutionDto(MOMResolutionDto momResolutionDto) {
        this.momResolutionDto = momResolutionDto;
    }

	public String getCommitteeCode() {
		return committeeCode;
	}

	public void setCommitteeCode(String committeeCode) {
		this.committeeCode = committeeCode;
	}

}
