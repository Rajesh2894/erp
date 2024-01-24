package com.abm.mainet.property.dto;

import java.io.Serializable;

public class LandTypeApiDetailRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String landType;
	private String districtId;
	private String tehsilId;
	private String villageId; 
	private String mohallaId;
	private String streetNo;
	private String landTypePrefix; 
	private String plotNo;
	private String khasraNo;
	private String villageCode; // vsrNo
	
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getTehsilId() {
		return tehsilId;
	}
	public void setTehsilId(String tehsilId) {
		this.tehsilId = tehsilId;
	}
	public String getVillageId() {
		return villageId;
	}
	public void setVillageId(String villageId) {
		this.villageId = villageId;
	}
	public String getMohallaId() {
		return mohallaId;
	}
	public void setMohallaId(String mohallaId) {
		this.mohallaId = mohallaId;
	}
	public String getStreetNo() {
		return streetNo;
	}
	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}
	public String getLandTypePrefix() {
		return landTypePrefix;
	}
	public void setLandTypePrefix(String landTypePrefix) {
		this.landTypePrefix = landTypePrefix;
	}
	public String getPlotNo() {
		return plotNo;
	}
	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}
	public String getKhasraNo() {
		return khasraNo;
	}
	public void setKhasraNo(String khasraNo) {
		this.khasraNo = khasraNo;
	}
	public String getLandType() {
		return landType;
	}
	public void setLandType(String landType) {
		this.landType = landType;
	}
	public String getVillageCode() {
		return villageCode;
	}
	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}

}
