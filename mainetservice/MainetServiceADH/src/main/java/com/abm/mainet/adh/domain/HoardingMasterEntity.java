package com.abm.mainet.adh.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Anwarul.Hassan
 * @since 16-Aug-2019
 */
@Entity
@Table(name = "TB_ADH_HOARDING_MAS")
public class HoardingMasterEntity implements Serializable {

    private static final long serialVersionUID = 5877654736213399721L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HRD_ID", length = 12, nullable = false)
    private Long hoardingId;

    @Column(name = "HRD_NO", length = 50, nullable = false)
    private String hoardingNumber;

    @Column(name = "HRD_OLDNO", length = 100, nullable = true)
    private String hoardingOldNumber;

    @Column(name = "HRD_REGDATE", nullable = true)
    private Date hoardingRegDate;

    @Column(name = "GIS_ID", length = 20, nullable = true)
    private String gisId;

    @Column(name = "HRD_LOCID", length = 12, nullable = false)
    private Long locationId;

    @Column(name = "HRD_ZONE1", length = 12, nullable = false)
    private Long hoardingZone1;

    @Column(name = "HRD_ZONE2", length = 12, nullable = true)
    private Long hoardingZone2;

    @Column(name = "HRD_ZONE3", length = 12, nullable = true)
    private Long hoardingZone3;

    @Column(name = "HRD_ZONE4", length = 12, nullable = true)
    private Long hoardingZone4;

    @Column(name = "HRD_ZONE5", length = 12, nullable = true)
    private Long hoardingZone5;

    @Column(name = "HRD_TYPE_ID1", length = 12, nullable = false)
    private Long hoardingTypeId1;

    @Column(name = "HRD_TYPE_ID2", length = 12, nullable = true)
    private Long hoardingTypeId2;

    @Column(name = "HRD_TYPE_ID3", length = 12, nullable = true)
    private Long hoardingTypeId3;

    @Column(name = "HRD_TYPE_ID4", length = 12, nullable = true)
    private Long hoardingTypeId4;

    @Column(name = "HRD_TYPE_ID5", length = 12, nullable = true)
    private Long hoardingTypeId5;

    @Column(name = "DISPL_TYPID", length = 12, nullable = false)
    private Long displayTypeId;

    @Column(name = "HRD_DESC", length = 100, nullable = false)
    private String hoardingDescription;

    @Column(name = "HRD_LENGTH", length = 12, nullable = false)
    private BigDecimal hoardingLength;

    @Column(name = "HRD_HEIGHT", length = 12, nullable = false)
    private BigDecimal hoardingHeight;

    @Column(name = "HRD_AREA", length = 12, nullable = false)
    private BigDecimal hoardingArea;

    @Column(name = "PROPERTY_TYPID", length = 12, nullable = false)
    private Long hoardingPropTypeId;

    @Column(name = "PROPERTY_ID", length = 20, nullable = true)
    private String hoardingPropertyId;

    @Column(name = "PT_OWNER_NAME", length = 100, nullable = true)
    private String propOwnerName;

    @Column(name = "UID_NO", length = 12, nullable = true)
    private Long uidNo;

    @Column(name = "HRD_FLAG", length = 1, nullable = false)
    private String hoardingFlag;

    @Column(name = "HRD_STATUS", length = 1, nullable = false)
    private Long hoardingStatus;

    @Column(name = "HRD_REMARK", length = 100, nullable = true)
    private String hoardingRemark;

    @Column(name = "ORGID", length = 12, nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", length = 12, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LANG_ID", length = 12, nullable = false)
    private Long langId;

