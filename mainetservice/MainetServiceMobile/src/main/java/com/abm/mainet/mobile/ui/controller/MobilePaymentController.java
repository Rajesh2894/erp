
package com.abm.mainet.mobile.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.PaymentDetailResDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.exception.InvalidRequestException;
import com.abm.mainet.common.integration.dto.BankRespDTO;
import com.abm.mainet.common.integration.payment.dto.BankDTO;
import com.abm.mainet.common.integration.payment.dto.PayURequestDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentReceiptDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.integration.payment.service.PaymentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.mobile.dto.CommonAppRequestDTO;
import com.abm.mainet.mobile.dto.MobilePaymentReqDTO;
import com.abm.mainet.mobile.dto.MobilePaymentRespDTO;
import com.abm.mainet.smsemail.service.ISMSService;

@Controller
@RequestMapping("/mobilePaymentController")
public class MobilePaymentController {

	private static final Logger LOG = Logger.getLogger(MobilePaymentController.class);

	@Autowired
	private PaymentService paymentService;

	@Resource
	private IChallanService iChallanService;

	@Autowired
	private TbLoiMasService tbLoiMasServiceImpl;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	
    @RequestMapping(value = "/getPgList", method = RequestMethod.POST)
	@ResponseBody
	Object getPaymentGatewayList(@RequestBody @Valid final CommonAppRequestDTO requestDTO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {

		LOG.info("Inter the getOnlineServiceList  Method in MobileOnlinePaymentController");
		final BankRespDTO responseDTO = new BankRespDTO();
		if (!bindingResult.hasErrors()) {
			Map<Long, String> map = paymentService.getAllPgBank(requestDTO.getOrgId());
			if ((map != null) && !map.isEmpty()) {
				List<BankDTO> bankVOs = new ArrayList<>();
				BankDTO bankVO = null;
				for (final Map.Entry<Long, String> m : map.entrySet()) {
					bankVO = new BankDTO();
					bankVO.setBankId(m.getKey());
					bankVO.setCbbankname(m.getValue());
					bankVOs.add(bankVO);
				}
				responseDTO.setResponseMsg(MainetConstants.PAYU_STATUS.COMM_RES_MSG);
				responseDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
				responseDTO.setList(bankVOs);
			} else {

				responseDTO.setErrorMsg(MainetConstants.PAYU_STATUS.COMM_ERR_MSG);
				responseDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			}

		} else {
			LOG.error("Error during binding  OnlinePaymentRequest in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseDTO;
	}

	@RequestMapping(value = "/getPrefixDataForPayAtCounter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object payAtCounterPrefix(@RequestBody final CommonAppRequestDTO requestDTO,
			final HttpServletRequest httprequest) {
		final Map<String, List<LookUp>> lookupMap = new HashMap<>(0);
		final Organisation organisation = new Organisation();
		organisation.setOrgid(requestDTO.getOrgId());
		final List<LookUp> bankLookUp = new ArrayList<>(0);
		LookUp bank = null;
		final List<LookUp> payPrefix = CommonMasterUtility.getListLookup(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
				organisation);
		lookupMap.put(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER, payPrefix);
		final Map<Long, String> banks = ApplicationSession.getInstance().getCustomerBanks();
		if ((banks != null) && !banks.isEmpty()) {
			for (final Entry<Long, String> entry : banks.entrySet()) {
				bank = new LookUp();
				bank.setLookUpId(entry.getKey());
				bank.setLookUpDesc(entry.getValue());
				bankLookUp.add(bank);
			}
		}
		lookupMap.put("BANK", bankLookUp);
		return lookupMap;
	}

	@RequestMapping(value = "/savePaymentRequest", method = RequestMethod.POST)
	@ResponseBody
	Object savePaymentRequest(@RequestBody @Valid final MobilePaymentReqDTO mobilePaymentReqDTO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
		LOG.info("Inter the OnlinePaymentRequest  Method in MobileOnlinePaymentController");
		LOG.info("MobilePaymentReqDTO " + mobilePaymentReqDTO.toString());
		final MobilePaymentRespDTO responseDTO = new MobilePaymentRespDTO();
		Assert.notNull(mobilePaymentReqDTO, "Connot be null");
		if (!bindingResult.hasErrors()) {
			PaymentRequestDTO paymentRequestDTO = convertMobileToPayment(mobilePaymentReqDTO, request);
			try {
				paymentRequestDTO.setControlUrl(request.getRequestURL().toString());
				paymentService.proceesTransactionOnApplication(request, paymentRequestDTO);

				final String amount = EncryptionAndDecryption.encrypt(paymentRequestDTO.getDueAmt().toString());
				paymentRequestDTO.setFinalAmount(amount);
				paymentService.proceesTransactionBeforePayment(request, paymentRequestDTO);
				

				createMobileResponseObjectAfterProcessTransactionBeforePayment(mobilePaymentReqDTO, responseDTO,
						paymentRequestDTO);

			} catch (Exception exp) {
				LOG.error("Exeception Occur in saveOnlineTransactionMaster ", exp);
			}

		} else {
			LOG.error("Error during binding  OnlinePaymentRequest in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseDTO;
	}

	@RequestMapping(value = "/cancelPaymentRequest", method = RequestMethod.POST)
	@ResponseBody
	Object cancelPaymentRequest(@RequestBody @Valid final MobilePaymentReqDTO mobilePaymentReqDTO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
		LOG.info("Inter the OnlinePaymentRequest  Method in MobileOnlinePaymentController");
		LOG.info("MobilePaymentReqDTO " + mobilePaymentReqDTO.toString());
		final MobilePaymentRespDTO responseDTO = new MobilePaymentRespDTO();
		Assert.notNull(mobilePaymentReqDTO, "Connot be null");
		if (!bindingResult.hasErrors()) {
			try {
				PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
				paymentRequestDTO.setTxnid(mobilePaymentReqDTO.getTxnId());
				paymentService.cancelTransactionBeforePayment(request, paymentRequestDTO);
			} catch (Exception exp) {
				LOG.error("Exeception Occur in cancelPaymentRequest ", exp);
			}

		} else {
			LOG.error("Error during binding  cancelPaymentRequest in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseDTO;
	}

	private void createMobileResponseObjectAfterProcessTransactionBeforePayment(
			final MobilePaymentReqDTO mobilePaymentReqDTO, final MobilePaymentRespDTO responseDTO,
			PaymentRequestDTO paymentRequestDTO) {
		boolean hdfcStatus = paymentRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.HDFC)
				&& (paymentRequestDTO.getPayRequestMsg() != null) && !paymentRequestDTO.getPayRequestMsg().isEmpty();
		boolean mahaOnlineStatus = paymentRequestDTO.getPgName()
				.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.MAHA_ONLINE)
				&& (paymentRequestDTO.getPayRequestMsg() != null) && !paymentRequestDTO.getPayRequestMsg().isEmpty();
		boolean techProcessStatus = paymentRequestDTO.getPgName()
				.equalsIgnoreCase(MainetConstants.PG_SHORTNAME.TECH_PROCESS)
				&& (paymentRequestDTO.getPayRequestMsg() != null) && !paymentRequestDTO.getPayRequestMsg().isEmpty();
		boolean easyPayStatus = paymentRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.EASYPAY)
				&& (paymentRequestDTO.getPayRequestMsg() != null) && !paymentRequestDTO.getPayRequestMsg().isEmpty();
		boolean awlineStatus = paymentRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.AWL)
				&& (paymentRequestDTO.getPayRequestMsg() != null) && !paymentRequestDTO.getPayRequestMsg().isEmpty();

		responseDTO.setTxnId(paymentRequestDTO.getTxnid());
		if (paymentRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.PAYU)) {
			final PayURequestDTO dto = new PayURequestDTO();
			dto.setKey(paymentRequestDTO.getKey());
			dto.setAmount(paymentRequestDTO.getDueAmt().toString());
			dto.setCurl(paymentRequestDTO.getCancelUrl());
			dto.setEmail(paymentRequestDTO.getEmail());
			dto.setFirstname(paymentRequestDTO.getApplicantName());
			dto.setFurl(paymentRequestDTO.getFailUrl());
			dto.setHash(paymentRequestDTO.getHash());
			dto.setPg(paymentRequestDTO.getPgName());
			dto.setPhone(paymentRequestDTO.getMobNo());
			dto.setProductinfo(paymentRequestDTO.getServiceName());
			dto.setSurl(paymentRequestDTO.getSuccessUrl());
			dto.setTxnid(paymentRequestDTO.getTxnid().toString());
			dto.setUdf1(paymentRequestDTO.getUdf1());
			dto.setUdf2(paymentRequestDTO.getUdf2());
			dto.setUdf3(paymentRequestDTO.getUdf3());
			dto.setUdf4(paymentRequestDTO.getUdf4());
			dto.setUdf5(paymentRequestDTO.getUdf5());
			dto.setUdf6(paymentRequestDTO.getUdf6());
			dto.setUdf7(paymentRequestDTO.getUdf7());
			dto.setUdf8(paymentRequestDTO.getUdf8());
			dto.setUdf9(paymentRequestDTO.getUdf9());
			dto.setUdf10(paymentRequestDTO.getUdf10());
			dto.setPayuUrl(paymentRequestDTO.getPgUrl());

			responseDTO.setPayuReqdto(dto);
			responseDTO.setErrorlist(Collections.<String>emptyList());
			responseDTO.setLangId(1);
			responseDTO.setOrgId(mobilePaymentReqDTO.getOrgId());
			responseDTO.setPayRequestMsg(MainetConstants.REQUIRED_PG_PARAM.NA);
			responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("eip.payment.successmsg"));

			responseDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
		} else if (hdfcStatus || mahaOnlineStatus || techProcessStatus || easyPayStatus || awlineStatus) {
			responseDTO.setErrorlist(Collections.<String>emptyList());
			responseDTO.setLangId(1);
			responseDTO.setOrgId(mobilePaymentReqDTO.getOrgId());
			responseDTO.setPayRequestMsg(paymentRequestDTO.getPayRequestMsg());
			responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.payment.successmsg"));
			responseDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
		} else if (paymentRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.CCA)
				&& (paymentRequestDTO.getPayRequestMsg() != null) && !paymentRequestDTO.getPayRequestMsg().isEmpty()) {
			responseDTO.setErrorlist(Collections.<String>emptyList());
			responseDTO.setLangId(1);
			responseDTO.setOrgId(mobilePaymentReqDTO.getOrgId());
			responseDTO.setPayRequestMsg(paymentRequestDTO.getPayRequestMsg());
			responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("eip.payment.successmsg"));
			responseDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);

		} else if (paymentRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.RAZORPAY)
				&& (paymentRequestDTO.getPayRequestMsg() != null) && !paymentRequestDTO.getPayRequestMsg().isEmpty()) {
			responseDTO.setErrorlist(Collections.<String>emptyList());
			responseDTO.setLangId(1);
			responseDTO.setOrgId(mobilePaymentReqDTO.getOrgId());
			responseDTO.setPayRequestMsg(paymentRequestDTO.getPayRequestMsg());
			responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("eip.payment.successmsg"));
			responseDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
		}
		else if (paymentRequestDTO.getPgName().equalsIgnoreCase(MainetConstants.PG_SHORTNAME.ATOMPAY)) {
			responseDTO.setErrorlist(Collections.<String>emptyList());
			responseDTO.setLangId(1);
			responseDTO.setOrgId(mobilePaymentReqDTO.getOrgId());
			responseDTO.setPayRequestMsg(paymentRequestDTO.getPayRequestMsg());
			responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("eip.payment.successmsg"));
			responseDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
     }
		else {
			responseDTO.setErrorlist(paymentRequestDTO.getErrors());
			responseDTO.setLangId(1);
			responseDTO.setOrgId(mobilePaymentReqDTO.getOrgId());
			responseDTO.setResponseMsg(ApplicationSession.getInstance().getMessage("app.payment.failsmsg"));
			responseDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
		}
	}

	private PaymentRequestDTO convertMobileToPayment(final MobilePaymentReqDTO mobilePaymentReqDTO,
			final HttpServletRequest request) {
		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
		final ServiceMaster service = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(mobilePaymentReqDTO.getServiceShortName(), mobilePaymentReqDTO.getOrgId());
		if (service != null) {
			paymentRequestDTO.setUdf1(service.getSmServiceId().toString());
			paymentRequestDTO.setServiceId(service.getSmServiceId());
			if (mobilePaymentReqDTO.getLangId() == 1) {
				paymentRequestDTO.setServiceName(service.getSmServiceName());
			} else {
				paymentRequestDTO.setServiceName(service.getSmServiceNameMar());
			}
		}
		final String url = request.getRequestURL().toString();
		paymentRequestDTO.setUdf2(mobilePaymentReqDTO.getReferenceId());
		paymentRequestDTO.setUdf3(mobilePaymentReqDTO.getUdf1());
		paymentRequestDTO.setUdf5(mobilePaymentReqDTO.getServiceShortName());
		paymentRequestDTO.setUdf6(mobilePaymentReqDTO.getOrgId().toString());
		paymentRequestDTO.setUdf7(String.valueOf(mobilePaymentReqDTO.getUserId()));
		paymentRequestDTO.setMobNo(mobilePaymentReqDTO.getMobileNo());
		paymentRequestDTO.setValidateAmount(mobilePaymentReqDTO.getDueAmt());
		paymentRequestDTO.setBankId(mobilePaymentReqDTO.getBankId());
		paymentRequestDTO.setControlUrl(MainetConstants.CommonConstants.NA);
		paymentRequestDTO.setFailUrl(url + MainetConstants.REQUIRED_PG_PARAM.FAIL);
		paymentRequestDTO.setCancelUrl(url + MainetConstants.REQUIRED_PG_PARAM.CANCEL);
		paymentRequestDTO.setSuccessUrl(url + MainetConstants.REQUIRED_PG_PARAM.SUCCESS_PAYU);
		paymentRequestDTO.setChallanServiceType(mobilePaymentReqDTO.getChallanServiceType());
		if (mobilePaymentReqDTO.getFeeIds() != null) {
			paymentRequestDTO.setFeeIds(mobilePaymentReqDTO.getFeeIds().toString());
		}
		if (mobilePaymentReqDTO.getDocumentUploaded()) {
			paymentRequestDTO.setDocumentUploaded("Y");
		} else {
			paymentRequestDTO.setDocumentUploaded("N");
		}
		paymentRequestDTO.setOrgId(mobilePaymentReqDTO.getOrgId());
		paymentRequestDTO.setEmpId(mobilePaymentReqDTO.getUserId());
		if(mobilePaymentReqDTO.getLangId()!=null)
		paymentRequestDTO.setLangId(mobilePaymentReqDTO.getLangId().intValue());

		/*
		 * Json passes from mobile : [applicantName=Vivek, mobileNo=9098889240,
		 * email=anuj.dixit@abmindia.com, bankId=171, serviceShortName= MUT,
		 * referenceId= CGMNCR0005672000, dueAmt= 500, securityId=null, udf1=
		 * 8721060802011, udf2=Y, udf3=null, udf4=null, udf5=null, udf6=null, udf7=null,
		 * challanServiceType=N, feeIds={1=500.0}, documentUploaded=false, txnId=null]
		 */
		paymentRequestDTO.setApplicationId(mobilePaymentReqDTO.getUdf1());
		paymentRequestDTO.setApplicantName(mobilePaymentReqDTO.getApplicantName().trim());
		paymentRequestDTO.setDueAmt(mobilePaymentReqDTO.getDueAmt());
		if ((mobilePaymentReqDTO.getEmail() != null) && !mobilePaymentReqDTO.getEmail().isEmpty()) {
			paymentRequestDTO.setEmail(mobilePaymentReqDTO.getEmail());
		} else {
			paymentRequestDTO.setEmail(MainetConstants.REQUIRED_PG_PARAM.NA);
		}
		paymentRequestDTO.setPayModeorType(MainetConstants.PAYMODE.MOBILE);
		Organisation org = new Organisation();
        org.setOrgid(mobilePaymentReqDTO.getOrgId());
		if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) && MainetConstants.MobileCommon.IOS.equalsIgnoreCase(mobilePaymentReqDTO.getUdf4())) {
			paymentRequestDTO.setUdf10(MainetConstants.MobileCommon.IOS);
		}
		return paymentRequestDTO;
	}

	/**
	 * This Method are used for get Online Payment Response status is success and
	 * update Payment Response in your database against the services.
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/HdfcRespSuccess/{orgId}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentHdfcRespSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Properties properties = new Properties();
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf("1"));
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			if (!properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_KEY).isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY,
						properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_KEY));

			}
			if (!properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_IV).isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV,
						properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_IV));
			}

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
					properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_BANKID));
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.HDFC, null,
					orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/HdfcRespFail/{orgId}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentHdfcRespFail(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Properties properties = new Properties();
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf("1"));
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			if (!properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_KEY).isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY,
						properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_KEY));

			}
			if (!properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_IV).isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV,
						properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_IV));
			}

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
					properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_BANKID));
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.HDFC, null,
					orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/HdfcRespCancel/{orgId}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentHdfcRespCancel(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Properties properties = new Properties();
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf("1"));
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			if (!properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_KEY).isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY,
						properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_KEY));

			}
			if (!properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_IV).isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV,
						properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_IV));
			}

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
					properties.getProperty(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_BANKID));
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.HDFC, null,
					orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/MolRespSuccess/{orgId}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentMolRespSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.MAHA_ONLINE,
					null, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}
	}

	@RequestMapping(value = "/MolRespFail/{orgId}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentMolRespFail(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.MAHA_ONLINE,
					null, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}
	}

	@RequestMapping(value = "/MolRespCancel/{orgId}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentMolRespCancel(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.MAHA_ONLINE,
					null, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}
	}

	@RequestMapping(value = "/TPRespSuccess/{orgId}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentTPRespSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf("1"));
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {

			if (!ApplicationSession.getInstance().getMessage("eip.payment.key").isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY,
						ApplicationSession.getInstance().getMessage("eip.payment.key"));

			}
			if (!ApplicationSession.getInstance().getMessage("eip.payment.iv").isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV,
						ApplicationSession.getInstance().getMessage("eip.payment.iv"));
			}

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
					ApplicationSession.getInstance().getMessage("eip.payment.bankId"));

			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.TECH_PROCESS,
					null, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.FAILVIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/TPRespFail/{orgId}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentTPRespFail(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);

		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf("1"));
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			if (!ApplicationSession.getInstance().getMessage("eip.payment.key").isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY,
						ApplicationSession.getInstance().getMessage("eip.payment.key"));

			}
			if (!ApplicationSession.getInstance().getMessage("eip.payment.iv").isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV,
						ApplicationSession.getInstance().getMessage("eip.payment.iv"));
			}

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
					ApplicationSession.getInstance().getMessage("eip.payment.bankId"));
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.TECH_PROCESS,
					null, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/TPRespCancel/{orgId}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePaymentTPRespCancel(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);

		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.LANG, String.valueOf("1"));
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			if (!ApplicationSession.getInstance().getMessage("eip.payment.key").isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.KEY,
						ApplicationSession.getInstance().getMessage("eip.payment.key"));

			}
			if (!ApplicationSession.getInstance().getMessage("eip.payment.iv").isEmpty()) {
				responseMap.put(MainetConstants.REQUIRED_PG_PARAM.IV,
						ApplicationSession.getInstance().getMessage("eip.payment.iv"));
			}

			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.PG_ID,
					ApplicationSession.getInstance().getMessage("eip.payment.bankId"));
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.TECH_PROCESS,
					null, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/PayuRespSuccess/{orgId}/{amount}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePayuRespSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.PAYU,
					decryptedAmount, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/PayuRespFail/{orgId}/{amount}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePayuRespFail(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.PAYU,
					decryptedAmount, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/PayuRespCancel/{orgId}/{amount}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlinePayuRespCancel(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.PAYU,
					decryptedAmount, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	// mobile call back sucesss URL
	@RequestMapping(value = "/CCAvenueRespSuccess/{orgId}/{amount}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlineCCAvenueRespSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			LOG.info("processGatewayRequest call statrted Method in MobileOnlinePaymentController");
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.CCA,
					decryptedAmount, orgId, 1, paymentReceiptDTO);
			LOG.info("processGatewayRequest call end Method in MobileOnlinePaymentController: " + paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				LOG.info("processGatewayRequest call FAILED: " + paymentReceiptDTO);
				return new ModelAndView(MainetConstants.COMMON.FAILVIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				LOG.info("processGatewayRequest call SUCCESS: " + paymentReceiptDTO);
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			LOG.info("processGatewayRequest call in exception" + exp.getMessage());
			LOG.error(exp.getMessage(), exp);
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/CCAvenueRespCancel/{orgId}/{amount}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlineCCAvenueRespCancel(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.CCA,
					decryptedAmount, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.FAILVIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/CCAvenueRespFail/{orgId}/{amount}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlineCCAvenueRespFail(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.CCA,
					decryptedAmount, orgId, 1, paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				return new ModelAndView(MainetConstants.COMMON.FAILVIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	private PaymentReceiptDTO processGatewayRequest(Map<String, String> responseMap, HttpServletRequest request,
			String gatewayFlag, String sessionAmount, Long orgId, Integer langId, PaymentReceiptDTO paymentReceiptDTO)
			throws Exception {
		LOG.error("Start processGatewayRequest in mobilepaymentController");
		responseMap = paymentService.genrateResponse(responseMap, gatewayFlag, sessionAmount, orgId, langId);
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGID, orgId.toString());
		responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID,
				responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1));
		LOG.error("after genrateResponse in processGatewayRequest of mobilepaymentController responseMap : "
				+ responseMap);
		PaymentTransactionMas master = null;
		try {
			master = paymentService.proceesTransactionAfterPayment(responseMap);
		} catch (Exception exp) {
			LOG.error("Exception occurs while processing transaction after payment ", exp);
		}

		if ((master != null) && (master.getRecvStatus() != null)
				&& master.getRecvStatus().equalsIgnoreCase(MainetConstants.PAYU_STATUS.SUCCESS)) {
			LOG.info("in case of success processGatewayRequest in mobilepaymentController");
			paymentReceiptDTO = processtransactionAfterPaymentWithupdateChallanData(request, master, responseMap);
			LOG.info("processGatewayRequest in mobilepaymentControlle completed");
		} else {
			// Defect# 126498 Failure and cancel payment receipt should be generated. and
			// record should be updated in
			// TB_ONL_TRAN_MAS_SERVICE table
			paymentReceiptDTO.setFirstName(Utility.toCamelCase(master.getSendFirstname()));
			paymentReceiptDTO.setEmail(master.getSendEmail());
			paymentReceiptDTO.setMobileNo(master.getSendPhone());
			// paymentReceiptDTO.setNetAmount(master.getRecvNetAmountDebit());
			if (master.getSendAmount() != null) {
				paymentReceiptDTO.setAmount(master.getSendAmount().doubleValue());
			}
			paymentReceiptDTO.setProductinfo(Utility.toCamelCase(master.getSendProductinfo()));
			paymentReceiptDTO.setPaymentDateTime(master.getLmodDate());
			try {
				paymentReceiptDTO.setTrackId(Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF3)));
			} catch (Exception e) {
				paymentReceiptDTO.setTrackId(0);
				Log.error("Application Id is not present " + e.getMessage());
			}
			paymentReceiptDTO.setTransactionId(master.getRecvMihpayid());
			LOG.info("Application Id is :" + master.getReferenceId());
			paymentReceiptDTO.setApplicationId(master.getReferenceId());
			// paymentReceiptDTO.setServiceType(Utility.toCamelCase(master.getSendProductinfo()));
			// paymentReceiptDTO.setErrorMsg(master.getRecvErrm());
			// paymentReceiptDTO.setOrgId(master.getOrgId());
			// paymentReceiptDTO.setEmpId(master.getUserId());
			paymentReceiptDTO.setLabelName(
					ApplicationSession.getInstance().getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_APPNO));
			// END Defect# 126498
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			if ((responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID) != null)
					&& !responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID).isEmpty()) {
				paymentReceiptDTO.setErrorMsg("Please save the Track ID"
						+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
						+ " for future Reference");
			} else {
				paymentReceiptDTO.setErrorMsg("Problem While Decrypting the encrypted response");
			}

		}
		LOG.error("processGatewayRequest in mobilepaymentController ends");
		return paymentReceiptDTO;
	}

	// this is used for mobile online payment
	private PaymentReceiptDTO processtransactionAfterPaymentWithupdateChallanData(final HttpServletRequest request,
			PaymentTransactionMas master, Map<String, String> responseMap) {
		LOG.info("processtransactionAfterPaymentWithupdateChallanData method is started");
		final PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		paymentReceiptDTO.setFirstName(Utility.toCamelCase(master.getSendFirstname()));
		paymentReceiptDTO.setEmail(master.getSendEmail());
		paymentReceiptDTO.setMobileNo(master.getSendPhone());
		paymentReceiptDTO.setNetAmount(master.getRecvNetAmountDebit());
		if (master.getSendAmount() != null) {
			paymentReceiptDTO.setAmount(master.getSendAmount().doubleValue());
		}
		paymentReceiptDTO.setProductinfo(Utility.toCamelCase(master.getSendProductinfo()));
		paymentReceiptDTO.setPaymentDateTime(master.getLmodDate());
		try {
			paymentReceiptDTO.setTrackId(Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)));
		} catch (Exception e) {
			paymentReceiptDTO.setTrackId(0);
			Log.error("Application Id is not present " + e.getMessage());
		}
		paymentReceiptDTO.setBankRefNo(master.getRecvBankRefNum());
		paymentReceiptDTO.setTransactionId(master.getRecvMihpayid());
		paymentReceiptDTO.setApplicationId(master.getReferenceId());
		paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
		paymentReceiptDTO.setServiceType(Utility.toCamelCase(master.getSendProductinfo()));
		paymentReceiptDTO.setErrorMsg(master.getRecvErrm());
		paymentReceiptDTO.setOrgId(master.getOrgId());
		paymentReceiptDTO.setEmpId(master.getUserId());
		if(master.getLangId()==2) {
			paymentReceiptDTO.setLabelName(
					ApplicationSession.getInstance().getMessage("eip.payment.applNo.reg"));
		
		}else {
			paymentReceiptDTO.setLabelName(
					ApplicationSession.getInstance().getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_APPNO));
		}
		paymentReceiptDTO.setLangId(master.getLangId());
		LOG.info("Called prepareChallanDTO method");
		try {
			prepareChallanDTO(request, master, responseMap);
			LOG.info("Ended prepareChallanDTO method " + responseMap);
		} catch (Exception exp) {
			LOG.error("Exception occurs while account posting for online mobile bill payment ", exp);
			throw new FrameworkException(exp);
		}

		final String msg = ApplicationSession.getInstance().getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_MSG,
				new Object[] { paymentReceiptDTO.getLabelName(), String.valueOf(paymentReceiptDTO.getApplicationId()),
						String.valueOf(paymentReceiptDTO.getTrackId()) });
		final String templateId = ApplicationSession.getInstance()
				.getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_TEMPLATE_KEY);
		LOG.info("processtransactionAfterPaymentWithupdateChallanData method sending SMS");
		ApplicationContextProvider.getApplicationContext().getBean(ISMSService.class).sendSMSInBackground(msg,
				String.valueOf(master.getSendPhone()), Integer.valueOf(master.getLangId()), templateId);
		LOG.info("processtransactionAfterPaymentWithupdateChallanData method is end");
		return paymentReceiptDTO;
	}

	// this will call common challan method

	private void prepareChallanDTO(final HttpServletRequest request, PaymentTransactionMas master,
			Map<String, String> responseMap) throws FrameworkException {
		LOG.info("prepareChallanDTO method is started ");
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMaster(master.getSmServiceId(), master.getOrgId());
		final Organisation organisation = new Organisation();
		organisation.setOrgid(master.getOrgId());
		CommonChallanDTO offline = new CommonChallanDTO();
		offline.setOrgId(master.getOrgId());
		offline.setUniquePrimaryId(master.getReferenceId());
		offline.setUserId(MainetConstants.Property.UserId);
		offline.setPgRefId(master.getTranCmId());
		LOG.info("iChallanService getBillDetails is called ");
		if (service.getTbDepartment().getDpDeptcode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.PROPERTY)
				|| service.getTbDepartment().getDpDeptcode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.WATER)
				|| service.getTbDepartment().getDpDeptcode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.ADH)) {
			offline = iChallanService.getBillDetails(offline, service.getTbDepartment().getDpDeptcode());// As per
																											// discussed
																											// with
																											// mayank
		}
		LOG.info("iChallanService getBillDetails is completed ");
		// defect raise in water citizen service payment
		// UDF3 is unique identifier in case of DES(Data Entry Suite) bill payment(water
		// and property) as app no is null
		// and in other cases(assessment,water other citizen services) UDF3 is
		// application no
		String appNo = responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF3);
		if (StringUtils.isNotEmpty(appNo) && StringUtils.isNumeric(appNo)) {
			offline.setApplNo(Long.valueOf(appNo));
		} else {
			// Defect #137091
			if (StringUtils.isNotEmpty(master.getReferenceId()) && StringUtils.isNumeric(master.getReferenceId())) {
				offline.setApplNo(Long.valueOf(master.getReferenceId()));
			}
			offline.setReferenceNo(master.getReferenceId());
		}
		// offline.setApplNo(master.getTranCmId());

		offline.setAmountToPay(master.getSendAmount().toString());
		if (master.getSendEmail() != null) {
			offline.setEmailId(master.getSendEmail());
		}
		offline.setMobileNumber(master.getSendPhone());
		offline.setApplicantName(master.getSendFirstname());
		offline.setChallanServiceType(master.getChallanServiceType());
		if (master.getDocumentUploaded().equals("Y")) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		Employee emp = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
				.findEmployeeById(master.getUserId());
		if (emp != null) {
			offline.setEmpType(emp.getEmplType());
		}
		final Object[] finData = ApplicationContextProvider.getApplicationContext().getBean(IFinancialYearService.class)
				.getFinacialYearByDate(new Date());
		if ((finData != null) && (finData.length > 0)) {
			offline.setFinYearEndDate((Date) finData[2]);
			offline.setFinYearStartDate((Date) finData[1]);
			offline.setFaYearId(finData[0].toString());
		}
		offline.setUserId(master.getUserId());
		offline.setOrgId(master.getOrgId());
		offline.setLangId(master.getLangId());
		offline.setLgIpMac(Utility.getClientIpAddress(request));
		offline.setServiceId(master.getSmServiceId());
		offline.setDeptId(service.getTbDepartment().getDpDeptid());
		offline.setPayModeIn(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.PAY_PREFIX.WEB,
				PrefixConstants.PAY_PREFIX.PREFIX_VALUE, organisation).getLookUpId());
		 Organisation org=new Organisation();
		 org.setOrgid(master.getOrgId());
		 if(!Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) 
		 offline.setUniquePrimaryId(master.getReferenceId());
		
		
		offline.setPaymentStatus(MainetConstants.PAYU_STATUS.SUCCESS);
		/*
		 * User Story #134664 Mobile Application "LOI to be Paid" and
		 * "Application Payment Pending"
		 */
		try {
			List<TbLoiMas> loiMas = new ArrayList<TbLoiMas>();
			Long applNo = null;
			if (StringUtils.isNotEmpty(appNo) && StringUtils.isNumeric(appNo)) {
				applNo = Long.valueOf(appNo);
				loiMas = tbLoiMasServiceImpl.getloiByApplicationId(Long.valueOf(appNo), master.getSmServiceId(),
						master.getOrgId());
			} else {
				loiMas = tbLoiMasServiceImpl.getloiByApplicationId(Long.valueOf(master.getReferenceId()),
						master.getSmServiceId(), master.getOrgId());
				applNo = Long.valueOf(master.getReferenceId());
			}
			if (CollectionUtils.isNotEmpty(loiMas)) {
				offline.setLoiNo(loiMas.get(0).getLoiNo());
			}
			Long taskId = iWorkflowTaskService.getTaskIdByAppIdAndOrgId(applNo, master.getOrgId());
			offline.setTaskId(taskId);
		} catch (Exception e) {
			LOG.error("Exception occurs while fetching mobile side LoiNo for appNo-> " + appNo + "Reference ", e);
		}
		try {
			LookUp paymentModeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.PRIFIX_CODE.PAY_AT_BANK, PrefixConstants.PRIFIX.ONLINE_OFFLINE, organisation);
			if (paymentModeLookUp != null) {
				final Long paymentMode = paymentModeLookUp.getLookUpId();
				offline.setOflPaymentMode(paymentMode);
				offline.setOfflinePaymentText(
						CommonMasterUtility.getNonHierarchicalLookUpObject(paymentMode, organisation).getLookUpCode());
			}
		} catch (Exception e) {
			LOG.error("Exception occurs while fetching lookup for code value-> "
					+ PrefixConstants.PRIFIX_CODE.PAY_AT_BANK, e);
		}
		if (master.getFeeIds() != null && !master.getFeeIds().isEmpty()) {
			Map<Long, Double> map = feeIdStringToMap(master);
			offline.setFeeIds(map);// need to set for all service payment
		}
		try {
			LOG.info("updateDataAfterPayment(offline)  is called ");
			ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class)
					.updateDataAfterPayment(offline);
			LOG.info("updateDataAfterPayment(offline)  is completed ");
		} catch (Exception exp) {
			LOG.error(
					"Exception occurs while calling updateDataAfterPayment of challan Service for online mobile bill payment ",
					exp);
			throw new FrameworkException(exp);
		}
		LOG.info("prepareChallanDTO method is Ends ");
	}

	private Map<Long, Double> feeIdStringToMap(PaymentTransactionMas master) {
		String value = master.getFeeIds();
		value = StringUtils.substringBetween(value, "{", "}"); // remove curly brackets
		String[] keyValuePairs = value.split(","); // split the string to creat key-value pairs
		Map<Long, Double> map = new HashMap<>();

		for (String pair : keyValuePairs) // iterate over the pairs
		{
			String[] entry = pair.split("="); // split the pairs to get key and value
			map.put(Long.valueOf(entry[0].trim()), Double.valueOf(entry[1].trim())); // add them to the hashmap and trim
			// whitespaces
		}
		return map;
	}

	@RequestMapping(value = "/EasypayRespSuccess/{orgId}/{amount}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlineEasypayRespSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Enter into getOnlineEasypayRespSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			LOG.info("Enter the getOnlineEasypayRespSuccess  Method in decryptedAmount  " + decryptedAmount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.EASYPAY,
					decryptedAmount, orgId, 1, paymentReceiptDTO);
			LOG.info("Enter the getOnlineEasypayRespSuccess  Method in decryptedAmount  " + paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.SUCCESS)) {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.FAILVIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}

	@RequestMapping(value = "/RazorpayRespSuccess/{orgId}/{amount}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlineRazorpayRespSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Enter into getOnlineRazorpayRespSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			LOG.info("Enter the getOnlineRazorpayRespSuccess  Method in decryptedAmount  " + decryptedAmount);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, decryptedAmount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.RAZORPAY,
					decryptedAmount, orgId, 1, paymentReceiptDTO);

			LOG.info("Enter the getOnlineRazorpayRespSuccess  Method in decryptedAmount  " + paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.SUCCESS)) {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.FAILVIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}
	
	@RequestMapping(value = "/RazorpayResponseSuccess/{orgId}/{amount}", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	PaymentReceiptDTO getOnlineRazorpayResponseSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Enter into getOnlineRazorpayRespSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		LOG.info("ResponseMap : "+responseMap);
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			LOG.info("Enter the getOnlineRazorpayRespSuccess  Method in decryptedAmount  " + decryptedAmount);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, decryptedAmount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.RAZORPAY,
					decryptedAmount, orgId, 1, paymentReceiptDTO);

			/*if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.SUCCESS)) {
				return paymentReceiptDTO;
			} else {
				return paymentReceiptDTO;
			}*/
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			//return paymentReceiptDTO;
		}
		LOG.info("Success Mobile Request");
		return paymentReceiptDTO;
	}
	@RequestMapping(value = "/AtomRespSuccess/{orgId}/{amount}", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "text/html;charset=UTF-8")
	ModelAndView getOnlineAtomRespSuccess(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("orgId") Long orgId, @PathVariable("amount") String amount) {
		LOG.info("Enter into getOnlineAtomRespSuccess  Method in MobileOnlinePaymentController");
		Map<String, String> responseMap = new HashMap<>(0);
		final Enumeration<String> paramNames = request.getParameterNames();
		String paramName;
		String paramValue;
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValue = request.getParameter(paramName);
			responseMap.put(paramName, paramValue);
		}
		PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
		try {
			String decryptedAmount = EncryptionAndDecryption.decrypt(amount);
			LOG.info("Enter the getOnlineAtomRespSuccess  Method in decryptedAmount  " + decryptedAmount);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SESSION_AMT, decryptedAmount);
			paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.ATOMPAY,
					decryptedAmount, orgId, 1, paymentReceiptDTO);

			LOG.info("Enter the getOnlineAtomRespSuccess  Method in decryptedAmount  " + paymentReceiptDTO);
			if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.SUCCESS)) {
				return new ModelAndView(MainetConstants.COMMON.VIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			} else {
				return new ModelAndView(MainetConstants.COMMON.FAILVIEWNAME, MainetConstants.COMMON.COMMAND,
						paymentReceiptDTO);
			}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ (Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID)))
					+ " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

	}
    //Create new api as per Palam Sir for getting payment details 
	@RequestMapping(value = "/checkPaymentStatus/{appId}", method = RequestMethod.POST)
	@ResponseBody
	PaymentDetailResDto checkPaymentStatus(@PathVariable("appId")  String appId) {
		LOG.info("Inter the checkPaymentStatus  Method in MobileOnlinePaymentController");
		try {
			return paymentService.checkPaymentStatus(appId);
		} catch (Exception e) {
			LOG.info("Exception occured at the time of getting payment status");
			return null;
		}

	}

}
