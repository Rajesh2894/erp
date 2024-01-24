package com.abm.mainet.account.dto;

import java.io.Serializable;

public class AccountFunctionWiseBudgetReportDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107133474393304730L;

	private String functionDesc;
	private String functionCode;
	private String revenuReceiptAmt;
	private String revenueExpensesAmt;
	private String capitalReceiptAmt;
	private String capitalExpensesAmt;
	private String outFlow;
	private String subTotalReceiptAmt;
	private String subtotalExpenseAmt;

	public String getFunctionDesc() {
		return functionDesc;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public String getRevenuReceiptAmt() {
		return revenuReceiptAmt;
	}

	public String getRevenueExpensesAmt() {
		return revenueExpensesAmt;
	}

	public String getCapitalReceiptAmt() {
		return capitalReceiptAmt;
	}

	public String getCapitalExpensesAmt() {
		return capitalExpensesAmt;
	}

	public String getOutFlow() {
		return outFlow;
	}

	public String getSubTotalReceiptAmt() {
		return subTotalReceiptAmt;
	}

	public String getSubtotalExpenseAmt() {
		return subtotalExpenseAmt;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public void setRevenuReceiptAmt(String revenuReceiptAmt) {
		this.revenuReceiptAmt = revenuReceiptAmt;
	}

	public void setRevenueExpensesAmt(String revenueExpensesAmt) {
		this.revenueExpensesAmt = revenueExpensesAmt;
	}

	public void setCapitalReceiptAmt(String capitalReceiptAmt) {
		this.capitalReceiptAmt = capitalReceiptAmt;
	}

	public void setCapitalExpensesAmt(String capitalExpensesAmt) {
		this.capitalExpensesAmt = capitalExpensesAmt;
	}

	public void setOutFlow(String outFlow) {
		this.outFlow = outFlow;
	}

	public void setSubTotalReceiptAmt(String subTotalReceiptAmt) {
		this.subTotalReceiptAmt = subTotalReceiptAmt;
	}

	public void setSubtotalExpenseAmt(String subtotalExpenseAmt) {
		this.subtotalExpenseAmt = subtotalExpenseAmt;
	}

	@Override
	public String toString() {
		return "AccountFunctionWiseBudgetReportDto [functionDesc=" + functionDesc + ", functionCode=" + functionCode
				+ ", revenuReceiptAmt=" + revenuReceiptAmt + ", revenueExpensesAmt=" + revenueExpensesAmt
				+ ", capitalReceiptAmt=" + capitalReceiptAmt + ", capitalExpensesAmt=" + capitalExpensesAmt
				+ ", outFlow=" + outFlow + ", subTotalReceiptAmt=" + subTotalReceiptAmt + ", subtotalExpenseAmt="
				+ subtotalExpenseAmt + "]";
	}

}
