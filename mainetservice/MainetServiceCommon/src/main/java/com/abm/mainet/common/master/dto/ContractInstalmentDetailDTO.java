package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author apurva.salgaonkar
 *
 */
public class ContractInstalmentDetailDTO implements Serializable {

    private static final long serialVersionUID = 62062452190346235L;
    private long conitId;
    private ContractMastDTO contId;
    private Long conitAmtType;
    private Double conitValue;
    private Date conitDueDate;
    private String conitMilestone;
    private String conttActive;
    private String conitPrFlag;
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
    private Double contAmt;
    private Long taxId;
    private String active;
    private Date conitStartDate;
    private String remark;

    /**
     * @return the conitId
     */
    public long getConitId() {
        return conitId;
    }

    /**
     * @param conitId the conitId to set
     */
    public void setConitId(final long conitId) {
        this.conitId = conitId;
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
     * @return the conitAmtType
     */
    public Long getConitAmtType() {
        return conitAmtType;
    }

    /**
     * @param conitAmtType the conitAmtType to set
     */
    public void setConitAmtType(final Long conitAmtType) {
        this.conitAmtType = conitAmtType;
    }

    /**
     * @return the conitValue
     */
    public Double getConitValue() {
        return conitValue;
    }

    /**
     * @param conitValue the conitValue to set
     */
    public void setConitValue(final Double conitValue) {
        this.conitValue = conitValue;
    }

    /**
     * @return the conitMilestone
     */
    public String getConitMilestone() {
        return conitMilestone;
    }

    /**
     * @param conitMilestone the conitMilestone to set
     */
    public void setConitMilestone(final String conitMilestone) {
        this.conitMilestone = conitMilestone;
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
     * @return the conitPrFlag
     */
    public String getConitPrFlag() {
        return conitPrFlag;
    }

    /**
     * @param conitPrFlag the conitPrFlag to set
     */
    public void setConitPrFlag(final String conitPrFlag) {
        this.conitPrFlag = conitPrFlag;
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

    public Double getContAmt() {
        return contAmt;
    }

    public void setContAmt(final Double contAmt) {
        this.contAmt = contAmt;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(final String active) {
        this.active = active;
    }

    public Date getConitDueDate() {
        return conitDueDate;
    }

    public void setConitDueDate(final Date conitDueDate) {
        this.conitDueDate = conitDueDate;
    }

    public Date getConitStartDate() {
        return conitStartDate;
    }

    public void setConitStartDate(final Date conitStartDate) {
        this.conitStartDate = conitStartDate;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}

