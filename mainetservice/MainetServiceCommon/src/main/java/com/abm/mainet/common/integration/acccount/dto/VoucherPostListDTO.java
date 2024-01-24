package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.List;

public class VoucherPostListDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9038205557221109375L;
	
	List<VoucherPostDTO> voucherdto = null;

	public List<VoucherPostDTO> getVoucherdto() {
		return voucherdto;
	}

	public void setVoucherdto(List<VoucherPostDTO> voucherdto) {
		this.voucherdto = voucherdto;
	}
	
}
