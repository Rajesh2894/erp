package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
public class WastageSegregationDetailsDTO implements Serializable {

    private static final long serialVersionUID = 7065368617925660234L;

    private Long grdId;

    private Long codWast1;

    private Long codWast2;

    private Long codWast3;

    private Long codWast4;

    private Long codWast5;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private BigDecimal tripVolume;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    private WastageSegregationDTO tbSwWasteseg;

    public WastageSegregationDetailsDTO() {
    }

    public Long getGrdId() {
        return this.grdId;
    }

    public void setGrdId(Long grdId) {
        this.grdId = grdId;
    }

    public Long getCodWast1() {
        return this.codWast1;
    }

    public void setCodWast1(Long codWast1) {
        this.codWast1 = codWast1;
    }

    public Long getCodWast2() {
        return this.codWast2;
    }

    public void setCodWast2(Long codWast2) {
        this.codWast2 = codWast2;
    }

    public Long getCodWast3() {
        return this.codWast3;
    }

    public void setCodWast3(Long codWast3) {
        this.codWast3 = codWast3;
    }

    public Long getCodWast4() {
        return this.codWast4;
    }

    public void setCodWast4(Long codWast4) {
        this.codWast4 = codWast4;
    }

    public Long getCodWast5() {
        return this.codWast5;
    }

    public void setCodWast5(Long codWast5) {
        this.codWast5 = codWast5;
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

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public BigDecimal getTripVolume() {
        return this.tripVolume;
    }

    public void setTripVolume(BigDecimal tripVolume) {
        this.tripVolume = tripVolume;
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

    public WastageSegregationDTO getTbSwWasteseg() {
        return this.tbSwWasteseg;
    }

    public void setTbSwWasteseg(WastageSegregationDTO tbSwWasteseg) {
        this.tbSwWasteseg = tbSwWasteseg;
    }

}