package com.abm.mainet.account.dto;

import java.math.BigDecimal;

public class AccountContraVoucherReportAccountheadBean {

    private String AccountCode;

    private String AccountPaymentHead;

    private String amount;
    private String AccountRecieptHead;
    private BigDecimal cpamount;

    private BigDecimal amountPaymentDebit;
    private BigDecimal amountPaymentCredit;
    private BigDecimal amountRecieptCredit;
    private BigDecimal amountRecieptDebit;
    private BigDecimal DrtotalAmount;
    private BigDecimal CrtotalAmount;
    private String budgetCodeId;
    private String budgetCode;

    public String getAccountCode() {
        return AccountCode;
    }

    public void setAccountCode(final String accountCode) {
        AccountCode = accountCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public BigDecimal getCpamount() {
        return cpamount;
    }

    public void setCpamount(final BigDecimal cpamount) {
        this.cpamount = cpamount;
    }

    public BigDecimal getDrtotalAmount() {
        return DrtotalAmount;
    }

    public void setDrtotalAmount(final BigDecimal drtotalAmount) {
        DrtotalAmount = drtotalAmount;
    }

    public BigDecimal getCrtotalAmount() {
        return CrtotalAmount;
    }

    public void setCrtotalAmount(final BigDecimal crtotalAmount) {
        CrtotalAmount = crtotalAmount;
    }

    public BigDecimal getAmountPaymentDebit() {
        return amountPaymentDebit;
    }

    public void setAmountPaymentDebit(final BigDecimal amountPaymentDebit) {
        this.amountPaymentDebit = amountPaymentDebit;
    }

    public BigDecimal getAmountPaymentCredit() {
        return amountPaymentCredit;
    }

    public void setAmountPaymentCredit(final BigDecimal amountPaymentCredit) {
        this.amountPaymentCredit = amountPaymentCredit;
    }

    public BigDecimal getAmountRecieptCredit() {
        return amountRecieptCredit;
    }

    public void setAmountRecieptCredit(final BigDecimal amountRecieptCredit) {
        this.amountRecieptCredit = amountRecieptCredit;
    }

    public BigDecimal getAmountRecieptDebit() {
        return amountRecieptDebit;
    }

    public void setAmountRecieptDebit(final BigDecimal amountRecieptDebit) {
        this.amountRecieptDebit = amountRecieptDebit;
    }

    public String getAccountPaymentHead() {
        return AccountPaymentHead;
    }

    public void setAccountPaymentHead(final String accountPaymentHead) {
        AccountPaymentHead = accountPaymentHead;
    }

    public String getAccountRecieptHead() {
        return AccountRecieptHead;
    }

    public void setAccountRecieptHead(final String accountRecieptHead) {
        AccountRecieptHead = accountRecieptHead;
    }

    public String getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final String budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public String getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(final String budgetCode) {
        this.budgetCode = budgetCode;
    }

}
