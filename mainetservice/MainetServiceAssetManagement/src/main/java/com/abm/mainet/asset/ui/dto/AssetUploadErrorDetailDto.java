/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.util.Date;

/**
 * @author satish.rathore
 *
 */
public class AssetUploadErrorDetailDto {

    private Long errId;

    private String errData;

    private String errDescription;

    private String fileName;

    private Long orgId;

    private Long createdBy;

    private Date creationDate;

    private String lgIpMac;

    private String assetType;

    /**
     * @return the errId
     */
    public Long getErrId() {
        return errId;
    }

    /**
     * @param errId the errId to set
     */
    public void setErrId(Long errId) {
        this.errId = errId;
    }

    /**
     * @return the errData
     */
    public String getErrData() {
        return errData;
    }

    /**
     * @param errData the errData to set
     */
    public void setErrData(String errData) {
        this.errData = errData;
    }

    /**
     * @return the errDescription
     */
    public String getErrDescription() {
        return errDescription;
    }

    /**
     * @param errDescription the errDescription to set
     */
    public void setErrDescription(String errDescription) {
        this.errDescription = errDescription;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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
     * @return the assetType
     */
    public String getAssetType() {
        return assetType;
    }

    /**
     * @param assetType the assetType to set
     */
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AssetUploadErrorDetailDto [errId=" + errId + ", errData=" + errData + ", errDescription=" + errDescription
                + ", fileName=" + fileName + ", orgId=" + orgId + ", createdBy=" + createdBy + ", creationDate=" + creationDate
                + ", lgIpMac=" + lgIpMac + ", assetType=" + assetType + "]";
    }

}
