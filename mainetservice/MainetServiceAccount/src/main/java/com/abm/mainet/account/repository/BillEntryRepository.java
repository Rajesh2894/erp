package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;

/**
 * @author tejas.kotekar
 *
 */
public interface BillEntryRepository extends PagingAndSortingRepository<AccountBillEntryMasterEnitity, Long> {

    /**
     * @param orgId
     * @param expenditureId
     * @return
     */
    @Query("select e from AccountBudgetProjectedExpenditureEntity e where e.orgid=:orgId and e.faYearid =:finYearId and e.tbAcBudgetCodeMaster.prBudgetCodeid =:budgetCodeId")
    List<AccountBudgetProjectedExpenditureEntity> getProjectedExpenditureDetailsForView(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId);

    /**
     * @param orgId
     * @return
     */
    @Query("select m from AccountBillEntryMasterEnitity m where m.orgId =:orgId")
    List<AccountBillEntryMasterEnitity> getBillEntryDetailsByOrgId(@Param("orgId") Long orgId);

    /**
     * @param orgId
     * @param bmId
     * @return
     */

    @Query("select bm from AccountBillEntryMasterEnitity bm where bm.orgId=:orgId and bm.id =:id")
    AccountBillEntryMasterEnitity findBillEntryById(@Param("orgId") Long orgId, @Param("id") Long billId);

    /**
     * @param projectedExpId
     * @param newBalanceAmount
     */
    @Modifying
    @Query("update AccountBudgetProjectedExpenditureEntity exp set exp.expenditureAmt =:newBalanceAmount where exp.prExpenditureid =:expId")
    void updateProjectedExpenditureBalanceAmount(@Param("expId") Long projectedExpId,
            @Param("newBalanceAmount") BigDecimal newBalanceAmount);

    @Query("select s.sacHeadId,s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode,s.sacHeadDesc from AccountHeadSecondaryAccountCodeMasterEntity s where s.orgid=:orgId and s.sacHeadId=:sacHeadId order by s.tbAcPrimaryheadMaster.primaryAcHeadCompcode,s.sacHeadCode asc")
    List<Object[]> getPrimarySecondaryHeads(@Param("orgId") Long orgId, @Param("sacHeadId") Long sacHeadId);

    /**
     * @param orgId
     * @return
     */
    @Query("select bm from AccountBillEntryMasterEnitity bm where bm.orgId=:orgId")
    List<AccountBillEntryMasterEnitity> getAllBillEntryData(@Param("orgId") Long orgId);
    
    @Query("select bm from AccountBillEntryMasterEnitity bm where bm.orgId=:orgId and bm.payStatus<>'Y' and bm.checkerAuthorization='Y'")
    List<AccountBillEntryMasterEnitity> getAllPendingPaymentBillEntryData(@Param("orgId") Long orgId);

    /**
     * @param orgId
     * @param finYearId
     * @return
     */
    @Query("select e from AccountBudgetProjectedExpenditureEntity e  where e.orgid=:orgId and e.faYearid =:finYearId and e.orginalEstamt !='0.0' ")
    List<AccountBudgetProjectedExpenditureEntity> findExpenditureDataByFinYearId(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId);

    @Query("SELECT DISTINCT (sm.sacHeadId)  FROM AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " WHERE sm.vmVendorid =:vendorId "
            + " AND sm.orgid =:orgId AND sm.sacStatusCpdId =:status")
    Long getVendorSacHeadIdByVendorId(@Param("vendorId") Long vendorId, @Param("orgId") Long orgId, @Param("status") Long status);

    // the bellow query added by Prasad.K, purpose is getting all bill charges amount and showing on Expenditure Budget Form time.

    @Query(value = "SELECT \r\n" +
            "    SUM(pd.PAYMENT_AMT)\r\n" +
            "FROM\r\n" +
            "    tb_ac_budgetcode_mas bm,\r\n" +
            "    tb_ac_secondaryhead_master sm,\r\n" +
            "    tb_ac_function_master fm,\r\n" +
            "    tb_ac_primaryhead_master pm,\r\n" +
            "    tb_ac_projected_expenditure pr,\r\n" +
            "    tb_ac_payment_mas ps,\r\n" +
            "    tb_ac_payment_det pd\r\n" +
            "WHERE\r\n" +
            "    sm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" +
            "        AND sm.FUNCTION_ID = fm.FUNCTION_ID\r\n" +
            "        AND sm.SAC_HEAD_ID = pd.BUDGETCODE_ID\r\n" +
            "        AND ps.PAYMENT_ID = pd.PAYMENT_ID\r\n" +
            "        AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n" +
            "        AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n" +
            "        AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" +
            "        AND ps.PAYMENT_DEL_FLAG IS NULL\r\n" +
            "        AND bm.ORGID = pr.ORGID\r\n" +
            "        AND ps.ORGID = pd.ORGID\r\n" +
            "        AND sm.ORGID=ps.ORGID\r\n" +
            "        and sm.ORGID=pd.ORGID\r\n" +
            "        and ps.payment_date between :fromDate and :toDate\r\n" +
            "        AND pr.BUDGETCODE_ID =:prBudgetCodeId\r\n" +
            "        AND pr.FA_YEARID =:faYearid\r\n" +
            "        AND pd.ORGID =:orgId", nativeQuery = true)

