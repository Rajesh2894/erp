package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prasant.sahu
 *
 */

@Entity
@Table(name = "TB_AC_BANK_DEPOSITSLIP_DENOM")
public class AccountBankDepositeSlipDenomEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CASHDEP_DET_ID")
    private Long cashdepDetId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DPS_SLIPID", referencedColumnName = "DPS_SLIPID")
    private AccountBankDepositeSlipMasterEntity depositeSlipId;

    @Column(name = "CPD_DENOMID")
    private Long cpdDenomId;

    @Column(name = "DENOM_COUNT")
    private Long denominationCount;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LANG_ID")
    private Integer langId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1")
    private Long fi04N1;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    /**
     * @return the cashdepDetId
     */
    public Long getCashdepDetId() {
        return cashdepDetId;
    }

    /**
     * @param cashdepDetId the cashdepDetId to set
     */
    public void setCashdepDetId(final Long cashdepDetId) {
        this.cashdepDetId = cashdepDetId;
    }

    /**
     * @return the depositeSlipId
     */
    public AccountBankDepositeSlipMasterEntity getDepositeSlipId() {
        return depositeSlipId;
    }

    /**
     * @param depositeSlipId the depositeSlipId to set
     */
    public void setDepositeSlipId(final AccountBankDepositeSlipMasterEntity depositeSlipId) {
        this.depositeSlipId = depositeSlipId;
    }

    /**
     * @return the cpdDenomId
     */
    public Long getCpdDenomId() {
        return cpdDenomId;
    }

    /**
     * @param cpdDenomId the cpdDenomId to set
     */
    public void setCpdDenomId(final Long cpdDenomId) {
        this.cpdDenomId = cpdDenomId;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
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
     * @return the fi04N1
     */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * @param fi04n1 the fi04N1 to set
     */
    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    /**
     * @return the fi04V1
     */
    public String getFi04V1() {
        return fi04V1;
    }

    /**
     * @param fi04v1 the fi04V1 to set
     */
    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    /**
     * @return the fi04D1
     */
    public Date getFi04D1() {
        return fi04D1;
    }

    /**
     * @param fi04d1 the fi04D1 to set
     */
    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    /**
     * @return the fi04Lo1
     */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @param fi04Lo1 the fi04Lo1 to set
     */
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    /**
     * @return the denominationCount
     */
    public Long getDenominationCount() {
        return denominationCount;
    }

    /**
     * @param denominationCount the denominationCount to set
     */
    public void setDenominationCount(final Long denominationCount) {
        this.denominationCount = denominationCount;
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BANK_DEPOSITSLIP_DENOM", "CASHDEP_DET_ID" };
    }

    /**
     * @return the langId
     */
    public Integer getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Integer langId) {
        this.langId = langId;
    }

}
