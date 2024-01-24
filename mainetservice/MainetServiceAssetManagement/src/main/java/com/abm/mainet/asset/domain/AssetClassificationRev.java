
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
 * Persistent class for Asset Classification entity stored in table "TB_AST_CLASSFCTN"
 *
 * @author Anuj Dixit
 *
 */

@Entity
@Table(name = "TB_AST_CLASSFCTN_REV")
public class AssetClassificationRev implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8939226294250174066L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_CLASSFCTN_ID_REV", nullable = false)
    private Long assetClassfcnIdRev;

    @Column(name = "ASSET_CLASSFCTN_ID", nullable = true)
    private Long assetClassificationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetId;

    @Column(name = "CREATION_DATE", nullable = true)
    private Date creationDate;

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

    @Column(name = "FUNCTIONLOCATION_CODE", nullable = false)
    private Long functionalLocationCode;

    @Column(name = "DEPARTMENT", nullable = false)
    private Long department;

    @Column(name = "GIS_ID", nullable = true)
    private BigDecimal gisId;

    @Column(name = "COST_CENTRE", nullable = true)
    private String costCenter;

    @Column(name = "LAT_POINT", nullable = true)
    private BigDecimal latitude;

    @Column(name = "LONG_POINT", nullable = true)
    private BigDecimal longitude;

    @Column(name = "LOC_ID", nullable = true)
    private Long location;

    @Column(name = "SURVEY_NO", length = 300, nullable = true)
    private String surveyNo;

    @Column(name = "ADDRESS", length = 500, nullable = true)
    private String address;

    @Column(name = "EAST", length = 200, nullable = true)
    private String east;

    @Column(name = "WEST", length = 200, nullable = true)
    private String west;

    @Column(name = "SOUTH", length = 200, nullable = true)
    private String south;

    @Column(name = "NORTH", length = 200, nullable = true)
    private String north;

    public Long getAssetClassfcnIdRev() {
        return assetClassfcnIdRev;
    }

    public void setAssetClassfcnIdRev(Long assetClassfcnIdRev) {
        this.assetClassfcnIdRev = assetClassfcnIdRev;
    }

    public AssetInformation getAssetId() {
        return assetId;
    }

    public void setAssetId(AssetInformation assetId) {
        this.assetId = assetId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public Long getFunctionalLocationCode() {
        return functionalLocationCode;
    }

    public void setFunctionalLocationCode(Long functionalLocationCode) {
        this.functionalLocationCode = functionalLocationCode;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    public BigDecimal getGisId() {
        return gisId;
    }

    public void setGisId(BigDecimal gisId) {
        this.gisId = gisId;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getAssetClassificationId() {
        return assetClassificationId;
    }

    public void setAssetClassificationId(Long assetClassificationId) {
        this.assetClassificationId = assetClassificationId;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
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

    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_CLASSFCTN_REV",
                "ASSET_CLASSIFICATION_ID" };
    }

}
