package com.abm.mainet.common.utility;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
 

public class HDFCIntegrationClass {
	
	private static final Logger LOGGER = Logger.getLogger(HDFCIntegrationClass.class);
	private static String wsURL="https://180.179.175.17/apis/servlet/DoWebTrans";
	//Production API URL:-https://login.ccavenue.com/apis/servlet/DoWebTrans
	//Staging API URL:-https://180.179.175.17/apis/servlet/DoWebTrans

	
	
	public static void main(String[] args) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		String dateString = format.format(new Date());
		System.out.println(dateString);
		//String pXmlData="<?xml version='1.0' encoding='UTF-8' standalone='yes'?><Order_Status_Query order_no='23828' reference_no=''/>";
		String pXmlData= "{'order_no':'24033'}";
		String pAccessCode="AVKH75EL03BL46HKLB";
		String aesKey="396F3069E147AE3F42D96C6E7F4559C9";
		String pCommand="orderStatusTracker";
		String pRequestType="json";
		String pResponseType="json";
		String pVersion="1.1";
		
		new HDFCIntegrationClass().callCCavenueApi(pXmlData, pAccessCode, pCommand, aesKey, pRequestType, pResponseType, pVersion);
		try {
			//processSms("", "");
			//processSms1("", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
	 * Method to call ccavenue api. Output is printed on console within this method.
	 * */
	public CallBackResponseObj callCCavenueApi(String pXmlData,String pAccessCode,String pCommand,String aesKey,String pRequestType,String pResponseType, String pVersion){
		LOGGER.info("pXmlData "+" "+pXmlData);
		LOGGER.info("pAccessCode "+" "+pAccessCode);
		LOGGER.info("pCommand "+" "+pCommand);
		LOGGER.info("aesKey "+" "+aesKey);
		LOGGER.info("pRequestType "+" "+pRequestType);
		LOGGER.info("pResponseType "+" "+pResponseType);
		LOGGER.info("pVersion "+" "+pVersion);
		
		String vResponse="",encXMLData="",encResXML="",decXML="";
		StringBuffer wsDataBuff=new StringBuffer();
		CallBackResponseObj hdfc =new CallBackResponseObj();
		try {
			 AesUtil aesUtil=new AesUtil(aesKey);
			 if(aesKey!=null && !aesKey.equals("") && pXmlData!=null && !pXmlData.equals("")){
				encXMLData  = aesUtil.encrypt(pXmlData);
			 }
			 
			 LOGGER.info("Encrypted format "+" "+encXMLData);
			 
			wsDataBuff.append("enc_request="+encXMLData+"&access_code="+pAccessCode+"&command="+pCommand+"&response_type="+pResponseType+"&request_type="+pRequestType+"&version="+pVersion);
			LOGGER.info("Request "+" "+wsDataBuff.toString());
			vResponse = processUrlConnectionReq(wsDataBuff.toString(), ApplicationSession.getInstance().getMessage("ccAvenue.payment.callback.url"));
			if(vResponse!=null && !vResponse.equals("")){
				Map hm=tokenizeToHashMap(vResponse, "&", "=");
				encResXML = hm.containsKey("enc_response")?hm.get("enc_response").toString():"";
				LOGGER.info("Complete response from API"+" "+encResXML);
				String vStatus = hm.containsKey("status")?hm.get("status").toString():"";
				String vError_code = hm.containsKey("enc_error_code")?hm.get("enc_error_code").toString():"";
				
				if(vStatus.equals("1")){//If Api call failed
					System.out.println("enc_response : "+ encResXML);
					System.out.println("error_code : "+ vError_code);
					hdfc.setStatus("1");
					return hdfc;
				} 
				if(!encResXML.equals("")) {
					//AesUtil aesUtil=new AesUtil(aesKey);
					decXML = aesUtil.decrypt(encResXML);
					System.out.println("enc_response : "+decXML);	
					JsonFactory factory = new JsonFactory();
					JsonParser  parser  = factory.createParser(decXML);
					
					while(!parser.isClosed()){
					    JsonToken jsonToken = parser.nextToken();

					    if(JsonToken.FIELD_NAME.equals(jsonToken)){
					        String fieldName = parser.getCurrentName();
					        System.out.println(fieldName);

					        jsonToken = parser.nextToken();

					        if("order_no".equals(fieldName)){
					            hdfc.setOrder_no(parser.getValueAsString());
					        } else if ("reference_no".equals(fieldName)){
					        	 hdfc.setReference_no(parser.getValueAsString());
					        }else if ("reference_no".equals(fieldName)){
					        	 hdfc.setReference_no(parser.getValueAsString());
					        }else if("order_amt".equals(fieldName)){
					        	hdfc.setOrder_amt(parser.getValueAsString());
					        }
					        else if("status".equals(fieldName)){
					        	hdfc.setStatus(parser.getValueAsString());
					        }
					    }
					}
					
					return hdfc;
				}
				
				LOGGER.info("Response from API "+" "+vStatus);
			}
			hdfc.setStatus("1");
			LOGGER.info("Failed the response");
		}
		catch (Exception e) {
			hdfc.setStatus("1");
			LOGGER.info("enc_response : "+ e.getMessage());
		}
		return hdfc;
	}
	
	public static HashMap tokenizeToHashMap(String msg, String delimPairValue, String delimKeyPair){
		HashMap keyPair = new HashMap();
		ArrayList respList = new ArrayList();
		String part = "";
		StringTokenizer strTkn = new StringTokenizer(msg, delimPairValue,true);
		while (strTkn.hasMoreTokens())
		{
			part = (String)strTkn.nextElement(); 
			if(part.equals(delimPairValue)) {
				part=null; 
			}
			else {
				String str[]=part.split(delimKeyPair, 2);
				keyPair.put(str[0], str.length>1?(str[1].equals("")?null:str[1]):null);
			}
			if(part == null) continue;
			if(strTkn.hasMoreTokens()) strTkn.nextElement();
		}		
		return keyPair.size() > 0 ? keyPair : null;
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
	

	
}
