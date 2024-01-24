package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DeptWiseTrendAnalysisEntity {
	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "totalRecieved")
	private int totalRecieved;

	@Column(name = "DepartmentName")
	private String deptName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotalRecieved() {
		return totalRecieved;
	}

	public void setTotalRecieved(int totalRecieved) {
		this.totalRecieved = totalRecieved;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "DeptwiseTrendAnalysisEntity [id=" + id + ", totalRecieved=" + totalRecieved + ", deptName=" + deptName
				+ "]";
	}

}
