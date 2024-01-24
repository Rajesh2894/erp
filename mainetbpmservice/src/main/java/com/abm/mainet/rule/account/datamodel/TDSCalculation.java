package com.abm.mainet.rule.account.datamodel;

import java.io.Serializable;

/**
 * This dataModel is being used for TDS rate
 * calculation
 * @author Vivek.Kumar
 *
 */
public class TDSCalculation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6953139037492131950L;

	private String departmentCode;
	private String taxCode;
	private String vendorType;
	private String vendorSubType;
	// whether PAN holder or not
	private String panCardHolder;
	private String billType;
	//I-individual, G-group
	private String billAmountType;
	private double billAmount;
	private String factor1;
	private String factor2;
	
	// result field
	private double tdsRate;

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

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	public String getVendorSubType() {
		return vendorSubType;
	}

	public void setVendorSubType(String vendorSubType) {
		this.vendorSubType = vendorSubType;
	}


	public String getPanCardHolder() {
		return panCardHolder;
	}

	public void setPanCardHolder(String panCardHolder) {
		this.panCardHolder = panCardHolder;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillAmountType() {
		return billAmountType;
	}

	public void setBillAmountType(String billAmountType) {
		this.billAmountType = billAmountType;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public double getTdsRate() {
		return tdsRate;
	}

	public void setTdsRate(double tdsRate) {
		this.tdsRate = tdsRate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TDSCalculation [departmentCode=");
		builder.append(departmentCode);
		builder.append(", taxCode=");
		builder.append(taxCode);
		builder.append(", vendorType=");
		builder.append(vendorType);
		builder.append(", vendorSubType=");
		builder.append(vendorSubType);
		builder.append(", panCardHolder=");
		builder.append(panCardHolder);
		builder.append(", billType=");
		builder.append(billType);
		builder.append(", billAmountType=");
		builder.append(billAmountType);
		builder.append(", billAmount=");
		builder.append(billAmount);
		builder.append(", factor1=");
		builder.append(factor1);
		builder.append(", factor2=");
		builder.append(factor2);
		builder.append(", tdsRate=");
		builder.append(tdsRate);
		builder.append("]");
		return builder.toString();
	}

	
	
	


	



	
}
