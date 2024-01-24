package com.abm.mainet.common.integration.acccount.domain;

import java.io.Serializable;
import java.util.Comparator;
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

/**
 * @author deepika.pimpale
 * @since 07 Jun 2016
 */
@Entity
@Table(name = "TB_AC_CODINGSTRUCTURE_DET")
public class TbAcCodingstructureDetEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 195790896352378410L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CODCOFDET_ID", precision = 12, scale = 0, nullable = false)
    private long codcofdetId;

    @Column(name = "CODCOF_ID", precision = 12, scale = 0, nullable = false)
    private Long tbAcCodingstructureMasEntity;

    @Column(name = "COD_LEVEL", precision = 2, scale = 0, nullable = true)
    private Long codLevel;

    @Column(name = "COD_DESCRIPTION", length = 500, nullable = true)
    private String codDescription;

    @Column(name = "COD_DIGITS", precision = 2, scale = 0, nullable = true)
    private Long codDigits;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long userId;

    /*
     * @Column(name = "LANG_ID", precision = 4, scale = 0, nullable = false) private Long langId;
     */

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    /**
     * @return the codcofdetId
     */
    public long getCodcofdetId() {
        return codcofdetId;
    }

    /**
     * @param codcofdetId the codcofdetId to set
     */
    public void setCodcofdetId(final long codcofdetId) {
        this.codcofdetId = codcofdetId;
    }

    /**
     * @return the tbAcCodingstructureMasEntity
     */
    public Long getTbAcCodingstructureMasEntity() {
        return tbAcCodingstructureMasEntity;
    }

    /**
     * @param tbAcCodingstructureMasEntity the tbAcCodingstructureMasEntity to set
     */
    public void setTbAcCodingstructureMasEntity(
            final Long tbAcCodingstructureMasEntity) {
        this.tbAcCodingstructureMasEntity = tbAcCodingstructureMasEntity;
    }

    /**
     * @return the codLevel
     */
    public Long getCodLevel() {
        return codLevel;
    }

    /**
     * @param codLevel the codLevel to set
     */
    public void setCodLevel(final Long codLevel) {
        this.codLevel = codLevel;
    }

    /**
     * @return the codDescription
     */
    public String getCodDescription() {
        return codDescription;
    }

    /**
     * @param codDescription the codDescription to set
     */
    public void setCodDescription(final String codDescription) {
        this.codDescription = codDescription;
    }

    /**
     * @return the codDigits
     */
    public Long getCodDigits() {
        return codDigits;
    }

    /**
     * @param codDigits the codDigits to set
     */
    public void setCodDigits(final Long codDigits) {
        this.codDigits = codDigits;
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
    /*
     * public Long getLangId() { return langId; }
     *//**
        * @param langId the langId to set
        *//*
           * public void setLangId(Long langId) { this.langId = langId; }
           */

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

    public String[] getPkValues() {
        return new String[] { "AC", "TB_AC_CODINGSTRUCTURE_DET", "CODCOFDET_ID" };
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result)
                + ((codLevel == null) ? 0 : codLevel.hashCode());
        return result;
    }

    public static Comparator<TbAcCodingstructureDetEntity> codingStructureLevelComparator = (coddingStructureDetail1,
            coddingStructureDetail2) -> {

        final Integer level1 = Integer.valueOf(coddingStructureDetail1.getCodLevel().toString());
        final Integer level2 = Integer.valueOf(coddingStructureDetail2.getCodLevel().toString());

        if (!level1.equals(level2)) {
            return level1 - level2;
        }
        final String fieldCode1 = coddingStructureDetail1.getCodLevel().toString();
        final String fieldCode2 = coddingStructureDetail2.getCodLevel().toString();
        // ascending order
        return fieldCode1.compareTo(fieldCode2);
    };

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
        final TbAcCodingstructureDetEntity other = (TbAcCodingstructureDetEntity) obj;
        if (codLevel == null) {
            if (other.codLevel != null) {
                return false;
            }
        } else if (!codLevel.equals(other.codLevel)) {
            return false;
        }
        return true;
    }

}