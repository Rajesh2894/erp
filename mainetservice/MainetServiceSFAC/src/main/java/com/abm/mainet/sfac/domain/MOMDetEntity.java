/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_MEETING_MOM")
public class MOMDetEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -288817820878197140L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MEETING_MOM_ID")
	private Long momId;

	@ManyToOne
	@JoinColumn(name = "MEETING_ID", referencedColumnName = "MEETING_ID")
	private MeetingMasterEntity meetingMasterEntity;

	@Column(name = "MOM_COMMENTS", nullable = false)
	private String momComments;

	@Column(name = "ACTION_OWNER", nullable = true)
	private Long actionowner;

	@Column(name = "STATUS", length = 1)
	private String status;

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

	/**
	 * @return the momId
	 */
	public Long getMomId() {
		return momId;
	}

	/**
	 * @param momId the momId to set
	 */
	public void setMomId(Long momId) {
		this.momId = momId;
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
	 * @return the meetingMasterEntity
	 */
	public MeetingMasterEntity getMeetingMasterEntity() {
		return meetingMasterEntity;
	}

	/**
	 * @param meetingMasterEntity the meetingMasterEntity to set
	 */
	public void setMeetingMasterEntity(MeetingMasterEntity meetingMasterEntity) {
		this.meetingMasterEntity = meetingMasterEntity;
	}

	/**
	 * @return the actionowner
	 */
	public Long getActionowner() {
		return actionowner;
	}

	/**
	 * @param actionowner the actionowner to set
	 */
	public void setActionowner(Long actionowner) {
		this.actionowner = actionowner;
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

	public static String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_MEETING_MOM", "MEETING_MOM_ID" };
	}

}
