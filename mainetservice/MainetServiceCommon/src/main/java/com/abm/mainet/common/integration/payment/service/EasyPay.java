package com.abm.mainet.common.integration.payment.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author Bhagyashri.Dongardive
 *
 */
public class EasyPay implements PaymentStrategy {
	protected Logger LOG = Logger.getLogger(EasyPay.class);
	final ObjectMapper mapper = new ObjectMapper();
	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {
		LOG.info("Start the genreatePaymentRequest() in EasyPay class ");
		try {
		String CKS_value = paymentRequestVO.getMerchantId() + paymentRequestVO.getTxnid() + paymentRequestVO.getApplicationId() 
							+ paymentRequestVO.getValidateAmount() + paymentRequestVO.getSalt();
		LOG.info("Merchant Id----------->"+paymentRequestVO.getMerchantId());
		LOG.info("Txn Id----------------->"+paymentRequestVO.getTxnid());
		LOG.info("Application------------>"+paymentRequestVO.getApplicationId());
		LOG.info("Salt------------------>"+paymentRequestVO.getSalt());
		LOG.info("Checksum------------------>"+CKS_value);
		LOG.info("URL------------------------>"+paymentRequestVO.getPgUrl());
		CKS_value = EncryptionAndDecryption.checkSum(CKS_value);
		
		LOG.info("Checksum :- "+ CKS_value);
		if(StringUtils.isEmpty(paymentRequestVO.getUdf8())) {
			
			paymentRequestVO.setUdf8(MainetConstants.EASYPAY_PARAM.DOUBLE_DASH);
		}
		if(StringUtils.isEmpty(paymentRequestVO.getApplicantName())) {
			paymentRequestVO.setApplicantName(MainetConstants.EASYPAY_PARAM.DOUBLE_DASH);
		}
		if(StringUtils.isEmpty(paymentRequestVO.getEmail())) {
			paymentRequestVO.setEmail(MainetConstants.EASYPAY_PARAM.DOUBLE_DASH);
		}
		String address = MainetConstants.EASYPAY_PARAM.DOUBLE_DASH;
		if(paymentRequestVO.getRecieptDTO() != null) {
			if(StringUtils.isNotEmpty(paymentRequestVO.getRecieptDTO().getApplicantAddress())) {
				address= paymentRequestVO.getRecieptDTO().getApplicantAddress();
			}
		}
		
		String ppi= paymentRequestVO.getUdf8()+MainetConstants.operator.ORR+paymentRequestVO.getEmail()
		            +MainetConstants.operator.ORR+paymentRequestVO.getMobNo()+MainetConstants.operator.ORR
		            +paymentRequestVO.getValidateAmount();
		//PTIN No|Owner email|Owner mobile number|Amount
		
		LOG.info("applicantName before encoded:- "+paymentRequestVO.getApplicantName());
		String ref4 = EncryptionAndDecryption.encode(paymentRequestVO.getApplicantName());
		LOG.info("encoded ref4:- "+ref4);
		LOG.info("address before encoded:- "+address);
		String ref5 = EncryptionAndDecryption.encode(address);
		LOG.info("encoded ref5:- "+ref5);
		
		 String reqString = MainetConstants.EASYPAY_PARAM.CID+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getMerchantId() 
					+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.RID+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
					+ paymentRequestVO.getTxnid()
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.CRN+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getApplicationId()
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.AMT+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getValidateAmount() 
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.VER+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getAddField1()
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.TYP+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getRequestType() 
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.CNY+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getTrnCurrency()
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.RTU+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getSuccessUrl()
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.PPI+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ ppi
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.RE1+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getAddField2()
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.RE2+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getUdf6()
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.RE3+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ paymentRequestVO.getUdf7() 
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.RE4+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ ref4
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.RE5+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ ref5
        			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.CKS+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
        			+ CKS_value;
		 LOG.info("In genreatePaymentRequest() in EasyPay class RequestBody before encrypt:- "+reqString);
		 String encryptedReq= EncryptionAndDecryption.encrypt(reqString,paymentRequestVO.getKey());
		 LOG.info("In genreatePaymentRequest() in EasyPay class RequestBody after encrypt:- "+encryptedReq);     
		if(StringUtils.isNotBlank(paymentRequestVO.getPayModeorType())) {
		 if (paymentRequestVO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
			 String token = null;
			 String tokenrespone = null;
			 try {
			 LOG.info("In genreatePaymentRequest() in EasyPay class token URL :- "+paymentRequestVO.getAddField6());
			 if(StringUtils.isNotBlank(paymentRequestVO.getAddField6()))
			 if(paymentRequestVO.getAddField6()!=null) {
				  ResponseEntity<?> Response = RestClient.callRestTemplateClient(null, paymentRequestVO.getAddField6(), HttpMethod.GET,String.class);
				   if(Response!=null  && Response.getStatusCode()==HttpStatus.OK && Response.getBody()!=null) {
					   tokenrespone=(String)Response.getBody();
				    }else {
				    	LOG.info("Token Not found");
				    	token = MainetConstants.EASYPAY_PARAM.EASYPAY_DEFAULT_TOKEN;
				    } 
			  }else {
				  LOG.info("URL is incorrect ");
			  }
		
			 LOG.info("In genreatePaymentRequest() in EasyPay class tokenrespone :- "+tokenrespone);
			 Map<String,Object> mp = null;
			 if(org.apache.commons.lang3.StringUtils.isNotBlank(tokenrespone)) {
			  mp = Utility.getMapper().readValue(tokenrespone, Map.class);
			 LOG.info("In genreatePaymentRequest() in EasyPay class tokenrespone map :- "+mp);
			 if(!mp.isEmpty() && mp!=null) {
			 token = (String) mp.get(MainetConstants.EASYPAY_PARAM.EASYPAY_DEFAULT_KEY);
			 LOG.info("In genreatePaymentRequest() in EasyPay class token is :- "+token);
			  if(StringUtils.isBlank(token)) {
				  token = MainetConstants.EASYPAY_PARAM.EASYPAY_DEFAULT_TOKEN;
			  }
			 }else {
				 token = MainetConstants.EASYPAY_PARAM.EASYPAY_DEFAULT_TOKEN;
			  }
			 }else {
				 token = MainetConstants.EASYPAY_PARAM.EASYPAY_DEFAULT_TOKEN;
			 }
			 }catch(final Exception exception){
				  token = MainetConstants.EASYPAY_PARAM.EASYPAY_DEFAULT_TOKEN;
				  LOG.error("In genreatePaymentRequest() in EasyPay exception occurs in execute post :- "+exception);
				  exception.printStackTrace();
			 }
				StringBuilder url = new StringBuilder(paymentRequestVO.getAddField7());
				url.append(MainetConstants.operator.QUE_MARK + MainetConstants.EASYPAY_PARAM.Encrypted_req_response
						+ MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE + encryptedReq
						+ MainetConstants.operator.AMPERSENT
	        			+ MainetConstants.EASYPAY_PARAM.EASYPAY_TOKEN + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
	        			+ token);
				paymentRequestVO.setPayRequestMsg(url.toString());
				LOG.info("In genreatePaymentRequest() in EasyPay class RequestBody in case of mobile:- "+url.toString());
			}else {
				paymentRequestVO.setPayRequestMsg(encryptedReq);
			}
		}
		      paymentRequestVO.setHash(reqString);		
		}catch (final Exception exception) {
			LOG.error(" Exception occur in genreatePaymentRequest in Easypay Payment", exception);
			throw new FrameworkException("Exception occur in genreatePaymentRequest in Easypay Payment ...",
					exception);
		}
		
		return paymentRequestVO;
	}

