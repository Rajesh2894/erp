package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the tb_sw_sanistaff_tg database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 15-Jun-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLE_TG")
public class SanitationStaffTarget implements Serializable {

    private static final long serialVersionUID = 4939154281358399814L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SAN_ID", unique = true, nullable = false)
    private Long sanId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Temporal(TemporalType.DATE)
    @Column(name = "SAN_TGFROMDT", nullable = false)
    private Date sanTgfromdt;

    @Temporal(TemporalType.DATE)
    @Column(name = "SAN_TGTODT", nullable = false)
    private Date sanTgtodt;

    @Column(name = "SAN_TYPE")
    private Long sanType;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @OneToMany(mappedBy = "sanitationStaffTarget", cascade = CascadeType.ALL)
    private List<SanitationStaffTargetDet> sanitationStaffTargetDet;

    public SanitationStaffTarget() {
    }

    public Long getSanId() {
        return this.sanId;
    }

    public void setSanId(Long sanId) {
        this.sanId = sanId;
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
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public List<SanitationStaffTargetDet> getSanitationStaffTargetDet() {
        return sanitationStaffTargetDet;
    }

    public void setSanitationStaffTargetDet(List<SanitationStaffTargetDet> sanitationStaffTargetDet) {
        this.sanitationStaffTargetDet = sanitationStaffTargetDet;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_VEHICLE_TG", "SAN_ID" };
    }
}