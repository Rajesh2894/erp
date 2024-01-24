/**
 * 
 */
package com.abm.mainet.payment.service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.crypto.IllegalBlockSizeException;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.awl.merchanttoolkit.dto.ReqMsgDTO;
import com.awl.merchanttoolkit.dto.ResMsgDTO;
import com.awl.merchanttoolkit.transaction.AWLMEAPI;
import com.ccavenue.security.AesCryptUtil;


/**
 * @author Santosh.Datir
 *
 */
public class Awlline implements PaymentStrategy {

	protected Logger LOG = Logger.getLogger(Awlline.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.payment.service.PaymentStrategy#generatePaymentRequest(com.abm
	 * .mainet.payment.dto.PaymentRequestDTO)
	 */
	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {

		LOG.info("Start the genreatePaymentRequest() in Awlline payment class ");
		
		try {
			if ((paymentRequestVO.getPgUrl() != null && !paymentRequestVO.getPgUrl().isEmpty())
					&& (paymentRequestVO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.AWL))) {
								
				ReqMsgDTO objReqMsgDTO = new ReqMsgDTO();
				objReqMsgDTO. setMid(paymentRequestVO.getMerchantId());
				objReqMsgDTO. setOrderId(paymentRequestVO.getTxnid().toString());
				Double totalAmount = new Double(0D);
				totalAmount=100* paymentRequestVO.getValidateAmount().doubleValue();
				LOG.info("totalTransAmount!!!!!!!!!!!!!!!!!!"+totalAmount.intValue());
				objReqMsgDTO. setTrnAmt(String.valueOf(totalAmount.intValue()));
				objReqMsgDTO. setTrnCurrency(paymentRequestVO.getTrnCurrency());
				objReqMsgDTO.setTrnRemarks(paymentRequestVO.getTrnRemarks());	
				objReqMsgDTO. setMeTransReqType(paymentRequestVO.getRequestType());
				objReqMsgDTO. setResponseUrl(paymentRequestVO.getSuccessUrl());
				LOG.info("paymentRequestVO.getResponseUrl()!!!!!!!!!!!!!!!!!!"+paymentRequestVO.getResponseUrl());
				LOG.info("paymentRequestVO.getResponseUrl()!!!!!!!!!!!!!!!!!!"+paymentRequestVO.getSuccessUrl());
				objReqMsgDTO. setEnckey(paymentRequestVO.getKey());
				objReqMsgDTO.setAddField1(paymentRequestVO.getAddField1());
				objReqMsgDTO.setAddField2(paymentRequestVO.getAddField2());
				objReqMsgDTO.setAddField3(paymentRequestVO.getAddField3());
				objReqMsgDTO.setAddField4(paymentRequestVO.getAddField4());
				objReqMsgDTO.setAddField5(paymentRequestVO.getAddField5());
				objReqMsgDTO.setAddField6(paymentRequestVO.getAddField6());
				objReqMsgDTO.setAddField7(paymentRequestVO.getAddField7());
				objReqMsgDTO.setAddField8(paymentRequestVO.getAddField8());
				objReqMsgDTO.setRecurrPeriod(paymentRequestVO.getRecurrPeriod());
				objReqMsgDTO.setRecurrDay(paymentRequestVO.getRecurrDay());
				objReqMsgDTO.setNoOfRecurring(paymentRequestVO.getNoOfRecurring());
				objReqMsgDTO.setAddField9(paymentRequestVO.getUdf8());
				AWLMEAPI objAWLMEAPI = new AWLMEAPI();
				objReqMsgDTO = objAWLMEAPI.generateTrnReqMsg(objReqMsgDTO);
				String merchantRequest = null; 
				if (objReqMsgDTO.getStatusDesc().equals("Success")) 
				{
					merchantRequest =objReqMsgDTO.getReqMsg();
				}
			
				StringBuilder url = new StringBuilder(paymentRequestVO.getPgUrl());
				
				url.append(merchantRequest);
				paymentRequestVO.setPayRequestMsg(merchantRequest);
				paymentRequestVO.setHash(url.toString());
				LOG.info("Single Token request String " + url.toString());
			} else {
				LOG.error("Payment Required parameter are not fetch from tables");
				throw new FrameworkException("Payment Required parameter are not fetch from tables");
			}
		} catch (final Exception exception) {
			LOG.error(" Exception occur in genreatePaymentRequest in Awlline Payment", exception);
			throw new FrameworkException("Exception occur in genreatePaymentRequest in Awlline Payment ...",
					exception);
		}
		return paymentRequestVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.payment.service.PaymentStrategy#generatePaymentResponse(java.
	 * lang.String, java.util.Map)
	 */
	
	 
	 @Override
		public Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
				throws FrameworkException {
				
			String workingKey = "", response = "";
			
			Map<String, String> paymentResponseMap = new HashMap<>(0);
			final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			final Date date = new Date();
			final String currentDate = dateFormat.format(date);
			

			if (responseMap != null && !responseMap.isEmpty()) {
				String sessionAmount = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT);
				
								
				if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.KEY)) {
					workingKey = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY);
					try {
					
					AWLMEAPI objAWLMEAPI = new AWLMEAPI();
					ResMsgDTO objResMsgDTO = objAWLMEAPI .parseTrnResMsg(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCH_RESP), workingKey);
					StringBuilder errorString = new StringBuilder();
					
					if (objResMsgDTO !=null)
					{
						if (objResMsgDTO.getStatusCode().equalsIgnoreCase("f")) {
							paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
									MainetConstants.PAYU_STATUS.FAIL);
							paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
									errorString.toString());
						} else {
							if (objResMsgDTO.getStatusCode().equalsIgnoreCase("s")) {
								paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
										MainetConstants.PAYU_STATUS.SUCCESS);

							}
							else {
								paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
										MainetConstants.PAYU_STATUS.FAIL);
								errorString.append((objResMsgDTO.getResponseCode() + " : "
										+ objResMsgDTO.getStatusDesc()));
							}
						}
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
								errorString.toString());
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,
								objResMsgDTO.getOrderId());
						
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,
								objResMsgDTO.getPgMeTrnRefNo());

						paymentResponseMap.put(MainetConstants.BankParam.TXN_ID,
								objResMsgDTO.getOrderId());

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, currentDate);

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,
								objResMsgDTO.getStatusDesc());

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,
								objResMsgDTO.getRrn());

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, sessionAmount);
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, sessionAmount);

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,
								MainetConstants.PG_SHORTNAME.AWL);

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
								objResMsgDTO.getAuthZCode());

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,
								objResMsgDTO.getAddField1());

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,
								objResMsgDTO.getAddField2());

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3,
								objResMsgDTO.getAddField3());

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF5,
								objResMsgDTO.getAddField4());
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF6,
								objResMsgDTO.getAddField5());
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF7,
								objResMsgDTO.getAddField6());
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF8,
								objResMsgDTO.getAddField7());
						
										
						paymentResponseMap.put(MainetConstants.BankParam.HASH,
								MainetConstants.REQUIRED_PG_PARAM.NA);

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,
								responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
								responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));

						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,
								responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID));
					} else {
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
								MainetConstants.PAYU_STATUS.FAIL);
						LOG.error("Session Parameters mismatched with Payment Gatway Parameters");
						throw new FrameworkException(
								"Session Paratemeters mismatched with Payment Gatway Parameters");
					}

				} catch (Exception exp) {
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
							MainetConstants.PAYU_STATUS.FAIL);
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
							"Erro While decrypting the encrypted response");
					paymentResponseMap.put(MainetConstants.BankParam.HASH,
							MainetConstants.REQUIRED_PG_PARAM.NA);
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
							MainetConstants.REQUIRED_PG_PARAM.NA);
				}	
						
					}
					 else {
							paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
									MainetConstants.PAYU_STATUS.FAIL);
							LOG.error("Session Parameters mismatched with Payment Gatway Parameters");
							throw new FrameworkException(
									"Session Paratemeters mismatched with Payment Gatway Parameters");
						}
		}
		
		else 
		        {
			LOG.error("Working key is not available for Awlline response");
			throw new FrameworkException("Working key is not available for Awlline response");
		}
	       

	responseMap.clear();
	responseMap.putAll(paymentResponseMap);
	return responseMap;

	}

	    public String getCurrentDate() {

	        final DateFormat inputformat = new SimpleDateFormat(MainetConstants.DATEFORMAT_DDMMYYYY);
	        final String currentDate = inputformat.format(new Date());
	        return currentDate;
	    }

	    private String generateEncryptedValue(final String workingKey, final LinkedHashMap<String, String> map) {
	        String pname = "", pvalue = "";
	        StringBuilder ccaRequest = new StringBuilder();
	        for (Entry<String, String> requestParameters : map.entrySet()) {
	            pname = requestParameters.getKey();
	            ccaRequest.append(pname + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE);
	            pvalue = requestParameters.getValue();
	            ccaRequest.append(pvalue + MainetConstants.operator.AMPERSAND);
	        }

	        AesCryptUtil aesUtil = new AesCryptUtil(workingKey);
	        return aesUtil.encrypt(ccaRequest.toString());
	    }

	    private String encryptedParameter(final LinkedHashMap<String, String> map) {
	        String pname = "", pvalue = "";
	        StringBuilder ccaRequest = new StringBuilder();
	        for (Entry<String, String> requestParameters : map.entrySet()) {
	            pname = requestParameters.getKey();
	            ccaRequest.append(pname + MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE);
	            pvalue = requestParameters.getValue();
	            ccaRequest.append(pvalue + MainetConstants.operator.AMPERSAND);
	        }
	        String encryptedString = null;
	        try {
	            encryptedString = EncryptionAndDecryption.encrypt(ccaRequest.toString());
	            LOG.info("map form before sending data is " + map.toString() + " while encrypted request is "
	                    + encryptedString);
	        } catch (IllegalBlockSizeException exp) {
	            LOG.error("Error while creating the encrypted request for Payment Request DTO");
	        }
	        return encryptedString;
	    }

	    private Map<String, String> decrypteParameter(final String response) {
	        String decResp = null;
	        try {
	            decResp = EncryptionAndDecryption.decrypt(response);
	            LOG.info(" decrypted request is " + decResp);
	        } catch (IllegalBlockSizeException exp) {
	            LOG.error("Error while creating the decrypted response from CCAvenue PG");
	            throw new FrameworkException("Error while creating the decrypted response from CCAvenue PG", exp);
	        }

	        StringTokenizer tokenizer = new StringTokenizer(decResp, MainetConstants.operator.AMPERSAND);
	        final Map<String, String> map = new HashMap<>(0);
	        String pair = "", pname = "", pvalue = "";

	        while (tokenizer.hasMoreTokens()) {
	            pair = tokenizer.nextToken();
	            if (pair != null) {
	                StringTokenizer strTok = new StringTokenizer(pair,
	                        MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE);
	                if (strTok.hasMoreTokens()) {
	                    pname = strTok.nextToken();
	                    if (strTok.hasMoreTokens())
	                        pvalue = strTok.nextToken();
	                    map.put(pname, pvalue);
	                }
	            }
	        }
	        LOG.info(" decrypted map is " + map.toString());
	        return map;
	    }

	    private Map<String, String> generateDecryptedValue(final String workingKey, final String response) {

	        AesCryptUtil aesUtil = new AesCryptUtil(workingKey);
	        String decResp = aesUtil.decrypt(response);

	        StringTokenizer tokenizer = new StringTokenizer(decResp, MainetConstants.operator.AMPERSAND);
	        final Map<String, String> map = new HashMap<>(0);
	        String pair = "", pname = "", pvalue = "";

	        while (tokenizer.hasMoreTokens()) {
	            pair = tokenizer.nextToken();
	            if (pair != null) {
	                StringTokenizer strTok = new StringTokenizer(pair,
	                        MainetConstants.SEARCH_PROPERTY_DETAILS.NAME.SEARCH_SPECIAL_CHAR_REPLACE);
	                if (strTok.hasMoreTokens()) {
	                    pname = strTok.nextToken();
	                    if (strTok.hasMoreTokens())
	                        pvalue = strTok.nextToken();
	                    map.put(pname, pvalue);
	                }
	            }
	        }
	        LOG.info("generateDecryptedValue is " + map.toString());
	        return map;
	    }
	}

