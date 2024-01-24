package com.abm.mainet.rule.propertytax.datamodel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.abm.mainet.rule.datamodel.CommonModel;

/**
 * 
 * @author Vivek.Kumar
 * @since 24/04/2017
 */
public class PropertyRateMasterModel extends CommonModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7950125718662478284L;

    private long orgId;
    private String taxCode;
    private String parentTaxCode;
    private double taxValue;
    private String taxCategory;
    private String taxSubCategory;
    private String taxName;

    private double taxableArea;
    private double arv;
    private double rv;
    private double cv;

    // Date in milisec
    private long rateStartDate;
    private double flatRate;
    private double percentageRate;
    private String occupancyType;

    private double multiplicationFactor;

    private String constructionClass;
    private String occupancyFactor;
    private String roadType;
    private String floorNo;
    private long constructionCompletionDate;
    private double monthlyRent;
    private Map<String, String> factor = new HashMap<>(0);
    private String wardZoneLevel1;
    private String wardZoneLevel2;
    private String wardZoneLevel3;
    private String wardZoneLevel4;
    private String wardZoneLevel5;
    private double plotArea;
    private double builtAreaGr;
    private double billingPeriod;
    private double differenceAmount;
    private String taxCalculationLevel;
    private double parentTaxValue;
    private String factor1;
    private String factor2;

    private String serviceCode;
    private String chargeApplicableAt;
    private String mutationType;
    private double saleDeadValue;
    private double totalArv;
    private double totalRv;
    private double totalCv;

    private long yearOfAcquisition;
    private String ownershipType;
    private double totalBillAmountWithoutRebateExemption;
    private double totalBillAmountWithRebateExemption;
    private String paymentType;
    private double totalPropertyTax;// current property tax with rebate
    private double totalPropertyTaxWithoutRebate;
    private double totalDemandWithRebate;
    private double totalDemandWithoutRebate;
    private double ageFactor;
    private double arrearAmountWithRebate;
    private double arrearAmountWithoutRebate;
    private double totalArrearPropertyTaxWithoutRebate;
    private double totalArrearPropertyTaxWithRebate;
    private double exemptionFactor1;
    private double exemptionFactor2;
    private double exemptionFactor3;
    private double exemptionFactor4;
    private double exemptionFactor5;
    private double flatWise;
    private double roomWise;
    private double plotAreaInLandCase;
    private int currentAssessmentYear;
    private int lastAssessmentYear;
    private String constructionTypeIsMixed;
    private String isDueDatePassOrNot;
    private double totalTaxableArea;
    //Added for OccupancyType
    private String occupacyTypeIsMixed;
    private String propertyTypeIsMixed;

    //Added for Korba widow rebate
	  private double totalRvOfExemption1;
	  private double totalRvOfExemption2;
	  private double totalRvOfExemption3; 
    private Map<String, Double> tax = new HashMap<>(0);
    //Added on behalf of srikanth
    private int noOfMonthsPT;
    private int arrearsPTCount;
    private String isGroup;
    
    private String demandNoticeGen;
    
    private String illegalFlag;
    private String propertyTypeLevel1;
    private String propertyTypeLevel2;
    private String propertyTypeLevel3;
    private String propertyTypeLevel4;
    private String propertyTypeLevel5;
    private double treeTax;
    private double educationCess;
    private double employeeCess;
    private double sumOfPercentageOfTaxes;
    private String isGroupProperty;
    
    private double totalResidentialRv;
    
    private String billingMethod;
    
    
    private double totalResIllegalTaxableArea;

    public PropertyRateMasterModel() {
        this.taxCode = "NA";
        this.parentTaxCode = "NA";
        this.factor1 = "NA";
        this.factor2 = "NA";
        this.taxCategory = "NA";
        this.taxSubCategory = "NA";
        this.constructionClass = "NA";
        this.occupancyFactor = "NA";
        this.taxCalculationLevel = "NA";
        this.paymentType = "NA";
        this.ownershipType = "NA";
    }

    public int getNoOfMonthsPT() {
		return noOfMonthsPT;
	}

	public void setNoOfMonthsPT(int noOfMonthsPT) {
		this.noOfMonthsPT = noOfMonthsPT;
	}

	public PropertyRateMasterModel ruleResult() {
        return this;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getParentTaxCode() {
        return parentTaxCode;
    }

    public void setParentTaxCode(String parentTaxCode) {
        this.parentTaxCode = parentTaxCode;
    }

    public double getTaxableArea() {
        return taxableArea;
    }

    public void setTaxableArea(double taxableArea) {
        this.taxableArea = taxableArea;
    }

    public String getFactor1() {
        return factor1;
    }

    public void setFactor1(String factor1) {
        this.factor1 = factor1;
    }

    public String getFactor2() {
        return factor2;
    }

    public void setFactor2(String factor2) {
        this.factor2 = factor2;
    }

    public double getRv() {
        return rv;
    }

    public void setRv(double rv) {
        this.rv = rv;
    }

    public long getRateStartDate() {
        return rateStartDate;
    }

    public void setRateStartDate(long rateStartDate) {
        this.rateStartDate = rateStartDate;
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

    public double getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(double flatRate) {
        this.flatRate = flatRate;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public double getPercentageRate() {
        return percentageRate;
    }

    public void setPercentageRate(double percentageRate) {
        this.percentageRate = percentageRate;
    }

    public double getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(double taxValue) {
        this.taxValue = taxValue;
    }

    public String getOccupancyType() {
        return occupancyType;
    }

    public void setOccupancyType(String occupancyType) {
        this.occupancyType = occupancyType;
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

    public double getMultiplicationFactor() {
        return multiplicationFactor;
    }

    public void setMultiplicationFactor(double multiplicationFactor) {
        this.multiplicationFactor = multiplicationFactor;
    }

    public double getArv() {
        return arv;
    }

    public void setArv(double arv) {
        this.arv = arv;
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

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public long getConstructionCompletionDate() {
        return constructionCompletionDate;
    }

    public void setConstructionCompletionDate(long constructionCompletionDate) {
        this.constructionCompletionDate = constructionCompletionDate;
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Map<String, String> getFactor() {
        return factor;
    }

    public void setFactor(Map<String, String> factor) {
        this.factor = factor;
    }

    public double getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(double plotArea) {
        this.plotArea = plotArea;
    }

    public double getBuiltAreaGr() {
        return builtAreaGr;
    }

    public void setBuiltAreaGr(double builtAreaGr) {
        this.builtAreaGr = builtAreaGr;
    }

    public Map<String, Double> getTax() {
        return tax;
    }

    public void setTax(Map<String, Double> tax) {
        this.tax = tax;
    }

    public double getCv() {
        return cv;
    }

    public void setCv(double cv) {
        this.cv = cv;
    }

    public String getFactorValue(String key) {
        if (factor != null && factor.get(key) != null) {
            return factor.get(key);
        }
        return "";

    }

    public Double getTaxValue(String key) {
        return tax.get(key);
    }

    public void setTaxMap(String taxCode, Double taxValue) {
        this.tax.put(taxCode, taxValue);
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

    public double getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(double billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public double getDifferenceAmount() {
        return differenceAmount;
    }

    public void setDifferenceAmount(double differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getChargeApplicableAt() {
        return chargeApplicableAt;
    }

    public void setChargeApplicableAt(String chargeApplicableAt) {
        this.chargeApplicableAt = chargeApplicableAt;
    }

    public String getMutationType() {
        return mutationType;
    }

    public void setMutationType(String mutationType) {
        this.mutationType = mutationType;
    }



    public String getTaxCalculationLevel() {
        return taxCalculationLevel;
    }

    public void setTaxCalculationLevel(String taxCalculationLevel) {
        this.taxCalculationLevel = taxCalculationLevel;
    }

    public double getParentTaxValue() {
        return parentTaxValue;
    }

    public void setParentTaxValue(double parentTaxValue) {
        this.parentTaxValue = parentTaxValue;
    }

    @Override
	public String toString() {
		return "PropertyRateMasterModel [orgId=" + orgId + ", taxCode=" + taxCode + ", parentTaxCode=" + parentTaxCode
				+ ", taxValue=" + taxValue + ", taxCategory=" + taxCategory + ", taxSubCategory=" + taxSubCategory
				+ ", taxName=" + taxName + ", taxableArea=" + taxableArea + ", arv=" + arv + ", rv=" + rv + ", cv=" + cv
				+ ", rateStartDate=" + rateStartDate + ", flatRate=" + flatRate + ", percentageRate=" + percentageRate
				+ ", occupancyType=" + occupancyType + ", multiplicationFactor=" + multiplicationFactor
				+ ", constructionClass=" + constructionClass + ", occupancyFactor=" + occupancyFactor + ", roadType="
				+ roadType + ", floorNo=" + floorNo + ", constructionCompletionDate=" + constructionCompletionDate
				+ ", monthlyRent=" + monthlyRent + ", factor=" + factor + ", wardZoneLevel1=" + wardZoneLevel1
				+ ", wardZoneLevel2=" + wardZoneLevel2 + ", wardZoneLevel3=" + wardZoneLevel3 + ", wardZoneLevel4="
				+ wardZoneLevel4 + ", wardZoneLevel5=" + wardZoneLevel5 + ", plotArea=" + plotArea + ", builtAreaGr="
				+ builtAreaGr + ", billingPeriod=" + billingPeriod + ", differenceAmount=" + differenceAmount
				+ ", taxCalculationLevel=" + taxCalculationLevel + ", parentTaxValue=" + parentTaxValue + ", factor1="
				+ factor1 + ", factor2=" + factor2 + ", serviceCode=" + serviceCode + ", chargeApplicableAt="
				+ chargeApplicableAt + ", mutationType=" + mutationType + ", saleDeadValue=" + saleDeadValue
				+ ", totalArv=" + totalArv + ", totalRv=" + totalRv + ", totalCv=" + totalCv + ", yearOfAcquisition="
				+ yearOfAcquisition + ", ownershipType=" + ownershipType + ", totalBillAmountWithoutRebateExemption="
				+ totalBillAmountWithoutRebateExemption + ", totalBillAmountWithRebateExemption="
				+ totalBillAmountWithRebateExemption + ", paymentType=" + paymentType + ", totalPropertyTax="
				+ totalPropertyTax + ", totalPropertyTaxWithoutRebate=" + totalPropertyTaxWithoutRebate
				+ ", totalDemandWithRebate=" + totalDemandWithRebate + ", totalDemandWithoutRebate="
				+ totalDemandWithoutRebate + ", ageFactor=" + ageFactor + ", arrearAmountWithRebate="
				+ arrearAmountWithRebate + ", arrearAmountWithoutRebate=" + arrearAmountWithoutRebate
				+ ", totalArrearPropertyTaxWithoutRebate=" + totalArrearPropertyTaxWithoutRebate
				+ ", totalArrearPropertyTaxWithRebate=" + totalArrearPropertyTaxWithRebate + ", exemptionFactor1="
				+ exemptionFactor1 + ", exemptionFactor2=" + exemptionFactor2 + ", exemptionFactor3=" + exemptionFactor3
				+ ", exemptionFactor4=" + exemptionFactor4 + ", exemptionFactor5=" + exemptionFactor5 + ", flatWise="
				+ flatWise + ", roomWise=" + roomWise + ", plotAreaInLandCase=" + plotAreaInLandCase
				+ ", currentAssessmentYear=" + currentAssessmentYear + ", lastAssessmentYear=" + lastAssessmentYear
				+ ", constructionTypeIsMixed=" + constructionTypeIsMixed + ", isDueDatePassOrNot=" + isDueDatePassOrNot
				+ ", totalTaxableArea=" + totalTaxableArea + ", occupacyTypeIsMixed=" + occupacyTypeIsMixed
				+ ", propertyTypeIsMixed=" + propertyTypeIsMixed + ", totalRvOfExemption1=" + totalRvOfExemption1
				+ ", totalRvOfExemption2=" + totalRvOfExemption2 + ", totalRvOfExemption3=" + totalRvOfExemption3
				+ ", tax=" + tax + ", noOfMonthsPT=" + noOfMonthsPT + ", arrearsPTCount=" + arrearsPTCount
				+ ", isGroup=" + isGroup + ", demandNoticeGen=" + demandNoticeGen + "]";
	}

    public double getTotalArv() {
        return totalArv;
    }

    public void setTotalArv(double totalArv) {
        this.totalArv = totalArv;
    }

    public double getTotalRv() {
        return totalRv;
    }

    public void setTotalRv(double totalRv) {
        this.totalRv = totalRv;
    }

    public double getTotalCv() {
        return totalCv;
    }

    public void setTotalCv(double totalCv) {
        this.totalCv = totalCv;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public double getTotalBillAmountWithoutRebateExemption() {
        return totalBillAmountWithoutRebateExemption;
    }

    public void setTotalBillAmountWithoutRebateExemption(double totalBillAmountWithoutRebateExemption) {
        this.totalBillAmountWithoutRebateExemption = totalBillAmountWithoutRebateExemption;
    }

    public double getTotalBillAmountWithRebateExemption() {
        return totalBillAmountWithRebateExemption;
    }

    public void setTotalBillAmountWithRebateExemption(double totalBillAmountWithRebateExemption) {
        this.totalBillAmountWithRebateExemption = totalBillAmountWithRebateExemption;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalPropertyTax() {
        return totalPropertyTax;
    }

    public void setTotalPropertyTax(double totalPropertyTax) {
        this.totalPropertyTax = totalPropertyTax;
    }

    public double getAgeFactor() {
        return ageFactor;
    }

    public void setAgeFactor(double ageFactor) {
        this.ageFactor = ageFactor;
    }

    public long getYearOfAcquisition() {
        return yearOfAcquisition;
    }

    public void setYearOfAcquisition(long yearOfAcquisition) {
        this.yearOfAcquisition = yearOfAcquisition;
    }

    public double getTotalPropertyTaxWithoutRebate() {
        return totalPropertyTaxWithoutRebate;
    }

    public void setTotalPropertyTaxWithoutRebate(double totalPropertyTaxWithoutRebate) {
        this.totalPropertyTaxWithoutRebate = totalPropertyTaxWithoutRebate;
    }

	public double getTotalDemandWithRebate() {
		return totalDemandWithRebate;
	}

	public void setTotalDemandWithRebate(double totalDemandWithRebate) {
		this.totalDemandWithRebate = totalDemandWithRebate;
	}

	public double getTotalDemandWithoutRebate() {
		return totalDemandWithoutRebate;
	}

	public void setTotalDemandWithoutRebate(double totalDemandWithoutRebate) {
		this.totalDemandWithoutRebate = totalDemandWithoutRebate;
	}

	public double getArrearAmountWithRebate() {
		return arrearAmountWithRebate;
	}

	public void setArrearAmountWithRebate(double arrearAmountWithRebate) {
		this.arrearAmountWithRebate = arrearAmountWithRebate;
	}

	public double getArrearAmountWithoutRebate() {
		return arrearAmountWithoutRebate;
	}

	public void setArrearAmountWithoutRebate(double arrearAmountWithoutRebate) {
		this.arrearAmountWithoutRebate = arrearAmountWithoutRebate;
	}

	public double getTotalArrearPropertyTaxWithoutRebate() {
		return totalArrearPropertyTaxWithoutRebate;
	}

	public void setTotalArrearPropertyTaxWithoutRebate(double totalArrearPropertyTaxWithoutRebate) {
		this.totalArrearPropertyTaxWithoutRebate = totalArrearPropertyTaxWithoutRebate;
	}

	public double getTotalArrearPropertyTaxWithRebate() {
		return totalArrearPropertyTaxWithRebate;
	}

	public void setTotalArrearPropertyTaxWithRebate(double totalArrearPropertyTaxWithRebate) {
		this.totalArrearPropertyTaxWithRebate = totalArrearPropertyTaxWithRebate;
	}

	public double getExemptionFactor1() {
		return exemptionFactor1;
	}

	public void setExemptionFactor1(double exemptionFactor1) {
		this.exemptionFactor1 = exemptionFactor1;
	}

	public double getExemptionFactor2() {
		return exemptionFactor2;
	}

	public void setExemptionFactor2(double exemptionFactor2) {
		this.exemptionFactor2 = exemptionFactor2;
	}

	public double getExemptionFactor3() {
		return exemptionFactor3;
	}

	public void setExemptionFactor3(double exemptionFactor3) {
		this.exemptionFactor3 = exemptionFactor3;
	}

	public double getExemptionFactor4() {
		return exemptionFactor4;
	}

	public void setExemptionFactor4(double exemptionFactor4) {
		this.exemptionFactor4 = exemptionFactor4;
	}

	public double getExemptionFactor5() {
		return exemptionFactor5;
	}

	public void setExemptionFactor5(double exemptionFactor5) {
		this.exemptionFactor5 = exemptionFactor5;
	}

	public double getFlatWise() {
		return flatWise;
	}

	public void setFlatWise(double flatWise) {
		this.flatWise = flatWise;
	}

	public double getRoomWise() {
		return roomWise;
	}

	public void setRoomWise(double roomWise) {
		this.roomWise = roomWise;
	}

	public double getPlotAreaInLandCase() {
		return plotAreaInLandCase;
	}

	public void setPlotAreaInLandCase(double plotAreaInLandCase) {
		this.plotAreaInLandCase = plotAreaInLandCase;
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

	public double getSaleDeadValue() {
		return saleDeadValue;
	}

	public void setSaleDeadValue(double saleDeadValue) {
		this.saleDeadValue = saleDeadValue;
	}

	public String getOccupacyTypeIsMixed() {
		return occupacyTypeIsMixed;
	}

	public void setOccupacyTypeIsMixed(String occupacyTypeIsMixed) {
		this.occupacyTypeIsMixed = occupacyTypeIsMixed;
	}

	public String getPropertyTypeIsMixed() {
		return propertyTypeIsMixed;
	}

	public void setPropertyTypeIsMixed(String propertyTypeIsMixed) {
		this.propertyTypeIsMixed = propertyTypeIsMixed;
	}

	public double getTotalRvOfExemption1() {
		return totalRvOfExemption1;
	}

	public void setTotalRvOfExemption1(double totalRvOfExemption1) {
		this.totalRvOfExemption1 = totalRvOfExemption1;
	}

	public double getTotalRvOfExemption2() {
		return totalRvOfExemption2;
	}

	public void setTotalRvOfExemption2(double totalRvOfExemption2) {
		this.totalRvOfExemption2 = totalRvOfExemption2;
	}

	public double getTotalRvOfExemption3() {
		return totalRvOfExemption3;
	}

	public void setTotalRvOfExemption3(double totalRvOfExemption3) {
		this.totalRvOfExemption3 = totalRvOfExemption3;
	}

	public int getArrearsPTCount() {
		return arrearsPTCount;
	}

	public void setArrearsPTCount(int arrearsPTCount) {
		this.arrearsPTCount = arrearsPTCount;
	}

	public String getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}

	public String getDemandNoticeGen() {
		return demandNoticeGen;
	}

	public void setDemandNoticeGen(String demandNoticeGen) {
		this.demandNoticeGen = demandNoticeGen;
	}

	public String getIllegalFlag() {
		return illegalFlag;
	}

	public void setIllegalFlag(String illegalFlag) {
		this.illegalFlag = illegalFlag;
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

	public double getTreeTax() {
		return treeTax;
	}

	public void setTreeTax(double treeTax) {
		this.treeTax = treeTax;
	}

	public double getEducationCess() {
		return educationCess;
	}

	public void setEducationCess(double educationCess) {
		this.educationCess = educationCess;
	}

	public double getEmployeeCess() {
		return employeeCess;
	}

	public void setEmployeeCess(double employeeCess) {
		this.employeeCess = employeeCess;
	}

	public double getSumOfPercentageOfTaxes() {
		return sumOfPercentageOfTaxes;
	}

	public void setSumOfPercentageOfTaxes(double sumOfPercentageOfTaxes) {
		this.sumOfPercentageOfTaxes = sumOfPercentageOfTaxes;
	}

	public String getIsGroupProperty() {
		return isGroupProperty;
	}

	public void setIsGroupProperty(String isGroupProperty) {
		this.isGroupProperty = isGroupProperty;
	}

	public double getTotalResidentialRv() {
		return totalResidentialRv;
	}

	public void setTotalResidentialRv(double totalResidentialRv) {
		this.totalResidentialRv = totalResidentialRv;
	}

	public String getBillingMethod() {
		return billingMethod;
	}

	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}

	public double getTotalResIllegalTaxableArea() {
		return totalResIllegalTaxableArea;
	}

	public void setTotalResIllegalTaxableArea(double totalResIllegalTaxableArea) {
		this.totalResIllegalTaxableArea = totalResIllegalTaxableArea;
	}
	
}
