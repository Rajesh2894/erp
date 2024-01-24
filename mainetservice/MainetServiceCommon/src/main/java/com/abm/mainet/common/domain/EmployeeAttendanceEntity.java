package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "EMPLOYEE_ATTENDANCE")
public class EmployeeAttendanceEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1135577683676353036L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "EMP_ATTND_ID", unique = true, nullable = false)
	private Long empAttndId;
	@Column(name = "EMP_ID")
	private Long empId;
	@Column(name = "ATTND_DATE")
	private Date atendanceDate;
	@Column(name = "ATTND_CHK_IN_TIME")
	private String checkInTime;
	@Column(name = "ATTND_CHK_OUT_TIME")
	private String checkOutTime;
	@Column(name = "ATTND_TYPE")
	private String attndnceType;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	@Column(name = "ORGID")
	private Long orgId;
	@Column(name = "CREATED_BY")
	private Long createdBy;
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	@Column(name = "LG_IP_MAC")
	private String lgIpMac;
	@Column(name = "LG_IP_MAC_UPD")
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
	public String[] getPkValues() {
        return new String[] { "COM", "EMPLOYEE_ATTENDANCE", "EMP_ATTND_ID" };
    }
}
