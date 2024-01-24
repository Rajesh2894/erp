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
@Table(name = "TB_FINCIALYEARORG_MAP_HIST")
public class TbFincialyearorgMapEntityHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_MAP_ID", nullable = false)
    private Long hMapId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "MAP_ID", nullable = false)
    private Long mapId;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "FA_YEARSTATUS")
    private Long yaTypeCpdId;

    @Column(name = "FA_FROMMONTH", nullable = true)
    private Long faFromMonth;

    @Column(name = "FA_TOMONTH", nullable = true)
    private Long faToMonth;

    @Column(name = "FA_FROMYEAR", nullable = true)
    private Long faFromYear;

    @Column(name = "FA_TOYEAR", nullable = true)
    private Long faToYear;

    @Column(name = "FA_MONSTATUS", nullable = true)
    private Long faMonthStatus;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------
    @Column(name = "FA_YEARID")
    private Long faYear;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbFincialyearorgMapEntityHistory() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    /**
     * @return the createdBy
     */
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

    public Long getFaYearID() {
        return faYear;
    }

    public void setFaYearID(Long faYearID) {
        this.faYear = faYearID;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : YA_TYPE_CPD_ID ( NUMBER )
    public void setYaTypeCpdId(final Long yaTypeCpdId) {
        this.yaTypeCpdId = yaTypeCpdId;
    }

    public Long getYaTypeCpdId() {
        return yaTypeCpdId;
    }

    public Long gethMapId() {
        return hMapId;
    }

    public void sethMapId(Long hMapId) {
        this.hMapId = hMapId;
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    /**
     * @return the faFromMonth
     */
    public Long getFaFromMonth() {
        return faFromMonth;
    }

    /**
     * @param faFromMonth the faFromMonth to set
     */
    public void setFaFromMonth(final Long faFromMonth) {
        this.faFromMonth = faFromMonth;
    }

    /**
     * @return the faToMonth
     */
    public Long getFaToMonth() {
        return faToMonth;
    }

    /**
     * @param faToMonth the faToMonth to set
     */
    public void setFaToMonth(final Long faToMonth) {
        this.faToMonth = faToMonth;
    }

    /**
     * @return the faFromYear
     */
    public Long getFaFromYear() {
        return faFromYear;
    }

    /**
     * @param faFromYear the faFromYear to set
     */
    public void setFaFromYear(final Long faFromYear) {
        this.faFromYear = faFromYear;
    }

    /**
     * @return the faToYear
     */
    public Long getFaToYear() {
        return faToYear;
    }

    /**
     * @param faToYear the faToYear to set
     */
    public void setFaToYear(final Long faToYear) {
        this.faToYear = faToYear;
    }

    /**
     * @return the faMonthStatus
     */
    public Long getFaMonthStatus() {
        return faMonthStatus;
    }

    /**
     * @param faMonthStatus the faMonthStatus to set
     */
    public void setFaMonthStatus(final Long faMonthStatus) {
        this.faMonthStatus = faMonthStatus;
    }

    @Column(name = "H_STATUS", length = 1)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_FINCIALYEARORG_MAP_HIST", "H_MAP_ID" };
    }
}
