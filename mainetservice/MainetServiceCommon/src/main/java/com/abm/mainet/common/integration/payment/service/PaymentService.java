/**
 *
 */
package com.abm.mainet.common.integration.payment.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.PaymentDetailResDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.PaymentDetailSearchDTO;
import com.abm.mainet.common.integration.dto.PaymentDetailsResponseDTO;
import com.abm.mainet.common.integration.dto.PaymentPostingDto;
import com.abm.mainet.common.integration.dto.ResponseMessageDto;
import com.abm.mainet.common.integration.payment.dto.PaymentReceiptDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentTransactionMasDTO;
import com.abm.mainet.common.integration.payment.entity.EgrassPaymentENtity;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;

public interface PaymentService {

    void proceesTransactionOnApplication(HttpServletRequest httpServletRequest, PaymentRequestDTO payURequestDTO)
            throws FrameworkException;

    void proceesTransactionBeforePayment(HttpServletRequest httpServletRequest, PaymentRequestDTO payURequestDTO)
            throws FrameworkException;

    Map<String, String> genrateResponse(Map<String, String> responseMap, String gatewayFlag, String sessionAmount, Long orgId, Integer langId);
    
    PaymentTransactionMas proceesTransactionAfterPayment(Map<String, String> responseMap);

    Map<Long, String> getAllPgBank(long orgId) throws FrameworkException;
    
    PaymentTransactionMas getOnlineTransactionMasByTrackId(Long trackId);
    
   // void updateReiceptData(CommonChallanDTO offlineDTO);
    
    void cancelTransactionBeforePayment(HttpServletRequest httpServletRequest, PaymentRequestDTO payURequestDTO)
            throws FrameworkException;
    
    List<PaymentTransactionMas> getPaymentMasterListById(Long tranCmId);
    
    public ResponseMessageDto paymentPostingDataUpdate(HttpServletRequest request, PaymentPostingDto paymentPostingDto, String pgName) throws Exception;
    
    public Map<String, String> getPaymentPostingStatus(String requestTransactionNo);
    
    public long getCountByTransactionId(String tranCmId, String orgCode);
    
    public String getEncryptDecryptKey(String bankShortCode,String orgCode);

    PaymentTransactionMasDTO getStatusByReferenceNo(String referenceId);

	public Map<String, String> processTransactionForCallStatusAPI(HttpServletRequest request, PaymentRequestDTO paymentRequestDTO);
    
	public PaymentDetailsResponseDTO fetchPaymentDetails(PaymentDetailSearchDTO paySearchDeatilDto, Long orgId);
	
	public  Long getCountByApplNoOrLegacyNo(String applNo, String legacyApplNo, Long orgId, String deptCode);
	
	public Map<String,Object> checkAuthorization(HttpServletRequest request);
	
	public Organisation getOgrnByOrgCode(String orgCode);
	
	public ResponseMessageDto checkValidationForPaymentPostingV2 (PaymentPostingDto payDto, ResponseMessageDto messageDto);
	
	public ResponseEntity<?> getDetails(PaymentDetailSearchDTO paySearchDeatilDto);
	
	public ResponseMessageDto checkAllValidation (PaymentPostingDto payDto,ResponseMessageDto messageDto);
	
	public ResponseMessageDto getPaymentPostingStatusV2(String requestTransactionNo);

	PaymentDetailResDto checkPaymentStatus(String string);

	String generateNicGateWayRequestUrl(PaymentRequestDTO paymentReqDto, CommonChallanDTO commonChallanDTO, HttpServletRequest request);

	EgrassPaymentENtity getEgrassChallanData(String refNo, Long orgId);

	Object[] getLicNoAndApplicationId(String refNo, Long orgId);
	
	Map<Long, String> getAllPgBankByDeptCode(long orgId, String deptCode) throws FrameworkException;

}
