package com.abm.mainet.council.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author aarti.paan
 * @since 5 April 2019
 */
@Entity
@Table(name = "TB_CMT_COUNCIL_MEMBER_COMMITTEE")
public class CouncilMemberCommitteeMasterEntity implements Serializable {

    private static final long serialVersionUID = 8618887108999378685L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MEMBER_COMMITTEE_ID")
    private Long memberCommmitteeId;

    @Column(name = "COMMITTEE_TYPE_ID")
    private Long committeeTypeId;

    @Column(name = "COMMITTEE_DSG_ID", nullable = false)
    private Long comDsgId;

    @OneToOne
    @JoinColumn(name = "COU_ID", referencedColumnName = "COU_ID")
    private CouncilMemberMasterEntity members;

    @Temporal(TemporalType.DATE)
    @Column(name = "DISSOLVE_DATE", nullable = false)
    private Date dissolveDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE", nullable = false)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE", nullable = false)
    private Date toDate;

    // used only report purpose in report display all data not status wise and in anywhere else use filter status
    @Column(name = "STATUS", nullable = false)
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "Expiry_Date")
    private Date expiryDate;

    @Column(name = "Expiry_Reason")
    private String expiryReason;

    // used to maintain member membership against committee
    @Column(name = "Member_Status")
    private String memberStatus;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

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

    public CouncilMemberMasterEntity getMembers() {
        return members;
    }

    public void setMembers(CouncilMemberMasterEntity members) {
        this.members = members;
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
        return new String[] { "CMT", "TB_CMT_COUNCIL_MEMBER_COMMITTEE", "MEMBER_COMMITTEE_ID" };
    }

}
