package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

/** @author tejas.kotekar */
public class AccountContraVoucherEntryBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long coTranId;

    private Date coEntryDate;

    private String coEntryDateStr;

    private Date coDate;

    private String coDateStr;

    private String coVouchernumber;

    private Character coTypeFlag;

    private Long bankIdPay;

    private Long baAccountidPay;

    private Long fundIdPay;

    private Long functionIdPay;

    private Long fieldIdPay;

    private Long pacHeadIdPay;

    private Long sacHeadIdPay;

    private BigDecimal coAmountPay;

    private Long cpdModePay;

    private Long chequebookDetid;

    private Date coChequedate;

    private String coChequedateStr;

    private String coRemarkPay;

    private Long bankIdRec;

    private Long baAccountidRec;

    private Long fundIdRec;

    private Long functionIdRec;

    private Long fieldIdRec;

    private Long pacHeadIdRec;

    private Long sacHeadIdRec;

    private BigDecimal coAmountRec;

    private Double coAmountRecDesc;

    private Long cpdModeRec;

    private String coRemarkRec;

    private Double amtInCoins;

    private String authFlag;

    private Long authBy;

    private Date authDate;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long langId;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private String hasError;

    private Double total;

    private String pacHeadIdPayDesc;

    private String sacHeadIdPayDesc;

    private String pacHeadIdRecDesc;

    private String sacHeadIdRecDesc;

    private String successfulFlag;

    private String modeDescription;

    private String payTo;

    private String payee;

    private Long fieldId;

    private BigDecimal bankBalance;

    private Long departmentId;

    private Long chequeNo;

    private String amountStr;

    private String contraType;

    private String amountPayStr;

    private String amountRecStr;

    private String paymentModeDesc;

    private String templateExistFlag;
    private String transactionDate;

    private String voucherNo;

    private List<AccountContraVoucherCashDepBean> cashDep = new ArrayList<>();

    private List<TbSrcptFeesDetBean> receiptDetList = new ArrayList<>();

    private List<Object[]> denominationList;
    private List<AccountContraVoucherEntryBean> oaccountBeanList = new ArrayList<>();

    /** @return the coTranId */
    public Long getCoTranId() {
        return coTranId;
    }

    /**
     * @param coTranId the coTranId to set
     */
    public void setCoTranId(final Long coTranId) {
        this.coTranId = coTranId;
    }

    /** @return the coEntryDate */
    public Date getCoEntryDate() {
        return coEntryDate;
    }

    /**
     * @param coEntryDate the coEntryDate to set
     */
    public void setCoEntryDate(final Date coEntryDate) {
        this.coEntryDate = coEntryDate;
    }

    /** @return the coDate */
    public Date getCoDate() {
        return coDate;
    }

    /**
     * @param coDate the coDate to set
     */
    public void setCoDate(final Date coDate) {
        this.coDate = coDate;
    }

    /** @return the coVouchernumber */
    public String getCoVouchernumber() {
        return coVouchernumber;
    }

    /**
     * @param coVouchernumber the coVouchernumber to set
     */
    public void setCoVouchernumber(final String coVouchernumber) {
        this.coVouchernumber = coVouchernumber;
    }

    /** @return the coTypeFlag */
    public Character getCoTypeFlag() {
        return coTypeFlag;
    }

    /**
     * @param coTypeFlag the coTypeFlag to set
     */
    public void setCoTypeFlag(final Character coTypeFlag) {
        this.coTypeFlag = coTypeFlag;
    }

    /** @return the baAccountidPay */
    public Long getBaAccountidPay() {
        return baAccountidPay;
    }

    /**
     * @param baAccountidPay the baAccountidPay to set
     */
    public void setBaAccountidPay(final Long baAccountidPay) {
        this.baAccountidPay = baAccountidPay;
    }

    /** @return the fundIdPay */
    public Long getFundIdPay() {
        return fundIdPay;
    }

    /**
     * @param fundIdPay the fundIdPay to set
     */
    public void setFundIdPay(final Long fundIdPay) {
        this.fundIdPay = fundIdPay;
    }

    /** @return the functionIdPay */
    public Long getFunctionIdPay() {
        return functionIdPay;
    }

    /**
     * @param functionIdPay the functionIdPay to set
     */
    public void setFunctionIdPay(final Long functionIdPay) {
        this.functionIdPay = functionIdPay;
    }

    /** @return the fieldIdPay */
    public Long getFieldIdPay() {
        return fieldIdPay;
    }

    /**
     * @param fieldIdPay the fieldIdPay to set
     */
    public void setFieldIdPay(final Long fieldIdPay) {
        this.fieldIdPay = fieldIdPay;
    }

    /** @return the pacHeadIdPay */
    public Long getPacHeadIdPay() {
        return pacHeadIdPay;
    }

    /**
     * @param pacHeadIdPay the pacHeadIdPay to set
     */
    public void setPacHeadIdPay(final Long pacHeadIdPay) {
        this.pacHeadIdPay = pacHeadIdPay;
    }

    /** @return the sacHeadIdPay */
    public Long getSacHeadIdPay() {
        return sacHeadIdPay;
    }

    /**
     * @param sacHeadIdPay the sacHeadIdPay to set
     */
    public void setSacHeadIdPay(final Long sacHeadIdPay) {
        this.sacHeadIdPay = sacHeadIdPay;
    }

    /** @return the coAmountPay */
    public BigDecimal getCoAmountPay() {
        return coAmountPay;
    }

    /**
     * @param coAmountPay the coAmountPay to set
     */
    public void setCoAmountPay(final BigDecimal coAmountPay) {
        this.coAmountPay = coAmountPay;
    }

    /** @return the cpdModePay */
    public Long getCpdModePay() {
        return cpdModePay;
    }

    /**
     * @param cpdModePay the cpdModePay to set
     */
    public void setCpdModePay(final Long cpdModePay) {
        this.cpdModePay = cpdModePay;
    }

    /** @return the chequebookDetid */
    public Long getChequebookDetid() {
        return chequebookDetid;
    }

    /**
     * @param chequebookDetid the chequebookDetid to set
     */
    public void setChequebookDetid(final Long chequebookDetid) {
        this.chequebookDetid = chequebookDetid;
    }

    /** @return the coChequedate */
    public Date getCoChequedate() {
        return coChequedate;
    }

    /**
     * @param coChequedate the coChequedate to set
     */
    public void setCoChequedate(final Date coChequedate) {
        this.coChequedate = coChequedate;
    }

    /** @return the coRemarkPay */
    public String getCoRemarkPay() {
        return coRemarkPay;
    }

    /**
     * @param coRemarkPay the coRemarkPay to set
     */
    public void setCoRemarkPay(final String coRemarkPay) {
        this.coRemarkPay = coRemarkPay;
    }

    /** @return the baAccountidRec */
    public Long getBaAccountidRec() {
        return baAccountidRec;
    }

    /**
     * @param baAccountidRec the baAccountidRec to set
     */
    public void setBaAccountidRec(final Long baAccountidRec) {
        this.baAccountidRec = baAccountidRec;
    }

    /** @return the fundIdRec */
    public Long getFundIdRec() {
        return fundIdRec;
    }

    /**
     * @param fundIdRec the fundIdRec to set
     */
    public void setFundIdRec(final Long fundIdRec) {
        this.fundIdRec = fundIdRec;
    }

    /** @return the functionIdRec */
    public Long getFunctionIdRec() {
        return functionIdRec;
    }

    /**
     * @param functionIdRec the functionIdRec to set
     */
    public void setFunctionIdRec(final Long functionIdRec) {
        this.functionIdRec = functionIdRec;
    }

    /** @return the fieldIdRec */
    public Long getFieldIdRec() {
        return fieldIdRec;
    }

    /**
     * @param fieldIdRec the fieldIdRec to set
     */
    public void setFieldIdRec(final Long fieldIdRec) {
        this.fieldIdRec = fieldIdRec;
    }

    /** @return the pacHeadIdRec */
    public Long getPacHeadIdRec() {
        return pacHeadIdRec;
    }

    /**
     * @param pacHeadIdRec the pacHeadIdRec to set
     */
    public void setPacHeadIdRec(final Long pacHeadIdRec) {
        this.pacHeadIdRec = pacHeadIdRec;
    }

    /** @return the sacHeadIdRec */
    public Long getSacHeadIdRec() {
        return sacHeadIdRec;
    }

    /**
     * @param sacHeadIdRec the sacHeadIdRec to set
     */
    public void setSacHeadIdRec(final Long sacHeadIdRec) {
        this.sacHeadIdRec = sacHeadIdRec;
    }

    /** @return the coAmountRec */
    public BigDecimal getCoAmountRec() {
        return coAmountRec;
    }

    /**
     * @param coAmountRec the coAmountRec to set
     */
    public void setCoAmountRec(final BigDecimal coAmountRec) {
        this.coAmountRec = coAmountRec;
    }

    /** @return the cpdModeRec */
    public Long getCpdModeRec() {
        return cpdModeRec;
    }

    /**
     * @param cpdModeRec the cpdModeRec to set
     */
    public void setCpdModeRec(final Long cpdModeRec) {
        this.cpdModeRec = cpdModeRec;
    }

    /** @return the coRemarkRec */
    public String getCoRemarkRec() {
        return coRemarkRec;
    }

    /**
     * @param coRemarkRec the coRemarkRec to set
     */
    public void setCoRemarkRec(final String coRemarkRec) {
        this.coRemarkRec = coRemarkRec;
    }

    /** @return the amtInCoins */
    public Double getAmtInCoins() {
        return amtInCoins;
    }

    /**
     * @param amtInCoins the amtInCoins to set
     */
    public void setAmtInCoins(final Double amtInCoins) {
        this.amtInCoins = amtInCoins;
    }

    /** @return the authFlag */
    public String getAuthFlag() {
        return authFlag;
    }

    /**
     * @param authFlag the authFlag to set
     */
    public void setAuthFlag(final String authFlag) {
        this.authFlag = authFlag;
    }

    /** @return the authBy */
    public Long getAuthBy() {
        return authBy;
    }

    /**
     * @param authBy the authBy to set
     */
    public void setAuthBy(final Long authBy) {
        this.authBy = authBy;
    }

    /** @return the authDate */
    public Date getAuthDate() {
        return authDate;
    }

    /**
     * @param authDate the authDate to set
     */
    public void setAuthDate(final Date authDate) {
        this.authDate = authDate;
    }

    /** @return the orgId */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /** @return the createdBy */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /** @return the lmodDate */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setCreatedDate(final Date lmodDate) {
        createdDate = lmodDate;
    }

    /** @return the langId */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /** @return the updatedBy */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** @return the updatedDate */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /** @return the lgIpMac */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /** @return the lgIpMacUpd */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /** @return the bankIDPay */
    public Long getBankIdPay() {
        return bankIdPay;
    }

    /**
     * @param bankIDPay the bankIDPay to set
     */
    public void setBankIdPay(final Long bankIdPay) {
        this.bankIdPay = bankIdPay;
    }

    /** @return the bankIdRec */
    public Long getBankIdRec() {
        return bankIdRec;
    }

    /**
     * @param bankIdRec the bankIdRec to set
     */
    public void setBankIdRec(final Long bankIdRec) {
        this.bankIdRec = bankIdRec;
    }

    /** @return the hasError */
    public String getHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    /** @return the coEntryDateStr */
    public String getCoEntryDateStr() {
        return coEntryDateStr;
    }

    /**
     * @param coEntryDateStr the coEntryDateStr to set
     */
    public void setCoEntryDateStr(final String coEntryDateStr) {
        this.coEntryDateStr = coEntryDateStr;
    }

    /** @return the coDateStr */
    public String getCoDateStr() {
        return coDateStr;
    }

    /**
     * @param coDateStr the coDateStr to set
     */
    public void setCoDateStr(final String coDateStr) {
        this.coDateStr = coDateStr;
    }

    /** @return the coChequedateStr */
    public String getCoChequedateStr() {
        return coChequedateStr;
    }

    /**
     * @param coChequedateStr the coChequedateStr to set
     */
    public void setCoChequedateStr(final String coChequedateStr) {
        this.coChequedateStr = coChequedateStr;
    }

    /** @return the cashDep */
    public List<AccountContraVoucherCashDepBean> getCashDep() {
        return cashDep;
    }

    /**
     * @param cashDep the cashDep to set
     */
    public void setCashDep(final List<AccountContraVoucherCashDepBean> cashDep) {
        this.cashDep = cashDep;
    }

    /** @return the total */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(final Double total) {
        this.total = total;
    }

    /** @return the pacHeadIdPayDesc */
    public String getPacHeadIdPayDesc() {
        return pacHeadIdPayDesc;
    }

    /**
     * @param pacHeadIdPayDesc the pacHeadIdPayDesc to set
     */
    public void setPacHeadIdPayDesc(final String pacHeadIdPayDesc) {
        this.pacHeadIdPayDesc = pacHeadIdPayDesc;
    }

    /** @return the sacHeadIdPayDesc */
    public String getSacHeadIdPayDesc() {
        return sacHeadIdPayDesc;
    }

    /**
     * @param sacHeadIdPayDesc the sacHeadIdPayDesc to set
     */
    public void setSacHeadIdPayDesc(final String sacHeadIdPayDesc) {
        this.sacHeadIdPayDesc = sacHeadIdPayDesc;
    }

    /** @return the pacHeadIdRecDesc */
    public String getPacHeadIdRecDesc() {
        return pacHeadIdRecDesc;
    }

    /**
     * @param pacHeadIdRecDesc the pacHeadIdRecDesc to set
     */
    public void setPacHeadIdRecDesc(final String pacHeadIdRecDesc) {
        this.pacHeadIdRecDesc = pacHeadIdRecDesc;
    }

    /** @return the sacHeadIdRecDesc */
    public String getSacHeadIdRecDesc() {
        return sacHeadIdRecDesc;
    }

    /**
     * @param sacHeadIdRecDesc the sacHeadIdRecDesc to set
     */
    public void setSacHeadIdRecDesc(final String sacHeadIdRecDesc) {
        this.sacHeadIdRecDesc = sacHeadIdRecDesc;
    }

    /**
     * @return the receiptDetList
     */
    public List<TbSrcptFeesDetBean> getReceiptDetList() {
        return receiptDetList;
    }

    /**
     * @param receiptDetList the receiptDetList to set
     */
    public void setReceiptDetList(final List<TbSrcptFeesDetBean> receiptDetList) {
        this.receiptDetList = receiptDetList;
    }

    /**
     * @return the coAmountRecDesc
     */
    public Double getCoAmountRecDesc() {
        return coAmountRecDesc;
    }

    /**
     * @param coAmountRecDesc the coAmountRecDesc to set
     */
    public void setCoAmountRecDesc(final Double coAmountRecDesc) {
        this.coAmountRecDesc = coAmountRecDesc;
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
     * @return the modeDescription
     */
    public String getModeDescription() {
        return modeDescription;
    }

    /**
     * @param modeDescription the modeDescription to set
     */
    public void setModeDescription(final String modeDescription) {
        this.modeDescription = modeDescription;
    }

    /**
     * @return the payTo
     */
    public String getPayTo() {
        return payTo;
    }

    /**
     * @param payTo the payTo to set
     */
    public void setPayTo(final String payTo) {
        this.payTo = payTo;
    }

    /**
     * @return the bankBalance
     */
    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    /**
     * @param bankBalance the bankBalance to set
     */
    public void setBankBalance(final BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    /**
     * @return the denominationList
     */
    public List<Object[]> getDenominationList() {
        return denominationList;
    }

    /**
     * @param denominationList the denominationList to set
     */
    public void setDenominationList(final List<Object[]> denominationList) {
        this.denominationList = denominationList;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(final String payee) {
        this.payee = payee;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(final Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(final Long chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(final String amountStr) {
        this.amountStr = amountStr;
    }

    public String getContraType() {
        return contraType;
    }

    public void setContraType(final String contraType) {
        this.contraType = contraType;
    }

    public String getAmountPayStr() {
        return amountPayStr;
    }

    public void setAmountPayStr(final String amountPayStr) {
        this.amountPayStr = amountPayStr;
    }

    public String getAmountRecStr() {
        return amountRecStr;
    }

    public void setAmountRecStr(final String amountRecStr) {
        this.amountRecStr = amountRecStr;
    }

    public String getPaymentModeDesc() {
        return paymentModeDesc;
    }

    public void setPaymentModeDesc(final String paymentModeDesc) {
        this.paymentModeDesc = paymentModeDesc;
    }

    public String getTemplateExistFlag() {
        return templateExistFlag;
    }

    public void setTemplateExistFlag(final String templateExistFlag) {
        this.templateExistFlag = templateExistFlag;
    }

    public List<AccountContraVoucherEntryBean> getOaccountBeanList() {
        return oaccountBeanList;
    }

    public void setOaccountBeanList(final List<AccountContraVoucherEntryBean> oaccountBeanList) {
        this.oaccountBeanList = oaccountBeanList;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("AccountContraVoucherEntryBean [coTranId=");
        builder.append(coTranId);
        builder.append(", coEntryDate=");
        builder.append(coEntryDate);
        builder.append(", coEntryDateStr=");
        builder.append(coEntryDateStr);
        builder.append(", coDate=");
        builder.append(coDate);
        builder.append(", coDateStr=");
        builder.append(coDateStr);
        builder.append(", coVouchernumber=");
        builder.append(coVouchernumber);
        builder.append(", coTypeFlag=");
        builder.append(coTypeFlag);
        builder.append(", bankIdPay=");
        builder.append(bankIdPay);
        builder.append(", baAccountidPay=");
        builder.append(baAccountidPay);
        builder.append(", fundIdPay=");
        builder.append(fundIdPay);
        builder.append(", functionIdPay=");
        builder.append(functionIdPay);
        builder.append(", fieldIdPay=");
        builder.append(fieldIdPay);
        builder.append(", pacHeadIdPay=");
        builder.append(pacHeadIdPay);
        builder.append(", sacHeadIdPay=");
        builder.append(sacHeadIdPay);
        builder.append(", coAmountPay=");
        builder.append(coAmountPay);
        builder.append(", cpdModePay=");
        builder.append(cpdModePay);
        builder.append(", chequebookDetid=");
        builder.append(chequebookDetid);
        builder.append(", coChequedate=");
        builder.append(coChequedate);
        builder.append(", coChequedateStr=");
        builder.append(coChequedateStr);
        builder.append(", coRemarkPay=");
        builder.append(coRemarkPay);
        builder.append(", bankIdRec=");
        builder.append(bankIdRec);
        builder.append(", baAccountidRec=");
        builder.append(baAccountidRec);
        builder.append(", fundIdRec=");
        builder.append(fundIdRec);
        builder.append(", functionIdRec=");
        builder.append(functionIdRec);
        builder.append(", fieldIdRec=");
        builder.append(fieldIdRec);
        builder.append(", pacHeadIdRec=");
        builder.append(pacHeadIdRec);
        builder.append(", sacHeadIdRec=");
        builder.append(sacHeadIdRec);
        builder.append(", coAmountRec=");
        builder.append(coAmountRec);
        builder.append(", coAmountRecDesc=");
        builder.append(coAmountRecDesc);
        builder.append(", cpdModeRec=");
        builder.append(cpdModeRec);
        builder.append(", coRemarkRec=");
        builder.append(coRemarkRec);
        builder.append(", amtInCoins=");
        builder.append(amtInCoins);
        builder.append(", authFlag=");
        builder.append(authFlag);
        builder.append(", authBy=");
        builder.append(authBy);
        builder.append(", authDate=");
        builder.append(authDate);
        builder.append(", orgId=");
        builder.append(orgId);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", langId=");
        builder.append(langId);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append(", hasError=");
        builder.append(hasError);
        builder.append(", total=");
        builder.append(total);
        builder.append(", pacHeadIdPayDesc=");
        builder.append(pacHeadIdPayDesc);
        builder.append(", sacHeadIdPayDesc=");
        builder.append(sacHeadIdPayDesc);
        builder.append(", pacHeadIdRecDesc=");
        builder.append(pacHeadIdRecDesc);
        builder.append(", sacHeadIdRecDesc=");
        builder.append(sacHeadIdRecDesc);
        builder.append(", successfulFlag=");
        builder.append(successfulFlag);
        builder.append(", modeDescription=");
        builder.append(modeDescription);
        builder.append(", payTo=");
        builder.append(payTo);
        builder.append(", payee=");
        builder.append(payee);
        builder.append(", fieldId=");
        builder.append(fieldId);
        builder.append(", bankBalance=");
        builder.append(bankBalance);
        builder.append(", departmentId=");
        builder.append(departmentId);
        builder.append(", chequeNo=");
        builder.append(chequeNo);
        builder.append(", amountStr=");
        builder.append(amountStr);
        builder.append(", contraType=");
        builder.append(contraType);
        builder.append(", amountPayStr=");
        builder.append(amountPayStr);
        builder.append(", amountRecStr=");
        builder.append(amountRecStr);
        builder.append(", paymentModeDesc=");
        builder.append(paymentModeDesc);
        builder.append(", templateExistFlag=");
        builder.append(templateExistFlag);
        builder.append(", transactionDate=");
        builder.append(transactionDate);
        builder.append(", cashDep=");
        builder.append(cashDep);
        builder.append(", receiptDetList=");
        builder.append(receiptDetList);
        builder.append(", denominationList=");
        builder.append(denominationList);
        builder.append(", oaccountBeanList=");
        builder.append(oaccountBeanList);
        builder.append("]");
        return builder.toString();
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

}
