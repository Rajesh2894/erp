package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_employee_details")
public class EmployeeDetailsView implements Serializable{
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
	    @Column(name = "empid")
	    private Long empId;

	    @Column(name = "cpd_desc")
	    private String cpddesc;
	    
	    @Column(name = "empname")
	    private String empName;
	    
	    @Column(name = "empmname")
	    private String empMName;
	    
	    @Column(name = "emplname")
	    private String empLName;
	    
	    @Column(name = "emp_gender")
	    private String gender;
	    
	    @Column(name = "empemail")
	    private String empEmailId;
	    
	    @Column(name = "empmobno")
	    private String empMobNo;
	    
	    @Column(name = "emp_address")
	    private String empAddress;
	    
	    @Column(name = "emp_address1")
	    private String empAddress1;
	    
	    @Column(name = "emppincode")
	    private String empPincode;
	    
	    @Column(name = "dp_deptdesc")  
	    private String deptdesc;
	    
	    @Column(name = "dsgname")  
	    private String dsgname;
	    
	    @Column(name = "dsgid")
	    private Long desgId;
	    
	    @Column(name = "dp_deptid")
	    private Long mrfId;
	    
	    @Column(name = "cpd_title")
	    private Long ttlId;

		public Long getEmpId() {
			return empId;
		}

		public void setEmpId(Long empId) {
			this.empId = empId;
		}

		public String getCpddesc() {
			return cpddesc;
		}

		public void setCpddesc(String cpddesc) {
			this.cpddesc = cpddesc;
		}

		public String getEmpName() {
			return empName;
		}

		public void setEmpName(String empName) {
			this.empName = empName;
		}

		public String getEmpMName() {
			return empMName;
		}

		public void setEmpMName(String empMName) {
			this.empMName = empMName;
		}

		public String getEmpLName() {
			return empLName;
		}

		public void setEmpLName(String empLName) {
			this.empLName = empLName;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getEmpEmailId() {
			return empEmailId;
		}

		public void setEmpEmailId(String empEmailId) {
			this.empEmailId = empEmailId;
		}

		public String getEmpMobNo() {
			return empMobNo;
		}

		public void setEmpMobNo(String empMobNo) {
			this.empMobNo = empMobNo;
		}

		public String getEmpAddress() {
			return empAddress;
		}

		public void setEmpAddress(String empAddress) {
			this.empAddress = empAddress;
		}

		public String getEmpAddress1() {
			return empAddress1;
		}

		public void setEmpAddress1(String empAddress1) {
			this.empAddress1 = empAddress1;
		}

		public String getEmpPincode() {
			return empPincode;
		}

		public void setEmpPincode(String empPincode) {
			this.empPincode = empPincode;
		}

		public String getDeptdesc() {
			return deptdesc;
		}

		public void setDeptdesc(String deptdesc) {
			this.deptdesc = deptdesc;
		}

		public String getDsgname() {
			return dsgname;
		}

		public void setDsgname(String dsgname) {
			this.dsgname = dsgname;
		}

		public Long getDesgId() {
			return desgId;
		}

		public void setDesgId(Long desgId) {
			this.desgId = desgId;
		}

		public Long getMrfId() {
			return mrfId;
		}

		public void setMrfId(Long mrfId) {
			this.mrfId = mrfId;
		}

		public Long getTtlId() {
			return ttlId;
		}

		public void setTtlId(Long ttlId) {
			this.ttlId = ttlId;
		}
	    
}
