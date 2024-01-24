package com.abm.mainet.property.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.abm.mainet.common.integration.brms.datamodel.CommonModel;

/**
 * 
 * @author Vivek.Kumar
 * @since 24/04/2017
 */
public class ALVMasterModel extends CommonModel {

    private static final long serialVersionUID = -7468828127036717943L;
    private String constructionClass;
    private String occupancyFactor;
    private String roadType;
    private double taxableArea;
    private double ageFactor;
    private String floorNo;
    // Date in milisec
    private long rateStartDate;
    private double arvStdRate;
    // result fields
    private double arv;
    private double rv;
    private double cv;
    private double multiplicationFactor;
    private double sddrRate;
    private String wardZoneLevel1;
    private String wardZoneLevel2;
    private String wardZoneLevel3;
    private String wardZoneLevel4;
    private String wardZoneLevel5;
    private long constructionCompletionDate;
    private Double monthlyRent;
    private double multiplicationFactorTax;
    private String propertyIsMixed;
    private Map<String, String> factor = new HashMap<>(0);
    private String propertyTypeLevel1;
    private String propertyTypeLevel2;
    private String propertyTypeLevel3;
    private String propertyTypeLevel4;
    private String propertyTypeLevel5;
    private double additionalALVRate; // #26577-- superset changes By Apeksha
    private long yearOfAcquisition;
    private String ownershipType;
    private double additionalCalculationRate;
    private int currentAssessmentYear;
    private int lastAssessmentYear;
    private double plotArea;
    private String constructionTypeIsMixed;
    private String isDueDatePassOrNot;
    private double totalTaxableArea;
    // Added occupacy Type
    private String occupacyTypeIsMixed;
    private String errorMsg;//for rule not found
    private double maintenancePercentage;
    private String usageClass;
    private double usageClassFactor;
    private double roadFactor;
    private String villageName;
    private String surveyNumber;
    private double floorFactor;
    private double slab1;
    private double slab2;
    private double slab3;
    private double slab4;
    private double slab5;
    private double slabRate1;
    private double slabRate2;
    private double slabRate3;
    private double slabRate4;
    private double slabRate5;
    private double vicinityFactor;
    private double amenityFactor;
    private double ageMultiplicationFactor;
    private String isGroupProperty;

    public ALVMasterModel() {
        this.constructionClass = "NA";
        this.occupancyFactor = "NA";
        this.roadType = "NA";
        this.floorNo = "NA";
        this.ownershipType = "NA";
    }

