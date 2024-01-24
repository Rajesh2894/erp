package com.abm.mainet.common.utility;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
public class TCPChallannRecocileService {

		public static Map<String, String> callSoapServiceForGrnDetails(String grnNo){
			String xmlResponse="";
			Map<String ,String> output=new HashMap<>();
			try {
				 String url = "https://egrashry.nic.in/grn_status.asmx";
				 URL obj = new URL(url);
				 HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				 con.setRequestMethod("POST");
				// con.setRequestProperty("Content-Type","text/xml; charset=utf-8");
				 con.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
				 //String encKey="C6A85267FD7B63F14F9A787E21CE488097E8990F2648C005CB4F4E7C15268D29AB322B347962659F1AACB9F8EF66E6488C4D7375533BFFD48887ACBEB6E7088454B84F649B2C1FA186EBFCFA60C59B5AE3020E3EBDF4CB8344BCDBD184A5D7A71CD0FDFBE050482C431AF0F4C01B1E6998D8F57969D174142994B6E8F16CC724";
				 //String deptCode="CIDCO";
				 String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				 		"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n" + 
				 		"  <soap12:Body>\r\n" + 
				 		"    <GetGrnDetails xmlns=\"http://tempuri.org/\">\r\n" + 
				 		"      <GRN>"+grnNo+"</GRN>\r\n" + 
				 		"    </GetGrnDetails>\r\n" + 
				 		"  </soap12:Body>\r\n" + 
				 		"</soap12:Envelope>";
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
				// LOGGER.error("response:" + response.toString());
				 String string2 = response.toString();
				 output = getParameterResultFromXML(string2, "GetGrnDetailsResult");
			} catch (Exception e) {
			e.printStackTrace(); 
				 }
			
			return output;
			
		}
		
		public static Document loadXMLString(String response) throws Exception
		{
		    DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(response));

		    return db.parse(is);
		}

		public static Map<String, String> getParameterResultFromXML(String response, String tagName) throws Exception {
		    Document xmlDoc = loadXMLString(response);
		    NodeList nodeList = xmlDoc.getElementsByTagName("*");
		   final Map<String, String> map=new HashMap<String, String>();
		    for(int i=0;i<nodeList.getLength(); i++) {
		        org.w3c.dom.Node x = nodeList.item(i);
		        if(nodeList.item(i).getFirstChild()!=null)
		        map.put(x.getNodeName(), nodeList.item(i).getFirstChild().getNodeValue());            
		    }
		    return map;
		}	
}

