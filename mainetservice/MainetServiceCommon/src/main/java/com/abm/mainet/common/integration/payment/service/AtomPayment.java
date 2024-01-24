package com.abm.mainet.common.integration.payment.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CallBackResponseObj;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class AtomPayment implements PaymentStrategy {
	private static Logger LOG = Logger.getLogger(AtomPayment.class);
	/*
	 * Hashing using key with HMACSHA512
	 */
	public static byte[] encodeWithHMACSHA2(String text, String keyString)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		java.security.Key sk = new javax.crypto.spec.SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8),
				"HMACSHA512");
		javax.crypto.Mac mac = javax.crypto.Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
		byte[] hmac = mac.doFinal(text.getBytes("UTF-8"));
		return hmac;
	}

	/*
	 * Convert from byte array to HexString
	 */
	public static String byteToHexString(byte byData[]) {
		StringBuilder sb = new StringBuilder(byData.length * 2);
		for (int i = 0; i < byData.length; i++) {
			int v = byData[i] & 0xff;
			if (v < 16)
				sb.append('0');
			sb.append(Integer.toHexString(v));
		}

		return sb.toString();
	}

	/*
	 * Encoded with HMACSHA512 and encoded with utf-8 using url encoder for given
	 * list of parameter values appended with the key
	 */
	public static String getEncodedValueWithSha2(String hashKey, String... param) {
		String resp = null;
		StringBuilder sb = new StringBuilder();
		for (String s : param) {
			sb.append(s);
		}
		try {
			System.out.println("[getEncodedValueWithSha2]String to Encode =" + sb.toString());
			resp = byteToHexString(encodeWithHMACSHA2(sb.toString(), hashKey));
//resp = URLEncoder.encode(resp,"UTF-8");
		} catch (Exception e) {
			System.out.println("[getEncodedValueWithSha2]Unable to encocd value with key :" + hashKey + " and input :"
					+ sb.toString());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) {
		LOG.info("Start   generatePaymentRequest for ATOM");
		String date = Utility.dateToString(new Date());
		byte[] encodedBytes = Base64.getEncoder().encode(paymentRequestVO.getSchemeCode().getBytes());
		String clientCode = new String(encodedBytes);
		System.out.println("encodedBytes " + new String(encodedBytes));
		String login = paymentRequestVO.getLogin();
		String reqHashKey =ApplicationSession.getInstance().getMessage("atom.payment.reqHash");
		String key=ApplicationSession.getInstance().getMessage("atom.payment.reqkey");
		String salt=ApplicationSession.getInstance().getMessage("atom.payment.reqsalt");
        //login,pass,ttype,prodid,txnid,amt,txncurr
		String signature_request = getEncodedValueWithSha2(reqHashKey, paymentRequestVO.getLogin(),
				paymentRequestVO.getPassword(), paymentRequestVO.getRequestType(), paymentRequestVO.getProdid(),
				paymentRequestVO.getTxnid().toString(), paymentRequestVO.getDueAmt().toString(),  paymentRequestVO.getTrnCurrency());
		LOG.info("Request signature ::" + signature_request);
		String encString = "login=" + paymentRequestVO.getLogin() + "&pass=" + paymentRequestVO.getPassword()
				+ "&ttype=" + paymentRequestVO.getRequestType() + "&prodid=" + paymentRequestVO.getProdid() + "&amt="
				+ paymentRequestVO.getDueAmt() + "&txncurr=" + paymentRequestVO.getTrnCurrency() + "&txnscamt=0"
				+ "&clientcode=" + clientCode + "&txnid=" + paymentRequestVO.getTxnid() + "&date=" + date
				+ "&custacc="+paymentRequestVO.getCustAccNo()+"&udf1="+paymentRequestVO.getUdf1()+"&udf2="+paymentRequestVO.getUdf2()+"&udf3="+paymentRequestVO.getUdf3()+"&ru="+paymentRequestVO.getSuccessUrl()+"&signature="
				+ signature_request;
		LOG.info("encString ::" + encString);
		String encVal = null;
		try {
			encVal = new AtomAES().encrypt(encString, key,salt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info("Succsess Message ::" + encVal);
		paymentRequestVO.setPayRequestMsg(
				paymentRequestVO.getPgUrl() + "login=" + paymentRequestVO.getLogin() + "&encdata=" + encVal);
		LOG.info("End   generatePaymentRequest for ATOM");
		return paymentRequestVO;
	}

	@Override
	public  Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
			throws FrameworkException {
		LOG.info("Start   generatePaymentResponse for ATOM");
		String decryptVal = null;
		String key=ApplicationSession.getInstance().getMessage("atom.payment.reskey");
		String salt=ApplicationSession.getInstance().getMessage("atom.payment.ressalt");
		try {
			decryptVal = new AtomAES().decrypt(responseMap.get("encdata"), key,
					salt);
		} catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
		}
		Map<String, String> resultMap=new HashMap<String, String>();
		String[] decryptValarr=decryptVal.split("&");
		for(String arr:decryptValarr) {
			String[] splitdata=arr.split("=");
			if(splitdata!=null && splitdata.length>1)
				resultMap.put(splitdata[0], splitdata[1]);
		}
		String finalStatus=resultMap.get("f_code");
		LOG.info("Final Status on call Back URL"+finalStatus);
		if(StringUtils.equalsIgnoreCase(finalStatus, MainetConstants.AccountBillEntry.OK) ) {
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.SUCCESS);
		}else if(StringUtils.equalsIgnoreCase(finalStatus, MainetConstants.FlagF) ) {
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.FAIL);
		}else if(StringUtils.equalsIgnoreCase(finalStatus, MainetConstants.FlagC) ) {
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.CANCEL);
		}
		
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,resultMap.get("ipg_txn_id"));
		responseMap.put(MainetConstants.BankParam.TXN_ID, resultMap.get("mer_txn"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, Utility.dateToString(new Date()));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,resultMap.get("desc"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,resultMap.get("bank_txn"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, resultMap.get("amt"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, resultMap.get("amt"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,MainetConstants.PG_SHORTNAME.ATOMPAY);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,MainetConstants.ONLINE);
		responseMap.put(MainetConstants.BankParam.HASH,responseMap.toString());
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,responseMap.get(responseMap.get("orderid")));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,resultMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,resultMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF2));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3,resultMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF3));
		LOG.info("End   generatePaymentResponse for ATOM");
		return responseMap;
	}
	
	public static CallBackResponseObj getResponseForPaymentStatus(String merchantId,Long merchantTransId,BigDecimal amnt, String transDate) {
		 String key=ApplicationSession.getInstance().getMessage("atom.payment.reqkey");
		 String salt=ApplicationSession.getInstance().getMessage("atom.payment.reqsalt");
		 String url=ApplicationSession.getInstance().getMessage("atom.payment.callback.url");
		 String encString = "merchantid=" + merchantId + "&merchanttxnid=" + merchantTransId
		                    + "&amt=" + amnt + "&tdate=" + transDate;
		 LOG.info("encString "+encString +" key "+key +"Salt "+salt+"url "+url);
		CallBackResponseObj atom =new CallBackResponseObj();
		try {
			String encVal = null;
			try {
				encVal = new AtomAES().encrypt(encString, key,salt);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String strURL = url+merchantId+"&encdata="+encVal;
			LOG.info(strURL);
			  //Creating a HttpClient object
		      CloseableHttpClient httpclient = getCloseableHttpClient();
	          //Creating a HttpGet object
		      HttpPost httpPost = new HttpPost(strURL);
	          httpPost.setHeader("accept", "application/json");
			  httpPost.setHeader("content-type", "application/json");
			  LOG.info("Request Type: "+httpPost.getMethod());
	
		      //Executing the Get request
		      HttpResponse httpresponse = httpclient.execute(httpPost);
	  	      String content = EntityUtils.toString(httpresponse.getEntity());
	  	      LOG.info("content "+content);
	          String decrypString="";
			 	try {
					decrypString=new AtomAES().decrypt(content,key,salt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 	LOG.info("decrypt plain "+decrypString);
			 
				JsonFactory factory = new JsonFactory();
				JsonParser  parser  = factory.createParser(decrypString);
				
				while(!parser.isClosed()){
				    JsonToken jsonToken = parser.nextToken();
	
				    if(JsonToken.FIELD_NAME.equals(jsonToken)){
				        String fieldName = parser.getCurrentName();
				        LOG.info(fieldName);
	
				        jsonToken = parser.nextToken();
	
				        if("merchantTxnID".equals(fieldName)){
				        	atom.setOrder_no(parser.getValueAsString());
				        }else if ("atomTxnId".equals(fieldName)){
				        	atom.setReference_no(parser.getValueAsString());
				        }else if("amt".equals(fieldName)){
				        	atom.setOrder_amt(parser.getValueAsString());
				        }
				        else if("statusCode".equals(fieldName)){
				        	atom.setStatus(parser.getValueAsString());
				        }
				    }
				}
		}catch(Exception e) {
			atom.setStatus("002");
			e.printStackTrace();
			LOG.info("enc_response : "+ e.getMessage());
		}
			
		return atom;
		 	
	}
	public static void main(String[] args)  {
		//String encString1 = "merchantid=192&merchanttxnid=24283&amt=10.00&tdate=2023-04-24";
				//String key="8E41C78439831010F81F61C344B7BFC7";
				//String salt ="8E41C78439831010F81F61C344B7BFC7";
		//String strURL = "https://paynetzuat.atomtech.in/paynetz/vftsv2?login=192&encdata="+encVal;
			
		getResponseForPaymentStatus(null, 0L, new BigDecimal(0.0), null);
		
		String encString = "merchantid=192&merchanttxnid=24283&amt=10.00&tdate=2023-04-24";
				String encVal = null;
				try {
					encVal = new AtomAES().encrypt(encString, "8E41C78439831010F81F61C344B7BFC7",
							"8E41C78439831010F81F61C344B7BFC7");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			System.out.println("palinText "+encString);	
			System.out.println("encrypt "+encVal);
			String decrypString="";
			String decPlainText="BFC23F835C2840C82CCA60671E585308F484FC2431D559E486D18F9B121C9544D863EA98A2270C2F03ED72F26CF0BE1BF5D04468D1C29D56E2F5A413E8C241E4C999A8B8445B5C78644335AF586C5C5BAA5521A7443CBF56A8EB260B51956795D837458C1C00E6680C53E3B4A2D1C84B92B6F4EC729BD801A207A90AC3F0AD408AFB4280D076A54B4AA29AC5AAA39D286BE62688C31C454DAD8885F43DFA55275D8E01543D399F70DF26E5F4AF7264518163E89A4DF591790912F4DF0AD45E57C64102AD0233CA92975E38CDFFE7F237E9551CE4DE06A5D39BA569BD7AFA9F0D2E14E792A370840A8F2563C0A6EB1C6125B93D6A5F778C7715DEC3E116DFB62AC82CC19AA06C543EF1A001CCA677CE2EB62846A2EB75BC71EBE6DDED1F93DFF4E5C9289A648F23DB3482BB4B86E91388801F235581743BCF4373DEC8D24EDD18A22B908F0A44541771E6DAAECE176589";
			System.out.println(decPlainText);
			try {
				decrypString=new AtomAES().decrypt(decPlainText,"8E41C78439831010F81F61C344B7BFC7",
						"8E41C78439831010F81F61C344B7BFC7");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("decrypt plain "+decrypString);
			
				}
	
	
	public static String processUrlConnectionReq(String pBankData,String pBankUrl) throws Exception{
		URL	vUrl = null;
		URLConnection vHttpUrlConnection = null;
		DataOutputStream vPrintout = null;
		DataInputStream	vInput = null;
		StringBuffer vStringBuffer=null;
		
		// Create a trust manager that does not validate certificate chains
       TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
 
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        vUrl = new URL(pBankUrl);
        // Install the all-trusting host verifier
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		if(vUrl.openConnection() instanceof HttpsURLConnection)
		{
			vHttpUrlConnection = (HttpsURLConnection)vUrl.openConnection();
		} 
		else if(vUrl.openConnection() instanceof HttpsURLConnection)
		{
			vHttpUrlConnection = (HttpsURLConnection)vUrl.openConnection();
		} else
		{
			vHttpUrlConnection = (URLConnection)vUrl.openConnection();
		}
		vHttpUrlConnection.setDoInput(true);
		vHttpUrlConnection.setDoOutput(true);
		vHttpUrlConnection.setUseCaches(false);
		vHttpUrlConnection.connect();
		vPrintout = new DataOutputStream (vHttpUrlConnection.getOutputStream());
		vPrintout.writeBytes(pBankData);
		vPrintout.flush();
		vPrintout.close();
		try {
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(vHttpUrlConnection.getInputStream()));
			vStringBuffer = new StringBuffer();
			String vRespData;
			while((vRespData = bufferedreader.readLine()) != null) 
				if(vRespData.length() != 0)
					vStringBuffer.append(vRespData.trim());
			bufferedreader.close();
			bufferedreader = null;
		}finally {  
			if (vInput != null)
				vInput.close();  
			if (vHttpUrlConnection != null)  
				vHttpUrlConnection = null;  
		}  
		return vStringBuffer.toString();
	}
	
	public static CloseableHttpClient getCloseableHttpClient()
	{
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
			        .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
			        {
			            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
			            {
			                return true;
			            }
			        }).build()).build();

		} catch (KeyManagementException e) {
			LOG.error("KeyManagementException in creating http client instance", e);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("NoSuchAlgorithmException in creating http client instance", e);
		} catch (KeyStoreException e) {
			LOG.error("KeyStoreException in creating http client instance", e);
		}
		return httpClient;
	}
}
