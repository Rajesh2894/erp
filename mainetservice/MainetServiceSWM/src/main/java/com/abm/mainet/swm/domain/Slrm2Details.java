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
@Table(name = "TB_SW_SLRM2")
public class Slrm2Details implements Serializable {
    private static final long serialVersionUID = -2623234965938588058L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SLRM_ID")
    private Long slrmId;

    @Column(name = "SLRM_CName")
    private String slrmCname;

    @Column(name = "SLRM_CNO")
    private String slrmCno;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SLRM_Date")
    private Date slrmDate;

    @Column(name = "SLRM_TAPAW")
    private BigDecimal slrmTapaw;

    @Column(name = "SLRM_TAPW")
    private String tapw;

    @Column(name = "SLRM_TACBW")
    private BigDecimal slrmTacbw;

    @Column(name = "SLRM_TAGW")
    private BigDecimal slrmTagw;

    @Column(name = "SLRM_TAMW")
    private BigDecimal slrmTamw;

    @Column(name = "SLRM_TARW")
    private BigDecimal slrmTarw;

    @Column(name = "SLRM_TALW")
    private String slrmTalw;

    @Column(name = "SLRM_TATW")
    private BigDecimal slrmTatw;

    @Column(name = "SLRM_TACW")
    private BigDecimal slrmTacw;

    @Column(name = "SLRM_TAWW")
    private BigDecimal slrmTaww;

    @Column(name = "SLRM_TAPVCW")
    private BigDecimal slrmTapvcw;

    @Column(name = "SLRM_TAPoW")
    private BigDecimal slrmTapow;

    @Column(name = "SLRM_TACEW")
    private BigDecimal slrmTacew;

    @Column(name = "SLRM_TCOM")
    private BigDecimal slrmTcom;

    @Column(name = "SLRM_TBP")
    private BigDecimal slrmTbp;

    @Column(name = "SLRM_TLAND")
    private BigDecimal slrmTland;

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

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public Long getSlrmId() {
        return slrmId;
    }

    public void setSlrmId(Long slrmId) {
        this.slrmId = slrmId;
    }

    public String getSlrmCname() {
        return slrmCname;
    }

    public void setSlrmCname(String slrmCname) {
        this.slrmCname = slrmCname;
    }

    public String getSlrmCno() {
        return slrmCno;
    }

    public void setSlrmCno(String slrmCno) {
        this.slrmCno = slrmCno;
    }

    public Date getSlrmDate() {
        return slrmDate;
    }

    public void setSlrmDate(Date slrmDate) {
        this.slrmDate = slrmDate;
    }

    public BigDecimal getSlrmTapaw() {
        return slrmTapaw;
    }

    public void setSlrmTapaw(BigDecimal slrmTapaw) {
        this.slrmTapaw = slrmTapaw;
    }

    public String getTapw() {
        return tapw;
    }

    public void setTapw(String tapw) {
        this.tapw = tapw;
    }

    public BigDecimal getSlrmTacbw() {
        return slrmTacbw;
    }

    public void setSlrmTacbw(BigDecimal slrmTacbw) {
        this.slrmTacbw = slrmTacbw;
    }

    public BigDecimal getSlrmTagw() {
        return slrmTagw;
    }

    public void setSlrmTagw(BigDecimal slrmTagw) {
        this.slrmTagw = slrmTagw;
    }

    public BigDecimal getSlrmTamw() {
        return slrmTamw;
    }

    public void setSlrmTamw(BigDecimal slrmTamw) {
        this.slrmTamw = slrmTamw;
    }

    public BigDecimal getSlrmTarw() {
        return slrmTarw;
    }

    public void setSlrmTarw(BigDecimal slrmTarw) {
        this.slrmTarw = slrmTarw;
    }

    public String getSlrmTalw() {
        return slrmTalw;
    }

    public void setSlrmTalw(String slrmTalw) {
        this.slrmTalw = slrmTalw;
    }

    public BigDecimal getSlrmTatw() {
        return slrmTatw;
    }

    public void setSlrmTatw(BigDecimal slrmTatw) {
        this.slrmTatw = slrmTatw;
    }

    public BigDecimal getSlrmTacw() {
        return slrmTacw;
    }

    public void setSlrmTacw(BigDecimal slrmTacw) {
        this.slrmTacw = slrmTacw;
    }

    public BigDecimal getSlrmTaww() {
        return slrmTaww;
    }

    public void setSlrmTaww(BigDecimal slrmTaww) {
        this.slrmTaww = slrmTaww;
    }

    public BigDecimal getSlrmTapvcw() {
        return slrmTapvcw;
    }

    public void setSlrmTapvcw(BigDecimal slrmTapvcw) {
        this.slrmTapvcw = slrmTapvcw;
    }

    public BigDecimal getSlrmTapow() {
        return slrmTapow;
    }

    public void setSlrmTapow(BigDecimal slrmTapow) {
        this.slrmTapow = slrmTapow;
    }

    public BigDecimal getSlrmTacew() {
        return slrmTacew;
    }

    public void setSlrmTacew(BigDecimal slrmTacew) {
        this.slrmTacew = slrmTacew;
    }

    public BigDecimal getSlrmTcom() {
        return slrmTcom;
    }

    public void setSlrmTcom(BigDecimal slrmTcom) {
        this.slrmTcom = slrmTcom;
    }

    public BigDecimal getSlrmTbp() {
        return slrmTbp;
    }

    public void setSlrmTbp(BigDecimal slrmTbp) {
        this.slrmTbp = slrmTbp;
    }

    public BigDecimal getSlrmTland() {
        return slrmTland;
    }

    public void setSlrmTland(BigDecimal slrmTland) {
        this.slrmTland = slrmTland;
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
        return new String[] { "SWM", "TB_SW_SLRM2", "SLRM_ID" };
    }
}
