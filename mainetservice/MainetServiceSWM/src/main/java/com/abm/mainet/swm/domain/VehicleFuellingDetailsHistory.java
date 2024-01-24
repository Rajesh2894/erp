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
 * The persistent class for the tb_sw_vehiclefuel_det_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLEFUEL_DET_HIST")
public class VehicleFuellingDetailsHistory implements Serializable {

    private static final long serialVersionUID = -8821399580084271427L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VEFD_ID_H", unique = true, nullable = false)
    private Long vefdIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "PFU_ID")
    private Long pfuId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VEF_ID")
    private Long vefId;

    @Column(name = "VEFD_ID")
    private Long vefdId;

    @Column(name = "VEFD_QUANTITY")
    private Long vefdQuantity;

    @Column(name = "VEFD_UNIT")
    private Long vefdUnit;

    @Column(name = "VEFD_COST")
    private BigDecimal vefdCost;

    public Long getVefdIdH() {
        return vefdIdH;
    }

    public void setVefdIdH(Long vefdIdH) {
        this.vefdIdH = vefdIdH;
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

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getPfuId() {
        return pfuId;
    }

    public void setPfuId(Long pfuId) {
        this.pfuId = pfuId;
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

    public Long getVefId() {
        return vefId;
    }

    public void setVefId(Long vefId) {
        this.vefId = vefId;
    }

    public Long getVefdId() {
        return vefdId;
    }

    public void setVefdId(Long vefdId) {
        this.vefdId = vefdId;
    }

    public Long getVefdQuantity() {
        return vefdQuantity;
    }

    public void setVefdQuantity(Long vefdQuantity) {
        this.vefdQuantity = vefdQuantity;
    }

    public Long getVefdUnit() {
        return vefdUnit;
    }

    public void setVefdUnit(Long vefdUnit) {
        this.vefdUnit = vefdUnit;
    }

    public BigDecimal getVefdCost() {
        return vefdCost;
    }

    public void setVefdCost(BigDecimal vefdCost) {
        this.vefdCost = vefdCost;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_VEHICLEFUEL_DET_HIST", "VEFD_ID_H" };
    }

}