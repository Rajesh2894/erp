package com.abm.mainet.common.integration.acccount.dto;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Vivek.Kumar
 * @since 06 Feb 2017
 */
@XmlRootElement(name = "VoucherPostDetailDTO", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoucherPostDetailDTO", namespace = "http://service.soap.account.mainet.abm.com/")
public class VoucherPostDetailDTO {
	//Mandatory
    // transaction amount
    @XmlElement(name = "voucherAmount", namespace = "http://service.soap.account.mainet.abm.com/")
    private BigDecimal voucherAmount;
    
    //optional //it is only used for account module.
    /*
     * Debit/Credit id from DCR prefix this field should be derived from Voucher Template while voucher posting only for account module used.
     */
    @XmlElement(name = "drCrId", namespace = "http://service.soap.account.mainet.abm.com/")
    private Long drCrId; //optional
    /**
     * Budget Head Id
     */
    //@XmlElement(name = "budgetCodeId", namespace = "http://service.soap.account.mainet.abm.com/")
    //private Long budgetCodeId; //fully optional
    /**
     * Secondary Head Id
     */
    @XmlElement(name = "sacHeadId", namespace = "http://service.soap.account.mainet.abm.com/")
    private Long sacHeadId; //mandatory
    /**
     * this field is mandatory in case of RV,CV and PV posting so this id need to be send from caller
     */
    @XmlElement(name = "payModeId", namespace = "http://service.soap.account.mainet.abm.com/")
    private Long payModeId; //optional

    //this is applicable for only receivable demand collection.
    @XmlElement(name = "demandTypeId", namespace = "http://service.soap.account.mainet.abm.com/")
    private Long demandTypeId;

    //this is applicable for only receivable demand collection.
    @XmlElement(name = "yearId", namespace = "http://service.soap.account.mainet.abm.com/")
    private Long yearId;

    //To Identify to debit/credit account heads.
    @XmlElement(name = "accountHeadFlag", namespace = "http://service.soap.account.mainet.abm.com/")
    private String accountHeadFlag;
    
    public BigDecimal getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(final BigDecimal voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public Long getDrCrId() {
        return drCrId;
    }

    public void setDrCrId(final Long drCrId) {
        this.drCrId = drCrId;
    }

    /*public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }*/

    public Long getPayModeId() {
        return payModeId;
    }

    public void setPayModeId(final Long payModeId) {
        this.payModeId = payModeId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getDemandTypeId() {
        return demandTypeId;
    }

    public void setDemandTypeId(Long demandTypeId) {
        this.demandTypeId = demandTypeId;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

	public String getAccountHeadFlag() {
		return accountHeadFlag;
	}

	public void setAccountHeadFlag(String accountHeadFlag) {
		this.accountHeadFlag = accountHeadFlag;
	}

	@Override
	public String toString() {
		return "VoucherPostDetailDTO [voucherAmount=" + voucherAmount + ", drCrId=" + drCrId + ", sacHeadId="
				+ sacHeadId + ", payModeId=" + payModeId + ", demandTypeId=" + demandTypeId + ", yearId=" + yearId
				+ ", accountHeadFlag=" + accountHeadFlag + "]";
	}

}
