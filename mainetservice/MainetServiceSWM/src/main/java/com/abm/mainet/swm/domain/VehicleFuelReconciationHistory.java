package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the tb_sw_vehiclefuel_inrec_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 16-Jun-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLEFUEL_INREC_HIST")
public class VehicleFuelReconciationHistory implements Serializable {

    private static final long serialVersionUID = -990364304901796240L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INREC_ID_H", unique = true, nullable = false)
    private Long inrecIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "PU_ID")
    private Long puId;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "INREC_FROMDT")
    private Date inrecFromdt;

    @Column(name = "INREC_ID")
    private Long inrecId;

    @Temporal(TemporalType.DATE)
    @Column(name = "INREC_TODT")
    private Date inrecTodt;

    @Column(name = "INRECD_EXPEN")
    private Long inrecdExpen;

    @Column(name = "INRECD_INVAMT")
    private BigDecimal inrecdInvamt;

    @Temporal(TemporalType.DATE)
    @Column(name = "INRECD_INVDATE")
    private Date inrecdInvdate;

    @Column(name = "INRECD_INVNO")
    private Long inrecdInvno;

    public VehicleFuelReconciationHistory() {
    }

    public Long getInrecIdH() {
        return this.inrecIdH;
    }

    public void setInrecIdH(Long inrecIdH) {
        this.inrecIdH = inrecIdH;
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

    public Date getInrecFromdt() {
        return this.inrecFromdt;
    }

    public void setInrecFromdt(Date inrecFromdt) {
        this.inrecFromdt = inrecFromdt;
    }

    public Long getInrecId() {
        return this.inrecId;
    }

    public void setInrecId(Long inrecId) {
        this.inrecId = inrecId;
    }

    public Date getInrecTodt() {
        return this.inrecTodt;
    }

    public void setInrecTodt(Date inrecTodt) {
        this.inrecTodt = inrecTodt;
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

    public Long getPuId() {
        return this.puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getInrecdExpen() {
        return inrecdExpen;
    }

    public void setInrecdExpen(Long inrecdExpen) {
        this.inrecdExpen = inrecdExpen;
    }

    public BigDecimal getInrecdInvamt() {
        return inrecdInvamt;
    }

    public void setInrecdInvamt(BigDecimal inrecdInvamt) {
        this.inrecdInvamt = inrecdInvamt;
    }

    public Date getInrecdInvdate() {
        return inrecdInvdate;
    }

    public void setInrecdInvdate(Date inrecdInvdate) {
        this.inrecdInvdate = inrecdInvdate;
    }

    public Long getInrecdInvno() {
        return inrecdInvno;
    }

    public void setInrecdInvno(Long inrecdInvno) {
        this.inrecdInvno = inrecdInvno;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_SANISTAFF_TG_HIST", "INREC_ID_H" };
    }
}