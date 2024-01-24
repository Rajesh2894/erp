/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pooja.maske
 *
 */
public class PrintingDto implements Serializable {

	private static final long serialVersionUID = 3222522762962838733L;

	private String meetingTypeName;

	private String meetingNo;

	private String meetingPlace;

	private String meetingDateDesc;

	private String momComments;

	private String actionable;

	private String meetingTime;

	private String memberName;

	private String designation;

	private String convenerOfMeeting;

	private String tableAgenda;

	private String meetingInvitationMsg;

	private String title;

	private List<MeetingMOMDto> momDetList = new ArrayList<>();

	private List<MeetingDetailDto> attendees = new ArrayList<>();

	private String momDetails;

	/**
	 * @return the meetingTypeName
	 */
	public String getMeetingTypeName() {
		return meetingTypeName;
	}

	/**
	 * @param meetingTypeName the meetingTypeName to set
	 */
	public void setMeetingTypeName(String meetingTypeName) {
		this.meetingTypeName = meetingTypeName;
	}

	/**
	 * @return the meetingNo
	 */
	public String getMeetingNo() {
		return meetingNo;
	}

	/**
	 * @param meetingNo the meetingNo to set
	 */
	public void setMeetingNo(String meetingNo) {
		this.meetingNo = meetingNo;
	}

	/**
	 * @return the meetingPlace
	 */
	public String getMeetingPlace() {
		return meetingPlace;
	}

	/**
	 * @param meetingPlace the meetingPlace to set
	 */
	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	/**
	 * @return the meetingDateDesc
	 */
	public String getMeetingDateDesc() {
		return meetingDateDesc;
	}

	/**
	 * @param meetingDateDesc the meetingDateDesc to set
	 */
	public void setMeetingDateDesc(String meetingDateDesc) {
		this.meetingDateDesc = meetingDateDesc;
	}

	/**
	 * @return the momComments
	 */
	public String getMomComments() {
		return momComments;
	}

	/**
	 * @param momComments the momComments to set
	 */
	public void setMomComments(String momComments) {
		this.momComments = momComments;
	}

	/**
	 * @return the actionable
	 */
	public String getActionable() {
		return actionable;
	}

	/**
	 * @param actionable the actionable to set
	 */
	public void setActionable(String actionable) {
		this.actionable = actionable;
	}

	/**
	 * @return the meetingTime
	 */
	public String getMeetingTime() {
		return meetingTime;
	}

	/**
	 * @param meetingTime the meetingTime to set
	 */
	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the convenerOfMeeting
	 */
	public String getConvenerOfMeeting() {
		return convenerOfMeeting;
	}

	/**
	 * @param convenerOfMeeting the convenerOfMeeting to set
	 */
	public void setConvenerOfMeeting(String convenerOfMeeting) {
		this.convenerOfMeeting = convenerOfMeeting;
	}

	/**
	 * @return the tableAgenda
	 */
	public String getTableAgenda() {
		return tableAgenda;
	}

	/**
	 * @param tableAgenda the tableAgenda to set
	 */
	public void setTableAgenda(String tableAgenda) {
		this.tableAgenda = tableAgenda;
	}

	/**
	 * @return the meetingInvitationMsg
	 */
	public String getMeetingInvitationMsg() {
		return meetingInvitationMsg;
	}

	/**
	 * @param meetingInvitationMsg the meetingInvitationMsg to set
	 */
	public void setMeetingInvitationMsg(String meetingInvitationMsg) {
		this.meetingInvitationMsg = meetingInvitationMsg;
	}

	/**
	 * @return the momDetList
	 */
	public List<MeetingMOMDto> getMomDetList() {
		return momDetList;
	}

	/**
	 * @param momDetList the momDetList to set
	 */
	public void setMomDetList(List<MeetingMOMDto> momDetList) {
		this.momDetList = momDetList;
	}

	/**
	 * @return the attendees
	 */
	public List<MeetingDetailDto> getAttendees() {
		return attendees;
	}

	/**
	 * @param attendees the attendees to set
	 */
	public void setAttendees(List<MeetingDetailDto> attendees) {
		this.attendees = attendees;
	}

	/**
	 * @return the momDetails
	 */
	public String getMomDetails() {
		return momDetails;
	}

	/**
	 * @param momDetails the momDetails to set
	 */
	public void setMomDetails(String momDetails) {
		this.momDetails = momDetails;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
