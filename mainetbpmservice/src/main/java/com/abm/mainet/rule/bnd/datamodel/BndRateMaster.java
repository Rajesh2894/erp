/**
 * 
 */
package com.abm.mainet.rule.bnd.datamodel;

import com.abm.mainet.rule.datamodel.CommonModel;

/**
 * DTO made for Web Service call, to hold request processing related common
 * request data.this dto must be passed while making web service call in order
 * to get the response
 * 
 * @author Vishwanath.s
 * @since 14-10-2019
 */
public class BndRateMaster extends CommonModel implements java.io.Serializable {

	private static final long serialVersionUID = 7962955595884665363L;

	private int noOfCopies;
	private String taxType;
	private String taxCode;
	private String taxCategory;
	private String taxSubCategory;
	private double flatRate;
	private double percentageRate;
	private String organisationType;
	private String chargeApplicableAt;
	private long slab1;
	private long slab2;
	private long slab3;
	private long slab4;
	private double slabRate1;
	private double slabRate2;
	private double slabRate3;
	private double slabRate4;
	private double freeCopy;
	private long  issuedCopy;
	private double certificateFee;
	private double certificateCharge;


	public BndRateMaster() {
		super();
		taxType = "NA";
		taxCode = "NA";
		taxCategory = "NA";
		taxSubCategory = "NA";
		percentageRate = 0.0D;
		chargeApplicableAt = "NA";
		this.noOfCopies = 0;
		this.slab1 = 0L;
		this.slab2 = 0L;
		this.slab3 = 0L;
		this.slab4 = 0L;
		this.slabRate1 = 0.0D;
		this.slabRate2 = 0.0D;
		this.slabRate3 = 0.0D;
		this.slabRate4 = 0.0D;
		this.freeCopy = 0.0D;
		this.issuedCopy=0L;
        this.certificateFee=0.0D;
        this.certificateCharge=0.0D;
        this.organisationType = "NA";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	public BndRateMaster ruleResult() {
		return this;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
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

	public double getFreeCopy() {
		return freeCopy;
	}

	public void setFreeCopy(double freeCopy) {
		this.freeCopy = freeCopy;
	}

	public void setSlab(final long slab1, final long slab2, final long slab3, final long slab4) {

		this.slab1 = slab1;
		this.slab2 = slab2;
		this.slab3 = slab3;
		this.slab4 = slab4;
	}

	public void setSlabRate(final double slabRate1, final double slabRate2, final double slabRate3,
			final double slabRate4) {

		this.slabRate1 = slabRate1;
		this.slabRate2 = slabRate2;
		this.slabRate3 = slabRate3;
		this.slabRate4 = slabRate4;

	}

	public long getIssuedCopy() {
		return issuedCopy;
	}

	public void setIssuedCopy(long issuedCopy) {
		this.issuedCopy = issuedCopy;
	}

	public double getCertificateFee() {
		return certificateFee;
	}

	public void setCertificateFee(double certificateFee) {
		this.certificateFee = certificateFee;
	}

	public double getCertificateCharge() {
		return certificateCharge;
	}

	public void setCertificateCharge(double certificateCharge) {
		this.certificateCharge = certificateCharge;
	}

	public String getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(String organisationType) {
		this.organisationType = organisationType;
	}

}
