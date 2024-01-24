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

/**
 * The persistent class for the tb_sw_sanistaff_tg_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 15-Jun-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLE_TG_HIST")
public class SanitationStaffTargetHistory implements Serializable {

    private static final long serialVersionUID = -3143903741274451207L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SAN_ID_H", unique = true, nullable = false)
    private Long sanIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "SAN_ID")
    private Long sanId;

    @Temporal(TemporalType.DATE)
    @Column(name = "SAN_TGFROMDT")
    private Date sanTgfromdt;

    @Temporal(TemporalType.DATE)
    @Column(name = "SAN_TGTODT")
    private Date sanTgtodt;

    @Column(name = "SAN_TYPE")
    private Long sanType;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public SanitationStaffTargetHistory() {
    }

    public Long getSanIdH() {
        return this.sanIdH;
    }

    public void setSanIdH(Long sanIdH) {
        this.sanIdH = sanIdH;
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

    public String getHStatus() {
        return this.hStatus;
    }

    public void setHStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public Long getSanId() {
        return this.sanId;
    }

    public void setSanId(Long sanId) {
        this.sanId = sanId;
    }

    public Date getSanTgfromdt() {
        return this.sanTgfromdt;
    }

    public void setSanTgfromdt(Date sanTgfromdt) {
        this.sanTgfromdt = sanTgfromdt;
    }

    public Date getSanTgtodt() {
        return this.sanTgtodt;
    }

    public void setSanTgtodt(Date sanTgtodt) {
        this.sanTgtodt = sanTgtodt;
    }

    public Long getSanType() {
        return this.sanType;
    }

    public void setSanType(Long sanType) {
        this.sanType = sanType;
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

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_VEHICLE_TG_HIST", "SAN_ID_H" };
    }
}