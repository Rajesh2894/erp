package com.abm.mainet.firemanagement.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Time;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * The persistent class for the tb_fm_complain_closure database table.
 * 
 */
@Entity
@Table(name="tb_fm_complain_closure")
@NamedQuery(name="TbFmComplainClosure.findAll", query="SELECT t FROM TbFmComplainClosure t")
public class TbFmComplainClosure implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2002069207884389612L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "closure_id", unique = true, nullable = false)
	private Long closureId;
	
	/*
	@Id
	@Column(name="closure_id")
	private Long closureId;
	*/

	@Column(name="assign_vehicle")
	private String assignVehicle;

	@Temporal(TemporalType.DATE)
	@Column(name="call_attend_date")
	private Date callAttendDate;

	@Column(name="call_attend_employee")
	private String callAttendEmployee;

	@Column(name="call_attend_time")
	private String callAttendTime;

	@Temporal(TemporalType.DATE)
	@Column(name="call_closed_date")
	private Date callClosedDate;

	@Column(name="call_closed_time")
	private Time callClosedTime;

	@Column(name="caller_add")
	private String callerAdd;

	@Column(name="caller_area")
	private String callerArea;

	@Column(name="caller_mobile_no")
	private String callerMobileNo;

	@Column(name="caller_name")
	private String callerName;

	@Column(name="closer_remarks")
	private String closerRemarks;

	@Column(name="cmplnt_id")
	private Long cmplntId;

	@Column(name="cmplnt_no")
	private String cmplntNo;

	@Column(name="complaint_fee")
	private double complaintFee;

	@Column(name="complaint_status")
	private String complaintStatus;

	@Column(name="cpd_call_type")
	private Long cpdCallType;

	@Column(name="cpd_fire_station")
	private String cpdFireStation;

	@Column(name="cpd_nature_of_call")
	private Long cpdNatureOfCall;

	@Column(name="cpd_reason_of_fire")
	private Long cpdReasonOfFire;

	@Column(name="created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name="duty_officer")
	private Long dutyOfficer;

	@Column(name="fire_stations_attend_call")
	private String fireStationsAttendCall;

	@Column(name="hod_remarks")
	private String hodRemarks;

	@Column(name="incident_desc")
	private String incidentDesc;

	@Column(name="incident_location")
	private String incidentLocation;

	@Column(name="lg_ip_mac")
	private String lgIpMac;

	@Column(name="lg_ip_mac_upd")
	private String lgIpMacUpd;

	@Column(name="name_of_occupier")
	private String nameOfOccupier;

	@Column(name="name_of_officer")
	private String nameOfOfficer;

	@Column(name="name_of_owner")
	private String nameOfOwner;

	@Column(name="no_of_death_child")
	private Long noOfDeathChild;

	@Column(name="no_of_death_female")
	private Long noOfDeathFemale;

	@Column(name="no_of_death_male")
	private Long noOfDeathMale;

	@Column(name="no_of_dept_death_female")
	private Long noOfDeptDeathFemale;

	@Column(name="no_of_dept_death_male")
	private Long noOfDeptDeathMale;

	@Column(name="no_of_dept_injury_female")
	private Long noOfDeptInjuryFemale;

	@Column(name="no_of_dept_injury_male")
	private Long noOfDeptInjuryMale;

	@Column(name="no_of_injury_child")
	private Long noOfInjuryChild;

	@Column(name="no_of_injury_female")
	private Long noOfInjuryFemale;

	@Column(name="no_of_injury_male")
	private Long noOfInjuryMale;

	private Long operator;

	@Column(name="operator_remarks")
	private String operatorRemarks;

	private Long orgid;

	@Column(name="other_details")
	private String otherDetails;

	@Column(name="reason_for_delay")
	private String reasonForDelay;

	@Column(name="rescued_with_fire_dept_female")
	private Long rescuedWithFireDeptFemale;

	@Column(name="rescued_with_fire_dept_male")
	private Long rescuedWithFireDeptMale;

	@Column(name="rescued_with_fire_dept_veheicle_female")
	private Long rescuedWithFireDeptVeheicleFemale;

	@Column(name="rescued_with_fire_dept_veheicle_male")
	private Long rescuedWithFireDeptVeheicleMale;

	@Column(name="rescued_without_fire_dept_female")
	private Long rescuedWithoutFireDeptFemale;

	@Column(name="rescued_without_fire_dept_male")
	private Long rescuedWithoutFireDeptMale;

	@Column(name="tb_fm_complain_registercol")
	private String tbFmComplainRegistercol;

	/* private Time time; */
	
	@Column(nullable = false)
	private String time;

	@Column(name="updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_date")
	private Date updatedDate;

	@Column(name="vehicle_in_time")
	private String vehicleInTime;

	@Column(name="vehicle_out_time")
	private String vehicleOutTime;

	@Column(name="WF_STATUS")
	private String wfStatus;
	
	@Column(name="PROPERTY_SAVED")
	private BigDecimal propertySaved;
	
	@Column(name="PROPERTY_LOST")
	private BigDecimal propertyLost;

	public TbFmComplainClosure() {
	}

	public Long getClosureId() {
		return closureId;
	}

	public void setClosureId(Long closureId) {
		this.closureId = closureId;
	}

	public Date getCallAttendDate() {
		return this.callAttendDate;
	}

	public void setCallAttendDate(Date callAttendDate) {
		this.callAttendDate = callAttendDate;
	}

	public String getCallAttendEmployee() {
		return this.callAttendEmployee;
	}

	public void setCallAttendEmployee(String callAttendEmployee) {
		this.callAttendEmployee = callAttendEmployee;
	}



	public String getCallAttendTime() {
		return callAttendTime;
	}

	public void setCallAttendTime(String callAttendTime) {
		this.callAttendTime = callAttendTime;
	}

	public Date getCallClosedDate() {
		return this.callClosedDate;
	}

	public void setCallClosedDate(Date callClosedDate) {
		this.callClosedDate = callClosedDate;
	}

	public Time getCallClosedTime() {
		return this.callClosedTime;
	}

	public void setCallClosedTime(Time callClosedTime) {
		this.callClosedTime = callClosedTime;
	}

	public String getCallerAdd() {
		return this.callerAdd;
	}

	public void setCallerAdd(String callerAdd) {
		this.callerAdd = callerAdd;
	}

	public String getCallerArea() {
		return this.callerArea;
	}

	public void setCallerArea(String callerArea) {
		this.callerArea = callerArea;
	}

	public String getCallerMobileNo() {
		return this.callerMobileNo;
	}

	public void setCallerMobileNo(String callerMobileNo) {
		this.callerMobileNo = callerMobileNo;
	}

	public String getCallerName() {
		return this.callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

	public String getCloserRemarks() {
		return this.closerRemarks;
	}

	public void setCloserRemarks(String closerRemarks) {
		this.closerRemarks = closerRemarks;
	}

	public String getCmplntNo() {
		return this.cmplntNo;
	}

	public void setCmplntNo(String cmplntNo) {
		this.cmplntNo = cmplntNo;
	}

	public double getComplaintFee() {
		return this.complaintFee;
	}

	public void setComplaintFee(double complaintFee) {
		this.complaintFee = complaintFee;
	}

	public String getComplaintStatus() {
		return this.complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public String getCpdFireStation() {
		return this.cpdFireStation;
	}

	public void setCpdFireStation(String cpdFireStation) {
		this.cpdFireStation = cpdFireStation;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFireStationsAttendCall() {
		return this.fireStationsAttendCall;
	}

	public void setFireStationsAttendCall(String fireStationsAttendCall) {
		this.fireStationsAttendCall = fireStationsAttendCall;
	}

	public String getHodRemarks() {
		return this.hodRemarks;
	}

	public void setHodRemarks(String hodRemarks) {
		this.hodRemarks = hodRemarks;
	}

	public String getIncidentDesc() {
		return this.incidentDesc;
	}

	public void setIncidentDesc(String incidentDesc) {
		this.incidentDesc = incidentDesc;
	}

	public String getIncidentLocation() {
		return this.incidentLocation;
	}

	public void setIncidentLocation(String incidentLocation) {
		this.incidentLocation = incidentLocation;
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

	public String getNameOfOccupier() {
		return this.nameOfOccupier;
	}

	public void setNameOfOccupier(String nameOfOccupier) {
		this.nameOfOccupier = nameOfOccupier;
	}

	public String getNameOfOfficer() {
		return this.nameOfOfficer;
	}

	public void setNameOfOfficer(String nameOfOfficer) {
		this.nameOfOfficer = nameOfOfficer;
	}

	public String getNameOfOwner() {
		return this.nameOfOwner;
	}

	public void setNameOfOwner(String nameOfOwner) {
		this.nameOfOwner = nameOfOwner;
	}

	public String getOperatorRemarks() {
		return this.operatorRemarks;
	}

	public void setOperatorRemarks(String operatorRemarks) {
		this.operatorRemarks = operatorRemarks;
	}

	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getOtherDetails() {
		return this.otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public String getReasonForDelay() {
		return this.reasonForDelay;
	}

	public void setReasonForDelay(String reasonForDelay) {
		this.reasonForDelay = reasonForDelay;
	}

	public String getTbFmComplainRegistercol() {
		return this.tbFmComplainRegistercol;
	}

	public void setTbFmComplainRegistercol(String tbFmComplainRegistercol) {
		this.tbFmComplainRegistercol = tbFmComplainRegistercol;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}



	public String getVehicleInTime() {
		return vehicleInTime;
	}

	public void setVehicleInTime(String vehicleInTime) {
		this.vehicleInTime = vehicleInTime;
	}

	public String getVehicleOutTime() {
		return vehicleOutTime;
	}

	public void setVehicleOutTime(String vehicleOutTime) {
		this.vehicleOutTime = vehicleOutTime;
	}

	public String getWfStatus() {
		return this.wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public String getAssignVehicle() {
		return assignVehicle;
	}

	public void setAssignVehicle(String assignVehicle) {
		this.assignVehicle = assignVehicle;
	}

	public Long getCmplntId() {
		return cmplntId;
	}

	public void setCmplntId(Long cmplntId) {
		this.cmplntId = cmplntId;
	}

	public Long getCpdCallType() {
		return cpdCallType;
	}

	public void setCpdCallType(Long cpdCallType) {
		this.cpdCallType = cpdCallType;
	}

	public Long getCpdNatureOfCall() {
		return cpdNatureOfCall;
	}

	public void setCpdNatureOfCall(Long cpdNatureOfCall) {
		this.cpdNatureOfCall = cpdNatureOfCall;
	}

	public Long getCpdReasonOfFire() {
		return cpdReasonOfFire;
	}

	public void setCpdReasonOfFire(Long cpdReasonOfFire) {
		this.cpdReasonOfFire = cpdReasonOfFire;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getDutyOfficer() {
		return dutyOfficer;
	}

	public void setDutyOfficer(Long dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	public Long getNoOfDeathChild() {
		return noOfDeathChild;
	}

	public void setNoOfDeathChild(Long noOfDeathChild) {
		this.noOfDeathChild = noOfDeathChild;
	}

	public Long getNoOfDeathFemale() {
		return noOfDeathFemale;
	}

	public void setNoOfDeathFemale(Long noOfDeathFemale) {
		this.noOfDeathFemale = noOfDeathFemale;
	}

	public Long getNoOfDeathMale() {
		return noOfDeathMale;
	}

	public void setNoOfDeathMale(Long noOfDeathMale) {
		this.noOfDeathMale = noOfDeathMale;
	}

	public Long getNoOfDeptDeathFemale() {
		return noOfDeptDeathFemale;
	}

	public void setNoOfDeptDeathFemale(Long noOfDeptDeathFemale) {
		this.noOfDeptDeathFemale = noOfDeptDeathFemale;
	}

	public Long getNoOfDeptDeathMale() {
		return noOfDeptDeathMale;
	}

	public void setNoOfDeptDeathMale(Long noOfDeptDeathMale) {
		this.noOfDeptDeathMale = noOfDeptDeathMale;
	}

	public Long getNoOfDeptInjuryFemale() {
		return noOfDeptInjuryFemale;
	}

	public void setNoOfDeptInjuryFemale(Long noOfDeptInjuryFemale) {
		this.noOfDeptInjuryFemale = noOfDeptInjuryFemale;
	}

	public Long getNoOfDeptInjuryMale() {
		return noOfDeptInjuryMale;
	}

	public void setNoOfDeptInjuryMale(Long noOfDeptInjuryMale) {
		this.noOfDeptInjuryMale = noOfDeptInjuryMale;
	}

	public Long getNoOfInjuryChild() {
		return noOfInjuryChild;
	}

	public void setNoOfInjuryChild(Long noOfInjuryChild) {
		this.noOfInjuryChild = noOfInjuryChild;
	}

	public Long getNoOfInjuryFemale() {
		return noOfInjuryFemale;
	}

	public void setNoOfInjuryFemale(Long noOfInjuryFemale) {
		this.noOfInjuryFemale = noOfInjuryFemale;
	}

	public Long getNoOfInjuryMale() {
		return noOfInjuryMale;
	}

	public void setNoOfInjuryMale(Long noOfInjuryMale) {
		this.noOfInjuryMale = noOfInjuryMale;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Long getRescuedWithFireDeptFemale() {
		return rescuedWithFireDeptFemale;
	}

	public void setRescuedWithFireDeptFemale(Long rescuedWithFireDeptFemale) {
		this.rescuedWithFireDeptFemale = rescuedWithFireDeptFemale;
	}

	public Long getRescuedWithFireDeptMale() {
		return rescuedWithFireDeptMale;
	}

	public void setRescuedWithFireDeptMale(Long rescuedWithFireDeptMale) {
		this.rescuedWithFireDeptMale = rescuedWithFireDeptMale;
	}

	public Long getRescuedWithFireDeptVeheicleFemale() {
		return rescuedWithFireDeptVeheicleFemale;
	}

	public void setRescuedWithFireDeptVeheicleFemale(Long rescuedWithFireDeptVeheicleFemale) {
		this.rescuedWithFireDeptVeheicleFemale = rescuedWithFireDeptVeheicleFemale;
	}

	public Long getRescuedWithFireDeptVeheicleMale() {
		return rescuedWithFireDeptVeheicleMale;
	}

	public void setRescuedWithFireDeptVeheicleMale(Long rescuedWithFireDeptVeheicleMale) {
		this.rescuedWithFireDeptVeheicleMale = rescuedWithFireDeptVeheicleMale;
	}

	public Long getRescuedWithoutFireDeptFemale() {
		return rescuedWithoutFireDeptFemale;
	}

	public void setRescuedWithoutFireDeptFemale(Long rescuedWithoutFireDeptFemale) {
		this.rescuedWithoutFireDeptFemale = rescuedWithoutFireDeptFemale;
	}

	public Long getRescuedWithoutFireDeptMale() {
		return rescuedWithoutFireDeptMale;
	}

	public void setRescuedWithoutFireDeptMale(Long rescuedWithoutFireDeptMale) {
		this.rescuedWithoutFireDeptMale = rescuedWithoutFireDeptMale;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public BigDecimal getPropertySaved() {
		return propertySaved;
	}

	public void setPropertySaved(BigDecimal propertySaved) {
		this.propertySaved = propertySaved;
	}

	public BigDecimal getPropertyLost() {
		return propertyLost;
	}

	public void setPropertyLost(BigDecimal propertyLost) {
		this.propertyLost = propertyLost;
	}

	public String[] getPkValues() {
		return new String[] { "FM", "tb_fm_complain_closure", "closure_id" };
	}

}