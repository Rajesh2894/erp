package com.abm.mainet.disastermanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.master.dto.DepartmentDTO;
import com.abm.mainet.disastermanagement.domain.ComplainScrutiny;

/**
 * A DTO for the ComplainRegister entity.
 */
public class ComplainRegisterDTO implements Serializable {

	private static final long serialVersionUID = 2110704719241314572L;

	private Long complainId;

	private String complainNo;
	
	private String  manualComplainNo;
	
	private String designation;

	private String department;
	
	private String strDepartmentList;
	
	private String strEmployeeList;

	private Long complaintType1;

	private Long complaintType2;

	private Long complaintType3;

	private Long complaintType4;

	private Long complaintType5;

	private Long applicationId;

	private String complainerName;

	private String complainerMobile;
	
	private String complainerMobile1;

	private String complainerAddress;

	private String complaintDescription;

	private Long location;

	private String complainStatus;

	private Long orgid;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	private Long departmentId;
	
	private Date frmDate;
	private Date toDate;
	private String remark;
	private Long userid;
	private String mobnumber;
	
	private String complaintType1Desc;

	private String employee;
	
	private Long noOfInjuryMale;
	
	private Long noOfInjuryFemale;
	
	private Long noOfInjuryChild;
	
	private Long totalInjured;
	
	private Long noOfDeathMale;
	
	private Long noOfDeathFemale;
	
	private Long noOfDeathChild;
	
	private Long totalDeath;
	
	private String codDesc;
	
	private String codDesc1;
	
	private List<ComplainScrutiny>	complainScrutinyLst		= new ArrayList<ComplainScrutiny>();
	
    private List<DepartmentDTO> departmentDTO;

	private List<EmployeeDTO> employeeDTO;
	
	private String StatusVariable;
	
	private Date callAttendDate;

	private String callAttendTime;
	
	private String callAttendEmployee;
	
	private String callerArea;

	private String reasonForDelay;
	
	private String noOfVehDamaged;
	
	private Long locId;
	
	public String getComplaintType2Desc() {
		return complaintType2Desc;
	}

	public void setComplaintType2Desc(String complaintType2Desc) {
		this.complaintType2Desc = complaintType2Desc;
	}

	private String complaintType2Desc;
	
	public String getStatusVariable() {
		return StatusVariable;
	}

	public void setStatusVariable(String statusVariable) {
		StatusVariable = statusVariable;
	}

	
	public String getManualComplainNo() {
		return manualComplainNo;
	}

	public void setManualComplainNo(String manualComplainNo) {
		this.manualComplainNo = manualComplainNo;
	}
	
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCodDesc() {
		return codDesc;
	}

	public void setCodDesc(String codDesc) {
		this.codDesc = codDesc;
	}

	public String getCodDesc1() {
		return codDesc1;
	}

	public void setCodDesc1(String codDesc1) {
		this.codDesc1 = codDesc1;
	}

	public List<ComplainScrutiny> getComplainScrutinyLst() {
		return complainScrutinyLst;
	}

	public void setComplainScrutinyLst(List<ComplainScrutiny> complainScrutinyLst) {
		this.complainScrutinyLst = complainScrutinyLst;
	}
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getMobnumber() {
		return mobnumber;
	}

	public void setMobnumber(String mobnumber) {
		this.mobnumber = mobnumber;
	}

	public Long getNoOfInjuryMale() {
		return noOfInjuryMale;
	}

	public void setNoOfInjuryMale(Long noOfInjuryMale) {
		this.noOfInjuryMale = noOfInjuryMale;
	}

	public Long getNoOfInjuryFemale() {
		return noOfInjuryFemale;
	}

	public void setNoOfInjuryFemale(Long noOfInjuryFemale) {
		this.noOfInjuryFemale = noOfInjuryFemale;
	}

	public Long getNoOfInjuryChild() {
		return noOfInjuryChild;
	}

	public void setNoOfInjuryChild(Long noOfInjuryChild) {
		this.noOfInjuryChild = noOfInjuryChild;
	}

	public Long getTotalInjured() {
		return totalInjured;
	}

	public void setTotalInjured(Long totalInjured) {
		this.totalInjured = totalInjured;
	}

	public Long getNoOfDeathMale() {
		return noOfDeathMale;
	}

	public void setNoOfDeathMale(Long noOfDeathMale) {
		this.noOfDeathMale = noOfDeathMale;
	}

	public Long getNoOfDeathFemale() {
		return noOfDeathFemale;
	}

	public void setNoOfDeathFemale(Long noOfDeathFemale) {
		this.noOfDeathFemale = noOfDeathFemale;
	}

	public Long getNoOfDeathChild() {
		return noOfDeathChild;
	}

	public void setNoOfDeathChild(Long noOfDeathChild) {
		this.noOfDeathChild = noOfDeathChild;
	}

	public Long getTotalDeath() {
		return totalDeath;
	}

