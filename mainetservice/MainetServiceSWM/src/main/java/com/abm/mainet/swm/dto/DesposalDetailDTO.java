package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.Date;

public class DesposalDetailDTO implements Serializable {

    private static final long serialVersionUID = -373091021864366413L;

    private Long dedId;

    private Long createdBy;

    private Date createdDate;

    private Long deWestType;

    private String deWestTypeDesc;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private DisposalMasterDTO tbSwDesposalMast;

    private String deActive;

    public DesposalDetailDTO() {
    }

    public Long getDedId() {
        return this.dedId;
    }

    public void setDedId(Long dedId) {
        this.dedId = dedId;
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

    public Long getDeWestType() {
        return this.deWestType;
    }

    public void setDeWestType(Long deWestType) {
        this.deWestType = deWestType;
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

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public DisposalMasterDTO getTbSwDesposalMast() {
        return this.tbSwDesposalMast;
    }

    public void setTbSwDesposalMast(DisposalMasterDTO tbSwDesposalMast) {
        this.tbSwDesposalMast = tbSwDesposalMast;
    }

    /**
     * @return the deWestTypeDesc
     */
    public String getDeWestTypeDesc() {
        return deWestTypeDesc;
    }

    /**
     * @param deWestTypeDesc the deWestTypeDesc to set
     */
    public void setDeWestTypeDesc(String deWestTypeDesc) {
        this.deWestTypeDesc = deWestTypeDesc;
    }

    public String getDeActive() {
        return deActive;
    }

    public void setDeActive(String deActive) {
        this.deActive = deActive;
    }

}