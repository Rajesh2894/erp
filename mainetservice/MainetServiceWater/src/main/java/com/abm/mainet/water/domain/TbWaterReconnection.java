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
 * @author Arun.Chavda
 *
 */
@Entity
@Table(name = "TB_WT_RECONNECTION")
public class TbWaterReconnection implements Serializable {

    private static final long serialVersionUID = 4532172998792054946L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RCN_IDN", precision = 12, scale = 0, nullable = false)
    private long rcnIdn;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    private Long csIdn;

    @Column(name = "RCN_DATE", nullable = true)
    private Date rcnDate;

    @Column(name = "RCN_STATUS", length = 1, nullable = true)
    private String rcnStatus;

    @Column(name = "RCN_GRANTED", length = 1, nullable = true)
    private String rcnGranted;

    @Column(name = "RCCN_METHOD", precision = 12, scale = 0, nullable = true)
    private Long rccnMethod;

    @Column(name = "PLUM_ID", precision = 12, scale = 0, nullable = true)
    private Long plumId;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private Long apmApplicationId;

    @Column(name = "RCN_REMARK", length = 200, nullable = true)
    private String rcnRemark;

    @Column(name = "RCN_EXEDATE", nullable = true)
    private Date rcnExedate;

    @Column(name = "CCN_FLAG", length = 1, nullable = true)
    private String ccnFlag;

    @Column(name = "DISC_APPDATE", nullable = true)
    private Date discAppdate;

    @Column(name = "DISC_REASON", length = 500, nullable = true)
    private String discReason;

    @Column(name = "DISC_TYPE", precision = 12, scale = 0, nullable = true)
    private Long discType;

    @Column(name = "DISC_METHOD", precision = 12, scale = 0, nullable = true)
    private Long discMethod;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private Long langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

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
     * @return the rcnIdn
     */
    public long getRcnIdn() {
        return rcnIdn;
    }

    /**
     * @param rcnIdn the rcnIdn to set
     */
    public void setRcnIdn(final long rcnIdn) {
        this.rcnIdn = rcnIdn;
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
     * @return the rcnDate
     */
    public Date getRcnDate() {
        return rcnDate;
    }

    /**
     * @param rcnDate the rcnDate to set
     */
    public void setRcnDate(final Date rcnDate) {
        this.rcnDate = rcnDate;
    }

    /**
     * @return the rcnStatus
     */
    public String getRcnStatus() {
        return rcnStatus;
    }

    /**
     * @param rcnStatus the rcnStatus to set
     */
    public void setRcnStatus(final String rcnStatus) {
        this.rcnStatus = rcnStatus;
    }

    /**
     * @return the rcnGranted
     */
    public String getRcnGranted() {
        return rcnGranted;
    }

    /**
     * @param rcnGranted the rcnGranted to set
     */
    public void setRcnGranted(final String rcnGranted) {
        this.rcnGranted = rcnGranted;
    }

    /**
     * @return the rccnMethod
     */
    public Long getRccnMethod() {
        return rccnMethod;
    }

    /**
     * @param rccnMethod the rccnMethod to set
     */
    public void setRccnMethod(final Long rccnMethod) {
        this.rccnMethod = rccnMethod;
    }

    /**
     * @return the plumId
     */
    public Long getPlumId() {
        return plumId;
    }

    /**
     * @param plumId the plumId to set
     */
    public void setPlumId(final Long plumId) {
        this.plumId = plumId;
    }

    /**
     * @return the apmApplicationId
     */
    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    /**
     * @param apmApplicationId the apmApplicationId to set
     */
    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the rcnRemark
     */
    public String getRcnRemark() {
        return rcnRemark;
    }

    /**
     * @param rcnRemark the rcnRemark to set
     */
    public void setRcnRemark(final String rcnRemark) {
        this.rcnRemark = rcnRemark;
    }

    /**
     * @return the rcnExedate
     */
    public Date getRcnExedate() {
        return rcnExedate;
    }

    /**
     * @param rcnExedate the rcnExedate to set
     */
    public void setRcnExedate(final Date rcnExedate) {
        this.rcnExedate = rcnExedate;
    }

    /**
     * @return the ccnFlag
     */
    public String getCcnFlag() {
        return ccnFlag;
    }

    /**
     * @param ccnFlag the ccnFlag to set
     */
    public void setCcnFlag(final String ccnFlag) {
        this.ccnFlag = ccnFlag;
    }

    /**
     * @return the discAppdate
     */
    public Date getDiscAppdate() {
        return discAppdate;
    }

    /**
     * @param discAppdate the discAppdate to set
     */
    public void setDiscAppdate(final Date discAppdate) {
        this.discAppdate = discAppdate;
    }

    /**
     * @return the discReason
     */
    public String getDiscReason() {
        return discReason;
    }

    /**
     * @param discReason the discReason to set
     */
    public void setDiscReason(final String discReason) {
        this.discReason = discReason;
    }

    /**
     * @return the discType
     */
    public Long getDiscType() {
        return discType;
    }

    /**
     * @param discType the discType to set
     */
    public void setDiscType(final Long discType) {
        this.discType = discType;
    }

    /**
     * @return the discMethod
     */
    public Long getDiscMethod() {
        return discMethod;
    }

    /**
     * @param discMethod the discMethod to set
     */
    public void setDiscMethod(final Long discMethod) {
        this.discMethod = discMethod;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
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
        return new String[] { "WT", "TB_WT_RECONNECTION", "RCN_IDN" };
    }
}
