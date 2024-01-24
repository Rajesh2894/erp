package com.abm.mainet.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Vivek.Kumar
 * @since 14-Dec-2015
 *
 */
@Entity
@Table(name = "VW_SERVICE_PREFIX_DETAILS")
public class PrefixesView implements Serializable {

    private static final long serialVersionUID = 5048429938606259456L;

    @Column(name = "CCM_ID", nullable = false)
    private Long ccmId;

    @Column(name = "CCM_PREFIX", nullable = false)
    private String ccmPrefix;

    @Column(name = "CCM_DESC", nullable = false)
    private String ccmDesc;

    @Id
    @Column(name = "CCD_ID", nullable = false)
    private Long ccdId;

    @Column(name = "CCD_VALUE", nullable = false)
    private String ccdValue;

    @Column(name = "CCD_DESC", nullable = false)
    private String ccdDesc;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    /**
     * @return the ccmId
     */
    public Long getCcmId() {
        return ccmId;
    }

    /**
     * @param ccmId the ccmId to set
     */
    public void setCcmId(final Long ccmId) {
        this.ccmId = ccmId;
    }

    /**
     * @return the ccmPrefix
     */
    public String getCcmPrefix() {
        return ccmPrefix;
    }

    /**
     * @param ccmPrefix the ccmPrefix to set
     */
    public void setCcmPrefix(final String ccmPrefix) {
        this.ccmPrefix = ccmPrefix;
    }

    /**
     * @return the ccmDesc
     */
    public String getCcmDesc() {
        return ccmDesc;
    }

    /**
     * @param ccmDesc the ccmDesc to set
     */
    public void setCcmDesc(final String ccmDesc) {
        this.ccmDesc = ccmDesc;
    }

    /**
     * @return the ccdId
     */
    public Long getCcdId() {
        return ccdId;
    }

    /**
     * @param ccdId the ccdId to set
     */
    public void setCcdId(final Long ccdId) {
        this.ccdId = ccdId;
    }

    /**
     * @return the ccdValue
     */
    public String getCcdValue() {
        return ccdValue;
    }

    /**
     * @param ccdValue the ccdValue to set
     */
    public void setCcdValue(final String ccdValue) {
        this.ccdValue = ccdValue;
    }

    /**
     * @return the ccdDesc
     */
    public String getCcdDesc() {
        return ccdDesc;
    }

    /**
     * @param ccdDesc the ccdDesc to set
     */
    public void setCcdDesc(final String ccdDesc) {
        this.ccdDesc = ccdDesc;
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

}
