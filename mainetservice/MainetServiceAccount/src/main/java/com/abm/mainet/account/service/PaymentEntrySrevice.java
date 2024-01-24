package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.RTGSPaymentEntryDTO;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;

public interface PaymentEntrySrevice {

    /**
     * @param paymentEntrydto
     * @return
     * @throws ParseException
     */
    PaymentEntryDto createPaymentEntry(PaymentEntryDto paymentEntrydto) throws ParseException;

    /**
     * @param paymentEntrydto
     * @return
     */
    public AccountPaymentMasterEntity createDirectPaymentEntry(PaymentEntryDto paymentEntrydto) throws ParseException;

    /**
     * @param orgId
     * @param paymentEntryDate
     * @param paymentAmount
     * @param vendorId
     * @param budgetCodeId
     * @param paymentNo
     * @param baAccountid
     * @return
     */
    List<AccountPaymentMasterEntity> getPaymentDetails(Long orgId, String paymentEntryDate, BigDecimal paymentAmount,
            Long vendorId, Long budgetCodeId, String paymentNo, Long baAccountid, Long paymentTypeFlag);

    /**
     * @param id
     * @param orgid
     * @return
     */
    PaymentEntryDto findPaymentEntryDataById(Long id, Long orgid, int langId);

    /**
     * @param paymentId
     * @param orgId
     * @return
     */
    List<Object[]> getDetailsForChequeUtilization(Long paymentId, Long orgId);

    /**
     * @param paymentId
     * @param orgId
     * @return
     */
    PaymentEntryDto getRecordForView(Long paymentId, Long orgId, int langId);

    void reversePaymentEntry(List<String> transactionIds, VoucherReversalDTO dto, long fieldId, long orgId, String ipMacAddress);

    PaymentEntryDto getRecordReportForm(Long paymentId, Long orgId);

    List<Object[]> getDetailsCheque(Long bankAccountId, Long orgId);

    List<TbAcChequebookleafDetEntity> getChequeNumbers(Long chequeId, Long orgId);

    // String getCheque(Long cpdIdstatus, Long chequebookDetid);

    ResponseEntity<?> searchRecordsForPaymentEntry(PaymentEntryDto dto, Long orgId);

    List<Long> getExpenditureDetDetails(Long billId, Long orgId);

    AccountPaymentMasterEntity findById(Long paymentId, Long orgId);

    List<String> getExpenditureDetailHead(Long billId, Long orgId);

    AccountPaymentMasterEntity findByPaymentNumber(String valueOf, long orgid, Date paymentDate);

    String getInstrumentChequeNo(Long cpdIdStatus, Long instrumentNo);

    Date checkClearanceDateExists(Long chequeBookId, Long orgId);

    String getCheque(Long chequebookDetid);

    BigDecimal getDepDefundAmountDetailsCheck(Long id, Long orgId);

    AccountPaymentMasterEntity createTDSPaymentEntry(PaymentEntryDto paymentEntrydto) throws ParseException;

    List<AccountPaymentMasterEntity> getTdsPaymentDetails(Long orgId, String paymentEntryDate, BigDecimal paymentAmount,
            Long vendorId, Long budgetCodeId, String paymentNo, Long baAccountid, Long paymentTypeFlag);

    List<PaymentDetailsDto> getPaymentDetails(Long orgId, Date fromDate, Date toDate, Long paymentTypeFlag);

    void createTdsAckPaymentEntry(PaymentEntryDto paymentEntrydto) throws ParseException;

    PaymentEntryDto findTdsAckPaymentDetailsById(long gridId, Long orgId);

    /**
     * @param id
     * @param orgid & langId
     * @return
     */
    PaymentEntryDto findDirectPaymentEntryDataById(Long id, Long orgid, int langId);

    RTGSPaymentEntryDTO createRTGSPaymentEntryFormData(RTGSPaymentEntryDTO rtgsPaymentEntryDTO) throws ParseException;

    ResponseEntity<?> searchRecordsForRTGSPaymentEntry(RTGSPaymentEntryDTO dto, Long orgid);

    /**
     * @param paymentId
     * @param orgId
     * @return
     */
    RTGSPaymentEntryDTO getRTGSRecordForView(Long paymentId, Long orgId, int langId);

    void updateDepositsByPaymentId(final Long depId, final Long paymentId, final Long orgId);
    
    public List<PaymentEntryDto> getPaymentDetailByBillId(Long billId, Long orgId);

    void updateUploadPaymentDeletedRecords(List<Long> removeFileById, Long updatedBy);
}
