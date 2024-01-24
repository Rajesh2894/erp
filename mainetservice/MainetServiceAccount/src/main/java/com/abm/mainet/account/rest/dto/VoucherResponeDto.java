package com.abm.mainet.account.rest.dto;

import java.io.Serializable;

public class VoucherResponeDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String systemVoucherRefNo;
	private String externalVoucherRefNo;
	private String successMsg;
	
	public VoucherResponeDto(String systemVoucherRefNo, String externalVoucherRefNo, String successMsg) {
		super();
		this.systemVoucherRefNo = systemVoucherRefNo;
		this.externalVoucherRefNo = externalVoucherRefNo;
		this.successMsg = successMsg;
	}

	public String getSystemVoucherRefNo() {
		return systemVoucherRefNo;
	}

	public void setSystemVoucherRefNo(String systemVoucherRefNo) {
		this.systemVoucherRefNo = systemVoucherRefNo;
	}

	public String getExternalVoucherRefNo() {
		return externalVoucherRefNo;
	}

	public void setExternalVoucherRefNo(String externalVoucherRefNo) {
		this.externalVoucherRefNo = externalVoucherRefNo;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	
	public static VoucherResponeDto getResponse(String systemVoucherRefNo, String externalVoucherRefNo, String successMsg) {
		return new VoucherResponeDto(systemVoucherRefNo,externalVoucherRefNo, successMsg);
	}
	
}