    public BigDecimal getAllExpenditureAmount(@Param("faYearid") Long faYearid, @Param("prBudgetCodeId") Long prBudgetCodeId,
            @Param("orgId") Long orgId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value = "SELECT \r\n" +
            "    SUM(pd.PAYMENT_AMT)\r\n" +
            "FROM\r\n" +
            "    tb_ac_budgetcode_mas bm,\r\n" +
            "    tb_ac_secondaryhead_master sm,\r\n" +
            "    tb_ac_function_master fm,\r\n" +
            "    tb_ac_primaryhead_master pm,\r\n" +
            "    tb_ac_projected_expenditure pr,\r\n" +
            "    tb_ac_payment_mas ps,\r\n" +
            "    tb_ac_payment_det pd,\r\n" +
            "    tb_ac_bill_mas  b\r\n" +
            "WHERE\r\n" +
            "    sm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" +
            "        AND sm.FUNCTION_ID = fm.FUNCTION_ID\r\n" +
            "        AND sm.SAC_HEAD_ID = pd.BUDGETCODE_ID\r\n" +
            "        AND ps.PAYMENT_ID = pd.PAYMENT_ID\r\n" +
            "        AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n" +
            "        AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n" +
            "        AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" +
            "        AND ps.PAYMENT_DEL_FLAG IS NULL\r\n" +
            "        AND bm.ORGID = pr.ORGID\r\n" +
            "        AND ps.ORGID = pd.ORGID\r\n" +
            "        AND sm.ORGID=ps.ORGID\r\n" +
            "        and sm.ORGID=pd.ORGID\r\n" +
            "        and ps.payment_date between :fromDate and :toDate\r\n" +
            "        AND pr.BUDGETCODE_ID =:prBudgetCodeId\r\n" +
            "        AND pr.FA_YEARID =:faYearid\r\n" +
            "        AND pr.DP_DEPTID =:deptId\r\n" +
            "        AND pd.ORGID =:orgId \r\n" +
            "        AND pd.ORGID = b.ORGID\r\n" + 
            "        AND pd.bm_id = b.bm_id\r\n" + 
            "        AND b.BM_DEL_FLAG  is null\r\n" + 
            "	     AND b.DP_DEPTID=:deptId", nativeQuery = true)
    public BigDecimal getAllExpenditureAmountBasedOnDeptId(@Param("faYearid") Long faYearid, @Param("prBudgetCodeId") Long prBudgetCodeId,
            @Param("orgId") Long orgId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("deptId") Long depetId);
    
    @Query(value = "SELECT \r\n" +
            "    SUM(pd.PAYMENT_AMT)\r\n" +
            "FROM\r\n" +
            "    tb_ac_budgetcode_mas bm,\r\n" +
            "    tb_ac_secondaryhead_master sm,\r\n" +
            "    tb_ac_function_master fm,\r\n" +
            "    tb_ac_primaryhead_master pm,\r\n" +
            "    tb_ac_projected_expenditure pr,\r\n" +
            "    tb_ac_payment_mas ps,\r\n" +
            "    tb_ac_payment_det pd,\r\n" +
            "    tb_ac_bill_mas  b\r\n" +
            "WHERE\r\n" +
            "    sm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" +
            "        AND sm.FUNCTION_ID = fm.FUNCTION_ID\r\n" +
            "        AND sm.SAC_HEAD_ID = pd.BUDGETCODE_ID\r\n" +
            "        AND ps.PAYMENT_ID = pd.PAYMENT_ID\r\n" +
            "        AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n" +
            "        AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n" +
            "        AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" +
            "        AND ps.PAYMENT_DEL_FLAG IS NULL\r\n" +
            "        AND bm.ORGID = pr.ORGID\r\n" +
            "        AND ps.ORGID = pd.ORGID\r\n" +
            "        AND sm.ORGID=ps.ORGID\r\n" +
            "        AND pr.FIELD_ID=b.FIELD_ID\r\n" +
            "        and sm.ORGID=pd.ORGID\r\n" +
            "        and ps.payment_date between :fromDate and :toDate\r\n" +
            "        AND pr.BUDGETCODE_ID =:prBudgetCodeId\r\n" +
            "        AND pr.FA_YEARID =:faYearid\r\n" +
            "        AND pr.DP_DEPTID =:deptId\r\n" +
            "        AND pd.ORGID =:orgId \r\n" +
            "        AND pd.ORGID = b.ORGID\r\n" + 
            "        AND pd.bm_id = b.bm_id\r\n" + 
            "        AND pr.FIELD_ID =:fieldId \r\n" + 
            "        AND b.BM_DEL_FLAG  is null\r\n" + 
            "	     AND b.DP_DEPTID=:deptId", nativeQuery = true)
    public BigDecimal getAllExpenditureAmountBasedOnDeptIdFieldId(@Param("faYearid") Long faYearid, @Param("prBudgetCodeId") Long prBudgetCodeId,
            @Param("orgId") Long orgId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("deptId") Long depetId ,@Param("fieldId") Long fieldId);
    
