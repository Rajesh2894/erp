package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ModeComplaintsEntity {
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "REFERENCE_MODE")
	private long refMode;

	@Column(name = "cpd_desc")
	private String deptDesc;

	@Column(name = "cpd_desc_mar")
	private String deptNameMar;

	@Column(name = "Closed")
	private int closed;

	@Column(name = "Pending")
	private int pending;

	@Column(name = "Hold")
	private int hold;

	@Column(name = "Rejected")
	private int rejected;

	@Column(name = "Received")
	private int received;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getRefMode() {
		return refMode;
	}

	public void setRefMode(long refMode) {
		this.refMode = refMode;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getDeptNameMar() {
		return deptNameMar;
	}

	public void setDeptNameMar(String deptNameMar) {
		this.deptNameMar = deptNameMar;
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

	public int getReceived() {
		return received;
	}

	public void setReceived(int received) {
		this.received = received;
	}

	@Override
	public String toString() {
		return "ModeComplaintsEntity [id=" + id + ", refMode=" + refMode + ", deptDesc=" + deptDesc + ", deptNameMar="
				+ deptNameMar + ", closed=" + closed + ", pending=" + pending + ", hold=" + hold + ", rejected="
				+ rejected + ", received=" + received + "]";
	}

}
