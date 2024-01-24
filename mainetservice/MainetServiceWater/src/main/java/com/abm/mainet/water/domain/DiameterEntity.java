package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author deepika.pimpale
 * @since 19 Apr 2016
 */
@Entity
@Table(name = "TB_CCNSIZE_PRM")
public class DiameterEntity implements Serializable {
    private static final long serialVersionUID = -4221368459955435992L;

    @Id
    @GenericGenerator(name = "CustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "CustomGenerator")
    @Column(name = "CNS_ID", precision = 12, scale = 0, nullable = false)
    private long cnsId;

    @Column(name = "CNS_FROM", precision = 5, scale = 2, nullable = true)
    private Double cnsFrom;

    @Column(name = "CNS_TO", precision = 5, scale = 2, nullable = true)
    private Double cnsTo;

    @Column(name = "CNS_VALUE", precision = 5, scale = 2, nullable = true)
    private Double cnsValue;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "LMODDATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "CNS_FRMDT", nullable = true)
    private Date cnsFrmdt;

    @Column(name = "CNS_TODT", nullable = true)
    private Date cnsTodt;

    @Column(name = "WT_V1", length = 100, nullable = true)
    private String wtV1;

    @Column(name = "WT_V2", length = 100, nullable = true)
    private String wtV2;

    @Column(name = "WT_V3", length = 100, nullable = true)
    private String wtV3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    private String wtV4;

    @Column(name = "WT_V5", length = 100, nullable = true)
    private String wtV5;

    @Column(name = "WT_N1", precision = 15, scale = 0, nullable = true)
    private Long wtN1;

    @Column(name = "WT_N2", precision = 15, scale = 0, nullable = true)
    private Long wtN2;

    @Column(name = "WT_N3", precision = 15, scale = 0, nullable = true)
    private Long wtN3;

    @Column(name = "WT_N4", precision = 15, scale = 0, nullable = true)
    private Long wtN4;

    @Column(name = "WT_N5", precision = 15, scale = 0, nullable = true)
    private Long wtN5;

    @Column(name = "WT_D1", nullable = true)
    private Date wtD1;

    @Column(name = "WT_D2", nullable = true)
    private Date wtD2;

    @Column(name = "WT_D3", nullable = true)
    private Date wtD3;

    @Column(name = "WT_LO1", length = 1, nullable = true)
    private String wtLo1;

    @Column(name = "WT_LO2", length = 1, nullable = true)
    private String wtLo2;

    @Column(name = "WT_LO3", length = 1, nullable = true)
    private String wtLo3;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public long getCnsId() {
        return cnsId;
    }

    public void setCnsId(final long cnsId) {
        this.cnsId = cnsId;
    }

    public Double getCnsFrom() {
        return cnsFrom;
    }

    public void setCnsFrom(final Double cnsFrom) {
        this.cnsFrom = cnsFrom;
    }

    public Double getCnsTo() {
        return cnsTo;
    }

    public void setCnsTo(final Double cnsTo) {
        this.cnsTo = cnsTo;
    }

    public Double getCnsValue() {
        return cnsValue;
    }

    public void setCnsValue(final Double cnsValue) {
        this.cnsValue = cnsValue;
    }

    public Organisation getOrgId() {
        return orgId;
    }

    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    public Employee getUserId() {
        return userId;
    }

    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCnsFrmdt() {
        return cnsFrmdt;
    }

    public void setCnsFrmdt(final Date cnsFrmdt) {
        this.cnsFrmdt = cnsFrmdt;
    }

    public Date getCnsTodt() {
        return cnsTodt;
    }

    public void setCnsTodt(final Date cnsTodt) {
        this.cnsTodt = cnsTodt;
    }

    public String getWtV1() {
        return wtV1;
    }

    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    public String getWtV2() {
        return wtV2;
    }

    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV3() {
        return wtV3;
    }

    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV4() {
        return wtV4;
    }

    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV5() {
        return wtV5;
    }

    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    public Long getWtN1() {
        return wtN1;
    }

    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    public Long getWtN2() {
        return wtN2;
    }

    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    public Long getWtN3() {
        return wtN3;
    }

    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    public Long getWtN4() {
        return wtN4;
    }

    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    public Long getWtN5() {
        return wtN5;
    }

    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    public Date getWtD1() {
        return wtD1;
    }

    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    public Date getWtD2() {
        return wtD2;
    }

    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD3() {
        return wtD3;
    }

    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo3() {
        return wtLo3;
    }

    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
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

    @JsonIgnore
    public String[] getPkValues() {

        return new String[] { "WT", "TB_CCNSIZE_PRM", "CNS_ID" };
    }

}