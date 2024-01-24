/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;

/**
 * @author pooja.maske
 *
 */
public class TbDistrictWiseODOPDto implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5230595491016592842L;
	private Long odopId;
	
	private String StateName;
	
	private Long districtCode; 

	private String districtName;
	
	private String odop;

	/**
	 * @return the odopId
	 */
	public Long getOdopId() {
		return odopId;
	}

	/**
	 * @param odopId the odopId to set
	 */
	public void setOdopId(Long odopId) {
		this.odopId = odopId;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return StateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		StateName = stateName;
	}

	/**
	 * @return the districtCode
	 */
	public Long getDistrictCode() {
		return districtCode;
	}

	/**
	 * @param districtCode the districtCode to set
	 */
	public void setDistrictCode(Long districtCode) {
		this.districtCode = districtCode;
	}

	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * @return the odop
	 */
	public String getOdop() {
		return odop;
	}

	/**
	 * @param odop the odop to set
	 */
	public void setOdop(String odop) {
		this.odop = odop;
	}
	
	

}
