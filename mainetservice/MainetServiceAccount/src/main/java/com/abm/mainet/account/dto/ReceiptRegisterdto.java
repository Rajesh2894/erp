package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptRegisterdto implements Serializable {

    private static final long serialVersionUID = -4661996578265033061L;

    private int index;
    private String toDate;
    private String fromDate;
    private Long empId;
    private String tranDate;
    private long rmRcptid;

    private Long rmRcptno;
    private String rmReceiptNo;
    private String rmDate;

    private BigDecimal rmAmount;

    private String rmReceivedfrom;

    private String manualReceiptNo;
    private String receiptHead;
    private Long cheqno;
    private Map<String, List<ReceiptRegisterdto>> listOfTbReceiptRegister;
    private List<ReceiptRegisterdto> listofbankAmount;
    private Long cbBankidDesc;
    private String organizationName;
    private BigDecimal cashAmount;
    private BigDecimal BankAmount;
    private BigDecimal chequeDD;
    private String username;
    private String bankName;
    private BigDecimal sumRmAmount;
    private BigDecimal sumBankAmount;
    private BigDecimal sumChequeDDAmount;
    private BigDecimal sumCashAmount;
    private String colletionMode;
    private List<ReceiptRegisterdto> listofSumHead;
    private String sumOfChequeAmountIndianCurrency;
    private String sumOfBankAmountIndianCurrency;
    private String sumOfCashAmountIndianCurrency;
    private String cashAmountIndianCurrency;
    private String bankAmountIndianCurrency;
    private String chequeAmountIndianCurrency;
    private String rmAmountIndianCurrency;
    private String sumOfRmAmountindianCurrency;
    private Map<String, String> amountMap = new HashMap<>(0);

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(final Long empId) {
        this.empId = empId;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(final String tranDate) {
        this.tranDate = tranDate;
    }

    private String EmployeeType;

    public String getEmployeeType() {
        return EmployeeType;
    }

    public void setEmployeeType(final String employeeType) {
        EmployeeType = employeeType;
    }

    public long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(final long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getRmRcptno() {
        return rmRcptno;
    }

    public void setRmRcptno(Long rmRcptno) {
        this.rmRcptno = rmRcptno;
    }

    public String getRmReceiptNo() {
		return rmReceiptNo;
	}

	public void setRmReceiptNo(String rmReceiptNo) {
		this.rmReceiptNo = rmReceiptNo;
	}

	public String getRmDate() {
        return rmDate;
    }

    public void setRmDate(String rmDate) {
        this.rmDate = rmDate;
    }

    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    public void setManualReceiptNo(String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public List<ReceiptRegisterdto> getListofbankAmount() {
        return listofbankAmount;
    }

    public void setListofbankAmount(final List<ReceiptRegisterdto> listofbankAmount) {
        this.listofbankAmount = listofbankAmount;
    }

    public String getReceiptHead() {
        return receiptHead;
    }

    public void setReceiptHead(final String receiptHead) {
        this.receiptHead = receiptHead;
    }

    public BigDecimal getRmAmount() {
        return rmAmount;
    }

    public void setRmAmount(BigDecimal rmAmount) {
        this.rmAmount = rmAmount;
    }

    public String getRmReceivedfrom() {
        return rmReceivedfrom;
    }

    public void setRmReceivedfrom(String rmReceivedfrom) {
        this.rmReceivedfrom = rmReceivedfrom;
    }

    public Long getCheqno() {
        return cheqno;
    }

    public void setCheqno(Long cheqno) {
        this.cheqno = cheqno;
    }

    public Long getCbBankidDesc() {
        return cbBankidDesc;
    }

    public void setCbBankidDesc(Long cbBankidDesc) {
        this.cbBankidDesc = cbBankidDesc;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getSumRmAmount() {
        return sumRmAmount;
    }

    public void setSumRmAmount(BigDecimal sumRmAmount) {
        this.sumRmAmount = sumRmAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getBankAmount() {
        return BankAmount;
    }

    public void setBankAmount(BigDecimal bankAmount) {
        BankAmount = bankAmount;
    }

    public BigDecimal getChequeDD() {
        return chequeDD;
    }

    public void setChequeDD(BigDecimal chequeDD) {
        this.chequeDD = chequeDD;
    }

    public BigDecimal getSumBankAmount() {
        return sumBankAmount;
    }

    public void setSumBankAmount(BigDecimal sumBankAmount) {
        this.sumBankAmount = sumBankAmount;
    }

    public BigDecimal getSumChequeDDAmount() {
        return sumChequeDDAmount;
    }

    public void setSumChequeDDAmount(BigDecimal sumChequeDDAmount) {
        this.sumChequeDDAmount = sumChequeDDAmount;
    }

    public BigDecimal getSumCashAmount() {
        return sumCashAmount;
    }

    public void setSumCashAmount(BigDecimal sumCashAmount) {
        this.sumCashAmount = sumCashAmount;
    }

    public String getColletionMode() {
        return colletionMode;
    }

    public void setColletionMode(String colletionMode) {
        this.colletionMode = colletionMode;
    }

    public List<ReceiptRegisterdto> getListofSumHead() {
        return listofSumHead;
    }

    public void setListofSumHead(List<ReceiptRegisterdto> listofSumHead) {
        this.listofSumHead = listofSumHead;
    }

    public String getSumOfChequeAmountIndianCurrency() {
        return sumOfChequeAmountIndianCurrency;
    }

    public void setSumOfChequeAmountIndianCurrency(String sumOfChequeAmountIndianCurrency) {
        this.sumOfChequeAmountIndianCurrency = sumOfChequeAmountIndianCurrency;
    }

    public String getSumOfBankAmountIndianCurrency() {
        return sumOfBankAmountIndianCurrency;
    }

    public void setSumOfBankAmountIndianCurrency(String sumOfBankAmountIndianCurrency) {
        this.sumOfBankAmountIndianCurrency = sumOfBankAmountIndianCurrency;
    }

    public String getSumOfCashAmountIndianCurrency() {
        return sumOfCashAmountIndianCurrency;
    }

    public void setSumOfCashAmountIndianCurrency(String sumOfCashAmountIndianCurrency) {
        this.sumOfCashAmountIndianCurrency = sumOfCashAmountIndianCurrency;
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

    public String getChequeAmountIndianCurrency() {
        return chequeAmountIndianCurrency;
    }

    public void setChequeAmountIndianCurrency(String chequeAmountIndianCurrency) {
        this.chequeAmountIndianCurrency = chequeAmountIndianCurrency;
    }

    public String getRmAmountIndianCurrency() {
        return rmAmountIndianCurrency;
    }

    public void setRmAmountIndianCurrency(String rmAmountIndianCurrency) {
        this.rmAmountIndianCurrency = rmAmountIndianCurrency;
    }

    public String getSumOfRmAmountindianCurrency() {
        return sumOfRmAmountindianCurrency;
    }

    public void setSumOfRmAmountindianCurrency(String sumOfRmAmountindianCurrency) {
        this.sumOfRmAmountindianCurrency = sumOfRmAmountindianCurrency;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Map<String, List<ReceiptRegisterdto>> getListOfTbReceiptRegister() {
        return listOfTbReceiptRegister;
    }

    public void setListOfTbReceiptRegister(Map<String, List<ReceiptRegisterdto>> listOfTbReceiptRegister) {
        this.listOfTbReceiptRegister = listOfTbReceiptRegister;
    }

    public Map<String, String> getAmountMap() {
        return amountMap;
    }

    public void setAmountMap(Map<String, String> amountMap) {
        this.amountMap = amountMap;
    }

}
