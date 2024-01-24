package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

public class TrasactionReversalDTO  implements Serializable
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7748680201361585879L;

	private Date transactionDate = null;
	
	private Long transactionNo = null;
	
	private String transactionType =null;
	
	private String referenceNo=null;
	
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Long getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(Long transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	
}
