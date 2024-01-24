package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_HOLIDAY_MAS")
public class HolidayMaster implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7214804459413309429L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HO_ID", precision = 12, scale = 0, nullable = false)
    private Long hoId;

    @Column(name = "HO_YEAR_START_DATE", nullable = false)
    private Date hoYearStartDate;

    @Column(name = "HO_YEAR_END_DATE", nullable = false)
    private Date hoYearEndDate;

    @Column(name = "HO_DATE", nullable = true)
    private Date hoDate;

    @Column(name = "HO_DESCRIPTION", length = 100, nullable = false)
    private String hoDescription;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "HO_ACTIVE", length = 1, nullable = true)
    private String hoActive;

    public Long getHoId() {
        return hoId;
    }

    public void setHoId(Long hoId) {
        this.hoId = hoId;
    }

    public Date getHoYearStartDate() {
        return hoYearStartDate;
    }

    public void setHoYearStartDate(Date hoYearStartDate) {
        this.hoYearStartDate = hoYearStartDate;
    }

    public Date getHoYearEndDate() {
        return hoYearEndDate;
    }

    public void setHoYearEndDate(Date hoYearEndDate) {
        this.hoYearEndDate = hoYearEndDate;
    }

    public Date getHoDate() {
        return hoDate;
    }

    public void setHoDate(Date hoDate) {
        this.hoDate = hoDate;
    }

    public String getHoDescription() {
        return hoDescription;
    }

    public void setHoDescription(String hoDescription) {
        this.hoDescription = hoDescription;
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

    public String getHoActive() {
        return hoActive;
    }

    public void setHoActive(String hoActive) {
        this.hoActive = hoActive;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_HOLIDAY_MAS", "HO_ID" };
    }

}
