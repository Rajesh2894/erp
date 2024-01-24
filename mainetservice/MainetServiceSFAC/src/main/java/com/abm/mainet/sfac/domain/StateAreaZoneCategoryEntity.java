/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "Tb_SFAC_State_Area_zone_Category")
public class StateAreaZoneCategoryEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7017762795154206320L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "STA_AREA_REG_ID", unique = true, nullable = false)
	private Long staAreaRegId;

	@Column(name = "STATE")
	private String State;

	@Column(name = "STATE_CODE")
	private String stateCode;

	@Column(name = "AREA_TYPE")
	private String areaType;

	@Column(name = "ZONE")
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

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_State_Area_zone_Category", "STA_AREA_REG_ID" };
	}

}
