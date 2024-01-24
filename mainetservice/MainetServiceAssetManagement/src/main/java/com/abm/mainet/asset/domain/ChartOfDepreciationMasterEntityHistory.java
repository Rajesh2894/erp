/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author sarojkumar.yadav
 *
 * Entity History Class for Chart of Depreciation Master
 */
@Entity
@Table(name = "TB_AST_CHAOFDEP_MAS_HIST")
public class ChartOfDepreciationMasterEntityHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8339516500770001456L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "GROUP_HIST_ID", nullable = false)
    private Long groupHistId;

    @Column(name = "GROUP_ID", nullable = false)
    private Long groupId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ACCOUNT_CODE", nullable = false)
    private String accountCode;

    @Column(name = "ASSET_CLASS", nullable = false)
    private Long assetClass;

    @Column(name = "FREQUENCY", nullable = false)
    private Long frequency;

    @Column(name = "REMARK", nullable = true)
    private String remark;

    @Column(name = "RATE", nullable = true)
    private BigDecimal rate;

    @Column(name = "DEPRECIATION_KEY", nullable = false)
    private Long depreciationKey;

    @Column(name = "CREATION_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "H_STATUS", length = 1)
    private String status;

    @Column(name = "DEPT_CODE", length = 5, nullable = false)
    private String deptCode;

    /**
     * @return the groupHistId
     */
    public Long getGroupHistId() {
        return groupHistId;
    }

    /**
     * @param groupHistId the groupHistId to set
     */
    public void setGroupHistId(Long groupHistId) {
        this.groupHistId = groupHistId;
    }

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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_CHAOFDEP_MAS_HIST",
                "GROUP_HIST_ID" };
    }
}
