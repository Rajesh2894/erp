/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pooja.maske
 *
 */
public class DistrictLocationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7385318418231172199L;

	@JsonProperty("DISTRICT")
	private String district;

	@JsonProperty("LATITUDE")
	private String latitude;

	@JsonProperty("LONGITUDE")
	private String longitude;

	@JsonProperty("STATE")
	private String state;

	private Long stateId;

	private Long distId;
	
	private Long blockId;
	
	@JsonProperty("BLOCK")
	private String block;
	
	private int count;
	
	@JsonProperty("RANGE")
	private String range;
	
	@JsonProperty("CATEGORY")
	private String category;
	
	@JsonProperty("RANK")
	private int rank;
	
	@JsonProperty("COLOR")
	private String color;
	
	
	
	
	
	
	
	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

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
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the distId
	 */
	public Long getDistId() {
		return distId;
	}

	/**
	 * @param distId the distId to set
	 */
	public void setDistId(Long distId) {
		this.distId = distId;
	}

}
