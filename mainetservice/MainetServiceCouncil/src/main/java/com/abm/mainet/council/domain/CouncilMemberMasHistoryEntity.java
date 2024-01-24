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
 * @author aarti.paan
 * @since 25 April 2019
 */
@Entity
@Table(name = "TB_CMT_COUNCIL_MEM_MAST_HIST")
public class CouncilMemberMasHistoryEntity implements Serializable {

    private static final long serialVersionUID = -9056172263891724094L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "COU_HIST_ID")
    private Long couHistoryId;

    @Column(name = "COU_ID")
    private Long couId;

    @Column(name = "COU_COD_ID1", nullable = false)
    private Long couEleWZId1;

    @Column(name = "COU_COD_ID2", nullable = false)
    private Long couEleWZId2;

    @Column(name = "COU_COD_ID3", nullable = false)
    private Long couEleWZId3;

    @Column(name = "COU_COD_ID4", nullable = false)
    private Long couEleWZId4;

    @Column(name = "COU_COD_ID5", nullable = false)
    private Long couEleWZId5;

    @Column(name = "COU_DESG_ID")
    private Long couMemberType;

    @Column(name = "COU_EDU_ID")
    private String couEduId;

    @Column(name = "COU_CAST_ID")
    private Long couCastId;

    @Column(name = "COU_MEM_NAME", length = 250)
    private String couMemName;

    @Column(name = "COU_GEN")
    private Long couGen;

    @Column(name = "COU_MOBNO")
    private Long couMobNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "COU_DOB")
    private Date couDOB;

    @Column(name = "COU_EMAIL", length = 250)
    private String couEmail;

    @Column(name = "COU_ADDRESS", length = 500)
    private String couAddress;

    @Temporal(TemporalType.DATE)
    @Column(name = "COU_ELECDATE")
    private Date couElecDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "COU_OATHDATE")
    private Date couOathDate;

    @Column(name = "COU_PARTYAFFILATION")
    private Long couPartyAffilation;

    @Column(name = "H_STATUS", length = 1)
    private String historyStatus;

    @Column(name = "PARTY_NAME", length = 250)
    private String couPartyName;

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

    public Long getCouHistoryId() {
        return couHistoryId;
    }

    public void setCouHistoryId(Long couHistoryId) {
        this.couHistoryId = couHistoryId;
    }

    public Long getCouId() {
        return couId;
    }

    public void setCouId(Long couId) {
        this.couId = couId;
    }

    public Long getCouMemberType() {
        return couMemberType;
    }

    public void setCouMemberType(Long couMemberType) {
        this.couMemberType = couMemberType;
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

    public String getCouPartyName() {
        return couPartyName;
    }

    public void setCouPartyName(String couPartyName) {
        this.couPartyName = couPartyName;
    }

    public static String[] getPkValues() {
        return new String[] { "CMT", "TB_CMT_COUNCIL_MEM_MAST_HIST", "COU_HIST_ID" };
    }

}
