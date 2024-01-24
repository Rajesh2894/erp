
package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Organisation;

/**
 * @author tejas.kotekar
 *
 */
public interface TransactionTrackingDao {

    /**
     * @param budgetCodeId
     * @param orgId
     * @return
     */
    List<Object[]> getTransactionDetails(Long budgetCodeId, Long orgId);

    /**
     * @param budgetCodeId
     * @param orgId
     * @return
     */
    List<Object[]> getMonthWiseTransactionDetails(Long budgetCodeId, Long orgId, Date fromDate, Date toDate, Long finYearId,
            Organisation orgnisation);

}
