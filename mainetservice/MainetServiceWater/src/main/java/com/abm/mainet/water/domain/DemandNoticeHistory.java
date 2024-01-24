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
 * @author Lalit.Prusti
 * @since 14 Jul 2016
 */
@Entity
@Table(name = "TB_WT_DEMAND_NOTICE_HIST")
public class DemandNoticeHistory implements Serializable {
    private static final long serialVersionUID = 2772614856391411945L;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "H_NOTICEID", precision = 12, scale = 0, nullable = false)
    private long hNoticeid;

    @Column(name = "NB_NOTICEID", precision = 12, scale = 0, nullable = true)
    private Long nbNoticeid;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    private Long csIdn;

    @Column(name = "BM_IDNO", precision = 12, scale = 0, nullable = true)
    private Long bmIdno;

    @Column(name = "CPD_NOTTYPE", precision = 12, scale = 0, nullable = true)
    private Long cpdNottype;

    @Column(name = "NB_NOTICENO", precision = 10, scale = 0, nullable = true)
    private Long nbNoticeno;

    @Column(name = "NB_NOTICEDT", nullable = true)
    private Date nbNoticedt;

    @Column(name = "NB_NOTACCEPTDT", nullable = true)
    private Date nbNotacceptdt;

    @Column(name = "NB_NOTDUEDT", nullable = true)
    private Date nbNotduedt;

    @Column(name = "NB_TOTCOPY", precision = 3, scale = 0, nullable = true)
    private Long nbTotcopy;

    @Column(name = "NB_PRINTSTS", length = 1, nullable = true)
    private String nbPrintsts;

    @Column(name = "TAX_CODE", length = 20, nullable = true)
    private String taxCode;

    @Column(name = "ORGID", precision = 8, scale = 0, nullable = true)
    private Long orgid;

    @Column(name = "USER_ID", precision = 12, scale = 0, nullable = true)
    private Long userId;

    @Column(name = "LANG_ID", precision = 8, scale = 0, nullable = true)
    private Long langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmoddate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
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

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String isDeleted;

    @Column(name = "TAX_AMOUNT")
    // comments : Apllicable Tax Amount
    private double taxAmount;

    @Column(name = "H_STATUS", length = 1, nullable = true)
    private String hStatus;

    public long gethNoticeid() {
        return hNoticeid;
    }

    public void sethNoticeid(final long hNoticeid) {
        this.hNoticeid = hNoticeid;
    }

    public Long getNbNoticeid() {
        return nbNoticeid;
    }

    public void setNbNoticeid(final Long nbNoticeid) {
        this.nbNoticeid = nbNoticeid;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(final Long bmIdno) {
        this.bmIdno = bmIdno;
    }

    public Long getCpdNottype() {
        return cpdNottype;
    }

    public void setCpdNottype(final Long cpdNottype) {
        this.cpdNottype = cpdNottype;
    }

    public Long getNbNoticeno() {
        return nbNoticeno;
    }

    public void setNbNoticeno(final Long nbNoticeno) {
        this.nbNoticeno = nbNoticeno;
    }

    public Date getNbNoticedt() {
        return nbNoticedt;
    }

    public void setNbNoticedt(final Date nbNoticedt) {
        this.nbNoticedt = nbNoticedt;
    }

    public Date getNbNotacceptdt() {
        return nbNotacceptdt;
    }

    public void setNbNotacceptdt(final Date nbNotacceptdt) {
        this.nbNotacceptdt = nbNotacceptdt;
    }

    public Date getNbNotduedt() {
        return nbNotduedt;
    }

    public void setNbNotduedt(final Date nbNotduedt) {
        this.nbNotduedt = nbNotduedt;
    }

    public Long getNbTotcopy() {
        return nbTotcopy;
    }

    public void setNbTotcopy(final Long nbTotcopy) {
        this.nbTotcopy = nbTotcopy;
    }

    public String getNbPrintsts() {
        return nbPrintsts;
    }

    public void setNbPrintsts(final String nbPrintsts) {
        this.nbPrintsts = nbPrintsts;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(final String taxCode) {
        this.taxCode = taxCode;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(final String hStatus) {
        this.hStatus = hStatus;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(final double taxAmount) {
        this.taxAmount = taxAmount;
    }

}