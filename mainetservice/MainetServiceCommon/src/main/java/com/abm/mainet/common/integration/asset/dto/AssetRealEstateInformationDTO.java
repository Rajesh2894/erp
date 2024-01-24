/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * DTO class for Asset Management Real Estate Information
 * 
 * @author sarojkumar.yadav
 *
 */
public class AssetRealEstateInformationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2302501144265750941L;

    private Long assetRealEstId;
    private Long assetId;
    private String assessmentNo;
    @NotNull(message = "{asset.real.estate.vldnn.propertyRegistrationNo}")
    private String propertyRegistrationNo;
    private String taxCode;
    private BigDecimal realEstAmount;
    private String taxZoneLocation;
    private String municipalityName;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Boolean isRealEstate = false;

    /**
     * @return the assetRealEstId
     */
    public Long getAssetRealEstId() {
        return assetRealEstId;
    }

    /**
     * @param assetRealEstId the assetRealEstId to set
     */
    public void setAssetRealEstId(Long assetRealEstId) {
        this.assetRealEstId = assetRealEstId;
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
     * @return the assessmentNo
     */
    public String getAssessmentNo() {
        return assessmentNo;
    }

    /**
     * @param assessmentNo the assessmentNo to set
     */
    public void setAssessmentNo(String assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public String getPropertyRegistrationNo() {
        return propertyRegistrationNo;
    }

    public void setPropertyRegistrationNo(String propertyRegistrationNo) {
        this.propertyRegistrationNo = propertyRegistrationNo;
    }

    /**
     * @return the taxCode
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * @param taxCode the taxCode to set
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    /**
     * @return the realEstAmount
     */
    public BigDecimal getRealEstAmount() {
        return realEstAmount;
    }

    /**
     * @param realEstAmount the realEstAmount to set
     */
    public void setRealEstAmount(BigDecimal realEstAmount) {
        this.realEstAmount = realEstAmount;
    }

    /**
     * @return the taxZoneLocation
     */
    public String getTaxZoneLocation() {
        return taxZoneLocation;
    }

    /**
     * @param taxZoneLocation the taxZoneLocation to set
     */
    public void setTaxZoneLocation(String taxZoneLocation) {
        this.taxZoneLocation = taxZoneLocation;
    }

    /**
     * @return the municipalityName
     */
    public String getMunicipalityName() {
        return municipalityName;
    }

    /**
     * @param municipalityName the municipalityName to set
     */
    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    /*
     * @Override public String toString() { return "AssetRealEstateInformationDTO [assetRealEstId=" + assetRealEstId +
     * ", assetId=" + assetId + ", assessmentNo=" + assessmentNo + ", propertyRegistrationNo=" + propertyRegistrationNo +
     * ", taxCode=" + taxCode + ", realEstAmount=" + realEstAmount + ", taxZoneLocation=" + taxZoneLocation +
     * ", municipalityName=" + municipalityName + ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", updatedBy=" +
     * updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]"; }
     */
    /**
     * @return the isRealEstate
     */
    public Boolean getIsRealEstate() {
        return isRealEstate;
    }

    /**
     * @param isRealEstate the isRealEstate to set
     */
    public void setIsRealEstate(Boolean isRealEstate) {
        this.isRealEstate = isRealEstate;
    }

    @Override
    public String toString() {
        return "AssetRealEstateInformationDTO [assetRealEstId=" + assetRealEstId + ", assetId=" + assetId + ", assessmentNo="
                + assessmentNo + ", propertyRegistrationNo=" + propertyRegistrationNo + ", taxCode=" + taxCode
                + ", realEstAmount=" + realEstAmount + ", taxZoneLocation=" + taxZoneLocation + ", municipalityName="
                + municipalityName + ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", isRealEstate="
                + isRealEstate + "]";
    }

}
