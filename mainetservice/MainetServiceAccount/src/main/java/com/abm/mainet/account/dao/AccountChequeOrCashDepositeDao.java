
package com.abm.mainet.account.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountBankDepositeSlipLedgerEntity;
import com.abm.mainet.account.domain.AccountBankDepositeSlipMasterEntity;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositeBean;
import com.abm.mainet.account.dto.VoucherReversalDTO;

/**
 * @author prasant.sahu
 *
 */
public interface AccountChequeOrCashDepositeDao {

    /**
     * @param fromDate
     * @param toDate
     * @param feeMode
     * @return
     * @throws ParseException
     */
    List<Object[]> getReceiptDetails(AccountChequeOrCashDepositeBean bean2) throws ParseException;

    List<AccountBankDepositeSlipLedgerEntity> getReceiptLedger(Date fromDate, Date toDate, Long orgid);

    /**
     * @param fromDate
     * @param toDate
     * @param feeMode
     * @param orgId
     * @param slipNumber
     * @param account
     * @param field
     * @return
     * @throws ParseException
     */
    List<AccountBankDepositeSlipMasterEntity> getSavedReceiptDetails(String fromDate, String toDate, String feeMode, Long orgId,
            String account, String slipNumber) throws ParseException;

    /**
     * @param depositeSlipMasterEntity
     * @return
     */
    AccountBankDepositeSlipMasterEntity saveDepositeSlipMasterEntity(
            AccountBankDepositeSlipMasterEntity depositeSlipMasterEntity);

    /**
     * @param depositeSlipId
     * @return
     */
    AccountBankDepositeSlipMasterEntity saveDenominationEntity(Long depositeSlipId);

    /**
     * @param fromDate
     * @param toDate
     * @param depositeType
     * @param functionId 
     * @return
     */
    List<Object[]> getLedgertDetails(Date fromDate, Date toDate, Long depositeType, Long fundId, Long fieldId, Long department,
            Long orgId, Long functionId);

    /**
     * @param fromDate
     * @param toDate
     * @param depositeType
     * @return
     */
    List<Object[]> getDraweeBankDetails(Date fromDate, Date toDate, Long depositeType, Long fundId, Long fieldId);

    /**
     * @param fromDate
     * @param toDate
     * @param rmDate 
     * @param functionId 
     * @param depTypeCheque
     * @param depTypeDemandDraft
     * @param depTypePayOrder
     * @return
     */
	List<Object[]> getChequeOrDDDetails(Date fromDate, Date toDate, Long mode, Long fieldId, Long orgId, Date rmDate,
			Long deptId, Long functionId);

    /**
     * @param bean
     * @return
     */
    List<Object[]> getReceiptDetailsView(AccountChequeOrCashDepositeBean bean);

    /**
     * @param bean
     * @return
     */
    List<Object[]> getLedgertDetailsView(AccountChequeOrCashDepositeBean bean);

    /**
     * @param depositeSlipId
     * @param orgId
     * @return
     */
    List<Object[]> getDraweeBankDetailsView(Long depositeSlipId, Long orgId, Character coTypeFlag, String depositeType);

    List<Object[]> getAllBankDepositSlipEntryData(Long receiptModeId, Long orgId);

    List<Object[]> getAllReceiptEntryData(Long receiptModeId, Long orgId);

    void reverseDepositSlip(VoucherReversalDTO dto, Long transactionId, Long orgId, String ipMacAddress);

    List<Object[]> getReceiptLedgertDetails(Date fromDate, Date toDate, Long depositeType, Long fundId, Long fieldId,
            Long department, Long orgId, Long functionId);

    List<Object[]> getReceiptLedgertDetailsView(AccountChequeOrCashDepositeBean bean);

	

}
