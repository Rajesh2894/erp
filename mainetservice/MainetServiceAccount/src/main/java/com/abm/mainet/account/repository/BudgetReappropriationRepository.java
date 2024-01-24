
package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBudgetReappropriationMasterEntity;

public interface BudgetReappropriationRepository
        extends org.springframework.data.repository.PagingAndSortingRepository<AccountBudgetReappropriationMasterEntity, Long> {

    @Query("select e from AccountBudgetReappropriationMasterEntity e  where e.orgid=:orgid and e.faYearid =:faYearid and e.budgetIdentifyFlag =:budgIdentifyFlag order by 1 desc")
    List<AccountBudgetReappropriationMasterEntity> findByFinancialId(@Param("faYearid") Long faYearid,
            @Param("budgIdentifyFlag") String budgIdentifyFlag, @Param("orgid") Long orgId);

    @Query("select e from AccountBudgetReappropriationMasterEntity e  where e.orgid=:orgid and e.budgetIdentifyFlag =:budgIdentifyFlag order by 1 desc")
    List<AccountBudgetReappropriationMasterEntity> findBudgetReappropriationMastersByOrgId(
            @Param("budgIdentifyFlag") String budgIdentifyFlag,
            @Param("orgid") Long orgId);

    @Query("select e from AccountBudgetReappropriationMasterEntity e where e.budgetTranRefNo =:budgTranRefNo and e.orgid=:orgid and e.budgetIdentifyFlag='R' order by 1 desc")
    AccountBudgetReappropriationMasterEntity findByReappWorkFlowDataByBudgetTranRefNo(
            @Param("budgTranRefNo") String budgTranRefNo,
            @Param("orgid") Long orgId);

    @Modifying
    @Query("update AccountBudgetReappropriationMasterEntity e set e.employee.empId =:approvedBy,e.authFlag='Y' where e.paAdjid=:paAdjid and e.orgid=:orgid")
    void updateMasterTableAuthFlagStatus(@Param("paAdjid") Long paAdjid, @Param("orgid") Long orgid,
            @Param("approvedBy") Long approvedBy);

    @Modifying
    @Query("update AccountBudgetReappropriationTrMasterEntity d set d.authFlag='Y' where d.paAdjidTr=:paAdjidTr and d.orgid=:orgid")
    void updateDetailsTrTableAuthFlagStatus(@Param("paAdjidTr") Long paAdjidTr, @Param("orgid") Long orgid);

}
