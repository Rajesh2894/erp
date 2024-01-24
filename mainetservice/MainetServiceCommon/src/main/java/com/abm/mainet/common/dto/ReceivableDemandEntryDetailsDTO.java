package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import org.codehaus.jackson.annotate.JsonIgnore;

public class ReceivableDemandEntryDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long billDetId;
    private Long sacHeadId;
    private String accNo;
    private BigDecimal billDetailsAmount;
    private BigDecimal paidBillDetAmount;
    private BigDecimal balancePayDetAmount = new BigDecimal(0);
    private BigDecimal currentPayDetAmount = new BigDecimal(0);
    private Long taxId;
    private Long taxCategory1;
    private Long taxCategory2;
    private Long taxCategory3;
    private Long taxCategory4;
    private Long taxCategory5;
    private String taxName;   
    private String isDeleted;
    private String remarks;
    private Long createdBy;
    private Date createdDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long orgid;
    private Long updatedBy;
    private Date updatedDate;
    private String accHead;
    @JsonIgnore
    private ReceivableDemandEntryDTO rcvblDemandDets;

    public Long getBillDetId() {
        return billDetId;
    }

    public void setBillDetId(Long billDetId) {
        this.billDetId = billDetId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public BigDecimal getBillDetailsAmount() {
        return billDetailsAmount;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public BigDecimal getCurrentPayDetAmount() {
        return currentPayDetAmount;
    }

    public void setCurrentPayDetAmount(BigDecimal currentPayDetAmount) {
        this.currentPayDetAmount = currentPayDetAmount;
    }

    public void setBillDetailsAmount(BigDecimal billDetailsAmount) {
        this.billDetailsAmount = billDetailsAmount;
    }

    public BigDecimal getPaidBillDetAmount() {
        return paidBillDetAmount;
    }

    public void setPaidBillDetAmount(BigDecimal paidBillDetAmount) {
        this.paidBillDetAmount = paidBillDetAmount;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Long getTaxCategory1() {
        return taxCategory1;
    }

    public void setTaxCategory1(Long taxCategory1) {
        this.taxCategory1 = taxCategory1;
    }

    public Long getTaxCategory2() {
        return taxCategory2;
    }

    public void setTaxCategory2(Long taxCategory2) {
        this.taxCategory2 = taxCategory2;
    }

    public Long getTaxCategory3() {
        return taxCategory3;
    }

    public void setTaxCategory3(Long taxCategory3) {
        this.taxCategory3 = taxCategory3;
    }

    public Long getTaxCategory4() {
        return taxCategory4;
    }

    public void setTaxCategory4(Long taxCategory4) {
        this.taxCategory4 = taxCategory4;
    }

    public Long getTaxCategory5() {
        return taxCategory5;
    }

    public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setTaxCategory5(Long taxCategory5) {
        this.taxCategory5 = taxCategory5;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public BigDecimal getBalancePayDetAmount() {
        return balancePayDetAmount;
    }

    public void setBalancePayDetAmount(BigDecimal balancePayDetAmount) {
        this.balancePayDetAmount = balancePayDetAmount;
    }

    public String getAccHead() {
        return accHead;
    }

    public void setAccHead(String accHead) {
        this.accHead = accHead;
    }

    public ReceivableDemandEntryDTO getRcvblDemandDets() {
        return rcvblDemandDets;
    }

    public void setRcvblDemandDets(ReceivableDemandEntryDTO rcvblDemandDets) {
        this.rcvblDemandDets = rcvblDemandDets;
    }

}
