package com.abm.mainet.rule.propertytax.datamodel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Vivek.Kumar
 * @since 20/04/2017
 */
public class FactorMasterModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4958419673682155L;

    private long orgId;
    private String factor;
    private String factorValue;
    private double slabValue;
    // set date in milliseconds
    private long rateStartDate;
    // result field
    private double flatRate;
    private String factApplicable;

    public FactorMasterModel() {
        this.factor = "NA";
        this.factorValue = "NA";
    }

    public FactorMasterModel ruleResult() {
        return this;
    }

    // helper method to convert String date to long millisec
    public long getRateFromDate(String rateFromDate) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormatter.parse(rateFromDate);
        return date.getTime();
    }

    // helper method to convert String date to long millisec
    public long getRateToDate(String rateToDate) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormatter.parse(rateToDate);
        return date.getTime();
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getFactorValue() {
        return factorValue;
    }

    public void setFactorValue(String factorValue) {
        this.factorValue = factorValue;
    }

    public double getSlabValue() {
        return slabValue;
    }

    public void setSlabValue(double slabValue) {
        this.slabValue = slabValue;
    }

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(long rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

    public double getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(double flatRate) {
        this.flatRate = flatRate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FactorMasterModel [orgId=");
        builder.append(orgId);
        builder.append(", factor=");
        builder.append(factor);
        builder.append(", factorValue=");
        builder.append(factorValue);
        builder.append(", slabValue=");
        builder.append(slabValue);
        builder.append(", rateStartDate=");
        builder.append(rateStartDate);
        builder.append(", flatRate=");
        builder.append(flatRate);
        builder.append("]");
        return builder.toString();
    }

    public String getFactApplicable() {
        return factApplicable;
    }

    public void setFactApplicable(String factApplicable) {
        this.factApplicable = factApplicable;
    }

}
