package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BidCompareDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4968275973588978900L;

	private String vendorName;
	private List<String> paramName;
	
	private Map<Long, Map<String, Long>> paramMap;

	public List<String> getParamName() {
		return paramName;
	}

	public void setParamName(List<String> paramName) {
		this.paramName = paramName;
	}

		

	public Map<Long, Map<String, Long>> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<Long, Map<String, Long>> paramMap) {
		this.paramMap = paramMap;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}
