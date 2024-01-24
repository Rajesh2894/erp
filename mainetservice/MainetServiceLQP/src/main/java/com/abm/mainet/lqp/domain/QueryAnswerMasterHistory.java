package com.abm.mainet.lqp.domain;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "TB_LQP_QUERY_ANSWER_HIST")
public class QueryAnswerMasterHistory implements Serializable {

    private static final long serialVersionUID = 1749141106434055724L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ANSWER_REG_ID_H")
    private Long answerRegHId;

    @Column(name = "ANSWER_REG_ID", nullable = false)
    private Long answerRegId;

    @Column(name = "ANSWER_DATE", nullable = false)
    private Date answerDate;

    @Column(name = "ANSWER", length = 3000, nullable = false)
    private String answer;

    @Column(name = "REMARK", length = 100)
    private String remark;

    @Column(name = "H_STATUS", length = 15, nullable = false)
    private String hStatus;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_REG_ID", nullable = false)
    private QueryRegistrationMaster queryRegistrationMaster;

    public Long getAnswerRegHId() {
        return answerRegHId;
    }

    public void setAnswerRegHId(Long answerRegHId) {
        this.answerRegHId = answerRegHId;
    }

    public Long getAnswerRegId() {
        return answerRegId;
    }

    public void setAnswerRegId(Long answerRegId) {
        this.answerRegId = answerRegId;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public QueryRegistrationMaster getQueryRegistrationMaster() {
        return queryRegistrationMaster;
    }

    public void setQueryRegistrationMaster(QueryRegistrationMaster queryRegistrationMaster) {
        this.queryRegistrationMaster = queryRegistrationMaster;
    }

    public static String[] getPkValues() {
        return new String[] { "LQP", "TB_LQP_QUERY_ANSWER_HIST", "ANSWER_REG_ID_H" };
    }

}
