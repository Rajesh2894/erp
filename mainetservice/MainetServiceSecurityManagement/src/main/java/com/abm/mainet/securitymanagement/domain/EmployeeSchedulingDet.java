package com.abm.mainet.securitymanagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_employee_scheduling_det database table.
 * 
 */
@Entity
@Table(name = "tb_employee_scheduling_det")
public class EmployeeSchedulingDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "EMPL_SCDL_DET_ID")
	private Long emplScdlDetId;

    // bi-directional many-to-one association to TbSwVehicleScheduling
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPL_SCDL_ID")
    private EmployeeScheduling employeeScheduling;
	
	@Column(name = "CONT_STAFF_ID_NO")
	private String contStaffIdNo;

	@Column(name = "CPD_SHIFT_ID")
	private Long cpdShiftId;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "EMP_TYPE_ID")
	private Long empTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDTIME_SHIFT")
	private Date endtimeShift;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name = "LOC_ID")
	private Long locId;

	private Long orgid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SHIFT_DATE")
	private Date shiftDate;

	@Column(name = "SHIFT_DAY")
	private String shiftDay;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTIME_SHIFT")
	private Date startimeShift;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "ATT_STATUS")
	private String attStatus;
	
	@Column(name = "OT_CPD_SHIFT_ID")
	private Long otShiftId;
	
	@Column(name = "OT_HOUR")
	private String otHour;
	
	@Column(name = "REMARKS", length = 500)
	private String remarks;

	public EmployeeScheduling getEmployeeScheduling() {
		return employeeScheduling;
	}

	public void setEmployeeScheduling(EmployeeScheduling employeeScheduling) {
		this.employeeScheduling = employeeScheduling;
	}

	public EmployeeSchedulingDet() {
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

	public String[] getPkValues() {
		return new String[] { "ESD", "tb_employee_scheduling_det", "EMPL_SCDL_DET_ID" };
	}
}
