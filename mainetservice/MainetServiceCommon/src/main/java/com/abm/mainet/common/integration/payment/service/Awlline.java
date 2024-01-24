/**
 * 
 */
package com.abm.mainet.common.integration.payment.service;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.utility.ApplicationSession;
import com.awl.merchanttoolkit.dto.ReqMsgDTO;
import com.awl.merchanttoolkit.dto.ResMsgDTO;
import com.awl.merchanttoolkit.security.HexUtil;
import com.awl.merchanttoolkit.transaction.AWLMEAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Santosh.Datir
 *
 */
public class Awlline implements PaymentStrategy {

	protected Logger LOG = Logger.getLogger(Awlline.class);
	final ObjectMapper mapper = new ObjectMapper();
    private SecretKeySpec skeySpec;
    private Cipher cipher;
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
				objReqMsgDTO.setMid(paymentRequestVO.getMerchantId());
				objReqMsgDTO.setOrderId(paymentRequestVO.getApplicationId()+":"+paymentRequestVO.getTxnid().toString());
				LOG.info("Unique Order Id:  "+objReqMsgDTO.getOrderId());
				Double totalAmount = new Double(0D);
				totalAmount=100* paymentRequestVO.getValidateAmount().doubleValue();//converting Rs. into paisa
				objReqMsgDTO.setTrnAmt(String.valueOf(totalAmount.intValue()));
				objReqMsgDTO.setTrnCurrency(paymentRequestVO.getTrnCurrency());
				objReqMsgDTO.setTrnRemarks(paymentRequestVO.getTrnRemarks());	
				objReqMsgDTO.setMeTransReqType(paymentRequestVO.getRequestType());
				objReqMsgDTO.setResponseUrl(paymentRequestVO.getResponseUrl());
				objReqMsgDTO.setEnckey(paymentRequestVO.getKey());
				objReqMsgDTO.setAddField1(paymentRequestVO.getApplicationId());
				objReqMsgDTO.setAddField2(paymentRequestVO.getAddField2());
				objReqMsgDTO.setAddField3(paymentRequestVO.getAddField3());
				objReqMsgDTO.setAddField4(paymentRequestVO.getAddField4());
				objReqMsgDTO.setAddField5(paymentRequestVO.getAddField5());
				objReqMsgDTO.setAddField6(paymentRequestVO.getAddField6());
				objReqMsgDTO.setAddField7(paymentRequestVO.getAddField7());
				objReqMsgDTO.setAddField8(paymentRequestVO.getAddField8());
				String requestBody = mapper.writeValueAsString(objReqMsgDTO);
				LOG.info("AWL Json Request : "+requestBody);
				AWLMEAPI objAWLMEAPI = new AWLMEAPI();
				objReqMsgDTO = objAWLMEAPI.generateTrnReqMsg(objReqMsgDTO);
				String merchantRequest = null; 
				if (objReqMsgDTO.getStatusDesc().equals("Success")) 
				{
					merchantRequest =objReqMsgDTO.getReqMsg();
				}
				if (StringUtils.isNotBlank(paymentRequestVO.getPayModeorType())) {
					if (paymentRequestVO.getPayModeorType().equals(MainetConstants.PAYMODE.MOBILE)) {
						StringBuilder url = new StringBuilder(paymentRequestVO.getPgUrl());
						url.append(MainetConstants.operator.QUE_MARK
								+ MainetConstants.EASYPAY_PARAM.MERCHANT_REQUEST
								+ MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE + merchantRequest
								+ MainetConstants.EASYPAY_PARAM.MID_AWL + MainetConstants.REQUIRED_PG_PARAM.SEARCH_SPECIAL_CHAR_REPLACE 
								+ paymentRequestVO.getMerchantId());
						paymentRequestVO.setPayRequestMsg(url.toString());
						LOG.info("In the genreatePaymentRequest() in Awlline mobile url :-  " + url.toString());
					} else {
						paymentRequestVO.setPayRequestMsg(merchantRequest);
					}
				}
				paymentRequestVO.setHash(requestBody);
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
			
		String workingKey = "";
		
		Map<String, String> paymentResponseMap = new HashMap<>(0);
		final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		final Date date = new Date();
		final String currentDate = dateFormat.format(date);
		

