package com.abm.mainet.account.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.account.dto.AccountChequeDishonourDTO;

public interface AccountChequeDishonourService {

    Map<Long, String> getBankAccountData(Long orgId);

    Map<Long, String> getChequeDDNoBankAccountData(Long orgId);

    List<AccountChequeDishonourDTO> findByAllGridPayInSlipSearchData(String number, Date date, BigDecimal amount, Long bankAccount,
            Long orgId);

    List<AccountChequeDishonourDTO> findByAllGridChequeDDNoSearchData(Long number, Date date, BigDecimal amount, Long bankAccount,
            Long orgId);

    AccountChequeDishonourDTO saveAccountChequeDishonourFormData(AccountChequeDishonourDTO tbAcChequeDishonourDTO, Long orgId,
            long fieldId, int langId, Long empId, String ipMacAddress) throws IllegalAccessException, InvocationTargetException;

}
