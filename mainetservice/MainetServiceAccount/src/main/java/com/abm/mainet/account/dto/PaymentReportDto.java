package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PaymentReportDto implements Serializable {

    private static final long serialVersionUID = -3359828533374442L;

    private Long id;

    private Long billTypeId;
    private String VoucherNo;
    private String billNo;

    private String billDate;
    private String voucherAmount;
    private Date paymentDate;

    private String paymentEntryDate;

    private Date billEntryDateDt;

    private Long vendorId;

    private String vendorCodeDescription;

    private String vendorName;

    private String vendorDesc;

    private BigDecimal billTotalAmount;

    private BigDecimal balanceAmount;

    private Character checkerAuthorization;

    private String checkerRemarks;

    private String checkerUser;

    private String checkerDate;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private Long languageId;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddress;

    private String hasError;

    private Long projectedExpenditureId;

    private BigDecimal newBalanceAmount;

    private BigDecimal totalBillAmount;

    private BigDecimal totalSanctionedAmount;

    private BigDecimal totalDisallowedAmount;

    private BigDecimal totalDeductions;

    private BigDecimal netPayable;

    private String successfulFlag;

    private String dataFlag;

    private String fromDate;

    private String toDate;

    private String budgetCodeId;

    private String payee;

    private String billAmountStr;

    private String deductionsStr;

    private String netPayableStr;

    private String billTypeDesc;

    private String invoiceValueStr;

    private String totalBillAmountStr;

    private String totalSanctionedAmtStr;

    private String totalDisallowedAmountStr;

    private String dedcutionAmountStr;

    private String totalDedcutionsAmountStr;

    private String netPayabletStr;

    private String actualAmountStr;

    private String sanctionedAmountStr;

    private String disallowedRemark;

    private String isMakerChecker;

    private Organisation organisation;

    private String authorizationMode;

    private String authorizationStatus;

    private String authorizerEmployee;

    private String templateExistFlag;

    private Long fieldId;

    private String paymentMode;

    private Long bankAcId;

    private Long instrumentNo;

    private String instrumentDate;

    private BigDecimal totalPaymentAmount;

    private String narration;

    private String paymentNo;
    private String voucherType;
    private String bankName;
    private String branchName;
    private String bankNumber;
    private String accountCode;
    private String chequeNo;
    private String amountInWords;
    private String organisationName;

    private String acountHead;

    private String payDate;

    private String vendorAccountHead;

    private String depatmentDesc;
    private String nameOfTheFund;
    private String preparedBy;
    private String preparedDate;
    private String voucherTypeReg;

    private List<PaymentDetailsDto> paymentDetailsDto;
    private List<PaymentReportDto> listOfTbPaymentRepor;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getBillTypeId() {
        return billTypeId;
    }

    public void setBillTypeId(final Long billTypeId) {
        this.billTypeId = billTypeId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(final String billNo) {
        this.billNo = billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(final String billDate) {
        this.billDate = billDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(final Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentEntryDate() {
        return paymentEntryDate;
    }

    public void setPaymentEntryDate(final String paymentEntryDate) {
        this.paymentEntryDate = paymentEntryDate;
    }

    public Date getBillEntryDateDt() {
        return billEntryDateDt;
    }

    public void setBillEntryDateDt(final Date billEntryDateDt) {
        this.billEntryDateDt = billEntryDateDt;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(final Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorCodeDescription() {
        return vendorCodeDescription;
    }

    public void setVendorCodeDescription(final String vendorCodeDescription) {
        this.vendorCodeDescription = vendorCodeDescription;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorDesc() {
        return vendorDesc;
    }

    public void setVendorDesc(final String vendorDesc) {
        this.vendorDesc = vendorDesc;
    }

    public BigDecimal getBillTotalAmount() {
        return billTotalAmount;
    }

    public void setBillTotalAmount(final BigDecimal billTotalAmount) {
        this.billTotalAmount = billTotalAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(final BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Character getCheckerAuthorization() {
        return checkerAuthorization;
    }

    public void setCheckerAuthorization(final Character checkerAuthorization) {
        this.checkerAuthorization = checkerAuthorization;
    }

    public String getCheckerRemarks() {
        return checkerRemarks;
    }

    public void setCheckerRemarks(final String checkerRemarks) {
        this.checkerRemarks = checkerRemarks;
    }

    public String getCheckerUser() {
        return checkerUser;
    }

    public void setCheckerUser(final String checkerUser) {
        this.checkerUser = checkerUser;
    }

    public String getCheckerDate() {
        return checkerDate;
    }

    public void setCheckerDate(final String checkerDate) {
        this.checkerDate = checkerDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(final Long languageId) {
        this.languageId = languageId;
    }

    public String getLgIpMacAddress() {
        return lgIpMacAddress;
    }

    public void setLgIpMacAddress(final String lgIpMacAddress) {
        this.lgIpMacAddress = lgIpMacAddress;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public Long getProjectedExpenditureId() {
        return projectedExpenditureId;
    }

    public void setProjectedExpenditureId(final Long projectedExpenditureId) {
        this.projectedExpenditureId = projectedExpenditureId;
    }

    public BigDecimal getNewBalanceAmount() {
        return newBalanceAmount;
    }

    public void setNewBalanceAmount(final BigDecimal newBalanceAmount) {
        this.newBalanceAmount = newBalanceAmount;
    }

    public BigDecimal getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(final BigDecimal totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    public BigDecimal getTotalSanctionedAmount() {
        return totalSanctionedAmount;
    }

    public void setTotalSanctionedAmount(final BigDecimal totalSanctionedAmount) {
        this.totalSanctionedAmount = totalSanctionedAmount;
    }

    public BigDecimal getTotalDisallowedAmount() {
        return totalDisallowedAmount;
    }

    public void setTotalDisallowedAmount(final BigDecimal totalDisallowedAmount) {
        this.totalDisallowedAmount = totalDisallowedAmount;
    }

    public BigDecimal getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(final BigDecimal totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public BigDecimal getNetPayable() {
        return netPayable;
    }

    public void setNetPayable(final BigDecimal netPayable) {
        this.netPayable = netPayable;
    }

    public String getSuccessfulFlag() {
        return successfulFlag;
    }

    public void setSuccessfulFlag(final String successfulFlag) {
        this.successfulFlag = successfulFlag;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(final String dataFlag) {
        this.dataFlag = dataFlag;
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

    public String getPayee() {
        return payee;
    }

    public void setPayee(final String payee) {
        this.payee = payee;
    }

    public String getBillAmountStr() {
        return billAmountStr;
    }

    public void setBillAmountStr(final String billAmountStr) {
        this.billAmountStr = billAmountStr;
    }

    public String getDeductionsStr() {
        return deductionsStr;
    }

    public void setDeductionsStr(final String deductionsStr) {
        this.deductionsStr = deductionsStr;
    }

    public String getNetPayableStr() {
        return netPayableStr;
    }

    public void setNetPayableStr(final String netPayableStr) {
        this.netPayableStr = netPayableStr;
    }

    public String getBillTypeDesc() {
        return billTypeDesc;
    }

    public void setBillTypeDesc(final String billTypeDesc) {
        this.billTypeDesc = billTypeDesc;
    }

    public String getInvoiceValueStr() {
        return invoiceValueStr;
    }

    public void setInvoiceValueStr(final String invoiceValueStr) {
        this.invoiceValueStr = invoiceValueStr;
    }

    public String getTotalBillAmountStr() {
        return totalBillAmountStr;
    }

    public void setTotalBillAmountStr(final String totalBillAmountStr) {
        this.totalBillAmountStr = totalBillAmountStr;
    }

    public String getTotalSanctionedAmtStr() {
        return totalSanctionedAmtStr;
    }

    public void setTotalSanctionedAmtStr(final String totalSanctionedAmtStr) {
        this.totalSanctionedAmtStr = totalSanctionedAmtStr;
    }

    public String getTotalDisallowedAmountStr() {
        return totalDisallowedAmountStr;
    }

    public void setTotalDisallowedAmountStr(final String totalDisallowedAmountStr) {
        this.totalDisallowedAmountStr = totalDisallowedAmountStr;
    }

    public String getDedcutionAmountStr() {
        return dedcutionAmountStr;
    }

    public void setDedcutionAmountStr(final String dedcutionAmountStr) {
        this.dedcutionAmountStr = dedcutionAmountStr;
    }

    public String getTotalDedcutionsAmountStr() {
        return totalDedcutionsAmountStr;
    }

    public void setTotalDedcutionsAmountStr(final String totalDedcutionsAmountStr) {
        this.totalDedcutionsAmountStr = totalDedcutionsAmountStr;
    }

    public String getNetPayabletStr() {
        return netPayabletStr;
    }

    public void setNetPayabletStr(final String netPayabletStr) {
        this.netPayabletStr = netPayabletStr;
    }

    public String getActualAmountStr() {
        return actualAmountStr;
    }

    public void setActualAmountStr(final String actualAmountStr) {
        this.actualAmountStr = actualAmountStr;
    }

    public String getSanctionedAmountStr() {
        return sanctionedAmountStr;
    }

    public void setSanctionedAmountStr(final String sanctionedAmountStr) {
        this.sanctionedAmountStr = sanctionedAmountStr;
    }

    public String getDisallowedRemark() {
        return disallowedRemark;
    }

    public void setDisallowedRemark(final String disallowedRemark) {
        this.disallowedRemark = disallowedRemark;
    }

    public String getIsMakerChecker() {
        return isMakerChecker;
    }

    public void setIsMakerChecker(final String isMakerChecker) {
        this.isMakerChecker = isMakerChecker;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(final Organisation organisation) {
        this.organisation = organisation;
    }

    public String getAuthorizationMode() {
        return authorizationMode;
    }

    public void setAuthorizationMode(final String authorizationMode) {
        this.authorizationMode = authorizationMode;
    }

    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(final String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public String getAuthorizerEmployee() {
        return authorizerEmployee;
    }

    public void setAuthorizerEmployee(final String authorizerEmployee) {
        this.authorizerEmployee = authorizerEmployee;
    }

    public String getTemplateExistFlag() {
        return templateExistFlag;
    }

    public void setTemplateExistFlag(final String templateExistFlag) {
        this.templateExistFlag = templateExistFlag;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getBankAcId() {
        return bankAcId;
    }

    public void setBankAcId(final Long bankAcId) {
        this.bankAcId = bankAcId;
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

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(final BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(final String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public List<PaymentDetailsDto> getPaymentDetailsDto() {
        return paymentDetailsDto;
    }

    public void setPaymentDetailsDto(final List<PaymentDetailsDto> paymentDetailsDto) {
        this.paymentDetailsDto = paymentDetailsDto;
    }

    public List<PaymentReportDto> getListOfTbPaymentRepor() {
        return listOfTbPaymentRepor;
    }

    public void setListOfTbPaymentRepor(final List<PaymentReportDto> listOfTbPaymentRepor) {
        this.listOfTbPaymentRepor = listOfTbPaymentRepor;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(final String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getVoucherNo() {
        return VoucherNo;
    }

    public void setVoucherNo(final String voucherNo) {
        VoucherNo = voucherNo;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(final String voucherType) {
        this.voucherType = voucherType;
    }

    public String getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final String budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(final String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(final String organisationName) {
        this.organisationName = organisationName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(final String accountCode) {
        this.accountCode = accountCode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(final String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(final String voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public String getAcountHead() {
        return acountHead;
    }

    public void setAcountHead(String acountHead) {
        this.acountHead = acountHead;
    }

    public void setChequeNo(final String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getVendorAccountHead() {
        return vendorAccountHead;
    }

    public void setVendorAccountHead(String vendorAccountHead) {
        this.vendorAccountHead = vendorAccountHead;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepatmentDesc() {
        return depatmentDesc;
    }

    public void setDepatmentDesc(String depatmentDesc) {
        this.depatmentDesc = depatmentDesc;
    }

    public String getNameOfTheFund() {
        return nameOfTheFund;
    }

    public void setNameOfTheFund(String nameOfTheFund) {
        this.nameOfTheFund = nameOfTheFund;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

	public String getVoucherTypeReg() {
		return voucherTypeReg;
	}

	public void setVoucherTypeReg(String voucherTypeReg) {
		this.voucherTypeReg = voucherTypeReg;
	}

}
