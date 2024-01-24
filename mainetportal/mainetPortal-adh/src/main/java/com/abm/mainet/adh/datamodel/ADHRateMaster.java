package com.abm.mainet.adh.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonModel;

/**
 * @author cherupelli.srikanth
 * @since 31 October 2019
 */
public class ADHRateMaster extends CommonModel {

    private static final long serialVersionUID = 2983240979741146217L;

    private long taxId;
    private String taxType;
    private String taxCode;
    private String taxCategory;
    private String taxSubCategory;
    private String dependsOnFactor;
    private String chargeApplicableAt;
    private String chargeDescEng;
    private String chargeDescReg;

    /* ADH Realted variables */

    private String licenseType;
    private String zone;
    private String ward;
    private String adhType;
    private String advertiserCategory;
    private String propertyType;
    private double area;
    private Long unit;
    private Long tenure;
    private String locationCategory;
    private Long delayPeriod;
    private Long attemptNo;
    private String licenseStatus;
    private long rateStartDate;

    private long slab1;
    private long slab2;
    private long slab3;
    private long slab4;
    private long slab5;
    private double slabRate1;
    private double slabRate2;
    private double slabRate3;
    private double slabRate4;
    private double slabRate5;
    private double flatRate;
    private double percentageRate;
    private List<String> dependsOnFactorList;
    private double length;
    private double height;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();

    }

    public ADHRateMaster ruleResult() {
        return this;
    }

    /**
     *
     * @param billingStartDate
     * @return
     * @throws ParseException
     */
    public long getBillingStartDate(final String billingStartDate) throws ParseException {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        final Date date = dateFormatter.parse(billingStartDate);

        return date.getTime();
    }

    /**
     *
     * @param billingEndDate
     * @return
     * @throws ParseException
     */
    public long getBillingEndDate(final String billingEndDate) throws ParseException {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        final Date date = dateFormatter.parse(billingEndDate);

        return date.getTime();
    }

    public ADHRateMaster() {

        this.taxType = "NA";
        this.taxCode = "NA";
        this.taxCategory = "NA";
        this.taxSubCategory = "NA";
        this.dependsOnFactor = "NA";
        this.chargeApplicableAt = "NA";
        this.licenseType = "NA";
        this.zone = "NA";
        this.ward = "NA";
        this.adhType = "NA";
        this.advertiserCategory = "NA";
        this.propertyType = "NA";
        this.area = 0.0d;
        this.unit = 0l;
        this.tenure = 0l;
        this.locationCategory = "NA";
        this.delayPeriod = 0l;
        this.attemptNo = 0l;
        this.licenseStatus = "NA";
        this.slab1 = 0l;
        this.slab2 = 0l;
        this.slab3 = 0l;
        this.slab4 = 0l;
        this.slab5 = 0l;
        this.slabRate1 = 0.0d;
        this.slabRate2 = 0.0d;
        this.slabRate3 = 0.0d;
        this.slabRate4 = 0.0d;
        this.slabRate5 = 0.0d;
        this.flatRate = 0.0d;
        this.percentageRate = 0.0d;

    }

    public void setSlab(long slab1, long slab2, long slab3,
            long slab4, long slab5) {

        this.slab1 = slab1;
        this.slab2 = slab2;
        this.slab3 = slab3;
        this.slab4 = slab4;
        this.slab5 = slab5;
    }

    public void setSlabRate(double slabRate1,
            double slabRate2, double slabRate3,
            double slabRate4, double slabRate5) {

        this.slabRate1 = slabRate1;
        this.slabRate2 = slabRate2;
        this.slabRate3 = slabRate3;
        this.slabRate4 = slabRate4;
        this.slabRate5 = slabRate5;

    }

    public long getTaxId() {
        return taxId;
    }

    public void setTaxId(long taxId) {
        this.taxId = taxId;
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

    public String getTaxSubCategory() {
        return taxSubCategory;
    }

    public void setTaxSubCategory(String taxSubCategory) {
        this.taxSubCategory = taxSubCategory;
    }

    public String getDependsOnFactor() {
        return dependsOnFactor;
    }

    public void setDependsOnFactor(String dependsOnFactor) {
        this.dependsOnFactor = dependsOnFactor;
    }

    public String getChargeApplicableAt() {
        return chargeApplicableAt;
    }

    public void setChargeApplicableAt(String chargeApplicableAt) {
        this.chargeApplicableAt = chargeApplicableAt;
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

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAdhType() {
        return adhType;
    }

    public void setAdhType(String adhType) {
        this.adhType = adhType;
    }

    public String getAdvertiserCategory() {
        return advertiserCategory;
    }

    public void setAdvertiserCategory(String advertiserCategory) {
        this.advertiserCategory = advertiserCategory;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Long getTenure() {
        return tenure;
    }

    public void setTenure(Long tenure) {
        this.tenure = tenure;
    }

    public String getLocationCategory() {
        return locationCategory;
    }

    public void setLocationCategory(String locationCategory) {
        this.locationCategory = locationCategory;
    }

    public Long getDelayPeriod() {
        return delayPeriod;
    }

    public void setDelayPeriod(Long delayPeriod) {
        this.delayPeriod = delayPeriod;
    }

    public Long getAttemptNo() {
        return attemptNo;
    }

    public void setAttemptNo(Long attemptNo) {
        this.attemptNo = attemptNo;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(long rateStartDate) {
        this.rateStartDate = rateStartDate;
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

    public long getSlab5() {
        return slab5;
    }

    public void setSlab5(long slab5) {
        this.slab5 = slab5;
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

    public double getSlabRate5() {
        return slabRate5;
    }

    public void setSlabRate5(double slabRate5) {
        this.slabRate5 = slabRate5;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
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

    public List<String> getDependsOnFactorList() {
        return dependsOnFactorList;
    }

    public void setDependsOnFactorList(List<String> dependsOnFactorList) {
        this.dependsOnFactorList = dependsOnFactorList;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
