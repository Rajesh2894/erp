package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.List;

public class VoucherPostExternalListDTO implements Serializable {

	/**
	 * VoucherPostExternalListDTO
	 */
	private static final long serialVersionUID = -9038205557221109375L;
	
	List<VoucherPostExternalDTO> voucherextsysdto = null;
	
	//chek sum add userid || 
	
	private String checkSum;

	public List<VoucherPostExternalDTO> getVoucherextsysdto() {
		return voucherextsysdto;
	}

	public void setVoucherextsysdto(List<VoucherPostExternalDTO> voucherextsysdto) {
		this.voucherextsysdto = voucherextsysdto;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

}
