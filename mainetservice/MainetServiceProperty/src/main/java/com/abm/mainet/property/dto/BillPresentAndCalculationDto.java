package com.abm.mainet.property.dto;

import java.io.Serializable;

public class BillPresentAndCalculationDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2398190803994738063L;

    private double arrearsTaxAmt;
    private double currentYearTaxAmt;
    private double totalTaxAmt;
    private Long taxCategoryId;
    private Long taxSubCategoryId;
    private Long taxSequenceId;
    private Long taxId;
    private String taxCategoryCodeValue;
    private double chargeAmount;
    private String chargeDescEng;
    private Long displaySeq;
    private String taxCode;
    private double rate;
    private String baseRate;
    private String ruleId;
    private String taxMethod;
    private Long parentCode;
    private double percentageRate;

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }

    public double getArrearsTaxAmt() {
        return arrearsTaxAmt;
    }

    public void setArrearsTaxAmt(double arrearsTaxAmt) {
        this.arrearsTaxAmt = arrearsTaxAmt;
    }

    public double getCurrentYearTaxAmt() {
        return currentYearTaxAmt;
    }

    public void setCurrentYearTaxAmt(double currentYearTaxAmt) {
        this.currentYearTaxAmt = currentYearTaxAmt;
    }

    public double getTotalTaxAmt() {
        return totalTaxAmt;
    }

    public void setTotalTaxAmt(double totalTaxAmt) {
        this.totalTaxAmt = totalTaxAmt;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getTaxSubCategoryId() {
        return taxSubCategoryId;
    }

    public void setTaxSubCategoryId(Long taxSubCategoryId) {
        this.taxSubCategoryId = taxSubCategoryId;
    }

    public Long getTaxSequenceId() {
        return taxSequenceId;
    }

    public void setTaxSequenceId(Long taxSequenceId) {
        this.taxSequenceId = taxSequenceId;
    }

    public String getTaxCategoryCodeValue() {
        return taxCategoryCodeValue;
    }

    public void setTaxCategoryCodeValue(String taxCategoryCodeValue) {
        this.taxCategoryCodeValue = taxCategoryCodeValue;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getChargeDescEng() {
        return chargeDescEng;
    }

    public void setChargeDescEng(String chargeDescEng) {
        this.chargeDescEng = chargeDescEng;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Long getDisplaySeq() {
        return displaySeq;
    }

    public void setDisplaySeq(Long displaySeq) {
        this.displaySeq = displaySeq;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(String baseRate) {
        this.baseRate = baseRate;
    }

    public String getTaxMethod() {
        return taxMethod;
    }

    public void setTaxMethod(String taxMethod) {
        this.taxMethod = taxMethod;
    }

    public Long getParentCode() {
        return parentCode;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

	public double getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(double percentageRate) {
		this.percentageRate = percentageRate;
	}
    
}
