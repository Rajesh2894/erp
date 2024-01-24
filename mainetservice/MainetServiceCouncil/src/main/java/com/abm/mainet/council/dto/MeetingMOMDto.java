package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MeetingMOMDto implements Serializable {
    private static final long serialVersionUID = 8448890233349372645L;

    private Long momId;

    private Long meetingId;

    private Date meetingDate;

    private Long proposalId;

    private String proposalNo;

    private Long agendaNo;

    private Long agendaId;

    private String detailsOfProposal;

    private String department;

    private String momResolutionComments;

    private String momStatus; // action taken

    private List<MeetingMOMSumotoDto> momSumotoDtos;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

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

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

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

    public Long getAgendaNo() {
        return agendaNo;
    }

    public void setAgendaNo(Long agendaNo) {
        this.agendaNo = agendaNo;
    }

    public String getDetailsOfProposal() {
        return detailsOfProposal;
    }

    public void setDetailsOfProposal(String detailsOfProposal) {
        this.detailsOfProposal = detailsOfProposal;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public List<MeetingMOMSumotoDto> getMomSumotoDtos() {
        return momSumotoDtos;
    }

    public void setMomSumotoDtos(List<MeetingMOMSumotoDto> momSumotoDtos) {
        this.momSumotoDtos = momSumotoDtos;
    }

    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long agendaId) {
        this.agendaId = agendaId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
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

}
