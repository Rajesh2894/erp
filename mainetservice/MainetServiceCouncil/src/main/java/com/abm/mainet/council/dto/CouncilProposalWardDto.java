package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author aarti.paan
 * @since 25 April 2019
 */

public class CouncilProposalWardDto implements Serializable {

    private static final long serialVersionUID = 3129498818557939218L;

    private Long ProposalWardId;

    private Long proposalId;

    private Long wardId;

    private Long orgId;

    private Long createdBy;

    private Long updatedBy;

    private Date createdDate;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

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

    public Long getProposalWardId() {
        return ProposalWardId;
    }

    public void setProposalWardId(Long proposalWardId) {
        ProposalWardId = proposalWardId;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
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

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

}
