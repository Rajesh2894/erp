package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

public class EmployeeAttendanceDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long empAttndId;
	private Long empId;
	private Date atendanceDate;
	private String checkInTime;
	private String checkOutTime;
	private String attndnceType;
	private Date createdDate;
	private Date updatedDate;
	private Long orgId;
	private Long createdBy;
	private Long updatedBy;
	private String lgIpMac;
	private String lgIpMacUpd;

	public Long getEmpAttndId() {
		return empAttndId;
	}

	public void setEmpAttndId(Long empAttndId) {
		this.empAttndId = empAttndId;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Date getAtendanceDate() {
		return atendanceDate;
	}

	public void setAtendanceDate(Date atendanceDate) {
		this.atendanceDate = atendanceDate;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getAttndnceType() {
		return attndnceType;
	}

	public void setAttndnceType(String attndnceType) {
		this.attndnceType = attndnceType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

}
