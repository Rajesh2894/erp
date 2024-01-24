package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class RTIApplicationByApplicantType {
	@Id
	@Column(name = "num")
	private int id;
	@Column(name = "applicantType")
	private String applicantType;
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

	public int getId() {
		return id;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public int getReceivedQuery() {
		return receivedQuery;
	}

	public int getPendingQuery() {
		return pendingQuery;
	}

	public int getClosedQuery() {
		return closedQuery;
	}

	public int getExpiredQuery() {
		return expiredQuery;
	}

	public int getHold() {
		return hold;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public void setReceivedQuery(int receivedQuery) {
		this.receivedQuery = receivedQuery;
	}

	public void setPendingQuery(int pendingQuery) {
		this.pendingQuery = pendingQuery;
	}

	public void setClosedQuery(int closedQuery) {
		this.closedQuery = closedQuery;
	}

	public void setExpiredQuery(int expiredQuery) {
		this.expiredQuery = expiredQuery;
	}

	public void setHold(int hold) {
		this.hold = hold;
	}

	@Override
	public String toString() {
		return "RTIApplicationByApplicantType [id=" + id + ", applicantType=" + applicantType + ", receivedQuery="
				+ receivedQuery + ", pendingQuery=" + pendingQuery + ", closedQuery=" + closedQuery + ", expiredQuery="
				+ expiredQuery + ", hold=" + hold + "]";
	}

	}
