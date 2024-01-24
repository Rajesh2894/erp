package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceMast;

/**
 * @author Lalit.Prusti
 *
 *         Created Date : 06-Jun-2018
 */
public class VehicleScheduleDTO implements Serializable {

	private static final long serialVersionUID = 8949778885532182788L;

	private Long vesId;

	private Long createdBy;

	private Date createdDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long orgid;

	private Long updatedBy;

	private Date updatedDate;

	private Long veId;

	private String veDesc;

	private String veRegnNo;

	private Long veVetype;

	private Date vesFromdt;

	private String vesReocc;

	private Date vesTodt;

	private String vehicleTypeMar;

	private String roadId;

	private String roadName;

	private String roadNameReg;

	private String vehicleTypeMar1;

	private String vehStartTym;

	private String vehEndTym;

	private Date vehStartTime;

	private String vesWeekday;

	private Date veScheduledate;

	private String latitude;

	private String longitude;

	private List<Date> sheduleDate;

	private String vehicleScheduledate;

	private Long beatId;

//	private Long occEmpName;
	private String occEmpName;

	private String beatNo;

	private String beatName;

	private String beatNoAndbeatName;

	private String empId;

	private String fromDate;

	private String toDate;

	private Long monthNo;

	private String empName;

	private Long collectionType;

	private String colleType;

	private Long empCode;

	private String flagMsg;

	private String monthName;
	
	private String isDeleted;

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	private List<String> empNameList;

	private List<Long> empUIdList;

	private Set<String> employeeNameList;

	private Set<String> employeeUIdList;

	private String drainingempName;

	private String dariningvehStartTym;

	private String dariningvehEndTym;

	private String sweepingemp;

	private String sweepingintime;

	private String drainingemp;

	private String drainingintime;

	private String collectionCode;

	private String veNo;

	private Long department;

	private Long cpdShiftId;

	private List<VehicleScheduleDetDTO> tbSwVehicleScheddets;

	private List<VehicleScheduleDTO> vehicleScheduleList;

	private List<VehicleScheduleDTO> vehicleSwepingDtoList;

	private List<VehicleScheduleDTO> vehicleDrainingDtoList;

	public VehicleScheduleDTO() {
	}

	public Long getCpdShiftId() {
		return cpdShiftId;
	}

