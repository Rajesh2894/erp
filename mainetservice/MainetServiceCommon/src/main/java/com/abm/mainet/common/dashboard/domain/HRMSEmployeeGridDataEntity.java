package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HRMSEmployeeGridDataEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "Employee_ID")
	private String empId;

	@Column(name = "Employee_Name")
	private String empName;

	@Column(name = "Department")
	private String department;

	@Column(name = "Age")
	private Integer age;

	@Column(name = "Date_of_Birth")
	private String dob;

	@Column(name = "Mobile_No")
	private Long mobileNo;

	@Column(name = "Email_ID")
	private String emailId;

	@Column(name = "Date_of_Joining")
	private String dateOfJoining;

	@Column(name = "Employee_Status")
	private String empStatus;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long l) {
		this.mobileNo = l;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	@Override
	public String toString() {
		return "HRMSEmployeeGridDataEntity [id=" + id + ", empId=" + empId + ", empName=" + empName + ", department="
				+ department + ", age=" + age + ", dob=" + dob + ", mobileNo=" + mobileNo + ", emailId=" + emailId
				+ ", dateOfJoining=" + dateOfJoining + ", empStatus=" + empStatus + "]";
	}

}
