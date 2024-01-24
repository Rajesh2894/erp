package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CouncilDeptYearWiseCntEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "DEPT_OR_YEAR")
	private String deptOrYear;

	@Column(name = "Received")
	private int recieved;

	@Column(name = "Rejected")
	private int rejected;

	@Column(name = "Approved")
	private int approved;

	@Column(name = "Pending")
	private int pending;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeptOrYear() {
		return deptOrYear;
	}

	public void setDeptOrYear(String deptOrYear) {
		this.deptOrYear = deptOrYear;
	}

	public int getRecieved() {
		return recieved;
	}

	public void setRecieved(int recieved) {
		this.recieved = recieved;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

	public int getApproved() {
		return approved;
	}

	public void setApproved(int approved) {
		this.approved = approved;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	@Override
	public String toString() {
		return "CouncilDeptYearWiseCntEntity [id=" + id + ", deptOrYear=" + deptOrYear + ", recieved=" + recieved
				+ ", rejected=" + rejected + ", approved=" + approved + ", pending=" + pending + "]";
	}

}
