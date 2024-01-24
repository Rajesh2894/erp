package com.abm.mainet.account.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.abm.mainet.common.utility.LookUp;

public class AccountChequeOrCashDepositSlipDTO {
    private String remittanceDate;
    private String bank;
    private String branch;
    private String bankaccountNo;
    private Long bankAccountTypeCode;
    private String bankAccountName;
    private String organisationName;
    private BigDecimal total;
    private String totalAmountInWords;
    private BigDecimal amount;
    private Long numberOfRupes;
    private String rupesTpye;
    private String slipNumber;
    private Long picesCount;
    private String payOrderNo;
    private String depositBankName;
    private Long orgId;
    private String bankAccountType;
    private BigDecimal decimalAmount;
    private String indainCurrencyAmount;
    private String indianCurrencyDecimalAmount;
    private String chequeNo;
    private String chequeDate;
    private Long modeId;
    private String userName;

    private List<LookUp> denLookupList = new ArrayList<>();
    private List<AccountChequeOrCashDepositSlipDTO> denomination;
    private String depositeTypeCode;
    
    private String totalAmount;
    public String getRemittanceDate() {
        return remittanceDate;
    }

    public void setRemittanceDate(String remittanceDate) {
        this.remittanceDate = remittanceDate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBankaccountNo() {
        return bankaccountNo;
    }

    public void setBankaccountNo(String bankaccountNo) {
        this.bankaccountNo = bankaccountNo;
    }

    public Long getBankAccountTypeCode() {
        return bankAccountTypeCode;
    }

    public void setBankAccountTypeCode(Long bankAccountTypeCode) {
        this.bankAccountTypeCode = bankAccountTypeCode;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public List<LookUp> getDenLookupList() {
        return denLookupList;
    }

    public void setDenLookupList(List<LookUp> denLookupList) {
        this.denLookupList = denLookupList;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getTotalAmountInWords() {
        return totalAmountInWords;
    }

    public void setTotalAmountInWords(String totalAmountInWords) {
        this.totalAmountInWords = totalAmountInWords;
    }

    public List<AccountChequeOrCashDepositSlipDTO> getDenomination() {
        return denomination;
    }

    public void setDenomination(List<AccountChequeOrCashDepositSlipDTO> denomination) {
        this.denomination = denomination;
    }

    public Long getNumberOfRupes() {
        return numberOfRupes;
    }

    public void setNumberOfRupes(Long numberOfRupes) {
        this.numberOfRupes = numberOfRupes;
    }

    public String getRupesTpye() {
        return rupesTpye;
    }

    public void setRupesTpye(String rupesTpye) {
        this.rupesTpye = rupesTpye;
    }

    public String getSlipNumber() {
        return slipNumber;
    }

    public void setSlipNumber(String slipNumber) {
        this.slipNumber = slipNumber;
    }

    public Long getPicesCount() {
        return picesCount;
    }

    public void setPicesCount(Long picesCount) {
        this.picesCount = picesCount;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getDepositBankName() {
        return depositBankName;
    }

    public void setDepositBankName(String depositBankName) {
        this.depositBankName = depositBankName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public BigDecimal getDecimalAmount() {
        return decimalAmount;
    }

    public void setDecimalAmount(BigDecimal decimalAmount) {
        this.decimalAmount = decimalAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIndainCurrencyAmount() {
        return indainCurrencyAmount;
    }

    public void setIndainCurrencyAmount(String indainCurrencyAmount) {
        this.indainCurrencyAmount = indainCurrencyAmount;
    }

    public String getIndianCurrencyDecimalAmount() {
        return indianCurrencyDecimalAmount;
    }

    public void setIndianCurrencyDecimalAmount(String indianCurrencyDecimalAmount) {
        this.indianCurrencyDecimalAmount = indianCurrencyDecimalAmount;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    @Override
    public String toString() {
        return "AccountChequeOrCashDepositSlipDTO [remittanceDate=" + remittanceDate + ", bank=" + bank + ", branch=" + branch
                + ", bankaccountNo=" + bankaccountNo + ", bankAccountTypeCode=" + bankAccountTypeCode + ", bankAccountName="
                + bankAccountName + ", organisationName=" + organisationName + ", total=" + total + ", totalAmountInWords="
                + totalAmountInWords + ", amount=" + amount + ", numberOfRupes=" + numberOfRupes + ", rupesTpye=" + rupesTpye
                + ", slipNumber=" + slipNumber + ", picesCount=" + picesCount + ", payOrderNo=" + payOrderNo
                + ", depositBankName=" + depositBankName + ", orgId=" + orgId + ", bankAccountType=" + bankAccountType
                + ", decimalAmount=" + decimalAmount + ", indainCurrencyAmount=" + indainCurrencyAmount
                + ", indianCurrencyTotalAmount=" + indianCurrencyDecimalAmount + ", denLookupList=" + denLookupList
                + ", denomination=" + denomination + "]";
    }

	public String getDepositeTypeCode() {
		return depositeTypeCode;
	}

	public void setDepositeTypeCode(String depositeTypeCode) {
		this.depositeTypeCode = depositeTypeCode;
	}

	public String getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	public Long getModeId() {
		return modeId;
	}

	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
