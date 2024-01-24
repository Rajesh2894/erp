package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountFundStatusEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "FUND_DESC")
	private String fundName;

	@Column(name = "OPENING_BALANCE")
	private double openingBalance;

	@Column(name = "RECEIPT")
	private double receipt;

	@Column(name = "PAYMENT")
	private double payment;

	@Column(name = "CLOSING_BALANCE")
	private double closingBalance;

	@Column(name = "FDR")
	private double fdr;

	@Column(name = "TOTAL_FUND_BAL")
	private double totalFundBalance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public double getReceipt() {
		return receipt;
	}

	public void setReceipt(double receipt) {
		this.receipt = receipt;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}

	public double getFdr() {
		return fdr;
	}

	public void setFdr(double fdr) {
		this.fdr = fdr;
	}

	public double getTotalFundBalance() {
		return totalFundBalance;
	}

	public void setTotalFundBalance(double totalFundBalance) {
		this.totalFundBalance = totalFundBalance;
	}

	@Override
	public String toString() {
		return "AccountFundStatusEntity [id=" + id + ", fundName=" + fundName + ", openingBalance=" + openingBalance
				+ ", receipt=" + receipt + ", payment=" + payment + ", closingBalance=" + closingBalance + ", fdr="
				+ fdr + ", totalFundBalance=" + totalFundBalance + "]";
	}

}
