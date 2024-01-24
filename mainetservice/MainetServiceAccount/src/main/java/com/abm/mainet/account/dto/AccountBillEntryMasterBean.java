package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tejas.kotekar
 *
 */
public class AccountBillEntryMasterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long billTypeId;

    private String billNo;

    private String billDate;

    private String billEntryDate;

    private Date billEntryDateDt;

    private Long departmentId;

    private Long vendorId;

    private String vendorCodeDescription;

    private String vendorName;

    private String vendorDesc;

    private String invoiceNumber;

    private String invoiceDate;

    private BigDecimal invoiceValue;

    private String workOrPurchaseOrderNumber;

    private String workOrPurchaseOrderDate;

    private String resolutionNumber;

    private String resolutionDate;

    private String narration;

    private BigDecimal billTotalAmount;

    private BigDecimal balanceAmount;

    private Long liabilityId;

    private Long depositId;

    private Long loanId;

    private Long advanceTypeId;

    private Character checkerAuthorization;

    private String checkerRemarks;

    private String checkerUser;

    private String checkerDate;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private String dupCreatedDate;

    private Long updatedBy;

    private Date updatedDate;

    private Long languageId;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddress;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddressUpdated;

    private String hasError;

    private Long projectedExpenditureId;

    private BigDecimal newBalanceAmount;

    private BigDecimal totalBillAmount;

    private BigDecimal totalSanctionedAmount;

    private BigDecimal totalDisallowedAmount;

    private BigDecimal totalDeductions;

    private BigDecimal netPayable;

    private Long fieldId;

    private String transHeadFlagBudgetCode;

    private String transHeadFlagAccountCode;

    private String successfulFlag;

    private String dataFlag;

    private String fromDate;

    private String toDate;

    private Long budgetCodeId;

    private String payee;

    private String billAmountStr;

    private String deductionsStr;

    private String netPayableStr;

    private String billTypeCode;

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

    private String paymentEntryFlag;

    private Long superOrgId;

    private String expenditureExistsFlag;

    private String advanceFlag;

    private Long prAdvEntryId;

    private BigDecimal balAmount;

    private Long depId;

    private Long trTenderId;

    private String billTotalAmt;

    private String billAmt;

    private String actualAmnt;

    private String outStandingAmt;
    private String totalSactAmt;

    private String depositFlag;
    private String workOrderFlag;
    private String transactionDate;
    private String authorizationDate;
    private String billBalanceAmt;
    private BigDecimal totalBillsamt;
    private String paymentDate;
    private int count;
    private String billtoatalAmt;
    private BigDecimal totalActualamt;
    private BigDecimal totalPayee;
    private BigDecimal totalBillstr;
    private String totalDeductionAmt;
    private String totalPayments;
    private String totalBalance;
    private String paymentNo;
    private String accountHead;
    private String amountInWords;
    private Long billIntRefId;
    private String department;
    private String SumDeductionAmt;
    private BigDecimal outStandingBalance;
    private String orgShortName;
    private String deletedExpIds;
    private String deletedDedIds;
    private Long actualTaskId;
    private String deletedMbIds;

    private Long workFlowLevel1;
    private Long workFlowLevel2;
    private Long workFlowLevel3;
    private Long workFlowLevel4;
    private Long workFlowLevel5;

    private Long paymentId;
    public String rtgsPaymentIdFlag;
    private String grantFlag;

    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList;
    private String removeFileById;

    private List<AccountBillEntryMasterBean> listOfBillRegister = new ArrayList<>();
    private List<AccountBillEntryMasterBean> listOfPayment = new ArrayList<>();
    private List<AccountBillEntryExpenditureDetBean> expenditureDetailList = new ArrayList<>();
    private List<AccountBillEntryDeductionDetBean> deductionDetailList = new ArrayList<>();
    private List<AccountBillEntryMeasurementDetBean> measurementDetailList = new ArrayList<>();

    private String empName;
    
    private String oherValueFlag;
    
    private String isServiceActive;
    
    private String grantNo;
    
    private String grantFund;
    
    private Long grantId;
    
    private BigDecimal grantPayableAmount;
    
    private String loanFlag;
    
    private double loanPayableAmount;
    
    private Long loanRepaymentId;
    
    private Long fundId;
    
    private BigDecimal bmTaxableValue;
    
    private String dueDate;
    
    private Long levelCheck;
    
    private boolean lastCheck;
    
    private BigDecimal mbTotalAmount;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setBillTypeId(final Long billTypeId) {
        this.billTypeId = billTypeId;
    }

    public Long getBillTypeId() {
        return billTypeId;
    }

    public void setBillNo(final String billNo) {
        this.billNo = billNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillDate(final String billDate) {
        this.billDate = billDate;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillEntryDate(final String billEntryDate) {
        this.billEntryDate = billEntryDate;
    }

    public String getBillEntryDate() {
        return billEntryDate;
    }

    public void setDepartmentId(final Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setVendorId(final Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    /**
     * @return the vmVendorCodeDesc
     */
    public String getVendorCodeDescription() {
        return vendorCodeDescription;
    }

    /**
     * @param vmVendorCodeDesc the vmVendorCodeDesc to set
     */
    public void setVendorCodeDescription(final String vendorCodeDescription) {
        this.vendorCodeDescription = vendorCodeDescription;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    /**
     * @return the vmVendorDesc
     */
    public String getVendorDesc() {
        return vendorDesc;
    }

    /**
     * @param vmVendorDesc the vmVendorDesc to set
     */
    public void setVendorDesc(final String vendorDesc) {
        this.vendorDesc = vendorDesc;
    }

    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceDate(final String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceValue(final BigDecimal invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public BigDecimal getInvoiceValue() {
        return invoiceValue;
    }

    public void setWorkOrPurchaseOrderNumber(final String workOrPurchaseOrderNumber) {
        this.workOrPurchaseOrderNumber = workOrPurchaseOrderNumber;
    }

    public String getWorkOrPurchaseOrderNumber() {
        return workOrPurchaseOrderNumber;
    }

    public void setWorkOrPurchaseOrderDate(final String workOrPurchaseOrderDate) {
        this.workOrPurchaseOrderDate = workOrPurchaseOrderDate;
    }

    public String getWorkOrPurchaseOrderDate() {
        return workOrPurchaseOrderDate;
    }

    public void setResolutionNumber(final String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }

    public String getResolutionNumber() {
        return resolutionNumber;
    }

    public void setResolutionDate(final String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public String getNarration() {
        return narration;
    }

    public void setBillTotalAmount(final BigDecimal billTotalAmount) {
        this.billTotalAmount = billTotalAmount;
    }

    public BigDecimal getBillTotalAmount() {
        return billTotalAmount;
    }

    public void setBalanceAmount(final BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setLiabilityId(final Long liabilityId) {
        this.liabilityId = liabilityId;
    }

    public Long getLiabilityId() {
        return liabilityId;
    }

    public void setDepositId(final Long depositId) {
        this.depositId = depositId;
    }

    public Long getDepositId() {
        return depositId;
    }

    public void setLoanId(final Long loanId) {
        this.loanId = loanId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setAdvanceTypeId(final Long advanceTypeId) {
        this.advanceTypeId = advanceTypeId;
    }

    public Long getAdvanceTypeId() {
        return advanceTypeId;
    }

    public void setCheckerAuthorization(final Character checkerAuthorization) {
        this.checkerAuthorization = checkerAuthorization;
    }

    public Character getCheckerAuthorization() {
        return checkerAuthorization;
    }

    public void setCheckerRemarks(final String checkerRemarks) {
        this.checkerRemarks = checkerRemarks;
    }

    public String getCheckerRemarks() {
        return checkerRemarks;
    }

    public void setCheckerUser(final String checkerUser) {
        this.checkerUser = checkerUser;
    }

    public String getCheckerUser() {
        return checkerUser;
    }

    public void setCheckerDate(final String checkerDate) {
        this.checkerDate = checkerDate;
    }

    public String getCheckerDate() {
        return checkerDate;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setLanguageId(final Long languageId) {
        this.languageId = languageId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLgIpMacAddress(final String lgIpMacAddress) {
        this.lgIpMacAddress = lgIpMacAddress;
    }

    public String getLgIpMacAddress() {
        return lgIpMacAddress;
    }

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    /**
     * @return the expDetList
     */
    public List<AccountBillEntryExpenditureDetBean> getExpenditureDetailList() {
        return expenditureDetailList;
    }

    /**
     * @param expDetList the expDetList to set
     */
    public void setExpenditureDetailList(final List<AccountBillEntryExpenditureDetBean> expenditureDetailList) {
        this.expenditureDetailList = expenditureDetailList;
    }

    /**
     * @return the dedDetList
     */
    public List<AccountBillEntryDeductionDetBean> getDeductionDetailList() {
        return deductionDetailList;
    }

    /**
     * @param dedDetList the dedDetList to set
     */
    public void setDeductionDetailList(final List<AccountBillEntryDeductionDetBean> deductionDetailList) {
        this.deductionDetailList = deductionDetailList;
    }

    /**
     * @return the projectedExpenditureId
     */
    public Long getProjectedExpenditureId() {
        return projectedExpenditureId;
    }

    /**
     * @param projectedExpenditureId the projectedExpenditureId to set
     */
    public void setProjectedExpenditureId(final Long projectedExpenditureId) {
        this.projectedExpenditureId = projectedExpenditureId;
    }

    /**
     * @return the newBalanceAmount
     */
    public BigDecimal getNewBalanceAmount() {
        return newBalanceAmount;
    }

    /**
     * @param newBalanceAmount the newBalanceAmount to set
     */
    public void setNewBalanceAmount(final BigDecimal newBalanceAmount) {
        this.newBalanceAmount = newBalanceAmount;
    }

    /**
     * @return the tbAcFieldMaster
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * @param tbAcFieldMaster the tbAcFieldMaster to set
     */
    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return the totalBillAmount
     */
    public BigDecimal getTotalBillAmount() {
        return totalBillAmount;
    }

    /**
     * @param totalBillAmount the totalBillAmount to set
     */
    public void setTotalBillAmount(final BigDecimal totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    /**
     * @return the totalSanctionedAmount
     */
    public BigDecimal getTotalSanctionedAmount() {
        return totalSanctionedAmount;
    }

    /**
     * @param totalSanctionedAmount the totalSanctionedAmount to set
     */
    public void setTotalSanctionedAmount(final BigDecimal totalSanctionedAmount) {
        this.totalSanctionedAmount = totalSanctionedAmount;
    }

    /**
     * @return the totalDisallowedAmount
     */
    public BigDecimal getTotalDisallowedAmount() {
        return totalDisallowedAmount;
    }

    /**
     * @param totalDisallowedAmount the totalDisallowedAmount to set
     */
    public void setTotalDisallowedAmount(final BigDecimal totalDisallowedAmount) {
        this.totalDisallowedAmount = totalDisallowedAmount;
    }

    /**
     * @return the totalDeductions
     */
    public BigDecimal getTotalDeductions() {
        return totalDeductions;
    }

    /**
     * @param totalDeductions the totalDeductions to set
     */
    public void setTotalDeductions(final BigDecimal totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    /**
     * @return the netPayable
     */
    public BigDecimal getNetPayable() {
        return netPayable;
    }

    /**
     * @param netPayable the netPayable to set
     */
    public void setNetPayable(final BigDecimal netPayable) {
        this.netPayable = netPayable;
    }

    /**
     * @return the transHeadFlagBudgetCode
     */
    public String getTransHeadFlagBudgetCode() {
        return transHeadFlagBudgetCode;
    }

    /**
     * @param transHeadFlagBudgetCode the transHeadFlagBudgetCode to set
     */
    public void setTransHeadFlagBudgetCode(final String transHeadFlagBudgetCode) {
        this.transHeadFlagBudgetCode = transHeadFlagBudgetCode;
    }

    /**
     * @return the transHeadFlagAccountCode
     */
    public String getTransHeadFlagAccountCode() {
        return transHeadFlagAccountCode;
    }

    /**
     * @param transHeadFlagAccountCode the transHeadFlagAccountCode to set
     */
    public void setTransHeadFlagAccountCode(final String transHeadFlagAccountCode) {
        this.transHeadFlagAccountCode = transHeadFlagAccountCode;
    }

    /**
     * @return the successfulFlag
     */
    public String getSuccessfulFlag() {
        return successfulFlag;
    }

    /**
     * @param successfulFlag the successfulFlag to set
     */
    public void setSuccessfulFlag(final String successfulFlag) {
        this.successfulFlag = successfulFlag;
    }

    /**
     * @return the dataFlag
     */
    public String getDataFlag() {
        return dataFlag;
    }

    /**
     * @param dataFlag the dataFlag to set
     */
    public void setDataFlag(final String dataFlag) {
        this.dataFlag = dataFlag;
    }

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the budgetCodeId
     */
    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    /**
     * @param budgetCodeId the budgetCodeId to set
     */
    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public Date getBillEntryDateDt() {
        return billEntryDateDt;
    }

    public void setBillEntryDateDt(final Date billEntryDateDt) {
        this.billEntryDateDt = billEntryDateDt;
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

    public String getPaymentEntryFlag() {
        return paymentEntryFlag;
    }

    public void setPaymentEntryFlag(final String paymentEntryFlag) {
        this.paymentEntryFlag = paymentEntryFlag;
    }

    public Long getSuperOrgId() {
        return superOrgId;
    }

    public void setSuperOrgId(final Long superOrgId) {
        this.superOrgId = superOrgId;
    }

    public String getExpenditureExistsFlag() {
        return expenditureExistsFlag;
    }

    public void setExpenditureExistsFlag(final String expenditureExistsFlag) {
        this.expenditureExistsFlag = expenditureExistsFlag;
    }

    public String getAdvanceFlag() {
        return advanceFlag;
    }

    public void setAdvanceFlag(final String advanceFlag) {
        this.advanceFlag = advanceFlag;
    }

    public Long getPrAdvEntryId() {
        return prAdvEntryId;
    }

    public void setPrAdvEntryId(final Long prAdvEntryId) {
        this.prAdvEntryId = prAdvEntryId;
    }

    public BigDecimal getBalAmount() {
        return balAmount;
    }

    public void setBalAmount(final BigDecimal balAmount) {
        this.balAmount = balAmount;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(final Long depId) {
        this.depId = depId;
    }

    public String getDepositFlag() {
        return depositFlag;
    }

    public void setDepositFlag(final String depositFlag) {
        this.depositFlag = depositFlag;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(final String authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public List<AccountBillEntryMasterBean> getListOfBillRegister() {
        return listOfBillRegister;
    }

    public void setListOfBillRegister(List<AccountBillEntryMasterBean> listOfBillRegister) {
        this.listOfBillRegister = listOfBillRegister;
    }

    public String getBillBalanceAmt() {
        return billBalanceAmt;
    }

    public void setBillBalanceAmt(String billBalanceAmt) {
        this.billBalanceAmt = billBalanceAmt;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTotalActualamt() {
        return totalActualamt;
    }

    public void setTotalActualamt(BigDecimal totalActualamt) {
        this.totalActualamt = totalActualamt;
    }

    public BigDecimal getTotalPayee() {
        return totalPayee;
    }

    public void setTotalPayee(BigDecimal totalPayee) {
        this.totalPayee = totalPayee;
    }

    public BigDecimal getTotalBillstr() {
        return totalBillstr;
    }

    public void setTotalBillstr(BigDecimal totalBillstr) {
        this.totalBillstr = totalBillstr;
    }

    public BigDecimal getTotalBillsamt() {
        return totalBillsamt;
    }

    public void setTotalBillsamt(BigDecimal totalBillsamt) {
        this.totalBillsamt = totalBillsamt;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("AccountBillEntryMasterBean [id=");
        builder.append(id);
        builder.append(", billTypeId=");
        builder.append(billTypeId);
        builder.append(", billNo=");
        builder.append(billNo);
        builder.append(", billDate=");
        builder.append(billDate);
        builder.append(", billEntryDate=");
        builder.append(billEntryDate);
        builder.append(", billEntryDateDt=");
        builder.append(billEntryDateDt);
        builder.append(", departmentId=");
        builder.append(departmentId);
        builder.append(", vendorId=");
        builder.append(vendorId);
        builder.append(", vendorCodeDescription=");
        builder.append(vendorCodeDescription);
        builder.append(", vendorName=");
        builder.append(vendorName);
        builder.append(", vendorDesc=");
        builder.append(vendorDesc);
        builder.append(", invoiceNumber=");
        builder.append(invoiceNumber);
        builder.append(", invoiceDate=");
        builder.append(invoiceDate);
        builder.append(", invoiceValue=");
        builder.append(invoiceValue);
        builder.append(", workOrPurchaseOrderNumber=");
        builder.append(workOrPurchaseOrderNumber);
        builder.append(", workOrPurchaseOrderDate=");
        builder.append(workOrPurchaseOrderDate);
        builder.append(", resolutionNumber=");
        builder.append(resolutionNumber);
        builder.append(", resolutionDate=");
        builder.append(resolutionDate);
        builder.append(", narration=");
        builder.append(narration);
        builder.append(", billTotalAmount=");
        builder.append(billTotalAmount);
        builder.append(", balanceAmount=");
        builder.append(balanceAmount);
        builder.append(", liabilityId=");
        builder.append(liabilityId);
        builder.append(", depositId=");
        builder.append(depositId);
        builder.append(", loanId=");
        builder.append(loanId);
        builder.append(", advanceTypeId=");
        builder.append(advanceTypeId);
        builder.append(", checkerAuthorization=");
        builder.append(checkerAuthorization);
        builder.append(", checkerRemarks=");
        builder.append(checkerRemarks);
        builder.append(", checkerUser=");
        builder.append(checkerUser);
        builder.append(", checkerDate=");
        builder.append(checkerDate);
        builder.append(", orgId=");
        builder.append(orgId);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", languageId=");
        builder.append(languageId);
        builder.append(", lgIpMacAddress=");
        builder.append(lgIpMacAddress);
        builder.append(", hasError=");
        builder.append(hasError);
        builder.append(", projectedExpenditureId=");
        builder.append(projectedExpenditureId);
        builder.append(", newBalanceAmount=");
        builder.append(newBalanceAmount);
        builder.append(", totalBillAmount=");
        builder.append(totalBillAmount);
        builder.append(", totalSanctionedAmount=");
        builder.append(totalSanctionedAmount);
        builder.append(", totalDisallowedAmount=");
        builder.append(totalDisallowedAmount);
        builder.append(", totalDeductions=");
        builder.append(totalDeductions);
        builder.append(", netPayable=");
        builder.append(netPayable);
        builder.append(", fieldId=");
        builder.append(fieldId);
        builder.append(", transHeadFlagBudgetCode=");
        builder.append(transHeadFlagBudgetCode);
        builder.append(", transHeadFlagAccountCode=");
        builder.append(transHeadFlagAccountCode);
        builder.append(", successfulFlag=");
        builder.append(successfulFlag);
        builder.append(", dataFlag=");
        builder.append(dataFlag);
        builder.append(", fromDate=");
        builder.append(fromDate);
        builder.append(", toDate=");
        builder.append(toDate);
        builder.append(", budgetCodeId=");
        builder.append(budgetCodeId);
        builder.append(", payee=");
        builder.append(payee);
        builder.append(", billAmountStr=");
        builder.append(billAmountStr);
        builder.append(", deductionsStr=");
        builder.append(deductionsStr);
        builder.append(", netPayableStr=");
        builder.append(netPayableStr);
        builder.append(", billTypeDesc=");
        builder.append(billTypeDesc);
        builder.append(", invoiceValueStr=");
        builder.append(invoiceValueStr);
        builder.append(", totalBillAmountStr=");
        builder.append(totalBillAmountStr);
        builder.append(", totalSanctionedAmtStr=");
        builder.append(totalSanctionedAmtStr);
        builder.append(", totalDisallowedAmountStr=");
        builder.append(totalDisallowedAmountStr);
        builder.append(", dedcutionAmountStr=");
        builder.append(dedcutionAmountStr);
        builder.append(", totalDedcutionsAmountStr=");
        builder.append(totalDedcutionsAmountStr);
        builder.append(", netPayabletStr=");
        builder.append(netPayabletStr);
        builder.append(", actualAmountStr=");
        builder.append(actualAmountStr);
        builder.append(", sanctionedAmountStr=");
        builder.append(sanctionedAmountStr);
        builder.append(", disallowedRemark=");
        builder.append(disallowedRemark);
        builder.append(", isMakerChecker=");
        builder.append(isMakerChecker);
        builder.append(", organisation=");
        builder.append(organisation);
        builder.append(", authorizationMode=");
        builder.append(authorizationMode);
        builder.append(", authorizationStatus=");
        builder.append(authorizationStatus);
        builder.append(", authorizerEmployee=");
        builder.append(authorizerEmployee);
        builder.append(", templateExistFlag=");
        builder.append(templateExistFlag);
        builder.append(", paymentEntryFlag=");
        builder.append(paymentEntryFlag);
        builder.append(", superOrgId=");
        builder.append(superOrgId);
        builder.append(", expenditureExistsFlag=");
        builder.append(expenditureExistsFlag);
        builder.append(", advanceFlag=");
        builder.append(advanceFlag);
        builder.append(", prAdvEntryId=");
        builder.append(prAdvEntryId);
        builder.append(", balAmount=");
        builder.append(balAmount);
        builder.append(", depId=");
        builder.append(depId);
        builder.append(", depositFlag=");
        builder.append(depositFlag);
        builder.append(", transactionDate=");
        builder.append(transactionDate);
        builder.append(", authorizationDate=");
        builder.append(authorizationDate);
        builder.append(", expenditureDetailList=");
        builder.append(expenditureDetailList);
        builder.append(", deductionDetailList=");
        builder.append(deductionDetailList);
        builder.append("]");
        return builder.toString();
    }

    public String getBillTotalAmt() {
        return billTotalAmt;
    }

    public void setBillTotalAmt(String billTotalAmt) {
        this.billTotalAmt = billTotalAmt;
    }

    public String getBillAmt() {
        return billAmt;
    }

    public void setBillAmt(String billAmt) {
        this.billAmt = billAmt;
    }

    public String getActualAmnt() {
        return actualAmnt;
    }

    public void setActualAmnt(String actualAmnt) {
        this.actualAmnt = actualAmnt;
    }

    public String getOutStandingAmt() {
        return outStandingAmt;
    }

    public void setOutStandingAmt(String outStandingAmt) {
        this.outStandingAmt = outStandingAmt;
    }

    public String getBilltoatalAmt() {
        return billtoatalAmt;
    }

    public void setBilltoatalAmt(String billtoatalAmt) {
        this.billtoatalAmt = billtoatalAmt;
    }

    public String getTotalSactAmt() {
        return totalSactAmt;
    }

    public void setTotalSactAmt(String totalSactAmt) {
        this.totalSactAmt = totalSactAmt;
    }

    public String getTotalDeductionAmt() {
        return totalDeductionAmt;
    }

    public void setTotalDeductionAmt(String totalDeductionAmt) {
        this.totalDeductionAmt = totalDeductionAmt;
    }

    public String getTotalPayments() {
        return totalPayments;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalPayments(String totalPayments) {
        this.totalPayments = totalPayments;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Long getTrTenderId() {
        return trTenderId;
    }

    public void setTrTenderId(Long trTenderId) {
        this.trTenderId = trTenderId;
    }

    public String getWorkOrderFlag() {
        return workOrderFlag;
    }

    public void setWorkOrderFlag(String workOrderFlag) {
        this.workOrderFlag = workOrderFlag;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public List<AccountBillEntryMasterBean> getListOfPayment() {
        return listOfPayment;
    }

    public void setListOfPayment(List<AccountBillEntryMasterBean> listOfPayment) {
        this.listOfPayment = listOfPayment;
    }

    public Long getBillIntRefId() {
        return billIntRefId;
    }

    public void setBillIntRefId(Long billIntRefId) {
        this.billIntRefId = billIntRefId;
    }

    public String getLgIpMacAddressUpdated() {
        return lgIpMacAddressUpdated;
    }

    public void setLgIpMacAddressUpdated(String lgIpMacAddressUpdated) {
        this.lgIpMacAddressUpdated = lgIpMacAddressUpdated;
    }

    public String getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(String accountHead) {
        this.accountHead = accountHead;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBillTypeCode() {
        return billTypeCode;
    }

    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode;
    }

    public String getSumDeductionAmt() {
        return SumDeductionAmt;
    }

    public void setSumDeductionAmt(String sumDeductionAmt) {
        SumDeductionAmt = sumDeductionAmt;
    }

    public BigDecimal getOutStandingBalance() {
        return outStandingBalance;
    }

    public void setOutStandingBalance(BigDecimal outStandingBalance) {
        this.outStandingBalance = outStandingBalance;
    }

    public String getOrgShortName() {
        return orgShortName;
    }

    public void setOrgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }

    public String getDeletedExpIds() {
        return deletedExpIds;
    }

    public void setDeletedExpIds(String deletedExpIds) {
        this.deletedExpIds = deletedExpIds;
    }

    public String getDeletedDedIds() {
        return deletedDedIds;
    }

    public void setDeletedDedIds(String deletedDedIds) {
        this.deletedDedIds = deletedDedIds;
    }

    public Long getActualTaskId() {
        return actualTaskId;
    }

    public void setActualTaskId(Long actualTaskId) {
        this.actualTaskId = actualTaskId;
    }

    public Long getWorkFlowLevel1() {
        return workFlowLevel1;
    }

    public void setWorkFlowLevel1(Long workFlowLevel1) {
        this.workFlowLevel1 = workFlowLevel1;
    }

    public Long getWorkFlowLevel2() {
        return workFlowLevel2;
    }

    public void setWorkFlowLevel2(Long workFlowLevel2) {
        this.workFlowLevel2 = workFlowLevel2;
    }

    public Long getWorkFlowLevel3() {
        return workFlowLevel3;
    }

    public void setWorkFlowLevel3(Long workFlowLevel3) {
        this.workFlowLevel3 = workFlowLevel3;
    }

    public Long getWorkFlowLevel4() {
        return workFlowLevel4;
    }

    public void setWorkFlowLevel4(Long workFlowLevel4) {
        this.workFlowLevel4 = workFlowLevel4;
    }

    public Long getWorkFlowLevel5() {
        return workFlowLevel5;
    }

    public void setWorkFlowLevel5(Long workFlowLevel5) {
        this.workFlowLevel5 = workFlowLevel5;
    }

    public String getDupCreatedDate() {
        return dupCreatedDate;
    }

    public void setDupCreatedDate(String dupCreatedDate) {
        this.dupCreatedDate = dupCreatedDate;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getRtgsPaymentIdFlag() {
        return rtgsPaymentIdFlag;
    }

    public void setRtgsPaymentIdFlag(String rtgsPaymentIdFlag) {
        this.rtgsPaymentIdFlag = rtgsPaymentIdFlag;
    }

    public String getRemoveFileById() {
        return removeFileById;
    }

    public void setRemoveFileById(String removeFileById) {
        this.removeFileById = removeFileById;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

	public String getOherValueFlag() {
		return oherValueFlag;
	}

	public void setOherValueFlag(String oherValueFlag) {
		this.oherValueFlag = oherValueFlag;
	}

	public String getGrantFlag() {
		return grantFlag;
	}

	public void setGrantFlag(String grantFlag) {
		this.grantFlag = grantFlag;
	}

	public String getIsServiceActive() {
		return isServiceActive;
	}

	public void setIsServiceActive(String isServiceActive) {
		this.isServiceActive = isServiceActive;
	}

	public String getGrantNo() {
		return grantNo;
	}

	public void setGrantNo(String grantNo) {
		this.grantNo = grantNo;
	}

	public String getGrantFund() {
		return grantFund;
	}

	public void setGrantFund(String grantFund) {
		this.grantFund = grantFund;
	}

	public Long getGrantId() {
		return grantId;
	}

	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}

	public BigDecimal getGrantPayableAmount() {
		return grantPayableAmount;
	}

	public void setGrantPayableAmount(BigDecimal grantPayableAmount) {
		this.grantPayableAmount = grantPayableAmount;
	}

	public String getLoanFlag() {
		return loanFlag;
	}

	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}

	public double getLoanPayableAmount() {
		return loanPayableAmount;
	}

	public void setLoanPayableAmount(double loanPayableAmount) {
		this.loanPayableAmount = loanPayableAmount;
	}

	public Long getLoanRepaymentId() {
		return loanRepaymentId;
	}

	public void setLoanRepaymentId(Long loanRepaymentId) {
		this.loanRepaymentId = loanRepaymentId;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public BigDecimal getBmTaxableValue() {
		return bmTaxableValue;
	}

	public void setBmTaxableValue(BigDecimal bmTaxableValue) {
		this.bmTaxableValue = bmTaxableValue;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public Long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(Long levelCheck) {
		this.levelCheck = levelCheck;
	}

	public boolean isLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(boolean lastCheck) {
		this.lastCheck = lastCheck;
	}

	public List<AccountBillEntryMeasurementDetBean> getMeasurementDetailList() {
		return measurementDetailList;
	}

	public void setMeasurementDetailList(List<AccountBillEntryMeasurementDetBean> measurementDetailList) {
		this.measurementDetailList = measurementDetailList;
	}

	public String getDeletedMbIds() {
		return deletedMbIds;
	}

	public void setDeletedMbIds(String deletedMbIds) {
		this.deletedMbIds = deletedMbIds;
	}

	public BigDecimal getMbTotalAmount() {
		return mbTotalAmount;
	}

	public void setMbTotalAmount(BigDecimal mbTotalAmount) {
		this.mbTotalAmount = mbTotalAmount;
	}
	
}
