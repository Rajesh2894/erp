package com.abm.mainet.disastermanagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the Occurance Book entity.
 */

public class DisasterOccuranceBookDTO implements Serializable {

	private static final long serialVersionUID = 4855166956304607404L;

	private String enterComplaintNumber;
	private Long occId;
	private Date fromDate;
	private Date toDate;
	private Date date;
	private String time;
	private String incidentDesc;
	private String operatorRemarks;
	private String complaintStatus;
	private Long orgid;
	private String fireStation;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long complaintType1;
	private Long complaintType2;
	private Long complaintType3;
	private Long complaintType4;
	private Long complaintType5;

	private String fsDesc;
	public String getEnterComplaintNumber() {
		return enterComplaintNumber;
	}

	public void setEnterComplaintNumber(String enterComplaintNumber) {
		this.enterComplaintNumber = enterComplaintNumber;
	}

	public Long getOccId() {
		return occId;
	}

	public void setOccId(Long occId) {
		this.occId = occId;
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

	public String getIncidentDesc() {
		return incidentDesc;
	}

	public void setIncidentDesc(String incidentDesc) {
		this.incidentDesc = incidentDesc;
	}

	public String getOperatorRemarks() {
		return operatorRemarks;
	}

	public void setOperatorRemarks(String operatorRemarks) {
		this.operatorRemarks = operatorRemarks;
	}

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getFireStation() {
		return fireStation;
	}

	public void setFireStation(String fireStation) {
		this.fireStation = fireStation;
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

	public String getFsDesc() {
		return fsDesc;
	}

	public void setFsDesc(String fsDesc) {
		this.fsDesc = fsDesc;
	}

	


	public Long getComplaintType1() {
		return complaintType1;
	}

	public void setComplaintType1(Long complaintType1) {
		this.complaintType1 = complaintType1;
	}

	public Long getComplaintType2() {
		return complaintType2;
	}

	public void setComplaintType2(Long complaintType2) {
		this.complaintType2 = complaintType2;
	}

	public Long getComplaintType3() {
		return complaintType3;
	}

	public void setComplaintType3(Long complaintType3) {
		this.complaintType3 = complaintType3;
	}

	public Long getComplaintType4() {
		return complaintType4;
	}

	public void setComplaintType4(Long complaintType4) {
		this.complaintType4 = complaintType4;
	}

	public Long getComplaintType5() {
		return complaintType5;
	}

	public void setComplaintType5(Long complaintType5) {
		this.complaintType5 = complaintType5;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
