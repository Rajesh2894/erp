package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author aarti.paan
 * @since 8th April 2019
 *
 */
public class CouncilMemberMasterDto implements Serializable {

    private static final long serialVersionUID = 1512272134979557480L;

    private Long couId;

    private Long couElectionId;

    private Long couDesgId;

    private String couEduId;

    private Long couCastId;

    private String couMemName;

    private Long couGen;

    private Long couMobNo;

    private Date couDOB;

    private String couEmail;

    private String couAddress;

    private Date couElecDate;

    private Date couOathDate;

    private Long couPartyAffilation;

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String couDesgName;

    private String couPartyAffDesc;

    private Long couEleWZId1;

    private Long couEleWZId2;

    private Long couEleWZId3;

    private Long couEleWZId4;

    private Long couEleWZId5;

    private String couEleWZ1Desc;

    private String couEleWZ2Desc;

    private String couEleWZ3Desc;

    private String couEleWZ4Desc;

    private String couEleWZ5Desc;

    private Long couMemberType;

    private String couMemberTypeDesc;

    // used to get MET prefix other value
    private String otherField;

    // Used in case of other party
    private String couPartyName;

    public Long getCouId() {
        return couId;
    }

    public void setCouId(Long couId) {
        this.couId = couId;
    }

    public Long getCouElectionId() {
        return couElectionId;
    }

    public void setCouElectionId(Long couElectionId) {
        this.couElectionId = couElectionId;
    }

    public Long getCouDesgId() {
        return couDesgId;
    }

    public void setCouDesgId(Long couDesgId) {
        this.couDesgId = couDesgId;
    }

    public String getCouEduId() {
        return couEduId;
    }

    public void setCouEduId(String couEduId) {
        this.couEduId = couEduId;
    }

    public Long getCouCastId() {
        return couCastId;
    }

    public void setCouCastId(Long couCastId) {
        this.couCastId = couCastId;
    }

    public String getCouMemName() {
        return couMemName;
    }

    public void setCouMemName(String couMemName) {
        this.couMemName = couMemName;
    }

    public Long getCouGen() {
        return couGen;
    }

    public void setCouGen(Long couGen) {
        this.couGen = couGen;
    }

    public Long getCouMobNo() {
        return couMobNo;
    }

    public void setCouMobNo(Long couMobNo) {
        this.couMobNo = couMobNo;
    }

    public Date getCouDOB() {
        return couDOB;
    }

    public void setCouDOB(Date couDOB) {
        this.couDOB = couDOB;
    }

    public String getCouEmail() {
        return couEmail;
    }

    public void setCouEmail(String couEmail) {
        this.couEmail = couEmail;
    }

    public String getCouAddress() {
        return couAddress;
    }

    public void setCouAddress(String couAddress) {
        this.couAddress = couAddress;
    }

    public Date getCouElecDate() {
        return couElecDate;
    }

    public void setCouElecDate(Date couElecDate) {
        this.couElecDate = couElecDate;
    }

    public Date getCouOathDate() {
        return couOathDate;
    }

    public void setCouOathDate(Date couOathDate) {
        this.couOathDate = couOathDate;
    }

    public Long getCouPartyAffilation() {
        return couPartyAffilation;
    }

    public void setCouPartyAffilation(Long couPartyAffilation) {
        this.couPartyAffilation = couPartyAffilation;
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

    public String getCouDesgName() {
        return couDesgName;
    }

    public void setCouDesgName(String couDesgName) {
        this.couDesgName = couDesgName;
    }

    public String getCouPartyAffDesc() {
        return couPartyAffDesc;
    }

    public void setCouPartyAffDesc(String couPartyAffDesc) {
        this.couPartyAffDesc = couPartyAffDesc;
    }

    public Long getCouEleWZId1() {
        return couEleWZId1;
    }

    public void setCouEleWZId1(Long couEleWZId1) {
        this.couEleWZId1 = couEleWZId1;
    }

    public Long getCouEleWZId2() {
        return couEleWZId2;
    }

    public void setCouEleWZId2(Long couEleWZId2) {
        this.couEleWZId2 = couEleWZId2;
    }

    public Long getCouEleWZId3() {
        return couEleWZId3;
    }

    public void setCouEleWZId3(Long couEleWZId3) {
        this.couEleWZId3 = couEleWZId3;
    }

    public Long getCouEleWZId4() {
        return couEleWZId4;
    }

    public void setCouEleWZId4(Long couEleWZId4) {
        this.couEleWZId4 = couEleWZId4;
    }

    public Long getCouEleWZId5() {
        return couEleWZId5;
    }

    public void setCouEleWZId5(Long couEleWZId5) {
        this.couEleWZId5 = couEleWZId5;
    }

    public String getCouEleWZ1Desc() {
        return couEleWZ1Desc;
    }

    public void setCouEleWZ1Desc(String couEleWZ1Desc) {
        this.couEleWZ1Desc = couEleWZ1Desc;
    }

    public String getCouEleWZ2Desc() {
        return couEleWZ2Desc;
    }

    public void setCouEleWZ2Desc(String couEleWZ2Desc) {
        this.couEleWZ2Desc = couEleWZ2Desc;
    }

    public String getCouEleWZ3Desc() {
        return couEleWZ3Desc;
    }

    public void setCouEleWZ3Desc(String couEleWZ3Desc) {
        this.couEleWZ3Desc = couEleWZ3Desc;
    }

    public String getCouEleWZ4Desc() {
        return couEleWZ4Desc;
    }

    public void setCouEleWZ4Desc(String couEleWZ4Desc) {
        this.couEleWZ4Desc = couEleWZ4Desc;
    }

    public String getCouEleWZ5Desc() {
        return couEleWZ5Desc;
    }

    public void setCouEleWZ5Desc(String couEleWZ5Desc) {
        this.couEleWZ5Desc = couEleWZ5Desc;
    }

    public Long getCouMemberType() {
        return couMemberType;
    }

    public void setCouMemberType(Long couMemberType) {
        this.couMemberType = couMemberType;
    }

    public String getCouMemberTypeDesc() {
        return couMemberTypeDesc;
    }

    public void setCouMemberTypeDesc(String couMemberTypeDesc) {
        this.couMemberTypeDesc = couMemberTypeDesc;
    }

    public String getCouPartyName() {
        return couPartyName;
    }

    public void setCouPartyName(String couPartyName) {
        this.couPartyName = couPartyName;
    }

    public String getOtherField() {
        return otherField;
    }

    public void setOtherField(String otherField) {
        this.otherField = otherField;
    }

}