    @Column(name = "UPDATED_BY", length = 12, nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getHoardingId() {
        return hoardingId;
    }

    public void setHoardingId(Long hoardingId) {
        this.hoardingId = hoardingId;
    }

    public String getHoardingNumber() {
        return hoardingNumber;
    }

    public void setHoardingNumber(String hoardingNumber) {
        this.hoardingNumber = hoardingNumber;
    }

    public String getHoardingOldNumber() {
        return hoardingOldNumber;
    }

    public void setHoardingOldNumber(String hoardingOldNumber) {
        this.hoardingOldNumber = hoardingOldNumber;
    }

    public Date getHoardingRegDate() {
        return hoardingRegDate;
    }

    public void setHoardingRegDate(Date hoardingRegDate) {
        this.hoardingRegDate = hoardingRegDate;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(String gisId) {
        this.gisId = gisId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getHoardingZone1() {
        return hoardingZone1;
    }

    public void setHoardingZone1(Long hoardingZone1) {
        this.hoardingZone1 = hoardingZone1;
    }

    public Long getHoardingZone2() {
        return hoardingZone2;
    }

    public void setHoardingZone2(Long hoardingZone2) {
        this.hoardingZone2 = hoardingZone2;
    }

    public Long getHoardingZone3() {
        return hoardingZone3;
    }

    public void setHoardingZone3(Long hoardingZone3) {
        this.hoardingZone3 = hoardingZone3;
    }

    public Long getHoardingZone4() {
        return hoardingZone4;
    }

    public void setHoardingZone4(Long hoardingZone4) {
        this.hoardingZone4 = hoardingZone4;
    }

    public Long getHoardingZone5() {
        return hoardingZone5;
    }

    public void setHoardingZone5(Long hoardingZone5) {
        this.hoardingZone5 = hoardingZone5;
    }

    public Long getHoardingTypeId1() {
        return hoardingTypeId1;
    }

    public void setHoardingTypeId1(Long hoardingTypeId1) {
        this.hoardingTypeId1 = hoardingTypeId1;
    }

    public Long getHoardingTypeId2() {
        return hoardingTypeId2;
    }

    public void setHoardingTypeId2(Long hoardingTypeId2) {
        this.hoardingTypeId2 = hoardingTypeId2;
    }

    public Long getHoardingTypeId3() {
        return hoardingTypeId3;
    }

    public void setHoardingTypeId3(Long hoardingTypeId3) {
        this.hoardingTypeId3 = hoardingTypeId3;
    }

    public Long getHoardingTypeId4() {
        return hoardingTypeId4;
    }

    public void setHoardingTypeId4(Long hoardingTypeId4) {
        this.hoardingTypeId4 = hoardingTypeId4;
    }

    public Long getHoardingTypeId5() {
        return hoardingTypeId5;
    }

    public void setHoardingTypeId5(Long hoardingTypeId5) {
        this.hoardingTypeId5 = hoardingTypeId5;
    }

    public Long getDisplayTypeId() {
        return displayTypeId;
    }

    public void setDisplayTypeId(Long displayTypeId) {
        this.displayTypeId = displayTypeId;
    }

    public String getHoardingDescription() {
        return hoardingDescription;
    }

    public void setHoardingDescription(String hoardingDescription) {
        this.hoardingDescription = hoardingDescription;
    }

    public BigDecimal getHoardingLength() {
        return hoardingLength;
    }

    public void setHoardingLength(BigDecimal hoardingLength) {
        this.hoardingLength = hoardingLength;
    }

    public BigDecimal getHoardingHeight() {
        return hoardingHeight;
    }

    public void setHoardingHeight(BigDecimal hoardingHeight) {
        this.hoardingHeight = hoardingHeight;
    }

    public BigDecimal getHoardingArea() {
        return hoardingArea;
    }

    public void setHoardingArea(BigDecimal hoardingArea) {
        this.hoardingArea = hoardingArea;
    }

    public Long getHoardingPropTypeId() {
        return hoardingPropTypeId;
    }

    public void setHoardingPropTypeId(Long hoardingPropTypeId) {
        this.hoardingPropTypeId = hoardingPropTypeId;
    }

    public String getHoardingPropertyId() {
        return hoardingPropertyId;
    }

    public void setHoardingPropertyId(String hoardingPropertyId) {
        this.hoardingPropertyId = hoardingPropertyId;
    }

    public String getPropOwnerName() {
        return propOwnerName;
    }

    public void setPropOwnerName(String propOwnerName) {
        this.propOwnerName = propOwnerName;
    }

    public Long getUidNo() {
        return uidNo;
    }

    public void setUidNo(Long uidNo) {
        this.uidNo = uidNo;
    }

    public String getHoardingFlag() {
        return hoardingFlag;
    }

    public void setHoardingFlag(String hoardingFlag) {
        this.hoardingFlag = hoardingFlag;
    }

    public Long getHoardingStatus() {
        return hoardingStatus;
    }

    public void setHoardingStatus(Long hoardingStatus) {
        this.hoardingStatus = hoardingStatus;
    }

    public String getHoardingRemark() {
        return hoardingRemark;
    }

    public void setHoardingRemark(String hoardingRemark) {
        this.hoardingRemark = hoardingRemark;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String[] getPkValues() {
        return new String[] { "ADH", "TB_ADH_HOARDING_MAS", "HRD_ID" };
    }

}
