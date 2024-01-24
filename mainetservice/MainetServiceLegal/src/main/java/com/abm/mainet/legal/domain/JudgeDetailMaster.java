package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_LGL_JUDGE_DET")
public class JudgeDetailMaster implements Serializable {

    private static final long serialVersionUID = 2627533837487344890L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "JUDGE_DET_ID", nullable = false)
    private Long id;

    @Column(name = "FROM_PERIOD", nullable = true)
    private Date fromPeriod;

    @Column(name = "TO_PERIOD", nullable = true)
    private Date toPeriod;

    @Column(name = "JUDGE_STATUS")
    private String judgeStatus;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "JUDGE_ID", nullable = false)
    private JudgeMaster judge;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CRT_ID", nullable = false)
    private CourtMaster court;

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

    public JudgeMaster getJudge() {
        return judge;
    }

    public void setJudge(JudgeMaster judge) {
        this.judge = judge;
    }

    public CourtMaster getCourt() {
        return court;
    }

    public void setCourt(CourtMaster court) {
        this.court = court;
    }

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_JUDGE_DET", "JUDGE_DET_ID" };
    }
}
