/**
 * 
 */
package com.abm.mainet.additionalservices.datamodel;

import java.io.Serializable;

import com.abm.mainet.common.integration.brms.datamodel.CommonModel;

/**
 * @author cherupelli.srikanth
 * @since 07 Feb 2023
 */
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
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
