package com.abm.mainet.rule.additionalservices.datamodel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.abm.mainet.rule.datamodel.CommonModel;

public class AdditionalServicesModel extends CommonModel implements Serializable{
	
	private static final long serialVersionUID = 5147110476213192442L;

	private String taxType;
	private String taxCode;
	private String taxCategory;
	private String taxSubCategory;
	private String chargeApplicableAt;
	private String itemName;
	private long slab1;
	private long slab2;
	private double slabRate1;
	private double flatRate;
    private double percentageRate;
    private double noOfCopies;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public AdditionalServicesModel() {
		super();
		taxType = "NA";
		taxCode = "NA";
		taxCategory = "NA";
		taxSubCategory = "NA";
		chargeApplicableAt = "NA";
		itemName = "NA";
		this.slab1 = 0L;
		this.slab2 = 0L;
		this.slabRate1 = 0.0D;
		this.flatRate = 0.0D;
		this.percentageRate = 0.0D;
		this.noOfCopies = 0.0D;
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
	
	public AdditionalServicesModel ruleResult() {
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

	public String getChargeApplicableAt() {
		return chargeApplicableAt;
	}

	public void setChargeApplicableAt(String chargeApplicableAt) {
		this.chargeApplicableAt = chargeApplicableAt;
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

	public double getSlabRate1() {
		return slabRate1;
	}

	public void setSlabRate1(double slabRate1) {
		this.slabRate1 = slabRate1;
	}
	
	public void setSlab(final long slab1, final long slab2) {

		this.slab1 = slab1;
		this.slab2 = slab2;
	}
	
	public void setSlabRate(final double slabRate1) {

		this.slabRate1 = slabRate1;
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

	public double getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(double noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}
