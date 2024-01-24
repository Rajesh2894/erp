/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * @author pooja.maske
 *
 */
public class TribalDistrictDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3847130073972965127L;
	private Long triDistId;

	private String stateName;

	private Long districtCode;

	private String districtName;

	private String isTribalDistrict;

	/**
	 * @return the triDistId
	 */
	public Long getTriDistId() {
		return triDistId;
	}

	/**
	 * @param triDistId the triDistId to set
	 */
	public void setTriDistId(Long triDistId) {
		this.triDistId = triDistId;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
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
	 * @return the isTribalDistrict
	 */
	public String getIsTribalDistrict() {
		return isTribalDistrict;
	}

	/**
	 * @param isTribalDistrict the isTribalDistrict to set
	 */
	public void setIsTribalDistrict(String isTribalDistrict) {
		this.isTribalDistrict = isTribalDistrict;
	}

}
