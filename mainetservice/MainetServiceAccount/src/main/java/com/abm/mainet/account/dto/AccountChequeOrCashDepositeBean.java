
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;

/**
 * @author prasant.sahu
 *
 */
public class AccountChequeOrCashDepositeBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3206125510148764250L;
    private Long depositeSlipId;
    private String cashDeposite;
    private String chequeDeposite;
    private String depositeSlipNo;
    private String depositeSlipDate;
    private Long fundId;
    private Long fieldId;
    private Date clearingDate;
    private BigDecimal amount;
    private String depositeType;
    private String cashdepositeType;
    private String accountName;
    private Date fromDate;
    private Date toDate;
    private BigDecimal depositeAmount;
    private Long accountCode;
    private BigDecimal total;
    private Long bankId;
    private Long baAccountid;
    private String depositSlipRemark;

    private String depositDateLedger;
    private String depositDateCheque;
    private Date depositDateReceipt;
    private Date depositDateCash;

    private String fundIdWithDesc;
    private String fieldIdWithDesc;

    private String SfromDate;
    private String StoDate;
    private String SfeeMode;
    private boolean allcheckbox;
    private Character coTypeFlag;

    private String baAccountName;

    private Long department;
    private Long hiddenfundId;
    private Long hiddenfieldId;
    private Long hiddendepartment;
    private List<AccountCashDepositeBean> cashDep;

    // Properties Receipt Details

    private List<TbServiceReceiptMasBean> listOfReceiptDetails = new ArrayList<>();
    private List<TbServiceReceiptMasBean> listOfChequeDDPoDetails = new ArrayList<>();
    private List<AccountLedgerMasBean> listOfLedgerDetails = new ArrayList<>();

    private List<DraweeBankDetailsBean> listOfDraweeBank = new ArrayList<>();

    private String isEmpty;
    private String hasError;
    private String templateExist;
    private String recptIntgFlagId;
    // to identify whether seachType Normal or Advance
    private String searchType;

    private Long depositSlipType;
    private Long depositSlipTypeId;
    private String depositTypeFlag;

    private BigDecimal depositSlipAmount;
    
    private String rmDate;
    
    private String chequeDate;
    
    private Long modeId;
    private Long funcId;

    /**
     * @return the listOfChequeDDPoDetails
     */
    public List<TbServiceReceiptMasBean> getListOfChequeDDPoDetails() {
        return listOfChequeDDPoDetails;
    }

    /**
     * @param listOfChequeDDPoDetails the listOfChequeDDPoDetails to set
     */
    public void setListOfChequeDDPoDetails(
            final List<TbServiceReceiptMasBean> listOfChequeDDPoDetails) {
        this.listOfChequeDDPoDetails = listOfChequeDDPoDetails;
    }

    /**
     * @return the department
     */
    public Long getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(final Long department) {
        this.department = department;
    }

    /**
     * @return the cashdepositeType
     */
    public String getCashdepositeType() {
        return cashdepositeType;
    }

    /**
     * @param cashdepositeType the cashdepositeType to set
     */
    public void setCashdepositeType(final String cashdepositeType) {
        this.cashdepositeType = cashdepositeType;
    }

    /**
     * @return the sfromDate
     */
    public String getSfromDate() {
        return SfromDate;
    }

    /**
     * @param sfromDate the sfromDate to set
     */
    public void setSfromDate(final String sfromDate) {
        SfromDate = sfromDate;
    }

    /**
     * @return the stoDate
     */
    public String getStoDate() {
        return StoDate;
    }

    /**
     * @param stoDate the stoDate to set
     */
    public void setStoDate(final String stoDate) {
        StoDate = stoDate;
    }

    /**
     * @return the sfeeMode
     */
    public String getSfeeMode() {
        return SfeeMode;
    }

    /**
     * @param sfeeMode the sfeeMode to set
     */
    public void setSfeeMode(final String sfeeMode) {
        SfeeMode = sfeeMode;
    }

    /**
     * @return the cashDeposite
     */
    public String getCashDeposite() {
        return cashDeposite;
    }

    /**
     * @param cashDeposite the cashDeposite to set
     */
    public void setCashDeposite(final String cashDeposite) {
        this.cashDeposite = cashDeposite;
    }

    /**
     * @return the chequeDeposite
     */
    public String getChequeDeposite() {
        return chequeDeposite;
    }

    /**
     * @param chequeDeposite the chequeDeposite to set
     */
    public void setChequeDeposite(final String chequeDeposite) {
        this.chequeDeposite = chequeDeposite;
    }

    /**
     * @return the depositeSlipNo
     */
    public String getDepositeSlipNo() {
        return depositeSlipNo;
    }

    /**
     * @param depositeSlipNo the depositeSlipNo to set
     */
    public void setDepositeSlipNo(final String depositeSlipNo) {
        this.depositeSlipNo = depositeSlipNo;
    }

    /**
     * @return the depositeSlipDate
     */
    public String getDepositeSlipDate() {
        return depositeSlipDate;
    }

    /**
     * @param depositeSlipDate the depositeSlipDate to set
     */
    public void setDepositeSlipDate(final String depositeSlipDate) {
        this.depositeSlipDate = depositeSlipDate;
    }

    /**
     * @return the fundId
     */
    public Long getFundId() {
        return fundId;
    }

    /**
     * @param fundId the fundId to set
     */
    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    /**
     * @return the fieldId
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return the clearingDate
     */
    public Date getClearingDate() {
        return clearingDate;
    }

    /**
     * @return the depositeSlipId
     */
    public Long getDepositeSlipId() {
        return depositeSlipId;
    }

    /**
     * @param depositeSlipId the depositeSlipId to set
     */
    public void setDepositeSlipId(final Long depositeSlipId) {
        this.depositeSlipId = depositeSlipId;
    }

    /**
     * @param clearingDate the clearingDate to set
     */
    public void setClearingDate(final Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the cashDep
     */
    public List<AccountCashDepositeBean> getCashDep() {
        return cashDep;
    }

    /**
     * @param cashDep the cashDep to set
     */
    public void setCashDep(final List<AccountCashDepositeBean> cashDep) {
        this.cashDep = cashDep;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(final String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the bankId
     */
    public Long getBankId() {
        return bankId;
    }

    /**
     * @param bankId the bankId to set
     */
    public void setBankId(final Long bankId) {
        this.bankId = bankId;
    }

    /**
     * @return the baAccountid
     */
    public Long getBaAccountid() {
        return baAccountid;
    }

    /**
     * @param baAccountid the baAccountid to set
     */
    public void setBaAccountid(final Long baAccountid) {
        this.baAccountid = baAccountid;
    }

    /**
     * @return the listOfDraweeBank
     */
    public List<DraweeBankDetailsBean> getListOfDraweeBank() {
        return listOfDraweeBank;
    }

    /**
     * @param listOfDraweeBank the listOfDraweeBank to set
     */
    public void setListOfDraweeBank(final List<DraweeBankDetailsBean> listOfDraweeBank) {
        this.listOfDraweeBank = listOfDraweeBank;
    }

    /**
     * @return the fundIdWithDesc
     */
    public String getFundIdWithDesc() {
        return fundIdWithDesc;
    }

    /**
     * @param fundIdWithDesc the fundIdWithDesc to set
     */
    public void setFundIdWithDesc(final String fundIdWithDesc) {
        this.fundIdWithDesc = fundIdWithDesc;
    }

    /**
     * @return the fieldIdWithDesc
     */
    public String getFieldIdWithDesc() {
        return fieldIdWithDesc;
    }

    /**
     * @param fieldIdWithDesc the fieldIdWithDesc to set
     */
    public void setFieldIdWithDesc(final String fieldIdWithDesc) {
        this.fieldIdWithDesc = fieldIdWithDesc;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the depositeType
     */
    public String getDepositeType() {
        return depositeType;
    }

    /**
     * @param depositeType the depositeType to set
     */
    public void setDepositeType(final String depositeType) {
        this.depositeType = depositeType;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the listOfReceiptDetails
     */
    public List<TbServiceReceiptMasBean> getListOfReceiptDetails() {
        return listOfReceiptDetails;
    }

    /**
     * @param listOfReceiptDetails the listOfReceiptDetails to set
     */
    public void setListOfReceiptDetails(
            final List<TbServiceReceiptMasBean> listOfReceiptDetails) {
        this.listOfReceiptDetails = listOfReceiptDetails;
    }

    /**
     * @return the depositeAmount
     */
    public BigDecimal getDepositeAmount() {
        return depositeAmount;
    }

    /**
     * @param depositeAmount the depositeAmount to set
     */
    public void setDepositeAmount(final BigDecimal depositeAmount) {
        this.depositeAmount = depositeAmount;
    }

    /**
     * @return the accountCode
     */
    public Long getAccountCode() {
        return accountCode;
    }

    /**
     * @param accountCode the accountCode to set
     */
    public void setAccountCode(final Long accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * @return the listOfLedgerDetails
     */
    public List<AccountLedgerMasBean> getListOfLedgerDetails() {
        return listOfLedgerDetails;
    }

    /**
     * @param listOfLedgerDetails the listOfLedgerDetails to set
     */
    public void setListOfLedgerDetails(
            final List<AccountLedgerMasBean> listOfLedgerDetails) {
        this.listOfLedgerDetails = listOfLedgerDetails;
    }

    /**
     * @return the hiddenfundId
     */
    public Long getHiddenfundId() {
        return hiddenfundId;
    }

    /**
     * @param hiddenfundId the hiddenfundId to set
     */
    public void setHiddenfundId(final Long hiddenfundId) {
        this.hiddenfundId = hiddenfundId;
    }

    /**
     * @return the hiddenfieldId
     */
    public Long getHiddenfieldId() {
        return hiddenfieldId;
    }

    /**
     * @param hiddenfieldId the hiddenfieldId to set
     */
    public void setHiddenfieldId(final Long hiddenfieldId) {
        this.hiddenfieldId = hiddenfieldId;
    }

    /**
     * @return the hiddendepartment
     */
    public Long getHiddendepartment() {
        return hiddendepartment;
    }

    /**
     * @param hiddendepartment the hiddendepartment to set
     */
    public void setHiddendepartment(final Long hiddendepartment) {
        this.hiddendepartment = hiddendepartment;
    }

    /**
     * @return the depositSlipRemark
     */
    public String getDepositSlipRemark() {
        return depositSlipRemark;
    }

    /**
     * @param depositSlipRemark the depositSlipRemark to set
     */
    public void setDepositSlipRemark(final String depositSlipRemark) {
        this.depositSlipRemark = depositSlipRemark;
    }

    /**
     * @return the isEmpty
     */
    public String getIsEmpty() {
        return isEmpty;
    }

    /**
     * @param isEmpty the isEmpty to set
     */
    public void setIsEmpty(final String isEmpty) {
        this.isEmpty = isEmpty;
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

    public String getDepositDateLedger() {
        return depositDateLedger;
    }

    public void setDepositDateLedger(final String depositDateLedger) {
        this.depositDateLedger = depositDateLedger;
    }

    public String getDepositDateCheque() {
        return depositDateCheque;
    }

    public void setDepositDateCheque(final String depositDateCheque) {
        this.depositDateCheque = depositDateCheque;
    }

    public Date getDepositDateReceipt() {
        return depositDateReceipt;
    }

    public void setDepositDateReceipt(final Date depositDateReceipt) {
        this.depositDateReceipt = depositDateReceipt;
    }

    public Date getDepositDateCash() {
        return depositDateCash;
    }

    public void setDepositDateCash(final Date depositDateCash) {
        this.depositDateCash = depositDateCash;
    }

    public String getTemplateExist() {
        return templateExist;
    }

    public void setTemplateExist(final String templateExist) {
        this.templateExist = templateExist;
    }

    public Character getCoTypeFlag() {
        return coTypeFlag;
    }

    public void setCoTypeFlag(final Character coTypeFlag) {
        this.coTypeFlag = coTypeFlag;
    }

    public String getBaAccountName() {
        return baAccountName;
    }

    public void setBaAccountName(final String baAccountName) {
        this.baAccountName = baAccountName;
    }

    public String getRecptIntgFlagId() {
        return recptIntgFlagId;
    }

    public void setRecptIntgFlagId(final String recptIntgFlagId) {
        this.recptIntgFlagId = recptIntgFlagId;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(final String searchType) {
        this.searchType = searchType;
    }

    /**
     * @return the allcheckbox
     */
    public boolean isAllcheckbox() {
        return allcheckbox;
    }

    /**
     * @param allcheckbox the allcheckbox to set
     */
    public void setAllcheckbox(final boolean allcheckbox) {
        this.allcheckbox = allcheckbox;
    }

    public Long getDepositSlipType() {
        return depositSlipType;
    }

    public void setDepositSlipType(Long depositSlipType) {
        this.depositSlipType = depositSlipType;
    }

    public Long getDepositSlipTypeId() {
        return depositSlipTypeId;
    }

    public void setDepositSlipTypeId(Long depositSlipTypeId) {
        this.depositSlipTypeId = depositSlipTypeId;
    }

    public String getDepositTypeFlag() {
        return depositTypeFlag;
    }

    public void setDepositTypeFlag(String depositTypeFlag) {
        this.depositTypeFlag = depositTypeFlag;
    }

    public BigDecimal getDepositSlipAmount() {
        return depositSlipAmount;
    }

    public void setDepositSlipAmount(BigDecimal depositSlipAmount) {
        this.depositSlipAmount = depositSlipAmount;
    }

	public String getRmDate() {
		return rmDate;
	}

	public void setRmDate(String rmDate) {
		this.rmDate = rmDate;
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

	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

	
	
	

}
