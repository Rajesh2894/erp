package com.abm.mainet.care.dto.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javaxt.utils.Array;

public class ComplaintReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgName;
	private String title;
	private String fromDate;
	private String toDate;
	private String departmentName;
	private String complaintType;
	private String dateTime;
	private String status;
	private String slaStatus;
	private Long codIdOperLevel1;
	private Long codIdOperLevel2;
	private Long codIdOperLevel3;
	private Long codIdOperLevel4;
	private Long codIdOperLevel5;
	private boolean showDepartment;
	private boolean showComplaintType;
	private boolean showStatus;
	private boolean showSlaStatus;

	private Long careWardNo;
	private String careWardNoEng;
	private String careWardNoReg;
	private Long careWardNo1;
	private String careWardNoEng1;
	private String careWardNoReg1;

	private Long locId;
	private String locNameEng;
	private String locNameReg;
	private String pincode;
	private Long numberOfDay;
	
	private String districtNameEng;
	private String stateNameEng;
	private String complaintDesc;

	

	private Set<ComplaintDTO> complaints = new LinkedHashSet<>();
	private Map<String, Set<ComplaintDTO>> complaintsGroups = new LinkedHashMap<>();
	private Map<SummaryField, Set<Map<String, Long>>> complaintSummary = new LinkedHashMap<>();
	private Set<ComplaintGradeSummary> complaintGradeSummary = new LinkedHashSet<>();
	private List<ComplaintDTO> complaintList = new ArrayList<>();
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Set<ComplaintDTO> getComplaints() {
		return complaints;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSlaStatus() {
		return slaStatus;
	}

	public void setSlaStatus(String slaStatus) {
		this.slaStatus = slaStatus;
	}

	public Long getCodIdOperLevel1() {
		return codIdOperLevel1;
	}

	public void setCodIdOperLevel1(Long codIdOperLevel1) {
		this.codIdOperLevel1 = codIdOperLevel1;
	}

	public Long getCodIdOperLevel2() {
		return codIdOperLevel2;
	}

	public void setCodIdOperLevel2(Long codIdOperLevel2) {
		this.codIdOperLevel2 = codIdOperLevel2;
	}

	public Long getCodIdOperLevel3() {
		return codIdOperLevel3;
	}

	public void setCodIdOperLevel3(Long codIdOperLevel3) {
		this.codIdOperLevel3 = codIdOperLevel3;
	}

	public Long getCodIdOperLevel4() {
		return codIdOperLevel4;
	}

	public void setCodIdOperLevel4(Long codIdOperLevel4) {
		this.codIdOperLevel4 = codIdOperLevel4;
	}

	public Long getCodIdOperLevel5() {
		return codIdOperLevel5;
	}

	public void setCodIdOperLevel5(Long codIdOperLevel5) {
		this.codIdOperLevel5 = codIdOperLevel5;
	}

	public boolean isShowDepartment() {
		return showDepartment;
	}

	public void setShowDepartment(boolean showDepartment) {
		this.showDepartment = showDepartment;
	}

	public boolean isShowComplaintType() {
		return showComplaintType;
	}

	public void setShowComplaintType(boolean showComplaintType) {
		this.showComplaintType = showComplaintType;
	}

	public boolean isShowStatus() {
		return showStatus;
	}

	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}

	public boolean isShowSlaStatus() {
		return showSlaStatus;
	}

	public void setShowSlaStatus(boolean showSlaStatus) {
		this.showSlaStatus = showSlaStatus;
	}

	public Long getCareWardNo() {
		return careWardNo;
	}

	public String getCareWardNoEng() {
		return careWardNoEng;
	}

	public String getCareWardNoReg() {
		return careWardNoReg;
	}

	public Long getCareWardNo1() {
		return careWardNo1;
	}

	public String getCareWardNoEng1() {
		return careWardNoEng1;
	}

	public String getCareWardNoReg1() {
		return careWardNoReg1;
	}

	public void setCareWardNo(Long careWardNo) {
		this.careWardNo = careWardNo;
	}

	public void setCareWardNoEng(String careWardNoEng) {
		this.careWardNoEng = careWardNoEng;
	}

	public void setCareWardNoReg(String careWardNoReg) {
		this.careWardNoReg = careWardNoReg;
	}

	public void setCareWardNo1(Long careWardNo1) {
		this.careWardNo1 = careWardNo1;
	}

	public void setCareWardNoEng1(String careWardNoEng1) {
		this.careWardNoEng1 = careWardNoEng1;
	}

	public void setCareWardNoReg1(String careWardNoReg1) {
		this.careWardNoReg1 = careWardNoReg1;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getLocNameEng() {
		return locNameEng;
	}

	public void setLocNameEng(String locNameEng) {
		this.locNameEng = locNameEng;
	}

	public String getLocNameReg() {
		return locNameReg;
	}

	public void setLocNameReg(String locNameReg) {
		this.locNameReg = locNameReg;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Long getNumberOfDay() {
		return numberOfDay;
	}

	public void setNumberOfDay(Long numberOfDay) {
		this.numberOfDay = numberOfDay;
	}

	public void setComplaints(Set<ComplaintDTO> complaints) {
		this.complaints = complaints;
	}

	public ComplaintReportDTO addComplaint(ComplaintDTO complaint) {
		this.complaints.add(complaint);
		return this;
	}

	public Map<String, Set<ComplaintDTO>> getComplaintsGroups() {
		return complaintsGroups;
	}

	public void setComplaintsGroups(Map<String, Set<ComplaintDTO>> complaintsGroups) {
		this.complaintsGroups = complaintsGroups;
	}

	public Map<SummaryField, Set<Map<String, Long>>> getComplaintSummary() {
		return complaintSummary;
	}

	public void setComplaintSummary(Map<SummaryField, Set<Map<String, Long>>> complaintSummary) {
		this.complaintSummary = complaintSummary;
	}

	public Set<ComplaintGradeSummary> getComplaintGradeSummary() {
		return complaintGradeSummary;
	}

	public void setComplaintGradeSummary(Set<ComplaintGradeSummary> complaintGradeSummary) {
		this.complaintGradeSummary = complaintGradeSummary;
	}

	public String getDistrictNameEng() {
		return districtNameEng;
	}

	public void setDistrictNameEng(String districtNameEng) {
		this.districtNameEng = districtNameEng;
	}

	public String getStateNameEng() {
		return stateNameEng;
	}

	public void setStateNameEng(String stateNameEng) {
		this.stateNameEng = stateNameEng;
	}

	public String getComplaintDesc() {
		return complaintDesc;
	}

	public void setComplaintDesc(String complaintDesc) {
		this.complaintDesc = complaintDesc;
	}

	public List<ComplaintDTO> getComplaintList() {
		return complaintList;
	}

	public void setComplaintList(List<ComplaintDTO> complaintList) {
		this.complaintList = complaintList;
	}

	
	
}
