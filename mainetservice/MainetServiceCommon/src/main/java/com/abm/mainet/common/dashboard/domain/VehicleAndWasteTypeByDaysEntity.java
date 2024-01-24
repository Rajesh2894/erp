package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VehicleAndWasteTypeByDaysEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "State")
	private String state;

	@Column(name = "OrganisationName")
	private String orgName;

	@Column(name = "HCity")
	private String hCity;

	@Column(name = "DistrinctName")
	private String districtName;

	@Column(name = "HDistrinctName")
	private String hDistrinctName;

	@Column(name = "DivisonName")
	private String divisonName;

	@Column(name = "HDivisonName")
	private String hDivisonName;

	@Column(name = "ORG_LATITUDE")
	private double orgLatitude;

	@Column(name = "ORG_LONGITUDE")
	private double orgLongitude;

	@Column(name = "VechicleType")
	private String vechicleType;

	@Column(name = "HVechicleType")
	private String hVechicleType;

	@Column(name = "TRIP_VOLUME")
	private double tripVolume;

	@Column(name = "CREATED_DATE")
	private String createdDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String gethCity() {
		return hCity;
	}

	public void sethCity(String hCity) {
		this.hCity = hCity;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String gethDistrinctName() {
		return hDistrinctName;
	}

	public void sethDistrinctName(String hDistrinctName) {
		this.hDistrinctName = hDistrinctName;
	}

	public String getDivisonName() {
		return divisonName;
	}

	public void setDivisonName(String divisonName) {
		this.divisonName = divisonName;
	}

	public String gethDivisonName() {
		return hDivisonName;
	}

	public void sethDivisonName(String hDivisonName) {
		this.hDivisonName = hDivisonName;
	}

	public double getOrgLatitude() {
		return orgLatitude;
	}

	public void setOrgLatitude(double orgLatitude) {
		this.orgLatitude = orgLatitude;
	}

	public double getOrgLongitude() {
		return orgLongitude;
	}

	public void setOrgLongitude(double orgLongitude) {
		this.orgLongitude = orgLongitude;
	}

	public String getVechicleType() {
		return vechicleType;
	}

	public void setVechicleType(String vechicleType) {
		this.vechicleType = vechicleType;
	}

	public String gethVechicleType() {
		return hVechicleType;
	}

	public void sethVechicleType(String hVechicleType) {
		this.hVechicleType = hVechicleType;
	}

	public double getTripVolume() {
		return tripVolume;
	}

	public void setTripVolume(double tripVolume) {
		this.tripVolume = tripVolume;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "VehicleAndWasteTypeByDaysEntity [id=" + id + ", state=" + state + ", orgName=" + orgName + ", hCity="
				+ hCity + ", districtName=" + districtName + ", hDistrinctName=" + hDistrinctName + ", divisonName="
				+ divisonName + ", hDivisonName=" + hDivisonName + ", orgLatitude=" + orgLatitude + ", orgLongitude="
				+ orgLongitude + ", vechicleType=" + vechicleType + ", hVechicleType=" + hVechicleType + ", tripVolume="
				+ tripVolume + ", createdDate=" + createdDate + "]";
	}

}
