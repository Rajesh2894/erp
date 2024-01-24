package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DryWasteHazSWMEntity {
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

	@Column(name = "COD_DESC")
	private String codDesc;

	@Column(name = "COD_DESC_MAR")
	private String codDescMar;

	@Column(name = "DryWaste")
	private int dryWaste;

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

	public String getCodDesc() {
		return codDesc;
	}

	public void setCodDesc(String codDesc) {
		this.codDesc = codDesc;
	}

	public String getCodDescMar() {
		return codDescMar;
	}

	public void setCodDescMar(String codDescMar) {
		this.codDescMar = codDescMar;
	}

	public int getDryWaste() {
		return dryWaste;
	}

	public void setDryWaste(int dryWaste) {
		this.dryWaste = dryWaste;
	}

	@Override
	public String toString() {
		return "GraphDryWasteEntity [id=" + id + ", state=" + state + ", orgName=" + orgName + ", hCity=" + hCity
				+ ", districtName=" + districtName + ", hDistrinctName=" + hDistrinctName + ", divisonName="
				+ divisonName + ", hDivisonName=" + hDivisonName + ", orgLatitude=" + orgLatitude + ", orgLongitude="
				+ orgLongitude + ", codDesc=" + codDesc + ", codDescMar=" + codDescMar + ", dryWaste=" + dryWaste + "]";
	}

}
