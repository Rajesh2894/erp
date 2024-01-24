package com.abm.mainet.water.dto;

import java.io.Serializable;

/**
 * @author Akshata Bhat
 */
public class WaterBillPrintSkdclDTO  implements Serializable{

	private static final long serialVersionUID = -1704064136710644611L;

	private String connectionNo;
	
	private String tariffCategory;
	
	private String tariffSubCategory;
	
	private String ward;
	
	private String zone;
	
	private Double tariffAmount;
	
	private Integer noOfFamilyMembers;
	
	private String billNo;
	
	private String billGenerationDate;
	
	private String billDueDate;
	
	private String meterNo;
	
	private Long meterReading;
	
	private String billingCycle;
	
	private String gapCode;
	
	private String prevReadingDate;
	
	private Long prevMeterReading;
	
	private String currentMeterReadingDate;

	private Long currentMeterReading;

	private Long consumption;
	
	private Double adjustmentAmt;
	
	private String connectionSize;
	
	private String oldConnNo;
	
	private String connectionDate;
	
	private Double meterRent;
	
	private Double meterCost;
	
	private Double currentBillAmt;
	
	private Double arrearsAmt;
	
	private Double penalty;
	
	private String prevBillDate;
	
	private Double outstandingAmount;
	
	private String address;
	
	private String name;
	
	private String propertyNo;
	
	private String mobileNo;
	
	private String amountInWords;
	
	private String pincode;
	
	private Double advanceAmount;
	
	private String prevBillNo;
	
	private String prevBillAmount;

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public String getTariffCategory() {
		return tariffCategory;
	}

	public void setTariffCategory(String tariffCategory) {
		this.tariffCategory = tariffCategory;
	}

	public String getTariffSubCategory() {
		return tariffSubCategory;
	}

	public void setTariffSubCategory(String tariffSubCategory) {
		this.tariffSubCategory = tariffSubCategory;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Double getTariffAmount() {
		return tariffAmount;
	}

	public void setTariffAmount(Double tariffAmount) {
		this.tariffAmount = tariffAmount;
	}

	public Integer getNoOfFamilyMembers() {
		return noOfFamilyMembers;
	}

	public void setNoOfFamilyMembers(Integer noOfFamilyMembers) {
		this.noOfFamilyMembers = noOfFamilyMembers;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillGenerationDate() {
		return billGenerationDate;
	}

	public void setBillGenerationDate(String billGenerationDate) {
		this.billGenerationDate = billGenerationDate;
	}

	public String getBillDueDate() {
		return billDueDate;
	}

	public void setBillDueDate(String billDueDate) {
		this.billDueDate = billDueDate;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public Long getMeterReading() {
		return meterReading;
	}

	public void setMeterReading(Long meterReading) {
		this.meterReading = meterReading;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getGapCode() {
		return gapCode;
	}

	public void setGapCode(String gapCode) {
		this.gapCode = gapCode;
	}

	public String getPrevReadingDate() {
		return prevReadingDate;
	}

	public void setPrevReadingDate(String prevReadingDate) {
		this.prevReadingDate = prevReadingDate;
	}

	public Long getPrevMeterReading() {
		return prevMeterReading;
	}

	public void setPrevMeterReading(Long prevMeterReading) {
		this.prevMeterReading = prevMeterReading;
	}

	public String getCurrentMeterReadingDate() {
		return currentMeterReadingDate;
	}

	public void setCurrentMeterReadingDate(String currentMeterReadingDate) {
		this.currentMeterReadingDate = currentMeterReadingDate;
	}

	public Long getCurrentMeterReading() {
		return currentMeterReading;
	}

	public void setCurrentMeterReading(Long currentMeterReading) {
		this.currentMeterReading = currentMeterReading;
	}

	public Long getConsumption() {
		return consumption;
	}

	public void setConsumption(Long consumption) {
		this.consumption = consumption;
	}

	public Double getAdjustmentAmt() {
		return adjustmentAmt;
	}

	public void setAdjustmentAmt(Double adjustmentAmt) {
		this.adjustmentAmt = adjustmentAmt;
	}

	public String getConnectionSize() {
		return connectionSize;
	}

	public void setConnectionSize(String connectionSize) {
		this.connectionSize = connectionSize;
	}

	public String getOldConnNo() {
		return oldConnNo;
	}

	public void setOldConnNo(String oldConnNo) {
		this.oldConnNo = oldConnNo;
	}

	public String getConnectionDate() {
		return connectionDate;
	}

	public void setConnectionDate(String connectionDate) {
		this.connectionDate = connectionDate;
	}

	public Double getMeterRent() {
		return meterRent;
	}

	public void setMeterRent(Double meterRent) {
		this.meterRent = meterRent;
	}

	public Double getMeterCost() {
		return meterCost;
	}

	public void setMeterCost(Double meterCost) {
		this.meterCost = meterCost;
	}

	public Double getCurrentBillAmt() {
		return currentBillAmt;
	}

	public void setCurrentBillAmt(Double currentBillAmt) {
		this.currentBillAmt = currentBillAmt;
	}

	public Double getArrearsAmt() {
		return arrearsAmt;
	}

	public void setArrearsAmt(Double arrearsAmt) {
		this.arrearsAmt = arrearsAmt;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public String getPrevBillDate() {
		return prevBillDate;
	}

	public void setPrevBillDate(String prevBillDate) {
		this.prevBillDate = prevBillDate;
	}

	public Double getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(Double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getPrevBillNo() {
		return prevBillNo;
	}

	public void setPrevBillNo(String prevBillNo) {
		this.prevBillNo = prevBillNo;
	}

	public String getPrevBillAmount() {
		return prevBillAmount;
	}

	public void setPrevBillAmount(String prevBillAmount) {
		this.prevBillAmount = prevBillAmount;
	}

	@Override
	public String toString() {
		return "WaterBillPrintSkdclDTO [connectionNo=" + connectionNo + ", tariffCategory=" + tariffCategory
				+ ", tariffSubCategory=" + tariffSubCategory + ", ward=" + ward + ", zone=" + zone + ", tariffAmount="
				+ tariffAmount + ", noOfFamilyMembers=" + noOfFamilyMembers + ", billNo=" + billNo
				+ ", billGenerationDate=" + billGenerationDate + ", billDueDate=" + billDueDate + ", meterNo=" + meterNo
				+ ", meterReading=" + meterReading + ", billingCycle=" + billingCycle + ", gapCode=" + gapCode
				+ ", prevReadingDate=" + prevReadingDate + ", prevMeterReading=" + prevMeterReading
				+ ", currentMeterReadingDate=" + currentMeterReadingDate + ", currentMeterReading="
				+ currentMeterReading + ", consumption=" + consumption + ", adjustmentAmt=" + adjustmentAmt
				+ ", connectionSize=" + connectionSize + ", oldConnNo=" + oldConnNo + ", connectionDate="
				+ connectionDate + ", meterRent=" + meterRent + ", meterCost=" + meterCost + ", currentBillAmt="
				+ currentBillAmt + ", arrearsAmt=" + arrearsAmt + ", penalty=" + penalty + ", prevBillDate="
				+ prevBillDate + ", outstandingAmount=" + outstandingAmount + ", address=" + address + ", name=" + name
				+ ", propertyNo=" + propertyNo + ", mobileNo=" + mobileNo + ", amountInWords=" + amountInWords
				+ ", pincode=" + pincode + ", advanceAmount=" + advanceAmount + ", prevBillNo=" + prevBillNo
				+ ", prevBillAmount=" + prevBillAmount + "]";
	}

}