package com.abm.mainet.council.dao;

import java.util.List;

import com.abm.mainet.council.domain.CouncilMemberCommitteeMasterEntity;

public interface ICouncilMemberCommitteeDao {

    List<CouncilMemberCommitteeMasterEntity> searchCouncilMemberCommitteeData(Long memberId, Long committeeTypeId, Long orgid,
            String status);

    List<CouncilMemberCommitteeMasterEntity> fetchCommitteeMemberByIdAndDissolveDate(Long committeeTypeId, Long orgId,
            String status, String memberStatus);

    CouncilMemberCommitteeMasterEntity checkMemberExistInDissolveDate(Long memberId, Long committeeTypeId, Long orgId,
            String status);

}
