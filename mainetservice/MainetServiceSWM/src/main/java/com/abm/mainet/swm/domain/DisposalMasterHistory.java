package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_disposal_mast_hist database table.
 * 
 */
@Entity
@Table(name = "TB_SW_DISPOSAL_MAST_HIST")
public class DisposalMasterHistory implements Serializable {

    private static final long serialVersionUID = 7793302319487153270L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DE_ID_H")
    private Long deIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "DE_ACTIVE")
    private String deActive;

    @Column(name = "DE_ADDRESS")
    private String deAddress;

    @Column(name = "DE_AREA")
    private BigDecimal deArea;

    @Column(name = "DE_AREA_UNIT")
    private Long deAreaUnit;

    @Column(name = "DE_CAPACITY")
    private BigDecimal deCapacity;

    @Column(name = "DE_CAPACITY_UNIT")
    private Long deCapacityUnit;

    @Column(name = "DE_CATEGORY")
    private Long deCategory;

    @Column(name = "DE_GIS_ID")
    private String deGisId;

    @Column(name = "DE_ID")
    private Long deId;

    @Column(name = "LOC_ID")
    private Long deLocId;

    @Column(name = "DE_NAME")
    private String deName;

    @Column(name = "DE_NAME_REG")
    private String deNameReg;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public DisposalMasterHistory() {
    }

    public Long getDeIdH() {
        return this.deIdH;
    }

    public void setDeIdH(Long deIdH) {
        this.deIdH = deIdH;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeActive() {
        return this.deActive;
    }

    public void setDeActive(String deActive) {
        this.deActive = deActive;
    }

    public String getDeAddress() {
        return this.deAddress;
    }

    public void setDeAddress(String deAddress) {
        this.deAddress = deAddress;
    }

    public BigDecimal getDeArea() {
        return this.deArea;
    }

    public void setDeArea(BigDecimal deArea) {
        this.deArea = deArea;
    }

    public Long getDeAreaUnit() {
        return deAreaUnit;
    }

    public void setDeAreaUnit(Long deAreaUnit) {
        this.deAreaUnit = deAreaUnit;
    }

    public Long getDeLocId() {
        return deLocId;
    }

    public void setDeLocId(Long deLocId) {
        this.deLocId = deLocId;
    }

    public BigDecimal getDeCapacity() {
        return this.deCapacity;
    }

    public void setDeCapacity(BigDecimal deCapacity) {
        this.deCapacity = deCapacity;
    }

    public Long getDeCapacityUnit() {
        return this.deCapacityUnit;
    }

    public void setDeCapacityUnit(Long deCapacityUnit) {
        this.deCapacityUnit = deCapacityUnit;
    }

    public Long getDeCategory() {
        return this.deCategory;
    }

    public void setDeCategory(Long deCategory) {
        this.deCategory = deCategory;
    }

    public String getDeGisId() {
        return this.deGisId;
    }

    public void setDeGisId(String deGisId) {
        this.deGisId = deGisId;
    }

    public Long getDeId() {
        return this.deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public String getDeName() {
        return this.deName;
    }

    public void setDeName(String deName) {
        this.deName = deName;
    }

    public String getDeNameReg() {
        return this.deNameReg;
    }

    public void setDeNameReg(String deNameReg) {
        this.deNameReg = deNameReg;
    }

    public String getHStatus() {
        return this.hStatus;
    }

    public void setHStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_DISPOSAL_MAST_HIST", "DE_ID_H" };
    }
}