package com.abm.mainet.water.dto;

import java.io.Serializable;

/**
 * @author Rahul.Yadav
 *
 */
public class WaterBillTaxDTO implements Serializable {

    private static final long serialVersionUID = -2544340187609469844L;

    private double taxAmount;

    private double arrearTaxAmount;

    private double total;

    private String taxdescription;

    private double balabceTaxAmount;

    private Long dispSeq;
    
    private Long taxId;

    public String getTaxdescription() {
        return taxdescription;
    }

    public void setTaxdescription(final String taxdescription) {
        this.taxdescription = taxdescription;
    }

    public double getArrearTaxAmount() {
        return arrearTaxAmount;
    }

    public void setArrearTaxAmount(final double arrearTaxAmount) {
        this.arrearTaxAmount = arrearTaxAmount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(final double total) {
        this.total = total;
    }

    public void setTaxAmount(final double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getBalabceTaxAmount() {
        return balabceTaxAmount;
    }

    public void setBalabceTaxAmount(final double balabceTaxAmount) {
        this.balabceTaxAmount = balabceTaxAmount;
    }

    public Long getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Long dispSeq) {
        this.dispSeq = dispSeq;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }
    
}
