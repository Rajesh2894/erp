package com.abm.mainet.account.dto;

import java.io.Serializable;

/**
 * @author tejas.kotekar
 *
 */
public class ChequeBookUtilizationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bankAccountId;
    private Long chequeBookId;
    private Long orgId;
    private String chequeNo;
    private Long chequeStatus;
    private String chequeStatusDesc;
    private String transactionNo;
    private String transactionDate;
    private String amountDesc;
    private String clearanceDate;
    private Long chequeId;
    private String paymentType;
    private Long paymentId;

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(final Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getChequeBookId() {
        return chequeBookId;
    }

    public void setChequeBookId(final Long chequeBookId) {
        this.chequeBookId = chequeBookId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(final String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public Long getChequeStatus() {
        return chequeStatus;
    }

    public void setChequeStatus(final Long chequeStatus) {
        this.chequeStatus = chequeStatus;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(final String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAmountDesc() {
        return amountDesc;
    }

    public void setAmountDesc(final String amountDesc) {
        this.amountDesc = amountDesc;
    }

    public String getClearanceDate() {
        return clearanceDate;
    }

    public void setClearanceDate(final String clearanceDate) {
        this.clearanceDate = clearanceDate;
    }

    public Long getChequeId() {
        return chequeId;
    }

    public void setChequeId(final Long chequeId) {
        this.chequeId = chequeId;
    }

    public String getChequeStatusDesc() {
        return chequeStatusDesc;
    }

    public void setChequeStatusDesc(final String chequeStatusDesc) {
        this.chequeStatusDesc = chequeStatusDesc;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(final String paymentType) {
        this.paymentType = paymentType;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final Long paymentId) {
        this.paymentId = paymentId;
    }
}
