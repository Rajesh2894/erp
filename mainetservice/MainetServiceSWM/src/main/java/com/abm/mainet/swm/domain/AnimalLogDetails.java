package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_sw_animallog")
public class AnimalLogDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ANI_ID")
    private Long aniId;

    @Temporal(TemporalType.DATE)
    @Column(name = "ANI_DATE")
    private Date aniDate;

    @Column(name = "ANI_QUANTITY")
    private Long aniQuantity;

    @Column(name = "ANI_TYPE")
    private String aniType;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VE_NO")
    private String veNo;

    public AnimalLogDetails() {
    }

    public Long getAniId() {
        return aniId;
    }

    public void setAniId(Long aniId) {
        this.aniId = aniId;
    }

    public Date getAniDate() {
        return aniDate;
    }

    public void setAniDate(Date aniDate) {
        this.aniDate = aniDate;
    }

    public Long getAniQuantity() {
        return aniQuantity;
    }

    public void setAniQuantity(Long aniQuantity) {
        this.aniQuantity = aniQuantity;
    }

    public String getAniType() {
        return aniType;
    }

    public void setAniType(String aniType) {
        this.aniType = aniType;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public String getVeNo() {
        return veNo;
    }

    public void setVeNo(String veNo) {
        this.veNo = veNo;
    }

    public String[] getPkValues() {
        return new String[] { "SWM", "tb_sw_animallog", "ANI_ID" };
    }
}