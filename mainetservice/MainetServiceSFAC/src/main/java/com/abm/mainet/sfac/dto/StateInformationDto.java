/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pooja.maske
 *
 */
public class StateInformationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 567295353226848955L;

	private Long stId;

	private Long state;

	private Long stateCode;

	private String stShortCode;

	private Long district;

	private Long distCode;

	private Long areaType;

	private Long zone;

	private Long odop;

	private Long aspirationalDist;

	private Long tribalDist;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long langId;

	private String stateName;

	private String distName;
	
	private String areaTypeDesc;


	String aspirationallDist ;
	String odopValue;
	String tirbalDist;
	String areaTypeValue;
	String region;

	/*
	 * @return the stId
	 */
	public Long getStId() {
		return stId;
	}

	/**
	 * @param stId the stId to set
	 */
	public void setStId(Long stId) {
		this.stId = stId;
	}

	/**
	 * @return the state
	 */
	public Long getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Long state) {
		this.state = state;
	}

	/**
	 * @return the stateCode
	 */
	public Long getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(Long stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the stShortCode
	 */
	public String getStShortCode() {
		return stShortCode;
	}

	/**
	 * @param stShortCode the stShortCode to set
	 */
	public void setStShortCode(String stShortCode) {
		this.stShortCode = stShortCode;
	}

	/**
	 * @return the district
	 */
	public Long getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(Long district) {
		this.district = district;
	}

	/**
	 * @return the distCode
	 */
	public Long getDistCode() {
		return distCode;
	}

	/**
	 * @param distCode the distCode to set
	 */
	public void setDistCode(Long distCode) {
		this.distCode = distCode;
	}

	/**
	 * @return the areaType
	 */
	public Long getAreaType() {
		return areaType;
	}

	/**
	 * @param areaType the areaType to set
	 */
	public void setAreaType(Long areaType) {
		this.areaType = areaType;
	}

	/**
	 * @return the zone
	 */
	public Long getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(Long zone) {
		this.zone = zone;
	}

	/**
	 * @return the odop
	 */
	public Long getOdop() {
		return odop;
	}

	/**
	 * @param odop the odop to set
	 */
	public void setOdop(Long odop) {
		this.odop = odop;
	}

	/**
	 * @return the aspirationalDist
	 */
	public Long getAspirationalDist() {
		return aspirationalDist;
	}

	/**
	 * @param aspirationalDist the aspirationalDist to set
	 */
	public void setAspirationalDist(Long aspirationalDist) {
		this.aspirationalDist = aspirationalDist;
	}

	/**
	 * @return the tribalDist
	 */
	public Long getTribalDist() {
		return tribalDist;
	}

	/**
	 * @param tribalDist the tribalDist to set
	 */
	public void setTribalDist(Long tribalDist) {
		this.tribalDist = tribalDist;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	/**
	 * @return the langId
	 */
	public Long getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(Long langId) {
		this.langId = langId;
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
	 * @return the distName
	 */
	public String getDistName() {
		return distName;
	}

	/**
	 * @param distName the distName to set
	 */
	public void setDistName(String distName) {
		this.distName = distName;
	}


	/**
	 * @return the aspirationallDist
	 */
	public String getAspirationallDist() {
		return aspirationallDist;
	}

	/**
	 * @param aspirationallDist the aspirationallDist to set
	 */
	public void setAspirationallDist(String aspirationallDist) {
		this.aspirationallDist = aspirationallDist;
	}

	/**
	 * @return the odopValue
	 */
	public String getOdopValue() {
		return odopValue;
	}

	/**
	 * @param odopValue the odopValue to set
	 */
	public void setOdopValue(String odopValue) {
		this.odopValue = odopValue;
	}

	/**
	 * @return the tirbalDist
	 */
	public String getTirbalDist() {
		return tirbalDist;
	}

	/**
	 * @param tirbalDist the tirbalDist to set
	 */
	public void setTirbalDist(String tirbalDist) {
		this.tirbalDist = tirbalDist;
	}

	/**
	 * @return the areaTypeValue
	 */
	public String getAreaTypeValue() {
		return areaTypeValue;
	}

	/**
	 * @param areaTypeValue the areaTypeValue to set
	 */
	public void setAreaTypeValue(String areaTypeValue) {
		this.areaTypeValue = areaTypeValue;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the areaTypeDesc
	 */
	public String getAreaTypeDesc() {
		return areaTypeDesc;
	}

	/**
	 * @param areaTypeDesc the areaTypeDesc to set
	 */
	public void setAreaTypeDesc(String areaTypeDesc) {
		this.areaTypeDesc = areaTypeDesc;
	}

	
	
}
