package com.abm.mainet.common.domain;

import java.io.Serializable;
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
@Table(name = "TB_FINANCIALYEAR_HIST")
public class FinancialYearHistory implements Serializable {

    private static final long serialVersionUID = 2648107386114405370L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_FAYEARID", nullable = false, length = 12)
    private long hFaYear;

    @Temporal(TemporalType.DATE)
    @Column(name = "FA_FROMDATE", nullable = true)
    private Date faFromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "FA_TODATE", nullable = true)
    private Date faToDate;

    @Column(name = "FA_YEARID", nullable = false, length = 12)
    private long faYear;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, precision = 12, scale = 0)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "H_STATUS", length = 1)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_FINANCIALYEAR_HIST", "H_FAYEARID" };
    }

    public long gethFaYear() {
        return hFaYear;
    }

    public void sethFaYear(long hFaYear) {
        this.hFaYear = hFaYear;
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

    public long getFaYear() {
        return faYear;
    }

    public void setFaYear(long faYear) {
        this.faYear = faYear;
    }

}
