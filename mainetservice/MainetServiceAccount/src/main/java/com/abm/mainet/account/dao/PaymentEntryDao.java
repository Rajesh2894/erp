
package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.List;

import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.dto.VoucherReversalDTO;

/**
 * @author tejas.kotekar
 *
 */
public interface PaymentEntryDao {

    /**
     * @param orgId
     * @param paymentEntryDate
     * @param paymentAmount
     * @param vendorId
     * @param budgetCodeId
     * @param paymentNo
     * @param baAccountid
     */
    List<AccountPaymentMasterEntity> getPaymentDetails(Long orgId, String paymentEntryDate, BigDecimal paymentAmount,
            Long vendorId,
            Long budgetCodeId, String paymentNo, Long baAccountid, Long paymentTypeFlag);

    void reversePaymentEntry(VoucherReversalDTO dto, Long transactionId, Long orgId, String ipMacAddress);

    void updateBillPaymentStatus(long paymentId, long orgId);

    List<AccountPaymentMasterEntity> getTdsPaymentDetails(Long orgId, String paymentEntryDate, BigDecimal paymentAmount,
            Long vendorId, Long budgetCodeId, String paymentNo, Long baAccountid, Long paymentTypeFlag);

}
