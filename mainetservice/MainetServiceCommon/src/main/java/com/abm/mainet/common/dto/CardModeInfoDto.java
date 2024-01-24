/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author cherupelli.srikanth
 * @since 20 Jan 2023
 */
public class CardModeInfoDto implements Serializable{

	private static final long serialVersionUID = -3309761181473488052L;

	private Long cardAmount;
	
	private String cardApprovalCode;
	
	private String cardMaskedNumber;
	
	private String cardTid;
	
	private String cardAcquirer;
	
	private String cardAcquirerMid;
	
	private String cardHolderName;


	public Long getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(Long cardAmount) {
		this.cardAmount = cardAmount;
	}

	public String getCardApprovalCode() {
		return cardApprovalCode;
	}

	public void setCardApprovalCode(String cardApprovalCode) {
		this.cardApprovalCode = cardApprovalCode;
	}

	public String getCardMaskedNumber() {
		return cardMaskedNumber;
	}

	public void setCardMaskedNumber(String cardMaskedNumber) {
		this.cardMaskedNumber = cardMaskedNumber;
	}

	public String getCardTid() {
		return cardTid;
	}

	public void setCardTid(String cardTid) {
		this.cardTid = cardTid;
	}

	public String getCardAcquirer() {
		return cardAcquirer;
	}

	public void setCardAcquirer(String cardAcquirer) {
		this.cardAcquirer = cardAcquirer;
	}


	public String getCardAcquirerMid() {
		return cardAcquirerMid;
	}

	public void setCardAcquirerMid(String cardAcquirerMid) {
		this.cardAcquirerMid = cardAcquirerMid;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	
	
}