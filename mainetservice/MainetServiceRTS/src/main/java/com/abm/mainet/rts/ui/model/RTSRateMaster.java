package com.abm.mainet.rts.ui.model;

import java.util.List;

import com.abm.mainet.common.integration.brms.datamodel.CommonModel;

public class RTSRateMaster extends CommonModel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    private String taxType;
    private String taxCode;
    private String taxCategory;
    private String taxSubCategory;
    private double consumption;
    private String meterType;
    private String gapCode;
    private String isTempPlug;
    private String dependsOnFactor;
    private String chargeApplicableAt;
    private double connectionSize;
    private String connectionType;
    private double roadLength;
    private double licencePeriod;
    private String roadType;
    private String typeOfTechnicalPerson;
    private String disConnectionType;
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
    private double chargeAmount;

    private int noOfFamilies;
    private double ClosingBalanceOfSecurityDeposit;
    private int noOfCopies;
    private long rateStartDate;
    private String transferMode;

    // for charge description and not being used for brms[START]
    private String chargeDescEng;
    private String chargeDescReg;
    private List<String> dependsOnFactorList;
    private long taxId;
    private String taxPayer;
    private double noOfRoomsORTabel;
    
    
    private double freeCopy;
    private double quantity;
    private String mediaType;
    
    
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
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	public String getMeterType() {
		return meterType;
	}
	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}
	public String getGapCode() {
		return gapCode;
	}
	public void setGapCode(String gapCode) {
		this.gapCode = gapCode;
	}
	public String getIsTempPlug() {
		return isTempPlug;
	}
	public void setIsTempPlug(String isTempPlug) {
		this.isTempPlug = isTempPlug;
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
	public double getConnectionSize() {
		return connectionSize;
	}
	public void setConnectionSize(double connectionSize) {
		this.connectionSize = connectionSize;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	public double getRoadLength() {
		return roadLength;
	}
	public void setRoadLength(double roadLength) {
		this.roadLength = roadLength;
	}
	public double getLicencePeriod() {
		return licencePeriod;
	}
	public void setLicencePeriod(double licencePeriod) {
		this.licencePeriod = licencePeriod;
	}
	public String getRoadType() {
		return roadType;
	}
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}
	public String getTypeOfTechnicalPerson() {
		return typeOfTechnicalPerson;
	}
	public void setTypeOfTechnicalPerson(String typeOfTechnicalPerson) {
		this.typeOfTechnicalPerson = typeOfTechnicalPerson;
	}
	public String getDisConnectionType() {
		return disConnectionType;
	}
	public void setDisConnectionType(String disConnectionType) {
		this.disConnectionType = disConnectionType;
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
	public int getNoOfFamilies() {
		return noOfFamilies;
	}
	public void setNoOfFamilies(int noOfFamilies) {
		this.noOfFamilies = noOfFamilies;
	}
	public double getClosingBalanceOfSecurityDeposit() {
		return ClosingBalanceOfSecurityDeposit;
	}
	public void setClosingBalanceOfSecurityDeposit(double closingBalanceOfSecurityDeposit) {
		ClosingBalanceOfSecurityDeposit = closingBalanceOfSecurityDeposit;
	}
	public int getNoOfCopies() {
		return noOfCopies;
	}
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	public long getRateStartDate() {
		return rateStartDate;
	}
	public void setRateStartDate(long rateStartDate) {
		this.rateStartDate = rateStartDate;
	}
	public String getTransferMode() {
		return transferMode;
	}
	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
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
	public String getTaxPayer() {
		return taxPayer;
	}
	public void setTaxPayer(String taxPayer) {
		this.taxPayer = taxPayer;
	}
	public double getNoOfRoomsORTabel() {
		return noOfRoomsORTabel;
	}
	public void setNoOfRoomsORTabel(double noOfRoomsORTabel) {
		this.noOfRoomsORTabel = noOfRoomsORTabel;
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
	
	
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	@Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }

}
