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
 * @author Rahul.Yadav
 * @since 17 Jan 2017
 */
@Entity
@Table(name = "TB_WT_EXCESS_AMT_HIST")
public class TbWtExcessAmtHist implements Serializable {
    private static final long serialVersionUID = 1335344109627554748L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_EXCESS_ID", precision = 12, scale = 0, nullable = false)
    private long hExcessId;

    @Column(name = "EXCESS_ID", precision = 12, scale = 0, nullable = false)
    private Long excessId;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    private Long csIdn;

    @Column(name = "RM_RCPTID", precision = 12, scale = 0, nullable = true)
    private Long rmRcptid;

    @Column(name = "EXC_AMT", precision = 12, scale = 2, nullable = true)
    private Double excAmt;

    @Column(name = "ADJ_AMT", precision = 12, scale = 2, nullable = true)
    private Double adjAmt;

    @Column(name = "EXCADJ_FLAG", length = 1, nullable = true)
    private String excadjFlag;

    @Column(name = "EXCESS_DIS", length = 1, nullable = true)
    private String excessDis;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
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

    @Column(name = "WT_V1", length = 100, nullable = true)
    private String wtV1;

    @Column(name = "WT_N1", precision = 15, scale = 0, nullable = true)
    private Long wtN1;

    @Column(name = "WT_D1", nullable = true)
    private Date wtD1;

    @Column(name = "WT_LO1", length = 1, nullable = true)
    private String wtLo1;

    @Column(name = "TAX_ID", precision = 12, scale = 0, nullable = true)
    private Long taxId;

    @Column(name = "H_STATUS", length = 1, nullable = true)
    private String hStatus;
    
    @Column(name = "EXCESS_ACTIVE")
    private String excessActive;

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_EXCESS_AMT_HIST", "H_EXCESS_ID" };
    }

    public long gethExcessId() {
        return hExcessId;
    }

    public void sethExcessId(final long hExcessId) {
        this.hExcessId = hExcessId;
    }

    public Long getExcessId() {
        return excessId;
    }

    public void setExcessId(final Long excessId) {
        this.excessId = excessId;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(final Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Double getExcAmt() {
        return excAmt;
    }

    public void setExcAmt(final Double excAmt) {
        this.excAmt = excAmt;
    }

    public Double getAdjAmt() {
        return adjAmt;
    }

    public void setAdjAmt(final Double adjAmt) {
        this.adjAmt = adjAmt;
    }

    public String getExcadjFlag() {
        return excadjFlag;
    }

    public void setExcadjFlag(final String excadjFlag) {
        this.excadjFlag = excadjFlag;
    }

    public String getExcessDis() {
        return excessDis;
    }

    public void setExcessDis(final String excessDis) {
        this.excessDis = excessDis;
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

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
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

    public String getWtV1() {
        return wtV1;
    }

    public void setWtV1(final String wtV1) {
        this.wtV1 = wtV1;
    }

    public Long getWtN1() {
        return wtN1;
    }

    public void setWtN1(final Long wtN1) {
        this.wtN1 = wtN1;
    }

    public Date getWtD1() {
        return wtD1;
    }

    public void setWtD1(final Date wtD1) {
        this.wtD1 = wtD1;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(final String hStatus) {
        this.hStatus = hStatus;
    }

	public String getExcessActive() {
		return excessActive;
	}

	public void setExcessActive(String excessActive) {
		this.excessActive = excessActive;
	}

}