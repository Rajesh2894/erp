/**
 * 
 */
package com.abm.mainet.brms.core.dto;

import java.io.Serializable;

/**
 * @author Vivek.Kumar
 * @since  18-04-2016
 *
 */
public class ChargeDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3189163645054858112L;

	
	private long chargeCode;
	private double chargeAmount;
	private String chargeDescEng;
	private String chargeDescReg;
	
	/**
	 * @return the chargeCode
	 */
	public long getChargeCode() {
		return chargeCode;
	}
	/**
	 * @param chargeCode the chargeCode to set
	 */
	public void setChargeCode(long chargeCode) {
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
	public void setChargeDescEng(String chargeDescEng) {
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
	public void setChargeDescReg(String chargeDescReg) {
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
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChargeDetailDTO [chargeCode=");
		builder.append(chargeCode);
		builder.append(", chargeDescEng=");
		builder.append(chargeDescEng);
		builder.append(", chargeDescReg=");
		builder.append(chargeDescReg);
		builder.append("]");
		return builder.toString();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (chargeCode ^ (chargeCode >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChargeDetailDTO other = (ChargeDetailDTO) obj;
		if (chargeCode != other.chargeCode)
			return false;
		return true;
	}
	
	
	
	
}
