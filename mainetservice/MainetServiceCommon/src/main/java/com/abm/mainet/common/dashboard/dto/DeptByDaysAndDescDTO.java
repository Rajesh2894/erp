package com.abm.mainet.common.dashboard.dto;

public class DeptByDaysAndDescDTO {

	private int id;
	private String applId;
	private String refMode;
	private String complaintNo;
	private String createdBy;
	private String dateOfRequest;
	private String complaintDesc;
	private String cpdDesc;
	private String cpdDescMar;
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplId() {
		return applId;
	}

	public void setApplId(String applId) {
		this.applId = applId;
	}

	public String getRefMode() {
		return refMode;
	}

	public void setRefMode(String refMode) {
		this.refMode = refMode;
	}

	public String getComplaintNo() {
		return complaintNo;
	}

	public void setComplaintNo(String complaintNo) {
		this.complaintNo = complaintNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDateOfRequest() {
		return dateOfRequest;
	}

	public void setDateOfRequest(String dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	public String getComplaintDesc() {
		return complaintDesc;
	}

	public void setComplaintDesc(String complaintDesc) {
		this.complaintDesc = complaintDesc;
	}

	public String getCpdDesc() {
		return cpdDesc;
	}

	public void setCpdDesc(String cpdDesc) {
		this.cpdDesc = cpdDesc;
	}

	public String getCpdDescMar() {
		return cpdDescMar;
	}

	public void setCpdDescMar(String cpdDescMar) {
		this.cpdDescMar = cpdDescMar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DeptByDaysAndDescDTO [id=" + id + ", applId=" + applId + ", refMode=" + refMode + ", complaintNo="
				+ complaintNo + ", createdBy=" + createdBy + ", dateOfRequest=" + dateOfRequest + ", complaintDesc="
				+ complaintDesc + ", cpdDesc=" + cpdDesc + ", cpdDescMar=" + cpdDescMar + ", status=" + status + "]";
	}

}
