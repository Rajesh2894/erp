/**
 * 
 */
package com.abm.mainet.asset.domain;

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
@Table(name = "TB_AST_REALESTD")
public class AssetRealStateInformation {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_REAL_STATE_ID", nullable = false)
    private Long assetRealStateId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetId;

    @Column(name = "ASSESSMENT_NO", nullable = true)
    private Long assessmentNo;

    @Column(name = "PROPERTY_REGISTRATION_NO", nullable = true)
    private Long propertyRegistrationNo;

    @Column(name = "TAX_CODE", nullable = true)
    private Long taxCode;

    @Column(name = "REAL_ESTATE_AMOUNT", nullable = true)
    private BigDecimal realEstateAmount;

    @Column(name = "TAX_ZONE_LOCATION", nullable = true)
    private String taxZoneLocation;

    @Column(name = "MUNICIPALITY_NAME", nullable = true)
    private String municipalityName;

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

    public Long getAssetRealStateId() {
        return assetRealStateId;
    }

    public void setAssetRealStateId(Long assetRealStateId) {
        this.assetRealStateId = assetRealStateId;
    }

    public AssetInformation getAssetId() {
        return assetId;
    }

    public void setAssetId(AssetInformation assetId) {
        this.assetId = assetId;
    }

    public Long getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(Long assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public Long getPropertyRegistrationNo() {
        return propertyRegistrationNo;
    }

    public void setPropertyRegistrationNo(Long propertyRegistrationNo) {
        this.propertyRegistrationNo = propertyRegistrationNo;
    }

    public Long getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(Long taxCode) {
        this.taxCode = taxCode;
    }

    public BigDecimal getRealEstateAmount() {
        return realEstateAmount;
    }

    public void setRealEstateAmount(BigDecimal realEstateAmount) {
        this.realEstateAmount = realEstateAmount;
    }

    public String getTaxZoneLocation() {
        return taxZoneLocation;
    }

    public void setTaxZoneLocation(String taxZoneLocation) {
        this.taxZoneLocation = taxZoneLocation;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
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

    public String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_REALESTD", "ASSET_REAL_STATE_ID" };
    }
}
