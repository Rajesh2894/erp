package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author apurva.salgaonkar
 * @since 19 Jan 2017
 */
@Entity
@Table(name = "TB_CONTRACT_TERMS_DETAIL")
public class ContractTermsDetailEntity implements Serializable {

    private static final long serialVersionUID = -3637420617722208532L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CONTT_ID", precision = 12, scale = 0, nullable = false)
    private long conttId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONT_ID", nullable = false)
    private ContractMastEntity contId;

    @Column(name = "CONTT_DESCRIPTION", length = 200, nullable = false)
    private String conttDescription;

    @Column(name = "CONTT_ACTIVE", length = 1, nullable = false)
    private String conttActive;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public String[] getPkValues() {
        return new String[] { MainetConstants.CommonConstants.COM, MainetConstants.RnLDetailEntity.TB_CONT_TERMS,
                MainetConstants.RnLDetailEntity.CONTT_ID };
    }

    /**
     * @return the conttId
     */
    public long getConttId() {
        return conttId;
    }

    /**
     * @param conttId the conttId to set
     */
    public void setConttId(final long conttId) {
        this.conttId = conttId;
    }

    /**
     * @return the contId
     */
    public ContractMastEntity getContId() {
        return contId;
    }

    /**
     * @param contId the contId to set
     */
    public void setContId(final ContractMastEntity contId) {
        this.contId = contId;
    }

    /**
     * @return the conttDescription
     */
    public String getConttDescription() {
        return conttDescription;
    }

    /**
     * @param conttDescription the conttDescription to set
     */
    public void setConttDescription(final String conttDescription) {
        this.conttDescription = conttDescription;
    }

    /**
     * @return the conttActive
     */
    public String getConttActive() {
        return conttActive;
    }

    /**
     * @param conttActive the conttActive to set
     */
    public void setConttActive(final String conttActive) {
        this.conttActive = conttActive;
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
}