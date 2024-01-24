/**
 * 
 */
package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.legal.domain.JudgementDetail;

/**
 * @author cherupelli.srikanth
 *
 */
public class AttendeeDetailDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5644816733003882042L;

    private Long attendeeId;

    private JudgementDetail tbCaseJudgeDetail;

    private String attendeeName;

    private String attendeeAddress;

    private Long createdBy;

    private Date createdDate;

    private Long orgId;

    private String lgIpMac;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMacUpd;

    public Long getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public JudgementDetail getTbCaseJudgeDetail() {
        return tbCaseJudgeDetail;
    }

    public void setTbCaseJudgeDetail(JudgementDetail tbCaseJudgeDetail) {
        this.tbCaseJudgeDetail = tbCaseJudgeDetail;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getAttendeeAddress() {
        return attendeeAddress;
    }

    public void setAttendeeAddress(String attendeeAddress) {
        this.attendeeAddress = attendeeAddress;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }
    
    
}
