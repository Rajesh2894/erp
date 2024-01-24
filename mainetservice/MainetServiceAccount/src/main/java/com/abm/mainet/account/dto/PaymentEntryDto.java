package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tejas.kotekar
 *
 */
public class PaymentEntryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long billTypeId;

    private String billNo;

    private String billDate;

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

    private Long budgetCodeId;

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

    private Long paymentMode;

    private Long bankAcId;

    private Long instrumentNo;
    private Long instrumentNumber;
    private Long cpdIdStatus;
    private String instrumentDate;

    private BigDecimal totalPaymentAmount;

    private String narration;

    private String bankacname;

    private String InsttDate;

    private String paymentNo;
    private String transactionDate;
    private BigDecimal paymentAmount;
    private Long expHeadId;
    private Long paymentId;
    private String viewURL;
    private Long sacHeadId;

    private String modeDesc;
    private String modeCode;
    private String payeeName;

    private String dupTransactionDate;
    private Long dupVendorId;
    private Long dupBillTypeId;
    private Long tdsTypeId;
    private String tdsTypeDesc;

    private Long qtrId;
    private String cpIdQtr;
    private String isEmpty;
    private String total;
    private String challanNo;
    private String challanDate;
    private String ackNo;
    private String ackDate;
    private String chargeAmt;
    private String tdsAmt;
    private String tdsPaidAmt;
    private String balanceAmt;
    private String totalTdsAmt;
    private String totalTdsPaidAmt;
    private String totalBalAmt;

    private String paymentIdFlagExist;

    private List<PaymentDetailsDto> paymentDetailsDto;

    private List<TbAcVendormaster> vendorDetails;

    private List<PaymentEntryDto> listOfdedRegister = new ArrayList<>();

    private Long paymentTypeFlag;

    private Long paymentTypeId;
    private String paymentTypeDesc;
    private String paymentTypeCode;

    private String utrNo;

    private String billRefNo;
    
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList;
    private String removeFileById;

    // private List<PaymentEntryDto> tdsDteailsDto;

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

    public Date getBillEntryDateDt() {
        return billEntryDateDt;
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

    public String getNarration() {
        return narration;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
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

    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
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

    public List<PaymentDetailsDto> getPaymentDetailsDto() {
        return paymentDetailsDto;
    }

    public void setPaymentDetailsDto(final List<PaymentDetailsDto> billDetailsDto) {
        paymentDetailsDto = billDetailsDto;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(final BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(final Long paymentMode) {
        this.paymentMode = paymentMode;
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

    public Long getInstrumentNumber() {
        return instrumentNumber;
    }

    public void setInstrumentNumber(Long instrumentNumber) {
        this.instrumentNumber = instrumentNumber;
    }

    public Long getCpdIdStatus() {
        return cpdIdStatus;
    }

    public void setCpdIdStatus(Long cpdIdStatus) {
        this.cpdIdStatus = cpdIdStatus;
    }

    public String getInstrumentDate() {
        return instrumentDate;
    }

    public void setInstrumentDate(final String instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(final String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(final BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getExpHeadId() {
        return expHeadId;
    }

    public void setExpHeadId(final Long expHeadId) {
        this.expHeadId = expHeadId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getViewURL() {
        return viewURL;
    }

    public void setViewURL(final String viewURL) {
        this.viewURL = viewURL;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public String getModeDesc() {
        return modeDesc;
    }

    public void setModeDesc(String modeDesc) {
        this.modeDesc = modeDesc;
    }

    public String getBankacname() {
        return bankacname;
    }

    public void setBankacname(String bankacname) {
        this.bankacname = bankacname;
    }

    public String getInsttDate() {
        return InsttDate;
    }

    public void setInsttDate(String insttDate) {
        InsttDate = insttDate;
    }

    public String getModeCode() {
        return modeCode;
    }

    public void setModeCode(String modeCode) {
        this.modeCode = modeCode;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getDupTransactionDate() {
        return dupTransactionDate;
    }

    public void setDupTransactionDate(String dupTransactionDate) {
        this.dupTransactionDate = dupTransactionDate;
    }

    public Long getDupVendorId() {
        return dupVendorId;
    }

    public void setDupVendorId(Long dupVendorId) {
        this.dupVendorId = dupVendorId;
    }

    public Long getDupBillTypeId() {
        return dupBillTypeId;
    }

    public void setDupBillTypeId(Long dupBillTypeId) {
        this.dupBillTypeId = dupBillTypeId;
    }

    public Long getTdsTypeId() {
        return tdsTypeId;
    }

    public void setTdsTypeId(Long tdsTypeId) {
        this.tdsTypeId = tdsTypeId;
    }

    public List<TbAcVendormaster> getVendorDetails() {
        return vendorDetails;
    }

    public void setVendorDetails(List<TbAcVendormaster> vendorDetails) {
        this.vendorDetails = vendorDetails;
    }

    public String getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty = isEmpty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Long getPaymentTypeFlag() {
        return paymentTypeFlag;
    }

    public void setPaymentTypeFlag(Long paymentTypeFlag) {
        this.paymentTypeFlag = paymentTypeFlag;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getCpIdQtr() {
        return cpIdQtr;
    }

    public void setCpIdQtr(String cpIdQtr) {
        this.cpIdQtr = cpIdQtr;
    }

    public String getAckNo() {
        return ackNo;
    }

    public void setAckNo(String ackNo) {
        this.ackNo = ackNo;
    }

    public String getAckDate() {
        return ackDate;
    }

    public void setAckDate(String ackDate) {
        this.ackDate = ackDate;
    }

    public String getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(String challanDate) {
        this.challanDate = challanDate;
    }

    public Long getQtrId() {
        return qtrId;
    }

    public void setQtrId(Long qtrId) {
        this.qtrId = qtrId;
    }

    public String getPaymentIdFlagExist() {
        return paymentIdFlagExist;
    }

    public void setPaymentIdFlagExist(String paymentIdFlagExist) {
        this.paymentIdFlagExist = paymentIdFlagExist;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    public void setPaymentTypeDesc(String paymentTypeDesc) {
        this.paymentTypeDesc = paymentTypeDesc;
    }

    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }
  
   public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

	@Override
    public String toString() {
        return "PaymentEntryDto [id=" + id + ", billTypeId=" + billTypeId + ", billNo=" + billNo + ", billDate="
                + billDate + ", paymentDate=" + paymentDate + ", paymentEntryDate=" + paymentEntryDate
                + ", billEntryDateDt=" + billEntryDateDt + ", vendorId=" + vendorId + ", vendorCodeDescription="
                + vendorCodeDescription + ", vendorName=" + vendorName + ", vendorDesc=" + vendorDesc
                + ", billTotalAmount=" + billTotalAmount + ", balanceAmount=" + balanceAmount
                + ", checkerAuthorization=" + checkerAuthorization + ", checkerRemarks=" + checkerRemarks
                + ", checkerUser=" + checkerUser + ", checkerDate=" + checkerDate + ", orgId=" + orgId + ", createdBy="
                + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", languageId=" + languageId + ", lgIpMacAddress=" + lgIpMacAddress + ", hasError="
                + hasError + ", projectedExpenditureId=" + projectedExpenditureId + ", newBalanceAmount="
                + newBalanceAmount + ", totalBillAmount=" + totalBillAmount + ", totalSanctionedAmount="
                + totalSanctionedAmount + ", totalDisallowedAmount=" + totalDisallowedAmount + ", totalDeductions="
                + totalDeductions + ", netPayable=" + netPayable + ", successfulFlag=" + successfulFlag + ", dataFlag="
                + dataFlag + ", fromDate=" + fromDate + ", toDate=" + toDate + ", budgetCodeId=" + budgetCodeId
                + ", payee=" + payee + ", billAmountStr=" + billAmountStr + ", deductionsStr=" + deductionsStr
                + ", netPayableStr=" + netPayableStr + ", billTypeDesc=" + billTypeDesc + ", invoiceValueStr="
                + invoiceValueStr + ", totalBillAmountStr=" + totalBillAmountStr + ", totalSanctionedAmtStr="
                + totalSanctionedAmtStr + ", totalDisallowedAmountStr=" + totalDisallowedAmountStr
                + ", dedcutionAmountStr=" + dedcutionAmountStr + ", totalDedcutionsAmountStr="
                + totalDedcutionsAmountStr + ", netPayabletStr=" + netPayabletStr + ", actualAmountStr="
                + actualAmountStr + ", sanctionedAmountStr=" + sanctionedAmountStr + ", disallowedRemark="
                + disallowedRemark + ", isMakerChecker=" + isMakerChecker + ", organisation=" + organisation
                + ", authorizationMode=" + authorizationMode + ", authorizationStatus=" + authorizationStatus
                + ", authorizerEmployee=" + authorizerEmployee + ", templateExistFlag=" + templateExistFlag
                + ", fieldId=" + fieldId + ", paymentMode=" + paymentMode + ", bankAcId=" + bankAcId + ", instrumentNo="
                + instrumentNo + ", instrumentDate=" + instrumentDate + ", totalPaymentAmount=" + totalPaymentAmount
                + ", narration=" + narration + ", bankacname=" + bankacname + ", InsttDate=" + InsttDate
                + ", paymentNo=" + paymentNo + ", transactionDate=" + transactionDate + ", paymentAmount="
                + paymentAmount + ", expHeadId=" + expHeadId + ", paymentId=" + paymentId + ", viewURL=" + viewURL
                + ", sacHeadId=" + sacHeadId + ", modeDesc=" + modeDesc + ", modeCode=" + modeCode + ", payeeName="
                + payeeName + ", dupTransactionDate=" + dupTransactionDate + ", dupVendorId=" + dupVendorId
                + ", dupBillTypeId=" + dupBillTypeId + ", tdsTypeId=" + tdsTypeId + ", qtrId=" + qtrId + ", cpIdQtr="
                + cpIdQtr + ", isEmpty=" + isEmpty + ", total=" + total + ", challanNo=" + challanNo + ", challanDate="
                + challanDate + ", ackNo=" + ackNo + ", ackDate=" + ackDate + ", paymentIdFlagExist="
                + paymentIdFlagExist + ", paymentDetailsDto=" + paymentDetailsDto + ", vendorDetails=" + vendorDetails
                + ", paymentTypeFlag=" + paymentTypeFlag + ", paymentTypeId=" + paymentTypeId + ", paymentTypeDesc="
                + paymentTypeDesc + ", paymentTypeCode=" + paymentTypeCode + "]";
    }

    public String getChargeAmt() {
        return chargeAmt;
    }

    public void setChargeAmt(String chargeAmt) {
        this.chargeAmt = chargeAmt;
    }

    public List<PaymentEntryDto> getListOfdedRegister() {
        return listOfdedRegister;
    }

    public void setListOfdedRegister(List<PaymentEntryDto> listOfdedRegister) {
        this.listOfdedRegister = listOfdedRegister;
    }

    public String getTdsAmt() {
        return tdsAmt;
    }

    public void setTdsAmt(String tdsAmt) {
        this.tdsAmt = tdsAmt;
    }

    public String getTdsPaidAmt() {
        return tdsPaidAmt;
    }

    public void setTdsPaidAmt(String tdsPaidAmt) {
        this.tdsPaidAmt = tdsPaidAmt;
    }

    public String getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(String balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public String getTotalTdsAmt() {
        return totalTdsAmt;
    }

    public void setTotalTdsAmt(String totalTdsAmt) {
        this.totalTdsAmt = totalTdsAmt;
    }

    public String getTotalTdsPaidAmt() {
        return totalTdsPaidAmt;
    }

    public void setTotalTdsPaidAmt(String totalTdsPaidAmt) {
        this.totalTdsPaidAmt = totalTdsPaidAmt;
    }

    public String getTotalBalAmt() {
        return totalBalAmt;
    }

    public void setTotalBalAmt(String totalBalAmt) {
        this.totalBalAmt = totalBalAmt;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public String getBillRefNo() {
        return billRefNo;
    }

    public void setBillRefNo(String billRefNo) {
        this.billRefNo = billRefNo;
    }

    public String getTdsTypeDesc() {
        return tdsTypeDesc;
    }

    public void setTdsTypeDesc(String tdsTypeDesc) {
        this.tdsTypeDesc = tdsTypeDesc;
    }

}