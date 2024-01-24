package com.abm.mainet.property.dto;

public class PropertyDetailRequestDTO {
	
	private static final long serialVersionUID = -6599341518218151848L;
	
	private String mobileNo;

	private String zone;

	private String ward;

	private String mohalla;

	private String houseNo;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}
	
	
}
