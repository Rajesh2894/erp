package com.abm.mainet.cfc.challan.service;

import java.io.Serializable;
import java.util.Map;

public class PushToPayRequestDto implements Serializable {

	private String appKey;
	private String username;
	private String customerMobileNumber;
	private String amount;
	private String externalRefNumber;
	private String externalRefNumber2;
	private String externalRefNumber3;
	private String externalRefNumber4;
	private String externalRefNumber5;
	private String externalRefNumber6;
	private String externalRefNumber7;

	private String paymentBy;
	private Map<String,String> pushTo;

	public String getAppKey() {
		return appKey;
	}

	public String getUsername() {
		return username;
	}

	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public String getAmount() {
		return amount;
	}

	public String getExternalRefNumber() {
		return externalRefNumber;
	}

	public String getExternalRefNumber2() {
		return externalRefNumber2;
	}

	public String getExternalRefNumber3() {
		return externalRefNumber3;
	}

	public String getExternalRefNumber4() {
		return externalRefNumber4;
	}

	public String getExternalRefNumber5() {
		return externalRefNumber5;
	}

	public String getExternalRefNumber6() {
		return externalRefNumber6;
	}

	public String getExternalRefNumber7() {
		return externalRefNumber7;
	}


	

	public String getPaymentBy() {
		return paymentBy;
	}

	public Map<String,String>  getPushTo() {
		return pushTo;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setExternalRefNumber(String externalRefNumber) {
		this.externalRefNumber = externalRefNumber;
	}

	public void setExternalRefNumber2(String externalRefNumber2) {
		this.externalRefNumber2 = externalRefNumber2;
	}

	public void setExternalRefNumber3(String externalRefNumber3) {
		this.externalRefNumber3 = externalRefNumber3;
	}

	public void setExternalRefNumber4(String externalRefNumber4) {
		this.externalRefNumber4 = externalRefNumber4;
	}

	public void setExternalRefNumber5(String externalRefNumber5) {
		this.externalRefNumber5 = externalRefNumber5;
	}

	public void setExternalRefNumber6(String externalRefNumber6) {
		this.externalRefNumber6 = externalRefNumber6;
	}

	public void setExternalRefNumber7(String externalRefNumber7) {
		this.externalRefNumber7 = externalRefNumber7;
	}


	

	public void setPaymentBy(String paymentBy) {
		this.paymentBy = paymentBy;
	}

	public void setPushTo(Map<String,String>  pushTo) {
		this.pushTo = pushTo;
	}

}
