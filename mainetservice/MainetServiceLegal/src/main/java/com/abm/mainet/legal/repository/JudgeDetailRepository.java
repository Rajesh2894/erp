package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.CourtMaster;
import com.abm.mainet.legal.domain.JudgeDetailMaster;

@Repository
public interface JudgeDetailRepository extends JpaRepository<JudgeDetailMaster, Long> {

    @Query("select jm from JudgeDetailMaster jm JOIN FETCH jm.judge where jm.court=:court and jm.orgId=:orgId")
    List<JudgeDetailMaster> findByCourtId(@Param("court") CourtMaster court, @Param("orgId") Long orgId);
}
