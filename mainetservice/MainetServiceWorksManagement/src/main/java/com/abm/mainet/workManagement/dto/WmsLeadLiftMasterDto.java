package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WmsLeadLiftMasterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal leLiFrom;

    private Long sorTypeId;

    private BigDecimal leLiTo;

    private Long leLiUnit;

    private String sorType;

    private BigDecimal leLiRate;

    private String leLiFlag;

    private String sorName;

    private Long orgId;

    private Long sorId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Character leLiActive;

    private Long leLiId;

    private Date sorFromDate;

    private Date sorToDate;

    private String unitName;

    private String leLiSlabFlg;

    private String isDeleted;

    private String errMessage;

    private List<WmsLeadLiftMasterDto> wmsLeadLiftMasterDto;

    public Date getSorFromDate() {
        return sorFromDate;
    }

    public void setSorFromDate(Date sorFromDate) {
        this.sorFromDate = sorFromDate;
    }

    public Date getSorToDate() {
        return sorToDate;
    }

    public void setSorToDate(Date sorToDate) {
        this.sorToDate = sorToDate;
    }

    public List<WmsLeadLiftMasterDto> getWmsLeadLiftMasterDto() {
        return wmsLeadLiftMasterDto;
    }

    public void setWmsLeadLiftMasterDto(List<WmsLeadLiftMasterDto> wmsLeadLiftMasterDto) {
        this.wmsLeadLiftMasterDto = wmsLeadLiftMasterDto;
    }

    public Character getLeLiActive() {
        return leLiActive;
    }

    public void setLeLiActive(Character leLiActive) {
        this.leLiActive = leLiActive;
    }

    public Long getLeLiId() {
        return leLiId;
    }

    public void setLeLiId(Long leLiId) {
        this.leLiId = leLiId;
    }

    public String getSorType() {
        return sorType;
    }

    public void setSorType(String sorType) {
        this.sorType = sorType;
    }

    public String getSorName() {
        return sorName;
    }

    public void setSorName(String sorName) {
        this.sorName = sorName;
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

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getLeLiSlabFlg() {
        return leLiSlabFlg;
    }

    public void setLeLiSlabFlg(String leLiSlabFlg) {
        this.leLiSlabFlg = leLiSlabFlg;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public Long getSorTypeId() {
        return sorTypeId;
    }

    public void setSorTypeId(Long sorTypeId) {
        this.sorTypeId = sorTypeId;
    }

}
