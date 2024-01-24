
package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;

/**
 *
 * @author prasad.kancharla
 *
 */

public interface BudgetHeadRepository
        extends PagingAndSortingRepository<AccountBudgetCodeEntity, Long>, BudgetHeadRepositoryCustom {

    @Query("select e from AccountHeadSecondaryAccountCodeMasterEntity e  where e.orgid=:orgid order by 1 desc")
    List<AccountBudgetCodeEntity> findByAllGridData(@Param("orgid") Long orgId);

    @Query("select bm.prBudgetCodeid, bm.prBudgetCode, pm.primaryAcHeadCompcode, sm.sacHeadCode, sm.sacHeadDesc  from "
            + "AccountHeadPrimaryAccountCodeMasterEntity pm, AccountHeadSecondaryAccountCodeMasterEntity sm, AccountBudgetCodeEntity bm  "
            + "where pm.primaryAcHeadId = sm.tbAcPrimaryheadMaster.primaryAcHeadId and pm.orgid =:superOrgId and sm.orgid =:orgId "
            + "and pm.cpdIdAcHeadTypes =:cpdIdHeadType  and sm.orgid=bm.orgid  and sm.sacHeadId=bm.tbAcSecondaryheadMaster.sacHeadId")
    List<Object[]> getBudgetHeads(@Param("superOrgId") Long superOrgId, @Param("orgId") Long orgId,
            @Param("cpdIdHeadType") Long cpdIdHeadType);

    @Query("SELECT DISTINCT sm.sacHeadId, sm.acHeadCode  FROM "
            + "AccountHeadPrimaryAccountCodeMasterEntity pm, AccountHeadSecondaryAccountCodeMasterEntity sm,  AccountTDSTaxHeadsEntity tds "
            + "WHERE pm.primaryAcHeadId = sm.tbAcPrimaryheadMaster.primaryAcHeadId AND pm.orgid =:superOrgId AND sm.orgid =:orgId "
            + " AND tds.tdsStatusFlg='A' ")
    List<Object[]> getTDSDeductionBudgetHeads(@Param("superOrgId") Long superOrgId, @Param("orgId") Long orgId);

    @Query("select distinct bm.prBudgetCodeid, bm.prBudgetCode from AccountBudgetCodeEntity bm where bm.orgid =:orgId and bm.prBudgetCodeid =:budgetCodeId")
    List<Object[]> getExpenditutreBudgetHeads(@Param("orgId") Long orgId, @Param("budgetCodeId") Long budgetCodeId);

    @Query("select s.prBudgetCodeid,s.prBudgetCode from "
            + "AccountBudgetCodeEntity s where s.orgid=:orgId order by s.prBudgetCode asc")
    List<Object[]> findAllBudgetCodeId(@Param("orgId") Long orgid);

    @Query(value = "SELECT BM.BUDGETCODE_ID AS COL_0_0_,\r\n" +
            "       SM.AC_HEAD_CODE AS COL_1_0_,\r\n" +
            "       SM.SAC_LED_TYPE AS COL_2_0_,\r\n" +
            "       PM.CPD_ID_ACHEADTYPES AS COL_3_0_,\r\n" +
            "       BM.FIELD_ID AS COL_4_0_,\r\n" +
            "       BM.FUNCTION_ID AS COL_5_0_\r\n" +
            "  FROM TB_AC_BUDGETCODE_MAS BM,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM,\r\n" +
            "       TB_AC_FUNCTION_MASTER FM\r\n" +
            " WHERE     BM.SAC_HEAD_ID = SM.SAC_HEAD_ID\r\n" +
            "       AND BM.FUNCTION_ID = FM.FUNCTION_ID\r\n" +
            "       AND PM.PAC_HEAD_ID = SM.PAC_HEAD_ID\r\n" +
            "       AND BM.CPD_ID_STATUS_FLG =:status \r\n" +
            "       AND BM.ORGID =:orgId\r\n" +
            "ORDER BY BM.BUDGET_CODE ASC", nativeQuery = true)
    List<Object[]> findByAllObjectBudgetHeadIds(@Param("status") String status, @Param("orgId") Long orgid);

    @Query("select m.prBudgetCodeid,m.prBudgetCode,m.tbAcSecondaryheadMaster.sacLeddgerTypeCpdId from "
            + "AccountBudgetCodeEntity m where m.tbDepartment.dpDeptid =:dpDeptid and m.cpdBugtypeId= :cpdBugtypeid and m.cpdBugsubtypeId =:cpdBugsubtypeId and m.cpdIdStatusFlag=:status"
            + " and m.prBudgetCodeid not in (select s.prBudgetCodeid from AccountBudgetCodeEntity s,AccountBudgetProjectedRevenueEntryEntity r where "
            + "r.orgid=s.orgid and r.tbAcBudgetCodeMaster.prBudgetCodeid =s.prBudgetCodeid and r.tbDepartment.dpDeptid=s.tbDepartment.dpDeptid "
            + "and s.cpdBugsubtypeId=r.cpdBugsubtypeId and r.faYearid= :faYearid and r.orgid=:orgId) order by m.prBudgetCode asc")
    List<Object[]> findByAllRevBudgetHeadIds(@Param("faYearid") Long faYearid, @Param("cpdBugtypeid") Long cpdBugtypeid,
            @Param("cpdBugsubtypeId") Long cpdBugsubtypeId, @Param("dpDeptid") Long dpDeptid, @Param("status") Long status,
            @Param("orgId") Long orgid);

    @Query("select m.prBudgetCodeid,m.prBudgetCode,m.tbAcSecondaryheadMaster.sacLeddgerTypeCpdId from "
            + "AccountBudgetCodeEntity m where m.tbDepartment.dpDeptid =:dpDeptid and m.cpdBugtypeId= :cpdBugtypeid and m.cpdBugsubtypeId =:cpdBugsubtypeId and m.cpdIdStatusFlag=:status"
            + " and m.prBudgetCodeid not in (select s.prBudgetCodeid from AccountBudgetCodeEntity s,AccountBudgetProjectedExpenditureEntity r where "
            + "r.orgid=s.orgid and r.tbAcBudgetCodeMaster.prBudgetCodeid =s.prBudgetCodeid and r.tbDepartment.dpDeptid=s.tbDepartment.dpDeptid  "
            + "and s.cpdBugsubtypeId=r.cpdBugsubtypeId and r.faYearid= :faYearid and r.orgid=:orgId) order by m.prBudgetCode asc")
    List<Object[]> findByAllExpBudgetHeadIds(@Param("faYearid") Long faYearid, @Param("cpdBugtypeid") Long cpdBugtypeid,
            @Param("cpdBugsubtypeId") Long cpdBugsubtypeId, @Param("dpDeptid") Long dpDeptid, @Param("status") Long status,
            @Param("orgId") Long orgid);

    @Query("select s.prBudgetCode from AccountBudgetCodeEntity s where s.prBudgetCodeid=:prBudgetCodeid and s.orgid=:orgId")
    String findByBudgetCode(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("orgId") Long orgId);
    
    @Query("select s.tbAcSecondaryheadMaster.acHeadCode from AccountBudgetCodeEntity s where s.prBudgetCodeid=:prBudgetCodeid and s.orgid=:orgId")
    String findByBudgetCodes(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("orgId") Long orgId);

    @Query("select s.tbAcFundMaster.fundId,s.tbAcFundMaster.fundCompositecode,s.tbAcFundMaster.fundDesc from AccountBudgetCodeEntity s where s.prBudgetCodeid=:prBudgetCodeid and s.orgid=:orgId")
    List<Object[]> getFundCode(@Param("prBudgetCodeid") Long prBudgetCodeId, @Param("orgId") Long orgId);

    @Query("select s.tbAcFunctionMaster.functionId,s.tbAcFunctionMaster.functionCompcode,s.tbAcFunctionMaster.functionDesc from AccountBudgetCodeEntity s where s.prBudgetCodeid=:prBudgetCodeid and s.orgid=:orgId")
    List<Object[]> getFunctionCode(@Param("prBudgetCodeid") Long prBudgetCodeId, @Param("orgId") Long orgId);

    @Query("select s.prBudgetCodeid,s.prBudgetCode from AccountBudgetCodeEntity s where s.prBudgetCodeid=:prBudgetCodeid and s.orgid=:orgId order by s.prBudgetCode")
    List<Object[]> findByAllBudgetCode(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("orgId") Long orgId);

    @Query("select b.prBudgetCodeid,b.prBudgetCode from AccountBudgetCodeEntity b  where b.orgid = :orgid")
    List<Object[]> getJournalBudgetCode(@Param("orgid") long orgid);

    @Query("select b.sacHeadId,b.acHeadCode from "
            + "AccountHeadSecondaryAccountCodeMasterEntity b  where b.sacLeddgerTypeCpdId IN(:cpdId1,:cpdId2) and b.orgid = :orgid and b.sacStatusCpdId =:activeStatus ")
    List<Object[]> getVoucherAccountHead(@Param("orgid") long orgid, @Param("cpdId1") Long cpdId1, @Param("cpdId2") Long cpdId2,@Param("activeStatus")  Long activeStatus);

    @Query("SELECT distinct accBudgetProjectedRevenue.tbAcBudgetCodeMaster FROM AccountBudgetProjectedRevenueEntryEntity accBudgetProjectedRevenue "
            + "WHERE "
            + "accBudgetProjectedRevenue.orgid=:orgid AND "
            + "accBudgetProjectedRevenue.tbAcBudgetCodeMaster.cpdIdStatusFlag=:activeId order by 1 desc")
    List<AccountBudgetCodeEntity> getBudgetCodeByDeptIdOrgId(@Param("orgid") Long orgId, @Param("activeId") String activeId);

    @Query("SELECT distinct bc FROM AccountBudgetCodeEntity bc "
            + "WHERE "
            + "bc.orgid =:orgid AND "
            + "bc.cpdIdStatusFlag =:activeId order by 1 desc")
    List<AccountBudgetCodeEntity> getBudgetCodeByAccountDeptIdOrgId(@Param("orgid") Long orgId,
            @Param("activeId") String activeId);

    /* Use this query to get budget code id for cash mode */
    @Query("select bm.prBudgetCodeid from AccountBudgetCodeEntity bm,AccountHeadPrimaryAccountCodeMasterEntity pm, AccountHeadSecondaryAccountCodeMasterEntity sm"
            + " where pm.cpdIdPayMode =:cpdIdayMode and sm.tbAcPrimaryheadMaster.primaryAcHeadId = pm.primaryAcHeadId and bm.tbAcSecondaryheadMaster.sacHeadId = sm.sacHeadId"
            + " and sm.orgid=pm.orgid and bm.orgid=pm.orgid and bm.orgid=sm.orgid  and bm.orgid =:orgId")
    Long getBudgetCodeIdForPayMode(@Param("cpdIdayMode") Long cpdIdayMode, @Param("orgId") Long orgId);

    /* Use this query to get budget code id of bank by passing bank account id and org id */
    @Query("select sm.sacHeadId from AccountHeadSecondaryAccountCodeMasterEntity sm where sm.tbBankaccount.baAccountId =:bankAccountId"
            + " and sm.orgid =:orgId")
    Long getBankBudgetCodeIdByAccountId(@Param("bankAccountId") Long bankAccountId, @Param("orgId") Long orgId);

    @Query("select b.tbAcSecondaryheadMaster.sacHeadId,b.prBudgetCodeid,b.prBudgetCode,b.tbAcSecondaryheadMaster.sacLeddgerTypeCpdId from AccountBudgetCodeEntity b  where b.orgid = :orgid order by b.prBudgetCode")
    List<Object[]> findSacBudgetHeadIdDescAllData(@Param("orgid") long orgId);

    @Query("select sm.tbAcPrimaryheadMaster.cpdIdPayMode from AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "where sm.tbAcPrimaryheadMaster.cpdIdAccountType =:accountType and sm.sacHeadId =:sacHeadId and sm.orgid =:orgId")
    List<Long> getMappingForDeposit(@Param("orgId") Long orgId, @Param("accountType") Long accountType,
            @Param("sacHeadId") Long sacHeadId);

    @Query("SELECT sm.acHeadCode FROM AccountHeadSecondaryAccountCodeMasterEntity sm WHERE sm.sacHeadId=:sacHeadId AND sm.orgid=:orgid")
    String findAccountHeadCodeBySacHeadId(@Param("sacHeadId") Long sacHeadId, @Param("orgid") Long orgId);

    @Query("select distinct pm.primaryAcHeadCompcode,sm.sacHeadCode from "
            + "AccountHeadPrimaryAccountCodeMasterEntity pm, AccountHeadSecondaryAccountCodeMasterEntity sm, AccountBudgetCodeEntity bm "
            + "where bm.prBudgetCodeid =:prBudgetCodeid and bm.orgid =:orgId and bm.tbAcSecondaryheadMaster.sacHeadId=sm.sacHeadId "
            + "and bm.orgid=sm.orgid and sm.tbAcPrimaryheadMaster.primaryAcHeadId=pm.primaryAcHeadId and pm.orgid=sm.orgid")
    List<Object[]> findBudgetCode(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("orgId") Long orgId);

    @Query("select distinct sm.tbAcPrimaryheadMaster.primaryAcHeadCompcode,sm.sacHeadCode from "
            + "AccountHeadSecondaryAccountCodeMasterEntity sm where sm.sacHeadId =:sacHeadId and sm.orgid =:orgId")
    List<Object[]> findAccountHeadCode(@Param("sacHeadId") Long prBudgetCodeid, @Param("orgId") Long orgId);

    @Query("select distinct sm.acHeadCode from "
            + "AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "where sm.sacHeadId =:prBudgetCodeid and sm.orgid =:orgId")
    List<String> findBudgetCodeDetails(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("orgId") Long orgId);

    @Query("select m.prBudgetCodeid,m.prBudgetCode,m.tbAcPrimaryheadMaster.primaryAcHeadCompcode,m.tbAcPrimaryheadMaster.cpdIdAcHeadTypes,m.tbAcFieldMaster.fieldId,m.tbAcFunctionMaster.functionId from "
            + "AccountBudgetCodeEntity m where m.cpdIdStatusFlag=:status and m.orgid=:orgId order by m.prBudgetCode asc")
    List<Object[]> findByAllPrimaryBudgetHeadIds(@Param("status") String status, @Param("orgId") Long orgid);

    @Query("select m.prBudgetCodeid,m.prBudgetCode from "
            + "AccountBudgetCodeEntity m where m.cpdBugtypeId= :cpdBugtypeid and m.cpdBugsubtypeId =:cpdBugsubtypeId and m.cpdIdStatusFlag=:status"
            + " and m.prBudgetCodeid not in (select s.prBudgetCodeid from AccountBudgetCodeEntity s,AccountBudgetProjectedExpenditureEntity r where "
            + "r.orgid=s.orgid and r.tbAcBudgetCodeMaster.prBudgetCodeid =s.prBudgetCodeid  "
            + "and s.cpdBugsubtypeId=r.cpdBugsubtypeId and r.faYearid= :faYearid and r.orgid=:orgId) order by m.prBudgetCode asc")
    List<Object[]> findByAllExpPrimaryBudgetHeadIds(@Param("faYearid") Long faYearid, @Param("cpdBugtypeid") Long cpdBugtypeid,
            @Param("cpdBugsubtypeId") Long cpdBugsubtypeId, @Param("status") String status, @Param("orgId") Long orgid);

    @Query("FROM AccountBudgetCodeEntity bm,AccountHeadSecondaryAccountCodeMasterEntity sm WHERE bm.tbAcPrimaryheadMaster.primaryAcHeadId=sm.tbAcPrimaryheadMaster.primaryAcHeadId"
            + " AND bm.tbAcFunctionMaster.functionId=sm.tbAcFunctionMaster.functionId "
    		+"  AND sm.sacHeadId=bm.tbAcSecondaryheadMaster.sacHeadId"
            + " AND sm.sacHeadId=:sacHeadId AND bm.orgid=:orgId AND bm.cpdIdStatusFlag='A'")
    AccountBudgetCodeEntity findBudgetHeadIdBySacHeadId(@Param("sacHeadId") Long sacHeadId, @Param("orgId") Long orgId);

    @Query("SELECT bm.prBudgetCodeid FROM AccountBudgetCodeEntity bm,AccountHeadSecondaryAccountCodeMasterEntity sm WHERE bm.tbAcPrimaryheadMaster.primaryAcHeadId=sm.tbAcPrimaryheadMaster.primaryAcHeadId"
            + " AND bm.tbAcFunctionMaster.functionId=sm.tbAcFunctionMaster.functionId "
            + " AND sm.sacHeadId =:sacHeadId AND bm.orgid=:orgId")
    List<Long> findBudgetHeadIdBySacHeadIdList(@Param("sacHeadId") Long sacHeadId,
            @Param("orgId") Long orgId);

    @Query("SELECT count(*) FROM AccountHeadSecondaryAccountCodeMasterEntity sm WHERE sm.sacHeadId=:sacHeadId AND sm.orgid=:orgId AND sm.sacStatusCpdId=:status and sm.tbAcPrimaryheadMaster.budgetCheckFlag='Y'")
    Long getBudgetFlagExists(@Param("sacHeadId") Long sacHeadId, @Param("orgId") Long orgId, @Param("status") Long status);

    @Query("SELECT bm FROM AccountBudgetCodeEntity bm WHERE bm.orgid=:orgid AND bm.cpdIdStatusFlag=:cpdIdStatusFlag")
    List<AccountBudgetCodeEntity> getBudgetHeadCodes(@Param("orgid") long orgid,
            @Param("cpdIdStatusFlag") String cpdIdStatusFlag);
    
    @Query("SELECT pm.cpdIdAcHeadTypes FROM AccountBudgetCodeEntity bm ,AccountHeadPrimaryAccountCodeMasterEntity pm WHERE bm.orgid=:orgid AND bm.prBudgetCodeid=:BudgetCodeId and bm.tbAcPrimaryheadMaster.primaryAcHeadId=pm.primaryAcHeadId")
    public Long getCPDAccTypeByBudgetCodeId(@Param("orgid") Long orgid,@Param("BudgetCodeId") Long BudgetCodeId);
    
    @Query("SELECT b.tbAcSecondaryheadMaster.sacHeadId,b.prBudgetCode FROM AccountBudgetProjectedExpenditureEntity a,AccountBudgetCodeEntity b WHERE a.tbAcBudgetCodeMaster.prBudgetCodeid=b.prBudgetCodeid and a.orgid=:orgid and a.tbDepartment.dpDeptid=:dpDeptid and a.fieldId=:fieldId and a.faYearid =:finYearId")
    List<Object[]> findByAllObjectBudgetHeadId(@Param("finYearId") Long finYearId, @Param("orgid") Long orgid,@Param("dpDeptid") Long dpDeptid,@Param("fieldId") Long fieldId);

    @Query("select e.tbAcSecondaryheadMaster.sacHeadId from AccountBudgetCodeEntity e  where e.orgid=:orgid and e.prBudgetCodeid=:prBudgetCodeid order by 1 desc")
    Long getSacHeadId(@Param("orgid") Long orgId,@Param("prBudgetCodeid") Long prBudgetCodeid);
    
    @Query("select a.sacHeadId, a.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity a,AccountBudgetCodeEntity b, AccountBudgetProjectedExpenditureEntity c where a.sacHeadId=b.tbAcSecondaryheadMaster.sacHeadId and c.tbAcBudgetCodeMaster.prBudgetCodeid=b.prBudgetCodeid and c.orgid=:orgId and c.tbDepartment.dpDeptid=:deptId and c.fieldId=:fieldId ")
	List<Object[]> getSecondaryHeadcodesDeptField(@Param("orgId") Long orgId,@Param("deptId") Long deptId,@Param("fieldId") Long fieldId);
	
	@Query("select a.sacHeadId, a.acHeadCode from AccountHeadSecondaryAccountCodeMasterEntity a,AccountBudgetCodeEntity b, AccountBudgetProjectedExpenditureEntity c where a.sacHeadId=b.tbAcSecondaryheadMaster.sacHeadId and c.tbAcBudgetCodeMaster.prBudgetCodeid=b.prBudgetCodeid and c.orgid=:orgId and c.fieldId=:fieldId ")
	List<Object[]> getSecondaryHeadcodesWithField(@Param("orgId") Long orgId,@Param("fieldId") Long fieldId);
	 
	@Query("select a from AccountHeadSecondaryAccountCodeMasterEntity a,AccountBudgetCodeEntity b, AccountBudgetProjectedExpenditureEntity c where a.sacHeadId=b.tbAcSecondaryheadMaster.sacHeadId and c.tbAcBudgetCodeMaster.prBudgetCodeid=b.prBudgetCodeid and c.orgid=:orgId and c.tbDepartment.dpDeptid=:deptId and c.fieldId=:fieldId")
	List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesWithDeptField(@Param("orgId") Long orgId,@Param("deptId") Long deptId,@Param("fieldId") Long fieldId);
	
	@Query("select a from AccountHeadSecondaryAccountCodeMasterEntity a,AccountBudgetCodeEntity b, AccountBudgetProjectedExpenditureEntity c where a.sacHeadId=b.tbAcSecondaryheadMaster.sacHeadId and c.tbAcBudgetCodeMaster.prBudgetCodeid=b.prBudgetCodeid and c.orgid=:orgId and c.fieldId=:fieldId")
	List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesField(@Param("orgId") Long orgId,@Param("fieldId") Long fieldId);
	
}
