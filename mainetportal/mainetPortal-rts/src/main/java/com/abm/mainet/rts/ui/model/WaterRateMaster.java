package com.abm.mainet.rts.ui.model;

import java.io.Serializable;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.abm.mainet.common.dto.CommonModel;


public class WaterRateMaster  extends CommonModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5076854444404314186L;
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
	    private String occupancyType;
	    private int noOfMonthsSurcharge;
	    private int noOfMonthsRebate;
	    private double arv;
	    private double dischargeRate;

	    // [END]

	    /**
	     * default constructor to initialize state of Object to default
	     */

	    private WaterRateMaster() {
	    }

	    public WaterRateMaster ruleResult() {
	        return this;
	    }

	    @Override
	    public Object clone() throws CloneNotSupportedException {

	        return super.clone();
	    }

	    public double getConsumption() {
	        return consumption;
	    }

	    public void setConsumption(final double consumption) {
	        this.consumption = consumption;
	    }

	    public String getGapCode() {
	        return gapCode;
	    }

	    public void setGapCode(final String gapCode) {
	        this.gapCode = gapCode;
	    }

	    public String getIsTempPlug() {
	        return isTempPlug;
	    }

	    public void setIsTempPlug(final String isTempPlug) {
	        this.isTempPlug = isTempPlug;
	    }

	    public void setSlab(final long slab1, final long slab2, final long slab3,
	            long slab4, long slab5) {

	        this.slab1 = slab1;
	        this.slab2 = slab2;
	        this.slab3 = slab3;
	        this.slab4 = slab4;
	        this.slab5 = slab5;
	    }

	    public void setSlabRate(final double slabRate1,
	            final double slabRate2, final double slabRate3,
	            double slabRate4, double slabRate5) {

	        this.slabRate1 = slabRate1;
	        this.slabRate2 = slabRate2;
	        this.slabRate3 = slabRate3;
	        this.slabRate4 = slabRate4;
	        this.slabRate5 = slabRate5;

	    }

	    public void setFlatRate(final double flatRate) {

	        this.flatRate = flatRate;
	    }

	    public void setPercentageRate(final double percentageRate) {

	        this.percentageRate = percentageRate;

	    }

	    public String getTaxType() {
	        return taxType;
	    }

	    public void setTaxType(final String taxType) {
	        this.taxType = taxType;
	    }

	    public String getTaxCode() {
	        return taxCode;
	    }

	    public void setTaxCode(final String taxCode) {
	        this.taxCode = taxCode;
	    }

	    public long getSlab1() {
	        return slab1;
	    }

	    public void setSlab1(final long slab1) {
	        this.slab1 = slab1;
	    }

	    public long getSlab2() {
	        return slab2;
	    }

	    public void setSlab2(final long slab2) {
	        this.slab2 = slab2;
	    }

	    public long getSlab3() {
	        return slab3;
	    }

	    public void setSlab3(final long slab3) {
	        this.slab3 = slab3;
	    }

	    public long getSlab4() {
	        return slab4;
	    }

	    public void setSlab4(final long slab4) {
	        this.slab4 = slab4;
	    }

	    public double getSlabRate1() {
	        return slabRate1;
	    }

	    public void setSlabRate1(final double slabRate1) {
	        this.slabRate1 = slabRate1;
	    }

	    public double getSlabRate2() {
	        return slabRate2;
	    }

	    public void setSlabRate2(final double slabRate2) {
	        this.slabRate2 = slabRate2;
	    }

	    public double getSlabRate3() {
	        return slabRate3;
	    }

	    public void setSlabRate3(final double slabRate3) {
	        this.slabRate3 = slabRate3;
	    }

	    public double getSlabRate4() {
	        return slabRate4;
	    }

	    public void setSlabRate4(final double slabRate4) {
	        this.slabRate4 = slabRate4;
	    }

	    public double getFlatRate() {
	        return flatRate;
	    }

	    public double getPercentageRate() {
	        return percentageRate;
	    }

	    public String getTaxCategory() {
	        return taxCategory;
	    }

	    public void setTaxCategory(final String taxCategory) {
	        this.taxCategory = taxCategory;
	    }

	    public String getMeterType() {
	        return meterType;
	    }

	    public void setMeterType(final String meterType) {
	        this.meterType = meterType;
	    }

	    public String getTaxSubCategory() {
	        return taxSubCategory;
	    }

	    public void setTaxSubCategory(final String taxSubCategory) {
	        this.taxSubCategory = taxSubCategory;
	    }

	    public String getChargeApplicableAt() {
	        return chargeApplicableAt;
	    }

	    public void setChargeApplicableAt(final String chargeApplicableAt) {
	        this.chargeApplicableAt = chargeApplicableAt;
	    }

	    public double getConnectionSize() {
	        return connectionSize;
	    }

	    public void setConnectionSize(final double connectionSize) {
	        this.connectionSize = connectionSize;
	    }

	    public String getConnectionType() {
	        return connectionType;
	    }

	    public void setConnectionType(final String connectionType) {
	        this.connectionType = connectionType;
	    }

	    public double getRoadLength() {
	        return roadLength;
	    }

	    public void setRoadLength(final double roadLength) {
	        this.roadLength = roadLength;
	    }

	    public double getLicencePeriod() {
	        return licencePeriod;
	    }

	    public void setLicencePeriod(final double licencePeriod) {
	        this.licencePeriod = licencePeriod;
	    }

	    public String getRoadType() {
	        return roadType;
	    }

	    public void setRoadType(final String roadType) {
	        this.roadType = roadType;
	    }

	    public String getTypeOfTechnicalPerson() {
	        return typeOfTechnicalPerson;
	    }

	    public void setTypeOfTechnicalPerson(final String typeOfTechnicalPerson) {
	        this.typeOfTechnicalPerson = typeOfTechnicalPerson;
	    }

	    public String getDisConnectionType() {
	        return disConnectionType;
	    }

	    public void setDisConnectionType(final String disConnectionType) {
	        this.disConnectionType = disConnectionType;
	    }

	    public double getChargeAmount() {
	        return chargeAmount;
	    }

	    public void setChargeAmount(final double chargeAmount) {
	        this.chargeAmount = chargeAmount;
	    }

	    public int getNoOfFamilies() {
	        return noOfFamilies;
	    }

	    public void setNoOfFamilies(final int noOfFamilies) {
	        this.noOfFamilies = noOfFamilies;
	    }

	    public double getClosingBalanceOfSecurityDeposit() {
	        return ClosingBalanceOfSecurityDeposit;
	    }

	    public void setClosingBalanceOfSecurityDeposit(final double closingBalanceOfSecurityDeposit) {
	        ClosingBalanceOfSecurityDeposit = closingBalanceOfSecurityDeposit;
	    }

	    public int getNoOfCopies() {
	        return noOfCopies;
	    }

	    public void setNoOfCopies(final int noOfCopies) {
	        this.noOfCopies = noOfCopies;
	    }

	    public String getDependsOnFactor() {
	        return dependsOnFactor;
	    }

	    public void setDependsOnFactor(final String dependsOnFactor) {
	        this.dependsOnFactor = dependsOnFactor;
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

	    /**
	     * 
	     * @param billingStartDate
	     * @return
	     * @throws ParseException
	     */
	    public long getBillingStartDate(final String billingStartDate) throws ParseException {

	        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
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

	        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	        final Date date = dateFormatter.parse(billingEndDate);

	        return date.getTime();
	    }

	    public String getTransferMode() {
	        return transferMode;
	    }

	    public void setTransferMode(final String transferMode) {
	        this.transferMode = transferMode;
	    }

	    public long getTaxId() {
	        return taxId;
	    }

	    public void setTaxId(final long taxId) {
	        this.taxId = taxId;
	    }

	    public long getRateStartDate() {
	        return rateStartDate;
	    }

	    public void setRateStartDate(final long rateStartDate) {
	        this.rateStartDate = rateStartDate;
	    }

	    public long getSlab5() {
	        return slab5;
	    }

	    public void setSlab5(long slab5) {
	        this.slab5 = slab5;
	    }

	    public double getSlabRate5() {
	        return slabRate5;
	    }

	    public void setSlabRate5(double slabRate5) {
	        this.slabRate5 = slabRate5;
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

	    
	    public String getOccupancyType() {
			return occupancyType;
		}

		public void setOccupancyType(String occupancyType) {
			this.occupancyType = occupancyType;
		}
		public int getNoOfMonthsRebate() {
			return noOfMonthsRebate;
		}

		public void setNoOfMonthsRebate(int noOfMonthsRebate) {
			this.noOfMonthsRebate = noOfMonthsRebate;
		}

		public int getNoOfMonthsSurcharge() {
			return noOfMonthsSurcharge;
		}

		public void setNoOfMonthsSurcharge(int noOfMonthsSurcharge) {
			this.noOfMonthsSurcharge = noOfMonthsSurcharge;
		}

		public double getArv() {
			return arv;
		}

		public void setArv(double arv) {
			this.arv = arv;
		}

		public double getDischargeRate() {
			return dischargeRate;
		}

		public void setDischargeRate(double dischargeRate) {
			this.dischargeRate = dischargeRate;
		}

		@Override
		public String toString() {
			return "WaterRateMaster [taxType=" + taxType + ", taxCode=" + taxCode + ", taxCategory=" + taxCategory
					+ ", taxSubCategory=" + taxSubCategory + ", consumption=" + consumption + ", meterType=" + meterType
					+ ", gapCode=" + gapCode + ", isTempPlug=" + isTempPlug + ", dependsOnFactor=" + dependsOnFactor
					+ ", chargeApplicableAt=" + chargeApplicableAt + ", connectionSize=" + connectionSize
					+ ", connectionType=" + connectionType + ", roadLength=" + roadLength + ", licencePeriod="
					+ licencePeriod + ", roadType=" + roadType + ", typeOfTechnicalPerson=" + typeOfTechnicalPerson
					+ ", disConnectionType=" + disConnectionType + ", slab1=" + slab1 + ", slab2=" + slab2 + ", slab3="
					+ slab3 + ", slab4=" + slab4 + ", slab5=" + slab5 + ", slabRate1=" + slabRate1 + ", slabRate2="
					+ slabRate2 + ", slabRate3=" + slabRate3 + ", slabRate4=" + slabRate4 + ", slabRate5=" + slabRate5
					+ ", flatRate=" + flatRate + ", percentageRate=" + percentageRate + ", chargeAmount=" + chargeAmount
					+ ", noOfFamilies=" + noOfFamilies + ", ClosingBalanceOfSecurityDeposit="
					+ ClosingBalanceOfSecurityDeposit + ", noOfCopies=" + noOfCopies + ", rateStartDate="
					+ rateStartDate + ", transferMode=" + transferMode + ", chargeDescEng=" + chargeDescEng
					+ ", chargeDescReg=" + chargeDescReg + ", dependsOnFactorList=" + dependsOnFactorList + ", taxId="
					+ taxId + ", taxPayer=" + taxPayer + ", noOfRoomsORTabel=" + noOfRoomsORTabel + ", occupancyType="
					+ occupancyType + "]";
		}


	}
