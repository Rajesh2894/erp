package com.abm.mainet.vehiclemanagement.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_VM_VEHICLE_MAINTENANCE_HIST")
public class VehicleMaintenanceMastHistory {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VE_MEID_H", unique = true, nullable = false)
    private Long veMainIdHist;

    @Column(name = "H_STATUS")
    private String status;

    @Column(name = "VE_MEID", unique = true, nullable = false)
    private Long veMeId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VE_VETYPE", nullable = false)
    private Long veVetype;

    @Column(name = "VE_MAINDAY", nullable = false)
    private Long veMainday;

    @Column(name = "VE_MAINUNIT", nullable = false)
    private Long veMainUnit;

    @Column(name = "VE_DOWNTIME", nullable = false)
    private Long veDowntime;

    @Column(name = "VE_DOWNTIMEUNIT", nullable = false)
    private Long veDowntimeUnit;

    public Long getVeMainIdHist() {
        return veMainIdHist;
    }

    public void setVeMainIdHist(Long veMainIdHist) {
        this.veMainIdHist = veMainIdHist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVeMeId() {
        return veMeId;
    }

    public void setVeMeId(Long veMeId) {
        this.veMeId = veMeId;
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

    public Long getVeVetype() {
        return veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public Long getVeMainday() {
        return veMainday;
    }

    public void setVeMainday(Long veMainday) {
        this.veMainday = veMainday;
    }

    public Long getVeMainUnit() {
        return veMainUnit;
    }

    public void setVeMainUnit(Long veMainUnit) {
        this.veMainUnit = veMainUnit;
    }

    public Long getVeDowntime() {
        return veDowntime;
    }

    public void setVeDowntime(Long veDowntime) {
        this.veDowntime = veDowntime;
    }

    public Long getVeDowntimeUnit() {
        return veDowntimeUnit;
    }

    public void setVeDowntimeUnit(Long veDowntimeUnit) {
        this.veDowntimeUnit = veDowntimeUnit;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_VM_VEHICLE_MAINTENANCE_HIST", "VE_MEID_H" };
    }
}
