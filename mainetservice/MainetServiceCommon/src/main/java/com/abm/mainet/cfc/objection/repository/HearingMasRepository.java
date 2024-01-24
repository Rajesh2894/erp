package com.abm.mainet.cfc.objection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.objection.domain.HearingMasterEntity;

public interface HearingMasRepository extends JpaRepository<HearingMasterEntity, Long> {

    @Query("SELECT h from  HearingMasterEntity h WHERE h.insHaerId in(select max(hm.insHaerId) from HearingMasterEntity hm where hm.orgId=:orgId"
            + " AND hm.objId=:appId)")
    HearingMasterEntity getHearingDetailByObjId(@Param("orgId") Long orgId, @Param("appId") Long objId);

}
