package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class MRFVehicleDetailDto implements Serializable {

    private static final long serialVersionUID = 7499875907673137491L;

    private Long mrfvId;

    private Long veVeType;

    private Long mrfvAvalCnt;

    private Long mrfvReqCnt;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    @JsonIgnore
    private MRFMasterDto mrfVEId;

    public Long getMrfvId() {
        return mrfvId;
    }

    public void setMrfvId(Long mrfvId) {
        this.mrfvId = mrfvId;
    }

    public Long getVeVeType() {
        return veVeType;
    }

    public void setVeVeType(Long veVeType) {
        this.veVeType = veVeType;
    }

    public Long getMrfvAvalCnt() {
        return mrfvAvalCnt;
    }

    public void setMrfvAvalCnt(Long mrfvAvalCnt) {
        this.mrfvAvalCnt = mrfvAvalCnt;
    }

    public Long getMrfvReqCnt() {
        return mrfvReqCnt;
    }

    public void setMrfvReqCnt(Long mrfvReqCnt) {
        this.mrfvReqCnt = mrfvReqCnt;
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

    public MRFMasterDto getMrfVEId() {
        return mrfVEId;
    }

    public void setMrfVEId(MRFMasterDto mrfVEId) {
        this.mrfVEId = mrfVEId;
    }

}
