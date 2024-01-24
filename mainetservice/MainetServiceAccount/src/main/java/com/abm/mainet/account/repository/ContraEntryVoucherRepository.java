package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountContraVoucherEntryEntity;
import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;

/** @author tejas.kotekar */

public interface ContraEntryVoucherRepository extends
        PagingAndSortingRepository<AccountContraVoucherEntryEntity, Long> {

    /**
     * @param bankId
     * @param bankAcId
     * @param statusId
     * @param orgId
     * @return
     */
    @Query("select d from TbAcChequebookleafMasEntity m,TbAcChequebookleafDetEntity d where m.chequebookId = d.tbAcChequebookleafMas.chequebookId and m.orgid = d.orgid and m.baAccountid =:bankAcId and d.cpdIdstatus =:statusId")
    List<TbAcChequebookleafDetEntity> getChequeNumbers(@Param("bankAcId") Long bankAcId, @Param("statusId") Long statusId);

    /**
     * @param orgId
     * @return
     */
    @Query("select a.depositeSlipNumber from AccountBankDepositeSlipMasterEntity a where a.orgid =:orgId and a.depositTypeFlag = 'C' order by a.depositeSlipNumber ASC")
    List<Long> getTransactionNo(@Param("orgId") Long orgId);

    /**
     * @param bankAcIdPay
     * @param orgId
     * @return
     */

    @Query(value = "  SELECT X.OPENBAL_AMT,\r\n" + 
    		"       (CASE\r\n" + 
    		"         WHEN X.OPNSACHEADID IS NULL THEN\r\n" + 
    		"          X.TRNSACHEADID\r\n" + 
    		"         ELSE\r\n" + 
    		"          X.OPNSACHEADID\r\n" + 
    		"       END) SAC_HEAD_ID,\r\n" + 
    		"       X.CPD_ID_DRCR,\r\n" + 
    		"       SUM(X.VAMT_CR),\r\n" + 
    		"       SUM(X.VAMT_DR)\r\n" + 
    		"  FROM (SELECT A.OPENBAL_AMT,\r\n" + 
    		"               A.SAC_HEAD_ID  OPNSACHEADID,\r\n" + 
    		"               A.CPD_ID_DRCR,\r\n" + 
    		"               VD.VAMT_CR,\r\n" + 
    		"               VD.VAMT_DR,\r\n" + 
    		"               VD.SAC_HEAD_ID TRNSACHEADID, \r\n" + 
    		"				VOU_ID    FROM (select \r\n" + 
    		"ac.VOU_ID AS VOU_ID,\r\n" + 
    		"ac.VOU_POSTING_DATE AS VOU_POSTING_DATE,\r\n" + 
    		"vd.SAC_HEAD_ID AS SAC_HEAD_ID,\r\n" + 
    		"b.BA_ACCOUNTID AS BA_ACCOUNTID,\r\n" + 
    		"(case when (vd.DRCR_CPD_ID = (select cd.CPD_ID from (tb_comparam_det cd join tb_comparam_mas cm) where ((cm.CPM_ID = cd.CPM_ID) and (cd.CPD_VALUE = 'CR') and (cm.CPM_PREFIX = 'DCR')))) then coalesce(vd.VOUDET_AMT,0) else 0 end) AS VAMT_CR,\r\n" + 
    		"(case when (vd.DRCR_CPD_ID = (select cd.CPD_ID from (tb_comparam_det cd join tb_comparam_mas cm) where ((cm.CPM_ID = cd.CPM_ID) and (cd.CPD_VALUE = 'DR') and (cm.CPM_PREFIX = 'DCR')))) then coalesce(vd.VOUDET_AMT,0) else 0 end) AS VAMT_DR\r\n" + 
    		"from (((tb_ac_voucher ac join tb_ac_voucher_det vd) join tb_comparam_det cd) join tb_ac_secondaryhead_master b) where ((ac.ORGID = vd.ORGID) and (ac.VOU_ID = vd.VOU_ID) and (ac.VOU_TYPE_CPD_ID = cd.CPD_ID) and (cd.CPD_STATUS = 'A') and (vd.ORGID = b.ORGID) and (vd.SAC_HEAD_ID = b.SAC_HEAD_ID) and (ac.AUTHO_FLG = 'Y'))\r\n" + 
    		"                  \r\n" + 
    		"                and  b.BA_ACCOUNTID =:bankAcIdPay\r\n" + 
    		"                   AND DATE(ac.VOU_POSTING_DATE) BETWEEN :finYrStartDate AND :tranDate) VD\r\n" + 
    		"          LEFT JOIN (SELECT BG.OPENBAL_AMT, BG.CPD_ID_DRCR, SM.SAC_HEAD_ID\r\n" + 
    		"                      FROM TB_BANK_ACCOUNT            BA,\r\n" + 
    		"                           TB_AC_BUGOPEN_BALANCE      BG,\r\n" + 
    		"                           TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + 
    		"                           TB_FINANCIALYEAR           FY\r\n" + 
    		"                     WHERE SM.BA_ACCOUNTID = BA.BA_ACCOUNTID\r\n" + 
    		"                       AND SM.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" + 
    		"                       AND BG.FA_YEARID = FY.FA_YEARID\r\n" + 
    		"                       AND SM.ORGID =:orgid\r\n" + 
    		"                       AND SM.BA_ACCOUNTID =:bankAcIdPay\r\n" + 
    		"                       AND FY.FA_YEARID = :finYearId) A\r\n" + 
    		"            ON VD.SAC_HEAD_ID = A.SAC_HEAD_ID\r\n" + 
    		"        UNION\r\n" + 
    		"        SELECT A.OPENBAL_AMT,\r\n" + 
    		"               A.SAC_HEAD_ID  OPNSACHEADID,\r\n" + 
    		"               A.CPD_ID_DRCR,\r\n" + 
    		"               VD.VAMT_CR,\r\n" + 
    		"               VD.VAMT_DR,\r\n" + 
    		"               VD.SAC_HEAD_ID TRNSACHEADID, \r\n" + 
    		"				VOU_ID           FROM (select \r\n" + 
    		"ac.VOU_ID AS VOU_ID,\r\n" + 
    		"ac.VOU_POSTING_DATE AS VOU_POSTING_DATE,\r\n" + 
    		"vd.SAC_HEAD_ID AS SAC_HEAD_ID,\r\n" + 
    		"b.BA_ACCOUNTID AS BA_ACCOUNTID,\r\n" + 
    		"(case when (vd.DRCR_CPD_ID = (select cd.CPD_ID from (tb_comparam_det cd join tb_comparam_mas cm) where ((cm.CPM_ID = cd.CPM_ID) and (cd.CPD_VALUE = 'CR') and (cm.CPM_PREFIX = 'DCR')))) then coalesce(vd.VOUDET_AMT,0) else 0 end) AS VAMT_CR,\r\n" + 
    		"(case when (vd.DRCR_CPD_ID = (select cd.CPD_ID from (tb_comparam_det cd join tb_comparam_mas cm) where ((cm.CPM_ID = cd.CPM_ID) and (cd.CPD_VALUE = 'DR') and (cm.CPM_PREFIX = 'DCR')))) then coalesce(vd.VOUDET_AMT,0) else 0 end) AS VAMT_DR\r\n" + 
    		"from (((tb_ac_voucher ac join tb_ac_voucher_det vd) join tb_comparam_det cd) join tb_ac_secondaryhead_master b) where ((ac.ORGID = vd.ORGID) and (ac.VOU_ID = vd.VOU_ID) and (ac.VOU_TYPE_CPD_ID = cd.CPD_ID) and (cd.CPD_STATUS = 'A') and (vd.ORGID = b.ORGID) and (vd.SAC_HEAD_ID = b.SAC_HEAD_ID) and (ac.AUTHO_FLG = 'Y'))\r\n" + 
    		"                  \r\n" + 
    		"              and    b.BA_ACCOUNTID =:bankAcIdPay\r\n" + 
    		"                   AND DATE(ac.VOU_POSTING_DATE) BETWEEN :finYrStartDate AND :tranDate) VD\r\n" + 
    		"         RIGHT JOIN (SELECT BG.OPENBAL_AMT, BG.CPD_ID_DRCR, SM.SAC_HEAD_ID\r\n" + 
    		"                      FROM TB_BANK_ACCOUNT            BA,\r\n" + 
    		"                           TB_AC_BUGOPEN_BALANCE      BG,\r\n" + 
    		"                           TB_AC_SECONDARYHEAD_MASTER SM,\r\n" + 
    		"                           TB_FINANCIALYEAR           FY\r\n" + 
    		"                     WHERE SM.BA_ACCOUNTID = BA.BA_ACCOUNTID\r\n" + 
    		"                       AND SM.SAC_HEAD_ID = BG.SAC_HEAD_ID\r\n" + 
    		"                       AND BG.FA_YEARID = FY.FA_YEARID\r\n" + 
    		"                       AND SM.ORGID =:orgid\r\n" + 
    		"                       AND SM.BA_ACCOUNTID =:bankAcIdPay\r\n" + 
    		"                       AND FY.FA_YEARID =:finYearId) A\r\n" + 
    		"            ON VD.SAC_HEAD_ID = A.SAC_HEAD_ID \r\n" + 
    		"			) X\r\n" + 
    		" GROUP BY X.OPNSACHEADID, X.OPENBAL_AMT, X.CPD_ID_DRCR, X.TRNSACHEADID;", nativeQuery = true)
    List<Object[]> getBankAcBalance(@Param("bankAcIdPay") Long bankAcIdPay, @Param("tranDate") Date tranDate,
            @Param("orgid") Long orgid, @Param("finYearId") Long finYearId, @Param("finYrStartDate") Date finYearStartDate);
    @Query("SELECT sm.sacHeadId FROM  AccountHeadSecondaryAccountCodeMasterEntity sm WHERE sm.tbBankaccount.baAccountId =:bankAccountId"
            + " AND sm.orgid =:orgId")
    Long getSacHeadIdByBankAccountId(@Param("bankAccountId") Long bankAccountId, @Param("orgId") Long orgId);

    @Query("select bm.prBudgetCodeid from AccountBudgetCodeEntity bm,AccountHeadPrimaryAccountCodeMasterEntity pm, AccountHeadSecondaryAccountCodeMasterEntity sm"
            + " where pm.cpdIdPayMode =:cpdIdayMode and sm.tbAcPrimaryheadMaster.primaryAcHeadId = pm.primaryAcHeadId and bm.tbAcSecondaryheadMaster.sacHeadId = sm.sacHeadId"
            + " and sm.orgid=pm.orgid and bm.orgid=pm.orgid and bm.orgid=sm.orgid  and bm.orgid =:orgId")
    Long getBudgetCodeIdForPettyChash(@Param("cpdIdayMode") Long cpdIdayMode, @Param("orgId") Long orgId);

    @Query("select m.depositeSlipNumber,m.depositeSlipDate,m.depositeAmount from AccountBankDepositeSlipMasterEntity m where m.depositeSlipId =:paymentId and m.orgid =:orgId")
    List<Object[]> getDepositDetailsForChequeUtilization(@Param("paymentId") Long paymentId, @Param("orgId") Long orgId);

    @Query("select sum(vw.crAmount),sum(vw.drAmount) from VoucherDetailViewEntity vw where vw.vouSubtypeCpdId IN :voucherSubTypeId and vw.sacHeadId =:sacHeadId and vw.orgId =:orgId and vw.voPostingDate <=:date")
    List<Object[]> getPettyCashAmount(@Param("date") Date date, @Param("orgId") Long orgId, @Param("sacHeadId") Long sacHeadId,
            @Param("voucherSubTypeId") List<Long> vouSubTypes);

    @Query("select sum(vw.crAmount),sum(vw.drAmount) from VoucherDetailViewEntity vw where vw.sacHeadId =:sacHeadId and vw.orgId =:orgId and vw.voPostingDate <=:date")
    List<Object[]> getCashAmount(@Param("date") Date date, @Param("orgId") Long orgId, @Param("sacHeadId") Long sacHeadId);

}
