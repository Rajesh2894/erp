
package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author vikrant.thakur
 * @since 22 jan 2014
 */

@Entity
@Table(name = "TB_FINANCIALYEAR")
public class FinancialYear implements Serializable {

    private static final long serialVersionUID = 2648107386114405370L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FA_YEARID", nullable = false, length = 12)
    private long faYear;

    @Temporal(TemporalType.DATE)
    @Column(name = "FA_FROMDATE", nullable = true)
    private Date faFromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "FA_TODATE", nullable = true)
    private Date faToDate;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, precision = 12, scale = 0)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    /*@OneToMany(fetch = FetchType.EAGER, mappedBy = "faYear", cascade = CascadeType.ALL)
    private List<FinancialYear> financialYears = new ArrayList<>();*/

    @OneToMany(mappedBy = "tbFinancialyear", fetch = FetchType.LAZY, targetEntity = TbFincialyearorgMapEntity.class, cascade = CascadeType.ALL)
    private List<TbFincialyearorgMapEntity> financialyearOrgMap = new ArrayList<>(0);

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    public String[] getPkValues() {
        return new String[] { "COM", "TB_FINANCIALYEAR", "FA_YEARID" };
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /** @return the updatedDate */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /** @return the updatedBy */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

   /* *//** @return the financialYears *//*
    public List<FinancialYear> getFinancialYears() {
        return financialYears;
    }

    *//**
     * @param financialYears the financialYears to set
     *//*
    public void setFinancialYears(final List<FinancialYear> financialYears) {
        this.financialYears = financialYears;
    }*/

    /** @return the financialyearOrgMap */
    public List<TbFincialyearorgMapEntity> getFinancialyearOrgMap() {
        return financialyearOrgMap;
    }

    /**
     * @param financialyearOrgMap the financialyearOrgMap to set
     */
    public void setFinancialyearOrgMap(final List<TbFincialyearorgMapEntity> financialyearOrgMap) {
        this.financialyearOrgMap = financialyearOrgMap;
    }

    /** @return the createdBy */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
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

    /** @return the faYear */
    public long getFaYear() {
        return faYear;
    }

    /**
     * @param faYear the faYear to set
     */
    public void setFaYear(final long faYear) {
        this.faYear = faYear;
    }

    /** @return the faFromDate */
    public Date getFaFromDate() {
        return faFromDate;
    }

    /**
     * @param faFromDate the faFromDate to set
     */
    public void setFaFromDate(final Date faFromDate) {
        this.faFromDate = faFromDate;
    }

    /** @return the faToDate */
    public Date getFaToDate() {
        return faToDate;
    }

    /**
     * @param faToDate the faToDate to set
     */
    public void setFaToDate(final Date faToDate) {
        this.faToDate = faToDate;
    }
}
