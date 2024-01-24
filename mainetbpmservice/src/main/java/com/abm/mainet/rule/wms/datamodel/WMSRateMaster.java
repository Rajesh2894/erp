package com.abm.mainet.rule.wms.datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.abm.mainet.rule.datamodel.CommonModel;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class WMSRateMaster extends CommonModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1205349038867386122L;
	private String chargeApplicableAt;
	private String taxCode;
	private String parentTaxCode;
	private String taxCategory;
	private String taxSubCategory;
	private double tenderAmount;
	private long rateStartDate;
	private double flatRate;
	private double percentageRate;
	private double emdValue;

	public String getChargeApplicableAt() {
		return chargeApplicableAt;
	}

	public void setChargeApplicableAt(String chargeApplicableAt) {
		this.chargeApplicableAt = chargeApplicableAt;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getParentTaxCode() {
		return parentTaxCode;
	}

	public void setParentTaxCode(String parentTaxCode) {
		this.parentTaxCode = parentTaxCode;
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

	public double getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(double tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public long getRateStartDate() {
		return rateStartDate;
	}

	public void setRateStartDate(long rateStartDate) {
		this.rateStartDate = rateStartDate;
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

	public double getEmdValue() {
		return emdValue;
	}

	public void setEmdValue(double emdValue) {
		this.emdValue = emdValue;
	}

	public WMSRateMaster() {
		super();
	}

	public WMSRateMaster ruleResult() {
		return this;
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

}
