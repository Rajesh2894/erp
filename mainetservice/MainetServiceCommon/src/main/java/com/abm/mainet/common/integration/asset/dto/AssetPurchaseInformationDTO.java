/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO class for Asset Management Purchase Information
 * 
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AssetPurchaseInformation")
public class AssetPurchaseInformationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6028700992077112731L;
    private Long assetPurchaserId;
    private Long assetId;
    // @NotNull(message = "{asset.vldnn.vendor}")
    private Long fromWhomAcquired;
    // @NotNull(message = "{asset.vldnn.manufacturer}")
    // @NotEmpty(message = "{asset.vldnn.manufacturer}")
    private String manufacturer;
    // @NotNull(message = "{asset.vldnn.purchaseOrderNo}")
    private String purchaseOrderNo;
    @NotNull(message = "{asset.vldnn.dateofacquisition}")
    private Date dateOfAcquisition;
    @NotNull(message = "{asset.vldnn.costOfAcquisition}")
    private BigDecimal costOfAcquisition;
    // Task #5318
    // @NotNull(message = "{asset.vldnn.bookValue}")
    private BigDecimal initialBookValue;
    private BigDecimal latestBookValue;
    private Long modeOfPayment;
    private Long countryOfOrigin1;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Date purchaseOrderDate;
    private Date initialBookDate;
    private String devProposal;
    /**
     * as discussed with S and R at 15-11-2018
     * 
     */
    private Date warrantyTillDate;

    /**
     * this is for User Story #3468: Asset demo observations - set 1
     */
    private Date astCreationDate;

    private String licenseNo;

    private Date licenseDate;

    /**
     * @return the assetPurchaserId
     */
    public Long getAssetPurchaserId() {
        return assetPurchaserId;
    }

    /**
     * @param assetPurchaserId the assetPurchaserId to set
     */
    public void setAssetPurchaserId(Long assetPurchaserId) {
        this.assetPurchaserId = assetPurchaserId;
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
     * @return the fromWhomAcquired
     */
    public Long getFromWhomAcquired() {
        return fromWhomAcquired;
    }

    /**
     * @param fromWhomAcquired the fromWhomAcquired to set
     */
    public void setFromWhomAcquired(Long fromWhomAcquired) {
        this.fromWhomAcquired = fromWhomAcquired;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the purchaseOrderNo
     */
    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    /**
     * @param purchaseOrderNo the purchaseOrderNo to set
     */
    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    /**
     * @return the dateOfAcquisition
     */
    public Date getDateOfAcquisition() {
        return dateOfAcquisition;
    }

    /**
     * @param dateOfAcquisition the dateOfAcquisition to set
     */
    public void setDateOfAcquisition(Date dateOfAcquisition) {
        this.dateOfAcquisition = dateOfAcquisition;
    }

    public BigDecimal getCostOfAcquisition() {
        return costOfAcquisition;
    }

    public void setCostOfAcquisition(BigDecimal costOfAcquisition) {
        this.costOfAcquisition = costOfAcquisition;
    }

    /**
     * @return the initialBookValue
     */
    public BigDecimal getInitialBookValue() {
        return initialBookValue;
    }

    /**
     * @param initialBookValue the initialBookValue to set
     */
    public void setInitialBookValue(BigDecimal initialBookValue) {
        this.initialBookValue = initialBookValue;
    }

    /**
     * @return the latestBookValue
     */
    public BigDecimal getLatestBookValue() {
        return latestBookValue;
    }

    /**
     * @param latestBookValue the latestBookValue to set
     */
    public void setLatestBookValue(BigDecimal latestBookValue) {
        this.latestBookValue = latestBookValue;
    }

    /**
     * @return the modeOfPayment
     */
    public Long getModeOfPayment() {
        return modeOfPayment;
    }

    /**
     * @param modeOfPayment the modeOfPayment to set
     */
    public void setModeOfPayment(Long modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    /**
     * @return the countryOfOrigin1
     */
    public Long getCountryOfOrigin1() {
        return countryOfOrigin1;
    }

    /**
     * @param countryOfOrigin1 the countryOfOrigin1 to set
     */
    public void setCountryOfOrigin1(Long countryOfOrigin1) {
        this.countryOfOrigin1 = countryOfOrigin1;
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

    public Date getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(Date purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public Date getAstCreationDate() {
        return astCreationDate;
    }

    public void setAstCreationDate(Date astCreationDate) {
        this.astCreationDate = astCreationDate;
    }

    public Date getInitialBookDate() {
        return initialBookDate;
    }

    public void setInitialBookDate(Date initialBookDate) {
        this.initialBookDate = initialBookDate;
    }

    public Date getWarrantyTillDate() {
        return warrantyTillDate;
    }

    public void setWarrantyTillDate(Date warrantyTillDate) {
        this.warrantyTillDate = warrantyTillDate;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    @Override
    public String toString() {
        return "AssetPurchaseInformationDTO [assetPurchaserId=" + assetPurchaserId + ", assetId=" + assetId
                + ", fromWhomAcquired=" + fromWhomAcquired + ", manufacturer=" + manufacturer + ", purchaseOrderNo="
                + purchaseOrderNo + ", dateOfAcquisition=" + dateOfAcquisition + ", costOfAcquisition=" + costOfAcquisition
                + ", initialBookValue=" + initialBookValue + ", latestBookValue=" + latestBookValue + ", modeOfPayment="
                + modeOfPayment + ", countryOfOrigin1=" + countryOfOrigin1 + ", creationDate=" + creationDate + ", createdBy="
                + createdBy + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", purchaseOrderDate=" + purchaseOrderDate + ", initialBookDate="
                + initialBookDate + ", warrantyTillDate=" + warrantyTillDate + ", astCreationDate=" + astCreationDate
                + ", licenseNo=" + licenseNo + ", licenseDate=" + licenseDate + "]";
    }

	public String getDevProposal() {
		return devProposal;
	}

	public void setDevProposal(String devProposal) {
		this.devProposal = devProposal;
	}

}
