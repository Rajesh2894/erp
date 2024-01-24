package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Anwarul.Hassan
 * @since 23-Oct-2019
 */
public class InspectionEntryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long inesId;

    private Long adhId;

    private Date inesDate;

    private Long inesEmpId;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private List<InspectionEntryDetDto> inspectionEntryDetDto = new ArrayList<InspectionEntryDetDto>();

    private Long noOfDays;

    private String noticeGenFlag;

    public Long getInesId() {
        return inesId;
    }

    public void setInesId(Long inesId) {
        this.inesId = inesId;
    }

    public Long getAdhId() {
        return adhId;
    }

    public void setAdhId(Long adhId) {
        this.adhId = adhId;
    }

    public Date getInesDate() {
        return inesDate;
    }

    public void setInesDate(Date inesDate) {
        this.inesDate = inesDate;
    }

    public Long getInesEmpId() {
        return inesEmpId;
    }

    public void setInesEmpId(Long inesEmpId) {
        this.inesEmpId = inesEmpId;
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

    public List<InspectionEntryDetDto> getInspectionEntryDetDto() {
        return inspectionEntryDetDto;
    }

    public void setInspectionEntryDetDto(List<InspectionEntryDetDto> inspectionEntryDetDto) {
        this.inspectionEntryDetDto = inspectionEntryDetDto;
    }

    public Long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getNoticeGenFlag() {
        return noticeGenFlag;
    }

    public void setNoticeGenFlag(String noticeGenFlag) {
        this.noticeGenFlag = noticeGenFlag;
    }

}
