package com.abm.mainet.account.dao;

import java.util.Set;

/**
 * @author tejas.kotekar
 *
 */
public interface ContraVoucherEntryDao {

    Set<Object[]> getContraEntryDetails(Long orgId, String fromDate, String toDate, Long transactionNo,
            Character transactionType);

    Set<Object[]> getAllContraEntryData(Long orgId);

    Object[] getContraEntryDataById(Long transactionId, Long orgId);

}
