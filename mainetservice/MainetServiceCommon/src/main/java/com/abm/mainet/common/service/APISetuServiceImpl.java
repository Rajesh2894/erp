package com.abm.mainet.common.service;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.integration.dto.DirectorDetails;
import com.abm.mainet.common.integration.dto.DirectorInfo;
import com.abm.mainet.common.integration.dto.MCACompany;
import com.abm.mainet.common.integration.dto.PanCard;
import com.abm.mainet.common.utility.ApplicationSession;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.util.Json;



@Service
public class APISetuServiceImpl implements IAPISetuService{
	
	private static Logger LOG = Logger.getLogger(APISetuServiceImpl.class);

	@Override
	public Map<String, String> verifyPanCard(PanCard root) {
		Map<String, String> response = new HashMap<String, String>();
		try {
			ObjectMapper mapper = new ObjectMapper();

			String json = mapper.writeValueAsString(root);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			LOG.info("PAN request dto mapped successfully..==> "+json);
			StringEntity entity = new StringEntity(json);

			CloseableHttpClient httpclient = getCloseableHttpClient();
			LOG.info("httpclient created successfully..");
			
		    //HttpPost httpPost = new HttpPost("https://apisetu.gov.in/certificate/v3/pan/pancr");
		    HttpPost httpPost = new HttpPost(ApplicationSession.getInstance().getMessage("api.setu.pan.verification.url"));
			LOG.info("HttpPost object created successfully..");

			httpPost.setHeader("accept", "application/json");
			httpPost.setHeader("content-type", "application/json");
			
			httpPost.setHeader("X-APISETU-APIKEY", ApplicationSession.getInstance().getMessage("api.setu.apikey"));
			httpPost.setHeader("X-APISETU-CLIENTID",ApplicationSession.getInstance().getMessage("api.setu.clientId"));
			
			//httpPost.setHeader("X-APISETU-APIKEY", "PDSHazinoV47E18bhNuBVCSEm90pYjEF");
			//httpPost.setHeader("X-APISETU-CLIENTID", "in.gov.tcpharyana");

			httpPost.setEntity(entity);
			LOG.info("Request Type: "+httpPost.getMethod());
			System.out.println("Request Type: "+httpPost.getMethod());

			//Executing the Get request
			HttpResponse httpresponse = httpclient.execute(httpPost);
			LOG.info("Executing httpresponse.. "+httpresponse.getEntity());

			System.out.println("Status Code: "+httpresponse.getStatusLine().getStatusCode());
			String content = EntityUtils.toString(httpresponse.getEntity());
			if(HttpStatus.OK.value() == httpresponse.getStatusLine().getStatusCode()) {
				response.put("result", "success");
				return response;
			}
			ObjectMapper obj = new ObjectMapper();
			LinkedHashMap<String,String> o = (LinkedHashMap<String, String>) obj.readValue(content, Object.class);
			String errorMsg= o.get("errorDescription");
			String errorMsgVal = errorMsg.substring(errorMsg.lastIndexOf("-") + 1);
			LOG.info("content************  ==> "+content);
			System.out.println("content************  ==> "+content);
			response.put("result", "fail");
			response.put("error", errorMsgVal);
			return response;
		}catch (Exception e) {
			LOG.error("Exception occured in getSOSCall --> ", e);
			e.printStackTrace();
			return response;
		}		
	}
	
	
	//@Override
	public boolean verifyMCA(String cin) {
		String content = getDeatailsByCIN(cin,ApplicationSession.getInstance().getMessage("api.setu.mca.company.details.url"));
		return false;
	}


	private String getDeatailsByCIN(String cin,String url) {
		try {
			CloseableHttpClient httpclient = getCloseableHttpClient();
			LOG.info("httpclient created successfully..");
			
			//Creating a HttpGet object
			
			HttpGet httpGet = new HttpGet(url+cin);
			//HttpGet httpGet = new HttpGet("https://apisetu.gov.in/mca/v1/companies/"+cin);
			LOG.info("HttpPost object created successfully..");

			httpGet.setHeader("accept", "application/json");
			httpGet.setHeader("content-type", "application/json");
			httpGet.setHeader("X-APISETU-APIKEY", ApplicationSession.getInstance().getMessage("api.setu.apikey"));
			httpGet.setHeader("X-APISETU-CLIENTID",ApplicationSession.getInstance().getMessage("api.setu.clientId"));

			LOG.info("Request Type: "+httpGet.getMethod());
			System.out.println("Request Type: "+httpGet.getMethod());

			//Executing the Get request
			HttpResponse httpresponse = httpclient.execute(httpGet);
			LOG.info("Executing httpresponse.. "+httpresponse.getEntity());

			System.out.println("Status Code: "+httpresponse.getStatusLine().getStatusCode());
			//Printing the status line
			//  System.out.println(httpresponse.getEntity());

			String content = EntityUtils.toString(httpresponse.getEntity());
			LOG.info("content************  ==> "+content);
			System.out.println("content************  ==> "+content);
			if(HttpStatus.OK.value() == httpresponse.getStatusLine().getStatusCode()) {
				return content;
			}else {
				content="";
				return content;
			}
			//return content;
		}catch (Exception e) {
			LOG.error("Exception occured in getSOSCall --> ", e);
			e.printStackTrace();
		}
		return cin;
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
			//LOG.error("KeyManagementException in creating http client instance", e);
		} catch (NoSuchAlgorithmException e) {
		//	LOG.error("NoSuchAlgorithmException in creating http client instance", e);
		} catch (KeyStoreException e) {
		//	LOG.error("KeyStoreException in creating http client instance", e);
		}
		return httpClient;
	}


	public static void main(String args[]) {
		  APISetuServiceImpl impl =new APISetuServiceImpl();
		  ObjectMapper mapper = new ObjectMapper();
	      //added a try catch block to ensure that our application works smoothly.
		  PanCard root=null;
		try {
			root = mapper.readValue(new File("C:\\Users\\RiteshPatil\\Downloads\\json2csharp\\simple.json.txt"), PanCard.class);
		    System.out.println(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          impl.verifyPanCard(root);
          //impl.verifyMCA("U74999MH2018PTC308726");
		  
	  }


	


	@Override
	public MCACompany fetchCompanyDetails(String cinNo) {
		String content = getDeatailsByCIN(cinNo,ApplicationSession.getInstance().getMessage("api.setu.mca.company.details.url"));
		ObjectMapper mapper =new ObjectMapper();
		MCACompany company = new MCACompany();
		if(content.isEmpty()){
			company.setApiStatus(false);	
			company.setErrorMsg(ApplicationSession.getInstance().getMessage("MCA.API.not.working"));
			return company;
		}
				
		try {
			company = mapper.readValue(content,MCACompany.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}


	@Override
	public DirectorDetails fetchDirectorDetails(String cinNo) {
		String content = getDeatailsByCIN(cinNo,ApplicationSession.getInstance().getMessage("api.setu.mca.director.details.url"));
		ObjectMapper mapper =new ObjectMapper();
		DirectorDetails details =new DirectorDetails();
		List<DirectorInfo> directorInfo =null;
		try {
			//directorInfo = mapper.readValue(content, new TypeReference<List<DirectorInfo>>(){});
			//details.setDirectorInfo(directorInfo);
			details = mapper.readValue(content,DirectorDetails.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return details;
	}
	

}

