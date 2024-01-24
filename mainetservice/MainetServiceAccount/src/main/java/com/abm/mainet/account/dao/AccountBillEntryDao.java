package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.dto.VoucherReversalDTO;

/**
 * @author tejas.kotekar
 *
 */
public interface AccountBillEntryDao {

    List<AccountBillEntryMasterEnitity> getBillEntryDetails(Long orgId, String fromDate, String toDate, String billNo,
            Long billType, Long vendorId, Long departmentId);

    void reverseBillInvoice(VoucherReversalDTO dto, Long transactionId, Long orgId, String ipMacAddress);

    boolean isPaymentDateisExists(Long orgid, Date paymentDate);
}
