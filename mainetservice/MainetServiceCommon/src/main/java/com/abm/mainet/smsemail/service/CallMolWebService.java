package com.abm.mainet.smsemail.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CallMolWebService
{
	
	static {
   //for localhost testing only
   javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
           new javax.net.ssl.HostnameVerifier() {

       @Override
       public boolean verify(String hostname,
               javax.net.ssl.SSLSession sslSession) {
           if (hostname.equals("push3.aclgateway.com")) {
               return true;
           }
           return false;
       }
   });
}
	private final static Logger LOGGER = Logger.getLogger(CallMolWebService.class);
    public int sendPushSMSHttpsUrl(final String username, final String password, final String msgBody, final String mobileNo, final String smsId,final String pushId, final String vendorID, final String senderID, final String flash, final String scheduleDate, final String scheduleTime,String mahaTemplateId, String url2) {
        final String route = "4";
        final String unicode = "0";
        // final String urlParameter = "authkey=" + vendorID + "&mobiles=" + mobileNo + "&message=" + msgBody + "&sender=" + senderID + "&route=" + route + "&unicode=" + unicode + "&DLT_TE_ID=" + mahaTemplateId + "&dev_mode=" + devMode;
        final String urlParameter = "enterpriseid=" + username + "&subEnterpriseid=" + username +"&pusheid=" +pushId  + "&pushepwd=" + password +  "&msisdn=" + mobileNo + "&sender=" + senderID  + "&msgtext=" + msgBody +"&dtm="+mahaTemplateId;
        
        LOGGER.info("SMS URL ================"+urlParameter);
        final byte[] postData = urlParameter.getBytes(StandardCharsets.UTF_8);
        final int postDataLength = postData.length;
        // final String MainURL = "https://control.msg91.com/api/sendhttp.php";
         String MainURL = "https://push3.aclgateway.com/servlet/com.aclwireless.pushconnectivity.listeners.TextListener";
         if(StringUtils.isNotBlank(url2))
        	 MainURL=url2;
        LOGGER.info("Main URL ================"+MainURL);
        HttpURLConnection http = null;
        try {
            final URL url = new URL(MainURL);
            http = (HttpURLConnection)url.openConnection();
            http.setDoOutput(true);
            http.setInstanceFollowRedirects(false);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            http.setRequestProperty("charset", "UTF8");
            http.setUseCaches(false);
            final DataOutputStream wr = new DataOutputStream(http.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();
            
        }
        catch (Exception e) {
        	LOGGER.error("RunJob sendPushSMSHttpsUrl  " + e);
			e.printStackTrace();
           // return 404;
        }
        finally {
        	int statusCode =404;
			try {
				statusCode = http.getResponseCode();
			} catch (IOException e) {
				LOGGER.error("RunJob sendPushSMSHttpsUrl inside finally " + e);
				e.printStackTrace();
			}
            
            return statusCode;
        }
    }
    
    public int sendPushSMSHttpUrl(final String username, final String password,final String pushId, final String msgBody, final String mobileNo, final String smsId, final String vendorID, final String senderID, final String flash, final String scheduleDate, final String scheduleTime,String mahaTemplateId, String url2) {
        final String route = "4";
        final String unicode = "0";
        final int devMode=1;
        final String urlParameter = "authkey=" + vendorID + "&mobiles=" + mobileNo + "&message=" + msgBody + "&sender=" + senderID + "&route=" + route + "&unicode=" + unicode + "&DLT_TE_ID="+ mahaTemplateId + "&dev_mode=" + devMode;
        LOGGER.info("URL PARAMETER================"+urlParameter);
        
        
        final byte[] postData = urlParameter.getBytes(StandardCharsets.UTF_8);
        final int postDataLength = postData.length;
         String MainURL = "https://push3.aclgateway.com/servlet/com.aclwireless.pushconnectivity.listeners.TextListener";
         if(StringUtils.isNotBlank(url2))
        	 MainURL=url2;
         HttpURLConnection http = null;
        try {
        	
            final URL url = new URL(MainURL);
            LOGGER.info("SMS URL ================"+url);
             http = (HttpURLConnection)url.openConnection();
            http.setDoOutput(true);
            http.setInstanceFollowRedirects(false);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            http.setRequestProperty("charset", "UTF8");
            http.setUseCaches(false);
            final DataOutputStream wr = new DataOutputStream(http.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();
          //  final int statusCode = http.getResponseCode();
           
           // return statusCode;
        }
        catch (Exception e) {
           
        	LOGGER.error("RunJob sendPushSMSHttpUrl  " + e);
			e.printStackTrace();
           // return 404;
        }
        finally {
        	int statusCode =404;
			try {
				statusCode = http.getResponseCode();
			} catch (IOException e) {
				LOGGER.error("RunJob sendPushSMSHttpsUrl inside finally " + e);
				e.printStackTrace();
			}
            
            return statusCode;
        }
    }
  public int sendPushSMSHttpsUrlNew(final String username, final String password, final String msgBody, final String mobileNo, final String smsId, final String vendorID, final String senderID, final String flash, final String scheduleDate, final String scheduleTime,String mahaTemplateId) {
    	
    	//final String urlParameter = "enterpriseid=" + username + "&subEnterpriseid=" + username +"&pusheid=" +pushId  + "&pushepwd=" + password +  "&msisdn=" + mobileNo + "&sender=" + senderID  + "&msgtext=" + msgBody +"&dtm="+mahaTemplateId;
    	// final String MainURL = "https://otp2.aclgateway.com/OTP_ACL_Web/OtpRequestListener";
        
        final String urlParameter = "appid=" + username + "&userId=" + username  + "&pass=" + password + "&contenttype=" + 1 + "&from=" + senderID  + "&to=" + mobileNo + "&text=" + msgBody +"&dtm="+mahaTemplateId+"&alert="+1+"&selfid="+true+"&dlrreq="+true+"&intflag="+false;
        LOGGER.info("URL PARAMETER================"+urlParameter);
        final byte[] postData = urlParameter.getBytes(StandardCharsets.UTF_8);
        final int postDataLength = postData.length;
        final String MainURL = "https://push3.aclgateway.com/servlet/com.aclwireless.pushconnectivity.listeners.TextListener";
        try {
            final URL url = new URL(MainURL);
            LOGGER.info("SMS URL ================"+url);
            final HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setDoOutput(true);
            http.setInstanceFollowRedirects(false);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            http.setRequestProperty("charset", "UTF8");
            http.setUseCaches(false);
            final DataOutputStream wr = new DataOutputStream(http.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();
            final int statusCode = http.getResponseCode();
            LOGGER.info("Status Code *** "+statusCode);
            return statusCode;
        }
        catch (Exception e) {
        	 LOGGER.error("Exception In RunJob sendPushSMSHttpsUrl***" + e.toString());
			 e.printStackTrace();
            return 404;
        }
    }
       
    public int sendPushSMSHttpUrlNew(final String username, final String password, final String msgBody, final String mobileNo, final String smsId, final String vendorID, final String senderID, final String flash, final String scheduleDate, final String scheduleTime,String mahaTemplateId) {
    	
    	//final String urlParameter = "enterpriseid=" + username + "&subEnterpriseid=" + username +"&pusheid=" +pushId  + "&pushepwd=" + password +  "&msisdn=" + mobileNo + "&sender=" + senderID  + "&msgtext=" + msgBody +"&dtm="+mahaTemplateId;
    	// final String MainURL = "https://otp2.aclgateway.com/OTP_ACL_Web/OtpRequestListener";
        
        final String urlParameter = "appid=" + username + "&userId=" + username  + "&pass=" + password + "&contenttype=" + 1 + "&from=" + senderID  + "&to=" + mobileNo + "&text=" + msgBody +"&dtm="+mahaTemplateId+"&alert="+1+"&selfid="+true+"&dlrreq="+true+"&intflag="+false;
        LOGGER.info("URL PARAMETER================"+urlParameter);
        final byte[] postData = urlParameter.getBytes(StandardCharsets.UTF_8);
        final int postDataLength = postData.length;
        final String MainURL = "https://push3.aclgateway.com/servlet/com.aclwireless.pushconnectivity.listeners.TextListener";
        try {
            final URL url = new URL(MainURL);
            LOGGER.info("SMS URL ================"+url);
            final HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setDoOutput(true);
            http.setInstanceFollowRedirects(false);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            http.setRequestProperty("charset", "UTF8");
            http.setUseCaches(false);
            final DataOutputStream wr = new DataOutputStream(http.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();
            final int statusCode = http.getResponseCode();
            LOGGER.info("Status Code ***** "+statusCode);
            return statusCode;
        }
        catch (Exception e) {
        	 LOGGER.error("Exception In RunJob sendPushSMSHttpsUrl*****" + e.toString());
			 e.printStackTrace();
            return 404;
        }
    }
}