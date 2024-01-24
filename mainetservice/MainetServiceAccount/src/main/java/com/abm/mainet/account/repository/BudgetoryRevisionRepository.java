
package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBudgetoryRevisionEntity;

public interface BudgetoryRevisionRepository
        extends org.springframework.data.repository.PagingAndSortingRepository<AccountBudgetoryRevisionEntity, Long> {

    @Query("select e from AccountBudgetoryRevisionEntity e  where e.orgid=:orgid order by 1 desc")
    List<AccountBudgetoryRevisionEntity> findByFinancialId(@Param("orgid") Long orgId);

    @Query("select sum(be.actualAmount) from AccountBillEntryMasterEnitity bm,AccountBillEntryExpenditureDetEntity be where bm.id = be.billMasterId.id and be.budgetCodeId.prBudgetCodeid =:prBudgetCodeid and bm.billEntryDate between :dateFrom and :dateTo and bm.orgId = be.orgid and bm.orgId= :orgId")
    BigDecimal getSumFeeExpAmount(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo, @Param("orgId") Long orgId);
}
