package com.abm.mainet.common.integration.payment.service;

import java.util.HashMap;
import java.util.Map;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;

@FunctionalInterface
public interface PaymentStrategy {

    public abstract PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException;

    public default Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
            throws FrameworkException{
    	return responseMap;
    }
    
    public default Map<String, String> generatePaymentRequestForStatusAPI(PaymentRequestDTO paymentRequestVO) throws FrameworkException,Exception{
    	 Map<String, String> responseMap=new HashMap<>();
    	 return responseMap;
    }

}
