package com.abm.mainet.cms.dto;


public class ItmsUpdateDTO {
	
	private String signalSCN;
	private String accidentType;
	private String weatherType;
	private String roadType;
	private String areaType;
	private String roadFeature;
	
	public String getSignalSCN() {
		return signalSCN;
	}
	public void setSignalSCN(String signalSCN) {
		this.signalSCN = signalSCN;
	}
	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	public String getWeatherType() {
		return weatherType;
	}
	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}
	public String getRoadType() {
		return roadType;
	}
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public String getRoadFeature() {
		return roadFeature;
	}
	public void setRoadFeature(String roadFeature) {
		this.roadFeature = roadFeature;
	}
	

}
