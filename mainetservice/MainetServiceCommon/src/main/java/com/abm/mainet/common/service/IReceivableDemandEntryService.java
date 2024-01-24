package com.abm.mainet.common.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.ReceivableDemandEntryDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;

/**
 * @author satish.kadu
 *
 */
@WebService
public interface IReceivableDemandEntryService {

    /**
     * @param receivableDemandEntryDto
     * @return
     */
    ReceivableDemandEntryDTO saveReceivableDemandEntry(ReceivableDemandEntryDTO receivableDemandEntryDto);

    /**
     * @param receivableDemandEntryDto
     * @return
     */
    ReceivableDemandEntryDTO updateReceivableDemandEntry(ReceivableDemandEntryDTO receivableDemandEntryDto);

    /**
     * @param billId
     * @return
     */
    ReceivableDemandEntryDTO getById(Long billId);

    /**
     * @param recvblDmndDto
     * @return
     */
    RequestDTO getCustomerDetailsByRefNo(ReceivableDemandEntryDTO recvblDmndDto);

    /**
     * @param recvblDmndDto
     * @return
     */
    RequestDTO getApplicationDetailsByApplicatioNoOrRefNo(ReceivableDemandEntryDTO recvblDmndDto);

    /**
     * @param recvblDmndDto
     * @return
     */
    ReceivableDemandEntryDTO getByRefNoOrAppNo(ReceivableDemandEntryDTO recvblDmndDto);

    /**
     * @param billNo
     * @param orgId
     * @return
     */
    List<ReceivableDemandEntryDTO> getBillInfoListByBillNoOrRefNo(String billNo, String refNumber, int dueDates, Long orgId); 

    /**
     * @param receivableDemandEntryDto
     * @param offline
     * @return
     */
    ChallanReceiptPrintDTO updateBillAfterPayment(List<ReceivableDemandEntryDTO> receivableDemandEntryDtoList, CommonChallanDTO offline);
    
    /**
     * @param refNumber
     * @param billNo
     * @param orgId
     * @return
     */
    List<ReceivableDemandEntryDTO> searchSupplementaryBillInfo(String refNumber, String billNo, Long orgId, String wardCode , Long locID);
    
    /**
     * @param billNo
     * @param orgId
     * @return
     */
    ReceivableDemandEntryDTO getBillInfoByBillNo(String billNo,Long orgId); 
    
    String printSupplementryBill(Long receiptId ,String type);
    
    Object[] getSupplimentryBillDetailsByBillandReceiptId(Long billId,Long ReceiptId,Long orgId);
    
    /**
     * @param receiptMaster
     * @param orgId
     * @param userId
     * @param ipAddress
     * @info used for update bill for receipt reversal
     */
    VoucherPostDTO getAccountPostingDtoForSupplimentaryBillReversal(TbServiceReceiptMasBean receiptMaster,final Long orgId,final Long userId,final String ipAddress);   

    /**
     * @param billIds
     * @param refundFlag
     * @param AdjustmentFlag
     * @info Updating the Refund and adjustment flag after Decision
     */
    void updateDepositRefundAdjustmentFlag(List<Long> billIds,final String refundFlag,final String AdjustmentFlag);

}
