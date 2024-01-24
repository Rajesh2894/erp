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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_AST_SERVICE_REALESTD_HIST")
public class ServiceInformationHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3174231924994722107L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_SERVICE_HIST_ID", nullable = false)
    private Long astServiceHistId;

    @Column(name = "ASSET_SERVICE_ID", nullable = false)
    private Long assetServiceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetId;

    @Column(name = "SERVICE_NUMBER", nullable = true)
    private Long serviceNo;

    @Column(name = "SERVICE_PROVIDER", nullable = true)
    private String serviceProvider;

    @Column(name = "SERVICE_START_DATE", nullable = true)
    private Date serviceStartDate;

    @Column(name = "SERVICE_EXPIRY_DATE", nullable = true)
    private Date serviceExpiryDate;

    @Column(name = "SERVICE_AMOUNT", nullable = true)
    private BigDecimal serviceAmount;

    @Column(name = "WARRANTY", nullable = true)
    private Long warrenty;

    @Column(name = "COST_CENTRE", nullable = true)
    private String costCenter;

    @Column(name = "SERVICE_CONTENT", nullable = true)
    private String serviceContent;

    @Column(name = "SERVICE_DESCRIPTION", nullable = true)
    private String serviceDescription;

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

    @Column(name = "MAN_TYPID", nullable = true)
    private Long manTypeId; // Maintenance Type - Prefix

    @Column(name = "MAN_CATID", nullable = true)
    private Long manCatId; // Maintenance Category - Prefix ID

    @Temporal(TemporalType.DATE)
    @Column(name = "MAN_DATE", nullable = true)
    private Date manDate;

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

    @Column(name = "H_STATUS", nullable = true)
    private String historyFlag;

    public Long getAstServiceHistId() {
        return astServiceHistId;
    }

    public void setAstServiceHistId(Long astServiceHistId) {
        this.astServiceHistId = astServiceHistId;
    }

    public Long getAssetServiceId() {
        return assetServiceId;
    }

    public void setAssetServiceId(Long assetServiceId) {
        this.assetServiceId = assetServiceId;
    }

    public AssetInformation getAssetId() {
        return assetId;
    }

    public void setAssetId(AssetInformation assetId) {
        this.assetId = assetId;
    }

    public Long getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Long serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public Date getServiceExpiryDate() {
        return serviceExpiryDate;
    }

    public void setServiceExpiryDate(Date serviceExpiryDate) {
        this.serviceExpiryDate = serviceExpiryDate;
    }

    public BigDecimal getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(BigDecimal serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public Long getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(Long warrenty) {
        this.warrenty = warrenty;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
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

    public Long getManTypeId() {
        return manTypeId;
    }

    public void setManTypeId(Long manTypeId) {
        this.manTypeId = manTypeId;
    }

    public Long getManCatId() {
        return manCatId;
    }

    public void setManCatId(Long manCatId) {
        this.manCatId = manCatId;
    }

    public Date getManDate() {
        return manDate;
    }

    public void setManDate(Date manDate) {
        this.manDate = manDate;
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

    public String getHistoryFlag() {
        return historyFlag;
    }

    public void setHistoryFlag(String historyFlag) {
        this.historyFlag = historyFlag;
    }

    public String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_SERVICE_REALESTD_HIST",
                "ASSET_SERVICE_HIST_ID" };
    }
}
