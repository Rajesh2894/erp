/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO class for Asset Management Insurance Details
 * 
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AssetInsuranceDetails")
public class AssetInsuranceDetailsDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1152969178534291925L;

    private Long assetInsuranceId;
    private Long assetId;
    @NotNull(message = "{asset.insurance.vldnn.insuranceno}")
    @NotEmpty(message = "{asset.insurance.vldnn.insuranceno}")
    private String insuranceNo;
    @NotNull(message = "{asset.insurance.vldnn.insuranceprov}")
    @NotEmpty(message = "{asset.insurance.vldnn.insuranceprov}")
    private String insuranceProvider;
    private Long typeOfInsurance;
    private BigDecimal insuranceRate;
    private BigDecimal insuranceAmount;
    private Long premiumFrequency;
    private BigDecimal premiumValue;
    private String status;
    private Date insuranceStartDate;
    private Date insuranceEndDate;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String costCenter;
    private String remark;
    private Long revGrpId;
    private String revGrpIdentity;
    private String subModeType;
    private Boolean isInsuApplicable = false;

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
    public Long getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(Long assetId) {
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

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public Long getRevGrpId() {
        return revGrpId;
    }

    public void setRevGrpId(Long revGrpId) {
        this.revGrpId = revGrpId;
    }

    public String getRevGrpIdentity() {
        return revGrpIdentity;
    }

    public void setRevGrpIdentity(String revGrpIdentity) {
        this.revGrpIdentity = revGrpIdentity;
    }

    public String getSubModeType() {
        return subModeType;
    }

    public void setSubModeType(String subModeType) {
        this.subModeType = subModeType;
    }

    public Boolean getIsInsuApplicable() {
        return isInsuApplicable;
    }

    public void setIsInsuApplicable(Boolean isInsuApplicable) {
        this.isInsuApplicable = isInsuApplicable;
    }

    @Override
    public String toString() {
        return "AssetInsuranceDetailsDTO [assetInsuranceId=" + assetInsuranceId + ", assetId=" + assetId + ", insuranceNo="
                + insuranceNo + ", insuranceProvider=" + insuranceProvider + ", typeOfInsurance=" + typeOfInsurance
                + ", insuranceRate=" + insuranceRate + ", insuranceAmount=" + insuranceAmount + ", premiumFrequency="
                + premiumFrequency + ", premiumValue=" + premiumValue + ", status=" + status + ", insuranceStartDate="
                + insuranceStartDate + ", insuranceEndDate=" + insuranceEndDate + ", creationDate=" + creationDate
                + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
                + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", costCenter=" + costCenter + ", remark=" + remark + ", revGrpId="
                + revGrpId + ", revGrpIdentity=" + revGrpIdentity + ", subModeType=" + subModeType + ", isInsuApplicable="
                + isInsuApplicable + "]";
    }

}
