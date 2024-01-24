package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LegalCountsEntity {
	@Id
	@Column(name = "YR")
	private String year;

	@Column(name = "CNT")
	private int count;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "LegalCountsEntity [year=" + year + ", count=" + count + "]";
	}

}
