package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the tb_sw_desposal_mast database table.
 *
 */
@Entity
@Table(name = "TB_SW_DISPOSAL_MAST")
public class DisposalMaster implements Serializable {

    private static final long serialVersionUID = -868735395257074945L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DE_ID", unique = true, nullable = false)
    private Long deId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "DE_ACTIVE", nullable = false, length = 1)
    private String deActive;

    @Column(name = "DE_ADDRESS", nullable = false, length = 200)
    private String deAddress;

    @Column(name = "DE_AREA", nullable = false, precision = 10, scale = 2)
    private BigDecimal deArea;

    @Column(name = "DE_AREA_UNIT")
    private Long deAreaUnit;

    @Column(name = "DE_CAPACITY", nullable = false, precision = 10, scale = 2)
    private BigDecimal deCapacity;

    @Column(name = "DE_CAPACITY_UNIT", nullable = false)
    private Long deCapacityUnit;

    @Column(name = "DE_CATEGORY", nullable = false)
    private Long deCategory;

    @Column(name = "DE_GIS_ID", length = 15)
    private String deGisId;

    @Column(name = "DE_NAME", nullable = false, length = 100)
    private String deName;

    @Column(name = "DE_NAME_REG", nullable = false, length = 100)
    private String deNameReg;

    @Column(name = "LOC_ID")
    private Long deLocId;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LATTIUDE")
    private String addLatitude;

    @Column(name = "LONGITUDE")
    private String addLongitude;

    // bi-directional many-to-one association to TbSwDesposalDet
    @JsonIgnore
    @OneToMany(mappedBy = "tbSwDesposalMast", cascade = CascadeType.ALL)
    private List<DisposalDetail> tbSwDesposalDets;

    public DisposalMaster() {
    }

    public Long getDeId() {
        return this.deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
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

    public Long getDeLocId() {
        return deLocId;
    }

    public void setDeLocId(Long deLocId) {
        this.deLocId = deLocId;
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

    public List<DisposalDetail> getTbSwDesposalDets() {
        return this.tbSwDesposalDets;
    }

    public void setTbSwDesposalDets(List<DisposalDetail> tbSwDesposalDets) {
        this.tbSwDesposalDets = tbSwDesposalDets;
    }

    public DisposalDetail addTbSwDesposalDet(DisposalDetail tbSwDesposalDet) {
        getTbSwDesposalDets().add(tbSwDesposalDet);
        tbSwDesposalDet.setTbSwDesposalMast(this);

        return tbSwDesposalDet;
    }

    public DisposalDetail removeTbSwDesposalDet(
            DisposalDetail tbSwDesposalDet) {
        getTbSwDesposalDets().remove(tbSwDesposalDet);
        tbSwDesposalDet.setTbSwDesposalMast(null);

        return tbSwDesposalDet;
    }

    public String getAddLatitude() {
        return addLatitude;
    }

    public void setAddLatitude(String addLatitude) {
        this.addLatitude = addLatitude;
    }

    public String getAddLongitude() {
        return addLongitude;
    }

    public void setAddLongitude(String addLongitude) {
        this.addLongitude = addLongitude;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_DISPOSAL_MAST", "DE_ID" };
    }

}