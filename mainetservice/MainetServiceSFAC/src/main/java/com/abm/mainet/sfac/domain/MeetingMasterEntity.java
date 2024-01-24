/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_MEETING_MASTER")
public class MeetingMasterEntity implements Serializable {

	private static final long serialVersionUID = 3444776690756965208L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MEETING_ID", unique = true, nullable = false)
	private Long meetingId;

	@Column(name = "MEETING_NUMBER", length = 15, nullable = false)
	private String meetingNo;

	@Column(name = "MEETING_TYPE_ID", nullable = false)
	private Long meetingTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MEETING_DATE_TIME")
	private Date meetingDate;

	@Column(name = "MEETING_PLACE")
	private String meetingPlace;

	@Column(name = "MEETING_REMARK")
	private String remark;

	@Column(name = "TABLE_AGENDA")
	private String tableAgenda;

	@Column(name = "MEETING_INVITATION_MSG", length = 250, nullable = true)
	private String meetingInvitationMsg;

	@Column(name = "CONVENER_OF_MEETING")
	private Long convenerofMeeting;

	@Column(name = "SUGGESTIONS")
	private String suggestions;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true, updatable = true)
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "meetingMasterEntity", cascade = CascadeType.ALL)
	private List<MeetingDetailEntity> meetingDetail = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingMasterEntity", cascade = CascadeType.ALL)
	private List<MOMDetEntity> momDetEntity = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingMasterEntity", cascade = CascadeType.ALL)
	private List<MeetingAgendaDetEntity> agendaDetEntity = new ArrayList<>();

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
	 * @return the meetingDetail
	 */
	public List<MeetingDetailEntity> getMeetingDetail() {
		return meetingDetail;
	}

	/**
	 * @param meetingDetail the meetingDetail to set
	 */
	public void setMeetingDetail(List<MeetingDetailEntity> meetingDetail) {
		this.meetingDetail = meetingDetail;
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
	 * @return the momDetEntity
	 */
	public List<MOMDetEntity> getMomDetEntity() {
		return momDetEntity;
	}

	/**
	 * @param momDetEntity the momDetEntity to set
	 */
	public void setMomDetEntity(List<MOMDetEntity> momDetEntity) {
		this.momDetEntity = momDetEntity;
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

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_MEETING_MASTER", "MEETING_ID" };
	}

	/**
	 * @return the agendaDetEntity
	 */
	public List<MeetingAgendaDetEntity> getAgendaDetEntity() {
		return agendaDetEntity;
	}

	/**
	 * @param agendaDetEntity the agendaDetEntity to set
	 */
	public void setAgendaDetEntity(List<MeetingAgendaDetEntity> agendaDetEntity) {
		this.agendaDetEntity = agendaDetEntity;
	}

}