		if (responseMap != null && !responseMap.isEmpty()) {
						
			if (responseMap.containsKey(MainetConstants.REQUIRED_PG_PARAM.KEY)) {
				workingKey = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY);
				try {

				AWLMEAPI objAWLMEAPI = new AWLMEAPI();
				ResMsgDTO objResMsgDTO = objAWLMEAPI .parseTrnResMsg(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.MERCH_RESP), workingKey);
				StringBuilder errorString = new StringBuilder();
				String responseBody = mapper.writeValueAsString(objResMsgDTO);
				LOG.info("AWL Json Response : "+responseBody);
				if (objResMsgDTO !=null)
				{
					if (objResMsgDTO.getStatusCode().equalsIgnoreCase("S")) {
						LOG.info(" Payment Status is Success ");
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
								MainetConstants.PAYU_STATUS.SUCCESS);
					} else {
						if (objResMsgDTO.getStatusCode().equalsIgnoreCase("F")) {
							LOG.info(" Payment Status is Failure ");
							paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
									MainetConstants.PAYU_STATUS.FAIL);
						}
						else {
							LOG.info(" Payment Status is Pending ");
							paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
									MainetConstants.PAYU_STATUS.PENDING);
						}
					}
					String orderId=objResMsgDTO.getOrderId();
					int subStringIndex=orderId.indexOf(":");
					String applno=orderId.substring(0, subStringIndex);
					orderId=orderId.substring(subStringIndex+1);
					
					errorString.append(("Status Code:"+objResMsgDTO.getStatusCode() 
							+",Response Code:"+objResMsgDTO.getResponseCode() + ", Description "
							+ objResMsgDTO.getStatusDesc()));
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
							errorString.toString());
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,orderId);
					
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,
							objResMsgDTO.getPgMeTrnRefNo());

					paymentResponseMap.put(MainetConstants.BankParam.TXN_ID,orderId);
					

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, currentDate);

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,
							objResMsgDTO.getStatusDesc());

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,
							objResMsgDTO.getRrn());
					
					double amount;
					try {
						 amount = Double.parseDouble(objResMsgDTO.getTrnAmt())/100; //converting paisa into Rs.
					} catch (Exception e) {
						 amount = 0.0;
					}
						

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, String.valueOf(amount));
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, String.valueOf(amount));

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,
							MainetConstants.PG_SHORTNAME.AWL);

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
							MainetConstants.ONLINE);

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,
							objResMsgDTO.getAddField1());

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,
							applno);

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
							responseBody);

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,
							responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
							responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));

					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,
							responseMap.get(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID));
				} else {
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
							MainetConstants.PAYU_STATUS.FAIL);
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
							"Response Object is null");
					LOG.error("Response Object is null");
					throw new FrameworkException(
							"Response Object is null");
				}

			} catch (Exception exp) {
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
						MainetConstants.PAYU_STATUS.FAIL);
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
						"Erro While parsing response object");
				exp.printStackTrace();
				paymentResponseMap.put(MainetConstants.BankParam.HASH,
						MainetConstants.REQUIRED_PG_PARAM.NA);
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
						MainetConstants.REQUIRED_PG_PARAM.NA);
			}	
					
				}
				 else {
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
								MainetConstants.PAYU_STATUS.FAIL);
						paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
								"Working key is not available for Awlline response");
						LOG.error("Working key is not available for Awlline response");
						throw new FrameworkException(
								"Working key is not available for Awlline response");
					}
	}
	
	else 
	        {
		paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
				MainetConstants.PAYU_STATUS.FAIL);
		paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
				"Response Map is null");
		LOG.error("Response Map is null");
		throw new FrameworkException("Response Map is null");
	}
       

