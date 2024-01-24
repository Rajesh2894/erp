package com.abm.mainet.securitymanagement.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DailyIncidentRegisterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7464384213994094748L;
	private Long incidentId;
	private Date fromDate;
	private Date toDate;
	private Date date;
	private String time;
	private String remarks;
	private String nameVisitingId;
	private List<Long> visitingOfficerIds;
	private String OfficerDesc;
	private String nameVisitingOffJoin;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public DailyIncidentRegisterDTO() {
		super();
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNameVisitingId() {
		return nameVisitingId;
	}

	public void setNameVisitingId(String nameVisitingId) {
		this.nameVisitingId = nameVisitingId;
	}

	public String getOfficerDesc() {
		return OfficerDesc;
	}

	public void setOfficerDesc(String officerDesc) {
		OfficerDesc = officerDesc;
	}

	public String getNameVisitingOffJoin() {
		return nameVisitingOffJoin;
	}

	public void setNameVisitingOffJoin(String nameVisitingOffJoin) {
		this.nameVisitingOffJoin = nameVisitingOffJoin;
	}

	public List<Long> getVisitingOfficerIds() {
		return visitingOfficerIds;
	}

	public void setVisitingOfficerIds(List<Long> visitingOfficerIds) {
		this.visitingOfficerIds = visitingOfficerIds;
	}

}
