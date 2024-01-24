package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CouncilMeetingCountYrwise {
	
	@Id
	@Column(name = "num")
	private int id;
	
	@Column(name = "YEARS")
	private String Years;

	@Column(name = "TOTOL_COUNT")
	private int totalCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYears() {
		return Years;
	}

	public void setYears(String years) {
		Years = years;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
