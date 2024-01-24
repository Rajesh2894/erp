package com.abm.mainet.council.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_CMT_SUMOTO_RESOLUTION")
public class MOMSumotoResolution implements Serializable {

    private static final long serialVersionUID = -7019560880284658076L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SUMOTO_RESO_ID")
    private Long sumotoResoId;

    @Column(name = "MEETING_MOM_ID")
    private Long meetingMomId;

    @Column(name = "SUMOTO_DEP_ID", nullable = false)
    private Long sumotoDepId;

    @Column(name = "DETAILS_OF_RESO", nullable = false)
    private String detailsOfReso;

    @Column(name = "RESOLUTION_NO", nullable = false)
    private String resolutionNo;

    @Column(name = "RESOLUTION_COMMENT", nullable = false)
    private String resolutionComment;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "STATUS", length = 100, nullable = false)
    private String Status;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true, updatable = true)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getSumotoResoId() {
        return sumotoResoId;
    }

    public void setSumotoResoId(Long sumotoResoId) {
        this.sumotoResoId = sumotoResoId;
    }

    public Long getMeetingMomId() {
        return meetingMomId;
    }

    public void setMeetingMomId(Long meetingMomId) {
        this.meetingMomId = meetingMomId;
    }

    public Long getSumotoDepId() {
        return sumotoDepId;
    }

    public void setSumotoDepId(Long sumotoDepId) {
        this.sumotoDepId = sumotoDepId;
    }

    public String getDetailsOfReso() {
        return detailsOfReso;
    }

    public void setDetailsOfReso(String detailsOfReso) {
        this.detailsOfReso = detailsOfReso;
    }

    public String getResolutionNo() {
        return resolutionNo;
    }

    public void setResolutionNo(String resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public String getResolutionComment() {
        return resolutionComment;
    }

    public void setResolutionComment(String resolutionComment) {
        this.resolutionComment = resolutionComment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    public static String[] getPkValues() {
        return new String[] { "CMT", "TB_CMT_SUMOTO_RESOLUTION", "SUMOTO_RESO_ID" };
    }
}
