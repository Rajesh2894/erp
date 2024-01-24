package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 * @since 05 June 2019
 */
public class EstatePropertyShiftDTO implements Serializable {

    private static final long serialVersionUID = 5209809837041600792L;

    private Long propShifId;
    private Long propId;
    private Long propShift;
    private String startTime;
    private String endTime;
    private Date propFromTime;
    private Date propToTime;
    private String shiftStatus;
    private Long orgId;
    private long langId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private String propShiftDesc;

    public Long getPropShifId() {
        return propShifId;
    }

    public void setPropShifId(Long propShifId) {
        this.propShifId = propShifId;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Long getPropShift() {
        return propShift;
    }

    public void setPropShift(Long propShift) {
        this.propShift = propShift;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getPropFromTime() {
        return propFromTime;
    }

    public Date getPropToTime() {
        return propToTime;
    }

    public void setPropFromTime(Date propFromTime) {
        this.propFromTime = propFromTime;
    }

    public void setPropToTime(Date propToTime) {
        this.propToTime = propToTime;
    }

    public String getShiftStatus() {
        return shiftStatus;
    }

    public void setShiftStatus(String shiftStatus) {
        this.shiftStatus = shiftStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(long langId) {
        this.langId = langId;
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

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public String getPropShiftDesc() {
        return propShiftDesc;
    }

    public void setPropShiftDesc(String propShiftDesc) {
        this.propShiftDesc = propShiftDesc;
    }

}
