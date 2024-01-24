
package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;

/**
 * @author tejas.kotekar
 *
 */
public interface TransactionTrackingRepository extends PagingAndSortingRepository<AccountVoucherEntryDetailsEntity, Long> {

    @Query("select distinct(vd.budgetCode.prBudgetCodeid), bm.prBudgetCode from AccountVoucherEntryDetailsEntity vd, AccountBudgetCodeEntity bm where vd.budgetCode.prBudgetCodeid = bm.prBudgetCodeid "
            + "and vd.orgId = bm.orgid and vd.orgId =:orgId")
    List<Object[]> getTransactedAccountHeads(@Param("orgId") Long orgId);

    @Query(value = "SELECT Y.OPENBAL_AMT, Y.SAC_HEAD_ID, Y.CPD_ID_DRCR, Y.TranCr, Y.TranDr\r\n" +
            "  FROM tb_ac_primaryhead_master pr,\r\n" +
            "       tb_ac_secondaryhead_master se,\r\n" +
            "       (SELECT X.OPENBAL_AMT,\r\n" +
            "               (CASE\r\n" +
            "                 WHEN X.OPNSACHEADID IS NULL THEN\r\n" +
            "                  X.TRNSACHEADID\r\n" +
            "                 ELSE\r\n" +
            "                  X.OPNSACHEADID\r\n" +
            "               END) SAC_HEAD_ID,\r\n" +
            "               X.CPD_ID_DRCR,\r\n" +
            "               SUM(X.VAMT_CR) TranCr,\r\n" +
            "               SUM(X.VAMT_DR) TranDr\r\n" +
            "          FROM (SELECT VOUDET_ID,\r\n" +
            "                       A.OPENBAL_AMT,\r\n" +
            "                       A.SAC_HEAD_ID  OPNSACHEADID,\r\n" +
            "                       A.CPD_ID_DRCR,\r\n" +
            "                       vd.VAMT_CR,\r\n" +
            "                       vd.VAMT_DR,\r\n" +
            "                       VD.SAC_HEAD_ID TRNSACHEADID\r\n" +
            "                  FROM (SELECT VOUDET_ID, SAC_HEAD_ID, VAMT_CR, VAMT_DR\r\n" +
            "                          FROM vw_voucher_detail\r\n" +
            "                         WHERE ORGID =:orgId\r\n" +
            "                           and VOU_POSTING_DATE BETWEEN :fromDate and\r\n" +
            "                               :toDate) vd\r\n" +
            "                  LEFT JOIN (SELECT bg.OPENBAL_AMT,\r\n" +
            "                                   bg.CPD_ID_DRCR,\r\n" +
            "                                   SM.SAC_HEAD_ID\r\n" +
            "                              FROM tb_ac_bugopen_balance      bg,\r\n" +
            "                                   tb_ac_secondaryhead_master sm\r\n" +
            "                             WHERE sm.SAC_HEAD_ID = bg.SAC_HEAD_ID\r\n" +
            "                               AND sm.ORGID =:orgId\r\n" +
            "                               and bg.FA_YEARID =:faYearid) A\r\n" +
            "                    ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID\r\n" +
            "                UNION\r\n" +
            "                SELECT\r\n" +
            "                \r\n" +
            "                 VOUDET_ID,\r\n" +
            "                 A.OPENBAL_AMT,\r\n" +
            "                 A.SAC_HEAD_ID  OPNSACHEADID,\r\n" +
            "                 A.CPD_ID_DRCR,\r\n" +
            "                 vd.VAMT_CR,\r\n" +
            "                 vd.VAMT_DR,\r\n" +
            "                 VD.SAC_HEAD_ID TRNSACHEADID\r\n" +
            "                  FROM (SELECT VOUDET_ID, SAC_HEAD_ID, VAMT_CR, VAMT_DR\r\n" +
            "                          FROM vw_voucher_detail\r\n" +
            "                         WHERE ORGID =:orgId\r\n" +
            "                           and VOU_POSTING_DATE BETWEEN :fromDate and\r\n" +
            "                               :toDate) VD\r\n" +
            "                 RIGHT JOIN (SELECT bg.OPENBAL_AMT,\r\n" +
            "                                   bg.CPD_ID_DRCR,\r\n" +
            "                                   SM.SAC_HEAD_ID\r\n" +
            "                              FROM tb_ac_bugopen_balance      bg,\r\n" +
            "                                   tb_ac_secondaryhead_master sm\r\n" +
            "                             WHERE sm.SAC_HEAD_ID = bg.SAC_HEAD_ID\r\n" +
            "                               AND sm.ORGID = :orgId\r\n" +
            "                               and bg.FA_YEARID =:faYearid) A\r\n" +
            "                    ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID) X\r\n" +
            "         GROUP BY X.OPNSACHEADID,\r\n" +
            "                  X.OPENBAL_AMT,\r\n" +
            "                  X.CPD_ID_DRCR,\r\n" +
            "                  X.TRNSACHEADID) Y\r\n" +
            " where Y.SAC_HEAD_ID = SE.SAC_HEAD_ID\r\n" +
            "   AND SE.PAC_HEAD_ID = PR.PAC_HEAD_ID\r\n" +
            " ORDER BY pr.PAC_HEAD_COMPO_CODE\r\n" +
            " ", nativeQuery = true)
    List<Object[]> getTransactionTrackingTrialBalance(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("faYearid") Long faYearid);