    @Query("select sum(d.billChargesAmount) from AccountBillEntryMasterEnitity m ,AccountBillEntryExpenditureDetEntity d ,AccountBudgetProjectedExpenditureEntity e\r\n" + 
    		" where m.orgId=d.orgid\r\n" + 
    		" and m.id=d.billMasterId.id\r\n" + 
    		" and m.billDeletionFlag is null\r\n" + 
    		" and m.orgId=:orgId\r\n" + 
    		" and m.billEntryDate between :fromDate and :toDate\r\n" + 
    		" and m.checkerAuthorization='Y'\r\n" + 
    		" and m.departmentId.dpDeptid=:deptId"+
    		" and e.orgid=m.orgId"+
    		" and e.tbAcBudgetCodeMaster.prBudgetCodeid=d.budgetCodeId.prBudgetCodeid"+
    		" and e.tbAcBudgetCodeMaster.prBudgetCodeid=:prBudgetCodeId"+
    		" and e.faYearid=:faYearid"+
    		" and e.tbDepartment.dpDeptid=:deptId")
    public BigDecimal getAllAccrualExpenditureAmountBasedOnDeptId(@Param("faYearid") Long faYearid, @Param("prBudgetCodeId") Long prBudgetCodeId,
            @Param("orgId") Long orgId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("deptId") Long depetId);
    
    @Query("select sum(d.billChargesAmount) from AccountBillEntryMasterEnitity m ,AccountBillEntryExpenditureDetEntity d ,AccountBudgetProjectedExpenditureEntity e\r\n" + 
    		" where m.orgId=d.orgid\r\n" + 
    		" and m.id=d.billMasterId.id\r\n" + 
    		" and m.billDeletionFlag is null\r\n" + 
    		" and m.orgId=:orgId\r\n" + 
    		" and m.fieldId=e.fieldId\r\n" + 
    		" and m.fieldId=:fieldId\r\n" + 
    		" and m.billEntryDate between :fromDate and :toDate\r\n" +
    		" and m.departmentId.dpDeptid=:deptId"+
    		" and e.orgid=m.orgId"+
    		" and e.tbAcBudgetCodeMaster.prBudgetCodeid=d.budgetCodeId.prBudgetCodeid"+
    		" and e.tbAcBudgetCodeMaster.prBudgetCodeid=:prBudgetCodeId"+
    		" and e.faYearid=:faYearid"+
    		" and e.tbDepartment.dpDeptid=:deptId")
    public BigDecimal getAllAccrualExpenditureAmountBasedOnDeptIdFieldId(@Param("faYearid") Long faYearid, @Param("prBudgetCodeId") Long prBudgetCodeId,
            @Param("orgId") Long orgId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("deptId") Long depetId,@Param("fieldId") Long fieldId);
    
    @Query(value = "SELECT accountbil0_.BM_ID AS col_0_0_, accountbil0_.BM_BILLNO AS col_1_0_\r\n" +
            "  FROM TB_AC_BILL_MAS accountbil0_\r\n" +
            " WHERE     accountbil0_.ORGID = :orgId\r\n" +
            "       AND accountbil0_.BM_BILLTYPE_CPD_ID = :billTypeId\r\n" +
            "       AND accountbil0_.VM_VENDORID = :vendorId\r\n" +
            "       AND accountbil0_.CHECKER_DATE <= :payDate\r\n" +
            "       AND accountbil0_.CHECKER_AUTHO = 'Y'\r\n" +
            "       AND accountbil0_.BM_PAY_STATUS = 'N'\r\n" +
            "       AND (accountbil0_.BM_DEL_FLAG IS NULL)", nativeQuery = true)