	public void setCpdShiftId(Long cpdShiftId) {
		this.cpdShiftId = cpdShiftId;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
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

	public Long getBeatId() {
		return beatId;
	}

	public void setBeatId(Long beatId) {
		this.beatId = beatId;
	}

	public Long getVesId() {
		return this.vesId;
	}

	public void setVesId(Long vesId) {
		this.vesId = vesId;
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

	public Long getVeId() {
		return this.veId;
	}

	public void setVeId(Long veId) {
		this.veId = veId;
	}

	public Long getVeVetype() {
		return this.veVetype;
	}

	public void setVeVetype(Long veVetype) {
		this.veVetype = veVetype;
	}

	public Date getVesFromdt() {
		return this.vesFromdt;
	}

	public void setVesFromdt(Date vesFromdt) {
		this.vesFromdt = vesFromdt;
	}

	public String getVesReocc() {
		return this.vesReocc;
	}

	public void setVesReocc(String vesReocc) {
		this.vesReocc = vesReocc;
	}

	public Date getVesTodt() {
		return this.vesTodt;
	}

	public void setVesTodt(Date vesTodt) {
		this.vesTodt = vesTodt;
	}

	public List<VehicleScheduleDetDTO> getTbSwVehicleScheddets() {
		return this.tbSwVehicleScheddets;
	}

	public void setTbSwVehicleScheddets(List<VehicleScheduleDetDTO> tbSwVehicleScheddets) {
		this.tbSwVehicleScheddets = tbSwVehicleScheddets;
	}

	public VehicleScheduleDetDTO addTbSwVehicleScheddet(VehicleScheduleDetDTO tbSwVehicleScheddet) {
		getTbSwVehicleScheddets().add(tbSwVehicleScheddet);
		tbSwVehicleScheddet.setTbSwVehicleScheduling(this);

		return tbSwVehicleScheddet;
	}

	public VehicleScheduleDetDTO removeTbSwVehicleScheddet(VehicleScheduleDetDTO tbSwVehicleScheddet) {
		getTbSwVehicleScheddets().remove(tbSwVehicleScheddet);
		tbSwVehicleScheddet.setTbSwVehicleScheduling(null);

		return tbSwVehicleScheddet;
	}

	public String getVeDesc() {
		return veDesc;
	}

	public void setVeDesc(String veDesc) {
		this.veDesc = veDesc;
	}

	public String getVeRegnNo() {
		return veRegnNo;
	}

	public void setVeRegnNo(String veRegnNo) {
		this.veRegnNo = veRegnNo;
	}

	public List<VehicleMaintenanceMast> searchVehicleScheduleByVehicleTypeAndVehicleNo(Long veVetype2, Long vesId2,
			Long orgid2) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVehicleTypeMar() {
		return vehicleTypeMar;
	}

	public void setVehicleTypeMar(String vehicleTypeMar) {
		this.vehicleTypeMar = vehicleTypeMar;
	}

	public String getRoadId() {
		return roadId;
	}

	public void setRoadId(String roadId) {
		this.roadId = roadId;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getRoadNameReg() {
		return roadNameReg;
	}

	public void setRoadNameReg(String roadNameReg) {
		this.roadNameReg = roadNameReg;
	}

	public String getVehicleTypeMar1() {
		return vehicleTypeMar1;
	}

	public void setVehicleTypeMar1(String vehicleTypeMar1) {
		this.vehicleTypeMar1 = vehicleTypeMar1;
	}

	public String getVehStartTym() {
		return vehStartTym;
	}

	public void setVehStartTym(String vehStartTym) {
		this.vehStartTym = vehStartTym;
	}

	public String getVehEndTym() {
		return vehEndTym;
	}

	public void setVehEndTym(String vehEndTym) {
		this.vehEndTym = vehEndTym;
	}

	public List<VehicleScheduleDTO> getVehicleScheduleList() {
		return vehicleScheduleList;
	}

	public void setVehicleScheduleList(List<VehicleScheduleDTO> vehicleScheduleList) {
		this.vehicleScheduleList = vehicleScheduleList;
	}

	public Date getVehStartTime() {
		return vehStartTime;
	}

	public void setVehStartTime(Date vehStartTime) {
		this.vehStartTime = vehStartTime;
	}

	public String getVesWeekday() {
		return vesWeekday;
	}

	public void setVesWeekday(String vesWeekday) {
		this.vesWeekday = vesWeekday;
	}

	public List<Date> getSheduleDate() {
		return sheduleDate;
	}

	public void setSheduleDate(List<Date> sheduleDate) {
		this.sheduleDate = sheduleDate;
	}

	public Date getVeScheduledate() {
		return veScheduledate;
	}

	public void setVeScheduledate(Date veScheduledate) {
		this.veScheduledate = veScheduledate;
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

	public String getVehicleScheduledate() {
		return vehicleScheduledate;
	}

	public void setVehicleScheduledate(String vehicleScheduledate) {
		this.vehicleScheduledate = vehicleScheduledate;
	}

	public String getBeatNo() {
		return beatNo;
	}

	public void setBeatNo(String beatNo) {
		this.beatNo = beatNo;
	}

	public String getBeatName() {
		return beatName;
	}

	public void setBeatName(String beatName) {
		this.beatName = beatName;
	}

	public String getBeatNoAndbeatName() {
		return beatNoAndbeatName;
	}

	public void setBeatNoAndbeatName(String beatNoAndbeatName) {
		this.beatNoAndbeatName = beatNoAndbeatName;
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

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Long getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(Long collectionType) {
		this.collectionType = collectionType;
	}

	public Long getMonthNo() {
		return monthNo;
	}

	public void setMonthNo(Long monthNo) {
		this.monthNo = monthNo;
	}

	public Long getEmpCode() {
		return empCode;
	}

	public void setEmpCode(Long empCode) {
		this.empCode = empCode;
	}

	public List<Long> getEmpUIdList() {
		return empUIdList;
	}

	public void setEmpUIdList(List<Long> empUIdList) {
		this.empUIdList = empUIdList;
	}

	public String getFlagMsg() {
		return flagMsg;
	}

	public void setFlagMsg(String flagMsg) {
		this.flagMsg = flagMsg;
	}

	public Set<String> getEmployeeNameList() {
		return employeeNameList;
	}

	public void setEmployeeNameList(Set<String> employeeNameList) {
		this.employeeNameList = employeeNameList;
	}

	public Set<String> getEmployeeUIdList() {
		return employeeUIdList;
	}

	public void setEmployeeUIdList(Set<String> employeeUIdList) {
		this.employeeUIdList = employeeUIdList;
	}

	public String getColleType() {
		return colleType;
	}

	public void setColleType(String colleType) {
		this.colleType = colleType;
	}

	public List<VehicleScheduleDTO> getVehicleSwepingDtoList() {
		return vehicleSwepingDtoList;
	}

	public void setVehicleSwepingDtoList(List<VehicleScheduleDTO> vehicleSwepingDtoList) {
		this.vehicleSwepingDtoList = vehicleSwepingDtoList;
	}

	public List<VehicleScheduleDTO> getVehicleDrainingDtoList() {
		return vehicleDrainingDtoList;
	}

	public void setVehicleDrainingDtoList(List<VehicleScheduleDTO> vehicleDrainingDtoList) {
		this.vehicleDrainingDtoList = vehicleDrainingDtoList;
	}

	public String getDrainingempName() {
		return drainingempName;
	}

	public void setDrainingempName(String drainingempName) {
		this.drainingempName = drainingempName;
	}

	public String getDariningvehStartTym() {
		return dariningvehStartTym;
	}

	public void setDariningvehStartTym(String dariningvehStartTym) {
		this.dariningvehStartTym = dariningvehStartTym;
	}

	public String getDariningvehEndTym() {
		return dariningvehEndTym;
	}

	public void setDariningvehEndTym(String dariningvehEndTym) {
		this.dariningvehEndTym = dariningvehEndTym;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getSweepingemp() {
		return sweepingemp;
	}

	public void setSweepingemp(String sweepingemp) {
		this.sweepingemp = sweepingemp;
	}

	public String getSweepingintime() {
		return sweepingintime;
	}

	public void setSweepingintime(String sweepingintime) {
		this.sweepingintime = sweepingintime;
	}

	public String getDrainingemp() {
		return drainingemp;
	}

	public void setDrainingemp(String drainingemp) {
		this.drainingemp = drainingemp;
	}

	public String getDrainingintime() {
		return drainingintime;
	}

	public void setDrainingintime(String drainingintime) {
		this.drainingintime = drainingintime;
	}

	public String getCollectionCode() {
		return collectionCode;
	}

	public void setCollectionCode(String collectionCode) {
		this.collectionCode = collectionCode;
	}

	public String getVeNo() {
		return veNo;
	}

	public void setVeNo(String veNo) {
		this.veNo = veNo;
	}

}