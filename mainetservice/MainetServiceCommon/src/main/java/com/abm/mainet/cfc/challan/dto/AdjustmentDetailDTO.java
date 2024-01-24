package com.abm.mainet.cfc.challan.dto;

import java.io.Serializable;
import java.util.Date;

public class AdjustmentDetailDTO implements Serializable {

    private static final long serialVersionUID = -1846737722257393858L;

    private long adjDetId;

    private Long adjId;

    private Long taxId;

    private double adjAmount;

    private double adjAdjustedAmount;

    private double adjBalanceAmount;

    private String adjAdjustedFlag;

    private String adjRemark;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String taxDesc;
    
    private Long bdBilldetid;
    
    private Long bmIdNo;

    public long getAdjDetId() {
        return adjDetId;
    }

    public void setAdjDetId(final long adjDetId) {
        this.adjDetId = adjDetId;
    }

    public Long getAdjId() {
        return adjId;
    }

    public void setAdjId(final Long adjId) {
        this.adjId = adjId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public double getAdjAmount() {
        return adjAmount;
    }

    public void setAdjAmount(final double adjAmount) {
        this.adjAmount = adjAmount;
    }

    public double getAdjAdjustedAmount() {
        return adjAdjustedAmount;
    }

    public void setAdjAdjustedAmount(final double adjAdjustedAmount) {
        this.adjAdjustedAmount = adjAdjustedAmount;
    }

    public double getAdjBalanceAmount() {
        return adjBalanceAmount;
    }

    public void setAdjBalanceAmount(final double adjBalanceAmount) {
        this.adjBalanceAmount = adjBalanceAmount;
    }

    public String getAdjAdjustedFlag() {
        return adjAdjustedFlag;
    }

    public void setAdjAdjustedFlag(final String adjAdjustedFlag) {
        this.adjAdjustedFlag = adjAdjustedFlag;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getAdjRemark() {
        return adjRemark;
    }

    public void setAdjRemark(final String adjRemark) {
        this.adjRemark = adjRemark;
    }

    public String getTaxDesc() {
        return taxDesc;
    }

    public void setTaxDesc(final String taxDesc) {
        this.taxDesc = taxDesc;
    }

	public Long getBdBilldetid() {
		return bdBilldetid;
	}

	public Long getBmIdNo() {
		return bmIdNo;
	}

	public void setBdBilldetid(Long bdBilldetid) {
		this.bdBilldetid = bdBilldetid;
	}

	public void setBmIdNo(Long bmIdNo) {
		this.bmIdNo = bmIdNo;
	}

}
