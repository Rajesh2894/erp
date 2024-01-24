package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountChequeDishonourDao {

    List<Object[]> findByAllGridPayInSlipSearchData(String number, Date date, BigDecimal amount,
            Long bankAccount, Long orgId);

    List<Object[]> findByAllGridChequeDDNoSearchData(Long number, Date date, BigDecimal amount,
            Long bankAccount, Long orgId);

    public void saveAccountChequeDishonourFormData(Long receiptModeId, Date chequeDishonourDate, Double chequeDisChgAmt,
            String remarks, String flag, Long orgId);

    Long getTaxMasterEntryBudgetCodeId(Long departmentId, Long voucherSubTypeId, Long orgId);

}
