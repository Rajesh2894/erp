package com.abm.mainet.mobile.dto;

import java.io.Serializable;

public class OptionDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long oId;
	String optionEn;
	String optionRg;
	public long getoId() {
		return oId;
	}
	public void setoId(long oId) {
		this.oId = oId;
	}
	public String getOptionEn() {
		return optionEn;
	}
	public void setOptionEn(String optionEn) {
		this.optionEn = optionEn;
	}
	public String getOptionRg() {
		return optionRg;
	}
	public void setOptionRg(String optionRg) {
		this.optionRg = optionRg;
	}
}
