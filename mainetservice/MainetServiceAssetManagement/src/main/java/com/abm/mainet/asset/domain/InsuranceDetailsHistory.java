/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_AST_INSURANCE_HIST")
public class InsuranceDetailsHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6355878765181771304L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_INSURANCE_HIST_ID", nullable = false)
    private Long astInsuranceHistId;

    @Column(name = "ASSET_INSURANCE_ID", nullable = false)
    private Long assetInsuranceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetId;

    @Column(name = "INSURANCE_NO", nullable = false)
    private String insuranceNo;

    @Column(name = "INSURANCE_PROVIDER", nullable = true)
    private String insuranceProvider;

    @Column(name = "TYPE_OF_INSURANCE", nullable = true)
    private Long typeOfInsurance;

    @Column(name = "INSURANCE_RATE", nullable = true)
    private BigDecimal insuranceRate;

    @Column(name = "INSURED_AMOUNT", nullable = true)
    private BigDecimal insuranceAmount;

    @Column(name = "PREMIUM_FREQUENCY", nullable = true)
    private Long premiumFrequency;

    @Column(name = "PREMIUM_VALUE", nullable = true)
    private BigDecimal premiumValue;

    @Column(name = "STATUS", nullable = true)
    private String status;

    @Column(name = "INSURANCE_START_DATE", nullable = true)
    private Date insuranceStartDate;

    @Column(name = "INSURANCE_END_DATE", nullable = true)
    private Date insuranceEndDate;

    @Column(name = "COST_CENTRE", nullable = true)
    private String costCenter;

    @Column(name = "REMARK", nullable = true)
    private String remark;

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

    @Column(name = "H_STATUS", nullable = true)
    private String historyFlag;

    public Long getAstInsuranceHistId() {
        return astInsuranceHistId;
    }

    public Long getAssetInsuranceId() {
        return assetInsuranceId;
    }

    public void setAssetInsuranceId(Long assetInsuranceId) {
        this.assetInsuranceId = assetInsuranceId;
    }

    public AssetInformation getAssetId() {
        return assetId;
    }

    public void setAssetId(AssetInformation assetId) {
        this.assetId = assetId;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public BigDecimal getInsuranceRate() {
        return insuranceRate;
    }

    public void setInsuranceRate(BigDecimal insuranceRate) {
        this.insuranceRate = insuranceRate;
    }

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Long getPremiumFrequency() {
        return premiumFrequency;
    }

    public void setPremiumFrequency(Long premiumFrequency) {
        this.premiumFrequency = premiumFrequency;
    }

    public BigDecimal getPremiumValue() {
        return premiumValue;
    }

    public void setPremiumValue(BigDecimal premiumValue) {
        this.premiumValue = premiumValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getInsuranceStartDate() {
        return insuranceStartDate;
    }

    public void setInsuranceStartDate(Date insuranceStartDate) {
        this.insuranceStartDate = insuranceStartDate;
    }

    public Date getInsuranceEndDate() {
        return insuranceEndDate;
    }

    public void setInsuranceEndDate(Date insuranceEndDate) {
        this.insuranceEndDate = insuranceEndDate;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getHistoryFlag() {
        return historyFlag;
    }

    public void setHistoryFlag(String historyFlag) {
        this.historyFlag = historyFlag;
    }

    public void setAstInsuranceHistId(Long astInsuranceHistId) {
        this.astInsuranceHistId = astInsuranceHistId;
    }

    public Long getTypeOfInsurance() {
        return typeOfInsurance;
    }

    public void setTypeOfInsurance(Long typeOfInsurance) {
        this.typeOfInsurance = typeOfInsurance;
    }

    public String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_INSURANCE_HIST",
                "ASSET_INSURANCE_HIST_ID" };
    }

}
