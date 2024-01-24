package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

public class CFCApplicationStatusDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5219217465861043965L;
	private Long orgId;
	private String fName;
	private String mName;
	private String lName;
	private Date appDate;
	private String applicationDate;
	private Long deptId;
	private Long serviceId;
	private Long appNo;

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Long getAppNo() {
		return appNo;
	}

	public void setAppNo(Long appNo) {
		this.appNo = appNo;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

}
