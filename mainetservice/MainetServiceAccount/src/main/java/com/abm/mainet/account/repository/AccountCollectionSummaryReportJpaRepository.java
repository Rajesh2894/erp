package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

public interface AccountCollectionSummaryReportJpaRepository extends PagingAndSortingRepository<TbServiceReceiptMasEntity, Long> {

    @Query(value = "SELECT A.DP_DEPTDESC,\r\n" +
            "       A.SAC_HEAD_ID,\r\n" +
            "       A.AC_HEAD_CODE,\r\n" +
            "       SUM(A.CASH_AMOUNT),\r\n" +
            "       SUM(A.CHEQUE_DD_AMOUNT),\r\n" +
            "       SUM(A.BANK_AMOUNT)\r\n" +
            "  FROM (SELECT R.RM_RCPTID,\r\n" +
            "               D.RF_FEEAMOUNT  AS CASH_AMOUNT,\r\n" +
            "               NULL            AS BANK_AMOUNT,\r\n" +
            "               NULL            AS CHEQUE_DD_AMOUNT,\r\n" +
            "               D.SAC_HEAD_ID,\r\n" +
            "               SM.AC_HEAD_CODE,\r\n" +
            "               R.ORGID,\r\n" +
            "               R.RM_DATE,\r\n" +
            "               DP.DP_DEPTDESC\r\n" +
            "          FROM TB_RECEIPT_MAS             R,\r\n" +
            "               TB_RECEIPT_DET             D,\r\n" +
            "               TB_RECEIPT_MODE            M,\r\n" +
            "               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "               TB_COMPARAM_MAS            CM,\r\n" +
            "               TB_COMPARAM_DET            CD,\r\n" +
            "               TB_DEPARTMENT              DP\r\n" +
            "         WHERE R.RM_RCPTID = D.RM_RCPTID\r\n" +
            "           AND R.ORGID = D.ORGID\r\n" +
            "           AND R.RM_RCPTID = M.RM_RCPTID\r\n" +
            "           AND R.ORGID = M.ORGID\r\n" +
            "           AND D.SAC_HEAD_ID = SM.SAC_HEAD_ID\r\n" +
            "           AND D.ORGID = SM.ORGID\r\n" +
            "           AND R.ORGID = SM.ORGID\r\n" +
            "           AND M.ORGID = SM.ORGID\r\n" +
            "           AND R.DP_DEPTID = DP.DP_DEPTID\r\n" +
            "           AND CM.CPM_ID = CD.CPM_ID\r\n" +
            "           AND CD.CPD_VALUE = 'C'\r\n" +
            "           AND D.RM_RCPTID = M.RM_RCPTID\r\n" +
            "           AND R.RECEIPT_DEL_FLAG IS NULL\r\n" +
            "           AND R.RECEIPT_TYPE_FLAG IN ('M', 'R', 'A', 'P')\r\n" +
            "           AND CM.CPM_PREFIX = 'PAY'\r\n" +
            "           AND CD.CPD_ID = M.CPD_FEEMODE\r\n" +
            "        UNION\r\n" +
            "        SELECT R.RM_RCPTID,\r\n" +
            "               NULL            AS CASH_AMOUNT,\r\n" +
            "               D.RF_FEEAMOUNT  AS BANK_AMOUNT,\r\n" +
            "               NULL            AS CHEQUE_DD_AMOUNT,\r\n" +
            "               D.SAC_HEAD_ID,\r\n" +
            "               SM.AC_HEAD_CODE,\r\n" +
            "               R.ORGID,\r\n" +
            "               R.RM_DATE,\r\n" +
            "               DP.DP_DEPTDESC\r\n" +
            "          FROM TB_RECEIPT_MAS             R,\r\n" +
            "               TB_RECEIPT_DET             D,\r\n" +
            "               TB_RECEIPT_MODE            M,\r\n" +
            "               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "               TB_COMPARAM_MAS            CM,\r\n" +
            "               TB_COMPARAM_DET            CD,\r\n" +
            "               TB_DEPARTMENT              DP\r\n" +
            "         WHERE R.RM_RCPTID = D.RM_RCPTID\r\n" +
            "           AND R.ORGID = D.ORGID\r\n" +
            "           AND R.RM_RCPTID = M.RM_RCPTID\r\n" +
            "           AND R.ORGID = M.ORGID\r\n" +
            "           AND D.SAC_HEAD_ID = SM.SAC_HEAD_ID\r\n" +
            "           AND D.ORGID = SM.ORGID\r\n" +
            "           AND R.ORGID = SM.ORGID\r\n" +
            "           AND M.ORGID = SM.ORGID\r\n" +
            "           AND CM.CPM_ID = CD.CPM_ID\r\n" +
            "           AND R.DP_DEPTID = DP.DP_DEPTID\r\n" +
            "           AND CD.CPD_VALUE = 'B'\r\n" +
            "           AND D.RM_RCPTID = M.RM_RCPTID\r\n" +
            "           AND R.RECEIPT_DEL_FLAG IS NULL\r\n" +
            "           AND R.RECEIPT_TYPE_FLAG IN ('M', 'R', 'A', 'P')\r\n" +
            "           AND CM.CPM_PREFIX = 'PAY'\r\n" +
            "           AND CD.CPD_ID = M.CPD_FEEMODE\r\n" +
            "        UNION\r\n" +
            "        SELECT R.RM_RCPTID,\r\n" +
            "               NULL            AS CASH_AMOUNT,\r\n" +
            "               NULL            AS BANK_AMOUNT,\r\n" +
            "               D.RF_FEEAMOUNT  AS CHEQUE_DD_AMOUNT,\r\n" +
            "               D.SAC_HEAD_ID,\r\n" +
            "               SM.AC_HEAD_CODE,\r\n" +
            "               R.ORGID,\r\n" +
            "               R.RM_DATE,\r\n" +
            "               DP.DP_DEPTDESC\r\n" +
            "          FROM TB_RECEIPT_MAS             R,\r\n" +
            "               TB_RECEIPT_DET             D,\r\n" +
            "               TB_RECEIPT_MODE            M,\r\n" +
            "               TB_BANK_MASTER             BM,\r\n" +
            "               TB_AC_SECONDARYHEAD_MASTER SM,\r\n" +
            "               TB_COMPARAM_MAS            CM,\r\n" +
            "               TB_COMPARAM_DET            CD,\r\n" +
            "               TB_DEPARTMENT              DP\r\n" +
            "         WHERE R.RM_RCPTID = D.RM_RCPTID\r\n" +
            "           AND R.ORGID = D.ORGID\r\n" +
            "           AND R.RM_RCPTID = M.RM_RCPTID\r\n" +
            "           AND R.ORGID = M.ORGID\r\n" +
            "           AND BM.BANKID = M.BANKID\r\n" +
            "           AND D.SAC_HEAD_ID = SM.SAC_HEAD_ID\r\n" +
            "           AND R.DP_DEPTID = DP.DP_DEPTID\r\n" +
            "           AND D.ORGID = SM.ORGID\r\n" +
            "           AND R.ORGID = SM.ORGID\r\n" +
            "           AND M.ORGID = SM.ORGID\r\n" +
            "           AND R.RECEIPT_DEL_FLAG IS NULL\r\n" +
            "           AND R.RECEIPT_TYPE_FLAG IN ('M', 'R', 'A', 'P')\r\n" +
            "           AND CM.CPM_ID = CD.CPM_ID\r\n" +
            "           AND CD.CPD_VALUE NOT IN ('C', 'B')\r\n" +
            "           AND CM.CPM_PREFIX = 'PAY'\r\n" +
            "           AND D.RM_RCPTID = M.RM_RCPTID\r\n" +
            "           AND CD.CPD_ID = M.CPD_FEEMODE) A\r\n" +
            " WHERE A.ORGID =:orgId\r\n" +
            "   AND A.RM_DATE BETWEEN :fromDates AND :toDates\r\n" +
            " GROUP BY A.SAC_HEAD_ID, A.AC_HEAD_CODE, A.DP_DEPTDESC", nativeQuery = true)
    List<Object[]> findCollectionSummaryReportByTodateAndFromDateAndOrgId(@Param("toDates") Date toDates,
            @Param("fromDates") Date fromDates, @Param("orgId") long orgId);

