package com.abm.mainet.additionalservices.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class NOCForBuildingPermissionExtDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -1263347711235244289L;

	@JsonIgnore
	private Long bpId;
	private Date date;
	private String fName;
	private String mName;
	private String lName;
	private String titleId;
	private String applicationType;
	private String Sex;
	@JsonIgnore
	private Long deptId;
	private String buildingPermissionAppNo;
	private String surveyNo;
	private String plotNo;
	private String citiySurveyNo;
	private String address;
	private String location;
	private Double plotArea;
	private Double builtUpArea;
	@JsonIgnore
	private String birthRegstatus;
	@JsonIgnore
	private String birthRegremark;
	@JsonIgnore
	private String lgIpMac;
	@JsonIgnore
	private String lgIpMacUpd;
	@JsonIgnore
	private Date lmoddate;
	@JsonIgnore
	private Long modeCpdId;
	@JsonIgnore
	private Long smServiceId;
	@JsonIgnore
	private Long updatedBy;
	@JsonIgnore
	private Date updatedDate;
	@JsonIgnore
	private Long userId;
	private String applicationId;
	@JsonIgnore
	private Long serviceId;
	private Long orgId;
	@JsonIgnore
	private int langId;
	private Long applnId;
	private Long apmApplicationId;
	private String wfStatus;
	private String status;
	@JsonIgnore
	private Date fromDate;
	@JsonIgnore
	private Date toDate;
	
	@JsonIgnore
	List<DocumentDetailsVO> docList = new ArrayList<>();
	@JsonIgnore
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

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
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
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
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

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	

}
