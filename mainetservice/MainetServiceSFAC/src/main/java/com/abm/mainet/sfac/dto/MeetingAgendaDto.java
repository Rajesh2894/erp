/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.abm.mainet.sfac.domain.MeetingMasterEntity;

/**
 * @author pooja.maske
 *
 */
public class MeetingAgendaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7807544517337037499L;

	private Long agendaId;

	private Long meetingId; // fk

	private String agendaDesc;

	private String status;

	private Long orgId;

	private Long createdBy;

	private Long updatedBy;

	private Date createdDate;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	/**
	 * @return the agendaId
	 */
	public Long getAgendaId() {
		return agendaId;
	}

	/**
	 * @param agendaId the agendaId to set
	 */
	public void setAgendaId(Long agendaId) {
		this.agendaId = agendaId;
	}

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
	 * @return the agendaDesc
	 */
	public String getAgendaDesc() {
		return agendaDesc;
	}

	/**
	 * @param agendaDesc the agendaDesc to set
	 */
	public void setAgendaDesc(String agendaDesc) {
		this.agendaDesc = agendaDesc;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
