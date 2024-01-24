package com.abm.mainet.council.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author aarti.paan
 * @since 5th July 2019
 *
 */
@Entity
@Table(name = "tb_cmt_council_meeting_mom_hist")
public class CouncilMeetingMomHistoryEntity implements Serializable {

    private static final long serialVersionUID = 4159686615908612569L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MEETING_MOM_ID_H")
    private Long momId_H;

    @Column(name = "MEETING_MOM_ID")
    private Long momId;

    @Column(name = "MEETING_ID")
    private Long meetingId;

    @Column(name = "PROPOSAL_ID")
    private Long proposalId;

    @Column(name = "MOM_RESOLUTION_COMMENTS")
    private String momResolutionComments;

    @Column(name = "MOM_STATUS")
    private String momStatus;

    @Column(name = "H_STATUS")
    private String historyStatus;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public Long getMomId_H() {
        return momId_H;
    }

    public void setMomId_H(Long momId_H) {
        this.momId_H = momId_H;
    }

    public Long getMomId() {
        return momId;
    }

    public void setMomId(Long momId) {
        this.momId = momId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getMomResolutionComments() {
        return momResolutionComments;
    }

    public void setMomResolutionComments(String momResolutionComments) {
        this.momResolutionComments = momResolutionComments;
    }

    public String getMomStatus() {
        return momStatus;
    }

    public void setMomStatus(String momStatus) {
        this.momStatus = momStatus;
    }

    public String getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(String historyStatus) {
        this.historyStatus = historyStatus;
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

    public static String[] getPkValues() {
        return new String[] { "CMT", "tb_cmt_council_meeting_mom_hist", "MEETING_MOM_ID_H" };
    }
}
