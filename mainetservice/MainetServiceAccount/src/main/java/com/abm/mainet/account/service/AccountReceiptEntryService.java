
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptDTO;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptExternalDto;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;

/**
 * @author dharmendra.chouhan
 *
 */
public interface AccountReceiptEntryService {

    TbServiceReceiptMasBean create(TbServiceReceiptMasBean entity, long fieldId) throws Exception;

    TbServiceReceiptMasBean findById(Long rmRcptid, Long orgId);

    List<TbServiceReceiptMasBean> findAll(Long orgId, BigDecimal rmAmount, Long rmRcptno, String rmReceivedfrom, Date rmDate);

    TbServiceReceiptMasBean findReceiptData(Long orgId,Long rmRcptno, Date rmDate,Long deptId);
    
    List<BankAccountMasterEntity> findBankacListReceipt(long orgId);

    void findByIdEdit(Long rmRcptid, Long orgId, String receiptDelRemark, Date receiptDelDate, String lgIpMacUpd, Long updatedBy);

    TbServiceReceiptMasBean deasEntryPosting(TbServiceReceiptMasBean tbServiceReceiptMasBean);

    List<String> getPayeeNames(Long orgId);

    Long getBudgetCodeIdForReceitMode(Long orgId, Organisation org, Long cpdFeemode);

    /**
     * being used for Account Receipt Rest Entry from other module where Account Module is available
     * @param dto : pass {@code AccountRecieptEntryRestDTO} by setting parameters
     * @throws NullPointerException : if Posting does not meet to a existing defined standard template for that posting parameters
     * @throws IllegalArgumentException : if mandatory parameters are not set properly
     */
    String validateReceiptInput(String receiptAmount, String receiptNumber, String receiptPayeeName, String receiptDate);

    List<AccountReceiptDTO> findAllReceiptRestData(Long orgId, String receiptAmount, String receiptNumber,
            String receiptPayeeName, String receiptDate) throws ParseException;

    AccountReceiptDTO findByRestId(Long rmRcptid, Long orgId);

    Long getFinanciaYearIds(Date receiptEntryDate);

    /**
     * being used for saveReceipt from other module where Account Module is available
     * @param dto : pass {@code AccountRecieptDTO} by setting parameters
     * @throws ParseException
     * @throws Exception
     * @throws NullPointerException : if Posting does not meet to a existing defined standard template for that posting parameters
     * @throws IllegalArgumentException : if mandatory parameters are not set properly
     */
    TbServiceReceiptMasEntity saveReceipt(AccountReceiptDTO dto, long fieldId) throws ParseException, Exception;

    List<VoucherReversalDTO> findRecordsForReversal(String transactionType, String transactionNo, Date transactionDate,
            String amount, String transactionTypeId);

    Long getBudgetCodeIdForPrimaryReceitMode(Long orgId, Organisation org, Long cpdFeemode);

    String validateInput(AccountReceiptDTO accountReceiptDTO);

    List<Object[]> findAllDataByReceiptId(Long rmRcptid, Long orgId);
    
    List<Object[]> findDataOfReceipt(String receiptTypeFlag,Long grantId, Long orgId);
    
    
    BigDecimal getReceiptsAmount(Long refId, Long orgId,String receiptTypeFlag);
    
    public TbServiceReceiptMasEntity saveExtReceipt(final AccountReceiptDTO accountReceiptDTO, long fieldId,String reciptCaragory)throws ParseException, Exception;;
    
    List<String> validateExternalSystemDTOInput(AccountReceiptExternalDto receiptEternalDto);
    
    AccountReceiptDTO convertExternalReceiptDtoToInternalReceiptDto(AccountReceiptExternalDto externalDto);
    
}
