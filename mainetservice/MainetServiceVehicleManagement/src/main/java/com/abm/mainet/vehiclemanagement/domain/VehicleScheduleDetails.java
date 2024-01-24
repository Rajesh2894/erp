package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_vehicle_scheddet database table.
 * 
 * @author Niraj.Kumar
 *
 *         Created Date : 28-02-2020
 */
@Entity
@Table(name = "TB_VM_VEHICLE_SCHEDDET")
public class VehicleScheduleDetails implements Serializable {

	private static final long serialVersionUID = 6350813471941136890L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "VESD_ID", unique = true, nullable = false)
	private Long vesdId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Column(nullable = false)
	private Long orgid;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VES_ENDTIME", nullable = false)
	private Date vesEndtime;

	@Column(name = "VES_MONTH")
	private int vesMonth;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VES_STARTIME", nullable = false)
	private Date vesStartime;

	@Column(name = "VES_WEEKDAY")
	private String vesWeekday;

//	@Column(name = "OCCUP_ID", nullable = false)
//	private Long occEmpName;
	@Column(name = "OCCUP_ID", nullable = false)
	private String occEmpName;

	@Column(name = "department", nullable = false)
	private Long department;
	
	@Column(name = "SHIFT_ID", nullable = false)
	private Long cpdShiftId;

	@Column(name = "VES_EMPID", nullable = false)
	private String empId;

	@Temporal(TemporalType.DATE)
	@Column(name = "VES_SCHEDULEDT", nullable = false)
	private Date veScheduledate;

	// bi-directional many-to-one association to TbSwVehicleScheduling
	@ManyToOne
	@JoinColumn(name = "VEHS_ID", nullable = false)
	private VehicleScheduleData tbSwVehicleScheduling;
	
	@Column(name = "DELETE_FLAG", length = 1)
	private String isDeleted;

	public String getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}



	public VehicleScheduleDetails() {
	}

	
	
	public Long getCpdShiftId() {
		return cpdShiftId;
	}



	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
	}



	public Long getVesdId() {
		return this.vesdId;
	}

	public void setVesdId(Long vesdId) {
		this.vesdId = vesdId;
	}

	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String getLgIpMac() {
		return this.lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return this.lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getVesEndtime() {
		return this.vesEndtime;
	}

//	public Long getOccEmpName() {
//		return occEmpName;
//	}
//
//	public void setOccEmpName(Long occEmpName) {
//		this.occEmpName = occEmpName;
//	}

	public String getOccEmpName() {
		return occEmpName;
	}

	public void setOccEmpName(String occEmpName) {
		this.occEmpName = occEmpName;
	}

	public void setVesEndtime(Date vesEndtime) {
		this.vesEndtime = vesEndtime;
	}

	public int getVesMonth() {
		return this.vesMonth;
	}

	public void setVesMonth(int vesMonth) {
		this.vesMonth = vesMonth;
	}

	public Date getVesStartime() {
		return this.vesStartime;
	}

	public void setVesStartime(Date vesStartime) {
		this.vesStartime = vesStartime;
	}

	public String getVesWeekday() {
		return vesWeekday;
	}

	public void setVesWeekday(String vesWeekday) {
		this.vesWeekday = vesWeekday;
	}

	public VehicleScheduleData getTbSwVehicleScheduling() {
		return this.tbSwVehicleScheduling;
	}

	public void setTbSwVehicleScheduling(VehicleScheduleData tbSwVehicleScheduling) {
		this.tbSwVehicleScheduling = tbSwVehicleScheduling;
	}

	public String[] getPkValues() {

		return new String[] { "VM", "TB_VM_VEHICLE_SCHEDDET", "VESD_ID" };
	}

	public Date getVeScheduledate() {
		return veScheduledate;
	}

	public void setVeScheduledate(Date veScheduledate) {
		this.veScheduledate = veScheduledate;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

}