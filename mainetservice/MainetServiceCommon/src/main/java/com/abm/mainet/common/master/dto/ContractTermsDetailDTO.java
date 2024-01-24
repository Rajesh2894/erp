package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author apurva.salgaonkar
 *
 */
public class ContractTermsDetailDTO implements Serializable {

    private static final long serialVersionUID = 4885697246982845491L;
    private long conttId;
    private ContractMastDTO contId;
    private String conttDescription;
    private String conttActive;
    private Long orgId;
    private Long createdBy;
    private int langId;
    private Date lmodDate;
    private Long updatedBy;
    private Date updatedDate;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;
    private String active;

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
    public ContractMastDTO getContId() {
        return contId;
    }

    /**
     * @param contId the contId to set
     */
    public void setContId(final ContractMastDTO contId) {
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

    public String getActive() {
        return active;
    }

    public void setActive(final String active) {
        this.active = active;
    }
}
