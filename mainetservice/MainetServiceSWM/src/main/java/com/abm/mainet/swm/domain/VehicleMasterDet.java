package com.abm.mainet.swm.domain;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_SW_VEHICLE_DET")
public class VehicleMasterDet implements Serializable {

    private static final long serialVersionUID = 4558112046285914936L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VED_ID ", unique = true, nullable = false)
    private Long vedId;

    @Column(name = "COD_WAST1 ", nullable = false)
    private Long wasteType;

    @Column(name = "VE_CAPACITY", nullable = false, precision = 10, scale = 2)
    private BigDecimal veCapacity;

    @Column(name = "VE_ACTIVE")
    private String veActive;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @ManyToOne
    @JoinColumn(name = "VE_ID", nullable = false)
    private VehicleMaster tbSwVehicleMaster;

    public VehicleMasterDet() {
    }

    public Long getVedId() {
        return vedId;
    }

    public void setVedId(Long vedId) {
        this.vedId = vedId;
    }

    public Long getWasteType() {
        return wasteType;
    }

    public void setWasteType(Long wasteType) {
        this.wasteType = wasteType;
    }

    public BigDecimal getVeCapacity() {
        return veCapacity;
    }

    public void setVeCapacity(BigDecimal veCapacity) {
        this.veCapacity = veCapacity;
    }

    public String getVeActive() {
        return veActive;
    }

    public void setVeActive(String veActive) {
        this.veActive = veActive;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public VehicleMaster getTbSwVehicleMaster() {
        return tbSwVehicleMaster;
    }

    public void setTbSwVehicleMaster(VehicleMaster tbSwVehicleMaster) {
        this.tbSwVehicleMaster = tbSwVehicleMaster;
    }

    public String[] getPkValues() {
        return new String[] { "SWM", "TB_SW_VEHICLE_DET", "VED_ID" };
    }
}
