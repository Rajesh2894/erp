package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReportDTO implements Serializable {

    private static final long serialVersionUID = -6547575917279727563L;
    private String occupancyType;
    private Date fromDate;
    private Date toDate;
    private String fromDateString;
    private String toDateString;
    private Long reportTypeId;
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
    private String totalAmountInWords;
    private Long receiptNumber;
    private String receiptDate;
    private String nameOfDepositer;
    private String receiptMode;
    private Long chequeNumber;
    private String bankAccountNumber;
    private String dateOfDeposit;
    private String dateOfRealisation;
    private String whetherReturned;
    private String remarks;

    // outstanding report attribute
    private String nameOfLessee;
    private String propertyName;
    private String contractNo;
    private BigDecimal outstandingAmt;

    // Demand Notice report attribute
    private Long noticeTypeId;
    private Long vendorId;
    private String noticeNo;
    private String outstandingDate;
    private Long contractId;
    private String tenantName;
    private String contractDate;
    private String previousFinancialEndDate;
    private String finalTotalAmtInWord;
    private BigDecimal finalTotalAmt;
    private String financialYear;

    // Demand Register
    private BigDecimal arrearsAmt;
    private BigDecimal currentAmt;
    private BigDecimal totalAmt;
    private BigDecimal totalArrearsAmt;
    private BigDecimal totalCurrentAmt;

    // mapping
    Long rmRcptid;
    Long rmRcptno;
    Date rmDate;
    Long cpdFeemode;
    String rmReceivedfrom;
    Long rdChequeddno;
    BigDecimal rfFeeamount;
    String rmNarration;

    private double total;
    private String asOnDate;
    private Long apmApplicationId;
    private BigDecimal bookinFee;
    private BigDecimal securityDeposit;
    private String mobileNoOfLessee;
    
    public ReportDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    // constructor for revenue report
    public ReportDTO(Long rmRcptid, Long rmRcptno, Date rmDate, Long cpdFeemode, String rmReceivedfrom, Long rdChequeddno,
            BigDecimal rfFeeamount, String rmNarration,Long apmApplicationId) {
        super();
        this.rmRcptid = rmRcptid;
        this.rmRcptno = rmRcptno;
        this.rmDate = rmDate;
        this.cpdFeemode = cpdFeemode;
        this.rmReceivedfrom = rmReceivedfrom;
        this.rdChequeddno = rdChequeddno;
        this.rfFeeamount = rfFeeamount;
        this.rmNarration = rmNarration;
        this.apmApplicationId=apmApplicationId;
    }

    // constructor for outstanding report
    public ReportDTO(Long contractId, double total) {
        super();
        this.contractId = contractId;
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOccupancyType() {
        return occupancyType;
    }

    public void setOccupancyType(String occupancyType) {
        this.occupancyType = occupancyType;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getFromDateString() {
        return fromDateString;
    }

    public void setFromDateString(String fromDateString) {
        this.fromDateString = fromDateString;
    }

    public String getToDateString() {
        return toDateString;
    }

    public void setToDateString(String toDateString) {
        this.toDateString = toDateString;
    }

    public Long getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId) {
        this.reportTypeId = reportTypeId;
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

    public String getTotalAmountInWords() {
        return totalAmountInWords;
    }

    public void setTotalAmountInWords(String totalAmountInWords) {
        this.totalAmountInWords = totalAmountInWords;
    }

    public Long getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(Long receiptNumber) {
        this.receiptNumber = receiptNumber;
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

    public String getReceiptMode() {
        return receiptMode;
    }

    public void setReceiptMode(String receiptMode) {
        this.receiptMode = receiptMode;
    }

    public Long getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(Long chequeNumber) {
        this.chequeNumber = chequeNumber;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getNameOfLessee() {
        return nameOfLessee;
    }

    public void setNameOfLessee(String nameOfLessee) {
        this.nameOfLessee = nameOfLessee;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public BigDecimal getOutstandingAmt() {
        return outstandingAmt;
    }

    public void setOutstandingAmt(BigDecimal outstandingAmt) {
        this.outstandingAmt = outstandingAmt;
    }

    public Long getNoticeTypeId() {
        return noticeTypeId;
    }

    public void setNoticeTypeId(Long noticeTypeId) {
        this.noticeTypeId = noticeTypeId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getOutstandingDate() {
        return outstandingDate;
    }

    public void setOutstandingDate(String outstandingDate) {
        this.outstandingDate = outstandingDate;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getPreviousFinancialEndDate() {
        return previousFinancialEndDate;
    }

    public void setPreviousFinancialEndDate(String previousFinancialEndDate) {
        this.previousFinancialEndDate = previousFinancialEndDate;
    }

    public String getFinalTotalAmtInWord() {
        return finalTotalAmtInWord;
    }

    public void setFinalTotalAmtInWord(String finalTotalAmtInWord) {
        this.finalTotalAmtInWord = finalTotalAmtInWord;
    }

    public BigDecimal getFinalTotalAmt() {
        return finalTotalAmt;
    }

    public void setFinalTotalAmt(BigDecimal finalTotalAmt) {
        this.finalTotalAmt = finalTotalAmt;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public BigDecimal getArrearsAmt() {
        return arrearsAmt;
    }

    public void setArrearsAmt(BigDecimal arrearsAmt) {
        this.arrearsAmt = arrearsAmt;
    }

    public BigDecimal getCurrentAmt() {
        return currentAmt;
    }

    public void setCurrentAmt(BigDecimal currentAmt) {
        this.currentAmt = currentAmt;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getTotalArrearsAmt() {
        return totalArrearsAmt;
    }

    public void setTotalArrearsAmt(BigDecimal totalArrearsAmt) {
        this.totalArrearsAmt = totalArrearsAmt;
    }

    public BigDecimal getTotalCurrentAmt() {
        return totalCurrentAmt;
    }

    public void setTotalCurrentAmt(BigDecimal totalCurrentAmt) {
        this.totalCurrentAmt = totalCurrentAmt;
    }

    public Long getCpdFeemode() {
        return cpdFeemode;
    }

    public void setCpdFeemode(Long cpdFeemode) {
        this.cpdFeemode = cpdFeemode;
    }

    public Long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getRmRcptno() {
        return rmRcptno;
    }

    public void setRmRcptno(Long rmRcptno) {
        this.rmRcptno = rmRcptno;
    }

    public Date getRmDate() {
        return rmDate;
    }

    public void setRmDate(Date rmDate) {
        this.rmDate = rmDate;
    }

    public String getRmReceivedfrom() {
        return rmReceivedfrom;
    }

    public void setRmReceivedfrom(String rmReceivedfrom) {
        this.rmReceivedfrom = rmReceivedfrom;
    }

    public Long getRdChequeddno() {
        return rdChequeddno;
    }

    public void setRdChequeddno(Long rdChequeddno) {
        this.rdChequeddno = rdChequeddno;
    }

    public BigDecimal getRfFeeamount() {
        return rfFeeamount;
    }

    public void setRfFeeamount(BigDecimal rfFeeamount) {
        this.rfFeeamount = rfFeeamount;
    }

    public String getRmNarration() {
        return rmNarration;
    }

    public void setRmNarration(String rmNarration) {
        this.rmNarration = rmNarration;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public BigDecimal getBookinFee() {
		return bookinFee;
	}

	public void setBookinFee(BigDecimal bookinFee) {
		this.bookinFee = bookinFee;
	}

	public BigDecimal getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(BigDecimal securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public String getMobileNoOfLessee() {
		return mobileNoOfLessee;
	}

	public void setMobileNoOfLessee(String mobileNoOfLessee) {
		this.mobileNoOfLessee = mobileNoOfLessee;
	}
	
	
}
