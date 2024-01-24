package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Jeetendra.Pal
 * @since 22 Jun 2016
 */
@Entity
@Table(name = "TB_WT_PLUM_EXPERIENCE ")
public class TbPlumberExperience implements Serializable {

    private static final long serialVersionUID = 7908706681892910661L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PLUM_EXP_ID", precision = 12, scale = 0, nullable = false)
    private long plumExpId;

    @Column(name = "PLUM_ID", precision = 12, scale = 0, nullable = true)
    private Long plumId;

    @Column(name = "PLUM_COMPANY_NAME", length = 150, nullable = true)
    private String plumCompanyName;

    @Column(name = "PLUM_COMPANY_ADDRESS", length = 150, nullable = true)
    private String plumCompanyAddress;

    @Column(name = "PLUM_EXP_MONTH", precision = 2, scale = 0, nullable = true)
    private Long plumExpMonth;

    @Column(name = "PLUM_EXP_YEAR", precision = 4, scale = 0, nullable = true)
    private Long plumExpYear;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

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

    @Column(name = "PLUM_FROM_DATE", nullable = true)
    private Date plumFromDate;

    @Column(name = "PLUM_TO_DATE", nullable = true)
    private Date plumToDate;

    @Column(name = "PLUM_EXP", precision = 10, nullable = true)
    private Double plumExp;

    @Column(name = "PLUM_CPD_FIRM_TYPE", precision = 12, nullable = true)
    private Long plumCPDFirmType;

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String isDeleted;

    /**
     * @return the plumExpId
     */
    public long getPlumExpId() {
        return plumExpId;
    }

    /**
     * @param plumExpId the plumExpId to set
     */
    public void setPlumExpId(final long plumExpId) {
        this.plumExpId = plumExpId;
    }

    /**
     * @return the plumId
     */
    public Long getPlumId() {
        return plumId;
    }

    /**
     * @param plumId the plumId to set
     */
    public void setPlumId(final Long plumId) {
        this.plumId = plumId;
    }

    /**
     * @return the plumCompanyName
     */
    public String getPlumCompanyName() {
        return plumCompanyName;
    }

    /**
     * @param plumCompanyName the plumCompanyName to set
     */
    public void setPlumCompanyName(final String plumCompanyName) {
        this.plumCompanyName = plumCompanyName;
    }

    /**
     * @return the plumCompanyAddress
     */
    public String getPlumCompanyAddress() {
        return plumCompanyAddress;
    }

    /**
     * @param plumCompanyAddress the plumCompanyAddress to set
     */
    public void setPlumCompanyAddress(final String plumCompanyAddress) {
        this.plumCompanyAddress = plumCompanyAddress;
    }

    /**
     * @return the plumExpMonth
     */
    public Long getPlumExpMonth() {
        return plumExpMonth;
    }

    /**
     * @param plumExpMonth the plumExpMonth to set
     */
    public void setPlumExpMonth(final Long plumExpMonth) {
        this.plumExpMonth = plumExpMonth;
    }

    /**
     * @return the plumExpYear
     */
    public Long getPlumExpYear() {
        return plumExpYear;
    }

    /**
     * @param plumExpYear the plumExpYear to set
     */
    public void setPlumExpYear(final Long plumExpYear) {
        this.plumExpYear = plumExpYear;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the wtV1
     */
    public String getWtV1() {
        return wtV1;
    }

    /**
     * @param wtV1 the wtV1 to set
     */
    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    /**
     * @return the wtV2
     */
    public String getWtV2() {
        return wtV2;
    }

    /**
     * @param wtV2 the wtV2 to set
     */
    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    /**
     * @return the wtV3
     */
    public String getWtV3() {
        return wtV3;
    }

    /**
     * @param wtV3 the wtV3 to set
     */
    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    /**
     * @return the wtV4
     */
    public String getWtV4() {
        return wtV4;
    }

    /**
     * @param wtV4 the wtV4 to set
     */
    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    /**
     * @return the wtV5
     */
    public String getWtV5() {
        return wtV5;
    }

    /**
     * @param wtV5 the wtV5 to set
     */
    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    /**
     * @return the wtN1
     */
    public Long getWtN1() {
        return wtN1;
    }

    /**
     * @param wtN1 the wtN1 to set
     */
    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    /**
     * @return the wtN2
     */
    public Long getWtN2() {
        return wtN2;
    }

    /**
     * @param wtN2 the wtN2 to set
     */
    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    /**
     * @return the wtN3
     */
    public Long getWtN3() {
        return wtN3;
    }

    /**
     * @param wtN3 the wtN3 to set
     */
    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    /**
     * @return the wtN4
     */
    public Long getWtN4() {
        return wtN4;
    }

    /**
     * @param wtN4 the wtN4 to set
     */
    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    /**
     * @return the wtN5
     */
    public Long getWtN5() {
        return wtN5;
    }

    /**
     * @param wtN5 the wtN5 to set
     */
    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    /**
     * @return the wtD1
     */
    public Date getWtD1() {
        return wtD1;
    }

    /**
     * @param wtD1 the wtD1 to set
     */
    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    /**
     * @return the wtD2
     */
    public Date getWtD2() {
        return wtD2;
    }

    /**
     * @param wtD2 the wtD2 to set
     */
    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    /**
     * @return the wtD3
     */
    public Date getWtD3() {
        return wtD3;
    }

    /**
     * @param wtD3 the wtD3 to set
     */
    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    /**
     * @return the wtLo1
     */
    public String getWtLo1() {
        return wtLo1;
    }

    /**
     * @param wtLo1 the wtLo1 to set
     */
    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    /**
     * @return the wtLo2
     */
    public String getWtLo2() {
        return wtLo2;
    }

    /**
     * @param wtLo2 the wtLo2 to set
     */
    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    /**
     * @return the wtLo3
     */
    public String getWtLo3() {
        return wtLo3;
    }

    /**
     * @param wtLo3 the wtLo3 to set
     */
    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    /**
     * @return the plumFromDate
     */
    public Date getPlumFromDate() {
        return plumFromDate;
    }

    /**
     * @param plumFromDate the plumFromDate to set
     */
    public void setPlumFromDate(final Date plumFromDate) {
        this.plumFromDate = plumFromDate;
    }

    /**
     * @return the plumToDate
     */
    public Date getPlumToDate() {
        return plumToDate;
    }

    /**
     * @param plumToDate the plumToDate to set
     */
    public void setPlumToDate(final Date plumToDate) {
        this.plumToDate = plumToDate;
    }

    /**
     * @return the plumExp
     */
    public Double getPlumExp() {
        return plumExp;
    }

    /**
     * @param plumExp the plumExp to set
     */
    public void setPlumExp(final Double plumExp) {
        this.plumExp = plumExp;
    }

    /**
     * @return the plumCPDFirmType
     */
    public Long getPlumCPDFirmType() {
        return plumCPDFirmType;
    }

    /**
     * @param plumCPDFirmType the plumCPDFirmType to set
     */
    public void setPlumCPDFirmType(final Long plumCPDFirmType) {
        this.plumCPDFirmType = plumCPDFirmType;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_PLUM_EXPERIENCE", "PLUM_EXP_ID" };
    }

    /**
     * @return the isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

}