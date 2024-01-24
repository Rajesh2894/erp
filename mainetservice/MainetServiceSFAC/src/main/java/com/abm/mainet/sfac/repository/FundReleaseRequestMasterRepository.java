package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FundReleaseRequestMasterEntity;

@Repository
public interface FundReleaseRequestMasterRepository extends JpaRepository<FundReleaseRequestMasterEntity, Long>{

	List<FundReleaseRequestMasterEntity> findByIaIdAndFinancialYearAndFileReferenceNumber(Long iaId, Long fy,
			String applicationRef);

	List<FundReleaseRequestMasterEntity> findByIaIdAndFinancialYear(Long iaId, Long fy);

	List<FundReleaseRequestMasterEntity> findByIaIdAndFileReferenceNumber(Long iaId, String applicationRef);

	FundReleaseRequestMasterEntity findByApplicationNumber(Long appNumber);

}
