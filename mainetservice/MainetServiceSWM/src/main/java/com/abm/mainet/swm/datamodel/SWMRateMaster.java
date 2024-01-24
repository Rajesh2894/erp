package com.abm.mainet.swm.datamodel;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.brms.datamodel.CommonModel;

public class SWMRateMaster extends CommonModel {

	private static final long serialVersionUID = 2583350081512360537L;

	private String taxType;
	private String taxCode;
	private String taxCategory;
	private String taxSubCategory;
	private String estateType1;
	private String estateType2;
	private String estateType3;
	private String estateType4;
	private String estateType5;

	private double flatRate;
	private double percentageRate;
	private String chargeApplicableAt;
	private String applicationCategory;

	// for charge description and not being used for brms[START]
	private String chargeDescEng;
	private String chargeDescReg;
	private long taxId;
	// [END]

	public String getApplicationCategory() {
		return applicationCategory;
	}

	public void setApplicationCategory(final String applicationCategory) {
		this.applicationCategory = applicationCategory;
	}

	public SWMRateMaster() {
		super();
		taxType = MainetConstants.CommonConstants.NA;
		taxCode = MainetConstants.CommonConstants.NA;
		taxCategory = MainetConstants.CommonConstants.NA;
		taxSubCategory = MainetConstants.CommonConstants.NA;
		estateType1 = MainetConstants.CommonConstants.NA;
		estateType2 = MainetConstants.CommonConstants.NA;
		estateType3 = MainetConstants.CommonConstants.NA;
		estateType4 = MainetConstants.CommonConstants.NA;
		estateType5 = MainetConstants.CommonConstants.NA;
		percentageRate = 0.0d;
		chargeApplicableAt = MainetConstants.CommonConstants.NA;
		applicationCategory = MainetConstants.CommonConstants.NA;
		chargeDescEng = MainetConstants.CommonConstants.NA;
		chargeDescReg = MainetConstants.CommonConstants.NA;
		taxId = 0l;
	}

	/**
	 * @return the taxType
	 */
	public String getTaxType() {
		return taxType;
	}

	/**
	 * @param taxType
	 *            the taxType to set
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
	 * @param taxCode
	 *            the taxCode to set
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
	 * @param taxCategory
	 *            the taxCategory to set
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
	 * @param taxSubCategory
	 *            the taxSubCategory to set
	 */
	public void setTaxSubCategory(final String taxSubCategory) {
		this.taxSubCategory = taxSubCategory;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
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

	public double getFlatRate() {
		return flatRate;
	}

	public void setFlatRate(double flatRate) {
		this.flatRate = flatRate;
	}

	/**
	 * @return the percentageRate
	 */
	public double getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(final double percentageRate) {

		this.percentageRate = percentageRate;

	}

	/**
	 * @return the chargeApplicableAt
	 */
	public String getChargeApplicableAt() {
		return chargeApplicableAt;
	}

	/**
	 * @param chargeApplicableAt
	 *            the chargeApplicableAt to set
	 */
	public void setChargeApplicableAt(final String chargeApplicableAt) {
		this.chargeApplicableAt = chargeApplicableAt;
	}

	public SWMRateMaster ruleResult() {
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

	public long getTaxId() {
		return taxId;
	}

	public void setTaxId(final long taxId) {
		this.taxId = taxId;
	}
}
