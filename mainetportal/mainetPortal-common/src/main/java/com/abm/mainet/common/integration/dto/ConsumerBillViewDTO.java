package com.abm.mainet.common.integration.dto;

public class ConsumerBillViewDTO {

	private String consumerName;
	private String consumerCode;
	private String conAddress;
	private String waterBillNo;
	private String waterBillAmount;
	private String waterPaymentDueDate;
	private String status;
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getConsumerCode() {
		return consumerCode;
	}
	public void setConsumerCode(String consumerCode) {
		this.consumerCode = consumerCode;
	}
	public String getConAddress() {
		return conAddress;
	}
	public void setConAddress(String conAddress) {
		this.conAddress = conAddress;
	}
	public String getWaterBillNo() {
		return waterBillNo;
	}
	public void setWaterBillNo(String waterBillNo) {
		this.waterBillNo = waterBillNo;
	}
	public String getWaterBillAmount() {
		return waterBillAmount;
	}
	public void setWaterBillAmount(String waterBillAmount) {
		this.waterBillAmount = waterBillAmount;
	}
	public String getWaterPaymentDueDate() {
		return waterPaymentDueDate;
	}
	public void setWaterPaymentDueDate(String waterPaymentDueDate) {
		this.waterPaymentDueDate = waterPaymentDueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "ConsumerBillViewDTO [consumerName=" + consumerName + ", consumerCode=" + consumerCode + ", conAddress="
				+ conAddress + ", waterBillNo=" + waterBillNo + ", waterBillAmount=" + waterBillAmount
				+ ", waterPaymentDueDate=" + waterPaymentDueDate + ", status=" + status + "]";
	}
}
