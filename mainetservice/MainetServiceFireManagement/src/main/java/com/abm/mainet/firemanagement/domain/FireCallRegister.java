package com.abm.mainet.firemanagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_fm_complain_register database table.
 * 
 */
@Entity
@Table(name = "tb_fm_complain_register")
public class FireCallRegister implements Serializable {

	private static final long serialVersionUID = 5584292297611769439L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "cmplnt_id", unique = true, nullable = false)
	private Long cmplntId;

	@Column(name = "cmplnt_no", nullable = false, length = 20)
	private String cmplntNo;

	@Column(name = "caller_add", nullable = false, length = 1000)
	private String callerAdd;

	@Column(name = "caller_mobile_no", length = 15)
	private String callerMobileNo;

	@Column(name = "caller_name", length = 100)
	private String callerName;

	@Column(name = "closer_remarks", length = 300)
	private String closerRemarks;

	@Column(name = "complaint_fee")
	private double complaintFee;

	@Column(name = "complaint_status", length = 15)
	private String complaintStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	@Column(name = "hod_remarks", length = 300)
	private String hodRemarks;

	@Column(name = "incident_desc", nullable = false, length = 500)
	private String incidentDesc;

	@Column(name = "incident_location", nullable = false, length = 300)
	private String incidentLocation;

	@Column(name = "no_of_death_child")
	private Long noOfDeathChild;

	@Column(name = "no_of_death_female")
	private Long noOfDeathFemale;

	@Column(name = "no_of_death_male")
	private Long noOfDeathMale;

	@Column(name = "no_of_injury_child")
	private Long noOfInjuryChild;

	@Column(name = "no_of_injury_female")
	private Long noOfInjuryFemale;

	@Column(name = "no_of_injury_male")
	private Long noOfInjuryMale;

	@Column(name = "operator_remarks", length = 300)
	private String operatorRemarks;

	@Column(nullable = false)
	private Long orgid;

	@Column(nullable = false)
	private String time;

	@Column(name = "cpd_fire_station")
	private String cpdFireStation;



	@Column(name = "caller_area")
	private String callerArea;

	@Column(name = "no_of_dept_death_female")
	private Long noOfDeptDeathFemale;

	@Column(name = "no_of_dept_death_male")
	private Long noOfDeptDeathMale;

	@Column(name = "no_of_dept_injury_female")
	private Long noOfDeptInjuryFemale;

	@Column(name = "no_of_dept_injury_male")
	private Long noOfDeptInjuryMale;

	@Column(name = "cpd_call_type")
	private Long cpdCallType;

	@Column(name = "cpd_nature_of_call")
	private Long cpdNatureOfCall;

	@Column(name = "cpd_reason_of_fire")
	private Long cpdReasonOfFire;

	@Column(name = "rescued_without_fire_dept_female")
	private Long rescuedWithoutFireDeptFemale;

	@Column(name = "rescued_without_fire_dept_male")
	private Long rescuedWithoutFireDeptMale;

	@Column(name = "rescued_with_fire_dept_female")
	private Long rescuedWithFireDeptFemale;

	@Column(name = "rescued_with_fire_dept_male")
	private Long rescuedWithFireDeptMale;

	@Column(name = "rescued_with_fire_dept_veheicle_female")
	private Long rescuedWithFireDeptVeheicleFemale;

	@Column(name = "rescued_with_fire_dept_veheicle_male")
	private Long rescuedWithFireDeptVeheicleMale;

	@Column(name = "reason_for_delay", length = 100)
	private String reasonForDelay;

	@Column(name = "name_of_occupier", length = 100)
	private String nameOfOccupier;

	@Column(name = "name_of_owner", length = 100)
	private String nameOfOwner;

	@Column(name = "name_of_officer", length = 100)
	private String nameOfOfficer;

	@Column(name = "fire_stations_attend_call", length = 100)
	private String fireStationsAttendCall;

	
	@Temporal(TemporalType.DATE)
	@Column(name = "call_attend_date")
	private Date callAttendDate;

	@Column(name = "call_attend_employee", length = 200)
	private String callAttendEmployee;

	@Column(name = "call_attend_time")
	private String callAttendTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "call_closed_date")
	private Date callClosedDate;

	@Column(name = "call_closed_time")
	private String callClosedTime;

	@Column(name = "created_by", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "lg_ip_mac", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "lg_ip_mac_upd", length = 100)
	private String lgIpMacUpd;
	
	@Column(name = "vehicle_Out_Time")
	private String vehicleOutTime;
	
	@Column(name = "other_details")
	private String otherDetails;

  @Column(name = "vehicle_in_time")
	private String vehicleInTime;

	@Column(name = "WF_STATUS", nullable = false)
	private String FireWFStatus;
	
	@Column(name = "duty_officer")
	private Long dutyOfficer;
	
	@Column(name = "assign_vehicle")
	private String assignVehicle;
	
	@Column(name = "call_attended_by", length = 100)
	private String callAttendedBy;
	
	@Column(name = "recorded_by", length = 100)
	private String recordedBy;
	
	@Column(name = "call_forwarded", length = 100)
	private String callForwarded;
	
	@Column(name = "operator")
	private Long operator;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "vehicle_out_date")
	private Date vehicleOutDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "vehicle_in_date")
	private Date vehicleInDate;
	

	public String getAssignVehicle() {
		return assignVehicle;
	}

	public void setAssignVehicle(String assignVehicle) {
		this.assignVehicle = assignVehicle;
	}

	public void setDutyOfficer(Long dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Long getDutyOfficer() {
		return dutyOfficer;
	}


	public String getVehicleOutTime() {
		return vehicleOutTime;
	}

	public void setVehicleOutTime(String vehicleOutTime) {
		this.vehicleOutTime = vehicleOutTime;
	}


	public FireCallRegister() {
	}

	public Long getCmplntId() {
		return this.cmplntId;
	}

	public void setCmplntId(Long cmplntId) {
		this.cmplntId = cmplntId;
	}

	public String getCallerAdd() {
		return this.callerAdd;
	}

	public void setCallerAdd(String callerAdd) {
		this.callerAdd = callerAdd;
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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Long getNoOfDeathChild() {
		return this.noOfDeathChild;
	}

	public void setNoOfDeathChild(Long noOfDeathChild) {
		this.noOfDeathChild = noOfDeathChild;
	}

	public Long getNoOfDeathFemale() {
		return this.noOfDeathFemale;
	}

	public void setNoOfDeathFemale(Long noOfDeathFemale) {
		this.noOfDeathFemale = noOfDeathFemale;
	}

	public Long getNoOfDeathMale() {
		return this.noOfDeathMale;
	}

	public void setNoOfDeathMale(Long noOfDeathMale) {
		this.noOfDeathMale = noOfDeathMale;
	}

	public Long getNoOfInjuryChild() {
		return this.noOfInjuryChild;
	}

	public void setNoOfInjuryChild(Long noOfInjuryChild) {
		this.noOfInjuryChild = noOfInjuryChild;
	}

	public Long getNoOfInjuryFemale() {
		return this.noOfInjuryFemale;
	}

	public void setNoOfInjuryFemale(Long noOfInjuryFemale) {
		this.noOfInjuryFemale = noOfInjuryFemale;
	}

	public Long getNoOfInjuryMale() {
		return this.noOfInjuryMale;
	}

	public void setNoOfInjuryMale(Long noOfInjuryMale) {
		this.noOfInjuryMale = noOfInjuryMale;
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

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the cpdFireStation
	 */
	public String getCpdFireStation() {
		return cpdFireStation;
	}

	/**
	 * @param cpdFireStation the cpdFireStation to set
	 */
	public void setCpdFireStation(String cpdFireStation) {
		this.cpdFireStation = cpdFireStation;
	}

	/**
	 * @return the callerArea
	 */
	public String getCallerArea() {
		return callerArea;
	}

	/**
	 * @param callerArea the callerArea to set
	 */
	public void setCallerArea(String callerArea) {
		this.callerArea = callerArea;
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

	/**
	 * @return the rescuedWithFireDeptFemale
	 */
	public Long getRescuedWithFireDeptFemale() {
		return rescuedWithFireDeptFemale;
	}

	/**
	 * @param rescuedWithFireDeptFemale the rescuedWithFireDeptFemale to set
	 */
	public void setRescuedWithFireDeptFemale(Long rescuedWithFireDeptFemale) {
		this.rescuedWithFireDeptFemale = rescuedWithFireDeptFemale;
	}

	/**
	 * @return the rescuedWithFireDeptMale
	 */
	public Long getRescuedWithFireDeptMale() {
		return rescuedWithFireDeptMale;
	}

	/**
	 * @param rescuedWithFireDeptMale the rescuedWithFireDeptMale to set
	 */
	public void setRescuedWithFireDeptMale(Long rescuedWithFireDeptMale) {
		this.rescuedWithFireDeptMale = rescuedWithFireDeptMale;
	}

	/**
	 * @return the rescuedWithFireDeptVeheicleFemale
	 */
	public Long getRescuedWithFireDeptVeheicleFemale() {
		return rescuedWithFireDeptVeheicleFemale;
	}

	/**
	 * @param rescuedWithFireDeptVeheicleFemale the
	 *                                          rescuedWithFireDeptVeheicleFemale to
	 *                                          set
	 */
	public void setRescuedWithFireDeptVeheicleFemale(Long rescuedWithFireDeptVeheicleFemale) {
		this.rescuedWithFireDeptVeheicleFemale = rescuedWithFireDeptVeheicleFemale;
	}

	/**
	 * @return the rescuedWithFireDeptVeheicleMale
	 */
	public Long getRescuedWithFireDeptVeheicleMale() {
		return rescuedWithFireDeptVeheicleMale;
	}

	/**
	 * @param rescuedWithFireDeptVeheicleMale the rescuedWithFireDeptVeheicleMale to
	 *                                        set
	 */
	public void setRescuedWithFireDeptVeheicleMale(Long rescuedWithFireDeptVeheicleMale) {
		this.rescuedWithFireDeptVeheicleMale = rescuedWithFireDeptVeheicleMale;
	}

	public String getReasonForDelay() {
		return reasonForDelay;
	}

	public void setReasonForDelay(String reasonForDelay) {
		this.reasonForDelay = reasonForDelay;
	}

	public String getNameOfOccupier() {
		return nameOfOccupier;
	}

	public void setNameOfOccupier(String nameOfOccupier) {
		this.nameOfOccupier = nameOfOccupier;
	}

	public String getNameOfOwner() {
		return nameOfOwner;
	}

	public void setNameOfOwner(String nameOfOwner) {
		this.nameOfOwner = nameOfOwner;
	}

	public String getNameOfOfficer() {
		return nameOfOfficer;
	}

	public void setNameOfOfficer(String nameOfOfficer) {
		this.nameOfOfficer = nameOfOfficer;
	}

	public String getFireStationsAttendCall() {
		return fireStationsAttendCall;
	}

	public void setFireStationsAttendCall(String fireStationsAttendCall) {
		this.fireStationsAttendCall = fireStationsAttendCall;
	}

	public Date getCallAttendDate() {
		return callAttendDate;
	}

	public void setCallAttendDate(Date callAttendDate) {
		this.callAttendDate = callAttendDate;
	}

	public String getCallAttendEmployee() {
		return callAttendEmployee;
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
		return callClosedDate;
	}

	public void setCallClosedDate(Date callClosedDate) {
		this.callClosedDate = callClosedDate;
	}

	public String getCallClosedTime() {
		return callClosedTime;
	}

	public void setCallClosedTime(String callClosedTime) {
		this.callClosedTime = callClosedTime;
	}

	public String getFireWFStatus() {
		return FireWFStatus;
	}

	public void setFireWFStatus(String fireWFStatus) {
		FireWFStatus = fireWFStatus;
	}
	

	public String getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public String getVehicleInTime() {
		return vehicleInTime;
	}

	public void setVehicleInTime(String vehicleInTime) {
		this.vehicleInTime = vehicleInTime;
	}
	
	public String getCallAttendedBy() {
		return callAttendedBy;
	}

	public void setCallAttendedBy(String callAttendedBy) {
		this.callAttendedBy = callAttendedBy;
	}

	public String getRecordedBy() {
		return recordedBy;
	}

	public void setRecordedBy(String recordedBy) {
		this.recordedBy = recordedBy;
	}

	public String getCallForwarded() {
		return callForwarded;
	}

	public void setCallForwarded(String callForwarded) {
		this.callForwarded = callForwarded;
	}

	public String[] getPkValues() {
		return new String[] { "FM", "TB_FM_COMPLAIN_REGISTER", "CMPLNT_ID" };
	}

	public Date getVehicleOutDate() {
		return vehicleOutDate;
	}

	public void setVehicleOutDate(Date vehicleOutDate) {
		this.vehicleOutDate = vehicleOutDate;
	}

	public Date getVehicleInDate() {
		return vehicleInDate;
	}

	public void setVehicleInDate(Date vehicleInDate) {
		this.vehicleInDate = vehicleInDate;
	}

}
