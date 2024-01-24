package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.abm.mainet.account.dto.ReceiptReversalViewDTO;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.rest.dto.VoucherReversalExtDTO;
import com.abm.mainet.common.utility.LookUp;

/**
 *
 * @author Vivek.Kumar
 * @since 19 May 2017
 */
public interface AccountVoucherReversalService {

    ResponseEntity<?> findRecordsForReversal(VoucherReversalDTO dto, long orgId, long deptId);

    List<LookUp> findApprovalAuthority(long deptId, long orgId);

    ReceiptReversalViewDTO veiwData(long primaryKey, long orgId);

    void saveOrUpdateVoucherReversal(List<String> transactionIds, VoucherReversalDTO dto, long fieldId, long orgId, int langId,
            String ipMacAddress);

    ResponseEntity<?> validateDataForReversal(List<VoucherReversalDTO> oldData, VoucherReversalDTO requestData, long orgId);

    boolean countDepositSlipAlreadyGenerated(long receiptId, long orgId);
    
    boolean checkBillReversalApplicable(VoucherReversalDTO voucherReversalDto, String transactionType, Long orgId);

	List<String> validateExternalReversalRequest(VoucherReversalExtDTO dto);
	
	VoucherReversalDTO convertExtDtoToInternalDto(VoucherReversalExtDTO extDto);

	ResponseEntity<?> saveReverseTransaction(VoucherReversalDTO dto);
	
	Long getDepositeStatusAgainstBillType(String receiptRefNo, Date ReceiptDate, long orgId);
}
