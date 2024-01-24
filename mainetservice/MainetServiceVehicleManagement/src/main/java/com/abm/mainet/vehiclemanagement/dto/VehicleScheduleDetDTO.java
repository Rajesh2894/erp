package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abm.mainet.common.domain.Employee;

/**
 * 
 * @author Niraj.Kumar
 *
 *         Created Date : 03-March-2020
 */
public class VehicleScheduleDetDTO implements Serializable {

	private static final long serialVersionUID = 4414452214849222685L;

	private Long vesdId;

	private Long createdBy;

	private Date createdDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long orgid;

	private Long updatedBy;

	private Date updatedDate;

	private int vesMonth;

	private Date vesEndtime;

	private Date vesStartime;

	private String endtime;

	private String startime;

	private String vesWeekday;

	private Long beatId;

	//private Long occEmpName;
	private String occEmpName;

	private Long veNo;

	private Long vesCollType;

	private Date veScheduledate;

	private String sheduleDate;

	private String empId;

	private Long department;
    private Long cpdShiftId;
    
    private String deptDesc;
    
    private String isDeleted;
    
	public String getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}



	private List<String> empNameList;

	List<SLRMEmployeeMasterDTO> employeeList = new ArrayList<>();
	List<Employee> emplList = new ArrayList<>();


	@JsonIgnore
	private VehicleScheduleDTO tbSwVehicleScheduling;

	public VehicleScheduleDetDTO() {
	}

	
	
	public Long getCpdShiftId() {
		return cpdShiftId;
	}



	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
	}

	public String getOccEmpName() {
		return occEmpName;
	}

	public void setOccEmpName(String occEmpName) {
		this.occEmpName = occEmpName;
	}

//	public Long getOccEmpName() {
//		return occEmpName;
//	}
//
//	public void setOccEmpName(Long occEmpName) {
//		this.occEmpName = occEmpName;
//	}



	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
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

	public Long getBeatId() {
		return beatId;
	}

	public void setBeatId(Long beatId) {
		this.beatId = beatId;
	}

	public Long getVesCollType() {
		return vesCollType;
	}

	public void setVesCollType(Long vesCollType) {
		this.vesCollType = vesCollType;
	}

	public VehicleScheduleDTO getTbSwVehicleScheduling() {
		return this.tbSwVehicleScheduling;
	}

	public void setTbSwVehicleScheduling(VehicleScheduleDTO tbSwVehicleScheduling) {
		this.tbSwVehicleScheduling = tbSwVehicleScheduling;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStartime() {
		return startime;
	}

	public void setStartime(String startime) {
		this.startime = startime;
	}

	public Date getVeScheduledate() {
		return veScheduledate;
	}

	public void setVeScheduledate(Date veScheduledate) {
		this.veScheduledate = veScheduledate;
	}

	public String getSheduleDate() {
		return sheduleDate;
	}

	public void setSheduleDate(String sheduleDate) {
		this.sheduleDate = sheduleDate;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public List<String> getEmpNameList() {
		return empNameList;
	}

	public void setEmpNameList(List<String> empNameList) {
		this.empNameList = empNameList;
	}

	public List<SLRMEmployeeMasterDTO> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<SLRMEmployeeMasterDTO> employeeList) {
		this.employeeList = employeeList;
	}

	public Long getVeNo() {
		return veNo;
	}

	public void setVeNo(Long veNo) {
		this.veNo = veNo;
	}

    


	public List<Employee> getEmplList() {
		return emplList;
	}



	public void setEmplList(List<Employee> emplList) {
		this.emplList = emplList;
	}



	public String getDeptDesc() {
		return deptDesc;
	}



	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

}