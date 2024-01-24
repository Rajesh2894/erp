package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.ParawiseRemark;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface ParawiseRemarkRepository extends JpaRepository<ParawiseRemark, Long> {
    List<ParawiseRemark> findByOrgidAndCaseId(Long orgid, Long caseId);

    @Query("SELECT pm FROM ParawiseRemark pm WHERE pm.caseId = :caseId AND (pm.status != :status OR pm.status IS NULL)")
    List<ParawiseRemark> findByCaseId(@Param("caseId") Long caseId, @Param("status") String status);

}
