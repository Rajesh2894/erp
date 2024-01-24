package com.abm.mainet.water.dto;

import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class WaterApplicationAcknowledgementDTO {
	private String applicationId;
	private String serviceName;
	
	private String wtConnDueDt;
	
	private String organizationName;
	
	private String applicantName;
	
	private String departmentName;
	
	private String appTime;
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getWtConnDueDt() {
		return wtConnDueDt;
	}

	public void setWtConnDueDt(String wtConnDueDt) {
		this.wtConnDueDt = wtConnDueDt;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	
}
