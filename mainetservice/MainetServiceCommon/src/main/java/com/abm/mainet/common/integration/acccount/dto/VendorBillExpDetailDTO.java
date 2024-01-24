package com.abm.mainet.common.integration.acccount.dto;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author tejas.kotekar
 *
 */
@XmlRootElement(name = "VendorBillExpDetailDTO", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorBillExpDetailDTO", namespace = "http://service.soap.account.mainet.abm.com/")
public class VendorBillExpDetailDTO {

	@XmlElement(name="id",namespace="http://service.soap.account.mainet.abm.com/")
    private Long id;
	@XmlElement(name="budgetCodeId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long budgetCodeId;
	@XmlElement(name="amount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal amount;
	@XmlElement(name="amountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String amountStr;
	@XmlElement(name="sanctionedAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal sanctionedAmount;
	@XmlElement(name="disallowedAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal disallowedAmount;
	@XmlElement(name="sanctionedAmountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String sanctionedAmountStr;
	@XmlElement(name="projectedExpenditureId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long projectedExpenditureId;
	@XmlElement(name="newBalanceAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private String newBalanceAmount;
	@XmlElement(name="disallowedRemark",namespace="http://service.soap.account.mainet.abm.com/")
    private String disallowedRemark;
	@XmlElement(name="originalEstimate",namespace="http://service.soap.account.mainet.abm.com/")
    private String originalEstimate;
	@XmlElement(name="balanceAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private String balanceAmount;
	@XmlElement(name="balProvisionAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private String balProvisionAmount;
	@XmlElement(name="budgetCodeDesc",namespace="http://service.soap.account.mainet.abm.com/")
    private String budgetCodeDesc;
	@XmlElement(name="bmId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long bmId;
    
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(final String amountStr) {
        this.amountStr = amountStr;
    }

    public BigDecimal getSanctionedAmount() {
        return sanctionedAmount;
    }

    public void setSanctionedAmount(final BigDecimal sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public BigDecimal getDisallowedAmount() {
        return disallowedAmount;
    }

    public void setDisallowedAmount(final BigDecimal disallowedAmount) {
        this.disallowedAmount = disallowedAmount;
    }

    public String getSanctionedAmountStr() {
        return sanctionedAmountStr;
    }

    public void setSanctionedAmountStr(final String sanctionedAmountStr) {
        this.sanctionedAmountStr = sanctionedAmountStr;
    }

    public Long getProjectedExpenditureId() {
        return projectedExpenditureId;
    }

    public void setProjectedExpenditureId(final Long projectedExpenditureId) {
        this.projectedExpenditureId = projectedExpenditureId;
    }

    public String getDisallowedRemark() {
        return disallowedRemark;
    }

    public void setDisallowedRemark(final String disallowedRemark) {
        this.disallowedRemark = disallowedRemark;
    }

    public String getBudgetCodeDesc() {
        return budgetCodeDesc;
    }

    public void setBudgetCodeDesc(final String budgetCodeDesc) {
        this.budgetCodeDesc = budgetCodeDesc;
    }

    public String getOriginalEstimate() {
        return originalEstimate;
    }

    public void setOriginalEstimate(final String originalEstimate) {
        this.originalEstimate = originalEstimate;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(final String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getBalProvisionAmount() {
        return balProvisionAmount;
    }

    public void setBalProvisionAmount(final String balProvisionAmount) {
        this.balProvisionAmount = balProvisionAmount;
    }

    public String getNewBalanceAmount() {
        return newBalanceAmount;
    }

    public void setNewBalanceAmount(final String newBalanceAmount) {
        this.newBalanceAmount = newBalanceAmount;
    }

	public Long getBmId() {
		return bmId;
	}

	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}

}
