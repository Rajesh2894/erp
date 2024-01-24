package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_LGL_JUDGEMENT_MAST")
public class JudgementMaster implements Serializable {

    private static final long serialVersionUID = -1342690596927154096L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "JUD_ID")
    private Long judId;

    /*
     * @Column(name = "CSE_ID", nullable = false) private Long cseId;
     */

    @ManyToOne
    @JoinColumn(name = "CSE_ID", nullable = false, referencedColumnName = "CSE_ID")
    private CaseEntry caseEntry;

    @Column(name = "CRT_ID", nullable = false)
    private Long crtId;

    @Temporal(TemporalType.DATE)
    @Column(name = "JUD_CASE_DATE", nullable = false)
    private Date judCaseDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "JUD_DATE", nullable = false)
    private Date judDate;

    @Column(name = "JUD_SUMMARY_DETAIL", length = 1000, nullable = false)
    private String judSummaryDetail;

    @Column(name = "JUD_BENCH_NAME", length = 500, nullable = false)
    private String judBenchName;

    @Column(name = "JUDGEMENT_STATUS", length = 1)
    private String judgementStatus; // status store Like A/D (ACTIVE/DEACTIVE)

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true, updatable = true)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getJudId() {
        return judId;
    }

    public void setJudId(Long judId) {
        this.judId = judId;
    }

    public CaseEntry getCaseEntry() {
        return caseEntry;
    }

    public void setCaseEntry(CaseEntry caseEntry) {
        this.caseEntry = caseEntry;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public Date getJudCaseDate() {
        return judCaseDate;
    }

    public void setJudCaseDate(Date judCaseDate) {
        this.judCaseDate = judCaseDate;
    }

    public Date getJudDate() {
        return judDate;
    }

    public void setJudDate(Date judDate) {
        this.judDate = judDate;
    }

    public String getJudSummaryDetail() {
        return judSummaryDetail;
    }

    public void setJudSummaryDetail(String judSummaryDetail) {
        this.judSummaryDetail = judSummaryDetail;
    }

    public String getJudBenchName() {
        return judBenchName;
    }

    public void setJudBenchName(String judBenchName) {
        this.judBenchName = judBenchName;
    }

    public String getJudgementStatus() {
        return judgementStatus;
    }

    public void setJudgementStatus(String judgementStatus) {
        this.judgementStatus = judgementStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
        return new String[] { "LGL", "TB_LGL_JUDGEMENT_MAST", "JUD_ID" };
    }

}
