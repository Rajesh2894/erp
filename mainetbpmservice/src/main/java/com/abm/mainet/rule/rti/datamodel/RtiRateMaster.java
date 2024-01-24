package com.abm.mainet.rule.rti.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.abm.mainet.rule.datamodel.CommonModel;

public class RtiRateMaster extends CommonModel {

    private static final long serialVersionUID = 693545759862235336L;

    private String taxType;
    private String taxCode;
    private String taxCategory;
    private String taxSubCategory;
    private Long rateStartDate;
    private String chargeApplicableAt;
    private String mediaType;
    private long slab1;
    private long slab2;
    private long slab3;
    private long slab4;
    private double slabRate1;
    private double slabRate2;
    private double slabRate3;
    private double slabRate4;
    private double flatRate;
    private double percentageRate;
    private double chargeAmount;
    private double freeCopy;
    private double quantity;
    private long taxId;

    public Long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(Long rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

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

    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public String getChargeApplicableAt() {
        return chargeApplicableAt;
    }

    public void setChargeApplicableAt(String chargeApplicableAt) {
        this.chargeApplicableAt = chargeApplicableAt;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
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

    public long getSlab4() {
        return slab4;
    }

    public void setSlab4(long slab4) {
        this.slab4 = slab4;
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

    public double getSlabRate4() {
        return slabRate4;
    }

    public void setSlabRate4(double slabRate4) {
        this.slabRate4 = slabRate4;
    }

    public double getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(double flatRate) {
        this.flatRate = flatRate;
    }

    public double getPercentageRate() {
        return percentageRate;
    }

    public void setPercentageRate(double percentageRate) {
        this.percentageRate = percentageRate;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public void setSlab(final long slab1, final long slab2, final long slab3,
            final long slab4) {

        this.slab1 = slab1;
        this.slab2 = slab2;
        this.slab3 = slab3;
        this.slab4 = slab4;
    }

    public void setSlabRate(final double slabRate1,
            final double slabRate2, final double slabRate3,
            final double slabRate4) {

        this.slabRate1 = slabRate1;
        this.slabRate2 = slabRate2;
        this.slabRate3 = slabRate3;
        this.slabRate4 = slabRate4;

    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }

    public long getApplicationStartDate(final String applicationStartDate) throws ParseException {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        final Date date = dateFormatter.parse(applicationStartDate);

        return date.getTime();
    }

    public long getApplicationEndDate(final String applicationEndDate) throws ParseException {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        final Date date = dateFormatter.parse(applicationEndDate);

        return date.getTime();
    }

    @Override
    public String toString() {
        return "RtiRateMaster [taxType=" + taxType + ", taxCode=" + taxCode + ", taxCategory=" + taxCategory + ", taxSubCategory="
                + taxSubCategory + ", rateStartDate=" + rateStartDate + ", chargeApplicableAt=" + chargeApplicableAt
                + ", mediaType=" + mediaType + ", slab1=" + slab1 + ", slab2=" + slab2 + ", slab3=" + slab3 + ", slab4=" + slab4
                + ", slabRate1=" + slabRate1 + ", slabRate2=" + slabRate2 + ", slabRate3=" + slabRate3 + ", slabRate4="
                + slabRate4 + ", flatRate=" + flatRate + ", percentageRate=" + percentageRate + ", chargeAmount=" + chargeAmount
                + ", freeCopy=" + freeCopy + ", quantity=" + quantity + ", taxId=" + taxId + "]";
    }

    public RtiRateMaster() {

        this.taxType = "NA";
        this.taxCode = "NA";
        this.taxCategory = "NA";
        this.taxSubCategory = "NA";
        this.rateStartDate = 0l;
        this.chargeApplicableAt = "NA";
        this.mediaType = "NA";
        this.slab1 = 0l;
        this.slab2 = 0l;
        this.slab3 = 0l;
        this.slab4 = 0l;
        this.slabRate1 = 0.0d;
        this.slabRate2 = 0.0d;
        this.slabRate3 = 0.0d;
        this.slabRate4 = 0.0d;
        this.flatRate = 0.0d;
        this.percentageRate = 0.0d;
        this.chargeAmount = 0.0d;
        this.freeCopy = 0.0d;
        this.quantity = 0.0d;
        this.taxId = 0L;
    }

    public String getTaxSubCategory() {
        return taxSubCategory;
    }

    public void setTaxSubCategory(String taxSubCategory) {
        this.taxSubCategory = taxSubCategory;
    }

    public double getFreeCopy() {
        return freeCopy;
    }

    public void setFreeCopy(double freeCopy) {
        this.freeCopy = freeCopy;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public long getTaxId() {
        return taxId;
    }

    public void setTaxId(long taxId) {
        this.taxId = taxId;
    }

    public RtiRateMaster ruleResult() {
        return this;
    }
}
