package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_counterschedule_hist")
public class CFCCounterScheduleHistEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4397375614969729950L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_cs_scheduleid", nullable = false)
	private Long hSsScheduleid;

	@Column(name = "cs_scheduleid", nullable = false)
	private Long csScheduleid;

	@Column(name = "cs_fromtime", nullable = false)
	private Date csFromTime;

	@Column(name = "cs_totime", nullable = false)
	private Date csToTime;

	@Column(name = "cs_user_id", nullable = false)
	private Long csUserId;

	@Column(name = "cu_counterid", nullable = false)
	private Long cuCounterid;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "createdDate", nullable = false)
	private Date creationDate;

	@Column(name = "createdBy", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "lgIpMac", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "lgIpMacUpd", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "updatedBy", nullable = true)
	private Long updatedBy;

	@Column(name = "updatedDate", nullable = true)
	private Date updatedDate;

	@Column(name = "H_STATUS", length = 100, nullable = true)
	private String hSTATUS;

	@Column(name = "cs_status", nullable = false)
	private String csStatus;
	
	//119534  new column added to store the frequency for scheduler
	@Column(name = "cs_frequency_sts", nullable = false)
	private String frequencySts;

	public String getCsStatus() {
		return csStatus;
	}

	public void setCsStatus(String csStatus) {
		this.csStatus = csStatus;
	}

	public Long gethSsScheduleid() {
		return hSsScheduleid;
	}

	public static String[] getPkValues() {
		return new String[] { "CFC", "tb_counterschedule_hist", "H_cs_scheduleid" };
	}

	public void sethSsScheduleid(Long hSsScheduleid) {
		this.hSsScheduleid = hSsScheduleid;
	}

	public Long getCsScheduleid() {
		return csScheduleid;
	}

	public void setCsScheduleid(Long csScheduleid) {
		this.csScheduleid = csScheduleid;
	}

	public Date getCsFromTime() {
		return csFromTime;
	}

	public void setCsFromTime(Date csFromTime) {
		this.csFromTime = csFromTime;
	}

	public Date getCsToTime() {
		return csToTime;
	}

	public void setCsToTime(Date csToTime) {
		this.csToTime = csToTime;
	}

	public Long getCsUserId() {
		return csUserId;
	}

	public void setCsUserId(Long csUserId) {
		this.csUserId = csUserId;
	}

	public Long getCuCounterid() {
		return cuCounterid;
	}

	public void setCuCounterid(Long cuCounterid) {
		this.cuCounterid = cuCounterid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String gethSTATUS() {
		return hSTATUS;
	}

	public void sethSTATUS(String hSTATUS) {
		this.hSTATUS = hSTATUS;
	}

	public String getFrequencySts() {
		return frequencySts;
	}

	public void setFrequencySts(String frequencySts) {
		this.frequencySts = frequencySts;
	}

	
}
