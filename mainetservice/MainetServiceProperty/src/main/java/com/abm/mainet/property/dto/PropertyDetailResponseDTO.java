package com.abm.mainet.property.dto;

public class PropertyDetailResponseDTO {
	
	private static final long serialVersionUID = -6599341518218151848L;

	private String propertyNo;

	private String oldPropertyNo;

	private String mobileNo;

	private String ownerName;

	private String guardianName;

	private String zone;

	private String ward;

	private String mohalla;

	private String address;

	private String houseNo;

	private int status;

	private String reason;

	public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public String getOldPropertyNo() {
		return oldPropertyNo;
	}

	public void setOldPropertyNo(String oldPropertyNo) {
		this.oldPropertyNo = oldPropertyNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getMohalla() {
		return mohalla;
	}

	public void setMohalla(String mohalla) {
		this.mohalla = mohalla;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
