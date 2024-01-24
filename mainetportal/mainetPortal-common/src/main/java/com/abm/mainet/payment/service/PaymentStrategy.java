package com.abm.mainet.payment.service;

import java.util.Map;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.payment.dto.PaymentRequestDTO;

public interface PaymentStrategy {

    public abstract PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException;

    public abstract Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
            throws FrameworkException;

}
