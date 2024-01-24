package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountReceiptsAndPaymentsEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "FUNCTION_OR_ZONE")
	private String functionOrZone;

	@Column(name = "OPERATING_RECEIPTS")
	private double operatingReceipts;

	@Column(name = "NON_OPERATING_RECEIPTS")
	private double nonOperatingReceipts;

	@Column(name = "TOTAL_RECEIPTS")
	private double totalReceipts;

	@Column(name = "OPERATING_PAYMENTS")
	private double operatingPayments;

	@Column(name = "NON_OPERATING_PAYMENTS")
	private double nonOperatingPayments;

	@Column(name = "TOTAL_PAYMENTS")
	private double totalPayments;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFunctionOrZone() {
		return functionOrZone;
	}

	public void setFunctionOrZone(String functionOrZone) {
		this.functionOrZone = functionOrZone;
	}

	public double getOperatingReceipts() {
		return operatingReceipts;
	}

	public void setOperatingReceipts(double operatingReceipts) {
		this.operatingReceipts = operatingReceipts;
	}

	public double getNonOperatingReceipts() {
		return nonOperatingReceipts;
	}

	public void setNonOperatingReceipts(double nonOperatingReceipts) {
		this.nonOperatingReceipts = nonOperatingReceipts;
	}

	public double getTotalReceipts() {
		return totalReceipts;
	}

	public void setTotalReceipts(double totalReceipts) {
		this.totalReceipts = totalReceipts;
	}

	public double getOperatingPayments() {
		return operatingPayments;
	}

	public void setOperatingPayments(double operatingPayments) {
		this.operatingPayments = operatingPayments;
	}

	public double getNonOperatingPayments() {
		return nonOperatingPayments;
	}

	public void setNonOperatingPayments(double nonOperatingPayments) {
		this.nonOperatingPayments = nonOperatingPayments;
	}

	public double getTotalPayments() {
		return totalPayments;
	}

	public void setTotalPayments(double totalPayments) {
		this.totalPayments = totalPayments;
	}

	@Override
	public String toString() {
		return "AccountReceiptsAndPaymentsEntity [id=" + id + ", functionOrZone=" + functionOrZone
				+ ", operatingReceipts=" + operatingReceipts + ", nonOperatingReceipts=" + nonOperatingReceipts
				+ ", totalReceipts=" + totalReceipts + ", operatingPayments=" + operatingPayments
				+ ", nonOperatingPayments=" + nonOperatingPayments + ", totalPayments=" + totalPayments + "]";
	}

}
