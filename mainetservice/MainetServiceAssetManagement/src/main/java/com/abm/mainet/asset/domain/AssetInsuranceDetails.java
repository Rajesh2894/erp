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
 * Persistent class for Asset Insurance Details entity stored in table "TB_AST_INSURANCE"
 * 
 * @author sarojkumar.yadav
 *
 */
@Entity
@Table(name = "TB_AST_INSURANCE")
public class AssetInsuranceDetails implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3817203877906664764L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
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

    /**
     * @return the assetInsuranceId
     */
    public Long getAssetInsuranceId() {
        return assetInsuranceId;
    }

    /**
     * @param assetInsuranceId the assetInsuranceId to set
     */
    public void setAssetInsuranceId(Long assetInsuranceId) {
        this.assetInsuranceId = assetInsuranceId;
    }

    /**
     * @return the assetId
     */
    public AssetInformation getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(AssetInformation assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the insuranceNo
     */
    public String getInsuranceNo() {
        return insuranceNo;
    }

    /**
     * @param insuranceNo the insuranceNo to set
     */
    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    /**
     * @return the insuranceProvider
     */
    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    /**
     * @param insuranceProvider the insuranceProvider to set
     */
    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    /**
     * @return the typeOfInsurance
     */

    /**
     * @return the insuranceRate
     */
    public BigDecimal getInsuranceRate() {
        return insuranceRate;
    }

    public Long getTypeOfInsurance() {
        return typeOfInsurance;
    }

    public void setTypeOfInsurance(Long typeOfInsurance) {
        this.typeOfInsurance = typeOfInsurance;
    }

    /**
     * @param insuranceRate the insuranceRate to set
     */
    public void setInsuranceRate(BigDecimal insuranceRate) {
        this.insuranceRate = insuranceRate;
    }

    /**
     * @return the insuranceAmount
     */
    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    /**
     * @param insuranceAmount the insuranceAmount to set
     */
    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    /**
     * @return the premiumFrequency
     */
    public Long getPremiumFrequency() {
        return premiumFrequency;
    }

    /**
     * @param premiumFrequency the premiumFrequency to set
     */
    public void setPremiumFrequency(Long premiumFrequency) {
        this.premiumFrequency = premiumFrequency;
    }

    /**
     * @return the premiumValue
     */
    public BigDecimal getPremiumValue() {
        return premiumValue;
    }

    /**
     * @param premiumValue the premiumValue to set
     */
    public void setPremiumValue(BigDecimal premiumValue) {
        this.premiumValue = premiumValue;
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

    /**
     * @return the insuranceStartDate
     */
    public Date getInsuranceStartDate() {
        return insuranceStartDate;
    }

    /**
     * @param insuranceStartDate the insuranceStartDate to set
     */
    public void setInsuranceStartDate(Date insuranceStartDate) {
        this.insuranceStartDate = insuranceStartDate;
    }

    /**
     * @return the insuranceEndDate
     */
    public Date getInsuranceEndDate() {
        return insuranceEndDate;
    }

    /**
     * @param insuranceEndDate the insuranceEndDate to set
     */
    public void setInsuranceEndDate(Date insuranceEndDate) {
        this.insuranceEndDate = insuranceEndDate;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
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

    public String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_INSURANCE", "ASSET_INSURANCE_ID" };
    }
}
