package com.abm.mainet.rnl.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.brms.datamodel.CommonModel;

public class RNLRateMaster extends CommonModel {

    private static final long serialVersionUID = 2583350081512360537L;
    /*---------- Master ---------- */
    private String taxType;
    private String taxCode;
    private String taxCategory;
    private String taxSubCategory;
    private String chargeApplicableAt;
    private String chargeDescEng;
    private String chargeDescReg;
    private long taxId;
    /* --------Master------ */

    private String floorLevel;
    private String roadType;
    private String occupancyType;

    private String estateType1;
    private String estateType2;
    private long rateStartDate;
    // new noOfdays
    private long noOfBookingDays;

    private String estateType3;
    private String estateType4;
    private String estateType5;

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
    private String applicationCategory;
    private String otherFacility;
    private String bookingPurpose;
    private double area;
    private String period;
    private double totalAmount;
    private String isOrganisationalEmployee;
    private String shiftType;// set data based on IEB prefix with SHF other value:Y

    // for charge description and not being used for brms[START]
    private List<String> dependsOnFactorList;

    // [END]

    public String getApplicationCategory() {
        return applicationCategory;
    }

    public void setApplicationCategory(final String applicationCategory) {
        this.applicationCategory = applicationCategory;
    }

    public String getOtherFacility() {
        return otherFacility;
    }

    public void setOtherFacility(final String otherFacility) {
        this.otherFacility = otherFacility;
    }

    public String getBookingPurpose() {
        return bookingPurpose;
    }

    public void setBookingPurpose(final String bookingPurpose) {
        this.bookingPurpose = bookingPurpose;
    }

    public double getArea() {
        return area;
    }

