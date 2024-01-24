/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * DTO class for Asset Management Inventory Information
 * 
 * @author sarojkumar.yadav
 *
 */
public class AssetInventoryInformationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5289782888684898486L;

    private Long assetId;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long inventoryNo;
    private Date lastInventoryOn;
    private String inventoryNote;
    private String includeAssetInventoryLst;

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

    /**
     * @return the inventoryNo
     */
    public Long getInventoryNo() {
        return inventoryNo;
    }

    /**
     * @param inventoryNo the inventoryNo to set
     */
    public void setInventoryNo(Long inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    /**
     * @return the lastInventoryOn
     */
    public Date getLastInventoryOn() {
        return lastInventoryOn;
    }

    /**
     * @param lastInventoryOn the lastInventoryOn to set
     */
    public void setLastInventoryOn(Date lastInventoryOn) {
        this.lastInventoryOn = lastInventoryOn;
    }

    /**
     * @return the inventoryNote
     */
    public String getInventoryNote() {
        return inventoryNote;
    }

    /**
     * @param inventoryNote the inventoryNote to set
     */
    public void setInventoryNote(String inventoryNote) {

        this.inventoryNote = inventoryNote;
    }

    /**
     * @return the includeAssetInventoryLst
     */
    public String getIncludeAssetInventoryLst() {

        return includeAssetInventoryLst;
    }

    /**
     * @param includeAssetInventoryLst the includeAssetInventoryLst to set
     */
    public void setIncludeAssetInventoryLst(String includeAssetInventoryLst) {
        if (StringUtils.isEmpty(includeAssetInventoryLst)) {
            this.includeAssetInventoryLst = "N";
        } else {
            this.includeAssetInventoryLst = includeAssetInventoryLst;
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AssetInventoryInformationDTO [assetId=" + assetId + ", creationDate=" + creationDate + ", createdBy="
                + createdBy + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", inventoryNo=" + inventoryNo + ", lastInventoryOn=" + lastInventoryOn
                + ", inventoryNote=" + inventoryNote + ", includeAssetInventoryLst=" + includeAssetInventoryLst + "]";
    }

}
