package com.abm.mainet.property.dto;

import java.io.Serializable;

public class BBPSPaymentResponseDTO implements Serializable{


	
	private static final long serialVersionUID = 1L;
	
	private String timestamp;
	private String responseReason;
	private String responseCode;
	private String status;
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getResponseReason() {
		return responseReason;
	}
	public void setResponseReason(String responseReason) {
		this.responseReason = responseReason;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BBPSPaymentResponseDTO [timestamp=" + timestamp + ", responseReason=" + responseReason
				+ ", responseCode=" + responseCode + ", status=" + status + "]";
	}
	
	


}
