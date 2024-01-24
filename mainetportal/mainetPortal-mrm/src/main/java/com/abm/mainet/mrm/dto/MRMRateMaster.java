package com.abm.mainet.mrm.dto;

import com.abm.mainet.common.dto.CommonModel;

public class MRMRateMaster extends CommonModel implements java.io.Serializable {

    private static final long serialVersionUID = 6904374979643977311L;
    private long taxId;
    private String taxType;
    private String taxCode;
    private String parentTaxCode;
    private String taxCategory;
    private String taxSubCategory;
    private long rateStartDate;
    private String chargeApplicableAt;
    private double flatRate;
    private double slabRate1;
    private double slabRate2;
    private double slabRate3;
    private long slab1;
    private long slab2;
    private long slab3;
    private double percentageRate;
    private String chargeDescEng;
    private String chargeDescReg;

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getParentTaxCode() {
        return parentTaxCode;
    }

    public void setParentTaxCode(String parentTaxCode) {
        this.parentTaxCode = parentTaxCode;
    }

    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public String getTaxSubCategory() {
        return taxSubCategory;
    }

    public void setTaxSubCategory(String taxSubCategory) {
        this.taxSubCategory = taxSubCategory;
    }

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(long rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

    public String getChargeApplicableAt() {
        return chargeApplicableAt;
    }

    public void setChargeApplicableAt(String chargeApplicableAt) {
        this.chargeApplicableAt = chargeApplicableAt;
    }

    public double getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(double flatRate) {
        this.flatRate = flatRate;
    }

    public double getSlabRate1() {
        return slabRate1;
    }

    public void setSlabRate1(double slabRate1) {
        this.slabRate1 = slabRate1;
    }

    public double getSlabRate2() {
        return slabRate2;
    }

    public void setSlabRate2(double slabRate2) {
        this.slabRate2 = slabRate2;
    }

    public double getSlabRate3() {
        return slabRate3;
    }

    public void setSlabRate3(double slabRate3) {
        this.slabRate3 = slabRate3;
    }

    public long getSlab1() {
        return slab1;
    }

    public void setSlab1(long slab1) {
        this.slab1 = slab1;
    }

    public long getSlab2() {
        return slab2;
    }

    public void setSlab2(long slab2) {
        this.slab2 = slab2;
    }

    public long getSlab3() {
        return slab3;
    }

    public void setSlab3(long slab3) {
        this.slab3 = slab3;
    }

    public double getPercentageRate() {
        return percentageRate;
    }

    public void setPercentageRate(double percentageRate) {
        this.percentageRate = percentageRate;
    }

    public long getTaxId() {
        return taxId;
    }

    public void setTaxId(long taxId) {
        this.taxId = taxId;
    }

    public String getChargeDescEng() {
        return chargeDescEng;
    }

    public void setChargeDescEng(String chargeDescEng) {
        this.chargeDescEng = chargeDescEng;
    }

    public String getChargeDescReg() {
        return chargeDescReg;
    }

    public void setChargeDescReg(String chargeDescReg) {
        this.chargeDescReg = chargeDescReg;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public MRMRateMaster ruleResult() {
        return this;
    }


    public void setSlabRate(double slabRate1, double slabRate2, double slabRate3) {
        this.slabRate1 = slabRate1;
        this.slabRate2 = slabRate2;
        this.slabRate3 = slabRate3;
    }

    public void setSlab(long slab1, long slab2, long slab3) {
        this.slab1 = slab1;
        this.slab2 = slab2;
        this.slab3 = slab3;
    }

    public MRMRateMaster() {
        super();
        taxType = "NA";
        parentTaxCode = "NA";
        taxCode = "NA";
        taxCategory = "NA";
        taxSubCategory = "NA";
        slab1 = 0l;
        slab2 = 0l;
        slab3 = 0l;
        slabRate1 = 0.0d;
        slabRate2 = 0.0d;
        slabRate3 = 0.0d;
        flatRate = 0.0d;
        percentageRate = 0.0d;
        chargeApplicableAt = "NA";
        rateStartDate = 0;

    }

}
