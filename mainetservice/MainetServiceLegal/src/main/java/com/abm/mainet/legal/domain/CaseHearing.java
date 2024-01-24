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
 * The persistent class for the tb_lgl_hearing database table.
 * 
 * @author Lalit.Prusti
 */

@Entity
@Table(name = "TB_LGL_HEARING")
public class CaseHearing implements Serializable {

    private static final long serialVersionUID = 7551885492350081079L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HR_ID", unique = true, nullable = false)
    private Long hrId;

    @Column(name = "CSE_ID", nullable = false)
    private Long cseId;

    @Column(name = "ADV_ID")
    private Long advId;

    @Column(name = "JUDGE_ID", length = 45)
    private String judgeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "HR_DATE")
    private Date hrDate;

    @Column(name = "HR_ATTENDEE", length = 50)
    private String hrAttendee;

    @Column(name = "HR_PREPARATION", length = 45)
    private String hrPreparation;

    @Column(name = "HR_PROCEEDING")
    private String hrProceeding;

    @Column(name = "HR_RATING")
    private Long hrRating;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "HR_STATUS", length = 1)
    private Long hrStatus;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public CaseHearing() {
    }

    public Long getHrId() {
        return this.hrId;
    }

    public void setHrId(Long hrId) {
        this.hrId = hrId;
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
        return new String[] { "LGL", "TB_LGL_HEARING", "HR_ID" };
    }
}