	@Override
	public Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
			throws FrameworkException {
		LOG.info("start generatePaymentResponse in EasyPay class");
		try {
		String decryptedResponse=EncryptionAndDecryption.decrypt(responseMap.get(MainetConstants.EASYPAY_PARAM.Encrypted_req_response), 
				responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY));
		LOG.info("decrypted response:-"+decryptedResponse);
		String[] splitedresponse = decryptedResponse.split(MainetConstants.operator.AMPERSENT);
		
		Map<String,String> map= new HashMap<>();
		for(String value:splitedresponse) {
			String[] Pairresponse=value.split(MainetConstants.EASYPAY_PARAM.EQUALS_SYMB);
			if(Pairresponse.length == 2) {
			map.put(Pairresponse[0].trim(),Pairresponse[1].trim());
			}else {
				map.put(Pairresponse[0].trim(),MainetConstants.EASYPAY_PARAM.DOUBLE_DASH);
			}
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
			double amount;
			try {
				 amount = Double.parseDouble(map.get(MainetConstants.EASYPAY_PARAM.AMT));
			} catch (Exception e) {
				 amount = 0.0;
			}
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,map.get(MainetConstants.EASYPAY_PARAM.STC)+":"+map.get(MainetConstants.EASYPAY_PARAM.RMK));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,map.get(MainetConstants.EASYPAY_PARAM.RID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,map.get(MainetConstants.EASYPAY_PARAM.BRN));
		responseMap.put(MainetConstants.BankParam.TXN_ID, map.get(MainetConstants.EASYPAY_PARAM.RID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, map.get(MainetConstants.EASYPAY_PARAM.TET));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,
				map.get(MainetConstants.EASYPAY_PARAM.RMK));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,
				map.get(MainetConstants.EASYPAY_PARAM.BRN));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, String.valueOf(amount));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, String.valueOf(amount));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,
				MainetConstants.PG_SHORTNAME.EASYPAY);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,MainetConstants.ONLINE);
		responseMap.put(MainetConstants.BankParam.HASH,
				decryptedResponse);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
				responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,
				map.get(MainetConstants.EASYPAY_PARAM.RID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,
				map.get(MainetConstants.EASYPAY_PARAM.CRN));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,
				MainetConstants.REQUIRED_PG_PARAM.NA);
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
	
	@Override
	public Map<String, String> generatePaymentRequestForStatusAPI(PaymentRequestDTO paymentRequestVO)
			throws FrameworkException, Exception {
		Map<String, String> responseMap=new HashMap<>();
		try {
			//CKS_value = hash("sha256", CID + RID + CRN + key);
			String CKS_value = paymentRequestVO.getMerchantId() + paymentRequestVO.getTxnid() + paymentRequestVO.getApplicationId() 
			 + paymentRequestVO.getSalt();
          CKS_value = EncryptionAndDecryption.checkSum(CKS_value);
           LOG.info("Checksum for statusEnquiry:- "+ CKS_value);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
   		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   		
           // CID=2835&RID=123456&CRN=123456&VER=1.0&TYP=TEST&CRN=123456&CKS=60fe1f3b8a86d9477bf7722d444b5f14da303f65bd55bb4efb37ac4370e0178e 
			
      	 String reqString = MainetConstants.EASYPAY_PARAM.CID+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getMerchantId() 
			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.RID+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
			+ paymentRequestVO.getTxnid()
			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.CRN+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
			+ paymentRequestVO.getApplicationId() 
			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.VER+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
			+ paymentRequestVO.getAddField1()
			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.TYP+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB 
			+ paymentRequestVO.getRequestType() 
			+ MainetConstants.operator.AMPERSENT+MainetConstants.EASYPAY_PARAM.CKS+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
			+ CKS_value;
           
      	LOG.info("In generatePaymentRequestForStatusAPI() in EasyPay class RequestBody before encrypt for StatusEnquiry:- "+reqString);
		 String encryptedReq= EncryptionAndDecryption.encrypt(reqString,paymentRequestVO.getKey());
		 LOG.info("In generatePaymentRequestForStatusAPI() in EasyPay class RequestBody after encrypt StatusEnquiry:- "+encryptedReq);     
		 String urlParameters = MainetConstants.EASYPAY_PARAM.Encrypted_req_response+MainetConstants.EASYPAY_PARAM.EQUALS_SYMB+encryptedReq;
		 LOG.info("urlParameters :- "+urlParameters);
		 String encResponse=executePost(paymentRequestVO.getAddField4(),urlParameters,Integer.parseInt(paymentRequestVO.getAddField5()));
		 LOG.info("Encrypted response :- "+encResponse); 
		 String decryptedResponse=EncryptionAndDecryption.decrypt(encResponse,paymentRequestVO.getKey());
			LOG.info("decrypted response:-"+decryptedResponse);
			String[] splitedresponse = decryptedResponse.split(MainetConstants.operator.AMPERSENT);
			
			Map<String,String> map= new HashMap<>();
			for(String value:splitedresponse) {
				String[] Pairresponse=value.split(MainetConstants.EASYPAY_PARAM.EQUALS_SYMB);
				if(Pairresponse.length == 2) {
				map.put(Pairresponse[0].trim(),Pairresponse[1].trim());
				}else {
					map.put(Pairresponse[0].trim(),MainetConstants.EASYPAY_PARAM.DOUBLE_DASH);
				}
			}
			LOG.info("response in key-value pair:-"+map);
			if(!map.isEmpty()) {
			
		  if (map.get(MainetConstants.EASYPAY_PARAM.STC).equals(MainetConstants.EASYPAY_PARAM.SUCCESS_CODE)) {
				LOG.info(" Payment Status is Success ");
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
						MainetConstants.PAYU_STATUS.SUCCESS);
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_MSG,
						ApplicationSession.getInstance().getMessage("eip.stp.msg"));
			}else if (map.get(MainetConstants.EASYPAY_PARAM.STC).equals(MainetConstants.EASYPAY_PARAM.FAILURE_CODE)) {
					LOG.info(" Payment Status is Failure ");
				 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
							MainetConstants.PAYU_STATUS.FAIL);
				 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_MSG,
						 ApplicationSession.getInstance().getMessage("eip.ftp.msg"));
				}else {
					LOG.info(" Payment Status is in Process/Pending ");
					 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
							 MainetConstants.EASYPAY_PARAM.IN_PROCESS_OR_PENDING);
					 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_MSG,
							 ApplicationSession.getInstance().getMessage("eip.pending.msg"));
				}
		      double amount;
				try {
					 amount = Double.parseDouble(map.get(MainetConstants.EASYPAY_PARAM.AMT));
				} catch (Exception e) {
					 amount = 0.0;
				}
		  responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,map.get(MainetConstants.EASYPAY_PARAM.STC)+":"+map.get(MainetConstants.EASYPAY_PARAM.RMK));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,map.get(MainetConstants.EASYPAY_PARAM.RID));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,map.get(MainetConstants.EASYPAY_PARAM.BRN));
			responseMap.put(MainetConstants.BankParam.TXN_ID, String.valueOf(paymentRequestVO.getTxnid()));
			if(!map.get(MainetConstants.EASYPAY_PARAM.TET).equals(MainetConstants.EASYPAY_PARAM.DOUBLE_DASH)) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, sdf2.format(sdf1.parse(map.get(MainetConstants.EASYPAY_PARAM.TET))));
			}
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,
					map.get(MainetConstants.EASYPAY_PARAM.RMK));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,
					map.get(MainetConstants.EASYPAY_PARAM.BRN));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, String.valueOf(amount));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, String.valueOf(amount));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,
					MainetConstants.PG_SHORTNAME.EASYPAY);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,MainetConstants.STATUS_API);
			responseMap.put(MainetConstants.BankParam.HASH,
					decryptedResponse);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
					responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,
					map.get(MainetConstants.EASYPAY_PARAM.RID));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,
					map.get(MainetConstants.EASYPAY_PARAM.CRN));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,
					MainetConstants.REQUIRED_PG_PARAM.NA);
			responseMap.put(MainetConstants.BankParam.PROD_INFO,
					paymentRequestVO.getServiceName());
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.OWNER_NAME, paymentRequestVO.getApplicantName());
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PHONE_NO, paymentRequestVO.getMobNo());
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.EMAIL, paymentRequestVO.getEmail());
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3,paymentRequestVO.getUdf3());
			}else {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
						MainetConstants.PAYU_STATUS.FAIL);
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
						"Response Map is null");
				LOG.error("Response Map is null");
				throw new FrameworkException("Response Map is null");
			}
			
		} catch (Exception e) {
			LOG.error(" Exception occur in generatePaymentRequestForStatusAPI in Easypay", e);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
					MainetConstants.PAYU_STATUS.FAIL);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
					"Error While parsing response object");
			responseMap.put(MainetConstants.BankParam.HASH,
					responseMap.get(MainetConstants.EASYPAY_PARAM.Encrypted_req_response));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
					MainetConstants.REQUIRED_PG_PARAM.NA);
			throw new FrameworkException("Exception occur in generatePaymentRequestForStatusAPI in Easypay Payment");
		}
		return responseMap;
	}

	public String executePost(final String targetURL, final String urlParameters, final int timeout) throws Exception {
        HttpURLConnection connection = null;
        String resStr = null;
        try {
            final URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", new StringBuilder().append(Integer.toString(urlParameters.getBytes().length)).toString());
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            final InputStream is = connection.getInputStream();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            final StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            resStr = response.toString();
            if (resStr != null) {
                return resStr.trim();
            }
            return null;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
	
}
