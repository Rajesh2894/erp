package com.abm.mainet.care.dto;

import java.io.Serializable;

public class SWMComplaintDTO implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	 private String name;
	 private String email;
	 private String address;
	 private String phoneNo;
	 private String latitude;
	 private String longitude;
	 private String complaintId;
	 private String description;
	 private Long incidentSubTypeId;
	 private String incidentSubType;
	 
	 private SWMComplaintFilesDTO files;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getIncidentSubTypeId() {
		return incidentSubTypeId;
	}

	public void setIncidentSubTypeId(Long incidentSubTypeId) {
		this.incidentSubTypeId = incidentSubTypeId;
	}

	public String getIncidentSubType() {
		return incidentSubType;
	}

	public void setIncidentSubType(String incidentSubType) {
		this.incidentSubType = incidentSubType;
	}

	public SWMComplaintFilesDTO getFiles() {
		return files;
	}

	public void setFiles(SWMComplaintFilesDTO files) {
		this.files = files;
	}
	 
}
