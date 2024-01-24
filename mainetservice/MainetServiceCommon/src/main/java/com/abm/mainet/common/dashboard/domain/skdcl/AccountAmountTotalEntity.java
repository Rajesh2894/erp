package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountAmountTotalEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "FA_YEAR")
	private String year;

	@Column(name = "CNT_OR_SUM")
	private double countOrAmount;

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

	public double getCountOrAmount() {
		return countOrAmount;
	}

	public void setCountOrAmount(double countOrAmount) {
		this.countOrAmount = countOrAmount;
	}

	@Override
	public String toString() {
		return "AccountAmountTotalEntity [id=" + id + ", year=" + year + ", countOrAmount=" + countOrAmount + "]";
	}

}
