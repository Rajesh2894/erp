
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tejas.kotekar
 *
 */
public class TransactionTrackingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long budgetCodeId;
    private String accountCode;
    private String accountHead;
    private String openingBalance;
    private String closingBalance;
    private String debitAmount;
    private String creditAmount;
    private String month;
    private Long faYearid;
    private BigDecimal openingDrAmount;
    private BigDecimal openingCrAmount;
    private BigDecimal closingDrAmount;
    private BigDecimal closingCrAmount;
    private BigDecimal transactionDrAmount;
    private BigDecimal transactionCrAmount;
    private List<TransactionTrackingDto> listOfSum = new ArrayList<>();
    private BigDecimal sumOpeningCR;
    private BigDecimal sumOpeningDR;
    private BigDecimal sumClosingCR;
    private BigDecimal sumClosingDR;
    private BigDecimal sumTransactionDR;
    private BigDecimal sumTransactionCR;
    private TransactionTrackingDto transactionTrackingDto1;
    private String fromDate;
    private String toDate;
    private String voucherNumber;
    private String narration;
    private Long voucherId;
    private String voucherDate;
    private String successfulFlag;

    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(final String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(final String accountHead) {
        this.accountHead = accountHead;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(final String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(final String closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(final String debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(final String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(final String month) {
        this.month = month;
    }

    public Long getFaYearid() {
        return faYearid;
    }

    public void setFaYearid(Long faYearid) {
        this.faYearid = faYearid;
    }

    public BigDecimal getOpeningDrAmount() {
        return openingDrAmount;
    }

    public void setOpeningDrAmount(BigDecimal openingDrAmount) {
        this.openingDrAmount = openingDrAmount;
    }

    public BigDecimal getOpeningCrAmount() {
        return openingCrAmount;
    }

    public void setOpeningCrAmount(BigDecimal openingCrAmount) {
        this.openingCrAmount = openingCrAmount;
    }

    public BigDecimal getClosingDrAmount() {
        return closingDrAmount;
    }

    public void setClosingDrAmount(BigDecimal closingDrAmount) {
        this.closingDrAmount = closingDrAmount;
    }

    public BigDecimal getClosingCrAmount() {
        return closingCrAmount;
    }

    public void setClosingCrAmount(BigDecimal closingCrAmount) {
        this.closingCrAmount = closingCrAmount;
    }

    public BigDecimal getTransactionDrAmount() {
        return transactionDrAmount;
    }

    public void setTransactionDrAmount(BigDecimal transactionDrAmount) {
        this.transactionDrAmount = transactionDrAmount;
    }

    public BigDecimal getTransactionCrAmount() {
        return transactionCrAmount;
    }

    public void setTransactionCrAmount(BigDecimal transactionCrAmount) {
        this.transactionCrAmount = transactionCrAmount;
    }

    public List<TransactionTrackingDto> getListOfSum() {
        return listOfSum;
    }

    public void setListOfSum(List<TransactionTrackingDto> listOfSum) {
        this.listOfSum = listOfSum;
    }

    public BigDecimal getSumOpeningCR() {
        return sumOpeningCR;
    }

    public void setSumOpeningCR(BigDecimal sumOpeningCR) {
        this.sumOpeningCR = sumOpeningCR;
    }

    public BigDecimal getSumOpeningDR() {
        return sumOpeningDR;
    }

    public void setSumOpeningDR(BigDecimal sumOpeningDR) {
        this.sumOpeningDR = sumOpeningDR;
    }

    public BigDecimal getSumClosingCR() {
        return sumClosingCR;
    }

    public void setSumClosingCR(BigDecimal sumClosingCR) {
        this.sumClosingCR = sumClosingCR;
    }

    public BigDecimal getSumClosingDR() {
        return sumClosingDR;
    }

    public void setSumClosingDR(BigDecimal sumClosingDR) {
        this.sumClosingDR = sumClosingDR;
    }

    public BigDecimal getSumTransactionDR() {
        return sumTransactionDR;
    }

    public void setSumTransactionDR(BigDecimal sumTransactionDR) {
        this.sumTransactionDR = sumTransactionDR;
    }

    public BigDecimal getSumTransactionCR() {
        return sumTransactionCR;
    }

    public void setSumTransactionCR(BigDecimal sumTransactionCR) {
        this.sumTransactionCR = sumTransactionCR;
    }

    public TransactionTrackingDto getTransactionTrackingDto1() {
        return transactionTrackingDto1;
    }

    public void setTransactionTrackingDto1(TransactionTrackingDto transactionTrackingDto1) {
        this.transactionTrackingDto1 = transactionTrackingDto1;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getSuccessfulFlag() {
        return successfulFlag;
    }

    public void setSuccessfulFlag(String successfulFlag) {
        this.successfulFlag = successfulFlag;
    }

}
