/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author cherupelli.srikanth
 * @since 20 Jan 2023
 */
public class CashModeInfoDto implements Serializable{

	private static final long serialVersionUID = 7080054344065950408L;
	
	private Long cashAmount;

	public Long getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Long cashAmount) {
		this.cashAmount = cashAmount;
	}

	
	
	
}
