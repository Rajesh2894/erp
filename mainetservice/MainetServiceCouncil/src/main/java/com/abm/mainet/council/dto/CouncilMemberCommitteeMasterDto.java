package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author aarti.paan
 * @since 25 April 2019
 */
public class CouncilMemberCommitteeMasterDto implements Serializable {

    private static final long serialVersionUID = -2512212586731021248L;

    private Long memberCommmitteeId;

    private Long committeeTypeId;

    private String committeeType;

    private Long memberId;

    private Date fromDate;

    private Date toDate;

    private Date dissolveDate;

    private String memberName;

    private String elecWardDesc;

    private String designation;

    private String partyAFFDesc;

    private String qualificationDesc;

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private int attendanceStatus; // in database store like 1/0

    private String status;

    private String couMemberTypeDesc;

    private Long couMemberType;

    private String memberAddress;

    private String otherField;// Committee type member count

    private Long comDsgId;

    private String comDsgDesc;

    private String seqCDS;// committee designation seq

    private Date expiryDate; // member expiry date

    private String expiryReason; // member expiry reason

    private String memberStatus; // member status against committee
    
    private String elecZoneDesc;
    
    private String attendStatus;
    
    private Long id;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCommitteeType() {
        return committeeType;
    }

    public void setCommitteeType(String committeeType) {
        this.committeeType = committeeType;
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

    public Date getDissolveDate() {
        return dissolveDate;
    }

    public void setDissolveDate(Date dissolveDate) {
        this.dissolveDate = dissolveDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getElecWardDesc() {
        return elecWardDesc;
    }

    public void setElecWardDesc(String elecWardDesc) {
        this.elecWardDesc = elecWardDesc;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPartyAFFDesc() {
        return partyAFFDesc;
    }

    public void setPartyAFFDesc(String partyAFFDesc) {
        this.partyAFFDesc = partyAFFDesc;
    }

    public String getQualificationDesc() {
        return qualificationDesc;
    }

    public void setQualificationDesc(String qualificationDesc) {
        this.qualificationDesc = qualificationDesc;
    }

    public int getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(int attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCouMemberTypeDesc() {
        return couMemberTypeDesc;
    }

    public void setCouMemberTypeDesc(String couMemberTypeDesc) {
        this.couMemberTypeDesc = couMemberTypeDesc;
    }

    public Long getCouMemberType() {
        return couMemberType;
    }

    public void setCouMemberType(Long couMemberType) {
        this.couMemberType = couMemberType;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getOtherField() {
        return otherField;
    }

    public void setOtherField(String otherField) {
        this.otherField = otherField;
    }

    public Long getComDsgId() {
        return comDsgId;
    }

    public void setComDsgId(Long comDsgId) {
        this.comDsgId = comDsgId;
    }

    public String getComDsgDesc() {
        return comDsgDesc;
    }

    public void setComDsgDesc(String comDsgDesc) {
        this.comDsgDesc = comDsgDesc;
    }

    public String getSeqCDS() {
        return seqCDS;
    }

    public void setSeqCDS(String seqCDS) {
        this.seqCDS = seqCDS;
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

	public String getElecZoneDesc() {
		return elecZoneDesc;
	}

	public void setElecZoneDesc(String elecZoneDesc) {
		this.elecZoneDesc = elecZoneDesc;
	}

	public String getAttendStatus() {
		return attendStatus;
	}

	public void setAttendStatus(String attendStatus) {
		this.attendStatus = attendStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    

}
