
package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBudgetEstimationPreparationEntity;

public interface BudgetEstimationPreparationRepository
        extends org.springframework.data.repository.PagingAndSortingRepository<AccountBudgetEstimationPreparationEntity, Long> {

    @Query("select e from AccountBudgetEstimationPreparationEntity e  where e.orgid=:orgid order by 1 desc")
    List<AccountBudgetEstimationPreparationEntity> findBudgetEstimationPreparationByFinId(@Param("orgid") Long orgId);
    
    
    @Query("select distinct e.bugestId from AccountBudgetEstimationPreparationEntity e where e.faYearid=:faYearid and e.dpDeptid=:dpDeptid and e.orgid=:orgid and e.tbAcBudgetCodeMaster.prBudgetCodeid=:budgetCode")
    Long getBudgetEstimationPrimaryKeyId(@Param("faYearid") Long faYearid, @Param("dpDeptid") Long dpDeptid,
             @Param("orgid") Long orgid, @Param("budgetCode") Long prRevBudgetCode);
    
    @Query("select sum(e.estimateForNextyear),sum(e.apprBugStandCom),sum(e.finalizedBugGenBody) from AccountBudgetEstimationPreparationEntity e where e.cpdBugtypeId=:cpdBugtypeId and e.nextFaYearid =:nextFaYearid")
    List<Object[]> findAccountBudgetReceiptsSummaryBasedOnNFY(@Param("nextFaYearid") Long nextFaYearid, @Param("cpdBugtypeId")Long cpdBugtypeId);
    
    @Query("select sum(e.estimateForNextyear),sum(e.apprBugStandCom),sum(e.finalizedBugGenBody) from AccountBudgetEstimationPreparationEntity e where e.cpdBugtypeId=:cpdBugtypeId and e.nextFaYearid =:nextFaYearid")
    List<Object[]> findAccountBudgetExpenditureSummaryBasedOnNFY(@Param("nextFaYearid") Long nextFaYearid,@Param("cpdBugtypeId")Long cpdBugtypeId);

}
	