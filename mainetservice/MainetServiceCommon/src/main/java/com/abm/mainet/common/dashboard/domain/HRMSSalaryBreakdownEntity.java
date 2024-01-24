package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HRMSSalaryBreakdownEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "Gross_pay_range")
	private String grossPayRange;

	@Column(name = "no_of_employees")
	private Integer count;

	@Column(name = "department")
	private String department;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGrossPayRange() {
		return grossPayRange;
	}

	public void setGrossPayRange(String grossPayRange) {
		this.grossPayRange = grossPayRange;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "HRMSSalaryBreakdownEntity [id=" + id + ", grossPayRange=" + grossPayRange + ", count=" + count
				+ ", department=" + department + "]";
	}

}
