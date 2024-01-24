package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBillEntryDeductionDetBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long billMasterId;

    private Long pacSacHeadId;

    private Long secondaryAccountHeadId;

    private Long deductionRate;

    private BigDecimal deductionAmount;

    private String deductionAmountStr;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private Long languageId;
    
    private Long raTaxFact;
    
	private BigDecimal raTaxPercent;
	
	private String raTaxFactDesc;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacAddress;

    private Long budgetCodeId;
    private String acHeadCode;
    private Long sacHeadId;

    private Long bchId;

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

    /**
     * @return the pacSacHeadId
     */
    public Long getPacSacHeadId() {
        return pacSacHeadId;
    }

    /**
     * @param pacSacHeadId the pacSacHeadId to set
     */
    public void setPacSacHeadId(final Long pacSacHeadId) {
        this.pacSacHeadId = pacSacHeadId;
    }

    public void setSecondaryAccountHeadId(final Long secondaryAccountHeadId) {
        this.secondaryAccountHeadId = secondaryAccountHeadId;
    }

    public Long getSecondaryAccountHeadId() {
        return secondaryAccountHeadId;
    }

    public void setDeductionRate(final Long deductionRate) {
        this.deductionRate = deductionRate;
    }

    public Long getDeductionRate() {
        return deductionRate;
    }

    public void setDeductionAmount(final BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public String getDeductionAmountStr() {
        return deductionAmountStr;
    }

    public void setDeductionAmountStr(final String deductionAmountStr) {
        this.deductionAmountStr = deductionAmountStr;
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

    public void setLanguageId(final Long langId) {
        languageId = langId;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountBillEntryDeductionDetBean [id=" + id + ", billMasterId="
                + billMasterId + ", pacSacHeadId=" + pacSacHeadId
                + ", secondaryAccountHeadId=" + secondaryAccountHeadId
                + ", deductionRate=" + deductionRate + ", deductionAmount="
                + deductionAmount + ", orgId=" + orgId + ", createdBy="
                + createdBy + ", createdDate=" + createdDate + ", updatedBy="
                + updatedBy + ", updatedDate=" + updatedDate + ", languageId="
                + languageId + ", lgIpMacAddress=" + lgIpMacAddress
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
        final AccountBillEntryDeductionDetBean other = (AccountBillEntryDeductionDetBean) obj;
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

    public Long getBchId() {
        return bchId;
    }

    public void setBchId(Long bchId) {
        this.bchId = bchId;
    }

	public Long getRaTaxFact() {
		return raTaxFact;
	}

	public void setRaTaxFact(Long raTaxFact) {
		this.raTaxFact = raTaxFact;
	}

	public BigDecimal getRaTaxPercent() {
		return raTaxPercent;
	}

	public void setRaTaxPercent(BigDecimal raTaxPercent) {
		this.raTaxPercent = raTaxPercent;
	}

	public String getRaTaxFactDesc() {
		return raTaxFactDesc;
	}

	public void setRaTaxFactDesc(String raTaxFactDesc) {
		this.raTaxFactDesc = raTaxFactDesc;
	}
	
}
