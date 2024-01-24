package com.abm.mainet.cms.dto;

import java.io.Serializable;

public class DepartmentUserLogReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String empName;
	private String empLoginName;
	private String orgName;
	private String activity;
	private String activityDesc;
	private String loginDate;
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpLoginName() {
		return empLoginName;
	}
	public void setEmpLoginName(String empLoginName) {
		this.empLoginName = empLoginName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	
}
