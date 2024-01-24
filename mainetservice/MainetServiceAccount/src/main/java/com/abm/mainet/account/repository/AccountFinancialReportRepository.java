package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountDepositEntity;
import com.abm.mainet.account.domain.AccountGrantMasterEntity;
import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.domain.VoucherDetailViewEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;


/**
 *
 * @author Vivek.Kumar
 * @since 09-Aug-2017
 */
@Repository
public interface AccountFinancialReportRepository extends CrudRepository<AccountVoucherEntryEntity, Long> {

    @Query("FROM AccountVoucherEntryEntity vm WHERE vm.vouDate=:vouDate AND vm.vouTypeCpdId=:vouTypeCpdId AND vm.dpDeptid=:dpDeptid AND vm.authoFlg=:authoFlg AND vm.org=:org")
    List<AccountVoucherEntryEntity> queryForVouchersByVoucherDate(@Param("vouDate") Date vouDate,
            @Param("vouTypeCpdId") Long vouTypeCpdId, @Param("dpDeptid") Long dpDeptid,
            @Param("authoFlg") String authoFlg, @Param("org") Long org);

    @Query("SELECT vm.org,vm.vouDate,vm.vouNo,vm.payerPayee,vm.narration,vd.budgetCode.prBudgetCodeid, "
            + "bm.prBudgetCode,vd.voudetAmt,(SELECT cod.cpdValue FROM TbComparamDetEntity cod WHERE cod.cpdId=vd.drcrCpdId), vm.vouSubtypeCpdId"
            + " FROM AccountVoucherEntryEntity vm, AccountVoucherEntryDetailsEntity vd, TbComparamDetEntity cd, AccountBudgetCodeEntity bm "
            + " WHERE vm.org=vd.orgId" + " AND vm.vouId=vd.master.vouId" + " AND vm.vouTypeCpdId=cd.cpdId"
            + " AND cd.cpdStatus=:cpdStatus" + " AND cd.cpdValue IN('RV','PV','CV')" + " AND vd.orgId=bm.orgid"
            + " AND vd.budgetCode.prBudgetCodeid=bm.prBudgetCodeid" + " AND vm.authoFlg=:authoFlg"
            + " AND vm.org=:orgId" + " AND vm.vouDate=:vouDate" + " AND vm.vouSubtypeCpdId "
            + "IN(SELECT vtm.templateFor FROM VoucherTemplateDetailEntity vtd, VoucherTemplateMasterEntity vtm WHERE vtm.templateId=vtd.templateId.templateId"
            + " AND vtd.cpdIdPayMode=:cpdIdPayMode AND vtd.orgid=:orgId)")
    List<Object[]> queryDataForReport(@Param("vouDate") Date vouDate, @Param("authoFlg") String authoFlg,
            @Param("orgId") Long org, @Param("cpdIdPayMode") Long cpdIdPayMode, @Param("cpdStatus") String cpdStatus);

    @Query("SELECT v1 FROM VoucherDetailViewEntity v1 , VoucherDetailViewEntity v2 "
            + " WHERE v1.vouNo = v2.vouNo and v1.sacHeadId !=:sacHeadId AND v2.sacHeadId=:sacHeadId AND v2.voPostingDate between :entryDate and :outDate AND v1.voPostingDate between :entryDate and :outDate"
            + "  and v1.vouTypeCpdId not in (select cpdId from TbComparamDetEntity where cpdValue='JV') "
            + "and v1.orgId=:orgId order by v1.voPostingDate , v1.vouId asc )")

    List<VoucherDetailViewEntity> queryReportDataFromView(@Param("entryDate") Date entryDate,
            @Param("orgId") Long orgId, @Param("sacHeadId") Long sacHeadId, @Param("outDate") Date outDate);

    @Query("SELECT bm.sacHeadId FROM AccountHeadSecondaryAccountCodeMasterEntity bm WHERE bm.sacHeadId IN(SELECT DISTINCT vtd.sacHeadId "
            + "FROM VoucherTemplateMasterEntity vtm, VoucherTemplateDetailEntity vtd WHERE vtm.templateId=vtd.templateId.templateId "
            + " AND vtd.cpdIdPayMode=:cpdIdPayMode AND vtd.orgid=:orgId AND vtd.sacHeadId IS NOT NULL)")
    Long findSacHeadIdByPayModeId(@Param("cpdIdPayMode") Long cpdIdPayMode, @Param("orgId") Long orgId);

    @Query("SELECT ob.openbalAmt,ob.tbComparamDet.cpdId FROM AccountBudgetOpenBalanceEntity ob WHERE ob.tbAcSecondaryheadMaster.sacHeadId=:sacHeadId AND ob.orgid=:orgId and ob.faYearid=:financialYearId")
    List<Object[]> queryOpenBalanceAmountAndCrDrType(@Param("sacHeadId") Long sacHeadId, @Param("orgId") Long orgId,
            @Param("financialYearId") Long financialYearId);
    @Query("SELECT ob.openbalAmt,ob.tbComparamDet.cpdId FROM AccountBudgetOpenBalanceEntity ob WHERE  ob.orgid=:orgId and ob.faYearid=:financialYearId")
    List<Object[]> queryOpenBalanceAmountAndCrDrTypeAll( @Param("orgId") Long orgId,
            @Param("financialYearId") Long financialYearId);
    @Query("SELECT ob.openbalAmt,ob.tbComparamDet.cpdValue FROM AccountBudgetOpenBalanceEntity ob WHERE ob.tbAcSecondaryheadMaster.sacHeadId in (:sacHeadId) AND ob.orgid=:orgId and ob.faYearid=:financialYearId")
    List<Object[]> queryOpenBalanceAmountAndCrDrTypeAndAccHeads(@Param("sacHeadId") List<Long> sacHeadIdList, @Param("orgId") Long orgId,
            @Param("financialYearId") Long financialYearId);

    @Query("SELECT SUM(vw.drAmount), SUM(vw.crAmount)  FROM VoucherDetailViewEntity vw WHERE vw.sacHeadId=:sacHeadId AND vw.orgId=:orgId AND vw.voPostingDate<:voPostingDate")
    List<Object[]> queryDrCrAmountBySacHeadId(@Param("sacHeadId") Long sacHeadId, @Param("orgId") Long orgId,
            @Param("voPostingDate") Date voPostingDate);

    @Query("FROM VoucherDetailViewEntity vw WHERE vw.vouTypeCpdId=:vouTypeCpdId AND vw.orgId=:orgId AND vw.voPostingDate between :fromDate and :toDate order by vw.voPostingDate,vw.vouNo asc")
    List<VoucherDetailViewEntity> queryJournalRegisterDataFromView(@Param("vouTypeCpdId") Long vouTypeCpdId,
            @Param("orgId") Long orgId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value ="select\r\n" + 
    		"vd.VOUDET_ID as VOUDET_I1_812_, b.AC_HEAD_CODE as AC_HEAD_2_812_, (case when (vd.DRCR_CPD_ID =\r\n" + 
    		"(select cd.CPD_ID from (tb_comparam_det cd join tb_comparam_mas cm) where ((cm.CPM_ID = cd.CPM_ID) and (cd.CPD_VALUE = 'CR') and (cm.CPM_PREFIX = 'DCR'))))\r\n" + 
    		"then coalesce(vd.VOUDET_AMT,0) else 0 end) as VAMT_CR3_812_,\r\n" + 
    		"(case when (vd.DRCR_CPD_ID = (select cd.CPD_ID from (tb_comparam_det cd join tb_comparam_mas cm) where ((cm.CPM_ID = cd.CPM_ID) and (cd.CPD_VALUE = 'DR') and (cm.CPM_PREFIX = 'DCR'))))\r\n" + 
    		"then coalesce(vd.VOUDET_AMT,0) else 0 end) as VAMT_DR4_812_,\r\n" + 
    		"(select tb_comparam_det.CPD_VALUE from tb_comparam_det where (tb_comparam_det.CPD_ID = vd.DRCR_CPD_ID)) as DRCR5_812_,\r\n" + 
    		"ac.FIELD_ID as FIELD_ID6_812_,\r\n" + 
    		" ac.ORGID as ORGID7_812_,\r\n" + 
    		"ac.NARRATION as Particul8_812_,\r\n" + 
    		"ac.PAYER_PAYEE as PAYER_PA9_812_,\r\n" + 
    		"ac.VOU_REFERENCE_NO as REFEREN10_812_,\r\n" + 
    		"vd.SAC_HEAD_ID as SAC_HEA11_812_,\r\n" + 
    		"ac.VOU_POSTING_DATE as VOU_POS12_812_, ac.VOU_DATE as VOU_DAT13_812_,\r\n" + 
    		"ac.VOU_ID as VOU_ID14_812_, ac.VOU_NO as VOU_NO15_812_,\r\n" + 
    		"ac.VOU_SUBTYPE_CPD_ID as VOU_SUB16_812_, ac.VOU_TYPE_CPD_ID as VOU_TYP17_812_, vd.VOUDET_AMT as Voucher18_812_\r\n" + 
    		"from (((tb_ac_voucher ac join tb_ac_voucher_det vd) join tb_comparam_det cd) join tb_ac_secondaryhead_master b)\r\n" + 
    		"where ((ac.ORGID = vd.ORGID) and (ac.VOU_ID = vd.VOU_ID) and (ac.VOU_TYPE_CPD_ID = cd.CPD_ID)\r\n" + 
    		"and (cd.CPD_STATUS = 'A') and (vd.ORGID = b.ORGID) and (vd.SAC_HEAD_ID = b.SAC_HEAD_ID) and (ac.AUTHO_FLG = 'Y'))\r\n" + 
    		"and ac.ORGID=:orgId\r\n" + 
    		"and ac.VOU_POSTING_DATE>=:fromDate and ac.VOU_POSTING_DATE<=:toDate  and vd.SAC_HEAD_ID=:accountHeadId\r\n" + 
    		"order by ac.VOU_POSTING_DATE, ac.VOU_NO asc\r\n" 
    		,nativeQuery = true)
    List<Object[]> queryForGeneralLedgerDataFromView(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("accountHeadId") Long accountHeadId, @Param("orgId") Long orgId);

    @Query(value = "SELECT   \r\n" + "                Y.OPENBAL_AMT,  \r\n" + "                Y.SAC_HEAD_ID,  \r\n"
            + "                Y.CPD_ID_DRCR,  \r\n" + "                Y.TranCr,  \r\n"
            + "                Y.TranDr,  \r\n" + "                B.OPVOUCR,  \r\n" + "                B.OPVOUDR \r\n"
            + "            FROM  \r\n" + "                 tb_ac_primaryhead_master pr,\r\n"
            + "                 tb_ac_secondaryhead_master se,\r\n" + "                (SELECT   \r\n"
            + "                    X.OPENBAL_AMT,  \r\n" + "                        (CASE  \r\n"
            + "                            WHEN X.OPNSACHEADID IS NULL THEN X.TRNSACHEADID  \r\n"
            + "                            ELSE X.OPNSACHEADID  \r\n"
            + "                        END) SAC_HEAD_ID,  \r\n" + "                        X.CPD_ID_DRCR,  \r\n"
            + "                        SUM(X.VAMT_CR) TranCr,  \r\n"
            + "                        SUM(X.VAMT_DR) TranDr  \r\n" + "                FROM  \r\n"
            + "                    (SELECT   \r\n" + "                    VOUDET_ID,A.OPENBAL_AMT,  \r\n"
            + "                        A.SAC_HEAD_ID OPNSACHEADID,  \r\n"
            + "                        A.CPD_ID_DRCR,  \r\n" + "                        vd.VAMT_CR,  \r\n"
            + "                        vd.VAMT_DR,  \r\n" + "                        VD.SAC_HEAD_ID TRNSACHEADID  \r\n"
            + "                FROM  \r\n" + "                    (SELECT   \r\n"
            + "                    VOUDET_ID,SAC_HEAD_ID, VAMT_CR, VAMT_DR  \r\n" + "                FROM  \r\n"
            + "                    vw_voucher_detail  \r\n" + "                WHERE  \r\n"
            + "                    ORGID =:orgId  \r\n"
            + "                    and VOU_POSTING_DATE BETWEEN :fromDate and :toDate) vd  \r\n"
            + "                LEFT JOIN (SELECT   \r\n"
            + "                    bg.OPENBAL_AMT, bg.CPD_ID_DRCR, SM.SAC_HEAD_ID  \r\n" + "                FROM  \r\n"
            + "                    tb_ac_bugopen_balance bg, tb_ac_secondaryhead_master sm  \r\n"
            + "                WHERE  \r\n" + "                    sm.SAC_HEAD_ID = bg.SAC_HEAD_ID  \r\n"
            + "                        AND sm.ORGID =:orgId and bg.FA_YEARID =:faYearIds) A ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID UNION  SELECT  \r\n"
            + "            \r\n" + "                    VOUDET_ID,A.OPENBAL_AMT,  \r\n"
            + "                        A.SAC_HEAD_ID OPNSACHEADID,  \r\n"
            + "                        A.CPD_ID_DRCR,  \r\n" + "                        vd.VAMT_CR,  \r\n"
            + "                        vd.VAMT_DR,  \r\n" + "                        VD.SAC_HEAD_ID TRNSACHEADID  \r\n"
            + "                FROM  \r\n" + "                    (SELECT   \r\n"
            + "                    VOUDET_ID,SAC_HEAD_ID, VAMT_CR, VAMT_DR  \r\n" + "                FROM  \r\n"
            + "                    vw_voucher_detail  \r\n" + "                WHERE ORGID =:orgId  \r\n"
            + "                    and VOU_POSTING_DATE BETWEEN :fromDate and :toDate ) VD  \r\n"
            + "                RIGHT JOIN (SELECT   \r\n"
            + "                    bg.OPENBAL_AMT, bg.CPD_ID_DRCR, SM.SAC_HEAD_ID  \r\n" + "                FROM  \r\n"
            + "                    tb_ac_bugopen_balance bg, tb_ac_secondaryhead_master sm  \r\n"
            + "                WHERE  \r\n" + "                    sm.SAC_HEAD_ID = bg.SAC_HEAD_ID  \r\n"
            + "                        AND sm.ORGID =:orgId and bg.FA_YEARID =:faYearIds) A ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID) X  \r\n"
            + "                GROUP BY X.OPNSACHEADID , X.OPENBAL_AMT , X.CPD_ID_DRCR , X.TRNSACHEADID) Y  \r\n"
            + "                    LEFT JOIN  \r\n" + "                (SELECT   \r\n"
            + "                    SAC_HEAD_ID, SUM(VAMT_CR) OPVOUCR, SUM(VAMT_DR) OPVOUDR  \r\n"
            + "                FROM  \r\n" + "                    vw_voucher_detail  \r\n"
            + "                WHERE ORGID =:orgId  \r\n"
            + "                    and VOU_POSTING_DATE BETWEEN :afterDate and :beforeDate \r\n"
            + "                GROUP BY SAC_HEAD_ID) B ON Y.SAC_HEAD_ID = B.SAC_HEAD_ID\r\n"
            + "                where Y.SAC_HEAD_ID=SE.SAC_HEAD_ID AND SE.PAC_HEAD_ID=PR.PAC_HEAD_ID\r\n"
            + "                ORDER BY pr.PAC_HEAD_COMPO_CODE", nativeQuery = true)

    List<Object[]> queryForTrailBalanceReportData(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("faYearIds") Long faYearIds, @Param("beforeDate") Date beforeDate,
            @Param("afterDate") Date afterDate);

    @Query(value = "SELECT \r\n" + "    scm.SAC_HEAD_ID,\r\n" + "    scm.AC_HEAD_CODE,\r\n" + "    md.RF_FEEAMOUNT,\r\n"
            + "        SUM(pr.ORGINAL_ESTAMT) ,\r\n" + "    SUM(pr.REVISED_ESTAMT)\r\n" + "FROM\r\n"
            + "    tb_ac_budgetcode_mas bm,\r\n" + "    tb_ac_projectedrevenue pr,\r\n"
            + "    tb_ac_primaryhead_master pm,\r\n" + "    tb_financialyear fn,\r\n"
            + "    Tb_Ac_Secondaryhead_Master scm\r\n" + "        LEFT OUTER JOIN\r\n" + "    (SELECT \r\n"
            + "        sm.SAC_HEAD_ID, rms.orgid, SUM(rd.RF_FEEAMOUNT) RF_FEEAMOUNT\r\n" + "    FROM\r\n"
            + "        tb_receipt_mas rms, tb_receipt_det rd, tb_receipt_mode rm,  Tb_Ac_Secondaryhead_Master sm\r\n"
            + "    WHERE\r\n" + "        rms.RM_RCPTID = rd.RM_RCPTID\r\n"
            + "            AND rms.RM_RCPTID = rm.RM_RCPTID\r\n" + "            AND rm.RM_RCPTID = rd.RM_RCPTID\r\n"
            + "            AND rd.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n" + "            AND rms.orgid = rd.orgid\r\n"
            + "            AND rms.RECEIPT_TYPE_FLAG in ('M','R','A','P')"
            + "            AND rm.CPD_FEEMODE IN (SELECT \r\n" + "                a.Cpd_id\r\n" + "            FROM\r\n"
            + "                tb_comparam_det a, tb_comparam_mas b\r\n" + "            WHERE\r\n"
            + "                b.CPM_PREFIX = 'PAY'\r\n"
            + "                    AND a.CPD_VALUE IN ('C' , 'Q', 'D', 'B', 'RT', 'W')\r\n"
            + "                    AND a.cpm_id = b.cpm_id)\r\n" + "            AND rms.RECEIPT_DEL_FLAG IS NULL\r\n"
            + "            AND rms.ORGID =:orgId\r\n" + "            AND rms.RM_DATE BETWEEN :fromDate AND :toDate \r\n"
            + "    GROUP BY sm.SAC_HEAD_ID , rms.orgid) md ON scm.sac_head_id = md.sac_head_id\r\n" + "WHERE\r\n"
            + "    bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" + "        AND bm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n"
            + "        AND scm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" + "        AND pr.FA_YEARID = fn.FA_YEARID\r\n"
            + "        AND pr.orgid =:orgId\r\n" + "        AND scm.orgid =:orgId\r\n"
            + "        AND fn.FA_YEARID =:finanacialYearId\r\n"
            + "GROUP BY scm.SAC_HEAD_ID , scm.AC_HEAD_CODE , md.RF_FEEAMOUNT," + "PM.PAC_HEAD_COMPO_CODE\r\n"
            + " ORDER BY PM.PAC_HEAD_COMPO_CODE\r\n" + "", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideReportData(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId, @Param("finanacialYearId") Long finanacialYearId);

    @Query(value = "SELECT \r\n" + "    scm.SAC_HEAD_ID,\r\n" + "    scm.AC_HEAD_CODE,\r\n" + "    md.PAYMENT_AMT,\r\n"
            + "    SUM(pe.ORGINAL_ESTAMT),\r\n" + "    SUM(pe.REVISED_ESTAMT)\r\n" + "FROM\r\n"
            + "    tb_ac_budgetcode_mas bm,\r\n" + "    tb_ac_projected_expenditure pe,\r\n"
            + "    tb_ac_primaryhead_master pm,\r\n" + "    Tb_Ac_Function_Master fm,\r\n"
            + "    tb_financialyear fn,\r\n" + "    Tb_Ac_Secondaryhead_Master scm\r\n" + "        LEFT OUTER JOIN\r\n"
            + "    (SELECT \r\n" + "        sm.SAC_HEAD_ID, pms.orgid, SUM(pd.PAYMENT_AMT) PAYMENT_AMT\r\n"
            + "    FROM\r\n" + "        tb_ac_payment_mas pms, tb_ac_payment_det pd,  Tb_Ac_Secondaryhead_Master sm\r\n"
            + "    WHERE\r\n" + "        pms.PAYMENT_ID = pd.PAYMENT_ID\r\n"
            + "            AND pd.BUDGETCODE_ID = sm.SAC_HEAD_ID\r\n" + "            AND pms.orgid = pd.orgid\r\n"
            + "            AND pms.ORGID =:orgId\r\n" + "            AND pms.PAYMENT_DEL_FLAG IS NULL\r\n"
            + "            AND pms.CPD_ID_PAYMENT_MODE IN (SELECT \r\n" + "                a.Cpd_id\r\n"
            + "            FROM\r\n" + "                tb_comparam_det a, tb_comparam_mas b\r\n"
            + "            WHERE\r\n" + "                b.CPM_PREFIX = 'PAY'\r\n"
            + "                    AND a.CPD_VALUE IN ('C','PCA' , 'Q', 'D', 'B', 'RT', 'W')\r\n"
            + "                    AND a.cpm_id = b.cpm_id)\r\n"
            + "            AND pms.PAYMENT_DATE BETWEEN :fromDate AND :toDate \r\n"
            + "    GROUP BY sm.SAC_HEAD_ID , pms.orgid) md ON scm.sac_head_id = md.sac_head_id\r\n" + "WHERE\r\n"
            + "    bm.BUDGETCODE_ID = pe.BUDGETCODE_ID\r\n" + "        AND bm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n"
            + "        AND scm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" + "        AND bm.function_id = fm.function_id\r\n"
            + "        AND scm.function_id = fm.function_id\r\n" + "        AND pe.FA_YEARID = fn.FA_YEARID\r\n"
            + "        AND scm.orgid =:orgId\r\n" + "        AND pe.orgid =:orgId\r\n"
            + "        AND fn.FA_YEARID =:finanacialYearId\r\n"
            + "GROUP BY scm.SAC_HEAD_ID , scm.AC_HEAD_CODE , md.PAYMENT_AMT," + "PM.PAC_HEAD_COMPO_CODE\r\n"
            + " ORDER BY PM.PAC_HEAD_COMPO_CODE\r\n" + "", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideReportData(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId, @Param("finanacialYearId") Long finanacialYearId);

    @Query("SELECT distinct(vtd.sacHeadId) from VoucherTemplateDetailEntity vtd where  vtd.cpdIdPayMode =:cpdIdPayMode and vtd.orgid =:orgId and vtd.cpdStatusFlg =:activeStatusId ")
    Long findSacHeadId(@Param("cpdIdPayMode") long cpdIdPayMode, @Param("orgId") long orgId,
            @Param("activeStatusId") Long activeStatusId);

    @Query(value = "SELECT  A.faYear FROM FinancialYear A WHERE :fromDate   BETWEEN A.faFromDate AND A.faToDate ")

    long queryToFindFinanacialYearID(@Param("fromDate") Date fromDate);

    @Query("SELECT v1 FROM VoucherDetailViewEntity v1 , VoucherDetailViewEntity v2 "
            + " WHERE v1.vouNo = v2.vouNo and v1.sacHeadId !=:sacHeadId AND v2.sacHeadId=:sacHeadId AND v2.voPostingDate between  :entryDate and :endDate AND v1.voPostingDate between  :entryDate and :endDate"
            + "  and v1.vouTypeCpdId not in (select cpdId from TbComparamDetEntity where cpdValue='JV') "
            + "and v1.orgId=:orgId order by v1.voPostingDate,v1.vouId asc )")
    List<VoucherDetailViewEntity> queryReportDataFromViewGeneralBankBook(@Param("entryDate") Date entryDate,
            @Param("orgId") Long orgId, @Param("sacHeadId") Long sacHeadId, @Param("endDate") Date endDate);

    @Query("select vd.sacHeadId, vd.acHeadCode ,SUM(vd.crAmount), SUM(vd.drAmount)"
            + " from VoucherDetailViewEntity vd " + " WHERE vd.voPostingDate BETWEEN :fromDates and :toDates "
            + " and vd.sacHeadId in (select sm.sacHeadId from AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " where sm.tbAcPrimaryheadMaster.cpdIdAcHeadTypes =:lookUpId and sm.orgid =:orgId)"
            + " GROUP BY vd.sacHeadId ,vd.acHeadCode ORDER BY vd.acHeadCode")
    List<Object[]> queryReportDataFromViewIncome(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId);

    @Query("select vd.sacHeadId, vd.acHeadCode ,SUM(vd.crAmount), SUM(vd.drAmount)"
            + " from VoucherDetailViewEntity vd " + " WHERE vd.voPostingDate BETWEEN :fromDates and :toDates "
            + " and vd.sacHeadId in (select sm.sacHeadId from AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " where sm.tbAcPrimaryheadMaster.cpdIdAcHeadTypes =:lookUpId and sm.orgid =:orgId)"
            + " GROUP BY vd.sacHeadId ,vd.acHeadCode ORDER BY vd.acHeadCode")
    List<Object[]> queryReportDataFromViewExpenditure(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId);

    @Query(value = "select PAC.PAC_HEAD_COMPO_CODE,CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		" IFNULL(CREDIT,0) CREDIT,IFNULL(DEBIT,0) DEBIT,PAC.SCH_CODE\r\n" + 
    		"FROM TB_AC_PRIMARYHEAD_MASTER PAC\r\n" + 
    		"left join\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"       CONCAT((CONCAT(R.PAC_HEAD_COMPO_CODE, ' - ')), R.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		"       SUM(P.VAMT_CR) AS CREDIT,\r\n" + 
    		"       SUM(P.VAMT_DR) AS DEBIT\r\n" + 
    		"  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"               A.PAC_HEAD_DESC,\r\n" + 
    		"               A.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               B.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + 
    		"               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       VAMT_CR,\r\n" + 
    		"                       VAMT_DR,\r\n" + 
    		"                       A.PAC_HEAD_ID,\r\n" + 
    		"                       A.FUNCTION_ID\r\n" + 
    		"                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                               SM.PAC_HEAD_ID,\r\n" + 
    		"                               SM.FUNCTION_ID\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + 
    		"                               TB_AC_PRIMARYHEAD_MASTER PM,\r\n" + 
    		"                               TB_AC_FUNCTION_MASTER FM\r\n" + 
    		"                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDates AND :toDates \r\n" + 
    		"                               AND VD.ORGID = :orgId\r\n" + 
    		"                               AND PM.CPD_ID_ACHEADTYPES in (:lookUpId)\r\n" + 
    		"                               AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + 
    		"                               AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n" + 
    		"                               AND FM.FUNCTION_ID = SM.FUNCTION_ID\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 SM.PAC_HEAD_ID,\r\n" + 
    		"                                 SM.FUNCTION_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" + 
    		"GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) INC\r\n" + 
    		"ON CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC)= INC.AC_HEAD\r\n" + 
    		"where pac.CPD_ID_ACHEADTYPES in(:lookUpId) and PAC.CODCOFDET_ID =:codDetId and  PAC.PAC_HEAD_COMPO_CODE in (110,120,130,140,150,160,170,171,180)\r\n" + 
    		"ORDER BY cast(replace(PAC.PAC_HEAD_COMPO_CODE,'-','') as unsigned) asc", nativeQuery = true)
    List<Object[]> queryReportDataFromViewMajorHeadIncomeAndExpenditure(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId,@Param("codDetId") Long codDetId);
    @Query(value = "select PAC.PAC_HEAD_COMPO_CODE,CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		" IFNULL(CREDIT,0) CREDIT,IFNULL(DEBIT,0) DEBIT,PAC.SCH_CODE\r\n" + 
    		"FROM TB_AC_PRIMARYHEAD_MASTER PAC\r\n" + 
    		"left join\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"       CONCAT((CONCAT(R.PAC_HEAD_COMPO_CODE, ' - ')), R.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		"       SUM(P.VAMT_CR) AS CREDIT,\r\n" + 
    		"       SUM(P.VAMT_DR) AS DEBIT\r\n" + 
    		"  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"               A.PAC_HEAD_DESC,\r\n" + 
    		"               A.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               B.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + 
    		"               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       VAMT_CR,\r\n" + 
    		"                       VAMT_DR,\r\n" + 
    		"                       A.PAC_HEAD_ID,\r\n" + 
    		"                       A.FUNCTION_ID\r\n" + 
    		"                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                               SM.PAC_HEAD_ID,\r\n" + 
    		"                               SM.FUNCTION_ID\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + 
    		"                               TB_AC_PRIMARYHEAD_MASTER PM,\r\n" + 
    		"                               TB_AC_FUNCTION_MASTER FM\r\n" + 
    		"                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDates AND :toDates \r\n" + 
    		"                               AND VD.ORGID = :orgId\r\n" + 
    		"                               AND PM.CPD_ID_ACHEADTYPES in (:lookUpId)\r\n" + 
    		"                               AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + 
    		"                               AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n" + 
    		"                               AND FM.FUNCTION_ID = SM.FUNCTION_ID\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 SM.PAC_HEAD_ID,\r\n" + 
    		"                                 SM.FUNCTION_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" + 
    		"GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) INC\r\n" + 
    		"ON CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC)= INC.AC_HEAD\r\n" + 
    		"where pac.CPD_ID_ACHEADTYPES in(:lookUpId) and PAC.CODCOFDET_ID =:codDetId and PAC.PAC_HEAD_COMPO_CODE in (210,220,230,240,250,260,270,271)\r\n" + 
    		"ORDER BY cast(replace(PAC.PAC_HEAD_COMPO_CODE,'-','') as unsigned) asc", nativeQuery = true)
    List<Object[]> queryReportDataFromViewMajorHeadExpenditure(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId,@Param("codDetId") Long codDetId);

    @Query(value = "select PAC.PAC_HEAD_COMPO_CODE,CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		"IFNULL(CREDIT,0) CREDIT,IFNULL(DEBIT,0) DEBIT,PAC.SCH_CODE\r\n" + 
    		"FROM TB_AC_PRIMARYHEAD_MASTER PAC\r\n" + 
    		"left join\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"       CONCAT((CONCAT(R.PAC_HEAD_COMPO_CODE, ' - ')), R.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		"       SUM(P.VAMT_CR) AS CREDIT,\r\n" + 
    		"       SUM(P.VAMT_DR) AS DEBIT\r\n" + 
    		"  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"               A.PAC_HEAD_DESC,\r\n" + 
    		"               A.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               B.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + 
    		"               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       VAMT_CR,\r\n" + 
    		"                       VAMT_DR,\r\n" + 
    		"                       A.PAC_HEAD_ID,\r\n" + 
    		"                       A.FUNCTION_ID\r\n" + 
    		"                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                               SM.PAC_HEAD_ID,\r\n" + 
    		"                               SM.FUNCTION_ID\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + 
    		"                               TB_AC_PRIMARYHEAD_MASTER PM,\r\n" + 
    		"                               TB_AC_FUNCTION_MASTER FM\r\n" + 
    		"                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDates\r\n" + 
    		"                                                           AND :toDates\r\n" + 
    		"                               AND VD.ORGID = :orgId\r\n" + 
    		"                               AND PM.CPD_ID_ACHEADTYPES in(:lookUpId)\r\n" + 
    		"                               AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + 
    		"                               AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n" + 
    		"                               AND FM.FUNCTION_ID = SM.FUNCTION_ID\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 SM.PAC_HEAD_ID,\r\n" + 
    		"                                 SM.FUNCTION_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL2 = R.PAC_HEAD_ID\r\n" + 
    		"GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) INC\r\n" + 
    		"ON CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC)= INC.AC_HEAD\r\n" + 
    		"where pac.CPD_ID_ACHEADTYPES in(:lookUpId) and PAC.CODCOFDET_ID = :CodDetId\r\n" + 
    		"ORDER BY cast(replace(PAC.PAC_HEAD_COMPO_CODE,'-','') as unsigned) asc", nativeQuery = true)
    List<Object[]> queryReportDataFromViewMinorHeadIncomeAndExpenditure(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId,@Param("CodDetId") Long accountHeadId);
    @Query(value = "select PAC.PAC_HEAD_COMPO_CODE,CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		"IFNULL(CREDIT,0) CREDIT,IFNULL(DEBIT,0) DEBIT,PAC.SCH_CODE\r\n" + 
    		"FROM TB_AC_PRIMARYHEAD_MASTER PAC\r\n" + 
    		"left join\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"       CONCAT((CONCAT(R.PAC_HEAD_COMPO_CODE, ' - ')), R.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		"       SUM(P.VAMT_CR) AS CREDIT,\r\n" + 
    		"       SUM(P.VAMT_DR) AS DEBIT\r\n" + 
    		"  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"               A.PAC_HEAD_DESC,\r\n" + 
    		"               A.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               B.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + 
    		"               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       VAMT_CR,\r\n" + 
    		"                       VAMT_DR,\r\n" + 
    		"                       A.PAC_HEAD_ID,\r\n" + 
    		"                       A.FUNCTION_ID\r\n" + 
    		"                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                               SM.PAC_HEAD_ID,\r\n" + 
    		"                               SM.FUNCTION_ID\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + 
    		"                               TB_AC_PRIMARYHEAD_MASTER PM,\r\n" + 
    		"                               TB_AC_FUNCTION_MASTER FM\r\n" + 
    		"                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDates\r\n" + 
    		"                                                           AND :toDates\r\n" + 
    		"                               AND VD.ORGID = :orgId\r\n" + 
    		"                               AND PM.CPD_ID_ACHEADTYPES in(:lookUpId)\r\n" + 
    		"                               AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + 
    		"                               AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n" + 
    		"                               AND FM.FUNCTION_ID = SM.FUNCTION_ID\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 SM.PAC_HEAD_ID,\r\n" + 
    		"                                 SM.FUNCTION_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL2 = R.PAC_HEAD_ID\r\n" + 
    		"GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) INC\r\n" + 
    		"ON CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC)= INC.AC_HEAD\r\n" + 
    		"where pac.CPD_ID_ACHEADTYPES in(:lookUpId) and PAC.CODCOFDET_ID = :CodDetId and PAC.PAC_HEAD_COMPO_CODE in (210,220,230,240,250,260,270,271)\r\n" + 
    		"ORDER BY cast(replace(PAC.PAC_HEAD_COMPO_CODE,'-','') as unsigned) asc", nativeQuery = true)
    List<Object[]> queryReportDataFromViewMinorHeadExpenditure(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId,@Param("CodDetId") Long accountHeadId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "      CONCAT((CONCAT(R.PAC_HEAD_COMPO_CODE, ' - ')), R.PAC_HEAD_DESC),\r\n" +
            "	   SUM(P.VAMT_CR),\r\n" +
            "      SUM(P.VAMT_DR)\r\n" +
            "  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "               A.PAC_HEAD_DESC,\r\n" +
            "               A.PAC_HEAD_ID AS LEVEL4,\r\n" +
            "               B.PAC_HEAD_ID AS LEVEL3,\r\n" +
            "               C.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "               D.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" +
            "               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       A.PAC_HEAD_ID,\r\n" +
            "                       A.FUNCTION_ID\r\n" +
            "                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                               SM.PAC_HEAD_ID,\r\n" +
            "                               SM.FUNCTION_ID\r\n" +
            "                          FROM VW_VOUCHER_DETAIL          VD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER   PM,\r\n" +
            "                               TB_AC_FUNCTION_MASTER      FM\r\n" +
            "                         WHERE VD.VOU_POSTING_DATE BETWEEN :fromDates AND\r\n" +
            "                               :toDates\r\n" +
            "                           AND VD.ORGID = :orgId\r\n" +
            "                           AND PM.CPD_ID_ACHEADTYPES = :lookUpId\r\n" +
            "                           AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" +
            "                           AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n" +
            "                           AND FM.FUNCTION_ID = SM.FUNCTION_ID\r\n" +
            "                         GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                  VD.SAC_HEAD_ID,\r\n" +
            "                                  SM.PAC_HEAD_ID,\r\n" +
            "                                  SM.FUNCTION_ID) A) AAA,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER B,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "           AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "           AND B.PAC_HEAD_PARENT_ID = C.PAC_HEAD_ID\r\n" +
            "           AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "         GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                  A.PAC_HEAD_DESC,\r\n" +
            "                  B.PAC_HEAD_ID,\r\n" +
            "                  C.PAC_HEAD_ID,\r\n" +
            "                  D.PAC_HEAD_ID,\r\n" +
            "                  A.PAC_HEAD_COMPO_CODE) P,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            " WHERE P.LEVEL4 = R.PAC_HEAD_ID\r\n" +
            " GROUP BY LEVEL4, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            " ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryReportDataFromViewDeatilHeadIncomeAndExpenditure(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "      CONCAT((CONCAT(R.PAC_HEAD_COMPO_CODE, ' - ')), R.PAC_HEAD_DESC),\r\n" +
            "	   SUM(P.VAMT_CR),\r\n" +
            "      SUM(P.VAMT_DR)\r\n" +
            "  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "               A.PAC_HEAD_DESC,\r\n" +
            "               A.PAC_HEAD_ID AS LEVEL4,\r\n" +
            "               B.PAC_HEAD_ID AS LEVEL3,\r\n" +
            "               C.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "               D.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" +
            "               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       A.PAC_HEAD_ID,\r\n" +
            "                       A.FUNCTION_ID\r\n" +
            "                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                               SM.PAC_HEAD_ID,\r\n" +
            "                               SM.FUNCTION_ID\r\n" +
            "                          FROM VW_VOUCHER_DETAIL          VD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER   PM,\r\n" +
            "                               TB_AC_FUNCTION_MASTER      FM\r\n" +
            "                         WHERE VD.VOU_POSTING_DATE BETWEEN :fromDates AND\r\n" +
            "                               :toDates\r\n" +
            "                           AND VD.ORGID = :orgId\r\n" +
            "                           AND PM.CPD_ID_ACHEADTYPES = :lookUpId\r\n" +
            "                           AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" +
            "                           AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n" +
            "                           AND FM.FUNCTION_ID = SM.FUNCTION_ID\r\n" +
            "                         GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                  VD.SAC_HEAD_ID,\r\n" +
            "                                  SM.PAC_HEAD_ID,\r\n" +
            "                                  SM.FUNCTION_ID) A) AAA,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER B,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "           AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "           AND B.PAC_HEAD_PARENT_ID = C.PAC_HEAD_ID\r\n" +
            "           AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "         GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                  A.PAC_HEAD_DESC,\r\n" +
            "                  B.PAC_HEAD_ID,\r\n" +
            "                  C.PAC_HEAD_ID,\r\n" +
            "                  D.PAC_HEAD_ID,\r\n" +
            "                  A.PAC_HEAD_COMPO_CODE) P,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            " WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            " GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            " ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryReportDataFromViewObjectClassIncomeAndExpenditure(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId);

    @Query("from AccountDepositEntity ade where  ade.orgid=:orgId and ade.dep_del_flag is null and ade.depReceiptdt between :fromDates and :toDates and ade.tbComparamDetEntity.cpdId=:registerDepTypeId")
    List<AccountDepositEntity> queryReportDataFromViewRegisterOfdeposit(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId,
            @Param("registerDepTypeId") Long registerDepTypeId);
    
    @Query(value = "Select A.*,B.col_0_1_,C.col_0_2_,D.col_0_3_\r\n" + 
    		"FROM\r\n" + 
    		"(select accountdep0_.DEP_ID as DEP_ID1_109_, accountdep0_.BM_ID as BM_ID23_109_,\r\n" + 
    		"accountdep0_.PAYMENT_ID as PAYMENT24_109_, accountdep0_.TR_TENDER_ID as TR_TEND25_109_,\r\n" + 
    		"accountdep0_.VOU_ID as VOU_ID26_109_, accountdep0_.CREATED_BY as CREATED_2_109_,\r\n" + 
    		"accountdep0_.CREATED_DATE as CREATED_3_109_, accountdep0_.DEFECT_LIABLITY_DATE as DEFECT_L4_109_,\r\n" + 
    		"accountdep0_.DEP_AMOUNT as DEP_AMOU5_109_, accountdep0_.DEP_ENTRY_TYPE as DEP_ENTR6_109_,\r\n" + 
    		"accountdep0_.DEP_NARRATION as DEP_NARR7_109_, accountdep0_.DEP_NO as DEP_NO8_109_,\r\n" + 
    		"accountdep0_.DEP_RECEIPTDT as DEP_RECE9_109_, accountdep0_.DEP_RECEIVEDFROM as DEP_REC10_109_,\r\n" + 
    		"accountdep0_.DEP_REFERENCENO as DEP_REF11_109_, accountdep0_.DEP_REFUND_BAL as DEP_REF12_109_,\r\n" + 
    		"accountdep0_.DEP_DEL_AUTH_BY as DEP_DEL13_109_, accountdep0_.DEP_DEL_DATE as DEP_DEL14_109_,\r\n" + 
    		"accountdep0_.DEP_DEL_FLAG as DEP_DEL15_109_, accountdep0_.DEP_DEL_REMARK as DEP_DEL16_109_,\r\n" + 
    		"accountdep0_.LG_IP_MAC as LG_IP_M17_109_, \r\n" + 
    		"accountdep0_.ORGID as ORGID19_109_, accountdep0_.SAC_HEAD_ID as SAC_HEA20_109_,\r\n" + 
    		"accountdep0_.CPD_DEPOSIT_TYPE as CPD_DEP27_109_, comp.CPD_DESC as CPD_STA28_109_,\r\n" + 
    		"accountdep0_.DP_DEPTID as DP_DEPT29_109_, accountdep0_.RM_RCPTID as RM_RCPT30_109_,\r\n" + 
    		"vm.VM_VENDORNAME as VM_VEND31_109_, \r\n" + 
    		"accounthea0_.AC_HEAD_CODE as col_0_0_\r\n" + 
    		"from   TB_AC_DEPOSITS accountdep0_\r\n" + 
    		"INNER JOIN TB_AC_SECONDARYHEAD_MASTER accounthea0_ on accounthea0_.SAC_HEAD_ID=accountdep0_.SAC_HEAD_ID\r\n" + 
    		"INNER JOIN TB_COMPARAM_DET comp on comp.cpd_id=accountdep0_.CPD_DEPOSIT_TYPE\r\n" + 
    		"INNER JOIN TB_VENDORMASTER vm on vm.VM_VENDORID=accountdep0_.VM_VENDORID\r\n" + 
    		"where accountdep0_.ORGID=:orgId\r\n" + 
    		"and (accountdep0_.DEP_DEL_FLAG is null)\r\n" + 
    		"and (accountdep0_.DEP_RECEIPTDT between :fromDates and :toDates)\r\n" + 
    		" and accountdep0_.CPD_DEPOSIT_TYPE=:registerDepTypeId \r\n" + 
    		" )A\r\n" + 
    		" LEFT JOIN (select accountdep0_.DEP_ID,sum(accountpay4_.PAYMENT_AMT) as col_0_1_\r\n" + 
    		"from TB_AC_DEPOSITS accountdep0_\r\n" + 
    		"cross join TB_AC_BILL_MAS accountbil1_\r\n" + 
    		"cross join TB_AC_BILL_EXP_DETAIL accountbil2_\r\n" + 
    		"cross join TB_AC_PAYMENT_MAS accountpay3_\r\n" + 
    		"cross join TB_AC_PAYMENT_DET accountpay4_\r\n" + 
    		"cross join TB_AC_VOUCHER accountvou5_\r\n" + 
    		"where accountbil1_.BM_ID=accountdep0_.BM_ID\r\n" + 
    		"and accountbil1_.BM_ID=accountbil2_.BM_ID\r\n" + 
    		"and accountbil2_.BM_ID=accountpay4_.BM_ID\r\n" + 
    		"and accountbil1_.BM_ID=accountpay4_.BM_ID\r\n" + 
    		"and accountvou5_.VOU_REFERENCE_NO=accountpay3_.PAYMENT_NO\r\n" + 
    		"and accountvou5_.AUTHO_FLG='Y' and accountvou5_.VOU_DATE=accountpay3_.PAYMENT_DATE\r\n" + 
    		"and accountvou5_.ORGID=accountpay3_.ORGID and accountpay3_.PAYMENT_ID=accountpay4_.PAYMENT_ID\r\n" + 
    		"and accountdep0_.ORGID=:orgId \r\n" + 
    		"and (accountpay3_.PAYMENT_DATE between :fromDates and :toDates)\r\n" + 
    		"Group by accountdep0_.DEP_ID)B ON A.DEP_ID1_109_=B.DEP_ID\r\n" + 
    		"LEFT JOIN (select accountdep0_.DEP_ID,sum(accountbil2_.DEDUCTION_AMT) as col_0_2_\r\n" + 
    		"from TB_AC_DEPOSITS accountdep0_\r\n" + 
    		"cross join TB_AC_BILL_MAS accountbil1_\r\n" + 
    		"cross join TB_AC_BILL_DEDUCTION_DETAIL accountbil2_\r\n" + 
    		"where accountbil1_.BM_ID=accountdep0_.BM_ID\r\n" + 
    		"and accountbil1_.BM_ID=accountbil2_.BM_ID\r\n" + 
    		"and accountdep0_.ORGID=:orgId\r\n" + 
    		"and (accountbil1_.CHECKER_DATE between :fromDates and :toDates)\r\n" + 
    		"Group by accountdep0_.DEP_ID)C ON A.DEP_ID1_109_=C.DEP_ID\r\n" + 
    		"LEFT JOIN (\r\n" + 
    		"select accountdep0_.DEP_ID,sum(accountvou2_.VOUDET_AMT) as col_0_3_\r\n" + 
    		"from TB_AC_DEPOSITS accountdep0_\r\n" + 
    		"cross join TB_AC_VOUCHER accountvou1_\r\n" + 
    		"cross join TB_AC_VOUCHER_DET accountvou2_\r\n" + 
    		"where accountdep0_.DEP_NO=accountvou1_.VOU_REFERENCE_NO\r\n" + 
    		"and accountvou1_.VOU_ID=accountvou2_.VOU_ID\r\n" + 
    		"and accountvou1_.AUTHO_FLG='Y'\r\n" + 
    		"and accountdep0_.ORGID=accountvou1_.ORGID\r\n" + 
    		"and (accountvou1_.VOU_SUBTYPE_CPD_ID in (select tbcomparam3_.CPD_ID from TB_COMPARAM_DET tbcomparam3_\r\n" + 
    		"where tbcomparam3_.CPD_VALUE='DFA')) and (accountvou2_.DRCR_CPD_ID\r\n" + 
    		"in (select tbcomparam4_.CPD_ID from TB_COMPARAM_DET tbcomparam4_\r\n" + 
    		"where tbcomparam4_.CPD_VALUE='DR')) and accountdep0_.ORGID=:orgId\r\n" + 
    		"and (accountvou1_.VOU_POSTING_DATE between :fromDates and :toDates)\r\n" + 
    		"Group by accountdep0_.DEP_ID)D ON A.DEP_ID1_109_=D.DEP_ID", nativeQuery = true)
    List<Object[]> queryReportDataFromViewRegisterOfdeposits(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId,
            @Param("registerDepTypeId") Long registerDepTypeId);

    @Query("SELECT sum(pm.paymentAmount) from AccountPaymentMasterEntity pm ,TbAcChequebookleafDetEntity cd , AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " WHERE pm.paymentId = cd.paymentId "
            + " and sm.tbBankaccount.baAccountId = pm.baBankAccountId.baAccountId "
           /* + " and pm.chequeClearanceDate is null"*/ + " and pm.paymentDeletionFlag is null" + " and pm.orgId =:orgId " + " and sm.sacHeadId =:accountHeadId "
            + " and cd.issuanceDate <=:transactionDate"+"  and ( pm.chequeClearanceDate is null or ( pm.chequeClearanceDate is not null and  pm.chequeClearanceDate > :transactionDate))"
			+ " and cd.cancellationDate is null")
    BigDecimal findChequeIssuedButNotPresent(@Param("orgId") long orgId, @Param("accountHeadId") Long accountHeadId,
            @Param("transactionDate") Date transactionDate);
    
    @Query("SELECT sum(pm.paymentAmount) from AccountPaymentMasterEntity pm ,TbAcChequebookleafDetEntity cd , AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " WHERE pm.paymentId = cd.paymentId and cd.cpdIdstatus=:cpdIdstatus"
           /* + " and pm.chequeClearanceDate is null"*/ + " and pm.paymentDeletionFlag is null" + " and pm.orgId =:orgId " + " and sm.sacHeadId =:accountHeadId "
            + " and cd.issuanceDate <=:transactionDate"+"  and ( pm.chequeClearanceDate is null or ( pm.chequeClearanceDate is not null and  pm.chequeClearanceDate > :transactionDate))"
			+ " and cd.cancellationDate is null")
    BigDecimal findChequeIssuedAndPaymentStop(@Param("orgId") long orgId, @Param("accountHeadId") Long accountHeadId,
            @Param("transactionDate") Date transactionDate,@Param("cpdIdstatus") Long cpdIdstatus);
    

    @Query(" select sum(rd.rfFeeamount) from TbServiceReceiptMasEntity rmas,TbSrcptFeesDetEntity rd, TbSrcptModesDetEntity rm , AccountBankDepositeSlipMasterEntity dm ,AccountHeadSecondaryAccountCodeMasterEntity sm "
            + "where dm.depositeSlipId=rd.depositeSlipId " + " and rd.rmRcptid.rmRcptid=rmas.rmRcptid "
            + "and sm.tbBankaccount.baAccountId= dm.depositeBAAccountId" + " and rmas.rmRcptid= rm.rmRcptid.rmRcptid "
            + " and dm.depositeSlipDate <=:transactionDate " + " and rm.orgId=:orgId "
            + " and rm.rdChequeddno is not null " + " and sm.sacHeadId=:accountHeadId "
            + " and ( rm.rdSrChkDate is null or ( rm.rdSrChkDate is not null and  rm.rdSrChkDate > :transactionDate))")
    BigDecimal findCheckDeposited(@Param("orgId") long orgId, @Param("accountHeadId") Long accountHeadId,
            @Param("transactionDate") Date transactionDate);

    @Query("SELECT distinct v1.prBudgetCodeid FROM AccountBudgetCodeEntity v1, AccountBudgetProjectedRevenueEntryEntity v2"
            + " WHERE v1.orgid=:orgId and v2.faYearid=:financialYearId  and v1.prBudgetCodeid=v2.tbAcBudgetCodeMaster.prBudgetCodeid")
    List<Object[]> queryReportDataFromBudgetEstimation(@Param("orgId") Long orgId,
            @Param("financialYearId") Long financialYearId);

    @Query(value = "select budget_code,(SELECT  sum(a.ORGINAL_ESTAMT)/count(*)*count(distinct a.FA_YEARID) from tb_ac_projectedrevenue a,tb_financialyear b"
            + " where BUDGETCODE_ID=:bcodeId" + " and a.FA_YEARID=b.FA_YEARID "
            + " and b.fa_fromdate between DATE_ADD(sysdate(), INTERVAL -:orgId year) and sysdate()) ,DATE_ADD(sysdate(), INTERVAL -1 year) as \"Actual Last Year\",b.ORGINAL_ESTAMT,b.REVISED_ESTAMT,b.NXT_YR_OE,a.CREATED_BY "
            + " from tb_ac_budgetcode_mas a,tb_ac_projectedrevenue b,tb_financialyear c "
            + " where a.BUDGETCODE_ID=:bcodeId and a.BUDGETCODE_ID=b.BUDGETCODE_ID " + " and b.FA_YEARID=:financialId "
            + " and sysdate() between c.fa_fromdate  and c.fa_todate", nativeQuery = true)
    List<Object[]> queryReportBudgetEstimation(@Param("bcodeId") Long bcodeId, @Param("financialId") Long financialId);

    @Query(value = "SELECT \r\n" + "    bm.BUDGETCODE_ID,\r\n" + "    bm.BUDGET_CODE,\r\n"
            + "    pr.ORGINAL_ESTAMT,\r\n" + "    ra.TRANSFER_AMOUNT,\r\n" + "    ra.CREATED_DATE,\r\n"
            + "    ra.CREATED_BY,\r\n" + "	 PA.REMARK	" + "FROM\r\n" + "    tb_ac_budgetcode_mas bm,\r\n"
            + "    tb_ac_projected_expenditure pr,\r\n" + "    tb_ac_projectedprovisionadj_tr ra,\r\n"
            + "	 TB_AC_PROJECTEDPROVISIONADJ PA " + "WHERE\r\n" + "    bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n"
            + "        AND pr.PR_EXPENDITUREID = ra.PR_EXPENDITUREID\r\n" + "		 AND PA.PA_ADJID = ra.PA_ADJID "
            + "		 AND ra.AUTH_FLG='Y'" + "        AND pr.ORGID =:orgId\r\n"
            + "        AND pr.FA_YEARID =:financialId\r\n"
            + "        GROUP BY bm.BUDGETCODE_ID,bm.BUDGET_CODE,pr.ORGINAL_ESTAMT,ra.TRANSFER_AMOUNT,ra.CREATED_DATE,ra.CREATED_BY,PA.REMARK", nativeQuery = true)
    List<Object[]> queryReAppReptExpDecrement(@Param("orgId") Long orgId, @Param("financialId") Long financialId);

    @Query(value = "SELECT \r\n" + "    bm.BUDGETCODE_ID,\r\n" + "    bm.BUDGET_CODE,\r\n"
            + "    pr.ORGINAL_ESTAMT,\r\n" + "    ra.TRANSFER_AMOUNT,\r\n" + "    ra.CREATED_DATE,\r\n"
            + "    ra.CREATED_BY,\r\n" + "    ra.REMARK\r\n" + "FROM\r\n" + "    tb_ac_budgetcode_mas bm,\r\n"
            + "    tb_ac_projected_expenditure pr,\r\n" + "    tb_ac_projectedprovisionadj ra\r\n" + "WHERE\r\n"
            + "    bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n"
            + "        AND pr.PR_EXPENDITUREID = ra.PR_EXPENDITUREID\r\n" + "		 AND ra.AUTH_FLG='Y'"
            + "        AND pr.ORGID = :orgId\r\n" + "        AND pr.FA_YEARID = :financialId\r\n"
            + "        GROUP BY bm.BUDGETCODE_ID,bm.BUDGET_CODE ,pr.ORGINAL_ESTAMT,ra.TRANSFER_AMOUNT,ra.CREATED_DATE,ra.CREATED_BY,ra.REMARK", nativeQuery = true)
    List<Object[]> queryReAppReptExpIncrement(@Param("orgId") Long orgId, @Param("financialId") Long financialId);

    @Query(" select sum(rd.rfFeeamount) from TbServiceReceiptMasEntity rmas,TbSrcptFeesDetEntity rd, TbSrcptModesDetEntity rm , AccountBankDepositeSlipMasterEntity dm ,AccountHeadSecondaryAccountCodeMasterEntity sm "
            + " where dm.depositeSlipId=rd.depositeSlipId " + " and rd.rmRcptid.rmRcptid=rmas.rmRcptid "
            + " and rmas.rmRcptid= rm.rmRcptid.rmRcptid " + " and sm.tbBankaccount.baAccountId= dm.depositeBAAccountId"
            + " and dm.depositeSlipDate <= :transactionDate " + " and rm.orgId=:orgId "
            + " and rm.rdChequeddno is not null " + " and sm.sacHeadId=:accountHeadId "
            + " and rm.rdSrChkDate is not null " + " and rm.rd_dishonor_remark is not null")
    BigDecimal findChequeDepositButDishonoured(@Param("orgId") long orgId, @Param("accountHeadId") Long accountHeadId,
            @Param("transactionDate") Date transactionDate);

    @Query("SELECT \r\n" + "    vh.vouPostingDate,\r\n" + "    vh.vouTypeCpdId,\r\n" + "    vh.vouNo,\r\n"
            + "    vh.vouSubtypeCpdId,\r\n" + "    vh.vouReferenceNo,\r\n" + "    vh.vouDate,\r\n"
            + "    vh.narration,\r\n" + "    vd.voudetAmt,\r\n" + "    vd.drcrCpdId,\r\n" + "    vd.sacHeadId\r\n"
            + "FROM\r\n" + "    AccountVoucherEntryEntity vh,\r\n" + "    AccountVoucherEntryDetailsEntity vd\r\n"
            + "WHERE\r\n" + "    vh.vouId = vd.master.vouId\r\n" + "        AND vh.authoFlg = 'Y'\r\n"
            + "        AND vh.vouPostingDate between :fromDates and :toDates \r\n"
            + "        AND vh.org =:orgId order by vh.vouPostingDate,vh.vouNo ")
    List<Object[]> findDayBookFromVoucherDetailViewEntity(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") long orgId);

    @Query("SELECT v1.acHeadCode,sum(v1.drAmount), sum(v1.crAmount) FROM VoucherDetailViewEntity v1 "
            + " WHERE v1.sacHeadId=:accidcHeadId AND v1.voPostingDate between :fromDatess and :transactionsDate"
            + " and v1.orgId=:orgId group by v1.acHeadCode")
    List<Object[]> queryReportDataCashEquivalent(@Param("transactionsDate") Date transactionsDate,
            @Param("orgId") Long orgId, @Param("accidcHeadId") long accidcHeadId, @Param("fromDatess") Date fromDatess);

    @Query("SELECT sum(vtd.crAmount), sum(vtd.drAmount) from VoucherDetailViewEntity vtd where  vtd.sacHeadId =:sacHeadId and vtd.orgId =:orgId and vtd.voPostingDate between :fromDates and :toDate ")
    List<Object[]> findCashAmountHeadId(@Param("sacHeadId") long sacHeadId, @Param("orgId") long orgId,
            @Param("toDate") Date toDate, @Param("fromDates") Date fromDates);

    @Query("SELECT sum(pd.paymentAmt) \r\n" + "   FROM\r\n" + "    AccountDepositEntity dp,\r\n"
            + "    AccountBillEntryMasterEnitity bm,\r\n" + "    AccountBillEntryExpenditureDetEntity be,\r\n"
            + "    AccountPaymentMasterEntity pm,\r\n" + "    AccountPaymentDetEntity pd,\r\n"
            + "   AccountVoucherEntryEntity vh " +

            "WHERE\r\n" + "    bm.id = dp.accountBillEntryMaster.id\r\n" + "        and bm.id = be.billMasterId.id\r\n"
            + "        and be.billMasterId.id = pd.billId\r\n" + "        and bm.id=pd.billId\r\n"
            + "  and vh.vouReferenceNo = pm.paymentNo\r\n" + "  and vh.authoFlg = 'Y'\r\n "
            + " and vh.vouDate=pm.paymentDate\r\n" + "  and vh.org=pm.orgId\r\n"
            + "        and pm.paymentId=pd.paymentMasterId.paymentId\r\n"
            + "        and dp.orgid=:orgId and dp.depId=:depId and pm.paymentDate between :fromDates and :toDates")
    BigDecimal findVoucherNumberAndPaymentAmount(@Param("toDates") Date toDates, @Param("fromDates") Date fromDates,
            @Param("orgId") Long orgId, @Param("depId") Long depId);

    @Query("SELECT sum(bd.deductionAmount)\r\n" + "   FROM\r\n" + "    AccountDepositEntity dp,\r\n"
            + "    AccountBillEntryMasterEnitity bm,\r\n" + "    AccountBillEntryDeductionDetEntity bd\r\n"
            + "WHERE\r\n" + "    bm.id = dp.accountBillEntryMaster.id\r\n"
            + "        AND bm.id = bd.billMasterId.id\r\n"
            + "        and dp.orgid=:orgId and dp.depId=:depId and bm.checkerDate between :fromDates and :toDates ")
    BigDecimal findDeductionAmount(@Param("toDates") Date toDates, @Param("fromDates") Date fromDates,
            @Param("orgId") Long orgId, @Param("depId") Long depId);

    @Query("SELECT \r\n" + "     sum (vd.voudetAmt)\r\n" + "FROM\r\n" + "    AccountDepositEntity dp,\r\n"
            + "    AccountVoucherEntryEntity vh,\r\n" + "    AccountVoucherEntryDetailsEntity vd\r\n" + "WHERE\r\n"
            + "    dp.depNo = vh.vouReferenceNo\r\n" + "  and   vh.vouId = vd.master.vouId \r\n"
            + "        AND vh.authoFlg = 'Y'\r\n" + "        and dp.orgid=vh.org"
            + "        AND vh.vouSubtypeCpdId IN (SELECT \r\n" + "            cpdId\r\n" + "        FROM\r\n"
            + "            TbComparamDetEntity\r\n" + "        WHERE\r\n" + "            cpdValue = 'DFA')\r\n"
            + "        AND vd.drcrCpdId IN (SELECT \r\n" + "            cpdId\r\n" + "        FROM\r\n"
            + "            TbComparamDetEntity\r\n" + "        WHERE\r\n" + "            cpdValue = 'DR')\r\n"
            + "        AND dp.orgid =:orgId and dp.depId=:depId and vh.vouPostingDate between :fromDates and :toDates ")
    BigDecimal findVoucherNumber(@Param("toDates") Date toDates, @Param("fromDates") Date fromDates,
            @Param("orgId") Long orgId, @Param("depId") Long depId);

    @Query("SELECT  fy.faYear, fy.faFromDate, fy.faToDate FROM FinancialYear fy Where fy.faYear=:financialId order by fy.faYear desc")
    List<Object[]> getAllFinincialFromDate(@Param("financialId") Long financialId);

    @Query("SELECT fy.faYear FROM FinancialYear fy Where fy.faFromDate between :frmDates and :toDates order by fy.faYear desc")
    List<Object[]> getAllFinincialFinancialYearId(@Param("frmDates") Date frmDates, @Param("toDates") Date toDates);

    @Query(value = "SELECT budget_code,\r\n" + "       (SELECT SUM(a.ORGINAL_ESTAMT) / COUNT(*) *\r\n"
            + "               COUNT(DISTINCT a.FA_YEARID)\r\n"
            + "          FROM tb_ac_projectedrevenue a, tb_financialyear b\r\n"
            + "         WHERE BUDGETCODE_ID =:bcodeId\r\n" + "           AND a.FA_YEARID = b.FA_YEARID\r\n"
            + "           AND b.fa_fromdate BETWEEN :frmDates AND :toDates) as Avrage ,b.ORGINAL_ESTAMT,b.REVISED_ESTAMT,b.NXT_YR_OE,a.CREATED_BY      \r\n"
            + "  FROM tb_ac_budgetcode_mas a, tb_ac_projectedrevenue b, tb_financialyear c\r\n"
            + " WHERE a.BUDGETCODE_ID =:bcodeId \r\n" + "   AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "   AND b.FA_YEARID =:financialYearId\r\n"
            + "   AND :frmDates BETWEEN c.fa_fromdate AND c.fa_todate ", nativeQuery = true)

    List<Object[]> queryReportDataFromBudgetEstimationData(@Param("frmDates") Date frmDates,
            @Param("toDates") Date toDates, @Param("bcodeId") long bcodeId,
            @Param("financialYearId") long financialYearId);

    @Query(value = "SELECT budget_code,\r\n" + "       (SELECT SUM(a.ORGINAL_ESTAMT) / COUNT(*) *\r\n"
            + "               COUNT(DISTINCT a.FA_YEARID)\r\n"
            + "          FROM tb_ac_projected_expenditure a, tb_financialyear b\r\n"
            + "         WHERE BUDGETCODE_ID =:bcodeId\r\n" + "           AND a.FA_YEARID = b.FA_YEARID\r\n"
            + "           AND b.fa_fromdate BETWEEN :frmDates AND :toDates) as Avrage ,b.ORGINAL_ESTAMT,b.REVISED_ESTAMT,a.CREATED_BY\r\n"
            + "  FROM tb_ac_budgetcode_mas a,tb_ac_projected_expenditure b, tb_financialyear c\r\n"
            + " WHERE a.BUDGETCODE_ID =:bcodeId \r\n" + "   AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "   AND b.FA_YEARID =:financialYearId\r\n"
            + "   AND :frmDates BETWEEN c.fa_fromdate AND c.fa_todate ", nativeQuery = true)
    List<Object[]> queryReportDataFromExpenditureData(@Param("frmDates") Date frmDates, @Param("toDates") Date toDates,
            @Param("bcodeId") long bcodeId, @Param("financialYearId") long financialYearId);

    @Query("SELECT \r\n" + "sm.sacHeadId,\r\n" + "sm.acHeadCode,\r\n" + "bg.openbalAmt,\r\n"
            + "bg.tbComparamDet.cpdId,\r\n" + "bg.flagFlzd,\r\n" + "bg.faYearid\r\n" + "FROM\r\n"
            + "AccountBudgetOpenBalanceEntity bg,\r\n" + "AccountHeadSecondaryAccountCodeMasterEntity sm\r\n"
            + "WHERE\r\n" + "sm.sacHeadId = bg.tbAcSecondaryheadMaster.sacHeadId\r\n" + "AND sm.orgid = :orgId\r\n"
            + "AND bg.faYearid = :financialYearId ORDER BY bg.tbComparamDet.cpdId")
    List<Object[]> findOpeningBalanceReport(@Param("orgId") Long orgId, @Param("financialYearId") Long financialYearId);

    @Query(value = "SELECT A.PAYMENT_NO,\r\n" +
            "       A.PAYMENT_DATE,\r\n" +
            "       A.BM_BILLNO,\r\n" +
            "       B.VM_VENDORNAME,\r\n" +
            "       A.NARRATION,\r\n" +
            "       CASE\r\n" +
            "         WHEN (A.TRAN_REFNO IS NULL AND A.CHEQUE_NO IS NOT NULL) THEN\r\n" +
            "          A.CHEQUE_NO\r\n" +
            "         ELSE\r\n" +
            "          A.TRAN_REFNO\r\n" +
            "       END AS TRAN_REFNO,\r\n" +
            "       A.BM_ENTRYDATE,\r\n" +
            "       A.PAYMENT_AMT,\r\n" +
            "       A.INSTRUMENT_DATE,\r\n" +
            "       A.CHEQUE_CLEARANCE_DATE,\r\n" +
            "       A.CREATED_BY\r\n" +
            "  FROM (SELECT Z.*, X.CHEQUE_NO, ISSUANCE_DATE\r\n" +
            "          FROM (SELECT A.PAYMENT_ID,\r\n" +
            "                       A.VM_VENDORID,\r\n" +
            "                       A.PAYMENT_NO,\r\n" +
            "                       A.PAYMENT_DATE,\r\n" +
            "                       B.BM_BILLNO,\r\n" +
            "                       A.NARRATION,\r\n" +
            "                       A.TRAN_REFNO,\r\n" +
            "                       B.BM_ENTRYDATE,\r\n" +
            "                       A.PAYMENT_AMT,\r\n" +
            "                       A.INSTRUMENT_DATE,\r\n" +
            "                       A.CHEQUE_CLEARANCE_DATE,\r\n" +
            "                       A.CREATED_BY\r\n" +
            "                  FROM (SELECT PM.VM_VENDORID,\r\n" +
            "                               PD.BM_ID,\r\n" +
            "                               PM.PAYMENT_ID,\r\n" +
            "                               PM.PAYMENT_NO,\r\n" +
            "                               PAYMENT_DATE,\r\n" +
            "                               PM.NARRATION,\r\n" +
            "                               PM.TRAN_REFNO,\r\n" +
            "                               SUM(PD.PAYMENT_AMT) AS PAYMENT_AMT,\r\n" +
            "                               PM.INSTRUMENT_DATE,\r\n" +
            "                               PM.CHEQUE_CLEARANCE_DATE,\r\n" +
            "                               PM.CREATED_BY\r\n" +
            "                          FROM (SELECT VM_VENDORID,\r\n" +
            "                                       PAYMENT_ID,\r\n" +
            "                                       PAYMENT_NO,\r\n" +
            "                                       PAYMENT_DATE,\r\n" +
            "                                       NARRATION,\r\n" +
            "                                       TRAN_REFNO,\r\n" +
            "                                       INSTRUMENT_DATE,\r\n" +
            "                                       CHEQUE_CLEARANCE_DATE,\r\n" +
            "                                       CREATED_BY\r\n" +
            "                                  FROM TB_AC_PAYMENT_MAS\r\n" +
            "                                 WHERE PAYMENT_DEL_FLAG IS NULL\r\n" +
            "                                   AND ORGID = :orgId\r\n" +
            "                                   AND PAYMENT_DATE BETWEEN :fromDate AND\r\n" +
            "                                       :toDate AND BA_ACCOUNTID=:accountHeadId AND FIELDID=:fieldId) PM,\r\n" +
            "                               TB_AC_PAYMENT_DET PD\r\n" +
            "                         WHERE PM.PAYMENT_ID = PD.PAYMENT_ID\r\n" +
            "                         GROUP BY PM.VM_VENDORID,\r\n" +
            "                                  PM.PAYMENT_ID,\r\n" +
            "                                  PM.PAYMENT_NO,\r\n" +
            "                                  PD.BM_ID,\r\n" +
            "                                  PAYMENT_DATE,\r\n" +
            "                                  PM.NARRATION,\r\n" +
            "                                  PM.TRAN_REFNO,\r\n" +
            "                                  PM.INSTRUMENT_DATE,\r\n" +
            "                                  PM.CHEQUE_CLEARANCE_DATE,\r\n" +
            "                                  PM.CREATED_BY) A\r\n" +
            "                  LEFT JOIN (SELECT BM.BM_ID, BM.BM_BILLNO, BM.BM_ENTRYDATE\r\n" +
            "                              FROM TB_AC_BILL_MAS        BM,\r\n" +
            "                                   TB_AC_BILL_EXP_DETAIL BD\r\n" +
            "                             WHERE BM.BM_ID = BD.BM_ID\r\n" +
            "                             GROUP BY BM.BM_ID, BM.BM_BILLNO, BM.BM_ENTRYDATE) B\r\n" +
            "                    ON A.BM_ID = B.BM_ID) Z\r\n" +
            "          LEFT JOIN (SELECT CHEQUE_NO, PAYMENT_ID, ISSUANCE_DATE\r\n" +
            "                      FROM TB_AC_CHEQUEBOOKLEAF_DET\r\n" +
            "                     WHERE CANCELLATION_DATE IS NULL) X\r\n" +
            "            ON Z.PAYMENT_ID = X.PAYMENT_ID) A\r\n" +
            "  LEFT JOIN (SELECT * FROM TB_VENDORMASTER) B\r\n" +
            "    ON A.VM_VENDORID = B.VM_VENDORID\r\n" +
            " ORDER BY A.PAYMENT_DATE ASC\r\n", nativeQuery = true)
    List<Object[]> querypaymentChequeReport(@Param("fromDate") Date fromDate, @Param("orgId") long orgId,
            @Param("toDate") Date toDate,@Param("accountHeadId") Long accountHeadId,@Param("fieldId") Long fieldId);

    @Query(value = " SELECT  " + "    Y.OPENBAL_AMT, " + "    Y.SAC_HEAD_ID, " + "    Y.CPD_ID_DRCR, "
            + "    Y.TranCr, " + "    Y.TranDr " + "FROM " + "    (SELECT  " + "        X.OPENBAL_AMT, "
            + "            (CASE " + "                WHEN X.OPNSACHEADID IS NULL THEN X.TRNSACHEADID "
            + "                ELSE X.OPNSACHEADID " + "            END) SAC_HEAD_ID, " + "            X.CPD_ID_DRCR, "
            + "            SUM(X.VAMT_CR) TranCr, " + "            SUM(X.VAMT_DR) TranDr " + "    FROM "
            + "        (SELECT  " + "        VOUDET_ID,A.OPENBAL_AMT, " + "            A.SAC_HEAD_ID OPNSACHEADID, "
            + "            A.CPD_ID_DRCR, " + "            vd.VAMT_CR, " + "            vd.VAMT_DR, "
            + "            VD.SAC_HEAD_ID TRNSACHEADID " + "    FROM " + "        (SELECT  "
            + "        VOUDET_ID,SAC_HEAD_ID, VAMT_CR, VAMT_DR " + "    FROM " + "        vw_voucher_detail "
            + "    WHERE " + "        ORGID =:orgId "
            + "        and VOU_POSTING_DATE BETWEEN :fromDate AND :toDate) vd " + "    LEFT JOIN (SELECT  "
            + "        bg.OPENBAL_AMT, bg.CPD_ID_DRCR, SM.SAC_HEAD_ID " + "    FROM "
            + "        tb_ac_bugopen_balance bg, tb_ac_secondaryhead_master sm " + "    WHERE "
            + "        sm.SAC_HEAD_ID = bg.SAC_HEAD_ID "
            + "            AND sm.ORGID =:orgId and bg.FA_YEARID =:faYearIds) A ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID UNION  SELECT  "
            + "        VOUDET_ID,A.OPENBAL_AMT, " + "            A.SAC_HEAD_ID OPNSACHEADID, "
            + "            A.CPD_ID_DRCR, " + "            vd.VAMT_CR, " + "            vd.VAMT_DR, "
            + "            VD.SAC_HEAD_ID TRNSACHEADID " + "    FROM " + "        (SELECT  "
            + "        VOUDET_ID,SAC_HEAD_ID, VAMT_CR, VAMT_DR " + "    FROM " + "        vw_voucher_detail "
            + "    WHERE ORGID =:orgId " + "        and VOU_POSTING_DATE BETWEEN :fromDate AND :toDate) VD "
            + "    RIGHT JOIN (SELECT  " + "        bg.OPENBAL_AMT, bg.CPD_ID_DRCR, SM.SAC_HEAD_ID " + "    FROM "
            + "        tb_ac_bugopen_balance bg, tb_ac_secondaryhead_master sm " + "    WHERE "
            + "        sm.SAC_HEAD_ID = bg.SAC_HEAD_ID "
            + "            AND sm.ORGID =:orgId and bg.FA_YEARID =:faYearIds) A ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID) X "
            + "    GROUP BY X.OPNSACHEADID , X.OPENBAL_AMT , X.CPD_ID_DRCR , X.TRNSACHEADID) Y ", nativeQuery = true)

    List<Object[]> queryForForYearEndProcessData(@Param("faYearIds") Long faYearIds, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId);

    @Query("SELECT rm.rmReceivedfrom,rm.rmRcptno,re.rdChequeddno,rm.rmAmount,re.rdSrChkDisChg,re.rdSrChkDate  \r\n"
            + "FROM \r\n" + "TbServiceReceiptMasEntity rm,\r\n" + "TbSrcptModesDetEntity re\r\n" + "where\r\n"
            + "rm.rmRcptid=re.rmRcptid.rmRcptid\r\n" + "and re.checkStatus =:chequeStatus\r\n" + "and rm.orgId =:orgId \r\n"
            + "and re.rdSrChkDate BETWEEN :fromDate AND :toDate ")
    List<Object[]> queryChequeDishonorReport(@Param("fromDate") Date fromDate, @Param("orgId") long orgId,
            @Param("toDate") Date toDate,@Param("chequeStatus") Long chequeStatus);

    @Query(value = "SELECT PR.BUDGETCODE_ID, BM.BUDGET_CODE, SUM(PD.PAYMENT_AMT)\r\n"
            + "  FROM TB_AC_BUDGETCODE_MAS        BM,\r\n" + "       TB_AC_SECONDARYHEAD_MASTER  SM,\r\n"
            + "       TB_AC_FUNCTION_MASTER       FM,\r\n" + "       TB_AC_PRIMARYHEAD_MASTER    PM,\r\n"
            + "       TB_AC_PROJECTED_EXPENDITURE PR,\r\n" + "       TB_AC_PAYMENT_MAS           PS,\r\n"
            + "       TB_AC_PAYMENT_DET           PD\r\n" + " WHERE SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n"
            + "   AND SM.FUNCTION_ID = FM.FUNCTION_ID\r\n" + "   AND SM.SAC_HEAD_ID = PD.BUDGETCODE_ID\r\n"
            + "   AND PS.PAYMENT_ID = PD.PAYMENT_ID\r\n" + "   AND PM.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n"
            + "   AND FM.FUNCTION_ID = BM.FUNCTION_ID\r\n" + "   AND BM.BUDGETCODE_ID = PR.BUDGETCODE_ID\r\n"
            + "   AND PS.PAYMENT_DEL_FLAG IS NULL\r\n" + "   AND BM.ORGID = PR.ORGID\r\n"
            + "   AND PS.ORGID = PD.ORGID\r\n" + "   AND SM.ORGID = PS.ORGID\r\n" + "   AND SM.ORGID = PD.ORGID\r\n"
            + "   AND PS.PAYMENT_DATE BETWEEN :fromDate AND :toDate\r\n" + "   AND PR.FA_YEARID = :financialId\r\n"
            + "   AND PD.ORGID = :orgId\r\n" + " GROUP BY PR.BUDGETCODE_ID, BM.BUDGET_CODE", nativeQuery = true)
    List<Object[]> queryReAppReptExpUtilizationAmt(@Param("orgId") Long orgId, @Param("financialId") Long financialId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query(value = "SELECT a.BUDGETCODE_ID,a.BUDGET_CODE,\r\n" + "    b.ORGINAL_ESTAMT,\r\n"
            + "    b.REVISED_ESTAMT,\r\n" + "    d.ESTIMATE_FOR_NEXTYEAR\r\n" + "FROM\r\n"
            + "    tb_ac_budgetcode_mas a,\r\n" + "    tb_ac_projectedrevenue b,\r\n" + "    tb_financialyear c,\r\n"
            + "    tb_ac_budgetory_estimate d\r\n" + "WHERE\r\n" + "    a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "        AND b.FA_YEARID = c.FA_YEARID\r\n" + "        AND d.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "        and b.ORGID =:orgId\r\n" + "        and c.FA_FROMDATE =:fromDat\r\n"
            + "       order by a.BUDGETCODE_ID,a.BUDGET_CODE", nativeQuery = true)
    List<Object[]> getAllBudgetEnstimationCurrentData(@Param("fromDat") Date fromDat, @Param("orgId") long orgId);

    @Query(value = "SELECT\r\n" + "    sum(b.ORGINAL_ESTAMT)/ COUNT(*),\r\n" + "    sum(b.REVISED_ESTAMT)\r\n"
            + "   \r\n" + "FROM\r\n" + "    tb_ac_budgetcode_mas a,\r\n" + "    tb_ac_projectedrevenue b,\r\n"
            + "    tb_financialyear c,\r\n" + "    tb_ac_budgetory_estimate d\r\n" + "WHERE\r\n"
            + "    a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + "        AND b.FA_YEARID = c.FA_YEARID\r\n"
            + "        AND d.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + "        and b.ORGID =:orgId\r\n"
            + "        AND b.BUDGETCODE_ID =:budgetCodeId\r\n"
            + "        and c.FA_FROMDATE between :lastfromDates and :lastToDates", nativeQuery = true)
    List<Object[]> getAllBudgetEnstimationLastData(@Param("budgetCodeId") long budgetCodeId, @Param("orgId") long orgId,
            @Param("lastfromDates") Date lastfromDates, @Param("lastToDates") Date lastToDates);

    @Query(value = "SELECT\r\n" + "    b.ORGINAL_ESTAMT\r\n" + "FROM\r\n" + "    tb_ac_budgetcode_mas a,\r\n"
            + "    tb_ac_projectedrevenue b,\r\n" + "    tb_financialyear c,\r\n" + "    tb_ac_budgetory_estimate d\r\n"
            + "WHERE\r\n" + "        a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "        AND b.FA_YEARID = c.FA_YEARID\r\n" + "        AND d.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "        and a.BUDGETCODE_ID =:budgetCodeId\r\n" + "        and b.ORGID =:orgId\r\n"
            + "        and c.FA_FROMDATE =:fromlastoneyr", nativeQuery = true)
    BigDecimal getAllBudgetEnstimationLastOneYrData(@Param("budgetCodeId") long budgetCodeId,
            @Param("orgId") long orgId, @Param("fromlastoneyr") Date fromlastoneyr);

    @Query("SELECT fy.faYear FROM FinancialYear fy Where fy.faFromDate=:lastyrdate order by fy.faYear desc")
    Long getAllFinincialPrevDate(@Param("lastyrdate") Date lastyrdate);

    @Query(value = "select a.BUDGETCODE_ID,\r\n"
            + "  c.budget_code,a.avg,c.LastYear,c.REVISED_ESTAMT,b.CurrYear,c.ESTIMATE_FOR_NEXTYEAR,CREATED_BY\r\n"
            + " from       \r\n"
            + " (SELECT BUDGETCODE_ID,:financialId as id_fin_year,(SUM(a.ORGINAL_ESTAMT) / COUNT(*)) as avg  FROM tb_ac_projected_expenditure a, tb_financialyear b \r\n"
            + " WHERE  BUDGETCODE_ID in (select distinct BUDGETCODE_ID as BUDGETCODE_ID from tb_ac_projected_expenditure)\r\n"
            + "  AND a.FA_YEARID = b.FA_YEARID AND b.fa_fromdate BETWEEN :lastfromDates AND :lastToDates \r\n"
            + "  group by BUDGETCODE_ID) a,(SELECT b.BUDGETCODE_ID,:financialId as id_fin_year,\r\n"
            + "    b.ORGINAL_ESTAMT as CurrYear  \r\n" + "FROM\r\n" + "    tb_ac_budgetcode_mas a,\r\n"
            + "    tb_ac_projected_expenditure b,\r\n" + "    tb_financialyear c,\r\n"
            + "    tb_ac_budgetory_estimate d\r\n" + "WHERE\r\n" + "     b.FA_YEARID=c.FA_YEARID\r\n"
            + "     AND d.BUDGETCODE_ID=b.BUDGETCODE_ID\r\n" + "        AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "         AND b.ORGID =:orgId\r\n"
            + "        and  a.BUDGETCODE_ID in (select distinct BUDGETCODE_ID as BUDGETCODE_ID from tb_ac_projected_expenditure)\r\n"
            + "        AND b.FA_YEARID =:financialId)b, ( SELECT b.BUDGETCODE_ID,:financialId as id_fin_year,\r\n"
            + "    budget_code,\r\n" + "    b.ORGINAL_ESTAMT as LastYear,\r\n" + "    b.REVISED_ESTAMT,\r\n"
            + "    d.ESTIMATE_FOR_NEXTYEAR,\r\n" + "    a.CREATED_BY\r\n" + "FROM\r\n"
            + "    tb_ac_budgetcode_mas a,\r\n" + "    tb_ac_projected_expenditure b,\r\n"
            + "    tb_financialyear c,\r\n" + "    tb_ac_budgetory_estimate d\r\n" + "WHERE\r\n"
            + "     b.FA_YEARID=c.FA_YEARID\r\n" + "     AND d.BUDGETCODE_ID=b.BUDGETCODE_ID\r\n"
            + "        AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + "        AND b.ORGID =:orgId\r\n"
            + "        and  a.BUDGETCODE_ID in (select distinct BUDGETCODE_ID as BUDGETCODE_ID from tb_ac_projected_expenditure)\r\n"
            + "        AND b.FA_YEARID =:financialId) c\r\n" + "        where a.BUDGETCODE_ID=b.BUDGETCODE_ID\r\n"
            + "        and a.BUDGETCODE_ID=c.BUDGETCODE_ID\r\n" + "        and a.id_fin_year=b.id_fin_year\r\n"
            + "        and a.id_fin_year=c.id_fin_year ", nativeQuery = true)
    List<Object[]> getAllExpenditureBudgetEnstimation(@Param("financialId") long financialId,
            @Param("lastfromDates") Date lastfromDates, @Param("lastToDates") Date lastToDates,
            @Param("orgId") Long orgId);

    @Query("select  op.openbalAmt,op.tbComparamDet.cpdId from AccountBudgetOpenBalanceEntity op where op.tbAcSecondaryheadMaster.sacHeadId=:sacHeadId and op.orgid=:orgId and op.faYearid=:financialYearId")
    List<Object[]> findOpeningBalanceAmount(@Param("sacHeadId") Long sacHeadId, @Param("orgId") Long orgId,
            @Param("financialYearId") Long financialYearId);

    @Query(value = "SELECT \r\n" + "    bm.BUDGETCODE_ID,\r\n" + "    bm.BUDGET_CODE,\r\n"
            + "    pr.ORGINAL_ESTAMT,\r\n" + "    ra.TRANSFER_AMOUNT,\r\n" + "    ra.CREATED_DATE,\r\n"
            + "    ra.CREATED_BY,\r\n" + "	 PA.REMARK	" + "FROM\r\n" + "    tb_ac_budgetcode_mas bm,\r\n"
            + "    tb_ac_projectedrevenue pr,\r\n" + "    tb_ac_projectedprovisionadj_tr ra,\r\n"
            + "	 TB_AC_PROJECTEDPROVISIONADJ PA " + "WHERE\r\n" + "    bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n"
            + "        AND pr.PR_PROJECTIONID = ra.PR_PROJECTIONID\r\n" + "		 AND PA.PA_ADJID = ra.PA_ADJID "
            + "		 AND ra.AUTH_FLG='Y'" + "        AND pr.ORGID =:orgId\r\n"
            + "        AND pr.FA_YEARID =:financialId\r\n"
            + "GROUP BY bm.BUDGETCODE_ID , bm.BUDGET_CODE , pr.ORGINAL_ESTAMT , ra.TRANSFER_AMOUNT , ra.CREATED_DATE , ra.CREATED_BY, PA.REMARK", nativeQuery = true)
    List<Object[]> queryReAppReptReceiptDecrement(@Param("orgId") long orgId, @Param("financialId") long financialId);

    @Query(value = "SELECT \r\n" + "    bm.BUDGETCODE_ID,\r\n" + "    bm.BUDGET_CODE,\r\n"
            + "    pr.ORGINAL_ESTAMT,\r\n" + "    ra.TRANSFER_AMOUNT,\r\n" + "    ra.CREATED_DATE,\r\n"
            + "    ra.CREATED_BY,\r\n" + "    ra.REMARK\r\n" + "FROM\r\n" + "    tb_ac_budgetcode_mas bm,\r\n"
            + "    tb_ac_projectedrevenue pr,\r\n" + "    tb_ac_projectedprovisionadj ra\r\n" + "WHERE\r\n"
            + "    bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" + "        AND pr.PR_PROJECTIONID = ra.PR_PROJECTIONID\r\n"
            + "		 AND ra.AUTH_FLG='Y'" + "        AND pr.ORGID =:orgId\r\n"
            + "        AND pr.FA_YEARID =:financialId\r\n"
            + "GROUP BY bm.BUDGETCODE_ID , bm.BUDGET_CODE , pr.ORGINAL_ESTAMT , ra.TRANSFER_AMOUNT , ra.CREATED_DATE , ra.CREATED_BY , ra.REMARK ", nativeQuery = true)
    List<Object[]> queryReAppReptReceiptIncrement(@Param("orgId") long orgId, @Param("financialId") long financialId);

    @Query(value = "SELECT \r\n" + "  SUM(rd.RF_FEEAMOUNT)\r\n" + "FROM\r\n" + "    tb_ac_budgetcode_mas bm,\r\n"
            + "    tb_ac_secondaryhead_master sm,\r\n" + "    tb_ac_function_master fm,\r\n"
            + "    tb_ac_primaryhead_master pm,\r\n" + "    tb_ac_projectedrevenue pr,\r\n"
            + "    tb_receipt_det rd,\r\n" + "    tb_ac_projectedprovisionadj_tr tr\r\n" + "WHERE\r\n"
            + "    sm.PAC_HEAD_ID = pm.PAC_HEAD_ID\r\n" + "        AND sm.FUNCTION_ID = fm.FUNCTION_ID\r\n"
            + "        AND sm.SAC_HEAD_ID = rd.SAC_HEAD_ID\r\n" + "        AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n"
            + "        AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n" + "        AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n"
            + "        AND tr.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n"
            + "        AND tr.BUDGETCODE_ID = bm.BUDGETCODE_ID\r\n" + "        AND tr.ORGID = pr.ORGID\r\n"
            + "        AND pr.FA_YEARID =:financialId\r\n" + "        AND pr.ORGID =:orgId\r\n"
            + "        AND pr.BUDGETCODE_ID =:budgetCodeId\r\n"
            + "GROUP BY pr.BUDGETCODE_ID , bm.BUDGET_CODE", nativeQuery = true)
    List<BigDecimal> queryReAppropriationReportUtilTotalAmtReceipt(@Param("orgId") long orgId,
            @Param("financialId") long financialId, @Param("budgetCodeId") long budgetCodeId);
//D#163428
    @Query("select CONCAT(v1.empname,' ',v1.empmname,' ',v1.emplname) from Employee v1 where v1.empId=:EmpId")
    String queryReAppropriationReportForUser(@Param("EmpId") long EmpId);

    @Query("SELECT  coalesce(sum(vtd.crAmount),0),coalesce(sum(vtd.drAmount),0)  from VoucherDetailViewEntity vtd where  vtd.sacHeadId =:accountHeadId and vtd.orgId =:orgId and vtd.voPostingDate between :financialYFromDate  and :dateBefore ")
    List<Object[]> getSumOfCreditAndSumOfDebitFromVoucher(@Param("financialYFromDate") Date financialYFromDate,
            @Param("dateBefore") Date dateBefore, @Param("accountHeadId") Long accountHeadId,
            @Param("orgId") long orgId);

    @Query(value = "SELECT \r\n" + "    bm.BANK,\r\n" + "    ba.ba_account_no AS BankAcNo,\r\n"
            + "    cd.CPD_DESC AS Act_type,\r\n" + "    m.OPENBAL_AMT As Opn_Balance,\r\n"
            + "    m.VAMT_DR AS Receipts,\r\n" + "    m.VAMT_CR As Payments , ba.BA_ACCOUNTNAME bankname      \r\n" + "FROM\r\n"
            + "    tb_bank_master bm,\r\n" + "    tb_comparam_det cd,\r\n" + "    tb_bank_account ba,\r\n"
            + "    (SELECT \r\n" + "        x.SAC_HEAD_ID,\r\n" + "            x.VAMT_DR,\r\n"
            + "            x.VAMT_CR,\r\n" + "            x.OPENBAL_AMT,\r\n" + "            bc.BA_ACCOUNTID\r\n"
            + "    FROM\r\n" + "        (SELECT \r\n" + "        SAC_HEAD_ID,\r\n"
            + "            SUM(VAMT_DR) VAMT_DR,\r\n" + "            SUM(VAMT_CR) VAMT_CR,\r\n"
            + "            SUM(OPENBAL_AMT) OPENBAL_AMT\r\n" + "    FROM\r\n" + "        (SELECT \r\n"
            + "        COALESCE(vd.VAMT_DR, 0) VAMT_DR,\r\n" + "            COALESCE(vd.VAMT_CR, 0) VAMT_CR,\r\n"
            + "            vd.SAC_HEAD_ID,\r\n" + "            0 OPENBAL_AMT\r\n" + "    FROM\r\n"
            + "        vw_voucher_detail vd, tb_financialyear f\r\n" + "    WHERE\r\n" + "        vd.ORGID =:orgId\r\n"
            + "            AND vd.VOU_POSTING_DATE BETWEEN :fromDates AND :toDates \r\n"
            + "            AND f.FA_YEARID =:financialYearId UNION SELECT \r\n" + "        0 VAMT_DR,\r\n"
            + "            0 VAMT_CR,\r\n" + "            b.SAC_HEAD_ID,\r\n"
            + "            COALESCE(b.OPENBAL_AMT, 0) OPENBAL_AMT\r\n" + "    FROM\r\n"
            + "        tb_ac_bugopen_balance b, tb_financialyear f\r\n" + "    WHERE\r\n"
            + "        b.FA_YEARID = f.FA_YEARID\r\n" + "            AND f.FA_YEARID =:financialYearId) A\r\n"
            + "    GROUP BY SAC_HEAD_ID) X, tb_bank_account bc, tb_ac_secondaryhead_master se\r\n" + "    WHERE\r\n"
            + "        x.SAC_HEAD_ID = se.SAC_HEAD_ID\r\n" + "            AND bc.BA_ACCOUNTID = se.BA_ACCOUNTID) M\r\n"
            + "WHERE\r\n" + "    bm.BANKID = ba.BANKID\r\n" + "        AND ba.cpd_accounttype = cd.CPd_ID\r\n"
            + "        AND ba.BA_ACCOUNTID = M.BA_ACCOUNTID\r\n" + "        AND ba.orgid =:orgId", nativeQuery = true)
    List<Object[]> queryBankAccountsSummaryReport(@Param("fromDates") Date fromDates, @Param("orgId") long orgId,
            @Param("toDates") Date toDates, @Param("financialYearId") long financialYearId);

    @Query(" SELECT shm.tbBankaccount.baAccountId, shm.acHeadCode FROM AccountHeadSecondaryAccountCodeMasterEntity shm WHERE shm.orgid=:orgId and shm.sacHeadId=:sacHeadId")
    List<Object[]> findBankAccIdByBank(@Param("orgId") Long orgId, @Param("sacHeadId") Long sacHeadId);

    @Query(value = "SELECT cm.RCPT_CHQBOOK_DATE Date_of_receipt," + "                   bm.bank Bank_Name,"
            + "                   bm.Branch Branch_Name," + "                   cm.FROM_CHEQUE_NO Number_of_first_leaf,"
            + "                   cm.TO_CHEQUE_NO Number_of_last_leaf,"
            + "                   cm.ISSUED_DATE Date_of_issue," + "                   cm.ISSUER_EMPID Issued_to_whom,"
            +

            "                   (CASE" + "                     WHEN cm.CHECK_BOOK_RETURN = 'Y' THEN"
            + "                      cm.CHKBOOK_RTN_DATE" + "                   END) Date_of_return,"
            + "                  ( CASE" + "                     WHEN cm.CHECK_BOOK_RETURN = 'Y' THEN"
            + "                      cm.UPDATED_BY" + "                   END) Return_by_whom,"
            + "                   (CASE" + "                     WHEN cm.CHECK_BOOK_RETURN = 'Y' THEN"
            + "                      (cm.FROM_CHEQUE_NO - cm.TO_CHEQUE_NO)" + "                   END) Leave_cancelled,"
            + "                   cm.RETURN_REMARK Remarks_if_any"
            + "              FROM tb_ac_chequebookleaf_mas cm, tb_bank_master bm, tb_bank_account ba"
            + "             WHERE bm.BANKID = ba.BANKID" + "               AND cm.BA_ACCOUNTID = ba.BA_ACCOUNTID"
            + "               AND cm.ORGID =:orgId"
            + "               AND ba.BA_ACCOUNTID =:bankAcntId", nativeQuery = true)
    List<Object[]> chequeBookControlRegisterbyAccount(@Param("orgId") long orgId,
            @Param("bankAcntId") String bankAcntId);

    @Query(value = "SELECT SUBSTR(AC_HEAD_CODE, 1, 20) AC_HEAD_CODE,\r\n" +
            "       SUBSTR(AC_HEAD_CODE, 23, LENGTH(AC_HEAD_CODE)) AC_HEAD_DESC,\r\n" +
            "       SAC_HEAD_ID,\r\n" +
            "       OPENBAL_AMT_DR,\r\n" +
            "       OPENBAL_AMT_CR,\r\n" +
            "       VAMT_DR,\r\n" +
            "       VAMT_CR\r\n" +
            "  FROM (SELECT AC_HEAD_CODE,\r\n" +
            "               SAC_HEAD_ID,\r\n" +
            "               (CASE WHEN CD.CPD_VALUE IN ('DR') THEN OPENBAL_AMT ELSE 0 END)\r\n" +
            "                  AS OPENBAL_AMT_DR,\r\n" +
            "               (CASE WHEN CD.CPD_VALUE IN ('CR') THEN OPENBAL_AMT ELSE 0 END)\r\n" +
            "                  AS OPENBAL_AMT_CR,\r\n" +
            "               VAMT_CR,\r\n" +
            "               VAMT_DR,\r\n" +
            "               PAC_HEAD_ID,\r\n" +
            "               FUNCTION_ID\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       A.PAC_HEAD_ID,\r\n" +
            "                       A.FUNCTION_ID,\r\n" +
            "                       CPD_ID_DRCR,\r\n" +
            "                       OPENBAL_AMT\r\n" +
            "                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                               PAC_HEAD_ID,\r\n" +
            "                               FUNCTION_ID\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID = :orgId\r\n" +
            "                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                 VD.SAC_HEAD_ID,\r\n" +
            "                                 PAC_HEAD_ID,\r\n" +
            "                                 FUNCTION_ID) A\r\n" +
            "                       LEFT JOIN\r\n" +
            "                       (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                               B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                               BG.CPD_ID_DRCR,\r\n" +
            "                               BG.OPENBAL_AMT,\r\n" +
            "                               B.PAC_HEAD_ID\r\n" +
            "                          FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                               TB_COMPARAM_DET CD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                         WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                               AND BG.ORGID = :orgId\r\n" +
            "                               AND BG.FA_YEARID = :financialYearId\r\n" +
            "                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                               AND B.ORGID = BG.ORGID) E\r\n" +
            "                          ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) Z\r\n" +
            "               LEFT JOIN TB_COMPARAM_DET CD ON Z.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "        UNION\r\n" +
            "        SELECT E.AC_HEAD_CODE,\r\n" +
            "               E.SAC_HEAD_ID,\r\n" +
            "               OPENBAL_AMT_DR,\r\n" +
            "               OPENBAL_AMT_CR,\r\n" +
            "               VAMT_CR,\r\n" +
            "               VAMT_DR,\r\n" +
            "               E.PAC_HEAD_ID,\r\n" +
            "               E.FUNCTION_ID\r\n" +
            "          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                       VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" +
            "                  FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                 WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                   AND :toDate\r\n" +
            "                       AND VD.ORGID = :orgId\r\n" +
            "                GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" +
            "               RIGHT JOIN\r\n" +
            "               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                       (CASE\r\n" +
            "                           WHEN CD.CPD_VALUE IN ('DR') THEN BG.OPENBAL_AMT\r\n" +
            "                           ELSE 0\r\n" +
            "                        END)\r\n" +
            "                          AS OPENBAL_AMT_DR,\r\n" +
            "                       (CASE\r\n" +
            "                           WHEN CD.CPD_VALUE IN ('CR') THEN BG.OPENBAL_AMT\r\n" +
            "                           ELSE 0\r\n" +
            "                        END)\r\n" +
            "                          AS OPENBAL_AMT_CR,\r\n" +
            "                       B.AC_HEAD_CODE,\r\n" +
            "                       B.SAC_HEAD_ID,\r\n" +
            "                       B.PAC_HEAD_ID,\r\n" +
            "                       B.FUNCTION_ID\r\n" +
            "                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                       TB_COMPARAM_DET CD,\r\n" +
            "                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                       AND BG.ORGID = :orgId\r\n" +
            "                       AND BG.FA_YEARID = :financialYearId\r\n" +
            "                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                       AND B.ORGID = BG.ORGID) E\r\n" +
            "                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER B,\r\n" +
            "       TB_AC_FUNCTION_MASTER C\r\n" +
            " WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID AND A.FUNCTION_ID = C.FUNCTION_ID\r\n" +
            "ORDER BY B.PAC_HEAD_COMPO_CODE, C.FUNCTION_COMPCODE ASC", nativeQuery = true)
    List<Object[]> findBalanceSheetReport(@Param("orgId") long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId);

    /*
     * @Query(value="SELECT AC_HEAD_CODE,\r\n" + "       SAC_HEAD_ID,\r\n" + "       OPENBAL_AMT_DR,\r\n" +
     * "       OPENBAL_AMT_CR,\r\n" + "       VAMT_CR,\r\n" + "       VAMT_DR\r\n" + "  FROM (SELECT AC_HEAD_CODE,\r\n" +
     * "               SAC_HEAD_ID,\r\n" + "               (CASE WHEN CD.CPD_VALUE IN ('DR') THEN OPENBAL_AMT ELSE 0 END)\r\n" +
     * "                  AS OPENBAL_AMT_DR,\r\n" +
     * "               (CASE WHEN CD.CPD_VALUE IN ('CR') THEN OPENBAL_AMT ELSE 0 END)\r\n" +
     * "                  AS OPENBAL_AMT_CR,\r\n" + "               VAMT_CR,\r\n" + "               VAMT_DR,\r\n" +
     * "               PAC_HEAD_ID,\r\n" + "               FUNCTION_ID\r\n" + "          FROM (SELECT AC_HEAD_CODE,\r\n" +
     * "                       VAMT_CR,\r\n" + "                       VAMT_DR,\r\n" + "                       SAC_HEAD_ID,\r\n" +
     * "                       A.PAC_HEAD_ID,\r\n" + "                       A.FUNCTION_ID,\r\n" +
     * "                       CPD_ID_DRCR,\r\n" + "                       OPENBAL_AMT\r\n" +
     * "                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
     * "                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
     * "                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
     * "                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + "                               PAC_HEAD_ID,\r\n" +
     * "                               FUNCTION_ID\r\n" + "                          FROM VW_VOUCHER_DETAIL VD\r\n" +
     * "                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
     * "                                                           AND :toDate\r\n" +
     * "                               AND VD.ORGID = :orgId\r\n" + "                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
     * "                                 VD.SAC_HEAD_ID,\r\n" + "                                 PAC_HEAD_ID,\r\n" +
     * "                                 FUNCTION_ID) A\r\n" + "                       LEFT JOIN\r\n" +
     * "                       (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
     * "                               B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" + "                               BG.CPD_ID_DRCR,\r\n" +
     * "                               BG.OPENBAL_AMT,\r\n" + "                               B.PAC_HEAD_ID\r\n" +
     * "                          FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" + "                               TB_COMPARAM_DET CD,\r\n" +
     * "                               TB_AC_SECONDARYHEAD_MASTER B\r\n" +
     * "                         WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
     * "                               AND BG.ORGID = :orgId\r\n" +
     * "                               AND BG.FA_YEARID = :financialYearId\r\n" +
     * "                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
     * "                               AND B.ORGID = BG.ORGID) E\r\n" +
     * "                          ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) Z\r\n" +
     * "               LEFT JOIN TB_COMPARAM_DET CD ON Z.CPD_ID_DRCR = CD.CPD_ID\r\n" + "        UNION\r\n" +
     * "        SELECT E.AC_HEAD_CODE,\r\n" + "               E.SAC_HEAD_ID,\r\n" + "               OPENBAL_AMT_DR,\r\n" +
     * "               OPENBAL_AMT_CR,\r\n" + "               VAMT_CR,\r\n" + "               VAMT_DR,\r\n" +
     * "               E.PAC_HEAD_ID,\r\n" + "               E.FUNCTION_ID\r\n" +
     * "          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + "                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
     * "                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" + "                       VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" +
     * "                  FROM VW_VOUCHER_DETAIL VD\r\n" +
     * "                 WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate AND :toDate\r\n" +
     * "                       AND VD.ORGID = :orgId\r\n" + "                GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" +
     * "               RIGHT JOIN\r\n" + "               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
     * "                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" + "                       (CASE\r\n" +
     * "                           WHEN CD.CPD_VALUE IN ('DR') THEN BG.OPENBAL_AMT\r\n" + "                           ELSE 0\r\n"
     * + "                        END)\r\n" + "                          AS OPENBAL_AMT_DR,\r\n" +
     * "                       (CASE\r\n" + "                           WHEN CD.CPD_VALUE IN ('CR') THEN BG.OPENBAL_AMT\r\n" +
     * "                           ELSE 0\r\n" + "                        END)\r\n" +
     * "                          AS OPENBAL_AMT_CR,\r\n" + "                       B.AC_HEAD_CODE,\r\n" +
     * "                       B.SAC_HEAD_ID,\r\n" + "                       B.PAC_HEAD_ID,\r\n" +
     * "                       B.FUNCTION_ID\r\n" + "                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
     * "                       TB_COMPARAM_DET CD,\r\n" + "                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
     * "                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" + "                       AND BG.ORGID = :orgId\r\n" +
     * "                       AND BG.FA_YEARID = :financialYearId\r\n" +
     * "                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" + "                       AND B.ORGID = BG.ORGID) E\r\n" +
     * "                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" + "       TB_AC_PRIMARYHEAD_MASTER B,\r\n" +
     * "       TB_AC_FUNCTION_MASTER C\r\n" + " WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID AND A.FUNCTION_ID = C.FUNCTION_ID\r\n" +
     * "ORDER BY B.PAC_HEAD_COMPO_CODE, C.FUNCTION_COMPCODE ASC", nativeQuery = true) List<Object[]>
     * findBalanceSheetReport(@Param("orgId") long orgId, @Param("fromDate") Date fromDate,
     * @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId);
     */

    @Query(value = "SELECT \r\n" + "    sm.SAC_HEAD_ID, sm.AC_HEAD_CODE\r\n" + "FROM\r\n"
            + "    tb_ac_secondaryhead_master sm,\r\n" + "    tb_ac_primaryhead_master pm\r\n" + "WHERE\r\n"
            + "    pm.PAC_HEAD_ID = sm.PAC_HEAD_ID\r\n" + "        AND sm.ORGID =:orgId\r\n"
            + "        AND pm.CPD_ID_ACHEADTYPES IN (SELECT \r\n" + "            d.CPD_ID\r\n" + "        FROM\r\n"
            + "            tb_comparam_det d\r\n" + "        WHERE\r\n" + "            d.CPD_VALUE = 'L'\r\n"
            + "                AND d.CPM_ID IN (SELECT \r\n" + "                    m.CPM_ID\r\n"
            + "                FROM\r\n" + "                    tb_comparam_mas m\r\n" + "                WHERE\r\n"
            + "                    m.CPM_PREFIX = 'COA'))", nativeQuery = true)
    List<Object[]> findliabilityHead(@Param("orgId") long orgId);

    @Query(value = "   SELECT \r\n" + "    sm.SAC_HEAD_ID, sm.AC_HEAD_CODE\r\n" + "FROM\r\n"
            + "    tb_ac_secondaryhead_master sm,\r\n" + "    tb_ac_primaryhead_master pm\r\n" + "WHERE\r\n"
            + "    pm.PAC_HEAD_ID = sm.PAC_HEAD_ID\r\n" + "        AND sm.ORGID =:orgId\r\n"
            + "        AND pm.CPD_ID_ACHEADTYPES IN (SELECT \r\n" + "            d.CPD_ID\r\n" + "        FROM\r\n"
            + "            tb_comparam_det d\r\n" + "        WHERE\r\n" + "            d.CPD_VALUE = 'A'\r\n"
            + "                AND d.CPM_ID IN (SELECT \r\n" + "                    m.CPM_ID\r\n"
            + "                FROM\r\n" + "                    tb_comparam_mas m\r\n" + "                WHERE\r\n"
            + "                    m.CPM_PREFIX = 'COA'))", nativeQuery = true)
    List<Object[]> findAssetHead(@Param("orgId") long orgId);

    @Query(value = "select rm.RM_RCPTNO Transation_No,rm.RM_DATE Transaction_Date,rm.RM_AMOUNT Amount,rm.RM_NARRATION Narration,rm.RM_RECEIVEDFROM Receiver_or_PayerName,\r\n"
            + "rm.UPDATED_BY Reversed_By,rm.RECEIPT_DEL_DATE Reversed_Date,rm.RECEIPT_DEL_REMARK Reversal_Reason,rm.RECEIPT_DEL_AUTH_BY Authorized_By,rm.DP_DEPTID \r\n"
            + " from tb_receipt_mas rm\r\n" + "where rm.RECEIPT_DEL_FLAG='Y' \r\n" + "and rm.ORGID =:orgId\r\n"
            + "and rm.RM_DATE between :transactionFromDate and :transactionToDate", nativeQuery = true)
    List<Object[]> transactionReversalReportReceiptByDate(@Param("orgId") long orgId,
            @Param("transactionFromDate") Date transactionFromDate, @Param("transactionToDate") Date transactionToDate);

    @Query(value = "select bm.BM_BILLNO Transation_No,bm.BM_ENTRYDATE Transaction_Date,bm.BM_INVOICEVALUE Amount,bm.BM_NARRATION Narration,\r\n"
            + "bm.VM_VENDORNAME Receiver_or_PayerName,bm.UPDATED_BY Reversed_By,bm.BM_DEL_DATE Reversed_Date,bm.BM_DEL_REMARK Reversal_Reason,bm.BM_DEL_AUTH_BY Authorized_By\r\n"
            + "from tb_ac_bill_mas bm\r\n" + "where bm.BM_DEL_FLAG ='Y' \r\n" + "and bm.ORGID =:orgId\r\n"
            + "and bm.BM_ENTRYDATE between :transactionFromDate and :transactionToDate", nativeQuery = true)
    List<Object[]> transactionReversalReportBillByDate(@Param("orgId") long orgId,
            @Param("transactionFromDate") Date transactionFromDate, @Param("transactionToDate") Date transactionToDate);

    @Query(value = "SELECT \r\n" + "    d.DPS_SLIPNO Transation_No,\r\n" + "    d.DPS_SLIPDATE Transaction_Date,\r\n"
            + "    d.DPS_AMOUNT Amount,\r\n" + "    d.DPS_REMARK Narration,\r\n" + "    d.DPS_REMARK,\r\n"
            + "    d.UPDATED_BY Reversed_By,\r\n" + "    d.DPS_DEL_DATE Reversed_Date,\r\n"
            + "    d.DPS_DEL_REMARK Reversal_Reason,\r\n" + "    d.DPS_DEL_AUTH_BY Authorized_By\r\n" + "FROM\r\n"
            + "    tb_ac_bank_depositslip_master d\r\n" + "WHERE\r\n" + "    d.DPS_DEL_FLAG = 'Y' \r\n"
            + "    AND d.ORGID =:orgId\r\n"
            + "        AND d.DPS_SLIPDATE BETWEEN :transactionFromDate AND :transactionToDate", nativeQuery = true)
    List<Object[]> transactionReversalReportDepositeSlipEntryByDate(@Param("orgId") long orgId,
            @Param("transactionFromDate") Date transactionFromDate, @Param("transactionToDate") Date transactionToDate);

/*    @Query(value = "SELECT \r\n"
            + "    bmg.budget_code, pr.ORGINAL_ESTAMT, pr.REVISED_ESTAMT, sum( mds.Collected_Balance )Collected_Balance\r\n"
            + "FROM\r\n" + "    tb_ac_budgetcode_mas bmg,\r\n" + "    tb_financialyear fy,\r\n"
            + "    tb_ac_projectedrevenue pr\r\n" + "       LEFT OUTER JOIN\r\n" + "    (SELECT \r\n"
            + "        bm.budgetcode_id, bm.budget_code, md.Collected_Balance\r\n" + "    FROM\r\n"
            + "        tb_ac_budgetcode_mas bm, tb_ac_primaryhead_master pm, Tb_Ac_Function_Master fm, Tb_Ac_Secondaryhead_Master sm, \r\n"
            + "        (SELECT \r\n" + "        rd.SAC_HEAD_ID,\r\n" + "            rm.orgid,\r\n"
            + "            SUM(rd.RF_FEEAMOUNT) Collected_Balance\r\n" + "    FROM\r\n"
            + "        tb_receipt_mas rm, tb_receipt_det rd\r\n" + "    WHERE\r\n"
            + "        rm.RM_RCPTID = rd.RM_RCPTID\r\n" + "            AND rm.RECEIPT_TYPE_FLAG in ('M','R','A','P')"
            + "            AND rm.orgid = rd.orgid\r\n" + "            AND rm.ORGID =:orgId\r\n"
            + "            AND rm.RM_DATE BETWEEN :fromDates AND :todates \r\n"
            + "            AND rm.RECEIPT_DEL_FLAG IS NULL\r\n" + "    GROUP BY rd.SAC_HEAD_ID , rm.orgid) md\r\n"
            + "    WHERE\r\n" + "        bm.function_id = fm.function_id\r\n"
            + "            AND bm.pac_head_id = pm.pac_head_id\r\n"
            + "            AND pm.pac_head_id = sm.pac_head_id\r\n"
            + "            AND fm.function_id = sm.function_id\r\n"
            + "            AND sm.sac_head_id = md.sac_head_id) mds ON pr.BUDGETCODE_ID = mds.BUDGETCODE_ID \r\n"
            + "WHERE\r\n" + "    bmg.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" + "    and bmg.orgid=pr.ORGID\r\n"
            + "    AND pr.FA_YEARID = fy.FA_YEARID\r\n" + "        AND pr.FA_YEARID =:financialId\r\n"
            + "        AND pr.orgid =:orgId group by    bmg.budget_code, pr.ORGINAL_ESTAMT, pr.REVISED_ESTAMT", nativeQuery = true)*/
     /*commented above query against Id #115975 */
    @Query(value = "SELECT\r\n" + 
    		"bmg.budget_code, pr.ORGINAL_ESTAMT, pr.REVISED_ESTAMT , sum( mds.Collected_Balance )Collected_Balance\r\n" + 
    		"FROM\r\n" + 
    		"tb_ac_budgetcode_mas bmg,\r\n" + 
    		"tb_financialyear fy,\r\n" + 
    		"tb_ac_projectedrevenue pr\r\n" + 
    		"LEFT OUTER JOIN\r\n" + 
    		"(SELECT\r\n" + 
    		"bm.budgetcode_id, bm.budget_code, md.Collected_Balance,md.FIELD_ID\r\n" + 
    		"FROM\r\n" + 
    		" tb_ac_budgetcode_mas bm, Tb_Ac_Function_Master fm, Tb_Ac_Secondaryhead_Master sm,\r\n" + 
    		"(SELECT\r\n" + 
    		"rd.SAC_HEAD_ID,\r\n" + 
    		"rm.orgid,rm.FIELD_ID,\r\n" + 
    		"SUM(rd.RF_FEEAMOUNT) Collected_Balance\r\n" + 
    		"FROM\r\n" + 
    		"tb_receipt_mas rm,  tb_receipt_det rd\r\n" + 
    		"WHERE\r\n" + 
    		"rm.RM_RCPTID = rd.RM_RCPTID\r\n" + 
    		"AND rm.RECEIPT_TYPE_FLAG in ('M','R','A','P') AND rm.orgid = rd.orgid\r\n" + 
    		"AND rm.ORGID =:orgId\r\n" + 
    		"AND rm.DP_DEPTID=:deptId  \r\n" + 
    		"  AND rm.RM_DATE BETWEEN :fromDates AND :todates\r\n" + 
    		"AND rm.RECEIPT_DEL_FLAG IS NULL\r\n" + 
    		"GROUP BY rd.SAC_HEAD_ID , rm.orgid,rm.FIELD_ID) md\r\n" + 
    		"WHERE\r\n" + 
    		"bm.function_id = fm.function_id\r\n" + 
    		"AND bm.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n" + 
    		"-- AND pm.pac_head_id = sm.pac_head_id\r\n" + 
    		"AND fm.function_id = sm.function_id\r\n" + 
    		"AND sm.sac_head_id = md.sac_head_id) mds ON pr.BUDGETCODE_ID = mds.BUDGETCODE_ID and pr.FIELD_ID=mds.FIELD_ID\r\n" + 
    		"WHERE\r\n" + 
    		"bmg.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" + 
    		" and bmg.orgid=pr.ORGID\r\n" + 
    		"AND pr.FA_YEARID = fy.FA_YEARID\r\n" + 
    		"AND pr.FA_YEARID =:financialId\r\n" + 
    		"and pr.DP_DEPTID =:deptId\r\n" + 
    		"and bmg.FUNCTION_ID =:functionId\r\n" + 
    		"AND pr.orgid =:orgId group by bmg.budget_code, pr.ORGINAL_ESTAMT, pr.REVISED_ESTAMT", nativeQuery = true)
    List<Object[]> ReceiptsBudgetStatusReport(@Param("fromDates") Date fromDates, @Param("todates") Date todates,
            @Param("financialId") Long financialId, @Param("orgId") long orgId,@Param("deptId") long deptId,@Param("functionId") long functionId);

    @Query(value = "SELECT \r\n"
            + "    bmg.budget_code, pe.ORGINAL_ESTAMT, pe.REVISED_ESTAMT, sum(a.Expenses_Balance) Expenses_Balance,pe.DP_DEPTID \r\n"
            + " FROM\r\n" + "    tb_ac_budgetcode_mas bmg,\r\n" + "    tb_financialyear fy,\r\n"
            + "    tb_ac_projected_expenditure pe\r\n" + "        LEFT OUTER JOIN\r\n" + "    (SELECT \r\n"
            + "        bm.budgetcode_id, bm.budget_code, md.Expenses_Balance\r\n" + "    FROM\r\n"
            + "        tb_ac_budgetcode_mas bm, tb_ac_primaryhead_master pm, Tb_Ac_Function_Master fm, Tb_Ac_Secondaryhead_Master sm, (SELECT \r\n"
            + "        pd.BUDGETCODE_ID,\r\n" + "            pm.orgid,\r\n"
            + "            SUM(pd.PAYMENT_AMT) Expenses_Balance\r\n" + "    FROM\r\n"
            + "        tb_ac_payment_mas pm, tb_ac_payment_det pd\r\n" + "    WHERE\r\n"
            + "                        pm.PAYMENT_ID = pd.PAYMENT_ID\r\n"
            + "                        AND pm.orgid = pd.orgid\r\n" + "            AND pm.ORGID =:orgId \r\n"
            + "            AND pm.PAYMENT_DEL_FLAG IS NULL\r\n"
            + "            AND pm.PAYMENT_DATE BETWEEN :fromDates AND :todates\r\n"
            + "    GROUP BY pd.BUDGETCODE_ID , pm.orgid) md\r\n" + "    WHERE\r\n"
            + "        bm.function_id = fm.function_id\r\n" + "            AND bm.pac_head_id = pm.pac_head_id\r\n"
            + "            AND pm.pac_head_id = sm.pac_head_id\r\n"
            + "            AND fm.function_id = sm.function_id\r\n"
            + "            AND sm.sac_head_id = md.BUDGETCODE_ID) a ON pe.BUDGETCODE_ID = a.BUDGETCODE_ID\r\n"
            + "WHERE\r\n" + "    bmg.BUDGETCODE_ID = pe.BUDGETCODE_ID\r\n"
            + "        AND pe.FA_YEARID = fy.FA_YEARID\r\n" + "        AND pe.orgid =:orgId\r\n"
            + "        AND pe.FA_YEARID = :financialId group by bmg.budget_code, pe.ORGINAL_ESTAMT, pe.REVISED_ESTAMT,pe.DP_DEPTID", nativeQuery = true)
    List<Object[]> expenditureBudgetStatusReport(@Param("fromDates") Date fromDates, @Param("todates") Date todates,
            @Param("financialId") Long financialId, @Param("orgId") long orgId);
    
    @Query(value = "SELECT\r\n" + 
    		"    bmg.budget_code, pe.ORGINAL_ESTAMT , pe.REVISED_ESTAMT REVISED_ESTAMT,\r\n" + 
    		"    sum(a.Expenses_Balance) Expenses_Balance,pe.DP_DEPTID,bmg.BUDGETCODE_ID,pe.FIELD_ID\r\n" + 
    		" FROM\r\n" + 
    		"    tb_ac_budgetcode_mas bmg,\r\n" + 
    		"    tb_financialyear fy,\r\n" + 
    		"    tb_ac_projected_expenditure pe\r\n" + 
    		"        LEFT OUTER JOIN\r\n" + 
    		"    (SELECT\r\n" + 
    		"        bm.budgetcode_id,pm.FIELD_ID, bm.budget_code, md.Expenses_Balance,  md.DP_DEPTID\r\n" + 
    		"    FROM\r\n" + 
    		"        tb_ac_budgetcode_mas bm, tb_ac_projected_expenditure pm,\r\n" + 
    		"        Tb_Ac_Function_Master fm, Tb_Ac_Secondaryhead_Master sm,\r\n" + 
    		"     (SELECT pd.BUDGETCODE_ID,pm.FIELDID,pm.orgid,\r\n" + 
    		"            SUM(pd.PAYMENT_AMT) Expenses_Balance,b.DP_DEPTID\r\n" + 
    		"    FROM tb_ac_payment_mas pm, tb_ac_payment_det pd , tb_ac_bill_mas b\r\n" + 
    		"    WHERE pm.PAYMENT_ID = pd.PAYMENT_ID\r\n" + 
    		"          AND pm.orgid = pd.orgid\r\n" + 
    		"          AND pm.ORGID =:orgId\r\n" + 
    		"          AND pm.PAYMENT_DEL_FLAG IS NULL\r\n" + 
    		"          AND pm.PAYMENT_DATE BETWEEN :fromDates AND :todates\r\n" + 
    		"          AND PD.ORGID = b.ORGID\r\n" + 
    		"          and pd.bm_id = b.bm_id\r\n" + 
    		"          and pm.FIELDID=b.FIELD_ID\r\n" + 
    		"          and b.BM_DEL_FLAG  is null\r\n" + 
    		"          GROUP BY pd.BUDGETCODE_ID , pm.orgid,b.DP_DEPTID,pm.FIELDID) md\r\n" + 
    		"    WHERE\r\n" + 
    		"        bm.function_id = fm.function_id\r\n" + 
    		"            AND bm.BUDGETCODE_ID = pm.BUDGETCODE_ID\r\n" + 
    		"            AND fm.function_id = sm.function_id\r\n" + 
    		"            AND BM.SAC_HEAD_ID=sm.SAC_HEAD_ID\r\n" + 
    		"            AND pm.FIELD_ID = md.FIELDID\r\n" + 
    		"            AND sm.SAC_HEAD_ID = md.BUDGETCODE_ID) a ON pe.BUDGETCODE_ID = a.BUDGETCODE_ID\r\n" + 
    		"            AND pe.FIELD_ID = a.FIELD_ID\r\n" + 
    		" WHERE\r\n" + 
    		"    bmg.BUDGETCODE_ID = pe.BUDGETCODE_ID\r\n" + 
    		"        AND pe.FA_YEARID = fy.FA_YEARID\r\n" + 
    		"        AND pe.orgid =:orgId\r\n" + 
    		"        AND pe.FA_YEARID =:financialId\r\n" + 
    		"        group by bmg.budget_code, pe.ORGINAL_ESTAMT, pe.REVISED_ESTAMT,pe.DP_DEPTID,bmg.BUDGETCODE_ID,pe.FIELD_ID", nativeQuery = true)
    List<Object[]> expenditureBudgetStatusReportBasedOnDeptId(@Param("fromDates") Date fromDates, @Param("todates") Date todates,
            @Param("financialId") Long financialId, @Param("orgId") long orgId);
    
    //RM.RECEIPT_DEL_FLAG IS NULL commented against #120771
    @Query(value = "SELECT RM.RM_RCPTID,\r\n" + "       RM.ORGID,\r\n" + "       RM.RM_RCPTNO RECEIPT_NO,\r\n"
            + "       RM.RM_DATE RECEIPT_DATE,\r\n" + "       RM.RM_RECEIVEDFROM,\r\n"
            + "       RMO.RD_CHEQUEDDNO CHEQUE_NO,\r\n" + "       RMO.RD_CHEQUEDDDATE,\r\n"
            + "       RMO.RD_CHEQUE_STATUS,\r\n" + "       RMO.RD_AMOUNT,\r\n" + "       BM.BANK,\r\n"
            + "       (SELECT CPD_DESC\r\n" + "          FROM TB_COMPARAM_DET A, TB_COMPARAM_MAS B\r\n"
            + "         WHERE B.CPM_ID = A.CPM_ID\r\n" + "           AND CPM_PREFIX = 'CLR'\r\n"
            + "           AND CPD_ID = RMO.RD_CHEQUE_STATUS),RM.DP_DEPTID\r\n"
            + "  FROM TB_RECEIPT_MAS RM, TB_RECEIPT_MODE RMO, TB_BANK_MASTER BM\r\n"
            + " WHERE RM.RM_RCPTID = RMO.RM_RCPTID\r\n" + "   AND RMO.BANKID = BM.BANKID\r\n"
            /*+ "   AND RM.RECEIPT_DEL_FLAG IS NULL\r\n"*/ + "   AND RM.ORGID = RMO.ORGID\r\n"
            + "   AND RM.ORGID =:orgId\r\n" + "   AND RMO.RD_CHEQUE_STATUS =:categoryId\r\n"
            + "   AND RM.RM_DATE BETWEEN :fromDates AND :todates\r\n", nativeQuery = true)
    List<Object[]> chequeReceivedStatusReport(@Param("fromDates") Date fromDates, @Param("todates") Date todates,
            @Param("orgId") long orgId, @Param("categoryId") String categoryId);

     //commented against id #132705 updated query shared by jitendra pal
   /* @Query(value = "SELECT   \r\n" + "    cd.CHEQUE_NO,\r\n" + "    cd.ISSUANCE_DATE,\r\n"
            + "    pm.PAYMENT_AMOUNT,\r\n" + "    cd.CANCELLATION_DATE,\r\n" + "    cd.CANCELLATION_REASON,\r\n"
            + "    cf.CHEQUE_NO NEW_CHEQUE_NO,\r\n" + "    pm.PAYMENT_NO,\r\n" + "    pm.NARRATION,\r\n"
            + "    ba.BA_ACCOUNTNAME,\r\n" + "    ba.BA_ACCOUNT_NO\r\n" + "    \r\n" + "FROM\r\n"
            + "    tb_ac_chequebookleaf_det cd,\r\n" + "    tb_ac_payment_mas pm,\r\n" + "    tb_bank_account ba,\r\n"
            + "    (SELECT \r\n" + "        cd.CHEQUE_NO, cd.CHEQUEBOOK_DETID, cd.orgid\r\n" + "    FROM\r\n"
            + "        tb_ac_chequebookleaf_det cd) cf\r\n" + "WHERE\r\n" + "    pm.PAYMENT_ID = cd.PAYMENT_ID\r\n"
            + "        AND cf.CHEQUEBOOK_DETID = cd.NEW_ISSUE_CHEQUEBOOK_DETID\r\n"
            + "        AND pm.BA_ACCOUNTID=ba.BA_ACCOUNTID\r\n" + "        AND cf.ORGID = cd.orgid\r\n"
            + "        AND pm.ORGID = cd.orgid\r\n" + "        AND cd.CPD_IDSTATUS = (SELECT \r\n"
            + "            a.CPD_ID\r\n" + "        FROM\r\n" + "            tb_comparam_det a,\r\n"
            + "            tb_comparam_mas b\r\n" + "        WHERE\r\n" + "                     b.cpm_id = a.cpm_id\r\n"
            + "                AND b.cpm_prefix = 'CLR'\r\n" + "                AND a.CPD_VALUE = 'CND')\r\n"
            + "        AND pm.orgid =:orgId\r\n"
            + "        AND cd.CANCELLATION_DATE BETWEEN :fromDates AND :todates ", nativeQuery = true)*/
    @Query(value = "SELECT  \r\n" + 
    		"    cd.CHEQUE_NO,\r\n" + 
    		"    cd.ISSUANCE_DATE,\r\n" + 
    		"    pm.PAYMENT_AMOUNT,\r\n" + 
    		"    cd.CANCELLATION_DATE,\r\n" + 
    		"    cd.CANCELLATION_REASON,\r\n" + 
    		"    cf.CHEQUE_NO NEW_CHEQUE_NO,\r\n" + 
    		"    pm.PAYMENT_NO,\r\n" + 
    		"    pm.NARRATION,\r\n" + 
    		"    ba.BA_ACCOUNTNAME,\r\n" + 
    		"    ba.BA_ACCOUNT_NO\r\n" + 
    		"   \r\n" + 
    		"  FROM\r\n" + 
    		"    tb_ac_chequebookleaf_det cd,\r\n" + 
    		"    tb_ac_payment_mas pm, tb_bank_account ba,\r\n" + 
    		"    tb_comparam_det a, tb_comparam_mas b,\r\n" + 
    		"    (SELECT cd.CHEQUE_NO, cd.CHEQUEBOOK_DETID, cd.orgid\r\n" + 
    		"    FROM  tb_ac_chequebookleaf_det cd) cf\r\n" + 
    		"  WHERE  pm.PAYMENT_ID = cd.PAYMENT_ID\r\n" + 
    		"        AND cf.CHEQUEBOOK_DETID = cd.NEW_ISSUE_CHEQUEBOOK_DETID\r\n" + 
    		"        AND pm.BA_ACCOUNTID=ba.BA_ACCOUNTID\r\n" + 
    		"        AND cf.ORGID = cd.orgid\r\n" + 
    		"        AND pm.ORGID = cd.orgid\r\n" + 
    		"        AND cd.CPD_IDSTATUS = a.CPD_ID\r\n" + 
    		"        AND b.cpm_id = a.cpm_id\r\n" + 
    		"        AND b.cpm_prefix = 'CLR'  AND a.CPD_VALUE = 'CND'\r\n" + 
    		"        AND pm.orgid =:orgId\r\n" + 
    		"        AND cd.CANCELLATION_DATE BETWEEN :fromDates AND :todates ", nativeQuery = true)
    List<Object[]> chequeCancellationReportByDate(@Param("fromDates") Date fromDates, @Param("todates") Date todates,
            @Param("orgId") long orgId);

    /*
     * @Query(value = "select YEAR(a.FA_FROMDATE) + 1, YEAR(a.FA_FROMDATE) + 2 \r\n" +
     * "  from tb_financialyear a where a.FA_YEARID =:financialId ", nativeQuery = true)
     */
    @Query(value = "select to_char(a.FA_FROMDATE,'YYYY') + 1, to_char(a.FA_FROMDATE,'YYYY') + 2 \r\n"
            + "from tb_financialyear a where a.FA_YEARID =:financialId", nativeQuery = true)
    List<Object[]> getAllFinincialFromNextDate(@Param("financialId") long financialId);

    @Query(value = "SELECT a.BUDGETCODE_ID,a.BUDGET_CODE,\r\n" + "    b.ORGINAL_ESTAMT,\r\n"
            + "    b.REVISED_ESTAMT,\r\n" + "    d.ESTIMATE_FOR_NEXTYEAR\r\n" + "FROM\r\n"
            + "    tb_ac_budgetcode_mas a,\r\n" + "    tb_ac_projected_expenditure b,\r\n"
            + "    tb_financialyear c,\r\n" + "    tb_ac_budgetory_estimate d\r\n" + "WHERE\r\n"
            + "    a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + "        AND b.FA_YEARID = c.FA_YEARID\r\n"
            + "        AND d.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + "        and b.ORGID =:orgId\r\n"
            + "        and c.FA_FROMDATE =:fromDat\r\n"
            + "       order by a.BUDGETCODE_ID,a.BUDGET_CODE", nativeQuery = true)
    List<Object[]> getAllExpenditureEnstimationCurrentData(@Param("fromDat") Date fromDat, @Param("orgId") long orgId);

    @Query(value = "SELECT\r\n" + "    sum(b.ORGINAL_ESTAMT)/ COUNT(*),\r\n" + "    sum(b.REVISED_ESTAMT)\r\n"
            + "   \r\n" + "FROM\r\n" + "    tb_ac_budgetcode_mas a,\r\n" + "    tb_ac_projected_expenditure b,\r\n"
            + "    tb_financialyear c,\r\n" + "    tb_ac_budgetory_estimate d\r\n" + "WHERE\r\n"
            + "    a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + "        AND b.FA_YEARID = c.FA_YEARID\r\n"
            + "        AND d.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + "        and b.ORGID =:orgId\r\n"
            + "        AND b.BUDGETCODE_ID =:budgetCodeId\r\n"
            + "        and c.FA_FROMDATE between :lastfromDates and :lastToDates", nativeQuery = true)
    List<Object[]> getAllExpenditureEnstimationLastData(@Param("budgetCodeId") long budgetCodeId,
            @Param("orgId") long orgId, @Param("lastfromDates") Date lastfromDates,
            @Param("lastToDates") Date lastToDates);

    @Query(value = "SELECT\r\n" + "    b.ORGINAL_ESTAMT\r\n" + "FROM\r\n" + "    tb_ac_budgetcode_mas a,\r\n"
            + "    tb_ac_projected_expenditure b,\r\n" + "    tb_financialyear c,\r\n"
            + "    tb_ac_budgetory_estimate d\r\n" + "WHERE\r\n" + "        a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "        AND b.FA_YEARID = c.FA_YEARID\r\n" + "        AND d.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n"
            + "        and a.BUDGETCODE_ID =:budgetCodeId\r\n" + "        and b.ORGID =:orgId\r\n"
            + "        and c.FA_FROMDATE =:fromlastoneyr", nativeQuery = true)
    BigDecimal getAllExpenditureLastOneYrData(@Param("budgetCodeId") long budgetCodeId, @Param("orgId") long orgId,
            @Param("fromlastoneyr") Date fromlastoneyr);

    @Query(value = "select pm.PAYMENT_NO          Transation_No,\r\n"
            + "       pm.PAYMENT_DATE        Transaction_Date,\r\n" + "       pm.PAYMENT_AMOUNT      Amount,\r\n"
            + "       pm.NARRATION           Narration,\r\n" + "       vm.VM_VENDORNAME       VendorName,\r\n"
            + "       pm.UPDATED_BY          Reversed_By,\r\n" + "       pm.PAYMENT_DEL_DATE    Reversed_Date,\r\n"
            + "       pm.PAYMENT_DEL_REMARK  Reversal_Reason,\r\n" + "       pm.PAYMENT_DEL_AUTH_BY Authorized_By\r\n"
            + "  from tb_ac_payment_mas pm left join\r\n" + "       tb_vendormaster vm\r\n"
            + "       ON pm.VM_VENDORID=vm.VM_VENDORID\r\n" + " where pm.PAYMENT_DEL_FLAG = 'Y'\r\n"
            + "   and pm.ORGID = :orgId\r\n"
            + "   and pm.PAYMENT_DATE between :transactionFromDate and :transactionToDate ORDER BY pm.PAYMENT_DEL_DATE ASC", nativeQuery = true)
    List<Object[]> transactionReversalReportDirectPaymentByDate(@Param("orgId") long orgId,
            @Param("transactionFromDate") Date transactionFromDate, @Param("transactionToDate") Date transactionToDate);

    @Query(value = "SELECT DISTINCT C.BUDGETCODE_ID,\r\n" + "       C.BUDGET_CODE,\r\n" + "       G.AVG,\r\n"
            + "       C.ACTUAL_OF_LAST_YEAR,\r\n" + "       G.REVISED_ESTAMT,\r\n" + "       G.ORGINAL_ESTAMT,\r\n"
            + "       C.ESTIMATE_FOR_NEXTYEAR,\r\n" + "       C.CREATED_BY,\r\n"+ "    group_concat(distinct C.REMARK_k) remark\r\n" + "  FROM (SELECT B.BUDGETCODE_ID,\r\n"
            + "               A.AVG,\r\n" + "               B.REVISED_ESTAMT,\r\n"
            + "               B.ORGINAL_ESTAMT,\r\n" + "               B.CREATED_BY \r\n"
            + "          FROM (SELECT INA.BUDGETCODE_ID, (SUM(INA.ORGINAL_ESTAMT) / COUNT(*)) AS AVG\r\n"
            + "                  FROM (SELECT *\r\n" + "                          FROM TB_AC_PROJECTEDREVENUE\r\n"
            + "                         WHERE ORGID = :orgId\r\n" + "                           AND FA_YEARID IN\r\n"
            + "                               (SELECT FA_YEARID\r\n"
            + "                                  FROM TB_FINANCIALYEAR\r\n"
            + "                                 WHERE FA_FROMDATE BETWEEN :lastYrFromDate AND\r\n"
            + "                                       :toDate))\r\n" + "                 INA GROUP BY INA.BUDGETCODE_ID) A\r\n"
            + "         RIGHT OUTER JOIN (SELECT B.BUDGETCODE_ID,\r\n"
            + "                                 B.ORGINAL_ESTAMT,\r\n"
            + "                                 B.REVISED_ESTAMT,\r\n"
            + "                                 USER_ID AS CREATED_BY\r\n"
            + "                            FROM TB_AC_BUDGETCODE_MAS     A,\r\n"
            + "                                 TB_AC_PROJECTEDREVENUE   B,\r\n"
            + "                                 TB_FINANCIALYEAR         C\r\n"
            + "                           WHERE B.ORGID = :orgId\r\n"
            + "                             AND B.FA_YEARID = C.FA_YEARID\r\n"
            + "                             AND A.BUDGETCODE_ID = B.BUDGETCODE_ID\r\n"
            + "                             AND A.BUDGETCODE_ID IN\r\n"
            + "                                 (SELECT DISTINCT BUDGETCODE_ID AS BUDGETCODE_ID\r\n"
            + "                                    FROM TB_AC_PROJECTEDREVENUE)\r\n"
            + "                             AND B.FA_YEARID = :financialId) B \r\n"
            + "            ON A.BUDGETCODE_ID = B.BUDGETCODE_ID\r\n" + "        ) G\r\n"
            + " RIGHT OUTER JOIN (SELECT Z.BUDGETCODE_ID,\r\n" + "                          Z.BUDGET_CODE,\r\n"
            + "                          B.ORGINAL_ESTAMT,\r\n" + "                          B.REVISED_ESTAMT,\r\n"
            + "                          Z.ESTIMATE_FOR_NEXTYEAR,\r\n" + "                          Z.CREATED_BY,\r\n"
            + "                          Z.ACTUAL_OF_LAST_YEAR,\r\n"+ "                          Z.REMARK_k\r\n"
            + "                     FROM (SELECT D.BUDGETCODE_ID,\r\n"
            + "                                  D.BUDGET_CODE2 AS BUDGET_CODE,\r\n"
            + "                                  D.ESTIMATE_FOR_NEXTYEAR,\r\n"
            + "                                  D.CREATED_BY,\r\n"
            + "                                  X.ACTUAL_OF_LAST_YEAR,\r\n"
            + "                                  D.REMARK_k\r\n"
            + "                             FROM (SELECT A.BUDGETCODE_ID,\r\n"
            + "                                          A.BUDGET_CODE,\r\n"
            + "                                          A.CREATED_BY,\r\n"
            + "                                          E.ACTUAL_OF_LAST_YEAR\r\n"
            + "                                     FROM TB_AC_BUDGETCODE_MAS A\r\n"
            + "                                     LEFT OUTER JOIN (SELECT BMG.BUDGETCODE_ID,\r\n"
            + "                                                            BMG.BUDGET_CODE,\r\n"
            + "                                                            PR.ORGINAL_ESTAMT,\r\n"
            + "                                                            MDS.ACTUAL_OF_LAST_YEAR\r\n"
            + "                                                       FROM TB_AC_BUDGETCODE_MAS   BMG,\r\n"
            + "                                                            TB_FINANCIALYEAR       FY,\r\n"
            + "                                                            TB_AC_PROJECTEDREVENUE PR\r\n"
            + "                                                       LEFT OUTER JOIN (SELECT BM.BUDGETCODE_ID,\r\n"
            + "                                                                              BM.BUDGET_CODE,\r\n"
            + "                                                                              MD.ACTUAL_OF_LAST_YEAR\r\n"
            + "                                                                         FROM TB_AC_BUDGETCODE_MAS BM,\r\n"
            + "                                                                              TB_AC_PRIMARYHEAD_MASTER PM,\r\n"
            + "                                                                              TB_AC_FUNCTION_MASTER FM,\r\n"
            + "                                                                              TB_AC_SECONDARYHEAD_MASTER SM,\r\n"
            + "                                                                              (SELECT RD.SAC_HEAD_ID,\r\n"
            + "                                                                                      RM.ORGID,\r\n"
            + "                                                                                      SUM(RD.RF_FEEAMOUNT) ACTUAL_OF_LAST_YEAR\r\n"
            + "                                                                                 FROM TB_RECEIPT_MAS RM,\r\n"
            + "                                                                                      TB_RECEIPT_DET RD\r\n"
            + "                                                                                WHERE RM.RM_RCPTID =\r\n"
            + "                                                                                      RD.RM_RCPTID\r\n"
            + "                                                                                  AND RM.ORGID =\r\n"
            + "                                                                                      RD.ORGID\r\n"
            + "                                                                                  AND RM.ORGID = :orgId\r\n"
            + "                                                                                  AND RM.RM_DATE BETWEEN\r\n"
            + "                                                                                      :prevYrFromDate AND\r\n"
            + "                                                                                      :toDate\r\n"
            + "                                                                                  AND RM.RECEIPT_DEL_FLAG IS NULL\r\n"
            + "                                                                                  AND RM.RECEIPT_TYPE_FLAG IN\r\n"
            + "                                                                                      ('M',\r\n"
            + "                                                                                       'R',\r\n"
            + "                                                                                       'P',\r\n"
            + "                                                                                       'A')\r\n"
            + "                                                                                GROUP BY RD.SAC_HEAD_ID,\r\n"
            + "                                                                                         RM.ORGID) MD\r\n"
            + "                                                                        WHERE BM.FUNCTION_ID =\r\n"
            + "                                                                              FM.FUNCTION_ID\r\n"
            + "                                                                          AND BM.PAC_HEAD_ID =\r\n"
            + "                                                                              PM.PAC_HEAD_ID\r\n"
            + "                                                                          AND PM.PAC_HEAD_ID =\r\n"
            + "                                                                              SM.PAC_HEAD_ID\r\n"
            + "                                                                          AND FM.FUNCTION_ID =\r\n"
            + "                                                                              SM.FUNCTION_ID\r\n"
            + "                                                                          AND SM.SAC_HEAD_ID =\r\n"
            + "                                                                              MD.SAC_HEAD_ID) MDS\r\n"
            + "                                                         ON PR.BUDGETCODE_ID =\r\n"
            + "                                                            MDS.BUDGETCODE_ID\r\n"
            + "                                                      WHERE BMG.BUDGETCODE_ID =\r\n"
            + "                                                            PR.BUDGETCODE_ID\r\n"
            + "                                                        AND PR.FA_YEARID =\r\n"
            + "                                                            FY.FA_YEARID\r\n"
            + "                                                        AND PR.FA_YEARID = :prvYrFinancialyr \r\n"
            + "                                                        AND PR.ORGID = :orgId) E\r\n"
            + "                                       ON A.BUDGETCODE_ID = E.BUDGETCODE_ID\r\n"
            + "                                    WHERE A.BUDGETCODE_ID IN\r\n"
            + "                                          (SELECT DISTINCT BUDGETCODE_ID AS BUDGETCODE_ID\r\n"
            + "                                             FROM TB_AC_PROJECTEDREVENUE)) X\r\n"
            + "                            RIGHT OUTER JOIN (SELECT EE.*,\r\n"
            + "                                                    MM.BUDGET_CODE AS BUDGET_CODE2,\r\n"
            + "                                                    PE.REMARK REMARK_k\r\n"
            + "                                               FROM TB_AC_BUDGETORY_ESTIMATE EE,\r\n"
            + "                                                    TB_AC_BUDGETCODE_MAS     MM,\r\n"
            + "                                                    TB_AC_PROJECTEDREVENUE PE\r\n"
            + "                                              WHERE EE.NFA_YEARID = :nextFaYearId\r\n"
            + "                                                AND EE.ORGID = :orgId\r\n"
            + "                                                AND EE.BUDGETCODE_ID =\r\n"
            + "                                                    MM.BUDGETCODE_ID\r\n"
            + "                                                AND EE.BUDGETCODE_ID =\r\n"
            + "                                                    PE.BUDGETCODE_ID\r\n"
            + "                                                 AND  PE.FA_YEARID = :nextFaYearId\r\n"
            + "                                                AND CPD_BUGTYPE_ID = :cpdBugtypeid) D \r\n"
            + "                               ON X.BUDGETCODE_ID = D.BUDGETCODE_ID) Z\r\n"
            + "                     LEFT OUTER JOIN (SELECT *\r\n"
            + "                                       FROM TB_AC_PROJECTEDREVENUE\r\n"
            + "                                      WHERE FA_YEARID = :financialId) B \r\n"
            + "                       ON Z.BUDGETCODE_ID = B.BUDGETCODE_ID) C\r\n"
            + "     ON G.BUDGETCODE_ID = C.BUDGETCODE_ID\r\n"  
            + "     group by C.BUDGETCODE_ID,\r\n"  
            + "       C.BUDGET_CODE,\r\n"  
            + "       G.AVG,\r\n" 
            + "       C.ACTUAL_OF_LAST_YEAR,\r\n"  
            + "       G.REVISED_ESTAMT,\r\n"  
            + "       G.ORGINAL_ESTAMT,\r\n" 
            + "       C.ESTIMATE_FOR_NEXTYEAR,C.CREATED_BY\r\n", nativeQuery = true)
    List<Object[]> getAllBudgetEnstimationOfReceiptReportData(@Param("lastYrFromDate") Date lastYrFromDate,
            @Param("toDate") Date toDate, @Param("prevYrFromDate") Date prevYrFromDate,
            @Param("financialId") long financialId, @Param("prvYrFinancialyr") long prvYrFinancialyr,
            @Param("orgId") long orgId, @Param("cpdBugtypeid") Long cpdBugtypeid,
            @Param("nextFaYearId") Long nextFaYearId);

    @Query(value = "SELECT DISTINCT C.BUDGETCODE_ID,\r\n" + "       C.BUDGET_CODE,\r\n" + "       G.AVG,\r\n"
            + "       C.ACTUAL_OF_LAST_YEAR,\r\n" + "       G.REVISED_ESTAMT,\r\n" + "       G.ORGINAL_ESTAMT,\r\n"
            + "       C.ESTIMATE_FOR_NEXTYEAR,\r\n" + "       C.CREATED_BY,\r\n"+ "    group_concat(distinct C.REMARK_k) remark\r\n" + "  FROM (SELECT B.BUDGETCODE_ID,B.DP_DEPTID,\r\n"
            + "               A.AVG,\r\n" + "               B.REVISED_ESTAMT,\r\n"
            + "               B.ORGINAL_ESTAMT,\r\n" + "               B.CREATED_BY\r\n"
            + "          FROM (SELECT INA.BUDGETCODE_ID, (SUM(INA.ORGINAL_ESTAMT) / COUNT(*)) AS AVG\r\n"
            + "                  FROM (SELECT *\r\n" + "                          FROM TB_AC_PROJECTED_EXPENDITURE\r\n"
            + "                         WHERE ORGID = :orgId\r\n" + "                           AND FA_YEARID IN\r\n"
            + "                               (SELECT FA_YEARID\r\n"
            + "                                  FROM TB_FINANCIALYEAR\r\n"
            + "                                 WHERE FA_FROMDATE BETWEEN :lastYrFromDate AND\r\n"
            + "                                       :toDate))\r\n" + "                 INA GROUP BY INA.BUDGETCODE_ID) A\r\n"
            + "         RIGHT OUTER JOIN (SELECT B.BUDGETCODE_ID,B.DP_DEPTID,\r\n"
            + "                                 B.ORGINAL_ESTAMT,\r\n"
            + "                                 B.REVISED_ESTAMT,\r\n"
            + "                                 USER_ID AS CREATED_BY\r\n"
            + "                            FROM TB_AC_BUDGETCODE_MAS     A,\r\n"
            + "                                 TB_AC_PROJECTED_EXPENDITURE   B,\r\n"
            + "                                 TB_FINANCIALYEAR         C\r\n"
            + "                           WHERE B.ORGID = :orgId\r\n"
            + "                             AND B.FA_YEARID = C.FA_YEARID\r\n"
            + "                             AND A.BUDGETCODE_ID = B.BUDGETCODE_ID\r\n"
            + "                             AND A.BUDGETCODE_ID IN\r\n"
            + "                                 (SELECT DISTINCT BUDGETCODE_ID AS BUDGETCODE_ID\r\n"
            + "                                    FROM TB_AC_PROJECTED_EXPENDITURE)\r\n"
            + "                             AND B.FA_YEARID = :financialId) B \r\n"
            + "            ON A.BUDGETCODE_ID = B.BUDGETCODE_ID\r\n" + "        ) G\r\n"
            + " RIGHT OUTER JOIN (SELECT Z.BUDGETCODE_ID,Z.DP_DEPTID,\r\n" + "                          Z.BUDGET_CODE,\r\n"
            + "                          B.ORGINAL_ESTAMT,\r\n" + "                          B.REVISED_ESTAMT,\r\n"
            + "                          Z.ESTIMATE_FOR_NEXTYEAR,\r\n" + "                          Z.CREATED_BY,\r\n"
            + "                          Z.ACTUAL_OF_LAST_YEAR,\r\n" + "                          Z.REMARK_k\r\n"
            + "                     FROM (SELECT D.BUDGETCODE_ID,D.DP_DEPTID,\r\n"
            + "                                  D.BUDGET_CODE2 AS BUDGET_CODE,\r\n"
            + "                                  D.ESTIMATE_FOR_NEXTYEAR,\r\n"
            + "                                  D.CREATED_BY,\r\n"
            + "                                  X.ACTUAL_OF_LAST_YEAR,\r\n"
            + "                                  D.REMARK_k\r\n"
            + "                             FROM (SELECT A.BUDGETCODE_ID,A.DP_DEPTID,\r\n"
            + "                                          A.BUDGET_CODE,\r\n"
            + "                                          A.CREATED_BY,\r\n"
            + "                                          E.ACTUAL_OF_LAST_YEAR\r\n"
            + "                                     FROM TB_AC_BUDGETCODE_MAS A\r\n"
            + "                                     LEFT OUTER JOIN (SELECT BMG.BUDGETCODE_ID,\r\n"
            + "                                                            BMG.BUDGET_CODE,\r\n"
            + "                                                            PR.ORGINAL_ESTAMT,\r\n"
            + "                                                            MDS.ACTUAL_OF_LAST_YEAR\r\n"
            + "                                                       FROM TB_AC_BUDGETCODE_MAS   BMG,\r\n"
            + "                                                            TB_FINANCIALYEAR       FY,\r\n"
            + "                                                            TB_AC_PROJECTED_EXPENDITURE PR\r\n"
            + "                                                       LEFT OUTER JOIN (SELECT BM.BUDGETCODE_ID,\r\n"
            + "                                                                              BM.BUDGET_CODE,\r\n"
            + "                                                                              MD.ACTUAL_OF_LAST_YEAR\r\n"
            + "                                                                         FROM TB_AC_BUDGETCODE_MAS BM,\r\n"
            + "                                                                              TB_AC_PRIMARYHEAD_MASTER PM,\r\n"
            + "                                                                              TB_AC_FUNCTION_MASTER FM,\r\n"
            + "                                                                              TB_AC_SECONDARYHEAD_MASTER SM,\r\n"
            + "                                                                              (SELECT RD.BUDGETCODE_ID,\r\n"
            + "                                                                                      RM.ORGID,\r\n"
            + "                                                                                      SUM(RD.PAYMENT_AMT) ACTUAL_OF_LAST_YEAR\r\n"
            + "                                                                                 FROM TB_AC_PAYMENT_MAS RM,\r\n"
            + "                                                                                      TB_AC_PAYMENT_DET RD\r\n"
            + "                                                                                WHERE RM.PAYMENT_ID =\r\n"
            + "                                                                                      RD.PAYMENT_ID\r\n"
            + "                                                                                  AND RM.ORGID =\r\n"
            + "                                                                                      RD.ORGID\r\n"
            + "                                                                                  AND RM.ORGID = :orgId\r\n"
            + "                                                                                  AND RM.PAYMENT_DATE BETWEEN\r\n"
            + "                                                                                      :prevYrFromDate AND\r\n"
            + "                                                                                      :toDate\r\n"
            + "                                                                                  AND RM.PAYMENT_DEL_FLAG IS NULL\r\n"
            + "                                                                                GROUP BY RD.BUDGETCODE_ID,\r\n"
            + "                                                                                         RM.ORGID) MD\r\n"
            + "                                                                        WHERE BM.FUNCTION_ID =\r\n"
            + "                                                                              FM.FUNCTION_ID\r\n"
            + "                                                                          AND BM.PAC_HEAD_ID =\r\n"
            + "                                                                              PM.PAC_HEAD_ID\r\n"
            + "                                                                          AND PM.PAC_HEAD_ID =\r\n"
            + "                                                                              SM.PAC_HEAD_ID\r\n"
            + "                                                                          AND FM.FUNCTION_ID =\r\n"
            + "                                                                              SM.FUNCTION_ID\r\n"
            + "                                                                          AND SM.SAC_HEAD_ID =\r\n"
            + "                                                                              MD.BUDGETCODE_ID) MDS\r\n"
            + "                                                         ON PR.BUDGETCODE_ID =\r\n"
            + "                                                            MDS.BUDGETCODE_ID\r\n"
            + "                                                      WHERE BMG.BUDGETCODE_ID =\r\n"
            + "                                                            PR.BUDGETCODE_ID\r\n"
            + "                                                        AND PR.FA_YEARID =\r\n"
            + "                                                            FY.FA_YEARID\r\n"
            + "                                                        AND PR.FA_YEARID = :prvYrFinancialyr \r\n"
            + "                                                        AND PR.ORGID = :orgId) E\r\n"
            + "                                       ON A.BUDGETCODE_ID = E.BUDGETCODE_ID\r\n"
            + "                                    WHERE A.BUDGETCODE_ID IN\r\n"
            + "                                          (SELECT DISTINCT BUDGETCODE_ID AS BUDGETCODE_ID\r\n"
            + "                                             FROM TB_AC_PROJECTED_EXPENDITURE)) X\r\n"
            + "                            RIGHT OUTER JOIN (SELECT EE.*,\r\n"
            + "                                                    MM.BUDGET_CODE AS BUDGET_CODE2,\r\n"
            + "                                                    PE.REMARK REMARK_k\r\n"
            + "                                               FROM TB_AC_BUDGETORY_ESTIMATE EE,\r\n"
            + "                                                    TB_AC_BUDGETCODE_MAS MM,     \r\n"
            + "                                                    TB_AC_PROJECTED_EXPENDITURE PE\r\n"
            + "                                              WHERE EE.NFA_YEARID = :nextFaYearId\r\n"
            + "                                                AND EE.ORGID = :orgId\r\n"
            + "                                                AND EE.BUDGETCODE_ID =\r\n"
            + "                                                    MM.BUDGETCODE_ID\r\n"
            + "                                                AND EE.BUDGETCODE_ID =\r\n"
            + "                                                    PE.BUDGETCODE_ID\r\n"
            + "                                                 AND  PE.FA_YEARID = :nextFaYearId\r\n"
            + "                                                AND CPD_BUGTYPE_ID = :cpdBugtypeid) D \r\n"
            + "                               ON X.BUDGETCODE_ID = D.BUDGETCODE_ID) Z\r\n"
            + "                     LEFT OUTER JOIN (SELECT *\r\n"
            + "                                       FROM TB_AC_PROJECTED_EXPENDITURE\r\n"
            + "                                      WHERE FA_YEARID = :financialId) B \r\n"
            + "                       ON Z.BUDGETCODE_ID = B.BUDGETCODE_ID and Z.DP_DEPTID=B.DP_DEPTID) C\r\n"
            + "    ON G.BUDGETCODE_ID = C.BUDGETCODE_ID and G.DP_DEPTID = C.DP_DEPTID\r\n"  
            + "     group by C.BUDGETCODE_ID,\r\n" 
            + "       C.BUDGET_CODE,\r\n"  
            + "       G.AVG,\r\n" 
            + "       C.ACTUAL_OF_LAST_YEAR,\r\n"  
            + "       G.REVISED_ESTAMT,\r\n"  
            + "       G.ORGINAL_ESTAMT,\r\n" 
            + "       C.ESTIMATE_FOR_NEXTYEAR,C.CREATED_BY\r\n", nativeQuery = true)
    List<Object[]> getAllBudgetEnstimationOfExpenditureReportData(@Param("lastYrFromDate") Date lastYrFromDate,
            @Param("toDate") Date toDate, @Param("prevYrFromDate") Date prevYrFromDate,
            @Param("financialId") long financialId, @Param("prvYrFinancialyr") long prvYrFinancialyr,
            @Param("orgId") long orgId, @Param("cpdBugtypeid") Long cpdBugtypeid,
            @Param("nextFaYearId") Long nextFaYearId);

    @Query(value = "SELECT bm.BUDGETCODE_ID,\r\n" + "bm.budget_code,\r\n" + " SUM(rd.RF_FEEAMOUNT) \r\n"
            + "  FROM tb_receipt_mas             rms,\r\n" + "       tb_receipt_det             rd,\r\n"
            + "       tb_receipt_mode            rm,\r\n" + "       Tb_Ac_Secondaryhead_Master sm,\r\n"
            + "       tb_ac_primaryhead_master   pm,\r\n" + "       tb_ac_function_master      fm,\r\n"
            + "       tb_ac_budgetcode_mas       bm,\r\n" + "       tb_ac_projectedrevenue     pr,\r\n"
            + "       tb_financialyear           fn\r\n" + " WHERE rms.RM_RCPTID = rd.RM_RCPTID\r\n"
            + "   AND rms.RM_RCPTID = rm.RM_RCPTID\r\n" + "   AND rm.RM_RCPTID = rd.RM_RCPTID\r\n"
            + "   AND rd.SAC_HEAD_ID = sm.SAC_HEAD_ID\r\n" + "   AND rms.orgid = rd.orgid      \r\n"
            + "   AND rms.RECEIPT_TYPE_FLAG IN ('M','R','A','P')\r\n" + "   AND rm.CPD_FEEMODE IN\r\n"
            + "       (SELECT a.Cpd_id\r\n" + "          FROM tb_comparam_det a, tb_comparam_mas b\r\n"
            + "         WHERE b.CPM_PREFIX = 'PAY'\r\n"
            + "           AND a.CPD_VALUE IN ('C', 'Q', 'D', 'B', 'RT', 'W')\r\n"
            + "           AND a.cpm_id = b.cpm_id)\r\n" + "   AND rms.RECEIPT_DEL_FLAG IS NULL\r\n"
            + "   AND bm.BUDGETCODE_ID = pr.BUDGETCODE_ID\r\n" + "   AND pr.FA_YEARID = fn.FA_YEARID\r\n"
            + "   AND pm.PAC_HEAD_ID = bm.PAC_HEAD_ID\r\n" + "   AND fm.FUNCTION_ID = bm.FUNCTION_ID\r\n"
            + "   AND pm.PAC_HEAD_ID = sm.PAC_HEAD_ID\r\n" + "   AND fm.FUNCTION_ID = sm.FUNCTION_ID\r\n"
            + "   AND bm.ORGID = pr.ORGID\r\n" + "   AND bm.ORGID = rms.ORGID\r\n"
            + "   AND fn.FA_YEARID =:financialId\r\n" + "   AND rms.ORGID =:orgId\r\n"
            + "   AND rms.RM_DATE BETWEEN :fromDate AND :toDate\r\n"
            + " GROUP BY bm.BUDGETCODE_ID, bm.BUDGET_CODE", nativeQuery = true)
    List<Object[]> queryReAppropriationReportUtilTotalAmtPayment(@Param("orgId") Long orgId,
            @Param("financialId") Long financialId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    /*
     * @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
     * "  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" + "               A.PAC_HEAD_DESC,\r\n" +
     * "               A.PAC_HEAD_ID AS LEVEL2,\r\n" + "               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
     * "               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" + "          FROM (SELECT AC_HEAD_CODE,\r\n" +
     * "                       SAC_HEAD_ID,\r\n" + "                       RCPTAMT,\r\n" +
     * "                       A.PAC_HEAD_ID\r\n" + "                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
     * "                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
     * "                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
     * "                               (SUM(VD.VAMT_CR) - SUM(VD.VAMT_DR)) RCPTAMT\r\n" +
     * "                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
     * "                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
     * "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
     * "                                      (SELECT CPD_ID\r\n" +
     * "                                         FROM TB_COMPARAM_DET D\r\n" +
     * "                                        WHERE     D.CPM_ID IN\r\n" +
     * "                                                     (SELECT CPM_ID\r\n" +
     * "                                                        FROM TB_COMPARAM_MAS\r\n" +
     * "                                                             M\r\n" +
     * "                                                       WHERE M.CPM_PREFIX =\r\n" +
     * "                                                                'TDP')\r\n" +
     * "                                              AND CPD_VALUE IN ('RRE',\r\n" +
     * "                                                                'RV',\r\n" +
     * "                                                                'RDC',\r\n" +
     * "                                                                'ASE'))\r\n" +
     * "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
     * "                               AND SD.SAC_LED_TYPE IN\r\n" + "                                      (SELECT CPD_ID\r\n" +
     * "                                         FROM TB_COMPARAM_DET D\r\n" +
     * "                                        WHERE     D.CPM_ID IN\r\n" +
     * "                                                     (SELECT CPM_ID\r\n" +
     * "                                                        FROM TB_COMPARAM_MAS\r\n" +
     * "                                                             M\r\n" +
     * "                                                       WHERE M.CPM_PREFIX =\r\n" +
     * "                                                                'FTY')\r\n" +
     * "                                              AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
     * "                               AND VD.VOU_POSTING_DATE BETWEEN '2017-04-01'\r\n" +
     * "                                                           AND '2017-04-01'\r\n" +
     * "                               AND VD.ORGID = 13\r\n" +
     * "                               AND FN.FA_YEARID =:finanacialYearId\r\n" +
     * "                        GROUP BY VD.AC_HEAD_CODE,\r\n" + "                                 VD.SAC_HEAD_ID,\r\n" +
     * "                                 VD.PAC_HEAD_ID) A) AAA,\r\n" + "               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
     * "               TB_AC_PRIMARYHEAD_MASTER B\r\n" + "         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
     * "               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + "        GROUP BY A.PAC_HEAD_ID,\r\n" +
     * "                 A.PAC_HEAD_DESC,\r\n" + "                 B.PAC_HEAD_ID,\r\n" +
     * "                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + "       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
     * " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES = 620\r\n" +
     * "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" + "ORDER BY R.PAC_HEAD_COMPO_CODE ASC;", nativeQuery = true)
     * List<Object[]> queryClassifiedBudgetReceiptSideReportDataL1(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
     * @Param("orgId") Long orgId, @Param("codcofdetId") Long codcofdetId);
     */

    @Query("SELECT sum(vtd.drAmount), sum(vtd.crAmount) from VoucherDetailViewEntity vtd where  vtd.sacHeadId =:accountHeadId and vtd.orgId =:orgId and vtd.voPostingDate between :financialYFromDate  and :dateBefore ")
    List<Object[]> getSumOfCreditAndSumOfDebitFromVouchers(@Param("financialYFromDate") Date financialYFromDate,
            @Param("dateBefore") Date dateBefore, @Param("accountHeadId") Long accountHeadId,
            @Param("orgId") long orgId);
    @Query("SELECT sum(vtd.drAmount), sum(vtd.crAmount) from VoucherDetailViewEntity vtd where  vtd.sacHeadId in (:accountHeadId) and vtd.orgId =:orgId and vtd.voPostingDate between :financialYFromDate  and :dateBefore ")
    List<Object[]> getSumOfCreditAndSumOfDebitFromVouchersAndAccHeads(@Param("financialYFromDate") Date financialYFromDate,
            @Param("dateBefore") Date dateBefore, @Param("accountHeadId") List<Long> accountHeadIds,
            @Param("orgId") long orgId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('RRE',\r\n" +
            "                                                                'RV',\r\n" +
            "                                                                'RDC',\r\n" +
            "                                                                'ASE'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES =:codcofdetId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideOpeningReportDataL3(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("codcofdetId") Long codcofdetId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('RRE',\r\n" +
            "                                                                'RV',\r\n" +
            "                                                                'RDC',\r\n" +
            "                                                                'ASE'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES <> :codcofdetId and R.PAC_HEAD_COMPO_CODE in (330,331,340,320,412,420,421,341,350,431)\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningReportDataL3(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("codcofdetId") Long codcofdetId);

    @Query(value = "SELECT A.PAC_HEAD_ID,\r\n" +
            "       CONCAT((CONCAT(A.PAC_HEAD_COMPO_CODE, ' - ')), A.PAC_HEAD_DESC) CODE_DESC,\r\n" +
            "       SUM(A.PAYMENT_AMT) PAYMENT_AMT,\r\n" +
            "       SUM(A.ORGINAL_ESTAMT) ORGINAL_ESTAMT,\r\n" +
            "       SUM(A.REVISED_ESTAMT) REVISED_ESTAMT\r\n" +
            "  FROM (SELECT PMS.PAC_HEAD_ID,\r\n" +
            "               0                       PAYMENT_ID,\r\n" +
            "               PMS.PAC_HEAD_COMPO_CODE,\r\n" +
            "               PMS.PAC_HEAD_DESC,\r\n" +
            "               PR.ORGINAL_ESTAMT,\r\n" +
            "               PR.REVISED_ESTAMT,\r\n" +
            "               0                       PAYMENT_AMT\r\n" +
            "          FROM TB_AC_PROJECTED_EXPENDITURE PR,\r\n" +
            "               TB_FINANCIALYEAR            FN,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER    PMS,\r\n" +
            "               TB_AC_BUDGETCODE_MAS        BM\r\n" +
            "         WHERE BM.BUDGETCODE_ID = PR.BUDGETCODE_ID\r\n" +
            "           AND PR.FA_YEARID = FN.FA_YEARID\r\n" +
            "           AND PMS.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n" +
            "           AND PMS.CODCOFDET_ID =:codcofdetId \r\n" +
            "           AND BM.ORGID =:orgId \r\n" +
            "           AND FN.FA_YEARID =:finanacialYearId \r\n" +
            "        UNION\r\n" +
            "        SELECT VP.PAC_HEAD_ID,\r\n" +
            "               VP.PAYMENT_ID,\r\n" +
            "               VP.PAC_HEAD_COMPO_CODE,\r\n" +
            "               VP.PAC_HEAD_DESC,\r\n" +
            "               0                      ORGINAL_ESTAMT,\r\n" +
            "               0                      REVISED_ESTAMT,\r\n" +
            "               VP.PAYMENT_AMT         PAYMENT_AMT\r\n" +
            "          FROM VW_AC_PAYMENT VP\r\n" +
            "         WHERE VP.ORGID =:orgId\r\n" +
            "           AND VP.PAYMENT_DATE BETWEEN :fromDate AND :toDate ) A\r\n" +
            " GROUP BY A.PAC_HEAD_ID, A.PAC_HEAD_COMPO_CODE, A.PAC_HEAD_DESC\r\n" +
            " ORDER BY A.PAC_HEAD_COMPO_CODE", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideReportDataL1(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId, @Param("finanacialYearId") Long finanacialYearId,
            @Param("codcofdetId") Long codcofdetId);

    @Query(value = " SELECT E.PAC_HEAD_ID,\r\n" +
            "       CONCAT((CONCAT(E.PAC_HEAD_COMPO_CODE, ' - ')), E.PAC_HEAD_DESC),\r\n" +
            "       SUM(B.RECEIPT_AMT) RECEIPT_AMT,\r\n" +
            "       SUM(B.ORGINAL_ESTAMT) ORGINAL_ESTAMT,\r\n" +
            "       SUM(B.REVISED_ESTAMT) REVISED_ESTAMT\r\n" +
            "  FROM (SELECT A.PAC_HEAD_ID,\r\n" +
            "               A.PAC_HEAD_PARENT_ID,\r\n" +
            "               A.PAC_HEAD_COMPO_CODE,\r\n" +
            "               A.PAC_HEAD_DESC,\r\n" +
            "               A.ORGINAL_ESTAMT,\r\n" +
            "               A.REVISED_ESTAMT,\r\n" +
            "               A.RECEIPT_AMT\r\n" +
            "          FROM (SELECT PMS.PAC_HEAD_ID,\r\n" +
            "                       PMS.PAC_HEAD_PARENT_ID,\r\n" +
            "                       PMS.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       PMS.PAC_HEAD_DESC,\r\n" +
            "                       PR.ORGINAL_ESTAMT       ORGINAL_ESTAMT,\r\n" +
            "                       PR.REVISED_ESTAMT       REVISED_ESTAMT,\r\n" +
            "                       0                       RECEIPT_AMT\r\n" +
            "                  FROM TB_AC_PROJECTEDREVENUE   PR,\r\n" +
            "                       TB_FINANCIALYEAR         FN,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER PMS,\r\n" +
            "                       TB_AC_BUDGETCODE_MAS     BM\r\n" +
            "                 WHERE BM.BUDGETCODE_ID = PR.BUDGETCODE_ID\r\n" +
            "                   AND PR.FA_YEARID = FN.FA_YEARID\r\n" +
            "                   AND PMS.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n" +
            "                   AND BM.ORGID =:orgId \r\n" +
            "                   AND FN.FA_YEARID =:finanacialYearId\r\n" +
            "                UNION\r\n" +
            "                SELECT VR.PAC_HEAD_ID,\r\n" +
            "                       VR.PAC_HEAD_PARENT_ID,\r\n" +
            "                       VR.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       VR.PAC_HEAD_DESC,\r\n" +
            "                       0                      ORGINAL_ESTAMT,\r\n" +
            "                       0                      REVISED_ESTAMT,\r\n" +
            "                       RF_FEEAMOUNT           RECEIPT_AMT\r\n" +
            "                  FROM VW_AC_RECEIPT VR\r\n" +
            "                 WHERE ORGID =:orgId \r\n" +
            "                   AND RM_DATE BETWEEN :fromDate  AND :toDate ) A) B,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER D,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER E\r\n" +
            " WHERE B.PAC_HEAD_PARENT_ID = C.PAC_HEAD_ID\r\n" +
            "   AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "   AND D.PAC_HEAD_PARENT_ID = E.PAC_HEAD_ID\r\n" +
            "   AND E.CODCOFDET_ID =:codcofdetId \r\n" +
            " GROUP BY E.PAC_HEAD_ID,E.PAC_HEAD_COMPO_CODE, E.PAC_HEAD_DESC\r\n" +
            " ORDER BY E.PAC_HEAD_COMPO_CODE ", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideReportDataL2(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("finanacialYearId") Long finanacialYearId, @Param("codcofdetId") Long codcofdetId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                               (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('PVE','RBP'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES =:codcofdetId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC;", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideOpeningReportDataL3(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId, @Param("codcofdetId") Long codcofdetId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                               (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('PVE', 'RBP'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES <>:codcofdetId and R.PAC_HEAD_COMPO_CODE in (350,320,360,340,410,412,341,420,421,460,440)\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReportDataL3(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId, @Param("codcofdetId") Long codcofdetId);

    @Query(value = " SELECT D.PAC_HEAD_ID,\r\n" +
            "       CONCAT((CONCAT(D.PAC_HEAD_COMPO_CODE, ' - ')), D.PAC_HEAD_DESC),\r\n" +
            "       SUM(B.RECEIPT_AMT) RECEIPT_AMT,\r\n" +
            "       SUM(B.ORGINAL_ESTAMT) ORGINAL_ESTAMT,\r\n" +
            "       SUM(B.REVISED_ESTAMT) REVISED_ESTAMT\r\n" +
            "  FROM (SELECT A.PAC_HEAD_ID,\r\n" +
            "               A.PAC_HEAD_PARENT_ID,\r\n" +
            "               A.PAC_HEAD_COMPO_CODE,\r\n" +
            "               A.PAC_HEAD_DESC,\r\n" +
            "               A.ORGINAL_ESTAMT,\r\n" +
            "               A.REVISED_ESTAMT,\r\n" +
            "               A.RECEIPT_AMT\r\n" +
            "          FROM (SELECT PMS.PAC_HEAD_ID,\r\n" +
            "                       PMS.PAC_HEAD_PARENT_ID,\r\n" +
            "                       PMS.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       PMS.PAC_HEAD_DESC,\r\n" +
            "                       PR.ORGINAL_ESTAMT       ORGINAL_ESTAMT,\r\n" +
            "                       PR.REVISED_ESTAMT       REVISED_ESTAMT,\r\n" +
            "                       0                       RECEIPT_AMT\r\n" +
            "                  FROM TB_AC_PROJECTEDREVENUE   PR,\r\n" +
            "                       TB_FINANCIALYEAR         FN,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER PMS,\r\n" +
            "                       TB_AC_BUDGETCODE_MAS     BM\r\n" +
            "                 WHERE BM.BUDGETCODE_ID = PR.BUDGETCODE_ID\r\n" +
            "                   AND PR.FA_YEARID = FN.FA_YEARID\r\n" +
            "                   AND PMS.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n" +
            "                   AND BM.ORGID =:orgId \r\n" +
            "                   AND FN.FA_YEARID =:finanacialYearId\r\n" +
            "                UNION\r\n" +
            "                SELECT VR.PAC_HEAD_ID,\r\n" +
            "                       VR.PAC_HEAD_PARENT_ID,\r\n" +
            "                       VR.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       VR.PAC_HEAD_DESC,\r\n" +
            "                       0                      ORGINAL_ESTAMT,\r\n" +
            "                       0                      REVISED_ESTAMT,\r\n" +
            "                       RF_FEEAMOUNT           RECEIPT_AMT\r\n" +
            "                  FROM VW_AC_RECEIPT VR\r\n" +
            "                 WHERE ORGID =:orgId \r\n" +
            "                   AND RM_DATE BETWEEN :fromDate AND :toDate ) A) B,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            " WHERE B.PAC_HEAD_PARENT_ID = C.PAC_HEAD_ID\r\n" +
            "   AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "   AND D.CODCOFDET_ID =:codcofdetId\r\n" +
            " GROUP BY D.PAC_HEAD_ID, D.PAC_HEAD_COMPO_CODE, D.PAC_HEAD_DESC\r\n" +
            " ORDER BY D.PAC_HEAD_COMPO_CODE", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideReportDataL4(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("orgId") Long orgId, @Param("finanacialYearId") Long finanacialYearId, @Param("codcofdetId") Long codcofdetId);

    @Query(value = "SELECT E.PAC_HEAD_ID,\r\n" +
            "       CONCAT((CONCAT(E.PAC_HEAD_COMPO_CODE, ' - ')), E.PAC_HEAD_DESC),\r\n" +
            "       SUM(B.PAYMENT_AMT) PAYMENT_AMT,\r\n" +
            "       SUM(B.ORGINAL_ESTAMT) ORGINAL_ESTAMT,\r\n" +
            "       SUM(B.REVISED_ESTAMT) REVISED_ESTAMTT\r\n" +
            "  FROM (SELECT A.PAC_HEAD_ID,\r\n" +
            "               A.PAC_HEAD_PARENT_ID,\r\n" +
            "               A.ORGINAL_ESTAMT     ORGINAL_ESTAMT,\r\n" +
            "               A.REVISED_ESTAMT     REVISED_ESTAMT,\r\n" +
            "               A.PAYMENT_AMT        PAYMENT_AMT\r\n" +
            "          FROM (SELECT PMS.PAC_HEAD_ID,\r\n" +
            "                       PMS.PAC_HEAD_PARENT_ID,\r\n" +
            "                       0                       PAYMENT_ID,\r\n" +
            "                       PMS.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       PMS.PAC_HEAD_DESC,\r\n" +
            "                       PR.ORGINAL_ESTAMT       ORGINAL_ESTAMT,\r\n" +
            "                       PR.REVISED_ESTAMT       REVISED_ESTAMT,\r\n" +
            "                       0                       PAYMENT_AMT\r\n" +
            "                  FROM TB_AC_PROJECTED_EXPENDITURE PR,\r\n" +
            "                       TB_FINANCIALYEAR            FN,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER    PMS,\r\n" +
            "                       TB_AC_BUDGETCODE_MAS        BM\r\n" +
            "                 WHERE BM.BUDGETCODE_ID = PR.BUDGETCODE_ID\r\n" +
            "                   AND PR.FA_YEARID = FN.FA_YEARID\r\n" +
            "                   AND PMS.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n" +
            "                   AND BM.ORGID =:orgId \r\n" +
            "                   AND FN.FA_YEARID =:finanacialYearId \r\n" +
            "                UNION\r\n" +
            "                SELECT VP.PAC_HEAD_ID,\r\n" +
            "                       VP.PAC_HEAD_PARENT_ID,\r\n" +
            "                       VP.PAYMENT_ID,\r\n" +
            "                       VP.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       VP.PAC_HEAD_DESC,\r\n" +
            "                       0                      ORGINAL_ESTAMT,\r\n" +
            "                       0                      REVISED_ESTAMT,\r\n" +
            "                       VP.PAYMENT_AMT         PAYMENT_AMT\r\n" +
            "                  FROM VW_AC_PAYMENT VP\r\n" +
            "                 WHERE VP.ORGID =:orgId \r\n" +
            "                   AND VP.PAYMENT_DATE BETWEEN :fromDate  AND\r\n" +
            "                       :toDate ) A) B,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER D,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER E\r\n" +
            " WHERE B.PAC_HEAD_PARENT_ID = C.PAC_HEAD_ID\r\n" +
            "   AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "   AND D.PAC_HEAD_PARENT_ID = E.PAC_HEAD_ID\r\n" +
            "   AND E.CODCOFDET_ID =:codcofdetId \r\n" +
            " GROUP BY E.PAC_HEAD_ID, E.PAC_HEAD_COMPO_CODE, E.PAC_HEAD_DESC\r\n" +
            " ORDER BY E.PAC_HEAD_COMPO_CODE", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideReportDataL2(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId, @Param("finanacialYearId") Long finanacialYearId,
            @Param("codcofdetId") Long codcofdetId);

    @Query(value = "SELECT D.PAC_HEAD_ID,\r\n" +
            "       CONCAT((CONCAT(D.PAC_HEAD_COMPO_CODE, ' - ')), D.PAC_HEAD_DESC),\r\n" +
            "       SUM(B.PAYMENT_AMT) PAYMENT_AMT,\r\n" +
            "       SUM(B.ORGINAL_ESTAMT) ORGINAL_ESTAMT,\r\n" +
            "       SUM(B.REVISED_ESTAMT) REVISED_ESTAMT\r\n" +
            "  FROM (SELECT A.PAC_HEAD_ID,\r\n" +
            "               A.PAC_HEAD_PARENT_ID,\r\n" +
            "               A.ORGINAL_ESTAMT     ORGINAL_ESTAMT,\r\n" +
            "               A.REVISED_ESTAMT     REVISED_ESTAMT,\r\n" +
            "               A.PAYMENT_AMT        PAYMENT_AMT\r\n" +
            "          FROM (SELECT PMS.PAC_HEAD_ID,\r\n" +
            "                       PMS.PAC_HEAD_PARENT_ID,\r\n" +
            "                       0                       PAYMENT_ID,\r\n" +
            "                       PMS.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       PMS.PAC_HEAD_DESC,\r\n" +
            "                       PR.ORGINAL_ESTAMT       ORGINAL_ESTAMT,\r\n" +
            "                       PR.REVISED_ESTAMT       REVISED_ESTAMT,\r\n" +
            "                       0                       PAYMENT_AMT\r\n" +
            "                  FROM TB_AC_PROJECTED_EXPENDITURE PR,\r\n" +
            "                       TB_FINANCIALYEAR            FN,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER    PMS,\r\n" +
            "                       TB_AC_BUDGETCODE_MAS        BM\r\n" +
            "                 WHERE BM.BUDGETCODE_ID = PR.BUDGETCODE_ID\r\n" +
            "                      \r\n" +
            "                   AND PR.FA_YEARID = FN.FA_YEARID\r\n" +
            "                   AND PMS.PAC_HEAD_ID = BM.PAC_HEAD_ID\r\n" +
            "                   AND BM.ORGID =:orgId \r\n" +
            "                   AND FN.FA_YEARID =:finanacialYearId \r\n" +
            "                UNION\r\n" +
            "                SELECT VP.PAC_HEAD_ID,\r\n" +
            "                       VP.PAC_HEAD_PARENT_ID,\r\n" +
            "                       VP.PAYMENT_ID,\r\n" +
            "                       VP.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       VP.PAC_HEAD_DESC,\r\n" +
            "                       0                      ORGINAL_ESTAMT,\r\n" +
            "                       0                      REVISED_ESTAMT,\r\n" +
            "                       VP.PAYMENT_AMT         PAYMENT_AMT\r\n" +
            "                  FROM VW_AC_PAYMENT VP\r\n" +
            "                 WHERE VP.ORGID =:orgId \r\n" +
            "                   AND VP.PAYMENT_DATE BETWEEN :fromDate  AND\r\n" +
            "                       :toDate ) A) B,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            " WHERE B.PAC_HEAD_PARENT_ID = C.PAC_HEAD_ID\r\n" +
            "   AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "   AND D.CODCOFDET_ID = :codcofdetId \r\n" +
            " GROUP BY D.PAC_HEAD_ID, D.PAC_HEAD_COMPO_CODE, D.PAC_HEAD_DESC\r\n" +
            " ORDER BY D.PAC_HEAD_COMPO_CODE", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideReportDataL4(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId, @Param("finanacialYearId") Long finanacialYearId,
            @Param("codcofdetId") Long codcofdetId);

    @Query(value = "SELECT  VM.VM_VENDORCODE,VM.VM_VENDORNAME,VM.VM_VENDORADD,VM.VM_PAN_NUMBER,\r\n"
            + "TB.O_NLS_ORGNAME,TB.ORG_ADDRESS,TB.TDS_PAN_GIR_NO\r\n" + "FROM  \r\n" + "tb_vendormaster VM,\r\n"
            + "tb_organisation TB\r\n" + "WHERE    VM.ORGID=TB.ORGID\r\n" + "         AND VM.ORGID =:orgId\r\n"
            + "         AND VM.VM_VENDORID =:vendorId", nativeQuery = true)
    List<Object[]> queryForTdsCertificate(@Param("orgId") long orgId, @Param("vendorId") Long vendorId);
    /*
     * @Query("SELECT v1 FROM  AccountBillEntryMasterHistEnitity v1 where v1.orgId=:orgId and v1.billEntryDate between :fromDate and :toDate"
     * ) List<AccountBillEntryMasterHistEnitity> queryForBasicsDetails(@Param("orgId") long orgId, @Param("fromDate") Date
     * fromDate, @Param("toDate") Date toDate);
     */

    /*
     * @Query(value = "SELECT DISTINCT (t1.bm_id),\r\n" + "                t1.BM_BILLNO,\r\n" +
     * "                t1.BM_ENTRYDATE,\r\n" + "                t1.VM_VENDORNAME,\r\n" + "                t2.SAC_HEAD_ID,\r\n" +
     * "                t2.BCH_CHARGES_AMT,\r\n" + "                t2.ACT_AMT,\r\n" + "                t3.DEDUCTION_AMT,\r\n" +
     * "                t1.BM_NARRATION,\r\n" + "                t1.LG_IP_MAC,\r\n" + "                t1.CREATED_BY,\r\n" +
     * "                t1.CREATED_DATE,\r\n" + "                t1.UPDATED_BY,\r\n" + "                t1.UPDATED_DATE,\r\n" +
     * "                t1.LG_IP_MAC_UPD,\r\n" + "                t1.H_STATUS,\r\n" +
     * "                t3.SAC_HEAD_ID as DeductionId\r\n" + "  FROM TB_AC_BILL_MAS_HIST t1,\r\n" +
     * "       tb_ac_bill_exp_detail_hist t2,\r\n" + "       tb_ac_bill_deduction_det_hist t3\r\n" +
     * " WHERE     t1.BM_ID = t2.BM_ID\r\n" + "       AND t1.BM_ID = t3.BM_ID\r\n" + "       AND t1.ORGID =:orgId\r\n" +
     * "       AND t1.BM_ENTRYDATE BETWEEN :fromDate AND :toDate ", nativeQuery = true)
     */
    @Query(value = "SELECT DISTINCT (P.BM_ID),\r\n" +
            "                P.BM_BILLNO,\r\n" +
            "                P.BM_ENTRYDATE,\r\n" +
            "                P.VM_VENDORNAME,\r\n" +
            "                P.SAC_HEAD_ID,\r\n" +
            "                P.BCH_CHARGES_AMT,\r\n" +
            "                P.ACT_AMT,\r\n" +
            "                Q.DEDUCTION_AMT,\r\n" +
            "                P.BM_NARRATION,\r\n" +
            "                P.LG_IP_MAC,\r\n" +
            "                P.CREATED_BY,\r\n" +
            "                P.CREATED_DATE,\r\n" +
            "                P.UPDATED_BY,\r\n" +
            "                P.UPDATED_DATE,\r\n" +
            "                P.LG_IP_MAC_UPD,\r\n" +
            "                P.H_STATUS,\r\n" +
            "                Q.SAC_HEAD_ID AS DEDUCTIONID\r\n" +
            "  FROM (SELECT T1.*, T2.SAC_HEAD_ID, T2.BCH_CHARGES_AMT, T2.ACT_AMT\r\n" +
            "          FROM TB_AC_BILL_MAS_HIST T1, TB_AC_BILL_EXP_DETAIL_HIST T2\r\n" +
            "         WHERE T1.BM_ID = T2.BM_ID\r\n" +
            "           AND T1.ORGID =:orgId\r\n" +
            "           AND T1.BM_ENTRYDATE BETWEEN :fromDate AND :toDate) P\r\n" +
            "  LEFT JOIN TB_AC_BILL_DEDUCTION_DET_HIST Q\r\n" +
            "    ON P.BM_ID = Q.BM_ID\r\n" +
            " WHERE P.BM_ID IN\r\n" +
            "       (SELECT P.BM_ID\r\n" +
            "          FROM (SELECT T1.*, T2.SAC_HEAD_ID, T2.BCH_CHARGES_AMT, T2.ACT_AMT\r\n" +
            "                  FROM TB_AC_BILL_MAS_HIST T1, TB_AC_BILL_EXP_DETAIL_HIST T2\r\n" +
            "                 WHERE T1.BM_ID = T2.BM_ID\r\n" +
            "                   AND T1.ORGID =:orgId\r\n" +
            "                   AND T1.BM_ENTRYDATE BETWEEN :fromDate AND\r\n" +
            "                       :toDate) P\r\n" +
            "          LEFT JOIN TB_AC_BILL_DEDUCTION_DET_HIST Q\r\n" +
            "            ON P.BM_ID = Q.BM_ID\r\n" +
            "         GROUP BY P.BM_ID\r\n" +
            "        HAVING COUNT(1) > 1)\r\n" +
            " ORDER BY BM_BILLNO, H_STATUS", nativeQuery = true)
    List<Object[]> queryForHistDetails(@Param("orgId") long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC \r\n" +
            "          AS ACCOUNTHEAD,\r\n" +
            "       R.PAC_HEAD_COMPO_CODE AS ACCODE,\r\n" +
            "       SUM(P.OPENBAL_AMT_DR) AS OPNDR,\r\n" +
            "       SUM(P.OPENBAL_AMT_CR) AS OPNCR,\r\n" +
            "       SUM(P.VAMT_DR) AS TRNDR,\r\n" +
            "       SUM(P.VAMT_CR) AS TRNCR\r\n" +
            "  FROM (SELECT C.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "               C.PAC_HEAD_COMPO_CODE,\r\n" +
            "               C.PAC_HEAD_DESC,\r\n" +
            "               D.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR,\r\n" +
            "               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" +
            "               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       A.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                               SAC_HEAD_ID,\r\n" +
            "                               OPENBAL_AMT_DR,\r\n" +
            "                               OPENBAL_AMT_CR,\r\n" +
            "                               VAMT_CR,\r\n" +
            "                               VAMT_DR,\r\n" +
            "                               A.PAC_HEAD_ID\r\n" +
            "                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                                       VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                       PAC_HEAD_ID\r\n" +
            "                                  FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                                 WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                                   AND :toDate\r\n" +
            "                                       AND VD.ORGID =:orgId\r\n" +
            "                                GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                         VD.SAC_HEAD_ID,\r\n" +
            "                                         PAC_HEAD_ID) A\r\n" +
            "                               LEFT JOIN\r\n" +
            "                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_DR,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_CR,\r\n" +
            "                                       B.PAC_HEAD_ID\r\n" +
            "                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                                       TB_COMPARAM_DET CD,\r\n" +
            "                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                                       AND BG.ORGID =:orgId\r\n" +
            "                                       AND BG.FA_YEARID =:financialYearId\r\n" +
            "                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                                       AND B.ORGID = BG.ORGID) E\r\n" +
            "                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1\r\n" +
            "                        UNION\r\n" +
            "                        SELECT E.AC_HEAD_CODE,\r\n" +
            "                               E.SAC_HEAD_ID,\r\n" +
            "                               OPENBAL_AMT_DR,\r\n" +
            "                               OPENBAL_AMT_CR,\r\n" +
            "                               VAMT_CR,\r\n" +
            "                               VAMT_DR,\r\n" +
            "                               E.PAC_HEAD_ID\r\n" +
            "                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                                       VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" +
            "                                  FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                                 WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                                   AND :toDate\r\n" +
            "                                       AND VD.ORGID =:orgId\r\n" +
            "                                GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" +
            "                               RIGHT JOIN\r\n" +
            "                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_DR,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_CR,\r\n" +
            "                                       B.AC_HEAD_CODE,\r\n" +
            "                                       B.SAC_HEAD_ID,\r\n" +
            "                                       B.PAC_HEAD_ID\r\n" +
            "                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                                       TB_COMPARAM_DET CD,\r\n" +
            "                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                                       AND BG.ORGID =:orgId\r\n" +
            "                                       AND BG.FA_YEARID =:financialYearId\r\n" +
            "                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                                       AND B.ORGID = BG.ORGID) E\r\n" +
            "                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                 WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" +
            "               AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "        GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID) P,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            " WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> findBalanceSheetMajorHeadReport(@Param("orgId") long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "       R.PAC_HEAD_DESC,\r\n" +
            "       R.PAC_HEAD_COMPO_CODE AS ACCODE,\r\n" +
            "       SUM(P.OPENBAL_AMT_DR) AS OPNDR,\r\n" +
            "       SUM(P.OPENBAL_AMT_CR) AS OPNCR,\r\n" +
            "       SUM(P.VAMT_DR) AS TRNDR,\r\n" +
            "       SUM(P.VAMT_CR) AS TRNCR\r\n" +
            "  FROM (SELECT C.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "               C.PAC_HEAD_COMPO_CODE,\r\n" +
            "               C.PAC_HEAD_DESC,\r\n" +
            "               D.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR,\r\n" +
            "               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" +
            "               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       A.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                               SAC_HEAD_ID,\r\n" +
            "                               OPENBAL_AMT_DR,\r\n" +
            "                               OPENBAL_AMT_CR,\r\n" +
            "                               VAMT_CR,\r\n" +
            "                               VAMT_DR,\r\n" +
            "                               A.PAC_HEAD_ID\r\n" +
            "                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                                       VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                       PAC_HEAD_ID\r\n" +
            "                                  FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                                 WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                                   AND :toDate\r\n" +
            "                                       AND VD.ORGID = :orgId\r\n" +
            "                                GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                         VD.SAC_HEAD_ID,\r\n" +
            "                                         PAC_HEAD_ID) A\r\n" +
            "                               LEFT JOIN\r\n" +
            "                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_DR,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_CR,\r\n" +
            "                                       B.PAC_HEAD_ID\r\n" +
            "                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                                       TB_COMPARAM_DET CD,\r\n" +
            "                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                                       AND BG.ORGID = :orgId\r\n" +
            "                                       AND BG.FA_YEARID = :financialYearId\r\n" +
            "                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                                       AND B.ORGID = BG.ORGID) E\r\n" +
            "                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1\r\n" +
            "                        UNION\r\n" +
            "                        SELECT E.AC_HEAD_CODE,\r\n" +
            "                               E.SAC_HEAD_ID,\r\n" +
            "                               OPENBAL_AMT_DR,\r\n" +
            "                               OPENBAL_AMT_CR,\r\n" +
            "                               VAMT_CR,\r\n" +
            "                               VAMT_DR,\r\n" +
            "                               E.PAC_HEAD_ID\r\n" +
            "                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                                       VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" +
            "                                  FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                                 WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                                   AND :toDate\r\n" +
            "                                       AND VD.ORGID = :orgId\r\n" +
            "                                GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" +
            "                               RIGHT JOIN\r\n" +
            "                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_DR,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_CR,\r\n" +
            "                                       B.AC_HEAD_CODE,\r\n" +
            "                                       B.SAC_HEAD_ID,\r\n" +
            "                                       B.PAC_HEAD_ID\r\n" +
            "                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                                       TB_COMPARAM_DET CD,\r\n" +
            "                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                                       AND BG.ORGID = :orgId\r\n" +
            "                                       AND BG.FA_YEARID = :financialYearId\r\n" +
            "                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                                       AND B.ORGID = BG.ORGID) E\r\n" +
            "                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                 WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" +
            "               AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "        GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID) P,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> findBalanceSheetMinorHeadReport(@Param("orgId") long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId);
    
    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC \r\n" + 
    		"          AS ACCOUNTHEAD,\r\n" + 
    		"       R.PAC_HEAD_COMPO_CODE AS ACCODE,\r\n" + 
    		"       SUM(P.OPENBAL_AMT_DR) AS OPNDR,\r\n" + 
    		"       SUM(P.OPENBAL_AMT_CR) AS OPNCR,\r\n" + 
    		"       SUM(P.VAMT_DR) AS TRNDR,\r\n" + 
    		"       SUM(P.VAMT_CR) AS TRNCR\r\n" + 
    		"  FROM (SELECT C.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               D.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               E.PAC_HEAD_ID AS LEVEL3,\r\n" + 
    		"               SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n" + 
    		"               SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR,\r\n" + 
    		"               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + 
    		"               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       OPENBAL_AMT_DR,\r\n" + 
    		"                       OPENBAL_AMT_CR,\r\n" + 
    		"                       VAMT_CR,\r\n" + 
    		"                       VAMT_DR,\r\n" + 
    		"                       A.PAC_HEAD_ID\r\n" + 
    		"                  FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                               SAC_HEAD_ID,\r\n" + 
    		"                               OPENBAL_AMT_DR,\r\n" + 
    		"                               OPENBAL_AMT_CR,\r\n" + 
    		"                               VAMT_CR,\r\n" + 
    		"                               VAMT_DR,\r\n" + 
    		"                               A.PAC_HEAD_ID\r\n" + 
    		"                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                                       VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                                       PAC_HEAD_ID\r\n" + 
    		"                                  FROM VW_VOUCHER_DETAIL VD\r\n" + 
    		"                                 WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" + 
    		"                                                                   AND :toDate\r\n" + 
    		"                                       AND VD.ORGID =:orgId\r\n" + 
    		"                                GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                         VD.SAC_HEAD_ID,\r\n" + 
    		"                                         PAC_HEAD_ID) A\r\n" + 
    		"                               LEFT JOIN\r\n" + 
    		"                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" + 
    		"                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" + 
    		"                                       (CASE\r\n" + 
    		"                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" + 
    		"                                           THEN\r\n" + 
    		"                                              BG.OPENBAL_AMT\r\n" + 
    		"                                           ELSE\r\n" + 
    		"                                              0\r\n" + 
    		"                                        END)\r\n" + 
    		"                                          AS OPENBAL_AMT_DR,\r\n" + 
    		"                                       (CASE\r\n" + 
    		"                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" + 
    		"                                           THEN\r\n" + 
    		"                                              BG.OPENBAL_AMT\r\n" + 
    		"                                           ELSE\r\n" + 
    		"                                              0\r\n" + 
    		"                                        END)\r\n" + 
    		"                                          AS OPENBAL_AMT_CR,\r\n" + 
    		"                                       B.PAC_HEAD_ID\r\n" + 
    		"                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" + 
    		"                                       TB_COMPARAM_DET CD,\r\n" + 
    		"                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" + 
    		"                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" + 
    		"                                       AND BG.ORGID =:orgId\r\n" + 
    		"                                       AND BG.FA_YEARID =:financialYearId\r\n" + 
    		"                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" + 
    		"                                       AND B.ORGID = BG.ORGID) E\r\n" + 
    		"                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1\r\n" + 
    		"                        UNION\r\n" + 
    		"                        SELECT E.AC_HEAD_CODE,\r\n" + 
    		"                               E.SAC_HEAD_ID,\r\n" + 
    		"                               OPENBAL_AMT_DR,\r\n" + 
    		"                               OPENBAL_AMT_CR,\r\n" + 
    		"                               VAMT_CR,\r\n" + 
    		"                               VAMT_DR,\r\n" + 
    		"                               E.PAC_HEAD_ID\r\n" + 
    		"                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                                       VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" + 
    		"                                  FROM VW_VOUCHER_DETAIL VD\r\n" + 
    		"                                 WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" + 
    		"                                                                   AND :toDate\r\n" + 
    		"                                       AND VD.ORGID =:orgId\r\n" + 
    		"                                GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" + 
    		"                               RIGHT JOIN\r\n" + 
    		"                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" + 
    		"                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" + 
    		"                                       (CASE\r\n" + 
    		"                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" + 
    		"                                           THEN\r\n" + 
    		"                                              BG.OPENBAL_AMT\r\n" + 
    		"                                           ELSE\r\n" + 
    		"                                              0\r\n" + 
    		"                                        END)\r\n" + 
    		"                                          AS OPENBAL_AMT_DR,\r\n" + 
    		"                                       (CASE\r\n" + 
    		"                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" + 
    		"                                           THEN\r\n" + 
    		"                                              BG.OPENBAL_AMT\r\n" + 
    		"                                           ELSE\r\n" + 
    		"                                              0\r\n" + 
    		"                                        END)\r\n" + 
    		"                                          AS OPENBAL_AMT_CR,\r\n" + 
    		"                                       B.AC_HEAD_CODE,\r\n" + 
    		"                                       B.SAC_HEAD_ID,\r\n" + 
    		"                                       B.PAC_HEAD_ID\r\n" + 
    		"                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" + 
    		"                                       TB_COMPARAM_DET CD,\r\n" + 
    		"                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" + 
    		"                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" + 
    		"                                       AND BG.ORGID =:orgId\r\n" + 
    		"                                       AND BG.FA_YEARID =:financialYearId\r\n" + 
    		"                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" + 
    		"                                       AND B.ORGID = BG.ORGID) E\r\n" + 
    		"                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" + 
    		"                       TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"                 WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER C,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER D,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER E\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + 
    		"               AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" + 
    		"               AND D.PAC_HEAD_PARENT_ID = E.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID, E.PAC_HEAD_ID) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL3 = R.PAC_HEAD_ID\r\n" + 
    		" GROUP BY R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" + 
    		" ORDER BY R.PAC_HEAD_COMPO_CODE ASC",nativeQuery=true)
    List<Object[]> findBalanceSheetActualMajorHeadReport(@Param("orgId") long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId);
    
    
    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"       R.PAC_HEAD_DESC AS ACCOUNTHEAD,\r\n" + 
    		"       R.PAC_HEAD_COMPO_CODE AS ACCODE,\r\n" + 
    		"       SUM(P.OPENBAL_AMT_DR) AS OPNDR,\r\n" + 
    		"       SUM(P.OPENBAL_AMT_CR) AS OPNCR\r\n" + 
    		"  FROM (SELECT C.PAC_HEAD_ID AS LEVEL3,\r\n" + 
    		"               D.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               E.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n" + 
    		"               SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       OPENBAL_AMT_DR,\r\n" + 
    		"                       OPENBAL_AMT_CR,\r\n" + 
    		"                       A.PAC_HEAD_ID\r\n" + 
    		"                  FROM (SELECT BG.SAC_HEAD_ID,\r\n" + 
    		"                               B.AC_HEAD_CODE,\r\n" + 
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
    		"                               AND BG.FA_YEARID =:financialYearId\r\n" + 
    		"                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" + 
    		"                               AND B.ORGID = BG.ORGID) A,\r\n" + 
    		"                       TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"                 WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER C,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER D,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER E\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + 
    		"               AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" + 
    		"               AND D.PAC_HEAD_PARENT_ID = E.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID, E.PAC_HEAD_ID) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" + 
    		" GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" + 
    		" ORDER BY R.PAC_HEAD_COMPO_CODE ASC",nativeQuery=true)
    List<Object[]> findBalanceSheetActualMajorHeadPreviousReport(@Param("orgId") long orgId, @Param("financialYearId") Long financialYearId);

    @Query(value = "SELECT PM.PAC_HEAD_COMPO_CODE,\r\n" +
            "       CONCAT((CONCAT(PM.PAC_HEAD_COMPO_CODE, ' - ')), PM.PAC_HEAD_DESC)\r\n" +
            "  FROM TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE PM.ORGID = :orgId AND PM.CPD_ID_ACHEADTYPES IN\r\n" +
            "       (SELECT D.CPD_ID\r\n" +
            "          FROM TB_COMPARAM_DET D\r\n" +
            "         WHERE D.CPD_VALUE = 'L'\r\n" +
            "           AND D.CPM_ID IN (SELECT M.CPM_ID\r\n" +
            "                              FROM TB_COMPARAM_MAS M\r\n" +
            "                             WHERE M.CPM_PREFIX = 'COA'))", nativeQuery = true)
    List<Object[]> findliabilityMajorHead(@Param("orgId") Long orgId);

    @Query(value = "SELECT PM.PAC_HEAD_COMPO_CODE,\r\n" +
            "       CONCAT((CONCAT(PM.PAC_HEAD_COMPO_CODE, ' - ')), PM.PAC_HEAD_DESC)\r\n" +
            "  FROM TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE PM.ORGID = :orgId AND PM.CPD_ID_ACHEADTYPES IN\r\n" +
            "       (SELECT D.CPD_ID\r\n" +
            "          FROM TB_COMPARAM_DET D\r\n" +
            "         WHERE D.CPD_VALUE = 'A'\r\n" +
            "           AND D.CPM_ID IN (SELECT M.CPM_ID\r\n" +
            "                              FROM TB_COMPARAM_MAS M\r\n" +
            "                             WHERE M.CPM_PREFIX = 'COA'))", nativeQuery = true)
    List<Object[]> findAssetMajorHead(@Param("orgId") Long orgId);

    @Query(value = "SELECT ORGID,\r\n" +
            "       ORGANIZATION_NAME CB_NAME,\r\n" +
            "       SUM(VENDOR_MASTER) VENDORS,\r\n" +
            "       SUM(SECONDARY_MASTER) ACCOUNTHEADS,\r\n" +
            "       SUM(TB_AC_DEPOSITS) DEPOSITS,\r\n" +
            "       SUM(TB_RECEIPT_MAS) RECEIPTS,\r\n" +
            "       SUM(BANK_DEPOSITSLIP_MASTER) DEPOSITSLIPS,\r\n" +
            "       SUM(TB_AC_BILL_MAS) BILLS,\r\n" +
            "       SUM(TB_AC_PAYMENT_MAS) PAYMENTS,\r\n" +
            "       SUM(VOUCHER_MASTER) VOUCHERS\r\n" +
            "  FROM (SELECT A.ORGID,\r\n" +
            "               B.O_NLS_ORGNAME ORGANIZATION_NAME,\r\n" +
            "               COUNT(A.ORGID) VENDOR_MASTER,\r\n" +
            "               0 CHEQUE_BOOK_MAS,\r\n" +
            "               0 TB_AC_DEPOSITS,\r\n" +
            "               0 SECONDARY_MASTER,\r\n" +
            "               0 TB_AC_BUDGETCODE_MAS,\r\n" +
            "               0 PROJECTED_REVENUE,\r\n" +
            "               0 PROJECTED_EXPENDITURE,\r\n" +
            "               0 TB_AC_BILL_MAS,\r\n" +
            "               0 TB_AC_BILL_DEDUCTION_DETAIL,\r\n" +
            "               0 TB_AC_PAYMENT_MAS,\r\n" +
            "               0 TB_RECEIPT_MAS,\r\n" +
            "               0 BANK_DEPOSITSLIP_MASTER,\r\n" +
            "               0 BANK_DEPOSIT_SLIP,\r\n" +
            "               0 VOUCHER_MASTER\r\n" +
            "          FROM TB_VENDORMASTER A, TB_ORGANISATION B\r\n" +
            "         WHERE A.ORGID = B.ORGID\r\n" +
            "         GROUP BY A.ORGID, B.O_NLS_ORGNAME\r\n" +
            "        UNION ALL\r\n" +
            "        SELECT A.ORGID,\r\n" +
            "               B.O_NLS_ORGNAME ORGANIZATION_NAME,\r\n" +
            "               0 VENDOR_MASTER,\r\n" +
            "               0 CHEQUE_BOOK_MAS,\r\n" +
            "               COUNT(A.ORGID) TB_AC_DEPOSITS,\r\n" +
            "               0 SECONDARY_MASTER,\r\n" +
            "               0 TB_AC_BUDGETCODE_MAS,\r\n" +
            "               0 PROJECTED_REVENUE,\r\n" +
            "               0 PROJECTED_EXPENDITURE,\r\n" +
            "               0 TB_AC_BILL_MAS,\r\n" +
            "               0 TB_AC_BILL_DEDUCTION_DETAIL,\r\n" +
            "               0 TB_AC_PAYMENT_MAS,\r\n" +
            "               0 TB_RECEIPT_MAS,\r\n" +
            "               0 BANK_DEPOSITSLIP_MASTER,\r\n" +
            "               0 BANK_DEPOSIT_SLIP,\r\n" +
            "               0 VOUCHER_MASTER\r\n" +
            "          FROM TB_AC_DEPOSITS A, TB_ORGANISATION B\r\n" +
            "         WHERE A.ORGID = B.ORGID\r\n" +
            "         GROUP BY A.ORGID, B.O_NLS_ORGNAME\r\n" +
            "        UNION ALL\r\n" +
            "        SELECT A.ORGID,\r\n" +
            "               B.O_NLS_ORGNAME ORGANIZATION_NAME,\r\n" +
            "               0 VENDOR_MASTER,\r\n" +
            "               0 CHEQUE_BOOK_MAS,\r\n" +
            "               0 TB_AC_DEPOSITS,\r\n" +
            "               COUNT(A.ORGID) SECONDARY_MASTER,\r\n" +
            "               0 TB_AC_BUDGETCODE_MAS,\r\n" +
            "               0 PROJECTED_REVENUE,\r\n" +
            "               0 PROJECTED_EXPENDITURE,\r\n" +
            "               0 TB_AC_BILL_MAS,\r\n" +
            "               0 TB_AC_BILL_DEDUCTION_DETAIL,\r\n" +
            "               0 TB_AC_PAYMENT_MAS,\r\n" +
            "               0 TB_RECEIPT_MAS,\r\n" +
            "               0 BANK_DEPOSITSLIP_MASTER,\r\n" +
            "               0 BANK_DEPOSIT_SLIP,\r\n" +
            "               0 VOUCHER_MASTER\r\n" +
            "          FROM TB_AC_SECONDARYHEAD_MASTER A, TB_ORGANISATION B\r\n" +
            "         WHERE A.ORGID = B.ORGID\r\n" +
            "         GROUP BY A.ORGID, B.O_NLS_ORGNAME\r\n" +
            "        UNION ALL\r\n" +
            "        SELECT A.ORGID,\r\n" +
            "               B.O_NLS_ORGNAME ORGANIZATION_NAME,\r\n" +
            "               0 VENDOR_MASTER,\r\n" +
            "               0 CHEQUE_BOOK_MAS,\r\n" +
            "               0 TB_AC_DEPOSITS,\r\n" +
            "               0 SECONDARY_MASTER,\r\n" +
            "               0 TB_AC_BUDGETCODE_MAS,\r\n" +
            "               0 PROJECTED_REVENUE,\r\n" +
            "               0 PROJECTED_EXPENDITURE,\r\n" +
            "               COUNT(A.ORGID) TB_AC_BILL_MAS,\r\n" +
            "               0 TB_AC_BILL_DEDUCTION_DETAIL,\r\n" +
            "               0 TB_AC_PAYMENT_MAS,\r\n" +
            "               0 TB_RECEIPT_MAS,\r\n" +
            "               0 BANK_DEPOSITSLIP_MASTER,\r\n" +
            "               0 BANK_DEPOSIT_SLIP,\r\n" +
            "               0 VOUCHER_MASTER\r\n" +
            "          FROM TB_AC_BILL_MAS A, TB_ORGANISATION B\r\n" +
            "         WHERE A.ORGID = B.ORGID\r\n" +
            "         GROUP BY A.ORGID, B.O_NLS_ORGNAME\r\n" +
            "        UNION ALL\r\n" +
            "        SELECT A.ORGID,\r\n" +
            "               B.O_NLS_ORGNAME ORGANIZATION_NAME,\r\n" +
            "               0 VENDOR_MASTER,\r\n" +
            "               0 CHEQUE_BOOK_MAS,\r\n" +
            "               0 TB_AC_DEPOSITS,\r\n" +
            "               0 SECONDARY_MASTER,\r\n" +
            "               0 TB_AC_BUDGETCODE_MAS,\r\n" +
            "               0 PROJECTED_REVENUE,\r\n" +
            "               0 PROJECTED_EXPENDITURE,\r\n" +
            "               0 TB_AC_BILL_MAS,\r\n" +
            "               0 TB_AC_BILL_DEDUCTION_DETAIL,\r\n" +
            "               COUNT(A.ORGID) TB_AC_PAYMENT_MAS,\r\n" +
            "               0 TB_RECEIPT_MAS,\r\n" +
            "               0 BANK_DEPOSITSLIP_MASTER,\r\n" +
            "               0 BANK_DEPOSIT_SLIP,\r\n" +
            "               0 VOUCHER_MASTER\r\n" +
            "          FROM TB_AC_PAYMENT_MAS A, TB_ORGANISATION B\r\n" +
            "         WHERE A.ORGID = B.ORGID\r\n" +
            "         GROUP BY A.ORGID, B.O_NLS_ORGNAME\r\n" +
            "        UNION ALL\r\n" +
            "        SELECT A.ORGID,\r\n" +
            "               B.O_NLS_ORGNAME ORGANIZATION_NAME,\r\n" +
            "               0 VENDOR_MASTER,\r\n" +
            "               0 CHEQUE_BOOK_MAS,\r\n" +
            "               0 TB_AC_DEPOSITS,\r\n" +
            "               0 SECONDARY_MASTER,\r\n" +
            "               0 TB_AC_BUDGETCODE_MAS,\r\n" +
            "               0 PROJECTED_REVENUE,\r\n" +
            "               0 PROJECTED_EXPENDITURE,\r\n" +
            "               0 TB_AC_BILL_MAS,\r\n" +
            "               0 TB_AC_BILL_DEDUCTION_DETAIL,\r\n" +
            "               0 TB_AC_PAYMENT_MAS,\r\n" +
            "               COUNT(A.ORGID) TB_RECEIPT_MAS,\r\n" +
            "               0 BANK_DEPOSITSLIP_MASTER,\r\n" +
            "               0 BANK_DEPOSIT_SLIP,\r\n" +
            "               0 VOUCHER_MASTER\r\n" +
            "          FROM TB_RECEIPT_MAS A, TB_ORGANISATION B\r\n" +
            "         WHERE A.ORGID = B.ORGID\r\n" +
            "         GROUP BY A.ORGID, B.O_NLS_ORGNAME\r\n" +
            "        UNION ALL\r\n" +
            "        SELECT A.ORGID,\r\n" +
            "               B.O_NLS_ORGNAME ORGANIZATION_NAME,\r\n" +
            "               0 VENDOR_MASTER,\r\n" +
            "               0 CHEQUE_BOOK_MAS,\r\n" +
            "               0 TB_AC_DEPOSITS,\r\n" +
            "               0 SECONDARY_MASTER,\r\n" +
            "               0 TB_AC_BUDGETCODE_MAS,\r\n" +
            "               0 PROJECTED_REVENUE,\r\n" +
            "               0 PROJECTED_EXPENDITURE,\r\n" +
            "               0 TB_AC_BILL_MAS,\r\n" +
            "               0 TB_AC_BILL_DEDUCTION_DETAIL,\r\n" +
            "               0 TB_AC_PAYMENT_MAS,\r\n" +
            "               0 TB_RECEIPT_MAS,\r\n" +
            "               COUNT(A.ORGID) BANK_DEPOSITSLIP_MASTER,\r\n" +
            "               0 BANK_DEPOSIT_SLIP,\r\n" +
            "               0 VOUCHER_MASTER\r\n" +
            "          FROM TB_AC_BANK_DEPOSITSLIP_MASTER A, TB_ORGANISATION B\r\n" +
            "         WHERE A.ORGID = B.ORGID\r\n" +
            "         GROUP BY A.ORGID, B.O_NLS_ORGNAME\r\n" +
            "        UNION ALL\r\n" +
            "        SELECT A.ORGID,\r\n" +
            "               B.O_NLS_ORGNAME ORGANIZATION_NAME,\r\n" +
            "               0 VENDOR_MASTER,\r\n" +
            "               0 CHEQUE_BOOK_MAS,\r\n" +
            "               0 TB_AC_DEPOSITS,\r\n" +
            "               0 SECONDARY_MASTER,\r\n" +
            "               0 TB_AC_BUDGETCODE_MAS,\r\n" +
            "               0 PROJECTED_REVENUE,\r\n" +
            "               0 PROJECTED_EXPENDITURE,\r\n" +
            "               0 TB_AC_BILL_MAS,\r\n" +
            "               0 TB_AC_BILL_DEDUCTION_DETAIL,\r\n" +
            "               0 TB_AC_PAYMENT_MAS,\r\n" +
            "               0 TB_RECEIPT_MAS,\r\n" +
            "               0 BANK_DEPOSITSLIP_MASTER,\r\n" +
            "               0 BANK_DEPOSIT_SLIP,\r\n" +
            "               COUNT(A.ORGID) VOUCHER_MASTER\r\n" +
            "          FROM TB_AC_VOUCHER A, TB_ORGANISATION B\r\n" +
            "         WHERE A.ORGID = B.ORGID\r\n" +
            "         GROUP BY A.ORGID, B.O_NLS_ORGNAME)\r\n" +
            " GROUP BY ORGID, ORGANIZATION_NAME\r\n" +
            " ORDER BY ORGID, ORGANIZATION_NAME\r\n", nativeQuery = true)
    List<Object[]> findAccountDashBoardAllRecords();

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "       R.PAC_HEAD_DESC AS ACCOUNTHEAD,\r\n" +
            "       R.PAC_HEAD_COMPO_CODE AS ACCODE,\r\n" +
            "       SUM(P.OPENBAL_AMT_DR) AS OPNDR,\r\n" +
            "       SUM(P.OPENBAL_AMT_CR) AS OPNCR\r\n" +
            "  FROM (SELECT C.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "               C.PAC_HEAD_COMPO_CODE,\r\n" +
            "               C.PAC_HEAD_DESC,\r\n" +
            "               D.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       A.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT BG.SAC_HEAD_ID,\r\n" +
            "                               B.AC_HEAD_CODE,\r\n" +
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
            "                               AND BG.FA_YEARID =:financialYearId\r\n" +
            "                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                               AND B.ORGID = BG.ORGID) A,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                 WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" +
            "               AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "        GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID) P,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            " WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> findBalanceSheetMajorHeadPreviousReport(@Param("orgId") long orgId,
            @Param("financialYearId") Long financialYearId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "       R.PAC_HEAD_DESC AS ACCOUNTHEAD,\r\n" +
            "       R.PAC_HEAD_COMPO_CODE AS ACCODE,\r\n" +
            "       SUM(P.OPENBAL_AMT_DR) AS OPNDR,\r\n" +
            "       SUM(P.OPENBAL_AMT_CR) AS OPNCR\r\n" +
            "  FROM (SELECT C.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "               C.PAC_HEAD_COMPO_CODE,\r\n" +
            "               C.PAC_HEAD_DESC,\r\n" +
            "               D.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       A.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT BG.SAC_HEAD_ID,\r\n" +
            "                               B.AC_HEAD_CODE,\r\n" +
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
            "                               AND BG.FA_YEARID =:financialYearId\r\n" +
            "                               AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                               AND B.ORGID = BG.ORGID) A,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                 WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" +
            "               AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "        GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID) P,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> findBalanceSheetMinorHeadPreviousReport(@Param("orgId") long orgId,
            @Param("financialYearId") Long financialYearId);

    @Query(value = "SELECT SUBSTR(AC_HEAD_CODE, 1, 20) AC_HEAD_CODE,\r\n" +
            "       SUBSTR(AC_HEAD_CODE, 23, LENGTH(AC_HEAD_CODE)) AC_HEAD_DESC,\r\n" +
            "       SAC_HEAD_ID,\r\n" +
            "       OPENBAL_AMT_DR,\r\n" +
            "       OPENBAL_AMT_CR\r\n" +
            "  FROM (SELECT A.AC_HEAD_CODE,\r\n" +
            "               SAC_HEAD_ID,\r\n" +
            "               (CASE WHEN A.CPD_VALUE IN ('DR') THEN OPENBAL_AMT ELSE 0 END)\r\n" +
            "                  AS OPENBAL_AMT_DR,\r\n" +
            "               (CASE WHEN A.CPD_VALUE IN ('CR') THEN OPENBAL_AMT ELSE 0 END)\r\n" +
            "                  AS OPENBAL_AMT_CR,\r\n" +
            "               A.PAC_HEAD_ID,\r\n" +
            "               A.FUNCTION_ID,\r\n" +
            "               B.PAC_HEAD_COMPO_CODE,\r\n" +
            "               C.FUNCTION_COMPCODE\r\n" +
            "          FROM (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                       B.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                       BG.CPD_ID_DRCR,\r\n" +
            "                       BG.OPENBAL_AMT,\r\n" +
            "                       B.PAC_HEAD_ID,\r\n" +
            "                       b.FUNCTION_ID,\r\n" +
            "                       CPD_VALUE\r\n" +
            "                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                       TB_COMPARAM_DET CD,\r\n" +
            "                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                       AND BG.ORGID = :orgId\r\n" +
            "                       AND BG.FA_YEARID  =:financialYearId\r\n" +
            "                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                       AND B.ORGID = BG.ORGID) A,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER B,\r\n" +
            "               TB_AC_FUNCTION_MASTER C\r\n" +
            "         WHERE     A.PAC_HEAD_ID = B.PAC_HEAD_ID\r\n" +
            "               AND A.FUNCTION_ID = C.FUNCTION_ID) z\r\n" +
            "ORDER BY PAC_HEAD_COMPO_CODE, FUNCTION_COMPCODE ASC", nativeQuery = true)
    List<Object[]> findBalanceSheetPreviousReport(@Param("orgId") long orgId, @Param("financialYearId") Long financialYearId);

    @Query(value = "SELECT COALESCE(SUM(COALESCE(P.OPENBAL_AMT_DR, 0)),0) AS OPENBAL_TOTAL_DR,\r\n" +
            "       COALESCE(SUM(COALESCE(P.OPENBAL_AMT_CR, 0)),0) AS OPENBAL_TOTAL_CR,\r\n" +
            "       COALESCE(SUM(COALESCE(P.VAMT_DR, 0)),0) AS TRN_TOTAL_DR,\r\n" +
            "       COALESCE(SUM(COALESCE(P.VAMT_CR, 0)),0)AS TRN_TOTAL_CR\r\n" +
            "  FROM (SELECT C.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "               C.PAC_HEAD_COMPO_CODE PAC_HEAD_COMPO_CODE,\r\n" +
            "               D.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n" +
            "               SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR,\r\n" +
            "               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" +
            "               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" +
            "          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                       SAC_HEAD_ID,\r\n" +
            "                       OPENBAL_AMT_DR,\r\n" +
            "                       OPENBAL_AMT_CR,\r\n" +
            "                       VAMT_CR,\r\n" +
            "                       VAMT_DR,\r\n" +
            "                       A.PAC_HEAD_ID\r\n" +
            "                  FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                               SAC_HEAD_ID,\r\n" +
            "                               OPENBAL_AMT_DR,\r\n" +
            "                               OPENBAL_AMT_CR,\r\n" +
            "                               VAMT_CR,\r\n" +
            "                               VAMT_DR,\r\n" +
            "                               A.PAC_HEAD_ID\r\n" +
            "                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                                       VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                       PAC_HEAD_ID\r\n" +
            "                                  FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                                 WHERE     VD.VOU_POSTING_DATE BETWEEN :financialYFromDate\r\n" +
            "                                                                   AND :toDates\r\n" +
            "                                       AND VD.ORGID = :orgId\r\n" +
            "                                GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                         VD.SAC_HEAD_ID,\r\n" +
            "                                         PAC_HEAD_ID) A\r\n" +
            "                               LEFT JOIN\r\n" +
            "                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_DR,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_CR,\r\n" +
            "                                       B.PAC_HEAD_ID\r\n" +
            "                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                                       TB_COMPARAM_DET CD,\r\n" +
            "                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                                       AND BG.ORGID = :orgId\r\n" +
            "                                       AND BG.FA_YEARID = :financialYearId\r\n" +
            "                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                                       AND B.ORGID = BG.ORGID) E\r\n" +
            "                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1\r\n" +
            "                        UNION\r\n" +
            "                        SELECT E.AC_HEAD_CODE,\r\n" +
            "                               E.SAC_HEAD_ID,\r\n" +
            "                               OPENBAL_AMT_DR,\r\n" +
            "                               OPENBAL_AMT_CR,\r\n" +
            "                               VAMT_CR,\r\n" +
            "                               VAMT_DR,\r\n" +
            "                               E.PAC_HEAD_ID\r\n" +
            "                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" +
            "                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" +
            "                                       VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" +
            "                                  FROM VW_VOUCHER_DETAIL VD\r\n" +
            "                                 WHERE     VD.VOU_POSTING_DATE BETWEEN :financialYFromDate\r\n" +
            "                                                                   AND :toDates\r\n" +
            "                                       AND VD.ORGID = :orgId\r\n" +
            "                                GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" +
            "                               RIGHT JOIN\r\n" +
            "                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" +
            "                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_DR,\r\n" +
            "                                       (CASE\r\n" +
            "                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" +
            "                                           THEN\r\n" +
            "                                              BG.OPENBAL_AMT\r\n" +
            "                                           ELSE\r\n" +
            "                                              0\r\n" +
            "                                        END)\r\n" +
            "                                          AS OPENBAL_AMT_CR,\r\n" +
            "                                       B.AC_HEAD_CODE,\r\n" +
            "                                       B.SAC_HEAD_ID,\r\n" +
            "                                       B.PAC_HEAD_ID\r\n" +
            "                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" +
            "                                       TB_COMPARAM_DET CD,\r\n" +
            "                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" +
            "                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" +
            "                                       AND BG.ORGID = :orgId\r\n" +
            "                                       AND BG.FA_YEARID = :financialYearId\r\n" +
            "                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" +
            "                                       AND B.ORGID = BG.ORGID) E\r\n" +
            "                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                 WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" +
            "               AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" +
            "        GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID) P,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            " WHERE P.LEVEL1 = R.PAC_HEAD_ID AND R.PAC_HEAD_COMPO_CODE in (:accountHeadCode)", nativeQuery = true)
    List<Object[]> getCashFlowReport(@Param("orgId") long orgId, @Param("financialYFromDate") Date financialYFromDate,
            @Param("toDates") Date toDates, @Param("accountHeadCode") String accountHeadCode,
            @Param("financialYearId") Long financialYearId);

    // Receipt
    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('RRE',\r\n" +
            "                                                                'RV',\r\n" +
            "                                                                'RDC',\r\n" +
            "                                                                'ASE'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES =:coaReceiptLookupId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideOpeningReportDataL2(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('RRE',\r\n" +
            "                                                                'RV',\r\n" +
            "                                                                'RDC',\r\n" +
            "                                                                'ASE'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES <> :coaReceiptLookupId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningReportDataL2(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);
    // Payment

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                               (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('PVE','RBP'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES =:coaPaymentLookupId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideOpeningReportDataL2(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId, @Param("coaPaymentLookupId") Long coaPaymentLookupId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                               (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('PVE', 'RBP'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                           AND :toDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES <>:coaPaymentLookupId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReportDataL2(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId,
            @Param("coaPaymentLookupId") Long coaPaymentLookupId);

    // MinorReceipt

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('RRE',\r\n" +
            "                                                                'RV',\r\n" +
            "                                                                'RDC',\r\n" +
            "                                                                'ASE'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :frmDate\r\n" +
            "                                                           AND :tDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES =:coaReceiptLookupId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideOpeningReportDataL1(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId, @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('RRE',\r\n" +
            "                                                                'RV',\r\n" +
            "                                                                'RDC',\r\n" +
            "                                                                'ASE'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :frmDate\r\n" +
            "                                                           AND :tDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES <> :coaReceiptLookupId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningReportDataL1(@Param("frmDate") Date frmDate,
            @Param("tDate") Date tDate, @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                               (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('PVE','RBP'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :frmDate\r\n" +
            "                                                           AND :tDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES =:coaPaymentLookupId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC;", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideOpeningReportDataL1(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId,
            @Param("coaPaymentLookupId") Long coaPaymentLookupId);

    @Query(value = "SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT)\r\n" +
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
            "                               (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" +
            "                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                      (SELECT CPD_ID\r\n" +
            "                                         FROM TB_COMPARAM_DET D\r\n" +
            "                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                     (SELECT CPM_ID\r\n" +
            "                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                             M\r\n" +
            "                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                'TDP')\r\n" +
            "                                              AND CPD_VALUE IN ('PVE', 'RBP'))\r\n" +
            "                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
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
            "                               AND VD.VOU_POSTING_DATE BETWEEN :frmDate\r\n" +
            "                                                           AND :tDate\r\n" +
            "                               AND VD.ORGID =:orgId\r\n" +
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
            " WHERE P.LEVEL2 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES <>:coaPaymentLookupId\r\n" +
            "GROUP BY LEVEL2, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE\r\n" +
            "ORDER BY R.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReportDataL1(@Param("frmDate") Date frmDate,
            @Param("tDate") Date tDate, @Param("orgId") Long orgId,
            @Param("coaPaymentLookupId") Long coaPaymentLookupId);

    // Secondary Head Receipt
    @Query(value = "SELECT VD.PAC_HEAD_ID,\r\n" +
            "       VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "       (SUM(VD.VAMT_CR) - SUM(VD.VAMT_DR)) RCPTAMT\r\n" +
            "  FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SD,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'TDP')\r\n" +
            "                      AND CPD_VALUE IN ('RRE',\r\n" +
            "                                        'RV',\r\n" +
            "                                        'RDC',\r\n" +
            "                                        'ASE'))\r\n" +
            "       AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
            "       AND PM.PAC_HEAD_ID = SD.PAC_HEAD_ID\r\n" +
            "       AND PM.CPD_ID_ACHEADTYPES =:coaReceiptLookupId\r\n" +
            "       AND SD.SAC_LED_TYPE IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'FTY')\r\n" +
            "                      AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
            "       AND VD.VOU_POSTING_DATE BETWEEN :frmDate AND :tDate\r\n" +
            "       AND VD.ORGID =:orgId\r\n" +
            "GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, VD.PAC_HEAD_ID", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideOpeningReport(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT VD.PAC_HEAD_ID,\r\n" +
            "       VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "       (SUM(VD.VAMT_CR) - SUM(VD.VAMT_DR)) RCPTAMT\r\n" +
            "  FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SD,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'TDP')\r\n" +
            "                      AND CPD_VALUE IN ('RRE',\r\n" +
            "                                        'RV',\r\n" +
            "                                        'RDC',\r\n" +
            "                                        'ASE'))\r\n" +
            "       AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
            "       AND PM.PAC_HEAD_ID = SD.PAC_HEAD_ID\r\n" +
            "       AND PM.CPD_ID_ACHEADTYPES <>:coaReceiptLookupId\r\n" +
            "       AND SD.SAC_LED_TYPE IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'FTY')\r\n" +
            "                      AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
            "       AND VD.VOU_POSTING_DATE BETWEEN :frmDate AND :tDate\r\n" +
            "       AND VD.ORGID =:orgId AND PM.PAC_HEAD_COMPO_CODE  in (330,331,340,320,412,420,421,341,350,431) \r\n" +
            "GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, VD.PAC_HEAD_ID", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideNonOpeningReport(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT VD.PAC_HEAD_ID,\r\n" +
            "       VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "       (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) PAYMENT\r\n" +
            "  FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SD,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'TDP')\r\n" +
            "                      AND CPD_VALUE IN ('PVE', 'RBP'))\r\n" +
            "       AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
            "       AND SD.SAC_LED_TYPE IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'FTY')\r\n" +
            "                      AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
            "       AND VD.VOU_POSTING_DATE BETWEEN :frmDate AND :tDate\r\n" +
            "       AND SD.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" +
            "       AND VD.ORGID =:orgId\r\n" +
            "       AND PM.CPD_ID_ACHEADTYPES =:coaReceiptLookupId\r\n" +
            "GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, VD.PAC_HEAD_ID", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideOpeningReport(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT VD.PAC_HEAD_ID,\r\n" +
            "       VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "       (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) PAYMENT\r\n" +
            "  FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SD,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'TDP')\r\n" +
            "                      AND CPD_VALUE IN ('PVE', 'RBP'))\r\n" +
            "       AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
            "       AND SD.SAC_LED_TYPE IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'FTY')\r\n" +
            "                      AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
            "       AND VD.VOU_POSTING_DATE BETWEEN :frmDate AND :tDate\r\n" +
            "       AND SD.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" +
            "       AND VD.ORGID =:orgId\r\n" +
            "       AND PM.CPD_ID_ACHEADTYPES <>:coaReceiptLookupId AND PM.PAC_HEAD_COMPO_CODE  in (350,320,360,340,410,412,341,420,421,460,440)\r\n" +
            "GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, VD.PAC_HEAD_ID", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideNonOpeningReport(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    /*
     * @Query(value ="SELECT DISTINCT VD.SAC_HEAD_ID\r\n" +
     * "  FROM TB_AC_VOUCHERTEMPLATE_MAS VM CROSS JOIN TB_AC_VOUCHERTEMPLATE_DET VD\r\n" +
     * " WHERE     VM.TEMPLATE_ID = VD.TEMPLATE_ID\r\n" + "       AND VM.CPD_ID_TEMPLATE_FOR =\r\n" +
     * "              (SELECT CPD_ID\r\n" + "                 FROM TB_COMPARAM_DET\r\n" +
     * "                WHERE     CPD_VALUE = 'RV'\r\n" + "                      AND CPM_ID IN (SELECT M.CPM_ID\r\n" +
     * "                                       FROM TB_COMPARAM_MAS M\r\n" +
     * "                                      WHERE M.CPM_PREFIX = 'TDP'))\r\n" +
     * "       AND VM.CPD_ID_STATUS_FLG =:cpdIdPayMode\r\n" + "       AND VD.ORGID =:orgId\r\n" +
     * "       AND VD.CPD_ID_PAY_MODE IN\r\n" + "              (SELECT CPD_ID\r\n" + "                 FROM TB_COMPARAM_DET\r\n" +
     * "                WHERE     CPD_VALUE IN ('C', 'Q', 'D')\r\n" + "                      AND CPM_ID IN (SELECT M.CPM_ID\r\n" +
     * "                                       FROM TB_COMPARAM_MAS M\r\n" +
     * "                                      WHERE M.CPM_PREFIX = 'PAY'))\r\n" +
     * "       AND VD.SAC_HEAD_ID IS NOT NULL; ",nativeQuery = true) List<Object[]>
     * findSacHeadIdByCashPayModeId(@Param("cpdIdPayMode") Long cpdIdPayMode, @Param("orgId") Long orgId);
     */

    @Query("SELECT DISTINCT VD.sacHeadId\r\n" +
            "FROM VoucherTemplateMasterEntity VM,\r\n" +
            "VoucherTemplateDetailEntity VD\r\n" +
            "WHERE \r\n" +
            "VM.templateId = VD.templateId.templateId\r\n" +
            "AND VM.templateFor=(SELECT cpdId FROM TbComparamDetEntity WHERE cpdValue= 'RV'\r\n" +
            "AND tbComparamMas.cpmId IN (SELECT M.cpmId FROM TbComparamMasEntity M WHERE M.cpmPrefix = 'TDP'))\r\n" +
            "AND VM.status = (SELECT cpdId FROM TbComparamDetEntity WHERE cpdValue= 'A' \r\n" +
            "AND tbComparamMas.cpmId IN (SELECT M.cpmId FROM TbComparamMasEntity M WHERE M.cpmPrefix = 'ACN'))\r\n" +
            "AND VD.orgid =:orgId\r\n" +
            "AND VD.cpdIdPayMode IN(SELECT cpdId FROM TbComparamDetEntity \r\n" +
            "WHERE cpdValue IN ('C', 'Q', 'D') AND tbComparamMas.cpmId IN (SELECT M.cpmId FROM TbComparamMasEntity M WHERE M.cpmPrefix = 'PAY')) AND VD.sacHeadId IS NOT NULL\r\n"
            +
            "")
    List<Object[]> findSacHeadIdByCashPayModeId(@Param("orgId") Long orgId);

    // Secondary Head Wise

    @Query(value = "SELECT VD.PAC_HEAD_ID,\r\n" +
            "       VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "       (SUM(VD.VAMT_CR) - SUM(VD.VAMT_DR)) RCPTAMT\r\n" +
            "  FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SD,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'TDP')\r\n" +
            "                      AND CPD_VALUE IN ('RRE',\r\n" +
            "                                        'RV',\r\n" +
            "                                        'RDC',\r\n" +
            "                                        'ASE'))\r\n" +
            "       AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
            "       AND PM.PAC_HEAD_ID = SD.PAC_HEAD_ID\r\n" +
            "       AND PM.CPD_ID_ACHEADTYPES =:coaReceiptLookupId\r\n" +
            "       AND SD.SAC_LED_TYPE IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'FTY')\r\n" +
            "                      AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
            "       AND VD.VOU_POSTING_DATE BETWEEN :fromDate AND :toDate\r\n" +
            "       AND VD.ORGID =:orgId\r\n" +
            "GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, VD.PAC_HEAD_ID", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPreviousReceiptSideReportDataL3(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT VD.PAC_HEAD_ID,\r\n" +
            "       VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "       (SUM(VD.VAMT_CR) - SUM(VD.VAMT_DR)) RCPTAMT\r\n" +
            "  FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SD,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'TDP')\r\n" +
            "                      AND CPD_VALUE IN ('RRE',\r\n" +
            "                                        'RV',\r\n" +
            "                                        'RDC',\r\n" +
            "                                        'ASE'))\r\n" +
            "       AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
            "       AND PM.PAC_HEAD_ID = SD.PAC_HEAD_ID\r\n" +
            "       AND PM.CPD_ID_ACHEADTYPES <>:coaReceiptLookupId\r\n" +
            "       AND SD.SAC_LED_TYPE IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'FTY')\r\n" +
            "                      AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
            "       AND VD.VOU_POSTING_DATE BETWEEN :fromDate AND :toDate\r\n" +
            "       AND VD.ORGID =:orgId AND  PM.PAC_HEAD_COMPO_CODE  in (330,331,340,320,412,420,421,341,350,431)\r\n" +
            "GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, VD.PAC_HEAD_ID", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetnonOperatingPreviousReceiptSideReportDataL3(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT VD.PAC_HEAD_ID,\r\n" +
            "       VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "       (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) PAYMENT\r\n" +
            "  FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SD,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'TDP')\r\n" +
            "                      AND CPD_VALUE IN ('PVE', 'RBP'))\r\n" +
            "       AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
            "       AND SD.SAC_LED_TYPE IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'FTY')\r\n" +
            "                      AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
            "       AND VD.VOU_POSTING_DATE BETWEEN :fromDate AND :toDate\r\n" +
            "       AND SD.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" +
            "       AND VD.ORGID =:orgId\r\n" +
            "       AND PM.CPD_ID_ACHEADTYPES =:coaReceiptLookupId\r\n" +
            "GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, VD.PAC_HEAD_ID", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetopeningPreviousPaymentReportDataL3(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT VD.PAC_HEAD_ID,\r\n" +
            "       VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "       (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) PAYMENT\r\n" +
            "  FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "       TB_AC_SECONDARYHEAD_MASTER SD,\r\n" +
            "       TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            " WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'TDP')\r\n" +
            "                      AND CPD_VALUE IN ('PVE', 'RBP'))\r\n" +
            "       AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" +
            "       AND SD.SAC_LED_TYPE IN\r\n" +
            "              (SELECT CPD_ID\r\n" +
            "                 FROM TB_COMPARAM_DET D\r\n" +
            "                WHERE     D.CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                         FROM TB_COMPARAM_MAS M\r\n" +
            "                                        WHERE M.CPM_PREFIX = 'FTY')\r\n" +
            "                      AND CPD_VALUE IN ('VD', 'OT'))\r\n" +
            "       AND VD.VOU_POSTING_DATE BETWEEN :fromDate AND :toDate\r\n" +
            "       AND SD.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" +
            "       AND VD.ORGID =:orgId\r\n" +
            "       AND PM.CPD_ID_ACHEADTYPES <>:coaReceiptLookupId and PM.PAC_HEAD_COMPO_CODE  in  (350,320,360,340,410,412,341,420,421,460,440)\r\n" +
            "GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID, VD.PAC_HEAD_ID", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetnonOpeningPreviousPaymentSideReportDataL3(@Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("orgId") Long orgId,
            @Param("coaReceiptLookupId") Long coaReceiptLookupId);

    @Query(value = "SELECT PM.PAYMENT_DATE,\r\n" +
            "       DM.DPS_SLIPNO,\r\n" +
            "       PM.BA_ACCOUNTID AS PAYMENTAC,\r\n" +
            "       DM.BA_ACCOUNTID AS RECEIPTAC,\r\n" +
            "       PM.PAYMENT_AMOUNT,\r\n" +
            "       PM.NARRATION\r\n" +
            "  FROM TB_RECEIPT_MODE RM,\r\n" +
            "       TB_RECEIPT_DET RD,\r\n" +
            "       TB_AC_BANK_DEPOSITSLIP_MASTER DM,\r\n" +
            "       TB_AC_PAYMENT_MAS PM\r\n" +
            " WHERE     RM.RM_RCPTID = RD.RM_RCPTID\r\n" +
            "       AND RD.DPS_SLIPID = DM.DPS_SLIPID\r\n" +
            "       AND DM.DPS_SLIPID = PM.DPS_SLIPID\r\n" +
            "       AND RM.ORGID =:orgId\r\n" +
            "       AND DM.DPS_SLIPDATE BETWEEN :fromDateId AND :toDateId\r\n" +
            "ORDER BY DM.DPS_SLIPDATE, DM.DPS_SLIPNO", nativeQuery = true)
    List<Object[]> getInterBankReportDataBy(@Param("fromDateId") Date fromDateId, @Param("toDateId") Date toDateId,
            @Param("orgId") Long orgId);

    /*@Query(value = "SELECT x.PAC_HEAD_CODE,\r\n" +
            "       x.PAC_HEAD_DESC,\r\n" +
            "       x.PAC_HEAD_ID,\r\n" +
            "       z.ORGINAL_ESTAMT2,\r\n" +
            "       z.REVISED_ESTAMT,\r\n" +
            "       y.1stqutr,\r\n" +
            "       y.2ndqutr,\r\n" +
            "       y.3rdqutr,\r\n" +
            "       y.TOTAL\r\n" +
            "  FROM (SELECT pm.PAC_HEAD_CODE,\r\n" +
            "               pm.PAC_HEAD_DESC,\r\n" +
            "               pm.PAC_HEAD_ID,\r\n" +
            "               sum(AMT.ORGINAL_ESTAMT),\r\n" +
            "               (SELECT SUM(P.AMOUNT)\r\n" +
            "                  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                               A.PAC_HEAD_DESC,\r\n" +
            "                               A.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "                               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "                               VOU_POSTING_DATE,\r\n" +
            "                               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" +
            "                          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                                       SAC_HEAD_ID,\r\n" +
            "                                       RCPTAMT,\r\n" +
            "                                       A.PAC_HEAD_ID,\r\n" +
            "                                       VOU_POSTING_DATE\r\n" +
            "                                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
            "                                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                               vd.VOU_POSTING_DATE,\r\n" +
            "                                               (  SUM(VD.VAMT_CR)\r\n" +
            "                                                - SUM(VD.VAMT_DR))\r\n" +
            "                                                  RCPTAMT\r\n" +
            "                                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'TDP')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('RRE',\r\n" +
            "                                                                      'RV',\r\n" +
            "                                                                      'RDC',\r\n" +
            "                                                                      'ASE'))\r\n" +
            "                                               AND VD.SAC_HEAD_ID =\r\n" +
            "                                                      SD.SAC_HEAD_ID\r\n" +
            "                                               AND SD.SAC_LED_TYPE IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'FTY')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('VD',\r\n" +
            "                                                                      'OT'))\r\n" +
            "                                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                                           AND :toDate\r\n" +
            "                                               AND VD.ORGID =:orgId\r\n" +
            "                                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                                 VD.SAC_HEAD_ID,\r\n" +
            "                                                 VD.PAC_HEAD_ID,\r\n" +
            "                                                 vd.VOU_POSTING_DATE) A) AAA,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "                               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "                        GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_DESC,\r\n" +
            "                                 B.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                                 AAA.VOU_POSTING_DATE) P,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            "                 WHERE     P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "                       AND R.PAC_HEAD_CODE = pm.PAC_HEAD_CODE\r\n" +
            "                GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE)\r\n" +
            "                  TRANS_AMOUNT\r\n" +
            "          FROM tb_ac_primaryhead_master pm\r\n" +
            "               LEFT JOIN\r\n" +
            "               (SELECT a.PAC_HEAD_ID, sum(b.ORGINAL_ESTAMT) ORGINAL_ESTAMT\r\n" +
            "                  FROM (SELECT BUDGETCODE_ID, PAC_HEAD_ID\r\n" +
            "                          FROM tb_ac_budgetcode_mas bm\r\n" +
            "                         WHERE bm.ORGID =:orgId) a,\r\n" +
            "                       tb_ac_projectedrevenue b\r\n" +
            "                 WHERE     b.BUDGETCODE_ID = a.BUDGETCODE_ID\r\n" +
            "                       AND b.orgid =:orgId\r\n" +
            "                       AND b.FA_YEARID =:financialYearId\r\n" +
            "                GROUP BY a.PAC_HEAD_ID) AMT\r\n" +
            "                  ON pm.PAC_HEAD_ID = amt.PAC_HEAD_ID\r\n" +
            "         WHERE     pm.PAC_HEAD_PARENT_ID IS NULL\r\n" +
            "               AND pm.CPD_ID_ACHEADTYPES IN\r\n" +
            "                      (SELECT cpd_id\r\n" +
            "                         FROM tb_comparam_det\r\n" +
            "                        WHERE     cpd_value IN (:lookUpCode)\r\n" +
            "                              AND cpm_id IN (SELECT cpm_id\r\n" +
            "                                               FROM tb_comparam_mas m\r\n" +
            "                                              WHERE m.CPM_PREFIX = 'COA'))\r\n" +
            "        GROUP BY pm.PAC_HEAD_CODE, pm.PAC_HEAD_DESC, pm.PAC_HEAD_ID) x\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT PAC_HEAD_CODE,\r\n" +
            "               sum(1stqutr) 1stqutr,\r\n" +
            "               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" +
            "                  2ndqutr,\r\n" +
            "               (  coalesce(sum(1stqutr), 0)\r\n" +
            "                + coalesce(sum(2ndqutr), 0)\r\n" +
            "                + coalesce(sum(3rdqutr), 0))\r\n" +
            "                  3rdqutr,\r\n" +
            "               sum(AMOUNT) TOTAL\r\n" +
            "          FROM (SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       R.PAC_HEAD_CODE,\r\n" +
            "                       R.PAC_HEAD_DESC,\r\n" +
            "                       VOU_POSTING_DATE,\r\n" +
            "                       CASE\r\n" +
            "                          WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" +
            "                                  ('04', '05', '06')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '1stqutr',\r\n" +
            "                       CASE\r\n" +
            "                          WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" +
            "                                  ('07', '08', '09')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '2ndqutr',\r\n" +
            "                       CASE\r\n" +
            "                          WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" +
            "                                  ('10', '11', '12')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '3rdqutr',\r\n" +
            "                       SUM(P.AMOUNT) AMOUNT\r\n" +
            "                  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                               A.PAC_HEAD_DESC,\r\n" +
            "                               A.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "                               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "                               VOU_POSTING_DATE,\r\n" +
            "                               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" +
            "                          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                                       SAC_HEAD_ID,\r\n" +
            "                                       RCPTAMT,\r\n" +
            "                                       A.PAC_HEAD_ID,\r\n" +
            "                                       VOU_POSTING_DATE\r\n" +
            "                                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
            "                                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                               vd.VOU_POSTING_DATE,\r\n" +
            "                                               (  SUM(VD.VAMT_CR)\r\n" +
            "                                                - SUM(VD.VAMT_DR))\r\n" +
            "                                                  RCPTAMT\r\n" +
            "                                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'TDP')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('RRE',\r\n" +
            "                                                                      'RV',\r\n" +
            "                                                                      'RDC',\r\n" +
            "                                                                      'ASE'))\r\n" +
            "                                               AND VD.SAC_HEAD_ID =\r\n" +
            "                                                      SD.SAC_HEAD_ID\r\n" +
            "                                               AND SD.SAC_LED_TYPE IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'FTY')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('VD',\r\n" +
            "                                                                      'OT'))\r\n" +
            "                                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                                           AND :toDate\r\n" +
            "                                               AND VD.ORGID =:orgId\r\n" +
            "                                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                                 VD.SAC_HEAD_ID,\r\n" +
            "                                                 VD.PAC_HEAD_ID,\r\n" +
            "                                                 vd.VOU_POSTING_DATE) A) AAA,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "                               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "                        GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_DESC,\r\n" +
            "                                 B.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                                 AAA.VOU_POSTING_DATE) P,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            "                 WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "                GROUP BY LEVEL1,\r\n" +
            "                         R.PAC_HEAD_DESC,\r\n" +
            "                         R.PAC_HEAD_COMPO_CODE,\r\n" +
            "                         P.VOU_POSTING_DATE) z\r\n" +
            "        GROUP BY PAC_HEAD_CODE) y\r\n" +
            "          ON x.PAC_HEAD_CODE = y.PAC_HEAD_CODE\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT c.PAC_HEAD_CODE,\r\n" +
            "               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" +
            "               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" +
            "          FROM TB_AC_PROJECTEDREVENUE a,\r\n" +
            "               TB_AC_BUDGETCODE_MAS b,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER c,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER d\r\n" +
            "         WHERE     FA_YEARID =:financialYearId\r\n" +
            "               AND a.ORGID =:orgId\r\n" +
            "               AND a.ORGID = b.ORGID\r\n" +
            "               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" +
            "               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" +
            "               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" +
            "        GROUP BY c.PAC_HEAD_CODE) z\r\n" +
            "          ON x.PAC_HEAD_CODE = z.PAC_HEAD_CODE", nativeQuery = true)*/
    @Query(value = "SELECT x.PAC_HEAD_CODE,\r\n" + 
    		"       x.PAC_HEAD_DESC,\r\n" + 
    		"       x.PAC_HEAD_ID,\r\n" + 
    		"       z.ORGINAL_ESTAMT2,\r\n" + 
    		"       z.REVISED_ESTAMT,\r\n" + 
    		"       y.1stqutr,\r\n" + 
    		"       y.2ndqutr,\r\n" + 
    		"       y.3rdqutr,\r\n" + 
    		"       y.TOTAL\r\n" + 
    		"  FROM (SELECT pm.PAC_HEAD_CODE,\r\n" + 
    		"               pm.PAC_HEAD_DESC,\r\n" + 
    		"               pm.PAC_HEAD_ID\r\n" + 
    		"           FROM tb_ac_primaryhead_master pm\r\n" + 
    		"           WHERE  pm.PAC_HEAD_PARENT_ID IS NULL\r\n" + 
    		"           AND pm.CPD_ID_ACHEADTYPES IN\r\n" + 
    		"            (SELECT cpd_id   FROM tb_comparam_det  WHERE cpd_value=:lookUpCode\r\n" + 
    		"           AND cpm_id IN (SELECT cpm_id   FROM tb_comparam_mas m    WHERE m.CPM_PREFIX = 'COA'))\r\n" + 
    		"        /*GROUP BY pm.PAC_HEAD_CODE, pm.PAC_HEAD_DESC, pm.PAC_HEAD_ID*/) x\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT PAC_HEAD_CODE,\r\n" + 
    		"               sum(1stqutr) 1stqutr,\r\n" + 
    		"               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" + 
    		"                  2ndqutr,\r\n" + 
    		"               (  coalesce(sum(1stqutr), 0)\r\n" + 
    		"                + coalesce(sum(2ndqutr), 0)\r\n" + 
    		"                + coalesce(sum(3rdqutr), 0))\r\n" + 
    		"                  3rdqutr,\r\n" + 
    		"               sum(AMOUNT) TOTAL\r\n" + 
    		"          FROM (SELECT P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE,\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('04', '05', '06')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)   END    '1stqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('07', '08', '09')\r\n" + 
    		"                          THEN SUM(P.AMOUNT)  END      '2ndqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('10', '11', '12')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)  END  '3rdqutr',\r\n" + 
    		"                       SUM(P.AMOUNT) AMOUNT\r\n" + 
    		"                    FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                                       SAC_HEAD_ID,\r\n" + 
    		"                                       SUM(RCPTAMT) AS AMOUNT,\r\n" + 
    		"                                       PAC_HEAD_ID,PAC_HEAD_DESC,PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                       VOU_POSTING_DATE\r\n" + 
    		"                                  FROM (SELECT R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID,\r\n" + 
    		"                                               VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE,\r\n" + 
    		"                                               (  SUM(VD.VAMT_CR)\r\n" + 
    		"                                                - SUM(VD.VAMT_DR))\r\n" + 
    		"                                                  RCPTAMT\r\n" + 
    		"                                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                                          TB_AC_SECONDARYHEAD_MASTER SD,\r\n" + 
    		"                                          TB_COMPARAM_DET D,TB_COMPARAM_MAS M,\r\n" + 
    		"                                          TB_COMPARAM_DET E,TB_COMPARAM_MAS F,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER B,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"                                         WHERE VD.VOU_SUBTYPE_CPD_ID= D.CPD_ID\r\n" + 
    		"                                               AND D.CPM_ID =M.CPM_ID\r\n" + 
    		"                                               AND M.CPM_PREFIX = 'TDP'\r\n" + 
    		"                                               AND D.CPD_VALUE IN ('RRE','RV','RDC','ASE')\r\n" + 
    		"                                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                                               AND SD.SAC_LED_TYPE=E.CPD_ID\r\n" + 
    		"                                               AND  E.CPM_ID= F.CPM_ID\r\n" + 
    		"                                               AND F.CPM_PREFIX ='FTY'\r\n" + 
    		"                                               AND E.CPD_VALUE IN ('VD','OT')\r\n" + 
    		"                                            AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" + 
    		"                                                                          AND :toDate\r\n" + 
    		"                                              AND VD.ORGID =:orgId\r\n" + 
    		"                                              AND  SD.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"                                              AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"                                              AND B.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                        GROUP BY R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID, VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE) A\r\n" + 
    		"                                 GROUP BY SAC_HEAD_ID,AC_HEAD_CODE,\r\n" + 
    		"                                 PAC_HEAD_DESC,\r\n" + 
    		"                                 PAC_HEAD_ID,\r\n" + 
    		"                                 PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                 VOU_POSTING_DATE) P\r\n" + 
    		"                        GROUP BY P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE) z\r\n" + 
    		"        GROUP BY PAC_HEAD_CODE) y\r\n" + 
    		"          ON x.PAC_HEAD_CODE = y.PAC_HEAD_CODE\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT e.PAC_HEAD_CODE,\r\n" + 
    		"               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" + 
    		"               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" + 
    		"          FROM TB_AC_PROJECTEDREVENUE a,\r\n" + 
    		"               TB_AC_BUDGETCODE_MAS b,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER c,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER d,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER e\r\n" + 
    		"         WHERE     FA_YEARID =:financialYearId\r\n" + 
    		"               AND a.ORGID =:orgId\r\n" + 
    		"               AND a.ORGID = b.ORGID\r\n" + 
    		"               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + 
    		"               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" + 
    		"               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" + 
    		"               AND c.PAC_HEAD_PARENT_ID = e.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY e.PAC_HEAD_CODE) z\r\n" + 
    		"          ON x.PAC_HEAD_CODE = z.PAC_HEAD_CODE",nativeQuery=true)
    List<Object[]> getQuaterlyRevenueReCeiptsReportData(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId, @Param("lookUpCode") String lookUpCode);
    
    @Query(value = "SELECT x.PAC_HEAD_CODE,\r\n" + 
    		"       x.PAC_HEAD_DESC,\r\n" + 
    		"       x.PAC_HEAD_ID,\r\n" + 
    		"       z.ORGINAL_ESTAMT2,\r\n" + 
    		"       z.REVISED_ESTAMT,\r\n" + 
    		"       y.1stqutr,\r\n" + 
    		"       y.2ndqutr,\r\n" + 
    		"       y.3rdqutr,\r\n" + 
    		"       y.TOTAL\r\n" + 
    		"  FROM (SELECT pm.PAC_HEAD_CODE,\r\n" + 
    		"               pm.PAC_HEAD_DESC,\r\n" + 
    		"               pm.PAC_HEAD_ID\r\n" + 
    		"           FROM tb_ac_primaryhead_master pm\r\n" + 
    		"           WHERE  pm.PAC_HEAD_PARENT_ID IS NULL\r\n" + 
    		"           AND pm.CPD_ID_ACHEADTYPES IN\r\n" + 
    		"            (SELECT cpd_id   FROM tb_comparam_det  WHERE cpd_value=:lookUpCode\r\n" + 
    		"           AND cpm_id IN (SELECT cpm_id   FROM tb_comparam_mas m    WHERE m.CPM_PREFIX = 'COA'))\r\n" + 
    		"        /*GROUP BY pm.PAC_HEAD_CODE, pm.PAC_HEAD_DESC, pm.PAC_HEAD_ID*/) x\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT PAC_HEAD_CODE,\r\n" + 
    		"               sum(1stqutr) 1stqutr,\r\n" + 
    		"               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" + 
    		"                  2ndqutr,\r\n" + 
    		"               (  coalesce(sum(1stqutr), 0)\r\n" + 
    		"                + coalesce(sum(2ndqutr), 0)\r\n" + 
    		"                + coalesce(sum(3rdqutr), 0))\r\n" + 
    		"                  3rdqutr,\r\n" + 
    		"               sum(AMOUNT) TOTAL\r\n" + 
    		"          FROM (SELECT P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE,\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('04', '05', '06')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)   END    '1stqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('07', '08', '09')\r\n" + 
    		"                          THEN SUM(P.AMOUNT)  END      '2ndqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('10', '11', '12')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)  END  '3rdqutr',\r\n" + 
    		"                       SUM(P.AMOUNT) AMOUNT\r\n" + 
    		"                    FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                                       SAC_HEAD_ID,\r\n" + 
    		"                                       SUM(RCPTAMT) AS AMOUNT,\r\n" + 
    		"                                       PAC_HEAD_ID,PAC_HEAD_DESC,PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                       VOU_POSTING_DATE\r\n" + 
    		"                                  FROM (SELECT R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID,\r\n" + 
    		"                                               VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE,\r\n" + 
    		"                                               (  SUM(VD.VAMT_CR)\r\n" + 
    		"                                                - SUM(VD.VAMT_DR))\r\n" + 
    		"                                                  RCPTAMT\r\n" + 
    		"                                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                                          TB_AC_SECONDARYHEAD_MASTER SD,\r\n" + 
    		"                                          TB_COMPARAM_DET D,TB_COMPARAM_MAS M,\r\n" + 
    		"                                          TB_COMPARAM_DET E,TB_COMPARAM_MAS F,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R/*,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R*/\r\n" + 
    		"                                         WHERE VD.VOU_SUBTYPE_CPD_ID= D.CPD_ID\r\n" + 
    		"                                               AND D.CPM_ID =M.CPM_ID\r\n" + 
    		"                                               AND M.CPM_PREFIX = 'TDP'\r\n" + 
    		"                                               AND D.CPD_VALUE IN ('RRE','RV','RDC','ASE')\r\n" + 
    		"                                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                                               AND SD.SAC_LED_TYPE=E.CPD_ID\r\n" + 
    		"                                               AND  E.CPM_ID= F.CPM_ID\r\n" + 
    		"                                               AND F.CPM_PREFIX ='FTY'\r\n" + 
    		"                                               AND E.CPD_VALUE IN ('VD','OT')\r\n" + 
    		"                                            AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" + 
    		"                                                                          AND :toDate\r\n" + 
    		"                                              AND VD.ORGID = :orgId\r\n" + 
    		"                                              AND  SD.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"                                              AND A.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                              -- AND B.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                        GROUP BY R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID, VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE) A\r\n" + 
    		"                                 GROUP BY SAC_HEAD_ID,AC_HEAD_CODE,\r\n" + 
    		"                                 PAC_HEAD_DESC,\r\n" + 
    		"                                 PAC_HEAD_ID,\r\n" + 
    		"                                 PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                 VOU_POSTING_DATE) P\r\n" + 
    		"                        GROUP BY P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE) z\r\n" + 
    		"        GROUP BY PAC_HEAD_CODE) y\r\n" + 
    		"          ON x.PAC_HEAD_CODE = y.PAC_HEAD_CODE\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT c.PAC_HEAD_CODE,\r\n" + 
    		"               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" + 
    		"               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" + 
    		"          FROM TB_AC_PROJECTEDREVENUE a,\r\n" + 
    		"               TB_AC_BUDGETCODE_MAS b,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER c,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER d/*,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER e*/\r\n" + 
    		"         WHERE     FA_YEARID =:financialYearId\r\n" + 
    		"               AND a.ORGID =:orgId\r\n" + 
    		"               AND a.ORGID = b.ORGID\r\n" + 
    		"               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + 
    		"               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" + 
    		"               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" + 
    		"               -- AND c.PAC_HEAD_PARENT_ID = e.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY c.PAC_HEAD_CODE) z\r\n" + 
    		"          ON x.PAC_HEAD_CODE = z.PAC_HEAD_CODE",nativeQuery=true)
    List<Object[]> getQuaterlyRevenueReCeiptsReportDataTSCL(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId, @Param("lookUpCode") String lookUpCode);

   /* @Query(value = "SELECT x.PAC_HEAD_CODE,\r\n" +
            "       x.PAC_HEAD_DESC,\r\n" +
            "       x.PAC_HEAD_ID,\r\n" +
            "       z.ORGINAL_ESTAMT2,\r\n" +
            "       z.REVISED_ESTAMT,\r\n" +
            "       y.1stqutr,\r\n" +
            "       y.2ndqutr,\r\n" +
            "       y.3rdqutr,\r\n" +
            "       y.TOTAL\r\n" +
            "  FROM (SELECT pm.PAC_HEAD_CODE,\r\n" +
            "               pm.PAC_HEAD_DESC,\r\n" +
            "               pm.PAC_HEAD_ID,\r\n" +
            "               sum(AMT.ORGINAL_ESTAMT),\r\n" +
            "               (SELECT SUM(P.AMOUNT)\r\n" +
            "                  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                               A.PAC_HEAD_DESC,\r\n" +
            "                               A.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "                               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "                               VOU_POSTING_DATE,\r\n" +
            "                               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" +
            "                          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                                       SAC_HEAD_ID,\r\n" +
            "                                       RCPTAMT,\r\n" +
            "                                       A.PAC_HEAD_ID,\r\n" +
            "                                       VOU_POSTING_DATE\r\n" +
            "                                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
            "                                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                               vd.VOU_POSTING_DATE,\r\n" +
            "                                               (  SUM(VD.VAMT_CR)\r\n" +
            "                                                - SUM(VD.VAMT_DR))\r\n" +
            "                                                  RCPTAMT\r\n" +
            "                                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'TDP')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('RRE',\r\n" +
            "                                                                      'RV',\r\n" +
            "                                                                      'RDC',\r\n" +
            "                                                                      'ASE'))\r\n" +
            "                                               AND VD.SAC_HEAD_ID =\r\n" +
            "                                                      SD.SAC_HEAD_ID\r\n" +
            "                                               AND SD.SAC_LED_TYPE IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'FTY')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('VD',\r\n" +
            "                                                                      'OT'))\r\n" +
            "                                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                                           AND :toDate\r\n" +
            "                                               AND VD.ORGID =:orgId\r\n" +
            "                                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                                 VD.SAC_HEAD_ID,\r\n" +
            "                                                 VD.PAC_HEAD_ID,\r\n" +
            "                                                 vd.VOU_POSTING_DATE) A) AAA,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "                               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "                        GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_DESC,\r\n" +
            "                                 B.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                                 AAA.VOU_POSTING_DATE) P,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            "                 WHERE     P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "                       AND R.PAC_HEAD_CODE = pm.PAC_HEAD_CODE\r\n" +
            "                GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE)\r\n" +
            "                  TRANS_AMOUNT\r\n" +
            "          FROM tb_ac_primaryhead_master pm\r\n" +
            "               LEFT JOIN\r\n" +
            "               (SELECT a.PAC_HEAD_ID, sum(b.ORGINAL_ESTAMT) ORGINAL_ESTAMT\r\n" +
            "                  FROM (SELECT BUDGETCODE_ID, PAC_HEAD_ID\r\n" +
            "                          FROM tb_ac_budgetcode_mas bm\r\n" +
            "                         WHERE bm.ORGID =:orgId) a,\r\n" +
            "                       tb_ac_projectedrevenue b\r\n" +
            "                 WHERE     b.BUDGETCODE_ID = a.BUDGETCODE_ID\r\n" +
            "                       AND b.orgid =:orgId\r\n" +
            "                       AND b.FA_YEARID =:financialYearId\r\n" +
            "                GROUP BY a.PAC_HEAD_ID) AMT\r\n" +
            "                  ON pm.PAC_HEAD_ID = amt.PAC_HEAD_ID\r\n" +
            "         WHERE     pm.PAC_HEAD_PARENT_ID IS NULL\r\n" +
            "               AND pm.CPD_ID_ACHEADTYPES IN\r\n" +
            "                      (SELECT cpd_id\r\n" +
            "                         FROM tb_comparam_det\r\n" +
            "                        WHERE     cpd_value IN ('L','A')\r\n" +
            "                              AND cpm_id IN (SELECT cpm_id\r\n" +
            "                                               FROM tb_comparam_mas m\r\n" +
            "                                              WHERE m.CPM_PREFIX = 'COA'))\r\n" +
            "        GROUP BY pm.PAC_HEAD_CODE, pm.PAC_HEAD_DESC, pm.PAC_HEAD_ID) x\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT PAC_HEAD_CODE,\r\n" +
            "               sum(1stqutr) 1stqutr,\r\n" +
            "               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" +
            "                  2ndqutr,\r\n" +
            "               (  coalesce(sum(1stqutr), 0)\r\n" +
            "                + coalesce(sum(2ndqutr), 0)\r\n" +
            "                + coalesce(sum(3rdqutr), 0))\r\n" +
            "                  3rdqutr,\r\n" +
            "               sum(AMOUNT) TOTAL\r\n" +
            "          FROM (SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       R.PAC_HEAD_CODE,\r\n" +
            "                       R.PAC_HEAD_DESC,\r\n" +
            "                       VOU_POSTING_DATE,\r\n" +
            "                       CASE\r\n" +
            "                          WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" +
            "                                  ('04', '05', '06')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '1stqutr',\r\n" +
            "                       CASE\r\n" +
            "                          WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" +
            "                                  ('07', '08', '09')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '2ndqutr',\r\n" +
            "                       CASE\r\n" +
            "                          WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" +
            "                                  ('10', '11', '12')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '3rdqutr',\r\n" +
            "                       SUM(P.AMOUNT) AMOUNT\r\n" +
            "                  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                               A.PAC_HEAD_DESC,\r\n" +
            "                               A.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "                               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "                               VOU_POSTING_DATE,\r\n" +
            "                               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" +
            "                          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                                       SAC_HEAD_ID,\r\n" +
            "                                       RCPTAMT,\r\n" +
            "                                       A.PAC_HEAD_ID,\r\n" +
            "                                       VOU_POSTING_DATE\r\n" +
            "                                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
            "                                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                               vd.VOU_POSTING_DATE,\r\n" +
            "                                               (  SUM(VD.VAMT_CR)\r\n" +
            "                                                - SUM(VD.VAMT_DR))\r\n" +
            "                                                  RCPTAMT\r\n" +
            "                                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'TDP')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('RRE',\r\n" +
            "                                                                      'RV',\r\n" +
            "                                                                      'RDC',\r\n" +
            "                                                                      'ASE'))\r\n" +
            "                                               AND VD.SAC_HEAD_ID =\r\n" +
            "                                                      SD.SAC_HEAD_ID\r\n" +
            "                                               AND SD.SAC_LED_TYPE IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'FTY')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('VD',\r\n" +
            "                                                                      'OT'))\r\n" +
            "                                               AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" +
            "                                                                           AND :toDate\r\n" +
            "                                               AND VD.ORGID =:orgId\r\n" +
            "                                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                                 VD.SAC_HEAD_ID,\r\n" +
            "                                                 VD.PAC_HEAD_ID,\r\n" +
            "                                                 vd.VOU_POSTING_DATE) A) AAA,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "                               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "                        GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_DESC,\r\n" +
            "                                 B.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                                 AAA.VOU_POSTING_DATE) P,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            "                 WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "                GROUP BY LEVEL1,\r\n" +
            "                         R.PAC_HEAD_DESC,\r\n" +
            "                         R.PAC_HEAD_COMPO_CODE,\r\n" +
            "                         P.VOU_POSTING_DATE) z\r\n" +
            "        GROUP BY PAC_HEAD_CODE) y\r\n" +
            "          ON x.PAC_HEAD_CODE = y.PAC_HEAD_CODE\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT c.PAC_HEAD_CODE,\r\n" +
            "               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" +
            "               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" +
            "          FROM TB_AC_PROJECTEDREVENUE a,\r\n" +
            "               TB_AC_BUDGETCODE_MAS b,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER c,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER d\r\n" +
            "         WHERE     FA_YEARID =:financialYearId\r\n" +
            "               AND a.ORGID =:orgId\r\n" +
            "               AND a.ORGID = b.ORGID\r\n" +
            "               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" +
            "               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" +
            "               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" +
            "        GROUP BY c.PAC_HEAD_CODE) z\r\n" +
            "          ON x.PAC_HEAD_CODE = z.PAC_HEAD_CODE", nativeQuery = true)*/
    @Query(value=" \r\n" + 
    		"SELECT x.PAC_HEAD_CODE,\r\n" + 
    		"       x.PAC_HEAD_DESC,\r\n" + 
    		"       x.PAC_HEAD_ID,\r\n" + 
    		"       z.ORGINAL_ESTAMT2,\r\n" + 
    		"       z.REVISED_ESTAMT,\r\n" + 
    		"       y.1stqutr,\r\n" + 
    		"       y.2ndqutr,\r\n" + 
    		"       y.3rdqutr,\r\n" + 
    		"       y.TOTAL\r\n" + 
    		"  FROM (SELECT pm.PAC_HEAD_CODE,\r\n" + 
    		"               pm.PAC_HEAD_DESC,\r\n" + 
    		"               pm.PAC_HEAD_ID\r\n" + 
    		"          FROM tb_ac_primaryhead_master pm\r\n" + 
    		"          WHERE pm.PAC_HEAD_PARENT_ID IS NULL\r\n" + 
    		"               AND pm.CPD_ID_ACHEADTYPES IN (SELECT cpd_id FROM tb_comparam_det WHERE cpd_value IN ('L','A')\r\n" + 
    		"               AND cpm_id IN (SELECT cpm_id  FROM tb_comparam_mas m  WHERE m.CPM_PREFIX = 'COA'))) x\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT PAC_HEAD_CODE,\r\n" + 
    		"               sum(1stqutr) 1stqutr,\r\n" + 
    		"               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" + 
    		"                  2ndqutr,\r\n" + 
    		"               (  coalesce(sum(1stqutr), 0)\r\n" + 
    		"                + coalesce(sum(2ndqutr), 0)\r\n" + 
    		"                + coalesce(sum(3rdqutr), 0))\r\n" + 
    		"                  3rdqutr,\r\n" + 
    		"               sum(AMOUNT) TOTAL\r\n" + 
    		"          FROM (SELECT P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE,\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('04', '05', '06')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)   END    '1stqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('07', '08', '09')\r\n" + 
    		"                          THEN SUM(P.AMOUNT)  END      '2ndqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('10', '11', '12')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)  END  '3rdqutr',\r\n" + 
    		"                       SUM(P.AMOUNT) AMOUNT\r\n" + 
    		"                    FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                                       SAC_HEAD_ID,\r\n" + 
    		"                                       SUM(RCPTAMT) AS AMOUNT,\r\n" + 
    		"                                       PAC_HEAD_ID,PAC_HEAD_DESC,PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                       VOU_POSTING_DATE\r\n" + 
    		"                                  FROM (SELECT R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID,\r\n" + 
    		"                                               VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE,\r\n" + 
    		"                                               (  SUM(VD.VAMT_CR)\r\n" + 
    		"                                                - SUM(VD.VAMT_DR))\r\n" + 
    		"                                                  RCPTAMT\r\n" + 
    		"                                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                                          TB_AC_SECONDARYHEAD_MASTER SD,\r\n" + 
    		"                                          TB_COMPARAM_DET D,TB_COMPARAM_MAS M,\r\n" + 
    		"                                          TB_COMPARAM_DET E,TB_COMPARAM_MAS F,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER B,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"                                         WHERE VD.VOU_SUBTYPE_CPD_ID= D.CPD_ID\r\n" + 
    		"                                               AND D.CPM_ID =M.CPM_ID\r\n" + 
    		"                                               AND M.CPM_PREFIX = 'TDP'\r\n" + 
    		"                                               AND D.CPD_VALUE IN ('RRE','RV','RDC','ASE')\r\n" + 
    		"                                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                                               AND SD.SAC_LED_TYPE=E.CPD_ID\r\n" + 
    		"                                               AND  E.CPM_ID= F.CPM_ID\r\n" + 
    		"                                               AND F.CPM_PREFIX ='FTY'\r\n" + 
    		"                                               AND E.CPD_VALUE IN ('VD','OT')\r\n" + 
    		"                                            AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" + 
    		"                                                                      AND :toDate\r\n" + 
    		"                                              AND VD.ORGID =:orgId\r\n" + 
    		"                                              AND  SD.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"                                              AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"                                              AND B.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                        GROUP BY R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID, VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE) A\r\n" + 
    		"                                 GROUP BY SAC_HEAD_ID,AC_HEAD_CODE,\r\n" + 
    		"                                 PAC_HEAD_DESC,\r\n" + 
    		"                                 PAC_HEAD_ID,\r\n" + 
    		"                                 PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                 VOU_POSTING_DATE) P\r\n" + 
    		"                        GROUP BY P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE) z\r\n" + 
    		"        GROUP BY PAC_HEAD_CODE) y\r\n" + 
    		"          ON x.PAC_HEAD_CODE = y.PAC_HEAD_CODE\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT e.PAC_HEAD_CODE,\r\n" + 
    		"               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" + 
    		"               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" + 
    		"          FROM TB_AC_PROJECTEDREVENUE a,  TB_AC_BUDGETCODE_MAS b,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER c,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER d,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER e\r\n" + 
    		"         WHERE  FA_YEARID =:financialYearId\r\n" + 
    		"               AND a.ORGID =:orgId\r\n" + 
    		"               AND a.ORGID = b.ORGID\r\n" + 
    		"               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + 
    		"               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" + 
    		"               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" + 
    		"               AND c.PAC_HEAD_PARENT_ID = e.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY e.PAC_HEAD_CODE) z\r\n" + 
    		"          ON x.PAC_HEAD_CODE = z.PAC_HEAD_CODE",nativeQuery=true)
    List<Object[]> getCapitalReceiptsDataBy(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId);
    
    @Query(value=" \r\n" + 
    		"SELECT x.PAC_HEAD_CODE,\r\n" + 
    		"       x.PAC_HEAD_DESC,\r\n" + 
    		"       x.PAC_HEAD_ID,\r\n" + 
    		"       z.ORGINAL_ESTAMT2,\r\n" + 
    		"       z.REVISED_ESTAMT,\r\n" + 
    		"       y.1stqutr,\r\n" + 
    		"       y.2ndqutr,\r\n" + 
    		"       y.3rdqutr,\r\n" + 
    		"       y.TOTAL\r\n" + 
    		"  FROM (SELECT pm.PAC_HEAD_CODE,\r\n" + 
    		"               pm.PAC_HEAD_DESC,\r\n" + 
    		"               pm.PAC_HEAD_ID\r\n" + 
    		"          FROM tb_ac_primaryhead_master pm\r\n" + 
    		"          WHERE pm.PAC_HEAD_PARENT_ID IS NULL\r\n" + 
    		"               AND pm.CPD_ID_ACHEADTYPES IN (SELECT cpd_id FROM tb_comparam_det WHERE cpd_value IN ('L','A')\r\n" + 
    		"               AND cpm_id IN (SELECT cpm_id  FROM tb_comparam_mas m  WHERE m.CPM_PREFIX = 'COA'))) x\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT PAC_HEAD_CODE,\r\n" + 
    		"               sum(1stqutr) 1stqutr,\r\n" + 
    		"               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" + 
    		"                  2ndqutr,\r\n" + 
    		"               (  coalesce(sum(1stqutr), 0)\r\n" + 
    		"                + coalesce(sum(2ndqutr), 0)\r\n" + 
    		"                + coalesce(sum(3rdqutr), 0))\r\n" + 
    		"                  3rdqutr,\r\n" + 
    		"               sum(AMOUNT) TOTAL\r\n" + 
    		"          FROM (SELECT P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE,\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('04', '05', '06')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)   END    '1stqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('07', '08', '09')\r\n" + 
    		"                          THEN SUM(P.AMOUNT)  END      '2ndqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('10', '11', '12')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)  END  '3rdqutr',\r\n" + 
    		"                       SUM(P.AMOUNT) AMOUNT\r\n" + 
    		"                    FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                                       SAC_HEAD_ID,\r\n" + 
    		"                                       SUM(RCPTAMT) AS AMOUNT,\r\n" + 
    		"                                       PAC_HEAD_ID,PAC_HEAD_DESC,PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                       VOU_POSTING_DATE\r\n" + 
    		"                                  FROM (SELECT R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID,\r\n" + 
    		"                                               VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE,\r\n" + 
    		"                                               (  SUM(VD.VAMT_CR)\r\n" + 
    		"                                                - SUM(VD.VAMT_DR))\r\n" + 
    		"                                                  RCPTAMT\r\n" + 
    		"                                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                                          TB_AC_SECONDARYHEAD_MASTER SD,\r\n" + 
    		"                                          TB_COMPARAM_DET D,TB_COMPARAM_MAS M,\r\n" + 
    		"                                          TB_COMPARAM_DET E,TB_COMPARAM_MAS F,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R/*,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R*/\r\n" + 
    		"                                         WHERE VD.VOU_SUBTYPE_CPD_ID= D.CPD_ID\r\n" + 
    		"                                               AND D.CPM_ID =M.CPM_ID\r\n" + 
    		"                                               AND M.CPM_PREFIX = 'TDP'\r\n" + 
    		"                                               AND D.CPD_VALUE IN ('RRE','RV','RDC','ASE')\r\n" + 
    		"                                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                                               AND SD.SAC_LED_TYPE=E.CPD_ID\r\n" + 
    		"                                               AND  E.CPM_ID= F.CPM_ID\r\n" + 
    		"                                               AND F.CPM_PREFIX ='FTY'\r\n" + 
    		"                                               AND E.CPD_VALUE IN ('VD','OT')\r\n" + 
    		"                                            AND VD.VOU_POSTING_DATE BETWEEN :fromDate\r\n" + 
    		"                                                                      AND :toDate\r\n" + 
    		"                                              AND VD.ORGID = :orgId\r\n" + 
    		"                                              AND  SD.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"                                              AND A.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                              -- AND B.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                        GROUP BY R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID, VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE) A\r\n" + 
    		"                                 GROUP BY SAC_HEAD_ID,AC_HEAD_CODE,\r\n" + 
    		"                                 PAC_HEAD_DESC,\r\n" + 
    		"                                 PAC_HEAD_ID,\r\n" + 
    		"                                 PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                 VOU_POSTING_DATE) P\r\n" + 
    		"                        GROUP BY P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE) z\r\n" + 
    		"        GROUP BY PAC_HEAD_CODE) y\r\n" + 
    		"          ON x.PAC_HEAD_CODE = y.PAC_HEAD_CODE\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT c.PAC_HEAD_CODE,\r\n" + 
    		"               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" + 
    		"               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" + 
    		"          FROM TB_AC_PROJECTEDREVENUE a,  TB_AC_BUDGETCODE_MAS b,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER c,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER d/*,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER e*/\r\n" + 
    		"         WHERE  FA_YEARID = :financialYearId\r\n" + 
    		"               AND a.ORGID = :orgId\r\n" + 
    		"               AND a.ORGID = b.ORGID\r\n" + 
    		"               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + 
    		"               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" + 
    		"               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" + 
    		"               -- AND c.PAC_HEAD_PARENT_ID = e.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY c.PAC_HEAD_CODE) z\r\n" + 
    		"          ON x.PAC_HEAD_CODE = z.PAC_HEAD_CODE",nativeQuery=true)
    List<Object[]> getCapitalReceiptsDataByTSCL(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("financialYearId") Long financialYearId);

   /* @Query(value = "SELECT x.PAC_HEAD_CODE,\r\n" +
            "       x.PAC_HEAD_DESC,\r\n" +
            "       x.PAC_HEAD_ID,\r\n" +
            "       z.ORGINAL_ESTAMT2,\r\n" +
            "       z.REVISED_ESTAMT,\r\n" +
            "       Y.1STQUTR,\r\n" +
            "       Y.2NDQUTR,\r\n" +
            "       Y.3RDQUTR,\r\n" +
            "       Y.TOTAL\r\n" +
            "  FROM (SELECT PM.PAC_HEAD_CODE,\r\n" +
            "               PM.PAC_HEAD_DESC,\r\n" +
            "               PM.PAC_HEAD_ID,\r\n" +
            "               SUM(AMT.ORGINAL_ESTAMT),\r\n" +
            "               (SELECT SUM(P.AMOUNT)\r\n" +
            "                  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                               A.PAC_HEAD_DESC,\r\n" +
            "                               A.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "                               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "                               VOU_POSTING_DATE,\r\n" +
            "                               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" +
            "                          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                                       SAC_HEAD_ID,\r\n" +
            "                                       RCPTAMT,\r\n" +
            "                                       A.PAC_HEAD_ID,\r\n" +
            "                                       VOU_POSTING_DATE\r\n" +
            "                                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
            "                                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                               VD.VOU_POSTING_DATE,\r\n" +
            "                                               (  SUM(VD.VAMT_DR)\r\n" +
            "                                                - SUM(VD.VAMT_CR))\r\n" +
            "                                                  RCPTAMT\r\n" +
            "                                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'TDP')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('PVE',\r\n" +
            "                                                                      'RBP'))\r\n" +
            "                                               AND VD.SAC_HEAD_ID =\r\n" +
            "                                                      SD.SAC_HEAD_ID\r\n" +
            "                                               AND SD.SAC_LED_TYPE IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'FTY')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('VD',\r\n" +
            "                                                                      'OT'))\r\n" +
            "                                               AND VD.VOU_POSTING_DATE BETWEEN :fromDates\r\n" +
            "                                                                           AND :todates\r\n" +
            "                                               AND VD.ORGID =:orgId\r\n" +
            "                                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                                 VD.SAC_HEAD_ID,\r\n" +
            "                                                 VD.PAC_HEAD_ID,\r\n" +
            "                                                 VD.VOU_POSTING_DATE) A) AAA,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "                               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "                        GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_DESC,\r\n" +
            "                                 B.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                                 AAA.VOU_POSTING_DATE) P,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            "                 WHERE     P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "                       AND R.PAC_HEAD_CODE = PM.PAC_HEAD_CODE\r\n" +
            "                GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE)\r\n" +
            "                  TRANS_AMOUNT\r\n" +
            "          FROM TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            "               LEFT JOIN\r\n" +
            "               (SELECT A.PAC_HEAD_ID, SUM(B.ORGINAL_ESTAMT) ORGINAL_ESTAMT\r\n" +
            "                  FROM (SELECT BUDGETCODE_ID, PAC_HEAD_ID\r\n" +
            "                          FROM TB_AC_BUDGETCODE_MAS BM\r\n" +
            "                         WHERE BM.ORGID =:orgId) A,\r\n" +
            "                       TB_AC_PROJECTEDREVENUE B\r\n" +
            "                 WHERE     B.BUDGETCODE_ID = A.BUDGETCODE_ID\r\n" +
            "                       AND B.ORGID =:orgId\r\n" +
            "                       AND B.FA_YEARID =:financialYearId\r\n" +
            "                GROUP BY A.PAC_HEAD_ID) AMT\r\n" +
            "                  ON PM.PAC_HEAD_ID = AMT.PAC_HEAD_ID\r\n" +
            "         WHERE     PM.PAC_HEAD_PARENT_ID IS NULL\r\n" +
            "               AND PM.CPD_ID_ACHEADTYPES IN\r\n" +
            "                      (SELECT CPD_ID\r\n" +
            "                         FROM TB_COMPARAM_DET\r\n" +
            "                        WHERE     CPD_VALUE =:lookUpCode\r\n" +
            "                              AND CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                               FROM TB_COMPARAM_MAS M\r\n" +
            "                                              WHERE M.CPM_PREFIX = 'COA'))\r\n" +
            "        GROUP BY PM.PAC_HEAD_CODE, PM.PAC_HEAD_DESC, PM.PAC_HEAD_ID) X\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT PAC_HEAD_CODE,\r\n" +
            "               SUM(1STQUTR) 1STQUTR,\r\n" +
            "               (COALESCE(SUM(1STQUTR), 0) + COALESCE(SUM(2NDQUTR), 0))\r\n" +
            "                  2NDQUTR,\r\n" +
            "               (  COALESCE(SUM(1STQUTR), 0)\r\n" +
            "                + COALESCE(SUM(2NDQUTR), 0)\r\n" +
            "                + COALESCE(SUM(3RDQUTR), 0))\r\n" +
            "                  3RDQUTR,\r\n" +
            "               SUM(AMOUNT) TOTAL\r\n" +
            "          FROM (SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       R.PAC_HEAD_CODE,\r\n" +
            "                       R.PAC_HEAD_DESC,\r\n" +
            "                       VOU_POSTING_DATE,\r\n" +
            "                       CASE\r\n" +
            "                          WHEN DATE_FORMAT(VOU_POSTING_DATE, '%M') IN\r\n" +
            "                                  ('04', '05', '06')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '1STQUTR',\r\n" +
            "                       CASE\r\n" +
            "                          WHEN DATE_FORMAT(VOU_POSTING_DATE, '%M') IN\r\n" +
            "                                  ('07', '08', '09')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '2NDQUTR',\r\n" +
            "                       CASE\r\n" +
            "                          WHEN DATE_FORMAT(VOU_POSTING_DATE, '%M') IN\r\n" +
            "                                  ('10', '11', '12')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '3RDQUTR',\r\n" +
            "                       SUM(P.AMOUNT) AMOUNT\r\n" +
            "                  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                               A.PAC_HEAD_DESC,\r\n" +
            "                               A.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "                               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "                               VOU_POSTING_DATE,\r\n" +
            "                               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" +
            "                          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                                       SAC_HEAD_ID,\r\n" +
            "                                       RCPTAMT,\r\n" +
            "                                       A.PAC_HEAD_ID,\r\n" +
            "                                       VOU_POSTING_DATE\r\n" +
            "                                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
            "                                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                               VD.VOU_POSTING_DATE,\r\n" +
            "                                               (  SUM(VD.VAMT_DR)\r\n" +
            "                                                - SUM(VD.VAMT_CR))\r\n" +
            "                                                  RCPTAMT\r\n" +
            "                                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'TDP')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('PVE',\r\n" +
            "                                                                      'RBP'))\r\n" +
            "                                               AND VD.SAC_HEAD_ID =\r\n" +
            "                                                      SD.SAC_HEAD_ID\r\n" +
            "                                               AND SD.SAC_LED_TYPE IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'FTY')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('VD',\r\n" +
            "                                                                      'OT'))\r\n" +
            "                                               AND VD.VOU_POSTING_DATE BETWEEN :fromDates\r\n" +
            "                                                                           AND :todates\r\n" +
            "                                               AND VD.ORGID =:orgId\r\n" +
            "                                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                                 VD.SAC_HEAD_ID,\r\n" +
            "                                                 VD.PAC_HEAD_ID,\r\n" +
            "                                                 VD.VOU_POSTING_DATE) A) AAA,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "                               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "                        GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_DESC,\r\n" +
            "                                 B.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                                 AAA.VOU_POSTING_DATE) P,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            "                 WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "                GROUP BY LEVEL1,\r\n" +
            "                         R.PAC_HEAD_DESC,\r\n" +
            "                         R.PAC_HEAD_COMPO_CODE,\r\n" +
            "                         P.VOU_POSTING_DATE) Z\r\n" +
            "        GROUP BY PAC_HEAD_CODE) Y\r\n" +
            "          ON X.PAC_HEAD_CODE = Y.PAC_HEAD_CODE\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT C.PAC_HEAD_CODE,\r\n" +
            "               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" +
            "               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" +
            "          FROM TB_AC_PROJECTED_EXPENDITURE A,\r\n" +
            "               TB_AC_BUDGETCODE_MAS B,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE     FA_YEARID =:financialYearId\r\n" +
            "               AND A.ORGID =:orgId\r\n" +
            "               AND A.ORGID = B.ORGID\r\n" +
            "               AND A.BUDGETCODE_ID = B.BUDGETCODE_ID\r\n" +
            "               AND B.PAC_HEAD_ID = D.PAC_HEAD_ID\r\n" +
            "               AND D.PAC_HEAD_PARENT_ID = C.PAC_HEAD_ID\r\n" +
            "        GROUP BY C.PAC_HEAD_CODE) Z\r\n" +
            "          ON X.PAC_HEAD_CODE = Z.PAC_HEAD_CODE", nativeQuery = true)*/
    @Query(value ="SELECT x.PAC_HEAD_CODE,\r\n" + 
    		"       x.PAC_HEAD_DESC,\r\n" + 
    		"       x.PAC_HEAD_ID,\r\n" + 
    		"       z.ORGINAL_ESTAMT2,\r\n" + 
    		"       z.REVISED_ESTAMT,\r\n" + 
    		"       y.1stqutr,\r\n" + 
    		"       y.2ndqutr,\r\n" + 
    		"       y.3rdqutr,\r\n" + 
    		"       y.TOTAL\r\n" + 
    		"  FROM (SELECT pm.PAC_HEAD_CODE,\r\n" + 
    		"               pm.PAC_HEAD_DESC,\r\n" + 
    		"               pm.PAC_HEAD_ID\r\n" + 
    		"           FROM tb_ac_primaryhead_master pm\r\n" + 
    		"           WHERE  pm.PAC_HEAD_PARENT_ID IS NULL\r\n" + 
    		"           AND pm.CPD_ID_ACHEADTYPES IN\r\n" + 
    		"            (SELECT cpd_id   FROM tb_comparam_det  WHERE cpd_value=:lookUpCode\r\n" + 
    		"           AND cpm_id IN (SELECT cpm_id   FROM tb_comparam_mas m    WHERE m.CPM_PREFIX = 'COA'))\r\n" + 
    		"        /*GROUP BY pm.PAC_HEAD_CODE, pm.PAC_HEAD_DESC, pm.PAC_HEAD_ID*/) x\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT PAC_HEAD_CODE,\r\n" + 
    		"               sum(1stqutr) 1stqutr,\r\n" + 
    		"               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" + 
    		"                  2ndqutr,\r\n" + 
    		"               (  coalesce(sum(1stqutr), 0)\r\n" + 
    		"                + coalesce(sum(2ndqutr), 0)\r\n" + 
    		"                + coalesce(sum(3rdqutr), 0))\r\n" + 
    		"                  3rdqutr,\r\n" + 
    		"               sum(AMOUNT) TOTAL\r\n" + 
    		"          FROM (SELECT P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE,\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('04', '05', '06')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)   END    '1stqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('07', '08', '09')\r\n" + 
    		"                          THEN SUM(P.AMOUNT)  END      '2ndqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('10', '11', '12')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)  END  '3rdqutr',\r\n" + 
    		"                       SUM(P.AMOUNT) AMOUNT\r\n" + 
    		"                    FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                                       SAC_HEAD_ID,\r\n" + 
    		"                                       SUM(RCPTAMT) AS AMOUNT,\r\n" + 
    		"                                       PAC_HEAD_ID,PAC_HEAD_DESC,PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                       VOU_POSTING_DATE\r\n" + 
    		"                                  FROM (SELECT R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID,\r\n" + 
    		"                                               VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE,\r\n" + 
    		"                                               (  SUM(VD.VAMT_CR)\r\n" + 
    		"                                                - SUM(VD.VAMT_DR))\r\n" + 
    		"                                                  RCPTAMT\r\n" + 
    		"                                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                                          TB_AC_SECONDARYHEAD_MASTER SD,\r\n" + 
    		"                                          TB_COMPARAM_DET D,TB_COMPARAM_MAS M,\r\n" + 
    		"                                          TB_COMPARAM_DET E,TB_COMPARAM_MAS F,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER B,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"                                         WHERE VD.VOU_SUBTYPE_CPD_ID= D.CPD_ID\r\n" + 
    		"                                               AND D.CPM_ID =M.CPM_ID\r\n" + 
    		"                                               AND M.CPM_PREFIX = 'TDP'\r\n" + 
    		"                                               AND D.CPD_VALUE IN ('RRE','RV','RDC','ASE')\r\n" + 
    		"                                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                                               AND SD.SAC_LED_TYPE=E.CPD_ID\r\n" + 
    		"                                               AND  E.CPM_ID= F.CPM_ID\r\n" + 
    		"                                               AND F.CPM_PREFIX ='FTY'\r\n" + 
    		"                                               AND E.CPD_VALUE IN ('VD','OT')\r\n" + 
    		"                                             AND VD.VOU_POSTING_DATE BETWEEN :fromDates\r\n" + 
    		"                                                                            AND :todates\r\n" + 
    		"                                              AND VD.ORGID =:orgId\r\n" + 
    		"                                              AND  SD.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"                                              AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"                                              AND B.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                        GROUP BY R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID, VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE) A\r\n" + 
    		"                                 GROUP BY SAC_HEAD_ID,AC_HEAD_CODE,\r\n" + 
    		"                                 PAC_HEAD_DESC,\r\n" + 
    		"                                 PAC_HEAD_ID,\r\n" + 
    		"                                 PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                 VOU_POSTING_DATE) P\r\n" + 
    		"                        GROUP BY P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE) z\r\n" + 
    		"        GROUP BY PAC_HEAD_CODE) y\r\n" + 
    		"          ON x.PAC_HEAD_CODE = y.PAC_HEAD_CODE\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT e.PAC_HEAD_CODE,\r\n" + 
    		"               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" + 
    		"               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" + 
    		"          FROM TB_AC_PROJECTEDREVENUE a,\r\n" + 
    		"               TB_AC_BUDGETCODE_MAS b,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER c,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER d,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER e\r\n" + 
    		"         WHERE     FA_YEARID =:financialYearId\r\n" + 
    		"               AND a.ORGID =:orgId\r\n" + 
    		"               AND a.ORGID = b.ORGID\r\n" + 
    		"               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + 
    		"               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" + 
    		"               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" + 
    		"               AND c.PAC_HEAD_PARENT_ID = e.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY e.PAC_HEAD_CODE) z\r\n" + 
    		"          ON x.PAC_HEAD_CODE = z.PAC_HEAD_CODE",nativeQuery=true)
    List<Object[]> getRevenueExpenditureReportData(@Param("orgId") Long orgId, @Param("fromDates") Date fromDates,
            @Param("todates") Date todates, @Param("financialYearId") Long financialYearId,
            @Param("lookUpCode") String lookUpCode);
    
    @Query(value ="SELECT x.PAC_HEAD_CODE,\r\n" + 
    		"       x.PAC_HEAD_DESC,\r\n" + 
    		"       x.PAC_HEAD_ID,\r\n" + 
    		"       z.ORGINAL_ESTAMT2,\r\n" + 
    		"       z.REVISED_ESTAMT,\r\n" + 
    		"       y.1stqutr,\r\n" + 
    		"       y.2ndqutr,\r\n" + 
    		"       y.3rdqutr,\r\n" + 
    		"       y.TOTAL\r\n" + 
    		"  FROM (SELECT pm.PAC_HEAD_CODE,\r\n" + 
    		"               pm.PAC_HEAD_DESC,\r\n" + 
    		"               pm.PAC_HEAD_ID\r\n" + 
    		"           FROM tb_ac_primaryhead_master pm\r\n" + 
    		"           WHERE  pm.PAC_HEAD_PARENT_ID IS NULL\r\n" + 
    		"           AND pm.CPD_ID_ACHEADTYPES IN\r\n" + 
    		"            (SELECT cpd_id   FROM tb_comparam_det  WHERE cpd_value=:lookUpCode\r\n" + 
    		"           AND cpm_id IN (SELECT cpm_id   FROM tb_comparam_mas m    WHERE m.CPM_PREFIX = 'COA'))\r\n" + 
    		"        /*GROUP BY pm.PAC_HEAD_CODE, pm.PAC_HEAD_DESC, pm.PAC_HEAD_ID*/) x\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT PAC_HEAD_CODE,\r\n" + 
    		"               sum(1stqutr) 1stqutr,\r\n" + 
    		"               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" + 
    		"                  2ndqutr,\r\n" + 
    		"               (  coalesce(sum(1stqutr), 0)\r\n" + 
    		"                + coalesce(sum(2ndqutr), 0)\r\n" + 
    		"                + coalesce(sum(3rdqutr), 0))\r\n" + 
    		"                  3rdqutr,\r\n" + 
    		"               sum(AMOUNT) TOTAL\r\n" + 
    		"          FROM (SELECT P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE,\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('04', '05', '06')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)   END    '1stqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('07', '08', '09')\r\n" + 
    		"                          THEN SUM(P.AMOUNT)  END      '2ndqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('10', '11', '12')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)  END  '3rdqutr',\r\n" + 
    		"                       SUM(P.AMOUNT) AMOUNT\r\n" + 
    		"                    FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                                       SAC_HEAD_ID,\r\n" + 
    		"                                       SUM(RCPTAMT) AS AMOUNT,\r\n" + 
    		"                                       PAC_HEAD_ID,PAC_HEAD_DESC,PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                       VOU_POSTING_DATE\r\n" + 
    		"                                  FROM (SELECT R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID,\r\n" + 
    		"                                               VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE,\r\n" + 
    		"                                               (  SUM(VD.VAMT_CR)\r\n" + 
    		"                                                - SUM(VD.VAMT_DR))\r\n" + 
    		"                                                  RCPTAMT\r\n" + 
    		"                                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                                          TB_AC_SECONDARYHEAD_MASTER SD,\r\n" + 
    		"                                          TB_COMPARAM_DET D,TB_COMPARAM_MAS M,\r\n" + 
    		"                                          TB_COMPARAM_DET E,TB_COMPARAM_MAS F,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER B,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"                                         WHERE VD.VOU_SUBTYPE_CPD_ID= D.CPD_ID\r\n" + 
    		"                                               AND D.CPM_ID =M.CPM_ID\r\n" + 
    		"                                               AND M.CPM_PREFIX = 'TDP'\r\n" + 
    		"                                               AND D.CPD_VALUE IN ('RRE','RV','RDC','ASE')\r\n" + 
    		"                                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                                               AND SD.SAC_LED_TYPE=E.CPD_ID\r\n" + 
    		"                                               AND  E.CPM_ID= F.CPM_ID\r\n" + 
    		"                                               AND F.CPM_PREFIX ='FTY'\r\n" + 
    		"                                               AND E.CPD_VALUE IN ('VD','OT')\r\n" + 
    		"                                             AND VD.VOU_POSTING_DATE BETWEEN :fromDates\r\n" + 
    		"                                                                            AND :todates\r\n" + 
    		"                                              AND VD.ORGID = :orgId\r\n" + 
    		"                                              AND  SD.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"                                              AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"                                              AND B.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                        GROUP BY R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                               VD.SAC_HEAD_ID, VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE) A\r\n" + 
    		"                                 GROUP BY SAC_HEAD_ID,AC_HEAD_CODE,\r\n" + 
    		"                                 PAC_HEAD_DESC,\r\n" + 
    		"                                 PAC_HEAD_ID,\r\n" + 
    		"                                 PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                 VOU_POSTING_DATE) P\r\n" + 
    		"                        GROUP BY P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE) z\r\n" + 
    		"        GROUP BY PAC_HEAD_CODE) y\r\n" + 
    		"          ON x.PAC_HEAD_CODE = y.PAC_HEAD_CODE\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT c.PAC_HEAD_CODE,\r\n" + 
    		"               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" + 
    		"               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" + 
    		"          FROM TB_AC_PROJECTEDREVENUE a,\r\n" + 
    		"               TB_AC_BUDGETCODE_MAS b,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER c,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER d/*,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER e*/\r\n" + 
    		"         WHERE     FA_YEARID = :financialYearId\r\n" + 
    		"               AND a.ORGID = :orgId\r\n" + 
    		"               AND a.ORGID = b.ORGID\r\n" + 
    		"               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + 
    		"               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" + 
    		"               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" + 
    		"               -- AND c.PAC_HEAD_PARENT_ID = e.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY c.PAC_HEAD_CODE) z\r\n" + 
    		"          ON x.PAC_HEAD_CODE = z.PAC_HEAD_CODE",nativeQuery=true)
    List<Object[]> getRevenueExpenditureReportDataTSCL(@Param("orgId") Long orgId, @Param("fromDates") Date fromDates,
            @Param("todates") Date todates, @Param("financialYearId") Long financialYearId,
            @Param("lookUpCode") String lookUpCode);

    /*@Query(value = "SELECT x.PAC_HEAD_CODE,\r\n" +
            "       x.PAC_HEAD_DESC,\r\n" +
            "       x.PAC_HEAD_ID,\r\n" +
            "       z.ORGINAL_ESTAMT2,\r\n" +
            "       z.REVISED_ESTAMT,\r\n" +
            "       Y.1STQUTR,\r\n" +
            "       Y.2NDQUTR,\r\n" +
            "       Y.3RDQUTR,\r\n" +
            "       Y.TOTAL\r\n" +
            "  FROM (SELECT PM.PAC_HEAD_CODE,\r\n" +
            "               PM.PAC_HEAD_DESC,\r\n" +
            "               PM.PAC_HEAD_ID,\r\n" +
            "               SUM(AMT.ORGINAL_ESTAMT),\r\n" +
            "               (SELECT SUM(P.AMOUNT)\r\n" +
            "                  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                               A.PAC_HEAD_DESC,\r\n" +
            "                               A.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "                               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "                               VOU_POSTING_DATE,\r\n" +
            "                               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" +
            "                          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                                       SAC_HEAD_ID,\r\n" +
            "                                       RCPTAMT,\r\n" +
            "                                       A.PAC_HEAD_ID,\r\n" +
            "                                       VOU_POSTING_DATE\r\n" +
            "                                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
            "                                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                               VD.VOU_POSTING_DATE,\r\n" +
            "                                               (  SUM(VD.VAMT_DR)\r\n" +
            "                                                - SUM(VD.VAMT_CR))\r\n" +
            "                                                  RCPTAMT\r\n" +
            "                                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'TDP')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('PVE',\r\n" +
            "                                                                      'RBP'))\r\n" +
            "                                               AND VD.SAC_HEAD_ID =\r\n" +
            "                                                      SD.SAC_HEAD_ID\r\n" +
            "                                               AND SD.SAC_LED_TYPE IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'FTY')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('VD',\r\n" +
            "                                                                      'OT'))\r\n" +
            "                                               AND VD.VOU_POSTING_DATE BETWEEN :fromDates\r\n" +
            "                                                                           AND :todates\r\n" +
            "                                               AND VD.ORGID =:orgId\r\n" +
            "                                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                                 VD.SAC_HEAD_ID,\r\n" +
            "                                                 VD.PAC_HEAD_ID,\r\n" +
            "                                                 VD.VOU_POSTING_DATE) A) AAA,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "                               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "                        GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_DESC,\r\n" +
            "                                 B.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                                 AAA.VOU_POSTING_DATE) P,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            "                 WHERE     P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "                       AND R.PAC_HEAD_CODE = PM.PAC_HEAD_CODE\r\n" +
            "                GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE)\r\n" +
            "                  TRANS_AMOUNT\r\n" +
            "          FROM TB_AC_PRIMARYHEAD_MASTER PM\r\n" +
            "               LEFT JOIN\r\n" +
            "               (SELECT A.PAC_HEAD_ID, SUM(B.ORGINAL_ESTAMT) ORGINAL_ESTAMT\r\n" +
            "                  FROM (SELECT BUDGETCODE_ID, PAC_HEAD_ID\r\n" +
            "                          FROM TB_AC_BUDGETCODE_MAS BM\r\n" +
            "                         WHERE BM.ORGID =:orgId) A,\r\n" +
            "                       TB_AC_PROJECTEDREVENUE B\r\n" +
            "                 WHERE     B.BUDGETCODE_ID = A.BUDGETCODE_ID\r\n" +
            "                       AND B.ORGID =:orgId\r\n" +
            "                       AND B.FA_YEARID =:financialYearId\r\n" +
            "                GROUP BY A.PAC_HEAD_ID) AMT\r\n" +
            "                  ON PM.PAC_HEAD_ID = AMT.PAC_HEAD_ID\r\n" +
            "         WHERE     PM.PAC_HEAD_PARENT_ID IS NULL\r\n" +
            "               AND PM.CPD_ID_ACHEADTYPES IN\r\n" +
            "                      (SELECT CPD_ID\r\n" +
            "                         FROM TB_COMPARAM_DET\r\n" +
            "                        WHERE     CPD_VALUE IN ('A', 'L')\r\n" +
            "                              AND CPM_ID IN (SELECT CPM_ID\r\n" +
            "                                               FROM TB_COMPARAM_MAS M\r\n" +
            "                                              WHERE M.CPM_PREFIX = 'COA'))\r\n" +
            "        GROUP BY PM.PAC_HEAD_CODE, PM.PAC_HEAD_DESC, PM.PAC_HEAD_ID) X\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT PAC_HEAD_CODE,\r\n" +
            "               SUM(1STQUTR) 1STQUTR,\r\n" +
            "               (COALESCE(SUM(1STQUTR), 0) + COALESCE(SUM(2NDQUTR), 0))\r\n" +
            "                  2NDQUTR,\r\n" +
            "               (  COALESCE(SUM(1STQUTR), 0)\r\n" +
            "                + COALESCE(SUM(2NDQUTR), 0)\r\n" +
            "                + COALESCE(SUM(3RDQUTR), 0))\r\n" +
            "                  3RDQUTR,\r\n" +
            "               SUM(AMOUNT) TOTAL\r\n" +
            "          FROM (SELECT R.PAC_HEAD_COMPO_CODE,\r\n" +
            "                       R.PAC_HEAD_CODE,\r\n" +
            "                       R.PAC_HEAD_DESC,\r\n" +
            "                       VOU_POSTING_DATE,\r\n" +
            "                       CASE\r\n" +
            "                          WHEN DATE_FORMAT(VOU_POSTING_DATE, '%M') IN\r\n" +
            "                                  ('04', '05', '06')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '1STQUTR',\r\n" +
            "                       CASE\r\n" +
            "                          WHEN DATE_FORMAT(VOU_POSTING_DATE, '%M') IN\r\n" +
            "                                  ('07', '08', '09')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '2NDQUTR',\r\n" +
            "                       CASE\r\n" +
            "                          WHEN DATE_FORMAT(VOU_POSTING_DATE, '%M') IN\r\n" +
            "                                  ('10', '11', '12')\r\n" +
            "                          THEN\r\n" +
            "                             SUM(P.AMOUNT)\r\n" +
            "                       END\r\n" +
            "                          '3RDQUTR',\r\n" +
            "                       SUM(P.AMOUNT) AMOUNT\r\n" +
            "                  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                               A.PAC_HEAD_DESC,\r\n" +
            "                               A.PAC_HEAD_ID AS LEVEL2,\r\n" +
            "                               B.PAC_HEAD_ID AS LEVEL1,\r\n" +
            "                               VOU_POSTING_DATE,\r\n" +
            "                               SUM(AAA.RCPTAMT) AS AMOUNT\r\n" +
            "                          FROM (SELECT AC_HEAD_CODE,\r\n" +
            "                                       SAC_HEAD_ID,\r\n" +
            "                                       RCPTAMT,\r\n" +
            "                                       A.PAC_HEAD_ID,\r\n" +
            "                                       VOU_POSTING_DATE\r\n" +
            "                                  FROM (SELECT VD.PAC_HEAD_ID,\r\n" +
            "                                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" +
            "                                               VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" +
            "                                               VD.VOU_POSTING_DATE,\r\n" +
            "                                               (  SUM(VD.VAMT_DR)\r\n" +
            "                                                - SUM(VD.VAMT_CR))\r\n" +
            "                                                  RCPTAMT\r\n" +
            "                                          FROM VW_VOUCHER_DETAIL VD,\r\n" +
            "                                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" +
            "                                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'TDP')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('PVE',\r\n" +
            "                                                                      'RBP'))\r\n" +
            "                                               AND VD.SAC_HEAD_ID =\r\n" +
            "                                                      SD.SAC_HEAD_ID\r\n" +
            "                                               AND SD.SAC_LED_TYPE IN\r\n" +
            "                                                      (SELECT CPD_ID\r\n" +
            "                                                         FROM TB_COMPARAM_DET\r\n" +
            "                                                              D\r\n" +
            "                                                        WHERE     D.CPM_ID IN\r\n" +
            "                                                                     (SELECT CPM_ID\r\n" +
            "                                                                        FROM TB_COMPARAM_MAS\r\n" +
            "                                                                             M\r\n" +
            "                                                                       WHERE M.CPM_PREFIX =\r\n" +
            "                                                                                'FTY')\r\n" +
            "                                                              AND CPD_VALUE IN\r\n" +
            "                                                                     ('VD',\r\n" +
            "                                                                      'OT'))\r\n" +
            "                                               AND VD.VOU_POSTING_DATE BETWEEN :fromDates\r\n" +
            "                                                                           AND :todates\r\n" +
            "                                               AND VD.ORGID =:orgId\r\n" +
            "                                        GROUP BY VD.AC_HEAD_CODE,\r\n" +
            "                                                 VD.SAC_HEAD_ID,\r\n" +
            "                                                 VD.PAC_HEAD_ID,\r\n" +
            "                                                 VD.VOU_POSTING_DATE) A) AAA,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER A,\r\n" +
            "                               TB_AC_PRIMARYHEAD_MASTER B\r\n" +
            "                         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" +
            "                               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" +
            "                        GROUP BY A.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_DESC,\r\n" +
            "                                 B.PAC_HEAD_ID,\r\n" +
            "                                 A.PAC_HEAD_COMPO_CODE,\r\n" +
            "                                 AAA.VOU_POSTING_DATE) P,\r\n" +
            "                       TB_AC_PRIMARYHEAD_MASTER R\r\n" +
            "                 WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" +
            "                GROUP BY LEVEL1,\r\n" +
            "                         R.PAC_HEAD_DESC,\r\n" +
            "                         R.PAC_HEAD_COMPO_CODE,\r\n" +
            "                         P.VOU_POSTING_DATE) Z\r\n" +
            "        GROUP BY PAC_HEAD_CODE) Y\r\n" +
            "          ON X.PAC_HEAD_CODE = Y.PAC_HEAD_CODE\r\n" +
            "       LEFT JOIN\r\n" +
            "       (SELECT C.PAC_HEAD_CODE,\r\n" +
            "               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" +
            "               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" +
            "          FROM TB_AC_PROJECTED_EXPENDITURE A,\r\n" +
            "               TB_AC_BUDGETCODE_MAS B,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER C,\r\n" +
            "               TB_AC_PRIMARYHEAD_MASTER D\r\n" +
            "         WHERE     FA_YEARID =:financialYearId\r\n" +
            "               AND A.ORGID =:orgId\r\n" +
            "               AND A.ORGID = B.ORGID\r\n" +
            "               AND A.BUDGETCODE_ID = B.BUDGETCODE_ID\r\n" +
            "               AND B.PAC_HEAD_ID = D.PAC_HEAD_ID\r\n" +
            "               AND D.PAC_HEAD_PARENT_ID = C.PAC_HEAD_ID\r\n" +
            "        GROUP BY C.PAC_HEAD_CODE) Z\r\n" +
            "          ON X.PAC_HEAD_CODE = Z.PAC_HEAD_CODE", nativeQuery = true)*/
    @Query(value = "SELECT x.PAC_HEAD_CODE,\r\n" + 
    		"       x.PAC_HEAD_DESC,\r\n" + 
    		"       x.PAC_HEAD_ID,\r\n" + 
    		"       z.ORGINAL_ESTAMT2,\r\n" + 
    		"       z.REVISED_ESTAMT,\r\n" + 
    		"       Y.1STQUTR,\r\n" + 
    		"       Y.2NDQUTR,\r\n" + 
    		"       Y.3RDQUTR,\r\n" + 
    		"       Y.TOTAL\r\n" + 
    		"  FROM (SELECT PM.PAC_HEAD_CODE,\r\n" + 
    		"               PM.PAC_HEAD_DESC,\r\n" + 
    		"               PM.PAC_HEAD_ID\r\n" + 
    		"          FROM TB_AC_PRIMARYHEAD_MASTER PM\r\n" + 
    		"          WHERE PM.PAC_HEAD_PARENT_ID IS NULL\r\n" + 
    		"               AND PM.CPD_ID_ACHEADTYPES IN\r\n" + 
    		"               (SELECT CPD_ID  FROM TB_COMPARAM_DET WHERE CPD_VALUE IN ('A', 'L')\r\n" + 
    		"               AND CPM_ID IN (SELECT CPM_ID FROM TB_COMPARAM_MAS M  WHERE M.CPM_PREFIX = 'COA'))\r\n" + 
    		"       ) X\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT PAC_HEAD_CODE,\r\n" + 
    		"               sum(1stqutr) 1stqutr,\r\n" + 
    		"               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" + 
    		"                  2ndqutr,\r\n" + 
    		"               (  coalesce(sum(1stqutr), 0)\r\n" + 
    		"                + coalesce(sum(2ndqutr), 0)\r\n" + 
    		"                + coalesce(sum(3rdqutr), 0))\r\n" + 
    		"                  3rdqutr,\r\n" + 
    		"               sum(AMOUNT) TOTAL\r\n" + 
    		"          FROM (SELECT P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE,\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('04', '05', '06')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)   END    '1stqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('07', '08', '09')\r\n" + 
    		"                          THEN SUM(P.AMOUNT)  END      '2ndqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('10', '11', '12')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)  END  '3rdqutr',\r\n" + 
    		"                       SUM(P.AMOUNT) AMOUNT\r\n" + 
    		"                    FROM (SELECT     -- SAC_HEAD_ID,\r\n" + 
    		"                                       SUM(RCPTAMT) AS AMOUNT,\r\n" + 
    		"                                       PAC_HEAD_ID,PAC_HEAD_DESC,PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                       VOU_POSTING_DATE\r\n" + 
    		"                                  FROM (SELECT R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"--                                                VD.SAC_HEAD_ID,\r\n" + 
    		"--                                                VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE,\r\n" + 
    		"                                               (  SUM(VD.VAMT_CR)\r\n" + 
    		"                                                - SUM(VD.VAMT_DR))\r\n" + 
    		"                                                  RCPTAMT\r\n" + 
    		"                                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                                          TB_AC_SECONDARYHEAD_MASTER SD,\r\n" + 
    		"                                          TB_COMPARAM_DET D,TB_COMPARAM_MAS M,\r\n" + 
    		"                                          TB_COMPARAM_DET E,TB_COMPARAM_MAS F,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER B,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"                                         WHERE VD.VOU_SUBTYPE_CPD_ID= D.CPD_ID\r\n" + 
    		"                                               AND D.CPM_ID =M.CPM_ID\r\n" + 
    		"                                               AND M.CPM_PREFIX = 'TDP'\r\n" + 
    		"                                               AND D.CPD_VALUE IN ('PVE','RBP')\r\n" + 
    		"                                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                                               AND SD.SAC_LED_TYPE=E.CPD_ID\r\n" + 
    		"                                               AND  E.CPM_ID= F.CPM_ID\r\n" + 
    		"                                               AND F.CPM_PREFIX ='FTY'\r\n" + 
    		"                                               AND E.CPD_VALUE IN ('VD','OT')\r\n" + 
    		"                                            AND VD.VOU_POSTING_DATE BETWEEN :fromDates  AND :todates\r\n" + 
    		"                                              AND VD.ORGID =:orgId\r\n" + 
    		"                                              AND  SD.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"                                              AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"                                              AND B.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                        GROUP BY R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                              R.PAC_HEAD_CODE,R.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE) A\r\n" + 
    		"                                 GROUP BY -- SAC_HEAD_ID,AC_HEAD_CODE,\r\n" + 
    		"                                 PAC_HEAD_DESC,\r\n" + 
    		"                                 PAC_HEAD_ID,\r\n" + 
    		"                                 PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                 VOU_POSTING_DATE) P\r\n" + 
    		"                        GROUP BY P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE) z\r\n" + 
    		"        GROUP BY PAC_HEAD_CODE) Y\r\n" + 
    		"          ON X.PAC_HEAD_CODE = Y.PAC_HEAD_CODE\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT e.PAC_HEAD_CODE,\r\n" + 
    		"               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" + 
    		"               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" + 
    		"          FROM TB_AC_PROJECTED_EXPENDITURE  a,\r\n" + 
    		"               TB_AC_BUDGETCODE_MAS b,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER c,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER d,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER e\r\n" + 
    		"         WHERE    FA_YEARID =:financialYearId\r\n" + 
    		"               AND a.ORGID =:orgId\r\n" + 
    		"               AND a.ORGID = b.ORGID\r\n" + 
    		"               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + 
    		"               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" + 
    		"               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" + 
    		"               AND c.PAC_HEAD_PARENT_ID = e.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY e.PAC_HEAD_CODE) Z\r\n" + 
    		"          ON X.PAC_HEAD_CODE = Z.PAC_HEAD_CODE\r\n" + 
    		"",nativeQuery=true)
    List<Object[]> getCapitalExpenditureReportData(@Param("orgId") Long orgId, @Param("fromDates") Date fromDates,
            @Param("todates") Date todates, @Param("financialYearId") Long financialYearId);
    
    @Query(value = "SELECT x.PAC_HEAD_CODE,\r\n" + 
    		"       x.PAC_HEAD_DESC,\r\n" + 
    		"       x.PAC_HEAD_ID,\r\n" + 
    		"       z.ORGINAL_ESTAMT2,\r\n" + 
    		"       z.REVISED_ESTAMT,\r\n" + 
    		"       Y.1STQUTR,\r\n" + 
    		"       Y.2NDQUTR,\r\n" + 
    		"       Y.3RDQUTR,\r\n" + 
    		"       Y.TOTAL\r\n" + 
    		"  FROM (SELECT PM.PAC_HEAD_CODE,\r\n" + 
    		"               PM.PAC_HEAD_DESC,\r\n" + 
    		"               PM.PAC_HEAD_ID\r\n" + 
    		"          FROM TB_AC_PRIMARYHEAD_MASTER PM\r\n" + 
    		"          WHERE PM.PAC_HEAD_PARENT_ID IS NULL\r\n" + 
    		"               AND PM.CPD_ID_ACHEADTYPES IN\r\n" + 
    		"               (SELECT CPD_ID  FROM TB_COMPARAM_DET WHERE CPD_VALUE IN ('A', 'L')\r\n" + 
    		"               AND CPM_ID IN (SELECT CPM_ID FROM TB_COMPARAM_MAS M  WHERE M.CPM_PREFIX = 'COA'))\r\n" + 
    		"       ) X\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT PAC_HEAD_CODE,\r\n" + 
    		"               sum(1stqutr) 1stqutr,\r\n" + 
    		"               (coalesce(sum(1stqutr), 0) + coalesce(sum(2ndqutr), 0))\r\n" + 
    		"                  2ndqutr,\r\n" + 
    		"               (  coalesce(sum(1stqutr), 0)\r\n" + 
    		"                + coalesce(sum(2ndqutr), 0)\r\n" + 
    		"                + coalesce(sum(3rdqutr), 0))\r\n" + 
    		"                  3rdqutr,\r\n" + 
    		"               sum(AMOUNT) TOTAL\r\n" + 
    		"          FROM (SELECT P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE,\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('04', '05', '06')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)   END    '1stqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('07', '08', '09')\r\n" + 
    		"                          THEN SUM(P.AMOUNT)  END      '2ndqutr',\r\n" + 
    		"                       CASE  WHEN date_format(VOU_POSTING_DATE, '%m') IN\r\n" + 
    		"                                  ('10', '11', '12')\r\n" + 
    		"                          THEN  SUM(P.AMOUNT)  END  '3rdqutr',\r\n" + 
    		"                       SUM(P.AMOUNT) AMOUNT\r\n" + 
    		"                    FROM (SELECT     -- SAC_HEAD_ID,\r\n" + 
    		"                                       SUM(RCPTAMT) AS AMOUNT,\r\n" + 
    		"                                       PAC_HEAD_ID,PAC_HEAD_DESC,PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                       VOU_POSTING_DATE\r\n" + 
    		"                                  FROM (SELECT R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"--                                                VD.SAC_HEAD_ID,\r\n" + 
    		"--                                                VD.AC_HEAD_CODE,\r\n" + 
    		"                                               R.PAC_HEAD_COMPO_CODE,R.PAC_HEAD_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE,\r\n" + 
    		"                                               (  SUM(VD.VAMT_CR)\r\n" + 
    		"                                                - SUM(VD.VAMT_DR))\r\n" + 
    		"                                                  RCPTAMT\r\n" + 
    		"                                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                                          TB_AC_SECONDARYHEAD_MASTER SD,\r\n" + 
    		"                                          TB_COMPARAM_DET D,TB_COMPARAM_MAS M,\r\n" + 
    		"                                          TB_COMPARAM_DET E,TB_COMPARAM_MAS F,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R/*,\r\n" + 
    		"                                          TB_AC_PRIMARYHEAD_MASTER R*/\r\n" + 
    		"                                         WHERE VD.VOU_SUBTYPE_CPD_ID= D.CPD_ID\r\n" + 
    		"                                               AND D.CPM_ID =M.CPM_ID\r\n" + 
    		"                                               AND M.CPM_PREFIX = 'TDP'\r\n" + 
    		"                                               AND D.CPD_VALUE IN ('PVE','RBP')\r\n" + 
    		"                                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
    		"                                               AND SD.SAC_LED_TYPE=E.CPD_ID\r\n" + 
    		"                                               AND  E.CPM_ID= F.CPM_ID\r\n" + 
    		"                                               AND F.CPM_PREFIX ='FTY'\r\n" + 
    		"                                               AND E.CPD_VALUE IN ('VD','OT')\r\n" + 
    		"                                            AND VD.VOU_POSTING_DATE BETWEEN :fromDates  AND :todates\r\n" + 
    		"                                              AND VD.ORGID = :orgId\r\n" + 
    		"                                              AND  SD.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"                                              AND A.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                              -- AND B.PAC_HEAD_PARENT_ID = R.PAC_HEAD_ID\r\n" + 
    		"                                        GROUP BY R.PAC_HEAD_ID,R.PAC_HEAD_DESC,\r\n" + 
    		"                                              R.PAC_HEAD_CODE,R.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                                               vd.VOU_POSTING_DATE) A\r\n" + 
    		"                                 GROUP BY -- SAC_HEAD_ID,AC_HEAD_CODE,\r\n" + 
    		"                                 PAC_HEAD_DESC,\r\n" + 
    		"                                 PAC_HEAD_ID,\r\n" + 
    		"                                 PAC_HEAD_COMPO_CODE,PAC_HEAD_CODE,\r\n" + 
    		"                                 VOU_POSTING_DATE) P\r\n" + 
    		"                        GROUP BY P.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"                       P.PAC_HEAD_CODE,\r\n" + 
    		"                       P.PAC_HEAD_DESC,\r\n" + 
    		"                       VOU_POSTING_DATE) z\r\n" + 
    		"        GROUP BY PAC_HEAD_CODE) Y\r\n" + 
    		"          ON X.PAC_HEAD_CODE = Y.PAC_HEAD_CODE\r\n" + 
    		"       LEFT JOIN\r\n" + 
    		"       (SELECT c.PAC_HEAD_CODE,\r\n" + 
    		"               SUM(ORGINAL_ESTAMT) ORGINAL_ESTAMT2,\r\n" + 
    		"               SUM(REVISED_ESTAMT) REVISED_ESTAMT\r\n" + 
    		"          FROM TB_AC_PROJECTED_EXPENDITURE  a,\r\n" + 
    		"               TB_AC_BUDGETCODE_MAS b,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER c,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER d/*,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER e*/\r\n" + 
    		"         WHERE    FA_YEARID = :financialYearId\r\n" + 
    		"               AND a.ORGID = :orgId\r\n" + 
    		"               AND a.ORGID = b.ORGID\r\n" + 
    		"               AND a.BUDGETCODE_ID = b.BUDGETCODE_ID\r\n" + 
    		"               AND b.PAC_HEAD_ID = d.PAC_HEAD_ID\r\n" + 
    		"               AND d.PAC_HEAD_PARENT_ID = c.PAC_HEAD_ID\r\n" + 
    		"               -- AND c.PAC_HEAD_PARENT_ID = e.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY c.PAC_HEAD_CODE) Z\r\n" + 
    		"          ON X.PAC_HEAD_CODE = Z.PAC_HEAD_CODE\r\n" + 
    		"",nativeQuery=true)
    List<Object[]> getCapitalExpenditureReportDataTSCL(@Param("orgId") Long orgId, @Param("fromDates") Date fromDates,
            @Param("todates") Date todates, @Param("financialYearId") Long financialYearId);
  
     //Commented below against #133593 query shared by jitendra
    /*@Query(value = "SELECT DT.VOU_ID,\r\n" +
            "       VM.VOU_POSTING_DATE,\r\n" +
            "       VM.VOU_NO,\r\n" +
            "       SM.AC_HEAD_CODE,\r\n" +
            "       VM.NARRATION,\r\n" +
            "       DT.VOUDET_AMT,\r\n" +
            "       VM.VOU_TYPE_CPD_ID,\r\n" +
            "       DT.DRCR_CPD_ID,\r\n" +
            "       sm.SAC_HEAD_ID as CR_SAC_HEAD_ID,\r\n" +
            "      (select SAC_HEAD_ID from tb_ac_voucher_det where VOU_ID=DT.VOU_ID and DRCR_CPD_ID=:drId) as DR_SAC_HEAD_ID\r\n"+
            "  FROM TB_AC_VOUCHER_DET DT, TB_AC_VOUCHER VM, TB_AC_SECONDARYHEAD_MASTER SM\r\n" +
            "  WHERE DT.VOU_ID = VM.VOU_ID\r\n" +
            "  AND SM.SAC_HEAD_ID = DT.SAC_HEAD_ID\r\n" +
            "  AND VM.VOU_POSTING_DATE=:fromDateId\r\n" + #129376
           "  AND VM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" +
            "  AND DT.DRCR_CPD_ID =:crId\r\n" +
            "  AND DT.ORGID =:orgid\r\n" +
            "  ORDER BY  VM.VOU_ID", nativeQuery = true)*/
    @Query(value = "SELECT DT.VOU_ID,\r\n" + 
    		"       VM.VOU_POSTING_DATE,\r\n" + 
    		"       VM.VOU_NO,\r\n" + 
    		"       SM.AC_HEAD_CODE,\r\n" + 
    		"       VM.NARRATION,\r\n" + 
    		"       DT.VOUDET_AMT,\r\n" + 
    		"       VM.VOU_TYPE_CPD_ID,\r\n" + 
    		"       DT.DRCR_CPD_ID,\r\n" + 
    		"       sm.SAC_HEAD_ID as CR_SAC_HEAD_ID\r\n" + 
    		"  FROM TB_AC_VOUCHER_DET DT, TB_AC_VOUCHER VM, TB_AC_SECONDARYHEAD_MASTER SM\r\n" + 
    		"  WHERE DT.VOU_ID = VM.VOU_ID\r\n" + 
    		"  AND SM.SAC_HEAD_ID = DT.SAC_HEAD_ID\r\n" + 
    		"   AND VM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" + 
    		"  AND DT.DRCR_CPD_ID  =:crId  \r\n" + 
    		"  AND DT.ORGID =:orgid\r\n" + 
    		"  UNION\r\n" + 
    		"  SELECT CT.VOU_ID,\r\n" + 
    		"       CM.VOU_POSTING_DATE,\r\n" + 
    		"       CM.VOU_NO,\r\n" + 
    		"       CSM.AC_HEAD_CODE,\r\n" + 
    		"       CM.NARRATION,\r\n" + 
    		"       CT.VOUDET_AMT,\r\n" + 
    		"       CM.VOU_TYPE_CPD_ID,\r\n" + 
    		"       CT.DRCR_CPD_ID,\r\n" + 
    		"       csm.SAC_HEAD_ID as CR_SAC_HEAD_ID\r\n" + 
    		"  FROM TB_AC_VOUCHER_DET CT, TB_AC_VOUCHER CM, TB_AC_SECONDARYHEAD_MASTER CSM\r\n" + 
    		"  WHERE CT.VOU_ID = CM.VOU_ID\r\n" + 
    		"  AND CSM.SAC_HEAD_ID = CT.SAC_HEAD_ID\r\n" + 
    		"  AND CM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" + 
    		"  AND CT.DRCR_CPD_ID =:drId  \r\n" + 
    		"  AND CT.ORGID =:orgid\r\n" + 
    		"  ORDER BY  VOU_ID", nativeQuery = true)
    List<Object[]> getReceiptBookReportData(@Param("orgid") Long orgid, @Param("fromDateId") Date fromDateId,@Param("toDateId") Date toDateId,
            @Param("drId") Long drId, @Param("crId") Long crId);

    @Query(value = "SELECT distinct SAC_HEAD_ID \r\n" +
            "FROM TB_AC_VOUCHERTEMPLATE_DET\r\n" +
            "WHERE CPD_ID_DRCR in (select cpd_id from tb_comparam_det where cpm_id in (select cpm_id from tb_comparam_mas where cpm_prefix='DCR') and cpd_value='DR')\r\n"
            +
            "AND CPD_ID_PAY_MODE in (select cpd_id from tb_comparam_det where cpm_id in (               \r\n" +
            "select cpm_id from tb_comparam_mas where cpm_prefix='PAY') and cpd_value='C')\r\n" +
            "AND TEMPLATE_ID IN (SELECT VTM.TEMPLATE_ID FROM TB_AC_VOUCHERTEMPLATE_MAS VTM\r\n" +
            "WHERE VTM.ORGID =:orgid \r\n" +
            "AND VTM.DP_DEPTID in (select dp_deptid from tb_department where dp_deptcode='AC')\r\n" +
            "AND VTM.CPD_ID_VOUCHER_TYPE IN (SELECT D.CPD_ID FROM TB_COMPARAM_DET D WHERE D.CPD_VALUE =:cpdValue))  ", nativeQuery = true)
    Long drcrSacHeadId(@Param("orgid") Long orgid, @Param("cpdValue") String cpdValue);

    //Commented below against #133593 query shared by jitendra
  /*  @Query(value = "SELECT DT.VOU_ID,\r\n" +
            "       VM.VOU_POSTING_DATE,\r\n" +
            "       VM.VOU_NO,\r\n" +
            "       SM.AC_HEAD_CODE,\r\n" +
            "       VM.NARRATION,\r\n" +
            "       DT.VOUDET_AMT,\r\n" +
            "       VM.VOU_TYPE_CPD_ID,\r\n" +
            "       DT.DRCR_CPD_ID,\r\n" +
            "       sm.SAC_HEAD_ID as CR_SAC_HEAD_ID,\r\n" +
            "      (select SAC_HEAD_ID from tb_ac_voucher_det where VOU_ID=DT.VOU_ID and DRCR_CPD_ID=:drId) as DR_SAC_HEAD_ID\r\n"+
            "  FROM TB_AC_VOUCHER_DET DT, TB_AC_VOUCHER VM, TB_AC_SECONDARYHEAD_MASTER SM\r\n" +
            "  WHERE DT.VOU_ID = VM.VOU_ID\r\n" +
            "  AND SM.SAC_HEAD_ID = DT.SAC_HEAD_ID\r\n" +
            "  AND VM.VOU_POSTING_DATE=:fromDateId\r\n" + #129376
            "  AND VM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" +
            "  AND DT.DRCR_CPD_ID =:crId\r\n" +
            "  AND DT.ORGID =:orgid\r\n" +
            "  ORDER BY  VM.VOU_ID", nativeQuery = true)*/
    @Query(value = "SELECT DT.VOU_ID,\r\n" + 
    		"       VM.VOU_POSTING_DATE,\r\n" + 
    		"       VM.VOU_NO,\r\n" + 
    		"       SM.AC_HEAD_CODE,\r\n" + 
    		"       VM.NARRATION,\r\n" + 
    		"       DT.VOUDET_AMT,\r\n" + 
    		"       VM.VOU_TYPE_CPD_ID,\r\n" + 
    		"       DT.DRCR_CPD_ID,\r\n" + 
    		"       sm.SAC_HEAD_ID as CR_SAC_HEAD_ID\r\n" + 
    		"  FROM TB_AC_VOUCHER_DET DT, TB_AC_VOUCHER VM, TB_AC_SECONDARYHEAD_MASTER SM\r\n" + 
    		"  WHERE DT.VOU_ID = VM.VOU_ID\r\n" + 
    		"  AND SM.SAC_HEAD_ID = DT.SAC_HEAD_ID\r\n" + 
    		"   AND VM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" + 
    		"  AND DT.DRCR_CPD_ID  =:crId  \r\n" + 
    		"  AND DT.ORGID =:orgid\r\n" + 
    		"  UNION\r\n" + 
    		"  SELECT CT.VOU_ID,\r\n" + 
    		"       CM.VOU_POSTING_DATE,\r\n" + 
    		"       CM.VOU_NO,\r\n" + 
    		"       CSM.AC_HEAD_CODE,\r\n" + 
    		"       CM.NARRATION,\r\n" + 
    		"       CT.VOUDET_AMT,\r\n" + 
    		"       CM.VOU_TYPE_CPD_ID,\r\n" + 
    		"       CT.DRCR_CPD_ID,\r\n" + 
    		"       csm.SAC_HEAD_ID as CR_SAC_HEAD_ID\r\n" + 
    		"  FROM TB_AC_VOUCHER_DET CT, TB_AC_VOUCHER CM, TB_AC_SECONDARYHEAD_MASTER CSM\r\n" + 
    		"  WHERE CT.VOU_ID = CM.VOU_ID\r\n" + 
    		"  AND CSM.SAC_HEAD_ID = CT.SAC_HEAD_ID\r\n" + 
    		"  AND CM.VOU_POSTING_DATE BETWEEN :fromDateId AND :toDateId\r\n" + 
    		"  AND CT.DRCR_CPD_ID =:drId  \r\n" + 
    		"  AND CT.ORGID =:orgid\r\n" + 
    		"  ORDER BY  VOU_ID", nativeQuery = true)
    List<Object[]> getPaymentBookReportData(@Param("orgid") Long orgid, @Param("fromDateId") Date fromDateId,@Param("toDateId") Date toDateId,
            @Param("drId") Long drId, @Param("crId") Long crId);

    
    
    @Query(value = "SELECT FM.FIELD_ID,\r\n" + 
    		"       BM.BUDGET_CODE,\r\n" + 
    		"       PR.ORGINAL_ESTAMT,\r\n" + 
    		"       PR.PR_PROJECTED,\r\n" + 
    		"       PR.REVISED_ESTAMT,\r\n" + 
    		"       PR.NXT_YR_OE,\r\n" + 
    		"       PR.PR_COLLECTED,\r\n" + 
    		"       PR.DP_DEPTID\r\n" + 
    		"       FROM TB_AC_BUDGETCODE_MAS AS BM\r\n" + 
    		"       JOIN TB_AC_PROJECTEDREVENUE AS PR\r\n" + 
    		"       ON PR.BUDGETCODE_ID = BM.BUDGETCODE_ID\r\n" + 
    		"       JOIN TB_AC_FIELD_MASTER FM ON BM.FIELD_ID = FM.FIELD_ID\r\n" + 
    		"       WHERE BM.FUNCTION_ID =:functionId AND PR.DP_DEPTID =:deptId AND PR.FA_YEARID =:financialYearId", nativeQuery = true)
	List<Object[]> getbudgetEstimationSheetsReportData(@Param("financialYearId") Long financialYearId, @Param("deptId") Long deptId, @Param("functionId") Long functionId);

	@Query(value = "SELECT DISTINCT tbsecmas.AC_HEAD_CODE, tbsecmas.SAC_HEAD_ID\r\n" + 
			"  FROM tb_ac_vouchertemplate_mas voutmp\r\n" + 
			"       LEFT JOIN tb_ac_vouchertemplate_det votmpdet\r\n" + 
			"          ON voutmp.TEMPLATE_ID = votmpdet.TEMPLATE_ID\r\n" + 
			"       INNER JOIN tb_ac_secondaryhead_master tbsecmas\r\n" + 
			"          ON votmpdet.SAC_HEAD_ID = tbsecmas.SAC_HEAD_ID\r\n" + 
			" WHERE     voutmp.CPD_ID_VOUCHER_TYPE =:voucherType\r\n" + 
			"       AND voutmp.ORGID =:orgId\r\n" + 
			"       AND voutmp.DP_DEPTID =:deptId\r\n" + 
			"       AND voutmp.CPD_ID_STATUS_FLG =:cpdIdStatusFlag",nativeQuery = true)
	List<Object[]> findAccountHeadsByOrgIdandDeptId(@Param("voucherType") Long voucherType, @Param("orgId") Long orgId, @Param("deptId") Long deptId, @Param("cpdIdStatusFlag") Long cpdIdStatusFlag);
    
    //query for loan report
	
	/*
	 * @Query(value = "SELECT \r\n" + "     any_value(mst.LN_DEPTNAME),\r\n" +
	 * "     any_value(mst.LN_PURPOSE),\r\n" +
	 * "       any_value(mst.SANCTION_DT),\r\n" +
	 * "     any_value(mst.SANCTION_AMT),\r\n" +
	 * "      any_value (mst.LN_INTRATE),\r\n" +
	 * "      any_value(mst.INST_AMT),\r\n" +
	 * "      any_value( mst.NO_OF_INSTALLMENTS),\r\n" +
	 * "      any_value( mst.LN_REMARK),\r\n" + "       any_value(mas.RM_DATE),\r\n"
	 * + "       any_value(mst.LN_PERIOD_UNIT),\r\n" +
	 * "      any_value( mas.RM_AMOUNT),\r\n" +
	 * "      any_value( det.INST_DUEDT),\r\n" +
	 * "      any_value( det.PRNPAL_AMT),\r\n" +
	 * "       any_value(det.INT_AMT),\r\n" +
	 * "       any_value(det.BAL_PRNPALAMT),\r\n" +
	 * "       any_value(mst.RES_NO),\r\n" + "       any_value(mst.RES_DT),\r\n" +
	 * "       any_value(mas.RECEIPT_DEL_FLAG)\r\n" + "   FROM \r\n" +
	 * "   tb_ac_lnmst mst,\r\n" + "    tb_ac_lndet det,\r\n" +
	 * "     tb_receipt_mas mas\r\n" + "     where \r\n" +
	 * "     mst.LN_LOANID=det.LN_LOANID and\r\n" +
	 * "      mst.LN_LOANID=mas.REF_ID and \r\n" +
	 * "       mst.LN_LOANID=mas.REF_ID AND \r\n" +
	 * " WHERE mst.LN_NO=:loanCode AND mst.ORGID=:orgId AND\r\n" +
	 * "mas.RECEIPT_DEL_FLAG IS NULL GROUP BY mas.RM_AMOUNT" ,nativeQuery = true)
	 * List<Object[]> loanReportData(@Param("loanCode")String
	 * loanCode, @Param("orgId") Long orgId);
	 */

	
	//RECENT QUERY
	
	
	  @Query(value = "SELECT  any_value(mst.LN_LOANID),\r\n" +
	  "     any_value(mst.LN_DEPTNAME),\r\n" +
	  "       any_value(mst.LN_PURPOSE),\r\n" +
	  "     any_value(mst.SANCTION_DT),\r\n" +
	  "      any_value (mst.SANCTION_AMT),\r\n" +
	  "      any_value(mst.LN_INTRATE),\r\n" +
	  "      any_value( mst.INST_AMT),\r\n" +
	  "      any_value( mst.NO_OF_INSTALLMENTS),\r\n" +
	  "       any_value(mst.LN_REMARK),\r\n" + "       any_value(mas.RM_DATE),\r\n"
	  + "      any_value( mst.LN_PERIOD_UNIT),\r\n" +
	  "       any_value(mas.RM_AMOUNT),\r\n" +
	  "      any_value(det.INST_DUEDT),\r\n" +
	  "      any_value( det.PRNPAL_AMT),\r\n" +
	  "       any_value(det.INT_AMT),\r\n" +
	  "       any_value(det.BAL_PRNPALAMT),\r\n" +
	  "       any_value(mst.RES_NO),\r\n" + "       any_value(mst.RES_DT),\r\n" +
	  "       any_value(det.BAL_INTAMT),\r\n" +
	  "       any_value(mst.SANCTION_NO)\r\n" +
	  "  		FROM tb_ac_lnmst mst\r\n" +
	  "       JOIN tb_ac_lndet det ON mst.LN_LOANID = det.LN_LOANID\r\n" +
	  "       LEFT JOIN tb_receipt_mas mas ON det.LN_LOANID = mas.REF_ID"+
	  " WHERE mst.LN_NO =:loanCode AND mst.ORGID =:orgId AND\r\n" +
	  "mas.RECEIPT_DEL_FLAG IS NULL GROUP BY mas.RM_AMOUNT" ,nativeQuery = true)
	  List<Object[]> loanReportData(@Param("loanCode")String
	  loanCode, @Param("orgId") Long orgId);
	 
	/*
	 * @Query(value = "SELECT  any_value(mst.LN_LOANID),\r\n" +
	 * "     any_value(mst.LN_DEPTNAME),\r\n" +
	 * "       any_value(mst.LN_PURPOSE),\r\n" +
	 * "     any_value(mst.SANCTION_DT),\r\n" +
	 * "      any_value (mst.SANCTION_AMT),\r\n" +
	 * "      any_value(mst.LN_INTRATE),\r\n" +
	 * "      any_value( mst.INST_AMT),\r\n" +
	 * "      any_value( mst.NO_OF_INSTALLMENTS),\r\n" +
	 * "       any_value(mst.LN_REMARK),\r\n" +
	 * "      any_value( mst.LN_PERIOD_UNIT),\r\n" +
	 * "      any_value(det.INST_DUEDT),\r\n" +
	 * "      any_value( det.PRNPAL_AMT),\r\n" +
	 * "       any_value(det.INT_AMT),\r\n" +
	 * "       any_value(det.BAL_PRNPALAMT),\r\n" +
	 * "       any_value(mst.RES_NO),\r\n" + "       any_value(mst.RES_DT),\r\n" +
	 * "       any_value(det.BAL_INTAMT),\r\n" +
	 * "       any_value(mst.SANCTION_NO)\r\n" +
	 * "  		FROM tb_ac_lnmst mst\r\n" +
	 * "       JOIN tb_ac_lndet det ON mst.LN_LOANID = det.LN_LOANID\r\n" +
	 * " WHERE mst.LN_NO =:loanCode AND mst.ORGID =:orgId " ,nativeQuery = true)
	 * List<Object[]> loanReportData(@Param("loanCode")String
	 * loanCode, @Param("orgId") Long orgId);
	 */
	
	
	
	
	
	/*@Query(value = "SELECT mst.loanId ,mst.lnDeptname,mst.lnPurpose,mst.resNo,mst.resDate,"
			+ "mst.sanctionDate,mst.santionAmount,mst.lnInrate,mst.noOfInstallments,mst.lmRemark,mst.loanPeriodUnit, "
			+ "det.prnpalAmount,det.intAmount,det.instDueDate,det.balIntAmt,mas.rmDate,mas.rmAmount,mst.instAmt "
			+ " FROM AccountLoanMasterEntity mst, AccountLoanDetEntity det ,TbServiceReceiptMasEntity mas WHERE  mst.loanId = det.lnmas.loanId AND mas.refId = det.lnmas.loanId "
			+ "  AND  mst.lnNo =:loanCode AND  mst.orgId =:orgId")
	List<AccountLoanReportDTO> loanRegisterData(@Param("loanCode")String loanCode,  @Param("orgId") Long orgId);
	*/
	@Query(value = "SELECT  lnNo FROM AccountLoanMasterEntity WHERE ORGID=:orgId")
	List<String> getLoanCode(@Param("orgId") Long orgId);
	
	
	
	@Query(" FROM AccountInvestmentMasterEntity inv WHERE inv.invstNo  =:invstNo  AND inv.orgId=:orgId ")
	List<AccountInvestmentMasterEntity> getRegisterData(@Param("invstNo")String invNo, @Param("orgId") Long orgId);

	@Query(value = "SELECT  invstNo FROM AccountInvestmentMasterEntity WHERE ORGID=:orgId")
	List<String> getInvestmentId(@Param("orgId") Long orgId);
	
	@Query(value = "SELECT  invstNo FROM AccountInvestmentMasterEntity WHERE ORGID=:orgId and fundId=:fundId")
	List<String> getInvestmentIdFromFundId(@Param("orgId") Long orgId,@Param("fundId") Long fundId);
	
	@Query(value = "SELECT grtNo,grtName FROM AccountGrantMasterEntity WHERE ORGID=:orgId")
	List<Object []> getGrantName(@Param("orgId") Long orgId);
	
	
	@Query("FROM AccountGrantMasterEntity grt where( (grt.grtName =:grtName AND orgId =:orgId) OR ( grt.sanctionDate BETWEEN :fromDate AND :toDate AND orgId =:orgId))")
	List<AccountGrantMasterEntity> getGrantRegisterData(@Param("grtName")String grtName , @Param("fromDate")Date fromDate,@Param("toDate")Date toDate,@Param("orgId")Long orgId);
	

	@Query("FROM AccountGrantMasterEntity grt where grt.grtName =:grtName AND orgId =:orgId")
	List<AccountGrantMasterEntity> getGrantRegisterDataName(@Param("grtName")String grtName ,@Param("orgId")Long orgId);

	@Query("SELECT SUM(pd.paymentAmt) FROM AccountPaymentDetEntity pd WHERE pd.billId=:billId AND pd.orgId=:orgId")
	BigDecimal getPaidDepositAmount(@Param("billId") Long billId, @Param("orgId") Long orgId);
	
	
	/*LOAN REPORT HQL*/
	
	//1 Receipt for register 
	@Query("FROM TbServiceReceiptMasEntity rm  where rm.refId=:loanId  AND rm.orgId=:orgid AND rm.receiptTypeFlag=:receiptTypeFlag AND rm.receiptDelFlag is NULL ") 
	List<TbServiceReceiptMasEntity> receiptsForRegister(@Param("orgid") Long
	  orgid, @Param("loanId")Long loanId,@Param("receiptTypeFlag")String receiptTypeFlag);
	  
	
	//3 amount repaid for loan register
	@Query("FROM AccountBillEntryMasterEnitity bmas where bmas.billIntRefId=:lndetId  AND bmas.orgId=:orgid and bmas.billDeletionFlag is NULL")
	AccountBillEntryMasterEnitity  amountPaidData(@Param("orgid") Long
			  orgid, @Param("lndetId")Long lndetId);
	
	@Query(value = "SELECT FIELD_DESC, AC_HEAD_CODE, SUM(ACTUALS_FOR_PREVIOUS_YEAR) AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"		SUM(ORGINAL_ESTAMT_CURRENT_YEAR) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        SUM(REVISED_ESTAMT_CURRENT_YEAR) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        SUM(ORGINAL_ESTAMT_NEXT_YEAR) AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM\r\n" + 
			"(\r\n" + 
			"SELECT AAA.FIELD_DESC AS FIELD_DESC, \r\n" + 
			"		FM.FUNCTION_DESC AS FUNCTION_DESC,\r\n" + 
			"		AAA.DP_DEPTDESC AS FUNCTIONARY,\r\n" + 
			"		C.AC_HEAD_CODE AS AC_HEAD_CODE,\r\n" + 
			"        0 AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.ORGINAL_ESTAMT_CURRENT_YEAR),0) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.REVISED_ESTAMT_CURRENT_YEAR),0) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.ORGINAL_ESTAMT_1Y),0) AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM tb_ac_secondaryhead_master C\r\n" + 
			"INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = C.STATUS_CPD_ID AND D1.CPD_VALUE = 'A'\r\n" + 
			"INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = C.FUNCTION_ID\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"    SELECT BCD.SAC_HEAD_ID AS SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC,\r\n" + 
			"                SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"                                SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId) THEN PRV.REVISED_ESTAMT END) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"                                SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) + 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_1Y\r\n" + 
			"         FROM tb_ac_budgetcode_mas BCD\r\n" + 
			"    INNER JOIN tb_ac_projected_expenditure PRV ON PRV.BUDGETCODE_ID = BCD.BUDGETCODE_ID AND PRV.ORGID = BCD.ORGID\r\n" + 
			"    INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = PRV.DP_DEPTID\r\n" + 
			"    LEFT JOIN tb_ac_field_master FM ON PRV.FIELD_ID = FM.FIELD_ID\r\n" + 
			"    WHERE BCD.ORGID = :orgId\r\n" + 
			"	AND PRV.FA_YEARID IN         \r\n" + 
			"	(\r\n" + 
			"		SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId\r\n" + 
			"		UNION ALL\r\n" + 
			"		SELECT FA_YEARID FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) + 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)\r\n" + 
			"	)\r\n" + 
			"    AND PRV.DP_DEPTID LIKE CASE WHEN :deptId = 0 THEN '%' ELSE :deptId END\r\n" + 
			"    GROUP BY BCD.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC\r\n" + 
			")AAA ON C.SAC_HEAD_ID = AAA.SAC_HEAD_ID\r\n" + 
			"WHERE C.FUNCTION_ID LIKE CASE WHEN :functionId = 0 THEN '%' ELSE :functionId END\r\n" + 
			"GROUP BY FM.FUNCTION_DESC, AAA.DP_DEPTDESC, C.AC_HEAD_CODE, AAA.FIELD_DESC\r\n" + 
			"\r\n" + 
			"UNION ALL\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"SELECT PYMT.FIELD_DESC AS FIELD_DESC,\r\n" + 
			"		FM.FUNCTION_DESC AS FUNCTION_DESC,\r\n" + 
			"		PYMT.DP_DEPTDESC AS FUNCTIONARY,\r\n" + 
			"		C.AC_HEAD_CODE AS AC_HEAD_CODE,\r\n" + 
			"        IFNULL(PYMT.PAYMENT_AMT,0) AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"        0 AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        0 AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        0 AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM tb_ac_secondaryhead_master C\r\n" + 
			"INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = C.STATUS_CPD_ID AND D1.CPD_VALUE = 'A'\r\n" + 
			"INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = C.FUNCTION_ID\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"        SELECT BED.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC,  SUM(PD.PAYMENT_AMT) AS PAYMENT_AMT\r\n" + 
			"        FROM (SELECT DISTINCT BM_ID, SAC_HEAD_ID FROM tb_ac_bill_exp_detail) BED \r\n" + 
			"        INNER JOIN tb_ac_bill_mas BM ON BM.BM_ID = BED.BM_ID AND BM.BM_DEL_FLAG IS NULL\r\n" + 
			"        INNER JOIN tb_ac_payment_det PD ON BM.BM_ID = PD.BM_ID\r\n" + 
			"        INNER JOIN tb_ac_payment_mas PM ON PD.PAYMENT_ID = PM.PAYMENT_ID\r\n" + 
			"        INNER JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = PD.BUDGETCODE_ID    \r\n" + 
			"        INNER JOIN tb_ac_primaryhead_master A ON A.PAC_HEAD_ID = SHM.PAC_HEAD_ID\r\n" + 
			"		INNER JOIN tb_ac_primaryhead_master B ON B.PAC_HEAD_ID = A.PAC_HEAD_PARENT_ID\r\n" + 
			"        INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = B.CPD_ID_ACHEADTYPES AND D1.CPD_VALUE = ('E')\r\n" + 
			"		INNER JOIN TB_COMPARAM_MAS M1 ON M1.CPM_ID = D1.CPM_ID AND M1.CPM_PREFIX = 'COA'\r\n" + 
			"        INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = BM.DP_DEPTID\r\n" + 
			"        INNER JOIN tb_ac_field_master FM ON PM.FIELDID = FM.FIELD_ID\r\n" + 
			"        WHERE PM.PAYMENT_DEL_FLAG IS NULL\r\n" + 
			"        AND PM.ORGID = :orgId\r\n" + 
			"		AND BM.CHECKER_AUTHO = 'Y'\r\n" + 
			"        AND BM.DP_DEPTID LIKE CASE WHEN  :deptId = 0 THEN '%' ELSE :deptId END\r\n" + 
			"        AND PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) - 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) \r\n" + 
			"                                AND (SELECT FA_TODATE FROM tb_financialyear WHERE YEAR(FA_TODATE) = (SELECT YEAR(FA_TODATE) - 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) \r\n" + 
			"         GROUP BY BED.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC\r\n" + 
			") PYMT ON PYMT.SAC_HEAD_ID = C.SAC_HEAD_ID\r\n" + 
			"WHERE C.FUNCTION_ID LIKE CASE WHEN :functionId = 0 THEN '%' ELSE :functionId END\r\n" + 
			") A\r\n" + 
			"GROUP BY FIELD_DESC, AC_HEAD_CODE" ,nativeQuery = true)
	List<Object[]> getbudgetEstimationSheetsFormateReportPaymentData(@Param("financialYearId") Long financialYearId, @Param("deptId") Long deptId, @Param("functionId") Long functionId,@Param("orgId") Long orgId);

	@Query(value = "\r\n" + 
			"SELECT FIELD_DESC, AC_HEAD_CODE, SUM(ACTUALS_FOR_PREVIOUS_YEAR) AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"		SUM(ORGINAL_ESTAMT_CURRENT_YEAR) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        SUM(REVISED_ESTAMT_CURRENT_YEAR) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        SUM(ORGINAL_ESTAMT_NEXT_YEAR) AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM\r\n" + 
			"(\r\n" + 
			"SELECT  RCPT.FIELD_DESC AS FIELD_DESC,\r\n" + 
			"		FM.FUNCTION_DESC AS FUNCTION_DESC,\r\n" + 
			"		RCPT.DP_DEPTDESC AS FUNCTIONARY,\r\n" + 
			"        C.AC_HEAD_CODE AS AC_HEAD_CODE,\r\n" + 
			"        IFNULL(RCPT.RECEIPT_AMT,0) AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"        0 AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        0 AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        0 AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM tb_ac_secondaryhead_master C\r\n" + 
			"INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = C.STATUS_CPD_ID  AND D1.CPD_VALUE = 'A'\r\n" + 
			"INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = C.FUNCTION_ID\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"        SELECT RD.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC,  SUM(RD.RF_FEEAMOUNT) AS RECEIPT_AMT\r\n" + 
			"        FROM tb_receipt_det RD\r\n" + 
			"        INNER JOIN tb_receipt_mas RM ON RD.RM_RCPTID = RM.RM_RCPTID\r\n" + 
			"		INNER JOIN tb_ac_field_master FM ON RM.FIELD_ID = FM.FIELD_ID\r\n" + 
			"        INNER JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = RD.SAC_HEAD_ID \r\n" + 
			"        INNER JOIN tb_ac_primaryhead_master A ON A.PAC_HEAD_ID = SHM.PAC_HEAD_ID\r\n" + 
			"		INNER JOIN tb_ac_primaryhead_master B ON B.PAC_HEAD_ID = A.PAC_HEAD_PARENT_ID\r\n" + 
			"        INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = B.CPD_ID_ACHEADTYPES AND D1.CPD_VALUE = ('I')\r\n" + 
			"		INNER JOIN TB_COMPARAM_MAS M1 ON M1.CPM_ID = D1.CPM_ID AND M1.CPM_PREFIX = 'COA'\r\n" + 
			"        INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = RM.DP_DEPTID\r\n" + 
			"        WHERE RM.RECEIPT_DEL_FLAG IS NULL\r\n" + 
			"        AND RM.ORGID =  :orgId\r\n" + 
			"        AND RM.DP_DEPTID LIKE CASE WHEN :deptId = 0 THEN '%' ELSE :deptId END\r\n" + 
			"        AND RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) - 1 FROM tb_financialyear WHERE FA_YEARID =:financialYearId)) \r\n" + 
			"                        AND (SELECT FA_TODATE FROM tb_financialyear WHERE YEAR(FA_TODATE) = (SELECT YEAR(FA_TODATE) - 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) \r\n" + 
			"         GROUP BY RD.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC\r\n" + 
			") RCPT  ON C.SAC_HEAD_ID = RCPT.SAC_HEAD_ID\r\n" + 
			"WHERE C.FUNCTION_ID LIKE CASE WHEN :functionId = 0 THEN '%' ELSE :functionId END\r\n" + 
			"\r\n" + 
			"UNION ALL\r\n" + 
			"\r\n" + 
			"SELECT AAA.FIELD_DESC AS FIELD_DESC, \r\n" + 
			"		FM.FUNCTION_DESC AS FUNCTION_DESC,\r\n" + 
			"		AAA.DP_DEPTDESC AS FUNCTIONARY,\r\n" + 
			"        C.AC_HEAD_CODE AS AC_HEAD_CODE,\r\n" + 
			"        0 AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.ORGINAL_ESTAMT_CURRENT_YEAR),0) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.REVISED_ESTAMT_CURRENT_YEAR),0) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.ORGINAL_ESTAMT_1Y),0) AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM tb_ac_secondaryhead_master C\r\n" + 
			"INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = C.STATUS_CPD_ID  AND D1.CPD_VALUE = 'A'\r\n" + 
			"INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = C.FUNCTION_ID\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"       SELECT BCD.SAC_HEAD_ID AS SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC,\r\n" + 
			"                                SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID =:financialYearId) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"                                SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId) THEN PRV.REVISED_ESTAMT END) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"                                SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) + 1 FROM tb_financialyear WHERE FA_YEARID =:financialYearId)) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_1Y\r\n" + 
			"        FROM tb_ac_budgetcode_mas BCD\r\n" + 
			"        INNER JOIN tb_ac_projectedrevenue PRV ON PRV.BUDGETCODE_ID = BCD.BUDGETCODE_ID AND PRV.ORGID = BCD.ORGID\r\n" + 
			"        INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = PRV.DP_DEPTID\r\n" + 
			"        LEFT JOIN tb_ac_field_master FM ON PRV.FIELD_ID = FM.FIELD_ID\r\n" + 
			"        WHERE BCD.ORGID =:orgId\r\n" + 
			"        AND PRV.FA_YEARID IN         \r\n" + 
			"        (\r\n" + 
			"			SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId\r\n" + 
			"			UNION ALL\r\n" + 
			"			SELECT FA_YEARID FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) + 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)\r\n" + 
			"        )\r\n" + 
			"        AND PRV.DP_DEPTID LIKE CASE WHEN :deptId = 0 THEN '%' ELSE :deptId END\r\n" + 
			"        GROUP BY BCD.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC\r\n" + 
			")AAA ON C.SAC_HEAD_ID = AAA.SAC_HEAD_ID\r\n" + 
			"WHERE C.FUNCTION_ID LIKE CASE WHEN :functionId = 0 THEN '%' ELSE :functionId END\r\n" + 
			"GROUP BY FM.FUNCTION_DESC, AAA.DP_DEPTDESC, C.AC_HEAD_CODE, AAA.FIELD_DESC\r\n" + 
			") A\r\n" + 
			"GROUP BY FIELD_DESC, AC_HEAD_CODE",nativeQuery=true)
	List<Object[]> getbudgetEstimationSheetsFormateReportReceiptData(@Param("financialYearId") Long financialYearId, @Param("deptId") Long deptId, @Param("functionId") Long functionId,@Param("orgId") Long orgId);

	@Query(value = "SELECT FIELD_DESC, FUNCTION_DESC, FUNCTIONARY, AC_HEAD_CODE, SUM(ACTUALS_FOR_PREVIOUS_YEAR) AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"		SUM(ORGINAL_ESTAMT_CURRENT_YEAR) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        SUM(REVISED_ESTAMT_CURRENT_YEAR) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        SUM(ORGINAL_ESTAMT_NEXT_YEAR) AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM\r\n" + 
			"(\r\n" + 
			"SELECT  RCPT.FIELD_DESC AS FIELD_DESC,\r\n" + 
			"		FM.FUNCTION_DESC AS FUNCTION_DESC,\r\n" + 
			"		RCPT.DP_DEPTDESC AS FUNCTIONARY,\r\n" + 
			"        C.AC_HEAD_CODE AS AC_HEAD_CODE,\r\n" + 
			"        IFNULL(RCPT.RECEIPT_AMT,0) AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"        0 AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        0 AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        0 AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM tb_ac_secondaryhead_master C\r\n" + 
			"INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = C.STATUS_CPD_ID  AND D1.CPD_VALUE = 'A'\r\n" + 
			"INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = C.FUNCTION_ID\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"        SELECT RD.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC,  SUM(RD.RF_FEEAMOUNT) AS RECEIPT_AMT\r\n" + 
			"        FROM tb_receipt_det RD\r\n" + 
			"        INNER JOIN tb_receipt_mas RM ON RD.RM_RCPTID = RM.RM_RCPTID\r\n" + 
			"		INNER JOIN tb_ac_field_master FM ON RM.FIELD_ID = FM.FIELD_ID\r\n" + 
			"        INNER JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = RD.SAC_HEAD_ID \r\n" + 
			"        INNER JOIN tb_ac_primaryhead_master A ON A.PAC_HEAD_ID = SHM.PAC_HEAD_ID\r\n" + 
			"		INNER JOIN tb_ac_primaryhead_master B ON B.PAC_HEAD_ID = A.PAC_HEAD_PARENT_ID\r\n" + 
			"        INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = B.CPD_ID_ACHEADTYPES AND D1.CPD_VALUE = ('I')\r\n" + 
			"		INNER JOIN TB_COMPARAM_MAS M1 ON M1.CPM_ID = D1.CPM_ID AND M1.CPM_PREFIX = 'COA'\r\n" + 
			"        INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = RM.DP_DEPTID\r\n" + 
			"        WHERE RM.RECEIPT_DEL_FLAG IS NULL\r\n" + 
			"        AND RM.ORGID = :orgId\r\n" + 
			"        AND RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) - 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) \r\n" + 
			"                        AND (SELECT FA_TODATE FROM tb_financialyear WHERE YEAR(FA_TODATE) = (SELECT YEAR(FA_TODATE) - 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) \r\n" + 
			"         GROUP BY RD.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC\r\n" + 
			") RCPT  ON C.SAC_HEAD_ID = RCPT.SAC_HEAD_ID\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"UNION ALL\r\n" + 
			"\r\n" + 
			"SELECT AAA.FIELD_DESC AS FIELD_DESC, \r\n" + 
			"		FM.FUNCTION_DESC AS FUNCTION_DESC,\r\n" + 
			"		AAA.DP_DEPTDESC AS FUNCTIONARY,\r\n" + 
			"        C.AC_HEAD_CODE AS AC_HEAD_CODE,\r\n" + 
			"        0 AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.ORGINAL_ESTAMT_CURRENT_YEAR),0) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.REVISED_ESTAMT_CURRENT_YEAR),0) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.ORGINAL_ESTAMT_1Y),0) AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM tb_ac_secondaryhead_master C\r\n" + 
			"INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = C.STATUS_CPD_ID  AND D1.CPD_VALUE = 'A'\r\n" + 
			"INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = C.FUNCTION_ID\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"        SELECT BCD.SAC_HEAD_ID AS SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC,\r\n" + 
			"				SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"				SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId) THEN PRV.REVISED_ESTAMT END) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"				SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) + 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_1Y\r\n" + 
			"\r\n" + 
			"        FROM tb_ac_budgetcode_mas BCD\r\n" + 
			"        INNER JOIN tb_ac_projectedrevenue PRV ON PRV.BUDGETCODE_ID = BCD.BUDGETCODE_ID AND PRV.ORGID = BCD.ORGID\r\n" + 
			"        INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = PRV.DP_DEPTID\r\n" + 
			"        LEFT JOIN tb_ac_field_master FM ON PRV.FIELD_ID = FM.FIELD_ID\r\n" + 
			"        WHERE BCD.ORGID = :orgId\r\n" + 
			"        AND PRV.FA_YEARID IN         \r\n" + 
			"        (\r\n" + 
			"			SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID =:financialYearId\r\n" + 
			"			UNION ALL\r\n" + 
			"			SELECT FA_YEARID FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) + 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)\r\n" + 
			"        )\r\n" + 
			"        GROUP BY BCD.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC\r\n" + 
			")AAA ON C.SAC_HEAD_ID = AAA.SAC_HEAD_ID\r\n" + 
			"GROUP BY FM.FUNCTION_DESC, AAA.DP_DEPTDESC, C.AC_HEAD_CODE, AAA.FIELD_DESC\r\n" + 
			") A\r\n" + 
			"GROUP BY FIELD_DESC, FUNCTION_DESC, FUNCTIONARY, AC_HEAD_CODE",nativeQuery=true)
	List<Object[]> getbudgetEstimationConsolidationFormatReportReceiptData(@Param("financialYearId") Long financialYearId,@Param("orgId") Long orgId);

	
	@Query(value = "SELECT FIELD_DESC, FUNCTION_DESC, FUNCTIONARY, AC_HEAD_CODE, SUM(ACTUALS_FOR_PREVIOUS_YEAR) AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"		SUM(ORGINAL_ESTAMT_CURRENT_YEAR) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        SUM(REVISED_ESTAMT_CURRENT_YEAR) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        SUM(ORGINAL_ESTAMT_NEXT_YEAR) AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM\r\n" + 
			"(\r\n" + 
			"SELECT AAA.FIELD_DESC AS FIELD_DESC, \r\n" + 
			"		FM.FUNCTION_DESC AS FUNCTION_DESC,\r\n" + 
			"		AAA.DP_DEPTDESC AS FUNCTIONARY,\r\n" + 
			"		C.AC_HEAD_CODE AS AC_HEAD_CODE,\r\n" + 
			"        0 AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.ORGINAL_ESTAMT_CURRENT_YEAR),0) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.REVISED_ESTAMT_CURRENT_YEAR),0) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        IFNULL(SUM(AAA.ORGINAL_ESTAMT_1Y),0) AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM tb_ac_secondaryhead_master C\r\n" + 
			"INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = C.STATUS_CPD_ID AND D1.CPD_VALUE = 'A'\r\n" + 
			"INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = C.FUNCTION_ID\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"    SELECT BCD.SAC_HEAD_ID AS SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC,\r\n" + 
			"			SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"			SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId) THEN PRV.REVISED_ESTAMT END) AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"			SUM(CASE WHEN PRV.FA_YEARID = (SELECT FA_YEARID FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) + 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) THEN PRV.ORGINAL_ESTAMT END) AS ORGINAL_ESTAMT_1Y\r\n" + 
			"\r\n" + 
			"    FROM tb_ac_budgetcode_mas BCD\r\n" + 
			"    INNER JOIN tb_ac_projected_expenditure PRV ON PRV.BUDGETCODE_ID = BCD.BUDGETCODE_ID AND PRV.ORGID = BCD.ORGID\r\n" + 
			"    INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = PRV.DP_DEPTID\r\n" + 
			"    LEFT JOIN tb_ac_field_master FM ON PRV.FIELD_ID = FM.FIELD_ID\r\n" + 
			"    WHERE BCD.ORGID = :orgId\r\n" + 
			"	AND PRV.FA_YEARID IN         \r\n" + 
			"	(\r\n" + 
			"		SELECT FA_YEARID FROM tb_financialyear WHERE FA_YEARID = :financialYearId\r\n" + 
			"		UNION ALL\r\n" + 
			"		SELECT FA_YEARID FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) + 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)\r\n" + 
			"	)\r\n" + 
			"    GROUP BY BCD.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC\r\n" + 
			")AAA ON C.SAC_HEAD_ID = AAA.SAC_HEAD_ID\r\n" + 
			"GROUP BY FM.FUNCTION_DESC, AAA.DP_DEPTDESC, C.AC_HEAD_CODE, AAA.FIELD_DESC\r\n" + 
			"\r\n" + 
			"UNION ALL\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"SELECT PYMT.FIELD_DESC AS FIELD_DESC,\r\n" + 
			"		FM.FUNCTION_DESC AS FUNCTION_DESC,\r\n" + 
			"		PYMT.DP_DEPTDESC AS FUNCTIONARY,\r\n" + 
			"		C.AC_HEAD_CODE AS AC_HEAD_CODE,\r\n" + 
			"        IFNULL(PYMT.PAYMENT_AMT,0) AS ACTUALS_FOR_PREVIOUS_YEAR,\r\n" + 
			"        0 AS ORGINAL_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        0 AS REVISED_ESTAMT_CURRENT_YEAR,\r\n" + 
			"        0 AS ORGINAL_ESTAMT_NEXT_YEAR\r\n" + 
			"FROM tb_ac_secondaryhead_master C\r\n" + 
			"INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = C.STATUS_CPD_ID AND D1.CPD_VALUE = 'A'\r\n" + 
			"INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = C.FUNCTION_ID\r\n" + 
			"INNER JOIN\r\n" + 
			"(\r\n" + 
			"        SELECT BED.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC,  SUM(PD.PAYMENT_AMT) AS PAYMENT_AMT\r\n" + 
			"        FROM (SELECT DISTINCT BM_ID, SAC_HEAD_ID FROM tb_ac_bill_exp_detail) BED \r\n" + 
			"        INNER JOIN tb_ac_bill_mas BM ON BM.BM_ID = BED.BM_ID AND BM.BM_DEL_FLAG IS NULL\r\n" + 
			"        INNER JOIN tb_ac_payment_det PD ON BM.BM_ID = PD.BM_ID\r\n" + 
			"        INNER JOIN tb_ac_payment_mas PM ON PD.PAYMENT_ID = PM.PAYMENT_ID\r\n" + 
			"        INNER JOIN tb_ac_secondaryhead_master SHM ON SHM.SAC_HEAD_ID = PD.BUDGETCODE_ID    \r\n" + 
			"        INNER JOIN tb_ac_primaryhead_master A ON A.PAC_HEAD_ID = SHM.PAC_HEAD_ID\r\n" + 
			"		INNER JOIN tb_ac_primaryhead_master B ON B.PAC_HEAD_ID = A.PAC_HEAD_PARENT_ID\r\n" + 
			"        INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = B.CPD_ID_ACHEADTYPES AND D1.CPD_VALUE = ('E')\r\n" + 
			"		INNER JOIN TB_COMPARAM_MAS M1 ON M1.CPM_ID = D1.CPM_ID AND M1.CPM_PREFIX = 'COA'\r\n" + 
			"        INNER JOIN tb_department DEPT ON DEPT.DP_DEPTID = BM.DP_DEPTID\r\n" + 
			"        INNER JOIN tb_ac_field_master FM ON PM.FIELDID = FM.FIELD_ID\r\n" + 
			"        WHERE PM.PAYMENT_DEL_FLAG IS NULL\r\n" + 
			"        AND PM.ORGID = :orgId\r\n" + 
			"		AND BM.CHECKER_AUTHO = 'Y'\r\n" + 
			"        AND PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE YEAR(FA_FROMDATE) = (SELECT YEAR(FA_FROMDATE) - 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) \r\n" + 
			"                                AND (SELECT FA_TODATE FROM tb_financialyear WHERE YEAR(FA_TODATE) = (SELECT YEAR(FA_TODATE) - 1 FROM tb_financialyear WHERE FA_YEARID = :financialYearId)) \r\n" + 
			"         GROUP BY BED.SAC_HEAD_ID, DEPT.DP_DEPTDESC, FM.FIELD_DESC\r\n" + 
			") PYMT ON PYMT.SAC_HEAD_ID = C.SAC_HEAD_ID\r\n" + 
			"\r\n" + 
			") A\r\n" + 
			"GROUP BY FIELD_DESC, FUNCTION_DESC, FUNCTIONARY, AC_HEAD_CODE",nativeQuery=true)
	List<Object[]> getbudgetEstimationConsolidationFormatReportPaymentData(@Param("financialYearId") Long financialYearId,@Param("orgId") Long orgId);

	
	@Query(value="SELECT group_concat(distinct FM.FUNCTION_DESC) AS FUNCTION_DESC,\r\n" + 
			"		 FM.FUNCTION_COMPCODE AS FUNCTION_CODE,\r\n" + 
			"    -- FM.FUNCTION_COMPCODE,\r\n" + 
			"        group_concat(distinct FM.FUNCTION_ID) AS FUNCTION_ID,\r\n" + 
			"		sum(IFNULL(OPERATING_RECEIPTS, 0)) AS OPERATING_RECEIPTS,\r\n" + 
			"        sum(IFNULL(NON_OPERATING_RECEIPTS, 0)) AS NON_OPERATING_RECEIPTS,\r\n" + 
			"        sum(IFNULL(OPERATING_RECEIPTS, 0)) + sum(IFNULL(NON_OPERATING_RECEIPTS, 0)) AS TOTAL_RECEIPTS,\r\n" + 
			"        sum(IFNULL(OPERATING_PAYMENTS, 0)) AS OPERATING_PAYMENTS,\r\n" + 
			"        sum(IFNULL(NON_OPERATING_PAYMENTS, 0)) AS NON_OPERATING_PAYMENTS,\r\n" + 
			"        sum(IFNULL(OPERATING_PAYMENTS, 0)) + sum(IFNULL(NON_OPERATING_PAYMENTS, 0)) AS TOTAL_PAYMENTS,\r\n" + 
			"        sum(IFNULL(OPERATING_RECEIPTS, 0)) + sum(IFNULL(NON_OPERATING_RECEIPTS, 0)) - sum(IFNULL(OPERATING_PAYMENTS, 0)) + sum(IFNULL(NON_OPERATING_PAYMENTS, 0)) AS TOTAL_INFLOW_OUTFLOW\r\n" + 
			"\r\n" + 
			"FROM tb_ac_function_master FM\r\n" + 
			"LEFT JOIN\r\n" + 
			"(\r\n" + 
			"	SELECT A.FUNCTION_ID AS FUNCTION_ID, \r\n" + 
			"			SUM(OPERATING_RECEIPTS) AS OPERATING_RECEIPTS, \r\n" + 
			"			SUM(NON_OPERATING_RECEIPTS) AS NON_OPERATING_RECEIPTS\r\n" + 
			"	FROM\r\n" + 
			"	(\r\n" + 
			"		SELECT FM.FUNCTION_ID AS FUNCTION_ID,\r\n" + 
			"				CASE WHEN D1.CPD_VALUE = 'I' THEN RD.RF_FEEAMOUNT END AS OPERATING_RECEIPTS,\r\n" + 
			"				CASE WHEN D1.CPD_VALUE != 'I' THEN RD.RF_FEEAMOUNT END AS NON_OPERATING_RECEIPTS\r\n" + 
			"		FROM tb_ac_secondaryhead_master SHM \r\n" + 
			"		INNER JOIN tb_ac_primaryhead_master CPHM ON SHM.PAC_HEAD_ID = CPHM.PAC_HEAD_ID\r\n" + 
			"		INNER JOIN tb_ac_primaryhead_master PHM ON CPHM.PAC_HEAD_PARENT_ID = PHM.PAC_HEAD_ID \r\n" + 
			"		INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = PHM.CPD_ID_ACHEADTYPES \r\n" + 
			"		INNER JOIN tb_comparam_det D2 ON D2.CPD_ID = SHM.STATUS_CPD_ID AND D2.CPD_VALUE = 'A'\r\n" + 
			"        INNER JOIN tb_receipt_det RD ON RD.SAC_HEAD_ID = SHM.SAC_HEAD_ID\r\n" + 
			"        INNER JOIN tb_receipt_mas RM ON RD.RM_RCPTID = RM.RM_RCPTID\r\n" + 
			"        INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = SHM.FUNCTION_ID\r\n" + 
			"		WHERE SHM.ORGID =:orgId\r\n" + 
			"		AND RM.RECEIPT_DEL_FLAG IS NULL\r\n" + 
			"		AND RM.RM_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE FA_YEARID =:financialYearId) AND \r\n" + 
			"								(SELECT FA_TODATE FROM tb_financialyear WHERE FA_YEARID =:financialYearId)	\r\n" + 
			"	) A\r\n" + 
			"	GROUP BY A.FUNCTION_ID\r\n" + 
			") RCPT ON RCPT.FUNCTION_ID = FM.FUNCTION_ID\r\n" + 
			"\r\n" + 
			"LEFT JOIN\r\n" + 
			"(\r\n" + 
			"	SELECT B.FUNCTION_ID AS FUNCTION_ID, \r\n" + 
			"			SUM(B.OPERATING_PAYMENTS) AS OPERATING_PAYMENTS, \r\n" + 
			"			SUM(B.NON_OPERATING_PAYMENTS) AS NON_OPERATING_PAYMENTS\r\n" + 
			"	FROM\r\n" + 
			"	(\r\n" + 
			"		SELECT FM.FUNCTION_ID AS FUNCTION_ID, \r\n" + 
			"				CASE WHEN D1.CPD_VALUE = 'E' THEN PD.PAYMENT_AMT END AS OPERATING_PAYMENTS,\r\n" + 
			"				CASE WHEN D1.CPD_VALUE != 'E' THEN PD.PAYMENT_AMT END AS NON_OPERATING_PAYMENTS\r\n" + 
			"		FROM tb_ac_secondaryhead_master SHM \r\n" + 
			"		INNER JOIN tb_ac_primaryhead_master CPHM ON SHM.PAC_HEAD_ID = CPHM.PAC_HEAD_ID\r\n" + 
			"		INNER JOIN tb_ac_primaryhead_master PHM ON CPHM.PAC_HEAD_PARENT_ID = PHM.PAC_HEAD_ID \r\n" + 
			"		INNER JOIN tb_comparam_det D1 ON D1.CPD_ID = PHM.CPD_ID_ACHEADTYPES \r\n" + 
			"		INNER JOIN tb_comparam_det D2 ON D2.CPD_ID = SHM.STATUS_CPD_ID  AND D2.CPD_VALUE = 'A'        \r\n" + 
			"        INNER JOIN (SELECT DISTINCT BM_ID, SAC_HEAD_ID FROM tb_ac_bill_exp_detail)BED ON BED.SAC_HEAD_ID = SHM.SAC_HEAD_ID\r\n" + 
			"        INNER JOIN tb_ac_bill_mas BM ON BM.BM_ID = BED.BM_ID AND BM.BM_DEL_FLAG IS NULL\r\n" + 
			"        INNER JOIN tb_ac_payment_det PD ON BM.BM_ID = PD.BM_ID\r\n" + 
			"        INNER JOIN tb_ac_payment_mas PM ON PD.PAYMENT_ID = PM.PAYMENT_ID\r\n" + 
			"        INNER JOIN tb_ac_function_master FM ON FM.FUNCTION_ID = SHM.FUNCTION_ID\r\n" + 
			"		WHERE SHM.ORGID =:orgId\r\n" + 
			"		AND PM.PAYMENT_DEL_FLAG IS NULL\r\n" + 
			"		AND PM.PAYMENT_DATE BETWEEN (SELECT FA_FROMDATE FROM tb_financialyear WHERE FA_YEARID =:financialYearId) AND \r\n" + 
			"								(SELECT FA_TODATE FROM tb_financialyear WHERE FA_YEARID =:financialYearId)	\r\n" + 
			"	) B\r\n" + 
			"	GROUP BY B.FUNCTION_ID\r\n" + 
			") PYMT ON PYMT.FUNCTION_ID = FM.FUNCTION_ID\r\n" + 
			"\r\n" + 
			"WHERE ( IFNULL(OPERATING_RECEIPTS, 0) != 0 OR IFNULL(NON_OPERATING_RECEIPTS, 0) != 0 OR IFNULL(OPERATING_PAYMENTS, 0) != 0 OR IFNULL(NON_OPERATING_PAYMENTS, 0) != 0 )\r\n" + 
			"Group by FM.FUNCTION_COMPCODE\r\n" + 
			"",nativeQuery=true)
	List<Object[]> getFunctionWiseBudgetData(@Param("financialYearId") Long financialYearId,@Param("orgId") Long orgId);

	//operating Receipt level3 
    @Query(value = "select pp.PAC_HEAD_COMPO_CODE PAC_HEAD_COMPO_CODE1,coalesce(pp.PAC_HEAD_DESC,'') PAC_HEAD_DESC,\r\n" + 
    		"coalesce(AMOUNT,0) AMOUNT from\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT) AMOUNT\r\n" + 
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
    		"                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" + 
    		"                                      (SELECT CPD_ID\r\n" + 
    		"                                         FROM TB_COMPARAM_DET D\r\n" + 
    		"                                        WHERE     D.CPM_ID IN\r\n" + 
    		"                                                     (SELECT CPM_ID\r\n" + 
    		"                                                        FROM TB_COMPARAM_MAS\r\n" + 
    		"                                                             M\r\n" + 
    		"                                                       WHERE M.CPM_PREFIX =\r\n" + 
    		"                                                                'TDP')\r\n" + 
    		"                                              AND CPD_VALUE IN ('RRE',\r\n" + 
    		"                                                                'RV',\r\n" + 
    		"                                                                'RDC',\r\n" + 
    		"                                                                'ASE'))\r\n" + 
    		"                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
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
    		"                                AND VD.VOU_POSTING_DATE BETWEEN :frmDate AND :tDate\r\n" + 
    		"                               AND VD.ORGID =:orgId\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 VD.PAC_HEAD_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B \r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"   WHERE P.LEVEL1 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES =:coaReceiptLookupId\r\n" + 
    		"   GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) z  right join TB_AC_PRIMARYHEAD_MASTER pp\r\n" + 
    		"   on pp.PAC_HEAD_COMPO_CODE=z.PAC_HEAD_COMPO_CODE where pp.CPD_ID_ACHEADTYPES=:coaReceiptLookupId and PAC_HEAD_PARENT_ID is null\r\n" + 
    		"   ORDER BY pp.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideOpeningReportDataLevel3(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId, @Param("coaReceiptLookupId") Long coaReceiptLookupId);	


//non operating Receipt level3 
    @Query(value = "select coalesce(pp.PAC_HEAD_COMPO_CODE,0) PAC_HEAD_COMPO_CODE,coalesce(pp.PAC_HEAD_DESC,'') PAC_HEAD_DESC,\r\n" + 
    		"coalesce(AMOUNT,0) AMOUNT from\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT) amount\r\n" + 
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
    		"                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" + 
    		"                                      (SELECT CPD_ID\r\n" + 
    		"                                         FROM TB_COMPARAM_DET D\r\n" + 
    		"                                        WHERE     D.CPM_ID IN\r\n" + 
    		"                                                     (SELECT CPM_ID\r\n" + 
    		"                                                        FROM TB_COMPARAM_MAS\r\n" + 
    		"                                                             M\r\n" + 
    		"                                                       WHERE M.CPM_PREFIX =\r\n" + 
    		"                                                                'TDP')\r\n" + 
    		"                                              AND CPD_VALUE IN ('RRE',\r\n" + 
    		"                                                                'RV',\r\n" + 
    		"                                                                'RDC',\r\n" + 
    		"                                                                'ASE'))\r\n" + 
    		"                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
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
    		"                                AND VD.VOU_POSTING_DATE BETWEEN :frmDate AND :tDate\r\n" + 
    		"                               AND VD.ORGID =:orgId\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 VD.PAC_HEAD_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B \r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"   WHERE P.LEVEL1 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES not in (:coaReceiptLookupId)\r\n" + 
    		"   GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) z  right join TB_AC_PRIMARYHEAD_MASTER pp\r\n" + 
    		"   on pp.PAC_HEAD_COMPO_CODE=z.PAC_HEAD_COMPO_CODE where pp.CPD_ID_ACHEADTYPES not in (:coaReceiptLookupId)\r\n" + 
    		" and pp.PAC_HEAD_PARENT_ID is null and PAC_HEAD_CODE  in (330,331,340,320,412,420,421,341,350,431)\r\n" + 
    		"  ORDER BY pp.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetReceiptSideNonOperatingOpeningReportDataLevel3(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId, @Param("coaReceiptLookupId") List<Long> ids);
    
    
    
	//operating payment level3 
    @Query(value = "select pp.PAC_HEAD_COMPO_CODE PAC_HEAD_COMPO_CODE1,coalesce(pp.PAC_HEAD_DESC,'') PAC_HEAD_DESC,\r\n" + 
    		"coalesce(AMOUNT,0) AMOUNT from\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT) AMOUNT\r\n" + 
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
    		"                               (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" + 
    		"                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" + 
    		"                                      (SELECT CPD_ID\r\n" + 
    		"                                         FROM TB_COMPARAM_DET D\r\n" + 
    		"                                        WHERE     D.CPM_ID IN\r\n" + 
    		"                                                     (SELECT CPM_ID\r\n" + 
    		"                                                        FROM TB_COMPARAM_MAS\r\n" + 
    		"                                                             M\r\n" + 
    		"                                                       WHERE M.CPM_PREFIX =\r\n" + 
    		"                                                                'TDP')\r\n" + 
    		"                                              AND CPD_VALUE IN ('PVE','RBP'))\r\n" + 
    		"                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
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
    		"                               AND VD.VOU_POSTING_DATE BETWEEN :frmDate AND :tDate \r\n" + 
    		"                               AND VD.ORGID =:orgId\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 VD.PAC_HEAD_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B \r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"   WHERE P.LEVEL1 = R.PAC_HEAD_ID  AND R.CPD_ID_ACHEADTYPES =:coaPaymentLookupId\r\n" + 
    		"   GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) z  right join TB_AC_PRIMARYHEAD_MASTER pp\r\n" + 
    		"   on pp.PAC_HEAD_COMPO_CODE=z.PAC_HEAD_COMPO_CODE where pp.CPD_ID_ACHEADTYPES=:coaPaymentLookupId and PAC_HEAD_PARENT_ID is null\r\n" + 
    		"   ORDER BY pp.PAC_HEAD_COMPO_CODE ASC", nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideOperatingOpeningReportDataLevel3(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId, @Param("coaPaymentLookupId") Long coaPaymentLookupId);

 //non operating payment level3 
    @Query(value = "select coalesce(pp.PAC_HEAD_COMPO_CODE,0) PAC_HEAD_COMPO_CODE,coalesce(pp.PAC_HEAD_DESC,'') PAC_HEAD_DESC,\r\n" + 
    		"coalesce(AMOUNT,0) AMOUNT from\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE, R.PAC_HEAD_DESC, SUM(P.AMOUNT) AMOUNT\r\n" + 
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
    		"                               (SUM(VD.VAMT_DR) - SUM(VD.VAMT_CR)) RCPTAMT\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SD\r\n" + 
    		"                         WHERE     VD.VOU_SUBTYPE_CPD_ID IN\r\n" + 
    		"                                      (SELECT CPD_ID\r\n" + 
    		"                                         FROM TB_COMPARAM_DET D\r\n" + 
    		"                                        WHERE     D.CPM_ID IN\r\n" + 
    		"                                                     (SELECT CPM_ID\r\n" + 
    		"                                                        FROM TB_COMPARAM_MAS\r\n" + 
    		"                                                             M\r\n" + 
    		"                                                       WHERE M.CPM_PREFIX =\r\n" + 
    		"                                                                'TDP')\r\n" + 
    		"                                              AND CPD_VALUE IN ('PVE','RBP'))\r\n" + 
    		"                               AND VD.SAC_HEAD_ID = SD.SAC_HEAD_ID\r\n" + 
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
    		"                               AND VD.VOU_POSTING_DATE BETWEEN  :frmDate AND :tDate\r\n" + 
    		"                               AND VD.ORGID =:orgId\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 VD.PAC_HEAD_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B \r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		"   WHERE P.LEVEL1 = R.PAC_HEAD_ID AND R.CPD_ID_ACHEADTYPES not in (:coaPaymentLookupId)\r\n" + 
    		"   GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) z  right join TB_AC_PRIMARYHEAD_MASTER pp\r\n" + 
    		"   on pp.PAC_HEAD_COMPO_CODE=z.PAC_HEAD_COMPO_CODE where pp.CPD_ID_ACHEADTYPES not in (:coaPaymentLookupId)\r\n" + 
    		" and pp.PAC_HEAD_PARENT_ID is null and PAC_HEAD_CODE  in (350,320,360,340,410,412,341,420,421,460,440)\r\n" + 
    		"  ORDER BY pp.PAC_HEAD_COMPO_CODE ASC\r\n" + 
    		"   " , nativeQuery = true)
    List<Object[]> queryClassifiedBudgetPaymentSideNonOperatingOpeningReportDataLevel3(@Param("frmDate") Date frmDate, @Param("tDate") Date tDate,
            @Param("orgId") Long orgId, @Param("coaPaymentLookupId") List<Long> l);
    
    
    @Query(value="select fn.function_id,\r\n" + 
			"       pm.pac_head_id,\r\n" + 
			"       cd.cpd_desc Type,       \r\n" + 
			"       fn.function_compcode,fn.function_desc function,\r\n" + 
			"       pm.pac_head_compo_code,pm.pac_head_desc Detail,\r\n" + 
			"       pm.cpd_id_acheadtypes ,\r\n" + 
			"       sm.sac_head_id,\r\n" + 
			"       sm.ac_head_code,\r\n" + 
			"       cd1.cpd_desc status\r\n" + 
			"  from tb_ac_secondaryhead_master sm,\r\n" + 
			"       tb_ac_primaryhead_master   pm,\r\n" + 
			"       tb_comparam_det            cd,\r\n" + 
			"       tb_ac_function_master      fn,\r\n" + 
			"       tb_comparam_det cd1\r\n" + 
			" where sm.orgid =:orgId\r\n" + 
			"   and sm.pac_head_id = pm.pac_head_id\r\n" + 
			"   and pm.cpd_id_acheadtypes = cd.cpd_id\r\n" + 
			"   and sm.function_id = fn.function_id\r\n" + 
			"   and sm.status_cpd_id = cd1.cpd_id\r\n" + 
			" order by fn.function_compcode, pm.pac_head_compo_code,sm.sac_head_code",nativeQuery=true)
	List<Object[]> getScondaryCodeDetails(@Param("orgId") Long orgId);
	
	@Query(value = "select rm.RM_RCPTNO Transation_No,rm.RM_DATE Transaction_Date,rm.RM_AMOUNT Amount,rm.RM_NARRATION Narration,rm.RM_RECEIVEDFROM Receiver_or_PayerName,\r\n"
            + "rm.UPDATED_BY Reversed_By,rm.RECEIPT_DEL_DATE Reversed_Date,rm.RECEIPT_DEL_REMARK Reversal_Reason,rm.RECEIPT_DEL_AUTH_BY Authorized_By,rm.DP_DEPTID \r\n"
            + " from tb_receipt_mas rm\r\n" + "where rm.RECEIPT_DEL_FLAG='Y' \r\n" + "and rm.ORGID =:orgId\r\n"
            + "and rm.RM_DATE between :transactionFromDate and :transactionToDate and FIELD_ID=:fieldId", nativeQuery = true)
    List<Object[]> transactionReversalReportReceiptByDateAndFieldId(@Param("orgId") long orgId,
            @Param("transactionFromDate") Date transactionFromDate, @Param("transactionToDate") Date transactionToDate, @Param("fieldId") Long fieldId);
    @Query(value = "select PAC.PAC_HEAD_COMPO_CODE,CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		" IFNULL(CREDIT,0) CREDIT,IFNULL(DEBIT,0) DEBIT,PAC.SCH_CODE\r\n" + 
    		"FROM TB_AC_PRIMARYHEAD_MASTER PAC\r\n" + 
    		"left join\r\n" + 
    		"(SELECT R.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"       CONCAT((CONCAT(R.PAC_HEAD_COMPO_CODE, ' - ')), R.PAC_HEAD_DESC) AS AC_HEAD,\r\n" + 
    		"       SUM(P.VAMT_CR) AS CREDIT,\r\n" + 
    		"       SUM(P.VAMT_DR) AS DEBIT\r\n" + 
    		"  FROM (SELECT A.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"               A.PAC_HEAD_DESC,\r\n" + 
    		"               A.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               B.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + 
    		"               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       VAMT_CR,\r\n" + 
    		"                       VAMT_DR,\r\n" + 
    		"                       A.PAC_HEAD_ID,\r\n" + 
    		"                       A.FUNCTION_ID\r\n" + 
    		"                  FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                               SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                               SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                               VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                               SM.PAC_HEAD_ID,\r\n" + 
    		"                               SM.FUNCTION_ID\r\n" + 
    		"                          FROM VW_VOUCHER_DETAIL VD,\r\n" + 
    		"                               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + 
    		"                               TB_AC_PRIMARYHEAD_MASTER PM,\r\n" + 
    		"                               TB_AC_FUNCTION_MASTER FM\r\n" + 
    		"                         WHERE     VD.VOU_POSTING_DATE BETWEEN :fromDates \r\n" + 
    		"                                                           AND :toDates \r\n" + 
    		"                               AND VD.ORGID = :orgId\r\n" + 
    		"                               AND PM.CPD_ID_ACHEADTYPES in (:lookUpId)\r\n" + 
    		"                               AND SM.PAC_HEAD_ID = PM.PAC_HEAD_ID\r\n" + 
    		"                               AND SM.SAC_HEAD_ID = VD.SAC_HEAD_ID\r\n" + 
    		"                               AND FM.FUNCTION_ID = SM.FUNCTION_ID\r\n" + 
    		"                        GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                 VD.SAC_HEAD_ID,\r\n" + 
    		"                                 SM.PAC_HEAD_ID,\r\n" + 
    		"                                 SM.FUNCTION_ID) A) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER A,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = A.PAC_HEAD_ID\r\n" + 
    		"               AND A.PAC_HEAD_PARENT_ID = B.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY A.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_DESC,\r\n" + 
    		"                 B.PAC_HEAD_ID,\r\n" + 
    		"                 A.PAC_HEAD_COMPO_CODE) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" + 
    		"GROUP BY LEVEL1, R.PAC_HEAD_DESC, R.PAC_HEAD_COMPO_CODE) INC\r\n" + 
    		"ON CONCAT((CONCAT(PAC.PAC_HEAD_COMPO_CODE, ' - ')), PAC.PAC_HEAD_DESC)= INC.AC_HEAD\r\n" + 
    		"where pac.CPD_ID_ACHEADTYPES in(:lookUpId) and PAC.CODCOFDET_ID = :codDetId and cast(replace(PAC.PAC_HEAD_COMPO_CODE,'-','') as unsigned) in ('280','290')\r\n" + 
    		"ORDER BY cast(replace(PAC.PAC_HEAD_COMPO_CODE,'-','') as unsigned) asc", nativeQuery = true)
    List<Object[]> queryReportDataFromViewMajorHeadIncomeAndExpenditureNMAM(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId, @Param("lookUpId") Long lookUpId,@Param("codDetId") Long codDetId);
    @Query(value = "SELECT SUM(P.VAMT_DR) as VAMT_DR,SUM(P.VAMT_CR) AS VAMT_CR\r\n" + 
    		"  FROM (SELECT C.PAC_HEAD_ID AS LEVEL2,\r\n" + 
    		"               C.PAC_HEAD_COMPO_CODE,\r\n" + 
    		"               D.PAC_HEAD_ID AS LEVEL1,\r\n" + 
    		"               SUM(AAA.OPENBAL_AMT_DR) AS OPENBAL_AMT_DR,\r\n" + 
    		"               SUM(AAA.OPENBAL_AMT_CR) AS OPENBAL_AMT_CR,\r\n" + 
    		"               SUM(AAA.VAMT_DR) AS VAMT_DR,\r\n" + 
    		"               SUM(AAA.VAMT_CR) AS VAMT_CR\r\n" + 
    		"          FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                       SAC_HEAD_ID,\r\n" + 
    		"                       OPENBAL_AMT_DR,\r\n" + 
    		"                       OPENBAL_AMT_CR,\r\n" + 
    		"                       VAMT_CR,\r\n" + 
    		"                       VAMT_DR,\r\n" + 
    		"                       A.PAC_HEAD_ID\r\n" + 
    		"                  FROM (SELECT AC_HEAD_CODE,\r\n" + 
    		"                               SAC_HEAD_ID,\r\n" + 
    		"                               OPENBAL_AMT_DR,\r\n" + 
    		"                               OPENBAL_AMT_CR,\r\n" + 
    		"                               VAMT_CR,\r\n" + 
    		"                               VAMT_DR,\r\n" + 
    		"                               A.PAC_HEAD_ID\r\n" + 
    		"                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                                       VD.SAC_HEAD_ID SAC_HEAD_ID,\r\n" + 
    		"                                       PAC_HEAD_ID\r\n" + 
    		"                                  FROM VW_VOUCHER_DETAIL VD\r\n" + 
    		"                                 WHERE     date(VD.VOU_POSTING_DATE) BETWEEN :fromDates  and :toDates \r\n" + 
    		"                                 \r\n" + 
    		"                                       AND VD.ORGID = :orgId\r\n" + 
    		"                                GROUP BY VD.AC_HEAD_CODE,\r\n" + 
    		"                                         VD.SAC_HEAD_ID,\r\n" + 
    		"                                         PAC_HEAD_ID) A\r\n" + 
    		"                               LEFT JOIN\r\n" + 
    		"                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" + 
    		"                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" + 
    		"                                       (CASE\r\n" + 
    		"                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" + 
    		"                                           THEN\r\n" + 
    		"                                              BG.OPENBAL_AMT\r\n" + 
    		"                                           ELSE\r\n" + 
    		"                                              0\r\n" + 
    		"                                        END)\r\n" + 
    		"                                          AS OPENBAL_AMT_DR,\r\n" + 
    		"                                       (CASE\r\n" + 
    		"                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" + 
    		"                                           THEN\r\n" + 
    		"                                              BG.OPENBAL_AMT\r\n" + 
    		"                                           ELSE\r\n" + 
    		"                                              0\r\n" + 
    		"                                        END)\r\n" + 
    		"                                          AS OPENBAL_AMT_CR,\r\n" + 
    		"                                       B.PAC_HEAD_ID\r\n" + 
    		"                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" + 
    		"                                       TB_COMPARAM_DET CD,\r\n" + 
    		"                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" + 
    		"                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" + 
    		"                                       AND BG.ORGID = :orgId\r\n" + 
    		"                                       AND BG.FA_YEARID = :finYrId\r\n" + 
    		"                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" + 
    		"                                       AND B.ORGID = BG.ORGID) E\r\n" + 
    		"                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1\r\n" + 
    		"                        UNION\r\n" + 
    		"                        SELECT E.AC_HEAD_CODE,\r\n" + 
    		"                               E.SAC_HEAD_ID,\r\n" + 
    		"                               OPENBAL_AMT_DR,\r\n" + 
    		"                               OPENBAL_AMT_CR,\r\n" + 
    		"                               VAMT_CR,\r\n" + 
    		"                               VAMT_DR,\r\n" + 
    		"                               E.PAC_HEAD_ID\r\n" + 
    		"                          FROM (SELECT VD.AC_HEAD_CODE AC_HEAD_CODE,\r\n" + 
    		"                                       SUM(VD.VAMT_CR) VAMT_CR,\r\n" + 
    		"                                       SUM(VD.VAMT_DR) VAMT_DR,\r\n" + 
    		"                                       VD.SAC_HEAD_ID SAC_HEAD_ID\r\n" + 
    		"                                  FROM VW_VOUCHER_DETAIL VD\r\n" + 
    		"                                 WHERE     date(VD.VOU_POSTING_DATE) BETWEEN :fromDates  and :toDates \r\n" + 
    		"                                 AND VD.ORGID =:orgId\r\n" + 
    		"                                GROUP BY VD.AC_HEAD_CODE, VD.SAC_HEAD_ID) A\r\n" + 
    		"                               RIGHT JOIN\r\n" + 
    		"                               (SELECT BG.SAC_HEAD_ID SAC_HEAD_ID1,\r\n" + 
    		"                                       B.AC_HEAD_CODE AC_HEAD_CODE1,\r\n" + 
    		"                                       (CASE\r\n" + 
    		"                                           WHEN CD.CPD_VALUE IN ('DR')\r\n" + 
    		"                                           THEN\r\n" + 
    		"                                              BG.OPENBAL_AMT\r\n" + 
    		"                                           ELSE\r\n" + 
    		"                                              0\r\n" + 
    		"                                        END)\r\n" + 
    		"                                          AS OPENBAL_AMT_DR,\r\n" + 
    		"                                       (CASE\r\n" + 
    		"                                           WHEN CD.CPD_VALUE IN ('CR')\r\n" + 
    		"                                           THEN\r\n" + 
    		"                                              BG.OPENBAL_AMT\r\n" + 
    		"                                           ELSE\r\n" + 
    		"                                              0\r\n" + 
    		"                                        END)\r\n" + 
    		"                                          AS OPENBAL_AMT_CR,\r\n" + 
    		"                                       B.AC_HEAD_CODE,\r\n" + 
    		"                                       B.SAC_HEAD_ID,\r\n" + 
    		"                                       B.PAC_HEAD_ID\r\n" + 
    		"                                  FROM TB_AC_BUGOPEN_BALANCE BG,\r\n" + 
    		"                                       TB_COMPARAM_DET CD,\r\n" + 
    		"                                       TB_AC_SECONDARYHEAD_MASTER B\r\n" + 
    		"                                 WHERE     BG.CPD_ID_DRCR = CD.CPD_ID\r\n" + 
    		"                                       AND BG.ORGID = :orgId\r\n" + 
    		"                                       AND BG.FA_YEARID = :finYrId\r\n" + 
    		"                                       AND B.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" + 
    		"                                       AND B.ORGID = BG.ORGID) E\r\n" + 
    		"                                  ON A.SAC_HEAD_ID = E.SAC_HEAD_ID1) A,\r\n" + 
    		"                       TB_AC_PRIMARYHEAD_MASTER B\r\n" + 
    		"                 WHERE A.PAC_HEAD_ID = B.PAC_HEAD_ID) AAA,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER C,\r\n" + 
    		"               TB_AC_PRIMARYHEAD_MASTER D\r\n" + 
    		"         WHERE     AAA.PAC_HEAD_ID = C.PAC_HEAD_ID\r\n" + 
    		"               AND C.PAC_HEAD_PARENT_ID = D.PAC_HEAD_ID\r\n" + 
    		"        GROUP BY C.PAC_HEAD_ID, D.PAC_HEAD_ID) P,\r\n" + 
    		"       TB_AC_PRIMARYHEAD_MASTER R\r\n" + 
    		" WHERE P.LEVEL1 = R.PAC_HEAD_ID\r\n" + 
    		" AND  R.PAC_HEAD_CODE=272\r\n" + 
    		" GROUP BY LEVEL1,R.PAC_HEAD_COMPO_CODE", nativeQuery = true)
    List<Object[]> queryReportDataFromViewMajorHeadExpenditure320NM(@Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("orgId") Long orgId,@Param("finYrId") Long fId);

    @Query("FROM VoucherDetailViewEntity vw WHERE vw.vouTypeCpdId=:vouTypeCpdId AND vw.orgId=:orgId AND vw.voPostingDate between :fromDate and :toDate and vw.fieldId=:fieldId order by vw.voPostingDate,vw.vouNo asc")
    List<VoucherDetailViewEntity> queryJournalRegisterDataFromViewByFieldId(@Param("vouTypeCpdId") Long vouTypeCpdId,
            @Param("orgId") Long orgId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("fieldId") Long fieldId);

	  @Query("SELECT sum(vtd.drAmount), sum(vtd.crAmount) from VoucherDetailViewEntity vtd where   vtd.orgId =:orgId and vtd.voPostingDate between :financialYFromDate  and :dateBefore ")
	    List<Object[]> getSumOfCreditAndSumOfDebitFromVouchersAll(@Param("financialYFromDate") Date financialYFromDate,
	            @Param("dateBefore") Date dateBefore, @Param("orgId") long orgId);
}
