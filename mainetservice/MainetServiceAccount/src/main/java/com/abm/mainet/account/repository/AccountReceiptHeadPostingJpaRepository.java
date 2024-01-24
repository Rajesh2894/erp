
package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;

/**
 * @author dharmendra.chouhan
 *
 */
public interface AccountReceiptHeadPostingJpaRepository extends
        PagingAndSortingRepository<TbSrcptFeesDetEntity, Long> {

    @Modifying
    @Query("update TbSrcptFeesDetEntity detail set detail.depositeSlipId =:depositeSlipId where detail.rmRcptid.rmRcptid=:rmRcptid")
    void updateReceiptWiseDepositId(@Param("rmRcptid") Long rmRcptid, @Param("depositeSlipId") Long depositeSlipId);

    /**
     * @param rfFeeid
     * @param depositeSlipId
     */
    @Modifying
    @Query("update TbSrcptFeesDetEntity detail set detail.depositeSlipId.depositeSlipId=:depositeSlipId where detail.rfFeeid=:rfFeeid")
    void updateLedgerWiseDepositId(@Param("rfFeeid") Long rfFeeid, @Param("depositeSlipId") Long depositeSlipId);

    //Changes against Id #120771
    @Modifying
    @Query("update TbSrcptModesDetEntity detail set checkStatus=:statusId where rmRcptid.rmRcptid=:rmRcptid")
    void updateSrcptModeChequeFlagUpdate(@Param("rmRcptid") Long rmRcptid, @Param("statusId") Long statusId);

    @Query("select mas from TbServiceReceiptMasEntity mas where mas.rmRcptid=:rmRcptid")
    TbServiceReceiptMasEntity getSrcptReciptMasDeatilsById(@Param("rmRcptid") Long rmRcptid);

    @Query("select DISTINCT tb.rmRcptid.rmRcptid from TbSrcptFeesDetEntity tb where tb.depositeSlipId=:depositeSlipId and tb.orgId=:orgid")
    List<Long> findRmcpIdBySlipId(@Param("depositeSlipId") Long depositeSlipId, @Param("orgid") Long orgid);

    @Query("select ts.rdChequeddno, ts.cbBankid , ts.rdAmount,ts.baAccountid,ts.tranRefNumber,ts.rdChequedddate,ts.rdModesid from TbSrcptModesDetEntity ts where ts.rmRcptid.rmRcptid=:rmcpid and ts.orgId=:orgid")
    List<Object[]> findChequeNoByRm_RcpId(@Param("rmcpid") long rmcpid, @Param("orgid") long orgid);

    @Query("select bs.branch, bs.bank from BankMasterEntity bs where bs.bankId=:bankId")
    List<Object[]> findBranchNameByBId(@Param("bankId") long bankId);

    @Query("select ba.bankId.bankId from BankAccountMasterEntity ba where ba.baAccountId=:bankId and ba.orgId=:orgid")
    Long findBankAccountBranchNameByBId(@Param("bankId") Long bankId, @Param("orgid") Long orgid);
    @Query("select mas.rdModesid from TbSrcptModesDetEntity mas where mas.rmRcptid.rmRcptid=:rmRcptid")
    Long getSrcptReciptModeId(@Param("rmRcptid") Long rmRcptid);

}
