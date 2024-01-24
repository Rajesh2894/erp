
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiren.poriya
 *
 */
public class StandardAccountHeadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long accountType;

    private Long accountSubType;

    private Long payMode;

    private Long vendorType;
    private Long depositType;
    private Long investmentType;
    private Long advanceType;
    private Long asset;
    private Long statutoryDeduction;
    private Long loans;
    private Long bankType;
    private Long bankAccountIntType;
    private String viewBankTypeDesc;
    private String viewPayModeDesc;
    private String viewVendorTypeDesc;
    private String viewInvestmentTypeDesc;
    private String viewDepositTypeDesc;
    private String viewAdvanceTypeDesc;
    private String viewAssetDesc;
    private String viewStatutoryDeductionDesc;
    private String viewLoansDesc;
    private String viewBankIntTypeDesc;
    private String successFlag;

    private String alreadyExist;

    private Long status;

    private Long primaryHeadId;

    private String mode;

    private List<StandardAccountHeadDto> primaryHeadMappingList = new ArrayList<>();
    // for view mode

    private String codeDescription;
    private String statusDescription;
    private String viewAccountTypeDesc;

    private String accountTypeDesc;

    private String accountSubTypeDesc;

    private String cpdAccountSubTypeCode;

    private String defaultOrgFlag;

    public Long getStatus() {
        return status;
    }

    public void setStatus(final Long status) {
        this.status = status;
    }

    public Long getPrimaryHeadId() {
        return primaryHeadId;
    }

    public void setPrimaryHeadId(final Long primaryHeadId) {
        this.primaryHeadId = primaryHeadId;
    }

    public List<StandardAccountHeadDto> getPrimaryHeadMappingList() {
        return primaryHeadMappingList;
    }

    public void setPrimaryHeadMappingList(final List<StandardAccountHeadDto> primaryHeadMappingList) {
        this.primaryHeadMappingList = primaryHeadMappingList;
    }

    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(final Long accountType) {
        this.accountType = accountType;
    }

    public Long getPayMode() {
        return payMode;
    }

    public void setPayMode(final Long payMode) {
        this.payMode = payMode;
    }

    public Long getBankType() {
        return bankType;
    }

    public void setBankType(final Long bankType) {
        this.bankType = bankType;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(final String successFlag) {
        this.successFlag = successFlag;
    }

    public String getAlreadyExist() {
        return alreadyExist;
    }

    public void setAlreadyExist(final String alreadyExist) {
        this.alreadyExist = alreadyExist;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(final String codeDescription) {
        this.codeDescription = codeDescription;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(final String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getViewAccountTypeDesc() {
        return viewAccountTypeDesc;
    }

    public void setViewAccountTypeDesc(final String viewAccountTypeDesc) {
        this.viewAccountTypeDesc = viewAccountTypeDesc;
    }

    public String getAccountTypeDesc() {
        return accountTypeDesc;
    }

    public void setAccountTypeDesc(final String accountTypeDesc) {
        this.accountTypeDesc = accountTypeDesc;
    }

    public String getCpdAccountSubTypeCode() {
        return cpdAccountSubTypeCode;
    }

    public void setCpdAccountSubTypeCode(final String cpdAccountSubTypeCode) {
        this.cpdAccountSubTypeCode = cpdAccountSubTypeCode;
    }

    public Long getVendorType() {
        return vendorType;
    }

    public void setVendorType(final Long vendorType) {
        this.vendorType = vendorType;
    }

    public Long getDepositType() {
        return depositType;
    }

    public void setDepositType(final Long depositType) {
        this.depositType = depositType;
    }

    public Long getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(final Long investmentType) {
        this.investmentType = investmentType;
    }

    public Long getAdvanceType() {
        return advanceType;
    }

    public void setAdvanceType(final Long advanceType) {
        this.advanceType = advanceType;
    }

    public Long getAsset() {
        return asset;
    }

    public void setAsset(final Long asset) {
        this.asset = asset;
    }

    public Long getStatutoryDeduction() {
        return statutoryDeduction;
    }

    public void setStatutoryDeduction(final Long statutoryDeduction) {
        this.statutoryDeduction = statutoryDeduction;
    }

    public Long getLoans() {
        return loans;
    }

    public void setLoans(final Long loans) {
        this.loans = loans;
    }

    public Long getAccountSubType() {
        return accountSubType;
    }

    public void setAccountSubType(final Long accountSubType) {
        this.accountSubType = accountSubType;
    }

    public String getViewBankTypeDesc() {
        return viewBankTypeDesc;
    }

    public void setViewBankTypeDesc(final String viewBankTypeDesc) {
        this.viewBankTypeDesc = viewBankTypeDesc;
    }

    public String getViewPayModeDesc() {
        return viewPayModeDesc;
    }

    public void setViewPayModeDesc(final String viewPayModeDesc) {
        this.viewPayModeDesc = viewPayModeDesc;
    }

    public String getViewVendorTypeDesc() {
        return viewVendorTypeDesc;
    }

    public void setViewVendorTypeDesc(final String viewVendorTypeDesc) {
        this.viewVendorTypeDesc = viewVendorTypeDesc;
    }

    public String getViewInvestmentTypeDesc() {
        return viewInvestmentTypeDesc;
    }

    public void setViewInvestmentTypeDesc(final String viewInvestmentTypeDesc) {
        this.viewInvestmentTypeDesc = viewInvestmentTypeDesc;
    }

    public String getViewDepositTypeDesc() {
        return viewDepositTypeDesc;
    }

    public void setViewDepositTypeDesc(final String viewDepositTypeDesc) {
        this.viewDepositTypeDesc = viewDepositTypeDesc;
    }

    public String getViewAdvanceTypeDesc() {
        return viewAdvanceTypeDesc;
    }

    public void setViewAdvanceTypeDesc(final String viewAdvanceTypeDesc) {
        this.viewAdvanceTypeDesc = viewAdvanceTypeDesc;
    }

    public String getViewAssetDesc() {
        return viewAssetDesc;
    }

    public void setViewAssetDesc(final String viewAssetDesc) {
        this.viewAssetDesc = viewAssetDesc;
    }

    public String getViewStatutoryDeductionDesc() {
        return viewStatutoryDeductionDesc;
    }

    public void setViewStatutoryDeductionDesc(final String viewStatutoryDeductionDesc) {
        this.viewStatutoryDeductionDesc = viewStatutoryDeductionDesc;
    }

    public String getViewLoansDesc() {
        return viewLoansDesc;
    }

    public void setViewLoansDesc(final String viewLoansDesc) {
        this.viewLoansDesc = viewLoansDesc;
    }

    public String getAccountSubTypeDesc() {
        return accountSubTypeDesc;
    }

    public void setAccountSubTypeDesc(final String accountSubTypeDesc) {
        this.accountSubTypeDesc = accountSubTypeDesc;
    }

    public String getDefaultOrgFlag() {
        return defaultOrgFlag;
    }

    public void setDefaultOrgFlag(final String defaultOrgFlag) {
        this.defaultOrgFlag = defaultOrgFlag;
    }


	public Long getBankAccountIntType() {
		return bankAccountIntType;
	}

	public void setBankAccountIntType(Long bankAccountIntType) {
		this.bankAccountIntType = bankAccountIntType;
	}

	public String getViewBankIntTypeDesc() {
		return viewBankIntTypeDesc;
	}

	public void setViewBankIntTypeDesc(String viewBankIntTypeDesc) {
		this.viewBankIntTypeDesc = viewBankIntTypeDesc;
	}

}
