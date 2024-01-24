package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 15-Jun-2018
 */
public class SanitationStaffTargetDTO implements Serializable {

    private static final long serialVersionUID = 8641992588759360212L;

    private Long sanId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Date sanTgfromdt;

    private Date sanTgtodt;

    private Long sanType;

    private Long updatedBy;

    private Date updatedDate;

    private String fromDate;

    private String toDate;

    private String vehicleRegNo;

    private BigDecimal collectionTarget;

    private BigDecimal totalTarget;

    private BigDecimal totalCollection;

    private BigDecimal actualCollection;

    private String flagMsg;

    private List<SanitationStaffTargetDetDTO> sanitationStaffTargetDet;

    private List<SanitationStaffTargetDTO> targetDto;

    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public SanitationStaffTargetDTO() {
    }

    public Long getSanId() {
        return this.sanId;
    }

    public void setSanId(Long sanId) {
        this.sanId = sanId;
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

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public List<SanitationStaffTargetDTO> getTargetDto() {
        return targetDto;
    }

    public void setTargetDto(List<SanitationStaffTargetDTO> targetDto) {
        this.targetDto = targetDto;
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

    public Date getSanTgfromdt() {
        return this.sanTgfromdt;
    }

    public void setSanTgfromdt(Date sanTgfromdt) {
        this.sanTgfromdt = sanTgfromdt;
    }

    public Date getSanTgtodt() {
        return this.sanTgtodt;
    }

    public void setSanTgtodt(Date sanTgtodt) {
        this.sanTgtodt = sanTgtodt;
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

    public List<SanitationStaffTargetDetDTO> getSanitationStaffTargetDet() {
        return sanitationStaffTargetDet;
    }

    public void setSanitationStaffTargetDet(List<SanitationStaffTargetDetDTO> sanitationStaffTargetDet) {
        this.sanitationStaffTargetDet = sanitationStaffTargetDet;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public BigDecimal getCollectionTarget() {
        return collectionTarget;
    }

    public void setCollectionTarget(BigDecimal collectionTarget) {
        this.collectionTarget = collectionTarget;
    }

    public BigDecimal getActualCollection() {
        return actualCollection;
    }

    public void setActualCollection(BigDecimal actualCollection) {
        this.actualCollection = actualCollection;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getTotalTarget() {
        return totalTarget;
    }

    public void setTotalTarget(BigDecimal totalTarget) {
        this.totalTarget = totalTarget;
    }

    public BigDecimal getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(BigDecimal totalCollection) {
        this.totalCollection = totalCollection;
    }

    public String getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(String flagMsg) {
        this.flagMsg = flagMsg;
    }

}