package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_lgl_caseparawise_remark_hist database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_CASEPARAWISE_REMARK_HIST")
public class ParawiseRemarkHistory implements Serializable {

    private static final long serialVersionUID = 8313155333366881213L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PAR_ID_H")
    private Long parIdH;

    @Column(name = "CSE_ID")
    private Long caseId;

    @Column(name = "ATD_ID")
    private Long atdId;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "PAR_COMMENT")
    private String parComment;

    @Column(name = "PAR_ID")
    private Long parId;

    @Column(name = "PAR_PAGNO")
    private String parPagno;

    @Column(name = "PAR_SECTIONNO")
    private String parSectionno;

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

    public Long getParIdH() {
        return parIdH;
    }

    public void setParIdH(Long parIdH) {
        this.parIdH = parIdH;
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

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public String getParComment() {
        return parComment;
    }

    public void setParComment(String parComment) {
        this.parComment = parComment;
    }

    public Long getParId() {
        return parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }

    public String getParPagno() {
        return parPagno;
    }

    public void setParPagno(String parPagno) {
        this.parPagno = parPagno;
    }

    public String getParSectionno() {
        return parSectionno;
    }

    public void setParSectionno(String parSectionno) {
        this.parSectionno = parSectionno;
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

    public Long getAtdId() {
        return atdId;
    }

    public void setAtdId(Long atdId) {
        this.atdId = atdId;
    }

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_CASEPARAWISE_REMARK_HIST", "PAR_ID_H" };
    }
}
