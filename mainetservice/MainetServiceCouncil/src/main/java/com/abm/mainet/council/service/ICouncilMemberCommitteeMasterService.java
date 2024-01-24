package com.abm.mainet.council.service;

import java.util.List;

import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;

public interface ICouncilMemberCommitteeMasterService {

    public boolean saveCouncilMemberCommitteeMaster(CouncilMemberCommitteeMasterDto councilDto,
            List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList, List<Long> oldMemberIds);

    public List<CouncilMemberCommitteeMasterDto> searchCouncilMemberCommitteeMasterData(Long memberId, Long committeeTypeId,
            Long orgId, String status);

    public List<CouncilMemberCommitteeMasterDto> fetchMappingMemberListByCommitteeTypeId(Long committeeTypeId,
            Boolean dataForMeetingCommitteeMember, String status, String memberStatus, Long orgId);

    public CouncilMemberCommitteeMasterDto getCommitteeMemberData(Long committeeTypeId, Long orgId, String status);

    // use for error message who not in dissolve date
    public List<String> fetchMemberNotExistInDissolveDate(List<Long> memberIds, Long committeeTypeId, String status, Long orgId);

    Boolean checkCommitteeTypeInDissolveDateByOrg(Long committeeTypeId, Long orgId, String status);

    void updateInactiveStatusOfOldCommitteeType(String status, Long committeeTypeId, Long orgId);
    
    Boolean checkMemberPresentInCommitteeByOrg(Long committeeTypeId, Long orgId, String status);

}
