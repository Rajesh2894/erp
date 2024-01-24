package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_WMS_LEADLIFT_MAST")
public class WmsLeadLiftMasterEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7203012892924671428L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LELI_ID", precision = 12, scale = 0, nullable = false)
    private Long leLiId;

    @Column(name = "LELI_FROM", length = 10, nullable = true)
    private BigDecimal leLiFrom;

    @Column(name = "LELI_TO", nullable = false)
    private BigDecimal leLiTo;

    @Column(name = "LELI_UNIT", nullable = false)
    private Long leLiUnit;

    @Column(name = "LELI_RATE", length = 1000, precision = 12, scale = 2, nullable = false)
    private BigDecimal leLiRate;

    @Column(name = "LELI_FLAG", nullable = true)
    private String leLiFlag;

    @Column(name = "LELI_ACTIVE", nullable = true)
    private String leLiActive;

    @Column(name = "LELI_SLABFLG", nullable = false)
    private String leLiSlabFlg;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @ManyToOne
    @JoinColumn(name = "SOR_ID", referencedColumnName = "SOR_ID", nullable = false)
    private ScheduleOfRateMstEntity sorMaster;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = false)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getLeLiId() {
        return leLiId;
    }

    public void setLeLiId(Long leLiId) {
        this.leLiId = leLiId;
    }

    public BigDecimal getLeLiRate() {
        return leLiRate;
    }

    public void setLeLiRate(BigDecimal leLiRate) {
        this.leLiRate = leLiRate;
    }

    public String getLeLiFlag() {
        return leLiFlag;
    }

    public void setLeLiFlag(String leLiFlag) {
        this.leLiFlag = leLiFlag;
    }

    public BigDecimal getLeLiFrom() {
        return leLiFrom;
    }

    public void setLeLiFrom(BigDecimal leLiFrom) {
        this.leLiFrom = leLiFrom;
    }

    public BigDecimal getLeLiTo() {
        return leLiTo;
    }

    public void setLeLiTo(BigDecimal leLiTo) {
        this.leLiTo = leLiTo;
    }

    public Long getLeLiUnit() {
        return leLiUnit;
    }

    public void setLeLiUnit(Long leLiUnit) {
        this.leLiUnit = leLiUnit;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getLeLiActive() {
        return leLiActive;
    }

    public void setLeLiActive(String leLiActive) {
        this.leLiActive = leLiActive;
    }

    public String getLeLiSlabFlg() {
        return leLiSlabFlg;
    }

    public void setLeLiSlabFlg(String leLiSlabFlg) {
        this.leLiSlabFlg = leLiSlabFlg;
    }

    public ScheduleOfRateMstEntity getSorMaster() {
        return sorMaster;
    }

    public void setSorMaster(ScheduleOfRateMstEntity sorMaster) {
        this.sorMaster = sorMaster;
    }

    public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_LEADLIFT_MAST", "LELI_ID" };
    }

}
