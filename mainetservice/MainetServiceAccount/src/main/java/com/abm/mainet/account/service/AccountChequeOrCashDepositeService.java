package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.abm.mainet.account.domain.AccountBankDepositeSlipMasterEntity;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositSlipDTO;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositeBean;
import com.abm.mainet.account.dto.AccountLedgerMasBean;
import com.abm.mainet.account.dto.DepositSlipReversalViewDTO;
import com.abm.mainet.account.dto.DraweeBankDetailsBean;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;

/**
 * Business Service Interface for entity TbAcFieldMaster.
 */
public interface AccountChequeOrCashDepositeService {

    /**
     * @param date
     * @param date2
     * @param feeMode
     * @return
     * @throws ParseException
     */

    List<TbServiceReceiptMasBean> getReceiptDetails(AccountChequeOrCashDepositeBean bean, Organisation org) throws ParseException;

    /**
     * @param feeMode
     * @param toDate
     * @param fromDate
     * @param l
     * @param slipNumber
     * @param account
     * @param field
     * @return
     * @throws ParseException
     */
    List<AccountChequeOrCashDepositeBean> getSavedReceiptDetails(String fromDate, String toDate, String feeMode, Long orgId,
            String account, String slipNumber) throws ParseException;

    /**
     * @param bean
     * @param empId
     * @param langId
     * @param orgId
     */
    AccountChequeOrCashDepositeBean saveRecords(AccountChequeOrCashDepositeBean bean, Long orgId, int langId, Long empId,
            String ipMacAddress)
            throws Exception;

    /**
     * @param depositeSlipId
     * @return
     */
    AccountChequeOrCashDepositeBean getSlipDetailsUsingDepSlipId(Long depositeSlipId);

    /**
     * @param frmDate
     * @param tDate
     * @param depositeType
     * @return
     */
    List<AccountLedgerMasBean> LedgerDetails(Date frmDate, Date tDate, Long depositeType, Long fundId, Long fieldId,
            Long department, Long orgId, String depSlipType,Long functionId);

    /**
     * @param fromDate
     * @param toDate
     * @param depositeType
     * @return
     */
    List<DraweeBankDetailsBean> getDraweeBankDetails(Date fromDate, Date toDate, Long depositeType, Long fundId, Long fieldId);

    /**
     * @param fromDate
     * @param toDate
     * @param rmDate 
     * @param long1 
     * @param depTypeCheque
     * @param depTypeDemandDraft
     * @param depTypePayOrder
     * @param bankIdString
     * @return
     */
	List<TbServiceReceiptMasBean> getChequeOrDDDetails(Date fromDate, Date toDate, Long mode, Long fieldId, Long orgId,
			Date rmDate, Long deptId, Long long1);

    /**
     * @param master
     * @param bean
     */
    void updateReceiptDetails(AccountBankDepositeSlipMasterEntity master,
            AccountChequeOrCashDepositeBean bean);

    /**
     * @param bean
     * @return
     */
    List<TbServiceReceiptMasBean> getReceiptDetailsView(
            AccountChequeOrCashDepositeBean bean, Organisation org);

    /**
     * @param bean
     * @return
     */
    List<AccountLedgerMasBean> LedgerDetailsView(
            AccountChequeOrCashDepositeBean bean);

    /**
     * @param depositSlipId
     * @param orgId
     * @param character
     * @return
     */
    List<TbServiceReceiptMasBean> getDraweeBankDetailsView(Long depositSlipId,
            Long orgId, Character coTypeFlag, String depositeType);

    boolean checkTemplateType(VoucherPostDTO postDTO, Long templateId, Long voucherTyepId);

    DepositSlipReversalViewDTO viewData(String transactionType, long primaryKey, long orgId);

    void reverseDepositSlip(List<String[]> transactionIds, VoucherReversalDTO dto, long fieldId, long orgId, String ipMacAddress);

    ResponseEntity<?> validateDataForReversal(List<VoucherReversalDTO> oldData, VoucherReversalDTO requestData, long orgId);

    ResponseEntity<?> validateDataForDepositSlipReversal(List<VoucherReversalDTO> oldData, VoucherReversalDTO requestData,
            long orgId);

    List<AccountChequeOrCashDepositSlipDTO> getBankDetails(Long depositeSlipId, Long orgid);

    List<AccountChequeOrCashDepositSlipDTO> getDenominationCashDetails(Long depositeSlipId, Long orgid);

    List<AccountChequeOrCashDepositSlipDTO> getBankAccountDetails(Long depositeSlipId, Long orgid);

    BigDecimal getCheckDepositSlipAmountExists(Date receiptDate, long orgid);



}
