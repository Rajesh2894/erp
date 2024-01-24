package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TdsPaymentEntryDto implements Serializable {

    private static final long serialVersionUID = 7883349474006271519L;

    private String transactionDate;
    private BigDecimal paymentAmount;
    private Long vendorId;
    private String paymentNo;
    private Long bankAcId;
    private Long sacHeadId;

    public String getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public Long getBankAcId() {
        return bankAcId;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public void setBankAcId(Long bankAcId) {
        this.bankAcId = bankAcId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

}
