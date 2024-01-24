package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.BankReconciliationDTO;

public interface BankReconciliationDao {

    List<Object[]> findByAllGridReceiptSearchData(Long bankAccount, String transactionMode,
            Date fromDte, Date toDte, Long orgId,Long catagoryStatus);

    List<Object[]> findByAllGridPaymentEntrySearchData(Long bankAccount, Long tranMode,
            Date fromDte, Date toDte, Long orgId,Long catagoryStatus);

    public void saveOrUpdateBankReconciliationFormReceiptData(Long receiptModeId, Date tranDate, String receiptTypeFlag,
            Long orgId, Long userId, Date lmoddate, String lgIpMac);

    List<Object[]> findByAllSummarySearchData(Long bankAccountId, BigDecimal amount, Long statusId, String chequeddno,
            Date fromDte, Date toDte, Long orgId);

    void saveOrUpdateBankDepositslipMasterData(BankReconciliationDTO bankReconciliationDTO);

}
