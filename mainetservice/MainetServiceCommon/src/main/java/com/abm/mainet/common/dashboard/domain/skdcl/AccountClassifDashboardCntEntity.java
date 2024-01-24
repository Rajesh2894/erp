package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountClassifDashboardCntEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "PAC_HEAD_DESC")
	private String incomeExpenseHead;

	@Column(name = "2Y_OLD")
	private double twoYearOld;

	@Column(name = "1Y_OLD")
	private double oneYearOld;

	@Column(name = "DIFF_INC_OR_DEC")
	private double diffIncOrDec;

	@Column(name = "CURRENT_YEAR")
	private double currYear;

	@Column(name = "AS_ON_2Y")
	private double asOnTwoYearOld;

	@Column(name = "AS_ON_1Y")
	private double asOnOneYearOld;

	@Column(name = "DIFF_INC_OR_DEC_ASON")
	private double diffIncOrDecAsOnDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIncomeExpenseHead() {
		return incomeExpenseHead;
	}

	public void setIncomeExpenseHead(String incomeExpenseHead) {
		this.incomeExpenseHead = incomeExpenseHead;
	}

	public double getTwoYearOld() {
		return twoYearOld;
	}

	public void setTwoYearOld(double twoYearOld) {
		this.twoYearOld = twoYearOld;
	}

	public double getOneYearOld() {
		return oneYearOld;
	}

	public void setOneYearOld(double oneYearOld) {
		this.oneYearOld = oneYearOld;
	}

	public double getDiffIncOrDec() {
		return diffIncOrDec;
	}

	public void setDiffIncOrDec(double diffIncOrDec) {
		this.diffIncOrDec = diffIncOrDec;
	}

	public double getCurrYear() {
		return currYear;
	}

	public void setCurrYear(double currYear) {
		this.currYear = currYear;
	}

	public double getAsOnTwoYearOld() {
		return asOnTwoYearOld;
	}

	public void setAsOnTwoYearOld(double asOnTwoYearOld) {
		this.asOnTwoYearOld = asOnTwoYearOld;
	}

	public double getAsOnOneYearOld() {
		return asOnOneYearOld;
	}

	public void setAsOnOneYearOld(double asOnOneYearOld) {
		this.asOnOneYearOld = asOnOneYearOld;
	}

	public double getDiffIncOrDecAsOnDate() {
		return diffIncOrDecAsOnDate;
	}

	public void setDiffIncOrDecAsOnDate(double diffIncOrDecAsOnDate) {
		this.diffIncOrDecAsOnDate = diffIncOrDecAsOnDate;
	}

}
