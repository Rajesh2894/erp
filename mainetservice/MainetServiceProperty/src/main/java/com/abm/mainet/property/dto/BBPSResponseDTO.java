package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BBPSResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private BigDecimal billAmount;
	private Long billNo;
	private String billDate;
	private String billFromDate;
	private String billToDate;
	private String billDueDate;
	private String consumerName;
	private String consumerAddress;
	private String propertyNumber;
	private String ts;
	private String txnReferenceId;
	private String responseReason;
	private String responseCode;
	private String status;
	private Long orgId;
	private String orgName;
	public BigDecimal getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}
	public Long getBillNo() {
		return billNo;
	}
	public void setBillNo(Long billNo) {
		this.billNo = billNo;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getBillFromDate() {
		return billFromDate;
	}
	public void setBillFromDate(String billFromDate) {
		this.billFromDate = billFromDate;
	}
	public String getBillToDate() {
		return billToDate;
	}
	public void setBillToDate(String billToDate) {
		this.billToDate = billToDate;
	}
	public String getBillDueDate() {
		return billDueDate;
	}
	public void setBillDueDate(String billDueDate) {
		this.billDueDate = billDueDate;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getConsumerAddress() {
		return consumerAddress;
	}
	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}
	public String getPropertyNumber() {
		return propertyNumber;
	}
	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getTxnReferenceId() {
		return txnReferenceId;
	}
	public void setTxnReferenceId(String txnReferenceId) {
		this.txnReferenceId = txnReferenceId;
	}
	public String getResponseReason() {
		return responseReason;
	}
	public void setResponseReason(String responseReason) {
		this.responseReason = responseReason;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Override
	public String toString() {
		return "BBPSResponseDTO [billAmount=" + billAmount + ", billNo=" + billNo + ", billDate=" + billDate
				+ ", billFromDate=" + billFromDate + ", billToDate=" + billToDate + ", billDueDate=" + billDueDate
				+ ", consumerName=" + consumerName + ", consumerAddress=" + consumerAddress + ", propertyNumber="
				+ propertyNumber + ", ts=" + ts + ", txnReferenceId=" + txnReferenceId + ", responseReason="
				+ responseReason + ", responseCode=" + responseCode + ", status=" + status + ", orgId=" + orgId
				+ ", orgName=" + orgName + "]";
	}
	


}
