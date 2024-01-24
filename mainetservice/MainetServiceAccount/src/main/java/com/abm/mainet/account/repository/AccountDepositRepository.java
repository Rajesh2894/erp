
package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountDepositEntity;
import com.abm.mainet.common.constant.MainetConstants;

public interface AccountDepositRepository extends
        PagingAndSortingRepository<AccountDepositEntity, Long> {

    @Override
    @Query("select dp from AccountDepositEntity dp where dp.depId=:depId")
    AccountDepositEntity findOne(@Param(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT_ID) Long depId);

    @Query("select te from AccountDepositEntity te where te.orgid =:orgId order by 1 desc")
    List<AccountDepositEntity> findAll(@Param(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID) Long orgId);

    @Query("select te.depAmount from AccountDepositEntity te where te.depId =:trEmdAmt and te.orgid =:orgId")
    BigDecimal getEmdAmount(@Param("trEmdAmt") Long trEmdAmt, @Param("orgId") Long orgId);

    @Query("select be.tbAcSecondaryheadMaster.sacHeadId from AccountBudgetCodeEntity be where be.prBudgetCodeid =:prBugetCodeid and be.orgid =:orgId")
    Long getSacHeadIdByBudgetCodeId(@Param("prBugetCodeid") Long prBugetCodeid, @Param("orgId") Long orgId);

    @Modifying
    @Query("update AccountDepositEntity as de set de.tbComparamDetEntity3.cpdId =:statusId where de.depId =:depId and de.orgid =:orgid")
    void updateDepositBalanceAmountInDepositTable(@Param("depId") Long depId, @Param("statusId") Long statusId,
            @Param("orgid") Long orgid);

    @Modifying
    @Query("update AccountDepositEntity as de set de.tbComparamDetEntity3.cpdId =:statusId,de.depRefundBal =:depRefundBal where de.depId =:depId and de.orgid =:orgid")
    void updateDepBalAmountInTransferDeposit(@Param("depId") Long depId, @Param("statusId") Long statusId,
            @Param("depRefundBal") BigDecimal depRefundBal,
            @Param("orgid") Long orgid);

    @Modifying
    @Query("update AccountDepositEntity as de set de.tbServiceReceiptMas.rmRcptid =:rmRcptid where de.depId =:depId and de.orgid =:orgid")
    void forUpdateReceiptInToDepositEntryTable(@Param("depId") Long depId, @Param("rmRcptid") Long rmRcptid,
            @Param("orgid") Long orgid);

    @Modifying
    @Query("update AccountDepositEntity as de set de.accountBillEntryMaster.id =:billId where de.depId =:depId and de.orgid =:orgid")
    void forUpdateBillIdInToDepositEntryTable(@Param("depId") Long depId, @Param("billId") Long billId,
            @Param("orgid") Long orgid);

    @Modifying
    @Query("update AccountDepositEntity as de set de.depRefundBal =:paymentAmount where de.accountBillEntryMaster.id =:id and de.orgid =:orgid")
    void updateDepDelRefundAmount(@Param("id") Long id,
            @Param("orgid") Long orgid, @Param("paymentAmount") BigDecimal paymentAmount);

    @Query("select dp.depRefundBal from AccountDepositEntity dp where dp.accountBillEntryMaster.id =:id and dp.orgid =:orgid")
    BigDecimal getDepDefundAmountDetails(@Param("id") Long id,
            @Param("orgid") Long orgid);

    @Query("select dp.depRefundBal from AccountDepositEntity dp where dp.depNo =:id and dp.orgid =:orgid")
    BigDecimal getDepRefundAmount(@Param("id") Long id, @Param("orgid") Long orgid);

    @Modifying
    @Query("update AccountDepositEntity as de set de.tbComparamDetEntity3.cpdId =:statusId,de.depRefundBal =:depRefundBal where de.depNo =:depId and de.orgid =:orgid")
    void updateDepBalAmountInTransferDepositAuth(@Param("depId") Long depId, @Param("statusId") Long statusId,
            @Param("depRefundBal") BigDecimal depRefundBal,
            @Param("orgid") Long orgid);

    @Query("select dp.depId from AccountDepositEntity dp where dp.depReferenceNo=:transactionNo and dp.orgid=:orgId and (dp.depEntryTypeFlag='Y' or dp.depEntryTypeFlag='B')")
   List<Long> findAccountDepositEntityByDepReceiptno(@Param("transactionNo") String transactionNo,
            @Param("orgId") long orgId);

    @Query("select dp.depId from AccountDepositEntity dp where dp.accountBillEntryMaster.id=:billId and dp.orgid=:orgId")
    Long findAccountDepositEntityByDepBiilId(@Param("billId") Long billId,
            @Param("orgId") long orgId);

    @Modifying
    @Query("update AccountDepositEntity as de set de.dep_del_flag =:dep_del_flag,de.tbComparamDetEntity3.cpdId =:receiptDeleted where de.depId =:depositId and de.orgid =:orgId")
    void updateDep_del_flagOfAccountDepositEntity(@Param("depositId") Long depositId, @Param("orgId") long orgId,
            @Param("dep_del_flag") String dep_del_flag, @Param("receiptDeleted") Long receiptDeleted);

    @Modifying
    @Query("update AccountDepositEntity as de set de.tbComparamDetEntity3.cpdId =:billDepStausId where de.depId =:depositId and de.orgid =:orgId")
    void updateDep_del_flagOfAccountBillDepositEntity(@Param("depositId") Long depositId, @Param("orgId") long orgId,
            @Param("billDepStausId") Long billDepStausId);

    @Modifying
    @Query("update AccountDepositEntity as de set de.tbComparamDetEntity3.cpdId =:statusId where de.accountBillEntryMaster.id =:depId and de.orgid =:orgid")
    void updateDepositBalanceAmountInDepositTableBybillMasterId(@Param("depId") Long depId, @Param("statusId") Long statusId,
            @Param("orgid") Long orgid);

    @Modifying
    @Query("update AccountDepositEntity as de set de.accountPaymentMaster.paymentId =:paymentId where de.accountBillEntryMaster.id =:depId and de.orgid =:orgid")
    void updateDepositsByPaymentId(@Param("depId") Long depId, @Param("paymentId") Long paymentId,
            @Param("orgid") Long orgid);

    @Modifying
    @Query("update AccountDepositEntity as de set de.accountVoucherEntry.vouId =:voucherId where de.depNo =:refNo and de.orgid =:orgId")
    void updateVoucherIdInDeposit(@Param("refNo") Long refNo, @Param("voucherId") Long voucherId, @Param("orgId") Long orgId);
    
    @Modifying
    @Query("update AccountDepositEntity as de set de.tbComparamDetEntity3.cpdId =:depStatusId where de.depId =:depId and de.orgid =:orgId")
    void updateDepStatusByBillId(@Param("depId") Long billId, @Param("depStatusId") Long depstatusId, @Param("orgId") Long orgId);
    
    @Query("select dp.depAmount from AccountDepositEntity dp where dp.accountBillEntryMaster.id =:id and dp.orgid =:orgid")
    BigDecimal getDepAmountDetails(@Param("id") Long id,
            @Param("orgid") Long orgid);
    
    @Query("select dp.depId from AccountDepositEntity dp where dp.depReferenceNo=:transactionNo and dp.orgid=:orgId and dp.depReceiptdt=:depDate and (dp.depEntryTypeFlag='Y' or dp.depEntryTypeFlag='B')")
    Long findAccountDepositEntityByDepReceiptnoAndDepDate(@Param("transactionNo") String transactionNo,
            @Param("orgId") long orgId, @Param("depDate") Date depDate);
    
    @Query("select dp.tbComparamDetEntity3.cpdId from AccountDepositEntity dp where dp.depReferenceNo=:rcpNo and dp.depReceiptdt=:ReceiptDate and dp.orgid=:orgId")
    Long findbillDepositeStatusInDepositeEntity(@Param("rcpNo") String rcpNo, @Param("ReceiptDate") Date ReceiptDate,@Param("orgId") long orgId);
}
