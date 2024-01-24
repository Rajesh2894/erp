package com.abm.mainet.account.rest.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author vishwanath.s
 *
 */
public class ResponseDto implements Serializable {

	private static final long serialVersionUID = -2957797920404967439L;

	private String billNo;
	private String successMsg;
	private List<String> errorMsg;
	private HttpStatus statusCode;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ResponseDto(String billNo, String successMsg, List<String> errorMsg, HttpStatus statusCode) {
		super();
		this.billNo = billNo;
		this.successMsg = successMsg;
		this.errorMsg = errorMsg;
		this.statusCode = statusCode;
	}

	public String getBillNo() {
		return billNo;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public List<String> getErrorMsg() {
		return errorMsg;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public static ResponseDto getResponse(String billNo, String successMsg, List<String> errorMsg, HttpStatus status) {

		return new ResponseDto(billNo, successMsg, errorMsg, status);
	}

}
