package com.abm.mainet.cfc.challan.dto;

import java.io.Serializable;
import java.util.Date;

public class ChallanReportDTO implements Serializable {

    private static final long serialVersionUID = -5444624859470990936L;

    private String billNo;
    private String billDate;
    private String details;

    private double amountPayable;
    private double amountPayableArrear;
    private double amountPayableCurrent;
    private double amountReceived;
    private double amountReceivedArrear;
    private double amountReceivedCurrent;
    private double amountReceivedArrearCurr;
    private double amountPayablArrearCurr;
    private String acHead;
    private Long displaySeq;
    private Long TaxId; 

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public double getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(final double amountPayable) {
        this.amountPayable = amountPayable;
    }

    public double getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(final double amountReceived) {
        this.amountReceived = amountReceived;
    }

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	
	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public double getAmountPayableArrear() {
		return amountPayableArrear;
	}

	public void setAmountPayableArrear(double amountPayableArrear) {
		this.amountPayableArrear = amountPayableArrear;
	}

	public double getAmountPayableCurrent() {
		return amountPayableCurrent;
	}

	public void setAmountPayableCurrent(double amountPayableCurrent) {
		this.amountPayableCurrent = amountPayableCurrent;
	}

	public double getAmountReceivedArrear() {
		return amountReceivedArrear;
	}

	public void setAmountReceivedArrear(double amountReceivedArrear) {
		this.amountReceivedArrear = amountReceivedArrear;
	}

	public double getAmountReceivedCurrent() {
		return amountReceivedCurrent;
	}

	public void setAmountReceivedCurrent(double amountReceivedCurrent) {
		this.amountReceivedCurrent = amountReceivedCurrent;
	}

	public double getAmountReceivedArrearCurr() {
		return amountReceivedArrearCurr;
	}

	public void setAmountReceivedArrearCurr(double amountReceivedArrearCurr) {
		this.amountReceivedArrearCurr = amountReceivedArrearCurr;
	}

	public double getAmountPayablArrearCurr() {
		return amountPayablArrearCurr;
	}

	public void setAmountPayablArrearCurr(double amountPayablArrearCurr) {
		this.amountPayablArrearCurr = amountPayablArrearCurr;
	}

	public Long getDisplaySeq() {
		return displaySeq;
	}

	public void setDisplaySeq(Long displaySeq) {
		this.displaySeq = displaySeq;
	}

	public String getAcHead() {
		return acHead;
	}

	public void setAcHead(String acHead) {
		this.acHead = acHead;
	}

	public Long getTaxId() {
		return TaxId;
	}

	public void setTaxId(Long taxId) {
		TaxId = taxId;
	}
	

}
