package com.abm.mainet.account.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountYearEndProcessDTO {

    private static final long serialVersionUID = 8683880608834172574L;
    private String alreadyExists = "N";
    private Long faYearid;
    private Long sacHeadId;
    private String hasError;

    private Long orgid;
    private Long updatedBy;
    private Date updatedDate;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long userId;
    private int langId;
    private Date lmoddate;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    private String accountCode;
    private BigDecimal openingDrAmount;
    private BigDecimal openingCrAmount;
    private BigDecimal closingDrAmount;
    private BigDecimal closingCrAmount;
    private BigDecimal transactionDrAmount;
    private BigDecimal transactionCrAmount;
    private BigDecimal sumClosingCR;
    private BigDecimal sumClosingDR;
    private String fromDate;
    private String toDate;
    private BigDecimal sumOpeningCR;
    private BigDecimal sumOpeningDR;
    private BigDecimal sumTransactionDR;
    private BigDecimal sumTransactionCR;

    private List<AccountYearEndProcessDTO> listOfSum = new ArrayList<>();

    public String getAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public Long getFaYearid() {
        return faYearid;
    }

    public void setFaYearid(Long faYearid) {
        this.faYearid = faYearid;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<AccountYearEndProcessDTO> getListOfSum() {
        return listOfSum;
    }

    public void setListOfSum(List<AccountYearEndProcessDTO> listOfSum) {
        this.listOfSum = listOfSum;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
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

    public String getHasError() {
        return hasError;
    }

    public void setHasError(String hasError) {
        this.hasError = hasError;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

}
