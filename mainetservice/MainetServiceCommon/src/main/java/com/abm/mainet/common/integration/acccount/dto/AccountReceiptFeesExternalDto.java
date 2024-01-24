package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cherupelli.srikanth
 *@since 04 FEB 2020
 */
public class AccountReceiptFeesExternalDto implements Serializable{

	private static final long serialVersionUID = -5358823021814718881L;

	private String receiptHead;
	
	private BigDecimal receptAmount;
	
	private String financialYear;
	
	private String demColCode;

	
	public String getReceiptHead() {
		return receiptHead;
	}

	public void setReceiptHead(String receiptHead) {
		this.receiptHead = receiptHead;
	}

	public BigDecimal getReceptAmount() {
		return receptAmount;
	}

	public void setReceptAmount(BigDecimal receptAmount) {
		this.receptAmount = receptAmount;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getDemColCode() {
		return demColCode;
	}

	public void setDemColCode(String demColCode) {
		this.demColCode = demColCode;
	}
	
	
}
