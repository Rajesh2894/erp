package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountRatioDashboardCntEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "PAC_HEAD_DESC")
	private String incomeExpenseHead;

	@Column(name = "ORGINAL_ESTAMT_CURRENT_YEAR")
	private double currentYearEstimated;

	@Column(name = "INCOM_EXPENSE_AMT_CURRENT_YEAR")
	private double incomeExpenseAmtCurrYearActual;

	@Column(name = "CURRENT_YEAR_RATIO")
	private double currYearRatio;

	@Column(name = "ORGINAL_ESTAMT_1Y")
	private double originalOneYearEstimated;

	@Column(name = "INCOM_EXPENSE_AMT_1Y")
	private double incomeExpenseAmtOneYearActual;

	@Column(name = "RATIO_1Y")
	private double ratioOneYear;

	@Column(name = "ORGINAL_ESTAMT_2Y")
	private double originalTwoYearEstimated;

	@Column(name = "INCOM_EXPENSE_AMT_2Y")
	private double incomeExpenseAmtTwoYearActual;

	@Column(name = "RATIO_2Y")
	private double ratioTwoYear;

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

	public double getCurrentYearEstimated() {
		return currentYearEstimated;
	}

	public void setCurrentYearEstimated(double currentYearEstimated) {
		this.currentYearEstimated = currentYearEstimated;
	}

	public double getIncomeExpenseAmtCurrYearActual() {
		return incomeExpenseAmtCurrYearActual;
	}

	public void setIncomeExpenseAmtCurrYearActual(double incomeExpenseAmtCurrYearActual) {
		this.incomeExpenseAmtCurrYearActual = incomeExpenseAmtCurrYearActual;
	}

	public double getCurrYearRatio() {
		return currYearRatio;
	}

	public void setCurrYearRatio(double currYearRatio) {
		this.currYearRatio = currYearRatio;
	}

	public double getOriginalOneYearEstimated() {
		return originalOneYearEstimated;
	}

	public void setOriginalOneYearEstimated(double originalOneYearEstimated) {
		this.originalOneYearEstimated = originalOneYearEstimated;
	}

	public double getIncomeExpenseAmtOneYearActual() {
		return incomeExpenseAmtOneYearActual;
	}

	public void setIncomeExpenseAmtOneYearActual(double incomeExpenseAmtOneYearActual) {
		this.incomeExpenseAmtOneYearActual = incomeExpenseAmtOneYearActual;
	}

	public double getRatioOneYear() {
		return ratioOneYear;
	}

	public void setRatioOneYear(double ratioOneYear) {
		this.ratioOneYear = ratioOneYear;
	}

	public double getOriginalTwoYearEstimated() {
		return originalTwoYearEstimated;
	}

	public void setOriginalTwoYearEstimated(double originalTwoYearEstimated) {
		this.originalTwoYearEstimated = originalTwoYearEstimated;
	}

	public double getIncomeExpenseAmtTwoYearActual() {
		return incomeExpenseAmtTwoYearActual;
	}

	public void setIncomeExpenseAmtTwoYearActual(double incomeExpenseAmtTwoYearActual) {
		this.incomeExpenseAmtTwoYearActual = incomeExpenseAmtTwoYearActual;
	}

	public double getRatioTwoYear() {
		return ratioTwoYear;
	}

	public void setRatioTwoYear(double ratioTwoYear) {
		this.ratioTwoYear = ratioTwoYear;
	}

	@Override
	public String toString() {
		return "AccountRatioDashboardCntEntity [id=" + id + ", incomeExpenseHead=" + incomeExpenseHead
				+ ", currentYearEstimated=" + currentYearEstimated + ", incomeExpenseAmtCurrYearActual="
				+ incomeExpenseAmtCurrYearActual + ", currYearRatio=" + currYearRatio + ", originalOneYearEstimated="
				+ originalOneYearEstimated + ", incomeExpenseAmtOneYearActual=" + incomeExpenseAmtOneYearActual
				+ ", ratioOneYear=" + ratioOneYear + ", originalTwoYearEstimated=" + originalTwoYearEstimated
				+ ", incomeExpenseAmtTwoYearActual=" + incomeExpenseAmtTwoYearActual + ", ratioTwoYear=" + ratioTwoYear
				+ "]";
	}

}
