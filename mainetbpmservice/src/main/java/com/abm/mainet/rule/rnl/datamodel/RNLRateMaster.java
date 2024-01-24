package com.abm.mainet.rule.rnl.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.abm.mainet.rule.datamodel.CommonModel;

public class RNLRateMaster extends CommonModel {

    private static final long serialVersionUID = 2583350081512360537L;

    private String taxType;
    private String taxCode;
    private String taxCategory;
    private String taxSubCategory;
    private String floorLevel;
    private String roadType;
    private String occupancyType;
    private String estateType1;
    private String estateType2;
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
    private long rateStartDate;
    private String chargeApplicableAt;
    private String applicationCategory;
    private String otherFacility;
    private String bookingPurpose;
    private double area;
    private String period;

    // for charge description and not being used for brms[START]
    private String chargeDescEng;
    private String chargeDescReg;
    private List<String> dependsOnFactorList;
    private long taxId;

    // [END]
    private long noOfBookingDays;
    private double totalAmount;
    
    private String isOrganisationalEmployee;
    
    private String shiftType;

    public String getApplicationCategory() {
        return applicationCategory;
    }

    public void setApplicationCategory(String applicationCategory) {
        this.applicationCategory = applicationCategory;
    }

    public String getOtherFacility() {
        return otherFacility;
    }

    public void setOtherFacility(String otherFacility) {
        this.otherFacility = otherFacility;
    }

    public String getBookingPurpose() {
        return bookingPurpose;
    }

    public void setBookingPurpose(String bookingPurpose) {
        this.bookingPurpose = bookingPurpose;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
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
    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }

