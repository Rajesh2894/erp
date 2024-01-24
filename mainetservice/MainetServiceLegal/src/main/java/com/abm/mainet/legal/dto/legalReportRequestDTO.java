package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

public class legalReportRequestDTO implements  Serializable {
 
	private static final long serialVersionUID = 1L;

	private Long crtType;
	private Long AdvName;
	private Long cseStatus;
	private Long cseType;
	private Long cseCategory1;
	private Long cseCategory2;
	private Long cseCategory3;
	private Long cseCategory4;
	private Long cseCategory5;
	private Date  csefrmDate;
	private Date  csetoDate; 
	private Long  cseDeptId;
	private Long cseDivWise;
	private String csereportName;
	private Long gender;
	
	public  Long getCrtType() {
		return crtType;
	}
	public void setCrtType(Long crtType) {
		this.crtType = crtType;
	}
	public Long getAdvName() {
		return AdvName;
	}
	public void setAdvName(Long advName) {
		AdvName = advName;
	}
	public Long getCseStatus() {
		return cseStatus;
	}
	public void setCseStatus(Long cseStatus) {
		this.cseStatus = cseStatus;
	}
	public Long getCseType() {
		return cseType;
	}
	public void setCseType(Long cseType) {
		this.cseType = cseType;
	}
	public Long getCseCategory1() {
		return cseCategory1;
	}
	public void setCseCategory1(Long cseCategory1) {
		this.cseCategory1 = cseCategory1;
	}
	
	public Long getCseCategory2() {
		return cseCategory2;
	}
	public void setCseCategory2(Long cseCategory2) {
		this.cseCategory2 = cseCategory2;
	}
	public Long getCseCategory3() {
		return cseCategory3;
	}
	public void setCseCategory3(Long cseCategory3) {
		this.cseCategory3 = cseCategory3;
	}
	public Long getCseCategory4() {
		return cseCategory4;
	}
	public void setCseCategory4(Long cseCategory4) {
		this.cseCategory4 = cseCategory4;
	}
	public Long getCseCategory5() {
		return cseCategory5;
	}
	public void setCseCategory5(Long cseCategory5) {
		this.cseCategory5 = cseCategory5;
	}
	public Date getCsefrmDate() {
		return csefrmDate;
	}
	public void setCsefrmDate(Date csefrmDate) {
		this.csefrmDate = csefrmDate;
	}
	public Date getCsetoDate() {
		return csetoDate;
	}
	public void setCsetoDate(Date csetoDate) {
		this.csetoDate = csetoDate;
	}
	public Long getCseDeptId() {
		return cseDeptId;
	}
	public void setCseDeptId(Long cseDeptId) {
		this.cseDeptId = cseDeptId;
	}
	public Long getCseDivWise() {
		return cseDivWise;
	}
	public void setCseDivWise(Long cseDivWise) {
		this.cseDivWise = cseDivWise;
	}
	public String getCsereportName() {
		return csereportName;
	}
	public void setCsereportName(String csereportName) {
		this.csereportName = csereportName;
	}
	public Long getGender() {
		return gender;
	}
	public void setGender(Long gender) {
		this.gender = gender;
	}
	
	
	
	
}