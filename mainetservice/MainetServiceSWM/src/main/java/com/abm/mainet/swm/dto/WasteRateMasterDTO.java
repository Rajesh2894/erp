package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WasteRateMasterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long wRateId;
    private Long codWast1;
    private Long codWast;
    private Long wasteRate;
    private Long orgid;
    private Date wFromDate;
    private Date wToDate;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String SrNo;

    private List<WasteRateMasterDTO> wasteRateList = new ArrayList<>();

    public Long getwRateId() {
        return wRateId;
    }

    public void setwRateId(Long wRateId) {
        this.wRateId = wRateId;
    }

    public Long getCodWast1() {
        return codWast1;
    }

    public void setCodWast1(Long codWast1) {
        this.codWast1 = codWast1;
    }

    public Long getCodWast() {
        return codWast;
    }

    public void setCodWast(Long codWast) {
        this.codWast = codWast;
    }

    public Long getWasteRate() {
        return wasteRate;
    }

    public void setWasteRate(Long wasteRate) {
        this.wasteRate = wasteRate;
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

    public List<WasteRateMasterDTO> getWasteRateList() {
        return wasteRateList;
    }

    public void setWasteRateList(List<WasteRateMasterDTO> wasteRateList) {
        this.wasteRateList = wasteRateList;
    }

    public Date getwFromDate() {
        return wFromDate;
    }

    public void setwFromDate(Date wFromDate) {
        this.wFromDate = wFromDate;
    }

    public Date getwToDate() {
        return wToDate;
    }

    public void setwToDate(Date wToDate) {
        this.wToDate = wToDate;
    }

    public String getSrNo() {
        return SrNo;
    }

    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

}
