package com.abm.mainet.payment.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.ProperySearchDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.payment.domain.PaymentTransactionMas;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.dto.PaymentTransactionMasDTO;
import com.abm.mainet.payment.dto.ProvisionalCertificateDTO;

public interface IPaymentService {

    void proceesPaymentTransactionOnApplicationSubmit(HttpServletRequest httpServletRequest, PaymentRequestDTO payURequestDTO)
            throws FrameworkException;

    void proceesPaymentTransactionBeforePayment(HttpServletRequest httpServletRequest, PaymentRequestDTO payURequestDTO)
            throws FrameworkException;

    Map<String, String> genrateResponse( Map<String, String> responseMap, String gatewayFlag, String sessionAmount,
            Long pgId, Long orgId, int LangId);

    void proceesPaymentTransactionAfterPayment(HttpServletRequest httpServletRequest, Map<String, String> responseMap)
            throws FrameworkException;

    Map<Long, String> getAllPgBank(long orgId) throws FrameworkException;

    PaymentTransactionMas getOnlineTransactionMasByTrackId(Long trackId);

    void updateReiceptData(CommonChallanDTO offlineDTO);

    void sendSMSandEMail(Map<String, String> responseMap);

    void canceTransactionBeforePayment(HttpServletRequest httpServletRequest, PaymentRequestDTO payURequestDTO)
            throws FrameworkException;
    
    List<PaymentTransactionMas> getPaymentMasterListById(Long tranCmId);
    
    PaymentTransactionMasDTO getStatusByReferenceNo(String referenceNo);
    
    ChallanReceiptPrintDTO getDuplicateReceipt(ProperySearchDto searchDto);

	ProvisionalCertificateDTO getProvisinalCertData(Long applicationId, Long orgId);
}
