package com.abm.mainet.common.dto;

import java.util.Date;

public class BillReceiptPrintingDTO {

    private Long taxId;

    private double taxAmount;

    private double payableAmount;

    private Long yearId;

    private Long billDetId;

    private Long billMasId;

    private Long taxCategory;

    private String taxCategoryCode;

    private Double totalDetAmount;

    private Double arrearAmount;

    private String rmNarration;

    private double totalDemand;

    private String bmIdNo;

    private Date billDate;

    private Long displaySeq;
    
    private Date billFromDate;
    
    private Date billToDate;
    
    private Double totalSurcharge;

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public Long getBillDetId() {
        return billDetId;
    }

    public void setBillDetId(Long billDetId) {
        this.billDetId = billDetId;
    }

    public Long getBillMasId() {
        return billMasId;
    }

    public void setBillMasId(Long billMasId) {
        this.billMasId = billMasId;
    }

    public Long getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(Long taxCategory) {
        this.taxCategory = taxCategory;
    }

    public String getTaxCategoryCode() {
        return taxCategoryCode;
    }

    public void setTaxCategoryCode(String taxCategoryCode) {
        this.taxCategoryCode = taxCategoryCode;
    }

    public Double getTotalDetAmount() {
        return totalDetAmount;
    }

    public void setTotalDetAmount(Double totalDetAmount) {
        this.totalDetAmount = totalDetAmount;
    }

    public Double getArrearAmount() {
        return arrearAmount;
    }

    public void setArrearAmount(Double arrearAmount) {
        this.arrearAmount = arrearAmount;
    }

    public String getRmNarration() {
        return rmNarration;
    }

    public void setRmNarration(String rmNarration) {
        this.rmNarration = rmNarration;
    }

    public double getTotalDemand() {
        return totalDemand;
    }

    public void setTotalDemand(double totalDemand) {
        this.totalDemand = totalDemand;
    }

    public String getBmIdNo() {
        return bmIdNo;
    }

    public void setBmIdNo(String bmIdNo) {
        this.bmIdNo = bmIdNo;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Long getDisplaySeq() {
        return displaySeq;
    }

    public void setDisplaySeq(Long displaySeq) {
        this.displaySeq = displaySeq;
    }

	public Date getBillFromDate() {
		return billFromDate;
	}

	public void setBillFromDate(Date billFromDate) {
		this.billFromDate = billFromDate;
	}

	public Date getBillToDate() {
		return billToDate;
	}

	public void setBillToDate(Date billToDate) {
		this.billToDate = billToDate;
	}

	public Double getTotalSurcharge() {
		return totalSurcharge;
	}

	public void setTotalSurcharge(Double totalSurcharge) {
		this.totalSurcharge = totalSurcharge;
	}
    
}