    public RNLRateMaster() {
        super();
        this.taxType = "NA";
        this.taxCode = "NA";
        this.taxCategory = "NA";
        this.taxSubCategory = "NA";
        this.floorLevel = "NA";
        this.roadType = "NA";
        this.occupancyType = "NA";
        this.estateType1 = "NA";
        this.estateType2 = "NA";
        this.estateType3 = "NA";
        this.estateType4 = "NA";
        this.estateType5 = "NA";
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
        this.chargeApplicableAt = "NA";
        this.applicationCategory = "NA";
        this.otherFacility = "NA";
        this.bookingPurpose = "NA";
        this.area = 0.0d;
        this.period = "NA";
        this.chargeDescEng = "NA";
        this.chargeDescReg = "NA";
        this.taxId = 0l;
        this.totalAmount = 0.0d;
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
    public void setRoadType(String roadType) {
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
    public void setOccupancyType(String occupancyType) {
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
    public void setTaxType(String taxType) {
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
    public void setTaxCode(String taxCode) {
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
    public void setTaxCategory(String taxCategory) {
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
    public void setTaxSubCategory(String taxSubCategory) {
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
    public void setSlab1(long slab1) {
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
    public void setSlab2(long slab2) {
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
    public void setSlab3(long slab3) {
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
    public void setSlab4(long slab4) {
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
    public void setSlabRate1(double slabRate1) {
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
    public void setSlabRate2(double slabRate2) {
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
    public void setSlabRate3(double slabRate3) {
        this.slabRate3 = slabRate3;
    }

    public String getEstateType1() {
        return estateType1;
    }

    public void setEstateType1(String estateType1) {
        this.estateType1 = estateType1;
    }

    public String getEstateType2() {
        return estateType2;
    }

    public void setEstateType2(String estateType2) {
        this.estateType2 = estateType2;
    }

    public String getEstateType3() {
        return estateType3;
    }

    public void setEstateType3(String estateType3) {
        this.estateType3 = estateType3;
    }

    public String getEstateType4() {
        return estateType4;
    }

    public void setEstateType4(String estateType4) {
        this.estateType4 = estateType4;
    }

    public String getEstateType5() {
        return estateType5;
    }

    public void setEstateType5(String estateType5) {
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
    public void setSlabRate4(double slabRate4) {
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

    public void setSlab(long slab1, long slab2, long slab3, long slab4) {

        this.slab1 = slab1;
        this.slab2 = slab2;
        this.slab3 = slab3;
        this.slab4 = slab4;
    }

    public void setSlabRate(double slabRate1, double slabRate2, double slabRate3, double slabRate4) {

        this.slabRate1 = slabRate1;
        this.slabRate2 = slabRate2;
        this.slabRate3 = slabRate3;
        this.slabRate4 = slabRate4;

    }

    public void setFlatRate(double flatRate) {

        this.flatRate = flatRate;
    }

    public void setPercentageRate(double percentageRate) {

        this.percentageRate = percentageRate;

    }

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(long rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

    /**
     * 
     * @param billingStartDate
     * @return
     * @throws ParseException
     */
    public long getBillingStartDate(String billingStartDate) throws ParseException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormatter.parse(billingStartDate);

        return date.getTime();
    }

    /**
     * 
     * @param billingEndDate
     * @return
     * @throws ParseException
     */
    public long getBillingEndDate(String billingEndDate) throws ParseException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormatter.parse(billingEndDate);

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
    public void setChargeApplicableAt(String chargeApplicableAt) {
        this.chargeApplicableAt = chargeApplicableAt;
    }

    public RNLRateMaster ruleResult() {
        return this;
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

    public List<String> getDependsOnFactorList() {
        return dependsOnFactorList;
    }

    public void setDependsOnFactorList(List<String> dependsOnFactorList) {
        this.dependsOnFactorList = dependsOnFactorList;
    }

    public long getTaxId() {
        return taxId;
    }

    public void setTaxId(long taxId) {
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

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RNLRateMaster [");
        builder.append(super.toString());
        builder.append(", taxType=");
        builder.append(taxType);
        builder.append(", taxCode=");
        builder.append(taxCode);
        builder.append(", taxCategory=");
        builder.append(taxCategory);
        builder.append(", taxSubCategory=");
        builder.append(taxSubCategory);
        builder.append(", floorLevel=");
        builder.append(floorLevel);
        builder.append(", roadType=");
        builder.append(roadType);
        builder.append(", occupancyType=");
        builder.append(occupancyType);
        builder.append(", estateType1=");
        builder.append(estateType1);
        builder.append(", estateType2=");
        builder.append(estateType2);
        builder.append(", estateType3=");
        builder.append(estateType3);
        builder.append(", estateType4=");
        builder.append(estateType4);
        builder.append(", estateType5=");
        builder.append(estateType5);
        builder.append(", slab1=");
        builder.append(slab1);
        builder.append(", slab2=");
        builder.append(slab2);
        builder.append(", slab3=");
        builder.append(slab3);
        builder.append(", slab4=");
        builder.append(slab4);
        builder.append(", slabRate1=");
        builder.append(slabRate1);
        builder.append(", slabRate2=");
        builder.append(slabRate2);
        builder.append(", slabRate3=");
        builder.append(slabRate3);
        builder.append(", slabRate4=");
        builder.append(slabRate4);
        builder.append(", flatRate=");
        builder.append(flatRate);
        builder.append(", percentageRate=");
        builder.append(percentageRate);
        builder.append(", rateStartDate=");
        builder.append(rateStartDate);
        builder.append(", chargeApplicableAt=");
        builder.append(chargeApplicableAt);
        builder.append(", applicationCategory=");
        builder.append(applicationCategory);
        builder.append(", otherFacility=");
        builder.append(otherFacility);
        builder.append(", bookingPurpose=");
        builder.append(bookingPurpose);
        builder.append(", area=");
        builder.append(area);
        builder.append(", period=");
        builder.append(period);
        builder.append(", chargeDescEng=");
        builder.append(chargeDescEng);
        builder.append(", chargeDescReg=");
        builder.append(chargeDescReg);
        builder.append(", dependsOnFactorList=");
        builder.append(dependsOnFactorList);
        builder.append(", taxId=");
        builder.append(taxId);
        builder.append(", noOfBookingDays=");
        builder.append(noOfBookingDays);
        builder.append(", totalAmount=");
        builder.append(totalAmount);
        builder.append(", isOrganisationalEmployee=");
        builder.append(isOrganisationalEmployee);
        builder.append("]");
        return builder.toString();
    }

	public String getShiftType() {
		return shiftType;
	}

	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}

}
