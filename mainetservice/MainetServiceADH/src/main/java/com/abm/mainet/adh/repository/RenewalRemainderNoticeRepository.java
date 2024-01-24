package com.abm.mainet.adh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.RenewalRemainderNoticeEntity;

/**
 * @author cherupelli.srikanth
 * @since 03 October 2019
 */
@Repository
public interface RenewalRemainderNoticeRepository extends JpaRepository<RenewalRemainderNoticeEntity, Long> {

    @Query("SELECT rm.createdDate,rm.noticeNo,rm.remarks FROM RenewalRemainderNoticeEntity rm where rm.agencyId=:agencyId AND rm.orgId=:orgId")
    List<Object[]> findNoticeCreatedDateByAgencyIdAndOrgId(@Param("agencyId") Long agencyId,
	    @Param("orgId") Long orgId);
}
