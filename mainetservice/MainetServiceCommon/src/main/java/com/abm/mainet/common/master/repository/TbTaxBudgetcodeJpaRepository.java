package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbTaxAcMappingEntity;

/**
 * Repository : TbTaxBudgetCodeEntity.
 */
public interface TbTaxBudgetcodeJpaRepository extends PagingAndSortingRepository<TbTaxAcMappingEntity, Long> {

    @Query("select tbc from TbTaxAcMappingEntity tbc where tbc.orgId=:orgId and tbc.taxId=:taxId")
    List<TbTaxAcMappingEntity> getByTaxIdOrgId(@Param("orgId") Long orgId, @Param("taxId") Long taxId);

    @Query("select taxBudgetCode.taxbActive,accountBudgetCode.prBudgetCode from TbTaxAcMappingEntity taxBudgetCode,AccountBudgetCodeEntity accountBudgetCode "
            + "where accountBudgetCode.prBudgetCodeid in (:budgetIdList)")
    List<Object> getBudgetCodeList(@Param("budgetIdList") List<Long> budgetIdList);

    @Query("select tbc from TbTaxAcMappingEntity tbc where tbc.taxId=:taxId and tbc.orgId=:orgId")
    TbTaxAcMappingEntity findTbTaxBudgetCode(@Param("taxId") Long taxId, @Param("orgId") Long orgId);

    @Query("select tbc from TbTaxAcMappingEntity tbc where tbc.sacHeadId=:sacHeadId and tbc.dmdClass=:dmdClass and tbc.orgId=:orgId")
    TbTaxAcMappingEntity getBySacHeadId(@Param("sacHeadId") Long sacHeadId, @Param("dmdClass") Long dmdClass, @Param("orgId") Long orgId);
}
