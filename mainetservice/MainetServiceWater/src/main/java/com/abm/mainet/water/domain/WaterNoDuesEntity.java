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

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author manisha.mudaliar
 * @since 10 May 2016
 */
@Entity
@Table(name = "TB_WT_NODUES ")
public class WaterNoDuesEntity implements Serializable {
    private static final long serialVersionUID = 1997354309213260524L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CA_ID", precision = 16, scale = 0, nullable = false)
    private Long acId;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = false)
    private Long csIdn;

    @Column(name = "CA_NOTICENO", precision = 12, scale = 0, nullable = true)
    private Long caNoticeno;

    @Column(name = "CA_COPIES", precision = 3, scale = 0, nullable = true)
    private Long caCopies;

    @Column(name = "CA_WATER_DUE", precision = 12, scale = 2, nullable = true)
    private Double caWaterDue;

    @Column(name = "CA_PROPDUES", precision = 12, scale = 2, nullable = true)
    private Double caPropdues;

    @Column(name = "RM_AMOUNT", precision = 12, scale = 2, nullable = true)
    private Double rmAmount;

    @Column(name = "RM_RCPTID", precision = 12, scale = 0, nullable = true)
    private Long rmRcptid;

    @Column(name = "FROM_YR", length = 4, nullable = true)
    private String fromYr;

    @Column(name = "TO_YR", length = 4, nullable = true)
    private String toYr;

    @JsonIgnore
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "LMODDATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "PR_FLAG", length = 1, nullable = true)
    private String prFlag;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "WT_V1", length = 100, nullable = true)
    private String wtV1;

    @Column(name = "WT_V2", length = 100, nullable = true)
    private String wtV2;

    @Column(name = "WT_V3", length = 100, nullable = true)
    private String wtV3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    private String wtV4;

    @Column(name = "WT_V5", length = 100, nullable = true)
    private String wtV5;

    @Column(name = "WT_N1", precision = 15, scale = 0, nullable = true)
    private Long wtN1;

    @Column(name = "WT_N2", precision = 15, scale = 0, nullable = true)
    private Long wtN2;

    @Column(name = "WT_N3", precision = 15, scale = 0, nullable = true)
    private Long wtN3;

    @Column(name = "WT_N4", precision = 15, scale = 0, nullable = true)
    private Long wtN4;

    @Column(name = "WT_N5", precision = 15, scale = 0, nullable = true)
    private Long wtN5;

    @Column(name = "WT_D1", nullable = true)
    private Date wtD1;

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

    /**
     * @return the acId
     */
    public Long getAcId() {
        return acId;
    }

    /**
     * @param acId the acId to set
     */
    public void setAcId(final Long acId) {
        this.acId = acId;
    }

    /**
     * @return the csIdn
     */
    public Long getCsIdn() {
        return csIdn;
    }

    /**
     * @param csIdn the csIdn to set
     */
    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    /**
     * @return the caNoticeno
     */
    public Long getCaNoticeno() {
        return caNoticeno;
    }

    /**
     * @param caNoticeno the caNoticeno to set
     */
    public void setCaNoticeno(final Long caNoticeno) {
        this.caNoticeno = caNoticeno;
    }

    /**
     * @return the caCopies
     */
    public Long getCaCopies() {
        return caCopies;
    }

    /**
     * @param caCopies the caCopies to set
     */
    public void setCaCopies(final Long caCopies) {
        this.caCopies = caCopies;
    }

    /**
     * @return the caWaterDue
     */
    public Double getCaWaterDue() {
        return caWaterDue;
    }

    /**
     * @param caWaterDue the caWaterDue to set
     */
    public void setCaWaterDue(final Double caWaterDue) {
        this.caWaterDue = caWaterDue;
    }

    /**
     * @return the caPropdues
     */
    public Double getCaPropdues() {
        return caPropdues;
    }

    /**
     * @param caPropdues the caPropdues to set
     */
    public void setCaPropdues(final Double caPropdues) {
        this.caPropdues = caPropdues;
    }

    /**
     * @return the rmAmount
     */
    public Double getRmAmount() {
        return rmAmount;
    }

    /**
     * @param rmAmount the rmAmount to set
     */
    public void setRmAmount(final Double rmAmount) {
        this.rmAmount = rmAmount;
    }

    /**
     * @return the rmRcptid
     */
    public Long getRmRcptid() {
        return rmRcptid;
    }

    /**
     * @param rmRcptid the rmRcptid to set
     */
    public void setRmRcptid(final Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    /**
     * @return the fromYr
     */
    public String getFromYr() {
        return fromYr;
    }

    /**
     * @param fromYr the fromYr to set
     */
    public void setFromYr(final String fromYr) {
        this.fromYr = fromYr;
    }

    /**
     * @return the toYr
     */
    public String getToYr() {
        return toYr;
    }

    /**
     * @param toYr the toYr to set
     */
    public void setToYr(final String toYr) {
        this.toYr = toYr;
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
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Employee userId) {
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
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Employee updatedBy) {
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
     * @return the prFlag
     */
    public String getPrFlag() {
        return prFlag;
    }

    /**
     * @param prFlag the prFlag to set
     */
    public void setPrFlag(final String prFlag) {
        this.prFlag = prFlag;
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
     * @return the wtV1
     */
    public String getWtV1() {
        return wtV1;
    }

    /**
     * @param wtV1 the wtV1 to set
     */
    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    /**
     * @return the wtV2
     */
    public String getWtV2() {
        return wtV2;
    }

    /**
     * @param wtV2 the wtV2 to set
     */
    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    /**
     * @return the wtV3
     */
    public String getWtV3() {
        return wtV3;
    }

    /**
     * @param wtV3 the wtV3 to set
     */
    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    /**
     * @return the wtV4
     */
    public String getWtV4() {
        return wtV4;
    }

    /**
     * @param wtV4 the wtV4 to set
     */
    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    /**
     * @return the wtV5
     */
    public String getWtV5() {
        return wtV5;
    }

    /**
     * @param wtV5 the wtV5 to set
     */
    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    /**
     * @return the wtN1
     */
    public Long getWtN1() {
        return wtN1;
    }

    /**
     * @param wtN1 the wtN1 to set
     */
    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    /**
     * @return the wtN2
     */
    public Long getWtN2() {
        return wtN2;
    }

    /**
     * @param wtN2 the wtN2 to set
     */
    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    /**
     * @return the wtN3
     */
    public Long getWtN3() {
        return wtN3;
    }

    /**
     * @param wtN3 the wtN3 to set
     */
    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    /**
     * @return the wtN4
     */
    public Long getWtN4() {
        return wtN4;
    }

    /**
     * @param wtN4 the wtN4 to set
     */
    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    /**
     * @return the wtN5
     */
    public Long getWtN5() {
        return wtN5;
    }

    /**
     * @param wtN5 the wtN5 to set
     */
    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    /**
     * @return the wtD1
     */
    public Date getWtD1() {
        return wtD1;
    }

    /**
     * @param wtD1 the wtD1 to set
     */
    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    /**
     * @return the wtD2
     */
    public Date getWtD2() {
        return wtD2;
    }

    /**
     * @param wtD2 the wtD2 to set
     */
    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    /**
     * @return the wtD3
     */
    public Date getWtD3() {
        return wtD3;
    }

    /**
     * @param wtD3 the wtD3 to set
     */
    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    /**
     * @return the wtLo1
     */
    public String getWtLo1() {
        return wtLo1;
    }

    /**
     * @param wtLo1 the wtLo1 to set
     */
    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    /**
     * @return the wtLo2
     */
    public String getWtLo2() {
        return wtLo2;
    }

    /**
     * @param wtLo2 the wtLo2 to set
     */
    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    /**
     * @return the wtLo3
     */
    public String getWtLo3() {
        return wtLo3;
    }

    /**
     * @param wtLo3 the wtLo3 to set
     */
    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_NODUES", "CA_ID" };
    }

}