    List<Object[]> getBillNumbers(@Param("orgId") Long orgId, @Param("billTypeId") Long billTytpeId,
            @Param("vendorId") Long vendorId, @Param("payDate") Date payDate);
    
    @Query(value = "SELECT accountbil0_.BM_ID AS col_0_0_, accountbil0_.BM_BILLNO AS col_1_0_\r\n" +
            "  FROM TB_AC_BILL_MAS accountbil0_\r\n" +
            " WHERE     accountbil0_.ORGID = :orgId\r\n" +
            "       AND accountbil0_.BM_BILLTYPE_CPD_ID = :billTypeId\r\n" +
            "       AND accountbil0_.VM_VENDORID = :vendorId\r\n" +
            "       AND accountbil0_.FIELD_ID = :fieldId\r\n" +
            "       AND accountbil0_.CHECKER_DATE <= :payDate\r\n" +
            "       AND accountbil0_.CHECKER_AUTHO = 'Y'\r\n" +
            "       AND accountbil0_.BM_PAY_STATUS = 'N'\r\n" +
            "       AND (accountbil0_.BM_DEL_FLAG IS NULL)", nativeQuery = true)

    List<Object[]> getBillNumbersWithFieldId(@Param("orgId") Long orgId, @Param("billTypeId") Long billTytpeId,
            @Param("vendorId") Long vendorId, @Param("payDate") Date payDate,@Param("fieldId") Long fieldId);
    
    @Query(value = "SELECT accountbil0_.BM_ID AS col_0_0_, accountbil0_.BM_BILLNO AS col_1_0_\r\n" +
            "  FROM TB_AC_BILL_MAS accountbil0_\r\n" +
            " WHERE     accountbil0_.ORGID = :orgId\r\n" +
            "       AND accountbil0_.VM_VENDORID = :vendorId\r\n" +
            "       AND accountbil0_.CHECKER_DATE <= :payDate\r\n" +
            "       AND accountbil0_.CHECKER_AUTHO = 'Y'\r\n" +
            "       AND accountbil0_.BM_PAY_STATUS = 'N'\r\n" +
            "       AND (accountbil0_.BM_DEL_FLAG IS NULL)", nativeQuery = true)

    List<Object[]> getBillNumbers(@Param("orgId") Long orgId,
            @Param("vendorId") Long vendorId, @Param("payDate") Date payDate);

    /**
     * @param billId
     * @param orgId
     * @return
     */
    @Query("select bm from AccountBillEntryMasterEnitity bm where bm.id=:billId and bm.orgId=:orgId")
    List<AccountBillEntryMasterEnitity> getBillDataByBillId(@Param("billId") Long billId, @Param("orgId") Long orgId);

    @Modifying
    @Query("update AccountBillEntryMasterEnitity m set m.payStatus =:payStatus,m.balanceAmount =:paymentAmount where m.id =:billId")
    void updateFullPaymentStatus(@Param("billId") Long billId, @Param("payStatus") Character payStatus,
            @Param("paymentAmount") BigDecimal paymentAmount);

    /**
     * @param billId
     * @param orgId
     * @return
     */
    @Query("select e.id,e.billChargesAmount,e.sacHeadId,e.fi04V1 from AccountBillEntryExpenditureDetEntity e where e.billMasterId.id =:billId and e.orgid =:orgId")
    List<Object[]> getExpenditureDetails(@Param("billId") Long billId, @Param("orgId") Long orgId);

    /**
     * @param billId
     * @param orgId
     * @return
     */
    @Query("select d.id,d.deductionAmount,d.sacHeadId from AccountBillEntryDeductionDetEntity d where d.billMasterId.id =:billId and d.orgid =:orgId")
    List<Object[]> getDeductionDetails(@Param("billId") Long billId, @Param("orgId") Long orgId);

    /**
     * @param billId
     * @param orgId
     * @return sacHeadId
     */
    @Query("select e.sacHeadId from AccountBillEntryExpenditureDetEntity e where   e.billMasterId.id =:billId and e.orgid =:orgId")
    List<Long> getExpenditureDetDetails(@Param("billId") Long billId, @Param("orgId") Long orgId);

