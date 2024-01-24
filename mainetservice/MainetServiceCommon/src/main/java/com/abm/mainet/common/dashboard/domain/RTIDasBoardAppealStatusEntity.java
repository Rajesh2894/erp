package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class RTIDasBoardAppealStatusEntity {
	@Id
	@Column(name = "num")
	private int id;
	
	@Column(name = "Pending")
	private int pending;
	
	@Column(name = "Closed")
	private int closed;

	public int getId() {
		return id;
	}

	public int getPending() {
		return pending;
	}

	public int getClosed() {
		return closed;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}


}
