package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */

@Entity
@Table(name = "TB_WMS_SOR_MAST")
public class ScheduleOfRateMstEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SOR_ID", nullable = false)
    private Long sorId;

    @Column(name = "SOR_CPD_ID")
    private Long sorCpdId;

    @Column(name = "SOR_NAME", nullable = false, length = 250)
    private String sorName;

    @Temporal(TemporalType.DATE)
    @Column(name = "SOR_FROMDATE", nullable = false)
    private Date sorFromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "SOR_TODATE", nullable = false)
    private Date sorToDate;

    @Column(name = "SOR_ACTIVE")
    private String sorActive;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String igIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String igIpMacUpd;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scheduleOfRateMst", cascade = CascadeType.ALL)
    @Where(clause = "SORD_ACTIVE='Y'")
    private List<ScheduleOfRateDetEntity> scheduleDetailsList = new ArrayList<>();

    public Long getSorId() {
        return sorId;
    }

    public void setSorId(Long sorId) {
        this.sorId = sorId;
    }

    public String getSorName() {
        return sorName;
    }

    public void setSorName(String sorName) {
        this.sorName = sorName;
    }

    public Date getSorFromDate() {
        return sorFromDate;
    }

    public void setSorFromDate(Date sorFromDate) {
        this.sorFromDate = sorFromDate;
    }

    public Date getSorToDate() {
        return sorToDate;
    }

    public void setSorToDate(Date sorToDate) {
        this.sorToDate = sorToDate;
    }

    public String getSorActive() {
        return sorActive;
    }

    public void setSorActive(String sorActive) {
        this.sorActive = sorActive;
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

    public String getIgIpMac() {
        return igIpMac;
    }

    public void setIgIpMac(String igIpMac) {
        this.igIpMac = igIpMac;
    }

    public String getIgIpMacUpd() {
        return igIpMacUpd;
    }

    public void setIgIpMacUpd(String igIpMacUpd) {
        this.igIpMacUpd = igIpMacUpd;
    }

    public List<ScheduleOfRateDetEntity> getScheduleDetailsList() {
        return scheduleDetailsList;
    }

    public void setScheduleDetailsList(List<ScheduleOfRateDetEntity> scheduleDetailsList) {
        this.scheduleDetailsList = scheduleDetailsList;
    }

    public Long getSorCpdId() {
        return sorCpdId;
    }

    public void setSorCpdId(Long sorCpdId) {
        this.sorCpdId = sorCpdId;
    }

    public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_SOR_MAST", "SOR_ID" };
    }

}
