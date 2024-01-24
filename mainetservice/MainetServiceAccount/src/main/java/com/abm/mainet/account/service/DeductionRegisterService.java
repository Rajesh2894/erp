package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;

public interface DeductionRegisterService {

    PaymentEntryDto getDeductionRegisterData(String frmDate, String toDate, Long orgId, Long tdsTypeId);

    List<PaymentDetailsDto> getActiveDetails(long orgId, Date fromDate, Date toDate, Long tdsTypeId);
}
