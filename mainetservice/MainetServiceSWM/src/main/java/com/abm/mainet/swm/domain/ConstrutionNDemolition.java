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
@Table(name = "TB_SW_ITC_CDWM")
public class ConstrutionNDemolition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ITCWM_ID")
    private Long itcwmId;

    @Column(name = "ITCWM_TRANDATE")
    private Date transDate;

    @Column(name = "VE_VETYPE")
    private Long VeType;

    @Column(name = "VE_NO")
    private String vehNo;

    @Column(name = "ITCWM_LATE_ARRIVALS")
    private String lateArrival;

    @Column(name = "ITCWM_NO_ARRIVALS")
    private String notArrival;

    @Column(name = "ITCWM_CD_WASTE")
    private String wasteFound;

    @Column(name = "ITCWM_CDWASTE_AREANAME")
    private String wastAreaName;

    @Column(name = "ITCWM_UNIFORM")
    private String uniform;

    @Column(name = "ITCWM_BEHCUR")
    private String behaviour;

    @Column(name = "ITCWM_MOBILE_APPUSAGE")
    private String mobileAppUse;

    @Column(name = "ITCWM_TRANSPORT_PROCESSING")
    private String transportTOProcess;

    @Column(name = "ITCWM_CDM_WASTE")
    private String mixwasteFound;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public ConstrutionNDemolition() {

    }

    public Long getItcwmId() {
        return itcwmId;
    }

    public void setItcwmId(Long itcwmId) {
        this.itcwmId = itcwmId;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Long getVeType() {
        return VeType;
    }

    public void setVeType(Long veType) {
        VeType = veType;
    }

    public String getVehNo() {
        return vehNo;
    }

    public void setVehNo(String vehNo) {
        this.vehNo = vehNo;
    }

    public String getLateArrival() {
        return lateArrival;
    }

    public void setLateArrival(String lateArrival) {
        this.lateArrival = lateArrival;
    }

    public String getNotArrival() {
        return notArrival;
    }

    public void setNotArrival(String notArrival) {
        this.notArrival = notArrival;
    }

    public String getWasteFound() {
        return wasteFound;
    }

    public void setWasteFound(String wasteFound) {
        this.wasteFound = wasteFound;
    }

    public String getWastAreaName() {
        return wastAreaName;
    }

    public void setWastAreaName(String wastAreaName) {
        this.wastAreaName = wastAreaName;
    }

    public String getUniform() {
        return uniform;
    }

    public void setUniform(String uniform) {
        this.uniform = uniform;
    }

    public String getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }

    public String getMobileAppUse() {
        return mobileAppUse;
    }

    public void setMobileAppUse(String mobileAppUse) {
        this.mobileAppUse = mobileAppUse;
    }

    public String getTransportTOProcess() {
        return transportTOProcess;
    }

    public void setTransportTOProcess(String transportTOProcess) {
        this.transportTOProcess = transportTOProcess;
    }

    public String getMixwasteFound() {
        return mixwasteFound;
    }

    public void setMixwasteFound(String mixwasteFound) {
        this.mixwasteFound = mixwasteFound;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public String[] getPkValues() {
        return new String[] { "SWM", "TB_SW_ITC_CDWM", "ITCWM_ID" };
    }

}
