/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO class for Asset Management Service Information
 * 
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AssetServiceInformation")
public class AssetServiceInformationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -977954804454163524L;

    private Long assetServiceId;
    private Long assetId;
    @NotNull(message = "{asset.service.serialno}")
    // @NotEmpty(message = "{asset.service.serialno}")
    private String serviceNo;
    @NotNull(message = "{asset.service.provider}")
    // @NotEmpty(message = "{asset.service.provider}")
    private String serviceProvider;
    private Date serviceStartDate;
    private Date serviceExpiryDate;
    private BigDecimal amount;
    private Long warrenty;
    private String costCenter;
    private String serviceContent;
    private String serviceDescription;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long revGroupId;
    private String revGrpIdentity;
    private String editFlag;

    // use at the time of maintenance
    private Long manTypeId; // Maintenance Type - Prefix
    private Long manCatId; // Maintenance Category - Prefix ID
    private Date manDate;

    private AssetRealEstateInformationDTO assetRealEstateInfoDTO;
    private Boolean isServiceAplicable = true;
    private Boolean checkedService = false;// D#79659

    /**
     * @return the assetServiceId
     */
    public Long getAssetServiceId() {
        return assetServiceId;
    }

    /**
     * @param assetServiceId the assetServiceId to set
     */
    public void setAssetServiceId(Long assetServiceId) {
        this.assetServiceId = assetServiceId;
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

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    /**
     * @return the serviceProvider
     */
    public String getServiceProvider() {
        return serviceProvider;
    }

    /**
     * @param serviceProvider the serviceProvider to set
     */
    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    /**
     * @return the serviceStartDate
     */
    public Date getServiceStartDate() {
        return serviceStartDate;
    }

    /**
     * @param serviceStartDate the serviceStartDate to set
     */
    public void setServiceStartDate(Date serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    /**
     * @return the serviceExpiryDate
     */
    public Date getServiceExpiryDate() {
        return serviceExpiryDate;
    }

    /**
     * @param serviceExpiryDate the serviceExpiryDate to set
     */
    public void setServiceExpiryDate(Date serviceExpiryDate) {
        this.serviceExpiryDate = serviceExpiryDate;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the warrenty
     */
    public Long getWarrenty() {
        return warrenty;
    }

    /**
     * @param warrenty the warrenty to set
     */
    public void setWarrenty(Long warrenty) {
        this.warrenty = warrenty;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    /**
     * @return the serviceContent
     */
    public String getServiceContent() {
        return serviceContent;
    }

    /**
     * @param serviceContent the serviceContent to set
     */
    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    /**
     * @return the serviceDescription
     */
    public String getServiceDescription() {
        return serviceDescription;
    }

    /**
     * @param serviceDescription the serviceDescription to set
     */
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
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

    /**
     * @return the assetRealEstateInfoDTO
     */
    public AssetRealEstateInformationDTO getAssetRealEstateInfoDTO() {
        return assetRealEstateInfoDTO;
    }

    /**
     * @param assetRealEstateInfoDTO the assetRealEstateInfoDTO to set
     */
    public void setAssetRealEstateInfoDTO(AssetRealEstateInformationDTO assetRealEstateInfoDTO) {
        this.assetRealEstateInfoDTO = assetRealEstateInfoDTO;
    }

    /**
     * @return the revGroupId
     */
    public Long getRevGroupId() {
        return revGroupId;
    }

    /**
     * @param revGroupId the revGroupId to set
     */
    public void setRevGroupId(Long revGroupId) {
        this.revGroupId = revGroupId;
    }

    /**
     * @return the revGrpIdentity
     */
    public String getRevGrpIdentity() {
        return revGrpIdentity;
    }

    /**
     * @param revGrpIdentity the revGrpIdentity to set
     */
    public void setRevGrpIdentity(String revGrpIdentity) {
        this.revGrpIdentity = revGrpIdentity;
    }

    /**
     * @return the editFlag
     */
    public String getEditFlag() {
        return editFlag;
    }

    /**
     * @param editFlag the editFlag to set
     */
    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }

    /**
     * @return the isServiceAplicable
     */
    public Boolean getIsServiceAplicable() {
        return isServiceAplicable;
    }

    /**
     * @param isServiceAplicable the isServiceAplicable to set
     */
    public void setIsServiceAplicable(Boolean isServiceAplicable) {
        this.isServiceAplicable = isServiceAplicable;
    }

    public Boolean getCheckedService() {
        return checkedService;
    }

    public void setCheckedService(Boolean checkedService) {
        this.checkedService = checkedService;
    }

    @Override
    public String toString() {
        return "AssetServiceInformationDTO [assetServiceId=" + assetServiceId + ", assetId=" + assetId + ", serviceNo="
                + serviceNo + ", serviceProvider=" + serviceProvider + ", serviceStartDate=" + serviceStartDate
                + ", serviceExpiryDate=" + serviceExpiryDate + ", amount=" + amount + ", warrenty=" + warrenty + ", costCenter="
                + costCenter + ", serviceContent=" + serviceContent + ", serviceDescription=" + serviceDescription
                + ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", revGroupId=" + revGroupId
                + ", revGrpIdentity=" + revGrpIdentity + ", editFlag=" + editFlag + ", assetRealEstateInfoDTO="
                + assetRealEstateInfoDTO + ", isServiceAplicable=" + isServiceAplicable + "]";
    }

}
