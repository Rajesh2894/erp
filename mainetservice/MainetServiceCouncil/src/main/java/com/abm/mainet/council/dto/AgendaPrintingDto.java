package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AgendaPrintingDto implements Serializable {

    private static final long serialVersionUID = 2373776087695048431L;

    private String committeeName;

    private String meetingName;

    private String meetingNo;

    private String meetingDateDesc;

    private String meetingTime;

    private String memberName;

    private String designation;

    private String ulbName;

    private String location;

    private List<CouncilProposalMasterDto> subjects = new ArrayList<>();

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

    public List<CouncilProposalMasterDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<CouncilProposalMasterDto> subjects) {
        this.subjects = subjects;
    }

}
