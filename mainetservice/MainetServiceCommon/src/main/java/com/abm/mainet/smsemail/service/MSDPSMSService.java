package com.abm.mainet.smsemail.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.smsemail.dto.SMSRequestDto;

@Service("msdpSMS")
public class MSDPSMSService implements SmsGatewayStrategy {

    private final static Logger LOGGER = Logger.getLogger(MSDPSMSService.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.smsemail.service.SmsGatewayStrategy#sendSMS(java.lang.String, java.lang.String, int)
     */
    @SuppressWarnings("deprecation")
	@Override
    public String sendSMS(String message, String mobileNumber, int languageId, String templateId) {
        ApplicationSession applicationSession = ApplicationSession.getInstance();
        String response = null;
        String UserName = applicationSession.getMessage("UserName");
        String PassWord = applicationSession.getMessage("Password");
        String SecureKey = applicationSession.getMessage("SecureKey");
        String SenderId = applicationSession.getMessage("SenderId");
        //Added by sunita for mahaonline SMS gateway
        String smsEmailMsg=message;
        
        if ((UserName != null && !UserName.isEmpty()) && (PassWord != null && !PassWord.isEmpty())
                && (SecureKey != null && !SecureKey.isEmpty()) && (SenderId != null && !SenderId.isEmpty())) {
            String finalmessage = "";
            String serviceType = "singlemsg";

            if (languageId != 1) {
                String sss = "";
                char ch = 0;
                for (int i = 0; i < message.length(); i++) {
                    ch = message.charAt(i);
                    int j = (int) ch;
                    sss = "&#" + j + ";";
                    finalmessage = finalmessage + sss;
                }
                message = finalmessage;
                serviceType = "unicodemsg";
            }
            String encryptedPassword;
            String genratedhashKey = hashGenerator(UserName, SenderId, message, SecureKey);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse httpResponse;
            if(null!=applicationSession.getMessage("app.name") && mobileNumber!=null && !mobileNumber.isEmpty() && (applicationSession.getMessage("app.name").equals(MainetConstants.APP_NAME.TSCL) || applicationSession.getMessage("app.name").equals(MainetConstants.APP_NAME.PSCL) || applicationSession.getMessage("app.name").equals(MainetConstants.ENV_SFAC))){
            	//MSG91 SMS code implementation for TSCL,PSCL,SFAC
                  try {
                	  SMSRequestDto smsreq=new SMSRequestDto();
					
					/*
					 * List<SMS> listsms=new ArrayList<>(); SMS sms=new SMS();
					 */
                	  String[] arrOfMob = mobileNumber.split(MainetConstants.operator.COMMA);
                	  String[] arrOfMessage = smsEmailMsg.split(MainetConstants.operator.COMMA);
                	  StringBuilder mobileNo=new StringBuilder();
                	  for (String mobile : arrOfMob) {  
                		  mobileNo.append("91" + mobile + MainetConstants.operator.COMMA);
                	  }
                	  mobileNo.deleteCharAt(mobileNo.length()-1);  
                	  smsreq.setMobiles(mobileNo.toString().trim());
                	  smsreq.setTemplate_id(templateId);
                	  if(arrOfMessage[0]!=null)
                	  smsreq.setVar1(arrOfMessage[0].toString().trim());
                	  if(arrOfMessage[1]!=null)
                	  smsreq.setVar2(arrOfMessage[1].toString().trim());
                	  LOGGER.info("templateId== "+templateId);
					/*
					 * smsreq.setCountry(applicationSession.getMessage("country"));
					 * smsreq.setSender(applicationSession.getMessage("sender"));
					 * smsreq.setRoute(applicationSession.getMessage("route"));
					 * smsreq.setUnicode(applicationSession.getMessage("unicode"));
					 * sms.setMessage(message); sms.setTo(new String[]{mobileNumber});
					 * listsms.add(sms); smsreq.setSms(listsms);
					 */
                	  String uri=applicationSession.getMessage("msg91.sms.uri");
                	  LOGGER.info("URL== "+uri);
                	  RestTemplate restReq=new RestTemplate();
                	  HttpHeaders headers = new HttpHeaders();
                	  headers.add("authkey", applicationSession.getMessage("msg91.sms.authkey"));
                	  LOGGER.info("authkey== "+applicationSession.getMessage("msg91.sms.authkey"));
                	  headers.add("Content-Type", "application/json");
                	  HttpEntity<SMSRequestDto>  httpentity=new HttpEntity<SMSRequestDto>(smsreq,headers);
             
                	 ResponseEntity<String> resp = restReq.exchange(new URI(uri),HttpMethod.POST, httpentity, String.class);
                	 LOGGER.info("response== "+resp.getBody());
                	 if(resp!=null) {
                	   response = "SMS Send Successfully Done";
                	 }
                	 LOGGER.info("URL== "+uri);
                	
                	  
                
      			} 
                catch (Exception e) {
                    LOGGER.error(e);
                }   	
            }else if(null!=applicationSession.getMessage("app.name") && applicationSession.getMessage("app.name").equals(MainetConstants.APP_NAME.ASCL)){
            	  URI  uri=null;
                  try {
      				 uri = new URIBuilder()
      				        .setScheme(applicationSession.getMessage("http.URL"))
      				        .setHost(applicationSession.getMessage("URL"))
      				        .setParameter(applicationSession.getMessage("key.username"), applicationSession.getMessage("value.username"))
      				        .setParameter(applicationSession.getMessage("key.password"), applicationSession.getMessage("value.password"))
      				        .setParameter(applicationSession.getMessage("key.to"), mobileNumber)
      				        .setParameter(applicationSession.getMessage("key.from"), applicationSession.getMessage("value.from"))
      				        .setParameter(applicationSession.getMessage("key.text"), message)
      				        .build();
      				 
      				  HttpGet methodforGet=new HttpGet(uri); 
      				 LOGGER.info("URI is ....."+uri);
      				  httpResponse = client.execute(methodforGet);      				  
      				 int statusCode = httpResponse.getStatusLine().getStatusCode();
                     if (statusCode == 200) {
                         response = "SMS Send Successfully Done";
                     }
      				 
      			} catch (URISyntaxException e1) {
      				 LOGGER.error(e1);
      				 LOGGER.error("URI is not properly build....."+uri);
      			}catch (IOException e) {
                    LOGGER.error(e);
                } catch (Exception e) {
                    LOGGER.error(e);
                }
            	
			} else if(null!=applicationSession.getMessage("app.name") && applicationSession.getMessage("app.name").equals(MainetConstants.APP_NAME.DSCL)) {
	          	  //URI  uri=null;
				// webline sms gateway starts -->
				String weblineUrl = applicationSession.getMessage("webline.url");
				String weblineUsername = applicationSession.getMessage("webline.username");
				String weblineSendername = applicationSession.getMessage("webline.sendername");
				String weblineSmstype = applicationSession.getMessage("webline.smstype");
				String weblineApikey = applicationSession.getMessage("webline.apikey");
				String weblinePeid = applicationSession.getMessage("webline.peid");
				String weblineTemplateid = templateId;

				RestTemplate webLineReq = new RestTemplate();
				HttpHeaders webLineHeaders = new HttpHeaders();
				String msgBody = smsEmailMsg;
				webLineHeaders.add("Content-Type", "application/json");
				StringBuilder webMailUri=new StringBuilder();
				HttpEntity<String> webLinbeHttpentity = new HttpEntity<>(webLineHeaders);
				/*
				 * webMailUri = weblineUrl + "?username=" + weblineUsername + "&message=" +
				 * msgBody + "&sendername=" + weblineSendername + "&smstype=" + weblineSmstype +
				 * "&numbers=" + mobileNumber + "&apikey=" + weblineApikey + "&peid=" +
				 * weblinePeid + "&templateid=" + weblineTemplateid;
				 */
				 webMailUri.append(weblineUrl+"?username="+weblineUsername+"&password="+weblineApikey+"&sender="+weblineSendername+"&to="+mobileNumber+"&message="+msgBody+"&format=json"+"&pe_id="+weblinePeid+"&template_id="+weblineTemplateid+"&reqid=1");//&msgtype=unicode)
			        if (languageId != 1) {
			        	webMailUri.append("&msgtype=unicode");	
			        }
				LOGGER.info("webMailUri :- " + webMailUri.toString());
				try {
					ResponseEntity<String> resp = webLineReq.exchange(webMailUri.toString(), HttpMethod.GET, webLinbeHttpentity,
							String.class);
					response = "SMS Send Successfully Done";
					LOGGER.info("response :- " + response);
				} catch (Exception e) {
					LOGGER.error("Exception occured in Send SMS Services " + e);
				}
				// webline sms gateway ends -->
	        }
			else if (null != applicationSession.getMessage("app.name")
					&& applicationSession.getMessage("app.name").equals(MainetConstants.APP_NAME.SKDCL)) {
				LOGGER.info(" start Mahaonline sms gateway ");
				CallMolWebService sendSMS = new CallMolWebService();
				//Changed by Sunita for sms gateway
				//String msgBody = message;
				String msgBody = smsEmailMsg;
				
				String mobileNo = mobileNumber;
				String url=ApplicationSession.getInstance().getMessage("mahaonline.url");
				String userName = ApplicationSession.getInstance().getMessage("mahaonline.userName");
				String passWord = ApplicationSession.getInstance().getMessage("mahaonline.password");
				String smsId = ApplicationSession.getInstance().getMessage("mahaonline.smsId");
				String vendorID = ApplicationSession.getInstance().getMessage("mahaonline.vendorid");
				String senderName = ApplicationSession.getInstance().getMessage("mahaonline.senderName");
				String flash = ApplicationSession.getInstance().getMessage("mahaonline.flash");
				String scheduleDate = ApplicationSession.getInstance().getMessage("mahaonline.scheduleDate");
				String scheduleTime = ApplicationSession.getInstance().getMessage("mahaonline.scheduleTime");

				LOGGER.info("msgBody:-" + msgBody + " MobileNo:-" + mobileNo + " UserName:-" + userName + " PassWord:-"
						+ passWord + " SmsId:-" + smsId + " VendorID:-" + vendorID + " SenderName:-" + senderName
						+ " Flash:-" + flash + " ScheduleDate:-" + scheduleDate + " ScheduleTime:-" + scheduleTime + " templateId:-" + templateId);
				try {
					int responseCode = sendSMS.sendPushSMSHttpsUrlNew(userName, passWord, msgBody, mobileNo, smsId,
							vendorID, senderName, flash, scheduleDate, scheduleTime,templateId);
					if (responseCode == 200) {
						response = "SMS Send Successfully Done";
						LOGGER.info("statusCode for mahaonline :" + responseCode);
					} else if (responseCode == 404) {
						responseCode = sendSMS.sendPushSMSHttpUrlNew(userName, passWord, msgBody, mobileNo, smsId,
								vendorID, senderName, flash, scheduleDate, scheduleTime,templateId);
						LOGGER.info("statusCode for mahaonline :" + responseCode);
					}
				} catch (Exception e) {
					LOGGER.error("Exception occured in Send SMS Services " + e);
				}
			}else if(null!=applicationSession.getMessage("app.name")  && (applicationSession.getMessage("app.name").equals(MainetConstants.APP_NAME.TCP))){
            	//TCP SMS code implementation 
			try {
				SMSRequestDto smsTokenreq = new SMSRequestDto();
				smsTokenreq.setUserId(applicationSession.getMessage("tcp_sms_userId"));
				smsTokenreq.setTpUserId(applicationSession.getMessage("tcp_sms_tpUserId"));
				smsTokenreq.setEmailId(applicationSession.getMessage("tcp_sms_emailId"));
				RestTemplate restToken = new RestTemplate();
				HttpHeaders headersToken = new HttpHeaders();
				headersToken.add("access_key", applicationSession.getMessage("tcp_sms_access_key"));
				headersToken.add("secret_key", applicationSession.getMessage("tcp_sms_secret_key"));
				headersToken.add("Content-Type", "application/json");
				HttpEntity<SMSRequestDto> httpentity1 = new HttpEntity<SMSRequestDto>(smsTokenreq, headersToken);
				ResponseEntity<String> respToken = restToken.exchange(new URI(applicationSession.getMessage("tcp_sms_tokenUrl")), HttpMethod.POST,
						httpentity1, String.class);
				String token = null;
				if (respToken != null && respToken.getStatusCode() == HttpStatus.OK) {
					JSONObject jsonObject = new JSONObject(respToken.getBody());
					java.util.Map<String, String> myMap = new java.util.HashMap<>();
					myMap.put(jsonObject.getString("Key"), jsonObject.getString("Value"));
					token = (String) myMap.get("1");
					if (token != null) {
						SMSRequestDto smsreq = new SMSRequestDto();
						smsreq.setMobileNo(mobileNumber);
						smsreq.setTemplateId(templateId);
						LOGGER.info("templateId== " + templateId);
						smsreq.setContent(message);
						smsreq.setPurpose(applicationSession.getMessage("tcp_sms_purpose"));
						smsreq.setTokenId(token);
						smsreq.setUserloginId(applicationSession.getMessage("tcp_sms_UserLoginId"));
						smsreq.setModuleId(applicationSession.getMessage("tcp_sms_moduleId"));
						String uri = applicationSession.getMessage("tcp_sms_sendUrl");
						LOGGER.info("URL== " + uri);
						RestTemplate restReq = new RestTemplate();
						HttpHeaders headers = new HttpHeaders();
						headers.add("Content-Type", "application/json");
						HttpEntity<SMSRequestDto> httpentity = new HttpEntity<SMSRequestDto>(smsreq, headers);
						ResponseEntity<String> resp = restReq.exchange(new URI(uri), HttpMethod.POST, httpentity,String.class);
						LOGGER.info("response== " + resp.getBody());
						if (resp != null) {
							response = "SMS Send Successfully Done";
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error(e);
			}
            }else {
            	
            	try {
            		 HttpPost method = new HttpPost(applicationSession.getMessage("URL")); 
                    encryptedPassword = MD5(PassWord);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("mobileno", mobileNumber));
                    params.add(new BasicNameValuePair("senderid", SenderId));
                    params.add(new BasicNameValuePair("content", message));
                    params.add(new BasicNameValuePair("smsservicetype", serviceType));
                    params.add(new BasicNameValuePair("username", UserName));
                    params.add(new BasicNameValuePair("password", encryptedPassword));
                    params.add(new BasicNameValuePair("key", genratedhashKey));
                    params.add(new BasicNameValuePair("templateid", templateId));
                    method.setEntity(new UrlEncodedFormEntity(params));
                    LOGGER.info("msgBody:-" + message + " MobileNo:-" + mobileNumber + " UserName:-" + UserName + " PassWord:-"
    						+ encryptedPassword  +  " key:-" + genratedhashKey
    						 +" templateId:-" + templateId+"  serviceType"+serviceType+"  senderid"+SenderId);
                    httpResponse = client.execute(method);
                    BufferedReader bf=new BufferedReader( 
    				new InputStreamReader(httpResponse.getEntity().getContent())); 
    				String line=""; 
    				while((line=bf.readLine())!=null){
                	LOGGER.error("response from sms provider: " + line);
    				}
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        response = "SMS Send Successfully Done";
                    }
                } catch (IOException e) {
                    LOGGER.error(e);
                } catch (NoSuchAlgorithmException e) {
                    LOGGER.error(e);
                } catch (Exception e) {
                    LOGGER.error(e);
                }
            }
            
        }
        return response;

    }

    protected static String hashGenerator(String userName, String senderId, String content, String secureKey) {
    	LOGGER.info("UserName  :"+userName+"  SenderId  "+senderId+"   content "+content+"  secureKey  "+secureKey );
        StringBuffer finalString = new StringBuffer();
        finalString.append(userName.trim()).append(senderId.trim()).append(content.trim()).append(secureKey.trim());
        String hashGen = finalString.toString();
        StringBuffer sb = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(hashGen.getBytes());
            byte byteData[] = md.digest();
            sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
        }
        LOGGER.info("Hash generated value  "+sb.toString());
        return sb.toString();
    }

    private static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] md5 = new byte[64];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5 = md.digest();
        return convertedToHex(md5);
    }

    private static String convertedToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfOfByte = (data[i] >>> 4) & 0x0F;
            int twoHalfBytes = 0;
            do {
                if ((0 <= halfOfByte) && (halfOfByte <= 9)) {
                    buf.append((char) ('0' + halfOfByte));
                } else {
                    buf.append((char) ('a' + (halfOfByte - 10)));
                }
                halfOfByte = data[i] & 0x0F;
            } while (twoHalfBytes++ < 1);
        }
        return buf.toString();
    }
}