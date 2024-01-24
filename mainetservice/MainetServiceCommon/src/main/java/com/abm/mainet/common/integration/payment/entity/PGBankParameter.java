package com.abm.mainet.common.integration.payment.entity;

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
 * @author prasant.sahu
 *
 */
@Entity
@Table(name = "TB_PG_BANK_PARAMETER_DETAIL")
public class PGBankParameter implements Serializable {

    private static final long serialVersionUID = 8662323761913226753L;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "PG_PRAM_DET_ID", precision = 15, scale = 0, nullable = false)
    private Long pgPramDetId;

    @Column(name = "PG_ID", precision = 15, scale = 0, nullable = true)
    private long pgId;

    @Column(name = "PAR_NAME", length = 500, nullable = true)
    private String parName;

    @Column(name = "PAR_VALUE", length = 500, nullable = true)
    private String parValue;

    @Column(name = "PAR_STATUS", length = 1, nullable = true)
    private String parStatus;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = true)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

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

    @Column(name = "COMM_N1", precision = 15, scale = 0, nullable = true)
    private Long commN1;

    @Column(name = "COMM_V1", length = 100, nullable = true)
    private String commV1;

    @Column(name = "COMM_D1", nullable = true)
    private Date commD1;

    @Column(name = "COMM_LO1", length = 1, nullable = true)
    private String commLo1;

    /**
     * @return the pgPramDetId
     */
    public Long getPgPramDetId() {
        return pgPramDetId;
    }

    /**
     * @param pgPramDetId the pgPramDetId to set
     */
    public void setPgPramDetId(final Long pgPramDetId) {
        this.pgPramDetId = pgPramDetId;
    }

    /**
     * @return the pgId
     */
    public long getPgId() {
        return pgId;
    }

    /**
     * @param pgId the pgId to set
     */
    public void setPgId(final long pgId) {
        this.pgId = pgId;
    }

    /**
     * @return the parName
     */
    public String getParName() {
        return parName;
    }

    /**
     * @param parName the parName to set
     */
    public void setParName(final String parName) {
        this.parName = parName;
    }

    /**
     * @return the parValue
     */
    public String getParValue() {
        return parValue;
    }

    /**
     * @param parValue the parValue to set
     */
    public void setParValue(final String parValue) {
        this.parValue = parValue;
    }

    /**
     * @return the parStatus
     */
    public String getParStatus() {
        return parStatus;
    }

    /**
     * @param parStatus the parStatus to set
     */
    public void setParStatus(final String parStatus) {
        this.parStatus = parStatus;
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
     * @return the commN1
     */
    public Long getCommN1() {
        return commN1;
    }

    /**
     * @param commN1 the commN1 to set
     */
    public void setCommN1(final Long commN1) {
        this.commN1 = commN1;
    }

    /**
     * @return the commV1
     */
    public String getCommV1() {
        return commV1;
    }

    /**
     * @param commV1 the commV1 to set
     */
    public void setCommV1(final String commV1) {
        this.commV1 = commV1;
    }

    /**
     * @return the commD1
     */
    public Date getCommD1() {
        return commD1;
    }

    /**
     * @param commD1 the commD1 to set
     */
    public void setCommD1(final Date commD1) {
        this.commD1 = commD1;
    }

    /**
     * @return the commLo1
     */
    public String getCommLo1() {
        return commLo1;
    }

    /**
     * @param commLo1 the commLo1 to set
     */
    public void setCommLo1(final String commLo1) {
        this.commLo1 = commLo1;
    }

}
