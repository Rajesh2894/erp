package com.abm.mainet.firemanagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the ComplainRegister entity.
 */
public class FireCallRegisterDTO implements Serializable {

	private static final long serialVersionUID = -6026546797287851506L;

	private Long closureId;
	
	private Long cmplntId;

	private String callerAdd;

	private String callerMobileNo;

	private String callerName;

	private Date fromDate;

	private List<String> multiSelect = new ArrayList<>();

	private Date toDate;

	private String closerRemarks;

	private String cmplntNo;

	private double complaintFee;

	private String complaintStatus;

	private Date date;

	private String hodRemarks;

	private String incidentDesc;

	private String incidentLocation;

	private Long noOfDeathChild;

	private Long noOfDeathFemale;

	private Long noOfDeathMale;

	private Long noOfInjuryChild;

	private Long noOfInjuryFemale;

	private Long noOfInjuryMale;

	private String operatorRemarks;

	private Long orgid;

	private String time;

	private String cpdFireStation;
	
	private List<String>  cpdFireStationList;
	
	private String callerArea;

	private Long noOfDeptDeathFemale;

	private Long noOfDeptDeathMale;

	private Long noOfDeptInjuryFemale;

	private Long noOfDeptInjuryMale;

	private Long cpdCallType;

	private Long cpdNatureOfCall;

	private Long cpdReasonOfFire;

	private Long rescuedWithoutFireDeptFemale;

	private Long rescuedWithoutFireDeptMale;

	private Long rescuedWithFireDeptFemale;

	private Long rescuedWithFireDeptMale;

	private Long rescuedWithFireDeptVeheicleFemale;

	private Long rescuedWithFireDeptVeheicleMale;

	private String reasonForDelay;

	private String nameOfOfficer;

	private String nameOfOccupier;

	private String nameOfOwner;

	private String fireStationsAttendCall;

	private List<String> fireStationsAttendCallList;

	private Date callAttendDate;

	private String callAttendEmployee;

	private List<String> callAttendEmployeeList;

	private String callAttendTime;

	private Date callClosedDate;

	private String callClosedTime;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private String fsDesc;
	
    private String callRegClosureStatus;
    
    private String callRegClosureRemark;
    
	private String FireWfStatus;
	
//    private String statusFlag;

	private String fireStation;

	private Long dutyOfficerLong;

	private String dutyOfficer;
	
	private String veFlag;

	private List<String> dutyOfficerList;
	
	private String atdFname;
	private String atdPath;
	private String saveMode;
	
	private Long deptId;
	
	private Date veRentFromdate;
	
	private Date veRentTodate;
	
	private String callAttendedBy;
	
	private String recordedBy;
	
	private String callForwarded;
	
	private BigDecimal propertySaved;
	
	private BigDecimal propertyLost;

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getAtdFname() {
		return atdFname;
	}

	public void setAtdFname(String atdFname) {
		this.atdFname = atdFname;
	}

	public String getAtdPath() {
		return atdPath;
	}

	public void setAtdPath(String atdPath) {
		this.atdPath = atdPath;
	}

	public List<String> getDutyOfficerList() {
		return dutyOfficerList;
	}

	public void setDutyOfficerList(List<String> dutyOfficerList) {
		this.dutyOfficerList = dutyOfficerList;
	}

	public String getDutyOfficer() {
		return dutyOfficer;
	}

	public void setDutyOfficer(String dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	private Long operator;

	private String vehicleOutTime;

	private String vehicleInTime;

	private Long assignVehicle;

	private String otherDetails;

	private String vehNoDesc;

	private String statusFlag;

	private Long fireDraftId;
	
	private Date vehicleOutDate;
	
	private Date vehicleInDate;
	
	private List<String> assignVehicleList;

	public Long getDutyOfficerLong() {
		return dutyOfficerLong;
	}

	public void setDutyOfficerLong(Long dutyOfficerLong) {
		this.dutyOfficerLong = dutyOfficerLong;
	}

	public Long getFireDraftId() {
		return fireDraftId;
	}

	public void setFireDraftId(Long fireDraftId) {
		this.fireDraftId = fireDraftId;
	}


	public String getVehNoDesc() {
		return vehNoDesc;
	}

	public void setVehNoDesc(String vehNoDesc) {
		this.vehNoDesc = vehNoDesc;
	}

	public String getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}

