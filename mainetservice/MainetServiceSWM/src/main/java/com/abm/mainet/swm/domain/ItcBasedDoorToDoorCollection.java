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
@Table(name = "tb_sw_itc_dodc")
public class ItcBasedDoorToDoorCollection implements Serializable {
    private static final long serialVersionUID = 8073590174103097058L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ITCD_ID")
    private Long itcdId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "ITCD_BEHCUR")
    private String itcdBehcur;

    @Column(name = "ITCD_BIOMEDICAL_WASTE")
    private String itcdBiomedicalWaste;

    @Column(name = "ITCD_DHWC_SEPARETLY")
    private String itcdDhwcSeparetly;

    @Column(name = "ITCD_DOMESTIC_WASTE")
    private String itcdDomesticWaste;

    @Column(name = "ITCD_INDUCSTRIAL_WASTE")
    private String itcdInducstrialWaste;

    @Column(name = "ITCD_LATE_ARRIVALS")
    private String itcdLateArrivals;

    @Column(name = "ITCD_LITERATE_AREA")
    private String itcdLiterateArea;

    @Column(name = "ITCD_LITERATE_AREANAME")
    private String itcdLiterateAreaname;

    @Column(name = "ITCD_MOBILE_APPUSAGE")
    private String itcdMobileAppusage;

    @Column(name = "ITCD_NO_ARRIVALS")
    private String itcdNoArrivals;

    @Column(name = "ITCD_SOURSAG")
    private String itcdSoursag;

    @Column(name = "ITCD_STORAGE_SEPARETLY")
    private String itcdStorageSeparetly;

    @Temporal(TemporalType.DATE)
    @Column(name = "ITCD_TRANDATE")
    private Date itcdTrandate;

    @Column(name = "ITCD_UNIFORM")
    private String itcdUniform;

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

    public ItcBasedDoorToDoorCollection() {
    }

    public Long getItcdId() {
        return itcdId;
    }

    public void setItcdId(Long itcdId) {
        this.itcdId = itcdId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getItcdBehcur() {
        return itcdBehcur;
    }

    public void setItcdBehcur(String itcdBehcur) {
        this.itcdBehcur = itcdBehcur;
    }

    public String getItcdBiomedicalWaste() {
        return itcdBiomedicalWaste;
    }

    public void setItcdBiomedicalWaste(String itcdBiomedicalWaste) {
        this.itcdBiomedicalWaste = itcdBiomedicalWaste;
    }

    public String getItcdDhwcSeparetly() {
        return itcdDhwcSeparetly;
    }

    public void setItcdDhwcSeparetly(String itcdDhwcSeparetly) {
        this.itcdDhwcSeparetly = itcdDhwcSeparetly;
    }

    public String getItcdDomesticWaste() {
        return itcdDomesticWaste;
    }

    public void setItcdDomesticWaste(String itcdDomesticWaste) {
        this.itcdDomesticWaste = itcdDomesticWaste;
    }

    public String getItcdInducstrialWaste() {
        return itcdInducstrialWaste;
    }

    public void setItcdInducstrialWaste(String itcdInducstrialWaste) {
        this.itcdInducstrialWaste = itcdInducstrialWaste;
    }

    public String getItcdLateArrivals() {
        return itcdLateArrivals;
    }

    public void setItcdLateArrivals(String itcdLateArrivals) {
        this.itcdLateArrivals = itcdLateArrivals;
    }

    public String getItcdLiterateArea() {
        return itcdLiterateArea;
    }

    public void setItcdLiterateArea(String itcdLiterateArea) {
        this.itcdLiterateArea = itcdLiterateArea;
    }

    public String getItcdLiterateAreaname() {
        return itcdLiterateAreaname;
    }

    public void setItcdLiterateAreaname(String itcdLiterateAreaname) {
        this.itcdLiterateAreaname = itcdLiterateAreaname;
    }

    public String getItcdMobileAppusage() {
        return itcdMobileAppusage;
    }

    public void setItcdMobileAppusage(String itcdMobileAppusage) {
        this.itcdMobileAppusage = itcdMobileAppusage;
    }

    public String getItcdNoArrivals() {
        return itcdNoArrivals;
    }

    public void setItcdNoArrivals(String itcdNoArrivals) {
        this.itcdNoArrivals = itcdNoArrivals;
    }

    public String getItcdSoursag() {
        return itcdSoursag;
    }

    public void setItcdSoursag(String itcdSoursag) {
        this.itcdSoursag = itcdSoursag;
    }

    public String getItcdStorageSeparetly() {
        return itcdStorageSeparetly;
    }

    public void setItcdStorageSeparetly(String itcdStorageSeparetly) {
        this.itcdStorageSeparetly = itcdStorageSeparetly;
    }

    public Date getItcdTrandate() {
        return itcdTrandate;
    }

    public void setItcdTrandate(Date itcdTrandate) {
        this.itcdTrandate = itcdTrandate;
    }

    public String getItcdUniform() {
        return itcdUniform;
    }

    public void setItcdUniform(String itcdUniform) {
        this.itcdUniform = itcdUniform;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
        return new String[] { "SWM", "tb_sw_itc_dodc", "ITCD_ID" };
    }
}
