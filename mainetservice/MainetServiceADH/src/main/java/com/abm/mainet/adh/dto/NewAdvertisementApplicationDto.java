package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author vishwajeet.kumar
 * @since 25 sept 2019
 */

public class NewAdvertisementApplicationDto implements Serializable {

    private static final long serialVersionUID = -7045546357645565281L;

    private Long adhId;

    private Long apmApplicationId;

    private Long appCategoryId;

    private Long agencyId;

    private String locCatType;

    private Long licenseType;

    private String licenseNo;

    private String oldLicenseNo;

    private Date licenseFromDate;

    private Date licenseToDate;

    private Date licenseIssueDate;

    private Long locId;

    private Long adhZone1;

    private Long adhZone2;

    private Long adhZone3;

    private Long adhZone4;

    private Long adhZone5;

    private Long propTypeId;

    private String propNumber;

    private String tradeLicNo;

    private String propOwnerName;

    private String adhStatus;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String agencyName;

    private String adhZoneDesc1;

    private String adhZoneDesc2;

    private String adhZoneDesc3;

    private String adhZoneDesc4;

    private String adhZoneDesc5;

    private String licenseFromDateDesc;

    private String licenseToDateDesc;

    private String applicantName;
    private String licenseTypeDesc;
    private String locIdDesc;
    private String adhStatusDesc;

    private String mobileNo;

    private List<NewAdvertisementApplicationDetDto> newAdvertDetDtos = new ArrayList<NewAdvertisementApplicationDetDto>();

    private String propAddress;
    private Double propOutstandingAmt;
    
    private String assessmentCheckFlag;
    
    private NewAdvertisementReqDto newAdvertisementReqDto;
    
    private String ulbOwned;
    
    public String getLicenseFromDateStr() {
		return licenseFromDateStr;
	}

	public void setLicenseFromDateStr(String licenseFromDateStr) {
		this.licenseFromDateStr = licenseFromDateStr;
	}

	public String getLicenseToDateStr() {
		return licenseToDateStr;
	}

	public void setLicenseToDateStr(String licenseToDateStr) {
		this.licenseToDateStr = licenseToDateStr;
	}

	private String licenseFromDateStr;
    private String licenseToDateStr;
    
    

    public String getAssessmentCheckFlag() {
		return assessmentCheckFlag;
	}

	public void setAssessmentCheckFlag(String assessmentCheckFlag) {
		this.assessmentCheckFlag = assessmentCheckFlag;
	}

	public Long getAdhId() {
        return adhId;
    }

