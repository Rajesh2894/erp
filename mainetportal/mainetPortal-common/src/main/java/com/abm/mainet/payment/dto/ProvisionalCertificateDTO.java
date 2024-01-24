/**
 * 
 */
package com.abm.mainet.payment.dto;

import java.util.Date;

/**
 * @author rajesh.das
 *
 */
public class ProvisionalCertificateDTO {

	private Long applicationNo;
	
	private Date applicationDate;
	
	private String fullName;
	
	private String address1;
	
	private String address2;
	
	private Long pincode;
	
	private String consumerName;
	
	private String consumerHouseNumber;
	
	private String consumerLandmark;
	
	private String ward;
	
	private String consumerAddress;
	
	private String consumerMobile;
	
	private String consumerEmail;
	
	private String tapTypeConnection;
	
	private String tapUsage;
	
	private Double applicationFee;
	
	private String paymentMode;
	
	private Date paymentDate;
	
	private String fatherHusbandName;
	private String conNo;
	
	private String provApplicationDate;
	
	private String provPaymentDate;
	
	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerHouseNumber() {
		return consumerHouseNumber;
	}

	public void setConsumerHouseNumber(String consumerHouseNumber) {
		this.consumerHouseNumber = consumerHouseNumber;
	}

	public String getConsumerLandmark() {
		return consumerLandmark;
	}

	public void setConsumerLandmark(String consumerLandmark) {
		this.consumerLandmark = consumerLandmark;
	}

	public String getConsumerAddress() {
		return consumerAddress;
	}

	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}

	public String getConsumerMobile() {
		return consumerMobile;
	}

	public void setConsumerMobile(String consumerMobile) {
		this.consumerMobile = consumerMobile;
	}

	public String getConsumerEmail() {
		return consumerEmail;
	}

	public void setConsumerEmail(String consumerEmail) {
		this.consumerEmail = consumerEmail;
	}

	public String getTapTypeConnection() {
		return tapTypeConnection;
	}

	public void setTapTypeConnection(String tapTypeConnection) {
		this.tapTypeConnection = tapTypeConnection;
	}

	public String getTapUsage() {
		return tapUsage;
	}

	public void setTapUsage(String tapUsage) {
		this.tapUsage = tapUsage;
	}

	public Double getApplicationFee() {
		return applicationFee;
	}

	public void setApplicationFee(Double applicationFee) {
		this.applicationFee = applicationFee;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Override
	public String toString() {
		return "ProvisionalCertificateDTO [applicationNo=" + applicationNo + ", applicationDate=" + applicationDate
				+ ", fullName=" + fullName + ", address1=" + address1 + ", address2=" + address2 + ", pincode="
				+ pincode + ", consumerName=" + consumerName + ", consumerHouseNumber=" + consumerHouseNumber
				+ ", consumerLandmark=" + consumerLandmark + ", ward=" + ward + ", consumerAddress=" + consumerAddress
				+ ", consumerMobile=" + consumerMobile + ", consumerEmail=" + consumerEmail + ", tapTypeConnection="
				+ tapTypeConnection + ", tapUsage=" + tapUsage + ", applicationFee=" + applicationFee + ", paymentMode="
				+ paymentMode + ", paymentDate=" + paymentDate + "]";
	}

	public String getFatherHusbandName() {
		return fatherHusbandName;
	}

	public void setFatherHusbandName(String fatherHusbandName) {
		this.fatherHusbandName = fatherHusbandName;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getProvApplicationDate() {
		return provApplicationDate;
	}

	public void setProvApplicationDate(String provApplicationDate) {
		this.provApplicationDate = provApplicationDate;
	}

	public String getProvPaymentDate() {
		return provPaymentDate;
	}

	public void setProvPaymentDate(String provPaymentDate) {
		this.provPaymentDate = provPaymentDate;
	}

}
