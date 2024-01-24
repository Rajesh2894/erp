package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author cherupelli.srikanth
 * @since 31 March 2023
 */
public class VoucherModeInfoDto implements Serializable{

	private static final long serialVersionUID = 6002291116389196019L;
	
	private String voucherNumber;

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	
	
}
