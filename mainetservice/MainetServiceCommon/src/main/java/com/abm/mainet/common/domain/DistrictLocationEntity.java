/**
 * 
 */
package com.abm.mainet.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name="DISTRICT_LOCATION")
public class DistrictLocationEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3943783372092289642L;
	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "district")
	private String district;

	
	@Column(name = "lat")
	private String lat;

	@Column(name = "lon")
	private String lon;
	
	@Column(name = "state")
	private String state;

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the lon
	 */
	public String getLon() {
		return lon;
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	

}
