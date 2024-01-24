package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author vishwanath.s
 *
 */
public class AccountReceiptExternalDto implements Serializable{
	
	private static final long serialVersionUID = 334282995795022674L;

	private String receiptNumber;
	
	private Date receiptDate;
	
	private String receiptCategory;
	
	private String vendorName;
	
	private String receivedFrom;
	
	private String departmentName;
	
	private String mobileNumber;
	
	private String emailId;
	
	private String fieldCode;
	
	private String ulbCode;
	
	private String bankName;
	
	private Long createdBy;
	
	private String payMode;
	
	private String ifscCode;
	
	private String instrumentNo;
	
	private Date instrumentDate;
	
	private String narration;
	
	private String checkSum;
	
	private List<AccountReceiptFeesExternalDto> receiptFeeDetailList = new ArrayList<>();

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptCategory() {
		return receiptCategory;
	}

	public void setReceiptCategory(String receiptCategory) {
		this.receiptCategory = receiptCategory;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getUlbCode() {
		return ulbCode;
	}

	public void setUlbCode(String ulbCode) {
		this.ulbCode = ulbCode;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getInstrumentNo() {
		return instrumentNo;
	}

	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}

	public Date getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(Date instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public List<AccountReceiptFeesExternalDto> getReceiptFeeDetailList() {
		return receiptFeeDetailList;
	}

	public void setReceiptFeeDetailList(List<AccountReceiptFeesExternalDto> receiptFeeDetailList) {
		this.receiptFeeDetailList = receiptFeeDetailList;
	}

	@Override
	public String toString() {
		return "AccountReceiptExternalDto [receiptNumber=" + receiptNumber + ", receiptDate=" + receiptDate
				+ ", receiptCategory=" + receiptCategory + ", vendorName=" + vendorName + ", receivedFrom="
				+ receivedFrom + ", departmentName=" + departmentName + ", mobileNumber=" + mobileNumber + ", emailId="
				+ emailId + ", fieldCode=" + fieldCode + ", ulbCode=" + ulbCode + ", bankName=" + bankName
				+ ", createdBy=" + createdBy + ", payMode=" + payMode + ", ifscCode=" + ifscCode + ", instrumentNo="
				+ instrumentNo + ", instrumentDate=" + instrumentDate + ", narration=" + narration + ", checkSum="
				+ checkSum + ", receiptFeeDetailList=" + receiptFeeDetailList + "]";
	}
	
	
}
