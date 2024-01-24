package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Jeetendra.Pal
 * @since 10 Mar 2016
 * @comment This table is used to store Disconnections(Temporary/Permanent) entries.
 */
@Entity
@Table(name = "TB_WT_DISCONNECTIONS")
public class TBWaterDisconnection implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2906418841345295764L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DISC_ID", precision = 12, scale = 0, nullable = false)
    private long discId;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    private Long csIdn;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private Long apmApplicationId;

    @Column(name = "DISC_APPDATE", nullable = true)
    private Date discAppdate;

    @Column(name = "DISC_REASON", length = 2000, nullable = true)
    private String discReason;

    @Column(name = "DISC_TYPE", precision = 12, scale = 0, nullable = true)
    private Long discType;

    @Column(name = "DISC_METHOD", precision = 12, scale = 0, nullable = true)
    private Long discMethod;

    @Column(name = "DISC_GRANT_FLAG", length = 1, nullable = true)
    private String discGrantFlag;

    @Column(name = "DISC_APRVDATE", nullable = true)
    private Date discAprvdate;

    @Column(name = "DISC_APPROVEDBY", precision = 7, scale = 0, nullable = true)
    private Long discApprovedby;

    @Column(name = "DISC_EXECDATE", nullable = true)
    private Date discExecdate;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "WLB_WR_PRFLG", length = 1, nullable = true)
    private String wlbWrPrflg;

    @Column(name = "WT_V2", length = 100, nullable = true)
    private String wtV2;

    @Column(name = "WT_V3", length = 100, nullable = true)
    private String wtV3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    private String wtV4;

    @Column(name = "WT_V5", length = 100, nullable = true)
    private String wtV5;

    @Column(name = "WLB_WKNO", precision = 12, scale = 0, nullable = true)
    private Long wlbWkno;

    @Column(name = "WT_N2", precision = 15, scale = 0, nullable = true)
    private Long wtN2;

    @Column(name = "WT_N3", precision = 15, scale = 0, nullable = true)
    private Long wtN3;

    @Column(name = "WT_N4", precision = 15, scale = 0, nullable = true)
    private Long wtN4;

    @Column(name = "WT_N5", precision = 15, scale = 0, nullable = true)
    private Long wtN5;

    @Column(name = "WLB_WKDT", nullable = true)
    private Date wlbWkdt;

    @Column(name = "WT_D2", nullable = true)
    private Date wtD2;

    @Column(name = "WT_D3", nullable = true)
    private Date wtD3;

    @Column(name = "WT_LO1", length = 1, nullable = true)
    private String wtLo1;

    @Column(name = "WT_LO2", length = 1, nullable = true)
    private String wtLo2;

    @Column(name = "WT_LO3", length = 1, nullable = true)
    private String wtLo3;

    @Column(name = "PLUM_ID", precision = 12, scale = 0, nullable = true)
    private Long plumId;

    @Temporal(TemporalType.DATE)
    @Column(name = "TEMP_DIS_FROM_DATE", nullable = true)
    private Date disconnectFromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TEMP_DIS_TO_DATE", nullable = true)
    private Date disconnectToDate;

    public long getDiscId() {
        return discId;
    }

    public void setDiscId(final long discId) {
        this.discId = discId;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Date getDiscAppdate() {
        return discAppdate;
    }

    public void setDiscAppdate(final Date discAppdate) {
        this.discAppdate = discAppdate;
    }

    public String getDiscReason() {
        return discReason;
    }

    public void setDiscReason(final String discReason) {
        this.discReason = discReason;
    }

    public Long getDiscType() {
        return discType;
    }

    public void setDiscType(final Long discType) {
        this.discType = discType;
    }

    public Long getDiscMethod() {
        return discMethod;
    }

    public void setDiscMethod(final Long discMethod) {
        this.discMethod = discMethod;
    }

    public String getDiscGrantFlag() {
        return discGrantFlag;
    }

    public void setDiscGrantFlag(final String discGrantFlag) {
        this.discGrantFlag = discGrantFlag;
    }

    public Date getDiscAprvdate() {
        return discAprvdate;
    }

    public void setDiscAprvdate(final Date discAprvdate) {
        this.discAprvdate = discAprvdate;
    }

    public Long getDiscApprovedby() {
        return discApprovedby;
    }

    public void setDiscApprovedby(final Long discApprovedby) {
        this.discApprovedby = discApprovedby;
    }

    public Date getDiscExecdate() {
        return discExecdate;
    }

    public void setDiscExecdate(final Date discExecdate) {
        this.discExecdate = discExecdate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getWlbWrPrflg() {
        return wlbWrPrflg;
    }

    public void setWlbWrPrflg(final String wlbWrPrflg) {
        this.wlbWrPrflg = wlbWrPrflg;
    }

    public String getWtV2() {
        return wtV2;
    }

    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV3() {
        return wtV3;
    }

    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV4() {
        return wtV4;
    }

    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV5() {
        return wtV5;
    }

    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    public Long getWlbWkno() {
        return wlbWkno;
    }

    public void setWlbWkno(final Long wlbWkno) {
        this.wlbWkno = wlbWkno;
    }

    public Long getWtN2() {
        return wtN2;
    }

    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    public Long getWtN3() {
        return wtN3;
    }

    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    public Long getWtN4() {
        return wtN4;
    }

    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    public Long getWtN5() {
        return wtN5;
    }

    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    public Date getWlbWkdt() {
        return wlbWkdt;
    }

    public void setWlbWkdt(final Date wlbWkdt) {
        this.wlbWkdt = wlbWkdt;
    }

    public Date getWtD2() {
        return wtD2;
    }

    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD3() {
        return wtD3;
    }

    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo3() {
        return wtLo3;
    }

    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    public Long getPlumId() {
        return plumId;
    }

    public void setPlumId(final Long plumId) {
        this.plumId = plumId;
    }

    public Date getDisconnectFromDate() {
        return disconnectFromDate;
    }

    public void setDisconnectFromDate(final Date disconnectFromDate) {
        this.disconnectFromDate = disconnectFromDate;
    }

    public Date getDisconnectToDate() {
        return disconnectToDate;
    }

    public void setDisconnectToDate(final Date disconnectToDate) {
        this.disconnectToDate = disconnectToDate;
    }

    @JsonIgnore
    public String[] getPkValues() {

        return new String[] { "WT", "TB_WT_DISCONNECTIONS", "DISC_ID" };
    }

}