    @Query(value = "SELECT\r\n" + 
    		"    r.RM_RCPTID,\r\n" + 
    		"    r.RM_RCPTNO,\r\n" + 
    		"    r.rm_date,\r\n" + 
    		"    m.CPD_FEEMODE,\r\n" + 
    		"    r.RM_RECEIVEDFROM,\r\n" + 
    		"    m.RD_CHEQUEDDNO,\r\n" + 
    		"    SUM(m.RD_AMOUNT) AS Receipt_Amount,\r\n" + 
    		"  r.RM_NARRATION,\r\n" + 
    		"   r.DP_DEPTID\r\n" + 
    		"   FROM\r\n" + 
    		"    tb_receipt_mas r,\r\n" + 
    		"    tb_receipt_mode m,\r\n" + 
    		"    tb_comparam_mas cm,\r\n" + 
    		"    tb_comparam_det cd\r\n" + 
    		" WHERE\r\n" + 
    		"    r.RM_RCPTID = m.RM_RCPTID\r\n" + 
    		"        AND cm.CPM_ID = cd.CPM_ID\r\n" + 
    		"        AND cd.CPD_ID = m.CPD_FEEMODE\r\n" + 
    		"        AND r.RECEIPT_DEL_FLAG is null        AND r.RECEIPT_TYPE_FLAG in ('M','R','A','P')\r\n" + 
    		"       AND r.ORGID =:orgId\r\n" + 
    		"                 AND rm_date BETWEEN :fromDates AND :toDates\r\n" + 
    		"\r\n" + 
    		"GROUP BY  r.RM_RCPTID , r.RM_RCPTNO , r.rm_date , m.CPD_FEEMODE , r.RM_RECEIVEDFROM , m.RD_CHEQUEDDNO,r.RM_NARRATION,r.DP_DEPTID\r\n" + 
    		"order by r.rm_date,r.RM_RCPTNO asc", nativeQuery = true)
    List<Object[]> findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(@Param("toDates") Date toDates,
            @Param("fromDates") Date fromDates, @Param("orgId") Long orgId);

