package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Vivek.Kumar
 * @since 09-Aug-2017
 */
public class AccountFinancialReportDTO implements Serializable {

	private static final long serialVersionUID = 1212840077375348915L;

	private Long reportTypeId;
	// form input date
	private String transactionDate;
	private String fromDate;
	private String toDate;
	private BigDecimal openingBalance;
	private String voucherDate;
	private String voucherNo;
	private String accountCode;
	private String accountHead;
	private String payerPayee;
	private String particular;
	private BigDecimal drAmount;
	private BigDecimal totalDrAmount;
	private BigDecimal crAmount;
	private BigDecimal totalCrAmount;
	private BigDecimal closingBalance;
	private BigDecimal closingBalanceAsOn;
	private Long accountHeadId;
	// selected reportType
	private String reportType;
	private BigDecimal openingDrAmount;
	private BigDecimal openingCrAmount;
	private BigDecimal closingDrAmount;
	private BigDecimal closingCrAmount;
	private BigDecimal transactionDrAmount;
	private BigDecimal transactionCrAmount;
	private BigDecimal voucherAmount;
	private BigDecimal budgetaryProvision;
	private BigDecimal balanceRecoverable;
	private BigDecimal totalBudgetaryProvision;
	private BigDecimal sumTransactionDR;
	private BigDecimal sumTransactionCR;
	private BigDecimal sumOpeningCR;
	private BigDecimal sumOpeningDR;
	private BigDecimal sumClosingCR;
	private BigDecimal sumClosingDR;
	private BigDecimal budgetAmount;
	private BigDecimal actualAmountReceived;
	private BigDecimal sumBudgetAmount;
	private BigDecimal sumAcutualAmount;
	private BigDecimal sumAcutualAmountPaymentBalance;
	private BigDecimal sumbalanceRecoverable;
	private BigDecimal chequeAmount;
	private BigDecimal sumOfCrAmount;
	private BigDecimal sumOfDrAmount;
	private BigDecimal currentYearAmount;
	private String depositNumber;
	private String depositNarration;
	private BigDecimal depositAmount;
	private String depositRecieptNumber;
	private BigDecimal depositBalance;
	private String typeOfDeposit;
	private BigDecimal budgetCodeId;
	private String originalEst;
	private String revisedEstimation;
	private String actualLastyear;
	private BigDecimal transferAmt;
	private BigDecimal bankChargeAmt;
	private String approvedBy;
	private String remarks;
	private Date paymentDate;
	private String totalOrgEsmtAmt;
	private BigDecimal totaltransferAmts;
	private BigDecimal totalcurrentAmt;
	private BigDecimal totalbankAmts;
	private List<AccountFinancialReportDTO> listOfSum = new ArrayList<>();
	private BigDecimal chequeDeposit;
	private BigDecimal chequeAmounts;
	private BigDecimal chequeDishonoured;
	private BigDecimal subTotalAmount;
	private BigDecimal totalDepositAndDishonoured;
	private BigDecimal subTractionTatal;
	private int index;
	private BigDecimal totalCashInHand;
	private String nxtYrOEstimation;
	private List<AccountFinancialReportDTO> listOfIncome = new ArrayList<>();
	private List<AccountFinancialReportDTO> listOfExpenditure = new ArrayList<>();
	private List<AccountFinancialReportDTO> listOfDeposit = new ArrayList<>();
	private List<AccountFinancialReportDTO> listOfBudgetEstimation = new ArrayList<>();
	private List<AccountFinancialReportDTO> listReAppropriation = new ArrayList<>();
	private List<AccountFinancialReportDTO> listDecReAppropriation = new ArrayList<>();
	private List<AccountFinancialReportDTO> listCashEquvalent = new ArrayList<>();
	private List<AccountFinancialReportDTO> listOfDayBook = new ArrayList<>();
	private List<AccountFinancialReportDTO> listOfExpenditureEstimation = new ArrayList<>();
	private List<AccountFinancialReportDTO> listOfOpeningBalance;
	private List<AccountFinancialReportDTO> listofchequepayment = new ArrayList<>();
	private List<AccountFinancialReportDTO> listofchequeDishonor = new ArrayList<>();
	private List<AccountFinancialReportDTO> listofreceiptdec = new ArrayList<>();
	private List<AccountFinancialReportDTO> listofreceiptinc = new ArrayList<>();
	private List<AccountFinancialReportDTO> bankAccountSummary = new ArrayList<>();
	private List<AccountFinancialReportDTO> assetList = new ArrayList<>();
	private List<AccountFinancialReportDTO> liabilityList = new ArrayList<>();
	private List<AccountFinancialReportDTO> transactionReversal = new ArrayList<>();
	private List<AccountFinancialReportDTO> generalLedgerList = new ArrayList<>();
	private List<AccountFinancialReportDTO> accountReceiavableList = new ArrayList<>();
	Long faYearid;
	private String depositDate;
	private String voucherType;
	private String voucherSubType;
	private String crAmountIndianCurrency;
	private String drAmountIndianCurrency;
	private String depositAmountIndianCurrency;
	private String balanceAmountIndianCurrency;
	private String totalDepositIndianCurrency;
	private String totalBalanceIndianCurrency;
	private String totalCrAmountIndianCurrency;
	private String totalDrAmountIndianCurrency;
	private BigDecimal totalRecoveredDepositAmount;
	private String totalRecoveredDepositAmountIndianCurrency;
	private String recoveredDepositAmountIndianCurrency;
	private BigDecimal recoveredDeposit;
	private String averageamount;
	private String voucherAmountIndianCurrency;
	private String depositBalanceIndianCurrency;
	private String receiptNumber;
	private String receiptDate;
	private String balanceType;
	private String finalizedFlag;
	private String openingBalanceIndianCurrency;
	private String paymentNo;
	private String billNo;
	private String billEntryDate;
	private String vendorName;
	private String chequeNo;
	private String instrumentDate;
	private String paymentAmnt;
	private String issuanceDate;
	private String chequeIssueDate;
	private String paymentDates;
	private String totalPaymentAmnt;
	private String financialYear;
	private String adjustmentForfeitureAmount;
	private String adjustmentForfeitureNo;
	private String totalAdjustmentForfeitureAmount;
	private String ReceivedFrom;
	private String receiptNo;
	private BigDecimal ramt;
	private BigDecimal discharge;
	private String chkDate;
	private String userId;
	private String lmodate;
	private String budgetSactionAmt;
	private String totalbudgetSacAmt;
	private String totalTransAmts;
	private String totalBudSacAmts;
	private String totalDescAmt;
	private String billAmt;
	private String budgetAmountIndianCurrency;
	private String actualAmountReceivedIndianCurrency;
	private String balanceRecoverableIndianCurrency;
	private String sumbalanceRecoverableIndianCurrency;
	private String sumBudgetAmountIndianCurrency;
	private String sumAcutualAmountIndianCurrency;
	private String chequeAmountIndianCurrency;
	private String chequeDepositIndianCurrency;
	private String chequeDishonouredIndianCurrency;
	private String subTotalAmountIndianCurrency;
	private String totalDepositAndDishonouredIndianCurrency;
	private String subTractionTatalIndianCurrency;
	private String totalUtilamt;
	private String totalBalance;
	private BigDecimal previousIncome;
	private BigDecimal priviousExp;
	private BigDecimal previousInTotal;
	private BigDecimal previousExpTotal;
	private String bankname;
	private String bankAcNo;
	private String accountType;
	private String openingBalancers;
	private String receiptAmt;
	private String paymentAmt;
	private String transactionNo;
	private String amount;
	private String narration;
	private String receivePayname;
	private String reversedBy;
	private String reversedDate;
	private String reversalReason;
	private String totalrevenue;
	private String totalCollected;
	private String sumofTotalBalance;
	private String closingAmt;
	private String lastThreeYrAmt;
	private String lastYrAmt;
	private BigDecimal voucherDetailAmt;
	private String branchName;
	private String noFirstLeave;
	private String nolastLeave;
	private String doIssue;
	private String issueToWhom;
	private String signatureOfReceipent;
	private String dateOfReturn;
	private String returnToWhom;
	private String leaveCancelled;
	private Date receipstDate;
	private String transactionTypeId;
	private String totalReceiptAmt;
	private String totalPaymentAmt;
	private String totalClosingAmt;
	private String totalLastThreeYrAmt;
	private String totalLastYrAmt;
	private String rdAmount;
	private String cancellationDate;
	private String newChequeNo;
	private String authorizedBy;
	private String paymodeId;
	private Long registerDepTypeId;
	private String categoryId;
	private String drCrType;
	private String closingCash;
	private boolean flag;
	private String vendorAdd;
	private String vendorPanNo;
	private String orgName;
	private String orgPanNo;
	private String orgAdd;
	private String oldValue;
	private String newValue;
	private String columnName;
	private String accountHeadDesc;
	private String closingBankAmount;

	private BigDecimal previousOpeningCashAmt;
	private BigDecimal previousOpeningBankAmt;
	private BigDecimal previousClosingCashAmt;
	private BigDecimal previousClosingBankAmt;

