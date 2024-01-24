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
 * The persistent class for the tb_sw_wasteseg_hist database table.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
@Entity
@Table(name = "TB_SW_WASTESEG_HIST")
public class WastageSegregationHistory implements Serializable {

    private static final long serialVersionUID = -8137988933728642688L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "GR_ID_H", unique = true, nullable = false)
    private Long grIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MRF_ID")
    private Long deId;

    @Temporal(TemporalType.DATE)
    @Column(name = "GR_DATE")
    private Date grDate;

    @Column(name = "GR_ID")
    private Long grId;

    @Column(name = "GR_TOTAL")
    private Long grTotal;

    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "VE_ID")
    private Long venId;

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

    public WastageSegregationHistory() {
    }

    public Long getGrIdH() {
        return this.grIdH;
    }

    public void setGrIdH(Long grIdH) {
        this.grIdH = grIdH;
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

    public Long getDeId() {
        return this.deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public Date getGrDate() {
        return this.grDate;
    }

    public void setGrDate(Date grDate) {
        this.grDate = grDate;
    }

    public Long getGrId() {
        return this.grId;
    }

    public void setGrId(Long grId) {
        this.grId = grId;
    }

    public Long getGrTotal() {
        return this.grTotal;
    }

    public void setGrTotal(Long grTotal) {
        this.grTotal = grTotal;
    }

    public String getHStatus() {
        return this.hStatus;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getVenId() {
        return venId;
    }

    public void setVenId(Long venId) {
        this.venId = venId;
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

        return new String[] { "SWM", "TB_SW_WASTESEG_HIST", "GR_ID_H" };
    }
}