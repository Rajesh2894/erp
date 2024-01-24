package com.abm.mainet.swm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.PopulationMaster;

/**
 * The Interface PopulationMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */
@Repository
public interface PopulationMasterRepository extends JpaRepository<PopulationMaster, Long> {

    /**
     * Find all by pop year and orgid.
     *
     * @param popYear the pop year
     * @param orgId the org id
     * @return the list
     */
    List<PopulationMaster> findAllByPopYearAndOrgid(Long popYear, Long orgId);

    @Query("SELECT distinct fy.faFromDate FROM FinancialYear fy Where fy.faFromDate <= CURRENT_DATE order by fy.faFromDate desc")
    List<Date> getAllFinincialYear();

}
