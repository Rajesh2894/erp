package com.abm.mainet.legal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_LGL_JUDGE_DET_HIST")
public class JudgeDetailMasterHistory {
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "JUDGE_DET_ID_H", nullable = false)
    private Long histDetId;

    @Column(name = "JUDGE_DET_ID")
    private Long id;

    @Column(name = "FROM_PERIOD")
    private Date fromPeriod;

    @Column(name = "TO_PERIOD")
    private Date toPeriod;

    @Column(name = "JUDGE_STATUS")
    private String judgeStatus;

    @Column(name = "H_STATUS")
    private String status;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "JUDGE_ID")
    private Long judgeId;

    @Column(name = "CRT_ID")
    private Long crtId;

    public Long getHistDetId() {
        return histDetId;
    }

    public void setHistDetId(Long histDetId) {
        this.histDetId = histDetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(Date fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public Date getToPeriod() {
        return toPeriod;
    }

    public void setToPeriod(Date toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(String judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Long getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Long judgeId) {
        this.judgeId = judgeId;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_JUDGE_DET_HIST", "JUDGE_DET_ID_H" };
    }
}
