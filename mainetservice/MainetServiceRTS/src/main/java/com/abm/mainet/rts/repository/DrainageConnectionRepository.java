package com.abm.mainet.rts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.rts.domain.DrainageConnectionEntity;

/**
 * @author rahul.chaubey
 * @since 13 March 2020
 */

@Repository
public interface DrainageConnectionRepository extends JpaRepository<DrainageConnectionEntity, Long> {

    @Query("FROM TbCfcApplicationMstEntity where orgId =:orgId and tbServicesMst.smServiceId =:serviceId")
    List<TbCfcApplicationMstEntity> loadSummaryData(@Param("orgId") Long orgId, @Param("serviceId") Long serviceId);

    @Query("FROM  CFCApplicationAddressEntity where orgId.orgid=:orgId and apmApplicationId=:apmApplicationNo")
    CFCApplicationAddressEntity getApplicationAddressData(@Param("orgId") Long orgId,
            @Param("apmApplicationNo") Long apmApplicationNo);

    @Query("FROM  DrainageConnectionEntity where orgId =:orgId and apmApplicationId=:apmApplicationNo")
    DrainageConnectionEntity getDrainageConnectionData(@Param("orgId") Long orgId,
            @Param("apmApplicationNo") Long apmApplicationNo);

    @Modifying
    @Query("update DrainageConnectionEntity ssa set ssa.status='R' where ssa.apmApplicationId=:applicationId and ssa.orgId=:orgId")
    void rejectPension(@Param("applicationId") String applicationId, @Param("orgId") Long orgId);

    @Modifying
    @Query("update DrainageConnectionEntity ssa set ssa.status=:status where ssa.apmApplicationId=:applicationId and ssa.orgId=:parentOrgId")
    void updateApprovalFlag(@Param("applicationId") String applicationId, @Param("parentOrgId") Long parentOrgId,
            @Param("status") String status);
    
    @Modifying
    @Query("update DrainageConnectionEntity d set d.roadType=:roadType,d.lenRoad=:lenRoad where d.apmApplicationId=:applicationId and d.orgId=:parentOrgId")
    void updateRoadLength(@Param("applicationId") Long applicationId, @Param("parentOrgId") Long parentOrgId,
            @Param("roadType") Long roadType,@Param("lenRoad") Long lenRoad);
}