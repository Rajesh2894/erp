package com.abm.mainet.property.dto;

import java.io.Serializable;

public class PrintBillDetails implements Serializable {

    private static final long serialVersionUID = -2486534657825267116L;

    private String taxNameV;
    private double arrearsAmountV;
    private double currentAmountV;
    private double totalAmountV;
    private double percentageRate;
    private Long displaySeq;
    private double firstHalfCurrentAmountV;
    private double secondHalfCurrentAmountV;

    public String getTaxNameV() {
        return taxNameV;
    }

    public void setTaxNameV(String taxNameV) {
        this.taxNameV = taxNameV;
    }

    public double getArrearsAmountV() {
        return arrearsAmountV;
    }

    public void setArrearsAmountV(double arrearsAmountV) {
        this.arrearsAmountV = arrearsAmountV;
    }

    public double getCurrentAmountV() {
        return currentAmountV;
    }

    public void setCurrentAmountV(double currentAmountV) {
        this.currentAmountV = currentAmountV;
    }

    public double getTotalAmountV() {
        return totalAmountV;
    }

    public void setTotalAmountV(double totalAmountV) {
        this.totalAmountV = totalAmountV;
    }

	public double getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(double percentageRate) {
		this.percentageRate = percentageRate;
	}

	public Long getDisplaySeq() {
		return displaySeq;
	}

	public void setDisplaySeq(Long displaySeq) {
		this.displaySeq = displaySeq;
	}

	public double getFirstHalfCurrentAmountV() {
		return firstHalfCurrentAmountV;
	}

	public void setFirstHalfCurrentAmountV(double firstHalfCurrentAmountV) {
		this.firstHalfCurrentAmountV = firstHalfCurrentAmountV;
	}

	public double getSecondHalfCurrentAmountV() {
		return secondHalfCurrentAmountV;
	}

	public void setSecondHalfCurrentAmountV(double secondHalfCurrentAmountV) {
		this.secondHalfCurrentAmountV = secondHalfCurrentAmountV;
	}
	
}
