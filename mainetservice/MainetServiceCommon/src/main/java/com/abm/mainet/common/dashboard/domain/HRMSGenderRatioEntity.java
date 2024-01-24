package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HRMSGenderRatioEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "gender")
	private String gender;

	@Column(name = "CNT")
	private Integer count;

	@Column(name = "Percent")
	private Double percent;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return "HRMSGenderRatioEntity [id=" + id + ", gender=" + gender + ", count=" + count + ", percent=" + percent
				+ "]";
	}

}