responseMap.clear();
responseMap.putAll(paymentResponseMap);
return responseMap;

}

	@Override
	public Map<String, String> generatePaymentRequestForStatusAPI(PaymentRequestDTO paymentRequestVO)
			throws FrameworkException, Exception {
		LOG.info("Start generatePaymentRequestForStatusAPI in Awline");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Map<String, String> paymentResponseMap=new HashMap<>();
        
        LOG.info(" paymentRequestVO.getMerchantId() : "+paymentRequestVO.getMerchantId());
        LOG.info(" OrderId() : "+paymentRequestVO.getApplicationId()+":"+String.valueOf(paymentRequestVO.getTxnid()));
        LOG.info("paymentRequestVO.getKey() : "+paymentRequestVO.getKey());
        LOG.info("paymentRequestVO.getAddField4() : "+paymentRequestVO.getAddField4());
        LOG.info("paymentRequestVO.getAddField5() : "+paymentRequestVO.getAddField5());
		try {
			ResMsgDTO objResMsgDTO = getTransactionStatus (paymentRequestVO.getMerchantId(), paymentRequestVO.getApplicationId()+":"+String.valueOf(paymentRequestVO.getTxnid()), null, paymentRequestVO.getKey(), paymentRequestVO.getAddField4(),paymentRequestVO.getAddField5());  
			StringBuilder errorString = new StringBuilder();
			LOG.info("In generatePaymentRequestForStatusAPI Status API Called for Payment Status ");
			String map = mapper.writeValueAsString(objResMsgDTO);
			LOG.info(" Response received from Status API:"+map);
			if (objResMsgDTO.getStatusCode().equalsIgnoreCase("S")) {
				LOG.info("In generatePaymentRequestForStatusAPI Payment Status is Success after Status API Called ");
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
						MainetConstants.PAYU_STATUS.SUCCESS);
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_MSG,
						ApplicationSession.getInstance().getMessage("eip.stp.msg"));
			} else {
				if (objResMsgDTO.getStatusCode().equalsIgnoreCase("F")) {
					LOG.info("In generatePaymentRequestForStatusAPI Payment Status is Failed after Status API Called ");
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
							MainetConstants.PAYU_STATUS.FAIL);
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_MSG,
							 ApplicationSession.getInstance().getMessage("eip.ftp.msg"));
				}
				else {
					LOG.info("In generatePaymentRequestForStatusAPI Payment Status is stil Pending after Status API Called ");
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,objResMsgDTO.getStatusCode());
					paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_MSG,
							 ApplicationSession.getInstance().getMessage("eip.pending.msg"));
				}
			}
				double amount;
				try {
					 amount = Double.parseDouble(objResMsgDTO.getTrnAmt())/100; //converting paisa into Rs.
				} catch (Exception e) {
					 amount = 0.0;
				}
				String orderId=objResMsgDTO.getOrderId();
				int subStringIndex=orderId.indexOf(":");
				orderId=orderId.substring(subStringIndex+1);
				errorString.append(("Status Code:"+objResMsgDTO.getStatusCode() 
						+",Response Code:"+objResMsgDTO.getResponseCode() + ", Description "
						+ objResMsgDTO.getStatusDesc()));
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
						errorString.toString());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,orderId);
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,
						objResMsgDTO.getPgMeTrnRefNo());
				paymentResponseMap.put(MainetConstants.BankParam.TXN_ID,String.valueOf(paymentRequestVO.getTxnid()));
				String trnReqDate = MainetConstants.BLANK;
			     try {
				   trnReqDate = sdf2.format(sdf1.parse(objResMsgDTO.getTrnReqDate()));
			     } catch (Exception ex) {
			    	 LOG.info("exception while parsing TrnReqDate and objResMsgDTO.getTrnReqDate() is :- "+objResMsgDTO.getTrnReqDate());
				   trnReqDate = objResMsgDTO.getTrnReqDate();
			     }
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, trnReqDate);
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,
						objResMsgDTO.getStatusDesc());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,
						objResMsgDTO.getRrn());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, String.valueOf(amount));
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, String.valueOf(amount));
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,
						MainetConstants.PG_SHORTNAME.AWL);
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
						MainetConstants.STATUS_API);
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,
						objResMsgDTO.getAddField1());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,
						paymentRequestVO.getApplicationId());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3,
						paymentRequestVO.getUdf3());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF5,
						objResMsgDTO.getAddField4());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF6,
						objResMsgDTO.getAddField5());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF7,
						objResMsgDTO.getAddField6());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF8,
						objResMsgDTO.getAddField7());
				paymentResponseMap.put(MainetConstants.BankParam.HASH,
						map);
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,
						objResMsgDTO.getOrderId());
				paymentResponseMap.put(MainetConstants.BankParam.PROD_INFO,
						paymentRequestVO.getServiceName());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.OWNER_NAME, paymentRequestVO.getApplicantName());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.PHONE_NO, paymentRequestVO.getMobNo());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.EMAIL, paymentRequestVO.getEmail());
				paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3,paymentRequestVO.getUdf3());
			LOG.info("response Map before return generatePaymentRequestForStatusAPI in awline" +paymentResponseMap);
			
		} catch (Exception e) {
			paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
					MainetConstants.PAYU_STATUS.FAIL);
			paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
					"Error While parsing response object");
			paymentResponseMap.put(MainetConstants.BankParam.HASH,
					MainetConstants.REQUIRED_PG_PARAM.NA);
			paymentResponseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,
					MainetConstants.REQUIRED_PG_PARAM.NA);
			LOG.error("Exception occur in generatePaymentRequestForStatusAPI in Worldline Payment",e);
			throw new FrameworkException("Exception occur in generatePaymentRequestForStatusAPI in Worldline Payment");
		}
		return paymentResponseMap;
	}

	public ResMsgDTO getTransactionStatus(String mid, String orderId, String pgmetrn, String enc_key, String tranStatusUrl,String timeOut) throws Exception {
	      if (tranStatusUrl != null && tranStatusUrl.trim().length() >= 1) {
	         if (mid != null && !mid.equals("") && orderId != null && !orderId.equals("") && enc_key != null && !enc_key.equals("")) {
	            StringBuilder merchantReqStr = new StringBuilder();
	            merchantReqStr.append(mid).append("|").append(orderId).append("|").append(pgmetrn).append("|").append("|").append("|").append("|").append("|").append("|").append("|").append("|").append("|").append("|").append("|");
	            initEncrypt(enc_key);
	            String urlParameters = "merchantReqStrT=" + encryptMEMessage(merchantReqStr.toString()) + "&mid=" + mid;
	            LOG.info("urlParameters : "+urlParameters);
	            LOG.info("timeOut : "+timeOut);
	            LOG.info("tranStatusUrl : "+tranStatusUrl);
	            String enc_data = executePost(tranStatusUrl, urlParameters, Integer.parseInt(timeOut));
	            return this.parseTrnResMsg(enc_data, enc_key);
	         } else {
	            ResMsgDTO resMsgDTO = new ResMsgDTO();
	            resMsgDTO.setStatusCode("F");
	            resMsgDTO.setStatusDesc("Mandatory fields are missing.");
	            return resMsgDTO;
	         }
	      } else {
	         throw new IllegalArgumentException("Property File Path is missing.");
	      }
	   }
	
    public void initEncrypt(final String key) throws Exception {
        try {
            this.skeySpec = new SecretKeySpec(HexUtil.HexfromString(key), "AES");
            (this.cipher = Cipher.getInstance("AES")).init(1, this.skeySpec);
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new Exception("Invalid Java Version");
        }
        catch (NoSuchPaddingException nse) {
            throw new Exception("Invalid Key");
        }
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
    
    public String encryptMEMessage(final String reqMsg) throws Exception {
        try {
            final byte[] encstr = this.cipher.doFinal(reqMsg.getBytes());
            return HexUtil.HextoString(encstr);
        }
        catch (BadPaddingException nse) {
            throw new Exception("Invalid input String");
        }
    }
    
    public ResMsgDTO parseTrnResMsg(String resMsg, String encKey) throws Exception {
        ResMsgDTO resMsgDTO = new ResMsgDTO();
        if (resMsg != null && !resMsg.equals("")) {
           initDecrypt(encKey);
           String trnResMsg = decryptMEssage(resMsg);
           if (trnResMsg != null && !trnResMsg.equals("")) {
              String[] resArray = trnResMsg.split("\\|");
              if (resArray.length != 22) {
                 resMsgDTO.setStatusCode("F");
                 resMsgDTO.setStatusDesc("Invalid Response message. No of fields in Response message are " + resArray.length + ". Expected 22 Fields.");
                 return resMsgDTO;
              } else {
                 resMsgDTO.setPgMeTrnRefNo(resArray[0]);
                 resMsgDTO.setOrderId(resArray[1]);
                 resMsgDTO.setTrnAmt(resArray[2]);
                 resMsgDTO.setAuthNStatus(resArray[3]);
                 resMsgDTO.setAuthZStatus(resArray[4]);
                 resMsgDTO.setCaptureStatus(resArray[5]);
                 resMsgDTO.setRrn(resArray[6]);
                 resMsgDTO.setAuthZCode(resArray[7]);
                 resMsgDTO.setResponseCode(resArray[8]);
                 resMsgDTO.setTrnReqDate(resArray[9]);
                 resMsgDTO.setStatusCode(resArray[10]);
                 resMsgDTO.setStatusDesc(resArray[11]);
                 resMsgDTO.setAddField1(resArray[12]);
                 resMsgDTO.setAddField2(resArray[13]);
                 resMsgDTO.setAddField3(resArray[14]);
                 resMsgDTO.setAddField4(resArray[15]);
                 resMsgDTO.setAddField5(resArray[16]);
                 resMsgDTO.setAddField6(resArray[17]);
                 resMsgDTO.setAddField7(resArray[18]);
                 resMsgDTO.setAddField8(resArray[19]);
                 resMsgDTO.setAddField9(resArray[20]);
                 resMsgDTO.setAddField10(resArray[21]);
                 return resMsgDTO;
              }
           } else {
              resMsgDTO.setStatusCode("F");
              resMsgDTO.setStatusDesc("Invalid response message, message is empty");
              return resMsgDTO;
           }
        } else {
           resMsgDTO.setStatusCode("F");
           resMsgDTO.setStatusDesc("Invalid Response message, its empty");
           return resMsgDTO;
        }
     }

    public void initDecrypt(final String key) throws Exception {
        try {
            this.skeySpec = new SecretKeySpec(HexUtil.HexfromString(key), "AES");
            (this.cipher = Cipher.getInstance("AES")).init(2, this.skeySpec);
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new Exception("Invalid Java Version");
        }
        catch (NoSuchPaddingException nse) {
            throw new Exception("Invalid Key");
        }
    }
    
    public String decryptMEssage(final String resMsg) throws Exception {
        try {
            final byte[] encstr = this.cipher.doFinal(HexUtil.HexfromString(resMsg));
            return new String(encstr);
        }
        catch (BadPaddingException nse) {
            throw new Exception("Invalid input String");
        }
    }
}
