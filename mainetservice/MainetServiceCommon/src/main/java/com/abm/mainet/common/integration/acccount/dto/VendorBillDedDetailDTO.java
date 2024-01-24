/**
 *
 */
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
@XmlRootElement(name = "VendorBillDedDetailDTO", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorBillDedDetailDTO", namespace = "http://service.soap.account.mainet.abm.com/")
public class VendorBillDedDetailDTO {
	@XmlElement(name = "id", namespace = "http://service.soap.account.mainet.abm.com/")
	private Long id;
	@XmlElement(name = "budgetCodeId", namespace = "http://service.soap.account.mainet.abm.com/")
	private Long budgetCodeId;
	// Dedcution rate
	@XmlElement(name = "rate", namespace = "http://service.soap.account.mainet.abm.com/")
	private BigDecimal rate;
	@XmlElement(name = "deductionAmount", namespace = "http://service.soap.account.mainet.abm.com/")
	private BigDecimal deductionAmount;
	@XmlElement(name = "deductionAmountStr", namespace = "http://service.soap.account.mainet.abm.com/")
	private String deductionAmountStr;
	@XmlElement(name = "budgetCodeDesc", namespace = "http://service.soap.account.mainet.abm.com/")
	private String budgetCodeDesc;
	@XmlElement(name = "bchId", namespace = "http://service.soap.account.mainet.abm.com/")
	private Long bchId;

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

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(final BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(final BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public String getDeductionAmountStr() {
		return deductionAmountStr;
	}

	public void setDeductionAmountStr(final String deductionAmountStr) {
		this.deductionAmountStr = deductionAmountStr;
	}

	public String getBudgetCodeDesc() {
		return budgetCodeDesc;
	}

	public void setBudgetCodeDesc(final String budgetCodeDesc) {
		this.budgetCodeDesc = budgetCodeDesc;
	}

	public Long getBchId() {
		return bchId;
	}

	public void setBchId(Long bchId) {
		this.bchId = bchId;
	}

	@Override
	public String toString() {
		return "VendorBillDedDetailDTO [id=" + id + ", budgetCodeId=" + budgetCodeId + ", rate=" + rate
				+ ", deductionAmount=" + deductionAmount + ", deductionAmountStr=" + deductionAmountStr
				+ ", budgetCodeDesc=" + budgetCodeDesc + ", bchId=" + bchId + "]";
	}

}
