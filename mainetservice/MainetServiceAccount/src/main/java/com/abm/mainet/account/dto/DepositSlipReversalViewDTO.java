package com.abm.mainet.account.dto;

import java.math.BigDecimal;

/**
 *
 * @author Vivek.Kumar
 * @see 07 June 2017
 */
public class DepositSlipReversalViewDTO {

    private long slipNo;
    private String slipDate;
    private String depositMode;
    private String bankAccount;
    private Long receiptNo;
    private String receiptDate;
    private String department;
    private String instrumentType;
    private Long instrumentNo;
    private String instrumentDate;
    private String drawnOnBank;
    private BigDecimal amount;

    public long getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(final long slipNo) {
        this.slipNo = slipNo;
    }

    public String getSlipDate() {
        return slipDate;
    }

    public void setSlipDate(final String slipDate) {
        this.slipDate = slipDate;
    }

    public String getDepositMode() {
        return depositMode;
    }

    public void setDepositMode(final String depositMode) {
        this.depositMode = depositMode;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(final String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(final Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(final String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(final String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public Long getInstrumentNo() {
        return instrumentNo;
    }

    public void setInstrumentNo(final Long instrumentNo) {
        this.instrumentNo = instrumentNo;
    }

    public String getInstrumentDate() {
        return instrumentDate;
    }

    public void setInstrumentDate(final String instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    public String getDrawnOnBank() {
        return drawnOnBank;
    }

    public void setDrawnOnBank(final String drawnOnBank) {
        this.drawnOnBank = drawnOnBank;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

}
