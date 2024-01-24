
package com.abm.mainet.account.repository;

import java.util.List;

import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.RTGSPaymentEntryDTO;

/**
 * @author Vivek.Kumar
 * @since 05-Sep-2017
 */
public interface AccountPaymentEntryRepositoryCustom {

    List<Object[]> queryRecordsForPaymentEntry(PaymentEntryDto dto, Long orgId);

    List<Object[]> queryRecordsForRTGSPaymentEntry(RTGSPaymentEntryDTO dto, Long orgId);

}
