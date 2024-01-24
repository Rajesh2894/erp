package com.abm.mainet.securitymanagement.dto;

import java.io.Serializable;

public class LocationDetailsOfStaffDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contStaffName;
	private Long  empTypeId;
	private Long vendorId;
	private Long locId;
	private Long cpdShiftId;
	

	public String getContStaffName() {
		return contStaffName;
	}
	public void setContStaffName(String contStaffName) {
		this.contStaffName = contStaffName;
	}
	public Long getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(Long empTypeId) {
		this.empTypeId = empTypeId;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public Long getLocId() {
		return locId;
	}
	public void setLocId(Long locId) {
		this.locId = locId;
	}
	public Long getCpdShiftId() {
		return cpdShiftId;
	}
	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
