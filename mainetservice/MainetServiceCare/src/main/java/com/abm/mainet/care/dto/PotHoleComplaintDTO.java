package com.abm.mainet.care.dto;

import java.io.Serializable;

public class PotHoleComplaintDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String additionalDetails;
	private String latitude;
	private String longitude;
	private String phone;
	private Long eventSubTypeId;
	private String complaintId;
	private String nameContact;
	private String location;
	private String emailId;
	private String tenantCode;
	private SWMComplaintFilesDTO files;

	public String getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getEventSubTypeId() {
		return eventSubTypeId;
	}

	public void setEventSubTypeId(Long eventSubTypeId) {
		this.eventSubTypeId = eventSubTypeId;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getNameContact() {
		return nameContact;
	}

	public void setNameContact(String nameContact) {
		this.nameContact = nameContact;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public SWMComplaintFilesDTO getFiles() {
		return files;
	}

	public void setFiles(SWMComplaintFilesDTO files) {
		this.files = files;
	}

}
