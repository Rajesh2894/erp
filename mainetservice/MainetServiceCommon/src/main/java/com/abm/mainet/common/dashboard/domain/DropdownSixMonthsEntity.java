package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DropdownSixMonthsEntity {

	@Id
	@Column(name = "Years")
	private String years;

	@Column(name = "Closed")
	private int closed;

	@Column(name = "Pending")
	private int pending;

	@Column(name = "Hold")
	private int hold;

	@Column(name = "Rejected")
	private int rejected;
	
	@Column(name = "Counts")
	private int counts;

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public int getClosed() {
		return closed;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getHold() {
		return hold;
	}

	public void setHold(int hold) {
		this.hold = hold;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	@Override
	public String toString() {
		return "DropdownSixMonthsEntity [years=" + years + ", closed=" + closed + ", pending=" + pending + ", hold="
				+ hold + ", rejected=" + rejected + ", counts=" + counts + "]";
	}
	
	
}
