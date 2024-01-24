package com.abm.mainet.payment.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EasyPay implements PaymentStrategy {
	protected Logger LOG = Logger.getLogger(EasyPay.class);
	final ObjectMapper mapper = new ObjectMapper();
	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {
		LOG.info("Start the genreatePaymentRequest() in EasyPay class of portal");
		try {
		String CKS_value = paymentRequestVO.getMerchantId() + paymentRequestVO.getTxnid() + paymentRequestVO.getApplicationId() 
							+ paymentRequestVO.getValidateAmount() + paymentRequestVO.getSalt();
		CKS_value = EncryptionAndDecryption.checkSum(CKS_value);
		LOG.info("Checksum :- "+ CKS_value);
		String ppi= paymentRequestVO.getApplicationId()+"|"+paymentRequestVO.getApplicantName()+"|"
				 	+"address"+"|"+"location"+"|"+"pincode"+"|"
		            +paymentRequestVO.getEmail()
		            +"|"+paymentRequestVO.getMobNo()+"|"+paymentRequestVO.getValidateAmount();
		
		 String reqString = MainetConstants.EASYPAY_PARAM.CID+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getMerchantId() 
					+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.RID+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
					+ paymentRequestVO.getTxnid()
					+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.CRN+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getApplicationId() 
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.AMT+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getValidateAmount() 
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.VER+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getAddField1()
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.TYP+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getRequestType() 
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.CNY+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getTrnCurrency()
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.RTU+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getResponseUrl()
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.PPI+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ ppi
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.RE1+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getAddField2()
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.RE2+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getUdf6()
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.RE3+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getUdf7() 
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.RE4+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getUdf9()
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.RE5+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getUdf10()
        			+ MainetConstants.operator.AMPERSAND+MainetConstants.EASYPAY_PARAM.CKS+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ CKS_value;
		 LOG.info("In genreatePaymentRequest() in EasyPay class Request portal side before encrypt:- "+reqString);
		 String encryptedReq= EncryptionAndDecryption.encrypt(reqString,paymentRequestVO.getKey());
		 LOG.info("In genreatePaymentRequest() in EasyPay class Request portal side after encrypt:- "+encryptedReq);     
		 paymentRequestVO.setPayRequestMsg(encryptedReq);
		      paymentRequestVO.setHash(encryptedReq);
		}catch (final Exception exception) {
			LOG.error(" Exception occur in genreatePaymentRequest in Easypay Payment of portal", exception);
			throw new FrameworkException("Exception occur in genreatePaymentRequest in Easypay Payment ...",
					exception);
		}
		LOG.info("End the genreatePaymentRequest() in EasyPay class of portal");
		return paymentRequestVO;
	}
	@Override
	public Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
			throws FrameworkException {
		LOG.info("start generatePaymentResponse in EasyPay class");
		try {
		String decryptedResponse=EncryptionAndDecryption.decrypt(responseMap.get(MainetConstants.EASYPAY_PARAM.Encrypted_req_response), 
				MainetConstants.EASYPAY_PARAM.EASYPAY_ENCRYPT_DECRYPT_KEY);
		LOG.info("decrypted response:-"+decryptedResponse);
		String[] splitedresponse = decryptedResponse.split(MainetConstants.operator.AMPERSAND);
		
		Map<String,String> map= new HashMap<>();
		for(String value:splitedresponse) {
			String[] Pairresponse=value.split(MainetConstants.EASYPAY_PARAM.EQUALS_SYMB);
			map.put(Pairresponse[0].trim(),Pairresponse[1].trim());
		}
		LOG.info("response in key-value pair:-"+map);
		if(!map.isEmpty()) {
			
			if (map.get(MainetConstants.EASYPAY_PARAM.STC).equals(MainetConstants.EASYPAY_PARAM.SUCCESS_CODE)) {
				LOG.info(" Payment Status is Success ");
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
						MainetConstants.PAYU_STATUS.SUCCESS);
			}else if (map.get(MainetConstants.EASYPAY_PARAM.STC).equals(MainetConstants.EASYPAY_PARAM.FAILURE_CODE)) {
					LOG.info(" Payment Status is Failure ");
				 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
							MainetConstants.PAYU_STATUS.FAIL);
				}else {
					LOG.info(" Payment Status is in Process/Pending ");
					 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
							 MainetConstants.EASYPAY_PARAM.IN_PROCESS_OR_PENDING);
				}
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,map.get(MainetConstants.EASYPAY_PARAM.STC)+":"+map.get(MainetConstants.EASYPAY_PARAM.RMK));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,map.get(MainetConstants.EASYPAY_PARAM.RID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,map.get(MainetConstants.EASYPAY_PARAM.TRN));
		responseMap.put(MainetConstants.BankParam.TXN_ID, map.get(MainetConstants.EASYPAY_PARAM.RID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, map.get(MainetConstants.EASYPAY_PARAM.TET));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,
				map.get(MainetConstants.EASYPAY_PARAM.RMK));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,
				map.get(MainetConstants.EASYPAY_PARAM.BRN));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, map.get(MainetConstants.EASYPAY_PARAM.AMT));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, map.get(MainetConstants.EASYPAY_PARAM.AMT));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,
				MainetConstants.PG_SHORTNAME.EASYPAY);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,map.get(MainetConstants.EASYPAY_PARAM.PMD));
		responseMap.put(MainetConstants.BankParam.HASH,
				decryptedResponse);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
				responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,
				map.get(MainetConstants.EASYPAY_PARAM.RID));
		 }else {
			 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
						MainetConstants.PAYU_STATUS.FAIL);
			 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
						"Response Object is null");
				LOG.error("Response Object is null");
				throw new FrameworkException(
						"Response Object is null");
			}
		} catch (Exception exp) {
			LOG.error(" Exception occur in generatePaymentResponse in Easypay Payment", exp);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
					MainetConstants.PAYU_STATUS.FAIL);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
					"Error While parsing response object");
			exp.printStackTrace();
			responseMap.put(MainetConstants.BankParam.HASH,
					responseMap.get(MainetConstants.EASYPAY_PARAM.Encrypted_req_response));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
					MainetConstants.REQUIRED_PG_PARAM.NA);
		}	
		LOG.info("End generatePaymentResponse in EasyPay class");
		return responseMap;
	}
	

}