    @Query(value = "SELECT Z.BM_BILLNO,\r\n" + 
    		"       Z.BM_ENTRYDATE AS BILL_DATE,\r\n" + 
    		"       Z.VM_VENDORNAME,\r\n" + 
    		"       Z.DP_DEPTID,\r\n" + 
    		"       Z.BM_NARRATION,\r\n" + 
    		"       Z.BM_INVOICEVALUE AS BILL_AMOUNT,\r\n" + 
    		"       Z.DISALLOWED_AMT,\r\n" + 
    		"       Z.ACT_AMT AS SANCTIONED_AMT,\r\n" + 
    		"       X.DEDUCTION_AMT,\r\n" + 
    		"       Z.CHECKER_DATE AS SANCTIONED_DATE,\r\n" + 
    		"       Z.VOU_NO,\r\n" + 
    		"       Z.PAYMENT_AMT,\r\n" + 
    		"       Z.DISALLOWED_REMARK,\r\n" + 
    		"       Z.CHECKER_USER AS AUTHORIZED_USER\r\n" + 
    		"  FROM (SELECT A.BM_ID,\r\n" + 
    		"               A.BM_ENTRYDATE,\r\n" + 
    		"               A.VM_VENDORNAME,\r\n" + 
    		"			   A.DP_DEPTID,\r\n" + 
    		"			   A.BM_BILLNO,\r\n" + 
    		"               A.BM_INVOICEVALUE,\r\n" + 
    		"               A.BM_NARRATION,\r\n" + 
    		"               A.ACT_AMT,\r\n" + 
    		"               A.CHECKER_DATE,\r\n" + 
    		"               A.VOU_NO,\r\n" + 
    		"               A.CHECKER_USER,\r\n" + 
    		"               B.PAYMENT_AMT,\r\n" + 
    		"               A.DISALLOWED_AMT,\r\n" + 
    		"               A.DISALLOWED_REMARK\r\n" + 
    		"          FROM (SELECT A.BM_ID,\r\n" + 
    		"                       A.BM_ENTRYDATE,\r\n" + 
    		"                       A.VM_VENDORNAME,\r\n" + 
    		"                       A.DP_DEPTID,\r\n" + 
    		"                       A.BM_BILLNO,\r\n" + 
    		"                       A.BM_INVOICEVALUE,\r\n" + 
    		"                       SUM(D.BCH_CHARGES_AMT) ACT_AMT,\r\n" + 
    		"                       A.BM_NARRATION,\r\n" + 
    		"                       A.CHECKER_DATE,\r\n" + 
    		"                       VH.VOU_NO,\r\n" + 
    		"                       A.CHECKER_USER,\r\n" + 
    		"                       SUM(D.DISALLOWED_AMT) DISALLOWED_AMT,\r\n" + 
    		"                       D.DISALLOWED_REMARK\r\n" + 
    		"                  FROM TB_AC_BILL_MAS A\r\n" + 
    		"                  LEFT JOIN TB_AC_VOUCHER VH ON  VH.VOU_DATE = A.CHECKER_DATE  AND VH.VOU_REFERENCE_NO = A.BM_BILLNO\r\n" + 
    		"                       AND VH.ORGID = A.ORGID AND VH.DP_DEPTID = A.DP_DEPTID AND VH.VOU_SUBTYPE_CPD_ID = (SELECT CPD_ID FROM TB_COMPARAM_DET D WHERE  D.CPM_ID IN\r\n" + 
    		"                       (SELECT CPM_ID FROM TB_COMPARAM_MAS M WHERE M.CPM_PREFIX = 'TDP')AND CPD_VALUE = 'BI')\r\n" + 
    		"                 INNER JOIN  (SELECT SUM(D.DISALLOWED_AMT) DISALLOWED_AMT,SUM(D.BCH_CHARGES_AMT) BCH_CHARGES_AMT,D.BM_ID,D.DISALLOWED_REMARK\r\n" + 
    		"                          FROM TB_AC_BILL_EXP_DETAIL D GROUP BY D.BM_ID, D.DISALLOWED_REMARK) D ON A.BM_ID = D.BM_ID\r\n" + 
    		"                 INNER JOIN TB_VENDORMASTER V ON A.VM_VENDORID = V.VM_VENDORID\r\n" + 
    		"                 WHERE A.CHECKER_AUTHO = 'Y'\r\n" + 
    		"                       AND A.BM_DEL_FLAG IS NULL\r\n" + 
    		"                       AND A.BM_ENTRYDATE BETWEEN :fromDates AND :toDates\r\n" + 
    		"                       AND A.ORGID =:orgId\r\n" + 
    		"                       AND A.BM_BILLTYPE_CPD_ID=:billType\r\n" + 
    		"                GROUP BY A.VM_VENDORNAME,\r\n" + 
    		"				         A.BM_INVOICENUMBER,\r\n" + 
    		"						 A.BM_BILLNO,\r\n" + 
    		"						 A.BM_INVOICEVALUE,\r\n" + 
    		"						 A.BM_NARRATION,\r\n" + 
    		"                         A.CHECKER_DATE,\r\n" + 
    		"						 VH.VOU_NO,\r\n" + 
    		"						 D.DISALLOWED_REMARK,\r\n" + 
    		"						 A.BM_ENTRYDATE,\r\n" + 
    		"						 A.BM_ID) A\r\n" + 
    		"               LEFT OUTER JOIN\r\n" + 
    		"               (SELECT SUM(PD.PAYMENT_AMT) PAYMENT_AMT, BM.BM_ID\r\n" + 
    		"                  FROM TB_AC_PAYMENT_MAS PM,\r\n" + 
    		"                       TB_AC_PAYMENT_DET PD,\r\n" + 
    		"                       TB_AC_BILL_MAS BM\r\n" + 
    		"                 WHERE     PM.ORGID =:orgId\r\n" + 
    		"                       AND PM.PAYMENT_ID = PD.PAYMENT_ID\r\n" + 
    		"                       AND PD.BM_ID = BM.BM_ID\r\n" + 
    		"                       AND PM.PAYMENT_DATE BETWEEN :fromDates AND :toDates\r\n" + 
    		"                       AND PM.PAYMENT_DEL_FLAG IS NULL\r\n" + 
    		"                       AND BM.BM_BILLTYPE_CPD_ID=:billType\r\n" + 
    		"                GROUP BY BM.BM_ID) B\r\n" + 
    		"                  ON A.BM_ID = B.BM_ID) Z\r\n" + 
    		"       LEFT JOIN (SELECT SUM(C.DEDUCTION_AMT) DEDUCTION_AMT, C.BM_ID\r\n" + 
    		"                    FROM TB_AC_BILL_DEDUCTION_DETAIL C\r\n" + 
    		"                  GROUP BY C.BM_ID) X\r\n" + 
    		"          ON Z.BM_ID = X.BM_ID\r\n" + 
    		"ORDER BY Z.BM_ENTRYDATE, Z.BM_BILLNO", nativeQuery = true)
    List<Object[]> getBillRegisterDetails(@Param("orgId") Long orgId, @Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates,@Param("billType") Long billType);

