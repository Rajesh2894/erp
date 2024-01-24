package com.abm.mainet.adh.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.AdvertiserMasterEntity;
@Repository
public interface ADHReportRepository extends JpaRepository<AdvertiserMasterEntity, Long>
{
	@Query(" FROM AdvertiserMasterEntity am WHERE agencyLicIssueDate BETWEEN :fromDate AND :toDate AND am.orgId=:orgId ")
	List<AdvertiserMasterEntity> findAdvertiserRegisterRepository(@Param("fromDate")Date fromDate,@Param("toDate")Date toDate, @Param("orgId") Long orgId);
}


