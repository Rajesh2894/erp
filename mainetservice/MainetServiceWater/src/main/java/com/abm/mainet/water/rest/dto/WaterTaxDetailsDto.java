/**
 * 
 */
package com.abm.mainet.water.rest.dto;

/**
 * @author akshata.bhat
 *
 */
public class WaterTaxDetailsDto {

	private String taxName;
	
	private Double balTaxAmt;
	
	private Double balArrearsAmt;
	
	private Double taxAmt;

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public Double getBalTaxAmt() {
		return balTaxAmt;
	}

	public void setBalTaxAmt(Double balTaxAmt) {
		this.balTaxAmt = balTaxAmt;
	}

	public Double getBalArrearsAmt() {
		return balArrearsAmt;
	}

	public void setBalArrearsAmt(Double balArrearsAmt) {
		this.balArrearsAmt = balArrearsAmt;
	}

	public Double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}

	@Override
	public String toString() {
		return "WaterTaxDetailsDto [taxName=" + taxName + ", balTaxAmt=" + balTaxAmt + ", balArrearsAmt="
				+ balArrearsAmt + ", taxAmt=" + taxAmt + "]";
	}

}
