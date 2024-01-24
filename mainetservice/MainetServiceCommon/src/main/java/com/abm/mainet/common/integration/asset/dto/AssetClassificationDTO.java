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
 * DTO class for Asset Management Classification
 * 
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AssetClassification")
public class AssetClassificationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7950853091245001826L;

    private Long assetClassificationId;
    private Long assetId;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    // @NotNull(message = "{asset.vldnn.locCode}")
    private Long functionalLocationCode;
    @NotNull(message = "{asset.vldnn.Dept}")
    private Long department;
    private BigDecimal gisId;
    private String costCenter;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long location;
    private String accountCode;
    private String surveyNo;
    private String address;

    private String east;
    private String west;
    private String south;
    private String north;

    /**
     * @return the assetClassificationId
     */
    public Long getAssetClassificationId() {
        return assetClassificationId;
    }

    /**
     * @param assetClassificationId the assetClassificationId to set
     */
    public void setAssetClassificationId(Long assetClassificationId) {
        this.assetClassificationId = assetClassificationId;
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
     * @return the functionalLocationCode
     */
    public Long getFunctionalLocationCode() {
        return functionalLocationCode;
    }

    /**
     * @param functionalLocationCode the functionalLocationCode to set
     */
    public void setFunctionalLocationCode(Long functionalLocationCode) {
        this.functionalLocationCode = functionalLocationCode;
    }

    /**
     * @return the department
     */
    public Long getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(Long department) {
        this.department = department;
    }

    /**
     * @return the gisId
     */
    public BigDecimal getGisId() {
        return gisId;
    }

    /**
     * @param gisId the gisId to set
     */
    public void setGisId(BigDecimal gisId) {
        this.gisId = gisId;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    /**
     * @return the latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    @Override
    public String toString() {
        return "AssetClassificationDTO [assetClassificationId=" + assetClassificationId + ", assetId=" + assetId
                + ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", functionalLocationCode="
                + functionalLocationCode + ", department=" + department + ", gisId=" + gisId + ", costCenter=" + costCenter
                + ", latitude=" + latitude + ", longitude=" + longitude + ", location=" + location + ", accountCode="
                + accountCode + ", surveyNo=" + surveyNo + "]";
    }

}
