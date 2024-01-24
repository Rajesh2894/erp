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
@Table(name = "TB_SW_CD_WCM")
public class CdWasteLogBookDetails implements Serializable {
    private static final long serialVersionUID = -2818439010375460663L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CD_ID")
    private Long cdId;

    @Column(name = "CD_Month")
    private String cdMonth;

    @Column(name = "CD_Year")
    private String cdYear;

    @Column(name = "CD_Ncentre")
    private String cdNcentre;

    @Column(name = "CD_WardNo")
    private String cdWardNo;

    @Column(name = "CD_NWard")
    private String cdNward;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CD_Date")
    private Date cdDate;

    @Column(name = "CD_NSource")
    private String cdNsource;

    @Column(name = "CD_Tvehicle")
    private String cdTvehicle;

    @Column(name = "CD_VNo")
    private String cdVno;

    @Column(name = "CD_MCapacity")
    private BigDecimal cdMcapacity;

    @Column(name = "CD_TAW")
    private BigDecimal cdTaw;

    @Column(name = "CD_AP")
    private char cdAp;

    @Column(name = "CD_NRN")
    private String cdNrn;

    @Column(name = "CD_BPSN")
    private String cdBpsn;

    @Column(name = "CD_NCAO")
    private String cdNcao;

    @Column(name = "CD_NWC")
    private String cdNwc;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

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

    public Long getCdId() {
        return cdId;
    }

    public void setCdId(Long cdId) {
        this.cdId = cdId;
    }

    public char getCdAp() {
        return cdAp;
    }

    public void setCdAp(char cdAp) {
        this.cdAp = cdAp;
    }

    public String getCdBpsn() {
        return cdBpsn;
    }

    public void setCdBpsn(String cdBpsn) {
        this.cdBpsn = cdBpsn;
    }

    public Date getCdDate() {
        return cdDate;
    }

    public void setCdDate(Date cdDate) {
        this.cdDate = cdDate;
    }

    public BigDecimal getCdMcapacity() {
        return cdMcapacity;
    }

    public void setCdMcapacity(BigDecimal cdMcapacity) {
        this.cdMcapacity = cdMcapacity;
    }

    public String getCdMonth() {
        return cdMonth;
    }

    public void setCdMonth(String cdMonth) {
        this.cdMonth = cdMonth;
    }

    public String getCdNcao() {
        return cdNcao;
    }

    public void setCdNcao(String cdNcao) {
        this.cdNcao = cdNcao;
    }

    public String getCdNcentre() {
        return cdNcentre;
    }

    public void setCdNcentre(String cdNcentre) {
        this.cdNcentre = cdNcentre;
    }

    public String getCdNrn() {
        return cdNrn;
    }

    public void setCdNrn(String cdNrn) {
        this.cdNrn = cdNrn;
    }

    public String getCdNsource() {
        return cdNsource;
    }

    public void setCdNsource(String cdNsource) {
        this.cdNsource = cdNsource;
    }

    public String getCdNward() {
        return cdNward;
    }

    public void setCdNward(String cdNward) {
        this.cdNward = cdNward;
    }

    public String getCdNwc() {
        return cdNwc;
    }

    public void setCdNwc(String cdNwc) {
        this.cdNwc = cdNwc;
    }

    public BigDecimal getCdTaw() {
        return cdTaw;
    }

    public void setCdTaw(BigDecimal cdTaw) {
        this.cdTaw = cdTaw;
    }

    public String getCdTvehicle() {
        return cdTvehicle;
    }

    public void setCdTvehicle(String cdTvehicle) {
        this.cdTvehicle = cdTvehicle;
    }

    public String getCdVno() {
        return cdVno;
    }

    public void setCdVno(String cdVno) {
        this.cdVno = cdVno;
    }

    public String getCdWardNo() {
        return cdWardNo;
    }

    public void setCdWardNo(String cdWardNo) {
        this.cdWardNo = cdWardNo;
    }

    public String getCdYear() {
        return cdYear;
    }

    public void setCdYear(String cdYear) {
        this.cdYear = cdYear;
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

    public String[] getPkValues() {
        return new String[] { "SWM", "TB_SW_CD_WCM", "CD_ID" };
    }

}
