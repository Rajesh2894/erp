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
 * @since 28 Jun 2016
 */
@Entity
@Table(name = "TB_WT_DEMAND_NOTICE")
public class DemandNotice implements Serializable {
    private static final long serialVersionUID = -1126909121529961157L;

    @Id
    @GenericGenerator(name = "CustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "CustomGenerator")
    @Column(name = "NB_NOTICEID", precision = 12, scale = 0, nullable = false)
    // comments : Notice ID
    private long nbNoticeid;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    // comments : Connection Number foreign key
    private Long csIdn;

    @Column(name = "BM_IDNO", precision = 12, scale = 0, nullable = false)
    // comments : Bill Id no.
    private Long bmIdno;

    @Column(name = "CPD_NOTTYPE", precision = 12, scale = 0, nullable = false)
    // comments : Notice type id
    private Long cpdNottype;

    @Column(name = "NB_NOTICENO", precision = 10, scale = 0, nullable = false)
    // comments : Notice number
    private Long nbNoticeno;

    @Column(name = "NB_NOTICEDT", nullable = false)
    // comments : Notice date
    private Date nbNoticedt;

    @Column(name = "NB_NOTACCEPTDT", nullable = true)
    // comments : Notice sent date
    private Date nbNotacceptdt;

    @Column(name = "NB_NOTDUEDT", nullable = true)
    // comments : Notice accept date
    private Date nbNotduedt;

    @Column(name = "NB_TOTCOPY", precision = 3, scale = 0, nullable = true)
    // comments : No. of copies
    private Long nbTotcopy;

    @Column(name = "NB_PRINTSTS", length = 1, nullable = true)
    // comments : Print Status
    private String nbPrintsts;

    @Column(name = "ORGID", precision = 8, scale = 0, nullable = false)
    // comments : Org ID
    private Long orgid;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
    // comments : User ID
    private Long userId;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Last Modification Date
    private Date lmoddate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
    // comments : User id who update the data
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Date on which data is going to update
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine�s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine�s Login Name | IP Address | Physical
    // Address
    private String lgIpMacUpd;

    @Column(name = "TAX_CODE", length = 20, nullable = true)
    // comments : Apllicable Tax Code from Tax Master
    private String taxCode;

    @Column(name = "TAX_AMOUNT")
    // comments : Apllicable Tax Amount
    private double taxAmount;

    @Column(name = "NB_ACTIVE", length = 1, nullable = true)
    // comments : Flag for Deleted Entries.
    private String isDeleted;

    public long getNbNoticeid() {
        return nbNoticeid;
    }

    public void setNbNoticeid(final long nbNoticeid) {
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

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(final double taxAmount) {
        this.taxAmount = taxAmount;
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

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(final String taxCode) {
        this.taxCode = taxCode;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String[] getPkValues() {

        return new String[] { "WT", "TB_WT_DEMAND_NOTICE", "NB_NOTICEID" };
    }
}