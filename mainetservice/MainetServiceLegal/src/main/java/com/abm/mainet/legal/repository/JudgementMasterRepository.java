package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.abm.mainet.legal.domain.CaseEntry;
import com.abm.mainet.legal.domain.JudgementMaster;

@Repository
public interface JudgementMasterRepository extends JpaRepository<JudgementMaster, Long> {

    List<JudgementMaster> findByCaseEntryAndCrtIdAndOrgIdAndJudgementStatus(CaseEntry caseEntry, Long crtId, Long orgId,
            String judgementStatus);

    @Modifying
    @Query("UPDATE JudgementMaster JM SET JM.judgementStatus ='D', JM.updatedBy =:updatedBy, JM.updatedDate = CURRENT_DATE "
            + "WHERE JM.judId in (:removeIds) ")
    void deActiveJudgementMaster(@Param("removeIds") List<Long> removeIds, @Param("updatedBy") Long updatedBy);
    
    @Query("select j from JudgementMaster j where j.caseEntry.cseId=:CSE_ID and j.orgId=:orgId")
    List<JudgementMaster> getLatestJudge(@Param("CSE_ID") Long CSE_ID,@Param("orgId") Long orgId,Pageable pageable);
    
    @Query("SELECT MAX(j.judDate) from JudgementMaster j where j.caseEntry.cseId=:cseId and j.orgId=:orgId")
    Object getJudgementMasterdate( @Param("orgId")Long orgId, @Param("cseId") Long cseId);
    
    @Query("SELECT MAX(j.judDate) from JudgementMaster j where j.caseEntry.cseId=:cseId ")
    Object getJudgementMasterdateById(@Param("cseId") Long cseId);
}
