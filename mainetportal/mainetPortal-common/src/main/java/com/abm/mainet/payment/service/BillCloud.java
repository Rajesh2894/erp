package com.abm.mainet.payment.service;

import java.util.HashMap;
import java.util.Map;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.EncryptionAndDecryption;
import com.abm.mainet.payment.dto.PaymentRequestDTO;

public class BillCloud implements PaymentStrategy {

	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {
		try {
			String checksum = paymentRequestVO.getTxnid() +"|"+ paymentRequestVO.getApplicationId()
					+"|"+ paymentRequestVO.getValidateAmount() +"|"+ paymentRequestVO.getKey();
			checksum = EncryptionAndDecryption.generateChecksumForBillCloud(checksum);

			StringBuilder url = new StringBuilder(paymentRequestVO.getPgUrl());
			url.append(MainetConstants.BILLCLOUD_PARAM.BILLER_ID + MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB
					+ paymentRequestVO.getMerchantId());
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.SERVICE_ID
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + paymentRequestVO.getServiceID());
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.FUND_ID
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + paymentRequestVO.getFundID());
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.CONSUMER_ID
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + paymentRequestVO.getApplicationId());
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.BILLER_TRANSACTION_ID
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + paymentRequestVO.getTxnid());
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.AMOUNT
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + paymentRequestVO.getValidateAmount());
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.RESPONSE_URL
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + paymentRequestVO.getResponseUrl());
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.EDITABLE
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + false);
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.NAME
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + paymentRequestVO.getApplicantName());
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.ADDRESS
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + MainetConstants.REQUIRED_PG_PARAM.NA);
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.CUSTOMFIELD1
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + MainetConstants.REQUIRED_PG_PARAM.NA);
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.CUSTOMFIELD2
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + MainetConstants.REQUIRED_PG_PARAM.NA);
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.CUSTOMFIELD3
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + MainetConstants.REQUIRED_PG_PARAM.NA);
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.CUSTOMFIELD4
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + MainetConstants.REQUIRED_PG_PARAM.NA);
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.CUSTOMFIELD5
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + MainetConstants.REQUIRED_PG_PARAM.NA);
			url.append(MainetConstants.operator.AMPERSAND + MainetConstants.BILLCLOUD_PARAM.CHECKSUM
					+ MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB + checksum);

			paymentRequestVO.setPayRequestMsg(url.toString());

		} catch (final Exception exception) {
			throw new FrameworkException("Exception occur in genreatePaymentRequest in Easypay Payment ...", exception);
		}
		return paymentRequestVO;
	}

	@Override
	public Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
			throws FrameworkException {

		try {

			String[] splitedresponse = responseString.split(MainetConstants.operator.AMPERSAND);

			Map<String, String> map = new HashMap<>();
			for (String value : splitedresponse) {
				String[] Pairresponse = value.split(MainetConstants.BILLCLOUD_PARAM.EQUALS_SYMB);
				map.put(Pairresponse[0].trim(), Pairresponse[1].trim());
			}

			if (!map.isEmpty()) {

				if (map.get(MainetConstants.REQUIRED_PG_PARAM.STATUS).equals(MainetConstants.BILLCLOUD_PARAM.SUCCESS_CODE)) {
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.SUCCESS);
				} else if (map.get(MainetConstants.REQUIRED_PG_PARAM.STATUS).equals(MainetConstants.BILLCLOUD_PARAM.FAIL_CODE)) {
					responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.FAIL);
				}
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR,map.get(MainetConstants.REQUIRED_PG_PARAM.STATUS) + ":" + map.get(MainetConstants.BILLCLOUD_PARAM.REASON));
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID, map.get(MainetConstants.BILLCLOUD_PARAM.CHANNELTRANSACTIONID));
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID, map.get(MainetConstants.BILLCLOUD_PARAM.TRANSACTIONID));
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, map.get(MainetConstants.BILLCLOUD_PARAM.TRANSACTIONDATE));
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SUCEESS_MSG,map.get(MainetConstants.BILLCLOUD_PARAM.REASON));
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,map.get(MainetConstants.BILLCLOUD_PARAM.TRANSACTIONID));
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.AMT, map.get(MainetConstants.BILLCLOUD_PARAM.AMOUNT));
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.NET_AMT, map.get(MainetConstants.BILLCLOUD_PARAM.NETAMOUNT));
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC, MainetConstants.PG_SHORTNAME.BILLCLOUD);
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE, map.get(MainetConstants.BILLCLOUD_PARAM.PAYMENTMODE));
			} else {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.FAIL);
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR, "Response Object is null");
				throw new FrameworkException("Response Object is null");
			}
		} catch (Exception exp) {
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.FAIL);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ERR_STR, "Error While parsing response object");
			exp.printStackTrace();
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE, MainetConstants.REQUIRED_PG_PARAM.NA);
		}
		return responseMap;
	}

}
