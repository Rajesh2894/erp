package com.abm.mainet.bill.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;

public interface BillPaymentService {

    /**
     * Update the bill master for successful payment via portal or service
     * @param uniqueId
     * @param amount
     * @param orgid
     * @param ipAddress
     * @return
     */
    List<BillReceiptPostingDTO> updateBillMasterAmountPaid(String uniqueId,
            Double amount, Long orgid, Long userId, String ipAddress, Date manualReceptDate,String flatNo);

    /**
     * Saving and updating advance payment amount
     * @param orgId
     * @param amount
     * @param uniqueId
     * @param userId
     * @param advanceTaxId
     * @return
     */
    boolean saveAdvancePayment(Long orgId, Double amount, String uniqueId,
            Long userId, final Long receiptId);

    String getApplicantUserNameModuleWise(long orgId, String uniqueKey);

    /**
     * reverse the bill by receipt detail and save
     * @param feedetailDto
     * @param orgId
     * @param userId
     * @param ipAddress
     * @return
     */

    VoucherPostDTO reverseBill(TbServiceReceiptMasBean feedetailDto, Long orgId, Long userId, String ipAddress);

    CommonChallanDTO getDataRequiredforRevenueReceipt(CommonChallanDTO offlineMaster, Organisation orgnisation);
    
   default public List<BillReceiptPostingDTO> updateBillMasterAmountPaidForGroupProperty(final String propertyNo, Double amount,
            final Long orgId, final Long userId, String ipAddress, Date manualReceptDate,String flatNo,String parentNo){
				return null;
	   
   }
   
   default public TbBillMas getLatestYearBill(String propNoOrConnNo, Long orgId){
				return null;
  }
   
  default boolean isChequeClearDishonour(TbServiceReceiptMasBean receiptMasBean, Long orgId) {
	  return true;
  }
}
