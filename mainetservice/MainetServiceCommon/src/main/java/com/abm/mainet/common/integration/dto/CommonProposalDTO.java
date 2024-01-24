package com.abm.mainet.common.integration.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommonProposalDTO implements Serializable {

    private static final long serialVersionUID = -5731388169313361774L;

    private Long proposalId;

    private String proposalNo;

    private Long proposalDepId;

    private Date proposalDate;

    private String proposalDetails;

    private BigDecimal proposalAmt;

    private String proposalStatus; // dept status

    private Long orgId;

    private String meetingProposalStatus; // final meeting status

    private String meetingDate;

    private String isMOMProposal;

    private String proposalType;

    private Long proposalSource;

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    public String getProposalDetails() {
        return proposalDetails;
    }

    public void setProposalDetails(String proposalDetails) {
        this.proposalDetails = proposalDetails;
    }

    public Long getProposalDepId() {
        return proposalDepId;
    }

    public void setProposalDepId(Long proposalDepId) {
        this.proposalDepId = proposalDepId;
    }

    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public BigDecimal getProposalAmt() {
        return proposalAmt;
    }

    public void setProposalAmt(BigDecimal proposalAmt) {
        this.proposalAmt = proposalAmt;
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getMeetingProposalStatus() {
        return meetingProposalStatus;
    }

    public void setMeetingProposalStatus(String meetingProposalStatus) {
        this.meetingProposalStatus = meetingProposalStatus;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getIsMOMProposal() {
        return isMOMProposal;
    }

    public void setIsMOMProposal(String isMOMProposal) {
        this.isMOMProposal = isMOMProposal;
    }

    public String getProposalType() {
        return proposalType;
    }

    public void setProposalType(String proposalType) {
        this.proposalType = proposalType;
    }

    public Long getProposalSource() {
        return proposalSource;
    }

    public void setProposalSource(Long proposalSource) {
        this.proposalSource = proposalSource;
    }

}
