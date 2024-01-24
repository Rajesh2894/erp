
package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface BudgetProjectedRevenueRepository
        extends PagingAndSortingRepository<AccountBudgetProjectedRevenueEntryEntity, Long> {

    @Query("select e from AccountBudgetProjectedRevenueEntryEntity e  where e.orgid=:orgid and e.faYearid =:faYearid order by 1 desc")
    List<AccountBudgetProjectedRevenueEntryEntity> findByFinancialId(@Param("faYearid") Long faYearid,
            @Param("orgid") Long orgId);

    @Query("select e.revisedEstamt,e.orginalEstamt,e.prCollected,e.prProjectionid,e.tbDepartment.dpDeptid,e.expectedCurrentYear,e.remark from AccountBudgetProjectedRevenueEntryEntity e  where e.faYearid= :faYearid and e.tbAcBudgetCodeMaster.prBudgetCodeid =:budgCodeid and e.orgid=:orgid")
    List<Object[]> findByRenueOrgAmount(@Param("faYearid") Long faYearid, @Param("budgCodeid") Long budgCodeid,
            @Param("orgid") Long orgId);

    @Modifying
    //@Query("update AccountBudgetProjectedRevenueEntryEntity as te set te.revisedEstamt =:revisedEstamt where te.faYearid =:faYearid and te.prProjectionid =:prProjectionid and te.orgid =:orgid")
    @Query("update AccountBudgetProjectedRevenueEntryEntity as te set te.revisedEstamt =:revisedEstamt where te.faYearid =:faYearid and te.prProjectionid =:prProjectionid and te.orgid =:orgid")
    void updateRevisedEstmtDataRevTable(@Param("faYearid") Long faYearid, @Param("prProjectionid") Long prProjectionid,
            @Param("revisedEstamt") String revisedEstamt, @Param("orgid") Long orgId);
    
    @Modifying
    //@Query("update AccountBudgetProjectedRevenueEntryEntity as te set te.revisedEstamt =:revisedEstamt where te.faYearid =:faYearid and te.prProjectionid =:prProjectionid and te.orgid =:orgid")
    @Query("update AccountBudgetProjectedRevenueEntryEntity as te set te.revisedEstamt =:revisedEstamt, te.expectedCurrentYear =:expectedCurrentYear where te.faYearid =:faYearid and te.prProjectionid =:prProjectionid and te.orgid =:orgid")
    void updateRevisedEstmtDataRevTable(@Param("faYearid") Long faYearid, @Param("prProjectionid") Long prProjectionid,
            @Param("revisedEstamt") String revisedEstamt,@Param("expectedCurrentYear") BigDecimal expectedCurrentYear, @Param("orgid") Long orgId);

    @Query("select s.tbAcBudgetCodeMaster.prBudgetCodeid from AccountBudgetProjectedRevenueEntryEntity s where s.faYearid= :faYearid and s.cpdBugsubtypeId=:cpdBugsubtypeId and s.tbDepartment.dpDeptid=:dpDeptid and s.orgid=:orgId ")
    List<Object[]> findBudgetCodeIdFromBudgetProjectedRevenueEntry(@Param("faYearid") Long faYearid,
            @Param("cpdBugsubtypeId") Long cpdBugsubtypeId, @Param("dpDeptid") Long dpDeptid,
            @Param("orgId") Long orgid);

    /**
     * @param finYearId
     * @param orgid
     * @return
     */
    @Query("select s.tbAcBudgetCodeMaster.prBudgetCodeid,s.tbAcBudgetCodeMaster.prBudgetCode from AccountBudgetProjectedRevenueEntryEntity s where s.faYearid= :faYearid and s.orgid=:orgId ")
    List<Object[]> getBudgetCodeInRevenue(@Param("faYearid") Long finYearId, @Param("orgId") long orgid);

    @Query("select s.tbDepartment.dpDeptid,s.tbDepartment.dpDeptdesc from AccountBudgetProjectedRevenueEntryEntity s where s.orgid=:orgId order by s.tbDepartment.dpDeptid,s.tbDepartment.dpDeptdesc desc")
    List<Object[]> getAllDepartmentIdsData(@Param("orgId") Long orgid);

    @Query("select  s.tbAcBudgetCodeMaster.prBudgetCodeid,s.tbAcBudgetCodeMaster.prBudgetCode,s.tbAcBudgetCodeMaster.tbAcPrimaryheadMaster.cpdIdAcHeadTypes from AccountBudgetProjectedRevenueEntryEntity s where s.orgid=:orgId order by s.tbAcBudgetCodeMaster.prBudgetCode desc")
    List<Object[]> findByAllRevBudgetHeads(@Param("orgId") Long orgId);
    
    @Query("select  s.tbAcBudgetCodeMaster.prBudgetCodeid,s.tbAcBudgetCodeMaster.prBudgetCode,s.tbAcBudgetCodeMaster.tbAcPrimaryheadMaster.cpdIdAcHeadTypes from AccountBudgetProjectedRevenueEntryEntity s where s.orgid=:orgId and s.fieldId=:fieldId order by s.tbAcBudgetCodeMaster.prBudgetCode desc")
    List<Object[]> findByAllRevBudgetHeadsWithFieldId(@Param("orgId") Long orgId,@Param("fieldId") Long fieldId);

    @Query("select e from AccountBudgetProjectedRevenueEntryEntity e  where e.orgid=:orgid order by 1 desc")
    List<AccountBudgetProjectedRevenueEntryEntity> findBudgetProjectedRevenueEntrysByOrgId(@Param("orgid") Long orgId);

    @Query("select s.sacHeadId,s.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity s where s.orgid=:orgId and s.sacStatusCpdId=:activeStatusId")
    List<Object[]> getAccountHeadCodeInRevenue(@Param("orgId") long orgid,@Param("activeStatusId") Long activeStatusId);

    @Query("select e from AccountBudgetProjectedRevenueEntryEntity e where e.orgid=:orgId order by 1 desc")
    List<AccountBudgetProjectedRevenueEntryEntity> getBudgetProjectedRevenueEntry(@Param("orgId") Long orgId);

    @Query("select distinct e.prProjectionid from AccountBudgetProjectedRevenueEntryEntity e where e.faYearid=:faYearid and e.tbDepartment.dpDeptid=:dpDeptid and e.orgid=:orgid and e.tbAcBudgetCodeMaster.prBudgetCodeid=:prRevBudgetCode")
    Long getBudgetProjectedRevenuePrimaryKeyId(@Param("faYearid") Long faYearid, @Param("dpDeptid") Long dpDeptid,
             @Param("orgid") Long orgid, @Param("prRevBudgetCode") Long prRevBudgetCode);

    @Modifying //update next year orginal estimate amount
    @Query("update AccountBudgetProjectedRevenueEntryEntity as te set te.orginalEstamt =:estimateForNextyear where te.prProjectionid =:prProjectionid and te.orgid =:orgid")
    void updateNextYearOriginalEstimateAmount(@Param("prProjectionid") Long prProjectionid,
            @Param("estimateForNextyear") BigDecimal estimateForNextyear, @Param("orgid") Long orgid);

}
