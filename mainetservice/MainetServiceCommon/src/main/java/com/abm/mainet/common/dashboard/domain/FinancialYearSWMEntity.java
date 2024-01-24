package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FinancialYearSWMEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "year")
	private String year;

	@Column(name = "DRY")
	private double dry;

	@Column(name = "WATE")
	private double wate;

	@Column(name = "HAZ")
	private double haz;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	@Override
	public String toString() {
		return "FinancialYrSWMEntity [id=" + id + ", year=" + year + ", dry=" + dry + ", wate=" + wate + ", haz=" + haz
				+ "]";
	}

}
