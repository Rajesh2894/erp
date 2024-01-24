/**
 *
 */
package com.abm.mainet.common.integration.dto;

import java.io.Serializable;

/**
 * @author Vivek.Kumar
 * @since 18-04-2016
 *
 */
public class ChargeDetailDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3189163645054858112L;

    private long chargeCode;
    private double chargeAmount;
    private String chargeDescEng;
    private String chargeDescReg;
    private String TaxCode;
    private double percentageRate;
    private double editedAmount;
    private Double length;
    private Double height;
    
    public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	/**
     * @return the chargeCode
     */
    public long getChargeCode() {
        return chargeCode;
    }

    /**
     * @param chargeCode the chargeCode to set
     */
    public void setChargeCode(final long chargeCode) {
        this.chargeCode = chargeCode;
    }

    /**
     * @return the chargeDescEng
     */
    public String getChargeDescEng() {
        return chargeDescEng;
    }

    /**
     * @param chargeDescEng the chargeDescEng to set
     */
    public void setChargeDescEng(final String chargeDescEng) {
        this.chargeDescEng = chargeDescEng;
    }

    /**
     * @return the chargeDescReg
     */
    public String getChargeDescReg() {
        return chargeDescReg;
    }

    /**
     * @param chargeDescReg the chargeDescReg to set
     */
    public void setChargeDescReg(final String chargeDescReg) {
        this.chargeDescReg = chargeDescReg;
    }

    /**
     * @return the chargeAmount
     */
    public double getChargeAmount() {
        return chargeAmount;
    }

    /**
     * @param chargeAmount the chargeAmount to set
     */
    public void setChargeAmount(final double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ChargeDetailDTO [chargeCode=");
        builder.append(chargeCode);
        builder.append(", chargeAmount=");
        builder.append(chargeAmount);
        builder.append(", chargeDescEng=");
        builder.append(chargeDescEng);
        builder.append(", chargeDescReg=");
        builder.append(chargeDescReg);
        builder.append("]");
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (int) (chargeCode ^ (chargeCode >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChargeDetailDTO other = (ChargeDetailDTO) obj;
        if (chargeCode != other.chargeCode) {
            return false;
        }
        return true;
    }

    /**
     * @return the taxCode
     */
    public String getTaxCode() {
        return TaxCode;
    }

    /**
     * @param taxCode the taxCode to set
     */
    public void setTaxCode(final String taxCode) {
        TaxCode = taxCode;
    }

    public double getPercentageRate() {
        return percentageRate;
    }

    public void setPercentageRate(double percentageRate) {
        this.percentageRate = percentageRate;
    }

	public double getEditedAmount() {
		return editedAmount;
	}

	public void setEditedAmount(double editedAmount) {
		this.editedAmount = editedAmount;
	}

	

	


    
}