	public Long getAssignVehicle() {
		return assignVehicle;
	}

	public void setAssignVehicle(Long assignVehicle) {
		this.assignVehicle = assignVehicle;
	}

	public String getFireStation() {
		return fireStation;
	}

	public void setFireStation(String fireStation) {
		this.fireStation = fireStation;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getVehicleOutTime() {
		return vehicleOutTime;
	}

	public void setVehicleOutTime(String vehicleOutTime) {
		this.vehicleOutTime = vehicleOutTime;
	}

	public String getVehicleInTime() {
		return vehicleInTime;
	}

	public void setVehicleInTime(String vehicleInTime) {
		this.vehicleInTime = vehicleInTime;
	}

	/**
	 * @return the cmplntId
	 */
	public Long getCmplntId() {
		return cmplntId;
	}

	/**
	 * @param cmplntId the cmplntId to set
	 */
	public void setCmplntId(Long cmplntId) {
		this.cmplntId = cmplntId;
	}

	/**
	 * @return the callerAdd
	 */
	public String getCallerAdd() {
		return callerAdd;
	}

	/**
	 * @param callerAdd the callerAdd to set
	 */
	public void setCallerAdd(String callerAdd) {
		this.callerAdd = callerAdd;
	}

	/**
	 * @return the callerMobileNo
	 */
	public String getCallerMobileNo() {
		return callerMobileNo;
	}

	/**
	 * @param callerMobileNo the callerMobileNo to set
	 */
	public void setCallerMobileNo(String callerMobileNo) {
		this.callerMobileNo = callerMobileNo;
	}

	/**
	 * @return the callerName
	 */
	public String getCallerName() {
		return callerName;
	}

	/**
	 * @param callerName the callerName to set
	 */
	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

	/**
	 * @return the closerRemarks
	 */
	public String getCloserRemarks() {
		return closerRemarks;
	}

	/**
	 * @param closerRemarks the closerRemarks to set
	 */
	public void setCloserRemarks(String closerRemarks) {
		this.closerRemarks = closerRemarks;
	}

	/**
	 * @return the cmplntNo
	 */
	public String getCmplntNo() {
		return cmplntNo;
	}

	/**
	 * @param cmplntNo the cmplntNo to set
	 */
	public void setCmplntNo(String cmplntNo) {
		this.cmplntNo = cmplntNo;
	}

	/**
	 * @return the complaintFee
	 */
	public double getComplaintFee() {
		return complaintFee;
	}

	/**
	 * @param complaintFee the complaintFee to set
	 */
	public void setComplaintFee(double complaintFee) {
		this.complaintFee = complaintFee;
	}

	/**
	 * @return the complaintStatus
	 */
	public String getComplaintStatus() {
		return complaintStatus;
	}

	/**
	 * @param complaintStatus the complaintStatus to set
	 */
	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the hodRemarks
	 */
	public String getHodRemarks() {
		return hodRemarks;
	}

	/**
	 * @param hodRemarks the hodRemarks to set
	 */
	public void setHodRemarks(String hodRemarks) {
		this.hodRemarks = hodRemarks;
	}

	/**
	 * @return the incidentDesc
	 */
	public String getIncidentDesc() {
		return incidentDesc;
	}

	/**
	 * @param incidentDesc the incidentDesc to set
	 */
	public void setIncidentDesc(String incidentDesc) {
		this.incidentDesc = incidentDesc;
	}

	/**
	 * @return the incidentLocation
	 */
	public String getIncidentLocation() {
		return incidentLocation;
	}

	/**
	 * @param incidentLocation the incidentLocation to set
	 */
	public void setIncidentLocation(String incidentLocation) {
		this.incidentLocation = incidentLocation;
	}

	/**
	 * @return the noOfDeathChild
	 */
	public Long getNoOfDeathChild() {
		return noOfDeathChild;
	}

	/**
	 * @param noOfDeathChild the noOfDeathChild to set
	 */
	public void setNoOfDeathChild(Long noOfDeathChild) {
		this.noOfDeathChild = noOfDeathChild;
	}

	/**
	 * @return the noOfDeathFemale
	 */
	public Long getNoOfDeathFemale() {
		return noOfDeathFemale;
	}

	/**
	 * @param noOfDeathFemale the noOfDeathFemale to set
	 */
	public void setNoOfDeathFemale(Long noOfDeathFemale) {
		this.noOfDeathFemale = noOfDeathFemale;
	}

	/**
	 * @return the noOfDeathMale
	 */
	public Long getNoOfDeathMale() {
		return noOfDeathMale;
	}

	/**
	 * @param noOfDeathMale the noOfDeathMale to set
	 */
	public void setNoOfDeathMale(Long noOfDeathMale) {
		this.noOfDeathMale = noOfDeathMale;
	}

	/**
	 * @return the noOfInjuryChild
	 */
	public Long getNoOfInjuryChild() {
		return noOfInjuryChild;
	}

	/**
	 * @param noOfInjuryChild the noOfInjuryChild to set
	 */
	public void setNoOfInjuryChild(Long noOfInjuryChild) {
		this.noOfInjuryChild = noOfInjuryChild;
	}

	/**
	 * @return the noOfInjuryFemale
	 */
	public Long getNoOfInjuryFemale() {
		return noOfInjuryFemale;
	}

	/**
	 * @param noOfInjuryFemale the noOfInjuryFemale to set
	 */
	public void setNoOfInjuryFemale(Long noOfInjuryFemale) {
		this.noOfInjuryFemale = noOfInjuryFemale;
	}

	/**
	 * @return the noOfInjuryMale
	 */
	public Long getNoOfInjuryMale() {
		return noOfInjuryMale;
	}

	/**
	 * @param noOfInjuryMale the noOfInjuryMale to set
	 */
	public void setNoOfInjuryMale(Long noOfInjuryMale) {
		this.noOfInjuryMale = noOfInjuryMale;
	}

	/**
	 * @return the operatorRemarks
	 */
	public String getOperatorRemarks() {
		return operatorRemarks;
	}

	/**
	 * @param operatorRemarks the operatorRemarks to set
	 */
	public void setOperatorRemarks(String operatorRemarks) {
		this.operatorRemarks = operatorRemarks;
	}

	/**
	 * @return the orgid
	 */
	public Long getOrgid() {
		return orgid;
	}

	/**
	 * @param orgid the orgid to set
	 */
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
	/*
	 * public String getCpdFireStation() { return cpdFireStation; }
	 * 
	 *//**
		 * @param cpdFireStation the cpdFireStation to set
		 *//*
			 * public void setCpdFireStation(String cpdFireStation) { this.cpdFireStation =
			 * cpdFireStation; }
			 */

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

	/**
	 * @return the noOfDeptDeathFemale
	 */
	public Long getNoOfDeptDeathFemale() {
		return noOfDeptDeathFemale;
	}

	/**
	 * @param noOfDeptDeathFemale the noOfDeptDeathFemale to set
	 */
	public void setNoOfDeptDeathFemale(Long noOfDeptDeathFemale) {
		this.noOfDeptDeathFemale = noOfDeptDeathFemale;
	}

	/**
	 * @return the noOfDeptDeathMale
	 */
	public Long getNoOfDeptDeathMale() {
		return noOfDeptDeathMale;
	}

	/**
	 * @param noOfDeptDeathMale the noOfDeptDeathMale to set
	 */
	public void setNoOfDeptDeathMale(Long noOfDeptDeathMale) {
		this.noOfDeptDeathMale = noOfDeptDeathMale;
	}

	/**
	 * @return the noOfDeptInjuryFemale
	 */
	public Long getNoOfDeptInjuryFemale() {
		return noOfDeptInjuryFemale;
	}

	/**
	 * @param noOfDeptInjuryFemale the noOfDeptInjuryFemale to set
	 */
	public void setNoOfDeptInjuryFemale(Long noOfDeptInjuryFemale) {
		this.noOfDeptInjuryFemale = noOfDeptInjuryFemale;
	}

	/**
	 * @return the noOfDeptInjuryMale
	 */
	public Long getNoOfDeptInjuryMale() {
		return noOfDeptInjuryMale;
	}

	/**
	 * @param noOfDeptInjuryMale the noOfDeptInjuryMale to set
	 */
	public void setNoOfDeptInjuryMale(Long noOfDeptInjuryMale) {
		this.noOfDeptInjuryMale = noOfDeptInjuryMale;
	}

	/**
	 * @return the cpdCallType
	 */
	public Long getCpdCallType() {
		return cpdCallType;
	}

	/**
	 * @param cpdCallType the cpdCallType to set
	 */
	public void setCpdCallType(Long cpdCallType) {
		this.cpdCallType = cpdCallType;
	}

	/**
	 * @return the cpdNatureOfCall
	 */
	public Long getCpdNatureOfCall() {
		return cpdNatureOfCall;
	}

	/**
	 * @param cpdNatureOfCall the cpdNatureOfCall to set
	 */
	public void setCpdNatureOfCall(Long cpdNatureOfCall) {
		this.cpdNatureOfCall = cpdNatureOfCall;
	}

	/**
	 * @return the cpdReasonOfFire
	 */
	public Long getCpdReasonOfFire() {
		return cpdReasonOfFire;
	}

	/**
	 * @param cpdReasonOfFire the cpdReasonOfFire to set
	 */
	public void setCpdReasonOfFire(Long cpdReasonOfFire) {
		this.cpdReasonOfFire = cpdReasonOfFire;
	}

	/**
	 * @return the rescuedWithoutFireDeptFemale
	 */
	public Long getRescuedWithoutFireDeptFemale() {
		return rescuedWithoutFireDeptFemale;
	}

	/**
	 * @param rescuedWithoutFireDeptFemale the rescuedWithoutFireDeptFemale to set
	 */
	public void setRescuedWithoutFireDeptFemale(Long rescuedWithoutFireDeptFemale) {
		this.rescuedWithoutFireDeptFemale = rescuedWithoutFireDeptFemale;
	}

	/**
	 * @return the rescuedWithoutFireDeptMale
	 */
	public Long getRescuedWithoutFireDeptMale() {
		return rescuedWithoutFireDeptMale;
	}

	/**
	 * @param rescuedWithoutFireDeptMale the rescuedWithoutFireDeptMale to set
	 */
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

	/**
	 * @return the reasonForDelay
	 */
	public String getReasonForDelay() {
		return reasonForDelay;
	}

	/**
	 * @param reasonForDelay the reasonForDelay to set
	 */
	public void setReasonForDelay(String reasonForDelay) {
		this.reasonForDelay = reasonForDelay;
	}

	/**
	 * @return the nameOfOfficer
	 */
	public String getNameOfOfficer() {
		return nameOfOfficer;
	}

	/**
	 * @param nameOfOfficer the nameOfOfficer to set
	 */
	public void setNameOfOfficer(String nameOfOfficer) {
		this.nameOfOfficer = nameOfOfficer;
	}

	/**
	 * @return the nameOfOccupier
	 */
	public String getNameOfOccupier() {
		return nameOfOccupier;
	}

	/**
	 * @param nameOfOccupier the nameOfOccupier to set
	 */
	public void setNameOfOccupier(String nameOfOccupier) {
		this.nameOfOccupier = nameOfOccupier;
	}

	/**
	 * @return the nameOfOwner
	 */
	public String getNameOfOwner() {
		return nameOfOwner;
	}

	/**
	 * @param nameOfOwner the nameOfOwner to set
	 */
	public void setNameOfOwner(String nameOfOwner) {
		this.nameOfOwner = nameOfOwner;
	}

	/**
	 * @return the fireStationsAttendCall
	 */
	public String getFireStationsAttendCall() {
		return fireStationsAttendCall;
	}

	/**
	 * @param fireStationsAttendCall the fireStationsAttendCall to set
	 */
	public void setFireStationsAttendCall(String fireStationsAttendCall) {
		this.fireStationsAttendCall = fireStationsAttendCall;
	}

	/**
	 * @return the callAttendDate
	 */
	public Date getCallAttendDate() {
		return callAttendDate;
	}

	/**
	 * @param callAttendDate the callAttendDate to set
	 */
	public void setCallAttendDate(Date callAttendDate) {
		this.callAttendDate = callAttendDate;
	}

	/**
	 * @return the callAttendEmployee
	 */
	public String getCallAttendEmployee() {
		return callAttendEmployee;
	}

	/**
	 * @param callAttendEmployee the callAttendEmployee to set
	 */
	public void setCallAttendEmployee(String callAttendEmployee) {
		this.callAttendEmployee = callAttendEmployee;
	}

	/**
	 * @return the callAttendTime
	 */
	public String getCallAttendTime() {
		return callAttendTime;
	}

	/**
	 * @param callAttendTime the callAttendTime to set
	 */
	public void setCallAttendTime(String callAttendTime) {
		this.callAttendTime = callAttendTime;
	}

	/**
	 * @return the callClosedDate
	 */
	public Date getCallClosedDate() {
		return callClosedDate;
	}

	/**
	 * @param callClosedDate the callClosedDate to set
	 */
	public void setCallClosedDate(Date callClosedDate) {
		this.callClosedDate = callClosedDate;
	}

	/**
	 * @return the callClosedTime
	 */
	public String getCallClosedTime() {
		return callClosedTime;
	}

	/**
	 * @param callClosedTime the callClosedTime to set
	 */
	public void setCallClosedTime(String callClosedTime) {
		this.callClosedTime = callClosedTime;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	/**
	 * @return the fireStationsAttendCallList
	 */
	public List<String> getFireStationsAttendCallList() {
		return fireStationsAttendCallList;
	}

	/**
	 * @param fireStationsAttendCallList the fireStationsAttendCallList to set
	 */
	public void setFireStationsAttendCallList(List<String> fireStationsAttendCallList) {
		this.fireStationsAttendCallList = fireStationsAttendCallList;
	}

	/**
	 * @return the callAttendEmployeeList
	 */
	public List<String> getCallAttendEmployeeList() {
		return callAttendEmployeeList;
	}

	/**
	 * @param callAttendEmployeeList the callAttendEmployeeList to set
	 */
	public void setCallAttendEmployeeList(List<String> callAttendEmployeeList) {
		this.callAttendEmployeeList = callAttendEmployeeList;
	}

	public String getFsDesc() {
		return fsDesc;
	}

	public void setFsDesc(String fsDesc) {
		this.fsDesc = fsDesc;
	}

	public String getCallRegClosureStatus() {
		return callRegClosureStatus;
	}

	public void setCallRegClosureStatus(String callRegClosureStatus) {
		this.callRegClosureStatus = callRegClosureStatus;
	}

	public String getCallRegClosureRemark() {
		return callRegClosureRemark;
	}

	public void setCallRegClosureRemark(String callRegClosureRemark) {
		this.callRegClosureRemark = callRegClosureRemark;
	}
	
	public String getFireWfStatus() {
		return FireWfStatus;
	}

	public void setFireWfStatus(String fireWfStatus) {
		FireWfStatus = fireWfStatus;
	}
	
	public Long getClosureId() {
		return closureId;
	}

	public void setClosureId(Long closureId) {
		this.closureId = closureId;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<String> getMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(List<String> multiSelect) {
		this.multiSelect = multiSelect;
	}

	
	public String getCpdFireStation() {
		return cpdFireStation;
	}

	public void setCpdFireStation(String cpdFireStation) {
		this.cpdFireStation = cpdFireStation;
	}
	
	public List<String> getCpdFireStationList() {
		return cpdFireStationList;
	}

	public void setCpdFireStationList(List<String> cpdFireStationList) {
		this.cpdFireStationList = cpdFireStationList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getVeFlag() {
		return veFlag;
	}

	public void setVeFlag(String veFlag) {
		this.veFlag = veFlag;
	}

	public Date getVeRentFromdate() {
		return veRentFromdate;
	}

	public void setVeRentFromdate(Date veRentFromdate) {
		this.veRentFromdate = veRentFromdate;
	}

	public Date getVeRentTodate() {
		return veRentTodate;
	}

	public void setVeRentTodate(Date veRentTodate) {
		this.veRentTodate = veRentTodate;
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

	public List<String> getAssignVehicleList() {
		return assignVehicleList;
	}

	public void setAssignVehicleList(List<String> assignVehicleList) {
		this.assignVehicleList = assignVehicleList;
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
