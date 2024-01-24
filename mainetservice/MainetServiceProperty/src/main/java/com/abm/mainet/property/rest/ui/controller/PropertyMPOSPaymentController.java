package com.abm.mainet.property.rest.ui.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonBillResponseDto;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.PaymentDetailForMPOSDto;
import com.abm.mainet.common.dto.ReversalPaymentForMPOSDto;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.integration.payment.service.PaymentService;
import com.abm.mainet.common.master.service.ICommonEncryptionAndDecryption;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyDetailRequestDTO;
import com.abm.mainet.property.dto.PropertyDetailResponseDTO;
import com.abm.mainet.property.service.IPropertyTaxSelfAssessmentService;
import com.abm.mainet.property.service.PropertyBillPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
//@RequestMapping("/propertyMPOSPaymentController")
public class PropertyMPOSPaymentController {

	private static final Logger LOGGER = Logger.getLogger(PropertyMPOSPaymentController.class);
	
	@Resource
    private ICommonEncryptionAndDecryption commonEncryptionAndDecryption;
    
    
    @Autowired
    private IPropertyTaxSelfAssessmentService propertyTaxSelfAssessmentService;
    
    @Autowired
	private IChallanService iChallanService;
    
    @Autowired
	private PaymentService paymentService;

    @Autowired
    private PropertyBillPaymentService propertyBillPaymentService;
    
    @Autowired
	IFinancialYearService iFinancialYearService;

