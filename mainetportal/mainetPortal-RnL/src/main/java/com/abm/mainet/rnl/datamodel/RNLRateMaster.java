package com.abm.mainet.rnl.datamodel;

import java.util.List;

import com.abm.mainet.common.dto.CommonModel;

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

    public RNLRateMaster() {
        super();
        taxType = "NA";
        taxCode = "NA";
        taxCategory = "NA";
        taxSubCategory = "NA";
        floorLevel = "NA";
        roadType = "NA";
        occupancyType = "NA";
        estateType1 = "NA";
        estateType2 = "NA";
        estateType3 = "NA";
        estateType4 = "NA";
        estateType5 = "NA";
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
        totalAmount    = 0.0d;
        chargeApplicableAt = "NA";
        applicationCategory = "NA";
        otherFacility = "NA";
        bookingPurpose = "NA";
        area = 0.0d;
        period = "NA";
        chargeDescEng = "NA";
        chargeDescReg = "NA";
        taxId = 0l;
        noOfBookingDays = 0l;
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

    public long getTaxId() {
        return taxId;
    }

    public void setTaxId(long taxId) {
        this.taxId = taxId;
    }

    public String getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public String getOccupancyType() {
        return occupancyType;
    }

    public void setOccupancyType(String occupancyType) {
        this.occupancyType = occupancyType;
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

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(long rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

    public long getNoOfBookingDays() {
        return noOfBookingDays;
    }

    public void setNoOfBookingDays(long noOfBookingDays) {
        this.noOfBookingDays = noOfBookingDays;
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

    public List<String> getDependsOnFactorList() {
        return dependsOnFactorList;
    }

    public void setDependsOnFactorList(List<String> dependsOnFactorList) {
        this.dependsOnFactorList = dependsOnFactorList;
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
