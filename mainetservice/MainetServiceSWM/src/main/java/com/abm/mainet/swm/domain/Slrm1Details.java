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
@Table(name = "TB_SW_SLRM1")
public class Slrm1Details implements Serializable {
    private static final long serialVersionUID = -1077919048238063466L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SLRM1_ID")
    private Long slrm1Id;

    @Column(name = "SLRM1_Month")
    private String slrm1Month;

    @Column(name = "SLRM1_Year")
    private String slrm1Year;

    @Column(name = "SLRM1_Ncentre")
    private String slrm1Ncentre;

    @Column(name = "SLRM1_NCNO")
    private String slrm1Ncno;

    @Column(name = "CD_WardNo")
    private String cdWardNo;

    @Column(name = "CD_NWard")
    private String cdWard;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public Long getSlrm1Id() {
        return slrm1Id;
    }

    public void setSlrm1Id(Long slrm1Id) {
        this.slrm1Id = slrm1Id;
    }

    public String getSlrm1Month() {
        return slrm1Month;
    }

    public void setSlrm1Month(String slrm1Month) {
        this.slrm1Month = slrm1Month;
    }

    public String getSlrm1Year() {
        return slrm1Year;
    }

    public void setSlrm1Year(String slrm1Year) {
        this.slrm1Year = slrm1Year;
    }

    public String getSlrm1Ncentre() {
        return slrm1Ncentre;
    }

    public void setSlrm1Ncentre(String slrm1Ncentre) {
        this.slrm1Ncentre = slrm1Ncentre;
    }

    public String getSlrm1Ncno() {
        return slrm1Ncno;
    }

    public void setSlrm1Ncno(String slrm1Ncno) {
        this.slrm1Ncno = slrm1Ncno;
    }

    public String getCdWardNo() {
        return cdWardNo;
    }

    public void setCdWardNo(String cdWardNo) {
        this.cdWardNo = cdWardNo;
    }

    public String getCdWard() {
        return cdWard;
    }

    public void setCdWard(String cdWard) {
        this.cdWard = cdWard;
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
        return new String[] { "SWM", "TB_SW_SLRM1", "SLRM1_ID" };
    }

}
