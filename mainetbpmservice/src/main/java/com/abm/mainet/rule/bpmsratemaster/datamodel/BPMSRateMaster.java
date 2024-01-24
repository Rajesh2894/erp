package com.abm.mainet.rule.bpmsratemaster.datamodel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.abm.mainet.rule.datamodel.CommonModel;

public class BPMSRateMaster extends CommonModel implements Serializable {

    /**
     * 
     */
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

    public BPMSRateMaster() {
        this.taxType = "NA";
        this.taxCode = "NA";
        this.parentTaxCode = "NA";
        this.taxCategory = "NA";
        this.taxSubCategory = "NA";
        this.itemCategory1 = "NA";
        this.itemCategory2 = "NA";
        this.itemCategory3 = "NA";
        this.itemCategory4 = "NA";
        this.itemCategory5 = "NA";
        this.rateStartDate = 0l;
        this.flatRate = 0.0d;
        this.slabRate1 = 0.0d;
        this.slabRate2 = 0.0d;
        this.slabRate3 = 0.0d;
        this.slabRate4 = 0.0d;
        this.slabRate5 = 0.0d;
        this.slab1 = 0l;
        this.slab2 = 0l;
        this.slab3 = 0l;
        this.slab4 = 0l;
        this.slab5 = 0l;
        this.applicableAt = "NA";
        this.percentageRate = 0.0d;
        this.unit = 0l;
        this.additionalValue = 0l;
        this.licenseType = "NA";
        this.area = 0l;
        this.parentTaxValue = 0.0d;
        this.purposeFactor="NA";
        this.zone="NA";
        this.zone1="NA";
        this.zone2="NA";
        this.zone3="NA";
        this.zone4="NA";
        this.far=0.0d;
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

	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BPMSRateMaster ruleResult() {
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

    public String getApplicableAt() {
        return applicableAt;
    }

    public void setApplicableAt(String applicableAt) {
        this.applicableAt = applicableAt;
    }

    public double getPercentageRate() {
        return percentageRate;
    }

    public void setPercentageRate(double percentageRate) {
        this.percentageRate = percentageRate;
    }

    public double getSlab1() {
        return slab1;
    }

    public void setSlab1(long slab1) {
        this.slab1 = slab1;
    }

    public double getSlab2() {
        return slab2;
    }

    public void setSlab2(long slab2) {
        this.slab2 = slab2;
    }

    public double getSlab3() {
        return slab3;
    }

    public void setSlab3(long slab3) {
        this.slab3 = slab3;
    }

    public double getSlab4() {
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

    public void setSlabRate(double slabRate1, double slabRate2, double slabRate3, double slabRate4, double slabRate5) {
        this.slabRate1 = slabRate1;
        this.slabRate2 = slabRate2;
        this.slabRate3 = slabRate3;
        this.slabRate4 = slabRate4;
        this.slabRate5 = slabRate5;
    }

    public void setSlab(long slab1, long slab2, long slab3, long slab4, long slab5) {
        this.slab1 = slab1;
        this.slab2 = slab2;
        this.slab3 = slab3;
        this.slab4 = slab4;
        this.slab5 = slab5;
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

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}


}
