package com.abm.mainet.payment.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.AtomAES;
import com.abm.mainet.payment.service.PaymentStrategy;


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
			encVal = new AtomAES().encrypt(encString, key,
					salt);
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
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, resultMap.get("desc"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,resultMap.get("ipg_txn_id"));
		responseMap.put(MainetConstants.BankParam.TXN_ID, resultMap.get("mer_txn"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, Utility.dateToString(new Date()));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,resultMap.get("desc"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,resultMap.get("bank_txn"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, resultMap.get("amt"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, resultMap.get("amt"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,MainetConstants.PG_SHORTNAME.ATOMPAY);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,"Real Time");
		responseMap.put(MainetConstants.BankParam.HASH,responseMap.toString());
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORDER_ID,responseMap.get(responseMap.get("orderId")));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF1,resultMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF2,resultMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF2));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.UDF3,resultMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF3));
		LOG.info("End   generatePaymentResponse for ATOM");
		return responseMap;
	}
}