    public ALVMasterModel ruleResult() {
        return this;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public double getArv() {
        return arv;
    }

    public void setArv(double arv) {
        this.arv = arv;
    }

    public double getRv() {
        if (this.rv < 0) {
            return 0;
        }
        return rv;
    }

    public void setRv(double rv) {
        this.rv = rv;
    }

    public double getCv() {
        return cv;
    }

    public void setCv(double cv) {
        this.cv = cv;
    }

    public String getConstructionClass() {
        return constructionClass;
    }

    public void setConstructionClass(String constructionClass) {
        this.constructionClass = constructionClass;
    }

    public String getOccupancyFactor() {
        return occupancyFactor;
    }

    public void setOccupancyFactor(String occupancyFactor) {
        this.occupancyFactor = occupancyFactor;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public double getTaxableArea() {
        return taxableArea;
    }

    public void setTaxableArea(double taxableArea) {
        this.taxableArea = taxableArea;
    }

    public double getAgeFactor() {
        return ageFactor;
    }

    public void setAgeFactor(double ageFactor) {
        this.ageFactor = ageFactor;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(long rateStartDate) {
        this.rateStartDate = rateStartDate;
    }

    public double getArvStdRate() {
        return arvStdRate;
    }

    public void setArvStdRate(double arvStdRate) {
        this.arvStdRate = arvStdRate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ALVMasterModel [");
        builder.append(super.toString());
        builder.append(", constructionClass=");
        builder.append(constructionClass);
        builder.append(", occupancyFactor=");
        builder.append(occupancyFactor);
        builder.append(", roadType=");
        builder.append(roadType);
        builder.append(", taxableArea=");
        builder.append(taxableArea);
        builder.append(", ageFactor=");
        builder.append(ageFactor);
        builder.append(", floorNo=");
        builder.append(floorNo);
        builder.append(", rateStartDate=");
        builder.append(rateStartDate);
        builder.append(", arvStdRate=");
        builder.append(arvStdRate);
        builder.append(", arv=");
        builder.append(arv);
        builder.append(", rv=");
        builder.append(rv);
        builder.append(", cv=");
        builder.append(cv);
        builder.append(", constructCompDate=");
        builder.append(", ownershipType=");
        builder.append(ownershipType);
        builder.append("]");
        return builder.toString();
    }

    public double getMultiplicationFactor() {
        return multiplicationFactor;
    }

    public void setMultiplicationFactor(double multiplicationFactor) {
        this.multiplicationFactor = multiplicationFactor;
    }

    public double getSddrRate() {
        return sddrRate;
    }

    public void setSddrRate(double sddrRate) {
        this.sddrRate = sddrRate;
    }

    public long getConstructionCompletionDate() {
        return constructionCompletionDate;
    }

    public void setConstructionCompletionDate(long constructionCompletionDate) {
        this.constructionCompletionDate = constructionCompletionDate;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Map<String, String> getFactor() {
        return factor;
    }

    public void setFactor(Map<String, String> factor) {
        this.factor = factor;
    }

    public String getWardZoneLevel1() {
        return wardZoneLevel1;
    }

    public void setWardZoneLevel1(String wardZoneLevel1) {
        this.wardZoneLevel1 = wardZoneLevel1;
    }

    public String getWardZoneLevel2() {
        return wardZoneLevel2;
    }

    public void setWardZoneLevel2(String wardZoneLevel2) {
        this.wardZoneLevel2 = wardZoneLevel2;
    }

    public String getWardZoneLevel3() {
        return wardZoneLevel3;
    }

    public void setWardZoneLevel3(String wardZoneLevel3) {
        this.wardZoneLevel3 = wardZoneLevel3;
    }

    public String getWardZoneLevel4() {
        return wardZoneLevel4;
    }

    public void setWardZoneLevel4(String wardZoneLevel4) {
        this.wardZoneLevel4 = wardZoneLevel4;
    }

    public String getWardZoneLevel5() {
        return wardZoneLevel5;
    }

    public void setWardZoneLevel5(String wardZoneLevel5) {
        this.wardZoneLevel5 = wardZoneLevel5;
    }

    public double getMultiplicationFactorTax() {
        return multiplicationFactorTax;
    }

    public void setMultiplicationFactorTax(double multiplicationFactorTax) {
        this.multiplicationFactorTax = multiplicationFactorTax;
    }

    public String getPropertyIsMixed() {
        return propertyIsMixed;
    }

    public void setPropertyIsMixed(String propertyIsMixed) {
        this.propertyIsMixed = propertyIsMixed;
    }

    public String getPropertyTypeLevel1() {
        return propertyTypeLevel1;
    }

    public void setPropertyTypeLevel1(String propertyTypeLevel1) {
        this.propertyTypeLevel1 = propertyTypeLevel1;
    }

    public String getPropertyTypeLevel2() {
        return propertyTypeLevel2;
    }

    public void setPropertyTypeLevel2(String propertyTypeLevel2) {
        this.propertyTypeLevel2 = propertyTypeLevel2;
    }

    public String getPropertyTypeLevel3() {
        return propertyTypeLevel3;
    }

    public void setPropertyTypeLevel3(String propertyTypeLevel3) {
        this.propertyTypeLevel3 = propertyTypeLevel3;
    }

    public String getPropertyTypeLevel4() {
        return propertyTypeLevel4;
    }

    public void setPropertyTypeLevel4(String propertyTypeLevel4) {
        this.propertyTypeLevel4 = propertyTypeLevel4;
    }

    public String getPropertyTypeLevel5() {
        return propertyTypeLevel5;
    }

    public void setPropertyTypeLevel5(String propertyTypeLevel5) {
        this.propertyTypeLevel5 = propertyTypeLevel5;
    }

    public double getAdditionalALVRate() {
        return additionalALVRate;
    }

    public void setAdditionalALVRate(double additionalALVRate) {
        this.additionalALVRate = additionalALVRate;
    }

    public long getYearOfAcquisition() {
        return yearOfAcquisition;
    }

    public void setYearOfAcquisition(long yearOfAcquisition) {
        this.yearOfAcquisition = yearOfAcquisition;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public double getAdditionalCalculationRate() {
        return additionalCalculationRate;
    }

    public void setAdditionalCalculationRate(double additionalCalculationRate) {
        this.additionalCalculationRate = additionalCalculationRate;
    }

    public int getCurrentAssessmentYear() {
        return currentAssessmentYear;
    }

    public void setCurrentAssessmentYear(int currentAssessmentYear) {
        this.currentAssessmentYear = currentAssessmentYear;
    }

    public int getLastAssessmentYear() {
        return lastAssessmentYear;
    }

    public void setLastAssessmentYear(int lastAssessmentYear) {
        this.lastAssessmentYear = lastAssessmentYear;
    }

    public String getFactorValue(String key) {
        if (factor != null && factor.get(key) != null) {
            return factor.get(key);
        }
        return "";
    }

    public double getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(double plotArea) {
        this.plotArea = plotArea;
    }

    public String getConstructionTypeIsMixed() {
        return constructionTypeIsMixed;
    }

    public void setConstructionTypeIsMixed(String constructionTypeIsMixed) {
        this.constructionTypeIsMixed = constructionTypeIsMixed;
    }

    public String getIsDueDatePassOrNot() {
        return isDueDatePassOrNot;
    }

    public void setIsDueDatePassOrNot(String isDueDatePassOrNot) {
        this.isDueDatePassOrNot = isDueDatePassOrNot;
    }

    public double getTotalTaxableArea() {
        return totalTaxableArea;
    }

    public void setTotalTaxableArea(double totalTaxableArea) {
        this.totalTaxableArea = totalTaxableArea;
    }

    public String getOccupacyTypeIsMixed() {
        return occupacyTypeIsMixed;
    }

    public void setOccupacyTypeIsMixed(String occupacyTypeIsMixed) {
        this.occupacyTypeIsMixed = occupacyTypeIsMixed;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

	
	public double getMaintenancePercentage() {
		return maintenancePercentage;
	}

	public void setMaintenancePercentage(double maintenancePercentage) {
		this.maintenancePercentage = maintenancePercentage;
	}

	public String getUsageClass() {
		return usageClass;
	}

	public void setUsageClass(String usageClass) {
		this.usageClass = usageClass;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getSurveyNumber() {
		return surveyNumber;
	}

	public void setSurveyNumber(String surveyNumber) {
		this.surveyNumber = surveyNumber;
	}

	public double getUsageClassFactor() {
		return usageClassFactor;
	}

	public void setUsageClassFactor(double usageClassFactor) {
		this.usageClassFactor = usageClassFactor;
	}

	public double getRoadFactor() {
		return roadFactor;
	}

	public void setRoadFactor(double roadFactor) {
		this.roadFactor = roadFactor;
	}

	public double getFloorFactor() {
		return floorFactor;
	}

	public void setFloorFactor(double floorFactor) {
		this.floorFactor = floorFactor;
	}

	public double getSlab1() {
		return slab1;
	}

	public void setSlab1(double slab1) {
		this.slab1 = slab1;
	}

	public double getSlab2() {
		return slab2;
	}

	public void setSlab2(double slab2) {
		this.slab2 = slab2;
	}

	public double getSlab3() {
		return slab3;
	}

	public void setSlab3(double slab3) {
		this.slab3 = slab3;
	}

	public double getSlab4() {
		return slab4;
	}

	public void setSlab4(double slab4) {
		this.slab4 = slab4;
	}

	public double getSlab5() {
		return slab5;
	}

	public void setSlab5(double slab5) {
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

	public void setSlab(final double slab1, final double slab2, final double slab3, final double slab4,
			final double slab5) {

		this.slab1 = slab1;
		this.slab2 = slab2;
		this.slab3 = slab3;
		this.slab4 = slab4;
		this.slab5 = slab5;
	}

	public void setSlabRate(final double slabRate1, final double slabRate2, final double slabRate3,
			final double slabRate4, final double slabRate5) {

		this.slabRate1 = slabRate1;
		this.slabRate2 = slabRate2;
		this.slabRate3 = slabRate3;
		this.slabRate4 = slabRate4;
		this.slabRate5 = slabRate5;

	}

	public double getVicinityFactor() {
		return vicinityFactor;
	}

	public void setVicinityFactor(double vicinityFactor) {
		this.vicinityFactor = vicinityFactor;
	}

	public double getAmenityFactor() {
		return amenityFactor;
	}

	public void setAmenityFactor(double amenityFactor) {
		this.amenityFactor = amenityFactor;
	}

	public double getAgeMultiplicationFactor() {
		return ageMultiplicationFactor;
	}

	public void setAgeMultiplicationFactor(double ageMultiplicationFactor) {
		this.ageMultiplicationFactor = ageMultiplicationFactor;
	}

	public String getIsGroupProperty() {
		return isGroupProperty;
	}

	public void setIsGroupProperty(String isGroupProperty) {
		this.isGroupProperty = isGroupProperty;
	}
	
	 
}