	@RequestMapping(value = "/updatePaymentInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public Map<String, String> updateBillDataForMPOS(@RequestBody PaymentDetailForMPOSDto mposDto,ServletRequest request) {
		Map<String, String> response = new HashMap<String, String>();
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String header = httpRequest.getHeader("X-req-hash");
			String convertDtoToString = commonEncryptionAndDecryption.convertDtoToString(mposDto);
			String inputHashCode = commonEncryptionAndDecryption.hashKeyForMpos(convertDtoToString);
			if(inputHashCode.equals(header)) {
				response =  propertyTaxSelfAssessmentService.updateBillDataForMPOS(mposDto);
			}else {
				response.put("status", "1");
				response.put("reason", "Hash code mismatch");
			}
		} catch (Exception e) {
			response.put("status", "1");
			response.put("reason", e.getMessage());
		}
		return response;
	}
	@RequestMapping(value = "/reversePaymentInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public Map<String, String> reversePaymentForMPOS(@RequestBody ReversalPaymentForMPOSDto mposDto,ServletRequest request){
		Map<String, String> response = new HashMap<String, String>();
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String header = httpRequest.getHeader("X-req-hash");
			String convertDtoToString = commonEncryptionAndDecryption.convertDtoToStringForReversePayment(mposDto);
			String inputHashCode = commonEncryptionAndDecryption.hashKeyForMpos(convertDtoToString);
			if(inputHashCode.equals(header)) {
				response = propertyTaxSelfAssessmentService.reversePaymentForMPOS(mposDto);
			}else {
				response.put("status", "1");
				response.put("reason", "Hash code mismatch");
			}
		} catch (Exception e) {
			response.put("status", "1");
			response.put("reason", e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/fetchBillInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public CommonBillResponseDto getBillOutstandingForMpos(@RequestBody String propertyNumber,ServletRequest request) {
		CommonBillResponseDto response = new CommonBillResponseDto();
		try {
			
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String header = httpRequest.getHeader("X-req-hash");
			String propArray[]=propertyNumber.split("=");
			if(StringUtils.isEmpty(propArray[1])) {
				response.setStatus(1);
				response.setReason("Property no should not be empty");
			}else {
				String inputHashCode = commonEncryptionAndDecryption.hashKeyForMpos(propertyNumber);
				if(inputHashCode.equals(header)) {
					response = propertyTaxSelfAssessmentService.getBillOutstandingForMpos(propArray[1]);
				}else {
					response.setStatus(1);
					response.setReason("Hash code mismatch");
				}
			}
			
		} catch (Exception e) {
			response.setStatus(1);
			response.setReason(e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "/fetchPropertyDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<PropertyDetailResponseDTO> getPropertyDeatilForMpos(@RequestBody PropertyDetailRequestDTO searchDTO,
			ServletRequest request) {
		List<PropertyDetailResponseDTO> response = new ArrayList<>();
		PropertyDetailResponseDTO error = new PropertyDetailResponseDTO();
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String header = httpRequest.getHeader("X-req-hash");
			String convertDtoToString = new ObjectMapper().writeValueAsString(searchDTO);
			String inputHashCode = commonEncryptionAndDecryption.hashKeyForMpos(convertDtoToString);
			if (inputHashCode.equals(header)) {
				if (StringUtils.isNotBlank(searchDTO.getMobileNo())) {
					response = propertyTaxSelfAssessmentService.getPropertyDeatilForMpos(searchDTO);
					if (response != null && !response.isEmpty()) {
						return response;
					} else {
						error.setStatus(1);
						error.setReason("No Property Found For Entered mobileNo");
					}

				} else {
					if (StringUtils.isNotBlank(searchDTO.getZone())
							&& StringUtils.isNotBlank(searchDTO.getWard())
							&& StringUtils.isNotBlank(searchDTO.getMohalla())
							&& StringUtils.isNotBlank(searchDTO.getHouseNo())) {
						response = propertyTaxSelfAssessmentService.getPropertyDeatilForMpos(searchDTO);
						if (response != null && !response.isEmpty()) {
							return response;
						} else {
							error.setStatus(1);
							error.setReason("No Property Found For Entered zone, ward, mohalla and houseNo");
						}

					} else {
						error.setStatus(1);
						error.setReason("Please Enter zone, ward, mohalla and houseNo");
					}
				}

			} else {
				error.setStatus(1);
				error.setReason("Hash code mismatch");
			}

		} catch (Exception e) {
			error.setStatus(1);
			error.setReason(e.getMessage());
		}
		if (error != null) {
			response.add(error);
		}
		return response;
	}
	
	@RequestMapping(value = "/getIpAddress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public Map<String, String> getIpAddress(ServletRequest servletRequest){
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		Map<String, String> response = new HashMap<String, String>();
		
		String XForwardedForipAddress = request.getHeader("X-Forwarded-For");
		response.put("X-Forwarded-For", XForwardedForipAddress);

		String ProxyClientipAddress = request.getHeader("Proxy-Client-IP");
		response.put("Proxy-Client-IP", ProxyClientipAddress);

		String WLProxyClientIPipAddress = request.getHeader("WL-Proxy-Client-IP");
		response.put("WL-Proxy-Client-IP", WLProxyClientIPipAddress);

		String HTTPCLIENTIPipAddress = request.getHeader("HTTP_CLIENT_IP");
		response.put("HTTP_CLIENT_IP", HTTPCLIENTIPipAddress);

		String HTTPXFORWARDEDFORipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		response.put("HTTP_X_FORWARDED_FOR", HTTPXFORWARDEDFORipAddress);
		
		String XRealIPipAddress = request.getHeader("X-Real-IP");
		response.put("X-Real-IP", XRealIPipAddress);
		

		String remoteipAddress = request.getRemoteAddr();
		response.put("Remote_Ip", remoteipAddress);
		
		String privateIpAddress = null;
		InetAddress IP = null;
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		}
		if (IP != null) {
			privateIpAddress = IP.getHostAddress();
			response.put("private Ip", privateIpAddress);
		}
		
		URL whatismyip = null;
		String publicIpAddress = null;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = null;
			in = new BufferedReader(new InputStreamReader(
	                whatismyip.openStream()));

			publicIpAddress = in.readLine();
			response.put("public Ip", publicIpAddress);
		
		} catch (Exception e) {
		}
        
		return response;
	}
	
	
	@RequestMapping(value = "/DQRCallback", method = { RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public void PaytmDQRCallback(ServletRequest request) {
		LOGGER.info("DQRCallback requestBody:- " + request.getParameter("ORDERID"));
		String responseObj = "msg";
		Map<String, String> responseMap = new HashMap<>(0);
		Map<Long, Double> details = new HashMap<>(0);
		final Map<Long, Long> billDetails = new HashMap<>(0);
		PaymentTransactionMas master = null;
		String redirectURL="";
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		responseMap.put(MainetConstants.BankParam.TXN_ID, responseMap.get("ORDERID"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, "1");
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PAY_SRC,responseMap.get("BANKNAME"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.MODE,responseMap.get("PAYMENTMODE"));
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.BNK_REF_NO,responseMap.get("BANKTXNID"));
		if ("TXN_SUCCESS".equalsIgnoreCase(responseMap.get("STATUS"))) {
			redirectURL = "PaytmPayments:?requestId=123;method=showSuccessScreen;mid=NAGARN91949950010751;portName=COM4;baudRate=115200;parity=0;dataBits=8;stopBits=1;order_id="+responseMap.get("ORDERID")+";order_amount="+responseMap.get("TXNAMOUNT")+";currencySign="+responseMap.get("CURRENCY")+";debugMode=1;posid=POS-1";
			responseMap.put(MainetConstants.BankParam.HASH,redirectURL);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.SUCCESS_MSG);
			try {
				master = paymentService.proceesTransactionAfterPayment(responseMap);
			} catch (Exception exp) {
				LOGGER.error("Exception occurs while processing transaction after payment ", exp);
			}
			BillPaymentDetailDto billPayDto = propertyBillPaymentService.getBillPaymentDetail(null,
					master.getReferenceId(), null, null, null, null, null);
			final ServiceMaster service = ApplicationContextProvider.getApplicationContext()
					.getBean(ServiceMasterService.class).getServiceMasterByShortCode("PBP", billPayDto.getOrgId());
			Organisation org = new Organisation();
			org.setOrgid(billPayDto.getOrgId());
			CommonChallanDTO offline = new CommonChallanDTO();
			final Long payId = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.PAY_PREFIX.ONLINE,
					MainetConstants.PAY_PREFIX.PREFIX_VALUE, org).getLookUpId();
			offline.setPgRefId(Long.parseLong(responseMap.get("ORDERID")));
			offline.setPayModeIn(payId);
			offline.setOrgId(billPayDto.getOrgId());
			offline.setAmountToPay(responseMap.get("TXNAMOUNT"));
			offline.setDeptId(service.getTbDepartment().getDpDeptid());
			offline.setUniquePrimaryId(billPayDto.getPropNo());
			offline.setLgIpMac("PAYTM");
			offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
			offline.setPaymentStatus(MainetConstants.PAYU_STATUS.SUCCESS);
			offline.setNewHouseNo(billPayDto.getNewHouseNo());
			offline.setPropNoConnNoEstateNoL("Property No.");
			offline.setPropNoConnNoEstateNoV(billPayDto.getPropNo());
			offline.setFaYearId(iFinancialYearService.getFinanceYearId(new Date()).toString());
			offline.setPaymentCategory(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE);
			FinancialYear finincialYearsById = iFinancialYearService.getFinincialYearsById(Long.valueOf(offline.getFaYearId()), org.getOrgid());
			offline.setFinYearStartDate(finincialYearsById.getFaFromDate());
			offline.setFinYearEndDate(finincialYearsById.getFaToDate());
			offline.setParentPropNo(billPayDto.getParentPropNo());
			offline.setApplicantName(billPayDto.getOwnerFullName());
			offline.setApplicantFullName(billPayDto.getOwnerFullName());
			offline.setPayeeName(billPayDto.getOwnerFullName());
			offline.setApplicantAddress(billPayDto.getAddress());
			offline.setUniquePrimaryId(billPayDto.getPropNo());
			offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
			offline.setServiceId(billPayDto.getServiceId());
			offline.setDocumentUploaded(false);
			offline.setPlotNo(billPayDto.getPlotNo());
			offline.setUserId(master.getUserId());
			offline.setManualReceiptNo(null);
	        offline.setManualReeiptDate(null);
	        if ((details != null) && !details.isEmpty()) {
	            offline.setFeeIds(details);
	        }
	        if ((billDetails != null) && !billDetails.isEmpty()) {
	            offline.setBillDetIds(billDetails);
	        }
			offline.setOfflinePaymentText(CommonMasterUtility
					.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), org).getLookUpCode());
			offline.setUsageType(billPayDto.getUsageType1());
			if (billPayDto.getWard1() != null) {
				offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
			}
			if (billPayDto.getWard2() != null) {
				offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
			}
			if (billPayDto.getWard3() != null) {
				offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
			}
			if (billPayDto.getWard4() != null) {
				offline.getDwzDTO().setAreaDivision4(billPayDto.getWard4());
			}
			if (billPayDto.getWard5() != null) {
				offline.getDwzDTO().setAreaDivision5(billPayDto.getWard5());
			}
			offline.setReferenceNo(billPayDto.getOldpropno());
			offline.setPdRv(billPayDto.getPdRv());
			offline.setParentPropNo(billPayDto.getParentPropNo());
			if ((billPayDto.getAssmtDto().getAssParshadWard1() != null)
					&& (billPayDto.getAssmtDto().getAssParshadWard1() > 0)) {
				offline.setParshadWard1(CommonMasterUtility
						.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard1(), org).getLookUpDesc());
			}
			if ((billPayDto.getAssmtDto().getAssParshadWard2() != null)
					&& (billPayDto.getAssmtDto().getAssParshadWard2() > 0)) {
				offline.setParshadWard2(CommonMasterUtility
						.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard2(), org).getLookUpDesc());
			}
			if ((billPayDto.getAssmtDto().getAssParshadWard3() != null)
					&& (billPayDto.getAssmtDto().getAssParshadWard3() > 0)) {
				offline.setParshadWard3(CommonMasterUtility
						.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard3(), org).getLookUpDesc());
			}
			if ((billPayDto.getAssmtDto().getAssParshadWard4() != null)
					&& (billPayDto.getAssmtDto().getAssParshadWard4() > 0)) {
				offline.setParshadWard4(CommonMasterUtility
						.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard4(), org).getLookUpDesc());
			}
			if ((billPayDto.getAssmtDto().getAssParshadWard5() != null)
					&& (billPayDto.getAssmtDto().getAssParshadWard5() > 0)) {
				offline.setParshadWard5(CommonMasterUtility
						.getHierarchicalLookUp(billPayDto.getAssmtDto().getAssParshadWard5(), org).getLookUpDesc());
			}
			ChallanReceiptPrintDTO receiptDto = iChallanService.updateDataAfterPayment(offline);
			responseObj = ApplicationSession.getInstance().getMessage("Payment Successfull for Property No:- "+master.getReferenceId());
		} else if("TXN_FAILURE".equalsIgnoreCase(responseMap.get("STATUS"))) {
			redirectURL =ApplicationSession.getInstance().getMessage("paytm.dqr.homescreen");
			responseMap.put(MainetConstants.BankParam.HASH,redirectURL);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,MainetConstants.FAILURE_MSG);
			try {
				master = paymentService.proceesTransactionAfterPayment(responseMap);
			} catch (Exception exp) {
				LOGGER.error("Exception occurs while processing transaction after payment ", exp);
			}
			responseObj = ApplicationSession.getInstance().getMessage("Payment Failed for Property No:- "+master.getReferenceId());
		}
		LOGGER.info("DQRCallback redirectURL :- " + redirectURL + "DQRCallback message :-"+ responseObj );
	}
	
	@RequestMapping(value = "/PropertyTax/Report/WardWiseDemandCollection", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public Map<String, String> getWardWiseDemandCollection(ServletRequest servletRequest){
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		Map<String, String> response = new HashMap<String, String>();
		return response;
	}
}
