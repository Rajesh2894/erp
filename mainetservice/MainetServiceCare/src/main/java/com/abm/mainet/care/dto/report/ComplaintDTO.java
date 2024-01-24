package com.abm.mainet.care.dto.report;

import java.io.Serializable;

public class ComplaintDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String status;
	private String departmentName;
	private String departmentRegName;
	private String comlaintSubType;
	private String codIdOperLevel1;
	private String codIdOperLevel2;
	private String codIdOperLevel3;
	private String codIdOperLevel4;
	private String codIdOperLevel5;

	private String duration;
	private String slaDuration;
	private String slaDurationDiff;
	private String slaStatus;
	private String dateOfRequest;
	private String lastDateOfAction;
	private String feedbackDate;
	private String apmName;
	private String apaEmail;
	private String apaMobilno;
	private String feedbackRatings;
	private String feedback;
	private String slab;
    private double totalfeedback;
    private String referenceMode;
    private String apaAreaName;
   
    //144020
    private String complaintId;
	
  //#155098
    private String pendingWith;
    private String pendingFromDate;
    private String closedBy;
    private String closedDate;
    
    
	public String getReferenceMode() {
		return referenceMode;
	}

	public void setReferenceMode(String referenceMode) {
		this.referenceMode = referenceMode;
	}

    public double getTotalfeedback() {
		return totalfeedback;
	}

	public void setTotalfeedback(double totalfeedback) {
		this.totalfeedback = totalfeedback;
	}
	/**
	  */

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
    private Long empId;
    private Long totalnoOfComplaints;
    private Long closedComplaints;
    private Long pendingComplaints;
    private Long rejectedComplaints;
    private Long totalResolvedWithinSla;
    private Long totalResolvedBeyoundSla;
    private Long totalPendingWithinSla;
    private Long totalPendingbeyoundSla;
	

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentRegName() {
		return departmentRegName;
	}

	public void setDepartmentRegName(String departmentRegName) {
		this.departmentRegName = departmentRegName;
	}

	public String getComlaintSubType() {
		return comlaintSubType;
	}

	public void setComlaintSubType(String comlaintSubType) {
		this.comlaintSubType = comlaintSubType;
	}

	public String getCodIdOperLevel1() {
		return codIdOperLevel1;
	}

	public void setCodIdOperLevel1(String codIdOperLevel1) {
		this.codIdOperLevel1 = codIdOperLevel1;
	}

	public String getCodIdOperLevel2() {
		return codIdOperLevel2;
	}

	public void setCodIdOperLevel2(String codIdOperLevel2) {
		this.codIdOperLevel2 = codIdOperLevel2;
	}

	public String getCodIdOperLevel3() {
		return codIdOperLevel3;
	}

	public void setCodIdOperLevel3(String codIdOperLevel3) {
		this.codIdOperLevel3 = codIdOperLevel3;
	}

	public String getCodIdOperLevel4() {
		return codIdOperLevel4;
	}

	public void setCodIdOperLevel4(String codIdOperLevel4) {
		this.codIdOperLevel4 = codIdOperLevel4;
	}

	public String getCodIdOperLevel5() {
		return codIdOperLevel5;
	}

	public void setCodIdOperLevel5(String codIdOperLevel5) {
		this.codIdOperLevel5 = codIdOperLevel5;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSlaDuration() {
		return slaDuration;
	}

	public void setSlaDuration(String slaDuration) {
		this.slaDuration = slaDuration;
	}

	public String getSlaDurationDiff() {
		return slaDurationDiff;
	}

	public void setSlaDurationDiff(String slaDurationDiff) {
		this.slaDurationDiff = slaDurationDiff;
	}

	public String getSlaStatus() {
		return slaStatus;
	}

	public void setSlaStatus(String slaStatus) {
		this.slaStatus = slaStatus;
	}

	public String getDateOfRequest() {
		return dateOfRequest;
	}

	public void setDateOfRequest(String dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	public String getLastDateOfAction() {
		return lastDateOfAction;
	}

	public void setLastDateOfAction(String lastDateOfAction) {
		this.lastDateOfAction = lastDateOfAction;
	}

	public String getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(String feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getApmName() {
		return apmName;
	}

	public void setApmName(String apmName) {
		this.apmName = apmName;
	}

	public String getApaEmail() {
		return apaEmail;
	}

	public void setApaEmail(String apaEmail) {
		this.apaEmail = apaEmail;
	}

	public String getApaMobilno() {
		return apaMobilno;
	}

	public void setApaMobilno(String apaMobilno) {
		this.apaMobilno = apaMobilno;
	}

	public String getFeedbackRatings() {
		return feedbackRatings;
	}

	public void setFeedbackRatings(String feedbackRatings) {
		this.feedbackRatings = feedbackRatings;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getSlab() {
		return slab;
	}

	public void setSlab(String slab) {
		this.slab = slab;
	}

	public Long getCareWardNo() {
		return careWardNo;
	}

	public void setCareWardNo(Long careWardNo) {
		this.careWardNo = careWardNo;
	}

	public String getCareWardNoEng() {
		return careWardNoEng;
	}

	public void setCareWardNoEng(String careWardNoEng) {
		this.careWardNoEng = careWardNoEng;
	}

	public String getCareWardNoReg() {
		return careWardNoReg;
	}

	public void setCareWardNoReg(String careWardNoReg) {
		this.careWardNoReg = careWardNoReg;
	}

	public Long getCareWardNo1() {
		return careWardNo1;
	}

	public void setCareWardNo1(Long careWardNo1) {
		this.careWardNo1 = careWardNo1;
	}

	public String getCareWardNoEng1() {
		return careWardNoEng1;
	}

	public void setCareWardNoEng1(String careWardNoEng1) {
		this.careWardNoEng1 = careWardNoEng1;
	}

	public String getCareWardNoReg1() {
		return careWardNoReg1;
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
	
	
	public Long getTotalnoOfComplaints() {
		return totalnoOfComplaints;
	}

	public void setTotalnoOfComplaints(Long totalnoOfComplaints) {
		this.totalnoOfComplaints = totalnoOfComplaints;
	}

	public Long getClosedComplaints() {
		return closedComplaints;
	}

	public void setClosedComplaints(Long closedComplaints) {
		this.closedComplaints = closedComplaints;
	}

	public Long getPendingComplaints() {
		return pendingComplaints;
	}

	public void setPendingComplaints(Long pendingComplaints) {
		this.pendingComplaints = pendingComplaints;
	}

	public Long getRejectedComplaints() {
		return rejectedComplaints;
	}

	public void setRejectedComplaints(Long rejectedComplaints) {
		this.rejectedComplaints = rejectedComplaints;
	}
	
	

	public Long getTotalResolvedWithinSla() {
		return totalResolvedWithinSla;
	}

	public void setTotalResolvedWithinSla(Long totalResolvedWithinSla) {
		this.totalResolvedWithinSla = totalResolvedWithinSla;
	}

	public Long getTotalResolvedBeyoundSla() {
		return totalResolvedBeyoundSla;
	}

	public void setTotalResolvedBeyoundSla(Long totalResolvedBeyoundSla) {
		this.totalResolvedBeyoundSla = totalResolvedBeyoundSla;
	}

	public Long getTotalPendingWithinSla() {
		return totalPendingWithinSla;
	}

	public void setTotalPendingWithinSla(Long totalPendingWithinSla) {
		this.totalPendingWithinSla = totalPendingWithinSla;
	}

	public Long getTotalPendingbeyoundSla() {
		return totalPendingbeyoundSla;
	}

	public void setTotalPendingbeyoundSla(Long totalPendingbeyoundSla) {
		this.totalPendingbeyoundSla = totalPendingbeyoundSla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationId == null) ? 0 : applicationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplaintDTO other = (ComplaintDTO) obj;
		if (applicationId == null) {
			if (other.applicationId != null)
				return false;
		} else if (!applicationId.equals(other.applicationId))
			return false;
		return true;
	}

	public String getApaAreaName() {
		return apaAreaName;
	}

	public void setApaAreaName(String apaAreaName) {
		this.apaAreaName = apaAreaName;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(String pendingWith) {
		this.pendingWith = pendingWith;
	}

	public String getPendingFromDate() {
		return pendingFromDate;
	}

	public void setPendingFromDate(String pendingFromDate) {
		this.pendingFromDate = pendingFromDate;
	}

	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

}
