package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.acccount.dto.AccountBudgetCodeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountContraVoucherReportBean implements Serializable {
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
    private String vouchersubType;
    private String voucherType;
    private String narration;
    private String departmentDesc;
    private String OrganizationName;
    private BigDecimal totalAmount;
    private Long budgetCodeId;
    private String OrganizationNameReg;
    private List<AccountContraVoucherReportAccountheadBean> oacBeanList = new ArrayList<>();

    private AccountBudgetCodeDto budgetCode;
    private String accountHeadDesc;

    public Long getCoTranId() {
        return coTranId;
    }

    public void setCoTranId(final Long coTranId) {
        this.coTranId = coTranId;
    }

    public Date getCoEntryDate() {
        return coEntryDate;
    }

    public void setCoEntryDate(final Date coEntryDate) {
        this.coEntryDate = coEntryDate;
    }

    public String getCoEntryDateStr() {
        return coEntryDateStr;
    }

    public void setCoEntryDateStr(final String coEntryDateStr) {
        this.coEntryDateStr = coEntryDateStr;
    }

    public Date getCoDate() {
        return coDate;
    }

    public void setCoDate(final Date coDate) {
        this.coDate = coDate;
    }

    public String getCoDateStr() {
        return coDateStr;
    }

    public void setCoDateStr(final String coDateStr) {
        this.coDateStr = coDateStr;
    }

    public String getCoVouchernumber() {
        return coVouchernumber;
    }

    public void setCoVouchernumber(final String coVouchernumber) {
        this.coVouchernumber = coVouchernumber;
    }

    public Character getCoTypeFlag() {
        return coTypeFlag;
    }

    public void setCoTypeFlag(final Character coTypeFlag) {
        this.coTypeFlag = coTypeFlag;
    }

    public Long getBankIdPay() {
        return bankIdPay;
    }

    public void setBankIdPay(final Long bankIdPay) {
        this.bankIdPay = bankIdPay;
    }

    public Long getBaAccountidPay() {
        return baAccountidPay;
    }

    public void setBaAccountidPay(final Long baAccountidPay) {
        this.baAccountidPay = baAccountidPay;
    }

    public Long getFundIdPay() {
        return fundIdPay;
    }

    public void setFundIdPay(final Long fundIdPay) {
        this.fundIdPay = fundIdPay;
    }

    public Long getFunctionIdPay() {
        return functionIdPay;
    }

    public void setFunctionIdPay(final Long functionIdPay) {
        this.functionIdPay = functionIdPay;
    }

    public Long getFieldIdPay() {
        return fieldIdPay;
    }

    public void setFieldIdPay(final Long fieldIdPay) {
        this.fieldIdPay = fieldIdPay;
    }

    public Long getPacHeadIdPay() {
        return pacHeadIdPay;
    }

    public void setPacHeadIdPay(final Long pacHeadIdPay) {
        this.pacHeadIdPay = pacHeadIdPay;
    }

    public Long getSacHeadIdPay() {
        return sacHeadIdPay;
    }

    public void setSacHeadIdPay(final Long sacHeadIdPay) {
        this.sacHeadIdPay = sacHeadIdPay;
    }

    public BigDecimal getCoAmountPay() {
        return coAmountPay;
    }

    public void setCoAmountPay(final BigDecimal coAmountPay) {
        this.coAmountPay = coAmountPay;
    }

    public Long getCpdModePay() {
        return cpdModePay;
    }

    public void setCpdModePay(final Long cpdModePay) {
        this.cpdModePay = cpdModePay;
    }

    public Long getChequebookDetid() {
        return chequebookDetid;
    }

    public void setChequebookDetid(final Long chequebookDetid) {
        this.chequebookDetid = chequebookDetid;
    }

    public Date getCoChequedate() {
        return coChequedate;
    }

    public void setCoChequedate(final Date coChequedate) {
        this.coChequedate = coChequedate;
    }

    public String getCoChequedateStr() {
        return coChequedateStr;
    }

    public void setCoChequedateStr(final String coChequedateStr) {
        this.coChequedateStr = coChequedateStr;
    }

    public String getCoRemarkPay() {
        return coRemarkPay;
    }

    public void setCoRemarkPay(final String coRemarkPay) {
        this.coRemarkPay = coRemarkPay;
    }

    public Long getBankIdRec() {
        return bankIdRec;
    }

    public void setBankIdRec(final Long bankIdRec) {
        this.bankIdRec = bankIdRec;
    }

    public Long getBaAccountidRec() {
        return baAccountidRec;
    }

    public void setBaAccountidRec(final Long baAccountidRec) {
        this.baAccountidRec = baAccountidRec;
    }

    public Long getFundIdRec() {
        return fundIdRec;
    }

    public void setFundIdRec(final Long fundIdRec) {
        this.fundIdRec = fundIdRec;
    }

    public Long getFunctionIdRec() {
        return functionIdRec;
    }

    public void setFunctionIdRec(final Long functionIdRec) {
        this.functionIdRec = functionIdRec;
    }

    public Long getFieldIdRec() {
        return fieldIdRec;
    }

    public void setFieldIdRec(final Long fieldIdRec) {
        this.fieldIdRec = fieldIdRec;
    }

    public Long getPacHeadIdRec() {
        return pacHeadIdRec;
    }

    public void setPacHeadIdRec(final Long pacHeadIdRec) {
        this.pacHeadIdRec = pacHeadIdRec;
    }

    public Long getSacHeadIdRec() {
        return sacHeadIdRec;
    }

    public void setSacHeadIdRec(final Long sacHeadIdRec) {
        this.sacHeadIdRec = sacHeadIdRec;
    }

    public BigDecimal getCoAmountRec() {
        return coAmountRec;
    }

    public void setCoAmountRec(final BigDecimal coAmountRec) {
        this.coAmountRec = coAmountRec;
    }

    public Double getCoAmountRecDesc() {
        return coAmountRecDesc;
    }

    public void setCoAmountRecDesc(final Double coAmountRecDesc) {
        this.coAmountRecDesc = coAmountRecDesc;
    }

    public Long getCpdModeRec() {
        return cpdModeRec;
    }

    public void setCpdModeRec(final Long cpdModeRec) {
        this.cpdModeRec = cpdModeRec;
    }

    public String getCoRemarkRec() {
        return coRemarkRec;
    }

    public void setCoRemarkRec(final String coRemarkRec) {
        this.coRemarkRec = coRemarkRec;
    }

    public Double getAmtInCoins() {
        return amtInCoins;
    }

    public void setAmtInCoins(final Double amtInCoins) {
        this.amtInCoins = amtInCoins;
    }

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(final String authFlag) {
        this.authFlag = authFlag;
    }

    public Long getAuthBy() {
        return authBy;
    }

    public void setAuthBy(final Long authBy) {
        this.authBy = authBy;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(final Date authDate) {
        this.authDate = authDate;
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

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(final Double total) {
        this.total = total;
    }

    public String getPacHeadIdPayDesc() {
        return pacHeadIdPayDesc;
    }

    public void setPacHeadIdPayDesc(final String pacHeadIdPayDesc) {
        this.pacHeadIdPayDesc = pacHeadIdPayDesc;
    }

    public String getSacHeadIdPayDesc() {
        return sacHeadIdPayDesc;
    }

    public void setSacHeadIdPayDesc(final String sacHeadIdPayDesc) {
        this.sacHeadIdPayDesc = sacHeadIdPayDesc;
    }

    public String getPacHeadIdRecDesc() {
        return pacHeadIdRecDesc;
    }

    public void setPacHeadIdRecDesc(final String pacHeadIdRecDesc) {
        this.pacHeadIdRecDesc = pacHeadIdRecDesc;
    }

    public String getSacHeadIdRecDesc() {
        return sacHeadIdRecDesc;
    }

    public void setSacHeadIdRecDesc(final String sacHeadIdRecDesc) {
        this.sacHeadIdRecDesc = sacHeadIdRecDesc;
    }

    public String getSuccessfulFlag() {
        return successfulFlag;
    }

    public void setSuccessfulFlag(final String successfulFlag) {
        this.successfulFlag = successfulFlag;
    }

    public String getModeDescription() {
        return modeDescription;
    }

    public void setModeDescription(final String modeDescription) {
        this.modeDescription = modeDescription;
    }

    public String getPayTo() {
        return payTo;
    }

    public void setPayTo(final String payTo) {
        this.payTo = payTo;
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

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(final BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
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

    public String getVouchersubType() {
        return vouchersubType;
    }

    public void setVouchersubType(final String vouchersubType) {
        this.vouchersubType = vouchersubType;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(final String voucherType) {
        this.voucherType = voucherType;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(final String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public AccountBudgetCodeDto getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(final AccountBudgetCodeDto budgetCode) {
        this.budgetCode = budgetCode;
    }

    public String getAccountHeadDesc() {
        return accountHeadDesc;
    }

    public void setAccountHeadDesc(final String accountHeadDesc) {
        this.accountHeadDesc = accountHeadDesc;
    }

    public List<AccountContraVoucherReportAccountheadBean> getOacBeanList() {
        return oacBeanList;
    }

    public void setOacBeanList(final List<AccountContraVoucherReportAccountheadBean> oacBeanList) {
        this.oacBeanList = oacBeanList;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(final String organizationName) {
        OrganizationName = organizationName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(final BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

	public String getOrganizationNameReg() {
		return OrganizationNameReg;
	}

	public void setOrganizationNameReg(String organizationNameReg) {
		OrganizationNameReg = organizationNameReg;
	}

}
