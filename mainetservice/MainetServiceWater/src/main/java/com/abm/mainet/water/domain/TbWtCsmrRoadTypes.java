package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author deepika.pimpale
 * @since 23 Mar 2016
 */
@Entity
@Table(name = "TB_WT_CSMR_ROAD_TYPES")
public class TbWtCsmrRoadTypes implements Serializable {
    private static final long serialVersionUID = 6333298755309296918L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CRT_ID", precision = 12, scale = 0, nullable = false)
    private long crtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRT_CS_IDN", nullable = false)
    private TbKCsmrInfoMH crtCsIdn;

    @Column(name = "CRT_ROAD_TYPES", precision = 15, scale = 0, nullable = false)
    private Long crtRoadTypes;

    @Column(name = "CRT_ROAD_UNITS", precision = 12, scale = 2, nullable = true)
    private Double crtRoadUnits;

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

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "CRT_GRANTED", length = 1, nullable = false)
    private String crtGranted;

    @Column(name = "CRT_LATEST", length = 1, nullable = false)
    private String crtLatest;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private Long apmApplicationId;

    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = true)
    private Long smServiceId;

    @Column(name = "CRT_DISC_TYPE", precision = 12, scale = 0, nullable = true)
    private Long crtDiscType;

    @Column(name = "CRT_DISC_RECN_ID", precision = 12, scale = 0, nullable = true)
    private Long crtDiscRecnId;

    @Column(name = "CRT_ROAD_TRENCH", precision = 7, scale = 2, nullable = true)
    private Double crtRoadTrench;

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String isDeleted;

    /**
     * @return the crtId
     */
    public long getCrtId() {
        return crtId;
    }

    /**
     * @param crtId the crtId to set
     */
    public void setCrtId(final long crtId) {
        this.crtId = crtId;
    }

    /**
     * @return the crtCsIdn
     */

    /**
     * @return the crtRoadTypes
     */
    public Long getCrtRoadTypes() {
        return crtRoadTypes;
    }

    /**
     * @param crtRoadTypes the crtRoadTypes to set
     */
    public void setCrtRoadTypes(final Long crtRoadTypes) {
        this.crtRoadTypes = crtRoadTypes;
    }

    /**
     * @return the crtRoadUnits
     */
    public Double getCrtRoadUnits() {
        return crtRoadUnits;
    }

    /**
     * @param crtRoadUnits the crtRoadUnits to set
     */
    public void setCrtRoadUnits(final Double crtRoadUnits) {
        this.crtRoadUnits = crtRoadUnits;
    }

    /**
     * @return the orgId
     */
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Employee userId) {
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
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Employee updatedBy) {
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
     * @return the crtGranted
     */
    public String getCrtGranted() {
        return crtGranted;
    }

    /**
     * @param crtGranted the crtGranted to set
     */
    public void setCrtGranted(final String crtGranted) {
        this.crtGranted = crtGranted;
    }

    /**
     * @return the crtLatest
     */
    public String getCrtLatest() {
        return crtLatest;
    }

    /**
     * @param crtLatest the crtLatest to set
     */
    public void setCrtLatest(final String crtLatest) {
        this.crtLatest = crtLatest;
    }

    /**
     * @return the apmApplicationId
     */
    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    /**
     * @param apmApplicationId the apmApplicationId to set
     */
    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the smServiceId
     */
    public Long getSmServiceId() {
        return smServiceId;
    }

    /**
     * @param smServiceId the smServiceId to set
     */
    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    /**
     * @return the crtDiscType
     */
    public Long getCrtDiscType() {
        return crtDiscType;
    }

    /**
     * @param crtDiscType the crtDiscType to set
     */
    public void setCrtDiscType(final Long crtDiscType) {
        this.crtDiscType = crtDiscType;
    }

    /**
     * @return the crtDiscRecnId
     */
    public Long getCrtDiscRecnId() {
        return crtDiscRecnId;
    }

    /**
     * @param crtDiscRecnId the crtDiscRecnId to set
     */
    public void setCrtDiscRecnId(final Long crtDiscRecnId) {
        this.crtDiscRecnId = crtDiscRecnId;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_CSMR_ROAD_TYPES", "CRT_ID" };
    }

    /**
     * @return the crtCsIdn
     */
    public TbKCsmrInfoMH getCrtCsIdn() {
        return crtCsIdn;
    }

    /**
     * @param crtCsIdn the crtCsIdn to set
     */
    public void setCrtCsIdn(final TbKCsmrInfoMH crtCsIdn) {
        this.crtCsIdn = crtCsIdn;
    }

    public Double getCrtRoadTrench() {
        return crtRoadTrench;
    }

    public void setCrtRoadTrench(final Double crtRoadTrench) {
        this.crtRoadTrench = crtRoadTrench;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TbWtCsmrRoadTypes other = (TbWtCsmrRoadTypes) obj;
        if (crtId != other.crtId) {
            return false;
        }
        return true;
    }

}