	// cash flow statement
	List<BigDecimal> amounts = new ArrayList<>();
	private BigDecimal cashBalance;
	private BigDecimal bankBalance;
	List<BigDecimal> prevusAmt = new ArrayList<>();
	private BigDecimal cashBalanceP;
	private BigDecimal bankBalanceP;
	private BigDecimal openblancePreous;
	private String quarter1Amount;
	private String quarter2Amount;
	private String quarter3Amount;
	private String totalBudget;
	private String totalQuater1Amt;
	private String totalQuater2Amt;
	private String totalQuater3Amt;
	private String sumOftotalAmt;
	private BigDecimal percentage;
	private String paymentHeadcode;
	private String paymentNarration;
	private BigDecimal paymentDrAmount;
	private BigDecimal paymentCrAmount;
	private Long receiptvouId;
	private Long paymentvouId;
	private String deptName;
	private String functionName;

	private String prevYramount;
	private String currentYrAmount;
	private String revisedofCurrentYrAmount;
	private String nextYrAmout;
	private String fieldId;
	private BigDecimal receiptDrAmount;
	private BigDecimal receiptCrAmount;
	private BigDecimal sumOfreceiptCrAmount;
	private BigDecimal sumOfreceiptDrAmount;
	private String baBankAcName;
	private String scheduleNo;
	private BigDecimal currAm280;
	private BigDecimal prevAm280;
	private BigDecimal currAm290;
	private BigDecimal prevAm290;
	private BigDecimal currAm312;
	private BigDecimal prevAm312;
	private BigDecimal currAm272;//for INE 272 code value
	private BigDecimal prevAm272;
	private BigDecimal currYrCD;
	private BigDecimal prevYrCD;
	private String indCurrBankBalance;
	private String indCurrClosingBankAmount;
	private String indCurrOpeningBalance;
	private String indCurrClosingBalance;
	private String indCurrPreviousOpeningBankAmt;
	private String indCurrPreviousOpeningCashAmt;
	private String indCurrPreviousClosingBankAmt;
	private String indCurrPreviousClosingCashAmt;
	private String indCurrOpeningDrAmount;
	private String indCurrOpeningCrAmount;
	private String indCurrTransactionCrAmount;
	private String indCurrTransactionDrAmount;
	private String indCurrClosingDrAmount;
	private String indCurrClosingCrAmount;
	private String indCurrSumClosingCR;
	private String indCurrSumClosingDR;
	private String indCurrSumOpeningCR;
	private String indCurrSumOpeningDR;
	private String indCurrSumTransactionCR;
	private String indCurrSumTransactionDR;

	public BigDecimal getPreviousOpeningCashAmt() {
		return previousOpeningCashAmt;
	}

	public void setPreviousOpeningCashAmt(BigDecimal previousOpeningCashAmt) {
		this.previousOpeningCashAmt = previousOpeningCashAmt;
	}

	public BigDecimal getPreviousOpeningBankAmt() {
		return previousOpeningBankAmt;
	}

	public void setPreviousOpeningBankAmt(BigDecimal previousOpeningBankAmt) {
		this.previousOpeningBankAmt = previousOpeningBankAmt;
	}

	public BigDecimal getPreviousClosingCashAmt() {
		return previousClosingCashAmt;
	}

	public void setPreviousClosingCashAmt(BigDecimal previousClosingCashAmt) {
		this.previousClosingCashAmt = previousClosingCashAmt;
	}

	public BigDecimal getPreviousClosingBankAmt() {
		return previousClosingBankAmt;
	}

	public void setPreviousClosingBankAmt(BigDecimal previousClosingBankAmt) {
		this.previousClosingBankAmt = previousClosingBankAmt;
	}

	public String getVendorAdd() {
		return vendorAdd;
	}

	public void setVendorAdd(String vendorAdd) {
		this.vendorAdd = vendorAdd;
	}

	public String getVendorPanNo() {
		return vendorPanNo;
	}

	public void setVendorPanNo(String vendorPanNo) {
		this.vendorPanNo = vendorPanNo;
	}

	public Date getReceipstDate() {
		return receipstDate;
	}

	public void setReceipstDate(Date receipstDate) {
		this.receipstDate = receipstDate;
	}

	private String totalOpeningBalancers;

	public String getTotalOpeningBalancers() {
		return totalOpeningBalancers;
	}

	public String getTotalReceiptAmt() {
		return totalReceiptAmt;
	}

	public String getTotalPaymentAmt() {
		return totalPaymentAmt;
	}

	public String getTotalClosingAmt() {
		return totalClosingAmt;
	}

	public String getTotalLastThreeYrAmt() {
		return totalLastThreeYrAmt;
	}

	public String getTotalLastYrAmt() {
		return totalLastYrAmt;
	}

	public void setTotalOpeningBalancers(String totalOpeningBalancers) {
		this.totalOpeningBalancers = totalOpeningBalancers;
	}

	public void setTotalReceiptAmt(String totalReceiptAmt) {
		this.totalReceiptAmt = totalReceiptAmt;
	}

	public void setTotalPaymentAmt(String totalPaymentAmt) {
		this.totalPaymentAmt = totalPaymentAmt;
	}

	public void setTotalClosingAmt(String totalClosingAmt) {
		this.totalClosingAmt = totalClosingAmt;
	}

	public void setTotalLastThreeYrAmt(String totalLastThreeYrAmt) {
		this.totalLastThreeYrAmt = totalLastThreeYrAmt;
	}

	public void setTotalLastYrAmt(String totalLastYrAmt) {
		this.totalLastYrAmt = totalLastYrAmt;
	}

	public BigDecimal getPreviousInTotal() {
		return previousInTotal;
	}

	public void setPreviousInTotal(BigDecimal previousInTotal) {
		this.previousInTotal = previousInTotal;
	}

	public BigDecimal getPreviousExpTotal() {
		return previousExpTotal;
	}

