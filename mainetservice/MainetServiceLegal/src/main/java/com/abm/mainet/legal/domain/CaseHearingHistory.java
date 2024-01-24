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
 * The persistent class for the tb_lgl_hearing_hist database table.
 * 
 * @author Lalit.Prusti
 */
@Entity
@Table(name = "tb_lgl_hearing_hist")
public class CaseHearingHistory implements Serializable {

    private static final long serialVersionUID = 7454326504729182432L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HR_ID_H", unique = true, nullable = false)
    private Long hrIdH;

    @Column(name = "ADV_ID")
    private Long advId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CSE_ID")
    private Long cseId;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    @Column(name = "HR_ATTENDEE", length = 50)
    private String hrAttendee;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "HR_DATE")
    private Date hrDate;

    @Column(name = "HR_ID")
    private Long hrId;

    @Column(name = "HR_PREPARATION", length = 45)
    private String hrPreparation;

    @Column(name = "HR_PROCEEDING", length = 50)
    private String hrProceeding;

    @Column(name = "HR_RATING")
    private Long hrRating;

    @Column(name = "HR_STATUS", length = 1)
    private Long hrStatus;

    @Column(name = "JUDGE_ID", length = 45)
    private String judgeId;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public CaseHearingHistory() {
    }

    public Long getHrIdH() {
        return this.hrIdH;
    }

    public void setHrIdH(Long hrIdH) {
        this.hrIdH = hrIdH;
    }

    public Long getAdvId() {
        return this.advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCseId() {
        return this.cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getHrAttendee() {
        return this.hrAttendee;
    }

    public void setHrAttendee(String hrAttendee) {
        this.hrAttendee = hrAttendee;
    }

    public Date getHrDate() {
        return this.hrDate;
    }

    public void setHrDate(Date hrDate) {
        this.hrDate = hrDate;
    }

    public Long getHrId() {
        return this.hrId;
    }

    public void setHrId(Long hrId) {
        this.hrId = hrId;
    }

    public String getHrPreparation() {
        return this.hrPreparation;
    }

    public void setHrPreparation(String hrPreparation) {
        this.hrPreparation = hrPreparation;
    }

    public String getHrProceeding() {
        return this.hrProceeding;
    }

    public void setHrProceeding(String hrProceeding) {
        this.hrProceeding = hrProceeding;
    }

    public Long getHrRating() {
        return this.hrRating;
    }

    public void setHrRating(Long hrRating) {
        this.hrRating = hrRating;
    }

    public Long getHrStatus() {
        return this.hrStatus;
    }

    public void setHrStatus(Long hrStatus) {
        this.hrStatus = hrStatus;
    }

    public String getJudgeId() {
        return this.judgeId;
    }

    public void setJudgeId(String judgeId) {
        this.judgeId = judgeId;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_HEARING_HIST", "HR_ID_H" };
    }
}