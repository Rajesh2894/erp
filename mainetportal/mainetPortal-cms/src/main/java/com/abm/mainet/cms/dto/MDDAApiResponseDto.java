package com.abm.mainet.cms.dto;

import java.io.Serializable;

public class MDDAApiResponseDto implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 

	private int mont;
	private Long year; 
	private  String subDesc; 
	private String subCode; 
	private String propCategory;
	private String statusCode; 
	private String statusDesc; 
	private  String objected;
	private int count;
	private String approvalStatus;
     
     
	public int getMont() {
		return mont;
	}
	public void setMont(int mont) {
		this.mont = mont;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	public String getSubDesc() {
		return subDesc;
	}
	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getPropCategory() {
		return propCategory;
	}
	public void setPropCategory(String propCategory) {
		this.propCategory = propCategory;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getObjected() {
		return objected;
	}
	public void setObjected(String objected) {
		this.objected = objected;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
     
     
     
     
}
