package com.abm.mainet.common.domain;

// Generated Oct 29, 2012 2:31:43 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * DeptOrgMap generated by hbm2java
 */
@Entity
@Table(name = "TB_DEPORG_MAP")
public class DeptOrgMap implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private long mapId;
    private Department department;
    private String mapStatus;
    private Long orgid;
    private int userId;
    private int langId;
    private Date lmoddate;
    private Integer updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;

    public DeptOrgMap() {
    }

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MAP_ID", nullable = false, precision = 12, scale = 0)
    public long getMapId() {
        return mapId;
    }

    public void setMapId(final long mapId) {
        this.mapId = mapId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DP_DEPTID", nullable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(final Department department) {
        this.department = department;
    }

    @Column(name = "MAP_STATUS", length = 1)
    public String getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(final String mapStatus) {
        this.mapStatus = mapStatus;
    }

    @Column(name = "ORGID", nullable = false, precision = 4, scale = 0)
    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    @Column(name = "USER_ID", nullable = false, precision = 7, scale = 0)
    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    @Column(name = "LANG_ID", nullable = false, precision = 7, scale = 0)
    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = false, length = 7)
    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    @Column(name = "UPDATED_BY", precision = 7, scale = 0)
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE", length = 7)
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Column(name = "LG_IP_MAC", length = 100)
    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Transient
    public String[] getPkValues() {
        return new String[] { "AUT", "TB_DEPORG_MAP", "MAP_ID" };
    }
}
