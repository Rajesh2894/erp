/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;

/**
 * @author pooja.maske
 *
 */
public class CBBOMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -588040363672690158L;

	private Long cbboId;

	private String cbboName;

	private String cbboUniqueId;

	private Long allocationCategory;

	private String IAName;

	private Long alcYear;

	private Date alcYearToCBBO;

	private Long sdb1;

	private Long sdb2;

	private Long sdb3;

	private String statecategory;

	private String region;

	private String isAspirationalDist;

	private String isTribalDist;

	private Long dmcApproval;

	private String odop;

	private String cBBOContactPerson;

	private String contactNo;

	private String emailId;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long langId;

	private Long iaId;

	private String panNo;

	private String iaAlcYear;

	private String alYrCbbo;

	private String status;

	private String address;

	private String pinCode;

	private Long typeofPromAgen;

	private List<CBBOMastDetailDto> cbboDetDto = new ArrayList<>();

	private Long cbboAppoitmentYr;

	private Long deptId;

	private Date appPendingDate;

	private String approved;

	private Long fpoAllocationTarget;

	private Long regPendCount;

	private Long regCount;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private Long applicationId;

	private String mobileNo;

	private String email;

	private String remark;

	private String appStatus;

	private String state;

	private String userName;

	private String statusDesc;

	private String appYear;

	private Long cbId;

	private String activeInactiveStatus;

	private String summaryStatus;

	/**
	 * @return the cbboId
	 */
	public Long getCbboId() {
		return cbboId;
	}

	/**
	 * @param cbboId the cbboId to set
	 */
	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	/**
	 * @return the cbboName
	 */
	public String getCbboName() {
		return cbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	/**
	 * @return the cbboUniqueId
	 */
	public String getCbboUniqueId() {
		return cbboUniqueId;
	}

	/**
	 * @param cbboUniqueId the cbboUniqueId to set
	 */
	public void setCbboUniqueId(String cbboUniqueId) {
		this.cbboUniqueId = cbboUniqueId;
	}

	/**
	 * @return the allocationCategory
	 */
	public Long getAllocationCategory() {
		return allocationCategory;
	}

	/**
	 * @param allocationCategory the allocationCategory to set
	 */
	public void setAllocationCategory(Long allocationCategory) {
		this.allocationCategory = allocationCategory;
	}

	/**
	 * @return the iAName
	 */
	public String getIAName() {
		return IAName;
	}

	/**
	 * @param iAName the iAName to set
	 */
	public void setIAName(String iAName) {
		IAName = iAName;
	}

	/**
	 * @return the alcYear
	 */
	public Long getAlcYear() {
		return alcYear;
	}

	/**
	 * @param alcYear the alcYear to set
	 */
	public void setAlcYear(Long alcYear) {
		this.alcYear = alcYear;
	}

	/**
	 * @return the alcYearToCBBO
	 */
	public Date getAlcYearToCBBO() {
		return alcYearToCBBO;
	}

	/**
	 * @param alcYearToCBBO the alcYearToCBBO to set
	 */
	public void setAlcYearToCBBO(Date alcYearToCBBO) {
		this.alcYearToCBBO = alcYearToCBBO;
	}

	/**
	 * @return the sdb1
	 */
	public Long getSdb1() {
		return sdb1;
	}

	/**
	 * @param sdb1 the sdb1 to set
	 */
	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	/**
	 * @return the sdb2
	 */
	public Long getSdb2() {
		return sdb2;
	}

	/**
	 * @param sdb2 the sdb2 to set
	 */
	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}

	/**
	 * @return the sdb3
	 */
	public Long getSdb3() {
		return sdb3;
	}

	/**
	 * @param sdb3 the sdb3 to set
	 */
	public void setSdb3(Long sdb3) {
		this.sdb3 = sdb3;
	}

	/**
	 * @return the statecategory
	 */
	public String getStatecategory() {
		return statecategory;
	}

	/**
	 * @param statecategory the statecategory to set
	 */
	public void setStatecategory(String statecategory) {
		this.statecategory = statecategory;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the isAspirationalDist
	 */
	public String getIsAspirationalDist() {
		return isAspirationalDist;
	}

	/**
	 * @param isAspirationalDist the isAspirationalDist to set
	 */
	public void setIsAspirationalDist(String isAspirationalDist) {
		this.isAspirationalDist = isAspirationalDist;
	}

	/**
	 * @return the isTribalDist
	 */
	public String getIsTribalDist() {
		return isTribalDist;
	}

	/**
	 * @param isTribalDist the isTribalDist to set
	 */
	public void setIsTribalDist(String isTribalDist) {
		this.isTribalDist = isTribalDist;
	}

	/**
	 * @return the dmcApproval
	 */
	public Long getDmcApproval() {
		return dmcApproval;
	}

	/**
	 * @param dmcApproval the dmcApproval to set
	 */
	public void setDmcApproval(Long dmcApproval) {
		this.dmcApproval = dmcApproval;
	}

	/**
	 * @return the odop
	 */
	public String getOdop() {
		return odop;
	}

	/**
	 * @param odop the odop to set
	 */
	public void setOdop(String odop) {
		this.odop = odop;
	}

	/**
	 * @return the cBBOContactPerson
	 */
	public String getcBBOContactPerson() {
		return cBBOContactPerson;
	}

	/**
	 * @param cBBOContactPerson the cBBOContactPerson to set
	 */
	public void setcBBOContactPerson(String cBBOContactPerson) {
		this.cBBOContactPerson = cBBOContactPerson;
	}

	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	 * @return the langId
	 */
	public Long getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(Long langId) {
		this.langId = langId;
	}

	/**
	 * @return the iaId
	 */
	public Long getIaId() {
		return iaId;
	}

	/**
	 * @param iaId the iaId to set
	 */
	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	/**
	 * @return the iaAlcYear
	 */
	public String getIaAlcYear() {
		return iaAlcYear;
	}

	/**
	 * @param iaAlcYear the iaAlcYear to set
	 */
	public void setIaAlcYear(String iaAlcYear) {
		this.iaAlcYear = iaAlcYear;
	}

	/**
	 * @return the alYrCbbo
	 */
	public String getAlYrCbbo() {
		return alYrCbbo;
	}

	/**
	 * @param alYrCbbo the alYrCbbo to set
	 */
	public void setAlYrCbbo(String alYrCbbo) {
		this.alYrCbbo = alYrCbbo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the typeofPromAgen
	 */
	public Long getTypeofPromAgen() {
		return typeofPromAgen;
	}

	/**
	 * @param typeofPromAgen the typeofPromAgen to set
	 */
	public void setTypeofPromAgen(Long typeofPromAgen) {
		this.typeofPromAgen = typeofPromAgen;
	}

	/**
	 * @return the cbboDetDto
	 */
	public List<CBBOMastDetailDto> getCbboDetDto() {
		return cbboDetDto;
	}

	/**
	 * @param cbboDetDto the cbboDetDto to set
	 */
	public void setCbboDetDto(List<CBBOMastDetailDto> cbboDetDto) {
		this.cbboDetDto = cbboDetDto;
	}

	/**
	 * @return the cbboAppoitmentYr
	 */
	public Long getCbboAppoitmentYr() {
		return cbboAppoitmentYr;
	}

	/**
	 * @param cbboAppoitmentYr the cbboAppoitmentYr to set
	 */
	public void setCbboAppoitmentYr(Long cbboAppoitmentYr) {
		this.cbboAppoitmentYr = cbboAppoitmentYr;
	}

	/**
	 * @return the deptId
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the appPendingDate
	 */
	public Date getAppPendingDate() {
		return appPendingDate;
	}

	/**
	 * @param appPendingDate the appPendingDate to set
	 */
	public void setAppPendingDate(Date appPendingDate) {
		this.appPendingDate = appPendingDate;
	}

	/**
	 * @return the approved
	 */
	public String getApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(String approved) {
		this.approved = approved;
	}

	/**
	 * @return the fpoAllocationTarget
	 */
	public Long getFpoAllocationTarget() {
		return fpoAllocationTarget;
	}

	/**
	 * @param fpoAllocationTarget the fpoAllocationTarget to set
	 */
	public void setFpoAllocationTarget(Long fpoAllocationTarget) {
		this.fpoAllocationTarget = fpoAllocationTarget;
	}

	/**
	 * @return the regPendCount
	 */
	public Long getRegPendCount() {
		return regPendCount;
	}

	/**
	 * @param regPendCount the regPendCount to set
	 */
	public void setRegPendCount(Long regPendCount) {
		this.regPendCount = regPendCount;
	}

	/**
	 * @return the regCount
	 */
	public Long getRegCount() {
		return regCount;
	}

	/**
	 * @param regCount the regCount to set
	 */
	public void setRegCount(Long regCount) {
		this.regCount = regCount;
	}

	/**
	 * @return the applicantDetailDto
	 */
	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	/**
	 * @param applicantDetailDto the applicantDetailDto to set
	 */
	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
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
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
	 * @return the appYear
	 */
	public String getAppYear() {
		return appYear;
	}

	/**
	 * @param appYear the appYear to set
	 */
	public void setAppYear(String appYear) {
		this.appYear = appYear;
	}

	/**
	 * @return the cbId
	 */
	public Long getCbId() {
		return cbId;
	}

	/**
	 * @param cbId the cbId to set
	 */
	public void setCbId(Long cbId) {
		this.cbId = cbId;
	}

	/**
	 * @return the activeInactiveStatus
	 */
	public String getActiveInactiveStatus() {
		return activeInactiveStatus;
	}

	/**
	 * @param activeInactiveStatus the activeInactiveStatus to set
	 */
	public void setActiveInactiveStatus(String activeInactiveStatus) {
		this.activeInactiveStatus = activeInactiveStatus;
	}

	/**
	 * @return the summaryStatus
	 */
	public String getSummaryStatus() {
		return summaryStatus;
	}

	/**
	 * @param summaryStatus the summaryStatus to set
	 */
	public void setSummaryStatus(String summaryStatus) {
		this.summaryStatus = summaryStatus;
	}

}
