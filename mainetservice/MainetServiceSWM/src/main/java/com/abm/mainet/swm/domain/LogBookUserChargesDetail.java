package com.abm.mainet.swm.domain;

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

@Entity
@Table(name = "TB_SW_UCHC")
public class LogBookUserChargesDetail implements Serializable {
    private static final long serialVersionUID = -708015792417271338L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "UCHC_ID")
    private Long uchcId;

    @Column(name = "UCHC_MONTH")
    private String uchcMonth;

    @Column(name = "UCHC_YEAR")
    private String uchcYear;

    @Temporal(TemporalType.DATE)
    @Column(name = "UCHC_DATE")
    private Date uchcDate;

    @Column(name = "UCHC_CNAME")
    private String uchcCname;

    @Column(name = "UCHC_CADD")
    private String uchcCadd;

    @Column(name = "UCHC_CMOBILENO")
    private String uchcCmobileno;

    @Column(name = "UCHC_CWARDNO")
    private String uchcCwardno;

    @Column(name = "UCHC_CCHARG")
    private BigDecimal uchcCcharg;

    @Column(name = "UCHC_CRBOOKNO")
    private String uchcCrbookno;

    @Column(name = "UCHC_CRRECEIPTNO")
    private String uchcCrreceiptno;

    @Column(name = "UCHC_CTOAMT")
    private BigDecimal uchcCtoamt;

    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public LogBookUserChargesDetail() {
    }

    public Long getUchcId() {
        return uchcId;
    }

    public void setUchcId(Long uchcId) {
        this.uchcId = uchcId;
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

    public String getUchcCadd() {
        return uchcCadd;
    }

    public void setUchcCadd(String uchcCadd) {
        this.uchcCadd = uchcCadd;
    }

    public BigDecimal getUchcCcharg() {
        return uchcCcharg;
    }

    public void setUchcCcharg(BigDecimal uchcCcharg) {
        this.uchcCcharg = uchcCcharg;
    }

    public String getUchcCmobileno() {
        return uchcCmobileno;
    }

    public void setUchcCmobileno(String uchcCmobileno) {
        this.uchcCmobileno = uchcCmobileno;
    }

    public String getUchcCname() {
        return uchcCname;
    }

    public void setUchcCname(String uchcCname) {
        this.uchcCname = uchcCname;
    }

    public String getUchcCrbookno() {
        return uchcCrbookno;
    }

    public void setUchcCrbookno(String uchcCrbookno) {
        this.uchcCrbookno = uchcCrbookno;
    }

    public String getUchcCrreceiptno() {
        return uchcCrreceiptno;
    }

    public void setUchcCrreceiptno(String uchcCrreceiptno) {
        this.uchcCrreceiptno = uchcCrreceiptno;
    }

    public BigDecimal getUchcCtoamt() {
        return uchcCtoamt;
    }

    public void setUchcCtoamt(BigDecimal uchcCtoamt) {
        this.uchcCtoamt = uchcCtoamt;
    }

    public String getUchcCwardno() {
        return uchcCwardno;
    }

    public void setUchcCwardno(String uchcCwardno) {
        this.uchcCwardno = uchcCwardno;
    }

    public Date getUchcDate() {
        return uchcDate;
    }

    public void setUchcDate(Date uchcDate) {
        this.uchcDate = uchcDate;
    }

    public String getUchcMonth() {
        return uchcMonth;
    }

    public void setUchcMonth(String uchcMonth) {
        this.uchcMonth = uchcMonth;
    }

    public String getUchcYear() {
        return uchcYear;
    }

    public void setUchcYear(String uchcYear) {
        this.uchcYear = uchcYear;
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

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_UCHC", "UCHC_ID" };
    }

}