    @Query("SELECT \r\n" +
            "    rm.rmRcptid,\r\n" +
            "    ba.baAccountNo,\r\n" +
            "    dm.depositeSlipDate,\r\n" +
            "    re.rdSrChkDate,\r\n" +
            "    re.checkStatus,\r\n" +
            "    rm.rmNarration\r\n" +
            "FROM\r\n" +
            "    TbServiceReceiptMasEntity rm,\r\n" +
            "    TbSrcptFeesDetEntity rd,\r\n" +
            "    TbSrcptModesDetEntity re,\r\n" +
            "    AccountBankDepositeSlipMasterEntity dm,\r\n" +
            "    BankAccountMasterEntity ba\r\n" +
            "WHERE\r\n" +
            "    rm.rmRcptid = rd.rmRcptid.rmRcptid\r\n" +
            "        AND rm.rmRcptid = re.rmRcptid.rmRcptid\r\n" +
            "        AND rd.rmRcptid.rmRcptid = re.rmRcptid.rmRcptid\r\n" +
            "        AND rd.depositeSlipId = dm.depositeSlipId\r\n" +
            "        AND dm.depositeBAAccountId = ba.baAccountId\r\n" +
            "        AND rm.orgId =:orgId \r\n" +
            "        AND rm.rmRcptid =:receiptId")
    List<Object[]> findBankDetailByReceiptHeadIdAndOrgId(@Param("receiptId") Long receiptId, @Param("orgId") Long orgId);

}
