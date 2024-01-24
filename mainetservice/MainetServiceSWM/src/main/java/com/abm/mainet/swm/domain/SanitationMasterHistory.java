package com.abm.mainet.swm.domain;

import java.io.Serializable;
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
 * The persistent class for the tb_sw_sanitation_mast_hist database table.
 * @author Lalit.Prusti
 *
 * Created Date : 17-May-2018
 */
@Entity
@Table(name = "TB_SW_SANITATION_MAST_HIST")
public class SanitationMasterHistory implements Serializable {

    private static final long serialVersionUID = 3504057850923685833L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SAN_ID_H")
    private Long sanIdH;

    @Column(name = "ASSET_ID")
    private Long assetId;

    @Column(name = "COD_WARD1")
    private Long codWard1;

    @Column(name = "COD_WARD2")
    private Long codWard2;

    @Column(name = "COD_WARD3")
    private Long codWard3;

    @Column(name = "COD_WARD4")
    private Long codWard4;

    @Column(name = "COD_WARD5")
    private Long codWard5;

    @Column(name = "H_STATUS")
    private String hStatus;

    private String lattiude;

    private String longitude;

    private Long orgid;

    @Column(name = "SAN_GISNO")
    private String sanGisno;

    @Column(name = "SAN_ID")
    private Long sanId;

    @Column(name = "LOC_ID")
    private Long sanLocId;

    @Column(name = "SAN_NAME")
    private String sanName;

    @Column(name = "SAN_SEAT_CNT")
    private String sanSeatCnt;

    @Column(name = "SAN_TYPE")
    private Long sanType;

    @Column(name = "SAN_ADDRESS")
    private String sanAddress;

    @Column(name = "SAN_ACTIVE", nullable = false, length = 1)
    private String sanActive;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public SanitationMasterHistory() {
    }

    public Long getSanIdH() {
        return this.sanIdH;
    }

    public void setSanIdH(Long sanIdH) {
        this.sanIdH = sanIdH;
    }

    public Long getAssetId() {
        return this.assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getCodWard1() {
        return this.codWard1;
    }

    public void setCodWard1(Long codWard1) {
        this.codWard1 = codWard1;
    }

    public Long getCodWard2() {
        return this.codWard2;
    }

    public void setCodWard2(Long codWard2) {
        this.codWard2 = codWard2;
    }

    public Long getCodWard3() {
        return this.codWard3;
    }

    public void setCodWard3(Long codWard3) {
        this.codWard3 = codWard3;
    }

    public Long getCodWard4() {
        return this.codWard4;
    }

    public void setCodWard4(Long codWard4) {
        this.codWard4 = codWard4;
    }

    public Long getCodWard5() {
        return this.codWard5;
    }

    public void setCodWard5(Long codWard5) {
        this.codWard5 = codWard5;
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

    public String getHStatus() {
        return this.hStatus;
    }

    public void setHStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getLattiude() {
        return this.lattiude;
    }

    public void setLattiude(String lattiude) {
        this.lattiude = lattiude;
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

    public String getLongitude() {
        return this.longitude;
    }

    public Long getSanLocId() {
        return sanLocId;
    }

    public void setSanLocId(Long sanLocId) {
        this.sanLocId = sanLocId;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getSanGisno() {
        return this.sanGisno;
    }

    public void setSanGisno(String sanGisno) {
        this.sanGisno = sanGisno;
    }

    public Long getSanId() {
        return this.sanId;
    }

    public void setSanId(Long sanId) {
        this.sanId = sanId;
    }

    public String getSanName() {
        return this.sanName;
    }

    public void setSanName(String sanName) {
        this.sanName = sanName;
    }

    public String getSanSeatCnt() {
        return this.sanSeatCnt;
    }

    public void setSanSeatCnt(String sanSeatCnt) {
        this.sanSeatCnt = sanSeatCnt;
    }

    public Long getSanType() {
        return this.sanType;
    }

    public void setSanType(Long sanType) {
        this.sanType = sanType;
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

    public String getSanAddress() {
        return sanAddress;
    }

    public void setSanAddress(String sanAddress) {
        this.sanAddress = sanAddress;
    }

    public String getSanActive() {
        return sanActive;
    }

    public void setSanActive(String sanActive) {
        this.sanActive = sanActive;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_SANITATION_MAST_HIST", "SAN_ID_H" };
    }
}