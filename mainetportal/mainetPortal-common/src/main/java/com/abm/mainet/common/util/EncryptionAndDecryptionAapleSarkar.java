package com.abm.mainet.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.CRC32;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class EncryptionAndDecryptionAapleSarkar {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionAndDecryptionAapleSarkar.class);
	

	String clientCode =ApplicationSession.getInstance().getMessage("aaple.sarkar.clientCode");  //"KDMCDeptN"
	String clientEncryptKey =ApplicationSession.getInstance().getMessage("aaple.sarkar.clientEncryptKey"); //  "@pn@kdm@m@h@0nl!ne@30489";  
	String clientEncryptIV =ApplicationSession.getInstance().getMessage("aaple.sarkar.clientEncryptIV");  // "KDM@02@3";  
	String checkSumkey = ApplicationSession.getInstance().getMessage("aaple.sarkar.checkSumkey");  //"KKDMCA9v4G8M";
	
	public String authentication(String str,String serviceId){
		
		String response = "false";
		
		try {

			// Decrypt the querystring and obtain token sent by aaple sarkar
			// application
			String requestDecryStr = simpleTripleDesDecrypt(str, clientEncryptKey, clientEncryptIV);
			String param[] = requestDecryStr.split("\\|");
			String checkValueRowData = "";
			String userId = param[0];
			String userTimeStamp = param[1];
			String userSession = param[2];
			String clientCheckSumValue = param[3]; // 2491092092
			String strServiceCookie = param[4];

			checkValueRowData = String.format("%s|%s|%s|%s|%s", userId, userTimeStamp, userSession, checkSumkey,
					strServiceCookie);

			String caluculatedCheckSumValue = generateCheckSumValue(checkValueRowData); // 2491092092

			if (clientCheckSumValue.equals(caluculatedCheckSumValue)) {
				
				String responseXML = "";

				String xmlResponse = "";

				/*String xmlResponse1="";*/
			    
				xmlResponse = callPushWebService(str, clientCode);

				responseXML = simpleTripleDesDecrypt(xmlResponse, clientEncryptKey, clientEncryptIV);

				LOGGER.error("ResponseXml: " + responseXML);
				
				
				List<String> output= new ArrayList<>();
				try {
					//output = getParameterResultFromXML(responseXML, "UserID");
					
					String trackId = getParameterResultFromXML(responseXML, "TrackId").get(0);
					String clienctCode=clientCode;
					String userId1 = getParameterResultFromXML(responseXML, "UserID").get(0);
					String serviceID= serviceId;
					String applicationId = "appId"; // Setting this since our applicationId will generate later
					String payStatus = "N";
				    String payDate = "NA";
					String digitalstatus = "N";
					String digitalDate = "NA";
					String serviceDays = "365";
					String serviceDate = "NA";
					String amount = "0.0";
					String requestFlag = "0";
					String appstatus = "appStatus";
					String remark = "remark"; 
					
					
					String request1 = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", 
							trackId, clienctCode, userId1, serviceID, applicationId, payStatus, payDate, digitalstatus, digitalDate, serviceDays, 
							serviceDate, amount, requestFlag, appstatus, remark, "NA", "NA", "NA", "NA", "NA", checkSumkey);
					
					LOGGER.error("Request1: " + request1);

				    String checksumvalue = generateCheckSumValue(request1);
				 
					LOGGER.error("checksumvalue: " + checksumvalue);

				    response = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
				    		trackId, clienctCode, userId, serviceID, applicationId, payStatus, payDate, digitalstatus, digitalDate, serviceDays, 
							serviceDate, amount, requestFlag, appstatus, remark, "NA", "NA", "NA", "NA", "NA", checksumvalue);
					
				    LOGGER.error("request2: " + response);

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				response = "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
		
	
	public String getUpdateStatus(String str){

		String xmlResponse1="";
	    
		try {

		    LOGGER.error("request2: " + str);
		    
		    String encKey = Utility.simpleTripleDes(str, clientEncryptKey, clientEncryptIV);
		    
		    
		    //String encKey = SimpleTripleDes(str, clientEncryptKey, clientEncryptIV);
		    
		    
		    LOGGER.error("EncKey: " + encKey);

		    xmlResponse1 = callPullWebService(encKey, clientCode);
		    
		    String requestDecryStr = simpleTripleDesDecrypt(xmlResponse1, clientEncryptKey, clientEncryptIV);
		    
		    xmlResponse1 =requestDecryStr;
		    
			LOGGER.error("Response Array is "+ xmlResponse1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return xmlResponse1;
		
	}	
	
	
	public String getParameterData(List<String> output){
		
		
		String[] strarray = new String[output.size()];
		 String[] array = output.toArray(strarray);
		
		 String userID="";
		 
		 if(array.length>0){
		 LOGGER.error("Response Array is "+ array[0]);
		 userID=array[0];
		 }
		
		return "";
	}

	
		public String simpleTripleDesDecrypt(String Data, String strKey, String striv)
		{
			byte[] key = strKey.getBytes(StandardCharsets.UTF_8);
		    byte[] iv = striv.getBytes(StandardCharsets.UTF_8);
		    byte[] data = stringToByteArray(Data);
		    byte[] enc = new byte[0];
		    try {
		        Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
		        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DESede");
		        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		        enc = cipher.doFinal(data);      
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		  return new String(enc, StandardCharsets.UTF_8).trim().replaceAll("[" + "\0" + "]+$", "").replaceAll("[" + "|" + "]+$", "");
		}
		
		public static String bytesToHex(byte[] bytes) {
		    StringBuilder hexString = new StringBuilder();
		    for (byte b : bytes) {
		        String hex = Integer.toHexString(0xff & b);
		        if (hex.length() == 1) {
		            hexString.append('0');
		        }
		        hexString.append(hex);
		    }
		    return hexString.toString();
		}

		private byte[] stringToByteArray(String hex) {
			int numberChars = hex.length();
        
			byte[] bytes = new byte[numberChars / 2];
			for (int i = 0; i < numberChars; i += 2) {
            bytes[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        	}
        
        return bytes;
		}
		
		public static String generateCheckSumValue(String reqStr) {
		    byte[] bytes = reqStr.getBytes(StandardCharsets.US_ASCII);
		    CRC32 crc32 = new CRC32();
		    crc32.update(bytes);
		    long checksumvalue = crc32.getValue();
		    return Long.toString(checksumvalue);
		}
		
		
		public static String callPushWebService(String str,String clientCode){
			String xmlResponse="";
			try {
				
				 String url= ApplicationSession.getInstance().getMessage("aaple.sarkar.url");
				 //String url = "http://testcitizenservices.MahaITgov.in/Dept_Authentication.asmx";
				 URL obj = new URL(url);
				 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				 con.setRequestMethod("POST");
				 con.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
				 //String encKey="C6A85267FD7B63F14F9A787E21CE488097E8990F2648C005CB4F4E7C15268D29AB322B347962659F1AACB9F8EF66E6488C4D7375533BFFD48887ACBEB6E7088454B84F649B2C1FA186EBFCFA60C59B5AE3020E3EBDF4CB8344BCDBD184A5D7A71CD0FDFBE050482C431AF0F4C01B1E6998D8F57969D174142994B6E8F16CC724";
				 //String deptCode="CIDCO";
				 String xml = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> <soap12:Body> <GetParameterNew xmlns=\"http://tempuri.org/\"> <EncyKey>"+str+"</EncyKey> <DeptCode>"+clientCode+"</DeptCode> </GetParameterNew> </soap12:Body> </soap12:Envelope>";
				 con.setDoOutput(true);
				 DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				 wr.writeBytes(xml);
				 wr.flush();
				 wr.close();
				 String responseStatus = con.getResponseMessage();
				 System.out.println(responseStatus);
				 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				 String inputLine;
				 StringBuffer response = new StringBuffer();
				 while ((inputLine = in.readLine()) != null) {
				 response.append(inputLine);
				 }
				 in.close();
				 LOGGER.error("response:" + response.toString());
				 String string2 = response.toString();
				
				 List<String> output = getParameterResultFromXML(string2, "GetParameterNewResult");
				 
				 String[] strarray = new String[output.size()];
				 String[] array = output.toArray(strarray);
				
				 if(array.length>0){
				 LOGGER.error("Response Array is "+ array[0]);
				 xmlResponse=array[0];
				 }
				 
			} catch (Exception e) {
				LOGGER.error("Response Array is "+ e); 
				 }
			
			return xmlResponse;
			
		}
		
		public static Document loadXMLString(String response) throws Exception
		{
		    DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(response));

		    return db.parse(is);
		}

		public static List<String> getParameterResultFromXML(String response, String tagName) throws Exception {
		    Document xmlDoc = loadXMLString(response);
		    NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
		    List<String> ids = new ArrayList<String>(nodeList.getLength());
		    for(int i=0;i<nodeList.getLength(); i++) {
		        org.w3c.dom.Node x = nodeList.item(i);
		        ids.add(x.getFirstChild().getNodeValue());             
		        System.out.println(nodeList.item(i).getFirstChild().getNodeValue());
		    }
		    return ids;
		}
		
		
		public static String simpleTripleDes(String Data, String strKey, String striv) {
	        byte[] key = strKey.getBytes(StandardCharsets.UTF_8);
	        byte[] iv = striv.getBytes(StandardCharsets.UTF_8);
	        byte[] data = Data.getBytes(StandardCharsets.UTF_8);
	        byte[] enc;
	        try {
	            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DESede");
	            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
	            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
	            enc = cipher.doFinal(data);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	        return Base64.getEncoder().encodeToString(enc);
	    

	
		}
		
		
		
		public static String callPullWebService(String str,String clientCode){
			String xmlResponse="";
			try {
				
				 String url= ApplicationSession.getInstance().getMessage("aaple.sarkar.url");
				 //String url = "http://testcitizenservices.MahaITgov.in/Dept_Authentication.asmx";
				 URL obj = new URL(url);
				 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				 con.setRequestMethod("POST");
				 con.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
				 //String encKey="C6A85267FD7B63F14F9A787E21CE488097E8990F2648C005CB4F4E7C15268D29AB322B347962659F1AACB9F8EF66E6488C4D7375533BFFD48887ACBEB6E7088454B84F649B2C1FA186EBFCFA60C59B5AE3020E3EBDF4CB8344BCDBD184A5D7A71CD0FDFBE050482C431AF0F4C01B1E6998D8F57969D174142994B6E8F16CC724";
				 //String deptCode="CIDCO";
				 //String xml = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> <soap12:Body> <GetParameterNew xmlns=\"http://tempuri.org/\"> <EncyKey>"+str+"</EncyKey> <DeptCode>"+clientCode+"</DeptCode> </GetParameterNew> </soap12:Body> </soap12:Envelope>";
				 
				 String xml = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> <soap12:Body> <SetAppStatus xmlns=\"http://tempuri.org/\"> <EncyKey>"+str+"</EncyKey> <DeptCode>"+clientCode+"</DeptCode> </SetAppStatus> </soap12:Body> </soap12:Envelope>";

				 con.setDoOutput(true);
				 DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				 wr.writeBytes(xml);
				 wr.flush();
				 wr.close();
				 String responseStatus = con.getResponseMessage();
				 System.out.println(responseStatus);
				 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				 String inputLine;
				 StringBuffer response = new StringBuffer();
				 while ((inputLine = in.readLine()) != null) {
				 response.append(inputLine);
				 }
				 in.close();
				 LOGGER.error("response:" + response.toString());
				 String string2 = response.toString();
				
				 List<String> output = getParameterResultFromXML(string2, "SetAppStatusResult");
				 
				 String[] strarray = new String[output.size()];
				 String[] array = output.toArray(strarray);
				
				 if(array.length>0){
				 LOGGER.error("Response Array is "+ array[0]);
				 xmlResponse=array[0];
				 }
				 
			} catch (Exception e) {
				LOGGER.error("Response Array is "+ e); 
				 }
			
			return xmlResponse;
			
		}
}
