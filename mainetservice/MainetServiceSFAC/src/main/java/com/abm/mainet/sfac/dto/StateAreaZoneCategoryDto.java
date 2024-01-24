/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;

/**
 * @author pooja.maske
 *
 */
public class StateAreaZoneCategoryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6540062581366262538L;

	private Long staAreaRegId;

	private String State;

	private String stateCode;

	private String areaType;

	private String zone;

	/**
	 * @return the staAreaRegId
	 */
	public Long getStaAreaRegId() {
		return staAreaRegId;
	}

	/**
	 * @param staAreaRegId the staAreaRegId to set
	 */
	public void setStaAreaRegId(Long staAreaRegId) {
		this.staAreaRegId = staAreaRegId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return State;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		State = state;
	}



	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the areaType
	 */
	public String getAreaType() {
		return areaType;
	}

	/**
	 * @param areaType the areaType to set
	 */
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

}
