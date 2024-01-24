/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author sarojkumar.yadav
 *
 * DTO Class for Chart of Depreciation Master
 */
public class ChartOfDepreciationMasterDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1913212978911222756L;

    private Long groupId;
    @NotNull(message = "{asset.depreciationMaster.vldnn.groupName}")
    @NotEmpty(message = "{asset.depreciationMaster.vldnn.groupName}")
    private String name;
    @NotNull(message = "{asset.depreciationMaster.vldnn.accountCode}")
    private String accountCode;
    private String remark;
    @NotNull(message = "{asset.depreciationMaster.vldnn.assetClass}")
    private Long assetClass;
    @NotNull(message = "{asset.depreciationMaster.vldnn.frequency}")
    private Long frequency;
    // @NotNull(message = "{asset.depreciationMaster.vldnn.rate}")
    private BigDecimal rate;
    @NotNull(message = "{asset.depreciationMaster.vldnn.depreciationKey}")
    private Long depreciationKey;
    private Date createdDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long orgId;
    private String accountCodeDesc;
    private String assetClassDesc;
    private String frequencyDesc;
    private String depreciationKeyDesc;
    private String deptCode;

    /**
     * @return the groupId
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
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
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the assetClass
     */
    public Long getAssetClass() {
        return assetClass;
    }

    /**
     * @param assetClass the assetClass to set
     */
    public void setAssetClass(Long assetClass) {
        this.assetClass = assetClass;
    }

    /**
     * @return the frequency
     */
    public Long getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    /**
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * @return the depreciationKey
     */
    public Long getDepreciationKey() {
        return depreciationKey;
    }

    /**
     * @param depreciationKey the depreciationKey to set
     */
    public void setDepreciationKey(Long depreciationKey) {
        this.depreciationKey = depreciationKey;
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
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
    public void setUpdatedBy(Long updatedBy) {
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
    public void setUpdatedDate(Date updatedDate) {
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
    public void setLgIpMac(String lgIpMac) {
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
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
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
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the accountCodeDesc
     */
    public String getAccountCodeDesc() {
        return accountCodeDesc;
    }

    /**
     * @param accountCodeDesc the accountCodeDesc to set
     */
    public void setAccountCodeDesc(String accountCodeDesc) {
        this.accountCodeDesc = accountCodeDesc;
    }

    /**
     * @return the assetClassDesc
     */
    public String getAssetClassDesc() {
        return assetClassDesc;
    }

    /**
     * @param assetClassDesc the assetClassDesc to set
     */
    public void setAssetClassDesc(String assetClassDesc) {
        this.assetClassDesc = assetClassDesc;
    }

    /**
     * @return the frequencyDesc
     */
    public String getFrequencyDesc() {
        return frequencyDesc;
    }

    /**
     * @param frequencyDesc the frequencyDesc to set
     */
    public void setFrequencyDesc(String frequencyDesc) {
        this.frequencyDesc = frequencyDesc;
    }

    /**
     * @return the depreciationKeyDesc
     */
    public String getDepreciationKeyDesc() {
        return depreciationKeyDesc;
    }

    /**
     * @param depreciationKeyDesc the depreciationKeyDesc to set
     */
    public void setDepreciationKeyDesc(String depreciationKeyDesc) {
        this.depreciationKeyDesc = depreciationKeyDesc;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Override
    public String toString() {
        return "ChartOfDepreciationMasterDTO [groupId=" + groupId + ", name=" + name + ", accountCode=" + accountCode
                + ", remark=" + remark + ", assetClass=" + assetClass + ", frequency=" + frequency + ", rate=" + rate
                + ", depreciationKey=" + depreciationKey + ", createdDate=" + createdDate + ", createdBy=" + createdBy
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", orgId=" + orgId + ", accountCodeDesc=" + accountCodeDesc + ", assetClassDesc=" + assetClassDesc
                + ", frequencyDesc=" + frequencyDesc + ", depreciationKeyDesc=" + depreciationKeyDesc + ", deptCode=" + deptCode
                + "]";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

}
