package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Jeetendra.Pal
 * @since 13 Jul 2016
 */
@Entity
@Table(name = "TB_WT_PLUM_RENWANL_SCH")
public class TbPlumRenewalScheduler implements Serializable {

    private static final long serialVersionUID = 3424982595946854168L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "REN_ID", precision = 12, scale = 0, nullable = false)
    private long renId;

    @Column(name = "SERVICEID", precision = 12, scale = 0, nullable = true)
    private Long serviceid;

    @Column(name = "FROMDATE", nullable = true)
    private Date fromdate;

    @Column(name = "DEPENDON", precision = 10, scale = 0, nullable = true)
    private Long dependon;

    @Column(name = "TODATE", nullable = true)
    private Date todate;

    @Column(name = "DAY_START", precision = 4, scale = 0, nullable = true)
    private Long dayStart;

    @Column(name = "MON_START", precision = 4, scale = 0, nullable = true)
    private Long monStart;

    @Column(name = "DAY_END", precision = 4, scale = 0, nullable = true)
    private Long dayEnd;

    @Column(name = "MON_END", precision = 4, scale = 0, nullable = true)
    private Long monEnd;

    @Column(name = "VALUE", precision = 4, scale = 0, nullable = true)
    private Long value;

    @Column(name = "DAYMONYEAR", precision = 4, scale = 0, nullable = true)
    private Long daymonyear;

    @Column(name = "STATUS", length = 1, nullable = false)
    private String status;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 4, scale = 0, nullable = false)
    private int langId;

    @Column(name = "LMODDATE", nullable = false)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "TP_V1", length = 100, nullable = true)
    private String tpV1;

    @Column(name = "TP_V2", length = 100, nullable = true)
    private String tpV2;

    @Column(name = "TP_V3", length = 100, nullable = true)
    private String tpV3;

    @Column(name = "TP_V4", length = 100, nullable = true)
    private String tpV4;

    @Column(name = "TP_V5", length = 100, nullable = true)
    private String tpV5;

    @Column(name = "TP_N1", precision = 15, scale = 0, nullable = true)
    private Long tpN1;

    @Column(name = "TP_N2", precision = 15, scale = 0, nullable = true)
    private Long tpN2;

    @Column(name = "TP_N3", precision = 15, scale = 0, nullable = true)
    private Long tpN3;

    @Column(name = "TP_N4", precision = 15, scale = 0, nullable = true)
    private Long tpN4;

    @Column(name = "TP_N5", precision = 15, scale = 0, nullable = true)
    private Long tpN5;

    @Column(name = "TP_D1", nullable = true)
    private Date tpD1;

    @Column(name = "TP_D2", nullable = true)
    private Date tpD2;

    @Column(name = "TP_D3", nullable = true)
    private Date tpD3;

    @Column(name = "TP_LO1", length = 1, nullable = true)
    private String tpLo1;

    @Column(name = "TP_LO2", length = 1, nullable = true)
    private String tpLo2;

    @Column(name = "TP_LO3", length = 1, nullable = true)
    private String tpLo3;

    /**
     * @return the renId
     */
    public long getRenId() {
        return renId;
    }

    /**
     * @param renId the renId to set
     */
    public void setRenId(final long renId) {
        this.renId = renId;
    }

    /**
     * @return the serviceid
     */
    public Long getServiceid() {
        return serviceid;
    }

    /**
     * @param serviceid the serviceid to set
     */
    public void setServiceid(final Long serviceid) {
        this.serviceid = serviceid;
    }

    /**
     * @return the fromdate
     */
    public Date getFromdate() {
        return fromdate;
    }

    /**
     * @param fromdate the fromdate to set
     */
    public void setFromdate(final Date fromdate) {
        this.fromdate = fromdate;
    }

    /**
     * @return the dependon
     */
    public Long getDependon() {
        return dependon;
    }

    /**
     * @param dependon the dependon to set
     */
    public void setDependon(final Long dependon) {
        this.dependon = dependon;
    }

    /**
     * @return the todate
     */
    public Date getTodate() {
        return todate;
    }

    /**
     * @param todate the todate to set
     */
    public void setTodate(final Date todate) {
        this.todate = todate;
    }

    /**
     * @return the dayStart
     */
    public Long getDayStart() {
        return dayStart;
    }

    /**
     * @param dayStart the dayStart to set
     */
    public void setDayStart(final Long dayStart) {
        this.dayStart = dayStart;
    }

    /**
     * @return the monStart
     */
    public Long getMonStart() {
        return monStart;
    }

    /**
     * @param monStart the monStart to set
     */
    public void setMonStart(final Long monStart) {
        this.monStart = monStart;
    }

    /**
     * @return the dayEnd
     */
    public Long getDayEnd() {
        return dayEnd;
    }

    /**
     * @param dayEnd the dayEnd to set
     */
    public void setDayEnd(final Long dayEnd) {
        this.dayEnd = dayEnd;
    }

    /**
     * @return the monEnd
     */
    public Long getMonEnd() {
        return monEnd;
    }

    /**
     * @param monEnd the monEnd to set
     */
    public void setMonEnd(final Long monEnd) {
        this.monEnd = monEnd;
    }

    /**
     * @return the value
     */
    public Long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final Long value) {
        this.value = value;
    }

    /**
     * @return the daymonyear
     */
    public Long getDaymonyear() {
        return daymonyear;
    }

    /**
     * @param daymonyear the daymonyear to set
     */
    public void setDaymonyear(final Long daymonyear) {
        this.daymonyear = daymonyear;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the tpV1
     */
    public String getTpV1() {
        return tpV1;
    }

    /**
     * @param tpV1 the tpV1 to set
     */
    public void setTpV1(final String tpV1) {
        this.tpV1 = tpV1;
    }

    /**
     * @return the tpV2
     */
    public String getTpV2() {
        return tpV2;
    }

    /**
     * @param tpV2 the tpV2 to set
     */
    public void setTpV2(final String tpV2) {
        this.tpV2 = tpV2;
    }

    /**
     * @return the tpV3
     */
    public String getTpV3() {
        return tpV3;
    }

    /**
     * @param tpV3 the tpV3 to set
     */
    public void setTpV3(final String tpV3) {
        this.tpV3 = tpV3;
    }

    /**
     * @return the tpV4
     */
    public String getTpV4() {
        return tpV4;
    }

    /**
     * @param tpV4 the tpV4 to set
     */
    public void setTpV4(final String tpV4) {
        this.tpV4 = tpV4;
    }

    /**
     * @return the tpV5
     */
    public String getTpV5() {
        return tpV5;
    }

    /**
     * @param tpV5 the tpV5 to set
     */
    public void setTpV5(final String tpV5) {
        this.tpV5 = tpV5;
    }

    /**
     * @return the tpN1
     */
    public Long getTpN1() {
        return tpN1;
    }

    /**
     * @param tpN1 the tpN1 to set
     */
    public void setTpN1(final Long tpN1) {
        this.tpN1 = tpN1;
    }

    /**
     * @return the tpN2
     */
    public Long getTpN2() {
        return tpN2;
    }

    /**
     * @param tpN2 the tpN2 to set
     */
    public void setTpN2(final Long tpN2) {
        this.tpN2 = tpN2;
    }

    /**
     * @return the tpN3
     */
    public Long getTpN3() {
        return tpN3;
    }

    /**
     * @param tpN3 the tpN3 to set
     */
    public void setTpN3(final Long tpN3) {
        this.tpN3 = tpN3;
    }

    /**
     * @return the tpN4
     */
    public Long getTpN4() {
        return tpN4;
    }

    /**
     * @param tpN4 the tpN4 to set
     */
    public void setTpN4(final Long tpN4) {
        this.tpN4 = tpN4;
    }

    /**
     * @return the tpN5
     */
    public Long getTpN5() {
        return tpN5;
    }

    /**
     * @param tpN5 the tpN5 to set
     */
    public void setTpN5(final Long tpN5) {
        this.tpN5 = tpN5;
    }

    /**
     * @return the tpD1
     */
    public Date getTpD1() {
        return tpD1;
    }

    /**
     * @param tpD1 the tpD1 to set
     */
    public void setTpD1(final Date tpD1) {
        this.tpD1 = tpD1;
    }

    /**
     * @return the tpD2
     */
    public Date getTpD2() {
        return tpD2;
    }

    /**
     * @param tpD2 the tpD2 to set
     */
    public void setTpD2(final Date tpD2) {
        this.tpD2 = tpD2;
    }

    /**
     * @return the tpD3
     */
    public Date getTpD3() {
        return tpD3;
    }

    /**
     * @param tpD3 the tpD3 to set
     */
    public void setTpD3(final Date tpD3) {
        this.tpD3 = tpD3;
    }

    /**
     * @return the tpLo1
     */
    public String getTpLo1() {
        return tpLo1;
    }

    /**
     * @param tpLo1 the tpLo1 to set
     */
    public void setTpLo1(final String tpLo1) {
        this.tpLo1 = tpLo1;
    }

    /**
     * @return the tpLo2
     */
    public String getTpLo2() {
        return tpLo2;
    }

    /**
     * @param tpLo2 the tpLo2 to set
     */
    public void setTpLo2(final String tpLo2) {
        this.tpLo2 = tpLo2;
    }

    /**
     * @return the tpLo3
     */
    public String getTpLo3() {
        return tpLo3;
    }

    /**
     * @param tpLo3 the tpLo3 to set
     */
    public void setTpLo3(final String tpLo3) {
        this.tpLo3 = tpLo3;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_PLUM_RENWANL_SCH", "REN_ID" };
    }

}