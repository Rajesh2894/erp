package com.abm.mainet.tradeLicense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.tradeLicense.domain.TbMlRenewalMastHist;

@Repository
public interface RenewalMasterHistoryRepository extends JpaRepository<TbMlRenewalMastHist, Long> {
	/**
	 * used to get Trade Renewal Details With All Details By Application Id
	 * 
	 * @param applicationId
	 */
	@Query("select p from TbMlRenewalMastHist p where p.apmApplicationId =:applicationId")
	List<TbMlRenewalMastHist> getLicenseDetailsByApplicationId(@Param("applicationId") Long applicationId);

}
