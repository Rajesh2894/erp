package com.abm.mainet.water.rest.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.domain.Organisation;

/**
 * @author Rahul.Yadav
 *
 */

public class WaterBillRequestDTO implements Serializable {

    private static final long serialVersionUID = -1501457334734716357L;

    private Double rebateAmount;

    private double totalOutstanding;

    private String ccnNumber;
    
    private String oldccNumber;

    private String status;

    private Double amountPaid;

    private long orgid;

    private Long csIdn;

    private long userId;

    private String advancePay;
    
    private String ipAddress;
    
    private Date manualReceiptDate;
    
    private double surcharge;

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getCcnNumber() {
        return ccnNumber;
    }

    public void setCcnNumber(final String ccnNumber) {
        this.ccnNumber = ccnNumber;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(final Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public long getOrgid() {
        return orgid;
    }

    public void setOrgid(final long orgid) {
        this.orgid = orgid;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public String getAdvancePay() {
        return advancePay;
    }

    public void setAdvancePay(final String advancePay) {
        this.advancePay = advancePay;
    }

    public Double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(final Double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public double getTotalOutstanding() {
        return totalOutstanding;
    }

    public String getOldccNumber() {
		return oldccNumber;
	}

	public void setOldccNumber(String oldccNumber) {
		this.oldccNumber = oldccNumber;
	}

	public void setTotalOutstanding(final double totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

	public Date getManualReceiptDate() {
		return manualReceiptDate;
	}

	public void setManualReceiptDate(Date manualReceiptDate) {
		this.manualReceiptDate = manualReceiptDate;
	}

	public double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}

    
}
