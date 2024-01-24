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

/**
 * The persistent class for the tb_sw_finecharge_col database table.
 * 
 */

@Entity
@Table(name = "TB_SW_FINECHARGE_COL")
public class FineChargeCollection implements Serializable {

    private static final long serialVersionUID = 8073590174103097058L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FCH_ID", unique = true, nullable = false)
    private Long fchId;

    private Long empid;

    @Column(name = "FCH_AMOUNT", precision = 10, scale = 2)
    private BigDecimal fchAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "FCH_ENTRYDATE")
    private Date fchEntrydate;

    @Column(name = "FCH_FLAG", length = 1)
    private String fchFlag;

    @Column(name = "FCH_MANUAL_NO", length = 30)
    private String fchManualNo;

    @Column(name = "FCH_MOBNO", length = 30)
    private String fchMobno;

    @Column(name = "FCH_NAME", length = 1000)
    private String fchName;

    @Column(name = "FCH_TYPE")
    private Long fchType;

    @Column(name = "REGISTRATION_ID")
    private Long registrationId;

    @Column(length = 100)
    private String lattiude;

    @Column(length = 100)
    private String longitude;

    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public FineChargeCollection() {
    }

    public Long getFchId() {
        return this.fchId;
    }

    public void setFchId(Long fchId) {
        this.fchId = fchId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getEmpid() {
        return this.empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public BigDecimal getFchAmount() {
        return this.fchAmount;
    }

    public void setFchAmount(BigDecimal fchAmount) {
        this.fchAmount = fchAmount;
    }

    public Date getFchEntrydate() {
        return this.fchEntrydate;
    }

    public void setFchEntrydate(Date fchEntrydate) {
        this.fchEntrydate = fchEntrydate;
    }

    public String getFchFlag() {
        return this.fchFlag;
    }

    public void setFchFlag(String fchFlag) {
        this.fchFlag = fchFlag;
    }

    public String getFchManualNo() {
        return this.fchManualNo;
    }

    public void setFchManualNo(String fchManualNo) {
        this.fchManualNo = fchManualNo;
    }

    public String getFchMobno() {
        return this.fchMobno;
    }

    public void setFchMobno(String fchMobno) {
        this.fchMobno = fchMobno;
    }

    public String getFchName() {
        return this.fchName;
    }

    public void setFchName(String fchName) {
        this.fchName = fchName;
    }

    public String getLattiude() {
        return this.lattiude;
    }

    public void setLattiude(String lattiude) {
        this.lattiude = lattiude;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getFchType() {
        return fchType;
    }

    public void setFchType(Long fchType) {
        this.fchType = fchType;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public String[] getPkValues() {
        return new String[] { "SWM", "TB_SW_FINECHARGE_COL", "FCH_ID" };
    }

}