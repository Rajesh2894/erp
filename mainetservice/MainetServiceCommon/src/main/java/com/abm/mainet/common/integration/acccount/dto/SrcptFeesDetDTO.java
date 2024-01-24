package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;
@XmlRootElement(name = "SrcptFeesDetDTO", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SrcptFeesDetDTO", namespace = "http://service.soap.account.mainet.abm.com/")
public class SrcptFeesDetDTO implements Serializable {

    private static final long serialVersionUID = 1125145928602487731L;

    @XmlElement(name="rfFeeid",namespace="http://service.soap.account.mainet.abm.com/")
    private Long rfFeeid;
    @XmlElement(name="rfFeeamount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal rfFeeamount;
    @XmlElement(name="budgetCodeid",namespace="http://service.soap.account.mainet.abm.com/")
    private Long budgetCodeid;

    @XmlElement(name="orgid",namespace="http://service.soap.account.mainet.abm.com/")
    private Long orgid;
    @XmlElement(name="createdBy",namespace="http://service.soap.account.mainet.abm.com/")
    private Long createdBy;
    @XmlElement(name="langId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long langId;
    @XmlElement(name="createdDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date createdDate;
    @XmlElement(name="updatedBy",namespace="http://service.soap.account.mainet.abm.com/")
    private Long updatedBy;
    @XmlElement(name="updatedDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date updatedDate;
    @XmlElement(name="lgIpMac",namespace="http://service.soap.account.mainet.abm.com/")
    @Size(max = 100)
    private String lgIpMac;
    @XmlElement(name="billType",namespace="http://service.soap.account.mainet.abm.com/")
    private String billType;

    public Long getRfFeeid() {
        return rfFeeid;
    }

    public void setRfFeeid(final Long rfFeeid) {
        this.rfFeeid = rfFeeid;
    }

    public BigDecimal getRfFeeamount() {
        return rfFeeamount;
    }

    public void setRfFeeamount(final BigDecimal rfFeeamount) {
        this.rfFeeamount = rfFeeamount;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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

    public Long getBudgetCodeid() {
        return budgetCodeid;
    }

    public void setBudgetCodeid(final Long budgetCodeid) {
        this.budgetCodeid = budgetCodeid;
    }

    public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Override
	public String toString() {
		return "SrcptFeesDetDTO [rfFeeid=" + rfFeeid + ", rfFeeamount=" + rfFeeamount + ", budgetCodeid=" + budgetCodeid
				+ ", orgid=" + orgid + ", createdBy=" + createdBy + ", langId=" + langId + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
				+ ", billType=" + billType + "]";
	}

}
