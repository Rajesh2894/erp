package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class YearWiseGrievanceGraphEntity {
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "recieved_query")
	private int receivedQuery;

	@Column(name = "Pending_query")
	private int pendingQuery;

	@Column(name = "closed_query")
	private int closedQuery;

	@Column(name = "expired_query")
	private int expiredQuery;

	@Column(name = "Hold")
	private int hold;

	@Column(name = "FY_Year")
	private String fyYear;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReceivedQuery() {
		return receivedQuery;
	}

	public void setReceivedQuery(int receivedQuery) {
		this.receivedQuery = receivedQuery;
	}

	public int getPendingQuery() {
		return pendingQuery;
	}

	public void setPendingQuery(int pendingQuery) {
		this.pendingQuery = pendingQuery;
	}

	public int getClosedQuery() {
		return closedQuery;
	}

	public void setClosedQuery(int closedQuery) {
		this.closedQuery = closedQuery;
	}

	public int getExpiredQuery() {
		return expiredQuery;
	}

	public void setExpiredQuery(int expiredQuery) {
		this.expiredQuery = expiredQuery;
	}

	public int getHold() {
		return hold;
	}

	public void setHold(int hold) {
		this.hold = hold;
	}

	public String getFyYear() {
		return fyYear;
	}

	public void setFyYear(String fyYear) {
		this.fyYear = fyYear;
	}

	@Override
	public String toString() {
		return "YearWiseGrievanceGraphEntity [id=" + id + ", receivedQuery=" + receivedQuery + ", pendingQuery="
				+ pendingQuery + ", closedQuery=" + closedQuery + ", expiredQuery=" + expiredQuery + ", hold=" + hold
				+ ", fyYear=" + fyYear + "]";
	}

}
