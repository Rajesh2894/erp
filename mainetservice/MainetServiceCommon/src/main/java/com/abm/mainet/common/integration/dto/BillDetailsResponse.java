/**
 * 
 */
package com.abm.mainet.common.integration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author akshata.bhat
 *
 */
public class BillDetailsResponse {

    private Long bmIdno;
    private Long csIdn;
    private Long bmYear;
    private Date bmBilldt;
    private Date bmFromdt;
    private Date bmTodt;
    private Date bmDuedate;
    private Double bmTotalAmount;
    private Double bmTotalBalAmount;
    private Double bmTotalArrears;
    private String bmPaidFlag;
    private Date bmPrintdate;
    private Double actualPenalty;
    private Double pendingPenalty;
    private List<BillTaxDetailsResponse> billTaxDetails = new ArrayList<>(0);
    private Double totalBillPaymentAmout;
    private Double adjustmentAmount;
    private Double meterCost;
    private String errorMessage;
    
	public Long getBmIdno() {
		return bmIdno;
	}
	public void setBmIdno(Long bmIdno) {
		this.bmIdno = bmIdno;
	}
	public Long getCsIdn() {
		return csIdn;
	}
	public void setCsIdn(Long csIdn) {
		this.csIdn = csIdn;
	}
	public Long getBmYear() {
		return bmYear;
	}
	public void setBmYear(Long bmYear) {
		this.bmYear = bmYear;
	}
	public Date getBmBilldt() {
		return bmBilldt;
	}
	public void setBmBilldt(Date bmBilldt) {
		this.bmBilldt = bmBilldt;
	}
	public Date getBmFromdt() {
		return bmFromdt;
	}
	public void setBmFromdt(Date bmFromdt) {
		this.bmFromdt = bmFromdt;
	}
	public Date getBmTodt() {
		return bmTodt;
	}
	public void setBmTodt(Date bmTodt) {
		this.bmTodt = bmTodt;
	}
	public Date getBmDuedate() {
		return bmDuedate;
	}
	public void setBmDuedate(Date bmDuedate) {
		this.bmDuedate = bmDuedate;
	}
	public Double getBmTotalAmount() {
		return bmTotalAmount;
	}
	public void setBmTotalAmount(Double bmTotalAmount) {
		this.bmTotalAmount = bmTotalAmount;
	}
	public Double getBmTotalBalAmount() {
		return bmTotalBalAmount;
	}
	public void setBmTotalBalAmount(Double bmTotalBalAmount) {
		this.bmTotalBalAmount = bmTotalBalAmount;
	}
	public Double getBmTotalArrears() {
		return bmTotalArrears;
	}
	public void setBmTotalArrears(Double bmTotalArrears) {
		this.bmTotalArrears = bmTotalArrears;
	}
	public String getBmPaidFlag() {
		return bmPaidFlag;
	}
	public void setBmPaidFlag(String bmPaidFlag) {
		this.bmPaidFlag = bmPaidFlag;
	}
	public Date getBmPrintdate() {
		return bmPrintdate;
	}
	public void setBmPrintdate(Date bmPrintdate) {
		this.bmPrintdate = bmPrintdate;
	}
	public Double getActualPenalty() {
		return actualPenalty;
	}
	public void setActualPenalty(Double actualPenalty) {
		this.actualPenalty = actualPenalty;
	}
	public Double getPendingPenalty() {
		return pendingPenalty;
	}
	public void setPendingPenalty(Double pendingPenalty) {
		this.pendingPenalty = pendingPenalty;
	}
	public List<BillTaxDetailsResponse> getBillTaxDetails() {
		return billTaxDetails;
	}
	public void setBillTaxDetails(List<BillTaxDetailsResponse> billTaxDetails) {
		this.billTaxDetails = billTaxDetails;
	}
	public Double getTotalBillPaymentAmout() {
		return totalBillPaymentAmout;
	}
	public void setTotalBillPaymentAmout(Double totalBillPaymentAmout) {
		this.totalBillPaymentAmout = totalBillPaymentAmout;
	}
	public Double getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(Double adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	public Double getMeterCost() {
		return meterCost;
	}
	public void setMeterCost(Double meterCost) {
		this.meterCost = meterCost;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

