package com.abm.mainet.common.integration.dto;

import java.io.Serializable;

public class ResponseMessageDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String responseCode;
	private String responseMessage;
	private String errorCode;
	private String errorDesc;
	private long responseTransactionNo;
	private String requestTransactionNo;
	
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
	
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public long getResponseTransactionNo() {
		return responseTransactionNo;
	}
	public void setResponseTransactionNo(long responseTransactionNo) {
		this.responseTransactionNo = responseTransactionNo;
	}
	public String getRequestTransactionNo() {
		return requestTransactionNo;
	}
	public void setRequestTransactionNo(String requestTransactionNo) {
		this.requestTransactionNo = requestTransactionNo;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
}
