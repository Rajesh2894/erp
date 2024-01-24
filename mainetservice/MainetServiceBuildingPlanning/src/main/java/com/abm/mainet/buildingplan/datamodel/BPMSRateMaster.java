package com.abm.mainet.buildingplan.datamodel;

import java.io.Serializable;

import com.abm.mainet.common.integration.brms.datamodel.CommonModel;

public class BPMSRateMaster extends CommonModel implements Serializable {
	
	private static final long serialVersionUID = -267132053848669300L;
	
	private String taxType;
    private String taxCode;
    private String parentTaxCode;
    private String taxCategory;
    private String taxSubCategory;
    private String itemCategory1;
    private String itemCategory2;
    private String itemCategory3;
    private String itemCategory4;
    private String itemCategory5;
    private long rateStartDate;
    private double flatRate;
    private String applicableAt;
    private double slabRate1;
    private double slabRate2;
    private double slabRate3;
    private double slabRate4;
    private double slabRate5;
    private long slab1;
    private long slab2;
    private long slab3;
    private long slab4;
    private long slab5;
    private double percentageRate;
    private long unit;
    private long additionalValue;
    private String licenseType;
    private double area;
    private double parentTaxValue;
    private String purposeFactor;
    private String zone;
    private String zone1;
    private String zone2;
    private String zone3;
    private String zone4;
    private double far;
    
    @Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
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
	public String getItemCategory1() {
		return itemCategory1;
	}
	public void setItemCategory1(String itemCategory1) {
		this.itemCategory1 = itemCategory1;
	}
	public String getItemCategory2() {
		return itemCategory2;
	}
	public void setItemCategory2(String itemCategory2) {
		this.itemCategory2 = itemCategory2;
	}
	public String getItemCategory3() {
		return itemCategory3;
	}
	public void setItemCategory3(String itemCategory3) {
		this.itemCategory3 = itemCategory3;
	}
	public String getItemCategory4() {
		return itemCategory4;
	}
	public void setItemCategory4(String itemCategory4) {
		this.itemCategory4 = itemCategory4;
	}
	public String getItemCategory5() {
		return itemCategory5;
	}
	public void setItemCategory5(String itemCategory5) {
		this.itemCategory5 = itemCategory5;
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
	public String getApplicableAt() {
		return applicableAt;
	}
	public void setApplicableAt(String applicableAt) {
		this.applicableAt = applicableAt;
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
	public double getPercentageRate() {
		return percentageRate;
	}
	public void setPercentageRate(double percentageRate) {
		this.percentageRate = percentageRate;
	}
	public long getUnit() {
		return unit;
	}
	public void setUnit(long unit) {
		this.unit = unit;
	}
	public long getAdditionalValue() {
		return additionalValue;
	}
	public void setAdditionalValue(long additionalValue) {
		this.additionalValue = additionalValue;
	}
	public String getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getParentTaxValue() {
		return parentTaxValue;
	}
	public void setParentTaxValue(double parentTaxValue) {
		this.parentTaxValue = parentTaxValue;
	}
	public String getPurposeFactor() {
		return purposeFactor;
	}
	public void setPurposeFactor(String purposeFactor) {
		this.purposeFactor = purposeFactor;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getZone1() {
		return zone1;
	}
	public void setZone1(String zone1) {
		this.zone1 = zone1;
	}
	public String getZone2() {
		return zone2;
	}
	public void setZone2(String zone2) {
		this.zone2 = zone2;
	}
	public String getZone3() {
		return zone3;
	}
	public void setZone3(String zone3) {
		this.zone3 = zone3;
	}
	public String getZone4() {
		return zone4;
	}
	public void setZone4(String zone4) {
		this.zone4 = zone4;
	}
	public double getFar() {
		return far;
	}
	public void setFar(double far) {
		this.far = far;
	}
    
    

}
