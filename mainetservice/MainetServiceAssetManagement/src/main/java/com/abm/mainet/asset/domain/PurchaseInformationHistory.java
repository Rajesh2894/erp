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
@Table(name = "TB_AST_PURCHASER_HIST")
public class PurchaseInformationHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -335525190692918143L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_PURCHASER_HIST_ID", nullable = false)
    private Long astPurHisId;

    @Column(name = "ASSET_PURCHASER_ID", nullable = false)
    private Long assetPurchaserId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetId;

    @Column(name = "VENDOR", nullable = true)
    private Long fromWhomAcquired;

    @Column(name = "MANUFACTURER", nullable = false)
    private String manufacturer;

    @Column(name = "PURCHASE_ORDER_NO", nullable = true)
    private Long purchaseOrderNo;

    @Column(name = "PURCHASE_DATE", nullable = false)
    private Date dateOfAcquisition;

    @Column(name = "PURCHASE_COST", nullable = false)
    private BigDecimal costOfAcquisition;

    @Column(name = "BOOK_VALUE", nullable = true)
    private BigDecimal bookValue;

    @Column(name = "MODE_OF_PAYMENT", nullable = true)
    private Long modeOfPayment;

    @Column(name = "COUNTRY_OF_ORIGIN", nullable = true)
    private Long countryOfOrigin1;

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

    /*
     * this is for User Story #3468: Asset demo observations - set 1
     */
    @Column(name = "AST_CREATION_DATE", nullable = true)
    private Date astCreationDate;

    @Column(name = "initial_book_date", nullable = true)
    private Date initialBookDate;

    /*
     * as discussed with S and R at 15-11-2018
     */

    @Column(name = "WARRANTY_TILL_DATE", nullable = true)
    private Date warrantyTillDate;

    @Column(name = "LICENSE_NO", nullable = true)
    private String licenseNo;

    @Column(name = "LICENSE_DATE", nullable = true)
    private Date licenseDate;

    public Long getAstPurHisId() {
        return astPurHisId;
    }

    public Long getAssetPurchaserId() {
        return assetPurchaserId;
    }

    public void setAssetPurchaserId(Long assetPurchaserId) {
        this.assetPurchaserId = assetPurchaserId;
    }

    public AssetInformation getAssetId() {
        return assetId;
    }

    public void setAssetId(AssetInformation assetId) {
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(Long purchaseOrderNo) {
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

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public BigDecimal getCostOfAcquisition() {
        return costOfAcquisition;
    }

    public void setCostOfAcquisition(BigDecimal costOfAcquisition) {
        this.costOfAcquisition = costOfAcquisition;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public Long getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(Long modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public Long getCountryOfOrigin1() {
        return countryOfOrigin1;
    }

    public void setCountryOfOrigin1(Long countryOfOrigin1) {
        this.countryOfOrigin1 = countryOfOrigin1;
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

    public void setAstPurHisId(Long astPurHisId) {
        this.astPurHisId = astPurHisId;
    }

    /**
     * @return the astCreationDate
     */
    public Date getAstCreationDate() {
        return astCreationDate;
    }

    /**
     * @param astCreationDate the astCreationDate to set
     */
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

    public String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_PURCHASER_HIST",
                "astPurHisId" };
    }
}
