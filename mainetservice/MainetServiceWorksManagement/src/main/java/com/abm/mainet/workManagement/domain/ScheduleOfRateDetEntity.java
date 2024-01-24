package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */
@Entity
@Table(name = "TB_WMS_SOR_DET")
public class ScheduleOfRateDetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SORD_ID", nullable = false)
    private Long sordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOR_ID", nullable = false)
    private ScheduleOfRateMstEntity scheduleOfRateMst;

    @Column(name = "SORD_ITEMNO", nullable = false)
    private String sorDIteamNo;

    @Column(name = "SORD_DESCRIPTION", nullable = false, length = 4000)
    private String sorDDescription;

    @Column(name = "SOR_ITEM_UNIT", nullable = false)
    private Long sorIteamUnit;

    @Column(name = "SOR_BASIC_RATE", nullable = false)
    private BigDecimal sorBasicRate;

    @Column(name = "SOR_LABOUR_RATE", nullable = false)
    private BigDecimal sorLabourRate;

    @Column(name = "SORD_ACTIVE")
    private String sorDActive;

    @Column(name = "orgId", nullable = false)
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
    private String igIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String igIpMacUpd;

    @Column(name = "LE_TO")
    private BigDecimal leadUpto;

    @Column(name = "LI_TO")
    private BigDecimal liftUpto;

    @Column(name = "LE_UNIT")
    private Long leadUnit;

    @Column(name = "SORD_CATEGORY")
    private Long sordCategory;

    @Column(name = "SORD_SUBCATEGORY")
    private String sordSubCategory;

    public Long getSordId() {
        return sordId;
    }

    public void setSordId(Long sordId) {
        this.sordId = sordId;
    }

    public ScheduleOfRateMstEntity getScheduleOfRateMst() {
        return scheduleOfRateMst;
    }

    public void setScheduleOfRateMst(ScheduleOfRateMstEntity scheduleOfRateMst) {
        this.scheduleOfRateMst = scheduleOfRateMst;
    }

    public String getSorDIteamNo() {
        return sorDIteamNo;
    }

    public void setSorDIteamNo(String sorDIteamNo) {
        this.sorDIteamNo = sorDIteamNo;
    }

    public String getSorDDescription() {
        return sorDDescription;
    }

    public void setSorDDescription(String sorDDescription) {
        this.sorDDescription = sorDDescription;
    }

    public Long getSorIteamUnit() {
        return sorIteamUnit;
    }

    public void setSorIteamUnit(Long sorIteamUnit) {
        this.sorIteamUnit = sorIteamUnit;
    }

    public BigDecimal getSorBasicRate() {
        return sorBasicRate;
    }

    public void setSorBasicRate(BigDecimal sorBasicRate) {
        this.sorBasicRate = sorBasicRate;
    }

    public BigDecimal getSorLabourRate() {
        return sorLabourRate;
    }

    public void setSorLabourRate(BigDecimal sorLabourRate) {
        this.sorLabourRate = sorLabourRate;
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

    public String getIgIpMac() {
        return igIpMac;
    }

    public void setIgIpMac(String igIpMac) {
        this.igIpMac = igIpMac;
    }

    public String getSorDActive() {
        return sorDActive;
    }

    public void setSorDActive(String sorDActive) {
        this.sorDActive = sorDActive;
    }

    public String getIgIpMacUpd() {
        return igIpMacUpd;
    }

    public void setIgIpMacUpd(String igIpMacUpd) {
        this.igIpMacUpd = igIpMacUpd;
    }

    public BigDecimal getLeadUpto() {
        return leadUpto;
    }

    public void setLeadUpto(BigDecimal leadUpto) {
        this.leadUpto = leadUpto;
    }

    public BigDecimal getLiftUpto() {
        return liftUpto;
    }

    public void setLiftUpto(BigDecimal liftUpto) {
        this.liftUpto = liftUpto;
    }

    public Long getLeadUnit() {
        return leadUnit;
    }

    public void setLeadUnit(Long leadUnit) {
        this.leadUnit = leadUnit;
    }

    public Long getSordCategory() {
        return sordCategory;
    }

    public void setSordCategory(Long sordCategory) {
        this.sordCategory = sordCategory;
    }

    public String getSordSubCategory() {
        return sordSubCategory;
    }

    public void setSordSubCategory(String sordSubCategory) {
        this.sordSubCategory = sordSubCategory;
    }

    public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_SOR_DET", "SORD_ID" };
    }

}