	public void setPreviousExpTotal(BigDecimal previousExpTotal) {
		this.previousExpTotal = previousExpTotal;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getTotalbudgetSacAmt() {
		return totalbudgetSacAmt;
	}

	public void setTotalbudgetSacAmt(String totalbudgetSacAmt) {
		this.totalbudgetSacAmt = totalbudgetSacAmt;
	}

	public BigDecimal getTotalRecoveredDepositAmount() {
		return totalRecoveredDepositAmount;
	}

	public void setTotalRecoveredDepositAmount(BigDecimal totalRecoveredDepositAmount) {
		this.totalRecoveredDepositAmount = totalRecoveredDepositAmount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<AccountFinancialReportDTO> getListReAppropriation() {
		return listReAppropriation;
	}

	public void setListReAppropriation(List<AccountFinancialReportDTO> listReAppropriation) {
		this.listReAppropriation = listReAppropriation;
	}

	public BigDecimal getBudgetaryProvision() {
		return budgetaryProvision;
	}

	public void setBudgetaryProvision(final BigDecimal budgetaryProvision) {
		this.budgetaryProvision = budgetaryProvision;
	}

	public BigDecimal getBalanceRecoverable() {
		return balanceRecoverable;
	}

	public void setBalanceRecoverable(final BigDecimal balanceRecoverable) {
		this.balanceRecoverable = balanceRecoverable;
	}

	public BigDecimal getOpeningDrAmount() {
		return openingDrAmount;
	}

	public void setOpeningDrAmount(final BigDecimal openingDrAmount) {
		this.openingDrAmount = openingDrAmount;
	}

	public BigDecimal getOpeningCrAmount() {
		return openingCrAmount;
	}

	public void setOpeningCrAmount(final BigDecimal openingCrAmount) {
		this.openingCrAmount = openingCrAmount;
	}

	public BigDecimal getClosingDrAmount() {
		return closingDrAmount;
	}

	public void setClosingDrAmount(final BigDecimal closingDrAmount) {
		this.closingDrAmount = closingDrAmount;
	}

	public BigDecimal getClosingCrAmount() {
		return closingCrAmount;
	}

	public void setClosingCrAmount(final BigDecimal closingCrAmount) {
		this.closingCrAmount = closingCrAmount;
	}

	public BigDecimal getTransactionDrAmount() {
		return transactionDrAmount;
	}

	public void setTransactionDrAmount(final BigDecimal transactionDrAmount) {
		this.transactionDrAmount = transactionDrAmount;
	}

	public BigDecimal getTransactionCrAmount() {
		return transactionCrAmount;
	}

	public void setTransactionCrAmount(final BigDecimal transactionCrAmount) {
		this.transactionCrAmount = transactionCrAmount;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(final String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(final String toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(final BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(final String voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(final String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getAccountHead() {
		return accountHead;
	}

	public void setAccountHead(final String accountHead) {
		this.accountHead = accountHead;
	}

	public String getPayerPayee() {
		return payerPayee;
	}

	public void setPayerPayee(final String payerPayee) {
		this.payerPayee = payerPayee;
	}

	public BigDecimal getDrAmount() {
		return drAmount;
	}

	public void setDrAmount(final BigDecimal drAmount) {
		this.drAmount = drAmount;
	}

	public BigDecimal getCrAmount() {
		return crAmount;
	}

	public void setCrAmount(final BigDecimal crAmount) {
		this.crAmount = crAmount;
	}

	public BigDecimal getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(final BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}

	public BigDecimal getClosingBalanceAsOn() {
		return closingBalanceAsOn;
	}

	public void setClosingBalanceAsOn(final BigDecimal closingBalanceAsOn) {
		this.closingBalanceAsOn = closingBalanceAsOn;
	}

	public Long getReportTypeId() {
		return reportTypeId;
	}

	public void setReportTypeId(final Long reportTypeId) {
		this.reportTypeId = reportTypeId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(final String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(final String accountCode) {
		this.accountCode = accountCode;
	}

	public Long getAccountHeadId() {
		return accountHeadId;
	}

	public void setAccountHeadId(final Long accountHeadId) {
		this.accountHeadId = accountHeadId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(final String reportType) {
		this.reportType = reportType;
	}

	public String getParticular() {
		return particular;
	}

	public void setParticular(final String particular) {
		this.particular = particular;
	}

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(final BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public BigDecimal getTotalCrAmount() {
		return totalCrAmount;
	}

	public void setTotalCrAmount(final BigDecimal totalCrAmount) {
		this.totalCrAmount = totalCrAmount;
	}

	public BigDecimal getTotalBudgetaryProvision() {
		return totalBudgetaryProvision;
	}

	public void setTotalBudgetaryProvision(final BigDecimal totalBudgetaryProvision) {
		this.totalBudgetaryProvision = totalBudgetaryProvision;
	}

	public BigDecimal getTotalDrAmount() {
		return totalDrAmount;
	}

	public void setTotalDrAmount(final BigDecimal totalDrAmount) {
		this.totalDrAmount = totalDrAmount;
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

	public List<AccountFinancialReportDTO> getListOfSum() {
		return listOfSum;
	}

	public void setListOfSum(List<AccountFinancialReportDTO> listOfSum) {
		this.listOfSum = listOfSum;
	}

	public BigDecimal getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(BigDecimal budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public BigDecimal getActualAmountReceived() {
		return actualAmountReceived;
	}

	public void setActualAmountReceived(BigDecimal actualAmountReceived) {
		this.actualAmountReceived = actualAmountReceived;
	}

	public BigDecimal getSumBudgetAmount() {
		return sumBudgetAmount;
	}

	public void setSumBudgetAmount(BigDecimal sumBudgetAmount) {
		this.sumBudgetAmount = sumBudgetAmount;
	}

	public BigDecimal getSumAcutualAmount() {
		return sumAcutualAmount;
	}

	public void setSumAcutualAmount(BigDecimal sumAcutualAmount) {
		this.sumAcutualAmount = sumAcutualAmount;
	}

	public BigDecimal getSumAcutualAmountPaymentBalance() {
		return sumAcutualAmountPaymentBalance;
	}

	public void setSumAcutualAmountPaymentBalance(BigDecimal sumAcutualAmountPaymentBalance) {
		this.sumAcutualAmountPaymentBalance = sumAcutualAmountPaymentBalance;
	}

	public BigDecimal getSumbalanceRecoverable() {
		return sumbalanceRecoverable;
	}

	public void setSumbalanceRecoverable(BigDecimal sumbalanceRecoverable) {
		this.sumbalanceRecoverable = sumbalanceRecoverable;
	}

	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public BigDecimal getSumOfCrAmount() {
		return sumOfCrAmount;
	}

	public void setSumOfCrAmount(BigDecimal sumOfCrAmount) {
		this.sumOfCrAmount = sumOfCrAmount;
	}

	public BigDecimal getSumOfDrAmount() {
		return sumOfDrAmount;
	}

	public void setSumOfDrAmount(BigDecimal sumOfDrAmount) {
		this.sumOfDrAmount = sumOfDrAmount;
	}

	public List<AccountFinancialReportDTO> getListOfIncome() {
		return listOfIncome;
	}

	public void setListOfIncome(List<AccountFinancialReportDTO> listOfIncome) {
		this.listOfIncome = listOfIncome;
	}

	public List<AccountFinancialReportDTO> getListOfExpenditure() {
		return listOfExpenditure;
	}

	public void setListOfExpenditure(List<AccountFinancialReportDTO> listOfExpenditure) {
		this.listOfExpenditure = listOfExpenditure;
	}

	public BigDecimal getCurrentYearAmount() {
		return currentYearAmount;
	}

	public void setCurrentYearAmount(BigDecimal currentYearAmount) {
		this.currentYearAmount = currentYearAmount;
	}

	

	public String getDepositNumber() {
		return depositNumber;
	}

	public void setDepositNumber(String depositNumber) {
		this.depositNumber = depositNumber;
	}

	public String getDepositNarration() {
		return depositNarration;
	}

	public void setDepositNarration(String depositNarration) {
		this.depositNarration = depositNarration;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getDepositRecieptNumber() {
		return depositRecieptNumber;
	}

	public void setDepositRecieptNumber(String depositRecieptNumber) {
		this.depositRecieptNumber = depositRecieptNumber;
	}

	public BigDecimal getDepositBalance() {
		return depositBalance;
	}

	public void setDepositBalance(BigDecimal depositBalance) {
		this.depositBalance = depositBalance;
	}

	public List<AccountFinancialReportDTO> getListOfDeposit() {
		return listOfDeposit;
	}

	public void setListOfDeposit(List<AccountFinancialReportDTO> listOfDeposit) {
		this.listOfDeposit = listOfDeposit;
	}

	public String getTypeOfDeposit() {
		return typeOfDeposit;
	}

	public void setTypeOfDeposit(String typeOfDeposit) {
		this.typeOfDeposit = typeOfDeposit;
	}

	public BigDecimal getBudgetCodeId() {
		return budgetCodeId;
	}

	public void setBudgetCodeId(BigDecimal budgetCodeId) {
		this.budgetCodeId = budgetCodeId;
	}

	public String getActualLastyear() {
		return actualLastyear;
	}

	public void setActualLastyear(String actualLastyear) {
		this.actualLastyear = actualLastyear;
	}

	public List<AccountFinancialReportDTO> getListOfBudgetEstimation() {
		return listOfBudgetEstimation;
	}

	public void setListOfBudgetEstimation(List<AccountFinancialReportDTO> listOfBudgetEstimation) {
		this.listOfBudgetEstimation = listOfBudgetEstimation;
	}

	public BigDecimal getTransferAmt() {
		return transferAmt;
	}

	public void setTransferAmt(BigDecimal transferAmt) {
		this.transferAmt = transferAmt;
	}

	public BigDecimal getBankChargeAmt() {
		return bankChargeAmt;
	}

	public void setBankChargeAmt(BigDecimal bankChargeAmt) {
		this.bankChargeAmt = bankChargeAmt;
	}

	public BigDecimal getTotaltransferAmts() {
		return totaltransferAmts;
	}

	public void setTotaltransferAmts(BigDecimal totaltransferAmts) {
		this.totaltransferAmts = totaltransferAmts;
	}

	public BigDecimal getTotalcurrentAmt() {
		return totalcurrentAmt;
	}

	public void setTotalcurrentAmt(BigDecimal totalcurrentAmt) {
		this.totalcurrentAmt = totalcurrentAmt;
	}

	public BigDecimal getTotalbankAmts() {
		return totalbankAmts;
	}

	public void setTotalbankAmts(BigDecimal totalbankAmts) {
		this.totalbankAmts = totalbankAmts;
	}

	public BigDecimal getChequeDeposit() {
		return chequeDeposit;
	}

	public void setChequeDeposit(BigDecimal chequeDeposit) {
		this.chequeDeposit = chequeDeposit;
	}

	public BigDecimal getChequeAmounts() {
		return chequeAmounts;
	}

	public void setChequeAmounts(BigDecimal chequeAmounts) {
		this.chequeAmounts = chequeAmounts;
	}

	public BigDecimal getChequeDishonoured() {
		return chequeDishonoured;
	}

	public void setChequeDishonoured(BigDecimal chequeDishonoured) {
		this.chequeDishonoured = chequeDishonoured;
	}

	public BigDecimal getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(BigDecimal subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}

	public BigDecimal getTotalDepositAndDishonoured() {
		return totalDepositAndDishonoured;
	}

	public void setTotalDepositAndDishonoured(BigDecimal totalDepositAndDishonoured) {
		this.totalDepositAndDishonoured = totalDepositAndDishonoured;
	}

	public BigDecimal getSubTractionTatal() {
		return subTractionTatal;
	}

	public void setSubTractionTatal(BigDecimal subTractionTatal) {
		this.subTractionTatal = subTractionTatal;
	}

	public Long getFaYearid() {
		return faYearid;
	}

	public void setFaYearid(Long faYearid) {
		this.faYearid = faYearid;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public List<AccountFinancialReportDTO> getListCashEquvalent() {
		return listCashEquvalent;
	}

	public void setListCashEquvalent(List<AccountFinancialReportDTO> listCashEquvalent) {
		this.listCashEquvalent = listCashEquvalent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTotalOrgEsmtAmt() {
		return totalOrgEsmtAmt;
	}

	public void setTotalOrgEsmtAmt(String totalOrgEsmtAmt) {
		this.totalOrgEsmtAmt = totalOrgEsmtAmt;
	}

	public String getOriginalEst() {
		return originalEst;
	}

	public void setOriginalEst(String originalEst) {
		this.originalEst = originalEst;
	}

	public BigDecimal getTotalCashInHand() {
		return totalCashInHand;
	}

	public void setTotalCashInHand(BigDecimal totalCashInHand) {
		this.totalCashInHand = totalCashInHand;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getVoucherSubType() {
		return voucherSubType;
	}

	public void setVoucherSubType(String voucherSubType) {
		this.voucherSubType = voucherSubType;
	}

	public String getCrAmountIndianCurrency() {
		return crAmountIndianCurrency;
	}

	public void setCrAmountIndianCurrency(String crAmountIndianCurrency) {
		this.crAmountIndianCurrency = crAmountIndianCurrency;
	}

	public String getDrAmountIndianCurrency() {
		return drAmountIndianCurrency;
	}

	public void setDrAmountIndianCurrency(String drAmountIndianCurrency) {
		this.drAmountIndianCurrency = drAmountIndianCurrency;
	}

	public List<AccountFinancialReportDTO> getListOfDayBook() {
		return listOfDayBook;
	}

	public void setListOfDayBook(List<AccountFinancialReportDTO> listOfDayBook) {
		this.listOfDayBook = listOfDayBook;
	}

	public String getDepositAmountIndianCurrency() {
		return depositAmountIndianCurrency;
	}

	public void setDepositAmountIndianCurrency(String depositAmountIndianCurrency) {
		this.depositAmountIndianCurrency = depositAmountIndianCurrency;
	}

	public String getBalanceAmountIndianCurrency() {
		return balanceAmountIndianCurrency;
	}

	public void setBalanceAmountIndianCurrency(String balanceAmountIndianCurrency) {
		this.balanceAmountIndianCurrency = balanceAmountIndianCurrency;
	}

	public String getTotalDepositIndianCurrency() {
		return totalDepositIndianCurrency;
	}

	public void setTotalDepositIndianCurrency(String totalDepositIndianCurrency) {
		this.totalDepositIndianCurrency = totalDepositIndianCurrency;
	}

	public String getTotalBalanceIndianCurrency() {
		return totalBalanceIndianCurrency;
	}

	public void setTotalBalanceIndianCurrency(String totalBalanceIndianCurrency) {
		this.totalBalanceIndianCurrency = totalBalanceIndianCurrency;
	}

	public String getTotalCrAmountIndianCurrency() {
		return totalCrAmountIndianCurrency;
	}

	public void setTotalCrAmountIndianCurrency(String totalCrAmountIndianCurrency) {
		this.totalCrAmountIndianCurrency = totalCrAmountIndianCurrency;
	}

	public String getTotalDrAmountIndianCurrency() {
		return totalDrAmountIndianCurrency;
	}

	public void setTotalDrAmountIndianCurrency(String totalDrAmountIndianCurrency) {
		this.totalDrAmountIndianCurrency = totalDrAmountIndianCurrency;
	}

	public String getTotalRecoveredDepositAmountIndianCurrency() {
		return totalRecoveredDepositAmountIndianCurrency;
	}

	public void setTotalRecoveredDepositAmountIndianCurrency(String totalRecoveredDepositAmountIndianCurrency) {
		this.totalRecoveredDepositAmountIndianCurrency = totalRecoveredDepositAmountIndianCurrency;
	}

	public String getRecoveredDepositAmountIndianCurrency() {
		return recoveredDepositAmountIndianCurrency;
	}

	public void setRecoveredDepositAmountIndianCurrency(String recoveredDepositAmountIndianCurrency) {
		this.recoveredDepositAmountIndianCurrency = recoveredDepositAmountIndianCurrency;
	}

	public BigDecimal getRecoveredDeposit() {
		return recoveredDeposit;
	}

	public void setRecoveredDeposit(BigDecimal recoveredDeposit) {
		this.recoveredDeposit = recoveredDeposit;
	}

	public String getVoucherAmountIndianCurrency() {
		return voucherAmountIndianCurrency;
	}

	public void setVoucherAmountIndianCurrency(String voucherAmountIndianCurrency) {
		this.voucherAmountIndianCurrency = voucherAmountIndianCurrency;
	}

	public String getDepositBalanceIndianCurrency() {
		return depositBalanceIndianCurrency;
	}

	public void setDepositBalanceIndianCurrency(String depositBalanceIndianCurrency) {
		this.depositBalanceIndianCurrency = depositBalanceIndianCurrency;
	}

	public String getAverageamount() {
		return averageamount;
	}

	public void setAverageamount(String averageamount) {
		this.averageamount = averageamount;
	}

	public List<AccountFinancialReportDTO> getListOfExpenditureEstimation() {
		return listOfExpenditureEstimation;
	}

	public void setListOfExpenditureEstimation(List<AccountFinancialReportDTO> listOfExpenditureEstimation) {
		this.listOfExpenditureEstimation = listOfExpenditureEstimation;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public String getFinalizedFlag() {
		return finalizedFlag;
	}

	public void setFinalizedFlag(String finalizedFlag) {
		this.finalizedFlag = finalizedFlag;
	}

	public List<AccountFinancialReportDTO> getListOfOpeningBalance() {
		return listOfOpeningBalance;
	}

	public void setListOfOpeningBalance(List<AccountFinancialReportDTO> listOfOpeningBalance) {
		this.listOfOpeningBalance = listOfOpeningBalance;
	}

	public String getOpeningBalanceIndianCurrency() {
		return openingBalanceIndianCurrency;
	}

	public void setOpeningBalanceIndianCurrency(String openingBalanceIndianCurrency) {
		this.openingBalanceIndianCurrency = openingBalanceIndianCurrency;
	}

	public List<AccountFinancialReportDTO> getListofchequepayment() {
		return listofchequepayment;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillEntryDate() {
		return billEntryDate;
	}

	public void setBillEntryDate(String billEntryDate) {
		this.billEntryDate = billEntryDate;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getInstrumentDate() {
		return instrumentDate;
	}

	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}

	public String getPaymentAmnt() {
		return paymentAmnt;
	}

	public void setPaymentAmnt(String paymentAmnt) {
		this.paymentAmnt = paymentAmnt;
	}

	public String getIssuanceDate() {
		return issuanceDate;
	}

	public void setIssuanceDate(String issuanceDate) {
		this.issuanceDate = issuanceDate;
	}

	public String getChequeIssueDate() {
		return chequeIssueDate;
	}

	public void setChequeIssueDate(String chequeIssueDate) {
		this.chequeIssueDate = chequeIssueDate;
	}

	public void setListofchequepayment(List<AccountFinancialReportDTO> listofchequepayment) {
		this.listofchequepayment = listofchequepayment;
	}

	public String getPaymentDates() {
		return paymentDates;
	}

	public void setPaymentDates(String paymentDates) {
		this.paymentDates = paymentDates;
	}

	public String getTotalPaymentAmnt() {
		return totalPaymentAmnt;
	}

	public void setTotalPaymentAmnt(String totalPaymentAmnt) {
		this.totalPaymentAmnt = totalPaymentAmnt;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getAdjustmentForfeitureAmount() {
		return adjustmentForfeitureAmount;
	}

	public void setAdjustmentForfeitureAmount(String adjustmentForfeitureAmount) {
		this.adjustmentForfeitureAmount = adjustmentForfeitureAmount;
	}

	public String getAdjustmentForfeitureNo() {
		return adjustmentForfeitureNo;
	}

	public void setAdjustmentForfeitureNo(String adjustmentForfeitureNo) {
		this.adjustmentForfeitureNo = adjustmentForfeitureNo;
	}

	public String getTotalAdjustmentForfeitureAmount() {
		return totalAdjustmentForfeitureAmount;
	}

	public void setTotalAdjustmentForfeitureAmount(String totalAdjustmentForfeitureAmount) {
		this.totalAdjustmentForfeitureAmount = totalAdjustmentForfeitureAmount;
	}

	public String getReceivedFrom() {
		return ReceivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		ReceivedFrom = receivedFrom;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public BigDecimal getRamt() {
		return ramt;
	}

	public void setRamt(BigDecimal ramt) {
		this.ramt = ramt;
	}

	public BigDecimal getDischarge() {
		return discharge;
	}

	public void setDischarge(BigDecimal discharge) {
		this.discharge = discharge;
	}

	public String getChkDate() {
		return chkDate;
	}

	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}

	public List<AccountFinancialReportDTO> getListofchequeDishonor() {
		return listofchequeDishonor;
	}

	public void setListofchequeDishonor(List<AccountFinancialReportDTO> listofchequeDishonor) {
		this.listofchequeDishonor = listofchequeDishonor;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLmodate() {
		return lmodate;
	}

	public void setLmodate(String lmodate) {
		this.lmodate = lmodate;
	}

	public String getBudgetSactionAmt() {
		return budgetSactionAmt;
	}

	public void setBudgetSactionAmt(String budgetSactionAmt) {
		this.budgetSactionAmt = budgetSactionAmt;
	}

	public List<AccountFinancialReportDTO> getListDecReAppropriation() {
		return listDecReAppropriation;
	}

	public void setListDecReAppropriation(List<AccountFinancialReportDTO> listDecReAppropriation) {
		this.listDecReAppropriation = listDecReAppropriation;
	}

	public String getTotalBudSacAmts() {
		return totalBudSacAmts;
	}

	public void setTotalBudSacAmts(String totalBudSacAmts) {
		this.totalBudSacAmts = totalBudSacAmts;
	}

	public String getTotalTransAmts() {
		return totalTransAmts;
	}

	public void setTotalTransAmts(String totalTransAmts) {
		this.totalTransAmts = totalTransAmts;
	}

	public String getTotalDescAmt() {
		return totalDescAmt;
	}

	public void setTotalDescAmt(String totalDescAmt) {
		this.totalDescAmt = totalDescAmt;
	}

	public String getBudgetAmountIndianCurrency() {
		return budgetAmountIndianCurrency;
	}

	public void setBudgetAmountIndianCurrency(String budgetAmountIndianCurrency) {
		this.budgetAmountIndianCurrency = budgetAmountIndianCurrency;
	}

	public String getActualAmountReceivedIndianCurrency() {
		return actualAmountReceivedIndianCurrency;
	}

	public void setActualAmountReceivedIndianCurrency(String actualAmountReceivedIndianCurrency) {
		this.actualAmountReceivedIndianCurrency = actualAmountReceivedIndianCurrency;
	}

	public String getBalanceRecoverableIndianCurrency() {
		return balanceRecoverableIndianCurrency;
	}

	public void setBalanceRecoverableIndianCurrency(String balanceRecoverableIndianCurrency) {
		this.balanceRecoverableIndianCurrency = balanceRecoverableIndianCurrency;
	}

	public String getSumbalanceRecoverableIndianCurrency() {
		return sumbalanceRecoverableIndianCurrency;
	}

	public void setSumbalanceRecoverableIndianCurrency(String sumbalanceRecoverableIndianCurrency) {
		this.sumbalanceRecoverableIndianCurrency = sumbalanceRecoverableIndianCurrency;
	}

	public String getSumBudgetAmountIndianCurrency() {
		return sumBudgetAmountIndianCurrency;
	}

	public void setSumBudgetAmountIndianCurrency(String sumBudgetAmountIndianCurrency) {
		this.sumBudgetAmountIndianCurrency = sumBudgetAmountIndianCurrency;
	}

	public String getSumAcutualAmountIndianCurrency() {
		return sumAcutualAmountIndianCurrency;
	}

	public void setSumAcutualAmountIndianCurrency(String sumAcutualAmountIndianCurrency) {
		this.sumAcutualAmountIndianCurrency = sumAcutualAmountIndianCurrency;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getChequeAmountIndianCurrency() {
		return chequeAmountIndianCurrency;
	}

	public void setChequeAmountIndianCurrency(String chequeAmountIndianCurrency) {
		this.chequeAmountIndianCurrency = chequeAmountIndianCurrency;
	}

	public String getChequeDepositIndianCurrency() {
		return chequeDepositIndianCurrency;
	}

	public void setChequeDepositIndianCurrency(String chequeDepositIndianCurrency) {
		this.chequeDepositIndianCurrency = chequeDepositIndianCurrency;
	}

	public String getChequeDishonouredIndianCurrency() {
		return chequeDishonouredIndianCurrency;
	}

	public void setChequeDishonouredIndianCurrency(String chequeDishonouredIndianCurrency) {
		this.chequeDishonouredIndianCurrency = chequeDishonouredIndianCurrency;
	}

	public String getSubTotalAmountIndianCurrency() {
		return subTotalAmountIndianCurrency;
	}

	public void setSubTotalAmountIndianCurrency(String subTotalAmountIndianCurrency) {
		this.subTotalAmountIndianCurrency = subTotalAmountIndianCurrency;
	}

	public String getTotalDepositAndDishonouredIndianCurrency() {
		return totalDepositAndDishonouredIndianCurrency;
	}

	public void setTotalDepositAndDishonouredIndianCurrency(String totalDepositAndDishonouredIndianCurrency) {
		this.totalDepositAndDishonouredIndianCurrency = totalDepositAndDishonouredIndianCurrency;
	}

	public String getSubTractionTatalIndianCurrency() {
		return subTractionTatalIndianCurrency;
	}

	public void setSubTractionTatalIndianCurrency(String subTractionTatalIndianCurrency) {
		this.subTractionTatalIndianCurrency = subTractionTatalIndianCurrency;
	}

	public String getBillAmt() {
		return billAmt;
	}

	public void setBillAmt(String billAmt) {
		this.billAmt = billAmt;
	}

	public String getTotalUtilamt() {
		return totalUtilamt;
	}

	public void setTotalUtilamt(String totalUtilamt) {
		this.totalUtilamt = totalUtilamt;
	}

	public BigDecimal getPreviousIncome() {
		return previousIncome;
	}

	public void setPreviousIncome(BigDecimal previousIncome) {
		this.previousIncome = previousIncome;
	}

	public BigDecimal getPriviousExp() {
		return priviousExp;
	}

	public void setPriviousExp(BigDecimal priviousExp) {
		this.priviousExp = priviousExp;
	}

	public String getLastThreeYrAmt() {
		return lastThreeYrAmt;
	}

	public String getLastYrAmt() {
		return lastYrAmt;
	}

	public void setLastThreeYrAmt(String lastThreeYrAmt) {
		this.lastThreeYrAmt = lastThreeYrAmt;
	}

	public void setLastYrAmt(String lastYrAmt) {
		this.lastYrAmt = lastYrAmt;
	}

	public List<AccountFinancialReportDTO> getListofreceiptdec() {
		return listofreceiptdec;
	}

	public List<AccountFinancialReportDTO> getListofreceiptinc() {
		return listofreceiptinc;
	}

	public void setListofreceiptdec(List<AccountFinancialReportDTO> listofreceiptdec) {
		this.listofreceiptdec = listofreceiptdec;
	}

	public void setListofreceiptinc(List<AccountFinancialReportDTO> listofreceiptinc) {
		this.listofreceiptinc = listofreceiptinc;
	}

	public String getBankname() {
		return bankname;
	}

	public String getBankAcNo() {
		return bankAcNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getOpeningBalancers() {
		return openingBalancers;
	}

	public String getReceiptAmt() {
		return receiptAmt;
	}

	public String getPaymentAmt() {
		return paymentAmt;
	}

	public String getClosingAmt() {
		return closingAmt;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setOpeningBalancers(String openingBalancers) {
		this.openingBalancers = openingBalancers;
	}

	public void setReceiptAmt(String receiptAmt) {
		this.receiptAmt = receiptAmt;
	}

	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public void setClosingAmt(String closingAmt) {
		this.closingAmt = closingAmt;
	}

	public List<AccountFinancialReportDTO> getBankAccountSummary() {
		return bankAccountSummary;
	}

	public void setBankAccountSummary(List<AccountFinancialReportDTO> bankAccountSummary) {
		this.bankAccountSummary = bankAccountSummary;
	}

	public List<AccountFinancialReportDTO> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<AccountFinancialReportDTO> assetList) {
		this.assetList = assetList;
	}

	public List<AccountFinancialReportDTO> getLiabilityList() {
		return liabilityList;
	}

	public void setLiabilityList(List<AccountFinancialReportDTO> liabilityList) {
		this.liabilityList = liabilityList;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getNoFirstLeave() {
		return noFirstLeave;
	}

	public String getNolastLeave() {
		return nolastLeave;
	}

	public String getDoIssue() {
		return doIssue;
	}

	public String getIssueToWhom() {
		return issueToWhom;
	}

	public String getSignatureOfReceipent() {
		return signatureOfReceipent;
	}

	public String getDateOfReturn() {
		return dateOfReturn;
	}

	public String getReturnToWhom() {
		return returnToWhom;
	}

	public String getLeaveCancelled() {
		return leaveCancelled;
	}

	public void setNoFirstLeave(String noFirstLeave) {
		this.noFirstLeave = noFirstLeave;
	}

	public void setNolastLeave(String nolastLeave) {
		this.nolastLeave = nolastLeave;
	}

	public void setDoIssue(String doIssue) {
		this.doIssue = doIssue;
	}

	public void setIssueToWhom(String issueToWhom) {
		this.issueToWhom = issueToWhom;
	}

	public void setSignatureOfReceipent(String signatureOfReceipent) {
		this.signatureOfReceipent = signatureOfReceipent;
	}

	public void setDateOfReturn(String dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}

	public void setReturnToWhom(String returnToWhom) {
		this.returnToWhom = returnToWhom;
	}

	public void setLeaveCancelled(String leaveCancelled) {
		this.leaveCancelled = leaveCancelled;
	}

	public String getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(String transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public String getAmount() {
		return amount;
	}

	public String getNarration() {
		return narration;
	}

	public String getReceivePayname() {
		return receivePayname;
	}

	public String getReversedBy() {
		return reversedBy;
	}

	public String getReversedDate() {
		return reversedDate;
	}

	public String getReversalReason() {
		return reversalReason;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public void setReceivePayname(String receivePayname) {
		this.receivePayname = receivePayname;
	}

	public void setReversedBy(String reversedBy) {
		this.reversedBy = reversedBy;
	}

	public void setReversedDate(String reversedDate) {
		this.reversedDate = reversedDate;
	}

	public void setReversalReason(String reversalReason) {
		this.reversalReason = reversalReason;
	}

	public List<AccountFinancialReportDTO> getTransactionReversal() {
		return transactionReversal;
	}

	public void setTransactionReversal(List<AccountFinancialReportDTO> transactionReversal) {
		this.transactionReversal = transactionReversal;
	}

	public String getTotalrevenue() {
		return totalrevenue;
	}

	public String getTotalCollected() {
		return totalCollected;
	}

	public String getSumofTotalBalance() {
		return sumofTotalBalance;
	}

	public void setTotalrevenue(String totalrevenue) {
		this.totalrevenue = totalrevenue;
	}

	public void setTotalCollected(String totalCollected) {
		this.totalCollected = totalCollected;
	}

	public void setSumofTotalBalance(String sumofTotalBalance) {
		this.sumofTotalBalance = sumofTotalBalance;
	}

	public BigDecimal getVoucherDetailAmt() {
		return voucherDetailAmt;
	}

	public void setVoucherDetailAmt(BigDecimal voucherDetailAmt) {
		this.voucherDetailAmt = voucherDetailAmt;
	}

	public String getRdAmount() {
		return rdAmount;
	}

	public void setRdAmount(String rdAmount) {
		this.rdAmount = rdAmount;
	}

	public String getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(String cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public String getNewChequeNo() {
		return newChequeNo;
	}

	public void setNewChequeNo(String newChequeNo) {
		this.newChequeNo = newChequeNo;
	}

	public String getAuthorizedBy() {
		return authorizedBy;
	}

	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}

	public Long getRegisterDepTypeId() {
		return registerDepTypeId;
	}

	public void setRegisterDepTypeId(Long registerDepTypeId) {
		this.registerDepTypeId = registerDepTypeId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getPaymodeId() {
		return paymodeId;
	}

	public void setPaymodeId(String paymodeId) {
		this.paymodeId = paymodeId;
	}

	public String getDrCrType() {
		return drCrType;
	}

	public void setDrCrType(String drCrType) {
		this.drCrType = drCrType;
	}

	public String getClosingCash() {
		return closingCash;
	}

	public void setClosingCash(String closingCash) {
		this.closingCash = closingCash;
	}

	public List<AccountFinancialReportDTO> getGeneralLedgerList() {
		return generalLedgerList;
	}

	public void setGeneralLedgerList(List<AccountFinancialReportDTO> generalLedgerList) {
		this.generalLedgerList = generalLedgerList;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgPanNo() {
		return orgPanNo;
	}

	public void setOrgPanNo(String orgPanNo) {
		this.orgPanNo = orgPanNo;
	}

	public String getOrgAdd() {
		return orgAdd;
	}

	public void setOrgAdd(String orgAdd) {
		this.orgAdd = orgAdd;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getRevisedEstimation() {
		return revisedEstimation;
	}

	public void setRevisedEstimation(String revisedEstimation) {
		this.revisedEstimation = revisedEstimation;
	}

	public String getNxtYrOEstimation() {
		return nxtYrOEstimation;
	}

	public void setNxtYrOEstimation(String nxtYrOEstimation) {
		this.nxtYrOEstimation = nxtYrOEstimation;
	}

	public String getAccountHeadDesc() {
		return accountHeadDesc;
	}

	public void setAccountHeadDesc(String accountHeadDesc) {
		this.accountHeadDesc = accountHeadDesc;
	}

	public List<BigDecimal> getAmounts() {
		return amounts;
	}

	public void setAmounts(List<BigDecimal> amounts) {
		this.amounts = amounts;
	}

	public BigDecimal getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}

	public BigDecimal getBankBalance() {
		return bankBalance;
	}

	public void setBankBalance(BigDecimal bankBalance) {
		this.bankBalance = bankBalance;
	}

	public List<BigDecimal> getPrevusAmt() {
		return prevusAmt;
	}

	public void setPrevusAmt(List<BigDecimal> prevusAmt) {
		this.prevusAmt = prevusAmt;
	}

	public BigDecimal getCashBalanceP() {
		return cashBalanceP;
	}

	public void setCashBalanceP(BigDecimal cashBalanceP) {
		this.cashBalanceP = cashBalanceP;
	}

	public BigDecimal getBankBalanceP() {
		return bankBalanceP;
	}

	public void setBankBalanceP(BigDecimal bankBalanceP) {
		this.bankBalanceP = bankBalanceP;
	}

	public BigDecimal getOpenblancePreous() {
		return openblancePreous;
	}

	public void setOpenblancePreous(BigDecimal openblancePreous) {
		this.openblancePreous = openblancePreous;
	}

	public String getClosingBankAmount() {
		return closingBankAmount;
	}

	public void setClosingBankAmount(String closingBankAmount) {
		this.closingBankAmount = closingBankAmount;
	}

	public String getQuarter1Amount() {
		return quarter1Amount;
	}

	public void setQuarter1Amount(String quarter1Amount) {
		this.quarter1Amount = quarter1Amount;
	}

	public String getQuarter2Amount() {
		return quarter2Amount;
	}

	public void setQuarter2Amount(String quarter2Amount) {
		this.quarter2Amount = quarter2Amount;
	}

	public String getQuarter3Amount() {
		return quarter3Amount;
	}

	public void setQuarter3Amount(String quarter3Amount) {
		this.quarter3Amount = quarter3Amount;
	}

	public String getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(String totalBudget) {
		this.totalBudget = totalBudget;
	}

	public String getTotalQuater1Amt() {
		return totalQuater1Amt;
	}

	public void setTotalQuater1Amt(String totalQuater1Amt) {
		this.totalQuater1Amt = totalQuater1Amt;
	}

	public String getTotalQuater2Amt() {
		return totalQuater2Amt;
	}

	public void setTotalQuater2Amt(String totalQuater2Amt) {
		this.totalQuater2Amt = totalQuater2Amt;
	}

	public String getTotalQuater3Amt() {
		return totalQuater3Amt;
	}

	public void setTotalQuater3Amt(String totalQuater3Amt) {
		this.totalQuater3Amt = totalQuater3Amt;
	}

	public String getSumOftotalAmt() {
		return sumOftotalAmt;
	}

	public void setSumOftotalAmt(String sumOftotalAmt) {
		this.sumOftotalAmt = sumOftotalAmt;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public String getPaymentHeadcode() {
		return paymentHeadcode;
	}

	public void setPaymentHeadcode(String paymentHeadcode) {
		this.paymentHeadcode = paymentHeadcode;
	}

	public String getPaymentNarration() {
		return paymentNarration;
	}

	public void setPaymentNarration(String paymentNarration) {
		this.paymentNarration = paymentNarration;
	}

	public BigDecimal getPaymentDrAmount() {
		return paymentDrAmount;
	}

	public void setPaymentDrAmount(BigDecimal paymentDrAmount) {
		this.paymentDrAmount = paymentDrAmount;
	}

	public BigDecimal getPaymentCrAmount() {
		return paymentCrAmount;
	}

	public void setPaymentCrAmount(BigDecimal paymentCrAmount) {
		this.paymentCrAmount = paymentCrAmount;
	}

	public Long getReceiptvouId() {
		return receiptvouId;
	}

	public void setReceiptvouId(Long receiptvouId) {
		this.receiptvouId = receiptvouId;
	}

	public Long getPaymentvouId() {
		return paymentvouId;
	}

	public void setPaymentvouId(Long paymentvouId) {
		this.paymentvouId = paymentvouId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public BigDecimal getCurrAm312() {
		return currAm312;
	}

	public void setCurrAm312(BigDecimal currAm312) {
		this.currAm312 = currAm312;
	}

	public BigDecimal getPrevAm312() {
		return prevAm312;
	}

	public void setPrevAm312(BigDecimal prevAm312) {
		this.prevAm312 = prevAm312;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getPrevYramount() {
		return prevYramount;
	}

	public void setPrevYramount(String prevYramount) {
		this.prevYramount = prevYramount;
	}

	public String getCurrentYrAmount() {
		return currentYrAmount;
	}

	public void setCurrentYrAmount(String currentYrAmount) {
		this.currentYrAmount = currentYrAmount;
	}

	public String getRevisedofCurrentYrAmount() {
		return revisedofCurrentYrAmount;
	}

	public void setRevisedofCurrentYrAmount(String revisedofCurrentYrAmount) {
		this.revisedofCurrentYrAmount = revisedofCurrentYrAmount;
	}

	public String getNextYrAmout() {
		return nextYrAmout;
	}

	public void setNextYrAmout(String nextYrAmout) {
		this.nextYrAmout = nextYrAmout;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public List<AccountFinancialReportDTO> getAccountReceiavableList() {
		return accountReceiavableList;
	}

	public void setAccountReceiavableList(List<AccountFinancialReportDTO> accountReceiavableList) {
		this.accountReceiavableList = accountReceiavableList;
	}

	public BigDecimal getReceiptDrAmount() {
		return receiptDrAmount;
	}

	public void setReceiptDrAmount(BigDecimal receiptDrAmount) {
		this.receiptDrAmount = receiptDrAmount;
	}

	public BigDecimal getReceiptCrAmount() {
		return receiptCrAmount;
	}

	public void setReceiptCrAmount(BigDecimal receiptCrAmount) {
		this.receiptCrAmount = receiptCrAmount;
	}

	public BigDecimal getSumOfreceiptCrAmount() {
		return sumOfreceiptCrAmount;
	}

	public void setSumOfreceiptCrAmount(BigDecimal sumOfreceiptCrAmount) {
		this.sumOfreceiptCrAmount = sumOfreceiptCrAmount;
	}

	public BigDecimal getSumOfreceiptDrAmount() {
		return sumOfreceiptDrAmount;
	}

	public void setSumOfreceiptDrAmount(BigDecimal sumOfreceiptDrAmount) {
		this.sumOfreceiptDrAmount = sumOfreceiptDrAmount;
	}

	public String getBaBankAcName() {
		return baBankAcName;
	}

	public void setBaBankAcName(String baBankAcName) {
		this.baBankAcName = baBankAcName;
	}

	public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public BigDecimal getCurrAm280() {
		return currAm280;
	}

	public void setCurrAm280(BigDecimal currAm280) {
		this.currAm280 = currAm280;
	}

	public BigDecimal getPrevAm280() {
		return prevAm280;
	}

	public void setPrevAm280(BigDecimal prevAm280) {
		this.prevAm280 = prevAm280;
	}

	public BigDecimal getCurrAm290() {
		return currAm290;
	}

	public void setCurrAm290(BigDecimal currAm290) {
		this.currAm290 = currAm290;
	}

	public BigDecimal getPrevAm290() {
		return prevAm290;
	}

	public void setPrevAm290(BigDecimal prevAm290) {
		this.prevAm290 = prevAm290;
	}

	public BigDecimal getCurrYrCD() {
		return currYrCD;
	}

	public void setCurrYrCD(BigDecimal currYrCD) {
		this.currYrCD = currYrCD;
	}

	public BigDecimal getPrevYrCD() {
		return prevYrCD;
	}

	public void setPrevYrCD(BigDecimal prevYrCD) {
		this.prevYrCD = prevYrCD;
	}

	public String getIndCurrBankBalance() {
		return indCurrBankBalance;
	}

	public void setIndCurrBankBalance(String indCurrBankBalance) {
		this.indCurrBankBalance = indCurrBankBalance;
	}

	public String getIndCurrClosingBankAmount() {
		return indCurrClosingBankAmount;
	}

	public void setIndCurrClosingBankAmount(String indCurrClosingBankAmount) {
		this.indCurrClosingBankAmount = indCurrClosingBankAmount;
	}

	public String getIndCurrOpeningBalance() {
		return indCurrOpeningBalance;
	}

	public void setIndCurrOpeningBalance(String indCurrOpeningBalance) {
		this.indCurrOpeningBalance = indCurrOpeningBalance;
	}

	public String getIndClosingBalance() {
		return indCurrClosingBalance;
	}

	public void setIndClosingBalance(String indCurrClosingBalance) {
		this.indCurrClosingBalance = indCurrClosingBalance;
	}

	public String getIndCurrPreviousOpeningBankAmt() {
		return indCurrPreviousOpeningBankAmt;
	}

	public void setIndCurrPreviousOpeningBankAmt(String indCurrPreviousOpeningBankAmt) {
		this.indCurrPreviousOpeningBankAmt = indCurrPreviousOpeningBankAmt;
	}

	public String getIndCurrPreviousOpeningCashAmt() {
		return indCurrPreviousOpeningCashAmt;
	}

	public void setIndCurrPreviousOpeningCashAmt(String indCurrPreviousOpeningCashAmt) {
		this.indCurrPreviousOpeningCashAmt = indCurrPreviousOpeningCashAmt;
	}

	public String getIndCurrPreviousClosingBankAmt() {
		return indCurrPreviousClosingBankAmt;
	}

	public void setIndCurrPreviousClosingBankAmt(String indCurrPreviousClosingBankAmt) {
		this.indCurrPreviousClosingBankAmt = indCurrPreviousClosingBankAmt;
	}

	public String getIndCurrPreviousClosingCashAmt() {
		return indCurrPreviousClosingCashAmt;
	}

	public void setIndCurrPreviousClosingCashAmt(String indCurrPreviousClosingCashAmt) {
		this.indCurrPreviousClosingCashAmt = indCurrPreviousClosingCashAmt;
	}
	public String getIndCurrClosingBalance() {
		return indCurrClosingBalance;
	}

	public void setIndCurrClosingBalance(String indCurrClosingBalance) {
		this.indCurrClosingBalance = indCurrClosingBalance;
	}
	public String getIndCurrOpeningDrAmount() {
		return indCurrOpeningDrAmount;
	}

	public void setIndCurrOpeningDrAmount(String indCurrOpeningDrAmount) {
		this.indCurrOpeningDrAmount = indCurrOpeningDrAmount;
	}

	public String getIndCurrOpeningCrAmount() {
		return indCurrOpeningCrAmount;
	}

	public void setIndCurrOpeningCrAmount(String indCurrOpeningCrAmount) {
		this.indCurrOpeningCrAmount = indCurrOpeningCrAmount;
	}

	public String getIndCurrTransactionCrAmount() {
		return indCurrTransactionCrAmount;
	}

	public void setIndCurrTransactionCrAmount(String indCurrTransactionCrAmount) {
		this.indCurrTransactionCrAmount = indCurrTransactionCrAmount;
	}

	public String getIndCurrTransactionDrAmount() {
		return indCurrTransactionDrAmount;
	}

	public void setIndCurrTransactionDrAmount(String indCurrTransactionDrAmount) {
		this.indCurrTransactionDrAmount = indCurrTransactionDrAmount;
	}

	public String getIndCurrClosingDrAmount() {
		return indCurrClosingDrAmount;
	}

	public void setIndCurrClosingDrAmount(String indCurrClosingDrAmount) {
		this.indCurrClosingDrAmount = indCurrClosingDrAmount;
	}

	public String getIndCurrClosingCrAmount() {
		return indCurrClosingCrAmount;
	}

	public void setIndCurrClosingCrAmount(String indCurrClosingCrAmount) {
		this.indCurrClosingCrAmount = indCurrClosingCrAmount;
	}

	public String getIndCurrSumClosingCR() {
		return indCurrSumClosingCR;
	}

	public void setIndCurrSumClosingCR(String indCurrSumClosingCR) {
		this.indCurrSumClosingCR = indCurrSumClosingCR;
	}

	public String getIndCurrSumClosingDR() {
		return indCurrSumClosingDR;
	}

	public void setIndCurrSumClosingDR(String indCurrSumClosingDR) {
		this.indCurrSumClosingDR = indCurrSumClosingDR;
	}

	public String getIndCurrSumOpeningCR() {
		return indCurrSumOpeningCR;
	}

	public void setIndCurrSumOpeningCR(String indCurrSumOpeningCR) {
		this.indCurrSumOpeningCR = indCurrSumOpeningCR;
	}

	public String getIndCurrSumOpeningDR() {
		return indCurrSumOpeningDR;
	}

	public void setIndCurrSumOpeningDR(String indCurrSumOpeningDR) {
		this.indCurrSumOpeningDR = indCurrSumOpeningDR;
	}

	public String getIndCurrSumTransactionCR() {
		return indCurrSumTransactionCR;
	}

	public void setIndCurrSumTransactionCR(String indCurrSumTransactionCR) {
		this.indCurrSumTransactionCR = indCurrSumTransactionCR;
	}

	public String getIndCurrSumTransactionDR() {
		return indCurrSumTransactionDR;
	}

	public void setIndCurrSumTransactionDR(String indCurrSumTransactionDR) {
		this.indCurrSumTransactionDR = indCurrSumTransactionDR;
	}

	public BigDecimal getCurrAm272() {
		return currAm272;
	}

	public void setCurrAm272(BigDecimal currAm272) {
		this.currAm272 = currAm272;
	}

	public BigDecimal getPrevAm272() {
		return prevAm272;
	}

	public void setPrevAm272(BigDecimal prevAm272) {
		this.prevAm272 = prevAm272;
	}

	@Override
	public String toString() {
		return "AccountFinancialReportDTO [reportTypeId=" + reportTypeId + ", transactionDate=" + transactionDate
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", openingBalance=" + openingBalance
				+ ", voucherDate=" + voucherDate + ", voucherNo=" + voucherNo + ", accountCode=" + accountCode
				+ ", accountHead=" + accountHead + ", payerPayee=" + payerPayee + ", particular=" + particular
				+ ", drAmount=" + drAmount + ", totalDrAmount=" + totalDrAmount + ", crAmount=" + crAmount
				+ ", totalCrAmount=" + totalCrAmount + ", closingBalance=" + closingBalance + ", closingBalanceAsOn="
				+ closingBalanceAsOn + ", accountHeadId=" + accountHeadId + ", reportType=" + reportType
				+ ", openingDrAmount=" + openingDrAmount + ", openingCrAmount=" + openingCrAmount + ", closingDrAmount="
				+ closingDrAmount + ", closingCrAmount=" + closingCrAmount + ", transactionDrAmount="
				+ transactionDrAmount + ", transactionCrAmount=" + transactionCrAmount + ", voucherAmount="
				+ voucherAmount + ", budgetaryProvision=" + budgetaryProvision + ", balanceRecoverable="
				+ balanceRecoverable + ", totalBudgetaryProvision=" + totalBudgetaryProvision + ", sumTransactionDR="
				+ sumTransactionDR + ", sumTransactionCR=" + sumTransactionCR + ", sumOpeningCR=" + sumOpeningCR
				+ ", sumOpeningDR=" + sumOpeningDR + ", sumClosingCR=" + sumClosingCR + ", sumClosingDR=" + sumClosingDR
				+ ", budgetAmount=" + budgetAmount + ", actualAmountReceived=" + actualAmountReceived
				+ ", sumBudgetAmount=" + sumBudgetAmount + ", sumAcutualAmount=" + sumAcutualAmount
				+ ", sumAcutualAmountPaymentBalance=" + sumAcutualAmountPaymentBalance + ", sumbalanceRecoverable="
				+ sumbalanceRecoverable + ", chequeAmount=" + chequeAmount + ", sumOfCrAmount=" + sumOfCrAmount
				+ ", sumOfDrAmount=" + sumOfDrAmount + ", currentYearAmount=" + currentYearAmount + ", depositNumber="
				+ depositNumber + ", depositNarration=" + depositNarration + ", depositAmount=" + depositAmount
				+ ", depositRecieptNumber=" + depositRecieptNumber + ", depositBalance=" + depositBalance
				+ ", typeOfDeposit=" + typeOfDeposit + ", budgetCodeId=" + budgetCodeId + ", originalEst=" + originalEst
				+ ", revisedEstimation=" + revisedEstimation + ", actualLastyear=" + actualLastyear + ", transferAmt="
				+ transferAmt + ", bankChargeAmt=" + bankChargeAmt + ", approvedBy=" + approvedBy + ", remarks="
				+ remarks + ", paymentDate=" + paymentDate + ", totalOrgEsmtAmt=" + totalOrgEsmtAmt
				+ ", totaltransferAmts=" + totaltransferAmts + ", totalcurrentAmt=" + totalcurrentAmt
				+ ", totalbankAmts=" + totalbankAmts + ", listOfSum=" + listOfSum + ", chequeDeposit=" + chequeDeposit
				+ ", chequeAmounts=" + chequeAmounts + ", chequeDishonoured=" + chequeDishonoured + ", subTotalAmount="
				+ subTotalAmount + ", totalDepositAndDishonoured=" + totalDepositAndDishonoured + ", subTractionTatal="
				+ subTractionTatal + ", index=" + index + ", totalCashInHand=" + totalCashInHand + ", nxtYrOEstimation="
				+ nxtYrOEstimation + ", listOfIncome=" + listOfIncome + ", listOfExpenditure=" + listOfExpenditure
				+ ", listOfDeposit=" + listOfDeposit + ", listOfBudgetEstimation=" + listOfBudgetEstimation
				+ ", listReAppropriation=" + listReAppropriation + ", listDecReAppropriation=" + listDecReAppropriation
				+ ", listCashEquvalent=" + listCashEquvalent + ", listOfDayBook=" + listOfDayBook
				+ ", listOfExpenditureEstimation=" + listOfExpenditureEstimation + ", listOfOpeningBalance="
				+ listOfOpeningBalance + ", listofchequepayment=" + listofchequepayment + ", listofchequeDishonor="
				+ listofchequeDishonor + ", listofreceiptdec=" + listofreceiptdec + ", listofreceiptinc="
				+ listofreceiptinc + ", bankAccountSummary=" + bankAccountSummary + ", assetList=" + assetList
				+ ", liabilityList=" + liabilityList + ", transactionReversal=" + transactionReversal
				+ ", generalLedgerList=" + generalLedgerList + ", faYearid=" + faYearid + ", depositDate=" + depositDate
				+ ", voucherType=" + voucherType + ", voucherSubType=" + voucherSubType + ", crAmountIndianCurrency="
				+ crAmountIndianCurrency + ", drAmountIndianCurrency=" + drAmountIndianCurrency
				+ ", depositAmountIndianCurrency=" + depositAmountIndianCurrency + ", balanceAmountIndianCurrency="
				+ balanceAmountIndianCurrency + ", totalDepositIndianCurrency=" + totalDepositIndianCurrency
				+ ", totalBalanceIndianCurrency=" + totalBalanceIndianCurrency + ", totalCrAmountIndianCurrency="
				+ totalCrAmountIndianCurrency + ", totalDrAmountIndianCurrency=" + totalDrAmountIndianCurrency
				+ ", totalRecoveredDepositAmount=" + totalRecoveredDepositAmount
				+ ", totalRecoveredDepositAmountIndianCurrency=" + totalRecoveredDepositAmountIndianCurrency
				+ ", recoveredDepositAmountIndianCurrency=" + recoveredDepositAmountIndianCurrency
				+ ", recoveredDeposit=" + recoveredDeposit + ", averageamount=" + averageamount
				+ ", voucherAmountIndianCurrency=" + voucherAmountIndianCurrency + ", depositBalanceIndianCurrency="
				+ depositBalanceIndianCurrency + ", receiptNumber=" + receiptNumber + ", receiptDate=" + receiptDate
				+ ", balanceType=" + balanceType + ", finalizedFlag=" + finalizedFlag
				+ ", openingBalanceIndianCurrency=" + openingBalanceIndianCurrency + ", paymentNo=" + paymentNo
				+ ", billNo=" + billNo + ", billEntryDate=" + billEntryDate + ", vendorName=" + vendorName
				+ ", chequeNo=" + chequeNo + ", instrumentDate=" + instrumentDate + ", paymentAmnt=" + paymentAmnt
				+ ", issuanceDate=" + issuanceDate + ", chequeIssueDate=" + chequeIssueDate + ", paymentDates="
				+ paymentDates + ", totalPaymentAmnt=" + totalPaymentAmnt + ", financialYear=" + financialYear
				+ ", adjustmentForfeitureAmount=" + adjustmentForfeitureAmount + ", adjustmentForfeitureNo="
				+ adjustmentForfeitureNo + ", totalAdjustmentForfeitureAmount=" + totalAdjustmentForfeitureAmount
				+ ", ReceivedFrom=" + ReceivedFrom + ", receiptNo=" + receiptNo + ", ramt=" + ramt + ", discharge="
				+ discharge + ", chkDate=" + chkDate + ", userId=" + userId + ", lmodate=" + lmodate
				+ ", budgetSactionAmt=" + budgetSactionAmt + ", totalbudgetSacAmt=" + totalbudgetSacAmt
				+ ", totalTransAmts=" + totalTransAmts + ", totalBudSacAmts=" + totalBudSacAmts + ", totalDescAmt="
				+ totalDescAmt + ", billAmt=" + billAmt + ", budgetAmountIndianCurrency=" + budgetAmountIndianCurrency
				+ ", actualAmountReceivedIndianCurrency=" + actualAmountReceivedIndianCurrency
				+ ", balanceRecoverableIndianCurrency=" + balanceRecoverableIndianCurrency
				+ ", sumbalanceRecoverableIndianCurrency=" + sumbalanceRecoverableIndianCurrency
				+ ", sumBudgetAmountIndianCurrency=" + sumBudgetAmountIndianCurrency
				+ ", sumAcutualAmountIndianCurrency=" + sumAcutualAmountIndianCurrency + ", chequeAmountIndianCurrency="
				+ chequeAmountIndianCurrency + ", chequeDepositIndianCurrency=" + chequeDepositIndianCurrency
				+ ", chequeDishonouredIndianCurrency=" + chequeDishonouredIndianCurrency
				+ ", subTotalAmountIndianCurrency=" + subTotalAmountIndianCurrency
				+ ", totalDepositAndDishonouredIndianCurrency=" + totalDepositAndDishonouredIndianCurrency
				+ ", subTractionTatalIndianCurrency=" + subTractionTatalIndianCurrency + ", totalUtilamt="
				+ totalUtilamt + ", totalBalance=" + totalBalance + ", previousIncome=" + previousIncome
				+ ", priviousExp=" + priviousExp + ", previousInTotal=" + previousInTotal + ", previousExpTotal="
				+ previousExpTotal + ", bankname=" + bankname + ", bankAcNo=" + bankAcNo + ", accountType="
				+ accountType + ", openingBalancers=" + openingBalancers + ", receiptAmt=" + receiptAmt
				+ ", paymentAmt=" + paymentAmt + ", transactionNo=" + transactionNo + ", amount=" + amount
				+ ", narration=" + narration + ", receivePayname=" + receivePayname + ", reversedBy=" + reversedBy
				+ ", reversedDate=" + reversedDate + ", reversalReason=" + reversalReason + ", totalrevenue="
				+ totalrevenue + ", totalCollected=" + totalCollected + ", sumofTotalBalance=" + sumofTotalBalance
				+ ", closingAmt=" + closingAmt + ", lastThreeYrAmt=" + lastThreeYrAmt + ", lastYrAmt=" + lastYrAmt
				+ ", voucherDetailAmt=" + voucherDetailAmt + ", branchName=" + branchName + ", noFirstLeave="
				+ noFirstLeave + ", nolastLeave=" + nolastLeave + ", doIssue=" + doIssue + ", issueToWhom="
				+ issueToWhom + ", signatureOfReceipent=" + signatureOfReceipent + ", dateOfReturn=" + dateOfReturn
				+ ", returnToWhom=" + returnToWhom + ", leaveCancelled=" + leaveCancelled + ", receipstDate="
				+ receipstDate + ", transactionTypeId=" + transactionTypeId + ", totalReceiptAmt=" + totalReceiptAmt
				+ ", totalPaymentAmt=" + totalPaymentAmt + ", totalClosingAmt=" + totalClosingAmt
				+ ", totalLastThreeYrAmt=" + totalLastThreeYrAmt + ", totalLastYrAmt=" + totalLastYrAmt + ", rdAmount="
				+ rdAmount + ", cancellationDate=" + cancellationDate + ", newChequeNo=" + newChequeNo
				+ ", authorizedBy=" + authorizedBy + ", paymodeId=" + paymodeId + ", registerDepTypeId="
				+ registerDepTypeId + ", categoryId=" + categoryId + ", drCrType=" + drCrType + ", closingCash="
				+ closingCash + ", flag=" + flag + ", vendorAdd=" + vendorAdd + ", vendorPanNo=" + vendorPanNo
				+ ", orgName=" + orgName + ", orgPanNo=" + orgPanNo + ", orgAdd=" + orgAdd + ", oldValue=" + oldValue
				+ ", newValue=" + newValue + ", columnName=" + columnName + ", accountHeadDesc=" + accountHeadDesc
				+ ", totalOpeningBalancers=" + totalOpeningBalancers + "]";
	}

}
