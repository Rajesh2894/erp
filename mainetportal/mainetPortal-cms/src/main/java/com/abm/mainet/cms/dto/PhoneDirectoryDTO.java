/**
 *
 */
package com.abm.mainet.cms.dto;

import java.io.Serializable;

/**
 * @author aparna.magdum
 */
public class PhoneDirectoryDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
   
    private Long srNo;
    private String department;
    private String name;
    private String division;
    private String designation;
    private String telephone;
    private Long faxNo;
    private Long mobile;
    private String emailId;
	
	public Long getSrNo() {
		return srNo;
	}
	public void setSrNo(Long srNo) {
		this.srNo = srNo;
	}
	public String getDepartment() {
		return department;
	}
	public String getName() {
		return name;
	}
	public String getDivision() {
		return division;
	}
	public String getDesignation() {
		return designation;
	}
	public String getTelephone() {
		return telephone;
	}
	public Long getFaxNo() {
		return faxNo;
	}
	public Long getMobile() {
		return mobile;
	}
	public String getEmailId() {
		return emailId;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setFaxNo(Long faxNo) {
		this.faxNo = faxNo;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	

   

}