    @Query(value = "SELECT Y.OPENBAL_AMT, Y.SAC_HEAD_ID, Y.CPD_ID_DRCR, Y.TranCr, Y.TranDr\r\n" +
            "  FROM tb_ac_primaryhead_master pr,\r\n" +
            "       tb_ac_secondaryhead_master se,\r\n" +
            "       (SELECT X.OPENBAL_AMT,\r\n" +
            "               (CASE\r\n" +
            "                 WHEN X.OPNSACHEADID IS NULL THEN\r\n" +
            "                  X.TRNSACHEADID\r\n" +
            "                 ELSE\r\n" +
            "                  X.OPNSACHEADID\r\n" +
            "               END) SAC_HEAD_ID,\r\n" +
            "               X.CPD_ID_DRCR,\r\n" +
            "               SUM(X.VAMT_CR) TranCr,\r\n" +
            "               SUM(X.VAMT_DR) TranDr\r\n" +
            "          FROM (SELECT VOUDET_ID,\r\n" +
            "                       A.OPENBAL_AMT,\r\n" +
            "                       A.SAC_HEAD_ID  OPNSACHEADID,\r\n" +
            "                       A.CPD_ID_DRCR,\r\n" +
            "                       vd.VAMT_CR,\r\n" +
            "                       vd.VAMT_DR,\r\n" +
            "                       VD.SAC_HEAD_ID TRNSACHEADID\r\n" +
            "                  FROM (SELECT VOUDET_ID, SAC_HEAD_ID, VAMT_CR, VAMT_DR\r\n" +
            "                          FROM vw_voucher_detail\r\n" +
            "                         WHERE ORGID =:orgId\r\n" +
            "                           and VOU_POSTING_DATE BETWEEN :fromDate and\r\n" +
            "                               :toDate) vd\r\n" +
            "                  LEFT JOIN (SELECT bg.OPENBAL_AMT,\r\n" +
            "                                   bg.CPD_ID_DRCR,\r\n" +
            "                                   SM.SAC_HEAD_ID\r\n" +
            "                              FROM tb_ac_bugopen_balance      bg,\r\n" +
            "                                   tb_ac_secondaryhead_master sm\r\n" +
            "                             WHERE sm.SAC_HEAD_ID = bg.SAC_HEAD_ID\r\n" +
            "                               AND sm.ORGID =:orgId\r\n" +
            "                               and bg.FA_YEARID =:faYearid) A\r\n" +
            "                    ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID\r\n" +
            "                UNION\r\n" +
            "                SELECT\r\n" +
            "                \r\n" +
            "                 VOUDET_ID,\r\n" +
            "                 A.OPENBAL_AMT,\r\n" +
            "                 A.SAC_HEAD_ID  OPNSACHEADID,\r\n" +
            "                 A.CPD_ID_DRCR,\r\n" +
            "                 vd.VAMT_CR,\r\n" +
            "                 vd.VAMT_DR,\r\n" +
            "                 VD.SAC_HEAD_ID TRNSACHEADID\r\n" +
            "                  FROM (SELECT VOUDET_ID, SAC_HEAD_ID, VAMT_CR, VAMT_DR\r\n" +
            "                          FROM vw_voucher_detail\r\n" +
            "                         WHERE ORGID =:orgId\r\n" +
            "                           and VOU_POSTING_DATE BETWEEN :fromDate and\r\n" +
            "                               :toDate) VD\r\n" +
            "                 RIGHT JOIN (SELECT bg.OPENBAL_AMT,\r\n" +
            "                                   bg.CPD_ID_DRCR,\r\n" +
            "                                   SM.SAC_HEAD_ID\r\n" +
            "                              FROM tb_ac_bugopen_balance      bg,\r\n" +
            "                                   tb_ac_secondaryhead_master sm\r\n" +
            "                             WHERE sm.SAC_HEAD_ID = bg.SAC_HEAD_ID\r\n" +
            "                               AND sm.ORGID = :orgId\r\n" +
            "                               and bg.FA_YEARID =:faYearid) A\r\n" +
            "                    ON vd.SAC_HEAD_ID = A.SAC_HEAD_ID) X\r\n" +
            "         GROUP BY X.OPNSACHEADID,\r\n" +
            "                  X.OPENBAL_AMT,\r\n" +
            "                  X.CPD_ID_DRCR,\r\n" +
            "                  X.TRNSACHEADID) Y\r\n" +
            " where Y.SAC_HEAD_ID = SE.SAC_HEAD_ID\r\n" +
            "   AND SE.PAC_HEAD_ID = PR.PAC_HEAD_ID\r\n" +
            " And Y.SAC_HEAD_ID=:sacHeadId " +
            " ORDER BY pr.PAC_HEAD_COMPO_CODE\r\n" + " ", nativeQuery = true)
    List<Object[]> getTransactionTrackingHeadWise(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("faYearid") Long faYearid, @Param("sacHeadId") String sacHeadId);

    @Query("select  distinct(voPostingDate)  from VoucherDetailViewEntity where orgId=:orgId and voPostingDate between :fromDates and :toDates and sacHeadId=:sacHeadId  order by 1 asc ")
    List<Object> findvoucherPostingDatesInMonth(@Param("orgId") Long orgId, @Param("fromDates") Date fromDates,
            @Param("toDates") Date toDates, @Param("sacHeadId") Long sacHeadId);

    @Query("select vw.vouNo,vw.voPostingDate,vw.particulars,vw.crAmount,vw.drAmount,vw.vouId\r\n" +
            "from VoucherDetailViewEntity vw where vw.voPostingDate =:fromDates and vw.sacHeadId =:sacHeadId and vw.orgId=:orgId ")
    List<Object[]> findVoucherWiseTransactionsRepo(@Param("orgId") Long orgId,
            @Param("sacHeadId") Long sacHeadId, @Param("fromDates") Date fromDates);

}
