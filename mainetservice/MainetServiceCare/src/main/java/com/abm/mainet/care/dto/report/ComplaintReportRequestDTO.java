package com.abm.mainet.care.dto.report;

import java.io.Serializable;
import java.util.Date;

public class ComplaintReportRequestDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long orgId;
    private Date fromDate;
    private Date toDate;
    private Long department;
    private Long complaintType;
    private String status;
    private Integer feedbackRating;

    private Long codIdOperLevel1;
    private Long codIdOperLevel2;
    private Long codIdOperLevel3;
    private Long codIdOperLevel4;
    private Long codIdOperLevel5;

    private Long reportType;
    private Long fromSlab;
    private Long toSlab;
    private Integer slabLevels;
    private Long slaStatus;
    private long langId;

    // private Long careWardNo;
    private String careWardNoEng;
    private String careWardNoReg;
    private Long careWardNo1;
    private Long careWardNo2;
    private Long careWardNo3;
    private Long careWardNo4;
    private Long careWardNo5;
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

    private char reportName;
    private char reports;

    private String alertType;
    private String referenceMode;
    private String empId;// contains empId and MOBILE and PORTAL

    public String getReferenceMode() {
        return referenceMode;
    }

    public void setReferenceMode(String referenceMode) {
        this.referenceMode = referenceMode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Long getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(Long complaintType) {
        this.complaintType = complaintType;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(Integer feedbackRating) {
        this.feedbackRating = feedbackRating;
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

    public Long getReportType() {
        return reportType;
    }

    public void setReportType(Long reportType) {
        this.reportType = reportType;
    }

    public Long getFromSlab() {
        return fromSlab;
    }

    public void setFromSlab(Long fromSlab) {
        this.fromSlab = fromSlab;
    }

    public Long getToSlab() {
        return toSlab;
    }

    public Long getCareWardNo2() {
        return careWardNo2;
    }

    public void setCareWardNo2(Long careWardNo2) {
        this.careWardNo2 = careWardNo2;
    }

    public Long getCareWardNo3() {
        return careWardNo3;
    }

    public void setCareWardNo3(Long careWardNo3) {
        this.careWardNo3 = careWardNo3;
    }

    public Long getCareWardNo4() {
        return careWardNo4;
    }

    public void setCareWardNo4(Long careWardNo4) {
        this.careWardNo4 = careWardNo4;
    }

    public Long getCareWardNo5() {
        return careWardNo5;
    }

    public void setCareWardNo5(Long careWardNo5) {
        this.careWardNo5 = careWardNo5;
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

    public void setToSlab(Long toSlab) {
        this.toSlab = toSlab;
    }

    public Integer getSlabLevels() {
        return slabLevels;
    }

    public void setSlabLevels(Integer slabLevels) {
        this.slabLevels = slabLevels;
    }

    public Long getSlaStatus() {
        return slaStatus;
    }

    public void setSlaStatus(Long slaStatus) {
        this.slaStatus = slaStatus;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(long langId) {
        this.langId = langId;
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

    public char getReportName() {
        return reportName;
    }

    public void setReportName(char reportName) {
        this.reportName = reportName;
    }

    public char getReports() {
        return reports;
    }

    public void setReports(char reports) {
        this.reports = reports;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

}
