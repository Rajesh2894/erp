package com.abm.mainet.workManagement.roadcutting.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonModel;

public class RoadCuttingRateMaster extends CommonModel {
	private static final long serialVersionUID = 2583350081512360537L;

	private String taxType;
	private String taxCode;
	private String taxCategory;
	private String taxSubCategory;
	private String roadType;
	private String roadCuttingTechnique;
	private String organisationType;
	private double totalRoadLength;
	private double roadLength;
	private double roadBreadth;
	private double roadHeight;
	private double roadDiemeter;
	private long number;
	private long startDate;

	private double flatRate;
	private double percentageRate;
	private double roadCuttingCharge;
	private String chargeApplicableAt;
	private String applicationCategory;

	// for charge description and not being used for brms[START]
	private String chargeDescEng;
	private String chargeDescReg;
	private long taxId;
	// [END]

	

	public RoadCuttingRateMaster() {
		super();
		taxType = MainetConstants.RoadCuttingConstant.NA;
		taxCode = MainetConstants.RoadCuttingConstant.NA;
		taxCategory = MainetConstants.RoadCuttingConstant.NA;
		taxSubCategory = MainetConstants.RoadCuttingConstant.NA;
		roadType = MainetConstants.RoadCuttingConstant.NA;
		roadCuttingTechnique = MainetConstants.RoadCuttingConstant.NA;
		percentageRate = 0.0D;
		roadCuttingCharge = 0.0D;
		chargeApplicableAt = MainetConstants.RoadCuttingConstant.NA;
		applicationCategory = MainetConstants.RoadCuttingConstant.NA;
		chargeDescEng = MainetConstants.RoadCuttingConstant.NA;
		chargeDescReg = MainetConstants.RoadCuttingConstant.NA;
		organisationType = MainetConstants.RoadCuttingConstant.NA;
		taxId = 0L;
		roadLength = 1.0D;
		roadBreadth = 1.0D;
		roadHeight = 1.0D;
		roadDiemeter = 1.0D ;
		number = 1L;
		totalRoadLength = 1.0D;

	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	public long getEndDate(String endDate) throws ParseException {

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormatter.parse(endDate);

		return date.getTime();
	}
	
	public RoadCuttingRateMaster ruleResult() {
		return this;
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

	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public String getRoadCuttingTechnique() {
		return roadCuttingTechnique;
	}

	public void setRoadCuttingTechnique(String roadCuttingTechnique) {
		this.roadCuttingTechnique = roadCuttingTechnique;
	}

	public String getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(String organisationType) {
		this.organisationType = organisationType;
	}

	public double getTotalRoadLength() {
		return totalRoadLength;
	}

	public void setTotalRoadLength(double totalRoadLength) {
		this.totalRoadLength = totalRoadLength;
	}

	public double getRoadLength() {
		return roadLength;
	}

	public void setRoadLength(double roadLength) {
		this.roadLength = roadLength;
	}

	public double getRoadBreadth() {
		return roadBreadth;
	}

	public void setRoadBreadth(double roadBreadth) {
		this.roadBreadth = roadBreadth;
	}

	public double getRoadHeight() {
		return roadHeight;
	}

	public void setRoadHeight(double roadHeight) {
		this.roadHeight = roadHeight;
	}

	public double getRoadDiemeter() {
		return roadDiemeter;
	}

	public void setRoadDiemeter(double roadDiemeter) {
		this.roadDiemeter = roadDiemeter;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
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

	public double getRoadCuttingCharge() {
		return roadCuttingCharge;
	}

	public void setRoadCuttingCharge(double roadCuttingCharge) {
		this.roadCuttingCharge = roadCuttingCharge;
	}

	public String getChargeApplicableAt() {
		return chargeApplicableAt;
	}

	public void setChargeApplicableAt(String chargeApplicableAt) {
		this.chargeApplicableAt = chargeApplicableAt;
	}

	public String getApplicationCategory() {
		return applicationCategory;
	}

	public void setApplicationCategory(String applicationCategory) {
		this.applicationCategory = applicationCategory;
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
	
}
