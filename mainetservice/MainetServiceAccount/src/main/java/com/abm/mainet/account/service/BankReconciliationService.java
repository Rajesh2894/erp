package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.BankReconciliationDTO;

public interface BankReconciliationService {

    BankReconciliationDTO saveBankReconciliationFormData(BankReconciliationDTO tbBankReconciliation);

    List<BankReconciliationDTO> findByAllGridReceiptSearchData(String categoryId, Long bankAccount,
            String transactionMode, Date fromDte, Date toDte, Long orgId,Long catagoryStatus);

    List<BankReconciliationDTO> findByAllGridPaymentEntrySearchData(Long categoryId, Long bankAccount,
            String transactionMode, Date fromDte, Date toDte, Long orgId);

    List<BankReconciliationDTO> findByAllGridReceiptAndPaymentEntrySearchData(Long bankAccount, Date fromDte,
            Date toDte, Long orgId);

    List<BankReconciliationDTO> getAllSummaryData(Long bankAccountId, Date fromDte, Date toDte, Long orgId);

    List<BankReconciliationDTO> getAllStatusId(Long orgId);

}
