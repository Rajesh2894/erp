package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class MRFEmployeeDetailDto implements Serializable {
    private static final long serialVersionUID = -5831114527071322409L;

    private Long mrfEId;

    private Long dsgId;

    private Long mrfeAvalCnt;

    private Long mrfeReqCnt;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    @JsonIgnore
    private MRFMasterDto mrfEMPId;

    public Long getMrfEId() {
        return mrfEId;
    }

    public void setMrfEId(Long mrfEId) {
        this.mrfEId = mrfEId;
    }

    public Long getDsgId() {
        return dsgId;
    }

    public void setDsgId(Long dsgId) {
        this.dsgId = dsgId;
    }

    public Long getMrfeAvalCnt() {
        return mrfeAvalCnt;
    }

    public void setMrfeAvalCnt(Long mrfeAvalCnt) {
        this.mrfeAvalCnt = mrfeAvalCnt;
    }

    public Long getMrfeReqCnt() {
        return mrfeReqCnt;
    }

    public void setMrfeReqCnt(Long mrfeReqCnt) {
        this.mrfeReqCnt = mrfeReqCnt;
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

    public MRFMasterDto getMrfEMPId() {
        return mrfEMPId;
    }

    public void setMrfEMPId(MRFMasterDto mrfEMPId) {
        this.mrfEMPId = mrfEMPId;
    }

}
