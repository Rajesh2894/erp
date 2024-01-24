package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */

public class ScheduleOfRateMstDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long sorId;

    private Long sorCpdId;

    private String sorName;

    private Date sorFromDate;

    private Date sorToDate;

    private String sorActive;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String igIpMac;

    private String igIpMacUpd;

    private String sorTypeDesc;

    private String fromDate;

    private String toDate;

    private List<ScheduleOfRateDetDto> detDto = new ArrayList<>();

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public Long getSorCpdId() {
        return sorCpdId;
    }

    public void setSorCpdId(Long sorCpdId) {
        this.sorCpdId = sorCpdId;
    }

    public String getSorName() {
        return sorName;
    }

    public void setSorName(String sorName) {
        this.sorName = sorName;
    }

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

    public String getSorActive() {
        return sorActive;
    }

    public void setSorActive(String sorActive) {
        this.sorActive = sorActive;
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

    public String getIgIpMac() {
        return igIpMac;
    }

    public void setIgIpMac(String igIpMac) {
        this.igIpMac = igIpMac;
    }

    public String getIgIpMacUpd() {
        return igIpMacUpd;
    }

    public void setIgIpMacUpd(String igIpMacUpd) {
        this.igIpMacUpd = igIpMacUpd;
    }

    public String getSorTypeDesc() {
        return sorTypeDesc;
    }

    public void setSorTypeDesc(String sorTypeDesc) {
        this.sorTypeDesc = sorTypeDesc;
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

    public List<ScheduleOfRateDetDto> getDetDto() {
        return detDto;
    }

    public void setDetDto(List<ScheduleOfRateDetDto> detDto) {
        this.detDto = detDto;
    }

}
