package com.abm.mainet.mobile.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.abm.mainet.common.integration.payment.dto.BankDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentReceiptDTO;
import com.abm.mainet.mobile.dto.MobilePaymentReqDTO;
import com.abm.mainet.mobile.dto.MobilePaymentRespDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface MobilePaymentService {

    /**
     * @param mobilePaymentReqDTO
     * @param request
     * @return
     */
    MobilePaymentRespDTO saveOnlineTransactionMaster(MobilePaymentReqDTO mobilePaymentReqDTO, HttpServletRequest request);

    /**
     * @param orgId
     * @return
     */
    List<BankDTO> getPaymentGatewayList(long orgId);

    /**
     * @param request
     * @param pgFlag
     * @return
     */
  //  PaymentReceiptDTO saveMobileOnlineTransAfterPaymentResp(HttpServletRequest request, String pgFlag);

}
