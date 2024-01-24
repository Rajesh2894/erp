//package com.abm.mainet.authentication.admin.ui.controller;
package com.abm.mainet.common.integration.payment.ui.controller;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dto.PaymentReceiptDTO;
import com.abm.mainet.common.integration.payment.entity.EgrassPaymentENtity;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.integration.payment.service.PaymentService;
import com.abm.mainet.common.integration.payment.ui.model.PaymentModel;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.service.ISMSService;

@Controller
@RequestMapping("/PaymentController.html")
public class PaymentController extends AbstractController<PaymentModel> {

	private static final Logger logger = Logger.getLogger(PaymentController.class);
	

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private TbTaxMasService taxMasterService;
	 @ResponseBody
	@RequestMapping(params = "nicSuccess", method = {RequestMethod.GET,RequestMethod.POST})
	 ModelAndView getOnlinePaymentTPRespSuccess( final HttpServletRequest request) {
		
			logger.info("Inter the OnlinePaymentResponseSuccess  Method in MobileOnlinePaymentController");
			Map<String, String> responseMap = new HashMap<>(0);
			final Enumeration<String> paramNames = request.getParameterNames();
			String paramName;
			String paramValue;
			while (paramNames.hasMoreElements()) {
				paramName = paramNames.nextElement();
				paramValue = request.getParameter(paramName);
				responseMap.put(paramName, paramValue);
			}
			Long orgId=null;
			if(responseMap.get("orgId")!=null)
				orgId=Long.valueOf(responseMap.get("orgId"));
			logger.error("Inside the OnlinePaymentResponseSuccess response parameter values "+ responseMap);
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

				paymentReceiptDTO = processGatewayRequest(responseMap, request, MainetConstants.PG_SHORTNAME.NicGateway,
						null, orgId, 1, paymentReceiptDTO);
				if (paymentReceiptDTO.getStatus().equals(MainetConstants.PAYU_STATUS.FAIL)) {
				
					return new ModelAndView(MainetConstants.COMMON.FAILVIEWNAME, MainetConstants.COMMON.COMMAND,
							paymentReceiptDTO);
				} else {
					if(StringUtils.equalsIgnoreCase(responseMap.get("payment_type"), MainetConstants.FlagN)) {
						paymentReceiptDTO.setStatus(MainetConstants.SUCCESS_MSG);
						return new ModelAndView(MainetConstants.COMMON.VIEWNAME_ONL, MainetConstants.COMMON.COMMAND,
								paymentReceiptDTO);
					}	else {
					return new ModelAndView(MainetConstants.COMMON.VIEWNAME_OFL, MainetConstants.COMMON.COMMAND,
							paymentReceiptDTO);
					}
				}
		} catch (Exception exp) {
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.FAIL);
			paymentReceiptDTO.setErrorMsg("Please save the Track ID"
					+ responseMap.get(MainetConstants.REQUIRED_PG_PARAM.TRACK_ID) + " for future Reference");
			return new ModelAndView(MainetConstants.COMMON.DEFAULT_EXE_VIEWNAME, MainetConstants.COMMON.COMMAND,
					paymentReceiptDTO);
		}

		}
		
		private PaymentReceiptDTO processGatewayRequest(Map<String, String> responseMap, HttpServletRequest request,
				String gatewayFlag, String sessionAmount, Long orgId, Integer langId, PaymentReceiptDTO paymentReceiptDTO)
				throws Exception {
			logger.error("Start processGatewayRequest in mobilepaymentController");
			responseMap = paymentService.genrateResponse(responseMap, gatewayFlag, sessionAmount, orgId, langId);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.ORGID, orgId.toString());
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.SERVICE_ID,
					responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF1));
			logger.error("after genrateResponse in processGatewayRequest of mobilepaymentController responseMap : "
					+ responseMap);
			PaymentTransactionMas master = null;
			try {
				master = paymentService.proceesTransactionAfterPayment(responseMap);
			} catch (Exception exp) {
				logger.error("Exception occurs while processing transaction after payment ", exp);
			}

			if ((master != null) && (master.getRecvStatus() != null)
					&& master.getRecvStatus().equals(MainetConstants.PAYU_STATUS.SUCCESS)) {
				logger.info("in case of success processGatewayRequest in mobilepaymentController");
				paymentReceiptDTO = processtransactionAfterPaymentWithupdateChallanData(request, master, responseMap);
				
				//LOG.info("processGatewayRequest in mobilepaymentControlle completed");
			} else {
				// Defect# 126498 Failure and cancel payment receipt should be generated. and
				// record should be updated in
				// TB_ONL_TRAN_MAS_SERVICE table
				if(master!=null) {
					paymentReceiptDTO.setFirstName(Utility.toCamelCase(master.getSendFirstname()));
					paymentReceiptDTO.setEmail(master.getSendEmail());
					paymentReceiptDTO.setMobileNo(master.getSendPhone());
					// paymentReceiptDTO.setNetAmount(master.getRecvNetAmountDebit());
					if (master.getSendAmount() != null) {
						paymentReceiptDTO.setAmount(master.getSendAmount().doubleValue());
					}
					paymentReceiptDTO.setProductinfo(Utility.toCamelCase(master.getSendProductinfo()));
					paymentReceiptDTO.setPaymentDateTime(master.getLmodDate());
					paymentReceiptDTO.setTransactionId(master.getRecvMihpayid());
					//LOG.info("Application Id is :" + master.getReferenceId());
					paymentReceiptDTO.setApplicationId(master.getReferenceId());
				}
				
				try {
					paymentReceiptDTO.setTrackId(Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF3)));
				} catch (Exception e) {
					paymentReceiptDTO.setTrackId(0);
					logger.error("Application Id is not present " + e.getMessage());
				}

				paymentReceiptDTO.setLabelName(
						ApplicationSession.getInstance().getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_APPNO));
				
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
			return paymentReceiptDTO;
		}
		private PaymentReceiptDTO processtransactionAfterPaymentWithupdateChallanData(final HttpServletRequest request,
				PaymentTransactionMas master, Map<String, String> responseMap) {
			logger.info("processtransactionAfterPaymentWithupdateChallanData method is started");
			final PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
			paymentReceiptDTO.setFirstName(Utility.toCamelCase(master.getSendFirstname()));
			paymentReceiptDTO.setEmail(master.getSendEmail());
			paymentReceiptDTO.setMobileNo(master.getSendPhone());
			if (master.getSendAmount() != null) {
				paymentReceiptDTO.setAmount(master.getSendAmount().doubleValue());
			}
			paymentReceiptDTO.setProductinfo(Utility.toCamelCase(master.getSendProductinfo()));
			paymentReceiptDTO.setPaymentDateTime(master.getLmodDate());
			try {
				paymentReceiptDTO.setTrackId(Long.valueOf(responseMap.get(MainetConstants.REQUIRED_PG_PARAM.UDF3)));
			} catch (Exception e) {
				paymentReceiptDTO.setTrackId(0);
				logger.error("Application Id is not present " + e.getMessage());
			}
			paymentReceiptDTO.setBankRefNo(master.getRecvBankRefNum());
			paymentReceiptDTO.setTransactionId(master.getRecvMihpayid());
			paymentReceiptDTO.setApplicationId(master.getReferenceId());
			paymentReceiptDTO.setStatus(MainetConstants.PAYU_STATUS.SUCCESS);
			paymentReceiptDTO.setServiceType(Utility.toCamelCase(master.getSendProductinfo()));
			paymentReceiptDTO.setErrorMsg(master.getRecvErrm());
			paymentReceiptDTO.setOrgId(master.getOrgId());
			paymentReceiptDTO.setEmpId(master.getUserId());
			paymentReceiptDTO.setTrackId(master.getTranCmId());
			paymentReceiptDTO.setFinYr(Utility.getCurrentFinancialYear());
			if (master.getSendAmount() != null)
			paymentReceiptDTO.setAmountStr(Utility.convertNumberToWord(master.getSendAmount().doubleValue()));
			if(master.getLangId()==2) {
				paymentReceiptDTO.setLabelName(
						ApplicationSession.getInstance().getMessage("eip.payment.applNo.reg"));
			
			}else {
				paymentReceiptDTO.setLabelName(
						ApplicationSession.getInstance().getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_APPNO));
			}
			paymentReceiptDTO.setLangId(master.getLangId());
			//LOG.info("Called prepareChallanDTO method");
			try {
				prepareChallanDTO(request, master, responseMap);
				//setRecieptData(responseMap,master,paymentReceiptDTO);
			} catch (Exception exp) {
				logger.error("Exception occurs while account posting for online mobile bill payment ", exp);
				
			}
            if(master!=null) {
            	EgrassPaymentENtity entity=paymentService.getEgrassChallanData(master.getReferenceId(), master.getOrgId());
            	if(entity!=null) {
            		paymentReceiptDTO.setBankName(entity.getPayBank());
            		if(entity.getChequeDDNo()!=null)
            		paymentReceiptDTO.setChequeNo(entity.getChequeDDNo().toString());
            		if(entity.getChequeDDDate()!=null)
            		paymentReceiptDTO.setChequeDate(Utility.dateToString(entity.getChequeDDDate()));
            		paymentReceiptDTO.setRemark(entity.getRemarks());
            	}
            	Object[] arr=	paymentService.getLicNoAndApplicationId(master.getReferenceId(), master.getOrgId());
            	if(arr!=null &&arr.length>1) {
            		if(arr[0]!=null) paymentReceiptDTO.setCaseId(arr[0].toString());
            		if(arr[1]!=null) paymentReceiptDTO.setAppId(arr[1].toString());
            	}
            }
			final String msg = ApplicationSession.getInstance().getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_MSG,
					new Object[] { paymentReceiptDTO.getLabelName(), String.valueOf(paymentReceiptDTO.getApplicationId()),
							String.valueOf(paymentReceiptDTO.getTrackId()) });
			final String templateId = ApplicationSession.getInstance()
					.getMessage(MainetConstants.REQUIRED_PG_PARAM.EIP_PAY_TEMPLATE_KEY);
			//LOG.info("processtransactionAfterPaymentWithupdateChallanData method sending SMS");
			ApplicationContextProvider.getApplicationContext().getBean(ISMSService.class).sendSMSInBackground(msg,
					String.valueOf(master.getSendPhone()), Integer.valueOf(master.getLangId()), templateId);
			//LOG.info("processtransactionAfterPaymentWithupdateChallanData method is end");
			return paymentReceiptDTO;
		}
		
		// this will call common challan method

		private void prepareChallanDTO(final HttpServletRequest request, PaymentTransactionMas master,
				Map<String, String> responseMap) throws FrameworkException {
			logger.info("prepareChallanDTO method is started ");
			ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMaster(master.getSmServiceId(), master.getOrgId());
			final Organisation organisation = new Organisation();
			organisation.setOrgid(master.getOrgId());
			CommonChallanDTO offline = new CommonChallanDTO();
			offline.setOrgId(master.getOrgId());
			offline.setPgRefId(master.getTranCmId());
			offline.setUniquePrimaryId(master.getReferenceId());
			if(StringUtils.isNotBlank(master.getFlatNo()))
				offline.setFlatNo(master.getFlatNo());
			offline.setUserId(MainetConstants.Property.UserId);
			logger.info("iChallanService getBillDetails is called ");
			logger.info("iChallanService getBillDetails is completed ");
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
			offline.setAmountToPay(master.getSendAmount().toString());
			if (master.getSendEmail() != null) {
				offline.setEmailId(master.getSendEmail());
			}
			offline.setMobileNumber(master.getSendPhone());
			offline.setApplicantName(master.getSendFirstname());
			offline.setChallanServiceType(master.getChallanServiceType());
			if (StringUtils.equalsIgnoreCase(master.getDocumentUploaded(), "Y")) {
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
			offline.setUniquePrimaryId(master.getReferenceId());
			offline.setPaymentStatus(MainetConstants.PAYU_STATUS.SUCCESS);
			if(StringUtils.isEmpty(offline.getWorkflowEnable()))
			//offline.setWorkflowEnable("false");
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
				logger.error("Exception occurs while fetching lookup for code value-> "
						+ PrefixConstants.PRIFIX_CODE.PAY_AT_BANK, e);
			}
			try {
				logger.info("updateDataAfterPayment(offline)  is called ");
				if(StringUtils.equalsIgnoreCase(responseMap.get("payment_type"), MainetConstants.FlagN)) {
				ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class)
						.updateDataAfterPayment(offline);
				logger.info("updateDataAfterPayment(online)  is completed ");
				}
			else {

				ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class)
						.InvokeGenerateChallan(offline);
				logger.info("updateDataAfterPayment(offline)  is completed ");
			}
				
			} catch (Exception exp) {
				logger.error(
						"Exception occurs while calling updateDataAfterPayment of challan Service for online mobile bill payment ",
						exp);
				throw new FrameworkException(exp);
			}
			logger.info("prepareChallanDTO method is Ends ");
		}
		private void setRecieptData(Map<String, String> responseMap, PaymentTransactionMas master,
				PaymentReceiptDTO paymentReceiptDTO) {
			Map<Long, Double> map=getFeeId(master.getFeeIds());
			Map<String, String> maps=new HashMap<>();
			if(map!=null) {
				for(Entry<Long, Double> eMap:map.entrySet()) {
					if(eMap.getKey()!=null)
						maps.put(taxMasterService.findTaxDescByTaxIdAndOrgId(eMap.getKey(), paymentReceiptDTO.getOrgId()),eMap.getValue().toString() );	
				}
			}
			paymentReceiptDTO.setSecurityAmt(maps.get("Scrutiny Fee"));
			paymentReceiptDTO.setLicenseFee(maps.get("Licence Fee"));
		}
		private Map<Long, Double> getFeeId(String feeIds) {

			String feeIds1 = feeIds.replace("{", "").replace("}", "");
			String[] arr = feeIds1.split(",");
			Map<Long, Double> map = new HashMap();
			if (arr != null && arr.length > 0) {
				for (String s : arr) {
					String[] s1 = s.split("=");

					map.put(Long.valueOf(s1[0].toString().trim()), Double.valueOf(s1[1].toString().trim()));
				}
			} 
			return map;
		}
	}


