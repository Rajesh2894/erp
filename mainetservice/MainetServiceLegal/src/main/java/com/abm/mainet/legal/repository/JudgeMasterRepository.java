package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.JudgeMaster;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author sanket.joshi
 *
 */
@Repository
public interface JudgeMasterRepository extends JpaRepository<JudgeMaster, Long> {
    List<JudgeMaster> findByOrgId(Long orgId);
    
    @Query("select a from JudgeMaster a where a.orgId=:orgId and a.id in (select b.judgeId from CaseHearing b where b.cseId=:caseId and b.orgid=:orgId)")
    List<JudgeMaster>getAllJudgeBycaseId(@Param("caseId") Long caseId,@Param("orgId") Long orgId);
    
}
