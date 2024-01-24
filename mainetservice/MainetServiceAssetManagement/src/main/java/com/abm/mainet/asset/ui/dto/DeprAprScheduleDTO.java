package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeprAprScheduleDTO implements Serializable {
	 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1627265915542624779L;
	/**
	 * Value at the start of the period
	 */
	private BigDecimal valueAtStart;
	/**
	 * Depreciation in this period
	 */
	private BigDecimal deprExpense;
	/**
	 * Rate at which depreciated
	 */
	private BigDecimal deprRate;

	public BigDecimal getValueAtStart() {
		return valueAtStart;
	}

	public void setValueAtStart(BigDecimal valueAtStart) {
		this.valueAtStart = valueAtStart;
	}

	public BigDecimal getDeprExpense() {
		return deprExpense;
	}

	public void setDeprExpense(BigDecimal deprExpense) {
		this.deprExpense = deprExpense;
	}

	public BigDecimal getDeprRate() {
		return deprRate;
	}

	public void setDeprRate(BigDecimal deprRate) {
		this.deprRate = deprRate;
	}

	@Override
	public String toString() {
		return "DeprAprScheduleDTO [valueAtStart=" + valueAtStart + ", deprExpense=" + deprExpense + ", deprRate="
				+ deprRate + "]";
	}

}
