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
 * The persistent class for the tb_sw_employee_scheduling_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Entity
@Table(name = "TB_SW_EMPLOYEE_SCHEDULING_HIST")
public class EmployeeScheduleHistory implements Serializable {

    private static final long serialVersionUID = 5767019996594235968L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "EMS_ID_H", unique = true, nullable = false)
    private Long emsIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMS_FROMDATE")
    private Date emsFromdate;

    @Column(name = "EMS_ID")
    private Long emsId;

    @Column(name = "EMS_REOCC", length = 1)
    private String emsReocc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMS_TODATE")
    private Date emsTodate;

    @Column(name = "EMS_TYPE")
    private String emsType;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public EmployeeScheduleHistory() {
    }

    public Long getEmsIdH() {
        return this.emsIdH;
    }

    public void setEmsIdH(Long emsIdH) {
        this.emsIdH = emsIdH;
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

    public Date getEmsFromdate() {
        return this.emsFromdate;
    }

    public void setEmsFromdate(Date emsFromdate) {
        this.emsFromdate = emsFromdate;
    }

    public Long getEmsId() {
        return this.emsId;
    }

    public void setEmsId(Long emsId) {
        this.emsId = emsId;
    }

    public String getEmsReocc() {
        return this.emsReocc;
    }

    public void setEmsReocc(String emsReocc) {
        this.emsReocc = emsReocc;
    }

    public Date getEmsTodate() {
        return this.emsTodate;
    }

    public void setEmsTodate(Date emsTodate) {
        this.emsTodate = emsTodate;
    }

    public String getEmsType() {
        return emsType;
    }

    public void setEmsType(String emsType) {
        this.emsType = emsType;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

        return new String[] { "SWM", "TB_SW_EMPLOYEE_SCHEDULING_HIST", "EMS_ID_H" };
    }

}