    @Query("select sm.acHeadCode from AccountBillEntryExpenditureDetEntity e,AccountHeadSecondaryAccountCodeMasterEntity sm where sm.sacHeadId=e.sacHeadId and e.billMasterId.id =:billId and e.orgid =:orgId")
    List<String> getExpenditureDetailHead(@Param("billId") Long billId, @Param("orgId") Long orgId);

    /**
     * @param billId
     * @param orgId
     * @return
     */
    @Query("select e.id from AccountBillEntryExpenditureDetEntity e where e.sacHeadId=:bchId and e.billMasterId.id =:billId and e.orgid =:orgId")
    Long getExpenditureBchId(@Param("bchId") Long bchId, @Param("billId") Long billId, @Param("orgId") Long orgId);

    @Modifying
    @Query("update AccountBillEntryExpenditureDetEntity exp set exp.fi04V1 =:newBalAmt where exp.id =:bchIdExpenditure")
    void updateExpenditureBalanceAmt(@Param("bchIdExpenditure") Long bchIdExpenditure,
            @Param("newBalAmt") String newBalAmt);

    @Query("select e.id,e.fi04V1 from AccountBillEntryExpenditureDetEntity e where e.id=:bchId and e.orgid =:orgId")
    List<Object[]> getExpenditurePaymentAmt(@Param("bchId") Long bchId, @Param("orgId") Long orgId);

    @Modifying
    @Query("update AccountBillEntryDeductionDetEntity ded set ded.deductionBalAmt=:newBalanceAmount  where ded.id =:bdhId")
    void updateDeductionBalanceAmount(@Param("bdhId") Long bdhId,
            @Param("newBalanceAmount") BigDecimal newBalanceAmount);

