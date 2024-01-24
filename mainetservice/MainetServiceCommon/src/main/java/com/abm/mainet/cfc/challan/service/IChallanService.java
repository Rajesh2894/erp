package com.abm.mainet.cfc.challan.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dto.ApplicationFormChallanDTO;

/**
 *
 * @author Rahul.Yadav
 *
 */
public interface IChallanService {

    public ChallanMaster getChallanMasters(Long challanNo, Long applNo);

    public ChallanMaster getchallanOfflineType(long applicationId, long orgid);

    public ChallanMaster getChallanMasterById(Long challanId);

    public boolean getChallanMasterTransId(String bankTransId);

    // it has to move to bank master repository
    public abstract List<String> getBankDetailsList(Long bankAccID, long organisation);

    public ApplicationFormChallanDTO getChallanData(CommonChallanDTO challanDTO, Organisation organisation);

    // This interface used for all offline payment - for entering details in bill , reciept and intiating or updating workflow
    // it is call at the time of challan verification
    public TbServiceReceiptMasEntity updateChallanDetails(ChallanMaster challanMaster, Long taskId, Long empType, Long empId,
            String empName);

    // offline challan - at the time of challan generation
    public ChallanMaster InvokeGenerateChallan(CommonChallanDTO requestDTO);

    /**
     * @param bmNo
     * @param orgid
     * @param serviceId
     * @param amount
     * @return
     */
    List<BillReceiptPostingDTO> updateBillMasterData(String primaryKeyId, long orgid,
            Long deptID, Double amount, Long empId, String ipAddress, Date manualReceptDate,String flatNo,String parentNo);

    /**
     * @param applNo
     * @param orgId
     */
    public void updateOnlinePaymentCFCStatus(Long applNo,
            Long orgId);

    /**
     * @param offline
     * @param serviceName
     * @return
     * @throws Exception
     * @throws URISyntaxException
     * @throws DroolException
     * @throws OptimisticLockException
     * @throws ProcessAlreadyStartedException
     * @throws TaskCompleteException
     */

    // pay at counter
    public ChallanReceiptPrintDTO savePayAtUlbCounter(CommonChallanDTO offline, String serviceName);

    public boolean saveAdvancePayment(final Long orgId, final String uniquePrimaryId, final Long receiptId, Long deptId,
            Long userId, Double amount);

    // void initiateAndUpdateWorkFlowProcess(CommonChallanDTO offline, WardZoneBlockDTO dwzDto);

    // void updateWorkFlow(Long taskId, CommonChallanDTO offlineDto, Long empType, Long empId, String empName);

    // online payment
    ChallanReceiptPrintDTO updateDataAfterPayment(CommonChallanDTO offline);

    /**
     * method use to do task after payment gets success
     * 
     * @param CommonChallanDTO
     */

    // this is to be moved to IPostPaymentService interface
    // public void postPaymentSuccess(final CommonChallanDTO CommonChallanDTO);

    /**
     * method use to do task after payment gets fail
     * 
     * @param CommonChallanDTO
     */

    // this is to be moved to IPostPaymentService interface
    // public void postPaymentFailure(final CommonChallanDTO CommonChallanDTO);

    /**
     * @param serviceReceiptMaster
     * @param CommonChallanDTO
     * @return
     */
    ChallanReceiptPrintDTO setValuesAndPrintReport(TbServiceReceiptMasEntity serviceReceiptMaster, CommonChallanDTO offline);

    /*
     * Method used to get extra data required on revenue receipt(for ex. usage type)
     */
    CommonChallanDTO getDataRequiredforRevenueReceipt(CommonChallanDTO offlineMaster, Organisation orgnisation);

    public CommonChallanDTO getBillDetails(CommonChallanDTO commonChallanDTO,String deptCode);
    
    ChallanReceiptPrintDTO setReceiptDtoAndSaveDuplicateService(TbServiceReceiptMasEntity receiptMaster,
			CommonChallanDTO offline);

    public CommonChallanDTO callPushToPayApiForPayment(CommonChallanDTO offline) throws IOException, InterruptedException;
}
