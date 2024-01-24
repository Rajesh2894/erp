
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.TransactionTrackingDto;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author tejas.kotekar
 *
 */
public interface TransactionTrackingService {

    List<Object[]> getTransactedAccountHeads(Long orgId);

    /**
     * @param budgetCodeId
     * @param orgId
     * @return
     */
    TransactionTrackingDto getTransactionDetails(Long budgetCodeId, Long orgId);

    /**
     * @param budgetCodeId
     * @param orgId
     * @param finYearId
     * @return
     * @throws ParseException
     */
    TransactionTrackingDto getMonthWiseDetails(Long budgetCodeId, Long orgId, Long finYearId, Organisation organisation)
            throws ParseException;

    TransactionTrackingDto getTransactionTrackingTrialBalance(Long orgId, Date fromDate, Date toDate, Long faYearid);

    TransactionTrackingDto getTransactionTrackingHeadWise(Long orgId, Date fromDate, Date toDate, Long faYearid,
            String sacHeadId);

    TransactionTrackingDto findHeadWiseBalance(Long orgId, String accountHead, Long faYearid, BigDecimal openingDr,
            BigDecimal openingCr);

    TransactionTrackingDto findDayWiseBalance(Long orgId, String accountHead, Long faYearid, String fromDate, String toDate);

    TransactionTrackingDto findVoucherWiseTransactions(Long orgId, String accountHead, String fromDate);

}
