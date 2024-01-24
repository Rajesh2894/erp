package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.master.dto.DesignationBean;

public class CouncilRestResponseDto implements Serializable {

    private static final long serialVersionUID = 1512272134979557480L;

    private List<CouncilMemberMasterDto> councilMemberDto = null;

    private List<CouncilProposalMasterDto> councilProposalDto = new ArrayList<>();

    private List<DesignationBean> designation = null;

    private List<CouncilAgendaMasterDto> councilAgendaMasterDtoList = new ArrayList<>();

    private List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = new ArrayList<>();

    private List<MOMResolutionDto> meetingMOMDtos = new ArrayList<>();

    private List<CouncilMemberCommitteeMasterDto> memberList = new ArrayList<>();

    private List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList = null;

    public List<CouncilMemberMasterDto> getCouncilMemberDto() {
        return councilMemberDto;
    }

    public void setCouncilMemberDto(List<CouncilMemberMasterDto> councilMemberDto) {
        this.councilMemberDto = councilMemberDto;
    }

    public List<DesignationBean> getDesignation() {
        return designation;
    }

    public void setDesignation(List<DesignationBean> designation) {
        this.designation = designation;
    }

    public List<CouncilAgendaMasterDto> getCouncilAgendaMasterDtoList() {
        return councilAgendaMasterDtoList;
    }

    public void setCouncilAgendaMasterDtoList(List<CouncilAgendaMasterDto> councilAgendaMasterDtoList) {
        this.councilAgendaMasterDtoList = councilAgendaMasterDtoList;
    }

    public List<CouncilProposalMasterDto> getCouncilProposalDto() {
        return councilProposalDto;
    }

    public void setCouncilProposalDto(List<CouncilProposalMasterDto> councilProposalDto) {
        this.councilProposalDto = councilProposalDto;
    }

    public List<CouncilMeetingMasterDto> getCouncilMeetingMasterDtoList() {
        return councilMeetingMasterDtoList;
    }

    public void setCouncilMeetingMasterDtoList(List<CouncilMeetingMasterDto> councilMeetingMasterDtoList) {
        this.councilMeetingMasterDtoList = councilMeetingMasterDtoList;
    }

    public List<CouncilMemberCommitteeMasterDto> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<CouncilMemberCommitteeMasterDto> memberList) {
        this.memberList = memberList;
    }

    public List<CouncilMemberCommitteeMasterDto> getCouncilMemberCommitteeMasterDtoList() {
        return councilMemberCommitteeMasterDtoList;
    }

    public void setCouncilMemberCommitteeMasterDtoList(
            List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList) {
        this.councilMemberCommitteeMasterDtoList = councilMemberCommitteeMasterDtoList;
    }

    public List<MOMResolutionDto> getMeetingMOMDtos() {
        return meetingMOMDtos;
    }

    public void setMeetingMOMDtos(List<MOMResolutionDto> meetingMOMDtos) {
        this.meetingMOMDtos = meetingMOMDtos;
    }

}
