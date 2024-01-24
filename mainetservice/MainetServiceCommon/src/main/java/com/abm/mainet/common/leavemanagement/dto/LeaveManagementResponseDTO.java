package com.abm.mainet.common.leavemanagement.dto;

import java.io.Serializable;

public class LeaveManagementResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2627224369898388814L;
	
	private String responseCode;
	private String responseMessage;
	
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
