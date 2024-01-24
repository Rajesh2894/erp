/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pooja.maske
 *
 */
public class MeetingMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2897364463340597126L;

	private Long meetingId;

	private String meetingNo;

	private Long meetingTypeId;

	private Date meetingDate;

	private String meetingPlace;

	private String remark;

	private String meetingAgenda;

	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String meetingTypeName;

	private String meetingDateDesc;

	private String meetingTime;

	private String tableAgenda;

	private String meetingInvitationMsg;

	private Long convenerofMeeting;

	private String suggestions;

	private List<MeetingDetailDto> meetingDetailDto = new ArrayList<>();
	private List<MeetingMOMDto> meetingMOMDto = new ArrayList<>();
	
	private List<MeetingAgendaDto> meetinAgendaDto = new ArrayList<>();

	/**
	 * @return the meetingId
	 */
	public Long getMeetingId() {
		return meetingId;
	}

	/**
	 * @param meetingId the meetingId to set
	 */
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
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
	 * @return the meetingTypeId
	 */
	public Long getMeetingTypeId() {
		return meetingTypeId;
	}

	/**
	 * @param meetingTypeId the meetingTypeId to set
	 */
	public void setMeetingTypeId(Long meetingTypeId) {
		this.meetingTypeId = meetingTypeId;
	}

	/**
	 * @return the meetingDate
	 */
	public Date getMeetingDate() {
		return meetingDate;
	}

	/**
	 * @param meetingDate the meetingDate to set
	 */
	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the meetingAgenda
	 */
	public String getMeetingAgenda() {
		return meetingAgenda;
	}

	/**
	 * @param meetingAgenda the meetingAgenda to set
	 */
	public void setMeetingAgenda(String meetingAgenda) {
		this.meetingAgenda = meetingAgenda;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	/**
	 * @return the meetingDetailDto
	 */
	public List<MeetingDetailDto> getMeetingDetailDto() {
		return meetingDetailDto;
	}

	/**
	 * @param meetingDetailDto the meetingDetailDto to set
	 */
	public void setMeetingDetailDto(List<MeetingDetailDto> meetingDetailDto) {
		this.meetingDetailDto = meetingDetailDto;
	}

	/**
	 * @return the meetingMOMDto
	 */
	public List<MeetingMOMDto> getMeetingMOMDto() {
		return meetingMOMDto;
	}

	/**
	 * @param meetingMOMDto the meetingMOMDto to set
	 */
	public void setMeetingMOMDto(List<MeetingMOMDto> meetingMOMDto) {
		this.meetingMOMDto = meetingMOMDto;
	}

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
	 * @return the convenerofMeeting
	 */
	public Long getConvenerofMeeting() {
		return convenerofMeeting;
	}

	/**
	 * @param convenerofMeeting the convenerofMeeting to set
	 */
	public void setConvenerofMeeting(Long convenerofMeeting) {
		this.convenerofMeeting = convenerofMeeting;
	}

	/**
	 * @return the suggestions
	 */
	public String getSuggestions() {
		return suggestions;
	}

	/**
	 * @param suggestions the suggestions to set
	 */
	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	/**
	 * @return the meetinAgendaDto
	 */
	public List<MeetingAgendaDto> getMeetinAgendaDto() {
		return meetinAgendaDto;
	}

	/**
	 * @param meetinAgendaDto the meetinAgendaDto to set
	 */
	public void setMeetinAgendaDto(List<MeetingAgendaDto> meetinAgendaDto) {
		this.meetinAgendaDto = meetinAgendaDto;
	}

	
}
