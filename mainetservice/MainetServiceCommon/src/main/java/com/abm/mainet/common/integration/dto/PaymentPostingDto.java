package com.abm.mainet.common.integration.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class PaymentPostingDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9105445323745012359L;
	private String applNo;
	private String payeeName;
	private String payeeEmail;
	private String payeeMobileNo;
	private String dept;
	private String paymentAmount;
	private String paymentDateAndTime;
	private String bankRefNum;
	private String requestTransactionNo;
	private String modeOfPay;
	private String orgCode;
	private String ipMac;
	

	
	public String getPaymentDateAndTime() {
		return paymentDateAndTime;
	}
	public void setPaymentDateAndTime(String paymentDateAndTime) {
		this.paymentDateAndTime = paymentDateAndTime;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeEmail() {
		return payeeEmail;
	}
	public void setPayeeEmail(String payeeEmail) {
		this.payeeEmail = payeeEmail;
	}

	public String getPayeeMobileNo() {
		return payeeMobileNo;
	}
	public void setPayeeMobileNo(String payeeMobileNo) {
		this.payeeMobileNo = payeeMobileNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getBankRefNum() {
		return bankRefNum;
	}
	public void setBankRefNum(String bankRefNum) {
		this.bankRefNum = bankRefNum;
	}
	public String getRequestTransactionNo() {
		return requestTransactionNo;
	}
	public void setRequestTransactionNo(String requestTransactionNo) {
		this.requestTransactionNo = requestTransactionNo;
	}
	public String getModeOfPay() {
		return modeOfPay;
	}
	public void setModeOfPay(String modeOfPay) {
		this.modeOfPay = modeOfPay;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getIpMac() {
		return ipMac;
	}
	public void setIpMac(String ipMac) {
		this.ipMac = ipMac;
	}
	
}
