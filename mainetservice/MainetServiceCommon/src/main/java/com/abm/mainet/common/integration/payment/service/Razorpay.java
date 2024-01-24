package com.abm.mainet.common.integration.payment.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
/*import com.razorpay.Order;
import com.razorpay.RazorpayClient;*/
import com.razorpay.RazorpayClient;

/**
 * @author Bhagyashri.Dongardive
 *
 */
public class Razorpay implements PaymentStrategy {
	protected Logger LOG = Logger.getLogger(Razorpay.class);
	final ObjectMapper mapper = new ObjectMapper();

	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {
		LOG.info("Start generatePaymentRequest in Razorpay");
		RazorpayClient razorpay;
		try {
			razorpay = new RazorpayClient(paymentRequestVO.getKey(), paymentRequestVO.getSalt());
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", Double.valueOf(String.valueOf(paymentRequestVO.getValidateAmount()))*100);
			orderRequest.put("currency", paymentRequestVO.getTrnCurrency());
			orderRequest.put("receipt", String.valueOf(paymentRequestVO.getTxnid()));
			Order order = razorpay.Orders.create(orderRequest);
			paymentRequestVO.setOrderId(order.get("id"));
			
			if (StringUtils.isNotBlank(paymentRequestVO.getPayModeorType())  && paymentRequestVO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
				 
			Map<String,String> map= new HashMap<>();
						 
			map.put(MainetConstants.RAZORYPAY_PARAM.KEY,paymentRequestVO.getKey() )	;
			map.put(MainetConstants.RAZORYPAY_PARAM.AMOUNT,String.valueOf(Double.valueOf(String.valueOf(paymentRequestVO.getValidateAmount()))*100));
			map.put(MainetConstants.RAZORYPAY_PARAM.CURRENCY,paymentRequestVO.getTrnCurrency() )	;
			map.put(MainetConstants.RAZORYPAY_PARAM.NAME,paymentRequestVO.getApplicantName())	;
			map.put(MainetConstants.RAZORYPAY_PARAM.DESCRIPTION,MainetConstants.RAZORYPAY_PARAM.DESCRIPTION_VALUE)	;
			map.put(MainetConstants.RAZORYPAY_PARAM.IMAGE,MainetConstants.RAZORYPAY_PARAM.IMAGE_VALUE )	;
			map.put(MainetConstants.RAZORYPAY_PARAM.ORDERID,paymentRequestVO.getOrderId() )	;
			map.put(MainetConstants.RAZORYPAY_PARAM.CALLBACKURL,paymentRequestVO.getSuccessUrl());
			map.put(MainetConstants.RAZORYPAY_PARAM.EMAIL,paymentRequestVO.getEmail() )	;
			map.put(MainetConstants.RAZORYPAY_PARAM.CONTACT,paymentRequestVO.getMobNo() )	;
			map.put(MainetConstants.RAZORYPAY_PARAM.ADDRESS, MainetConstants.RAZORYPAY_PARAM.ADDRESS_VALUE);	
			
			String requestString = Utility.getMapper().writeValueAsString(map);
			paymentRequestVO.setPayRequestMsg(requestString);
			
			 }
			
		} catch (Exception e) {
			LOG.error(" Exception occur in generatePaymentRequest in Razorpay", e);
		}
		return paymentRequestVO;
	}

	@Override
	public Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
			throws FrameworkException {
		LOG.info("Start generatePaymentResponse in Razorpay");
		boolean response = SignatureVerification(responseMap);
		try {
		if(response) {
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.PAYU_STATUS.SUCCESS);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,MainetConstants.PAYU_STATUS.SUCCESS);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,responseMap.get(MainetConstants.RAZORYPAY_PARAM.ORDER_ID));
		responseMap.put(MainetConstants.BankParam.TXN_ID, responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, Utility.dateToString(new Date()));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,MainetConstants.PAYU_STATUS.SUCCESS);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,responseMap.get(MainetConstants.RAZORYPAY_PARAM.PAYMENT_ID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, responseMap.get(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, responseMap.get(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,MainetConstants.PG_SHORTNAME.RAZORPAY);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,MainetConstants.ONLINE);
		responseMap.put(MainetConstants.BankParam.HASH,responseMap.toString());
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,responseMap.get(responseMap.get(MainetConstants.RAZORYPAY_PARAM.ORDER_ID)));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,MainetConstants.REQUIRED_PG_PARAM.NA);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3,MainetConstants.RAZORYPAY_PARAM.UDF3_VALUE);
		
		
		}else{
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.PAYU_STATUS.FAIL);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,"Response Map is null");
			LOG.error("Response Map is null");
			throw new FrameworkException("Response Map is null");	
		}
		}catch (Exception e) {
			LOG.error(" Exception occur in generatePaymentRequestForStatusAPI in Razorpay", e);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
					MainetConstants.PAYU_STATUS.FAIL);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
					"Error While parsing response object");
			responseMap.put(MainetConstants.BankParam.HASH,
					responseMap.get(MainetConstants.EASYPAY_PARAM.Encrypted_req_response));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
					MainetConstants.REQUIRED_PG_PARAM.NA);
			throw new FrameworkException("Exception occur in generatePaymentRequestForStatusAPI in Razorpay Payment");
		}
		return responseMap;
	}

	@Override
	public Map<String, String> generatePaymentRequestForStatusAPI(PaymentRequestDTO paymentRequestVO)
			throws FrameworkException, Exception {
		LOG.info("Start generatePaymentRequestForStatusAPI in Razorpay");
		Map<String, String> responseMap = new HashMap<>();
		return responseMap;
	}
	
	private boolean SignatureVerification(Map<String, String> responseMap) {
		boolean response = false;
		LOG.info("Start SignatureVerification in Razorpay");
		
		String generated_signature=null;
	      try {
        generated_signature =EncryptionAndDecryption.calculateRFC2104HMAC(responseMap.get(MainetConstants.RAZORYPAY_PARAM.ORDER_ID) + "|" + responseMap.get(MainetConstants.RAZORYPAY_PARAM.PAYMENT_ID),responseMap.get(MainetConstants.RAZORYPAY_PARAM.SECRETKEY));
        System.out.println("generated_signature :: " + generated_signature);
        if(generated_signature!=null)
       {
        if (generated_signature.equals(responseMap.get(MainetConstants.RAZORYPAY_PARAM.Signature) )) {
        	
        	LOG.info("Start SignatureVerification success in Razorpay success ");
        	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.PAYU_STATUS.SUCCESS);
          
       response = true;
        }
        else 
        {	
         
        	LOG.info("Insi de  SignatureVerification failed in Razorpay fail" );
        	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.PAYU_STATUS.FAIL);
       response = false;   
        }
		
		 
		
       }
	      }
	      catch(Exception e)
	      {
	        	responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.PAYU_STATUS.FAIL);

	    	  LOG.info("Inside Exception of SignatureVerification in Razorpay"+e.getMessage()); 
	      }
        return response;
}
}

