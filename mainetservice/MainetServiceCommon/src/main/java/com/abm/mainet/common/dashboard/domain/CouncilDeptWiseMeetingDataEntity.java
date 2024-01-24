package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CouncilDeptWiseMeetingDataEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "RESOLUTION_ID")
	private String resolutionId;

	@Column(name = "MEETING_ID")
	private String meetingId;

	@Column(name = "MEETING_NUMBER")
	private String meetingNo;

	@Column(name = "MEETING_DATE_TIME")
	private String meetingDate;

	@Column(name = "MEETING_TYPE")
	private String meetingType;

	@Column(name = "MEETING_PLACE")
	private String meetingPlace;

	@Column(name = "MEETING_INVITATION_MSG")
	private String meetingInviteMsg;

	@Column(name = "MEETING_STATUS")
	private String meetingStatus;

	@Column(name = "DP_DEPTDESC")
	private String deptName;

	@Column(name = "PROPOSAL_NUMBER")
	private String proposalNo;

	@Column(name = "PROPOSAL_DATE")
	private String proposalDate;

	@Column(name = "AGENDA_DATE")
	private String agendaDate;

	@Column(name = "PROPOSAL_DETAILS")
	private String proposalDtls;

	@Column(name = "PROPOSAL_AMOUNT")
	private String proposalAmt;

	@Column(name = "purpose_remark")
	private String proposalRemark;

	@Column(name = "CREATED_BY")
	private String empName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResolutionId() {
		return resolutionId;
	}

	public void setResolutionId(String resolutionId) {
		this.resolutionId = resolutionId;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getMeetingNo() {
		return meetingNo;
	}

	public void setMeetingNo(String meetingNo) {
		this.meetingNo = meetingNo;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public String getMeetingInviteMsg() {
		return meetingInviteMsg;
	}

	public void setMeetingInviteMsg(String meetingInviteMsg) {
		this.meetingInviteMsg = meetingInviteMsg;
	}

	public String getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getProposalDate() {
		return proposalDate;
	}

	public void setProposalDate(String proposalDate) {
		this.proposalDate = proposalDate;
	}

	public String getAgendaDate() {
		return agendaDate;
	}

	public void setAgendaDate(String agendaDate) {
		this.agendaDate = agendaDate;
	}

	public String getProposalDtls() {
		return proposalDtls;
	}

	public void setProposalDtls(String proposalDtls) {
		this.proposalDtls = proposalDtls;
	}

	public String getProposalAmt() {
		return proposalAmt;
	}

	public void setProposalAmt(String proposalAmt) {
		this.proposalAmt = proposalAmt;
	}

	public String getProposalRemark() {
		return proposalRemark;
	}

	public void setProposalRemark(String proposalRemark) {
		this.proposalRemark = proposalRemark;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Override
	public String toString() {
		return "CouncilDeptWiseMeetingDataEntity [id=" + id + ", resolutionId=" + resolutionId + ", meetingId="
				+ meetingId + ", meetingNo=" + meetingNo + ", meetingDate=" + meetingDate + ", meetingType="
				+ meetingType + ", meetingPlace=" + meetingPlace + ", meetingInviteMsg=" + meetingInviteMsg
				+ ", meetingStatus=" + meetingStatus + ", deptName=" + deptName + ", proposalNo=" + proposalNo
				+ ", proposalDate=" + proposalDate + ", agendaDate=" + agendaDate + ", proposalDtls=" + proposalDtls
				+ ", proposalAmt=" + proposalAmt + ", proposalRemark=" + proposalRemark + ", empName=" + empName + "]";
	}

}
