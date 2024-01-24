
package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountBudgetReappropriationTrMasterEntity;

/**
 * Repository : TbAcProjectedprovisionadjTr.
 */
@Repository
public interface BudgetReappropriationTransactionRepository
        extends PagingAndSortingRepository<AccountBudgetReappropriationTrMasterEntity, Long> {

    @Query("select e from AccountBudgetReappropriationTrMasterEntity e  where  e.tbAcProjectedprovisionadj.paAdjid =:paAdjid ")
    List<AccountBudgetReappropriationTrMasterEntity> findByReappData(@Param("paAdjid") Long paAdjid);

}
