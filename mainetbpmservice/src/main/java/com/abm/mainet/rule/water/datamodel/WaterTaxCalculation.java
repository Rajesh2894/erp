package com.abm.mainet.rule.water.datamodel;

import java.io.Serializable;

public class WaterTaxCalculation implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 4619472757996536102L;
    private long orgId;
    private String meterType;
    private String connectionType;
    private String typeOfPeriod;
    private String billingMethod;
    private double baseRate;
    private long consumption;
    private long noOfDays;
    private String taxCode;
    private double typeofOwnershipFactor;
    private double slopeFactor;
    private double noOfFamiliesFactor;
    private double noOfRoomsORTabel;
    private double noOfTapsFactor;
    private double noOfConnectionsFactor;
    private double noOfTenantsFactor;
    private double dailyDischargefactor;
    private double generalTax;

    private String factor1;
    private String factor2;
    private String factor3;
    private String factor4;
    private String taxCategory;

    private double tax;
    private String ruleId;

    /**
     * default constructor to initialize state of Object to default
     */
    public WaterTaxCalculation() {
        this.orgId = 0l;
        this.meterType = "NA";
        this.connectionType = "NA";
        this.typeOfPeriod = "NA";
        this.billingMethod = "NA";
        this.baseRate = 0.0d;
        this.consumption = 0l;
        this.noOfDays = 0l;
        this.taxCode = "NA";
        this.typeofOwnershipFactor = 1d;
        this.slopeFactor = 0.0d;
        this.noOfFamiliesFactor = 1d;
        this.noOfRoomsORTabel = 1d;
        this.noOfTapsFactor = 1d;
        this.noOfConnectionsFactor = 1d;
        this.noOfTenantsFactor = 0.0d;
        this.dailyDischargefactor = 0.0d;
        this.generalTax = 0.0d;
        this.factor1 = "NA";
        this.factor2 = "NA";
        this.factor3 = "NA";
        this.factor4 = "NA";
        this.taxCategory = "NA";

    }

    public WaterTaxCalculation ruleResult() {
        return this;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }

    /**
     * @return the tax
     */
    public double getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(double tax) {
        this.tax = tax;
    }

    /**
     * @return the factor1
     */
    public String getFactor1() {
        return factor1;
    }

    /**
     * @param factor1 the factor1 to set
     */
    public void setFactor1(String factor1) {
        this.factor1 = factor1;
    }

    /**
     * @return the factor2
     */
    public String getFactor2() {
        return factor2;
    }

    /**
     * @param factor2 the factor2 to set
     */
    public void setFactor2(String factor2) {
        this.factor2 = factor2;
    }

    /**
     * @return the factor3
     */
    public String getFactor3() {
        return factor3;
    }

    /**
     * @param factor3 the factor3 to set
     */
    public void setFactor3(String factor3) {
        this.factor3 = factor3;
    }

    /**
     * @return the factor4
     */
    public String getFactor4() {
        return factor4;
    }

    /**
     * @param factor4 the factor4 to set
     */
    public void setFactor4(String factor4) {
        this.factor4 = factor4;
    }

    public double getGeneralTax() {
        return generalTax;
    }

    public void setGeneralTax(double generalTax) {
        this.generalTax = generalTax;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public double getTypeofOwnershipFactor() {
        return typeofOwnershipFactor;
    }

    public void setTypeofOwnershipFactor(double typeofOwnershipFactor) {
        this.typeofOwnershipFactor = typeofOwnershipFactor;
    }

    public double getSlopeFactor() {
        return slopeFactor;
    }

    public void setSlopeFactor(double slopeFactor) {
        this.slopeFactor = slopeFactor;
    }

    public double getNoOfFamiliesFactor() {
        return noOfFamiliesFactor;
    }

    public void setNoOfFamiliesFactor(double noOfFamiliesFactor) {
        this.noOfFamiliesFactor = noOfFamiliesFactor;
    }

    public double getNoOfTapsFactor() {
        return noOfTapsFactor;
    }

    public void setNoOfTapsFactor(double noOfTapsFactor) {
        this.noOfTapsFactor = noOfTapsFactor;
    }

    public double getNoOfConnectionsFactor() {
        return noOfConnectionsFactor;
    }

    public void setNoOfConnectionsFactor(double noOfConnectionsFactor) {
        this.noOfConnectionsFactor = noOfConnectionsFactor;
    }

    public double getNoOfTenantsFactor() {
        return noOfTenantsFactor;
    }

    public void setNoOfTenantsFactor(double noOfTenantsFactor) {
        this.noOfTenantsFactor = noOfTenantsFactor;
    }

    public double getDailyDischargefactor() {
        return dailyDischargefactor;
    }

    public void setDailyDischargefactor(double dailyDischargefactor) {
        this.dailyDischargefactor = dailyDischargefactor;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getTypeOfPeriod() {
        return typeOfPeriod;
    }

    public void setTypeOfPeriod(String typeOfPeriod) {
        this.typeOfPeriod = typeOfPeriod;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }

    public long getConsumption() {
        return consumption;
    }

    public void setConsumption(long consumption) {
        this.consumption = consumption;
    }

    public long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod;
    }

    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public double getNoOfRoomsORTabel() {
        return noOfRoomsORTabel;
    }

    public void setNoOfRoomsORTabel(double noOfRoomsORTabel) {
        this.noOfRoomsORTabel = noOfRoomsORTabel;
    }

    @Override
    public String toString() {
        return "WaterTaxCalculation [orgId=" + orgId + ", meterType=" + meterType + ", connectionType=" + connectionType
                + ", typeOfPeriod=" + typeOfPeriod + ", billingMethod=" + billingMethod + ", baseRate=" + baseRate
                + ", consumption=" + consumption + ", noOfDays=" + noOfDays + ", taxCode=" + taxCode + ", typeofOwnershipFactor="
                + typeofOwnershipFactor + ", slopeFactor=" + slopeFactor + ", noOfFamiliesFactor=" + noOfFamiliesFactor
                + ", noOfRoomsORTabel=" + noOfRoomsORTabel + ", noOfTapsFactor=" + noOfTapsFactor + ", noOfConnectionsFactor="
                + noOfConnectionsFactor + ", noOfTenantsFactor=" + noOfTenantsFactor + ", dailyDischargefactor="
                + dailyDischargefactor + ", generalTax=" + generalTax + ", factor1=" + factor1 + ", factor2=" + factor2
                + ", factor3=" + factor3 + ", factor4=" + factor4 + ", taxCategory=" + taxCategory + ", tax=" + tax + ", ruleId="
                + ruleId + "]";
    }

}
