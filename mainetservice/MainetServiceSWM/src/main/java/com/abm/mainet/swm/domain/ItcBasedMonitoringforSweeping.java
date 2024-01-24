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
@Table(name = "tb_sw_itc_sweepingm")
public class ItcBasedMonitoringforSweeping implements Serializable {
    private static final long serialVersionUID = -4716447220292545850L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ITCM_ID")
    private Long itcmId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "ITCM_BEHCUR")
    private String itcmBehcur;

    @Column(name = "ITCM_EMPNAME")
    private String itcmEmpname;

    @Column(name = "ITCM_GVP_EYESORE")
    private String itcmGvpEyesore;

    @Column(name = "ITCM_LATE_ARRIVALS")
    private String itcmLateArrivals;

    @Column(name = "ITCM_LITERATE_AREA")
    private String itcmLiterateArea;

    @Column(name = "ITCM_LITERATE_AREANAME")
    private String itcmLiterateAreaname;

    @Column(name = "ITCM_MOBILE_APPUSAGE")
    private String itcmMobileAppusage;

    @Column(name = "ITCM_NOT_ARRIVALS")
    private String itcmNotArrivals;

    @Column(name = "ITCM_SCREEN_CLEANNING")
    private String itcmScreenCleanning;

    @Temporal(TemporalType.DATE)
    @Column(name = "ITCM_TRANDATE")
    private Date itcmTrandate;

    @Column(name = "ITCM_UNIFORM")
    private String itcmUniform;

    @Column(name = "ITCM_WTPF")
    private String itcmWtpf;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public ItcBasedMonitoringforSweeping() {
    }

    public Long getItcmId() {
        return itcmId;
    }

    public void setItcmId(Long itcmId) {
        this.itcmId = itcmId;
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

    public String getItcmBehcur() {
        return itcmBehcur;
    }

    public void setItcmBehcur(String itcmBehcur) {
        this.itcmBehcur = itcmBehcur;
    }

    public String getItcmEmpname() {
        return itcmEmpname;
    }

    public void setItcmEmpname(String itcmEmpname) {
        this.itcmEmpname = itcmEmpname;
    }

    public String getItcmGvpEyesore() {
        return itcmGvpEyesore;
    }

    public void setItcmGvpEyesore(String itcmGvpEyesore) {
        this.itcmGvpEyesore = itcmGvpEyesore;
    }

    public String getItcmLateArrivals() {
        return itcmLateArrivals;
    }

    public void setItcmLateArrivals(String itcmLateArrivals) {
        this.itcmLateArrivals = itcmLateArrivals;
    }

    public String getItcmLiterateArea() {
        return itcmLiterateArea;
    }

    public void setItcmLiterateArea(String itcmLiterateArea) {
        this.itcmLiterateArea = itcmLiterateArea;
    }

    public String getItcmLiterateAreaname() {
        return itcmLiterateAreaname;
    }

    public void setItcmLiterateAreaname(String itcmLiterateAreaname) {
        this.itcmLiterateAreaname = itcmLiterateAreaname;
    }

    public String getItcmMobileAppusage() {
        return itcmMobileAppusage;
    }

    public void setItcmMobileAppusage(String itcmMobileAppusage) {
        this.itcmMobileAppusage = itcmMobileAppusage;
    }

    public String getItcmNotArrivals() {
        return itcmNotArrivals;
    }

    public void setItcmNotArrivals(String itcmNotArrivals) {
        this.itcmNotArrivals = itcmNotArrivals;
    }

    public String getItcmScreenCleanning() {
        return itcmScreenCleanning;
    }

    public void setItcmScreenCleanning(String itcmScreenCleanning) {
        this.itcmScreenCleanning = itcmScreenCleanning;
    }

    public Date getItcmTrandate() {
        return itcmTrandate;
    }

    public void setItcmTrandate(Date itcmTrandate) {
        this.itcmTrandate = itcmTrandate;
    }

    public String getItcmUniform() {
        return itcmUniform;
    }

    public void setItcmUniform(String itcmUniform) {
        this.itcmUniform = itcmUniform;
    }

    public String getItcmWtpf() {
        return itcmWtpf;
    }

    public void setItcmWtpf(String itcmWtpf) {
        this.itcmWtpf = itcmWtpf;
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

    public String[] getPkValues() {
        return new String[] { "SWM", "tb_sw_itc_sweepingm", "itcmId" };
    }
}
