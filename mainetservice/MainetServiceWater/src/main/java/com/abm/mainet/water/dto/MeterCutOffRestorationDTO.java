package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Arun.Chavda
 *
 */
public class MeterCutOffRestorationDTO implements Serializable {

    private static final long serialVersionUID = 4864625791725539045L;

    private Long mcrId;

    private Long mmMtnid;

    private String cutResDate;

    private Long cutResRead;

    private String cutResRemark;

    private String mmCutResFlag;

    private String mmBulkEntryFlag;

    private Long plumId;

    private Long csIdn;

    private String mmWtSupply;

    private String mmEntryFlag;

    private String mmPorted;

    private String remark;

    private Long orgId;

    private Long userId;

    private int langId;

    private Date lmodDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long meterStatus;

    private String connectionNo;

    private String meterNo;

    private String ownership;

    private String cutOffDate;

    private String approvedBy;

    private String meterBillingFlag;

    private String meteredFlag;

    /**
     * @return the mcrId
     */
    public Long getMcrId() {
        return mcrId;
    }

    /**
     * @param mcrId the mcrId to set
     */
    public void setMcrId(final Long mcrId) {
        this.mcrId = mcrId;
    }

    /**
     * @return the mmMtnid
     */
    public Long getMmMtnid() {
        return mmMtnid;
    }

    /**
     * @param mmMtnid the mmMtnid to set
     */
    public void setMmMtnid(final Long mmMtnid) {
        this.mmMtnid = mmMtnid;
    }

    /**
     * @return the mmCutResFlag
     */
    public String getMmCutResFlag() {
        return mmCutResFlag;
    }

    /**
     * @param mmCutResFlag the mmCutResFlag to set
     */
    public void setMmCutResFlag(final String mmCutResFlag) {
        this.mmCutResFlag = mmCutResFlag;
    }

    /**
     * @return the mmBulkEntryFlag
     */
    public String getMmBulkEntryFlag() {
        return mmBulkEntryFlag;
    }

    /**
     * @param mmBulkEntryFlag the mmBulkEntryFlag to set
     */
    public void setMmBulkEntryFlag(final String mmBulkEntryFlag) {
        this.mmBulkEntryFlag = mmBulkEntryFlag;
    }

    /**
     * @return the plumId
     */
    public Long getPlumId() {
        return plumId;
    }

    /**
     * @param plumId the plumId to set
     */
    public void setPlumId(final Long plumId) {
        this.plumId = plumId;
    }

    /**
     * @return the csIdn
     */
    public Long getCsIdn() {
        return csIdn;
    }

    /**
     * @param csIdn the csIdn to set
     */
    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    /**
     * @return the mmWtSupply
     */
    public String getMmWtSupply() {
        return mmWtSupply;
    }

    /**
     * @param mmWtSupply the mmWtSupply to set
     */
    public void setMmWtSupply(final String mmWtSupply) {
        this.mmWtSupply = mmWtSupply;
    }

    /**
     * @return the mmEntryFlag
     */
    public String getMmEntryFlag() {
        return mmEntryFlag;
    }

    /**
     * @param mmEntryFlag the mmEntryFlag to set
     */
    public void setMmEntryFlag(final String mmEntryFlag) {
        this.mmEntryFlag = mmEntryFlag;
    }

    /**
     * @return the mmPorted
     */
    public String getMmPorted() {
        return mmPorted;
    }

    /**
     * @param mmPorted the mmPorted to set
     */
    public void setMmPorted(final String mmPorted) {
        this.mmPorted = mmPorted;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(final String remark) {
        this.remark = remark;
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

    public Long getMeterStatus() {
        return meterStatus;
    }

    public void setMeterStatus(final Long meterStatus) {
        this.meterStatus = meterStatus;
    }

    public String getConnectionNo() {
        return connectionNo;
    }

    public void setConnectionNo(final String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public String getCutResDate() {
        return cutResDate;
    }

    public void setCutResDate(final String cutResDate) {
        this.cutResDate = cutResDate;
    }

    public Long getCutResRead() {
        return cutResRead;
    }

    public void setCutResRead(final Long cutResRead) {
        this.cutResRead = cutResRead;
    }

    public String getCutResRemark() {
        return cutResRemark;
    }

    public void setCutResRemark(final String cutResRemark) {
        this.cutResRemark = cutResRemark;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(final String meterNo) {
        this.meterNo = meterNo;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(final String ownership) {
        this.ownership = ownership;
    }

    /**
     * @return the cutOffDate
     */
    public String getCutOffDate() {
        return cutOffDate;
    }

    /**
     * @param cutOffDate the cutOffDate to set
     */
    public void setCutOffDate(final String cutOffDate) {
        this.cutOffDate = cutOffDate;
    }

    /**
     * @return the approvedBy
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * @param approvedBy the approvedBy to set
     */
    public void setApprovedBy(final String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getMeterBillingFlag() {
        return meterBillingFlag;
    }

    public void setMeterBillingFlag(final String meterBillingFlag) {
        this.meterBillingFlag = meterBillingFlag;
    }

    /**
     * @return the meteredFlag
     */
    public String getMeteredFlag() {
        return meteredFlag;
    }

    /**
     * @param meteredFlag the meteredFlag to set
     */
    public void setMeteredFlag(final String meteredFlag) {
        this.meteredFlag = meteredFlag;
    }

}
