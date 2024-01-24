
package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBudgetAdditionalSupplementalEntity;

public interface BudgetAdditionalSupplementalRepository
        extends org.springframework.data.repository.PagingAndSortingRepository<AccountBudgetAdditionalSupplementalEntity, Long> {

    @Query("select e from AccountBudgetAdditionalSupplementalEntity e  where e.orgid=:orgid and e.faYearid =:faYearid and e.budgetIdentifyFlag =:budgIdentifyFlag order by 1 desc")
    List<AccountBudgetAdditionalSupplementalEntity> findByFinancialId(@Param("faYearid") Long faYearid,
            @Param("budgIdentifyFlag") String budgIdentifyFlag, @Param("orgid") Long orgId);

    @Query("select e from AccountBudgetAdditionalSupplementalEntity e  where e.orgid =:orgid and e.budgetIdentifyFlag =:budgIdentifyFlag order by 1 desc")
    List<AccountBudgetAdditionalSupplementalEntity> findByOrgId(@Param("orgid") Long orgId,
            @Param("budgIdentifyFlag") String budgIdentifyFlag);

}