	public void setTotalDeath(Long totalDeath) {
		this.totalDeath = totalDeath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the complainId
	 */
	public Long getComplainId() {
		return complainId;
	}

	/**
	 * @param complainId the complainId to set
	 */
	public void setComplainId(Long complainId) {
		this.complainId = complainId;
	}

	/**
	 * @return the complainNo
	 */
	public String getComplainNo() {
		return complainNo;
	}

	/**
	 * @param complainNo the complainNo to set
	 */
	public void setComplainNo(String complainNo) {
		this.complainNo = complainNo;
	}

	

	/**
	 * @return the complaintType1
	 */
	public Long getComplaintType1() {
		return complaintType1;
	}

	/**
	 * @param complaintType1 the complaintType1 to set
	 */
	public void setComplaintType1(Long complaintType1) {
		this.complaintType1 = complaintType1;
	}

	/**
	 * @return the complaintType2
	 */
	public Long getComplaintType2() {
		return complaintType2;
	}

	/**
	 * @param complaintType2 the complaintType2 to set
	 */
	public void setComplaintType2(Long complaintType2) {
		this.complaintType2 = complaintType2;
	}

	/**
	 * @return the complaintType3
	 */
	public Long getComplaintType3() {
		return complaintType3;
	}

	/**
	 * @param complaintType3 the complaintType3 to set
	 */
	public void setComplaintType3(Long complaintType3) {
		this.complaintType3 = complaintType3;
	}

	/**
	 * @return the complaintType4
	 */
	public Long getComplaintType4() {
		return complaintType4;
	}

	/**
	 * @param complaintType4 the complaintType4 to set
	 */
	public void setComplaintType4(Long complaintType4) {
		this.complaintType4 = complaintType4;
	}

	/**
	 * @return the complaintType5
	 */
	public Long getComplaintType5() {
		return complaintType5;
	}

	/**
	 * @param complaintType5 the complaintType5 to set
	 */
	public void setComplaintType5(Long complaintType5) {
		this.complaintType5 = complaintType5;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the complainerName
	 */
	public String getComplainerName() {
		return complainerName;
	}

	/**
	 * @param complainerName the complainerName to set
	 */
	public void setComplainerName(String complainerName) {
		this.complainerName = complainerName;
	}

	/**
	 * @return the complainerMobile
	 */
	public String getComplainerMobile() {
		return complainerMobile;
	}

	/**
	 * @param complainerMobile the complainerMobile to set
	 */
	public void setComplainerMobile(String complainerMobile) {
		this.complainerMobile = complainerMobile;
	}

	/**
	 * @return the complainerAddress
	 */
	public String getComplainerAddress() {
		return complainerAddress;
	}

	/**
	 * @param complainerAddress the complainerAddress to set
	 */
	public void setComplainerAddress(String complainerAddress) {
		this.complainerAddress = complainerAddress;
	}

	/**
	 * @return the complaintDescription
	 */
	public String getComplaintDescription() {
		return complaintDescription;
	}

	/**
	 * @param complaintDescription the complaintDescription to set
	 */
	public void setComplaintDescription(String complaintDescription) {
		this.complaintDescription = complaintDescription;
	}

	/**
	 * @return the location
	 */
	public Long getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Long location) {
		this.location = location;
	}

	/**
	 * @return the complaintStatus
	 */
	public String getComplaintStatus() {
		return complainStatus;
	}

	/**
	 * @param complaintStatus the complaintStatus to set
	 */
	public void setComplaintStatus(String complainStatus) {
		this.complainStatus = complainStatus;
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
	

	public Date getFrmDate() {
		return frmDate;
	}

	public void setFrmDate(Date frmDate) {
		this.frmDate = frmDate;
	}
	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
    public List<DepartmentDTO> getDepartmentDTO() {
		return departmentDTO;
	}

	public void setDepartmentDTO(List<DepartmentDTO> departmentDTO) {
		this.departmentDTO = departmentDTO;
	}

	public List<EmployeeDTO> getEmployeeDTO() {
		return employeeDTO;
	}

	public void setEmployeeDTO(List<EmployeeDTO> employeeDTO) {
		this.employeeDTO = employeeDTO;
	}
	
	public String getComplaintType1Desc() {
		return complaintType1Desc;
	}

	public void setComplaintType1Desc(String complaintType1Desc) {
		this.complaintType1Desc = complaintType1Desc;
	}

	public String getStrDepartmentList() {
		return strDepartmentList;
	}

	public void setStrDepartmentList(String strDepartmentList) {
		this.strDepartmentList = strDepartmentList;
	}

	public String getStrEmployeeList() {
		return strEmployeeList;
	}

	public void setStrEmployeeList(String strEmployeeList) {
		this.strEmployeeList = strEmployeeList;
	}
	
	public Date getCallAttendDate() {
		return callAttendDate;
	}

	public void setCallAttendDate(Date callAttendDate) {
		this.callAttendDate = callAttendDate;
	}

	public String getCallAttendTime() {
		return callAttendTime;
	}

	public void setCallAttendTime(String callAttendTime) {
		this.callAttendTime = callAttendTime;
	}

	public String getCallAttendEmployee() {
		return callAttendEmployee;
	}

	public void setCallAttendEmployee(String callAttendEmployee) {
		this.callAttendEmployee = callAttendEmployee;
	}

	public String getReasonForDelay() {
		return reasonForDelay;
	}

	public void setReasonForDelay(String reasonForDelay) {
		this.reasonForDelay = reasonForDelay;
	}

	public String getComplainStatus() {
		return complainStatus;
	}

	public void setComplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
	}

	public String getCallerArea() {
		return callerArea;
	}

	public void setCallerArea(String callerArea) {
		this.callerArea = callerArea;
	}

	public String getNoOfVehDamaged() {
		return noOfVehDamaged;
	}

	public void setNoOfVehDamaged(String noOfVehDamaged) {
		this.noOfVehDamaged = noOfVehDamaged;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getComplainerMobile1() {
		return complainerMobile1;
	}

	public void setComplainerMobile1(String complainerMobile1) {
		this.complainerMobile1 = complainerMobile1;
	}
	
}
