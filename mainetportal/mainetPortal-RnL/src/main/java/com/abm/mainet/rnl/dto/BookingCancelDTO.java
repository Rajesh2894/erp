package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BookingCancelDTO implements Serializable {

    private static final long serialVersionUID = 5177130765493831223L;

    private Long bcancelId;
    private Long bookingId;
    private BigDecimal refundAmt;// input value from UI estateBookingCancel
    private Long rfFeeid;// receipt DET primary key

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long taxId;// used when account integrate

    private String feeDescription;

    private BigDecimal rfFeeamount;
    
    private String chargestoShow;

    public Long getBcancelId() {
        return bcancelId;
    }

    public void setBcancelId(Long bcancelId) {
        this.bcancelId = bcancelId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }

    public Long getRfFeeid() {
        return rfFeeid;
    }

    public void setRfFeeid(Long rfFeeid) {
        this.rfFeeid = rfFeeid;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public String getFeeDescription() {
        return feeDescription;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
    }

    public BigDecimal getRfFeeamount() {
        return rfFeeamount;
    }

    public void setRfFeeamount(BigDecimal rfFeeamount) {
        this.rfFeeamount = rfFeeamount;
    }

	public String getChargestoShow() {
		return chargestoShow;
	}

	public void setChargestoShow(String chargestoShow) {
		this.chargestoShow = chargestoShow;
	}

    
}
