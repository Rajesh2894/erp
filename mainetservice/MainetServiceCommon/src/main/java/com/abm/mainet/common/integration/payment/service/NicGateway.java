package com.abm.mainet.common.integration.payment.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.utility.Utility;


public class NicGateway implements PaymentStrategy {
	private static Logger LOG = Logger.getLogger(NicGateway.class);
	@Override
	public PaymentRequestDTO generatePaymentRequest(PaymentRequestDTO paymentRequestVO) throws FrameworkException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String validUpto = df.format(new Date());

		// todo challan year
		TreeMap<String, String> paramMap = new TreeMap<>();

		paramMap.put("DTO", paymentRequestVO.getDto());
		paramMap.put("STO", paymentRequestVO.getSto());
		paramMap.put("DDO", paymentRequestVO.getDdo());
		paramMap.put("Deptcode", paymentRequestVO.getDeptCode());
		paramMap.put("Applicationnumber", paymentRequestVO.getTxnid().toString());
		paramMap.put("ORDER_ID", paymentRequestVO.getTxnid().toString());
		paramMap.put("Fullname", paymentRequestVO.getApplicantName());
		paramMap.put("cityname", paymentRequestVO.getCityName());//
		paramMap.put("address", paymentRequestVO.getAddress());
		paramMap.put("PINCODE", paymentRequestVO.getPinCode());
		paramMap.put("officename", paymentRequestVO.getDto()+paymentRequestVO.getSto()+paymentRequestVO.getDdo());// DTO+STO+DDO
		paramMap.put("TotalAmount", paymentRequestVO.getDueAmt().toString());
		paramMap.put("ChallanYear",paymentRequestVO.getRecurrDay());
		paramMap.put("UURL",paymentRequestVO.getResponseUrl() );
		paramMap.put("ptype", paymentRequestVO.getPaymentType());//
		paramMap.put("bank", paymentRequestVO.getBankIdStr());
		paramMap.put("remarks",paymentRequestVO.getTrnRemarks());
		paramMap.put("securityemail", paymentRequestVO.getEmail());
		paramMap.put("securityphone", paymentRequestVO.getMobNo());
		paramMap.put("valid_upto", validUpto);
		paramMap.put("SCHEMENAME",paymentRequestVO.getSchemeCode());
		paramMap.put("SCHEMECOUNT", paymentRequestVO.getSchemeCount());
		paramMap.put("FEEAMOUNT1", paymentRequestVO.getDueAmt().toString());

		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			paramMap.forEach((key, value) -> params.put(key, Collections.singletonList(value)));

			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(paymentRequestVO.getPgUrl())
					.queryParams(params).build().encode();
			LOG.info("NicGateway Request URL >>>>>"+uriComponents);
			paymentRequestVO.setPayRequestMsg(uriComponents.toString());
		} catch (Exception e) {
			LOG.error("Error at the time of building URL for NIC Gateway"+e);
			e.printStackTrace();

		}
		return paymentRequestVO;
	}
	@Override
	public  Map<String, String> generatePaymentResponse(String responseString, Map<String, String> responseMap)
			throws FrameworkException {
		if(MapUtils.isNotEmpty(responseMap)) {
			if(StringUtils.equalsIgnoreCase(responseMap.get("status"), MainetConstants.PAYU_STATUS.SUCCESS)) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.SUCCESS);
			}else {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS, MainetConstants.PAYU_STATUS.FAIL);
			}
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.TRANS_ID,responseMap.get("GRN"));
			responseMap.put(MainetConstants.BankParam.TXN_ID, responseMap.get("Applicationnumber"));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAYDATE, Utility.dateToString(new Date()));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,MainetConstants.PG_SHORTNAME.NicGateway);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,responseMap.get("payment_type"));
			responseMap.put(MainetConstants.BankParam.HASH,responseMap.toString());
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.LANG));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,responseMap.get(MainetConstants.REQUIRED_PG_PARAM.PG_ID));
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,responseMap.get("CIN"));
			responseMap.put(MainetConstants.PaymentEntry.PAYMENT_TYPE_FLAG, responseMap.get("payment_type"));
			LOG.info("NicGateway Response  Map >>>>>"+responseMap);
		}
		
		return responseMap;
	}
	
	public static void main(String args[]) {
		NicGateway n = new NicGateway();
		PaymentRequestDTO p = new PaymentRequestDTO();
		BigDecimal d=new BigDecimal("1");
		System.out.println(d.toString());
		p.setPgUrl("https://egrashry.nic.in/webpages/EgEChallan_Excise.aspx");
		//n.generatePaymentRequest(p);

	}
}
