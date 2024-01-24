package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BankReconciliationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Long bankReconciliationId;

    private Long receiptModeId;

    private String bankReconIds;

    private String clearanceDateIds;

    private String transactionMode;

    private String transactionDate;

    private String  transactionNo;

    private String type;

    private int index;

    private String hasError;

    private String alreadyExists;

    private Long number;

    private String date;

    private String amount;

    private String serchType;

    private String bankAccount;

    private String chequeddno;

    private String chequedddate;

    private String remarks;

    private String flag;

    private String formDate;

    private String toDate;

    private Long orgid;

    private Long userId;

    private int langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    private Long statusId;

    private String previousDate;

    private String balanceAsUlb;

    private String bankBalanceAsperStatement;

    private BigDecimal totalReceiptAmount;

    private BigDecimal totalPaymentAmount;

    private Long depositSlipId;

    private String transMode;

    private String transType;
    @Transient
    private String uploadFileName;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    private List<BankReconciliationDTO> bankReconciliationDTO = new ArrayList<>();

    private List<BankReconciliationDTO> listofStatusId = new ArrayList<>();

    private Map<String, String> bankReconMap = new LinkedHashMap<>();

    private String categoryId;

    private String paymentCategoryId;

    private String successfulFlag;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getBankReconciliationId() {
        return bankReconciliationId;
    }

    public void setBankReconciliationId(final Long bankReconciliationId) {
        this.bankReconciliationId = bankReconciliationId;
    }

    public Long getReceiptModeId() {
        return receiptModeId;
    }

    public void setReceiptModeId(final Long receiptModeId) {
        this.receiptModeId = receiptModeId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(final String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(final String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getChequeddno() {
        return chequeddno;
    }

    public void setChequeddno(final String chequeddno) {
        this.chequeddno = chequeddno;
    }

    public String getChequedddate() {
        return chequedddate;
    }

    public void setChequedddate(final String chequedddate) {
        this.chequedddate = chequedddate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public List<BankReconciliationDTO> getBankReconciliationDTO() {
        return bankReconciliationDTO;
    }

    public void setBankReconciliationDTO(final List<BankReconciliationDTO> bankReconciliationDTO) {
        this.bankReconciliationDTO = bankReconciliationDTO;
    }

    public String getSerchType() {
        return serchType;
    }

    public void setSerchType(final String serchType) {
        this.serchType = serchType;
    }

    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(final String formDate) {
        this.formDate = formDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(final String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getBankReconIds() {
        return bankReconIds;
    }

    public void setBankReconIds(final String bankReconIds) {
        this.bankReconIds = bankReconIds;
    }

    public String getClearanceDateIds() {
        return clearanceDateIds;
    }

    public void setClearanceDateIds(final String clearanceDateIds) {
        this.clearanceDateIds = clearanceDateIds;
    }

    public Map<String, String> getBankReconMap() {
        return bankReconMap;
    }

    public void setBankReconMap(final Map<String, String> bankReconMap) {
        this.bankReconMap = bankReconMap;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSuccessfulFlag() {
        return successfulFlag;
    }

    public void setSuccessfulFlag(String successfulFlag) {
        this.successfulFlag = successfulFlag;
    }

    public String getPaymentCategoryId() {
        return paymentCategoryId;
    }

    public void setPaymentCategoryId(String paymentCategoryId) {
        this.paymentCategoryId = paymentCategoryId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(String previousDate) {
        this.previousDate = previousDate;
    }

    public List<BankReconciliationDTO> getListofStatusId() {
        return listofStatusId;
    }

    public void setListofStatusId(List<BankReconciliationDTO> listofStatusId) {
        this.listofStatusId = listofStatusId;
    }

    public BigDecimal getTotalReceiptAmount() {
        return totalReceiptAmount;
    }

    public void setTotalReceiptAmount(BigDecimal totalReceiptAmount) {
        this.totalReceiptAmount = totalReceiptAmount;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public String getBalanceAsUlb() {
        return balanceAsUlb;
    }

    public void setBalanceAsUlb(String balanceAsUlb) {
        this.balanceAsUlb = balanceAsUlb;
    }

    public String getBankBalanceAsperStatement() {
        return bankBalanceAsperStatement;
    }

    public void setBankBalanceAsperStatement(String bankBalanceAsperStatement) {
        this.bankBalanceAsperStatement = bankBalanceAsperStatement;
    }

    public Long getDepositSlipId() {
        return depositSlipId;
    }

    public void setDepositSlipId(Long depositSlipId) {
        this.depositSlipId = depositSlipId;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

}
