package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BillDisplayDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -684919642726370968L;

    private BigDecimal arrearsTaxAmt;
    private BigDecimal currentYearTaxAmt;
    private BigDecimal totalTaxAmt;
    private double taxAmt;
    private String taxDesc;
    private Long displaySeq;
    private Long taxCategoryId;
    private Long taxId;
    private String parentTaxCode;
    private Long finYearId;
    private double percentageRate;
    private Long parentTaxId;
    private Long bmIdNo;

    public String getTaxDesc() {
        return taxDesc;
    }

    public void setTaxDesc(String taxDesc) {
        this.taxDesc = taxDesc;
    }

    public Long getDisplaySeq() {
        return displaySeq;
    }

    public void setDisplaySeq(Long displaySeq) {
        this.displaySeq = displaySeq;
    }

    public Long getTaxCategoryId() {
        return taxCategoryId;
    }

    public void setTaxCategoryId(Long taxCategoryId) {
        this.taxCategoryId = taxCategoryId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public BigDecimal getArrearsTaxAmt() {
        return arrearsTaxAmt;
    }

    public void setArrearsTaxAmt(BigDecimal arrearsTaxAmt) {
        this.arrearsTaxAmt = arrearsTaxAmt;
    }

    public BigDecimal getCurrentYearTaxAmt() {
        return currentYearTaxAmt;
    }

    public void setCurrentYearTaxAmt(BigDecimal currentYearTaxAmt) {
        this.currentYearTaxAmt = currentYearTaxAmt;
    }

    public BigDecimal getTotalTaxAmt() {
        return totalTaxAmt;
    }

    public void setTotalTaxAmt(BigDecimal totalTaxAmt) {
        this.totalTaxAmt = totalTaxAmt;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }

	public String getParentTaxCode() {
		return parentTaxCode;
	}

	public void setParentTaxCode(String parentTaxCode) {
		this.parentTaxCode = parentTaxCode;
	}

	public Long getFinYearId() {
		return finYearId;
	}

	public void setFinYearId(Long finYearId) {
		this.finYearId = finYearId;
	}

	public double getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(double percentageRate) {
		this.percentageRate = percentageRate;
	}

	public Long getParentTaxId() {
		return parentTaxId;
	}

	public void setParentTaxId(Long parentTaxId) {
		this.parentTaxId = parentTaxId;
	}

	public Long getBmIdNo() {
		return bmIdNo;
	}

	public void setBmIdNo(Long bmIdNo) {
		this.bmIdNo = bmIdNo;
	}
	
	

}
