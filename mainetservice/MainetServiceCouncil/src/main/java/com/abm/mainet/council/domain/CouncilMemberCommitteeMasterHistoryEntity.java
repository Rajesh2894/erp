package com.abm.mainet.council.domain;

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
 * 
 * @author aarti.paan
 * @since 5 July 2019
 */
@Entity
@Table(name = "tb_cmt_council_member_committee_hist")

public class CouncilMemberCommitteeMasterHistoryEntity implements Serializable {

    private static final long serialVersionUID = -8999174386182101719L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MEMBER_COMMITTEE_ID_H")
    private Long memberCommmitteeHisId;

    @Column(name = "MEMBER_COMMITTEE_ID")
    private Long memberCommmitteeId;

    @Column(name = "COMMITTEE_TYPE_ID")
    private Long committeeTypeId;

    @Column(name = "COU_ID")
    private Long members;

    @Column(name = "COMMITTEE_DSG_ID")
    private Long comDsgId;

    @Temporal(TemporalType.DATE)
    @Column(name = "DISSOLVE_DATE")
    private Date dissolveDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE")
    private Date toDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "H_STATUS")
    private String historyStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "Expiry_Date")
    private Date expiryDate;

    @Column(name = "Expiry_Reason")
    private String expiryReason;

    @Column(name = "Member_Status")
    private String memberStatus;

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

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public Long getMemberCommmitteeHisId() {
        return memberCommmitteeHisId;
    }

    public void setMemberCommmitteeHisId(Long memberCommmitteeHisId) {
        this.memberCommmitteeHisId = memberCommmitteeHisId;
    }

    public Long getMemberCommmitteeId() {
        return memberCommmitteeId;
    }

    public void setMemberCommmitteeId(Long memberCommmitteeId) {
        this.memberCommmitteeId = memberCommmitteeId;
    }

    public Long getCommitteeTypeId() {
        return committeeTypeId;
    }

    public void setCommitteeTypeId(Long committeeTypeId) {
        this.committeeTypeId = committeeTypeId;
    }

    public Long getMembers() {
        return members;
    }

    public void setMembers(Long members) {
        this.members = members;
    }

    public Date getDissolveDate() {
        return dissolveDate;
    }

    public void setDissolveDate(Date dissolveDate) {
        this.dissolveDate = dissolveDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getComDsgId() {
        return comDsgId;
    }

    public void setComDsgId(Long comDsgId) {
        this.comDsgId = comDsgId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryReason() {
        return expiryReason;
    }

    public void setExpiryReason(String expiryReason) {
        this.expiryReason = expiryReason;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public static String[] getPkValues() {
        return new String[] { "CMT", "tb_cmt_council_member_committee_hist", "MEMBER_COMMITTEE_ID_H" };
    }
}
