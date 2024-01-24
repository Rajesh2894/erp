package com.abm.mainet.account.rest.dto;

import java.io.Serializable;

/**
 * 
 * @author vishwanath.s
 *
 */
public class VoucherReversalExtDTO implements Serializable{

	private static final long serialVersionUID = 6556553207468594183L;

	private String ulbCode;
	private String transactionNo;
	private String transactionType;
	private String transactionDate;
	private String approvalOrderNo;
	private String narration;
	private String approvedBy;
	private String fieldCode;
	private String checkSum;
	private String departmentName;
	
	public String getUlbCode() {
		return ulbCode;
	}
	public void setUlbCode(String ulbCode) {
		this.ulbCode = ulbCode;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getApprovalOrderNo() {
		return approvalOrderNo;
	}
	public void setApprovalOrderNo(String approvalOrderNo) {
		this.approvalOrderNo = approvalOrderNo;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
}