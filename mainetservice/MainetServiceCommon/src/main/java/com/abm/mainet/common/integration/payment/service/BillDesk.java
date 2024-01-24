package com.abm.mainet.common.integration.payment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.utility.EncryptionAndDecryption;

public class BillDesk implements PaymentStrategy  {
	protected Logger LOG = Logger.getLogger(BillDesk.class);
	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {
        try {       	 
       String 	billDeskMsg = paymentRequestVO.getMerchantId()+"|"+paymentRequestVO.getTxnid()+
    		         "|"+MainetConstants.CommonConstants.NA+"|"+paymentRequestVO.getDueAmt()+"|"+
        			MainetConstants.CommonConstants.NA+"|"+MainetConstants.CommonConstants.NA+"|"
    		        +MainetConstants.CommonConstants.NA+"|"+paymentRequestVO.getTrnCurrency()+"|"
    		        +MainetConstants.CommonConstants.NA+"|"+paymentRequestVO.getTypeField1()+"|"
    		        +paymentRequestVO.getSecurityId()+"|"+MainetConstants.CommonConstants.NA+"|"
        			+MainetConstants.CommonConstants.NA+"|"+paymentRequestVO.getTypeField2()
        			+"|"+paymentRequestVO.getEmail()+"|"+paymentRequestVO.getMobNo()+"|"+paymentRequestVO.getApplicationId()      			
        			+"|"+( (paymentRequestVO.getAddField4() !=null)? paymentRequestVO.getAddField4() :MainetConstants.CommonConstants.NA  )
        			+"|"+( (paymentRequestVO.getAddField5() !=null)? paymentRequestVO.getAddField5() :MainetConstants.CommonConstants.NA  )
        			+"|"+( (paymentRequestVO.getAddField6() !=null)? paymentRequestVO.getAddField6() :MainetConstants.CommonConstants.NA  )
        			+"|"+( (paymentRequestVO.getAddField7() !=null)? paymentRequestVO.getAddField7() :MainetConstants.CommonConstants.NA  )
        			+"|"+( (paymentRequestVO.getTypeField3() !=null)? paymentRequestVO.getTypeField3() :MainetConstants.CommonConstants.NA  );
        	//billDeskMsg = "BDSKUATY|A116155367083651|NA|2.00|NA|NA|NA|INR|NA|R|bdskuaty|NA|NA|F|A1|A2|A3|A4|A5|A6|A7|NA|405D294F85733F3D0BBA7245FFE299ED5FB33F22892910B2AA56FEB6AE48667B";
           String  checksum = EncryptionAndDecryption.HmacSHA256ForChecksumBillDesk(billDeskMsg,paymentRequestVO.getKey());
           billDeskMsg = billDeskMsg+"|"+checksum;
           paymentRequestVO.setPayRequestMsg(billDeskMsg);
           LOG.info("Request String for BillDesk "+billDeskMsg);
        } catch (final Exception exception) {
            throw new FrameworkException("Exception occur in genreatePaymentRequest in BillDesk Payment ...", exception);
        }
        return paymentRequestVO;
    }
	
	
	@Override
	public Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
            throws FrameworkException{
		LOG.info("start generatePaymentResponse in BillDesk class");
		try {
			//msg:BDSKUATY|2634|U4560001901792|093062|2327.00|456|424242|02|INR|PGSI|05-093062|NA|00000000.00|30-03-2021 18:33:42|0300|NA|bhagyashri.dongardive@abmindia.com|8888888888|Z02W02SDPR0012808|NA|NA|NA|NA|NA|PGS10001-Success|F773352CD8A1D0CAA338902D9362BA0F790ED6E435619D7B6A6D075B4EDB3255			
			//String responseMsg = responseMap.get(MainetConstants.BILLDESK.MSG);
			String checksumKey =responseMap.get(MainetConstants.REQUIRED_PG_PARAM.KEY);//this value need to pick form database or property file to compare the checksumKey from response string
			String responseMsg = responseString;
            String responseCheckSum=responseMsg.substring(responseMsg.lastIndexOf("|")+1, responseMsg.length());
            Map<String,String> map= new HashMap<>();
			String responseMsgWithOutChechSum = responseMsg.substring(0,responseMsg.lastIndexOf("|"));
			 String  ourChecksum = EncryptionAndDecryption.HmacSHA256ForChecksumBillDesk(responseMsgWithOutChechSum,checksumKey);
			 if(responseCheckSum.equals(ourChecksum)) {// validate checkhsum
				 String[] splitedresponse = responseMsg.split("|");	
				 List<String> mapKeys = new ArrayList<String>();
					mapKeys.add(MainetConstants.BILLDESK.MERCHANTID);
					mapKeys.add(MainetConstants.BILLDESK.CUSTOMERID);
					mapKeys.add(MainetConstants.BILLDESK.TXNREFERENCENO);
					mapKeys.add(MainetConstants.BILLDESK.BANKREFERENCENO);
					mapKeys.add(MainetConstants.BILLDESK.TXNAMOUNT);
					mapKeys.add(MainetConstants.BILLDESK.BANKID);
					mapKeys.add(MainetConstants.BILLDESK.BANKMERCHANTID);
					mapKeys.add(MainetConstants.BILLDESK.TXNTYPE);
					mapKeys.add(MainetConstants.BILLDESK.CURRENCYNAME);
					mapKeys.add(MainetConstants.BILLDESK.ITEMCODE);
					mapKeys.add(MainetConstants.BILLDESK.SECURITYTYPE);
					mapKeys.add(MainetConstants.BILLDESK.SECURITYID);
					mapKeys.add(MainetConstants.BILLDESK.SECURITYPASSWORD);
					mapKeys.add(MainetConstants.BILLDESK.TXNDATE);
					mapKeys.add(MainetConstants.BILLDESK.AUTHSTATUS);
					mapKeys.add(MainetConstants.BILLDESK.SETTLEMENTTYPE);
					mapKeys.add(MainetConstants.BankParam.ADD_F1);
					mapKeys.add(MainetConstants.BankParam.ADD_F2);
					mapKeys.add(MainetConstants.BankParam.ADD_F3);
					mapKeys.add(MainetConstants.BankParam.ADD_F4);
					mapKeys.add(MainetConstants.BankParam.ADD_F5);
					mapKeys.add(MainetConstants.BankParam.ADD_F6);
					mapKeys.add(MainetConstants.BankParam.ADD_F7);
					mapKeys.add(MainetConstants.BILLDESK.ERRORSTATUS);
					mapKeys.add(MainetConstants.BILLDESK.ERRORDESCRIPTION);
					mapKeys.add(MainetConstants.BILLDESK.CHECKSUM);
					
					StringBuilder responsesplit = new StringBuilder();
					for(String ss : splitedresponse) {
						if(ss.equals("|")) {
							responsesplit.append(MainetConstants.operator.AMPERSENT);
						}else {
							responsesplit.append(ss);
						}
					}
					String[] splitedresponses = responsesplit.toString().split(MainetConstants.operator.AMPERSENT);
					LOG.info("bill desk splitedresponses by & operator :- ");
				 if(mapKeys.size() == splitedresponses.length) {
					 int cnt =  0;
					 for(String s :mapKeys) {
						 map.put(s, splitedresponses[cnt]);
						 cnt++;
					 }
					 LOG.info("bill desk in map before response :- "+map);
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.SUCCESS);
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,
							map.get(MainetConstants.BILLDESK.ERRORSTATUS) + ":"
									+ map.get(MainetConstants.EASYPAY_PARAM.RMK));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID,
							map.get(MainetConstants.BILLDESK.CUSTOMERID));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,
							map.get(MainetConstants.BILLDESK.TXNREFERENCENO));
					responseMap.put(MainetConstants.BankParam.TXN_ID, map.get(MainetConstants.BILLDESK.CUSTOMERID));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE,
							map.get(MainetConstants.BILLDESK.TXNDATE));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,
							map.get(MainetConstants.BILLDESK.ERRORDESCRIPTION));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,
							map.get(MainetConstants.BILLDESK.BANKREFERENCENO));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT,
							(map.get(MainetConstants.BILLDESK.TXNAMOUNT)));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT,
							(map.get(MainetConstants.BILLDESK.TXNAMOUNT)));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC, MainetConstants.PG_SHORTNAME.BILLDESK);
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE, MainetConstants.ONLINE);
					responseMap.put(MainetConstants.BankParam.HASH, responseMsg);
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,
							responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
							responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,
							map.get(MainetConstants.BILLDESK.CUSTOMERID));

					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2, map.get(MainetConstants.BankParam.ADD_F3));
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1, MainetConstants.REQUIRED_PG_PARAM.NA);
				 }else {
					 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.PAYU_STATUS.FAIL);
					 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,	"Response map cannot be empty");
						LOG.error("Response map cannot be empty for BillDesk");
						throw new FrameworkException("Response map cannot be empty"); 
				 }
				 
				
			 }else {
				 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.PAYU_STATUS.FAIL);
				 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,	"CheckSum not matched");
					LOG.error("CheckSum not matched for BillDesk");
					throw new FrameworkException("CheckSum not matched");
			 }
		} catch (Exception exp) {
			LOG.error(" Exception occur in generatePaymentResponse in BillDesk Payment", exp);
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
		LOG.info("bill desk response map:- "+responseMap);
		LOG.info("End generatePaymentResponse in BillDesk class");
		return responseMap;
	}
    
	@Override
	public  Map<String, String> generatePaymentRequestForStatusAPI(PaymentRequestDTO paymentRequestVO) throws FrameworkException,Exception{
   	 Map<String, String> responseMap=new HashMap<>();
  //customerId == 	paymentRequestVO.getApplicationId()
   	 String responseStatusAPI = paymentRequestVO.getRequestType()+paymentRequestVO.getMerchantId()+paymentRequestVO.getApplicationId()/*+paymentRequestVO.getDate()*/+paymentRequestVO.getChecksumKey();
   	 return responseMap;
   }

}