    public void setArea(final double area) {
        this.area = area;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(final String period) {
        this.period = period;
    }

    /**
     * @return the floorLevel
     */
    public String getFloorLevel() {
        return floorLevel;
    }

    /**
     * @param floorLevel the floorLevel to set
     */
    public void setFloorLevel(final String floorLevel) {
        this.floorLevel = floorLevel;
    }

    public RNLRateMaster() {
        super();
        taxType = MainetConstants.CommonConstants.NA;
        taxCode = MainetConstants.CommonConstants.NA;
        taxCategory = MainetConstants.CommonConstants.NA;
        taxSubCategory = MainetConstants.CommonConstants.NA;
        floorLevel = MainetConstants.CommonConstants.NA;
        roadType = MainetConstants.CommonConstants.NA;
        occupancyType = MainetConstants.CommonConstants.NA;
        estateType1 = MainetConstants.CommonConstants.NA;
        estateType2 = MainetConstants.CommonConstants.NA;
        estateType3 = MainetConstants.CommonConstants.NA;
        estateType4 = MainetConstants.CommonConstants.NA;
        estateType5 = MainetConstants.CommonConstants.NA;
        slab1 = 0l;
        slab2 = 0l;
        slab3 = 0l;
        slab4 = 0l;
        slabRate1 = 0.0d;
        slabRate2 = 0.0d;
        slabRate3 = 0.0d;
        slabRate4 = 0.0d;
        flatRate = 0.0d;
        percentageRate = 0.0d;
        totalAmount = 0.0d;
        chargeApplicableAt = MainetConstants.CommonConstants.NA;
        applicationCategory = MainetConstants.CommonConstants.NA;
        otherFacility = MainetConstants.CommonConstants.NA;
        bookingPurpose = MainetConstants.CommonConstants.NA;
        area = 0.0d;
        period = MainetConstants.CommonConstants.NA;
        chargeDescEng = MainetConstants.CommonConstants.NA;
        chargeDescReg = MainetConstants.CommonConstants.NA;
        taxId = 0l;
        noOfBookingDays = 0l;
    }

    /**
     * @return the roadType
     */
    public String getRoadType() {
        return roadType;
    }

    /**
     * @param roadType the roadType to set
     */
    public void setRoadType(final String roadType) {
        this.roadType = roadType;
    }

    /**
     * @return the occupancyType
     */
    public String getOccupancyType() {
        return occupancyType;
    }

    /**
     * @param occupancyType the occupancyType to set
     */
    public void setOccupancyType(final String occupancyType) {
        this.occupancyType = occupancyType;
    }

    /**
     * @return the taxType
     */
    public String getTaxType() {
        return taxType;
    }

    /**
     * @param taxType the taxType to set
     */
    public void setTaxType(final String taxType) {
        this.taxType = taxType;
    }

    /**
     * @return the taxCode
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * @param taxCode the taxCode to set
     */
    public void setTaxCode(final String taxCode) {
        this.taxCode = taxCode;
    }

    /**
     * @return the taxCategory
     */
    public String getTaxCategory() {
        return taxCategory;
    }

    /**
     * @param taxCategory the taxCategory to set
     */
    public void setTaxCategory(final String taxCategory) {
        this.taxCategory = taxCategory;
    }

    /**
     * @return the taxSubCategory
     */
    public String getTaxSubCategory() {
        return taxSubCategory;
    }

    /**
     * @param taxSubCategory the taxSubCategory to set
     */
    public void setTaxSubCategory(final String taxSubCategory) {
        this.taxSubCategory = taxSubCategory;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }

    /**
     * @return the slab1
     */
    public long getSlab1() {
        return slab1;
    }

    /**
     * @param slab1 the slab1 to set
     */
    public void setSlab1(final long slab1) {
        this.slab1 = slab1;
    }

    /**
     * @return the slab2
     */
    public long getSlab2() {
        return slab2;
    }

    /**
     * @param slab2 the slab2 to set
     */
    public void setSlab2(final long slab2) {
        this.slab2 = slab2;
    }

    /**
     * @return the slab3
     */
    public long getSlab3() {
        return slab3;
    }

    /**
     * @param slab3 the slab3 to set
     */
    public void setSlab3(final long slab3) {
        this.slab3 = slab3;
    }

    /**
     * @return the slab4
     */
    public long getSlab4() {
        return slab4;
    }

    /**
     * @param slab4 the slab4 to set
     */
    public void setSlab4(final long slab4) {
        this.slab4 = slab4;
    }

    /**
     * @return the slabRate1
     */
    public double getSlabRate1() {
        return slabRate1;
    }

    /**
     * @param slabRate1 the slabRate1 to set
     */
    public void setSlabRate1(final double slabRate1) {
        this.slabRate1 = slabRate1;
    }

    /**
     * @return the slabRate2
     */
    public double getSlabRate2() {
        return slabRate2;
    }

    /**
     * @param slabRate2 the slabRate2 to set
     */
    public void setSlabRate2(final double slabRate2) {
        this.slabRate2 = slabRate2;
    }

    /**
     * @return the slabRate3
     */
    public double getSlabRate3() {
        return slabRate3;
    }

    /**
     * @param slabRate3 the slabRate3 to set
     */
    public void setSlabRate3(final double slabRate3) {
        this.slabRate3 = slabRate3;
    }

    public String getEstateType1() {
        return estateType1;
    }

    public void setEstateType1(final String estateType1) {
        this.estateType1 = estateType1;
    }

    public String getEstateType2() {
        return estateType2;
    }

    public void setEstateType2(final String estateType2) {
        this.estateType2 = estateType2;
    }

    public String getEstateType3() {
        return estateType3;
    }

    public void setEstateType3(final String estateType3) {
        this.estateType3 = estateType3;
    }

    public String getEstateType4() {
        return estateType4;
    }

    public void setEstateType4(final String estateType4) {
        this.estateType4 = estateType4;
    }

    public String getEstateType5() {
        return estateType5;
    }

    public void setEstateType5(final String estateType5) {
        this.estateType5 = estateType5;
    }

    /**
     * @return the slabRate4
     */
    public double getSlabRate4() {
        return slabRate4;
    }

    /**
     * @param slabRate4 the slabRate4 to set
     */
    public void setSlabRate4(final double slabRate4) {
        this.slabRate4 = slabRate4;
    }

    /**
     * @return the flatRate
     */
    public double getFlatRate() {
        return flatRate;
    }

    /**
     * @return the percentageRate
     */
    public double getPercentageRate() {
        return percentageRate;
    }

    public void setSlab(final long slab1, final long slab2, final long slab3, final long slab4) {

        this.slab1 = slab1;
        this.slab2 = slab2;
        this.slab3 = slab3;
        this.slab4 = slab4;
    }

    public void setSlabRate(final double slabRate1, final double slabRate2, final double slabRate3, final double slabRate4) {

        this.slabRate1 = slabRate1;
        this.slabRate2 = slabRate2;
        this.slabRate3 = slabRate3;
        this.slabRate4 = slabRate4;

    }

    public void setFlatRate(final double flatRate) {

        this.flatRate = flatRate;
    }

    public void setPercentageRate(final double percentageRate) {

        this.percentageRate = percentageRate;

    }

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(final long rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

    /**
     *
     * @param billingStartDate
     * @return
     * @throws ParseException
     */
    public long getBillingStartDate(final String billingStartDate) throws ParseException {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
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

        final SimpleDateFormat dateFormatter = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
        final Date date = dateFormatter.parse(billingEndDate);

        return date.getTime();
    }

    /**
     * @return the chargeApplicableAt
     */
    public String getChargeApplicableAt() {
        return chargeApplicableAt;
    }

    /**
     * @param chargeApplicableAt the chargeApplicableAt to set
     */
    public void setChargeApplicableAt(final String chargeApplicableAt) {
        this.chargeApplicableAt = chargeApplicableAt;
    }

    public RNLRateMaster ruleResult() {
        return this;
    }

    public String getChargeDescEng() {
        return chargeDescEng;
    }

    public void setChargeDescEng(final String chargeDescEng) {
        this.chargeDescEng = chargeDescEng;
    }

    public String getChargeDescReg() {
        return chargeDescReg;
    }

    public void setChargeDescReg(final String chargeDescReg) {
        this.chargeDescReg = chargeDescReg;
    }

    public List<String> getDependsOnFactorList() {
        return dependsOnFactorList;
    }

    public void setDependsOnFactorList(final List<String> dependsOnFactorList) {
        this.dependsOnFactorList = dependsOnFactorList;
    }

    public long getTaxId() {
        return taxId;
    }

    public void setTaxId(final long taxId) {
        this.taxId = taxId;
    }

    public long getNoOfBookingDays() {
        return noOfBookingDays;
    }

    public void setNoOfBookingDays(long noOfBookingDays) {
        this.noOfBookingDays = noOfBookingDays;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIsOrganisationalEmployee() {
        return isOrganisationalEmployee;
    }

    public void setIsOrganisationalEmployee(String isOrganisationalEmployee) {
        this.isOrganisationalEmployee = isOrganisationalEmployee;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

}
