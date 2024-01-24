package com.abm.mainet.common.dashboard.domain.skdcl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FinancialDurationWiseServiceStatusEntity {
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "FIN_DURATION")
	private String financialDuration;

	@Column(name = "RECEIVED")
	private int received;

	@Column(name = "PENDING")
	private int pending;

	@Column(name = "CLOSED")
	private int closed;

	@Column(name = "EXPIRED")
	private int expired;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFinancialDuration() {
		return financialDuration;
	}

	public void setFinancialDuration(String financialDuration) {
		this.financialDuration = financialDuration;
	}

	public int getReceived() {
		return received;
	}

	public void setReceived(int received) {
		this.received = received;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getClosed() {
		return closed;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		return "FinancialDurationWiseStatusEntity [id=" + id + ", financialDuration=" + financialDuration
				+ ", received=" + received + ", pending=" + pending + ", closed=" + closed + ", expired=" + expired
				+ "]";
	}

}
