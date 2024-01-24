package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;

public class NursingHomeSummaryDto implements Serializable {

	private static final long serialVersionUID = -6556058243000597344L;
	private Long appId;
	private String serviceName;
	private String fName;
	private String lName;
	private String refNo;

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

}
