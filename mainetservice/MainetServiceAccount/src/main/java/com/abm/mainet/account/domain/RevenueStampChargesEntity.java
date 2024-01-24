package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_REVSTAMP_SLAB")

public class RevenueStampChargesEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8403328831937056731L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RSS_ID", precision = 12, scale = 0, nullable = false)
    private Long rssId;

    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE", nullable = false)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE", nullable = true)
    private Date toDate;

    @Column(name = "FROM_AMT", precision = 12, scale = 2, nullable = false)
    private BigDecimal fromAmount;

    @Column(name = "TO_AMT", precision = 12, scale = 2, nullable = false)
    private BigDecimal toAmount;

    @Column(name = "STAMP_CHRG", precision = 4, scale = 2, nullable = false)
    private BigDecimal stampCharge;

    @Column(name = "ORGID", precision = 12, nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = true)
    private Long updatedby;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_REVSTAMP_SLAB", "RSS_ID" };
    }

    public Long getRssId() {
        return rssId;
    }

    public void setRssId(Long rssId) {
        this.rssId = rssId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }

    public BigDecimal getStampCharge() {
        return stampCharge;
    }

    public void setStampCharge(BigDecimal stampCharge) {
        this.stampCharge = stampCharge;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Long updatedby) {
        this.updatedby = updatedby;
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

}
