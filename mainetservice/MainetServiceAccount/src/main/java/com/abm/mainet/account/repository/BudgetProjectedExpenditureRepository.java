
package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;

/**
 * @author prasad.kancharla
 *
 */

public interface BudgetProjectedExpenditureRepository
        extends PagingAndSortingRepository<AccountBudgetProjectedExpenditureEntity, Long> {

    @Query("select e from AccountBudgetProjectedExpenditureEntity e  where e.orgid=:orgid and e.faYearid =:faYearid order by 1 desc")
    List<AccountBudgetProjectedExpenditureEntity> findByFinancialId(@Param("faYearid") Long faYearid,
            @Param("orgid") Long orgId);
    
    @Query("select e from AccountBudgetProjectedExpenditureEntity e  where e.orgid=:orgid and e.faYearid =:faYearid and e.tbDepartment.dpDeptid=:dpDeptid order by 1 desc")
    List<AccountBudgetProjectedExpenditureEntity> findByFinancialIdDeptId(@Param("faYearid") Long faYearid,
            @Param("orgid") Long orgId,@Param("dpDeptid") Long dpDeptid);

    @Query("select e.revisedEstamt,e.orginalEstamt,e.expenditureAmt,e.prExpenditureid,e.tbDepartment.dpDeptid,e.expectedCurrentYearO,e.remark from AccountBudgetProjectedExpenditureEntity e where e.faYearid= :faYearid and e.tbAcBudgetCodeMaster.prBudgetCodeid =:budgCodeid and e.orgid=:orgid")
    List<Object[]> findByExpOrgAmount(@Param("faYearid") Long faYearid, @Param("budgCodeid") Long budgCodeid,
            @Param("orgid") Long orgId);

    @Modifying
    @Query("update AccountBudgetProjectedExpenditureEntity as te set te.revisedEstamt =:revisedEstamt where te.faYearid =:faYearid and te.prExpenditureid =:prExpenditureid and te.orgid =:orgid")
    void updateRevisedEstmtDataExpTable(@Param("faYearid") Long faYearid,
            @Param("prExpenditureid") Long prExpenditureid, @Param("revisedEstamt") String revisedEstamt,
            @Param("orgid") Long orgId);
    
    @Modifying
    @Query("update AccountBudgetProjectedExpenditureEntity as te set te.revisedEstamt =:revisedEstamt,te.expectedCurrentYearO =:expectedCurrentYearO where te.faYearid =:faYearid and te.prExpenditureid =:prExpenditureid and te.orgid =:orgid")
    void updateRevisedEstmtDataExpTable(@Param("faYearid") Long faYearid,
            @Param("prExpenditureid") Long prExpenditureid, @Param("revisedEstamt") String revisedEstamt,@Param("expectedCurrentYearO") BigDecimal expectedCurrentYearO,
            @Param("orgid") Long orgId);
    

    @Query("select s.tbAcBudgetCodeMaster.prBudgetCodeid from AccountBudgetProjectedExpenditureEntity s where s.faYearid= :faYearid and s.cpdBugsubtypeId=:cpdBugsubtypeId and s.tbDepartment.dpDeptid=:dpDeptid and s.orgid=:orgId ")
    List<Object[]> findBudgetCodeId(@Param("faYearid") Long faYearid, @Param("cpdBugsubtypeId") Long cpdBugsubtypeId,
            @Param("dpDeptid") Long dpDeptid, @Param("orgId") Long orgid);

    @Query("select s.tbDepartment.dpDeptid,s.tbDepartment.dpDeptdesc from AccountBudgetProjectedExpenditureEntity s where s.orgid=:orgId order by s.tbDepartment.dpDeptid,s.tbDepartment.dpDeptdesc desc")
    List<Object[]> getAllDepartmentIdsData(@Param("orgId") Long orgid);

    @Query("select distinct s.tbAcBudgetCodeMaster.prBudgetCodeid,s.tbAcBudgetCodeMaster.prBudgetCode from AccountBudgetProjectedExpenditureEntity s where s.tbDepartment.dpDeptid=:dpDeptid and s.orgid=:orgId")
    List<Object[]> findByAllExpBudgetHeads(@Param("dpDeptid") Long dpDeptid, @Param("orgId") Long orgId);

    @Query("select s.revisedEstamt,s.orginalEstamt,s.expenditureAmt from AccountBudgetProjectedExpenditureEntity s where s.tbAcBudgetCodeMaster.prBudgetCodeid=:budgCodeid and s.orgid=:orgId")
    List<Object[]> findByOrgAmount(@Param("budgCodeid") Long budgCodeid, @Param("orgId") Long orgId);

    @Query("select  s.tbAcBudgetCodeMaster.prBudgetCodeid,s.tbAcBudgetCodeMaster.prBudgetCode,s.tbAcBudgetCodeMaster.tbAcPrimaryheadMaster.cpdIdAcHeadTypes from AccountBudgetProjectedExpenditureEntity s where s.orgid=:orgId order by s.tbAcBudgetCodeMaster.prBudgetCode desc")
    List<Object[]> findByAllExpBudgetHeads(@Param("orgId") Long orgId);
    
    @Query("select  s.tbAcBudgetCodeMaster.prBudgetCodeid,s.tbAcBudgetCodeMaster.prBudgetCode,s.tbAcBudgetCodeMaster.tbAcPrimaryheadMaster.cpdIdAcHeadTypes from AccountBudgetProjectedExpenditureEntity s where s.orgid=:orgId and s.fieldId=:fieldId order by s.tbAcBudgetCodeMaster.prBudgetCode desc")
    List<Object[]> findByAllExpBudgetHeadsFieldId(@Param("orgId") Long orgId,@Param("fieldId") Long fieldId);

    @Query("select e from AccountBudgetProjectedExpenditureEntity e  where e.orgid=:orgid order by 1 desc")
    List<AccountBudgetProjectedExpenditureEntity> findAllBudgetProjectedExpenditureByOrgId(@Param("orgid") Long orgId);

    @Query("select e from AccountBudgetProjectedExpenditureEntity e  where e.orgid=:orgId and e.faYearid =:finYearId and e.orginalEstamt !='0.0' ")
    List<AccountBudgetProjectedExpenditureEntity> findExpenditureDataByFinYearId(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId);

    @Query("select e.tbDepartment.dpDeptid from AccountBudgetProjectedExpenditureEntity e where e.orgid =:orgId and e.tbAcBudgetCodeMaster.prBudgetCodeid =:budgetCode and e.faYearid =:finYearId")
    Long getDepartmentByBudget(@Param("orgId") Long orgId, @Param("budgetCode") Long budgetCode,
            @Param("finYearId") Long finYearId);

    @Query(value = "select PE.PR_EXPENDITUREID,\r\n" + "       PE.ORGINAL_ESTAMT,\r\n" + "       PE.REVISED_ESTAMT,\r\n"
            + "       sum(BE.BCH_CHARGES_AMT), BMS.DP_DEPTID\r\n" + "  from TB_AC_PROJECTED_EXPENDITURE PE,\r\n"
            + "       TB_AC_BUDGETCODE_MAS        BM,\r\n" + "       TB_AC_SECONDARYHEAD_MASTER  SM,\r\n"
            + "       TB_AC_PRIMARYHEAD_MASTER    PM,\r\n" + "       TB_AC_FUNCTION_MASTER       FM,\r\n"
            + "       TB_AC_BILL_EXP_DETAIL       BE,\r\n" + "       TB_AC_BILL_MAS              BMS\r\n"
            + " where BE.BM_ID = BMS.BM_ID\r\n" + "   AND PE.BUDGETCODE_ID = BM.BUDGETCODE_ID\r\n"
            + "   AND PM.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n" + "   AND FM.FUNCTION_ID = BM.FUNCTION_ID\r\n"
            + "   AND SM.SAC_HEAD_ID = BE.SAC_HEAD_ID\r\n" + "   AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n"
            + "   AND SM.FUNCTION_ID = FM.FUNCTION_ID\r\n" + "   AND SM.ORGID = BE.ORGID\r\n"
            + "   AND BMS.DP_DEPTID = PE.DP_DEPTID\r\n "
            + "   AND PE.ORGID = :orgId\r\n" + "   AND BMS.ORGID = :orgId\r\n" + "   AND PE.FA_YEARID =:finYearId\r\n"
            + "   AND PE.BUDGETCODE_ID =:budgetCodeId\r\n" + "   AND BMS.CHECKER_AUTHO = 'Y'\r\n"
            + "   AND BMS.BM_DEL_FLAG IS NULL\r\n" + "   AND (BMS.CHECKER_DATE between :fromDate and :toDate)\r\n"
            + " GROUP BY PE.PR_EXPENDITUREID,\r\n" + "          PE.ORGINAL_ESTAMT,\r\n"
            + "          PE.REVISED_ESTAMT, BMS.DP_DEPTID\r\n", nativeQuery = true)

    List<Object[]> getExpenditureDetailsForBillEntryFormView(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query(value = "select PE.PR_EXPENDITUREID,\r\n" + 
    		"       PE.ORGINAL_ESTAMT,\r\n" + 
    		"       PE.REVISED_ESTAMT,\r\n" + 
    		"       sum(BMS.BCH_CHARGES_AMT), PE.DP_DEPTID\r\n" + 
    		"  from TB_AC_PROJECTED_EXPENDITURE PE\r\n" + 
    		"  INNER JOIN   TB_AC_BUDGETCODE_MAS  BM ON PE.BUDGETCODE_ID = BM.BUDGETCODE_ID\r\n" + 
    		"  INNER JOIN   TB_AC_SECONDARYHEAD_MASTER  SM ON SM.SAC_HEAD_ID = BM.SAC_HEAD_ID\r\n" + 
    		"  INNER JOIN   TB_AC_FUNCTION_MASTER  FM ON SM.FUNCTION_ID = FM.FUNCTION_ID\r\n" + 
    		"  LEFT JOIN  (Select BMS.FIELD_ID,BE.BUDGETCODE_ID, BMS.DP_DEPTID,sum(BE.BCH_CHARGES_AMT) BCH_CHARGES_AMT\r\n" + 
    		"  From TB_AC_BILL_MAS BMS,TB_AC_BILL_EXP_DETAIL BE\r\n" + 
    		"  Where BE.BM_ID=BMS.BM_ID  AND BMS.CHECKER_AUTHO='Y' AND  BMS.BM_DEL_FLAG IS NULL  \r\n" + 
    		"  AND (BMS.BM_ENTRYDATE between :fromDate and :toDate)\r\n" + 
    		"  Group by BMS.FIELD_ID,BE.BUDGETCODE_ID, BMS.DP_DEPTID)BMS ON\r\n" + 
    		"  PE.BUDGETCODE_ID = BMS.BUDGETCODE_ID AND  BMS.DP_DEPTID=PE.DP_DEPTID AND  BMS.FIELD_ID=PE.FIELD_ID             \r\n" + 
    		" where PE.ORGID =:orgId\r\n" + 
    		"   AND PE.FA_YEARID =:finYearId\r\n" + 
    		"   AND PE.FIELD_ID =:fieldId\r\n" + 
    		"   AND PE.BUDGETCODE_ID =:budgetCodeId\r\n" + 
    		"    GROUP BY PE.PR_EXPENDITUREID,\r\n" + 
    		"          PE.ORGINAL_ESTAMT,\r\n" + 
    		"          PE.REVISED_ESTAMT, BMS.DP_DEPTID\r\n", nativeQuery = true)

    List<Object[]> getExpenditureDetailsForBillEntryFormViewWithFieldId(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("fieldId") Long fieldId);

    @Query(value = "SELECT pr.pr_expenditureid,pr.orginal_estamt, pr.revised_estamt, SUM(pd.PAYMENT_AMT)\r\n"
            + "  FROM tb_ac_budgetcode_mas        bm,\r\n" + "       tb_ac_secondaryhead_master  sm,\r\n"
            + "       tb_ac_function_master       fm,\r\n" + "       tb_ac_primaryhead_master    pm,\r\n"
            + "       tb_ac_projected_expenditure pr,\r\n" + "       tb_ac_payment_mas           ps,\r\n"
            + "       tb_ac_payment_det           pd\r\n" + "   WHERE sm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n"
            + "   AND sm.FUNCTION_ID = fm.FUNCTION_ID\r\n" + "   AND sm.SAC_HEAD_ID = pd.BUDGETCODE_ID\r\n"
            + "   AND ps.PAYMENT_ID = pd.PAYMENT_ID\r\n" + "   AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n"
            + "   AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n" + "   AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n"
            + "   AND ps.PAYMENT_DEL_FLAG IS NULL\r\n" + "   AND bm.ORGID = pr.ORGID\r\n"
            + "   AND ps.ORGID = pd.ORGID\r\n" + "   AND sm.ORGID = ps.ORGID\r\n" + "   and sm.ORGID = pd.ORGID\r\n"
            + "   and ps.payment_date between :fromDate and :toDate\r\n" + "   AND pr.BUDGETCODE_ID =:budgetCodeId\r\n"
            + "   AND pr.FA_YEARID =:finYearId\r\n" + "   AND pd.ORGID = :orgId\r\n"
            + "   GROUP BY pr.PR_EXPENDITUREID, pr.ORGINAL_ESTAMT, pr.REVISED_ESTAMT", nativeQuery = true)

    List<Object[]> getExpenditureDetailsForPaymentEntryFormView(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("select e.prExpenditureid,e.orginalEstamt,e.revisedEstamt,e.expenditureAmt,e.tbDepartment.dpDeptid from AccountBudgetProjectedExpenditureEntity e,AccountBudgetCodeEntity bc "
            + "where e.tbAcBudgetCodeMaster.prBudgetCodeid=bc.prBudgetCodeid and e.orgid=:orgId and e.faYearid =:finYearId and e.tbAcBudgetCodeMaster.prBudgetCodeid =:budgetCodeId "
            + "group by e.prExpenditureid,e.orginalEstamt,e.revisedEstamt,e.expenditureAmt")
    List<Object[]> getExpenditureDetailsForPaymentEntryTransactionFormView(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId);
    
    @Query("select e.prExpenditureid,e.orginalEstamt,e.revisedEstamt,e.expenditureAmt,e.tbDepartment.dpDeptid from AccountBudgetProjectedExpenditureEntity e,AccountBudgetCodeEntity bc "
            + "where e.tbAcBudgetCodeMaster.prBudgetCodeid=bc.prBudgetCodeid and e.orgid=:orgId and e.fieldId =:fieldId and e.faYearid =:finYearId and e.tbAcBudgetCodeMaster.prBudgetCodeid =:budgetCodeId "
            + "group by e.prExpenditureid,e.orginalEstamt,e.revisedEstamt,e.expenditureAmt")
    List<Object[]> getExpenditureDetailsForPaymentEntryTransactionFormViewWithFieldId(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId,@Param("fieldId") Long fieldId);

    @Query("select sum(e.orginalEstamt),sum(e.revisedEstamt) from AccountBudgetProjectedExpenditureEntity e where e.orgid=:orgId and e.faYearid =:finYearId and e.tbAcBudgetCodeMaster.prBudgetCodeid in :budgetCodeIdList "
            + "group by e.orginalEstamt,e.revisedEstamt")
    List<Object[]> getExpenditureDetailsForPaymentEntryFinalView(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeIdList") List<Long> budgetCodeIdList);
    
    @Query("select sum(e.orginalEstamt),sum(e.revisedEstamt) from AccountBudgetProjectedExpenditureEntity e where e.orgid=:orgId and e.faYearid =:finYearId and e.tbAcBudgetCodeMaster.prBudgetCodeid in :budgetCodeIdList and e.tbDepartment.dpDeptid=:deptId "
            + "group by e.orginalEstamt,e.revisedEstamt")
    List<Object[]> getExpenditureDetailsForPaymentEntryFinalViewBasedOnDeptId(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeIdList") List<Long> budgetCodeIdList,@Param("deptId") Long deptId);
    
    @Query("select sum(e.orginalEstamt),sum(e.revisedEstamt) from AccountBudgetProjectedExpenditureEntity e where e.orgid=:orgId and e.faYearid =:finYearId and e.fieldId=:fieldId and e.tbAcBudgetCodeMaster.prBudgetCodeid in :budgetCodeIdList and e.tbDepartment.dpDeptid=:deptId "
            + "group by e.orginalEstamt,e.revisedEstamt")
    List<Object[]> getExpenditureDetailsForPaymentEntryFinalViewBasedOnDeptIdFieldId(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeIdList") List<Long> budgetCodeIdList,@Param("deptId") Long deptId,@Param("fieldId") Long fieldId);

    @Query("select sum(pd.paymentAmt) from AccountPaymentMasterEntity pe, AccountPaymentDetEntity pd where pe.paymentDate between :fromDate and :toDate and pe.paymentId=pd.paymentMasterId.paymentId and pd.orgId=:orgId and pe.paymentDeletionFlag IS NULL and pd.budgetCodeId =:sacHeadIdList "
            + "group by pd.paymentAmt")
    List<BigDecimal> getExpenditureDetailsForPaymentEntryFinalPaymentAmt(@Param("orgId") Long orgId,
            @Param("sacHeadIdList") Long sacHeadIdList, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("select sum(pd.paymentAmt) from AccountPaymentMasterEntity pe, AccountPaymentDetEntity pd,AccountBillEntryMasterEnitity b\r\n " + 
    		" where pe.paymentDate between :fromDate and :toDate \r\n" + 
    		" and pe.paymentId=pd.paymentMasterId.paymentId \r\n" + 
    		" and pd.orgId=:orgId \r\n" + 
    		" and pe.paymentDeletionFlag IS NULL \r\n" + 
    		" and pd.budgetCodeId =:sacHeadIdList \r\n" + 
    		" and pd.orgId=b.orgId\r\n" + 
    		" and pd.billId=b.id\r\n" + 
    		" and b.departmentId.dpDeptid =:deptId \r\n"+
    		" and b.billDeletionFlag is null\r\n")
    List<BigDecimal> getExpenditureDetailsForPaymentEntryFinalPaymentAmtBasedOnDeparment(@Param("orgId") Long orgId,
            @Param("sacHeadIdList") Long sacHeadIdList, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("deptId") Long deptId);
    
    @Query("select sum(pd.paymentAmt) from AccountPaymentMasterEntity pe, AccountPaymentDetEntity pd,AccountBillEntryMasterEnitity b\r\n " + 
    		" where pe.paymentDate between :fromDate and :toDate \r\n" + 
    		" and pe.paymentId=pd.paymentMasterId.paymentId \r\n" + 
    		" and pd.orgId=:orgId \r\n" + 
    		" and pe.paymentDeletionFlag IS NULL \r\n" + 
    		" and pd.budgetCodeId =:sacHeadIdList \r\n" + 
    		" and pd.orgId=b.orgId\r\n" + 
    		" and pe.fieldId=:fieldId\r\n" + 
    		" and pd.billId=b.id\r\n" + 
    		" and b.departmentId.dpDeptid =:deptId \r\n"+
    		" and b.billDeletionFlag is null\r\n")
    List<BigDecimal> getExpenditureDetailsForPaymentEntryFinalPaymentAmtBasedOnDeparmentWithField(@Param("orgId") Long orgId,
            @Param("sacHeadIdList") Long sacHeadIdList, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("deptId") Long deptId,@Param("fieldId") Long fieldId);
    
    @Query("select sum(d.billChargesAmount) from AccountBillEntryMasterEnitity m ,AccountBillEntryExpenditureDetEntity d \r\n" + 
    		" where m.orgId=d.orgid\r\n" + 
    		" and m.id=d.billMasterId.id\r\n" + 
    		" and m.billDeletionFlag is null\r\n" + 
    		" and d.sacHeadId=:sacHeadIdList\r\n" + 
    		" and m.orgId=:orgId\r\n" + 
    		" and m.billEntryDate between :fromDate and :toDate\r\n" + 
    		" and m.checkerAuthorization='Y'\r\n" + 
    		" and m.departmentId.dpDeptid=:deptId")
    List<BigDecimal> getAccrualExpenditureDetails(@Param("orgId") Long orgId,
            @Param("sacHeadIdList") Long sacHeadIdList, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("deptId") Long deptId);
    
    @Query("select sum(d.billChargesAmount) from AccountBillEntryMasterEnitity m ,AccountBillEntryExpenditureDetEntity d \r\n" + 
    		" where m.orgId=d.orgid\r\n" + 
    		" and m.id=d.billMasterId.id\r\n" + 
    		" and m.billDeletionFlag is null\r\n" + 
    		" and d.sacHeadId=:sacHeadIdList\r\n" + 
    		" and m.orgId=:orgId\r\n" + 
    		" and m.fieldId=:fieldId\r\n" +
    		" and m.billEntryDate between :fromDate and :toDate\r\n" + 
    		" and m.checkerAuthorization='Y'\r\n" + 
    		" and m.departmentId.dpDeptid=:deptId")
    List<BigDecimal> getAccrualExpenditureDetailsWithField(@Param("orgId") Long orgId,
            @Param("sacHeadIdList") Long sacHeadIdList, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("deptId") Long deptId,@Param("fieldId") Long fieldId);

    @Query("select s.revisedEstamt,s.orginalEstamt,s.expenditureAmt from AccountBudgetProjectedExpenditureEntity s where s.tbAcBudgetCodeMaster.prBudgetCodeid=:budgCodeid and s.orgid=:orgId and s.faYearid=:yearId ")
    List<Object[]> findByOrgAmounts(@Param("budgCodeid") Long budgCodeid, @Param("orgId") Long orgId,
            @Param("yearId") Long yearId);

    @Query("select ae from AccountBudgetProjectedExpenditureEntity ae where ae.orgid=:orgId")
    List<AccountBudgetProjectedExpenditureEntity> getBudgetProjectedExpenditure(@Param("orgId") Long orgId);

    @Query("select distinct e.prExpenditureid from AccountBudgetProjectedExpenditureEntity e where e.faYearid=:faYearid and e.tbDepartment.dpDeptid=:dpDeptid and e.orgid=:orgid and e.tbAcBudgetCodeMaster.prBudgetCodeid=:prExpBudgetCode")
    Long getBudgetProjectedExpenditurePrimaryKeyId(@Param("faYearid") Long faYearid, @Param("dpDeptid") Long dpDeptid,
            @Param("orgid") Long orgid,@Param("prExpBudgetCode") Long prExpBudgetCode);

    @Modifying //update next year original estimate
    @Query("update AccountBudgetProjectedExpenditureEntity as te set te.orginalEstamt =:estimateForNextyearExp where te.prExpenditureid =:prExpenditureid and te.orgid =:orgid")
    void updateNextYearOriginalEstimateAmount(@Param("prExpenditureid") Long prExpenditureid,
            @Param("estimateForNextyearExp") BigDecimal estimateForNextyearExp, @Param("orgid") Long orgid);

    @Query("select e.prExpenditureid,e.orginalEstamt,e.revisedEstamt,e.expenditureAmt from AccountBudgetProjectedExpenditureEntity e,AccountBudgetCodeEntity bc "
            + "where e.tbAcBudgetCodeMaster.prBudgetCodeid=bc.prBudgetCodeid and e.orgid=:orgId and e.faYearid =:finYearId and e.tbAcBudgetCodeMaster.prBudgetCodeid =:budgetCodeId and e.tbDepartment.dpDeptid =:dpDeptid "
            + "group by e.prExpenditureid,e.orginalEstamt,e.revisedEstamt,e.expenditureAmt")
    List<Object[]> getExpDetailsForPayEntryTransFormInvoiceBillBudgetView(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId, @Param("dpDeptid") Long dpDeptid);

    @Query(value = "select PE.PR_EXPENDITUREID,\r\n" + "       PE.ORGINAL_ESTAMT,\r\n" + "       PE.REVISED_ESTAMT,\r\n"
            + "       sum(BE.BCH_CHARGES_AMT)\r\n" + "  from TB_AC_PROJECTED_EXPENDITURE PE,\r\n"
            + "       TB_AC_BUDGETCODE_MAS        BM,\r\n" + "       TB_AC_SECONDARYHEAD_MASTER  SM,\r\n"
            + "       TB_AC_PRIMARYHEAD_MASTER    PM,\r\n" + "       TB_AC_FUNCTION_MASTER       FM,\r\n"
            + "       TB_AC_BILL_EXP_DETAIL       BE,\r\n" + "       TB_AC_BILL_MAS              BMS\r\n"
            + " where BE.BM_ID = BMS.BM_ID\r\n" + "   AND PE.BUDGETCODE_ID = BM.BUDGETCODE_ID\r\n"
            + "   AND PM.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n" + "   AND FM.FUNCTION_ID = BM.FUNCTION_ID\r\n"
            + "   AND SM.SAC_HEAD_ID = BE.SAC_HEAD_ID\r\n" + "   AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n"
            + "   AND SM.FUNCTION_ID = FM.FUNCTION_ID\r\n" + "   AND SM.ORGID = BE.ORGID\r\n"
            + "   AND PE.ORGID = :orgId\r\n" + "   AND BMS.ORGID = :orgId\r\n" + "   AND PE.FA_YEARID =:finYearId\r\n"
            + "   AND PE.DP_DEPTID = :dpDeptid\r\n"
            + "   AND PE.BUDGETCODE_ID =:budgetCodeId\r\n" + "   AND BMS.CHECKER_AUTHO = 'Y'\r\n"
            + "   AND BMS.BM_DEL_FLAG IS NULL\r\n" + "   AND (BMS.CHECKER_DATE between :fromDate and :toDate)\r\n"
            + " GROUP BY PE.PR_EXPENDITUREID,\r\n" + "          PE.ORGINAL_ESTAMT,\r\n"
            + "          PE.REVISED_ESTAMT\r\n", nativeQuery = true)
    List<Object[]> getExpDetailsForInvoiceBillEntryFormBudgetView(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dpDeptid") Long dpDeptid);
    
    @Query(value = "select PE.PR_EXPENDITUREID,\r\n" + 
    		"       PE.ORGINAL_ESTAMT,\r\n" + 
    		"       PE.REVISED_ESTAMT,\r\n" + 
    		"       sum(BE.BCH_CHARGES_AMT)\r\n" + 
    		"  from TB_AC_PROJECTED_EXPENDITURE PE\r\n" + 
    		"  inner join TB_AC_BUDGETCODE_MAS  BM       on PE.BUDGETCODE_ID = BM.BUDGETCODE_ID\r\n" + 
    		"  inner join TB_AC_SECONDARYHEAD_MASTER  SM on SM.SAC_HEAD_ID = BM.SAC_HEAD_ID\r\n" + 
    		"  inner join TB_AC_PRIMARYHEAD_MASTER    PM on SM.PAC_HEAD_ID = PM.PAC_HEAD_ID and PM.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n" + 
    		"  inner join TB_AC_FUNCTION_MASTER       FM on FM.FUNCTION_ID = BM.FUNCTION_ID and SM.FUNCTION_ID = FM.FUNCTION_ID\r\n" + 
    		"  left join  TB_AC_BILL_EXP_DETAIL       BE on BE.SAC_HEAD_ID = SM.SAC_HEAD_ID and SM.ORGID = BE.ORGID\r\n" + 
    		"  left join  TB_AC_BILL_MAS              BMS on BE.BM_ID = BMS.BM_ID  AND (BMS.BM_ENTRYDATE between :fromDate and :toDate)\r\n" + 
    		" where PE.ORGID =:orgId\r\n" + 
    		"   AND PE.FA_YEARID =:finYearId\r\n" + 
    		"   AND PE.DP_DEPTID =:dpDeptid\r\n" + 
    		"   AND PE.BUDGETCODE_ID =:budgetCodeId\r\n" + 
    		"   AND PE.FIELD_ID =:fieldId\r\n" + 
    		"    AND BMS.BM_DEL_FLAG IS NULL\r\n" + 
    		"   \r\n" + 
    		" GROUP BY PE.PR_EXPENDITUREID,\r\n" + 
    		"          PE.REVISED_ESTAMT\r\n", nativeQuery = true)
    List<Object[]> getExpDetailsForInvoiceBillEntryFormBudgetViewWithField(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dpDeptid") Long dpDeptid, @Param("fieldId") Long fieldId);
    
   // #157313
    @Query(value = "SELECT Z.*\r\n" + 
    		"FROM(Select SMD.SAC_HEAD_ID,SMD.AC_HEAD_CODE,\r\n" + 
    		"sum(coalesce(A.Estimated_amt,0)) Budget_Provision,\r\n" + 
    		"(sum(coalesce(A.adminapproval_amt,0))+sum(coalesce(spill_over_currentyr,0))) Previous_Sanctions\r\n" + 
    		"From tb_ac_secondaryhead_master SMD\r\n" + 
    		"LEFT JOIN\r\n" + 
    		"(SELECT SHM.SAC_HEAD_ID AS SAC_HEAD_ID,BDDM.BUDGETCODE_ID,\r\n" + 
    		"sum(distinct coalesce(ORGINAL_ESTAMT,0)) Estimated_amt,\r\n" + 
    		"sum(coalesce(case when WD.WORK_STATUS='A' then coalesce(WE.WORKE_AMounT,0) end,0)) as adminapproval_amt,\r\n" + 
    		"sum(coalesce(PREX.CUR_YR_SPAMT,0)) as spill_over_currentyr\r\n" + 
    		"FROM tb_ac_projected_expenditure PREX\r\n" + 
    		"INNER JOIN tb_ac_budgetcode_mas BDDM ON BDDM.BUDGETCODE_ID=PREX.BUDGETCODE_ID and BDDM.ORGID=PREX.ORGID\r\n" + 
    		"INNER JOIN tb_ac_secondaryhead_master SHM on BDDM.SAC_HEAD_ID=SHM.SAC_HEAD_ID and PREX.ORGID=SHM.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_workdefination_year_det WDD ON SHM.SAC_HEAD_ID = WDD.SAC_HEAD_ID and WDD.FA_YEARID=PREX.FA_YEARID and PREX.ORGID=WDD.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_workdefination WD ON WD.WORK_ID = WDD.WORK_ID and WD.orgid=WDD.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_workestimate_mas WE ON WD.WORK_ID = WE.WORK_ID and WD.ORGID = WE.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_tender_work TW ON TW.WORK_ID = WD.WORK_ID AND TW.CONT_ID is null and WE.ORGID = TW.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_tender_mast TM ON TM.TND_ID = TW.TND_ID and TM.TND_STATUS='A' \r\n" + 
    		"LEFT JOIN tb_wms_workeorder WO ON WO.CONT_ID = TW.CONT_ID\r\n" + 
    		"LEFT JOIN tb_contract_detail CD ON CD.CONT_ID = WO.CONT_ID\r\n" + 
    		"WHERE PREX.ORGID=:orgId AND PREX.FA_YEARID =:finYearId \r\n" + 
    		"and PREX.FIELD_ID =:fieldId and SHM.SAC_HEAD_ID =:sacHeadId\r\n" + 
    		"Group By SHM.SAC_HEAD_ID,BDDM.BUDGETCODE_ID\r\n" + 
    		"UNION\r\n" + 
    		"SELECT SHM.SAC_HEAD_ID AS SAC_HEAD_ID,BDDM.BUDGETCODE_ID,\r\n" + 
    		"sum(distinct coalesce(0,0)) Estimated_amt,\r\n" + 
    		"sum(coalesce(WD.YE_BUGEDED_AMOUNT,0)) as adminapproval_amt,\r\n" + 
    		"sum(distinct coalesce(PREX.CUR_YR_SPAMT,0)) as spill_over_currentyr\r\n" + 
    		"FROM tb_cmt_councilBudet_Det WD\r\n" + 
    		"INNER JOIN TB_CMT_COUNCIL_PROPOSAL_MAST CM ON CM.PROPOSAL_ID = WD.PROPOSAL_ID\r\n" + 
    		"LEFT JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = WD.SAC_HEAD_ID\r\n" +
    		"left join tb_ac_budgetcode_mas BDDM on BDDM.SAC_HEAD_ID=SHM.SAC_HEAD_ID\r\n" + 
    		"left join tb_ac_projected_expenditure PREX on BDDM.BUDGETCODE_ID=PREX.BUDGETCODE_ID and WD.FA_YEARID=PREX.FA_YEARID\r\n" + 
    		"WHERE WD.ORGID=:orgId AND WD.FA_YEARID =:finYearId and PREX.FIELD_ID =:fieldId and CM.PROPOSAL_STATUS='A' and SHM.SAC_HEAD_ID =:sacHeadId\r\n" + 
    		"Group By SHM.SAC_HEAD_ID,BDDM.BUDGETCODE_ID)A\r\n" + 
    		"ON SMD.SAC_HEAD_ID=A.SAC_HEAD_ID\r\n" + 
    		"Group by SMD.AC_HEAD_CODE, SMD.SAC_HEAD_ID)Z\r\n" + 
    		"WHERE(coalesce(Z.Budget_Provision,0)) !=0 OR\r\n" + 
    		"(coalesce(Z.Previous_Sanctions,0)) !=0", nativeQuery = true)
    List<Object[]> getExpDetailsForCouncilBillEntryFormBudgetViewWithField(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("sacHeadId") Long sacHeadId,
            @Param("fieldId") Long fieldId);

    @Query(value = "select pac.PAC_HEAD_COMPO_CODE,pac.PAC_HEAD_DESC,\r\n" + 
    		" SUM(IFNULL(INC.PAMOUNT,0)) PAMOUNT,IFNULL(pac.SCH_CODE,'')SCH_CODE\r\n" + 
    		" FROM TB_AC_PRIMARYHEAD_MASTER PAC  \r\n" + 
    		"left join\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT) PAMOUNT\r\n" + 
    		"  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"               A.PAC_HEAD_DESC,\r\n" + 
    		"               A.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               B.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       RCPTAMT,\r\n" + 
    		"                       A.PAC_HEAD_ID\r\n" + 
    		"                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" + 
    		"                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                               (SUM(VD.VAMT_CR) - SUM(VD.VAMT_DR)) RCPTAMT\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" + 
    		"                         WHERE    VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                               AND SD.SAC_LED_TYPE IN\r\n" + 
    		"                                      (SELECT CPD_ID\r\n" + 
    		"                                         FROM TB_COMPARAM_DET D\r\n" + 
    		"                                        WHERE     D.CPM_ID IN\r\n" + 
    		"                                                     (SELECT CPM_ID\r\n" + 
    		"                                                        FROM TB_COMPARAM_MAS\r\n" + 
    		"                                                             M\r\n" + 
    		"                                                       WHERE M.CPM_PREFIX =\r\n" + 
    		"                                                                'FTY')\r\n" + 
    		"                                              AND CPD_VALUE IN ('VD', 'OT'))\r\n" + 
    		"                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate AND :toDate \r\n" + 
    		"                               AND VD.ORGID = :orgId\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 VD.PAC_HEAD_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL2 = R.PAC_HEAD_ID  \r\n" + 
    		"GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) inc\r\n" + 
    		"ON pac.PAC_HEAD_COMPO_CODE = INC.PAC_HEAD_COMPO_CODE AND pac.PAC_HEAD_DESC = INC.PAC_HEAD_DESC\r\n" + 
    		"where pac.PAC_HEAD_COMPO_CODE = :primaryAcHeadId and pac.CPD_ID_ACHEADTYPES in(:cpdAcIdTypes)\r\n" + 
    		"group by  pac.PAC_HEAD_COMPO_CODE,pac.PAC_HEAD_DESC,pac.SCH_CODE\r\n" + 
    		"ORDER BY pac.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryIncomeReportData(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("primaryAcHeadId") Long primaryAcHeadId,@Param("cpdAcIdTypes") long incExpId);

    @Query(value = "select pac.PAC_HEAD_COMPO_CODE,pac.PAC_HEAD_DESC,\r\n" + 
    		" SUM(IFNULL(INC.PAMOUNT,0)) PAMOUNT,IFNULL(pac.SCH_CODE,'')SCH_CODE\r\n" + 
    		" FROM TB_AC_PRIMARYHEAD_MASTER PAC  \r\n" + 
    		"left join\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT) PAMOUNT\r\n" + 
    		"  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"               A.PAC_HEAD_DESC,\r\n" + 
    		"               A.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               B.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       RCPTAMT,\r\n" + 
    		"                       A.PAC_HEAD_ID\r\n" + 
    		"                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" + 
    		"                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                               ( SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" + 
    		"                         WHERE     VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                               AND SD.SAC_LED_TYPE IN\r\n" + 
    		"                                      (SELECT CPD_ID\r\n" + 
    		"                                         FROM TB_COMPARAM_DET D\r\n" + 
    		"                                        WHERE     D.CPM_ID IN\r\n" + 
    		"                                                     (SELECT CPM_ID\r\n" + 
    		"                                                        FROM TB_COMPARAM_MAS\r\n" + 
    		"                                                             M\r\n" + 
    		"                                                       WHERE M.CPM_PREFIX =\r\n" + 
    		"                                                                'FTY')\r\n" + 
    		"                                              AND CPD_VALUE IN ('VD', 'OT'))\r\n" + 
    		"                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate AND :toDate \r\n" + 
    		"                               AND VD.ORGID = :orgId\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 VD.PAC_HEAD_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL2 = R.PAC_HEAD_ID  \r\n" + 
    		"GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) inc\r\n" + 
    		"ON pac.PAC_HEAD_COMPO_CODE = INC.PAC_HEAD_COMPO_CODE AND pac.PAC_HEAD_DESC = INC.PAC_HEAD_DESC\r\n" + 
    		"where pac.PAC_HEAD_COMPO_CODE = :primaryAcHeadId and pac.CPD_ID_ACHEADTYPES in(:cpdAcIdTypes)\r\n" + 
    		"group by  pac.PAC_HEAD_COMPO_CODE,pac.PAC_HEAD_DESC,pac.SCH_CODE\r\n" + 
    		"ORDER BY pac.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryExpenditureReportData(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("primaryAcHeadId") Long primaryAcHeadId,@Param("cpdAcIdTypes") long incExpId);

    @Query(value = "SELECT PM.PAC_HEAD_CODE,PM.PAC_HEAD_DESC,PM.CPD_ID_ACHEADTYPES,PM.orgid,pm.PAC_HEAD_ID FROM TB_AC_PRIMARYHEAD_MASTER PM WHERE PM.PAC_HEAD_PARENT_ID IS NULL", nativeQuery = true)
    List<Object[]> getPrimaryHeadId();

    @Query(value = "SELECT C.PAC_HEAD_COMPO_CODE PAC_HEAD_COMPO_CODE,\r\n" +
            "       C.PAC_HEAD_DESC HEAD_DESC,\r\n" +
            "       SUM(AAA.OPENBAL_AMT_CR) - SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_CR,\r\n" +
            "       SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" +
            "       SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" +
            "  FROM (SELECT AC_HEAD_CODE,\r\n" +
            "               SAC_HEAD_ID,\r\n" +
            "               OPENBAL_AMT_DR,\r\n" +
            "               OPENBAL_AMT_CR,\r\n" +
            "               VAMT_CR,\r\n" +
            "               VAMT_DR,\r\n" +
            "               A.PAC_HEAD_ID\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       A.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                               PAC_HEAD_ID\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
            "                        GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, PAC_HEAD_ID)\r\n" +
            "                       A\r\n" +
            "                       LEFT JOIN\r\n" +
            "                       (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                               B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                               (CASE\r\n" +
            "                                   WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                   THEN\r\n" +
            "                                      BG.OPENBAL_AMT\r\n" +
            "                                   ELSE\r\n" +
            "                                      0\r\n" +
            "                                END)\r\n" +
            "                                  AS OPENBAL_AMT_DR,\r\n" +
            "                               (CASE\r\n" +
            "                                   WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                   THEN\r\n" +
            "                                      BG.OPENBAL_AMT\r\n" +
            "                                   ELSE\r\n" +
            "                                      0\r\n" +
            "                                END)\r\n" +
            "                                  AS OPENBAL_AMT_CR,\r\n" +
            "                               B.PAC_HEAD_ID\r\n" +
            "                          FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                               TB_COMPARAM_DET CD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                         WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                               AND BG.ORGID =:orgId\r\n" +
            "                               AND BG.FA_YEARID =:faYearId\r\n" +
            "                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                               AND B.ORGID = BG.ORGID) E\r\n" +
            "                          ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1\r\n" +
            "                UNION\r\n" +
            "                SELECT E.AC_HEAD_CODE,\r\n" +
            "                       E.SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       E.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                               VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
            "                        GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" +
            "                       RIGHT JOIN\r\n" +
            "                       (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                               B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                               (CASE\r\n" +
            "                                   WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                   THEN\r\n" +
            "                                      BG.OPENBAL_AMT\r\n" +
            "                                   ELSE\r\n" +
            "                                      0\r\n" +
            "                                END)\r\n" +
            "                                  AS OPENBAL_AMT_DR,\r\n" +
            "                               (CASE\r\n" +
            "                                   WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                   THEN\r\n" +
            "                                      BG.OPENBAL_AMT\r\n" +
            "                                   ELSE\r\n" +
            "                                      0\r\n" +
            "                                END)\r\n" +
            "                                  AS OPENBAL_AMT_CR,\r\n" +
            "                               B.AC_HEAD_CODE,\r\n" +
            "                               B.SAC_HEAD_ID,\r\n" +
            "                               B.PAC_HEAD_ID\r\n" +
            "                          FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                               TB_COMPARAM_DET CD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                         WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                               AND BG.ORGID =:orgId\r\n" +
            "                               AND BG.FA_YEARID =:faYearId\r\n" +
            "                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                               AND B.ORGID = BG.ORGID) E\r\n" +
            "                          ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "         WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            " WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" +
            "       AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "       AND C.PAC_HEAD_PARENT_ID =:primaryAcHeadId\r\n" +
            "GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID ", nativeQuery = true)
    List<Object[]> queryLiabilitiesReportData(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("primaryAcHeadId") Long primaryAcHeadId, @Param("faYearId") Long faYearId);

    @Query(value = "SELECT C.PAC_HEAD_COMPO_CODE PAC_HEAD_COMPO_CODE,\r\n" +
            "       C.PAC_HEAD_DESC HEAD_DESC,\r\n" +
            "       SUM(AAA.OPENBAL_AMT_DR) - SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_DR,\r\n" +
            "       SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" +
            "       SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" +
            "  FROM (SELECT AC_HEAD_CODE,\r\n" +
            "               SAC_HEAD_ID,\r\n" +
            "               OPENBAL_AMT_DR,\r\n" +
            "               OPENBAL_AMT_CR,\r\n" +
            "               VAMT_CR,\r\n" +
            "               VAMT_DR,\r\n" +
            "               A.PAC_HEAD_ID\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       A.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                               PAC_HEAD_ID\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
            "                        GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, PAC_HEAD_ID)\r\n" +
            "                       A\r\n" +
            "                       LEFT JOIN\r\n" +
            "                       (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                               B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                               (CASE\r\n" +
            "                                   WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                   THEN\r\n" +
            "                                      BG.OPENBAL_AMT\r\n" +
            "                                   ELSE\r\n" +
            "                                      0\r\n" +
            "                                END)\r\n" +
            "                                  AS OPENBAL_AMT_DR,\r\n" +
            "                               (CASE\r\n" +
            "                                   WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                   THEN\r\n" +
            "                                      BG.OPENBAL_AMT\r\n" +
            "                                   ELSE\r\n" +
            "                                      0\r\n" +
            "                                END)\r\n" +
            "                                  AS OPENBAL_AMT_CR,\r\n" +
            "                               B.PAC_HEAD_ID\r\n" +
            "                          FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                               TB_COMPARAM_DET CD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                         WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                               AND BG.ORGID =:orgId\r\n" +
            "                               AND BG.FA_YEARID =:faYearId\r\n" +
            "                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                               AND B.ORGID = BG.ORGID) E\r\n" +
            "                          ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1\r\n" +
            "                UNION\r\n" +
            "                SELECT E.AC_HEAD_CODE,\r\n" +
            "                       E.SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       E.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                               VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
            "                        GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" +
            "                       RIGHT JOIN\r\n" +
            "                       (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                               B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                               (CASE\r\n" +
            "                                   WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                   THEN\r\n" +
            "                                      BG.OPENBAL_AMT\r\n" +
            "                                   ELSE\r\n" +
            "                                      0\r\n" +
            "                                END)\r\n" +
            "                                  AS OPENBAL_AMT_DR,\r\n" +
            "                               (CASE\r\n" +
            "                                   WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                   THEN\r\n" +
            "                                      BG.OPENBAL_AMT\r\n" +
            "                                   ELSE\r\n" +
            "                                      0\r\n" +
            "                                END)\r\n" +
            "                                  AS OPENBAL_AMT_CR,\r\n" +
            "                               B.AC_HEAD_CODE,\r\n" +
            "                               B.SAC_HEAD_ID,\r\n" +
            "                               B.PAC_HEAD_ID\r\n" +
            "                          FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                               TB_COMPARAM_DET CD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                         WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                               AND BG.ORGID =:orgId\r\n" +
            "                               AND BG.FA_YEARID =:faYearId\r\n" +
            "                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                               AND B.ORGID = BG.ORGID) E\r\n" +
            "                          ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "         WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            " WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" +
            "       AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "       AND C.PAC_HEAD_PARENT_ID =:primaryAcHeadId\r\n" +
            "GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID\r\n" +
            "ORDER BY PAC_HEAD_COMPO_CODE", nativeQuery = true)
    List<Object[]> queryAssetsReportData(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("primaryAcHeadId") Long primaryAcHeadId, @Param("faYearId") Long faYearId);
    
    @Query(value = "SELECT Z.* FROM\r\n" + 
    		"(Select SMD.SAC_HEAD_ID,\r\n" + 
    		"SMD.AC_HEAD_CODE,\r\n" + 
    		"sum(coalesce(A.Estimated_amt,0)) Estimated_amt,\r\n" + 
    		"sum(coalesce(A.adminapproval_amt,0)) adminapproval_amt,\r\n" + 
    		"sum(coalesce(A.TENDER_AMT,0)) TENDER_AMT,\r\n" + 
    		"sum(coalesce(TDSHAM.WorkOrder_Amount,0)) WorkOrder_Amount,\r\n" + 
    		"sum(coalesce(B.Tilldatepending,0)) Tilldatepending\r\n" + 
    		"From\r\n" + 
    		"tb_ac_secondaryhead_master  SMD\r\n" + 
    		"LEFT JOIN\r\n" + 
    		"(SELECT SHM.SAC_HEAD_ID AS SAC_HEAD_ID,SHM.AC_HEAD_CODE,ABm.DP_DEPTID,\r\n" + 
    		"        sum(coalesce(ACD.BCH_CHARGES_AMT,0)) Tilldatepending\r\n" + 
    		"FROM   tb_ac_bill_mas ABM\r\n" + 
    		"INNER JOIN  tb_ac_bill_exp_detail ACD ON ACD.BM_ID = ABM.BM_ID\r\n" + 
    		"LEFT JOIN tb_wms_rabill RB ON ABM.BM_BILLNO=RB.RA_BILLNO\r\n" + 
    		"LEFT JOIN tb_wms_workdefination WD ON WD.WORK_ID = RB.WORK_ID\r\n" + 
    		"LEFT JOIN tb_wms_workdefination_year_det WDD ON WDD.WORK_ID = WD.WORK_ID\r\n" + 
    		"LEFT JOIN tb_ac_secondaryhead_master SHM ON ACD.SAC_HEAD_ID = SHM.SAC_HEAD_ID\r\n" + 
    		"LEFT JOIN tb_wms_workestimate_mas WE ON RB.WORK_ID = WE.WORK_ID\r\n" + 
    		"LEFT JOIN tb_wms_measurementbook_det MBDET ON  MBDET.WORKE_ID = WE.WORKE_ID\r\n" + 
    		"LEFT JOIN tb_wms_tender_work TW ON TW.WORK_ID = WD.WORK_ID\r\n" + 
    		"LEFT JOIN tb_wms_tender_mast TM ON TM.TND_ID = TW.TND_ID\r\n" + 
    		"LEFT JOIN tb_wms_workeorder WO ON WO.CONT_ID = TW.CONT_ID\r\n" + 
    		"LEFT JOIN tb_contract_detail CD ON CD.CONT_ID = WO.CONT_ID\r\n" + 
    		"INNER JOIN tb_financialyear FY ON  ABM.BM_ENTRYDATE BETWEEN FY.FA_FROMDATE AND FY.FA_TODATE\r\n" + 
    		"WHERE  ABM.ORGID=:orgId and ABM.BM_PAY_STATUS='N' and\r\n" + 
    		"FY.FA_YEARID =:finYearId AND TW.CONT_ID is not  null and TM.TND_STATUS='A'\r\n" + 
    		"  and WD.DP_DEPTID=:deptId and ACD.SAC_HEAD_ID=:sacHeadId   and ABM.FIELD_ID=:fieldId\r\n" + 
    		"Group By SHM.SAC_HEAD_ID,SHM.AC_HEAD_CODE,DP_DEPTID\r\n" + 
    		")B\r\n" + 
    		"ON\r\n" + 
    		"SMD.SAC_HEAD_ID=B.SAC_HEAD_ID\r\n" + 
    		"LEFT JOIN\r\n" + 
    		"(SELECT SHM.SAC_HEAD_ID AS SAC_HEAD_ID,BDDM.BUDGETCODE_ID,\r\n" + 
    		"        sum(distinct coalesce(TW.TND_AMOUNT,0)) AS TENDER_AMT, sum(distinct coalesce(CD.CONT_AMOUNT,0))  as WorkOrder_Amount,\r\n" + 
    		"        sum(distinct coalesce(ORGINAL_ESTAMT,0)) Estimated_amt,\r\n" + 
    		"                 sum(coalesce(case when WD.WORK_STATUS='A' then coalesce(WE.WORKE_AMounT,0) end,0)) as adminapproval_amt\r\n" + 
    		"FROM  tb_ac_projected_expenditure PREX\r\n" + 
    		"INNER JOIN tb_ac_budgetcode_mas BDDM ON BDDM.BUDGETCODE_ID=PREX.BUDGETCODE_ID and BDDM.ORGID=PREX.ORGID\r\n" + 
    		"INNER JOIN tb_ac_secondaryhead_master SHM on BDDM.SAC_HEAD_ID=SHM.SAC_HEAD_ID and PREX.ORGID=SHM.ORGID\r\n" + 
    		"INNER JOIN  tb_wms_workdefination_year_det WDD ON SHM.SAC_HEAD_ID = WDD.SAC_HEAD_ID and WDD.FA_YEARID=PREX.FA_YEARID and  PREX.ORGID=WDD.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_workdefination WD ON WD.WORK_ID = WDD.WORK_ID   and WD.orgid=WDD.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_workestimate_mas WE ON WD.WORK_ID = WE.WORK_ID and WD.ORGID = WE.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_tender_work TW ON TW.WORK_ID = WD.WORK_ID    AND TW.CONT_ID is  null and WE.ORGID = TW.ORGID\r\n" + 
    		"LEFT JOIN tb_wms_tender_mast TM ON TM.TND_ID = TW.TND_ID and TM.TND_STATUS='A'\r\n" + 
    		"LEFT JOIN tb_wms_workeorder WO ON WO.CONT_ID = TW.CONT_ID\r\n" + 
    		"LEFT JOIN tb_contract_detail CD ON CD.CONT_ID = WO.CONT_ID\r\n" + 
    		"WHERE PREX.ORGID=:orgId AND PREX.FA_YEARID =:finYearId\r\n" + 
    		"  and PREX.FIELD_ID=:fieldId\r\n" + 
    		"and PREX.DP_DEPTID=:deptId and SHM.SAC_HEAD_ID=:sacHeadId\r\n" + 
    		"Group By SHM.SAC_HEAD_ID,BDDM.BUDGETCODE_ID\r\n" + 
    		"UNION\r\n" + 
    		"SELECT SHM.SAC_HEAD_ID AS SAC_HEAD_ID,BDDM.BUDGETCODE_ID,\r\n" + 
    		"        sum(0) AS TENDER_AMT, sum(0)  as WorkOrder_Amount,\r\n" + 
    		"        sum(distinct coalesce(ORGINAL_ESTAMT,0)) Estimated_amt,\r\n" + 
    		"                 sum(distinct coalesce(WD.PROPOSAL_AMOUNT,0)) as adminapproval_amt\r\n" + 
    		"FROM tb_cmt_council_proposal_mast WD\r\n" + 
    		"LEFT JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = WD.SAC_HEAD_ID\r\n" + 
    		"left join   tb_ac_budgetcode_mas BDDM on BDDM.SAC_HEAD_ID=SHM.SAC_HEAD_ID\r\n" + 
    		"left join  tb_ac_projected_expenditure PREX on BDDM.BUDGETCODE_ID=PREX.BUDGETCODE_ID and WD.YEAR_ID=PREX.FA_YEARID\r\n" + 
    		"WHERE WD.ORGID=:orgId AND\r\n" + 
    		"WD.YEAR_ID =:finYearId   and PREX.FIELD_ID=:fieldId\r\n" + 
    		"and PREX.DP_DEPTID=:deptId and SHM.SAC_HEAD_ID=:sacHeadId\r\n" + 
    		"Group By SHM.SAC_HEAD_ID,BDDM.BUDGETCODE_ID\r\n" + 
    		")A ON SMD.SAC_HEAD_ID=A.SAC_HEAD_ID\r\n" + 
    		"left join (SELECT SHM.SAC_HEAD_ID AS SAC_HEAD_ID,\r\n" + 
    		"        sum(distinct coalesce(CD.CONT_AMOUNT,0))  as WorkOrder_Amount\r\n" + 
    		"FROM  tb_wms_workdefination WD\r\n" + 
    		"INNER JOIN tb_wms_workdefination_year_det WDD ON WDD.WORK_ID = WD.WORK_ID\r\n" + 
    		"LEFT JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = WDD.SAC_HEAD_ID\r\n" + 
    		"INNER JOIN tb_wms_workestimate_mas WE ON WD.WORK_ID = WE.WORK_ID\r\n" + 
    		"LEFT JOIN tb_wms_measurementbook_det MBDET ON  MBDET.WORKE_ID = WE.WORKE_ID\r\n" + 
    		"LEFT JOIN tb_wms_tender_work TW ON TW.WORK_ID = WD.WORK_ID\r\n" + 
    		"LEFT JOIN tb_wms_tender_mast TM ON TM.TND_ID = TW.TND_ID and TM.TND_STATUS='A'\r\n" + 
    		"LEFT JOIN tb_wms_workeorder WO ON WO.CONT_ID = TW.CONT_ID\r\n" + 
    		"LEFT JOIN tb_contract_detail CD ON CD.CONT_ID = WO.CONT_ID\r\n" + 
    		"LEFT JOIN tb_wms_workdefination_wardzone_det WZD ON WZD.WORK_ID=WD.WORK_ID\r\n" + 
    		"LEFT JOIN TB_LOCATION_OPER_WARDZONE OPWZ  ON  OPWZ.COD_ID_OPER_LEVEL1=WZD.COD_ID1 and WD.DP_DEPTID=OPWZ.DP_DEPTID\r\n" + 
    		"LEFT JOIN TB_LOCATION_REVENUE_WARDZONE loc on loc.LOC_ID=OPWZ.LOC_ID\r\n" + 
    		"WHERE WD.ORGID=:orgId AND WDD.FA_YEARID =:finYearId and SHM.SAC_HEAD_ID=:sacHeadId\r\n" + 
    		"AND WD.DP_DEPTID=:deptId  and loc.COD_ID_REV_LEVEL1=:fieldId\r\n" + 
    		"AND TW.CONT_ID is not null\r\n" + 
    		"Group By SHM.SAC_HEAD_ID) TDSHAM ON SMD.SAC_HEAD_ID=TDSHAM.SAC_HEAD_ID\r\n" + 
    		"Group by SMD.AC_HEAD_CODE, SMD.SAC_HEAD_ID)Z\r\n" + 
    		"WHERE(coalesce(Z.TENDER_AMT,0)) !=0 OR(coalesce(Z.WorkOrder_Amount,0)) !=0 OR\r\n" + 
    		"(coalesce(Z.Estimated_amt,0)) !=0 OR(coalesce(Z.adminapproval_amt,0)) !=0 OR\r\n" + 
    		"(coalesce(Z.Tilldatepending,0)) !=0\r\n" + 
    		" ", nativeQuery = true)
    List<Object[]> getExpenditureDetails(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("sacHeadId") Long sacHeadId,@Param("fieldId") Long fieldId,@Param("deptId") Long deptId);

    @Query(value = "Select distinct case when bill.SAC_HEAD_ID or WD.SAC_HEAD_ID or CBD.SAC_HEAD_ID is not null then 'Y' else 'N' end SAC_ID\r\n" + 
    		"FROM TB_AC_BUDGETCODE_MAS a\r\n" + 
    		"LEFT JOIN tb_ac_bill_exp_detail bill on bill.SAC_HEAD_ID=a.SAC_HEAD_ID\r\n" + 
    		"LEFT JOIN TB_WMS_WORKDEFINATION_YEAR_DET WD on WD.SAC_HEAD_ID=a.SAC_HEAD_ID\r\n" + 
    		"LEFT JOIN tb_cmt_councilBudet_Det CBD ON CBD.SAC_HEAD_ID=a.SAC_HEAD_ID\r\n" + 
    		"where a.BUDGETCODE_ID =:prBudgetCodeid and a.ORGID =:orgId", nativeQuery = true)
    String checkSacHeadId(@Param("prBudgetCodeid") Long prBudgetCodeid,@Param("orgId") Long orgId);
}