    @Query(value = "SELECT A.BM_BILLNO,\r\n" +
            "       A.VM_VENDORNAME,\r\n" +
            "       A.BM_NARRATION,\r\n" +
            "       SM.AC_HEAD_CODE,\r\n" +
            "       A.CHECKER_DATE,\r\n" +
            "       D.BCH_CHARGES_AMT,\r\n" +
            "       SM.FUND_ID,\r\n" +
            "       A.CHECKER_REMARKS\r\n" +
            "  FROM TB_AC_BILL_MAS A,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "       (SELECT SUM(D.BCH_CHARGES_AMT) BCH_CHARGES_AMT, D.BM_ID, D.SAC_HEAD_ID\r\n" +
            "          FROM TB_AC_BILL_EXP_DETAIL D\r\n" +
            "        GROUP BY D.BM_ID, D.SAC_HEAD_ID) D,\r\n" +
            "       TB_VENDORMASTER V\r\n" +
            " WHERE     A.VM_VENDORID = V.VM_VENDORID\r\n" +
            "       AND A.BM_ID = D.BM_ID\r\n" +
            "       AND D.SAC_HEAD_ID = SM.SAC_HEAD_ID\r\n" +
            "       AND A.CHECKER_AUTHO = 'Y'\r\n" +
            "       AND A.BM_PAY_STATUS = 'N'\r\n" +
            "       AND A.BM_DEL_FLAG IS NULL\r\n" +
            "       AND A.CHECKER_DATE <=:fromdate\r\n" +
            "       AND A.ORGID =:orgId\r\n" +
            "       AND A.DP_DEPTID =:dpDeptid\r\n" +
            "ORDER BY A.CHECKER_DATE, A.BM_BILLNO", nativeQuery = true)
    List<Object[]> getOutStandingBillRegisterDetails(@Param("orgId") Long orgId, @Param("fromdate") Date fromdate,
            @Param("dpDeptid") Long dpDeptid);
    
    //defect id 85228
    @Query(value = "SELECT A.BM_BILLNO,\r\n" +
            "       ifnull(CONCAT(V.VM_VENDORCODE,'-',V.VM_VENDORNAME),A.VM_VENDORNAME) VM_VENDORNAME,\r\n" +
            "       A.BM_NARRATION,\r\n" +
            "       SM.AC_HEAD_CODE,\r\n" +
            "       A.CHECKER_DATE,\r\n" +
            "       D.BCH_CHARGES_AMT,\r\n" +
            "        F.FUND_DESC,\r\n" +
            "       A.CHECKER_REMARKS\r\n" +
            "  FROM TB_AC_BILL_MAS A LEFT OUTER JOIN tb_ac_fund_master F ON\r\n" + 
            "         A.FUND_ID = F.FUND_ID,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "       (SELECT SUM(D.BCH_CHARGES_AMT) BCH_CHARGES_AMT, D.BM_ID, D.SAC_HEAD_ID\r\n" +
            "          FROM TB_AC_BILL_EXP_DETAIL D\r\n" +
            "        GROUP BY D.BM_ID, D.SAC_HEAD_ID) D,\r\n" +
            "       TB_VENDORMASTER V\r\n" +
            " WHERE     A.VM_VENDORID = V.VM_VENDORID\r\n" +
            "       AND A.BM_ID = D.BM_ID\r\n" +
            "       AND D.SAC_HEAD_ID = SM.SAC_HEAD_ID\r\n" +
            "       AND A.CHECKER_AUTHO = 'Y'\r\n" +
            "       AND A.BM_PAY_STATUS = 'N'\r\n" +
            "       AND A.BM_DEL_FLAG IS NULL\r\n" +
            "       AND A.CHECKER_DATE <=:fromdate\r\n" +
            "       AND A.ORGID =:orgId\r\n" +
            "       AND A.DP_DEPTID =:dpDeptid\r\n" +
            "ORDER BY A.CHECKER_DATE, A.BM_BILLNO", nativeQuery = true)
    List<Object[]> getOutStandingBillRegisterDetailsnNew(@Param("orgId") Long orgId, @Param("fromdate") Date fromdate,
            @Param("dpDeptid") Long dpDeptid);
    
    
    @Query(value = "SELECT A.BM_BILLNO,\r\n" +
            "       ifnull(CONCAT(V.VM_VENDORCODE,'-',V.VM_VENDORNAME),A.VM_VENDORNAME) VM_VENDORNAME,\r\n" +
            "       A.BM_NARRATION,\r\n" +
            "       SM.AC_HEAD_CODE,\r\n" +
            "       A.CHECKER_DATE,\r\n" +
            "       D.BCH_CHARGES_AMT,\r\n" +
            "        F.FUND_DESC,\r\n" +
            "       A.CHECKER_REMARKS\r\n" +
            "  FROM TB_AC_BILL_MAS A LEFT OUTER JOIN tb_ac_fund_master F ON\r\n" + 
            "         A.FUND_ID = F.FUND_ID,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "       (SELECT SUM(D.BCH_CHARGES_AMT) BCH_CHARGES_AMT, D.BM_ID, D.SAC_HEAD_ID\r\n" +
            "          FROM TB_AC_BILL_EXP_DETAIL D where D.SAC_HEAD_ID=:accountHeadId\r\n" +
            "        GROUP BY D.BM_ID, D.SAC_HEAD_ID) D,\r\n" +
            "       TB_VENDORMASTER V\r\n" +
            " WHERE     A.VM_VENDORID = V.VM_VENDORID\r\n" +
            "       AND A.BM_ID = D.BM_ID\r\n" +
            "       AND D.SAC_HEAD_ID = SM.SAC_HEAD_ID\r\n" +
            "       AND A.CHECKER_AUTHO = 'Y'\r\n" +
            "       AND A.BM_PAY_STATUS = 'N'\r\n" +
            "       AND A.BM_DEL_FLAG IS NULL\r\n" +
            "       AND A.CHECKER_DATE <=:fromdate\r\n" +
            "       AND A.ORGID =:orgId\r\n" +
            "       AND A.DP_DEPTID =:dpDeptid\r\n" +
            "ORDER BY A.CHECKER_DATE, A.BM_BILLNO", nativeQuery = true)
    List<Object[]> getOutStandingBillRegisterDetailsnNewWithAccountHead(@Param("orgId") Long orgId, @Param("fromdate") Date fromdate,
            @Param("dpDeptid") Long dpDeptid,@Param("accountHeadId") Long accountHeadId);
    

    @Query("select sum(pd.paymentAmt) from AccountPaymentDetEntity pd where pd.billId=:id and pd.orgId=:orgId and pd.paymentMasterId.paymentDeletionFlag IS NULL")
    BigDecimal findPaymentAmount(@Param("id") Long id, @Param("orgId") Long orgId);

    @Query("select count(*) from AccountPaymentDetEntity pd where pd.billId=:id and pd.bchIdExpenditure=:bchId and pd.orgId=:orgId and pd.paymentMasterId.paymentDeletionFlag IS NULL")
    int findbmIdIsExistOrNot(@Param("id") Long id, @Param("bchId") Long bchId, @Param("orgId") Long orgId);

    @Modifying
    @Query(value = "delete from AccountBillEntryExpenditureDetEntity exp where exp.id=:id")
    void deleteByExpenditureDetails(@Param("id") Long id);

    @Modifying
    @Query(value = "delete from AccountBillEntryDeductionDetEntity ded where ded.id=:id")
    void deleteByDeductionDetails(@Param("id") Long id);
    
    @Modifying
    @Query(value = "delete from AccountBillEntryMeasurementDetEntity ded where ded.mbId in (:id)")
    void deleteByMeasurementDetails(@Param("id") List<Long> removeMbIdList);

    @Query("SELECT bm.billIntRefId FROM AccountBillEntryMasterEnitity bm where bm.id=:billId and bm.departmentId.dpDeptid=:dpDeptid and bm.orgId=:orgId")
    public Long gettingSalBillRefIdInPropertyTax(@Param("billId") Long billId, @Param("dpDeptid") Long dpDeptid,
            @Param("orgId") Long orgId);

    @Query("select bm from AccountBillEntryMasterEnitity bm where bm.orgId=:orgId and bm.billNo =:bmNo")
    AccountBillEntryMasterEnitity findBillEntryByBillNo(@Param("orgId") Long orgId, @Param("bmNo") String bmNo);

    @Modifying
    @Query("update AccountBillEntryMasterEnitity bm set bm.checkerUser =:checkerUser,bm.checkerDate=:authDate,bm.checkerRemarks=:checkerRemarks,bm.checkerAuthorization=:authFlag where bm.id=:billId and bm.orgId=:orgId")
    void updateBillEntryAuthFlagDetailsInWorkflow(@Param("billId") Long id, @Param("orgId") Long orgId,
            @Param("checkerUser") Long checkerUser, @Param("authDate") Date authDate,
            @Param("checkerRemarks") String checkerRemarks, @Param("authFlag") Character authFlag);
    
    @Query("SELECT SUM(bm.invoiceValue) FROM AccountBillEntryMasterEnitity bm WHERE bm.billIntRefId=:intRefId AND bm.billTypeId.cpdId=:billTypeId AND bm.orgId=:orgId AND bm.billDeletionFlag is null")
    Long getTotalBillAmountByIntRefIdAndBillTypeId(@Param("intRefId") Long intRefId, @Param("billTypeId") Long billTypeId, @Param("orgId") Long orgId);

    @Query("SELECT bm FROM AccountBillEntryMasterEnitity bm WHERE bm.billIntRefId=:intRefId AND bm.orgId=:orgId AND bm.billDeletionFlag is null")
    List<AccountBillEntryMasterEnitity> getAllBillListByIntRefIdAndOrgId(@Param("intRefId") Long intRefId, @Param("orgId") Long orgId);
}
