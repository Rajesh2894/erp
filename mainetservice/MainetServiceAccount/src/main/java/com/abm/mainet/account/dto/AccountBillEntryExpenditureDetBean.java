package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBillEntryExpenditureDetBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long billMasterId;

    private Long expenditureId;

    private Long pacSacHeadId;

    private Long pacSacHeadIdHidden;

    private Long secondaryAccountHeadId;

    private Long fundId;

    private Long functionId;

    private Long fieldId;

    private BigDecimal billChargesAmount;

    private BigDecimal disallowedAmount;

    private String disallowedRemark;

    private BigDecimal actualAmount;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private Long languageId;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddress;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddressUpdated;

    private BigDecimal originalEstimate;

    private BigDecimal balanceAmount;

    private BigDecimal balProvisionAmount;

    private Long projectedExpenditureId;

    private BigDecimal newBalanceAmount;

    private Long rowCount;

    private String expenditureBudgetCode;

    private Long budgetCodeId;

    private String hasError;
    private String acHeadCode;
    private Long sacHeadId;

    private String billBudIdfyFlag;
    
    private String BudgetCheckFlag;

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
    public void setBillMasterId(final Long billMasterId) {
        this.billMasterId = billMasterId;
    }

    public Long getBillMasterId() {
        return billMasterId;
    }

    public Long getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(final Long expenditureId) {
        this.expenditureId = expenditureId;
    }

    public Long getPacSacHeadId() {
        return pacSacHeadId;
    }

    public void setPacSacHeadId(final Long pacSacHeadId) {
        this.pacSacHeadId = pacSacHeadId;
    }

    public Long getPacSacHeadIdHidden() {
        return pacSacHeadIdHidden;
    }

    public void setPacSacHeadIdHidden(final Long pacSacHeadIdHidden) {
        this.pacSacHeadIdHidden = pacSacHeadIdHidden;
    }

    public void setSecondaryAccountHeadId(final Long secondaryAccountHeadId) {
        this.secondaryAccountHeadId = secondaryAccountHeadId;
    }

    public Long getSecondaryAccountHeadId() {
        return secondaryAccountHeadId;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
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

    public void setBillChargesAmount(final BigDecimal billChargesAmount) {
        this.billChargesAmount = billChargesAmount;
    }

    public BigDecimal getBillChargesAmount() {
        return billChargesAmount;
    }

    public void setDisallowedAmount(final BigDecimal disallowedAmount) {
        this.disallowedAmount = disallowedAmount;
    }

    public BigDecimal getDisallowedAmount() {
        return disallowedAmount;
    }

    public String getDisallowedRemark() {
        return disallowedRemark;
    }

    public void setDisallowedRemark(final String disallowedRemark) {
        this.disallowedRemark = disallowedRemark;
    }

    public void setActualAmount(final BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
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

    public void setLgIpMacAddressUpdated(final String lgIpMacAddressUpdated) {
        this.lgIpMacAddressUpdated = lgIpMacAddressUpdated;
    }

    public String getLgIpMacAddressUpdated() {
        return lgIpMacAddressUpdated;
    }

    /**
     * @return the orginalEstimate
     */
    public BigDecimal getOriginalEstimate() {
        return originalEstimate;
    }

    /**
     * @param orginalEstimate the orginalEstimate to set
     */
    public void setOriginalEstimate(final BigDecimal originalEstimate) {
        this.originalEstimate = originalEstimate;
    }

    /**
     * @return the balanceAmount
     */
    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * @param balanceAmount the balanceAmount to set
     */
    public void setBalanceAmount(final BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     * @return the balProvisionAmount
     */
    public BigDecimal getBalProvisionAmount() {
        return balProvisionAmount;
    }

    /**
     * @param balProvisionAmount the balProvisionAmount to set
     */
    public void setBalProvisionAmount(final BigDecimal balProvisionAmount) {
        this.balProvisionAmount = balProvisionAmount;
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
     * @return the rowCount
     */
    public Long getRowCount() {
        return rowCount;
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(final Long rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * @return the expenditureBudgetCode
     */
    public String getExpenditureBudgetCode() {
        return expenditureBudgetCode;
    }

    /**
     * @param expenditureBudgetCode the expenditureBudgetCode to set
     */
    public void setExpenditureBudgetCode(final String expenditureBudgetCode) {
        this.expenditureBudgetCode = expenditureBudgetCode;
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

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getBudgetCheckFlag() {
		return BudgetCheckFlag;
	}

	public void setBudgetCheckFlag(String budgetCheckFlag) {
		BudgetCheckFlag = budgetCheckFlag;
	}

	/*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountBillEntryExpenditureDetBean [id=" + id
                + ", billMasterId=" + billMasterId + ", expenditureId="
                + expenditureId + ", pacSacHeadId=" + pacSacHeadId
                + ", pacSacHeadIdHidden=" + pacSacHeadIdHidden
                + ", secondaryAccountHeadId=" + secondaryAccountHeadId
                + ", fundId=" + fundId + ", functionId=" + functionId
                + ", fieldId=" + fieldId + ", billChargesAmount="
                + billChargesAmount + ", disallowedAmount=" + disallowedAmount
                + ", disallowedRemark=" + disallowedRemark + ", actualAmount="
                + actualAmount + ", orgId=" + orgId + ", createdBy="
                + createdBy + ", createdDate=" + createdDate + ", updatedBy="
                + updatedBy + ", updatedDate=" + updatedDate + ", languageId="
                + languageId + ", lgIpMacAddress=" + lgIpMacAddress
                + ", lgIpMacAddressUpdated=" + lgIpMacAddressUpdated
                + ", originalEstimate=" + originalEstimate + ", balanceAmount="
                + balanceAmount + ", balProvisionAmount=" + balProvisionAmount
                + ", projectedExpenditureId=" + projectedExpenditureId
                + ", newBalanceAmount=" + newBalanceAmount + ", rowCount="
                + rowCount + ", expenditureBudgetCode=" + expenditureBudgetCode
                + ", budgetCodeId=" + budgetCodeId + "]";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime
                * result)
                + ((expenditureBudgetCode == null) ? 0
                        : expenditureBudgetCode
                                .hashCode());
        result = (prime * result)
                + ((pacSacHeadId == null) ? 0 : pacSacHeadId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountBillEntryExpenditureDetBean other = (AccountBillEntryExpenditureDetBean) obj;
        if (expenditureBudgetCode == null) {
            if (other.expenditureBudgetCode != null) {
                return false;
            }
        } else if (!expenditureBudgetCode.equals(other.expenditureBudgetCode)) {
            return false;
        }
        if (pacSacHeadId == null) {
            if (other.pacSacHeadId != null) {
                return false;
            }
        } else if (!pacSacHeadId.equals(other.pacSacHeadId)) {
            return false;
        }
        return true;
    }

    public String getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(final String acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public String getBillBudIdfyFlag() {
        return billBudIdfyFlag;
    }

    public void setBillBudIdfyFlag(String billBudIdfyFlag) {
        this.billBudIdfyFlag = billBudIdfyFlag;
    }

}
