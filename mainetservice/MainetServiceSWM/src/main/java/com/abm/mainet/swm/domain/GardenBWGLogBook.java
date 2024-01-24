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

@Entity
@Table(name = "TB_SW_GARDEN_BWG_LOGBOOK")
public class GardenBWGLogBook implements Serializable {
    private static final long serialVersionUID = -4572346118727483522L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "GALOG_ID")
    private Long gaLogId;

    @Column(name = "COD_ID_OPER_LEVEL1")
    private Long codIdOperLevel1;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "GALOG_AWIPIT")
    private BigDecimal galogAwipit;

    @Column(name = "GALOG_CPM")
    private String galogCpm;

    @Column(name = "GALOG_CPY")
    private String galogCpy;

    @Column(name = "GALOG_GBFC")
    private String galogGbfc;

    @Column(name = "GALOG_MFMSP")
    private String galogMfmsp;

    @Column(name = "GALOG_PITNO")
    private String galogPitno;

    @Column(name = "GALOG_PLANTC")
    private BigDecimal galogPlantc;

    @Column(name = "GALOG_SLRMCNAME")
    private String galogSlrmcname;

    @Column(name = "GALOG_SLRMCNO")
    private String galogSlrmcno;

    @Temporal(TemporalType.DATE)
    @Column(name = "GALOG_SLRMDT")
    private Date galogSlrmdt;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VE_NO")
    private String veNo;

    public GardenBWGLogBook() {
    }

    public Long getGaLogId() {
        return gaLogId;
    }

    public void setGaLogId(Long gaLogId) {
        this.gaLogId = gaLogId;
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
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getGalogAwipit() {
        return this.galogAwipit;
    }

    public void setGalogAwipit(BigDecimal galogAwipit) {
        this.galogAwipit = galogAwipit;
    }

    public String getGalogCpm() {
        return this.galogCpm;
    }

    public void setGalogCpm(String galogCpm) {
        this.galogCpm = galogCpm;
    }

    public String getGalogCpy() {
        return this.galogCpy;
    }

    public void setGalogCpy(String galogCpy) {
        this.galogCpy = galogCpy;
    }

    public String getGalogGbfc() {
        return this.galogGbfc;
    }

    public void setGalogGbfc(String galogGbfc) {
        this.galogGbfc = galogGbfc;
    }

    public String getGalogMfmsp() {
        return this.galogMfmsp;
    }

    public void setGalogMfmsp(String galogMfmsp) {
        this.galogMfmsp = galogMfmsp;
    }

    public String getGalogPitno() {
        return this.galogPitno;
    }

    public void setGalogPitno(String galogPitno) {
        this.galogPitno = galogPitno;
    }

    public BigDecimal getGalogPlantc() {
        return this.galogPlantc;
    }

    public void setGalogPlantc(BigDecimal galogPlantc) {
        this.galogPlantc = galogPlantc;
    }

    public String getGalogSlrmcname() {
        return this.galogSlrmcname;
    }

    public void setGalogSlrmcname(String galogSlrmcname) {
        this.galogSlrmcname = galogSlrmcname;
    }

    public String getGalogSlrmcno() {
        return this.galogSlrmcno;
    }

    public void setGalogSlrmcno(String galogSlrmcno) {
        this.galogSlrmcno = galogSlrmcno;
    }

    public Date getGalogSlrmdt() {
        return this.galogSlrmdt;
    }

    public void setGalogSlrmdt(Date galogSlrmdt) {
        this.galogSlrmdt = galogSlrmdt;
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

    public Long getCodIdOperLevel1() {
        return codIdOperLevel1;
    }

    public void setCodIdOperLevel1(Long codIdOperLevel1) {
        this.codIdOperLevel1 = codIdOperLevel1;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getVeNo() {
        return this.veNo;
    }

    public void setVeNo(String veNo) {
        this.veNo = veNo;
    }

    public String[] getPkValues() {
        return new String[] { "SWM", "TB_SW_GARDEN_BWG_LOGBOOK", "GALOG_ID" };
    }

}
