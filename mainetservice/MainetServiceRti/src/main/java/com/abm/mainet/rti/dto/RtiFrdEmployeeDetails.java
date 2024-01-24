package com.abm.mainet.rti.dto;

import java.io.Serializable;

public class RtiFrdEmployeeDetails implements Serializable {
	private static final long serialVersionUID = 4587771458335877655L;

	private String deptName;
	private String empDesg;
	private String empRematk;
	private String empName;

	public String getDeptName() {
		return deptName;
	}

	public String getEmpDesg() {
		return empDesg;
	}

	public String getEmpRematk() {
		return empRematk;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setEmpDesg(String empDesg) {
		this.empDesg = empDesg;
	}

	public void setEmpRematk(String empRematk) {
		this.empRematk = empRematk;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
