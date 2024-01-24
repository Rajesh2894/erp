package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.TbFincialyearorgMapEntity;

/**
 * Repository : TbFincialyearorgMap.
 */
public interface TbFincialyearorgMapJpaRepository extends
        PagingAndSortingRepository<TbFincialyearorgMapEntity, Long> {

    @Query("select om from TbFincialyearorgMapEntity om  where om.orgid=:orgId and om.tbFinancialyear.faYear=:faYearId")
    TbFincialyearorgMapEntity findOrgFincialYear(@Param("orgId") Long orgId, @Param("faYearId") Long faYearId);

    @Query("select om.tbFinancialyear.faYear from TbFincialyearorgMapEntity om where "
            + "om.tbFinancialyear.faFromDate < sysdate and "
            + "to_char(om.tbFinancialyear.faFromDate,'YYYY') >= (to_char(sysdate,'YYYY')-2)")
    List<Long> getCurrentandPreviousFinancialYear();

    @Query("select om.tbFinancialyear from TbFincialyearorgMapEntity om where om.orgid=:orgId order by om.faFromYear desc")
    List<FinancialYear> findAllFinYearByOrgId(@Param("orgId") Long orgId);
    
    @Query("select om from FinancialYear om order by om.faFromDate desc")
    List<FinancialYear> findAllFinYear();
}
