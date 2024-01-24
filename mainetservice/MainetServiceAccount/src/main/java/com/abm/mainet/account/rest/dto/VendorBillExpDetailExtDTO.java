package com.abm.mainet.account.rest.dto;

import java.io.Serializable;

public class VendorBillExpDetailExtDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 390135724962004423L;
	
	private String accountHead; //budgetCodeId;
    private String amount;
    private String amountStr;
    private String sanctionedAmount;
    private String disallowedAmount;
    private String sanctionedAmountStr;
    private Long projectedExpenditureId;
    private String newBalanceAmount;
    private String disallowedRemark;
    private String originalEstimate;
    private String balanceAmount;
    private String balProvisionAmount;
    private String budgetCodeDesc;
    private Long bmId;
    
    
    
	public String getAccountHead() {
		return accountHead;
	}
	public String getAmount() {
		return amount;
	}
	public String getAmountStr() {
		return amountStr;
	}
	public String getSanctionedAmount() {
		return sanctionedAmount;
	}
	public String getDisallowedAmount() {
		return disallowedAmount;
	}
	public String getSanctionedAmountStr() {
		return sanctionedAmountStr;
	}
	public Long getProjectedExpenditureId() {
		return projectedExpenditureId;
	}
	public String getNewBalanceAmount() {
		return newBalanceAmount;
	}
	public String getDisallowedRemark() {
		return disallowedRemark;
	}
	public String getOriginalEstimate() {
		return originalEstimate;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public String getBalProvisionAmount() {
		return balProvisionAmount;
	}
	public String getBudgetCodeDesc() {
		return budgetCodeDesc;
	}
	public Long getBmId() {
		return bmId;
	}
	public void setAccountHead(String accountHead) {
		this.accountHead = accountHead;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	public void setSanctionedAmount(String sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}
	public void setDisallowedAmount(String disallowedAmount) {
		this.disallowedAmount = disallowedAmount;
	}
	public void setSanctionedAmountStr(String sanctionedAmountStr) {
		this.sanctionedAmountStr = sanctionedAmountStr;
	}
	public void setProjectedExpenditureId(Long projectedExpenditureId) {
		this.projectedExpenditureId = projectedExpenditureId;
	}
	public void setNewBalanceAmount(String newBalanceAmount) {
		this.newBalanceAmount = newBalanceAmount;
	}
	public void setDisallowedRemark(String disallowedRemark) {
		this.disallowedRemark = disallowedRemark;
	}
	public void setOriginalEstimate(String originalEstimate) {
		this.originalEstimate = originalEstimate;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public void setBalProvisionAmount(String balProvisionAmount) {
		this.balProvisionAmount = balProvisionAmount;
	}
	public void setBudgetCodeDesc(String budgetCodeDesc) {
		this.budgetCodeDesc = budgetCodeDesc;
	}
	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}
}
