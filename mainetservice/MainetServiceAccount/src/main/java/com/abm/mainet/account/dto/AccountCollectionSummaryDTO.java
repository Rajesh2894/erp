package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class AccountCollectionSummaryDTO implements Serializable {

    private static final long serialVersionUID = -1400258736957094770L;
    private Long reportTypeId;
    private String fromDate;
    private String toDate;
    private String nameOfDepartment;
    private String nameOfTheRevenueHead;
    private String cashAmountIndianCurrency;
    private String bankAmountIndianCurrency;
    private String chequeDDAmountIndianCurrency;
    private String grandTotalIndianCurrency;
    private BigDecimal cashAmount;
    private BigDecimal bankAmount;
    private BigDecimal chequeDDAmount;
    private String grandTotal;
    private String cashAmountTotalIndianCurrency;
    private String chequeDDTotalIndianCurrency;
    private String bankTotalIndianCurrency;
    private List<AccountCollectionSummaryDTO> collectionSummaryRecordList;
    private String totalAmountInWords;
    private Long receiptNumber;
    private String receiptDate;
    private String nameOfDepositer;
    private String receiptMode;
    private Long chequeNumber;
    private List<AccountCollectionSummaryDTO> listOfCollectionDetail;
    private String bankAccountNumber;
    private String dateOfDeposit;
    private String dateOfRealisation;
    private String whetherReturned;
    private String remarks;
    private String rmReceiptNo;
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getDateOfDeposit() {
        return dateOfDeposit;
    }

    public void setDateOfDeposit(String dateOfDeposit) {
        this.dateOfDeposit = dateOfDeposit;
    }

    public String getDateOfRealisation() {
        return dateOfRealisation;
    }

    public void setDateOfRealisation(String dateOfRealisation) {
        this.dateOfRealisation = dateOfRealisation;
    }

    public String getWhetherReturned() {
        return whetherReturned;
    }

    public void setWhetherReturned(String whetherReturned) {
        this.whetherReturned = whetherReturned;
    }

    public String getReceiptMode() {
        return receiptMode;
    }

    public void setReceiptMode(String receiptMode) {
        this.receiptMode = receiptMode;
    }

    public String getTotalAmountInWords() {
        return totalAmountInWords;
    }

    public void setTotalAmountInWords(String totalAmountInWords) {
        this.totalAmountInWords = totalAmountInWords;
    }

    public Long getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId) {
        this.reportTypeId = reportTypeId;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getNameOfDepartment() {
        return nameOfDepartment;
    }

    public void setNameOfDepartment(String nameOfDepartment) {
        this.nameOfDepartment = nameOfDepartment;
    }

    public String getNameOfTheRevenueHead() {
        return nameOfTheRevenueHead;
    }

    public void setNameOfTheRevenueHead(String nameOfTheRevenueHead) {
        this.nameOfTheRevenueHead = nameOfTheRevenueHead;
    }

    public String getCashAmountIndianCurrency() {
        return cashAmountIndianCurrency;
    }

    public void setCashAmountIndianCurrency(String cashAmountIndianCurrency) {
        this.cashAmountIndianCurrency = cashAmountIndianCurrency;
    }

    public String getBankAmountIndianCurrency() {
        return bankAmountIndianCurrency;
    }

    public void setBankAmountIndianCurrency(String bankAmountIndianCurrency) {
        this.bankAmountIndianCurrency = bankAmountIndianCurrency;
    }

    public String getChequeDDAmountIndianCurrency() {
        return chequeDDAmountIndianCurrency;
    }

    public void setChequeDDAmountIndianCurrency(String chequeDDAmountIndianCurrency) {
        this.chequeDDAmountIndianCurrency = chequeDDAmountIndianCurrency;
    }

    public String getGrandTotalIndianCurrency() {
        return grandTotalIndianCurrency;
    }

    public void setGrandTotalIndianCurrency(String grandTotalIndianCurrency) {
        this.grandTotalIndianCurrency = grandTotalIndianCurrency;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(BigDecimal bankAmount) {
        this.bankAmount = bankAmount;
    }

    public BigDecimal getChequeDDAmount() {
        return chequeDDAmount;
    }

    public void setChequeDDAmount(BigDecimal chequeDDAmount) {
        this.chequeDDAmount = chequeDDAmount;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<AccountCollectionSummaryDTO> getCollectionSummaryRecordList() {
        return collectionSummaryRecordList;
    }

    public void setCollectionSummaryRecordList(List<AccountCollectionSummaryDTO> collectionSummaryRecordList) {
        this.collectionSummaryRecordList = collectionSummaryRecordList;
    }

    public String getCashAmountTotalIndianCurrency() {
        return cashAmountTotalIndianCurrency;
    }

    public void setCashAmountTotalIndianCurrency(String cashAmountTotalIndianCurrency) {
        this.cashAmountTotalIndianCurrency = cashAmountTotalIndianCurrency;
    }

    public String getChequeDDTotalIndianCurrency() {
        return chequeDDTotalIndianCurrency;
    }

    public void setChequeDDTotalIndianCurrency(String chequeDDTotalIndianCurrency) {
        this.chequeDDTotalIndianCurrency = chequeDDTotalIndianCurrency;
    }

    public String getBankTotalIndianCurrency() {
        return bankTotalIndianCurrency;
    }

    public void setBankTotalIndianCurrency(String bankTotalIndianCurrency) {
        this.bankTotalIndianCurrency = bankTotalIndianCurrency;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getNameOfDepositer() {
        return nameOfDepositer;
    }

    public void setNameOfDepositer(String nameOfDepositer) {
        this.nameOfDepositer = nameOfDepositer;
    }

    public Long getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(Long receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Long getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(Long chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getRmReceiptNo() {
		return rmReceiptNo;
	}

	public void setRmReceiptNo(String rmReceiptNo) {
		this.rmReceiptNo = rmReceiptNo;
	}

	public List<AccountCollectionSummaryDTO> getListOfCollectionDetail() {
        return listOfCollectionDetail;
    }

    public void setListOfCollectionDetail(List<AccountCollectionSummaryDTO> listOfCollectionDetail) {
        this.listOfCollectionDetail = listOfCollectionDetail;
    }

}
