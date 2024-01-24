package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FirstTableYearwiseSWMEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "ORG_CPD_ID_DIV")
	private long orgCpdIdDiv;

	@Column(name = "Division")
	private String division;

	@Column(name = "DivisionReg")
	private String divisionReg;

	@Column(name = "DRY")
	private double dry;

	@Column(name = "WATE")
	private double wate;

	@Column(name = "HAZ")
	private double haz;

	@Column(name = "YEAR")
	private String year;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getOrgCpdIdDiv() {
		return orgCpdIdDiv;
	}

	public void setOrgCpdIdDiv(long orgCpdIdDiv) {
		this.orgCpdIdDiv = orgCpdIdDiv;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDivisionReg() {
		return divisionReg;
	}

	public void setDivisionReg(String divisionReg) {
		this.divisionReg = divisionReg;
	}

	public double getDry() {
		return dry;
	}

	public void setDry(double dry) {
		this.dry = dry;
	}

	public double getWate() {
		return wate;
	}

	public void setWate(double wate) {
		this.wate = wate;
	}

	public double getHaz() {
		return haz;
	}

	public void setHaz(double haz) {
		this.haz = haz;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "FirstTableYearwiseSWMEntity [id=" + id + ", orgCpdIdDiv=" + orgCpdIdDiv + ", division=" + division
				+ ", divisionReg=" + divisionReg + ", dry=" + dry + ", wate=" + wate + ", haz=" + haz + ", year=" + year
				+ "]";
	}

}
