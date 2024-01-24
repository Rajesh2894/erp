package com.abm.mainet.adh.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vishwajeet.kumar
 * @since 25 Sept 2019
 */
@Entity
@Table(name = "TB_ADH_DET")
public class NewAdvertisementApplicationDet implements Serializable {

    private static final long serialVersionUID = 9113055461698006733L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ADHDET_ID")
    private Long adhHrdDetId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ADH_ID", referencedColumnName = "ADH_ID")
    private NewAdvertisementApplication newAdvertisement;

    @Column(name = "ADH_TYPE_ID1")
    private Long adhTypeId1;

    @Column(name = "ADH_TYPE_ID2")
    private Long adhTypeId2;

    @Column(name = "ADH_TYPE_ID3")
    private Long adhTypeId3;

    @Column(name = "ADH_TYPE_ID4")
    private Long adhTypeId4;

    @Column(name = "ADH_TYPE_ID5")
    private Long adhTypeId5;

    @Column(name = "ADH_DESC")
    private String advDetailsDesc;

    @Column(name = "ADH_LENGTH")
    private BigDecimal advDetailsLength;

    @Column(name = "ADH_HEIGHT")
    private BigDecimal advDetailsHeight;

    @Column(name = "ADH_AREA")
    private BigDecimal advDetailsArea;

    @Column(name = "ADH_UNIT")
    private Long unit;

    @Column(name = "HRD_ID")
    private Long hrdId;

    @Column(name = "DISPL_TYPID")
    private Long dispTypeId;

    @Column(name = "ADH_FEE")
    private BigDecimal hrdFee;

    @Column(name = "GIS_ID")
    private String gisId;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public Long getAdhHrdDetId() {
        return adhHrdDetId;
    }

    public void setAdhHrdDetId(Long adhHrdDetId) {
        this.adhHrdDetId = adhHrdDetId;
    }

    public NewAdvertisementApplication getNewAdvertisement() {
        return newAdvertisement;
    }

    public void setNewAdvertisement(NewAdvertisementApplication newAdvertisement) {
        this.newAdvertisement = newAdvertisement;
    }

    public Long getAdhTypeId1() {
        return adhTypeId1;
    }

    public void setAdhTypeId1(Long adhTypeId1) {
        this.adhTypeId1 = adhTypeId1;
    }

    public Long getAdhTypeId2() {
        return adhTypeId2;
    }

    public void setAdhTypeId2(Long adhTypeId2) {
        this.adhTypeId2 = adhTypeId2;
    }

    public Long getAdhTypeId3() {
        return adhTypeId3;
    }

    public void setAdhTypeId3(Long adhTypeId3) {
        this.adhTypeId3 = adhTypeId3;
    }

    public Long getAdhTypeId4() {
        return adhTypeId4;
    }

    public void setAdhTypeId4(Long adhTypeId4) {
        this.adhTypeId4 = adhTypeId4;
    }

    public Long getAdhTypeId5() {
        return adhTypeId5;
    }

    public void setAdhTypeId5(Long adhTypeId5) {
        this.adhTypeId5 = adhTypeId5;
    }

    public String getAdvDetailsDesc() {
        return advDetailsDesc;
    }

    public void setAdvDetailsDesc(String advDetailsDesc) {
        this.advDetailsDesc = advDetailsDesc;
    }

    public BigDecimal getAdvDetailsLength() {
        return advDetailsLength;
    }

    public void setAdvDetailsLength(BigDecimal advDetailsLength) {
        this.advDetailsLength = advDetailsLength;
    }

    public BigDecimal getAdvDetailsHeight() {
        return advDetailsHeight;
    }

    public void setAdvDetailsHeight(BigDecimal advDetailsHeight) {
        this.advDetailsHeight = advDetailsHeight;
    }

    public BigDecimal getAdvDetailsArea() {
        return advDetailsArea;
    }

    public void setAdvDetailsArea(BigDecimal advDetailsArea) {
        this.advDetailsArea = advDetailsArea;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Long getHrdId() {
        return hrdId;
    }

    public void setHrdId(Long hrdId) {
        this.hrdId = hrdId;
    }

    public Long getDispTypeId() {
        return dispTypeId;
    }

    public void setDispTypeId(Long dispTypeId) {
        this.dispTypeId = dispTypeId;
    }

    public BigDecimal getHrdFee() {
        return hrdFee;
    }

    public void setHrdFee(BigDecimal hrdFee) {
        this.hrdFee = hrdFee;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(String gisId) {
        this.gisId = gisId;
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
        return new String[] { "ADH", "TB_ADH_DET", "ADHDET_ID" };
    }
}
