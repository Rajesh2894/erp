package com.abm.mainet.common.integration.payment.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;

public class Easebuzz implements PaymentStrategy {

	protected Logger LOG = Logger.getLogger(Easebuzz.class);


	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {
		LOG.info("In generatePaymentRequest() in Easybuzz class Started-------------> ");
		
		// Generate hash value
		paymentRequestVO.setDueAmt(new BigDecimal(String.format("%.1f", paymentRequestVO.getDueAmt())));
		String hashInput = paymentRequestVO.getKey() + MainetConstants.operator.ORR + paymentRequestVO.getTxnid()
				+ MainetConstants.operator.ORR + paymentRequestVO.getDueAmt() + MainetConstants.operator.ORR
				+ paymentRequestVO.getProduction() + MainetConstants.operator.ORR + paymentRequestVO.getApplicantName()
				+ MainetConstants.operator.ORR + paymentRequestVO.getEmail() + MainetConstants.operator.ORR
				+ paymentRequestVO.getUdf1() + MainetConstants.operator.ORR + paymentRequestVO.getUdf2()
				+ MainetConstants.operator.ORR + paymentRequestVO.getUdf3() + MainetConstants.operator.ORR
				+ paymentRequestVO.getUdf4() + MainetConstants.operator.ORR + paymentRequestVO.getUdf5()
				+ MainetConstants.operator.ORR+paymentRequestVO.getUdf6() + MainetConstants.operator.ORR + paymentRequestVO.getUdf7() + MainetConstants.operator.ORR
				+ MainetConstants.operator.ORR + MainetConstants.operator.ORR + MainetConstants.operator.ORR
				+ paymentRequestVO.getSalt();
		LOG.info("In HashInput() -------------> "+hashInput);
		String hash = getHashValue("SHA-512", hashInput);
		paymentRequestVO.setHash(hash);
		LOG.info("In Hashoutput() -------------> "+hash);
		String reqString = MainetConstants.REQUIRED_PG_PARAM.KEY + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getKey() + MainetConstants.operator.AMPERSENT
				+ MainetConstants.REQUIRED_PG_PARAM.TRACK_ID + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getTxnid() + MainetConstants.operator.AMPERSENT
				+ MainetConstants.REQUIRED_PG_PARAM.AMT + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getDueAmt() + MainetConstants.operator.AMPERSENT
				+ MainetConstants.BankParam.PROD_INFO + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getProduction() + MainetConstants.operator.AMPERSENT
				+ MainetConstants.BankParam.FNAME + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getApplicantName() + MainetConstants.operator.AMPERSENT
				+ MainetConstants.BankParam.PHONE + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getMobNo() + MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.EMAIL
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getEmail()
				+ MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.SURL
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getSuccessUrl()
				+ MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.FURL
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getFailUrl()
				+ MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.HASH
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getHash()
				+ MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.UDF1
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getUdf1()
				+ MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.UDF2
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getUdf2()
				+ MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.UDF3
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getUdf3()
				+ MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.UDF4
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getUdf4()
				+ MainetConstants.operator.AMPERSENT + MainetConstants.BankParam.UDF5
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getUdf5()
				+ MainetConstants.operator.AMPERSENT  + MainetConstants.BankParam.UDF6
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getUdf6()
				+ MainetConstants.operator.AMPERSENT +MainetConstants.BankParam.UDF7
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getUdf7()
				+ MainetConstants.operator.AMPERSENT +"address1" + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getAddField1() + MainetConstants.operator.AMPERSENT + "address2"
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getAddField2()
				+ MainetConstants.operator.AMPERSENT + "city" + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getAddField3() + MainetConstants.operator.AMPERSENT + "state"
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + paymentRequestVO.getAddField4()
				+ MainetConstants.operator.AMPERSENT + "country" + MainetConstants.EASYPAY_PARAM.EQUALS_SYMB
				+ paymentRequestVO.getAddField5() + MainetConstants.operator.AMPERSENT + "zipcode"
				+ MainetConstants.EASYPAY_PARAM.EQUALS_SYMB + "";

		LOG.info("RequestBody Easybuzz :- " + reqString);
		try {
			String encResponse = executePost(paymentRequestVO.getPgUrl()+"payment/initiateLink", reqString,5000);
			LOG.info("ResponseBody Easybuzz :- " + encResponse);
			encResponse = encResponse.substring(1, encResponse.length() - 1).replace("\"", "");
			Map<String, String> myMap = new HashMap<String, String>();
			String[] pairs = encResponse.split(",");
			for (int i = 0; i < pairs.length; i++) {
				String pair = pairs[i];
				String[] keyValue = pair.split(":");
				myMap.put(keyValue[0].trim(), (keyValue[1]).trim());
			}
			if(myMap.get("status").equals("1")) {
			String finalUrl = paymentRequestVO.getPgUrl() + "pay/" + myMap.get("data");
			LOG.info("Final URL Easybuzz :- " + finalUrl);
			paymentRequestVO.setPgUrl(finalUrl);
			}
		} catch (NumberFormatException e) {
			LOG.error("Exception Easybuzz :- " + e);
		} catch (Exception e) {
			LOG.error("Exception Easybuzz :- " + e);
		}

		return paymentRequestVO;
	}

	
	@Override
	public  Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
			throws FrameworkException {
		LOG.info("Start   generatePaymentResponse for Easebuzz");
		
		 responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,
                 responseMap.get("easepayid"));
		LOG.info("End   generatePaymentResponse for Easebuzz");
		return responseMap;
	}
	
	
	public String executePost(final String targetURL, final String urlParameters, final int timeout) throws Exception {
		HttpURLConnection connection = null;
		String resStr = null;
		try {
			final URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					new StringBuilder().append(Integer.toString(urlParameters.getBytes().length)).toString());
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
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private String getHashValue(final String type, final String str) {

		final byte[] hashseq = str.getBytes();
		final StringBuffer hexString = new StringBuffer();
		try {
			final MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			final byte messageDigest[] = algorithm.digest();

			for (final byte element : messageDigest) {
				final String hex = Integer.toHexString(0xFF & element);
				if (hex.length() == 1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}
		} catch (final NoSuchAlgorithmException e) {
			throw new FrameworkException("Problem with generation of hash string!", e);
		}

		return hexString.toString();
	}

}
