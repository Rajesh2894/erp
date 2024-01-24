package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DeprAprFactorsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5453205160248267453L;
	
	private BigDecimal initialCost;
	private Date capitalizedOn;
	private BigDecimal salvageValue;
	private BigDecimal rate;
	private BigDecimal life;

	public BigDecimal getInitialCost() {
		return initialCost;
	}

	public void setInitialCost(BigDecimal initialCost) {
		this.initialCost = initialCost;
	}

	public Date getCapitalizedOn() {
		return capitalizedOn;
	}

	public void setCapitalizedOn(Date capitalizedOn) {
		this.capitalizedOn = capitalizedOn;
	}

	public BigDecimal getSalvageValue() {
		return salvageValue;
	}

	public void setSalvageValue(BigDecimal salvageValue) {
		this.salvageValue = salvageValue;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getLife() {
		return life;
	}

	public void setLife(BigDecimal life) {
		this.life = life;
	}

	@Override
	public String toString() {
		return "DeprAprFactorsDTO [initialCost=" + initialCost + ", capitalizedOn=" + capitalizedOn + ", salvageValue="
				+ salvageValue + ", rate=" + rate + ", life=" + life + "]";
	}

}
