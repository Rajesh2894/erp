
package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author cherupelli.srikanth
 * @since 05 April 2023
 */
public class ReversalPaymentForMPOSDto implements Serializable{
	
	private static final long serialVersionUID = -670011852105447497L;

	private String billIdentifier;
	
	private Long plTransactionId;
	
	private Long reversedAmount;
	
	private String reversalTime;
	
	private String reversalReason;

	public String getBillIdentifier() {
		return billIdentifier;
	}

	public void setBillIdentifier(String billIdentifier) {
		this.billIdentifier = billIdentifier;
	}

	public Long getPlTransactionId() {
		return plTransactionId;
	}

	public void setPlTransactionId(Long plTransactionId) {
		this.plTransactionId = plTransactionId;
	}

	public Long getReversedAmount() {
		return reversedAmount;
	}

	public void setReversedAmount(Long reversedAmount) {
		this.reversedAmount = reversedAmount;
	}

	public String getReversalTime() {
		return reversalTime;
	}

	public void setReversalTime(String reversalTime) {
		this.reversalTime = reversalTime;
	}

	public String getReversalReason() {
		return reversalReason;
	}

	public void setReversalReason(String reversalReason) {
		this.reversalReason = reversalReason;
	}
	
	
}
