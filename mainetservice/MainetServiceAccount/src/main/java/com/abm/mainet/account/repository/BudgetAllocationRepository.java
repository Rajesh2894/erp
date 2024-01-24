
package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBudgetAllocationEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface BudgetAllocationRepository
        extends org.springframework.data.repository.PagingAndSortingRepository<AccountBudgetAllocationEntity, Long> {

    @Query("select e from AccountBudgetAllocationEntity e  where e.orgid=:orgid and e.financialYear =:faYearid order by 1 desc")
    List<AccountBudgetAllocationEntity> findByFinancialIds(@Param("faYearid") Long faYearid, @Param("orgid") Long orgId);

}
