package com.abm.mainet.council.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.council.domain.CouncilMemberCommitteeMasterEntity;

@Repository
public interface CouncilMemberCommitteMasterRepository extends JpaRepository<CouncilMemberCommitteeMasterEntity, Long> {

    List<CouncilMemberCommitteeMasterEntity> findAllByCommitteeTypeIdAndStatusAndOrgId(Long committeeTypeId, String status,
            Long orgId);

    @Modifying
    @Query("DELETE from  CouncilMemberCommitteeMasterEntity cm where cm.memberCommmitteeId in (?1)")
    void deleteEntityRecords(List<Long> removeIds);

    @Query("select case when count(cm)>0 THEN true ELSE false END from CouncilMemberCommitteeMasterEntity cm where cm.committeeTypeId = :committeeTypeId AND cm.dissolveDate >= CURRENT_DATE AND cm.orgId= :orgId AND status = :status")
    Boolean checkCommitteeTypeInDissolveDate(@Param("committeeTypeId") Long committeeTypeId, @Param("orgId") Long orgId,
            @Param("status") String status);

    @Modifying
    @Query("UPDATE CouncilMemberCommitteeMasterEntity cm SET cm.status = :status  WHERE cm.committeeTypeId= :committeeTypeId and cm.orgId= :orgId")
    void updateInactiveStatusOfOldCommiitteeType(@Param("status") String status, @Param("orgId") Long orgId,
            @Param("committeeTypeId") Long committeeTypeId);
    
    @Query("select case when count(cm)>0 THEN true ELSE false END from CouncilMemberCommitteeMasterEntity cm where cm.committeeTypeId = :committeeTypeId AND cm.orgId= :orgId AND cm.memberStatus = :status")
    Boolean checkMemberPresentInCommitteeType(@Param("committeeTypeId") Long committeeTypeId, @Param("orgId") Long orgId,
            @Param("status") String status);

}
