package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class NOCForBuildingPermissionDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -1263347711235244289L;

	private Long bpId;
	private Date date;
	private String fName;
	private String mName;
	private String lName;
	private Long titleId;
	private Long applicationType;
	private String Sex;
	private Long deptId;
	private String buildingPermissionAppNo;
	private String surveyNo;
	private String plotNo;
	private String citiySurveyNo;
	private String address;
	private Long location;
	private Double plotArea;
	private Double builtUpArea;
	private String birthRegstatus;
	private String birthRegremark;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private Long modeCpdId;
	private Long smServiceId;
	private Long updatedBy;
	private Date updatedDate;
	private Long userId;
	private String applicationId;
	private Long serviceId;
	private Long orgId;
	private int langId;
	private Long applnId;
	private Long apmApplicationId;
	private String wfStatus;
	private String status;
	private Date fromDate;
	private Date toDate;
	
	private String applicantAddress;
	private Long nocType;
	private Long usageType1;
	private Long usageType2;
	private Long usageType3;
	private Long usageType4;
	private Long usageType5;
	private String refNo;
	private String fNo;
	private Date refDate;
	private String purchaserName;
	private String purchaserAddress;
	private String sellerName;
	private String sellerAddress;
	private String east;
	private String west;
	private String north;
	private String south;
	private Long  contactNumber;
	private String applicantEmail;
	private String proDesc;
	private Date saleDate;
	private Double malabaCharge;
	private Date scrutinyDate;
	private String saleD;
	private String scrutinyD;
	private String updateD;
	private String usaget;
	private String refdat;
	private String title;
	private String finacialYear;
	private String landOwner;
	private String regNo;
	private String loiNo;
	private String refNoc;
	
	
	
	List<DocumentDetailsVO> docList = new ArrayList<>();
	private List<AttachDocs> fetchIntDocList = new ArrayList<>();
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getBpId() {
		return bpId;
	}

	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public Long getApplnId() {
		return applnId;
	}

	public void setApplnId(Long applnId) {
		this.applnId = applnId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getBirthRegstatus() {
		return birthRegstatus;
	}

	public void setBirthRegstatus(String birthRegstatus) {
		this.birthRegstatus = birthRegstatus;
	}

	public String getBirthRegremark() {
		return birthRegremark;
	}

	public void setBirthRegremark(String birthRegremark) {
		this.birthRegremark = birthRegremark;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Long getModeCpdId() {
		return modeCpdId;
	}

	public void setModeCpdId(Long modeCpdId) {
		this.modeCpdId = modeCpdId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	public Long getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(Long applicationType) {
		this.applicationType = applicationType;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getBuildingPermissionAppNo() {
		return buildingPermissionAppNo;
	}

	public void setBuildingPermissionAppNo(String buildingPermissionAppNo) {
		this.buildingPermissionAppNo = buildingPermissionAppNo;
	}

	public String getSurveyNo() {
		return surveyNo;
	}

	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}

	public String getPlotNo() {
		return plotNo;
	}

	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}

	public String getCitiySurveyNo() {
		return citiySurveyNo;
	}

	public void setCitiySurveyNo(String citiySurveyNo) {
		this.citiySurveyNo = citiySurveyNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public Double getPlotArea() {
		return plotArea;
	}

	public void setPlotArea(Double plotArea) {
		this.plotArea = plotArea;
	}

	public Double getBuiltUpArea() {
		return builtUpArea;
	}

	public void setBuiltUpArea(Double builtUpArea) {
		this.builtUpArea = builtUpArea;
	}

	public List<DocumentDetailsVO> getDocList() {
		return docList;
	}

	public void setDocList(List<DocumentDetailsVO> docList) {
		this.docList = docList;
	}

	public List<AttachDocs> getFetchIntDocList() {
		return fetchIntDocList;
	}

	public void setFetchIntDocList(List<AttachDocs> fetchIntDocList) {
		this.fetchIntDocList = fetchIntDocList;
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	public Long getNocType() {
		return nocType;
	}

	public void setNocType(Long nocType) {
		this.nocType = nocType;
	}

	public Long getUsageType1() {
		return usageType1;
	}

	public void setUsageType1(Long usageType1) {
		this.usageType1 = usageType1;
	}

	public Long getUsageType2() {
		return usageType2;
	}

	public void setUsageType2(Long usageType2) {
		this.usageType2 = usageType2;
	}

	public Long getUsageType3() {
		return usageType3;
	}

	public void setUsageType3(Long usageType3) {
		this.usageType3 = usageType3;
	}

	public Long getUsageType4() {
		return usageType4;
	}

	public void setUsageType4(Long usageType4) {
		this.usageType4 = usageType4;
	}

	public Long getUsageType5() {
		return usageType5;
	}

	public void setUsageType5(Long usageType5) {
		this.usageType5 = usageType5;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getfNo() {
		return fNo;
	}

	public void setfNo(String fNo) {
		this.fNo = fNo;
	}

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserAddress() {
		return purchaserAddress;
	}

	public void setPurchaserAddress(String purchaserAddress) {
		this.purchaserAddress = purchaserAddress;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getEast() {
		return east;
	}

	public void setEast(String east) {
		this.east = east;
	}

	public String getWest() {
		return west;
	}

	public void setWest(String west) {
		this.west = west;
	}

	public String getNorth() {
		return north;
	}

	public void setNorth(String north) {
		this.north = north;
	}

	public String getSouth() {
		return south;
	}

	public void setSouth(String south) {
		this.south = south;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getApplicantEmail() {
		return applicantEmail;
	}

	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	
	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Double getMalabaCharge() {
		return malabaCharge;
	}

	public void setMalabaCharge(Double malabaCharge) {
		this.malabaCharge = malabaCharge;
	}

	public Date getScrutinyDate() {
		return scrutinyDate;
	}

	public void setScrutinyDate(Date scrutinyDate) {
		this.scrutinyDate = scrutinyDate;
	}

	public String getSaleD() {
		return saleD;
	}

	public void setSaleD(String saleD) {
		this.saleD = saleD;
	}

	public String getScrutinyD() {
		return scrutinyD;
	}

	public void setScrutinyD(String scrutinyD) {
		this.scrutinyD = scrutinyD;
	}

	public String getUpdateD() {
		return updateD;
	}

	public void setUpdateD(String updateD) {
		this.updateD = updateD;
	}

	public String getUsaget() {
		return usaget;
	}

	public void setUsaget(String usaget) {
		this.usaget = usaget;
	}

	public String getRefdat() {
		return refdat;
	}

	public void setRefdat(String refdat) {
		this.refdat = refdat;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFinacialYear() {
		return finacialYear;
	}

	public void setFinacialYear(String finacialYear) {
		this.finacialYear = finacialYear;
	}

	public String getLandOwner() {
		return landOwner;
	}

	public void setLandOwner(String landOwner) {
		this.landOwner = landOwner;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public String getRefNoc() {
		return refNoc;
	}

	public void setRefNoc(String refNoc) {
		this.refNoc = refNoc;
	}

	

	


	
	
}