    public void setAdhId(Long adhId) {
        this.adhId = adhId;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Long getAppCategoryId() {
        return appCategoryId;
    }

    public void setAppCategoryId(Long appCategoryId) {
        this.appCategoryId = appCategoryId;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getLocCatType() {
        return locCatType;
    }

    public void setLocCatType(String locCatType) {
        this.locCatType = locCatType;
    }

    public Long getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(Long licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getOldLicenseNo() {
        return oldLicenseNo;
    }

    public void setOldLicenseNo(String oldLicenseNo) {
        this.oldLicenseNo = oldLicenseNo;
    }

    public Date getLicenseFromDate() {
        return licenseFromDate;
    }

    public void setLicenseFromDate(Date licenseFromDate) {
        this.licenseFromDate = licenseFromDate;
    }

    public Date getLicenseToDate() {
        return licenseToDate;
    }

    public void setLicenseToDate(Date licenseToDate) {
        this.licenseToDate = licenseToDate;
    }

    public Date getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(Date licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getAdhZone1() {
        return adhZone1;
    }

    public void setAdhZone1(Long adhZone1) {
        this.adhZone1 = adhZone1;
    }

    public Long getAdhZone2() {
        return adhZone2;
    }

    public void setAdhZone2(Long adhZone2) {
        this.adhZone2 = adhZone2;
    }

    public Long getAdhZone3() {
        return adhZone3;
    }

    public void setAdhZone3(Long adhZone3) {
        this.adhZone3 = adhZone3;
    }

    public Long getAdhZone4() {
        return adhZone4;
    }

    public void setAdhZone4(Long adhZone4) {
        this.adhZone4 = adhZone4;
    }

    public Long getAdhZone5() {
        return adhZone5;
    }

    public void setAdhZone5(Long adhZone5) {
        this.adhZone5 = adhZone5;
    }

    public Long getPropTypeId() {
        return propTypeId;
    }

    public void setPropTypeId(Long propTypeId) {
        this.propTypeId = propTypeId;
    }

    public String getPropNumber() {
        return propNumber;
    }

    public void setPropNumber(String propNumber) {
        this.propNumber = propNumber;
    }

    public String getTradeLicNo() {
        return tradeLicNo;
    }

    public void setTradeLicNo(String tradeLicNo) {
        this.tradeLicNo = tradeLicNo;
    }

    public String getPropOwnerName() {
        return propOwnerName;
    }

    public void setPropOwnerName(String propOwnerName) {
        this.propOwnerName = propOwnerName;
    }

    public String getAdhStatus() {
        return adhStatus;
    }

    public void setAdhStatus(String adhStatus) {
        this.adhStatus = adhStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAdhZoneDesc1() {
        return adhZoneDesc1;
    }

    public void setAdhZoneDesc1(String adhZoneDesc1) {
        this.adhZoneDesc1 = adhZoneDesc1;
    }

    public String getAdhZoneDesc2() {
        return adhZoneDesc2;
    }

    public void setAdhZoneDesc2(String adhZoneDesc2) {
        this.adhZoneDesc2 = adhZoneDesc2;
    }

    public String getAdhZoneDesc3() {
        return adhZoneDesc3;
    }

    public void setAdhZoneDesc3(String adhZoneDesc3) {
        this.adhZoneDesc3 = adhZoneDesc3;
    }

    public String getAdhZoneDesc4() {
        return adhZoneDesc4;
    }

    public void setAdhZoneDesc4(String adhZoneDesc4) {
        this.adhZoneDesc4 = adhZoneDesc4;
    }

    public String getAdhZoneDesc5() {
        return adhZoneDesc5;
    }

    public void setAdhZoneDesc5(String adhZoneDesc5) {
        this.adhZoneDesc5 = adhZoneDesc5;
    }

    public String getLicenseFromDateDesc() {
        return licenseFromDateDesc;
    }

    public void setLicenseFromDateDesc(String licenseFromDateDesc) {
        this.licenseFromDateDesc = licenseFromDateDesc;
    }

    public String getLicenseToDateDesc() {
        return licenseToDateDesc;
    }

    public void setLicenseToDateDesc(String licenseToDateDesc) {
        this.licenseToDateDesc = licenseToDateDesc;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getLicenseTypeDesc() {
        return licenseTypeDesc;
    }

    public void setLicenseTypeDesc(String licenseTypeDesc) {
        this.licenseTypeDesc = licenseTypeDesc;
    }

    public String getLocIdDesc() {
        return locIdDesc;
    }

    public void setLocIdDesc(String locIdDesc) {
        this.locIdDesc = locIdDesc;
    }

    public List<NewAdvertisementApplicationDetDto> getNewAdvertDetDtos() {
        return newAdvertDetDtos;
    }

    public void setNewAdvertDetDtos(List<NewAdvertisementApplicationDetDto> newAdvertDetDtos) {
        this.newAdvertDetDtos = newAdvertDetDtos;
    }

    public String getAdhStatusDesc() {
        return adhStatusDesc;
    }

    public void setAdhStatusDesc(String adhStatusDesc) {
        this.adhStatusDesc = adhStatusDesc;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPropAddress() {
        return propAddress;
    }

    public void setPropAddress(String propAddress) {
        this.propAddress = propAddress;
    }

    public Double getPropOutstandingAmt() {
        return propOutstandingAmt;
    }

    public void setPropOutstandingAmt(Double propOutstandingAmt) {
        this.propOutstandingAmt = propOutstandingAmt;
    }

	public NewAdvertisementReqDto getNewAdvertisementReqDto() {
		return newAdvertisementReqDto;
	}

	public void setNewAdvertisementReqDto(NewAdvertisementReqDto newAdvertisementReqDto) {
		this.newAdvertisementReqDto = newAdvertisementReqDto;
	}

	public String getUlbOwned() {
		return ulbOwned;
	}

	public void setUlbOwned(String ulbOwned) {
		this.ulbOwned = ulbOwned;
	}

}
