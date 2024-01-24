package com.abm.mainet.property.dto;

import java.io.Serializable;

public class DemandLevelRebateDTO implements Serializable {

    private static final long serialVersionUID = 7059940597751365308L;

    private Long taxId;

    private double taxAmount;

    private Long yearId;

    private Long billDetId;

    private Long billMasId;

    private Long taxCategory;

    private Double totalDetAmount;

    private String rmNarration;

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

    public Double getTotalDetAmount() {
        return totalDetAmount;
    }

    public void setTotalDetAmount(Double totalDetAmount) {
        this.totalDetAmount = totalDetAmount;
    }

    public String getRmNarration() {
        return rmNarration;
    }

    public void setRmNarration(String rmNarration) {
        this.rmNarration = rmNarration;
    }

}
