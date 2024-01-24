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
 * The persistent class for the tb_sw_wasteseg_det_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
@Entity
@Table(name = "TB_SW_WASTESEG_DET_HIST")
public class WastageSegregationDetailsHistory implements Serializable {

    private static final long serialVersionUID = 7229237749380946530L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "GRD_ID_H", unique = true, nullable = false)
    private Long grdIdH;

    @Column(name = "COD_WAST1")
    private Long codWast1;

    @Column(name = "COD_WAST2")
    private Long codWast2;

    @Column(name = "COD_WAST3")
    private Long codWast3;

    @Column(name = "COD_WAST4")
    private Long codWast4;

    @Column(name = "COD_WAST5")
    private Long codWast5;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "GR_ID")
    private Long grId;

    @Column(name = "GRD_ID")
    private Long grdId;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "TRIP_VOLUME")
    private BigDecimal tripVolume;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public WastageSegregationDetailsHistory() {
    }

    public Long getGrdIdH() {
        return this.grdIdH;
    }

    public void setGrdIdH(Long grdIdH) {
        this.grdIdH = grdIdH;
    }

    public Long getCodWast1() {
        return this.codWast1;
    }

    public void setCodWast1(Long codWast1) {
        this.codWast1 = codWast1;
    }

    public Long getCodWast2() {
        return this.codWast2;
    }

    public void setCodWast2(Long codWast2) {
        this.codWast2 = codWast2;
    }

    public Long getCodWast3() {
        return this.codWast3;
    }

    public void setCodWast3(Long codWast3) {
        this.codWast3 = codWast3;
    }

    public Long getCodWast4() {
        return this.codWast4;
    }

    public void setCodWast4(Long codWast4) {
        this.codWast4 = codWast4;
    }

    public Long getCodWast5() {
        return this.codWast5;
    }

    public void setCodWast5(Long codWast5) {
        this.codWast5 = codWast5;
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

    public Long getGrId() {
        return this.grId;
    }

    public void setGrId(Long grId) {
        this.grId = grId;
    }

    public Long getGrdId() {
        return this.grdId;
    }

    public void setGrdId(Long grdId) {
        this.grdId = grdId;
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

    public BigDecimal getTripVolume() {
        return this.tripVolume;
    }

    public void setTripVolume(BigDecimal tripVolume) {
        this.tripVolume = tripVolume;
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

        return new String[] { "SWM", "TB_SW_WASTESEG_DET_HIST", "GRD_ID_H" };
    }
}