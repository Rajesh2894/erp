package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdvocateMastRegEntity {

	

	@Id
	@Column(name = "NoOfRequests")
	private int noOfRequests;

	@Column(name = "REJECTED")
	private int rejected;

	@Column(name = "CLOSED")
	private int closed;

	@Column(name = "PENDING")
	private int pending;

	

	public int getNoOfRequests() {
		return noOfRequests;
	}

	public void setNoOfRequests(int noOfRequests) {
		this.noOfRequests = noOfRequests;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
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

	@Override
	public String toString() {
		return "AdvocateMastRegEntity [noOfRequests=" + noOfRequests + ", rejected=" + rejected
				+ ", closed=" + closed + ", pending=" + pending + "]";
	}



	

}
