/**
 *
 */
package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 14-May-2018
 */
public class SanitationMasterDTO implements Serializable {

    private static final long serialVersionUID = -4468401531071193017L;

    private Long sanId;

    private Long assetId;

    private Long codWard1;

    private Long codWard2;

    private Long codWard3;

    private Long codWard4;

    private Long codWard5;

    private Long createdBy;

    private Date createdDate;

    private String lattiude;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String longitude;

    private Long orgid;

    private String sanGisno;

    private String sanName;

    private Long sanLocId;

    private String sanSeatCnt;

    private Long sanType;

    private String sanAddress;

    private Long updatedBy;

    private Date updatedDate;

    private String sanActive;

    public Long getSanId() {
        return sanId;
    }

    public void setSanId(Long sanId) {
        this.sanId = sanId;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getCodWard1() {
        return codWard1;
    }

    public void setCodWard1(Long codWard1) {
        this.codWard1 = codWard1;
    }

    public Long getCodWard2() {
        return codWard2;
    }

    public void setCodWard2(Long codWard2) {
        this.codWard2 = codWard2;
    }

    public Long getCodWard3() {
        return codWard3;
    }

    public String getSanActive() {
        return sanActive;
    }

    public void setSanActive(String sanActive) {
        this.sanActive = sanActive;
    }

    public void setCodWard3(Long codWard3) {
        this.codWard3 = codWard3;
    }

    public Long getCodWard4() {
        return codWard4;
    }

    public Long getSanLocId() {
        return sanLocId;
    }

    public void setSanLocId(Long sanLocId) {
        this.sanLocId = sanLocId;
    }

    public void setCodWard4(Long codWard4) {
        this.codWard4 = codWard4;
    }

    public Long getCodWard5() {
        return codWard5;
    }

    public void setCodWard5(Long codWard5) {
        this.codWard5 = codWard5;
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

    public String getLattiude() {
        return lattiude;
    }

    public void setLattiude(String lattiude) {
        this.lattiude = lattiude;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getSanGisno() {
        return sanGisno;
    }

    public void setSanGisno(String sanGisno) {
        this.sanGisno = sanGisno;
    }

    public String getSanName() {
        return sanName;
    }

    public void setSanName(String sanName) {
        this.sanName = sanName;
    }

    public String getSanSeatCnt() {
        return sanSeatCnt;
    }

    public void setSanSeatCnt(String sanSeatCnt) {
        this.sanSeatCnt = sanSeatCnt;
    }

    public Long getSanType() {
        return sanType;
    }

    public void setSanType(Long sanType) {
        this.sanType = sanType;
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

    public String getSanAddress() {
        return sanAddress;
    }

    public void setSanAddress(String sanAddress) {
        this.sanAddress = sanAddress;
    }

}
