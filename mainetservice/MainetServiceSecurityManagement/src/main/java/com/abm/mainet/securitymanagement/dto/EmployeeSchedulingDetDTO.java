package com.abm.mainet.securitymanagement.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EmployeeSchedulingDetDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long emplScdlDetId;
	private String contStaffIdNo;
	private Long cpdShiftId;
	private Long createdBy;
	private Date createdDate;
	private Long empTypeId;
	private Long emplScdlId;
	private Date endtimeShift;
	private Long dayPrefixId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long locId;
	private Long orgid;
	private Date shiftDate;
	private String shiftDay;
	private Date startimeShift;
	private Long updatedBy;
	private Date updatedDate;
	private List<Date> sheduleDate;
	private List<EmployeeSchedulingDetDTO> detDto;
	private String attStatus;
	private Long otShiftId;
	private String otHour;
	private String remarks;
	
	public List<EmployeeSchedulingDetDTO> getDetDto() {
		return detDto;
	}

	public void setDetDto(List<EmployeeSchedulingDetDTO> detDto) {
		this.detDto = detDto;
	}

	public Long getDayPrefixId() {
		return dayPrefixId;
	}

	public void setDayPrefixId(Long dayPrefixId) {
		this.dayPrefixId = dayPrefixId;
	}

	public List<Date> getSheduleDate() {
		return sheduleDate;
	}

	public void setSheduleDate(List<Date> sheduleDate) {
		this.sheduleDate = sheduleDate;
	}

	public Long getEmplScdlDetId() {
		return emplScdlDetId;
	}

	public void setEmplScdlDetId(Long emplScdlDetId) {
		this.emplScdlDetId = emplScdlDetId;
	}

	public String getContStaffIdNo() {
		return contStaffIdNo;
	}

	public void setContStaffIdNo(String contStaffIdNo) {
		this.contStaffIdNo = contStaffIdNo;
	}

	public Long getCpdShiftId() {
		return cpdShiftId;
	}

	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
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

	public Long getEmpTypeId() {
		return empTypeId;
	}

	public void setEmpTypeId(Long empTypeId) {
		this.empTypeId = empTypeId;
	}

	public Long getEmplScdlId() {
		return emplScdlId;
	}

	public void setEmplScdlId(Long emplScdlId) {
		this.emplScdlId = emplScdlId;
	}

	public Date getEndtimeShift() {
		return endtimeShift;
	}

	public void setEndtimeShift(Date endtimeShift) {
		this.endtimeShift = endtimeShift;
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

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Date getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(Date shiftDate) {
		this.shiftDate = shiftDate;
	}

	public String getShiftDay() {
		return shiftDay;
	}

	public void setShiftDay(String shiftDay) {
		this.shiftDay = shiftDay;
	}

	public Date getStartimeShift() {
		return startimeShift;
	}

	public void setStartimeShift(Date startimeShift) {
		this.startimeShift = startimeShift;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAttStatus() {
		return attStatus;
	}

	public void setAttStatus(String attStatus) {
		this.attStatus = attStatus;
	}

	public Long getOtShiftId() {
		return otShiftId;
	}

	public void setOtShiftId(Long otShiftId) {
		this.otShiftId = otShiftId;
	}

	public String getOtHour() {
		return otHour;
	}

	public void setOtHour(String otHour) {
		this.otHour = otHour;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
