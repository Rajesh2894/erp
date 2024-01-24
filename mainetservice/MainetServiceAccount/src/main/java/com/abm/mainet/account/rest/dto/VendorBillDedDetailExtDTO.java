package com.abm.mainet.account.rest.dto;

import java.io.Serializable;

public class VendorBillDedDetailExtDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1128049213390525565L;
	private Long id;
	private String accountcode;//budgetCodeId;
	private String rate;
	private String deductionAmount;
	private String deductionAmountStr;
	private String budgetCodeDesc;
	private Long bchId;
	private String expHeadAgainstDeductionHead;
	
	public Long getId() {
		return id;
	}
	public String getAccountcode() {
		return accountcode;
	}
	public String getRate() {
		return rate;
	}
	public String getDeductionAmount() {
		return deductionAmount;
	}
	public String getDeductionAmountStr() {
		return deductionAmountStr;
	}
	public String getBudgetCodeDesc() {
		return budgetCodeDesc;
	}
	public Long getBchId() {
		return bchId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public void setDeductionAmount(String deductionAmount) {
		this.deductionAmount = deductionAmount;
	}
	public void setDeductionAmountStr(String deductionAmountStr) {
		this.deductionAmountStr = deductionAmountStr;
	}
	public void setBudgetCodeDesc(String budgetCodeDesc) {
		this.budgetCodeDesc = budgetCodeDesc;
	}
	public void setBchId(Long bchId) {
		this.bchId = bchId;
	}
	public String getExpHeadAgainstDeductionHead() {
		return expHeadAgainstDeductionHead;
	}
	public void setExpHeadAgainstDeductionHead(String expHeadAgainstDeductionHead) {
		this.expHeadAgainstDeductionHead = expHeadAgainstDeductionHead;
	}
	
}
