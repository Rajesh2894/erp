package com.abm.mainet.council.dao;

import java.util.List;

import com.abm.mainet.council.domain.CouncilMemberMasterEntity;

public interface ICouncilMemberMasterDao {

    /**
     * Below method is used to search records
     * @param couMemName
     * @param designation
     * @param couPartyAffilation
     * @param orgid
     * @return list of data
     */
    List<CouncilMemberMasterEntity> searchCouncilMasterData(String couMemName, Long designation, Long couPartyAffilation,
            Long orgid, Long couEleWZId1, Long couEleWZId2);

}
